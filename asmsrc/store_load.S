.section .text
.globl _start

_start:
    la a0, 4
    li t0, 10
    sw t0, 0(a0)
    lw t1, 0(a0)
hang:
    j hang
