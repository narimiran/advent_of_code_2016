def execute(a):
    with open("./sources/23 - Safe Cracking.txt", 'r') as infile:
        instructions = infile.read().splitlines()

    register = {'a': a, 'b': 0, 'c': 0, 'd': 0}
    interpret = lambda val: register[val] if val.isalpha() else int(val)
    i = 0

    def toggler(pos):
        line = instructions[pos].split()
        if len(line) == 2:
            line[0] = 'dec' if line[0] == 'inc' else 'inc'
        else:
            line[0] = 'cpy' if line[0] == 'jnz' else 'jnz'
        return ' '.join(line)

    def increaser(pos, value=1):
        register[pos] += value

    while i < len(instructions):
        instr, x, *y = instructions[i].split()
        y = y[0] if y else None

        # optimization for instruction lines 5-10
        if i == 9:
            register['a'] += register['d'] * register['b']
            register['c'] = 0
            register['d'] = 0
            i += 1
            continue

        if instr == 'cpy':
            if isinstance(y, int):
                i += 1
                continue
            register[y] = interpret(x)

        if instr == 'inc':
            increaser(x)

        if instr == 'dec':
            increaser(x, -1)

        if instr == 'jnz':
            if interpret(x) != 0:
                i += interpret(y)
                continue

        if instr == 'tgl':
            pos = i + interpret(x)
            if 0 <= pos < len(instructions):
                instructions[pos] = toggler(pos)

        i += 1
    return register['a']


print('Assembunny code again? Not a problem for me.')
print('If I start with 7 eggs in the register a, '
      'I get {} as a final value for that register.'.format(execute(a=7)))
print('....')
print('Oh! I should have started with 12 eggs in the register a!')
print('Then I get {} as a final value.'.format(execute(a=12)))
