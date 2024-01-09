try:
    file = open("Input_Day9" , "r")
except IOError:
    print("*** ERROR: Could not open file for reading input ***")
    raise SystemExit

input_line = file.readline()
index = 0
garbage_counter = 0

# "Empty the garbage"
while (input_line[index] != "\n"):
    if (input_line[index] == "<"):
        input_line = input_line[:index] + input_line[index+1:]
        while (input_line[index] != ">"):
            if (input_line[index] == "!"):
                input_line = input_line[:index] + input_line[index+2:]
            else:
                garbage_counter += 1
                input_line = input_line[:index] + input_line[index+1:]
        input_line = input_line[:index] + input_line[index+1:]
    else:
        index += 1

print(garbage_counter)
