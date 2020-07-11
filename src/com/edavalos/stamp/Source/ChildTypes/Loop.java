package com.edavalos.stamp.Source.ChildTypes;

import com.edavalos.stamp.Runner;
import com.edavalos.stamp.Source.Child;
import com.edavalos.stamp.Types.LoopType;
import com.edavalos.stamp.Types.Statement;

import java.util.ArrayList;
import java.util.List;

public class Loop extends Child {
    private LoopType type; // If, For(i), or While(i)
    private List contents; // Any code inside
    private boolean keepIndex; // Does it have an 'at i'?
    private String index; // only has value when keepIndex is true
    private String arg; // Condition or times to loop

    public Loop(int line, LoopType loopType, String index, String args) {
        super(line, Statement.LOOP);
        this.type = loopType;
        this.arg = args;
        this.contents = new ArrayList<>();

        this.keepIndex = true;
        this.index = index;
    }
    public Loop(int line, LoopType loopType, String args) {
        super(line, Statement.LOOP);
        this.type = loopType;
        this.arg = args;
        this.contents = new ArrayList<>();

        this.keepIndex = false;
        this.index = null;
    }

    public void addStatement(Child statement) {
        contents.add(statement);
    }

    public LoopType getType() {
        return type;
    }

    public String CalculateToString(String indent) {

        String indents = indent + "  ";
        String str = " {\n";
        for (Object statement : contents) {
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
                    str += indents + "NEW LOOP: " + loop.getType().name();

                    str += loop.CalculateToString(indents);
                }
            }
        }
        return str + indent + "} \n";
    }

    @Override
    public void run() {

    }
}
