#!/usr/bin/python3
import re

try:
    file = open("Input_Day5" , "r")
except IOError:
    print("*** ERROR: Could not open file for reading input ***")
    raise SystemExit

raw_input = file.readlines()

for i in range(0 , len(raw_input)):
    raw_input[i] = int(raw_input[i].strip())

index = 0
num_steps = 0
jump = None

while (index < len(raw_input)):
    jump = raw_input[index]

    if (jump >= 3):
        raw_input[index] -= 1
    else:
        raw_input[index] += 1
        
    index += jump
    num_steps += 1

print(num_steps)
