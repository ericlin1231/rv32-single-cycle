package core

import chisel3._
import chisel3.util.Cat
import chisel3.util.MuxLookup

import parameters.System
import parameters.signals.Opcode
import parameters.signals.Funct3TypeB
import parameters.signals.ALUOp1Source
import parameters.signals.ALUOp2Source

class Execute extends Module {
  val io = IO(new Bundle {
    val instruction         = Input(UInt(System.InstructionWidth))
    val instruction_address = Input(UInt(System.AddrWidth))
    val reg1_data           = Input(UInt(System.DataWidth))
    val reg2_data           = Input(UInt(System.DataWidth))
    val immediate           = Input(UInt(System.DataWidth))
    val aluop1_source       = Input(UInt(1.W))
    val aluop2_source       = Input(UInt(1.W))

    val mem_alu_result  = Output(UInt(System.DataWidth))
    val if_jump_flag    = Output(Bool())
    val if_jump_address = Output(UInt(System.DataWidth))
  })

  val opcode = io.instruction(6, 0)
  val funct3 = io.instruction(14, 12)
  val funct7 = io.instruction(31, 25)
  val rd     = io.instruction(11, 7)
  val uimm   = io.instruction(19, 15)

  val alu      = Module(new ALU)
  val alu_ctrl = Module(new ALUControl)

  alu_ctrl.io.opcode := opcode
  alu_ctrl.io.funct3 := funct3
  alu_ctrl.io.funct7 := funct7

  alu.io.func := alu_ctrl.io.alu_funct
  alu.io.op1  := Mux(io.aluop1_source === ALUOp1Source.Register, io.reg1_data, io.instruction_address)
  alu.io.op2  := Mux(io.aluop2_source === ALUOp2Source.Register, io.reg2_data, io.immediate)

  io.mem_alu_result := alu.io.result
  io.if_jump_flag := ((opcode === Opcode.jal)
                      || (opcode === Opcode.jalr)
                      || (opcode === Opcode.B) && MuxLookup(funct3, false.B)(
                          IndexedSeq(
                            Funct3TypeB.beq  -> (io.reg1_data        === io.reg2_data),
                            Funct3TypeB.bne  -> (io.reg1_data        =/= io.reg2_data),
                            Funct3TypeB.blt  -> (io.reg1_data.asSInt <   io.reg2_data.asSInt),
                            Funct3TypeB.bge  -> (io.reg1_data.asSInt >=  io.reg2_data.asSInt),
                            Funct3TypeB.bltu -> (io.reg1_data.asUInt <   io.reg2_data.asUInt),
                            Funct3TypeB.bgeu -> (io.reg1_data.asUInt >=  io.reg2_data.asUInt)
                          )
                        ))

  io.if_jump_address := io.immediate + Mux(opcode === Opcode.jalr,
                                            io.reg1_data,
                                            io.instruction_address)
}
