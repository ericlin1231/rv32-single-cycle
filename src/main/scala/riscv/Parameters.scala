package riscv

import chisel3._
import chisel3.util._

object Parameters {
  val AddrBits  = 32
  val AddrWidth = AddrBits.W

  val InstructionBits  = 32
  val InstructionWidth = InstructionBits.W
  val DataBits         = 32
  val DataWidth        = DataBits.W
  val ByteBits         = 8
  val ByteWidth        = ByteBits.W
  val WordSize         = DataBits / ByteBits

  val PhysicalRegisters         = 32
  val PhysicalRegisterAddrBits  = log2Up(PhysicalRegisters)
  val PhysicalRegisterAddrWidth = PhysicalRegisterAddrBits.W

  val CSRRegisterAddrBits  = 12
  val CSRRegisterAddrWidth = CSRRegisterAddrBits.W

  val InterruptFlagBits  = 32
  val InterruptFlagWidth = InterruptFlagBits.W

  val HoldStateBits   = 3
  val StallStateWidth = HoldStateBits.W

  val MemorySizeInBytes = 32768
  val MemorySizeInWords = MemorySizeInBytes / 4

  val EntryAddress = 0x1000.U(Parameters.AddrWidth)

  val MasterDeviceCount    = 1
  val SlaveDeviceCount     = 8
  val SlaveDeviceCountBits = log2Up(Parameters.SlaveDeviceCount)
}

