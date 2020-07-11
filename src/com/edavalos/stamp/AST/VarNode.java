package com.edavalos.stamp.AST;

public final class VarNode<T> {
    T contents;

    public VarNode(T contents) {
        this.contents = contents;
    }

    public T getContents() {
        return contents;
    }

    public void setContents(T contents) {
        this.contents = contents;
    }
}
