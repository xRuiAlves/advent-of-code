#!/usr/bin/python3
import re

def spin(programs , n):
    for i in range(0 , n):
        programs = programs[15] + programs[:15]
    return programs

def exchange(programs, i , j):
    string_l = []
    for p in programs:
        string_l.append(p)
    temp = string_l[j]
    string_l[j] = string_l[i]
    string_l[i] = temp
    programs = ""
    for p in string_l:
        programs += p
    return programs

def partener(programs , p1 , p2):
    index1 = get_program_index(programs, p1)
    index2 = get_program_index(programs, p2)
    return exchange(programs , index1 , index2)

def get_program_index(programs , program):
    for i in range(0 , len(programs)):
        if (programs[i] == program):
            return i
    return -1

def parse_dance_moves(line):
    dance_moves = []
    dance_move = ""
    for char in line:
        if (char=="," or char=="\n"):
            dance_moves.append(dance_move)
            dance_move = ""
        else:
            dance_move += char
    return dance_moves

def main():
    try:
        file = open("Input_Day16" , "r")
    except IOError:
        print("*** ERROR: Could not open file for reading input ***")
        raise SystemExit

    input_line = file.readline()
    programs = "abcdefghijklmnop"

    dance_moves = parse_dance_moves(input_line)
    num_dances = 1000000000%60  # Pattern repeats each 60 "dances"

    n = None
    n1 = None
    n2 = None

    for i in range(0 , num_dances):
        for d in dance_moves:
            n = re.findall("\d+",d)
            if (d[0] == "s"):
                n1 = int(n[0])
                programs = spin(programs , n1)
            elif (d[0] == "x"):
                n1 = int(n[0])
                n2 = int(n[1])
                programs = exchange(programs , n1 , n2)
            else:
                programs = partener(programs , d[1] , d[3])

    print("Programs: " , programs)



main()
