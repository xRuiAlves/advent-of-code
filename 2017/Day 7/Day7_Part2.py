#!/usr/bin/python3
import re

# Find the single weight of a specified program (the program's weight itself, without accounting the children's weight)
def find_single_weight(list_of_programs , program_name):
    for line in list_of_programs:
        if (line[0] == program_name):
            return line[1]

# Find the total weight of a specified program (accounting children's weight)
def find_total_weigth(list_of_programs , program_name):
    for line in list_of_programs:
        if (line[0] == program_name):
            if (len(line) == 2):
                return line[1]
            else:
                line_sum = line[1]
                for i in range(2, len(line)):
                    line_sum += find_total_weigth(list_of_programs , line[i])
                return line_sum

# Checks if there are different weights in the list
def check_different_weights(weights_list):
    for i in range(0 , len(weights_list) - 1):
        if (weights_list[i] != weights_list[i+1]):
            return False
    return True


try:
    file = open("Input_Day7" , "r")
except IOError:
    print("*** ERROR: Could not open file for reading input ***")
    raise SystemExit

raw_input = file.readlines()
file.close()
weights = []

# Parse the input
for i in range(0, len(raw_input)):
    raw_input[i] = re.findall("[a-z0-9]+" , raw_input[i])
    raw_input[i][1] = int(raw_input[i][1])

# Find the proram who's children have different sizes:
for line in raw_input:
    if (len(line) == 2):    # Leafs can't be unbalenced!
        continue
    weights.clear()

    # Get the weights of the children programs
    for i in range(2 , len(line)):
        weights.append(find_total_weigth(raw_input , line[i]))

    # Check if the current program has different weights!
    if(not(check_different_weights(weights))):
        print("\n---------- Program" , line[0] , "----------")
        print("Subprograms:")
        for i in range(2, len(line)):
            print("Name:" , line[i] , "\tTotal weight:" , weights[i-2] , "\tSingle weight:" , find_single_weight(raw_input,line[i]))
