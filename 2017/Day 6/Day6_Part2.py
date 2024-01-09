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

# Adds a tupple to a list, if it does not exist already. If it exists, returns the index. Otherwise, returns -1.
def insert_tup(different_configurations , configuration):
    for i in range(0 , len(different_configurations)):
        if (different_configurations[i] == configuration):
            return i
    different_configurations.append(configuration)
    return -1


try:
    file = open("Input_Day6" , "r")
except IOError:
    print("*** ERROR: Could not open file for reading input ***")
    raise SystemExit

# Parse input
input = file.readline()
input = re.findall("\d+",input)
line = [int(x) for x in input]

different_configurations = [ tuple(input) ]
cycles_counter = 0
index = None
value = None
add_result = None

while True:
    # Find the higher value and do the reallocation
    index = find_greater(line)
    value = line[index]
    line[index] = 0
    reallocation_cycle(line, value , index+1)

    # Another cycle has passed
    cycles_counter += 1

    # Add the configuration to the list
    add_result = insert_tup(different_configurations , tuple(line))
    if (add_result != -1):
        break


print(len(different_configurations) - add_result)
