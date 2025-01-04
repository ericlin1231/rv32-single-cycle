package riscv

import chisel3._
import chisel3.simulator.EphemeralSimulator._
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.must.Matchers

import core.Decode
import core.ALUOp1Source
import core.ALUOp2Source

class DecodeTest extends AnyFreeSpec with Matchers {
  "produce correct control signal" in {
    simulate(new Decode) { c =>
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

