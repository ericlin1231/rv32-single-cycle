#
# Computes fib(10) = 55 and stores the result into memory at address 4
#

.section .text
.globl _start

_start:
        # Initialize registers
        li      t0, 0          # t0 = fib(0)
        li      t1, 1          # t1 = fib(1)
        li      t2, 2          # t2 = loop index (starts at 2 because we already have fib(0), fib(1))
        li      t3, 10         # t3 = target index = 10

loop:
        # As long as t2 <= t3, keep calculating next Fibonacci
        ble     t2, t3, calc
        j       done

calc:
        add     t4, t0, t1     # t4 = fib(n-2) + fib(n-1)
        mv      t0, t1         # shift: t0 = fib(n-1)
        mv      t1, t4         # shift: t1 = fib(n)
        addi    t2, t2, 1      # increment index
        j       loop

done:
        # At this point, t1 holds fib(10) = 55
        # Store fib(10) to address 4
        sw      t1, 4(zero)    # Memory[4] = 55
        nop
