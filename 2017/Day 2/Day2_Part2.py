import re

# Find the numbers which evenly divide
def findValues(line):
    for i in range(0,len(line)):
        for j in range(0,len(line)):
            if ((i != j) and (line[i] % line[j] == 0)):
                return (line[i] // line[j])

try:
    file = open("Input_Day2" , "r")
except IOError:
    print("*** ERROR: Could not open file for reading input ***")
    raise SystemExit

# Get the file content into an array
input_lines = file.readlines()

# Parse the strings into numbers only, and get the checksum
checksum = 0
for line in input_lines:
    line = re.findall("\d+",line)        # Make all the elements independant numbers
    line = [int(elem) for elem in line]  # Cast all elements into int
    checksum += findValues(line)

print(checksum)
