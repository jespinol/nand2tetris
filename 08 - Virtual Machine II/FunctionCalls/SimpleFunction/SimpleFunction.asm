// function SimpleFunction.test 2
(SimpleFunction.test)
@0
D=A
@LCL
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
@1
D=A
@LCL
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
// push local 0
@0
D=A
@LCL
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
// push local 1
@1
D=A
@LCL
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
// add
@SP
M=M-1
@SP
A=M
D=M
@SP
M=M-1
@SP
A=M
M=M+D
@SP
M=M+1
// not
@SP
M=M-1
@SP
A=M
D=M
M=!D
@SP
M=M+1
// push argument 0
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
// add
@SP
M=M-1
@SP
A=M
D=M
@SP
M=M-1
@SP
A=M
M=M+D
@SP
M=M+1
// push argument 1
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
// sub
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
// return
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
