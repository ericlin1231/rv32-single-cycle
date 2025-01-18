package parameters.signals

import chisel3._

import parameters.System

object InstructionsRet {
  val mret = 0x30200073L.U(System.DataWidth)
  val ret  = 0x00008067L.U(System.DataWidth)
}

object InstructionsEnv {
  val ecall  = 0x00000073L.U(System.DataWidth)
  val ebreak = 0x00100073L.U(System.DataWidth)
}

