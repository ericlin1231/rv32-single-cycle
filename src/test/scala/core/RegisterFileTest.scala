package core

import chisel3._
import chiseltest._
import org.scalatest._
import org.scalatest.flatspec.AnyFlatSpec

class RegisterFileTest extends AnyFlatSpec with ChiselScalatestTester {
  "read the written content" should "pass" in {
    test(new RegisterFile) { c =>
      c.io.write_enable.poke(true.B)
      c.io.write_address.poke(1.U)
      c.io.write_data.poke(0xdeadbeefL.U)
      c.clock.step()
      c.io.read_address1.poke(1.U)
      c.io.read_data1.expect(0xdeadbeefL.U)
    }
  }

  "x0 always be zero" should "pass" in {
    test(new RegisterFile) { c =>
      c.io.write_enable.poke(true.B)
      c.io.write_address.poke(0.U)
      c.io.write_data.poke(0xdeadbeefL.U)
      c.clock.step()
      c.io.read_address1.poke(0.U)
      c.io.read_data1.expect(0.U)
    }
  }

  "read the writing content" should "pass" in {
    test(new RegisterFile) { c =>
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
