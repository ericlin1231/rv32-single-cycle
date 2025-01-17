package core

import chisel3._
import chisel3.util.Cat

import parameters.System
import bundle.CPUBundle
import peripheral.DataRAM
import peripheral.InstructionROM

class CPU(filename: String) extends Module {
  val io   = IO(new CPUBundle)
  val REGS = Module(new RegisterFile)
  val IROM = Module(new InstructionROM(filename))
  val IF   = Module(new Fetch)
  val ID   = Module(new Decode)
  val EX   = Module(new Execute)
  val MEM  = Module(new DataRAM)
  val WB   = Module(new WriteBack)

  /* Initialization */
  IROM.io.DebugPort.instructionROM_debug_read_address := DontCare
  IROM.io.DebugPort.instructionROM_debug_read_instruction := DontCare

  /* Instruction Fetch */
  IF.io.jump_address_id      := EX.io.if_jump_address
  IF.io.jump_flag_id         := EX.io.if_jump_flag
  IF.io.instruction_valid    := io.instruction_valid
  IF.io.IROMPort.instruction := IROM.io.IROMPort.instruction
  IROM.io.IROMPort.address   := IF.io.IROMPort.address

  /* Instruction Fetch Debug */
  io.fetch_debug_read_pc := IF.io.DebugPort.fetch_debug_read_pc
  io.fetch_debug_read_instruction := IF.io.DebugPort.fetch_debug_read_instruction

  /* InstructionROM Debug */
  IROM.io.DebugPort.instructionROM_debug_read_address := io.instructionROM_debug_read_address
  io.instructionROM_debug_read_instruction := IROM.io.DebugPort.instructionROM_debug_read_instruction

  /* Register File */
  REGS.io.write_enable  := ID.io.reg_write_enable
  REGS.io.write_address := ID.io.reg_write_address
  REGS.io.write_data    := WB.io.regs_write_data
  REGS.io.read_address1 := ID.io.regs_reg1_read_address
  REGS.io.read_address2 := ID.io.regs_reg2_read_address

  /* Register File Debug */
  REGS.io.DebugPort.register_debug_read_address := io.register_debug_read_address
  io.register_debug_read_data                   := REGS.io.DebugPort.register_debug_read_data

  /* Instruction Decoder */
  ID.io.instruction := IF.io.instruction

  /* Execution */
  EX.io.instruction         := IF.io.instruction
  EX.io.instruction_address := IF.io.instruction_address
  EX.io.reg1_data           := REGS.io.read_data1
  EX.io.reg2_data           := REGS.io.read_data2
  EX.io.immediate           := ID.io.ex_immediate
  EX.io.aluop1_source       := ID.io.ex_aluop1_source
  EX.io.aluop2_source       := ID.io.ex_aluop2_source

  /* Memory */
  MEM.io.bundle.address := EX.io.mem_alu_result
  MEM.io.bundle.wEn     := ID.io.memory_write_enable
  MEM.io.bundle.wData   := REGS.io.read_data2
  MEM.io.bundle.rEn     := ID.io.memory_read_enable
  MEM.io.funct3         := IF.io.instruction(14, 12)

  /* Memory Debug */
  MEM.io.bundle.mem_debug_read_address := io.mem_debug_read_address
  io.mem_debug_read_data               := MEM.io.bundle.mem_debug_read_data

  /* Write Back */
  WB.io.instruction_address := IF.io.instruction_address
  WB.io.alu_result          := EX.io.mem_alu_result
  WB.io.memory_read_data    := MEM.io.bundle.rData
  WB.io.regs_write_source   := ID.io.wb_reg_write_source
}
