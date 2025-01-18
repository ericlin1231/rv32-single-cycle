package core

import chisel3._
import chisel3.util._

import parameters.signals.Opcode
import parameters.signals.ALUFunctions
import parameters.signals.Funct3TypeI
import parameters.signals.Funct3TypeR

class ALUControl extends Module {
  val io = IO(new Bundle {
    val opcode = Input(UInt(7.W))
    val funct3 = Input(UInt(3.W))
    val funct7 = Input(UInt(7.W))

    val alu_funct = Output(ALUFunctions())
  })

  io.alu_funct := ALUFunctions.zero

  switch(io.opcode) {
    is(Opcode.I) {
      io.alu_funct := MuxLookup(io.funct3, ALUFunctions.zero)(
        IndexedSeq(
          Funct3TypeI.addi  -> ALUFunctions.add,
          Funct3TypeI.slli  -> ALUFunctions.sll,
          Funct3TypeI.slti  -> ALUFunctions.slt,
          Funct3TypeI.sltiu -> ALUFunctions.sltu,
          Funct3TypeI.xori  -> ALUFunctions.xor,
          Funct3TypeI.ori   -> ALUFunctions.or,
          Funct3TypeI.andi  -> ALUFunctions.and,
          Funct3TypeI.sri   -> Mux(io.funct7(5), ALUFunctions.sra, ALUFunctions.srl)
        )
      );
    }
    is(Opcode.RM) {
      io.alu_funct := MuxLookup(io.funct3, ALUFunctions.zero)(
        IndexedSeq(
          Funct3TypeR.add_sub -> Mux(io.funct7(5), ALUFunctions.sub, ALUFunctions.add),
          Funct3TypeR.sll     -> ALUFunctions.sll,
          Funct3TypeR.slt     -> ALUFunctions.slt,
          Funct3TypeR.sltu    -> ALUFunctions.sltu,
          Funct3TypeR.xor     -> ALUFunctions.xor,
          Funct3TypeR.or      -> ALUFunctions.or,
          Funct3TypeR.and     -> ALUFunctions.and,
          Funct3TypeR.sr      -> Mux(io.funct7(5), ALUFunctions.sra, ALUFunctions.srl)
        )
      );
    }
    is(Opcode.B) {
      io.alu_funct := ALUFunctions.add
    }
    is(Opcode.L) {
      io.alu_funct := ALUFunctions.add
    }
    is(Opcode.S) {
      io.alu_funct := ALUFunctions.add
    }
    is(Opcode.jal) {
      io.alu_funct := ALUFunctions.add
    }
    is(Opcode.jalr) {
      io.alu_funct := ALUFunctions.add
    }
    is(Opcode.lui) {
      io.alu_funct := ALUFunctions.add
    }
    is(Opcode.auipc) {
      io.alu_funct := ALUFunctions.add
    }
  }
}
