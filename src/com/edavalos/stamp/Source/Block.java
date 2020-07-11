package com.edavalos.stamp.Source;

import com.edavalos.stamp.Runner;
import com.edavalos.stamp.Source.ChildTypes.*;
import com.edavalos.stamp.Types.Statement;

import java.util.ArrayList;
import java.util.List;

public class Block {
    private int id; // line
    private String name; // name part of "on <name>:"
    private List children; // Any code inside

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
        String str = name + " {\n";
        for (Object statement : children) {
            Statement type = Runner.determineStatementType(statement);
            if (type == null) {
                str += indents + "UNKNOWN ACTION\n";
            }
            else {
                if (type == Statement.VAR) {
                    assert statement instanceof Var;
                    Var variable = ((Var) statement);
                    str += indents + "VARIABLE ASSIGNMENT: " + variable.getName() + "\n";
                    continue;
                }
                if (type == Statement.FUNC) {
                    assert statement instanceof Func;
                    Func function = ((Func) statement);
                    str += indents + "FUNCTION CALL: " + function.getAction().name() + "\n";
                    continue;
                }
                if (type == Statement.LOOP) {
                    assert statement instanceof Loop;
                    Loop loop = ((Loop) statement);
                    str += indents + "NEW LOOP: " + loop.getType().name() + "\n";
                }
            }
        }
        return str + "}";
    }
}
