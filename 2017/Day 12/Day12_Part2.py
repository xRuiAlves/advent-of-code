#!/usr/bin/python3
import re

programs = []
groups = []

class Program:
    def __init__(self , id_num ,connections):
        self.id_num = id_num
        self.connections = connections

def addGroupToGroups(current_group):
    if (not (groupAlreadyExists(current_group))):
        groups.append(current_group)

def groupAlreadyExists(current_group):
    for g in groups:
        if (current_group == g):
            return True
    # Not found!
    return False

def programIsInGroup(id_num, group):
    for prog_num in group:
        if (prog_num == id_num):
            return True
    # Not found!
    return False

def addProgramToGroup(id_num,group):
    if (not(programIsInGroup(id_num,group))):
        group.append(id_num)

        for prog_num in programs[id_num].connections:
            addProgramToGroup(prog_num,group)


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

    for i in range(0 , len(programs)):
        group = []
        addProgramToGroup(i,group)
        group = sorted(group)
        addGroupToGroups(group)

    print("Number of Groups: " , len(groups))

main()
