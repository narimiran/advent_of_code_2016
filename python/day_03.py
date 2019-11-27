from itertools import chain

with open('./inputs/03.txt', 'r') as infile:
    puzzle = infile.readlines()

horizontal = [[int(value) for value in row.split()] for row in puzzle]
vertical = list(chain.from_iterable(zip(*horizontal)))


def is_triangle(sides):
    a, b, c = sorted(sides)
    return a + b > c


def find_triangles(candidates, second_part=False):
    if not second_part:
        return sum(is_triangle(row) for row in candidates)
    else:
        return sum(is_triangle(candidates[i:i+3])
                   for i in range(0, len(candidates)-2, 3))


print("Lots of potential triangles on the walls here.")
print("Let me just quickly calculate their number:", find_triangles(horizontal))
print('.....')
print("But wait! Maybe they are drawn vertically?")
print("Number of those triangles is:", find_triangles(vertical, True))
