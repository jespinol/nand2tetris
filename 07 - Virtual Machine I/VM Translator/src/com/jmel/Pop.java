package com.jmel;

public class Pop extends Addressable {
    public static String write(String segment, String i, String fileName) {
        // general pseudocode for pop statements
        // baseAddress = segmentPointer + i
        // SP--
        // *baseAddress = *SP

        switch (segment) {
            case "static":
                return popStaticAssembly(i, fileName);
            case "temp":
                return popTempAssembly(segment, i);
            case "pointer":
                return popPointerAssembly(i);
            default:
                return popOtherAssembly(segment, i);
        }
    }

    private static String popStaticAssembly(String i, String fileName) {
        return "@SP\n"
                + "M=M-1\n"
                + "A=M\n"
                + "D=M\n"
                + "@" + fileName + "." + i + "\n"
                + "M=D\n";
    }

    private static String popTempAssembly(String segment, String i) {
        // gets the symbol for the given segment as defined in the Addressable class
        String segmentSymbol = getSegmentSymbol(segment);

        return "@" + segmentSymbol + "\n"
                + "D=A\n"
                + "@" + i + "\n"
                + "D=D+A\n"
                + "@R13\n"
                + "M=D\n"
                + "@SP\n"
                + "M=M-1\n"
                + "@SP\n"
                + "A=M\n"
                + "D=M\n"
                + "@R13\n"
                + "A=M\n"
                + "M=D\n";
    }

    private static String popPointerAssembly(String i) {
        // checks whether the value of i corresponds to THIS or THAT (0 or 1)
        String selectedSegment = i.equals("0") ? "THIS" : "THAT";

        return "@SP\n"
                + "M=M-1\n"
                + "@SP\n"
                + "A=M\n"
                + "D=M\n"
                + "@" + selectedSegment + "\n"
                + "M=D\n";
    }

    private static String popOtherAssembly(String segment, String i) {
        // gets the symbol for the given segment as defined in the Addressable class
        String segmentSymbol = getSegmentSymbol(segment);

        return "@" + i + "\n"
                + "D=A\n"
                + "@" + segmentSymbol + "\n"
                + "A=M\n"
                + "D=D+A\n"
                + "@R13\n"
                + "M=D\n"
                + "@SP\n"
                + "M=M-1\n"
                + "@SP\n"
                + "A=M\n"
                + "D=M\n"
                + "@R13\n"
                + "A=M\n"
                + "M=D\n";
    }
}
