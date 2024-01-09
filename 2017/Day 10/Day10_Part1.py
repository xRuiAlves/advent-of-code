#!/usr/bin/python3
import re

def perform_run(my_list , lengths_list):
    list_size = len(my_list)
    skip_size = 0
    current_pos = 0
    beggining_index = None
    ending_index = None

    for length in lengths_list:
        beggining_index = current_pos
        ending_index = (current_pos + length - 1)%list_size

        for i in range(0 , length//2):
            # swap values
            my_list[beggining_index] = my_list[beggining_index] ^ my_list[ending_index]
            my_list[ending_index] = my_list[ending_index] ^ my_list[beggining_index]
            my_list[beggining_index] = my_list[beggining_index] ^ my_list[ending_index]

            beggining_index = (beggining_index + 1)%list_size
            ending_index -= 1
            if (ending_index < 0):
                ending_index = list_size-1

        current_pos = (current_pos + length + skip_size)%list_size
        skip_size += 1
    return (my_list[0]*my_list[1])

def main():
    try:
        file = open("Input_Day10" , "r")
    except IOError:
        print("*** ERROR: Could not open file for reading input ***")
        raise SystemExit

    raw_input = file.readline()
    lengths = list(map(int , re.findall("\d+" , raw_input)))
    list_size = 256
    my_list = [x for x in range(0,list_size)]

    answer = perform_run(my_list , lengths)
    print(answer)


main()
