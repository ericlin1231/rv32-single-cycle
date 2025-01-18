package bundle

import chisel3._

import parameters.System

class MemoryDebugBundle extends Bundle {
  val debug_read_address = Input(UInt(System.AddrWidth))
  val debug_read_data    = Output(UInt(System.DataWidth))
}
