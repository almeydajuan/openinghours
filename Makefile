# https://stackoverflow.com/questions/2145590/what-is-the-purpose-of-phony-in-a-makefile
.PHONY: install
install:
    sh config/jabba.sh
	sh config/gradle.sh