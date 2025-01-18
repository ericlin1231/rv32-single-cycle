package bundle

import chisel3._

import parameters.System

class MemoryBundle extends Bundle {
  val address = Input(UInt(System.AddrWidth))
  val wEn     = Input(Bool())
  val wData   = Input(UInt(System.DataWidth))
  val rEn     = Input(Bool())
  val rData   = Output(UInt(System.DataWidth))
}

