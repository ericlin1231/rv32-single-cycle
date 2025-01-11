.PHONY: hex elf clean

CROSS_COMPILATION ?= riscv64-unknown-linux-gnu
HEX_DIR = ../src/main/resources

CC   = $(CROSS_COMPILATION)-gcc
LD   = $(CROSS_COMPILATION)-ld
BIN  = $(CROSS_COMPILATION)-objcopy
DUMP = $(CROSS_COMPILATION)-objdump
HEX  = hexdump

CFLAG    = -c -march=rv32i -mabi=ilp32
LDFLAG   = -m elf32lriscv -T linker.ld
BINFLAG  = -O binary -j .text -j .data
DUMPFLAG = -d -j .text -j .data
HEXFLAG  = -v -e '/4 "%08x\n"'

CSRC = \
	  fibonacci.c

%.o: %.c
	$(CC) $(CFLAG) -o $@ $<

%.elf: %.o
	$(LD) $(LDFLAG) -o $@ $<

%.bin: %.elf
	$(BIN) $(BINFLAG) $< $@

%.hex: %.bin
	$(HEX) $(HEXFLAG) $< > $@

ELFS = $(CSRC:.c=.elf)
HEXS = $(CSRC:.c=.hex)

hex: $(HEXS)
	mkdir -p $(HEX_DIR)
	mv $(HEXS) $(HEX_DIR)

elf: $(ELFS)

clean:
	rm -rf *.o *.elf *.bin $(HEX_DIR)

