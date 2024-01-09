try:
    file = open("Input_Day1" , "r")
except IOError:
    print("*** ERROR: Could not open file for reading input ***")
    raise SystemExit

input = file.readline()
input = input.rstrip("\n")
sum = 0

for i in range(0,len(input)-1):
    if (input[i] == input[i+1]):
        sum += int(input[i])

if (input[0] == input[len(input)-1]):   # List is circular!
    sum += int(input[0])

print(sum)
