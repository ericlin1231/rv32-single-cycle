package riscv.core

import chisel3._
import chisel3.util.Cat
import _root_.circt.stage.ChiselStage

import riscv.CPUBundle
import riscv.Parameters

class CPU extends Module {
  val io   = IO(new CPUBundle)
  val REGS = Module(new RegisterFile)
  val IF   = Module(new Fetch)
  val ID   = Module(new Decode)
  val EX   = Module(new Execute)
  val MEM  = Module(new DataRAM(4096))
  val WB   = Module(new WriteBack)

  /* Instruction Fetch */
  IF.io.jump_address_id   := EX.io.if_jump_address
  IF.io.jump_flag_id      := EX.io.if_jump_flag
  IF.io.instruction_valid := io.instruction_valid

  /* Register File */
  REGS.io.write_enable  := ID.io.reg_write_enable
  REGS.io.write_address := ID.io.reg_write_address
  REGS.io.write_data    := WB.io.regs_write_data
  REGS.io.read_address1 := ID.io.regs_reg1_read_address
  REGS.io.read_address2 := ID.io.regs_reg2_read_address

  /* Instruction Decoder */
  ID.io.instruction := IF.io.instruction

  /* Execution */
  EX.io.instruction := IF.io.instruction
  EX.io.instruction_address := IF.io.instruction_address
  EX.io.reg1_data := REGS.io.read_data1
  EX.io.reg2_data := REGS.io.read_data2
  EX.io.immediate := ID.io.ex_immediate
  EX.io.aluop1_source := ID.io.ex_aluop1_source
  EX.io.aluop2_source := ID.io.ex_aluop2_source

  /* Memory */
  MEM.io.bundle.wData := REGS.io.read_data2
  MEM.io.bundle.rEn   := ID.io.memory_read_enable
  MEM.io.bundle.wEn   := ID.io.memory_write_enable
  MEM.io.alu_result   := EX.io.mem_alu_result
  MEM.io.funct3       := IF.io.instruction(14, 12)

  /* Write Back */
  WB.io.instruction_address := IF.io.instruction_address
  WB.io.alu_result          := EX.io.mem_alu_result
  WB.io.memory_read_data    := MEM.io.wb_memory_read_data
  WB.io.regs_write_source   := ID.io.wb_reg_write_source
}
