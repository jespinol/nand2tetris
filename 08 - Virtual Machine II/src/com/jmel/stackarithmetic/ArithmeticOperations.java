package com.jmel.stackarithmetic;

import com.jmel.programcontrol.Addressable;

public class ArithmeticOperations extends Addressable {

    // to be used with equality operations
    public static int equalityCounter = -1;

    public static String write(String operation) {
        return switch (operation) {
            case "add" -> additionAssembly();
            case "sub" -> subtractionAssembly();
            case "neg" -> negationAssembly();
            case "eq", "gt", "lt" -> equalityAssembly(operation);
            case "and", "or", "not" -> bitWiseOperationAssembly(operation);
            default -> "// arithmetic operation not implemented\n";
        };
    }

    private static String additionAssembly() {
        return """
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
                """;
    }

    private static String subtractionAssembly() {
        return """
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
                """;
    }

    private static String negationAssembly() {
        return """
                @SP
                M=M-1
                @SP
                A=M
                D=M
                M=-D
                @SP
                M=M+1
                """;
    }

    private static String equalityAssembly(String equalityOperation) {
        // counter to append to label used here so that each label is unique
        // increase counter each time before return
        equalityCounter++;

        // stores jump assembly dest;jump statement in the String variable depending on the operation needed
        String equalityJumpStatement = equalityOperation.equals("eq") ? "D;JEQ" : equalityOperation.equals("gt") ? "D;JGT" : "D;JLT";

        return "@SP\n"
                + "M=M-1\n"
                + "@SP\n"
                + "A=M\n"
                + "D=M\n"
                + "@SP\n"
                + "M=M-1\n"
                + "@SP\n"
                + "A=M\n"
                + "D=M-D\n"
                + "@TRUE." + equalityCounter + "\n"
                + equalityJumpStatement + "\n"
                + "@SP\n"
                + "A=M\n"
                + "M=0\n"
                + "@END." + equalityCounter + "\n"
                + "0;JMP\n"
                + "\n"
                + "(TRUE." + equalityCounter + ")\n"
                + "@SP\n"
                + "A=M\n"
                + "M=-1\n"
                + "\n"
                + "(END." + equalityCounter + ")\n"
                + "@SP\n"
                + "M=M+1\n"
                + "\n";
    }

    private static String bitWiseOperationAssembly(String bitWiseOperation) {
        // not
        if (bitWiseOperation.equals("not")) {
            return """
                    @SP
                    M=M-1
                    @SP
                    A=M
                    D=M
                    M=!D
                    @SP
                    M=M+1
                    """;
        }

        // and, or
        // stores the assembly comp statement in String variable depending on the operation needed
        String binaryBitWiseOperation = bitWiseOperation.equals("and") ? "D&M" : "D|M";

        return "@SP\n"
                + "M=M-1\n"
                + "@SP\n"
                + "A=M\n"
                + "D=M\n"
                + "@SP\n"
                + "M=M-1\n"
                + "@SP\n"
                + "A=M\n"
                + "D=" + binaryBitWiseOperation + "\n"
                + "@SP\n"
                + "A=M\n"
                + "M=D\n"
                + "@SP\n"
                + "M=M+1\n";
    }
}
