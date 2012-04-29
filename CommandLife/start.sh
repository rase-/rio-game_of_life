#!/bin/bash

# java -jar dist/CommandLife.jar input_file threads [comparison_file]

java -jar dist/CommandLife.jar life_800_10000.txt 16 life_800_10000_expected_result.txt
