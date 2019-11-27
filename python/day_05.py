import hashlib

DOOR_ID = 'wtnhxymk'

first_password = ''
second_password = [''] * 8
available_positions = set('01234567')

i = 0
while available_positions:
    char_input = (DOOR_ID+str(i)).encode()
    md5_hex = hashlib.md5(char_input).hexdigest()

    if md5_hex.startswith(5*'0'):
        if len(first_password) < 8:
            first_password += md5_hex[5]
        if md5_hex[5] in available_positions:
            second_password[int(md5_hex[5])] = md5_hex[6]
            available_positions.remove(md5_hex[5])
    i += 1

print("The password for a first door is:", first_password)
print('....')
print("The password for a second door is:", ''.join(second_password))
