#!/usr/bin/python3
import re

class Layer:
    def __init__(self , depth , layerRange):
        self.depth = depth
        self.layerRange = layerRange

def findLayer(layerDepth , layers):
    for i in range(0,len(layers)):
        if (layers[i].depth >= layerDepth):
            if (layers[i].depth == layerDepth):
                return i
            else:
                return -1
    return -1

def main():
    try:
        file = open("Input_Day13" , "r")
    except IOError:
        print("*** ERROR: Could not open file for reading input ***")
        raise SystemExit

    file_input = file.readlines()

    layers = []

    for line in file_input:
        line = re.findall("\d+" , line)
        line = list(map(int,line))
        layers.append(Layer(line[0] , line[1]))

    numLayers = layers[-1].depth

    layerIndex = None
    startingPicosecond = 0
    caught = True

    while(caught):
        caught = False;
        for picosecond in range(startingPicosecond, startingPicosecond+numLayers+1):
            if (picosecond == 0):
                continue

            layerIndex = findLayer(picosecond-startingPicosecond , layers)

            if (layerIndex == -1):
                continue

            if ( picosecond%((layers[layerIndex].layerRange - 1) * 2) == 0):
                caught = True
                startingPicosecond += 1
                break

    print("Delayed Seconds: " , startingPicosecond)


main()
