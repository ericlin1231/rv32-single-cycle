FIRRTL version 1.2.0
circuit Fetch :
  module Fetch :
    input clock : Clock
    input reset : UInt<1>
    input io_jump_flag_id : UInt<1> @[src/main/scala/riscv/core/Fetch.scala 13:14]
    input io_jump_address_id : UInt<32> @[src/main/scala/riscv/core/Fetch.scala 13:14]
    input io_instruction_read_data : UInt<32> @[src/main/scala/riscv/core/Fetch.scala 13:14]
    input io_instruction_valid : UInt<1> @[src/main/scala/riscv/core/Fetch.scala 13:14]
    output io_instruction_address : UInt<32> @[src/main/scala/riscv/core/Fetch.scala 13:14]
    output io_instruction : UInt<32> @[src/main/scala/riscv/core/Fetch.scala 13:14]

    reg pc : UInt<32>, clock with :
      reset => (UInt<1>("h0"), pc) @[src/main/scala/riscv/core/Fetch.scala 22:19]
    node _T = eq(io_jump_flag_id, UInt<1>("h1")) @[src/main/scala/riscv/core/Fetch.scala 26:26]
    node _pc_T = add(pc, UInt<3>("h4")) @[src/main/scala/riscv/core/Fetch.scala 29:16]
    node _pc_T_1 = tail(_pc_T, 1) @[src/main/scala/riscv/core/Fetch.scala 29:16]
    node _GEN_0 = mux(_T, io_jump_address_id, _pc_T_1) @[src/main/scala/riscv/core/Fetch.scala 26:38 27:10 29:10]
    node _GEN_1 = mux(io_instruction_valid, io_instruction_read_data, UInt<5>("h13")) @[src/main/scala/riscv/core/Fetch.scala 24:30 25:20 33:20]
    node _GEN_2 = mux(io_instruction_valid, _GEN_0, pc) @[src/main/scala/riscv/core/Fetch.scala 24:30 32:20]
    io_instruction_address <= pc @[src/main/scala/riscv/core/Fetch.scala 35:26]
    io_instruction <= _GEN_1
    pc <= mux(reset, UInt<32>("h1000"), _GEN_2) @[src/main/scala/riscv/core/Fetch.scala 22:{19,19}]
