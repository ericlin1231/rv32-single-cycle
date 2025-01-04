package riscv.core

import chisel3._
import riscv.Parameters

object ProgramCounter {
  val EntryAddress = Parameters.EntryAddress
}

class Fetch extends Module {
  val io = IO(new Bundle {
    val jump_flag_id          = Input(Bool())
    val jump_address_id       = Input(UInt(Parameters.AddrWidth))
    val instruction_read_data = Input(UInt(Parameters.DataWidth))
    val instruction_valid     = Input(Bool())

    val instruction_address = Output(UInt(Parameters.AddrWidth))
    val instruction         = Output(UInt(Parameters.InstructionWidth))
  })
  val pc = RegInit(ProgramCounter.EntryAddress)

  when(io.instruction_valid) {
    io.instruction := io.instruction_read_data
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

