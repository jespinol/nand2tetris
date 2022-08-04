package com.jmel;

public class Push extends Addressable {
    public static String write(String segment, String i, String fileName) {
        // general pseudocode for pop statements
        // baseAddress = segmentPointer + i
        // *SP = *baseAddress
        // SP++

        switch (segment) {
            case "static":
                return pushStaticAssembly(i, fileName);
            case "constant":
                return pushConstantAssembly(i);
            case "temp":
                return pushTempAssembly(segment, i);
            case "pointer":
                return pushPointerAssembly(i);
            default:
                return pushOtherAssembly(segment, i);
        }
    }

    private static String pushStaticAssembly(String i, String fileName) {
        return "@" + fileName + "." + i + "\n"
                + "A=M\n"
                + "D=A\n"
                + "@SP\n"
                + "A=M\n"
                + "M=D\n"
                + "@SP\n"
                + "M=M+1\n";
    }

    private static String pushConstantAssembly(String i) {
        return "@" + i + "\n"
                + "D=A\n"
                + "@SP\n"
                + "A=M\n"
                + "M=D\n"
                + "@SP\n"
                + "M=M+1\n";
    }

    private static String pushTempAssembly(String segment, String i) {
        // gets the symbol for the given segment as defined in the Addressable class
        String segmentSymbol = getSegmentSymbol(segment);

        return "@" + segmentSymbol + "\n"
                + "D=A\n"
                + "@" + i + "\n"
                + "D=D+A\n"
                + "@R13\n"
                + "M=D\n"
                + "@R13\n"
                + "A=M\n"
                + "D=M\n"
                + "@SP\n"
                + "A=M\n"
                + "M=D\n"
                + "@SP\n"
                + "M=M+1\n";
    }

    private static String pushPointerAssembly(String i) {
        // checks whether the value of i corresponds to THIS or THAT (0 or 1)
        String selectedSegment = i.equals("0") ? "THIS" : "THAT";

        return "@" + selectedSegment + "\n"
                + "A=M\n"
                + "D=A\n"
                + "@SP\n"
                + "A=M\n"
                + "M=D\n"
                + "@SP\n"
                + "M=M+1\n";
    }

    private static String pushOtherAssembly(String segment, String i) {
        // gets the symbol for the given segment as defined in the Addressable class
        String segmentSymbol = getSegmentSymbol(segment);

        return "@" + i + "\n"
                + "D=A\n"
                + "@" + segmentSymbol + "\n"
                + "A=M\n"
                + "D=D+A\n"
                + "@R13\n"
                + "M=D\n"
                + "@R13\n"
                + "A=M\n"
                + "D=M\n"
                + "@SP\n"
                + "A=M\n"
                + "M=D\n"
                + "@SP\n"
                + "M=M+1\n";
    }
}