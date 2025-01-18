package core

import chisel3._
import chiseltest._
import chiseltest.simulator.WriteVcdAnnotation
import org.scalatest.flatspec.AnyFlatSpec

class FibonacciTest extends AnyFlatSpec with ChiselScalatestTester {
  "recursively calculate Fibonacci(10)" should "pass" in {
    test(new CPU("csrc/fibonacci.hex")).withAnnotations(Seq(WriteVcdAnnotation)) { c =>
      c.io.instruction_valid.poke(true.B)
      for (i <- 0 to 4000) {
        c.clock.step()
        c.io.MEMDebug.debug_read_address.poke(i.U)
      }
      
      c.io.MEMDebug.debug_read_address.poke(4.U)
      c.clock.step()
      c.io.MEMDebug.debug_read_data.expect(55.U)
    }
  }

  "non recursively Fibonacci(10) in assembly version" should "pass" in {
    test(new CPU("asm/fibonacci.hex")) { c =>
      c.io.instruction_valid.poke(true.B)
      c.clock.step(100)
      
      c.io.MEMDebug.debug_read_address.poke(4.U)
      c.clock.step()
      c.io.MEMDebug.debug_read_data.expect(55.U)
    }
  }
}

class CountTest extends AnyFlatSpec with ChiselScalatestTester {
  "count 0 to 10, store counter to address 4" should "pass" in {
    test(new CPU("asm/count.hex")) { c => 
      c.io.instruction_valid.poke(true.B)
      c.clock.step(100)
      
      c.io.MEMDebug.debug_read_address.poke(4.U)
      c.clock.step()
      c.io.MEMDebug.debug_read_data.expect(10.U)
    }
  }
}

class SumTest extends AnyFlatSpec with ChiselScalatestTester {
  "recursive sum 0 to 10, store result to address 4" should "pass" in {
    test(new CPU("csrc/recursive_sum.hex")) { c =>
      c.io.instruction_valid.poke(true.B)
      for(i <- 0 to 1000) {
        c.clock.step()
        c.io.MEMDebug.debug_read_address.poke(i.U) /* Avoid Timeout */
      }

      c.io.MEMDebug.debug_read_address.poke(4.U)
      c.clock.step()
      c.io.MEMDebug.debug_read_data.expect(55.U)
    }
  }

  "sum 0 to 10, store result to address 4" should "pass" in {
    test(new CPU("csrc/sum.hex")) { c =>
      c.io.instruction_valid.poke(true.B)
      c.clock.step(200)

      c.io.MEMDebug.debug_read_address.poke(4.U)
      c.clock.step()
      c.io.MEMDebug.debug_read_data.expect(55.U)
    }
  }
}

class FunctionCallTest extends AnyFlatSpec with ChiselScalatestTester {
  "call a function from main, store the return value in address 4 when at main" should "pass" in {
    test(new CPU("csrc/function.hex")) { c => 
      c.io.instruction_valid.poke(true.B)
      c.clock.step(200)

      c.io.MEMDebug.debug_read_address.poke(4.U)
      c.clock.step()
      c.io.MEMDebug.debug_read_data.expect(10.U) /* The simple function return 10 directly */
    }
  }
}

class CheckSP extends AnyFlatSpec with ChiselScalatestTester {
  "check can use 12(sp) to load value" should "pass" in {
    test(new CPU("asm/sp.hex")) { c =>
      c.io.instruction_valid.poke(true.B)
      c.clock.step(10)

      c.io.REGSDebug.debug_read_address.poke(6.U) /* Load Data to t1 Register */
      c.io.REGSDebug.debug_read_data.expect(10.U)
    }
  }
}

class StoreLoadTest extends AnyFlatSpec with ChiselScalatestTester {
  "store and load a word with value 10 at address 4" should "pass" in {
    test(new CPU("asm/store_load.hex")).withAnnotations(Seq(WriteVcdAnnotation)) { c => 
      c.io.instruction_valid.poke(true.B)
      c.clock.step(10)
      
      /* Check Data Can Store in `offset(address register)` Pattern */
      c.io.MEMDebug.debug_read_address.poke(4.U)
      c.clock.step()
      c.io.MEMDebug.debug_read_data.expect(10.U)

      /* Check Data Can Load in `offset(address register)` Pattern */
      c.io.REGSDebug.debug_read_address.poke(6.U) /* Load Data to t0 register */
      c.io.REGSDebug.debug_read_data.expect(10.U)
    }
  }
}
