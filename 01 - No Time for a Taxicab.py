with open('./01 - No Time for a Taxicab.txt', 'r') as infile:
    directions = infile.read().strip().split(', ')

ROTATION = {
    'L': 1j,
    'R': -1j
}

current_direction = 1j
location = 0+0j

visited_locations = set()
passed_twice = False


def find_manhattan(loc):
    return abs(loc.real) + abs(loc.imag)


for instruction in directions:
    rot, dist = instruction[0], int(instruction[1:])
    current_direction *= ROTATION[rot]

    for _ in range(dist):
        location += current_direction
        if not passed_twice and location in visited_locations:
            print("This looks familiar!\n"
                  "I must have been at {} before!".format(location))
            print("Distance from the start:", find_manhattan(location))
            passed_twice = True
        else:
            visited_locations.add(location)

print("\nOk, I've come to the end of your instructions and I'm at:", location)
print("That's {} blocks away from the the start.".format(find_manhattan(location)))
