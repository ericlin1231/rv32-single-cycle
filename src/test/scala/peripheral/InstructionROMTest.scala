package peripheral

import chisel3._
import chiseltest._
import org.scalatest._
import org.scalatest.flatspec.AnyFlatSpec

class InstructionROMTest extends AnyFlatSpec with ChiselScalatestTester {
  "show instruction ROM content" should "pass" in {
    test(new InstructionROM("asm/count.hex")) { c =>
      for(i <- 0 to 50) {
        c.io.IROMPort.address.poke(i.U)
        c.clock.step()
        val instruction = c.io.IROMPort.instruction.peek().litValue
      }
    }
  }
}

