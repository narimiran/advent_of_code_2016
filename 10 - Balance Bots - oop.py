from collections import defaultdict

with open('./10 - Balance Bots.txt', 'r') as infile:
    instructions = infile.read().split('\n')

initial = [line for line in instructions if line.startswith('value')]
commands = [line for line in instructions if not line.startswith('value')]


class Bot:
    def __init__(self, name):
        self.name = name
        self.low = None
        self.high = None
        self.values = []

    def add_neighbour(self, name):
        if name[0] == 'bot':
            return bots[name[1]]
        else:
            return outputs[name[1]]

    def add_lower(self, name):
        self.low = self.add_neighbour(name)

    def add_higher(self, name):
        self.high = self.add_neighbour(name)

    def receive_value(self, value):
        self.values.append(value)
        if len(self.values) == 2:
            l, h = sorted(self.values)
            if l == 17 and h == 61:
                print(self.name)
            self.low.receive_value(l)
            self.high.receive_value(h)
