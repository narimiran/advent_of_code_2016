with open('./inputs/04.txt', 'r') as infile:
    all_rooms = infile.read().splitlines()

rooms = ((room[:-11].replace('-', ''),
          int(room[-10:-7]),
          room[-6:-1]
         ) for room in all_rooms)


def find_most_common(name):
    ranking = sorted((-name.count(letter), letter) for letter in set(name))
    return ''.join(letter for _, letter in ranking[:5])


def find_rooms():
    total = 0
    for name, sector, ch_sum in rooms:
        if find_most_common(name) == ch_sum:
            total += sector
            decrypted_name = ''.join(chr((ord(letter) - 97 + sector) % 26 + 97)
                                     for letter in name)
            if decrypted_name.startswith('northpole'):
                wanted_room = (sector, decrypted_name)
    return total, wanted_room


total, (sector, name) = find_rooms()

print("Rules for decoding this are too easy for me.")
print("I'll calculate the sum of all sectors of real rooms just for fun.")
print(f"The sum is: {total}")
print("....")
print(f"Oh, look at this room called '{name}' at sector {sector}!")
