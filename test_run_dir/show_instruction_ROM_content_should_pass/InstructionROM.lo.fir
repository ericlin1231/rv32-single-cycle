FIRRTL version 1.2.0
circuit InstructionROM :
  module InstructionROM :
    input clock : Clock
    input reset : UInt<1>
    input io_address : UInt<32> @[src/main/scala/peripheral/InstructionROM.scala 11:14]
    output io_data : UInt<32> @[src/main/scala/peripheral/InstructionROM.scala 11:14]

    mem mem : @[src/main/scala/peripheral/InstructionROM.scala 16:24]
      data-type => UInt<32>
      depth => 1024
      read-latency => 1
      write-latency => 1
      reader => io_data_MPORT
      read-under-write => undefined
    node _GEN_0 = validif(UInt<1>("h1"), io_address) @[src/main/scala/peripheral/InstructionROM.scala 23:{22,22}]
    node _io_data_WIRE = _GEN_0 @[src/main/scala/peripheral/InstructionROM.scala 23:22]
    node _io_data_T = or(_io_data_WIRE, UInt<10>("h0")) @[src/main/scala/peripheral/InstructionROM.scala 23:22]
    node _io_data_T_1 = bits(_io_data_T, 9, 0) @[src/main/scala/peripheral/InstructionROM.scala 23:22]
    node _GEN_1 = mux(UInt<1>("h1"), UInt<1>("h1"), UInt<1>("h0")) @[src/main/scala/peripheral/InstructionROM.scala 23:{22,22} 16:24]
    node _GEN_2 = validif(UInt<1>("h1"), _io_data_T_1) @[src/main/scala/peripheral/InstructionROM.scala 23:{22,22}]
    node _GEN_3 = validif(UInt<1>("h1"), clock) @[src/main/scala/peripheral/InstructionROM.scala 23:{22,22}]
    io_data <= mem.io_data_MPORT.data @[src/main/scala/peripheral/InstructionROM.scala 23:11]
    mem.io_data_MPORT.addr <= _GEN_2
    mem.io_data_MPORT.en <= _GEN_1
    mem.io_data_MPORT.clk <= _GEN_3
