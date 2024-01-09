#!/usr/bin/python3
import re

# Finds the memory bank with the most elements
def find_greater(line):
    greater = line[0]
    index = 0
    for i in range(1 , len(line)):
        if (line[i] > greater):
            index = i
            greater = line[i]
    return index

# Does a full realocation cycly, reallocating numElements elements starting in index starting_index
def reallocation_cycle(line , numElements , starting_index):
    index = starting_index
    while (numElements > 0):
        if (index >= len(line)):  # Rotate if needed
            index = 0
        line[index] += 1
        index += 1
        numElements -= 1


try:
    file = open("Input_Day6" , "r")
except IOError:
    print("*** ERROR: Could not open file for reading input ***")
    raise SystemExit

# Parse input
input = file.readline()
input = re.findall("\d+",input)
line = [int(x) for x in input]

different_configurations = { tuple(line) }
cycles_counter = 0
index = None
size = None
value = None

while True:
    # Find the higher value and do the reallocation
    index = find_greater(line)
    value = line[index]
    line[index] = 0
    reallocation_cycle(line, value , index+1)

    # Another cycle has passed
    cycles_counter += 1

    # Add the configuration to the set : If the element is not added, it is because it is already in the set! Break in that case
    size = len(different_configurations)
    different_configurations.add(tuple(line))
    if (size == len(different_configurations)):
        break

print(cycles_counter)
