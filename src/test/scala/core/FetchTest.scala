package core

import chisel3._
import chiseltest._
import org.scalatest._
import org.scalatest.flatspec.AnyFlatSpec

import scala.util.Random

class FetchTest extends AnyFlatSpec with ChiselScalatestTester {
  "fetch instruction" should "pass" in {
    test(new Fetch) { c =>
      val entry = 0x0
      var pre   = 0x0
      var cur   = 0x0
      c.io.instruction_valid.poke(true.B)
      // Init PC to entry point
      c.io.jump_flag_id.poke(true.B)
      c.io.jump_address_id.poke(entry)
      c.clock.step()
      c.io.jump_flag_id.poke(false.B)
      var x = 0
      for (x <- 0 to 100) {
        Random.nextInt(2) match {
          case 0 => // no jump
            cur = pre + 4
            c.io.jump_flag_id.poke(false.B)
            c.clock.step()
            c.io.instruction_address.expect(cur)
            pre = cur
          case 1 => // jump
            c.io.jump_flag_id.poke(true.B)
            c.io.jump_address_id.poke(entry)
            c.clock.step()
            c.io.instruction_address.expect(entry)
            pre = entry
        }
      }
    }
  }
}

