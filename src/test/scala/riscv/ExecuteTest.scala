package riscv

import chisel3._
import chisel3.simulator.EphemeralSimulator._
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.must.Matchers

import riscv.core.Execute

class ExecuteTest extends AnyFreeSpec with Matchers {
  "execute correctly" in {
    simulate(new Execute) { c =>
      c.io.instruction.poke(0x001101b3L.U)

      var x = 0
      for (x <- 0 to 100) {
        val op1    = scala.util.Random.nextInt(429496729)
        val op2    = scala.util.Random.nextInt(429496729)
        val result = op1 + op2
        val addr   = scala.util.Random.nextInt(32)

        c.io.reg1_data.poke(op1.U)
        c.io.reg2_data.poke(op2.U)

        c.clock.step()
        c.io.mem_alu_result.expect(result.U)
        c.io.if_jump_flag.expect(false.B)
      }

      // beq test
      c.io.instruction.poke(0x00208163L.U)
      c.io.instruction_address.poke(2.U)
      c.io.immediate.poke(2.U)
      c.io.aluop1_source.poke(1.U)
      c.io.aluop2_source.poke(1.U)
      c.clock.step()

      // equ
      c.io.reg1_data.poke(9.U)
      c.io.reg2_data.poke(9.U)
      c.clock.step()
      c.io.if_jump_flag.expect(true.B)
      c.io.if_jump_address.expect(4.U)

      // not equ
      c.io.reg1_data.poke(9.U)
      c.io.reg2_data.poke(19.U)
      c.clock.step()
      c.io.if_jump_flag.expect(false.B)
      c.io.if_jump_address.expect(4.U)
    }
  }
}

