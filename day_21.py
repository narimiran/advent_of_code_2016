with open('./sources/21 - Scrambled Letters and Hash.txt', 'r') as infile:
    INSTRUCTIONS = infile.read().split('\n')


class Scrambler:
    def __init__(self, pw, direction):
        self.pw = pw
        self.direction = direction

    def __repr__(self):
        return ''.join(self.pw)

    def swap_positions(self, x_pos, y_pos):
        self.pw[x_pos], self.pw[y_pos] = self.pw[y_pos], self.pw[x_pos]

    def swap_letters(self, x, y):
        x_pos = self.pw.index(x)
        y_pos = self.pw.index(y)
        self.swap_positions(x_pos, y_pos)

    def rotate(self, x_pos):
        x_pos %= len(self.pw)
        self.pw = self.pw[-x_pos:] + self.pw[:-x_pos]

    def rotate_letter(self, x):
        x_pos = self.pw.index(x)
        if x_pos >= 4:
            x_pos += 1
        self.rotate(x_pos + 1)

    def reverse(self, x_pos, y_pos):
        y_pos += 1
        tmp = self.pw[x_pos:y_pos]
        tmp.reverse()
        self.pw[x_pos:y_pos] = tmp

    def move(self, x_pos, y_pos):
        x = self.pw.pop(x_pos)
        self.pw.insert(y_pos, x)

    def derotate_letter(self, x):
        x_pos = self.pw.index(x)
        if x_pos % 2:
            rot = - (x_pos + 1) // 2
        elif x_pos:
            rot = (6 - x_pos) // 2
        else:
            rot = -1
        self.rotate(rot)

    def scramble(self):
        for instruction in INSTRUCTIONS[::self.direction]:
            line = instruction.split()
            if instruction.startswith('swap'):
                x, y = line[2], line[-1]
                if line[1] == 'position':
                    self.swap_positions(int(x), int(y))
                else:
                    self.swap_letters(x, y)
            elif instruction.startswith('rotate'):
                if line[1] == 'based':
                    if self.direction > 0:
                        self.rotate_letter(line[-1])
                    else:
                        self.derotate_letter(line[-1])
                else:
                    x_pos = int(line[2])
                    if line[1] == 'left':
                        x_pos *= -1
                    if self.direction < 0:
                        x_pos *= -1
                    self.rotate(x_pos)
            else:
                x_pos = int(line[2])
                y_pos = int(line[-1])
                if instruction.startswith('reverse'):
                    self.reverse(x_pos, y_pos)
                else:
                    if self.direction < 0:
                        x_pos, y_pos = y_pos, x_pos
                    self.move(x_pos, y_pos)


plain = Scrambler(list('abcdefgh'), 1)
plain.scramble()
hashed = Scrambler(list('fbgdceah'), -1)
hashed.scramble()

print("OK, I have scrambling instructions in front of me, let's try this.")
print('The scrambled version of "abcdefgh" is: {}.'.format(plain))
print()
print('This works, but I need to unscramble "fbgdceah".')
print('This is {} in plain text.'.format(hashed))
