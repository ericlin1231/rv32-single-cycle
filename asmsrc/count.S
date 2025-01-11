.section .text
.globl _start

_start:
    li t0, 0          # t0 will serve as the counter, starting at 0
    li t1, 10         # t1 holds the maximum count value (10)

loop:
    blt t0, t1, continue  # If t0 < t1, jump to 'continue'
    j end_loop             # Otherwise, exit the loop

continue:
    addi t0, t0, 1        # Increment counter: t0 = t0 + 1
    j loop                # Repeat the loop

end_loop:
    li t2, 1000           # Load immediate value 1000 into register t2
    sw t0, 0(t2)          # Store word from t0 into memory address contained in t2
    nop
