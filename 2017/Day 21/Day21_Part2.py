#!/usr/bin/python3
import re
import math

def count_pixels_on(grid):
    on_count = 0

    for row in grid:
        for elem in row:
            if ( elem == '#'):
                on_count += 1
    return on_count

def get_match(grid , matches):
    for match in matches:
        if (match[0] == grid):
            return match[1]
    return ""

def string_to_matrix(string):
    if (len(string) == 11):
        return [ [string[0],string[1],string[2]] , [string[4],string[5],string[6]] , [string[8],string[9],string[10]] ]
    else:
        return [ [string[0],string[1],string[2],string[3]] , [string[5],string[6],string[7],string[8]] , [string[10],string[11],string[12],string[13]] , [string[15],string[16],string[17],string[18]] ]

def find_match(grid , matches):
    m = get_match(grid,matches)     # Rotation no. 1
    if (m != ""):
        return m;

    grid = perform_rotation(grid)
    m = get_match(grid,matches)     # Rotation no. 2
    if (m != ""):
        return m;

    grid = perform_rotation(grid)
    m = get_match(grid,matches)     # Rotation no. 3
    if (m != ""):
        return m;

    grid = perform_rotation(grid)
    m = get_match(grid,matches)     # Rotation no. 4
    if (m != ""):
        return m;

    grid = perform_flip(grid)
    m = get_match(grid,matches)     # Rotation no. 5
    if (m != ""):
        return m;

    grid = perform_rotation(grid)
    m = get_match(grid,matches)     # Rotation no. 6
    if (m != ""):
        return m;

    grid = perform_rotation(grid)
    m = get_match(grid,matches)     # Rotation no. 7
    if (m != ""):
        return m;

    grid = perform_rotation(grid)
    m = get_match(grid,matches)     # Rotation no. 8
    if (m != ""):
        return m;

def perform_rotation(grid):
    if (len(grid) == 5):
        return (grid[3] + grid[0] + "/" + grid[4] + grid[1])
    else:
        return (grid[8] + grid[4] + grid[0] + "/" + grid[9] + grid[5] + grid[1] + "/" + grid[10] + grid[6] + grid[2])

def perform_flip(grid):
    if (len(grid) == 5):
        return (grid[1] + grid[0] + "/" + grid[4] + grid[3])
    else:
        return (grid[2] + grid[1] + grid[0] + "/" + grid[6] + grid[5] + grid[4] + "/" + grid[10] + grid[9] + grid[8])

def perform_iteration(grid , matches):
    n = len(grid)
    block_size = None
    solutions = []

    if (n%2 == 0):
        block_size = 2
    else:
        block_size = 3

    for i in range(n//block_size):
        for j in range(n//block_size):
            if(block_size==2):
                solutions.append(string_to_matrix(find_match(grid[2*j][2*i] + grid[2*j][2*i + 1] + "/" + grid[2*j + 1][2*i] + grid[2*j + 1][2*i + 1] , matches)))
            else:
                solutions.append(string_to_matrix(find_match(grid[3*j][3*i] + grid[3*j][3*i + 1] + grid[3*j][3*i + 2] + "/" +
                                                             grid[3*j + 1][3*i] + grid[3*j + 1][3*i + 1] + grid[3*j + 1][3*i + 2] + "/" +
                                                             grid[3*j + 2][3*i] + grid[3*j + 2][3*i + 1] + grid[3*j + 2][3*i + 2] , matches)))

    num_solutions = len(solutions)
    solution_size = len(solutions[0])
    new_grid_side_size = int(math.sqrt(num_solutions))*solution_size

    new_grid = [ [ None for y in range( new_grid_side_size ) ] for x in range( new_grid_side_size ) ]
    const = int(math.sqrt(num_solutions))

    for i in range(const):
        for j in range(const):
            for x in range(solution_size):
                for y in range(solution_size):
                    new_grid[j*solution_size+y][i*solution_size+x] = solutions[i*const+j][y][x]

    return new_grid


def main():
    try:
        file = open("Input_Day21" , "r")
    except IOError:
        print("*** ERROR: Could not open file for reading input ***")
        raise SystemExit

    raw_input = file.readlines()
    matches = []
    grid = [['.' , '#' , '.'] ,
            ['.' , '.' , '#'] ,
            ['#' , '#' , '#']]
    numIterations = 18

    for line in raw_input:
        if (line[6] == "="):
            matches.append( (line[0:5] , line[9:20]) )
        else:
            matches.append( (line[0:11] , line[15:34]) )


    for i in range(numIterations):
        grid = perform_iteration(grid , matches)
        print(i+1 , ":" , count_pixels_on(grid))


main()
