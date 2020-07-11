package com.edavalos.stamp.Source.ChildTypes;

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

    @Override
    public void run() {

    }
}
