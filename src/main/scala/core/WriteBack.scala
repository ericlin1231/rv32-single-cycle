package core

import chisel3._
import chisel3.util._

import parameters.System
import parameters.signals.RegWriteSource

class WriteBack extends Module {
  val io = IO(new Bundle() {
    val instruction_address = Input(UInt(System.AddrWidth))
    val alu_result          = Input(UInt(System.DataWidth))
    val memory_read_data    = Input(UInt(System.DataWidth))
    val regs_write_source   = Input(UInt(2.W))
    val regs_write_data     = Output(UInt(System.DataWidth))
  })
  io.regs_write_data := MuxLookup(io.regs_write_source, io.alu_result)(
    IndexedSeq(
      RegWriteSource.Memory                 -> io.memory_read_data,
      RegWriteSource.NextInstructionAddress -> (io.instruction_address + 4.U)
    )
  );
}
