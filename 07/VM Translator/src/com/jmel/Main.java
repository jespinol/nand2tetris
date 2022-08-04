package com.jmel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner arguments = new Scanner(System.in);

        System.out.print("Enter path to input vm file: ");
        String inputFileName = arguments.nextLine();

        try {
            BufferedReader reader;
            reader = new BufferedReader(new FileReader(inputFileName));
            setCustomBaseAddresses(); // this might be used later on
            processFile(reader, inputFileName);
        } catch (IOException | NoSuchFieldException e) {
            System.out.println("Input file does not exist or is not a valid vm file.");
        }

    }

    private static void setCustomBaseAddresses() {
        Addressable.segmentPointers.put("sp", 0);
        Addressable.segmentPointers.put("local", 1);
        Addressable.segmentPointers.put("argument", 2);
        Addressable.segmentPointers.put("this", 3);
        Addressable.segmentPointers.put("that", 4);

        Addressable.segmentPointers.put("temp", 5);

        Addressable.segmentPointers.put("static", 16);
    }

    private static void processFile(BufferedReader reader, String inputFileName) throws IOException, NoSuchFieldException {
        // gets the file name without vm extension
        String outputFileName = inputFileName.substring(inputFileName.lastIndexOf("/") + 1, inputFileName.lastIndexOf("."));

        // creates an output file with asm extension
        FileWriter writer = new FileWriter(outputFileName + ".asm");

        // read the input file line by line
        String line = reader.readLine();
        while (line != null) {
            // ignore empty lines, comments, lines with only space
            if (line.length() > 0 && !line.startsWith("/") && !line.startsWith(" ")) {
                // adds a comment to the asm file containing the original vm statement
                writer.write("// " + line + "\n");
                // splits the vm statement in three parts: push/pop segment i
                String[] lineArr = line.split(" ");
                switch (lineArr[0]) {
                    case "push":
                        writer.write(Push.write(lineArr[1], lineArr[2], outputFileName));
                        break;
                    case "pop":
                        writer.write(Pop.write(lineArr[1], lineArr[2], outputFileName));
                        break;
                    default:
                        writer.write(Arithmetic.write(lineArr[0]));
                }
            }
            line = reader.readLine();
        }
        reader.close();
        writer.close();
    }
}
