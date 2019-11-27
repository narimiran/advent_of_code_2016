import numpy as np
# import matplotlib.pyplot as plt

with open('./inputs/08.txt', 'r') as infile:
    instructions = infile.read().split('\n')

lcd = np.zeros((6, 50))

for line in instructions:
    if line.startswith('rect'):
        width, height = map(int, line.split()[1].split('x'))
        lcd[:height, :width] = 1
    elif line.startswith('rotate'):
        _, ax, pos, _, shift = line.split()
        pos = int(pos.split('=')[1])
        shift = int(shift)
        if ax == 'row':
            lcd[pos] = np.roll(lcd[pos], shift)
        else:
            lcd[:, pos] = np.roll(lcd[:, pos], shift)

print('Oh, look at all those {:0.0f} blinking lights!'.format(np.sum(lcd)))
print('....')
print('If I squint my eyes, I might be able to read the code from the screen....\n')
print('\n'.join(' '.join('#' if on else ' ' for on in line) for line in lcd))

# plt.imshow(lcd, cmap='viridis')
# plt.show()
