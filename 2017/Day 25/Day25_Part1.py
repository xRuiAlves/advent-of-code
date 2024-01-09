#!/usr/bin/python3
import re

def getStateIndex(stateId):
    if (stateId == 'A'):
        return 0
    elif (stateId == 'B'):
        return 1
    elif (stateId == 'C'):
        return 2
    elif (stateId == 'D'):
        return 3
    elif (stateId == 'E'):
        return 4
    elif (stateId == 'F'):
        return 5

def direction(direct):
    if (direct == 'r'):
        return 1
    else:
        return -1

def initializeStates(raw_input):
    states = []
    for i in range(6):
        valueIfZero = int(raw_input[10*i + 5][22])
        moveIfZero = raw_input[10*i + 6][27]
        stateIfZero = raw_input[10*i + 7][26]
        valueIfOne = int(raw_input[10*i + 9][22])
        moveIfOne = raw_input[10*i + 10][27]
        stateIfOne = raw_input[10*i + 11][26]
        states.append( ( (valueIfZero,moveIfZero,stateIfZero) , (valueIfOne,moveIfOne,stateIfOne)) )
    return states

def checksum(tape):
    count = 0
    for elem in tape:
        count += elem
    return count

def main():
    try:
        file = open("Input_Day25" , "r")
    except IOError:
        print("*** ERROR: Could not open file for reading input ***")
        raise SystemExit

    raw_input = file.readlines()
    num_steps = int(re.findall("\d+" , raw_input[1])[0])
    states = initializeStates(raw_input)
    tape = [0 for i in range(10000)]
    tapePointer = len(tape)//2
    currentState = 'A'
    stateIndex = 0

    for i in range(num_steps):
        stateIndex = getStateIndex(currentState)
        if (tape[tapePointer] == 0):
            tape[tapePointer] = states[stateIndex][0][0]
            tapePointer += direction(states[stateIndex][0][1])
            currentState = states[stateIndex][0][2]
        else:
            tape[tapePointer] = states[stateIndex][1][0]
            tapePointer += direction(states[stateIndex][1][1])
            currentState = states[stateIndex][1][2]

    print(checksum(tape))

main()
