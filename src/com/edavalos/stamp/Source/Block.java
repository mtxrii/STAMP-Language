package com.edavalos.stamp.Source;

import com.edavalos.stamp.Runner;
import com.edavalos.stamp.Source.ChildTypes.*;
import com.edavalos.stamp.Types.Statement;

import java.util.ArrayList;
import java.util.List;

public class Block {
    private final int id; // line
    private final String name; // name part of "on <name>:"
    private final List children; // Any code inside

    public Block(int line, String name) {
        this.id = line;
        this.name = name;
        this.children = new ArrayList<>();
    }

    public void addStatement(Child statement) {
        children.add(statement);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List getChildren() {
        return children;
    }

    public String toString() {
        String indents = "  ";
        StringBuilder str = new StringBuilder(name + " {\n");
        for (Object statement : children) {
            Statement type = Runner.determineStatementType(statement);
            if (type == null) {
                str.append(indents).append("UNKNOWN ACTION\n");
            }
            else {
                if (type == Statement.VAR) {
                    assert statement instanceof Var;
                    Var variable = ((Var) statement);
                    str.append(indents).append("VARIABLE ASSIGNMENT: ").append(variable.getName()).append("\n");
                    continue;
                }
                if (type == Statement.FUNC) {
                    assert statement instanceof Func;
                    Func function = ((Func) statement);
                    str.append(indents).append("FUNCTION CALL: ").append(function.getAction().name()).append("\n");
                    continue;
                }
                if (type == Statement.LOOP) {
                    assert statement instanceof Loop;
                    Loop loop = ((Loop) statement);
                    str.append(indents).append("NEW LOOP: ").append(loop.getType().name());

                    str.append(loop.CalculateToString(indents));
                }
            }
        }
        return str + "}";
    }
}
