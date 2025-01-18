package parameters.signals

import chisel3._

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
 * x12-17: a2-7      Function Arguments
 * x18-27: s2-11     Saved Registers
 * x28-31: t3-6      Temporaries
 */

object Registers {
  val zero = 0.U    /* x0: zero, always zero */
  val ra   = 1.U    /* x1: return address */
  val sp   = 2.U    /* x2: stack pointer */
  val gp   = 3.U    /* x3: global pointer */
  val tp   = 4.U    /* x4: thread pointer */

  /* Temporaries */
  val t0   = 5.U    /* x5: temporary register 0 */
  val t1   = 6.U    /* x6: temporary register 1 */
  val t2   = 7.U    /* x7: temporary register 2 */

  /* Saved Registers / Frame Pointer */
  val fp   = 8.U    /* x8: saved register/frame pointer */
  val s1   = 9.U    /* x9: saved register 1 */

  /* Function Arguments / Return Values */
  val a0   = 10.U   /* x10: function argument 0 / return value 0 */
  val a1   = 11.U   /* x11: function argument 1 / return value 1 */
  val a2   = 12.U   /* x12: function argument 2 */
  val a3   = 13.U   /* x13: function argument 3 */
  val a4   = 14.U   /* x14: function argument 4 */
  val a5   = 15.U   /* x15: function argument 5 */
  val a6   = 16.U   /* x16: function argument 6 */
  val a7   = 17.U   /* x17: function argument 7 */

  /* Saved Registers */
  val s2   = 18.U   /* x18: saved register 2 */
  val s3   = 19.U   /* x19: saved register 3 */
  val s4   = 20.U   /* x20: saved register 4 */
  val s5   = 21.U   /* x21: saved register 5 */
  val s6   = 22.U   /* x22: saved register 6 */
  val s7   = 23.U   /* x23: saved register 7 */
  val s8   = 24.U   /* x24: saved register 8 */
  val s9   = 25.U   /* x25: saved register 9 */
  val s10  = 26.U   /* x26: saved register 10 */
  val s11  = 27.U   /* x27: saved register 11 */

  /* Temporaries */
  val t3   = 28.U   /* x28: temporary register 3 */
  val t4   = 29.U   /* x29: temporary register 4 */
  val t5   = 30.U   /* x30: temporary register 5 */
  val t6   = 31.U   /* x31: temporary register 6 */
}
