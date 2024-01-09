#!/usr/bin/python3
import re

def parse_reg_val(line):
    num = int( re.findall("\d+",line)[0] )
    return num

def main():
    try:
        file = open("Input_Day15" , "r")
    except IOError:
        print("*** ERROR: Could not open file for reading input ***")
        raise SystemExit

    raw_input = file.readlines()

    reg_a_val = parse_reg_val(raw_input[0])
    reg_b_val = parse_reg_val(raw_input[1])

    large_divisor = 2147483647
    mask_16_bits = 0xFFFF
    reg_a_multiplyer = 16807
    reg_b_multiplyer = 48271
    reg_a_16 = None
    reg_b_16 = None
    num_pairs = 40000000
    judge_count = 0

    for i in range(0,num_pairs):
        reg_a_val *= reg_a_multiplyer
        reg_a_val %= large_divisor
        reg_b_val *= reg_b_multiplyer
        reg_b_val %= large_divisor
        reg_a_16 = reg_a_val & mask_16_bits
        reg_b_16 = reg_b_val & mask_16_bits

        if (reg_a_16 == reg_b_16):
            judge_count += 1

    print("Judge count: " , judge_count)




main()
