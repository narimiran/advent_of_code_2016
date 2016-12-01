with open('./01 - No Time for a Taxicab.txt', 'r') as infile:
    directions = infile.read().strip().split(', ')

ROTATION = {'L': -1, 'R': 1}
DELTAS = [
    (0, 1),     # go north
    (1, 0),     # go east
    (0, -1),    # go south
    (-1, 0),    # go west
]

location = (0, 0)
current_direction = 0

visited_locations = set()
passed_twice = False


def find_manhattan(loc):
    return sum(abs(d) for d in loc)


for instruction in directions:
    rot, dist = instruction[0], int(instruction[1:])
    current_direction = (current_direction + ROTATION[rot]) % 4
    current_delta = DELTAS[current_direction]


    for _ in range(dist):
        location = tuple(location[i] + current_delta[i] for i in range(2))
        if not passed_twice and location in visited_locations:
            print("I've been here {} before!".format(location))
            print("Distance from the start:", find_manhattan(location))
            passed_twice = True
        else:
            visited_locations.add(location)

print("\nOk, I've come to the end of your instructions and I'm at", location)
print("That's {} away from the the start".format(find_manhattan(location)))
