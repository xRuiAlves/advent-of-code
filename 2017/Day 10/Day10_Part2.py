#!/usr/bin/python3
import re

def append_given_lengths(lengths_list):
    lengths_list.append(17)
    lengths_list.append(31)
    lengths_list.append(73)
    lengths_list.append(47)
    lengths_list.append(23)

def get_dense_hash(my_list):
    dense_hash = []
    for i in range(0, 16):
        dense_hash.append( my_list[i*16+0] ^ my_list[i*16+1] ^ my_list[i*16+2] ^ my_list[i*16+3] ^ my_list[i*16+4] ^ my_list[i*16+5] ^ my_list[i*16+6] ^ my_list[i*16+7] ^ my_list[i*16+8] ^ my_list[i*16+9] ^ my_list[i*16+10] ^ my_list[i*16+11] ^ my_list[i*16+12] ^ my_list[i*16+13] ^ my_list[i*16+14] ^ my_list[i*16+15])
    return dense_hash

def knot_hash(string_to_hash):
    lengths = [ord(string_to_hash[i]) for i in range(0 , len(string_to_hash))]
    append_given_lengths(lengths)
    list_size = 256
    my_list = [x for x in range(0,list_size)]

    skip_size = 0
    current_pos = 0
    beggining_index = None
    ending_index = None

    for k in range(0 , 64):
        for length in lengths:
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

    dense_hash = get_dense_hash(my_list)
    hex_digit = None
    hashed_string = ""

    for x in dense_hash:
        hex_digit = hex(x)[2:]
        if(len(hex_digit) == 1):
            hex_digit = "0" + hex_digit
        hashed_string += hex_digit

    return hashed_string

def main():
    try:
        file = open("Input_Day10" , "r")
    except IOError:
        print("*** ERROR: Could not open file for reading input ***")
        raise SystemExit

    raw_input = file.read()
    raw_input = re.sub("\s", "", raw_input)

    puzzle_answer = knot_hash(raw_input)
    print(puzzle_answer)


main()
