package parameters

import chisel3._
import chisel3.ChiselEnum

/*
 * RISC-V 32 Architecture Registers Calling Convention
 * x0: zero          Always ZERO
 * x1: ra            Return Address
 * x2: sp            Stack Pointer
 * x3: gp            Global Pointer
 * x4: tp            Thread Pointer
 * x5-7: t0-2        Temporaries
 * x8: s0/fp         Saved Register/Frame Pointer
 * x9: s1            Saved Registers
 * x10-11: a0-1      Function Arguments/Return Values
 * x12-17: a2-11     Function Arguments
 * x18-27: s2-11     Saved Registers
 * x28-32: t3-6      Temporaries
 */

object Registers extends ChiselEnum {
  val zero, ra, sp, gp, tp, fp,
      t0, t1, t2, t3, t4, t5, t6,
      a0, a1, a2, a3, a4, a5, a6, a7,
      s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11 = Value
}

object ALUFunctions extends ChiselEnum {
  val zero, add, sub, sll, slt, xor, or, and, srl, sra, sltu = Value
}
