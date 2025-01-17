FIRRTL version 1.2.0
circuit Fetch :
  module Fetch :
    input clock : Clock
    input reset : UInt<1>
    output io_IROMPort_address : UInt<32> @[src/main/scala/core/Fetch.scala 10:14]
    input io_IROMPort_instruction : UInt<32> @[src/main/scala/core/Fetch.scala 10:14]
    input io_jump_flag_id : UInt<1> @[src/main/scala/core/Fetch.scala 10:14]
    input io_jump_address_id : UInt<32> @[src/main/scala/core/Fetch.scala 10:14]
    input io_instruction_valid : UInt<1> @[src/main/scala/core/Fetch.scala 10:14]
    output io_instruction_address : UInt<32> @[src/main/scala/core/Fetch.scala 10:14]
    output io_instruction : UInt<32> @[src/main/scala/core/Fetch.scala 10:14]
    output io_DebugPort_fetch_debug_read_pc : UInt<32> @[src/main/scala/core/Fetch.scala 10:14]
    output io_DebugPort_fetch_debug_read_instruction : UInt<32> @[src/main/scala/core/Fetch.scala 10:14]

    reg PC : UInt<32>, clock with :
      reset => (UInt<1>("h0"), PC) @[src/main/scala/core/Fetch.scala 21:19]
    node _io_IROMPort_address_T = shr(PC, 2) @[src/main/scala/core/Fetch.scala 25:31]
    node _PC_T = eq(io_jump_flag_id, UInt<1>("h1")) @[src/main/scala/core/Fetch.scala 27:31]
    node _PC_T_1 = add(PC, UInt<3>("h4")) @[src/main/scala/core/Fetch.scala 27:66]
    node _PC_T_2 = tail(_PC_T_1, 1) @[src/main/scala/core/Fetch.scala 27:66]
    node _PC_T_3 = mux(_PC_T, io_jump_address_id, _PC_T_2) @[src/main/scala/core/Fetch.scala 27:14]
    node _GEN_0 = validif(io_instruction_valid, _io_IROMPort_address_T) @[src/main/scala/core/Fetch.scala 24:30 25:25]
    node _GEN_1 = mux(io_instruction_valid, io_IROMPort_instruction, UInt<5>("h13")) @[src/main/scala/core/Fetch.scala 24:30 26:20 30:25]
    node _GEN_2 = mux(io_instruction_valid, _PC_T_3, PC) @[src/main/scala/core/Fetch.scala 24:30 27:8 29:25]
    io_IROMPort_address <= pad(_GEN_0, 32)
    io_instruction_address <= PC @[src/main/scala/core/Fetch.scala 33:26]
    io_instruction <= _GEN_1
    io_DebugPort_fetch_debug_read_pc <= PC @[src/main/scala/core/Fetch.scala 36:36]
    io_DebugPort_fetch_debug_read_instruction <= io_IROMPort_instruction @[src/main/scala/core/Fetch.scala 37:45]
    PC <= mux(reset, UInt<32>("h0"), _GEN_2) @[src/main/scala/core/Fetch.scala 21:{19,19}]
