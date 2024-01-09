#!/usr/bin/python3
import re

def main():
    try:
        file = open("Input_Day17" , "r")
    except IOError:
        print("*** ERROR: Could not open file for reading input ***")
        raise SystemExit

    spinlock_num = int(re.findall("\d+" , file.readline())[0])
    stop_num = 50000000
    current_pos = 1
    circ_buffer_len = 2
    zero_index = 0
    value_after_zero = 1

    for i in range(2, stop_num+1):
        current_pos = (current_pos + spinlock_num + 1)%circ_buffer_len
        circ_buffer_len += 1

        if (current_pos <= zero_index):
            zero_index += 1
        elif (current_pos == zero_index+1):
            value_after_zero = i

    print(value_after_zero)


main()
