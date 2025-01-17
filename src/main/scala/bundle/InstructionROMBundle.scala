package bundle

import chisel3._

import parameters.System

class InstructionROMBundle extends Bundle {
  val address     = Input(UInt(System.AddrWidth))
  val instruction = Output(UInt(System.InstructionWidth))
}
