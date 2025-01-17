.section .text
.globl _start

_start:
    li sp, 4096
    li t0, 10
    sw t0, 12(sp)
    lw t1, 12(sp)
hang:
    j hang
