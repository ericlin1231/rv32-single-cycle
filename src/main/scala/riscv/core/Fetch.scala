package riscv.core

import chisel3._
import _root_.circt.stage.ChiselStage

import riscv.Parameters

import peripheral.InstructionROM

object ProgramCounter {
  val EntryAddress = Parameters.EntryAddress
}

class Fetch(filename: String) extends Module {
  val io = IO(new Bundle {
    val jump_flag_id          = Input(Bool())
    val jump_address_id       = Input(UInt(Parameters.AddrWidth))
    val instruction_valid     = Input(Bool())

    val instruction_address = Output(UInt(Parameters.AddrWidth))
    val instruction         = Output(UInt(Parameters.InstructionWidth))
  })
  val InstructionROM      = Module(new InstructionROM(filename))
  val PC                  = RegInit(ProgramCounter.EntryAddress)
  val instruction_address = PC >> 2

  when(io.instruction_valid) {
    InstructionROM.io.address := instruction_address.U
    io.instruction := InstructionROM.io.instruction
    when(io.jump_flag_id === true.B) {
      pc := io.jump_address_id
    }.otherwise {
      pc := pc + 4.U
    }
  }.otherwise {
    pc             := pc
    io.instruction := 0x00000013.U
  }
  io.instruction_address := pc
}

object Fetch extends App {
  ChiselStage.emitSystemVerilogFile(
    new Fetch,
    args = Array("--target-dir", "build/core"),
    firtoolOpts = Array(
      "--disable-all-randomization",
      "--lowering-options=disallowLocalVariables",
      "--strip-debug-info"
    )
  )
}
