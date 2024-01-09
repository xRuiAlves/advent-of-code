#!/usr/bin/python3
import re

try:
    file = open("Input_Day7" , "r")
except IOError:
    print("*** ERROR: Could not open file for reading input ***")
    raise SystemExit

raw_input = file.readlines()
file.close()

# Parse the input
for i in range(0, len(raw_input)):
    raw_input[i] = re.findall("[a-z]+" , raw_input[i])

# Find the name of the bottom program!
program = raw_input[0][0]
raw_input.pop(0)
index = 0
poppedOne = False


# Find the program that is holding the current program
while True:
    if (index >= len(raw_input)):
        break

    if (len(raw_input[index]) == 1):    # This is a leaf from the tree ; Not relevant, might aswell remove it so the next iterations are shorter.
        raw_input.pop(index)
        continue
    else:
        for j in range(1 , len(raw_input[index])):  # Search for the current program in the children of program at index
            if (raw_input[index][j] == program):    # Found it!! Now the program we are analizing is the father of the program we were analizing
                program = raw_input[index][0]
                raw_input.pop(index)
                index = 0
                poppedOne = True
                break
        if (poppedOne):
            poppedOne = False
        else:
            index += 1

print(program)
