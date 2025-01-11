.PHONY: hex elf clean

CROSS_COMPILATION ?= riscv64-unknown-linux-gnu
HEX_DIR = ../src/main/resources

AS   = $(CROSS_COMPILATION)-as
LD   = $(CROSS_COMPILATION)-ld
BIN  = $(CROSS_COMPILATION)-objcopy
DUMP = $(CROSS_COMPILATION)-objdump
HEX  = hexdump

ASFLAG   = -march=rv32i -mabi=ilp32
LDFLAG   = -m elf32lriscv -T linker.ld
BINFLAG  = -O binary -j .text -j .data
DUMPFLAG = -d -j .text -j .data
HEXFLAG  = -v -e '/4 "%08x\n"'

ASM = \
	  count.S

%.o: %.S
	$(AS) $(ASFLAG) -o $@ $<

%.elf: %.o
	$(LD) $(LDFLAG) -o $@ $<

%.bin: %.elf
	$(BIN) $(BINFLAG) $< $@

%.hex: %.bin
	$(HEX) $(HEXFLAG) $< > $@

ELFS = $(ASM:.S=.elf)
HEXS = $(ASM:.S=.hex)

hex: $(HEXS)
	mkdir -p $(HEX_DIR)
	mv $(HEXS) $(HEX_DIR)

elf: $(ELFS)

clean:
	rm -rf *.o *.elf *.bin $(HEX_DIR)
