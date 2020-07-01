package com.edavalos.stamp;

import java.util.LinkedHashMap;
import java.util.Map;

public final class Lexer {
    public static int indentLevel = 0;
    public static boolean shouldIndent = false;

    public static Map<Double, String> tokens = new LinkedHashMap<>();


    public static void processLine(String line, int lineNumber) {


        tokens.put((double) lineNumber, line.trim());
    }

    public static boolean validate(String line, int lineNumber) {
        assert (line.trim().length() > 0);

        boolean hasColon = line.trim().endsWith(":");
        int indent = getIndent(line);
        int indentChange = determineIndentChange(indent);

        if (indentChange == -99) {
            System.out.println("[ERR] " + Main.fileName + ":" + lineNumber + " -> Indent level " + indentLevel + " expected, " +
                    "got " + indent + " instead");
            return false;
        }

        if (indentChange == -404) {
            System.out.println("[ERR] " + Main.fileName + ":" + lineNumber + " -> Indent change is too big. " + (indentLevel + 2) +
                    " expected, got " + indent + " instead");
            return false;
        }

        if (indent == 0 && !hasColon) {
            System.out.println("[ERR] " + Main.fileName + ":" + lineNumber + " -> Code outside block. Expected inside an 'on run:'");
            return false;
        }

        if (!shouldIndent && indentChange >= 1) {
            System.out.println("[ERR] " + Main.fileName + ":" + (lineNumber-1) + " -> Expected statement ending in ':' before indent.");
            return false;
        }

        if (shouldIndent && indentChange == 0) {
            System.out.println("[ERR] " + Main.fileName + ":" + (lineNumber) + " -> Expected indent after statement ending in ':'.");
            return false;
        }


        if (indentChange > 0) {
            assert indentChange < 2;
            tokens.put((lineNumber - 0.5), "+");
        }

        if (indentChange < 0) {
            double counter = 0.01;
            for (int i = 0; i < (-1 * indentChange); i++) {
                tokens.put((lineNumber - 1.0 + counter), "-");
                counter += 0.01;
            }
        }

        shouldIndent = hasColon;
        indentLevel += indentChange*2;

        if (indentLevel == 0) {
            Parser.inBlock = false;
        }

        return true;


    }

    private static int determineIndentChange(int indent) {
        int difference = indent - indentLevel;
        if (difference % 2 != 0) return -99; // error code for uneven indent

        int change = difference / 2;

        if (change > 1) return -404; // error code for too big indent

        return change;
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
