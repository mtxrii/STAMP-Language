package com.edavalos.stamp.Source.ChildTypes;

import com.edavalos.stamp.Source.Child;
import com.edavalos.stamp.Types.VarType;

public class Var extends Child {
    private boolean isNew; // if statement has variable type
    private VarType type; // primitive type for var
    private String contents; // what to set var to

    @Override
    public void run() {

    }
}
