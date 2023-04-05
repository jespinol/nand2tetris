import argparse


def parser_module(file):
    print('Parsing vm file...')
    current_file = open(file, 'r')
    c_arithmetic = {}
    c_push = {}
    c_pop = {}
    c_label = {}
    c_goto = {}
    c_if = {}
    c_function = {}
    c_return = {}
    c_call = {}
    count = 0
    for line in current_file:
        print(line.strip())
        line = line.strip()
        if not line.startswith('/') and len(line) > 2:
            if line.startswith('push'):
                print('as push')
                c_push[count] = line.split()[1], line.split()[2]
                print(f'added "{line}" in push as {count} = {line.split()[1]}, {line.split()[2]}')
                count += 1
            elif line.startswith('pop'):
                print('as pop')
                c_pop[count] = line.split()[1], line.split()[2]
                print(f'added "{line}" in pop as {count} = {line.split()[1]}, {line.split()[2]}')
                count += 1
            else:
                print('as else')
                c_arithmetic[count] = line
                print(f'added "{line}" in c_arith as {count} = {line}')
                count += 1
    print('Parsing done')
    print('push', c_push)
    print('pop', c_pop)
    print('command', c_arithmetic)
    return


def output_module(input_file, assembly):
    print('Writing asm file...')
    # the code below will name the output file as the input file but with the 'asm' extension
    dot_position = input_file.find('.')
    file_name = input_file[:dot_position]
    output_file = open(f'{file_name}.asm', 'w')
    # add binary translations line by line in the same order as in the input file
    for key, value in sorted(c.items()):
        output_file.write(f'{value}\n')
    output_file.close()
    print('Output file written')


def main(file):
    parser_module(file)


# This handles the command line input (single file at the moment)
parser = argparse.ArgumentParser(description='This program will convert a valid vm file into an asm file')
parser.add_argument('file', help='Input file in vm format')
args = parser.parse_args()

# Execute main program
main(args.file)
