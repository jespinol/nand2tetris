package com.jmel.programcontrol;

import com.jmel.stackarithmetic.Push;

import java.util.ArrayList;

public class Functions extends Addressable {

    static int returnCounter = 0;
    static ArrayList<Integer> returnIndexes = new ArrayList<>();

    public static String write(String[] line) {
        switch (line[0]) {
            case "function":
                return functionAssembly(line[1], Integer.parseInt(line[2]));
            case "call":
                // every time a function is called a unique return address label is necessary
                // the return label format is ClassName.functionName$ret.index
                // here indexCounter is increased every time a function is called
                // the value of indexCounter is associated to the return label of function in question
                returnCounter++;
                // the value of indexCounter is added to returnIndexes
                // the last value in the ArrayList always refers to the return of the current function being executed
                returnIndexes.add(returnCounter);
                return callAssembly(line[1], Integer.parseInt(line[2]));
            case "return":
                String output = returnAssembly();
                try {
                    // once a return is complete the last index in returnIndexes is no longer needed
                    returnIndexes.remove(returnIndexes.size() - 1);
                } catch (IndexOutOfBoundsException ignored) {
                    // normally a return would occur after a function call is complete
                    // this error will not occur because at least one index would exist in returnIndexes
                    // however, one of the test files involves a vm file with a return without a call
                    // this try catch takes care of that edge scenario
                }
                return output;
            default:
                return "// function command not implemented\n";
        }
    }

    public static String callAssembly(String functionName, int argNum) {
        String returnAddress = functionName + "$ret." + returnCounter;
        return saveBaseAddressToStack(returnAddress)// push return Address
                // push LCL
                + saveBaseAddressToStack("LCL")
                // push ARG
                + saveBaseAddressToStack("ARG")
                // push THIS
                + saveBaseAddressToStack("THIS")
                // push THAT
                + saveBaseAddressToStack("THAT")
                // arg = sp - 5 - nArgs
                + repositionBaseAddress("ARG", "SP", argNum)
                // lcl = sp
                + repositionBaseAddress("LCL", "SP", argNum)
                // goto functionName
                + Addressable.jumpAssembly("goto", functionName)
                // (returnAddress)
                + Addressable.createLabel(returnAddress);
    }

    public static String functionAssembly(String functionName, int argNum) {
        StringBuilder pushArgNum = new StringBuilder();
        for (int i = 0; i < argNum; i++) {
            pushArgNum.append(Push.write("local", String.valueOf(i), ""));
        }

        // (functionName)
        // push 0 argNum times
        return Addressable.createLabel(functionName)
                + pushArgNum;
    }

    public static String returnAssembly() {
        // R13 is used to store the endFrame of the current function
        // R14 is used to store the return address
        return repositionBaseAddress("R13", "LCL", 0) // endFrame = LCL
                // retAddr = *(endFrame - 5)
                + restoreCallerAddress("R14", 5)
                // *ARG = pop()
                + "@SP\n"
                + "M=M-1\n"
                + "@SP\n"
                + "A=M\n"
                + "D=M\n"
                + "@ARG\n"
                + "A=M\n"
                + "M=D\n"
                // SP = ARG + 1
                + "@ARG\n"
                + "A=M\n"
                + "D=A+1\n"
                + "@SP\n"
                + "M=D\n"
                // THAT = *(endFrame - 1)
                + restoreCallerAddress("THAT", 1)
                // THIS = *(endFrame - 2)
                + restoreCallerAddress("THIS", 2)
                // ARG = *(endFrame - 3)
                + restoreCallerAddress("ARG", 3)
                // LCL = *(endFrame - 4)
                + restoreCallerAddress("LCL", 4)
                // goto retAddr
                + "@R14\n"
                + "A=M\n"
                + "0;JMP\n";
    }

    public static String saveBaseAddressToStack(String segment) {
        // push the selected segment address to the stack
        return "@" + segment + "\n"
                + "D=M\n"
                + "@SP\n"
                + "A=M\n"
                + "M=D\n"
                + "@SP\n"
                + "M=M+1\n";
    }

    public static String repositionBaseAddress(String destinationSegment, String originSegment, int argNum) {
        // sets the address of destinationSegment to that of the originSegment
        // if the destinationSegment is ARG, there's an offset of argNum
        if ("ARG".equals(destinationSegment)) {
            return "@5\n"
                    + "D=A\n"
                    + "@SP\n"
                    + "D=M-D\n"
                    + "@" + argNum + "\n"
                    + "D=D-A\n"
                    + "@ARG\n"
                    + "M=D\n";
        }
        return "@" + originSegment + "\n"
                + "D=M\n"
                + "@" + destinationSegment + "\n"
                + "M=D\n";
    }

    public static String restoreCallerAddress(String targetSegment, int endFrameOffset) {
        // R13 stores the address of endFrame, i.e. the value of LCL of the current call
        // R15 is used to store temporary values
        return "@" + endFrameOffset + "\n"
                + "D=A\n"
                + "@R13\n"
                + "D=M-D\n"
                + "@R15\n"
                + "M=D\n"
                + "@R15\n"
                + "A=M\n"
                + "D=M\n"
                + "@" + targetSegment + "\n"
                + "M=D\n";
    }
}
