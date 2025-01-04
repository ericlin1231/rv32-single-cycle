package peripheral

import chisel3._
import chisel3.util._
import chisel3.util.experimental.loadMemoryFromFile

import riscv.Parameters

class ROM(capacity: Int, filename: String) extends Module {
  val io = IO(new Bundle {
    val addr = Input(UInt(Parameters.AddrWidth))
    val inst = Output(UInt(Parameters.InstructionWidth))
  })

  // If the capacity is 1024, will create a 1024 KiB memory
  val rom = SyncReadMem(capacity << 10, UInt(Parameters.ByteWidth))

  loadMemoryFromFile(rom, filename)

  io.inst := rom.read(io.addr)
}
