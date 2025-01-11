package riscv.core

import chisel3._
import chisel3.util._
import _root_.circt.stage.ChiselStage

import riscv.Parameters

class WriteBack extends Module {
  val io = IO(new Bundle() {
    val instruction_address = Input(UInt(Parameters.AddrWidth))
    val alu_result          = Input(UInt(Parameters.DataWidth))
    val memory_read_data    = Input(UInt(Parameters.DataWidth))
    val regs_write_source   = Input(UInt(2.W))
    val regs_write_data     = Output(UInt(Parameters.DataWidth))
  })
  io.regs_write_data := MuxLookup(io.regs_write_source, io.alu_result)(
    IndexedSeq(
      RegWriteSource.Memory                 -> io.memory_read_data,
      RegWriteSource.NextInstructionAddress -> (io.instruction_address + 4.U)
    )
  );
}

object WriteBack extends App {
  ChiselStage.emitSystemVerilogFile(
    new WriteBack,
    args = Array("--target-dir", "build/core"),
    firtoolOpts = Array(
      "--disable-all-randomization",
      "--lowering-options=disallowLocalVariables",
      "--strip-debug-info"
    )
  )
}

