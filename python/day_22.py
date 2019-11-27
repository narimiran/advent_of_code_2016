import re
import numpy as np

with open('./inputs/22.txt', 'r') as infile:
    lines = infile.read().split('\n')

avail = []
used = []
grid = [['.' for _ in range(33)] for _ in range(30)]

for line in lines[2:]:
    sizes = re.search(r'x(\d+)-y(\d+).+T\s+(\d+)T\s+(\d+)T\s+\d+%', line)
    x = int(sizes.group(1))
    y = int(sizes.group(2))
    usd = int(sizes.group(3))
    avl = int(sizes.group(4))
    used.append(usd)
    avail.append(avl)
    if usd > 100:
        grid[y][x] = '#'
    elif usd == 0:
        grid[y][x] = '_'

grid[0][-1] = 'G'
grid[0][0] = 'F'


print("Let's examine top 10 disks regarding free space:")
print(sorted(avail, reverse=True)[:10])
print("And let's see 10 disks with the least amount of data:")
print(sorted(used)[:10])
print("This means that only one disk can be used as a 'reciever'.")

uss = np.array(used)
print(f"There are {sum((uss <= 94) & (uss > 0))} viable pairs of nodes.")
print('....')

print('I should plot the map of this storage cluster!')
for line in grid:
    print(''.join(line))
print("Now I see it, it's quite easy, I need to bypass this wall, "
      "then go to the top right, and then it is just repeat repeat repeat "
      "until I get to top-left corner.")


start = (len(grid)-1, grid[-1].index('_'))
wall = (len(grid)-3, grid[-3].index('#')-1)
goal = (0, len(grid[0])-1)
finish = (0, 0)

def get_manhattan(a, b):
    x1, y1 = a
    x2, y2 = b
    return abs(x2 - x1) + abs(y2 - y1)

steps = get_manhattan(start, wall)
steps += get_manhattan(wall, goal)
steps += 5 * (goal[1] - 1)

print(f'And all that would be {steps} steps.')
