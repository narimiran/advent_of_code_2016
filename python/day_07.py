import re

with open('./inputs/07.txt', 'r') as infile:
    addresses = infile.readlines()

addresses_separated = [re.split(r'\[|\]', line.strip()) for line in addresses]

supernet = [' '.join(line[::2]) for line in addresses_separated]
hypernet = [' '.join(line[1::2]) for line in addresses_separated]


def is_abba(line):
    return any(a+b == d+c and a != b
               for a, b, c, d in zip(line, line[1:], line[2:], line[3:]))

def is_ababab(sup, hyp):
    return any(a == c and a != b and b+a+b in hyp
               for a, b, c in zip(sup, sup[1:], sup[2:]))


first_solution = sum(is_abba(sup) and not is_abba(hyp)
                     for sup, hyp in zip(supernet, hypernet))

second_solution = sum(is_ababab(sup, hyp)
                      for sup, hyp in zip(supernet, hypernet))


print("Look at all those IP addresses. I wonder how many support TLS?")
print(f"I would say somewhere around {first_solution} of them.")
print("....")
print("And the number of addresses which support SSL?")
print(f"Only {second_solution} support SSL out of {len(addresses)} total.")
