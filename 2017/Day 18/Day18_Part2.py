#!/usr/bin/python3
import re

class Register:
    def __init__(self , identificator , val):
        self.identificator = identificator
        self.val = val

registers_p0 = []
registers_p1 = []
program0_queue = []
program1_queue = []
program0_counter = 0
program1_counter = 0
running_program = 0
program0_halted = False
program1_halted = False
answer = 0

def is_register(r):
    return ((r >= "a") and (r <= "z"))

def get_register_index(reg_identificator):
    for i in range(0 , len(registers_p0)):
        if (registers_p0[i].identificator == reg_identificator):
            return i
    return -1

def create_register(reg_identificator):
    index = get_register_index(reg_identificator)
    if (index == -1):
        registers_p0.append(Register(reg_identificator , 0))

def get_reg_val(reg_identificator):
    index = get_register_index(reg_identificator)

    if (running_program == 0):
        return registers_p0[index].val
    else:
        return registers_p1[index].val

def instruction_set(instruction):
    if (not (is_register(instruction[4]))):
        return

    reg1_index = get_register_index(instruction[4])

    if (running_program == 0):
        if (is_register(instruction[6])):
            registers_p0[reg1_index].val = get_reg_val(instruction[6])
        else:
            registers_p0[reg1_index].val = int(re.findall("-?\d+" , instruction)[0])
    else:
        if (is_register(instruction[6])):
            registers_p1[reg1_index].val = get_reg_val(instruction[6])
        else:
            registers_p1[reg1_index].val = int(re.findall("-?\d+" , instruction)[0])

def instruction_add(instruction):
    if (not (is_register(instruction[4]))):
        return

    reg1_index = get_register_index(instruction[4])

    if (running_program == 0):
        if (is_register(instruction[6])):
            registers_p0[reg1_index].val += get_reg_val(instruction[6])
        else:
            registers_p0[reg1_index].val += int(re.findall("-?\d+" , instruction)[0])
    else:
        if (is_register(instruction[6])):
            registers_p1[reg1_index].val += get_reg_val(instruction[6])
        else:
            registers_p1[reg1_index].val += int(re.findall("-?\d+" , instruction)[0])

def instruction_mul(instruction):
    if (not (is_register(instruction[4]))):
        return

    reg1_index = get_register_index(instruction[4])

    if (running_program == 0):
        if (is_register(instruction[6])):
            registers_p0[reg1_index].val *= get_reg_val(instruction[6])
        else:
            registers_p0[reg1_index].val *= int(re.findall("-?\d+" , instruction)[0])
    else:
        if (is_register(instruction[6])):
            registers_p1[reg1_index].val *= get_reg_val(instruction[6])
        else:
            registers_p1[reg1_index].val *= int(re.findall("-?\d+" , instruction)[0])

def instruction_mod(instruction):
    if (not (is_register(instruction[4]))):
        return

    reg1_index = get_register_index(instruction[4])

    if (running_program == 0):
        if (is_register(instruction[6])):
            registers_p0[reg1_index].val %= get_reg_val(instruction[6])
        else:
            registers_p0[reg1_index].val %= int(re.findall("-?\d+" , instruction)[0])
    else:
        if (is_register(instruction[6])):
            registers_p1[reg1_index].val %= get_reg_val(instruction[6])
        else:
            registers_p1[reg1_index].val %= int(re.findall("-?\d+" , instruction)[0])

def instruction_jgz(instruction):
    global program0_counter
    global program1_counter

    if (is_register(instruction[4])):
        if (get_reg_val(instruction[4]) > 0):
            if (is_register(instruction[6])):
                if (running_program == 0):
                    program0_counter += get_reg_val(instruction[6])-1
                else:
                    program1_counter += get_reg_val(instruction[6])-1
            else:
                if (running_program == 0):
                    program0_counter += int(re.findall("-?\d+" , instruction)[0])-1
                else:
                    program1_counter += int(re.findall("-?\d+" , instruction)[0])-1
    else:
        nums = re.findall("-?\d+" , instruction)
        if (int(nums[0]) > 0):
            if (is_register(instruction[6])):
                if (running_program == 0):
                    program0_counter += get_reg_val(instruction[6])-1
                else:
                    program1_counter += get_reg_val(instruction[6])-1
            else:
                if (running_program == 0):
                    program0_counter += int(nums[1])-1
                else:
                    program1_counter += int(nums[1])-1

def instruction_snd(instruction):
    global program0_queue
    global program1_queue
    global program0_halted
    global program1_halted
    global answer
    if (not (is_register(instruction[4]))):
        if (running_program == 0):
            program1_queue.append(int(re.findall("-?\d+" , instruction)[0]))
            program1_halted = False
        else:
            answer += 1
            program0_queue.append(int(re.findall("-?\d+" , instruction)[0]))
            program0_halted = False
    else:
        if (running_program == 0):
            program1_queue.append(get_reg_val(instruction[4]))
            program1_halted = False
        else:
            answer += 1
            program0_queue.append(get_reg_val(instruction[4]))
            program0_halted = False

def instruction_rcv(instruction):
    global program0_halted
    global program1_halted
    global program0_queue
    global program1_queue

    if (not (is_register(instruction[4]))):
        return

    reg_index = get_register_index(instruction[4])

    if (running_program == 0):
        if (len(program0_queue) == 0):
            program0_halted = True
        else:
            registers_p0[reg_index].val = program0_queue[0]
            program0_queue = program0_queue[1:]
    else:
        if (len(program1_queue) == 0):
            program1_halted = True
        else:
            registers_p1[reg_index].val = program1_queue[0]
            program1_queue = program1_queue[1:]

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
    global program0_counter
    global program1_counter
    global registers_p1
    global running_program

    try:
        file = open("Input_Day18" , "r")
    except IOError:
        print("*** ERROR: Could not open file for reading input ***")
        raise SystemExit

    raw_input = file.readlines()
    parsed_line = None
    instructions = []
    instruction = None
    program0_ended = False
    program1_ended = False

    for line in raw_input:
        instructions.append(line[:len(line)-1])

        parsed_line = re.findall("( [a-z] | [a-z]\n)" , line)
        if (len(parsed_line) != 0):
            create_register(parsed_line[0][1])

    for r in registers_p0:
        if (r.identificator != "p"):
            registers_p1.append(Register(r.identificator , 0))
        else:
            registers_p1.append(Register(r.identificator , 1))


    while(True):
        if (running_program == 0 and not program0_ended):
            execute_instruction(instructions[program0_counter])
            if (program0_halted):
                running_program = 1
            else:
                program0_counter += 1
            running_program = 1
        elif (running_program == 1 and not program1_ended):
            execute_instruction(instructions[program1_counter])
            if (program1_halted):
                running_program = 0
            else:
                program1_counter += 1
            running_program = 0

        if (program0_counter >= len(instructions) or program0_counter < 0):
            program0_ended = True
        if (program1_counter >= len(instructions) or program1_counter < 0):
            program1_ended = True

        if ((program0_ended or program0_halted) and (program1_ended or program1_halted)):
            break

    print(answer)



main()
