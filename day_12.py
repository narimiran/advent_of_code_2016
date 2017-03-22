def translate_asembunny(c_val):
    # lines 1-9
    a = b = 1
    d = 26 + 7 * c_val

    # lines 10-16, Fibonacci
    for _ in range(d):
        a, b = a+b, a

    # lines 17-23
    c = 19
    d = 14
    a += c * d

    return a


print("Well, this should be quick....")
print("Initializing all registers with 0. Firing the code.")
print(f"The end value of A register is: {translate_asembunny(c_val=0)}")
print("....")
print("What? The starting value for C register is 1?")
print(f"Then the end value of A register is: {translate_asembunny(c_val=1)}")
