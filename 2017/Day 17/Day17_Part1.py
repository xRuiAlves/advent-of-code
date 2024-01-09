#!/usr/bin/python3
import re

def main():
    try:
        file = open("Input_Day17" , "r")
    except IOError:
        print("*** ERROR: Could not open file for reading input ***")
        raise SystemExit

    spinlock_num = int(re.findall("\d+" , file.readline())[0])
    stop_num = 2017
    current_pos = 1

    circ_buffer = [0 , 1]
    answer = None

    for i in range(2, stop_num+1):
        current_pos = (current_pos + spinlock_num + 1)%len(circ_buffer)
        circ_buffer.insert(current_pos,i)

    for i in range(0 , len(circ_buffer)):
        if (circ_buffer[i] == stop_num):
            if (i == len(circ_buffer)-1):
                answer = circ_buffer[0]
            else:
                answer = circ_buffer[i+1]

    print(answer)


main()
