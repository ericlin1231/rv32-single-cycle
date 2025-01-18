package core

import scala.collection.immutable.ArraySeq

import chisel3._
import chisel3.util._

import parameters.System
import parameters.signals.Opcode
import parameters.signals.ALUOp1Source
import parameters.signals.ALUOp2Source
import parameters.signals.RegWriteSource

class Decode extends Module {
  val io = IO(new Bundle {
    val instruction = Input(UInt(System.InstructionWidth))

    val regs_reg1_read_address = Output(UInt(System.PhysicalRegisterAddrWidth))
    val regs_reg2_read_address = Output(UInt(System.PhysicalRegisterAddrWidth))
    val ex_immediate           = Output(UInt(System.DataWidth))
    val ex_aluop1_source       = Output(UInt(1.W))
    val ex_aluop2_source       = Output(UInt(1.W))
    val memory_read_enable     = Output(Bool())
    val memory_write_enable    = Output(Bool())
    val wb_reg_write_source    = Output(UInt(2.W))
    val reg_write_enable       = Output(Bool())
    val reg_write_address      = Output(UInt(System.PhysicalRegisterAddrWidth))
  })
  val opcode = io.instruction(6, 0)
  val funct3 = io.instruction(14, 12)
  val funct7 = io.instruction(31, 25)
  val rd     = io.instruction(11, 7)
  val rs1    = io.instruction(19, 15)
  val rs2    = io.instruction(24, 20)

  io.regs_reg1_read_address := Mux(opcode === Opcode.lui, 0.U(System.PhysicalRegisterAddrWidth), rs1)
  io.regs_reg2_read_address := rs2
  val immediate = MuxLookup(opcode, Cat(Fill(20, io.instruction(31)), io.instruction(31, 20)))(
    IndexedSeq(
      Opcode.I -> Cat(Fill(21, io.instruction(31)), io.instruction(30, 20)),
      Opcode.L -> Cat(Fill(21, io.instruction(31)), io.instruction(30, 20)),
      Opcode.jalr  -> Cat(Fill(21, io.instruction(31)), io.instruction(30, 20)),
      Opcode.S -> Cat(Fill(21, io.instruction(31)), io.instruction(30, 25), io.instruction(11, 7)),
      Opcode.B -> Cat(
        Fill(20, io.instruction(31)),
        io.instruction(7),
        io.instruction(30, 25),
        io.instruction(11, 8),
        0.U(1.W)
      ),
      Opcode.lui   -> Cat(io.instruction(31, 12), 0.U(12.W)),
      Opcode.auipc -> Cat(io.instruction(31, 12), 0.U(12.W)),
      // jal's imm represents a multiple of 2 bytes.
      Opcode.jal -> Cat(
        Fill(12, io.instruction(31)),
        io.instruction(19, 12),
        io.instruction(20),
        io.instruction(30, 21),
        0.U(1.W)
      )
    )
  )
  io.ex_immediate := immediate
  io.ex_aluop1_source := Mux(
    opcode === Opcode.auipc || opcode === Opcode.B || opcode === Opcode.jal,
    ALUOp1Source.InstructionAddress,
    ALUOp1Source.Register
  )

  // ALU op2 from reg: R-type,
  // ALU op2 from imm: L-Type (I-type subtype),
  //                   I-type (nop=addi, jalr, csr-class, fence),
  //                   J-type (jal),
  //                   U-type (lui, auipc),
  //                   S-type (rs2 value sent to MemControl, ALU computes rs1 + imm.)
  //                   B-type (rs2 compares with rs1 in jump judge unit, ALU computes jump address PC+imm.)
  io.ex_aluop2_source := Mux(
    opcode === Opcode.RM,
    ALUOp2Source.Register,
    ALUOp2Source.Immediate
  )

  io.memory_read_enable := Mux(
    opcode === Opcode.L,
    true.B,
    false.B
  )

  io.memory_write_enable := Mux(
    opcode === Opcode.S,
    true.B,
    false.B
  )

  io.wb_reg_write_source := MuxCase(
    RegWriteSource.ALUResult,
    ArraySeq(
      (opcode === Opcode.RM
       || opcode === Opcode.I
       || opcode === Opcode.lui
       || opcode === Opcode.auipc) -> RegWriteSource.ALUResult,
      (opcode === Opcode.jal
       || opcode === Opcode.jalr)  -> RegWriteSource.NextInstructionAddress,
      (opcode === Opcode.L)    -> RegWriteSource.Memory
    )
  )

  io.reg_write_enable := ((opcode === Opcode.RM)
                          || (opcode === Opcode.I)
                          || (opcode === Opcode.L)
                          || (opcode === Opcode.auipc)
                          || (opcode === Opcode.lui)
                          || (opcode === Opcode.jal)
                          || (opcode === Opcode.jalr))

  io.reg_write_address := rd
}
