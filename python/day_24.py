from collections import deque
from itertools import permutations

with open("./inputs/24.txt", 'r') as infile:
    maze = infile.read().splitlines()


def bfs(start, goal):
    DELTAS = ((1, 0), (-1, 0), (0, 1), (0, -1))
    que = deque([(start, 0)])
    seen = set(start)

    while que:
        (x, y), steps = que.popleft()
        if (x, y) == goal:
            return steps
        for dx, dy in DELTAS:
            nx, ny = (x+dx, y+dy)
            if not (nx, ny) in seen and maze[ny][nx] != '#':
                que.append(((nx, ny), steps+1))
                seen.add((nx, ny))


def find_shortest(second_part=False):
    min_dist = 1e9
    for path in permutations(range(1, len(coordinates))):
        path_dist = distances[0][path[0]]
        for a, b in zip(path, path[1:]):
            path_dist += distances[a][b]
        if second_part:
            path_dist += distances[b][0]
        min_dist = min(min_dist, path_dist)
    return min_dist


coordinates = {}
for y, row in enumerate(maze[1:-1], 1):
    for x, value in enumerate(row[1:-1], 1):
        if value.isdigit():
            coordinates[value] = (x, y)

distances = [[0 for _ in range(len(coordinates))] for _ in range(len(coordinates))]

points = sorted(coordinates.keys())
for start in points[:-1]:
    st = int(start)
    for goal in points[st+1:]:
        dist = bfs(coordinates[start], coordinates[goal])
        gl = int(goal)
        distances[st][gl] = distances[gl][st] = dist


print('Wow, this robot is very slow! '
      'I better find the shortest path from here to all needed locations.')
print(f'That should be this path that takes {find_shortest()} steps.')
print('....')
print('Oh, I forgot my robot in the middle of the maze!')
print("I'll send another one, let me just first recalculate the shortest path.")
print(f'The shortest path with returning here takes {find_shortest(2)} steps.')
