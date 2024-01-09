#!/usr/bin/python3
import re

class Register:
    def __init__(self , name="" , value=0):
        self.name = name
        self.value = value

def findRegister(register_list , register_name):
    for i in range(0 , len(register_list)):
        if(register_list[i].name == register_name):
            return i
    register_list.append(Register(register_name , 0))
    return (len(register_list) - 1)

def findLargestValue(register_list):
    max_val = register_list[0].value
    for register in register_list:
        if (register.value > max_val):
            max_val = register.value
    return max_val


def main():
    try:
        file = open("Input_Day8" , "r")
    except IOError:
        print("*** ERROR: Could not open file for reading input ***")
        raise SystemExit

    raw_input = file.readlines()

    register_list = []
    type_of_instruction = None
    operand_reg = None
    delta = None
    compare_reg = None
    compare_val = None
    operation = None
    operand_reg_index = None
    compare_reg_index = None

    for line in raw_input:
        # Parse line
        type_of_instruction = "inc" if (line.find("inc") != -1) else "dec"
        operand_reg = re.search("\w+",line).group()
        delta = int(re.search("-?\d+",line).group())
        compare_reg = re.findall("[a-zA-Z]+",line)[-1]
        compare_val = int(re.findall("-?\d+",line)[-1])
        operation = re.search(">=|<=|<|>|==|!=", line).group()

        operand_reg_index = findRegister(register_list , operand_reg)
        compare_reg_index = findRegister(register_list , compare_reg)

        if (operation == ">"):
            if (register_list[compare_reg_index].value > compare_val):
                register_list[operand_reg_index].value += delta if (type_of_instruction=="inc") else (-delta)
        elif (operation == "<"):
            if (register_list[compare_reg_index].value < compare_val):
                register_list[operand_reg_index].value += delta if (type_of_instruction=="inc") else (-delta)
        elif (operation == "=="):
            if (register_list[compare_reg_index].value == compare_val):
                register_list[operand_reg_index].value += delta if (type_of_instruction=="inc") else (-delta)
        elif (operation == "!="):
            if (register_list[compare_reg_index].value != compare_val):
                register_list[operand_reg_index].value += delta if (type_of_instruction=="inc") else (-delta)
        elif (operation == ">="):
            if (register_list[compare_reg_index].value >= compare_val):
                register_list[operand_reg_index].value += delta if (type_of_instruction=="inc") else (-delta)
        elif (operation == "<="):
            if (register_list[compare_reg_index].value <= compare_val):
                register_list[operand_reg_index].value += delta if (type_of_instruction=="inc") else (-delta)

    print(findLargestValue(register_list))


main()
