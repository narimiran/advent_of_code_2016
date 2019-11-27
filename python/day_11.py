from collections import deque


def make_hash(gens, chips, lift, *args):
    g = [gens.count(str(i)) for i in range(4)]
    c = [chips.count(str(i)) for i in range(4)]
    return ''.join(map(str, g+c)) + str(lift)


def is_invalid(gens, chips, lift, *args):
    if lift not in range(4):
        return True
    for generator, chip in zip(gens, chips):
        if chip != generator and any(gen == chip for gen in gens):
            return True
    return False


def is_solved(positions):
    return all(pos == 3 for pos in positions)


def get_gcls(positions, l, s):
    g = ''.join(map(str, positions[: len(positions)//2]))
    c = ''.join(map(str, positions[len(positions)//2 :]))
    return g, c, l, s


def calculate_steps(gens, chips, lift, steps):
    seen = set()
    que = deque([(gens, chips, lift, steps)])
    while que:
        *state, steps = que.popleft()
        hash_ = make_hash(*state)
        if hash_ in seen or is_invalid(*state):
            continue

        seen.add(hash_)

        positions = list(map(int, ''.join(state[:-1])))
        lift = state[-1]

        if is_solved(positions):
            return steps

        for i, first in enumerate(positions):
            if first == lift:
                if lift < 3:
                    positions[i] += 1
                    que.append(get_gcls(positions, lift+1, steps+1))
                    positions[i] -= 1
                if lift > 0:
                    positions[i] -= 1
                    que.append(get_gcls(positions, lift-1, steps+1))
                    positions[i] += 1

                for j, second in enumerate(positions[i+1:], i+1):
                    if second == lift:
                        if lift < 3:
                            positions[i] += 1
                            positions[j] += 1
                            que.append(get_gcls(positions, lift+1, steps+1))
                            positions[i] -= 1
                            positions[j] -= 1
                        if lift > 0:
                            positions[i] -= 1
                            positions[j] -= 1
                            que.append(get_gcls(positions, lift-1, steps+1))
                            positions[i] += 1
                            positions[j] += 1


first_solution = calculate_steps(gens='01111', chips='02222', lift=0, steps=0)
second_solution = calculate_steps(gens='0001111', chips='0002222', lift=0, steps=0)

print("I should bring all those things to 4th floor!")
print(f"By my calculation, it should take no more than {first_solution} steps.")
print('....')
print(f"Even if there were two more elements, it should be {second_solution} steps total.")
