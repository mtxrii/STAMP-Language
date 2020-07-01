package com.edavalos.stamp;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

public final class Main {
    public static String fileName;

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
            System.out.println("[ERR] File " + args[0] + " not found.");
            return;
        }

        BufferedReader lineReader;
        try {
            lineReader = new BufferedReader(fileReader);

            String line = lineReader.readLine();
            int i = 1;
            while (line != null) {
                String strippedLine = removeComments(line).replaceAll("\\s+$", "");
                if (strippedLine.length() <= 0) {
                    line = lineReader.readLine();
                    i++;
                    continue;
                }

                fileName = args[0];

                if (!Lexer.validate(strippedLine, i)) {
                    break;
                }

                Lexer.processLine(strippedLine, i);

                line = lineReader.readLine();
                i++;
            }
            lineReader.close();
            printMap(Lexer.tokens);

            Parser.parse(Lexer.tokens);
        }
        catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static String removeComments(String code) {
        return code.split("//")[0];
    }

    public static void printMap(Map<Double, String> map) {
        for (Map.Entry<Double, String> token : map.entrySet()) {
            double key = token.getKey();
            String val = token.getValue();
            System.out.println(key + " : " + val);
        }
    }

}
