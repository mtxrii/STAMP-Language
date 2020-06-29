package com.edavalos.stamp.Source;

import com.edavalos.stamp.Types.Statement;

public class Child {
    private int id; // line
    private Statement type; // Func, Var, or Loop

    public Child(int line, Statement type) {
        this.id = line;
        this.type = type;
    }

    // override this in child types
    public void run() {/* do things. */}
}
