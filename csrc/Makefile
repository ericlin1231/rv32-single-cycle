.PHONY: hex elf clean

CROSS_COMPILATION ?= riscv64-unknown-elf
HEX_DIR = ../src/main/resources/csrc

CC   = $(CROSS_COMPILATION)-gcc
LD   = $(CROSS_COMPILATION)-ld
BIN  = $(CROSS_COMPILATION)-objcopy
HEX  = hexdump

CFLAG    = -O0 -march=rv32i -mabi=ilp32 -nostdlib
LDFLAG   = -m elf32lriscv -T link.ld
BINFLAG  = -O binary -j .text -j .data
HEXFLAG  = -v -e '/4 "%08x\n"'

HEXS = \
	fibonacci.hex     \
	function.hex      \
	recursive_sum.hex \
	sum.hex

init.o: init.S
	$(CC) $(CFLAG) -o $@ -c $<

%.o: %.c init.o
	$(CC) $(CFLAG) -o $@ -c $<

%.elf: %.o
	$(LD) $(LDFLAG) -o $@ init.o $<

%.bin: %.elf
	$(BIN) $(BINFLAG) $< $@

%.hex: %.bin
	$(HEX) $(HEXFLAG) $< > $@

hex: $(HEXS)
	mkdir -p $(HEX_DIR)
	mv $(HEXS) $(HEX_DIR)

elf: $(HEXS:.hex=.elf)

clean:
	rm -rf *.elf *.o $(HEX_DIR)
