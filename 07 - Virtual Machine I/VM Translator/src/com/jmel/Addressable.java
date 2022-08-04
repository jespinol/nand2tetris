package com.jmel;

import java.util.HashMap;

public class Addressable {
    // Map with custom base addresses, might be used later on
    public static HashMap<String, Integer> segmentPointers = new HashMap<>();

    // Function to return Hack symbol depending on vm segment name
    public static String getSegmentSymbol(String segment) {
        switch (segment) {
            case "local":
                return "LCL";
            case "argument":
                return "ARG";
            case "temp":
                return "5";
            case "that":
                return "THAT";
            case "this":
                return "THIS";
            default:
                // if a segment is not recognized return symbol for RAM[15], a general purpose address
                return "R15";
        }
    }

}
