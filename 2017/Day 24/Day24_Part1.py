#!/usr/bin/python3
import re

def getStrength(component_list):
    strength = 0
    for component in component_list:
        strength += component[0] + component[1]
    return strength


def getKey(component):
    return component[0]


def getMaxStrength(available_components , used_components):
    print(used_components)
    # Base Case 1
    if (len(available_components) == 0):
        return getStrength(used_components)

    # Reduction Case
    lastPieceUsed = used_components[-1][1]
    strengths = []
    for i in range(len(available_components)):
        if (available_components[i][0] == lastPieceUsed):
            strengths.append(getMaxStrength(
                available_components[0:i] + available_components[i+1:],
                used_components + available_components[i:i+1]
            ))


        # Check for the "reversed" component , if the component isn't symetric
        if (available_components[i][0] != available_components[i][1]):
            if (available_components[i][1] == lastPieceUsed):
                available_components[i] = (available_components[i][1] , available_components[i][0])
                strengths.append(getMaxStrength(
                    available_components[0:i] + available_components[i+1:],
                    used_components + available_components[i:i+1]
                ))

    if (len(strengths) == 0):
        # Base Case 2
        return getStrength(used_components)
    else:
        # Base Case 3
        return max(strengths)


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
