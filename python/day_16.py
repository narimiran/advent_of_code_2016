START = list(map(int, '01110110101001000'))
DISK_1 = 272
DISK_2 = 35651584


def fill_disk(data, disk_size):
    while len(data) < disk_size:
        new = [1-i for i in reversed(data)]
        data.append(0)
        data.extend(new)
    return data[:disk_size]


def create_checksum(data):
    while len(data) % 2 == 0:
        data = [a == b for a, b in zip(data[::2], data[1::2])]
    return ''.join(map(str, map(int, data)))


first = create_checksum(fill_disk(START, DISK_1))
second = create_checksum(fill_disk(START, DISK_2))

print("Let's fill the disk no. 1 with the 'random' data.")
print("The checksum is:", first)
print("....")
print("Disk 2 is so much bigger!")
print("Its checksum is", second)
