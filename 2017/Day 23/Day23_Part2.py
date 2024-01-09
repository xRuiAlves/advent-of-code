#!/usr/bin/python3
import re

def is_prime(num):
    if (num<2):
        return False
    for i in range(2 , (num//2 + 1)):
        if (num%i == 0):
            return False
    return True

def main():
    b = 0
    c = 0
    h = 0

    b = 67
    b *= 100
    b += 100000
    c = b
    c += 17000

    while (c >= b):
        if (not(is_prime(b))):
            h += 1
        b += 17

    print(h)


main()
