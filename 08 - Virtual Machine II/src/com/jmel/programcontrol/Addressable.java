package com.jmel.programcontrol;

public class Addressable {

    // Function to return Hack symbol depending on vm segment name
    public static String getSegmentSymbol(String segment) {
        return switch (segment) {
            case "local" -> "LCL";
            case "argument" -> "ARG";
            case "temp" -> "5";
            case "that" -> "THAT";
            case "this" -> "THIS";
            // if a segment is not recognized return symbol for RAM[15], a general purpose address
            default -> "R15";
        };
    }

    public static String createLabel(String label) {
        return "(" + label + ")\n";
    }

    public static String jumpAssembly(String jumpStatement, String label) {
        return switch (jumpStatement) {
            case "if-goto" -> "@SP\n"
                    + "M=M-1\n"
                    + "@SP\n"
                    + "A=M\n"
                    + "D=M\n"
                    + "@" + label + "\n"
                    + "D;JNE\n";
            case "goto" -> "@" + label + "\n"
                    + "0;JMP\n";
            default -> "// jump statement not implemented";
        };
    }
}
