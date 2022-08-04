// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Mult.asm

// Multiplies R0 and R1 and stores the result in R2.
// (R0, R1, R2 refer to RAM[0], RAM[1], and RAM[2], respectively.)
//
// This program only needs to handle arguments that satisfy
// R0 >= 0, R1 >= 0, and R0*R1 < 32768.

// Put your code here.

// The strategy is to add R0 R1 times
// Each time R1 is decreased by 1 until it reaches 0
// The final value is stored in R2

@total			// this will store the final result while the program is running
M=0				// set variable total to 0
@R1
D=M				
@multiplicand
M=D				// the initial value of R1 stored in variable multiplicand

(LOOP)
@multiplicand
D=M				// retrieves the current value of multiplicand
@END
D;JEQ			// jump to END if D, i.e. multiplicand, is 0
@R0
D=M				// stores value of R0
@total
M=M+D			// adds R0 to total
@multiplicand
M=M-1			// decreases multiplicand by 1
@LOOP
0;JMP			// restarts the loop

(END)
@total
D=M				// retrieves total
@R2
M=D				// stores total into R2