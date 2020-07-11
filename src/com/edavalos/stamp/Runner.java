package com.edavalos.stamp;

import com.edavalos.stamp.AST.VarNode;
import com.edavalos.stamp.Source.Block;
import com.edavalos.stamp.Source.ChildTypes.*;
import com.edavalos.stamp.Types.Statement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Runner {

    public static Map<String, VarNode<?>> variables = new HashMap<>();

    public static void execute(List<Block> blocks, String[] programArgs) {
        for (int i = 1; i <= 4; i++) {
            VarNode<String> value = new VarNode<>(programArgs[i]);
            String key = "arg" + (i - 1);
            variables.put(key, value);
        }




    }



    public static Statement determineStatementType(Object child) {
        if (child instanceof Func) return Statement.FUNC;
        if (child instanceof Loop) return Statement.LOOP;
        if (child instanceof Var) return Statement.VAR;

        return null;
    }

}
