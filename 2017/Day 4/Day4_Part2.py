#!/usr/bin/python3
import re

def is_valid(line):
    for i in range(0, len(line)-1):
        if (line[i] == line[i+1]):
            return False
    return True

try:
    file = open("Input_Day4" , "r")
except IOError:
    print("*** ERROR: Could not open file for reading input ***")
    raise SystemExit

raw_input = file.readlines();
valid_counter = 0

for line in raw_input:
    line = re.findall("[a-z]+" , line)

    for i in range(0,len(line)):   # Sort the words to check for anagrams
        line[i] = "".join(sorted(line[i]))

    line.sort()

    if (is_valid(line)):
        valid_counter += 1

print(valid_counter)
