package bundle

import chisel3._

import parameters.System
import bundle.InstructionROMDebugBundle

class CPUBundle extends Bundle {
  val instruction_valid      = Input(Bool())

  val IROMDebug = new InstructionROMDebugBundle
  val MEMDebug  = new MemoryDebugBundle
  val REGSDebug = new RegisterDebugBundle
  val IFDebug   = new FetchDebugBundle
}
