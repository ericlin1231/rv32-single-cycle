package peripheral

import chisel3._
import chisel3.util._

import riscv.Parameters

class RAMBundle extends Bundle {
  val address = Input(UInt(Parameters.AddrWidth))
  val wEn     = Input(Bool())
  val rEn     = Input(Bool())
  val wData   = Input(UInt(Parameters.DataWidth))
  val rData   = Output(UInt(Parameters.DataWidth))
}

class DataRAM(capacity: Int) extends Module {
  val io = IO(new Bundle {
    val bundle = new RAMBundle
  })

  val mem = SyncReadMem(capacity, UInt(Parameters.ByteWidth))

  val rData = Cat(mem.read((io.bundle.address >> 2) + 3.U),
                  mem.read((io.bundle.address >> 2) + 2.U),
                  mem.read((io.bundle.address >> 2) + 1.U),
                  mem.read(io.bundle.address >> 2))

  when(io.bundle.wEn) {
    mem.write(io.bundle.address + 3.U, io.bundle.wData(31, 24))
    mem.write(io.bundle.address + 2.U, io.bundle.wData(23, 16))
    mem.write(io.bundle.address + 1.U, io.bundle.wData(15, 8))
    mem.write(io.bundle.address      , io.bundle.wData(7, 0))
  }

  io.bundle.rData := rData
}
