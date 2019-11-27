from collections import defaultdict

with open('./inputs/10.txt', 'r') as infile:
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


def add_value(name, value):
    bots[name].append(value)
    if len(bots[name]) == 2:
        stack.append(name)


def send_value(connection, value):
    out_type, out_name = connection
    if out_type == 'bot':
        add_value(out_name, value)
    else:
        outputs[out_name].append(value)


for line in initial:
    value, name = int(line[1]), line[-1]
    add_value(name, value)


while stack:
    name = stack.pop()
    low_value, high_value = sorted(bots[name])
    if low_value == 17 and high_value == 61:
        wanted_bot = name
    lower_connection, higher_connection = connections[name]
    send_value(lower_connection, low_value)
    send_value(higher_connection, high_value)

a, b, c = (outputs[i][0] for i in '012')


print(f"I'm Bot {wanted_bot}, and I'm responsible for microchips 17 and 61.")
print('....')
print(f"Outputs zero, one, and two have values: {a}, {b}, and {c}.")
print(f"Did you know that if you multiply them, you get {a*b*c}?")
