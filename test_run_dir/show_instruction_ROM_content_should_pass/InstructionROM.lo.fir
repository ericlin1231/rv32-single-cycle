FIRRTL version 1.2.0
circuit InstructionROM :
  module InstructionROM :
    input clock : Clock
    input reset : UInt<1>
    input io_IROMPort_address : UInt<32> @[src/main/scala/peripheral/InstructionROM.scala 14:14]
    output io_IROMPort_instruction : UInt<32> @[src/main/scala/peripheral/InstructionROM.scala 14:14]
    input io_DebugPort_instructionROM_debug_read_address : UInt<32> @[src/main/scala/peripheral/InstructionROM.scala 14:14]
    output io_DebugPort_instructionROM_debug_read_instruction : UInt<32> @[src/main/scala/peripheral/InstructionROM.scala 14:14]

    mem mem : @[src/main/scala/peripheral/InstructionROM.scala 19:16]
      data-type => UInt<32>
      depth => 32768
      read-latency => 0
      write-latency => 1
      reader => io_IROMPort_instruction_MPORT
      reader => io_DebugPort_instructionROM_debug_read_instruction_MPORT
      read-under-write => undefined
    node _io_IROMPort_instruction_T = bits(io_IROMPort_address, 14, 0) @[src/main/scala/peripheral/InstructionROM.scala 29:38]
    node _io_DebugPort_instructionROM_debug_read_instruction_T = bits(io_DebugPort_instructionROM_debug_read_address, 14, 0) @[src/main/scala/peripheral/InstructionROM.scala 33:13]
    io_IROMPort_instruction <= mem.io_IROMPort_instruction_MPORT.data @[src/main/scala/peripheral/InstructionROM.scala 29:27]
    io_DebugPort_instructionROM_debug_read_instruction <= mem.io_DebugPort_instructionROM_debug_read_instruction_MPORT.data @[src/main/scala/peripheral/InstructionROM.scala 32:54]
    mem.io_IROMPort_instruction_MPORT.addr <= _io_IROMPort_instruction_T @[src/main/scala/peripheral/InstructionROM.scala 29:38]
    mem.io_IROMPort_instruction_MPORT.en <= UInt<1>("h1") @[src/main/scala/peripheral/InstructionROM.scala 29:38]
    mem.io_IROMPort_instruction_MPORT.clk <= clock @[src/main/scala/peripheral/InstructionROM.scala 29:38]
    mem.io_DebugPort_instructionROM_debug_read_instruction_MPORT.addr <= _io_DebugPort_instructionROM_debug_read_instruction_T @[src/main/scala/peripheral/InstructionROM.scala 33:13]
    mem.io_DebugPort_instructionROM_debug_read_instruction_MPORT.en <= UInt<1>("h1") @[src/main/scala/peripheral/InstructionROM.scala 33:13]
    mem.io_DebugPort_instructionROM_debug_read_instruction_MPORT.clk <= clock @[src/main/scala/peripheral/InstructionROM.scala 33:13]
