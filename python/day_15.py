import re

with open('./inputs/15.txt', 'r') as infile:
    instructions = infile.read().split('\n')

discs = []
for i, line in enumerate(instructions, 1):
    settings = re.search(r'(\d+) positions; .+ (\d+)', line)
    slots = int(settings.group(1))
    position = int(settings.group(2)) + i
    discs.append((slots, position))


def wait_a_sec(discs):
    time = 0
    while True:
        if not sum((position+time)%slot for (slot, position) in discs):
            return time
        time += 1


first = wait_a_sec(discs)

# a new disc with 11 positions and starting at position 0 has appeared
# exactly one second below the previously-bottom disc
discs.append((11, 0+7))
second = wait_a_sec(discs)


print("What a nice arrangement of rotating discs!")
print(f"I should wait {first} seconds for the perfect arrangement.")
print(f"That's only {first//3600} hours! I have plenty of time.")
print("....")
print("A new disc? Let's do this all over again.")
print(f"Waiting and waiting and waiting for {second} seconds.")
print(f"That's {second//86400} days! Ain't nobody got time fo' dat!")
