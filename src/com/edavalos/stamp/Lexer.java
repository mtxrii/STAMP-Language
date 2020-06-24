package com.edavalos.stamp;

public final class Lexer {
    public static int indentLevel = 0;


    public static void processLine(String line) {
        System.out.println("indent lvl [" + indentLevel + "] for: " + line);
    }

    public static boolean validate(String line, int lineNumber, String fileName) {
        if (line.trim().length() <= 0) return true;

        int indent = getIndent(line);

        if (indent > indentLevel*2) {
            System.out.println("[ERR] " + fileName + ":" + lineNumber + " -> Indent level " + indentLevel + " expected, " +
                    "got " + indent + " instead");
            return false;
        }

        if (indent != indentLevel) {
            if (indent % 2 != 0) {
                System.out.println("[ERR] " + fileName + ":" + lineNumber + " -> Indent level " + indentLevel + " expected, " +
                        "got " + indent + " instead");
                return false;
            }
            indentLevel -= 2;
        }

        if (line.trim().endsWith(":")) {
            indentLevel += 2;
        }

        return true;


    }

    private static int getIndent(String code) {
        int indents = 0;
        for (int i = 0; i < code.length(); i++) {
            if (code.charAt(i) != ' ') {
                break;
            }
            indents ++;
        }
        return indents;
    }



}
