from collections import defaultdict

with open('./sources/10 - Balance Bots.txt', 'r') as infile:
    instructions = infile.read().split('\n')

initial = [line.split() for line in instructions if line.startswith('value')]
commands = [line.split() for line in instructions if not line.startswith('value')]

connections = {}
for line in commands:
    name, lower, higher = line[1], line[5:7], line[-2:]
    connections[name] = (lower, higher)

bots = defaultdict(list)
outputs = defaultdict(list)
stack = []

for line in initial:
    value, name = int(line[1]), line[-1]
    bots[name].append(value)
    if len(bots[name]) == 2:
        stack.append(name)


def send_value(connection, value):
    out_type, out_name = connection
    if out_type == 'bot':
        bots[out_name].append(value)
        if len(bots[out_name]) == 2:
            stack.append(out_name)
    else:
        outputs[out_name].append(value)


while stack:
    name = stack.pop()
    if len(bots[name]) == 2:
        low_value, high_value = sorted(bots[name])
        if low_value == 17 and high_value == 61:
            wanted_bot = name
        lower_connection, higher_connection = connections[name]
        send_value(lower_connection, low_value)
        send_value(higher_connection, high_value)

a, b, c = (outputs[i][0] for i in '012')


print("I'm Bot {}, and I'm responsible for microchips 17 and 61.".format(wanted_bot))
print('....')
print("Outputs zero, one, and two have values: {}, {}, and {}.".format(a, b, c))
print("Did you know that if you multiply them, you get {}?".format(a*b*c))
