#!/usr/bin/python3
import re

def getStrength(component_list):
    strength = 0
    for component in component_list:
        strength += component[0] + component[1]
    return strength


def getKey(component):
    return component[0]


def getBestOutput(outputs):
    index = 0
    maxLength = outputs[0][0]
    maxStrength = outputs[0][1]

    for i in range(1,len(outputs)):
        if (outputs[i][0] >= maxLength):
            if (outputs[i][0] == maxLength):
                if (outputs[i][1] > maxStrength):
                    index = i
                    maxStrength = outputs[i][1]
                    maxLength = outputs[i][0]
            else:
                index = i
                maxStrength = outputs[i][1]
                maxLength = outputs[i][0]

    return outputs[index]


def getMaxStrength(available_components , used_components):
    print(used_components)
    # Base Case 1
    if (len(available_components) == 0):
        return (len(used_components) , getStrength(used_components))

    # Reduction Case
    lastPieceUsed = used_components[-1][1]
    outputs = []
    for i in range(len(available_components)):
        if (available_components[i][0] == lastPieceUsed):
            outputs.append(getMaxStrength(
                available_components[0:i] + available_components[i+1:],
                used_components + available_components[i:i+1]
            ))
        # Check for the "reversed" component , if the component isn't symetric
        if (available_components[i][0] != available_components[i][1]):
            if (available_components[i][1] == lastPieceUsed):
                available_components[i] = (available_components[i][1] , available_components[i][0])
                outputs.append(getMaxStrength(
                    available_components[0:i] + available_components[i+1:],
                    used_components + available_components[i:i+1]
                ))

    if (len(outputs) == 0):
        # Base Case 2
        return (len(used_components) , getStrength(used_components))
    else:
        # Base Case 3
        return getBestOutput(outputs)


def main():
    global program_counter

    try:
        file = open("Input_Day24" , "r")
    except IOError:
        print("*** ERROR: Could not open file for reading input ***")
        raise SystemExit

    raw_input = file.readlines()
    nums = None
    components = []


    for line in raw_input:
        nums = re.findall("\d+" , line)
        components.append( (int(nums[0]) , int(nums[1])) )

    components.sort(key=getKey)

    print(getMaxStrength(components , [(0,0)]))


main()
