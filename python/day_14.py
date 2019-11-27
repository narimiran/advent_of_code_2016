from hashlib import md5

SALT = 'qzyelonm'


def find_keys(second_part=False):
    triplets = {}
    valid_keys = set()
    index = 0

    while len(valid_keys) < 64 or index < max(valid_keys) + 1000:
        hex_ = md5((SALT+str(index)).encode()).hexdigest()

        if second_part:
            for _ in range(2016):
                hex_ = md5(hex_.encode()).hexdigest()

        found_triplet = False
        for a, b, c in zip(hex_, hex_[1:], hex_[2:]):
            if a == b == c:
                if 5*a in hex_:
                    for k, v in triplets.items():
                        if a == v and k < index <= 1000+k:
                            valid_keys.add(k)
                if not found_triplet:
                    triplets[index] = a
                    found_triplet = True
        index += 1
    return sorted(valid_keys)[63]


print("I need to contact Santa, and I need MD5 keys for that.")
print(f"He said to use 64th key, which is {find_keys()}.")
print("....")
print("Wait, he said to encript this 2016 times!")
print(f"Then the 64th key is {find_keys('second')}.")
