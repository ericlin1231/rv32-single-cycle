.PHONY: sv test clean

PROJECT = cpu

test:
	mill $(PROJECT).test

clean:
	@rm -rf out
