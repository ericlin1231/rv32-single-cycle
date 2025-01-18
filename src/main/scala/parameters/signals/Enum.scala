package parameters.signals

import chisel3._
import chisel3.ChiselEnum

object ALUFunctions extends ChiselEnum {
  val zero, add, sub, sll, slt, xor, or, and, srl, sra, sltu = Value
}

