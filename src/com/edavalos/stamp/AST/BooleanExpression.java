package com.edavalos.stamp.AST;

public class BooleanExpression implements ASTEval {
    private final String[] splitInput;
    private boolean checked;

    public BooleanExpression(String input) {
        splitInput = splitTokens(input);
        checked = false;
    }

    @Override
    public boolean areVarsValid() {
        return false;
    }

    @Override
    public void insertVars() {

    }

    @Override
    public void evaluate() {

    }

    private static String[] splitTokens(String str) {
        //do this
    }

}
