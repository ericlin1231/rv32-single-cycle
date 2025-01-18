package peripheral

import chisel3._
import chisel3.util._

import bundle.MemoryBundle
import bundle.MemoryDebugBundle
import parameters.System
import parameters.signals.Funct3TypeL
import parameters.signals.Funct3TypeS

class Memory extends Module {
  val io = IO(new Bundle {
    val MEMPort = new MemoryBundle
    val funct3      = Input(UInt(3.W))

    val DebugPort = new MemoryDebugBundle
  })
  
  /* MEM Initialization */
  io.MEMPort.rData := DontCare
  val mem = Mem(System.DataMemorySizeInBytes, UInt(System.ByteWidth))

  val debug_data = Cat(
    mem.read(io.DebugPort.debug_read_address + 3.U),
    mem.read(io.DebugPort.debug_read_address + 2.U),
    mem.read(io.DebugPort.debug_read_address + 1.U),
    mem.read(io.DebugPort.debug_read_address)
  )

  io.DebugPort.debug_read_data := debug_data

  when(io.MEMPort.rEn) {
    val data = Cat(
      mem.read(io.MEMPort.address + 3.U),
      mem.read(io.MEMPort.address + 2.U),
      mem.read(io.MEMPort.address + 1.U),
      mem.read(io.MEMPort.address)
    )

    io.MEMPort.rData := MuxLookup(io.funct3, 0.U)(
      IndexedSeq(
        Funct3TypeL.lb -> MuxLookup(
          io.MEMPort.address,
          Cat(Fill(24, data(31)), data(31, 24)))(
          IndexedSeq(
            0.U -> Cat(Fill(24, data(7)), data(7, 0)),
            1.U -> Cat(Fill(24, data(15)), data(15, 8)),
            2.U -> Cat(Fill(24, data(23)), data(23, 16))
          )
        ),
        Funct3TypeL.lbu -> MuxLookup(
          io.MEMPort.address,
          Cat(Fill(24, 0.U), data(31, 24)))(
          IndexedSeq(
            0.U -> Cat(Fill(24, 0.U), data(7, 0)),
            1.U -> Cat(Fill(24, 0.U), data(15, 8)),
            2.U -> Cat(Fill(24, 0.U), data(23, 16))
          )
        ),
        Funct3TypeL.lh -> Mux(
          io.MEMPort.address === 0.U,
          Cat(Fill(16, data(15)), data(15, 0)),
          Cat(Fill(16, data(31)), data(31, 16))
        ),
        Funct3TypeL.lhu -> Mux(
          io.MEMPort.address === 0.U,
          Cat(Fill(16, 0.U), data(15, 0)),
          Cat(Fill(16, 0.U), data(31, 16))
        ),
        Funct3TypeL.lw -> data
      )
    )
  }.elsewhen(io.MEMPort.wEn) {
    when(io.funct3 === Funct3TypeS.sb) {
      mem.write(io.MEMPort.address, io.MEMPort.wData(7, 0))
    }.elsewhen(io.funct3 === Funct3TypeS.sh) {
      mem.write(io.MEMPort.address + 1.U, io.MEMPort.wData(15, 8))
      mem.write(io.MEMPort.address      , io.MEMPort.wData(7, 0))
    }.elsewhen(io.funct3 === Funct3TypeS.sw) {
      mem.write(io.MEMPort.address + 3.U, io.MEMPort.wData(31, 24))
      mem.write(io.MEMPort.address + 2.U, io.MEMPort.wData(23, 16))
      mem.write(io.MEMPort.address + 1.U, io.MEMPort.wData(15, 8))
      mem.write(io.MEMPort.address      , io.MEMPort.wData(7, 0))
    }
  }
}
