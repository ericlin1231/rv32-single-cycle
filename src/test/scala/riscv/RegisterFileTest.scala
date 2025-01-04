package riscv

import chisel3._
import chisel3.simulator.EphemeralSimulator._

import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.must.Matchers

import core.RegisterFile

class RegisterFileTest extends AnyFreeSpec with Matchers {
  "read the written content" in {
    simulate(new RegisterFile) { c =>
      c.io.write_enable.poke(true.B)
      c.io.write_address.poke(1.U)
      c.io.write_data.poke(0xdeadbeefL.U)
      c.clock.step()
      c.io.read_address1.poke(1.U)
      c.io.read_data1.expect(0xdeadbeefL.U)
    }
  }

  "x0 always be zero" in {
    simulate(new RegisterFile) { c =>
      c.io.write_enable.poke(true.B)
      c.io.write_address.poke(0.U)
      c.io.write_data.poke(0xdeadbeefL.U)
      c.clock.step()
      c.io.read_address1.poke(0.U)
      c.io.read_data1.expect(0.U)
    }
  }

  "read the writing content" in {
    simulate(new RegisterFile) { c =>
      c.io.read_address1.poke(2.U)
      c.io.read_data1.expect(0.U)
      c.io.write_enable.poke(true.B)
      c.io.write_address.poke(2.U)
      c.io.write_data.poke(0xdeadbeefL.U)
      c.clock.step()
      c.io.read_address1.poke(2.U)
      c.io.read_data1.expect(0xdeadbeefL.U)
      c.clock.step()
      c.io.read_address1.poke(2.U)
      c.io.read_data1.expect(0xdeadbeefL.U)
    }
  }
}
