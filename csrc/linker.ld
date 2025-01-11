SECTIONS
{
    . = 0x00000000;         /* Start address */
    .text : { *(.text) }    /* Place .text section */
    .data : { *(.data) }    /* Place .data section */
}

