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
(RESET)			// This loops resets a counter (i) to 0
@i				// Then it decides which screen loop to jump to if a key is pressed or not
M=0
@KBD
D=M
@SCREENON
D;JGT
@SCREENOFF
D;JEQ

(SCREENON)		// This loop turns on pixels starting at the SCREEN address if a key is pressed
@KBD			
D=M
@RESET			// Here it checks if a key is still pressed, if not jump to reset
D;JEQ
@i				// Counter, starts at 0
D=M
@SCREEN
D=A+D
A=D
M=-1			// Sets value constant 1 at SCREEN + i address
@i
M=M+1			// Adds 1 to i counter
@SCREENON
0;JMP			// Restarts this loop

(SCREENOFF)		// This loop turns off pixels starting at the SCREEN address if a key is pressed
@KBD
D=M
@RESET			// Here it checks if a key is still not pressed, if not jump to reset
D;JGT
@i				// Counter, starts at 0
D=M
@SCREEN
D=A+D
A=D
M=0				// Sets value constant 0 at SCREEN + i address
@i
M=M+1			// Adds 1 to i counter
@SCREENOFF
0;JMP			// Restarts this loop