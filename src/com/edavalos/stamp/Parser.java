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

            if (statement.equals("+")) {

            }

            if (statement.equals("-")) {

            }

            if (statement.contains("=") && !statement.contains("==")) {
                addVar(statement, line);
                continue;
            }

            for (LoopType loop : LoopType.values()) {
                if (loop.name().equals(firstWord)) {
                    if (!statement.endsWith(":")) {
                        System.out.println("[ERR] " + Main.fileName + ":" + ((int) line) + " -> Loop '" + statement + "' should end with ':'");
                        System.exit(0);
                    }
                    else {
                        addLoop(statement, line);
                        continue;
                    }
                }
            }

            for (Action act : Action.values()) {
                if (act.name().equals(firstWord)) {
                    addFunc(statement, line);
                    continue;
                }
            }

        }
    }


}
