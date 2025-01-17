package core

import chisel3._
import chiseltest._
import chiseltest.simulator.WriteVcdAnnotation
import org.scalatest.flatspec.AnyFlatSpec

class FibonacciTest extends AnyFlatSpec with ChiselScalatestTester {
  "recursively calculate Fibonacci(10)" should "pass" in {
    test(new CPU("csrc/fibonacci.hex")) { c =>
      c.io.instruction_valid.poke(true.B)
      for (_ <- 0 to 600) {
        c.clock.step()
      }
      
      c.io.mem_debug_read_address.poke(4.U)
      c.clock.step()
      c.io.mem_debug_read_data.expect(55.U)
    }
  }

  "Non recursively Fibonacci(10) in assembly version" should "pass" in {
    test(new CPU("asm/fibonacci.hex")) { c =>
      c.io.instruction_valid.poke(true.B)
      for(_ <- 0 to 100) {
        c.clock.step()
      }
      
      c.io.mem_debug_read_address.poke(4.U)
      c.clock.step()
      c.io.mem_debug_read_data.expect(55.U)
    }
  }
}

class CountTest extends AnyFlatSpec with ChiselScalatestTester {
  "count 0 to 10, store counter to address 4" should "pass" in {
    test(new CPU("asm/count.hex")) { c => 
      c.io.instruction_valid.poke(true.B)
      for(_ <- 0 to 100) {
        c.clock.step()
      }
      
      c.io.mem_debug_read_address.poke(4.U)
      c.clock.step()
      c.io.mem_debug_read_data.expect(10.U)
    }
  }
}

class SumTest extends AnyFlatSpec with ChiselScalatestTester {
  "sum 0 to 10, store result to address 4" should "pass" in {
    test(new CPU("csrc/sum.hex")) { c =>
      c.io.instruction_valid.poke(true.B)
      for(_ <- 0 to 200) {
        c.clock.step()
      }

      c.io.mem_debug_read_address.poke(4.U)
      c.clock.step()
      c.io.mem_debug_read_data.expect(55.U)
    }
  }
}

class FunctionCallTest extends AnyFlatSpec with ChiselScalatestTester {
  "call a function from main, store the return value in address 4 when at main" should "pass" in {
    test(new CPU("csrc/function.hex")) { c => 
      c.io.instruction_valid.poke(true.B)
      for(_ <- 0 to 200) {
        c.clock.step()
      }

      c.io.mem_debug_read_address.poke(4.U)
      c.clock.step()
      c.io.mem_debug_read_data.expect(10.U) /* The simple function return 10 directly */
    }
  }
}

class CheckSP extends AnyFlatSpec with ChiselScalatestTester {
  "check can use 12(sp) to load value" should "pass" in {
    test(new CPU("asm/sp.hex")) { c =>
      c.io.instruction_valid.poke(true.B)
      for(_ <- 0 to 10) {
        c.clock.step()
      }
      c.io.register_debug_read_address.poke(6.U) /* t1 */
      c.io.register_debug_read_data.expect(10.U)
    }
  }
}

class StoreLoadTest extends AnyFlatSpec with ChiselScalatestTester {
  "store and load a word with value 10 at address 4" should "pass" in {
    test(new CPU("asm/store_load.hex")).withAnnotations(Seq(WriteVcdAnnotation)) { c => 
      c.io.instruction_valid.poke(true.B)
      for(_ <- 0 to 10) {
        c.clock.step()
      }
      
      /* Check Data Can Store in `offset(address register)` Pattern */
      c.io.mem_debug_read_address.poke(4.U)
      c.clock.step()
      c.io.mem_debug_read_data.expect(10.U)

      /* Check Data Can Load in `offset(address register)` Pattern */
      c.io.register_debug_read_address.poke(6.U) /* Load Data to t0 register */
      c.io.register_debug_read_data.expect(10.U)
    }
  }
}
