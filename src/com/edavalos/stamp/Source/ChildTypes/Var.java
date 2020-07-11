package com.edavalos.stamp.Source.ChildTypes;

import com.edavalos.stamp.Source.Child;
import com.edavalos.stamp.Types.Statement;
import com.edavalos.stamp.Types.VarType;

public class Var extends Child {
    private boolean isNew; // if statement has variable type
    private VarType type; // primitive type for var
    private String name; // identifier for variable
    private String contents; // what to set var to

    public Var(int line, VarType variableType, String identifier, String contents) {
        super(line, Statement.VAR);
        this.type = variableType;
        this.name = identifier;
        this.contents = contents;
        this.isNew = true;
    }

    public Var(int line, String identifier, String contents) {
        super(line, Statement.VAR);
        this.type = null; // get from hashmap
        this.name = identifier;
        this.contents = contents;
        this.isNew = false;
    }

    public boolean isNew() {
        return isNew;
    }

    public VarType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    @Override
    public void run() {

    }
}
