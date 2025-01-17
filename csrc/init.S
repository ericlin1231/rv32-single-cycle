.section .text
.globl _start

_start:
    li sp, 4096
    call main
hang:
    j hang
