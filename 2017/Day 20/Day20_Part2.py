#!/usr/bin/python3
import re
import math

class Particle:
    def __init__(self , idNum , p , v , a):
        self.idNum = idNum
        self.p = p
        self.v = v
        self.a = a
    def print_info(self):
        print(self.idNum , ":  " , self.p , " , " , self.v , " , " , self.a)
    def get_distance(self):
        return (abs(self.p[0]) + abs(self.p[1]) + abs(self.p[2]))
    def time_tick(self):
        for i in range(0,3):
            self.v[i] += self.a[i]
            self.p[i] += self.v[i]

def delete_particles(particles):
    current_particle_index = 0
    indexes_to_delete = []
    while (current_particle_index<len(particles)):
        indexes_to_delete = []

        for i in range(0,len(particles)):
            if (i != current_particle_index):
                if (particles[i].p == particles[current_particle_index].p):
                    indexes_to_delete.append(i)


        if (len(indexes_to_delete) > 0):
            indexes_to_delete.insert(0,current_particle_index)
            for index in reversed(indexes_to_delete):
                particles.pop(index)
        else:
            current_particle_index += 1




def main():
    try:
        file = open("Input_Day20" , "r")
    except IOError:
        print("*** ERROR: Could not open file for reading input ***")
        raise SystemExit

    raw_particles = file.readlines()
    parsed_line = None
    particles = []
    p_counter = 0
    times_unchanged = 0

    for line in raw_particles:
        line = re.findall("-?\d+" , line)
        parsed_line = [int(x) for x in line]
        particles.append(Particle(p_counter , parsed_line[0:3],parsed_line[3:6],parsed_line[6:]))
        p_counter += 1


    current_len = len(particles)

    while (times_unchanged < 100):
        delete_particles(particles)

        for p in particles:
            p.time_tick()

        if (current_len != len(particles)):
            current_len = len(particles)
            times_unchanged = 0
        else:
            times_unchanged += 1

    print(len(particles))

main()
