package parameters.signals

import chisel3._

object ALUOp1Source {
  val Register           = 0.U(1.W)
  val InstructionAddress = 1.U(1.W)
}

object ALUOp2Source {
  val Register  = 0.U(1.W)
  val Immediate = 1.U(1.W)
}

object RegWriteSource {
  val ALUResult              = 0.U(2.W)
  val Memory                 = 1.U(2.W)
  val CSR                    = 2.U(2.W)
  val NextInstructionAddress = 3.U(2.W)
}

