with open('./20 - Firewall Rules.txt', 'r') as infile:
    addresses = infile.read().split('\n')

ips = sorted([tuple(map(int, address.split('-'))) for address in addresses])


def find_lowest(ips):
    nr_available = 0
    lowest_available = 0
    the_lowest = 0

    for (low, high) in ips:
        if low > lowest_available:
            nr_available += low - lowest_available
            if not the_lowest:
                the_lowest = lowest_available
        lowest_available = max(lowest_available, high + 1)
    return the_lowest, nr_available


first, second = find_lowest(ips)

print("Let me search from the beginning and find first non-blocked IP")
print("That's: {}.".format(first))
print("....")
print("But there are in total {} non-blocked IPs.".format(second))
