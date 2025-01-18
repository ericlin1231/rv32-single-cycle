.PHONY: run test clean

PROJECT = cpu

test:
	@- sbt test
run:
	@mkdir -p build
	@sbt run

clean:
	@rm -rf build target project test_run_dir
