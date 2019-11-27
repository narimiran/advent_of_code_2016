from math import factorial

def translate_asembunny(a):
    return factorial(a) + 87 * 70


print('Assembunny code again? Not a problem for me.')
print('If I start with 7 eggs in the register a, '
     f'I get {translate_asembunny(a=7)} as a final value for that register.')
print('....')
print('Oh! I should have started with 12 eggs in the register a!')
print(f'Then I get {translate_asembunny(a=12)} as a final value.')