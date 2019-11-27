import numpy as np

in_ = np.loadtxt('./inputs/03.txt')

def find_triangles(arr):
    arr.sort(axis=1)
    return sum(np.sum(arr[:, :2], axis=1) > arr[:, 2])

print(find_triangles(in_.copy()))
print(find_triangles(in_.T.reshape(-1, 3)))
