with open('./inputs/21.txt', 'r') as infile:
    INSTRUCTIONS = infile.read().split('\n')


class Scrambler:
    def __init__(self, pw):
        self.pw = pw

    def __repr__(self):
        return ''.join(self.pw)

    def swap_positions(self, x_pos, y_pos):
        self.pw[x_pos], self.pw[y_pos] = self.pw[y_pos], self.pw[x_pos]

    def swap_letters(self, x, y):
        self.swap_positions(self.pw.index(x), self.pw.index(y))

    def rotate(self, x_pos):
        x_pos %= len(self.pw)
        self.pw = self.pw[-x_pos:] + self.pw[:-x_pos]

    def rotate_letter(self, x):
        x_pos = self.pw.index(x)
        if x_pos >= 4:
            x_pos += 1
        self.rotate(x_pos + 1)

    def derotate_letter(self, x):
        x_pos = self.pw.index(x)
        if x_pos % 2:
            rot = - (x_pos + 1) // 2
        elif x_pos:
            rot = (6 - x_pos) // 2
        else:
            rot = -1
        self.rotate(rot)

    def reverse(self, x_pos, y_pos):
        y_pos += 1
        tmp = self.pw[x_pos:y_pos]
        tmp.reverse()
        self.pw[x_pos:y_pos] = tmp

    def move(self, x_pos, y_pos):
        self.pw.insert(y_pos, self.pw.pop(x_pos))

    def scramble(self, direction=1):
        for instruction in INSTRUCTIONS[::direction]:
            line = instruction.split()
            if instruction.startswith('swap'):
                x, y = line[2], line[-1]
                if line[1] == 'position':
                    self.swap_positions(int(x), int(y))
                else:
                    self.swap_letters(x, y)
            elif instruction.startswith('rotate'):
                if line[1] == 'based':
                    if direction > 0:
                        self.rotate_letter(line[-1])
                    else:
                        self.derotate_letter(line[-1])
                else:
                    x_pos = int(line[2])
                    if line[1] == 'left':
                        x_pos *= -1
                    if direction < 0:
                        x_pos *= -1
                    self.rotate(x_pos)
            else:
                x_pos = int(line[2])
                y_pos = int(line[-1])
                if instruction.startswith('reverse'):
                    self.reverse(x_pos, y_pos)
                else:
                    if direction < 0:
                        x_pos, y_pos = y_pos, x_pos
                    self.move(x_pos, y_pos)
        return self

    def unscramble(self):
        return self.scramble(-1)


plain = list('abcdefgh')
hashed = list('fbgdceah')

first_part = Scrambler(plain).scramble()
second_part = Scrambler(hashed).unscramble()

print("OK, I have scrambling instructions in front of me, let's try this.")
print(f'The scrambled version of "abcdefgh" is: {first_part}.')
print('....')
print('This works, but I need to unscramble "fbgdceah".')
print(f'This is {second_part} in plain text.')
