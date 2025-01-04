package peripheral

import chisel3._
import chisel3.util._

import riscv.Parameters

class DRAM(capacity: Int) extends Module {
  val io = IO(new Bundle {
    val addr   = Input(UInt(Parameters.AddrWidth))
    val wEn    = Input(Bool())
    val wData  = Input(UInt(Parameters.DataWidth))
    val rData  = Output(UInt(Parameters.DataWidth))
  })

  // If the capacity is 1024, will create a 1024 KiB memory
  val mem = SyncReadMem(capacity << 10, UInt(Parameters.ByteWidth))

  val readData = Cat(mem.read((io.addr >> 2) + 3.U), 
                     mem.read((io.addr >> 2) + 2.U),
                     mem.read((io.addr >> 2) + 1.U), 
                     mem.read(io.addr >> 2))

  when(io.wEn) {
    mem.write(io.addr + 3.U, io.wData(31, 24))
    mem.write(io.addr + 2.U, io.wData(23, 16))
    mem.write(io.addr + 1.U, io.wData(15, 8))
    mem.write(io.addr, io.wData(7, 0))
  }

  io.rData := readData
}

