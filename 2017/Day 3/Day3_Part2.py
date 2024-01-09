# Compute a point
def computePoint(matrix , x , y):
    matrix[x][y] = matrix[x-1][y] + matrix[x-1][y-1] + matrix[x][y-1] + matrix[x+1][y-1] + matrix[x+1][y] + matrix[x+1][y+1] + matrix[x][y+1] + matrix[x-1][y+1]


input_val = 347991

# Create the matrix  !!!! MATRIX SIZE MAY NEED RE-SIZING DEPENDING ON THE INPUT !!!!
MATRIX_WIDTH = 12
MATRIX_HEIGHT = 12
matrix = [[0 for i in range (0,MATRIX_WIDTH)] for j in range(0,MATRIX_HEIGHT)]

# Set the starting  point (Level 1)
level = 1
x = MATRIX_WIDTH//2
y = MATRIX_HEIGHT//2
matrix[x][y] = 1

while True:
    # Go to the next level (by summing one to X)
    level += 1
    x += 1

    # Compute that point
    computePoint(matrix , x , y)

    if(matrix[x][y] > input_val):
        print(matrix[x][y])
        break

    # Compute the remaining points in this level
    for i in range(0,(level-1)*2 - 1):
        y -= 1
        computePoint(matrix , x , y)
        if(matrix[x][y] > input_val):
            print(matrix[x][y])
            exit()

    for i in range(0,(level-1)*2):
        x -= 1
        computePoint(matrix , x , y)
        if(matrix[x][y] > input_val):
            print(matrix[x][y])
            exit()

    for i in range(0,(level-1)*2):
        y += 1
        computePoint(matrix , x , y)
        if(matrix[x][y] > input_val):
            print(matrix[x][y])
            exit()

    for i in range(0,(level-1)*2):
        x += 1
        computePoint(matrix , x , y)
        if(matrix[x][y] > input_val):
            print(matrix[x][y])
            exit()
