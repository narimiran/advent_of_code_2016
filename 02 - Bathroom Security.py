with open('./02 - Bathroom Security.txt', 'r') as infile:
    directions = infile.read().strip().split('\n')

deltas = {
    'R': 1,
    'L': -1,
    'U': 1j,
    'D': -1j,
}

KEYPAD_1 = [
    [1, 2, 3],
    [4, 5, 6],
    [7, 8, 9]
]

KEYPAD_2 = [
    [0,  0,  1,  0,  0],
    [0,  2,  3,  4,  0],
    [5,  6,  7,  8,  9],
    [0, 'A','B','C', 0],
    [0,  0, 'D', 0,  0]
]


def is_inside(pos, second_part):
    if not second_part:
        return abs(pos.real) <= 1 and abs(pos.imag) <= 1
    else:
        return abs(pos.real) + abs(pos.imag) <= 2


def get_key(preal, pimag, k_pad):
    return k_pad[int(pimag)][int(preal)]


def find_solutions(second_part=False):
    if not second_part:
        pos = 0+0j      # start from 5, in the center
    else:
        pos = -2+0j     # start from 5, left-middle

    key_positions = []
    for line in directions:
        for c in line:
            new = pos + deltas[c]
            if is_inside(new, second_part):
                pos = new
        key_positions.append(pos)

    if not second_part:
        return [get_key(pos.real+1, 1-pos.imag, KEYPAD_1) for pos in key_positions]
    else:
        return [get_key(pos.real+2, 2-pos.imag, KEYPAD_2) for pos in key_positions]


print(find_solutions())
print(find_solutions(second_part=True))
