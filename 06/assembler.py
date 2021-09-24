import argparse


def make_list(dictionary):  # this is will generate a list of strings corresponding to individual values in a dictionary
    dict_list = []
    for k, v in dictionary.items():
        for x in v:
            dict_list.append(x)
    return dict_list


def parser_module(file):
    print('Parsing asm file...')
    current_file = open(file, 'r')
    a_instructions = {}  # here and line below, a- and c-instructions will be split in two separate dictionaries
    c_instructions = {}
    goto_instructions = {}
    count = 0  # this counter will preserve the order of a- and c-instructions in the separate dictionaries
    for line in current_file:
        if not line.startswith('/') and len(line) > 2:
            if '@' in line:  # the logic below applies to the elif and else blocks below also
                white_left = line.lstrip()  # non-L programs have leading white spaces, this takes care of them
                white_right = white_left.rstrip()  # removes trailing white spaces
                no_at = white_right[1:]  # removes @
                first_word = no_at.split()[0]  # non-L programs can have comments, but in each instance the part of interest is the "first word"
                a_instructions[count] = first_word  # create a key:value where the key is the line number (current count) and the value is the a-instruction
                count += 1
            elif line.startswith('('):
                white_left = line.lstrip()
                white_right = white_left.rstrip()
                goto = white_right[1:-1]
                try:  # sometimes goto blocks have multiple names (so they'll need the same address), if another goto at address, append new goto to it
                    goto_instructions[count].append(goto)
                except KeyError:  # if goto being tested is new create a key:value pair
                    goto_instructions[count] = [goto]
            else:
                white_left = line.lstrip()
                first_word = white_left.split()[0]
                c_instructions[count] = first_word
                count += 1
    print('Parsing done')
    return a_instructions, c_instructions, goto_instructions


def symbol_table(a_instructions, goto_instructions):
    print('Preparing a-instructions...')
    # the dictionary below contains initially only reserved symbols. New symbols will be added to it
    symbol_address = {0: ['R0', '0', 'SP'], 1: ['R1', '1', 'LCL'], 2: ['R2', '2', 'ARG'], 3: ['R3', '3', 'THIS'], 4: ['R4', '4', 'THAT'], 5: ['R5', '5'], 6: ['R6', '6'], 7: ['R7', '7'], 8: ['R8', '8'], 9: ['R9', '9'], 10: ['R10', '10'], 11: ['R11', '11'], 12: ['R12', '12'], 13: ['R13', '13'], 14: ['R14', '14'], 15: ['R15', '15'], 16384: ['16384', 'SCREEN'], 24576: ['24576', 'KBD']}
    all_values = make_list(symbol_address)  # This is to keep track of values that are already in the symbol_address dictionary
    all_goto = make_list(goto_instructions)  # This is to keep track of existing goto instructions
    count = 16  # to keep track of usable address
    for key, value in a_instructions.items():
        if value in all_values:
            continue  # skip to next a-instruction if already in all_values list
        else:
            if value in all_goto:  # if a-instruction is goto do the following
                for key_, value_ in goto_instructions.items():
                    if value in value_:  # the correct address for the goto instruction is stored as its key in the goto_instructions dictionary
                        try:  # do this if more than one goto instruction refer to the same address
                            current_value = symbol_address[key_]
                            current_value.append(value)
                            symbol_address[key_] = current_value
                            all_values.append(value)  # once goto added to symbol_address skip next time
                        except KeyError:  # if goto is new
                            symbol_address[key_] = [value]
                            all_values.append(value)
            else:
                try:  # if a-instruction is a number and not a symbol do this
                    number = int(value)
                    if number in symbol_address.keys():  # if the address is already occupied
                        current_value = symbol_address[number]
                        current_value.append(str(number))
                        symbol_address[number] = current_value
                        all_values.append(value)
                    else:
                        symbol_address[number] = [value]
                        all_values.append(value)
                except ValueError:  # if the a-instruction is a symbol instead do this
                    if count in symbol_address.keys():  # if address is already occupied
                        current_value = symbol_address[count]
                        current_value.append(value)
                        symbol_address[count] = current_value
                        count += 1
                        all_values.append(value)
                    else:
                        symbol_address[count] = [value]
                        count += 1
                        all_values.append(value)
    return symbol_address


def symbol_module(symbol_address, a_instructions):
    print('Translating a-instructions to binary...')
    address_binary = {}  # dict to contain line_number:binary address entries
    for key, value in a_instructions.items():  # iterate through a-instruction items
        for key_, value_ in symbol_address.items():  # iterate through symbol_address items
            if value in value_:  # When a_instruction is found in the symbol_address dictionary store it in address_binary as line_number:binary
                address_binary[key] = bin(key_).replace('0b', '').zfill(16)  # removes 0b from binary and add leading 0s
                break
    print('Translated a-instructions')
    return address_binary


def code_module(c_instructions):
    print('Translating c-instructions to binary...')
    # the following dictionaries contain comp, dest, or jump fields and their binary translation
    comp_0_dict = {'0': '101010', '1': '111111', '-1': '111010', 'D': '001100', 'A': '110000', '!D': '001101', '!A': '110001', '-D': '001101', '-A': '110011', 'D+1': '011111', 'A+1': '110111', 'D-1': '001110', 'A-1': '110010', 'D+A': '000010', 'D-A': '010011', 'A-D': '000111', 'D&A': '000000', 'D|A': '010101'}
    comp_1_dict = {'M': '110000', '!M': '110001', '-M': '110011', 'M+1': '110111', 'M-1': '110010', 'D+M': '000010', 'D-M': '010011', 'M-D': '000111', 'D&M': '000000', 'D|M': '010101'}
    dest_dict = {'M': '001', 'D': '010', 'MD': '011', 'A': '100', 'AM': '101', 'AD': '110', 'AMD': '111'}
    jump_dict = {'JGT': '001', 'JEQ': '010', 'JGE': '011', 'JLT': '100', 'JNE': '101', 'JLE': '110', 'JMP': '111'}
    # this dictionary will store line_number:binary for c-instructions
    c_instructions_binary = {}
    for key, value in c_instructions.items():
        binary = '111'  # c-instructions always start with 111
        if ';' not in value:  # jump = null
            char_position = value.find('=')  # split the c-instruction if left and right side around '='
            left_side = value[0:char_position]
            right_side = value[char_position+1:]
            if right_side in comp_0_dict.keys():
                binary = binary + '0'  # certain comp fields have 0 as the 'a' bit
                for key_, value_ in comp_0_dict.items():
                    if right_side == key_:
                        binary = binary + value_  # find the translation for the right side and add to binary
            elif right_side in comp_1_dict.keys():
                binary = binary + '1'  # certain comp fields have 1 as the 'a' bit
                for key_, value_ in comp_1_dict.items():
                    if right_side == key_:
                        binary = binary + value_  # find the translation for the right side and add to binary
            for key__, value__ in dest_dict.items():
                if left_side == key__:
                    binary = binary + value__  # find the translation for the left side and add to binary
            binary = binary + '000'  # for these c-instructions the jump field (j1j2j3) is empty
            c_instructions_binary[key] = binary
        elif '=' not in value:  # dest = null
            char_position = value.find(';')  # split the c-instruction if left and right side around ';'
            left_side = value[0:char_position]
            right_side = value[char_position+1:]
            if left_side in comp_0_dict.keys():  # similar logic as above: find left side in comp_0 or comp_1, add null dest, then find jump in jump_dict
                binary = binary + '0'
                for key_, value_ in comp_0_dict.items():
                    if left_side == key_:
                        binary = binary + value_
            elif left_side in comp_1_dict.keys():
                binary = binary + '1'
                for key_, value_ in comp_1_dict.items():
                    if left_side == key_:
                        binary = binary + value_
            binary = binary + '000'  # for these c-instructions the dest field (d1d2d3) is empty
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
    c.update(a)  # this adds the contest of c to a
    # the code below will name the output file as the input file but with the 'hack' extension
    dot_position = input_file.find('.')
    file_name = input_file[:dot_position]
    output_file = open(f'{file_name}.hack', 'w')
    # add binary translations line by line in the same order as in the input file
    for key, value in sorted(c.items()):
        output_file.write(f'{value}\n')
    output_file.close()
    print('Output file written')


def main(file):
    # split input into a- and c- instructions, also record in which line the goto-instructions are
    a_instructions, c_instructions, goto_instructions = parser_module(file)
    # build a hash table linking symbols and goto-instructions with the correct address
    symbol_address = symbol_table(a_instructions, goto_instructions)
    # convert a-instructions into binary
    address_binary = symbol_module(symbol_address, a_instructions)
    # convert a-instructions into binary
    c_instructions_binary = code_module(c_instructions)
    # write an output hack file
    output_module(args.file, address_binary, c_instructions_binary)
    print('Done!')


# This handles the command line input
parser = argparse.ArgumentParser(description='This program will convert a valid asm file into a hack file. Usage: >assembler.py INPUT')
parser.add_argument('file', help='Input file in asm format')
args = parser.parse_args()

# Execute main program
main(args.file)
