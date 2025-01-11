package peripheral

import chisel3._
import chiseltest._
import org.scalatest._
import org.scalatest.flatspec.AnyFlatSpec

class InstructionROMTest extends AnyFlatSpec with ChiselScalatestTester {
  "show instruction ROM content" should "pass" in {
    test(new InstructionROM("count.hex")) { c =>
      for(i <- 0 to 50) {
        c.io.address.poke(i.U)
        c.clock.step()
        val instruction = c.io.instruction.peek().litValue
        println(f"Address ${i << 2}: ${instruction}%08x")
      }
    }
  }
}

