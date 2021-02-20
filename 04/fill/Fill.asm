// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Fill.asm

// Runs an infinite loop that listens to the keyboard input.
// When a key is pressed (any key), the program blackens the screen,
// i.e. writes "black" in every pixel;
// the screen should remain fully black as long as the key is pressed. 
// When no key is pressed, the program clears the screen, i.e. writes
// "white" in every pixel;
// the screen should remain fully clear as long as no key is pressed.

// Put your code here.

(RESET)			// This loops resets a counter to 0
@COUNT			// Then it decides which screen loop to jump to if a key is pressed or not
M=0
@KBD
D=M
@SCREENON
D;JGT
@SCREENOFF
D;JEQ


(SCREENON)		// This loop turns on all screen pixels starting at SCREEN address
@COUNT
D=M
@8192
D=D-A
@RESET			// Jump to RESET if the last screen pixel, address SCREEN + 8192, has been reached
D;JEQ
@COUNT
D=M
@SCREEN			// The rest of this loop sets a value of -1 (constant 1)
D=A+D			// to each screen address starting at SCREEN
A=D
M=-1
@COUNT
M=M+1
@SCREENON
0;JMP

(SCREENOFF)		// This loop turns off all screen pixels starting at SCREEN address
@COUNT
D=M
@8192
D=D-A
@RESET			// Jump to RESET if the last screen pixel, address SCREEN + 8192, has been reached
D;JEQ
@COUNT
D=M
@SCREEN			// The rest of this loop sets a value of 0
D=A+D			// to each screen address starting at SCREEN
A=D
M=0
@COUNT
M=M+1
@SCREENOFF
0;JMP



