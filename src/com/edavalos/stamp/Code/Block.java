package com.edavalos.stamp.Code;

import java.util.ArrayList;
import java.util.List;

public class Block {
    private final String header;
    private List<String> body;
    private List<Loop> children;
    private final boolean main;

    public Block(String header) {
        this.header = header;
        this.body = new ArrayList<String>();
        this.children = new ArrayList<Loop>();
        this.main = header.toLowerCase().contains("on run:");
    }

    public void addLine(String line) {
        this.body.add(line);
    }

    public void addChild(Loop loop) {
        this.children.add(loop);
    }

    public String getHeader() {
        return header;
    }

    public List<String> getBody() {
        return body;
    }

    public List<Loop> getChildren() {
        return children;
    }

    public boolean isMain() {
        return main;
    }

    @Override
    public String toString() {
        return "Block{" +
                "header='" + header + '\'' +
                ", body=" + body +
                ", children=" + children +
                ", main=" + main +
                '}';
    }
}
