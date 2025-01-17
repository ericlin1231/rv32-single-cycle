package peripheral

import chisel3._
import chiseltest._
import org.scalatest._
import org.scalatest.flatspec.AnyFlatSpec

import scala.util.Random

import parameters._

class DataRAMTest extends AnyFlatSpec with ChiselScalatestTester {
  "DataRAM Test store and read word with random address" should "pass" in {
    test(new DataRAM) { c =>
      for (_ <- 0 until 100) {
        val wordIndex = Random.nextInt(System.DataMemorySizeInWords - 4)
        val address   = wordIndex * System.WordSize
        val wData     = Random.nextInt(1 << 32)

        Random.nextInt(2) match {
          case 0 =>  /* store then read */
            c.io.bundle.address.poke(address)
            c.io.bundle.wEn.poke(true.B)
            c.io.bundle.wData.poke(wData)
            c.io.funct3.poke(InstructionsTypeS.sw)
            c.clock.step()

            c.io.bundle.address.poke(address)
            c.io.bundle.wEn.poke(false.B)
            c.io.bundle.rEn.poke(true.B)
            c.io.funct3.poke(InstructionsTypeL.lw)
            c.clock.step()

            c.io.bundle.rData.expect(wData)

          case 1 => /* disable store then read */
            c.io.bundle.address.poke(address)
            c.io.bundle.wEn.poke(false.B)
            c.io.bundle.rEn.poke(true.B)
            c.io.funct3.poke(InstructionsTypeL.lw)
            c.clock.step()

            val expect = c.io.bundle.rData.peek().litValue

            c.io.bundle.address.poke(address)
            c.io.bundle.wEn.poke(false.B)
            c.io.bundle.wData.poke(wData)
            c.io.funct3.poke(InstructionsTypeS.sw)
            c.clock.step()

            c.io.bundle.address.poke(address)
            c.io.bundle.wEn.poke(false.B)
            c.io.bundle.rEn.poke(true.B)
            c.io.funct3.poke(InstructionsTypeL.lw)
            c.clock.step()

            c.io.bundle.rData.expect(expect.U)
        }
      }
    }
  }
}

