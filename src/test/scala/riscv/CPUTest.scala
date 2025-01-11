package riscv

import java.nio.ByteBuffer
import java.nio.ByteOrder

import chisel3._
import chiseltest._
import org.scalatest._
import org.scalatest.flatspec.AnyFlatSpec

import peripheral.InstructionROM
import peripheral.Memory
import peripheral.ROMLoader
import riscv.core.CPU
import riscv.core.ProgramCounter
import riscv.Parameters

// class FibonacciTest extends AnyFlatSpec with ChiselScalatestTester {
//   "recursively calculate Fibonacci(10)" should "pass" in {
//     test(new TestTopModule("fibonacci.hex")) { c =>
//       for (i <- 1 to 50) {
//         c.clock.step(1000)
//         c.io.mem_debug_read_address.poke((i * 4).U)
//       }

//       c.io.mem_debug_read_address.poke(4.U)
//       c.clock.step()
//       c.io.mem_debug_read_data.expect(55.U)
//     }
//   }
// }

class CountTest extends AnyFlatSpec with ChiselScalatestTester {
  "count 0 to 10, store counter to address 0x1000" should "pass" in {
    test(new CPU("count.hex")) { c => 
      for(_ <- 1 to 50) {
        c.clock.step()
      }

      c.io.mem_debug_read_address.poke(1000.U)
      c.clock.step()
      c.io.mem_debug_read_data.expect(10.U)
    }
  }
}
