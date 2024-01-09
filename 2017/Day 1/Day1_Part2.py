try:
    file = open("Input_Day1" , "r")
except IOError:
    print("*** ERROR: Could not open file for reading input ***")
    raise SystemExit

input = file.readline()
input = input.rstrip("\n")
sum = 0
half_len = len(input)//2

for i in range(0,half_len):
    if (input[i] == input[i+half_len]):
        sum += (int(input[i])*2)


print(sum)
