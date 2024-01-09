#!/usr/bin/python3
import re

programs = []
group = []

class Program:
    def __init__(self , id_num ,connections):
        self.id_num = id_num
        self.connections = connections

def programIsInGroup(id_num):
    for prog_num in group:
        if (prog_num == id_num):
            return True
    # Not found!
    return False

def addProgramToGroup(id_num):
    if (not(programIsInGroup(id_num))):
        group.append(id_num)

        for prog_num in programs[id_num].connections:
            addProgramToGroup(prog_num)


def main():
    try:
        file = open("Input_Day12" , "r")
    except IOError:
        print("*** ERROR: Could not open file for reading input ***")
        raise SystemExit

    file_input = file.readlines()
    input_line = None

    for line in file_input:
        input_line = re.findall("\d+" , line)
        input_line = list(map(int,input_line))
        programs.append(Program(input_line[0] , input_line[1:]))

    initialProgramID = 0;
    addProgramToGroup(initialProgramID)

    print("Group size: " , len(group))

main()
