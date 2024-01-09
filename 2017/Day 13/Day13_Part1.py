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

def getSeverity(caughtLayers):
    severity = 0
    for layer in caughtLayers:
        severity += layer.depth * layer.layerRange
    return severity


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

    caughtLayers = []
    layerIndex = None

    for picosecond in range(1, numLayers+1):
        layerIndex = findLayer(picosecond , layers)

        if (layerIndex == -1):
            continue

        if ( picosecond%((layers[layerIndex].layerRange - 1) * 2) == 0):
            caughtLayers.append(layers[layerIndex])

    severity = getSeverity(caughtLayers)
    print("Severity: " , severity)




main()
