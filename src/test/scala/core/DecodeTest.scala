package core

import chisel3._
import chiseltest._
import org.scalatest._
import org.scalatest.flatspec.AnyFlatSpec

import parameters.ALUOp1Source
import parameters.ALUOp2Source

class DecodeTest extends AnyFlatSpec with ChiselScalatestTester {
  "produce correct control signal" should "pass" in {
    test(new Decode) { c =>
      c.io.instruction.poke(0x00a02223L.U) // S-type
      c.io.ex_aluop1_source.expect(ALUOp1Source.Register)
      c.io.ex_aluop2_source.expect(ALUOp2Source.Immediate)
      c.io.regs_reg1_read_address.expect(0.U)
      c.io.regs_reg2_read_address.expect(10.U)
      c.clock.step()

      c.io.instruction.poke(0x000022b7L.U) // lui
      c.io.regs_reg1_read_address.expect(0.U)
      c.io.ex_aluop1_source.expect(ALUOp1Source.Register)
      c.io.ex_aluop2_source.expect(ALUOp2Source.Immediate)
      c.clock.step()

      c.io.instruction.poke(0x002081b3L.U) // add
      c.io.ex_aluop1_source.expect(ALUOp1Source.Register)
      c.io.ex_aluop2_source.expect(ALUOp2Source.Register)
      c.clock.step()
    }
  }
}

