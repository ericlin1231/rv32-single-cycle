package parameters.signals

import chisel3._

object Funct3TypeL {
  val lb  = "b000".U
  val lh  = "b001".U
  val lw  = "b010".U
  val lbu = "b100".U
  val lhu = "b101".U
}

object Funct3TypeI {
  val addi  = 0.U
  val slli  = 1.U
  val slti  = 2.U
  val sltiu = 3.U
  val xori  = 4.U
  val sri   = 5.U
  val ori   = 6.U
  val andi  = 7.U
}

object Funct3TypeS {
  val sb = 0.U
  val sh = 1.U
  val sw = 2.U
}

object Funct3TypeR {
  val add_sub = 0.U
  val sll     = 1.U
  val slt     = 2.U
  val sltu    = 3.U
  val xor     = 4.U
  val sr      = 5.U
  val or      = 6.U
  val and     = 7.U
}

object Funct3TypeM {
  val mul    = 0.U
  val mulh   = 1.U
  val mulhsu = 2.U
  val mulhum = 3.U
  val div    = 4.U
  val divu   = 5.U
  val rem    = 6.U
  val remu   = 7.U
}

object Funct3TypeB {
  val beq  = "b000".U
  val bne  = "b001".U
  val blt  = "b100".U
  val bge  = "b101".U
  val bltu = "b110".U
  val bgeu = "b111".U
}

object Funct3TypeCSR {
  val csrrw  = "b001".U
  val csrrs  = "b010".U
  val csrrc  = "b011".U
  val csrrwi = "b101".U
  val csrrsi = "b110".U
  val csrrci = "b111".U
}

