import re

with open('./sources/09 - Explosives in Cyberspace.txt', 'r') as infile:
    compressed = infile.read()


def unzip(s, second_part=False):
    parens = re.search(r'\((\d+)x(\d+)\)', s)
    if not parens:
        return len(s)
    length = int(parens.group(1))
    times = int(parens.group(2))
    start = parens.start() + len(parens.group())
    count = unzip(s[start:start+length], True) if second_part else length

    return (len(s[:parens.start()])
            + times * count
            + unzip(s[start+length:], second_part))


print("Let's do a quick decompression of this file....")
print(f"Its decompressed size is {unzip(compressed)} characters.")
print('....')
print("Hmmm, this still looks compressed. Let's decompress it fully.")
print(f"Now it has {unzip(compressed, second_part=True)} characters.")
