#!/usr/bin/python3

cleanNode = '.'
infectedNode = '#'

def turn_right(current_dir):
    return ((current_dir+1)%4)

def turn_left(current_dir):
    if(current_dir==0):
        return 3
    else:
        return (current_dir-1)

def is_infected(node_id):
    return (node_id == infectedNode)

def is_clean(node_id):
    return (node_id == cleanNode)


def main():
    try:
        file = open("Input_Day22" , "r")
    except IOError:
        print("*** ERROR: Could not open file for reading input ***")
        raise SystemExit



    raw_input = file.readlines()
    map = []
    map_row = None

    num_bursts = 10000
    infection_counter = 0
    matrix_size = 501
    matrix = [ [ cleanNode for y in range( matrix_size ) ] for x in range( matrix_size ) ]

    for line in raw_input:
        line = line[:-1]
        map_row = []
        for c in line:
            map_row.append(c)
        map.append(map_row)

    x = len(map[0])//2
    y = len(map)//2
    current_dir = 0

    for i in range(len(map[0])):
        for j in range(len(map)):
            matrix[matrix_size//2-y+j][matrix_size//2-x+i] = map[j][i]

    x = len(matrix[0])//2
    y = len(matrix)//2

    for i in range(num_bursts):
        if (is_infected(matrix[y][x])):
            current_dir = turn_right(current_dir)
            matrix[y][x] = cleanNode
        else:
            infection_counter += 1
            current_dir = turn_left(current_dir)
            matrix[y][x] = infectedNode

        if (current_dir == 0):
            y -= 1
        elif (current_dir == 1):
            x += 1
        elif (current_dir == 2):
            y += 1
        else:
            x -= 1

    print(infection_counter)



main()
