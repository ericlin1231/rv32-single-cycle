package peripheral

import chisel3._
import chiseltest._
import org.scalatest._
import org.scalatest.flatspec.AnyFlatSpec

import scala.util.Random

import parameters._

class MemoryTest extends AnyFlatSpec with ChiselScalatestTester {
  "DataRAM Test store and read word with random address" should "pass" in {
    test(new Memory) { c =>
      for (_ <- 0 until 100) {
        val wordIndex = Random.nextInt(System.DataMemorySizeInWords - 4)
        val address   = wordIndex * System.WordSize
        val wData     = Random.nextInt(1 << 32)

        Random.nextInt(2) match {
          case 0 =>  /* store then read */
            c.io.MEMPort.address.poke(address)
            c.io.MEMPort.wEn.poke(true.B)
            c.io.MEMPort.wData.poke(wData)
            c.io.funct3.poke(InstructionsTypeS.sw)
            c.clock.step()

            c.io.MEMPort.address.poke(address)
            c.io.MEMPort.wEn.poke(false.B)
            c.io.MEMPort.rEn.poke(true.B)
            c.io.funct3.poke(InstructionsTypeL.lw)
            c.clock.step()

            c.io.MEMPort.rData.expect(wData)

          case 1 => /* disable store then read */
            c.io.MEMPort.address.poke(address)
            c.io.MEMPort.wEn.poke(false.B)
            c.io.MEMPort.rEn.poke(true.B)
            c.io.funct3.poke(InstructionsTypeL.lw)
            c.clock.step()

            val expect = c.io.MEMPort.rData.peek().litValue

            c.io.MEMPort.address.poke(address)
            c.io.MEMPort.wEn.poke(false.B)
            c.io.MEMPort.wData.poke(wData)
            c.io.funct3.poke(InstructionsTypeS.sw)
            c.clock.step()

            c.io.MEMPort.address.poke(address)
            c.io.MEMPort.wEn.poke(false.B)
            c.io.MEMPort.rEn.poke(true.B)
            c.io.funct3.poke(InstructionsTypeL.lw)
            c.clock.step()

            c.io.MEMPort.rData.expect(expect.U)
        }
      }
    }
  }
}

