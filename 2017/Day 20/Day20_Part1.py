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
    min_distance = None
    current_particle = 0
    this_run_particle = None

    for line in raw_particles:
        line = re.findall("-?\d+" , line)
        parsed_line = [int(x) for x in line]
        particles.append(Particle(p_counter , parsed_line[0:3],parsed_line[3:6],parsed_line[6:]))
        p_counter += 1

    while (times_unchanged < 1000):
        min_distance = particles[0].get_distance()
        for p in particles:
            if (p.get_distance() < min_distance):
                min_distance = p.get_distance()
                this_run_particle = p.idNum
            p.time_tick()

        if (this_run_particle != current_particle):
            current_particle = this_run_particle
            times_unchanged = 0
        else:
            times_unchanged += 1

    print(current_particle)

main()
