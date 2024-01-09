#!/usr/bin/python3

V_PATH = "|"
H_PATH = "-"
C_PATH = "+"
NO_PATH = " "

def find_starting_j(path):
    for j in range(0 , len(path[0])):
        if (path[0][j] == V_PATH):
            return j

def main():
    global program_counter
    try:
        file = open("Input_Day19" , "r")
    except IOError:
        print("*** ERROR: Could not open file for reading input ***")
        raise SystemExit

    path = file.readlines()

    i = 0
    j = find_starting_j(path)
    direction = "S"
    num_steps = 0

    while(path[i][j] != NO_PATH):
        if (direction == "N"):
            i -= 1
        elif (direction == "E"):
            j += 1
        elif (direction == "S"):
            i += 1
        elif (direction == "W"):
            j -= 1

        num_steps += 1


        if (path[i][j] == V_PATH or path[i][j] == H_PATH):
            continue
        elif (path[i][j] == C_PATH):
            if (direction == "N" or direction == "S"):
                if (( (j-1) >= 0) and (path[i][j-1] == H_PATH)):
                    direction = "W"
                else:
                    direction = "E"
            else:
                if (( (i-1) >= 0) and (path[i-1][j] == V_PATH)):
                    direction = "N"
                else:
                    direction = "S"
        elif (path[i][j] == NO_PATH):
            break

    print(num_steps)


main()
