package bundle

import chisel3._

import parameters.System

class FetchDebugBundle extends Bundle {
  val debug_read_pc          = Output(UInt(System.AddrWidth))
  val debug_read_instruction = Output(UInt(System.InstructionWidth))
}
