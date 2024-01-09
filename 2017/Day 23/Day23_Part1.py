#!/usr/bin/python3
import re

program_counter = 0
a = 0
b = 0
c = 0
d = 0
e = 0
f = 0
g = 0
h = 0

def set_register_val(register , val):
    global a , b , c , d , e , f , g , h
    if (register == 'a'):
        a = val
    elif (register == 'b'):
        b = val
    elif (register == 'c'):
        c = val
    elif (register == 'd'):
        d = val
    elif (register == 'e'):
        e = val
    elif (register == 'f'):
        f = val
    elif (register == 'g'):
        g = val
    elif (register == 'h'):
        h = val

def get_register_val(register):
    if (register == 'a'):
        return a
    elif (register == 'b'):
        return b
    elif (register == 'c'):
        return c
    elif (register == 'd'):
        return d
    elif (register == 'e'):
        return e
    elif (register == 'f'):
        return f
    elif (register == 'g'):
        return g
    elif (register == 'h'):
        return h

def is_register(operand):
    return ( (operand >= 'a')  and  (operand <= 'h') )

def set(instruction):
    if (not (is_register(instruction[4]))):
        return

    val = None
    if (is_register(instruction[6])):
        val = get_register_val(instruction[6])
    else:
        val = int(re.findall("-?\d+" , instruction)[0])

    set_register_val(instruction[4] , val)

def sub(instruction):
    if (not (is_register(instruction[4]))):
        return

    val = None
    if (is_register(instruction[6])):
        val = get_register_val(instruction[6])
    else:
        val = int(re.findall("-?\d+" , instruction)[0])

    set_register_val(instruction[4] , get_register_val(instruction[4]) - val)

def mul(instruction):
    if (not (is_register(instruction[4]))):
        return

    val = None
    if (is_register(instruction[6])):
        val = get_register_val(instruction[6])
    else:
        val = int(re.findall("-?\d+" , instruction)[0])

    set_register_val(instruction[4] , get_register_val(instruction[4]) * val)

def jnz(instruction):
    global program_counter

    nums = re.findall("-?\d+" , instruction)

    if (is_register(instruction[4])):
        if (get_register_val(instruction[4]) == 0):
            return
    else:
        if (int(nums[0]) == 0):
            return

    if (is_register(instruction[6])):
        program_counter += get_register_val(instruction[6]) - 1
    else:
        if (len(nums) == 1):
            program_counter += int(nums[0]) - 1
        else:
            program_counter += int(nums[1]) - 1


def main():
    global program_counter

    try:
        file = open("Input_Day23" , "r")
    except IOError:
        print("*** ERROR: Could not open file for reading input ***")
        raise SystemExit

    instructions = file.readlines()
    mulCounter = 0

    while True:
        if (instructions[program_counter][0:3] == "set"):
            set(instructions[program_counter])
        elif (instructions[program_counter][0:3] == "sub"):
            sub(instructions[program_counter])
        elif (instructions[program_counter][0:3] == "mul"):
            mul(instructions[program_counter])
            mulCounter += 1
        elif (instructions[program_counter][0:3] == "jnz"):
            jnz(instructions[program_counter])

        program_counter += 1

        if (program_counter >= len(instructions)):
            break

    print(mulCounter)

main()
