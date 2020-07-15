package com.edavalos.stamp.Source.ChildTypes;

import com.edavalos.stamp.Main;
import com.edavalos.stamp.Source.Child;
import com.edavalos.stamp.Types.Statement;
import com.edavalos.stamp.Types.VarType;

public class Var extends Child {
    private final boolean isNew; // if statement has variable type
    private final VarType type; // primitive type for var
    private final String name; // identifier for variable
    private String contents; // what to set var to

    public Var(int line, VarType variableType, String identifier, String contents) {
        super(line, Statement.VAR);
        if (!isValidIdentifier(identifier)) {
            System.out.println("[ERR] " + Main.fileName + ":" + line + " -> Variable name '" + identifier + "' cannot have special characters.");
            System.exit(0);
        }

        this.type = variableType;
        this.name = identifier;
        this.contents = contents;
        this.isNew = true;
    }

    public Var(int line, String identifier, String contents) {
        super(line, Statement.VAR);
        if (!isValidIdentifier(identifier)) {
            System.out.println("[ERR] " + Main.fileName + ":" + line + " -> Variable name '" + identifier + "' cannot have special characters.");
            System.exit(0);
        }

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



    public static boolean isValidIdentifier(String str) {
        int length = str.length();
        assert length != 0;

        if ((length < 2) && (str.toUpperCase().charAt(0) == 'T' || str.toUpperCase().charAt(0) == 'F')) return false;
        for (int i = 0; i < length; i++) {
            char c = str.charAt(i);
            if (!Character.isLetterOrDigit(c) && (c != '_')) return false;
        }
        return true;
    }
}
