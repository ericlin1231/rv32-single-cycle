package peripheral

import chisel3._
import chisel3.util._

import riscv.Parameters

class RAMBundle extends Bundle {
  val address = Input(UInt(Parameters.AddrWidth))
  val wEn     = Input(Bool())
  val wData   = Input(UInt(Parameters.DataWidth))
  val wStrobe = Input(Vec(Parameters.WordSize, Bool()))
  val rEn     = Input(Bool())
  val rData   = Output(UInt(Parameters.DataWidth))
}

class DataRAM(capacity: Int) extends Module {
  val io = IO(new Bundle {
    val bundle          = new RAMBundle
    val alu_result      = Input(UInt(Parameters.DataWidth))
    val reg2_data       = Input(UInt(Parameters.DataWidth))
    val funct3          = Input(UInt(3.W))
    val wb_memory_rData = Output(UInt(Parameters.DataWidth))
  })
  
  val mem_address_index = io.alu_result(log2Up(Parameters.WordSize) - 1, 0).asUInt

  io.bundle.wEn      := false.B
  io.bundle.wData    := 0.U
  io.bundle.wStrobe  := VecInit(Seq.fill(Parameters.WordSize)(false.B))
  io.bundle.address  := io.alu_result
  io.wb_memory_rData := 0.U

  when(io.bundle.rEn) {
    val data = io.bundle.rData
    io.wb_memory_rData := MuxLookup(io.funct3, 0.U)(
      IndexedSeq(
        InstructionsTypeL.lb -> MuxLookup(
          mem_address_index,
          Cat(Fill(24, data(31)), data(31, 24)))(
          IndexedSeq(
            0.U -> Cat(Fill(24, data(7)), data(7, 0)),
            1.U -> Cat(Fill(24, data(15)), data(15, 8)),
            2.U -> Cat(Fill(24, data(23)), data(23, 16))
          )
        ),
        InstructionsTypeL.lbu -> MuxLookup(
          mem_address_index,
          Cat(Fill(24, 0.U), data(31, 24)))(
          IndexedSeq(
            0.U -> Cat(Fill(24, 0.U), data(7, 0)),
            1.U -> Cat(Fill(24, 0.U), data(15, 8)),
            2.U -> Cat(Fill(24, 0.U), data(23, 16))
          )
        ),
        InstructionsTypeL.lh -> Mux(
          mem_address_index === 0.U,
          Cat(Fill(16, data(15)), data(15, 0)),
          Cat(Fill(16, data(31)), data(31, 16))
        ),
        InstructionsTypeL.lhu -> Mux(
          mem_address_index === 0.U,
          Cat(Fill(16, 0.U), data(15, 0)),
          Cat(Fill(16, 0.U), data(31, 16))
        ),
        InstructionsTypeL.lw -> data
      )
    )
  }.elsewhen(io.memory_write_enable) {
    io.memory_bundle.write_data   := io.reg2_data
    io.memory_bundle.write_enable := true.B
    io.memory_bundle.write_strobe := VecInit(Seq.fill(Parameters.WordSize)(false.B))
    when(io.funct3 === InstructionsTypeS.sb) {
      io.memory_bundle.write_strobe(mem_address_index) := true.B
      io.memory_bundle.write_data := io.reg2_data(Parameters.ByteBits, 0) << (mem_address_index << log2Up(
        Parameters.ByteBits
      ).U)
    }.elsewhen(io.funct3 === InstructionsTypeS.sh) {
      when(mem_address_index === 0.U) {
        for (i <- 0 until Parameters.WordSize / 2) {
          io.memory_bundle.write_strobe(i) := true.B
        }
        io.memory_bundle.write_data := io.reg2_data(Parameters.WordSize / 2 * Parameters.ByteBits, 0)
      }.otherwise {
        for (i <- Parameters.WordSize / 2 until Parameters.WordSize) {
          io.memory_bundle.write_strobe(i) := true.B
        }
        io.memory_bundle.write_data := io.reg2_data(
          Parameters.WordSize / 2 * Parameters.ByteBits,
          0
        ) << (Parameters.WordSize / 2 * Parameters.ByteBits)
      }
    }.elsewhen(io.funct3 === InstructionsTypeS.sw) {
      for (i <- 0 until Parameters.WordSize) {
        io.memory_bundle.write_strobe(i) := true.B
      }
    }
  }
}
