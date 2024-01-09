#!/usr/bin/python3
import re

def hex_to_bin(hex_digit):
    if (hex_digit == "0"):
        return "0000"
    elif (hex_digit == "1"):
        return "0001"
    elif (hex_digit == "2"):
        return "0010"
    elif (hex_digit == "3"):
        return "0011"
    elif (hex_digit == "4"):
        return "0100"
    elif (hex_digit == "5"):
        return "0101"
    elif (hex_digit == "6"):
        return "0110"
    elif (hex_digit == "7"):
        return "0111"
    elif (hex_digit == "8"):
        return "1000"
    elif (hex_digit == "9"):
        return "1001"
    elif (hex_digit == "a" or hex_digit == "A"):
        return "1010"
    elif (hex_digit == "b" or hex_digit == "B"):
        return "1011"
    elif (hex_digit == "c" or hex_digit == "C"):
        return "1100"
    elif (hex_digit == "d" or hex_digit == "D"):
        return "1101"
    elif (hex_digit == "e" or hex_digit == "E"):
        return "1110"
    elif (hex_digit == "f" or hex_digit == "F"):
        return "1111"

def hex_string_to_bin_string(hex_string):
    bin_string = ""
    for i in range(0, len(hex_string)):
        bin_string += hex_to_bin(hex_string[i])
    return bin_string

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
        file = open("Input_Day14" , "r")
    except IOError:
        print("*** ERROR: Could not open file for reading input ***")
        raise SystemExit

    puzzle_input = "oundnydw"
    puzzle_input += "-"

    matrix_string = ""
    num_rows = 128

    for i in range(0,num_rows):
        matrix_string += hex_string_to_bin_string(knot_hash(puzzle_input+str(i)))

    num_used = 0
    for i in range(0 , len(matrix_string)):
        if (matrix_string[i] == "1"):
            num_used += 1

    print("Used: " , num_used)


main()
