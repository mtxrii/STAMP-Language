package com.edavalos.stamp;

import com.edavalos.stamp.Code.Block;

import java.util.ArrayList;
import java.util.List;

public final class Lexer {
    public static int indentLevel = 0;
    public static int indentIncrease = 0;

    public static List<Block> blocks = new ArrayList<>();
    public static Block currentBlock = null;


    public static void processLine(String line) {
        //System.out.println("indent lvl [" + getIndent(line) + "] for: " + line);

        if (line.trim().endsWith(":")) {
            System.out.println("new block");
            currentBlock = new Block(line);
            return;
        }

        if (indentIncrease != 0) {
            blocks.add(currentBlock);
            currentBlock = null;
            return;
        }

        currentBlock.addLine(line);
        System.out.println(currentBlock.toString());



    }

    public static boolean validateIndent(String line, int lineNumber, String fileName) {
        if (line.trim().length() <= 0) return true;

        int indent = getIndent(line);
        indentIncrease = 0;

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
            indentIncrease = -1;
        }

        if (indentLevel == 0 && currentBlock == null && !line.trim().endsWith(":")) {
            System.out.println("[ERR] " + fileName + ":" + lineNumber + " -> Code outside block. Expected inside an 'on run:'");
            return false;
        }

        if (line.trim().endsWith(":")) {
            indentLevel += 2;
            indentIncrease = 1;
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
