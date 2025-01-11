package peripheral

import chisel3._
import chiseltest._
import org.scalatest._
import org.scalatest.flatspec.AnyFlatSpec

import scala.util.Random

import riscv.Parameters

class DRAMTest extends AnyFlatSpec with ChiselScalatestTester {
  "DRAM write and read word with random addresses" should "pass" in {
    test(new DRAM(Parameters.MemorySizeInBytes)) { c =>
      for (_ <- 0 until 100) {
        val wordIndex = Random.nextInt(Parameters.MemorySizeInWords)
        val addr = wordIndex * Parameters.WordSize
        val wData = Random.nextInt(1 << 32)

        c.io.addr.poke(addr.U)
        c.io.wData.poke(wData.U)
        c.io.wEn.poke(true.B)
        c.clock.step()

        c.io.wEn.poke(false.B)
        c.io.addr.poke(addr.U)
        c.clock.step()

        c.io.rData.expect(wData.U)
      }
    }
  }
}

