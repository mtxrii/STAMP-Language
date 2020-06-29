package com.edavalos.stamp.Source.ChildTypes;

import com.edavalos.stamp.Source.Child;
import com.edavalos.stamp.Types.LoopType;

import java.util.List;

public class Loop extends Child {
    private LoopType type; // If, For(i), or While(i)
    private List<Child> contents; // Any code inside

    @Override
    public void run() {

    }
}
