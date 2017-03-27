import numpy as np

INPUT = 3018458

def find_elf(elves):
    if len(elves) <= 2:
        return elves[0] + 1

    if len(elves) % 2:
        return find_elf(np.roll(elves[::2], 1))
    else:
        return find_elf(elves[::2])


def new_rules(elves):
    n = len(elves)
    if n <= 2:
        return elves[0] + 1

    across = n // 2
    if n % 2:
        removed = n - (n+1)//3
        new = np.roll(elves, -across)[1::3]
    else:
        removed = n - n//3
        new = np.roll(elves, -across)[2::3]

    return new_rules(np.roll(new, across-removed))


elf_circle = np.arange(INPUT)

print("Hey Elves, why don't you start playing the Yankee swap?")
print(f"Who did win all presents? Elf {find_elf(elf_circle)}?")
print("....")
print("Wait wait wait, you should take the present from the one across you!")
print(f"Who is the winner now? Elf {new_rules(elf_circle)}?")
