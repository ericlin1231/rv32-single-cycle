.section .text
.globl _start

_start:
    li t0, 0
    li t1, 10
    la a0, 4
    ble t0, t1, store
    nop
    nop
    nop
    nop
store:
    sw t1, 0(a0)
