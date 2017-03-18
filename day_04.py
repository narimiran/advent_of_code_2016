with open('./sources/04 - Security Through Obscurity.txt', 'r') as infile:
    all_rooms = infile.read().split('\n')

names = [room[:-11].replace('-', '') for room in all_rooms]
sectors = [int(room[-10:-7]) for room in all_rooms]
ch_sums = [room[-6:-1] for room in all_rooms]


def find_most_common(name):
    ranking = sorted((-name.count(letter), letter) for letter in set(name))
    return ''.join(letter for _, letter in ranking[:5])


def find_rooms():
    total = 0
    wanted_room = ()
    for name, sector, ch_sum in zip(names, sectors, ch_sums):
        if find_most_common(name) == ch_sum:
            total += sector

            decrypted_name = ''.join(chr((ord(letter) - 97 + sector) % 26 + 97)
                                     for letter in name)
            if decrypted_name.startswith('northpole'):
                wanted_room = (sector, decrypted_name)
    return total, wanted_room


total, wanted_room = find_rooms()

print("Rules for decoding this are too easy for me.")
print("I'll calculate the sum of all sectors of real rooms just for fun.")
print("The sum is: {}".format(total))
print("....")
print("Oh, look at this room called {0[1]} at sector {0[0]}, this must be it!"
      .format(wanted_room))
