package riscv

import chisel3._

import peripheral.RAMBundle

class CPUBundle extends Bundle {
  val instruction_valid      = Input(Bool())
  val mem_debug_read_address = Input(UInt(Parameters.PhysicalRegisterAddrWidth))
  val mem_debug_read_data    = Output(UInt(Parameters.DataWidth))
}
