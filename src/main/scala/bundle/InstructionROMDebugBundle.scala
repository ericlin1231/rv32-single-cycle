package bundle

import chisel3._

import parameters.System

class InstructionROMDebugBundle extends Bundle {
  val instructionROM_debug_read_address     = Input(UInt(System.AddrWidth))
  val instructionROM_debug_read_instruction = Output(UInt(System.InstructionWidth))
}
