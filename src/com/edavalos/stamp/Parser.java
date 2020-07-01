package com.edavalos.stamp;

import com.edavalos.stamp.Source.Block;
import com.edavalos.stamp.Types.Action;
import com.edavalos.stamp.Types.LoopType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class Parser {
    public static List<Block> blocks = new ArrayList<>();

    public static void parse(Map<Double, String> tokens) {
        for (Map.Entry<Double, String> token : tokens.entrySet()) {
            double line = token.getKey();
            String statement = token.getValue();

            String firstWord = (statement.split(" ")[0]).toUpperCase();

//            if (statement.equals("+")) {
//                enterBlock();
//            }

            if (statement.equals("-")) {
                leaveBlock();
            }

            if (firstWord.equals("ON") && statement.endsWith(":")) {
                addBlock(statement, line);
                continue;
            }

            if (statement.contains("=") && !statement.contains("==")) {
                addVar(statement, line);
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
                addLoop(statement, line);
                continue;
            }

            for (Action act : Action.values()) {
                if (act.name().equals(firstWord)) {
                    addFunc(statement, line);
                    break;
                }
            }

        }
    }


}
