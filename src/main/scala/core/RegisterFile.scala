package core

import chisel3._

import parameters.System
import parameters.signals.Registers
import bundle.RegisterDebugBundle

class RegisterFile extends Module {
  val io = IO(new Bundle {
    val write_enable  = Input(Bool())
    val write_address = Input(UInt(System.PhysicalRegisterAddrWidth))
    val write_data    = Input(UInt(System.DataWidth))

    val read_address1 = Input(UInt(System.PhysicalRegisterAddrWidth))
    val read_address2 = Input(UInt(System.PhysicalRegisterAddrWidth))
    val read_data1    = Output(UInt(System.DataWidth))
    val read_data2    = Output(UInt(System.DataWidth))

    val DebugPort     = new RegisterDebugBundle
  })

  val registers = RegInit(VecInit(Seq.fill(System.PhysicalRegisters)(0.U(System.DataWidth))))

  when(!reset.asBool) {
    when(io.write_enable && io.write_address =/= 0.U) {
      registers(io.write_address) := io.write_data
    }
  }

  io.read_data1 := Mux(
    io.read_address1 === 0.U,
    0.U,
    registers(io.read_address1)
  )

  io.read_data2 := Mux(
    io.read_address2 === 0.U,
    0.U,
    registers(io.read_address2)
  )

  io.DebugPort.debug_read_data := Mux(
    io.DebugPort.debug_read_address === 0.U,
    0.U,
    registers(io.DebugPort.debug_read_address)
  )
}
