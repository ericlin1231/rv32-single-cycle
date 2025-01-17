package bundle

import chisel3._

import parameters.System

class RAMBundle extends Bundle {
  val address = Input(UInt(System.AddrWidth))
  val wEn     = Input(Bool())
  val wData   = Input(UInt(System.DataWidth))
  val rEn     = Input(Bool())
  val rData   = Output(UInt(System.DataWidth))

  val mem_debug_read_address = Input(UInt(System.AddrWidth))
  val mem_debug_read_data    = Output(UInt(System.DataWidth))
}

