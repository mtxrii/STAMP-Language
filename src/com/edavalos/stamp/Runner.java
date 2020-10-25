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
            if (i >= programArgs.length) {
                break;
            }
            VarNode<String> value = new VarNode<>(programArgs[i]);
            String key = "arg" + (i - 1);
            variables.put(key, value);
        }

        if (Main.debugMode) {
            VarNode<Integer> intExample = new VarNode<>(5);
            VarNode<Double>  floExample = new VarNode<>(5.3);
            VarNode<Boolean> blnExample = new VarNode<>(false);

            variables.put("intExample", intExample);
            variables.put("floExample", floExample);
            variables.put("blnExample", blnExample);
        }





    }



    public static Statement determineStatementType(Object child) {
        if (child instanceof Func) return Statement.FUNC;
        if (child instanceof Loop) return Statement.LOOP;
        if (child instanceof Var)  return Statement.VAR;

        return null;
    }

    public static void printVariables() {
        for (Map.Entry<String, VarNode<?>> variable : variables.entrySet()) {
            String name = variable.getKey();
            String type = variable.getValue().getContents().getClass().toString();
            String value = variable.getValue().getContents().toString();
            System.out.println(type + " " + name + " = " + value);
        }
    }

}
