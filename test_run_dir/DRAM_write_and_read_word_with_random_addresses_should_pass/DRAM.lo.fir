FIRRTL version 1.2.0
circuit DRAM :
  module DRAM :
    input clock : Clock
    input reset : UInt<1>
    input io_addr : UInt<32> @[src/main/scala/peripheral/DRAM.scala 9:14]
    input io_wEn : UInt<1> @[src/main/scala/peripheral/DRAM.scala 9:14]
    input io_wData : UInt<32> @[src/main/scala/peripheral/DRAM.scala 9:14]
    output io_rData : UInt<32> @[src/main/scala/peripheral/DRAM.scala 9:14]

    mem mem : @[src/main/scala/peripheral/DRAM.scala 17:24]
      data-type => UInt<8>
      depth => 33554432
      read-latency => 1
      write-latency => 1
      reader => readData_MPORT
      reader => readData_MPORT_1
      reader => readData_MPORT_2
      reader => readData_MPORT_3
      writer => MPORT
      writer => MPORT_1
      writer => MPORT_2
      writer => MPORT_3
      read-under-write => undefined
    node _readData_T = shr(io_addr, 2) @[src/main/scala/peripheral/DRAM.scala 19:40]
    node _readData_T_1 = add(_readData_T, UInt<2>("h3")) @[src/main/scala/peripheral/DRAM.scala 19:46]
    node _readData_T_2 = tail(_readData_T_1, 1) @[src/main/scala/peripheral/DRAM.scala 19:46]
    node _GEN_0 = validif(UInt<1>("h1"), _readData_T_2) @[src/main/scala/peripheral/DRAM.scala 19:{30,30}]
    node _readData_WIRE = _GEN_0 @[src/main/scala/peripheral/DRAM.scala 19:30]
    node _readData_T_3 = or(_readData_WIRE, UInt<25>("h0")) @[src/main/scala/peripheral/DRAM.scala 19:30]
    node _readData_T_4 = bits(_readData_T_3, 24, 0) @[src/main/scala/peripheral/DRAM.scala 19:30]
    node _GEN_1 = mux(UInt<1>("h1"), UInt<1>("h1"), UInt<1>("h0")) @[src/main/scala/peripheral/DRAM.scala 17:24 19:{30,30}]
    node _GEN_2 = validif(UInt<1>("h1"), _readData_T_4) @[src/main/scala/peripheral/DRAM.scala 19:{30,30}]
    node _GEN_3 = validif(UInt<1>("h1"), clock) @[src/main/scala/peripheral/DRAM.scala 19:{30,30}]
    node _readData_T_5 = shr(io_addr, 2) @[src/main/scala/peripheral/DRAM.scala 20:40]
    node _readData_T_6 = add(_readData_T_5, UInt<2>("h2")) @[src/main/scala/peripheral/DRAM.scala 20:46]
    node _readData_T_7 = tail(_readData_T_6, 1) @[src/main/scala/peripheral/DRAM.scala 20:46]
    node _GEN_4 = validif(UInt<1>("h1"), _readData_T_7) @[src/main/scala/peripheral/DRAM.scala 20:{30,30}]
    node _readData_WIRE_1 = _GEN_4 @[src/main/scala/peripheral/DRAM.scala 20:30]
    node _readData_T_8 = or(_readData_WIRE_1, UInt<25>("h0")) @[src/main/scala/peripheral/DRAM.scala 20:30]
    node _readData_T_9 = bits(_readData_T_8, 24, 0) @[src/main/scala/peripheral/DRAM.scala 20:30]
    node _GEN_5 = validif(UInt<1>("h1"), _readData_T_9) @[src/main/scala/peripheral/DRAM.scala 20:{30,30}]
    node _readData_T_10 = shr(io_addr, 2) @[src/main/scala/peripheral/DRAM.scala 21:40]
    node _readData_T_11 = add(_readData_T_10, UInt<1>("h1")) @[src/main/scala/peripheral/DRAM.scala 21:46]
    node _readData_T_12 = tail(_readData_T_11, 1) @[src/main/scala/peripheral/DRAM.scala 21:46]
    node _GEN_6 = validif(UInt<1>("h1"), _readData_T_12) @[src/main/scala/peripheral/DRAM.scala 21:{30,30}]
    node _readData_WIRE_2 = _GEN_6 @[src/main/scala/peripheral/DRAM.scala 21:30]
    node _readData_T_13 = or(_readData_WIRE_2, UInt<25>("h0")) @[src/main/scala/peripheral/DRAM.scala 21:30]
    node _readData_T_14 = bits(_readData_T_13, 24, 0) @[src/main/scala/peripheral/DRAM.scala 21:30]
    node _GEN_7 = validif(UInt<1>("h1"), _readData_T_14) @[src/main/scala/peripheral/DRAM.scala 21:{30,30}]
    node _readData_T_15 = shr(io_addr, 2) @[src/main/scala/peripheral/DRAM.scala 22:39]
    node _GEN_8 = validif(UInt<1>("h1"), _readData_T_15) @[src/main/scala/peripheral/DRAM.scala 22:{30,30}]
    node _readData_WIRE_3 = _GEN_8 @[src/main/scala/peripheral/DRAM.scala 22:30]
    node _readData_T_16 = or(_readData_WIRE_3, UInt<25>("h0")) @[src/main/scala/peripheral/DRAM.scala 22:30]
    node _readData_T_17 = bits(_readData_T_16, 24, 0) @[src/main/scala/peripheral/DRAM.scala 22:30]
    node _GEN_9 = validif(UInt<1>("h1"), _readData_T_17) @[src/main/scala/peripheral/DRAM.scala 22:{30,30}]
    node readData_lo = cat(mem.readData_MPORT_2.data, mem.readData_MPORT_3.data) @[src/main/scala/peripheral/DRAM.scala 19:21]
    node readData_hi = cat(mem.readData_MPORT.data, mem.readData_MPORT_1.data) @[src/main/scala/peripheral/DRAM.scala 19:21]
    node readData = cat(readData_hi, readData_lo) @[src/main/scala/peripheral/DRAM.scala 19:21]
    node _T = add(io_addr, UInt<2>("h3")) @[src/main/scala/peripheral/DRAM.scala 25:23]
    node _T_1 = tail(_T, 1) @[src/main/scala/peripheral/DRAM.scala 25:23]
    node _T_2 = bits(io_wData, 31, 24) @[src/main/scala/peripheral/DRAM.scala 25:38]
    node _T_3 = bits(_T_1, 24, 0)
    node _T_4 = add(io_addr, UInt<2>("h2")) @[src/main/scala/peripheral/DRAM.scala 26:23]
    node _T_5 = tail(_T_4, 1) @[src/main/scala/peripheral/DRAM.scala 26:23]
    node _T_6 = bits(io_wData, 23, 16) @[src/main/scala/peripheral/DRAM.scala 26:38]
    node _T_7 = bits(_T_5, 24, 0)
    node _T_8 = add(io_addr, UInt<1>("h1")) @[src/main/scala/peripheral/DRAM.scala 27:23]
    node _T_9 = tail(_T_8, 1) @[src/main/scala/peripheral/DRAM.scala 27:23]
    node _T_10 = bits(io_wData, 15, 8) @[src/main/scala/peripheral/DRAM.scala 27:38]
    node _T_11 = bits(_T_9, 24, 0)
    node _T_12 = bits(io_wData, 7, 0) @[src/main/scala/peripheral/DRAM.scala 28:32]
    node _T_13 = bits(io_addr, 24, 0)
    node _GEN_10 = validif(io_wEn, _T_3) @[src/main/scala/peripheral/DRAM.scala 24:16]
    node _GEN_11 = validif(io_wEn, clock) @[src/main/scala/peripheral/DRAM.scala 24:16]
    node _GEN_12 = mux(io_wEn, UInt<1>("h1"), UInt<1>("h0")) @[src/main/scala/peripheral/DRAM.scala 24:16 17:24]
    node _GEN_13 = validif(io_wEn, UInt<1>("h1")) @[src/main/scala/peripheral/DRAM.scala 24:16]
    node _GEN_14 = validif(io_wEn, _T_2) @[src/main/scala/peripheral/DRAM.scala 24:16]
    node _GEN_15 = validif(io_wEn, _T_7) @[src/main/scala/peripheral/DRAM.scala 24:16]
    node _GEN_16 = validif(io_wEn, _T_6) @[src/main/scala/peripheral/DRAM.scala 24:16]
    node _GEN_17 = validif(io_wEn, _T_11) @[src/main/scala/peripheral/DRAM.scala 24:16]
    node _GEN_18 = validif(io_wEn, _T_10) @[src/main/scala/peripheral/DRAM.scala 24:16]
    node _GEN_19 = validif(io_wEn, _T_13) @[src/main/scala/peripheral/DRAM.scala 24:16]
    node _GEN_20 = validif(io_wEn, _T_12) @[src/main/scala/peripheral/DRAM.scala 24:16]
    io_rData <= readData @[src/main/scala/peripheral/DRAM.scala 31:12]
    mem.readData_MPORT.addr <= _GEN_2
    mem.readData_MPORT.en <= _GEN_1
    mem.readData_MPORT.clk <= _GEN_3
    mem.readData_MPORT_1.addr <= _GEN_5
    mem.readData_MPORT_1.en <= _GEN_1
    mem.readData_MPORT_1.clk <= _GEN_3
    mem.readData_MPORT_2.addr <= _GEN_7
    mem.readData_MPORT_2.en <= _GEN_1
    mem.readData_MPORT_2.clk <= _GEN_3
    mem.readData_MPORT_3.addr <= _GEN_9
    mem.readData_MPORT_3.en <= _GEN_1
    mem.readData_MPORT_3.clk <= _GEN_3
    mem.MPORT.addr <= _GEN_10
    mem.MPORT.en <= _GEN_12
    mem.MPORT.clk <= _GEN_11
    mem.MPORT.data <= _GEN_14
    mem.MPORT.mask <= _GEN_13
    mem.MPORT_1.addr <= _GEN_15
    mem.MPORT_1.en <= _GEN_12
    mem.MPORT_1.clk <= _GEN_11
    mem.MPORT_1.data <= _GEN_16
    mem.MPORT_1.mask <= _GEN_13
    mem.MPORT_2.addr <= _GEN_17
    mem.MPORT_2.en <= _GEN_12
    mem.MPORT_2.clk <= _GEN_11
    mem.MPORT_2.data <= _GEN_18
    mem.MPORT_2.mask <= _GEN_13
    mem.MPORT_3.addr <= _GEN_19
    mem.MPORT_3.en <= _GEN_12
    mem.MPORT_3.clk <= _GEN_11
    mem.MPORT_3.data <= _GEN_20
    mem.MPORT_3.mask <= _GEN_13
