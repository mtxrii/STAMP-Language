package com.edavalos.stamp;

import com.edavalos.stamp.Source.Block;
import com.edavalos.stamp.Source.Child;
import com.edavalos.stamp.Source.ChildTypes.*;
import com.edavalos.stamp.Types.Action;
import com.edavalos.stamp.Types.LoopType;
import com.edavalos.stamp.Types.VarType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public final class Parser {
    public static List<Block> blocks = new ArrayList<>();
    public static Block currentBlock;
    public static boolean inBlock = false;

    public static Stack<Loop> loops = new Stack<>();
    public static Loop currentLoop;
    public static boolean inLoop = false;

    public static Stack<String> indents = new Stack<>();

    public static void parse(Map<Double, String> tokens) {
        for (Map.Entry<Double, String> token : tokens.entrySet()) {
            double line = token.getKey();
            String statement = token.getValue();
            String firstWord = (statement.split(" ")[0]).toUpperCase();
            boolean found = false;

            if (statement.equals("+")) {
                indents.push("+");
                continue;
            }

            if (statement.equals("-")) {
                indents.pop();
                leaveBlock(line);
                continue;
            }

            if (statement.equals("~endoffile~")) {
                while (indents.size() > 0) {
                    indents.pop();
                    leaveBlock(line);
                }
                continue;
            }

            if (firstWord.equals("ON") && statement.endsWith(":")) {
                addBlock(statement, ((int) line));
                continue;
            }

            if (statement.split(" ")[1].equals("=") || statement.split(" ")[2].equals("=")) {
                addVar(statement, ((int) line));
                continue;
            }

            boolean isLoop = (firstWord.equals("LOOP") || firstWord.equals("WHILE") || firstWord.equals("IF"));
            if (!statement.endsWith(":") && isLoop) {
                System.out.println("[ERR] " + Main.fileName + ":" + ((int) line) + " -> Loop '" + statement + "' should end with ':'");
                found = true;
                System.exit(0);
            }

            if (statement.endsWith(":") && !isLoop) {
                System.out.println("[ERR] " + Main.fileName + ":" + ((int) line) + " -> Loop '" + statement + "' is not a valid loop.");
                found = true;
                System.exit(0);
            }

            if (statement.endsWith(":") && isLoop) {
                addLoop(statement, ((int) line));
                continue;
            }

            for (Action act : Action.values()) {
                if (act.name().equals(firstWord)) {
                    addFunc(statement, ((int) line));
                    found = true;
                    break;
                }
            }

            if (!found) {
                System.out.println("[ERR] " + Main.fileName + ":" + ((int) line) + " -> Loop '" + statement + "' is not a valid statement.");
                System.exit(0);
            }

        }
    }

    private static void addBlock(String header, int line) {
        String[] parts = header.split(" ");
        if (parts.length != 2) {
            System.out.println("[ERR] " + Main.fileName + ":" + line + " -> Syntax should be 'on <name>:'");
            System.exit(0);
        }
        if (inBlock) {
            System.out.println("[ERR] " + Main.fileName + ":" + line + " -> This section is already in a block.");
            System.exit(0);
        }
        assert parts[0].toUpperCase().equals("ON");
        String name = parts[1].replace(":", "");
        currentBlock = new Block(line, name);
        inBlock = true;
    }

    private static void leaveBlock(double line) {
        if (indents.isEmpty()) {
            inBlock = false;
        }

        // if section leaving is a block
        if (!inBlock) {
            blocks.add(currentBlock);
            currentBlock = null;
        }

        // if section leaving is a loop
        else {
            inLoop = (indents.size() > 1) && (loops.size() > 0);

            if (inLoop) {
                if (currentLoop.isEmpty()) {
                    System.out.println("[ERR] " + Main.fileName + ":" + ((int) line - 3) + " -> Blocks of code should not be left empty.");
                    System.exit(0);
                }

                Loop top = loops.pop();
                top.addStatement(currentLoop);
                currentLoop = top;
            }

            else {
                assert Lexer.indentLevel == 2;
                currentBlock.addStatement(currentLoop);
                currentLoop = null;
            }

        }
    }

    private static void addVar(String statement, int line) {
        String[] parts = statement.split(" ");
        if (parts[2].equals("=")) {
            // new var
            if (!(parts[0].toUpperCase().equals("INT") || parts[0].toUpperCase().equals("FLO") ||
                    parts[0].toUpperCase().equals("STR") || parts[0].toUpperCase().equals("BLN"))) {
                System.out.println("[ERR] " + Main.fileName + ":" + line + " -> Incorrect syntax for setting variables. " +
                        "Valid variable types are: INT, FLO, STR and BLN");
                System.exit(0);
            }
            String contents = statement.split("=")[1];
            VarType type = VarType.valueOf(parts[0].toUpperCase());

            insertStatement(new Var(line, type, parts[1], contents));

            return;
        }

        if (parts[1].equals("=")) {
            // existing var
            String contents = statement.split("=")[1];

            insertStatement(new Var(line, parts[0], contents));

            return;
        }

        System.out.println("[ERR] " + Main.fileName + ":" + line + " -> Incorrect syntax for setting variables. " +
                "Should be 'type name = value' for new vars and 'name = value' for existing vars.");
        System.exit(0);
    }

    private static void addLoop(String header, int line) {
        LoopType loopType = determineLoopType(header);
        String[] parts = deconstructLoop(loopType, header);

        switch (loopType) {
            case IF     -> insertLoop(new Loop(line, LoopType.IF, parts[0]));
            case WHILE  -> insertLoop(new Loop(line, LoopType.WHILE, parts[0]));
            case WHILEI -> insertLoop(new Loop(line, LoopType.WHILEI, parts[1], parts[0]));
            case FOR    -> insertLoop(new Loop(line, LoopType.FOR, parts[0]));
            case FORI   -> insertLoop(new Loop(line, LoopType.FORI, parts[1], parts[0]));
        }
    }

    private static void addFunc(String statement, int line) {
        String[] parts = statement.split(" ");
        Action action = Action.valueOf(parts[0].toUpperCase());

        insertStatement(new Func(line, action, statement.replaceFirst(parts[0], "")));
    }


    private static void insertStatement(Child statement) {
        if (inLoop) {
            currentLoop.addStatement(statement);
        }
        else {
            currentBlock.addStatement(statement);
        }
    }

    private static void insertLoop(Loop loop) {
        if (inLoop) {
            loops.push(currentLoop);
        }
        currentLoop = loop;
        inLoop = true;
    }

    private static LoopType determineLoopType(String header) {
        String[] parts = header.split(" ");
        return switch (parts[0].toUpperCase()) {
            case "IF" -> LoopType.IF;
            case "LOOP" -> {
                if (!header.toUpperCase().contains("TIMES")) yield null;
                else if (header.toUpperCase().contains("AT")) yield LoopType.FORI;
                else yield LoopType.FOR;
            }
            case "WHILE" -> {
                if (header.toUpperCase().contains("AT")) yield LoopType.WHILEI;
                else yield LoopType.WHILE;
            }
            default -> null;
        };
    }

    private static String[] deconstructLoop(LoopType type, String header) {
        return switch (type) {
            case IF     -> header.toUpperCase().replace(":", "").split("IF ");
            case WHILE  -> header.toUpperCase().replace(":", "").split("WHILE ");
            case FOR    -> header.toUpperCase().replace("TIMES:", "").split("LOOP ");

            case WHILEI -> {
                String stripped = header.toUpperCase().replace("WHILE ", "").replace(":", "");
                yield stripped.split(" AT ");
            }
            case FORI   -> {
                String stripped = header.toUpperCase().replace("LOOP ", "").replace(":", "");
                yield stripped.split(" TIMES AT ");
            }
        };
    }


}
