with open('./03 - Squares With Three Sides.txt', 'r') as infile:
    triangles = infile.read().split('\n')

horizontal = [[int(side) for side in sides.split()] for sides in triangles]
vertical = list(zip(*horizontal))


def is_triangle(sides):
    a, b, c = sorted(sides)
    return a + b > c


def find_triangles(candidates, second_part=False):
    solution = []
    if not second_part:
        for row in candidates:
            solution.append(is_triangle(row))
    else:
        for col in candidates:
            for i in range(0, len(col)-2, 3):
                solution.append(is_triangle(col[i:i+3]))
    return sum(solution)


print(find_triangles(horizontal))
print(find_triangles(vertical, True))
