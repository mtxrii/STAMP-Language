package com.edavalos.stamp.AST;

import com.edavalos.stamp.Types.VarType;

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

    public VarType getType() {
        return switch (contents.getClass().toString().replaceFirst("class ", "")) {
            case "java.lang.String"  -> VarType.STR;
            case "java.lang.Double"  -> VarType.FLO;
            case "java.lang.Integer" -> VarType.INT;
            case "java.lang.Boolean" -> VarType.BLN;
            default -> VarType.UND;
        };
    }
}
