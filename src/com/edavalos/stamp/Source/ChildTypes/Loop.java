package com.edavalos.stamp.Source.ChildTypes;

import com.edavalos.stamp.Source.Child;
import com.edavalos.stamp.Types.LoopType;
import com.edavalos.stamp.Types.Statement;

import java.util.ArrayList;
import java.util.List;

public class Loop extends Child {
    private LoopType type; // If, For(i), or While(i)
    private List<Child> contents; // Any code inside

    public Loop(int line, LoopType loopType) {
        super(line, Statement.LOOP);
        this.type = loopType;
        this.contents = new ArrayList<>();
    }

    public void addStatement(Child statement) {
        contents.add(statement);
    }

    @Override
    public void run() {

    }
}
