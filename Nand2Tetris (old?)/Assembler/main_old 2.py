import argparse


def parser_module(file):
    print('Parsing asm file...')
    current_file = open(file, 'r')
    a_instructions = {}  # here and line below, a- and c-instructions will be split in two separate dictionaries
    c_instructions = {}
    goto_instructions = {}
    count = 0  # this counter will preserve the order of a- and c-instructions in the separate dictionaries
    for line in current_file:
        if not line.startswith('/') and len(line) > 2:
            if '@' in line:
                white_left = line.lstrip()  # non-L programs have leading white spaces, this takes care of them
                white_right = white_left.rstrip()  # removes trailing white spaces
                no_at = white_right[1:]  # removes @
                first_word = no_at.split()[0]  # non-L programs can have comments, but in each instance the part of interest is the "first word"
                a_instructions[count] = first_word  # create a key:value where the key is the line number (current count) and the value is the a-instruction
                count += 1
            elif line.startswith('('):
                print(line.strip())
                print(f'found ( in {line.strip()}')
                white_left = line.lstrip()
                white_right = white_left.rstrip()
                goto = white_right[1:-1]
                goto_instructions[count] = goto
                print(f'added {goto} at {count}')
            else:
                # print(f'found else in {line.strip()}')
                white_left = line.lstrip()  # see comments in if block above, similar logic here
                first_word = white_left.split()[0]
                c_instructions[count] = first_word
                count += 1
    print('Parsing done')
    print(f'xxxxxxxxxxxxx {goto_instructions}')
    return a_instructions, c_instructions, goto_instructions


def symbol_table(a_instructions, goto_instructions):
    print('Preparing a-instructions...')
    # the dictionary below contains reserved symbols. New symbols will be added starting at address (keys here) 16 or, if the symbol is numeric, at address=symbol
    symbol_address = {0: ['R0', '0', 'SP'], 1: ['R1', '1', 'LCL'], 2: ['R2', '2', 'ARG'], 3: ['R3', '3', 'THIS'], 4: ['R4', '4', 'THAT'], 5: ['R5', '5'], 6: ['R6', '6'], 7: ['R7', '7'], 8: ['R8', '8'], 9: ['R9', '9'], 10: ['R10', '10'], 11: ['R11', '11'], 12: ['R12', '12'], 13: ['R13', '13'], 14: ['R14', '14'], 15: ['R15', '15'], 16384: ['16384', 'SCREEN'], 24576: ['24576', 'KBD']}
    all_values = []
    count = 16
    for key, value in symbol_address.items():
        for word in value:
            all_values.append(word)
    for key_, value_ in a_instructions.items():
        # print(f'working with {value_}')
        if value_ in all_values:
            # print(f'{value_} found')
            continue
        else:
            # print('value not in table')
            try:
                # print('testing if number')
                already_number = int(value_)
                try:  # here try to append to list
                    # print(f'appending {already_number}')
                    symbol_address[already_number].append(str(already_number))
                    # symbol_address[already_number] = [symbol_address[already_number], [str(already_number)]]
                    all_values.append(str(already_number))
                    # print('added as number')
                    continue
                except (KeyError, AttributeError):
                    # print(f'new {already_number}')
                    symbol_address[already_number] = [(str(already_number))]
                    all_values.append(str(already_number))
                    # print('added as number')
                continue
            except ValueError:
                # print('testing as alpha')
                if value_ in goto_instructions.values() and value_ not in all_values:
                    for key___, value___ in goto_instructions.items():
                        if value_ == value___:
                            symbol_address[key___] = [str(value_)]
                            all_values.append(str(value_))
                            # print(f'{value_} was goto, added at {key___}')
                            continue
                else:
                    if count in symbol_address.keys():
                        count += 1
                        continue
                    else:
                        symbol_address[count] = [str(value_)]
                        count += 1
                        all_values.append(str(value_))
                        print('was new')
                        continue
    # print('xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx', goto_instructions)
    # print('xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx', symbol_address)
    return symbol_address


def symbol_module(symbol_address, a_instructions):
    print('Translating a-instructions to binary...')
    address_binary = {}  # dict to contain line number:binary address entries
    for key, value in a_instructions.items():  # iterate through a-instruction items
        for key_, value_ in symbol_address.items():  # iterate through symbol table items
            # print('looking', value, 'in', value_)
            if value in value_:  # Check this later. For larger programs like PongL, this will encounter if 0 in 200, which is True but should be False? messing up everything.
                address_binary[key] = bin(key_).replace('0b', '').zfill(16)  # removes 0b from binary and add leading 0s
                break
    print('Translated a-instructions')
    return address_binary


def code_module(c_instructions):
    print('Translating c-instructions to binary...')
    comp_0_dict = {'0': '101010', '1': '111111', '-1': '111010', 'D': '001100', 'A': '110000', '!D': '001101', '!A': '110001', '-D': '001101', '-A': '110011', 'D+1': '011111', 'A+1': '110111', 'D-1': '001110', 'A-1': '110010', 'D+A': '000010', 'D-A': '010011', 'A-D': '000111', 'D&A': '000000', 'D|A': '010101'}
    comp_1_dict = {'M': '110000', '!M': '110001', '-M': '110011', 'M+1': '110111', 'M-1': '110010', 'D+M': '000010', 'D-M': '010011', 'M-D': '000111', 'D&M': '000000', 'D|M': '010101'}
    dest_dict = {'M': '001', 'D': '010', 'MD': '011', 'A': '100', 'AM': '101', 'AD': '110', 'AMD': '111'}
    jump_dict = {'JGT': '001', 'JEQ': '010', 'JGE': '011', 'JLT': '100', 'JNE': '101', 'JLE': '110', 'JMP': '111'}
    c_instructions_binary = {}
    for key, value in c_instructions.items():
        binary = '111'
        if ';' not in value:  # jump = null
            char_position = value.find('=')
            left_side = value[0:char_position]
            right_side = value[char_position+1:]
            if right_side in comp_0_dict.keys():
                binary = binary + '0'
                for key_, value_ in comp_0_dict.items():
                    if right_side == key_:
                        binary = binary + value_
            elif right_side in comp_1_dict.keys():
                binary = binary + '1'
                for key_, value_ in comp_1_dict.items():
                    if right_side == key_:
                        binary = binary + value_
            for key__, value__ in dest_dict.items():
                if left_side == key__:
                    binary = binary + value__
            binary = binary + '000'
            c_instructions_binary[key] = binary
        elif '=' not in value:  # dest = null
            char_position = value.find(';')
            left_side = value[0:char_position]
            right_side = value[char_position+1:]
            if left_side in comp_0_dict.keys():
                binary = binary + '0'
                for key_, value_ in comp_0_dict.items():
                    if left_side == key_:
                        binary = binary + value_
            elif left_side in comp_1_dict.keys():
                binary = binary + '1'
                for key_, value_ in comp_1_dict.items():
                    if left_side == key_:
                        binary = binary + value_
            binary = binary + '000'
            for key__, value__ in jump_dict.items():
                if right_side == key__:
                    binary = binary + value__
            c_instructions_binary[key] = binary
    print('Translated c-instructions')
    return c_instructions_binary


def output_module(input_file, a_instructions, c_instructions):
    print('Writing hack file...')
    a = a_instructions
    c = c_instructions
    c.update(a)
    dot_position = input_file.find('.')
    file_name = input_file[:dot_position]
    output_file = open(f'{file_name}.hack', 'w')
    for key, value in sorted(c.items()):
        output_file.write(f'{value}\n')
    output_file.close()
    print('Output file written')


def main(file):
    a_instructions, c_instructions, goto_instructions = parser_module(file)
    symbol_address = symbol_table(a_instructions, goto_instructions)
    address_binary = symbol_module(symbol_address, a_instructions)
    c_instructions_binary = code_module(c_instructions)
    output_module(args.file, address_binary, c_instructions_binary)
    print('Done!')


# This handles the command line input
parser = argparse.ArgumentParser(description='This program will convert a valid asm file into a hack file')
parser.add_argument('file', help='Input file in asm format')
args = parser.parse_args()

# Execute main program
main(args.file)
