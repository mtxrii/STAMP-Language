package com.edavalos.stamp.Source;

import java.util.ArrayList;
import java.util.List;

public class Block {
    private int id; // line
    private String name; // name part of "on <name>:"
    private List<Child> children; // Any code inside

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

    public List<Child> getChildren() {
        return children;
    }
}
