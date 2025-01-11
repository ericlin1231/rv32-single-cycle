package peripheral

import chisel3._
import chisel3.util.experimental.loadMemoryFromFileInline

import _root_.circt.stage.ChiselStage

import riscv.Parameters

class InstructionROM(filename: String) extends Module {
  val io = IO(new Bundle {
    val address     = Input(UInt(Parameters.AddrWidth))
    val instruction = Output(UInt(Parameters.InstructionWidth))
  })

  val mem = SyncReadMem(1024, UInt(Parameters.InstructionWidth))
  loadMemoryFromFileInline(
    mem,
    f"./src/main/resources/${filename}",
    firrtl.annotations.MemoryLoadFileType.Hex
  )

  io.instruction := mem.read(io.address)
}


object InstructionROMsv extends App {
  ChiselStage.emitSystemVerilogFile(
    new InstructionROM("count.hex"),
    args = Array("--target-dir", "build/peripheral"),
    firtoolOpts = Array(
      "--disable-all-randomization",
      "--lowering-options=disallowLocalVariables",
      "--strip-debug-info"
    )
  )
}
