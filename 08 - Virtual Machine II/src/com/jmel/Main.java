package com.jmel;

import com.jmel.programcontrol.Addressable;
import com.jmel.programcontrol.Functions;
import com.jmel.stackarithmetic.ArithmeticOperations;
import com.jmel.stackarithmetic.Pop;
import com.jmel.stackarithmetic.Push;

import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner arguments = new Scanner(System.in);

        System.out.print("Enter path to a vm file or directory: ");
        String inputString = arguments.nextLine();


        BufferedReader reader = null;
        FileWriter writer = null;

        try {
            boolean directoryProvided = !inputString.endsWith(".vm");

            File directory = new File(directoryProvided ? inputString : inputString.substring(0, inputString.lastIndexOf("/")));
            File[] directoryFiles = directory.listFiles();

            // stores the parent directory name to be used as the output file name
            String path = directory.getAbsolutePath();
            String outputFileName = directoryProvided ? path.substring(path.lastIndexOf("/") + 1) : inputString.substring(inputString.lastIndexOf("/") + 1, inputString.lastIndexOf("."));

            // creates an output file with asm extension, file is saved in the input directory
            writer = new FileWriter(path + "/" + outputFileName + ".asm");

            if (directoryProvided && directoryFiles != null) {
                writer.write(bootstrapCodeAssembly());
                for (File file : directoryFiles) {
                    if (file.isFile() && file.getName().endsWith(".vm")) {
                        reader = new BufferedReader(new FileReader(file));
                        processFile(reader, writer, file.getName().substring(0, file.getName().lastIndexOf(".")));
                    }

                }
            } else {
                reader = new BufferedReader(new FileReader(inputString));
                processFile(reader, writer, outputFileName);
            }
        } catch (IOException | NoSuchFieldException e) {
            System.out.println("Input file does not exist or is not a valid vm file.");
        }

        assert reader != null;
        reader.close();
        writer.close();
    }

    private static String bootstrapCodeAssembly() {
        return """
                @256
                D=A
                @R0
                M=D
                """
                + Functions.callAssembly("Sys.init", 0);
    }

    private static void processFile(BufferedReader reader, FileWriter writer, String outputFileName) throws IOException, NoSuchFieldException {
        String line = reader.readLine();

        while (line != null) {
            // ignores empty lines, stand-alone comments, lines with only space
            if (line.length() > 0 && !line.startsWith("/") && !line.startsWith(" ")) {

                // splits the vm statement in three parts: push/pop segment i
                // as a side effect keeps any in-line comments from the original vm file
                String[] lineArr = line.split(" ");

                // for each vm line adds a comment to the asm commands containing the original vm statement
                String commentVM = "//" + cleanUpLine(line) + "\n";
                writer.write(commentVM);

                // calls the appropriate function depending on the value of lineArr[0]
                switch (lineArr[0]) {
                    case "function", "return", "call" -> writer.write(Functions.write(lineArr));
                    case "push" -> writer.write(Push.write(lineArr[1], lineArr[2], outputFileName));
                    case "pop" -> writer.write(Pop.write(lineArr[1], lineArr[2], outputFileName));
                    case "label" -> writer.write(Addressable.createLabel(lineArr[1]));
                    case "goto", "if-goto" -> writer.write(Addressable.jumpAssembly(lineArr[0], lineArr[1]));
                    default -> writer.write(ArithmeticOperations.write(lineArr[0]));
                }
            }
            line = reader.readLine();
        }
    }

    private static String cleanUpLine(String line) {
        // removes in-line comments from the original vm file
        if (line.contains("  ")) {
            return line.substring(0, line.indexOf("  "));
        }
        return line;
    }
}

