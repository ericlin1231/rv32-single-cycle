package peripheral

import chisel3._
import chisel3.experimental.annotate
import chisel3.experimental.ChiselAnnotation
import chisel3.util.experimental.loadMemoryFromFileInline
import firrtl.transforms.DontTouchAnnotation

import bundle.InstructionROMBundle
import bundle.InstructionROMDebugBundle
import parameters.System

class InstructionROM(filename: String) extends Module {
  val io = IO(new Bundle {
    val IROMPort  = new InstructionROMBundle
    val DebugPort = new InstructionROMDebugBundle
  })

  val mem = Mem(System.InstructionMemorySizeInBytes, UInt(System.InstructionWidth)).suggestName("mem")
  annotate(new ChiselAnnotation {
    def toFirrtl = DontTouchAnnotation(mem.toTarget)
  })
  loadMemoryFromFileInline(
    mem,
    f"./src/main/resources/${filename}",
    firrtl.annotations.MemoryLoadFileType.Hex
  )

  io.IROMPort.instruction := mem.read(io.IROMPort.address)

  /* InstructionROM Debug */
  io.DebugPort.debug_read_instruction := mem.read(io.DebugPort.debug_read_address)
}
