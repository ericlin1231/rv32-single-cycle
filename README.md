# RISC-V 32 Bits Single Cycle CPU

## Build Binary Instruction Files

The `csrc` directory store the function implemented in C, with `init.S`, the CPU will doing some preprocess before enter the `main` in the C program, and wait until test terminated after return from `main`.

Enter `csrc` to generate the binary instruction files.

```shell
$ cd csrc
$ make hex
```
The `asmsrc` directory store the program implemented in assembly, while I am currently learning how to trace the CPU behavior via waveform, the simple assembly code is more suitable for beginner like me.

Enter `asmsrc` to generate the binary instruction files.

```shell
$ cd asmsrc
$ cd make hex
```

## Test

The testcases stored in the `src/test/scala`, use below command to run all testcases.

```shell
$ sbt test
```
