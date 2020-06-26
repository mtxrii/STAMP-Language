package com.edavalos.stamp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public final class Main {
    public static void main(String[] args) throws IOException {
        if (args == null || args.length < 1) {
            System.out.println("Usage: ./Stamp <Filename> [arg0] [arg1] [arg2] [arg3]");
            return;
        }

        FileReader fileReader;
        try {
            fileReader = new FileReader(args[0]);
        }
        catch (IOException exception) {
            System.out.println("File " + args[0] + " not found.");
            return;
        }

        BufferedReader lineReader;
        try {
            lineReader = new BufferedReader(fileReader);

            String line = lineReader.readLine();
            int i = 1;
            while (line != null) {
                if (removeComments(line.trim()).length() <= 0) {
                    line = lineReader.readLine();
                    i++;
                    continue;
                }

                if (!Lexer.validateIndent(line, i, args[0])) {
                    break;
                }

                Lexer.processLine(removeComments(line));

                line = lineReader.readLine();
                i++;
            }
            lineReader.close();
        }
        catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static String removeComments(String code) {
        return code.split("//")[0];
    }

}
