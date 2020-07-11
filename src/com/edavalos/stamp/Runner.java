package com.edavalos.stamp;

import com.edavalos.stamp.Source.ChildTypes.*;
import com.edavalos.stamp.Types.Statement;

import java.util.HashMap;
import java.util.Map;

public final class Runner {
    public static Map<String, Object> variables = new HashMap<>();




    public static Statement determineStatementType(Object child) {
        if (child instanceof Func) return Statement.FUNC;
        if (child instanceof Loop) return Statement.LOOP;
        if (child instanceof Var) return Statement.VAR;

        return null;
    }

}
