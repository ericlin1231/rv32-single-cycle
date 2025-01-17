package bundle

import chisel3._

import parameters.System

class CPUBundle extends Bundle {
  val instruction_valid      = Input(Bool())

  val mem_debug_read_address = Input(UInt(System.AddrWidth))
  val mem_debug_read_data    = Output(UInt(System.DataWidth))

  val instructionROM_debug_read_address = Input(UInt(System.AddrWidth))
  val instructionROM_debug_read_instruction = Output(UInt(System.InstructionWidth))

  val register_debug_read_address = Input(UInt(System.PhysicalRegisterAddrWidth))
  val register_debug_read_data    = Output(UInt(System.DataWidth))

  val fetch_debug_read_pc          = Output(UInt(System.AddrWidth))
  val fetch_debug_read_instruction = Output(UInt(System.InstructionWidth))     
}
