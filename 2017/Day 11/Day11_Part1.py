#!/usr/bin/python3
import re

def main():
    try:
        file = open("Input_Day11" , "r")
    except IOError:
        print("*** ERROR: Could not open file for reading input ***")
        raise SystemExit

    input_line = file.readline()

    directions = re.findall("\w+" , input_line)

    s = 0
    n = 0
    se = 0
    sw = 0
    ne = 0
    nw = 0


    for direction in directions:
        if (direction == "s"):
            s += 1
        elif (direction == "n"):
            n += 1
        elif (direction == "se"):
            se += 1
        elif (direction == "sw"):
            sw += 1
        elif (direction == "ne"):
            ne += 1
        elif (direction == "nw"):
            nw += 1


    if (ne > sw):
        ne -= sw
        sw = 0
    else:
        sw -= ne
        ne = 0

    if (nw > se):
        nw -= se
        se = 0
    else:
        se -= nw
        nw = 0


    if (ne>0 and nw>0):
        minimum = min([ne,nw])
        n += minimum
        ne -= minimum
        nw -= minimum
    elif (se>0 and sw>0):
        minimum = min([se,sw])
        s += minimum
        se -= minimum
        sw -= minimum

    if (n > s):
        n -= s;
        s = 0;
    else:
        s -= n;
        n = 0;

    if (n>0 and sw>0):
        if (n>sw):
            nw += sw
            n -= sw
            sw = 0
        else:
            nw += n
            sw -= n
            n = 0

    if (n>0 and se>0):
        if (n>se):
            ne += se
            n -= se
            se = 0
        else:
            ne += n
            se -= n
            n = 0

    if (s>0 and nw>0):
        if (s>nw):
            sw += nw
            s -= nw
            nw = 0
        else:
            sw += s
            nw -= s
            s = 0

    if (s>0 and ne>0):
        if (s>ne):
            se += ne
            s -= ne
            ne = 0
        else:
            se += s
            ne -= s
            s = 0


    print("Distance: " , n + s + ne + se + nw + sw)


main()
