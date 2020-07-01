package com.edavalos.stamp;

import com.edavalos.stamp.Source.Block;
import com.edavalos.stamp.Source.ChildTypes.*;
import com.edavalos.stamp.Types.Action;
import com.edavalos.stamp.Types.LoopType;
import com.edavalos.stamp.Types.VarType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class Parser {
    public static List<Block> blocks = new ArrayList<>();
    public static Block currentBlock;
    public static boolean inBlock = false;

//    public static Map<String, Object> variables = new HashMap<>();

    public static void parse(Map<Double, String> tokens) {
        for (Map.Entry<Double, String> token : tokens.entrySet()) {
            double line = token.getKey();
            String statement = token.getValue();

            String firstWord = (statement.split(" ")[0]).toUpperCase();

//            if (statement.equals("+")) {
//                enterBlock();
//                continue;
//            }

            if (statement.equals("-")) {
                leaveBlock();
                continue;
            }

            if (firstWord.equals("ON") && statement.endsWith(":")) {
                addBlock(statement, ((int) line));
                continue;
            }

            if (statement.contains("=") && !statement.contains("==")) {
                addVar(statement, ((int) line));
                continue;
            }

            boolean isLoop = (firstWord.equals("LOOP") || firstWord.equals("WHILE") || firstWord.equals("IF"));
            if (!statement.endsWith(":") && isLoop) {
                System.out.println("[ERR] " + Main.fileName + ":" + ((int) line) + " -> Loop '" + statement + "' should end with ':'");
                System.exit(0);
            }

            if (statement.endsWith(":") && !isLoop) {
                System.out.println("[ERR] " + Main.fileName + ":" + ((int) line) + " -> Loop '" + statement + "' is not a valid loop.");
                System.exit(0);
            }

            if (statement.endsWith(":") && isLoop) {
                addLoop(statement, ((int) line));
                continue;
            }

            boolean found = false;
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

    private static void leaveBlock() {
        if (!inBlock) {
            blocks.add(currentBlock);
        }

        else {



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

            currentBlock.addStatement(new Var(line, type, parts[1], contents));

            return;
        }

        if (parts[1].equals("=")) {
            // existing var
            String contents = statement.split("=")[1];

            currentBlock.addStatement(new Var(line, parts[0], contents));

            return;
        }

        System.out.println("[ERR] " + Main.fileName + ":" + line + " -> Incorrect syntax for setting variables. " +
                "Should be 'type name = value' for new vars and 'name = value' for existing vars.");
        System.exit(0);
    }

    private static void addLoop(String header, int line) {
        String[] parts = header.toUpperCase().split(" ");
        if (parts.length > 2 && parts[0].equals("LOOP") && parts[2].equals("TIMES")) {
            if (parts.length > 5 && parts[3].equals("AT")) {
                currentBlock.addStatement(new Loop(line, LoopType.FORI, parts[4], parts[1]));
                return;
            }
            currentBlock.addStatement(new Loop(line, LoopType.FOR, parts[1]));
            return;
        }

        if (parts[0].equals("WHILE")) {
            String args = header.toUpperCase().replaceFirst("WHILE", "")
                    .replaceFirst("AT", "").replace(parts[parts.length-1], "");
            if (header.toUpperCase().contains("AT")) {
                currentBlock.addStatement(new Loop(line, LoopType.WHILEI, parts[parts.length-1], args));
            }
            else {
                currentBlock.addStatement(new Loop(line, LoopType.WHILE, args));
            }
            return;
        }

        if (parts.length > 1 && parts[0].equals("IF")) {
            currentBlock.addStatement(new Loop(line, LoopType.IF, header.toUpperCase().replaceFirst("IF", "")));
        }
    }

    private static void addFunc(String statement, int line) {
        String[] parts = statement.split(" ");
        Action action = Action.valueOf(parts[0].toUpperCase());

        currentBlock.addStatement(new Func(line, action, statement.replaceFirst(parts[0], "")));
    }


}
