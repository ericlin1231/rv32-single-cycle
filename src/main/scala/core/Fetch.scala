package core

import chisel3._

import bundle.InstructionROMBundle
import bundle.FetchDebugBundle
import parameters.System

class Fetch extends Module {
  val io = IO(new Bundle {
    val IROMPort            = Flipped(new InstructionROMBundle)
    val jump_flag_id        = Input(Bool())
    val jump_address_id     = Input(UInt(System.AddrWidth))
    val instruction_valid   = Input(Bool())
    val instruction_address = Output(UInt(System.AddrWidth))
    val instruction         = Output(UInt(System.InstructionWidth))

    val DebugPort           = new FetchDebugBundle
  })
  
  val PC = RegInit(System.EntryAddress)
  io.IROMPort.address := System.EntryAddress

  when(io.instruction_valid) {
    io.IROMPort.address := PC >> 2
    io.instruction := io.IROMPort.instruction
    PC := Mux(io.jump_flag_id === true.B, io.jump_address_id, PC + 4.U)
  }.otherwise {
    PC                  := PC
    io.instruction      := 0x00000013.U
    io.IROMPort.address := DontCare
  }
  io.instruction_address := PC

  /* Fetch Debug */
  io.DebugPort.debug_read_pc := PC
  io.DebugPort.debug_read_instruction := io.IROMPort.instruction
}
