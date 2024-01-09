# Finds which of the numbers n1 n2 n3 n4 is closest to num
def closest_num(num , n1 , n2 , n3 , n4):
    distance_1 = abs(n1 - num)
    distance_2 = abs(n2 - num)
    distance_3 = abs(n3 - num)
    distance_4 = abs(n4 - num)

    if (distance_1 <= distance_2 and distance_1 <= distance_3 and distance_1 <= distance_4):
        return n1
    elif (distance_2 <= distance_1 and distance_2 <= distance_3 and distance_2 <= distance_4):
        return n2
    elif (distance_3 <= distance_1 and distance_3 <= distance_2 and distance_3 <= distance_4):
        return n3
    else:
        return n4

# Finds closest higher odd perfect square
def closest_odd_perfect_exponent(num):
    i = 1
    while True:
        if (i**2 > num):
            return i
        else:
            i += 2


input_val = 347991;

# Find the closest odd perfect square
closest_odd_square = closest_odd_perfect_exponent(347991)
level  = closest_odd_square - 1

# Find the closest "level square" corner and get the distance to it
closest_corner = closest_num(input_val , closest_odd_square**2 , closest_odd_square**2 - level , closest_odd_square**2 - 2*level , closest_odd_square**2 - 3*level)
distance = abs(input_val - closest_corner)

if (distance > level//2):   # Assert the distance if needed
    distance = distance % (level//2)

manhattan_distance = level - distance   # Compute the final distance

print (manhattan_distance)
