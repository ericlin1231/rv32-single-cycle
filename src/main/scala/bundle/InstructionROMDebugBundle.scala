package bundle

import chisel3._

import parameters.System

class InstructionROMDebugBundle extends Bundle {
  val debug_read_address     = Input(UInt(System.AddrWidth))
  val debug_read_instruction = Output(UInt(System.InstructionWidth))
}
