@256
D=A
@R0
M=D
@Sys.init$ret.0
D=A
@SP
A=M
M=D
@SP
M=M+1
@LCL
D=M
@SP
A=M
M=D
@SP
M=M+1
@ARG
D=M
@SP
A=M
M=D
@SP
M=M+1
@THIS
D=M
@SP
A=M
M=D
@SP
M=M+1
@THAT
D=M
@SP
A=M
M=D
@SP
M=M+1
@5
D=A
@SP
D=M-D
@0
D=D-A
@ARG
M=D
@SP
D=M
@LCL
M=D
@Sys.init
0;JMP
(Sys.init$ret.0)
//function Class1.set 0
(Class1.set)
//push argument 0
@0
D=A
@ARG
A=M
D=D+A
@R13
M=D
@R13
A=M
D=M
@SP
A=M
M=D
@SP
M=M+1
//pop static 0
@SP
M=M-1
A=M
D=M
@Class1.0
M=D
//push argument 1
@1
D=A
@ARG
A=M
D=D+A
@R13
M=D
@R13
A=M
D=M
@SP
A=M
M=D
@SP
M=M+1
//pop static 1
@SP
M=M-1
A=M
D=M
@Class1.1
M=D
//push constant 0
@0
D=A
@SP
A=M
M=D
@SP
M=M+1
//return
@LCL
D=M
@R13
M=D
@5
D=A
@R13
D=M-D
@R15
M=D
@R15
A=M
D=M
@R14
M=D
@SP
M=M-1
@SP
A=M
D=M
@ARG
A=M
M=D
@ARG
A=M
D=A+1
@SP
M=D
@1
D=A
@R13
D=M-D
@R15
M=D
@R15
A=M
D=M
@THAT
M=D
@2
D=A
@R13
D=M-D
@R15
M=D
@R15
A=M
D=M
@THIS
M=D
@3
D=A
@R13
D=M-D
@R15
M=D
@R15
A=M
D=M
@ARG
M=D
@4
D=A
@R13
D=M-D
@R15
M=D
@R15
A=M
D=M
@LCL
M=D
@R14
A=M
0;JMP
//function Class1.get 0
(Class1.get)
//push static 0
@Class1.0
A=M
D=A
@SP
A=M
M=D
@SP
M=M+1
//push static 1
@Class1.1
A=M
D=A
@SP
A=M
M=D
@SP
M=M+1
//sub
@SP
M=M-1
@SP
A=M
D=M
@SP
M=M-1
@SP
A=M
M=M-D
@SP
M=M+1
//return
@LCL
D=M
@R13
M=D
@5
D=A
@R13
D=M-D
@R15
M=D
@R15
A=M
D=M
@R14
M=D
@SP
M=M-1
@SP
A=M
D=M
@ARG
A=M
M=D
@ARG
A=M
D=A+1
@SP
M=D
@1
D=A
@R13
D=M-D
@R15
M=D
@R15
A=M
D=M
@THAT
M=D
@2
D=A
@R13
D=M-D
@R15
M=D
@R15
A=M
D=M
@THIS
M=D
@3
D=A
@R13
D=M-D
@R15
M=D
@R15
A=M
D=M
@ARG
M=D
@4
D=A
@R13
D=M-D
@R15
M=D
@R15
A=M
D=M
@LCL
M=D
@R14
A=M
0;JMP
//function Sys.init 0
(Sys.init)
//push constant 6
@6
D=A
@SP
A=M
M=D
@SP
M=M+1
//push constant 8
@8
D=A
@SP
A=M
M=D
@SP
M=M+1
//call Class1.set 2
@Class1.set$ret.1
D=A
@SP
A=M
M=D
@SP
M=M+1
@LCL
D=M
@SP
A=M
M=D
@SP
M=M+1
@ARG
D=M
@SP
A=M
M=D
@SP
M=M+1
@THIS
D=M
@SP
A=M
M=D
@SP
M=M+1
@THAT
D=M
@SP
A=M
M=D
@SP
M=M+1
@5
D=A
@SP
D=M-D
@2
D=D-A
@ARG
M=D
@SP
D=M
@LCL
M=D
@Class1.set
0;JMP
(Class1.set$ret.1)
//pop temp 0 // Dumps the return value
@5
D=A
@0
D=D+A
@R13
M=D
@SP
M=M-1
@SP
A=M
D=M
@R13
A=M
M=D
//push constant 23
@23
D=A
@SP
A=M
M=D
@SP
M=M+1
//push constant 15
@15
D=A
@SP
A=M
M=D
@SP
M=M+1
//call Class2.set 2
@Class2.set$ret.2
D=A
@SP
A=M
M=D
@SP
M=M+1
@LCL
D=M
@SP
A=M
M=D
@SP
M=M+1
@ARG
D=M
@SP
A=M
M=D
@SP
M=M+1
@THIS
D=M
@SP
A=M
M=D
@SP
M=M+1
@THAT
D=M
@SP
A=M
M=D
@SP
M=M+1
@5
D=A
@SP
D=M-D
@2
D=D-A
@ARG
M=D
@SP
D=M
@LCL
M=D
@Class2.set
0;JMP
(Class2.set$ret.2)
//pop temp 0 // Dumps the return value
@5
D=A
@0
D=D+A
@R13
M=D
@SP
M=M-1
@SP
A=M
D=M
@R13
A=M
M=D
//call Class1.get 0
@Class1.get$ret.3
D=A
@SP
A=M
M=D
@SP
M=M+1
@LCL
D=M
@SP
A=M
M=D
@SP
M=M+1
@ARG
D=M
@SP
A=M
M=D
@SP
M=M+1
@THIS
D=M
@SP
A=M
M=D
@SP
M=M+1
@THAT
D=M
@SP
A=M
M=D
@SP
M=M+1
@5
D=A
@SP
D=M-D
@0
D=D-A
@ARG
M=D
@SP
D=M
@LCL
M=D
@Class1.get
0;JMP
(Class1.get$ret.3)
//call Class2.get 0
@Class2.get$ret.4
D=A
@SP
A=M
M=D
@SP
M=M+1
@LCL
D=M
@SP
A=M
M=D
@SP
M=M+1
@ARG
D=M
@SP
A=M
M=D
@SP
M=M+1
@THIS
D=M
@SP
A=M
M=D
@SP
M=M+1
@THAT
D=M
@SP
A=M
M=D
@SP
M=M+1
@5
D=A
@SP
D=M-D
@0
D=D-A
@ARG
M=D
@SP
D=M
@LCL
M=D
@Class2.get
0;JMP
(Class2.get$ret.4)
//label WHILE
(WHILE)
//goto WHILE
@WHILE
0;JMP
//function Class2.set 0
(Class2.set)
//push argument 0
@0
D=A
@ARG
A=M
D=D+A
@R13
M=D
@R13
A=M
D=M
@SP
A=M
M=D
@SP
M=M+1
//pop static 0
@SP
M=M-1
A=M
D=M
@Class2.0
M=D
//push argument 1
@1
D=A
@ARG
A=M
D=D+A
@R13
M=D
@R13
A=M
D=M
@SP
A=M
M=D
@SP
M=M+1
//pop static 1
@SP
M=M-1
A=M
D=M
@Class2.1
M=D
//push constant 0
@0
D=A
@SP
A=M
M=D
@SP
M=M+1
//return
@LCL
D=M
@R13
M=D
@5
D=A
@R13
D=M-D
@R15
M=D
@R15
A=M
D=M
@R14
M=D
@SP
M=M-1
@SP
A=M
D=M
@ARG
A=M
M=D
@ARG
A=M
D=A+1
@SP
M=D
@1
D=A
@R13
D=M-D
@R15
M=D
@R15
A=M
D=M
@THAT
M=D
@2
D=A
@R13
D=M-D
@R15
M=D
@R15
A=M
D=M
@THIS
M=D
@3
D=A
@R13
D=M-D
@R15
M=D
@R15
A=M
D=M
@ARG
M=D
@4
D=A
@R13
D=M-D
@R15
M=D
@R15
A=M
D=M
@LCL
M=D
@R14
A=M
0;JMP
//function Class2.get 0
(Class2.get)
//push static 0
@Class2.0
A=M
D=A
@SP
A=M
M=D
@SP
M=M+1
//push static 1
@Class2.1
A=M
D=A
@SP
A=M
M=D
@SP
M=M+1
//sub
@SP
M=M-1
@SP
A=M
D=M
@SP
M=M-1
@SP
A=M
M=M-D
@SP
M=M+1
//return
@LCL
D=M
@R13
M=D
@5
D=A
@R13
D=M-D
@R15
M=D
@R15
A=M
D=M
@R14
M=D
@SP
M=M-1
@SP
A=M
D=M
@ARG
A=M
M=D
@ARG
A=M
D=A+1
@SP
M=D
@1
D=A
@R13
D=M-D
@R15
M=D
@R15
A=M
D=M
@THAT
M=D
@2
D=A
@R13
D=M-D
@R15
M=D
@R15
A=M
D=M
@THIS
M=D
@3
D=A
@R13
D=M-D
@R15
M=D
@R15
A=M
D=M
@ARG
M=D
@4
D=A
@R13
D=M-D
@R15
M=D
@R15
A=M
D=M
@LCL
M=D
@R14
A=M
0;JMP
