.PHONY: run test clean

PROJECT = cpu

test:
	@- sbt test
	@rm -rf target project
run:
	@mkdir -p build/core
	@mkdir -p build/peripheral
	@sbt run
	@rm -rf target project

clean:
	@rm -rf build target project
