#!/usr/bin/python3
import re

class Register:
    def __init__(self , identificator , val):
        self.identificator = identificator
        self.val = val

registers = []
last_played_sound_freq = None
rcv_triggered = False
program_counter = 0

def is_register(r):
    return ((r >= "a") and (r <= "z"))

def get_register_index(reg_identificator):
    for i in range(0 , len(registers)):
        if (registers[i].identificator == reg_identificator):
            return i
    return -1

def create_register(reg_identificator):
    index = get_register_index(reg_identificator)
    if (index == -1):
        registers.append(Register(reg_identificator , 0))

def get_reg_val(reg_identificator):
    index = get_register_index(reg_identificator)
    return registers[index].val

def instruction_set(instruction):
    if (not (is_register(instruction[4]))):
        return

    reg1_index = get_register_index(instruction[4])

    if (is_register(instruction[6])):
        registers[reg1_index].val = get_reg_val(instruction[6])
    else:
        registers[reg1_index].val = int(re.findall("-?\d+" , instruction)[0])

def instruction_add(instruction):
    if (not (is_register(instruction[4]))):
        return

    reg1_index = get_register_index(instruction[4])

    if (is_register(instruction[6])):
        registers[reg1_index].val += get_reg_val(instruction[6])
    else:
        registers[reg1_index].val += int(re.findall("-?\d+" , instruction)[0])

def instruction_mul(instruction):
    if (not (is_register(instruction[4]))):
        return

    reg1_index = get_register_index(instruction[4])

    if (is_register(instruction[6])):
        registers[reg1_index].val *= get_reg_val(instruction[6])
    else:
        registers[reg1_index].val *= int(re.findall("-?\d+" , instruction)[0])

def instruction_mod(instruction):
    if (not (is_register(instruction[4]))):
        return

    reg1_index = get_register_index(instruction[4])

    if (is_register(instruction[6])):
        registers[reg1_index].val %= get_reg_val(instruction[6])
    else:
        registers[reg1_index].val %= int(re.findall("-?\d+" , instruction)[0])

def instruction_jgz(instruction):
    global program_counter
    if (is_register(instruction[4])):
        if (get_reg_val(instruction[4]) > 0):
            if (is_register(instruction[6])):
                program_counter += get_reg_val(instruction[6])-1
            else:
                program_counter += int(re.findall("-?\d+" , instruction)[0])-1
    else:
        if (int(re.findall("-?\d+" , instruction)[0]) > 0):
            if (is_register(instruction[6])):
                program_counter += get_reg_val(instruction[6])-1
            else:
                program_counter += int(re.findall("-?\d+" , instruction)[0])-1

def instruction_snd(instruction):
    global last_played_sound_freq
    if (not (is_register(instruction[4]))):
        return
    else:
        last_played_sound_freq = get_reg_val(instruction[4])

def instruction_rcv(instruction):
    global rcv_triggered
    if (not (is_register(instruction[4]))):
        return
    elif (get_reg_val(instruction[4]) != 0):
        rcv_triggered = True

def execute_instruction(instruction):
    if (instruction[0] == "j"):
        instruction_jgz(instruction)
    elif (instruction[0] == "r"):
        instruction_rcv(instruction)
    elif (instruction[0] == "a"):
        instruction_add(instruction)
    elif (instruction[0]+instruction[1] == "sn"):
        instruction_snd(instruction)
    elif (instruction[0]+instruction[1] == "se"):
        instruction_set(instruction)
    elif (instruction[0]+instruction[1] == "mu"):
        instruction_mul(instruction)
    elif (instruction[0]+instruction[1] == "mo"):
        instruction_mod(instruction)


def main():
    global program_counter
    try:
        file = open("Input_Day18" , "r")
    except IOError:
        print("*** ERROR: Could not open file for reading input ***")
        raise SystemExit

    raw_input = file.readlines()
    parsed_line = None
    instructions = []
    instruction = None

    for line in raw_input:
        instructions.append(line[:len(line)-1])

        parsed_line = re.findall("( [a-z] | [a-z]\n)" , line)
        if (len(parsed_line) != 0):
            create_register(parsed_line[0][1])

    while(not (rcv_triggered)):
        execute_instruction(instructions[program_counter])
        program_counter += 1

    print(last_played_sound_freq)





main()
