import re
import numpy as np

with open('./22 - Grid Computing.txt', 'r') as infile:
    lines = infile.read().split('\n')

# print(lines[:10])

node_combinations = set()
avail = []
used = []

for line in lines[2:]:
    sizes = re.search(r'(\d+)T\s+(\d+)T\s+\d+%', line)
    node_combinations.add(tuple(map(int, sizes.groups())))
    used.append(int(sizes.group(1)))
    avail.append(int(sizes.group(2)))

print(len(node_combinations))
print(len(avail))

print(sorted(avail, reverse=True)[:10])
print(sorted(used)[:10])

uss = np.array(used)

print(sum((uss <= 94) & (uss > 0) ))