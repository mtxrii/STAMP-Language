package com.edavalos.stamp.AST;

import com.edavalos.stamp.Runner;

import java.util.Stack;

public class BooleanExpression implements ASTEval {
    private static final char[] operators = {'(', ')', '=', '!', '&', '|'};

    private final String[] splitInput;
    private boolean checked;
    private boolean result;

    public BooleanExpression(String input) {
        splitInput = splitTokens(input);
        checked = false;
    }

    @Override
    public boolean areVarsValid() {

    }

    @Override
    public void insertVars() {

    }

    @Override
    public boolean evaluate() {
        //do this
    }

    public boolean getResult() {
        return result;
    }

    private static String[] splitTokens(String str) {
        int length = str.length();
        StringBuilder parsed = new StringBuilder();
        Stack<String> parenthesis = new Stack<>();
        for (int i = 0; i < length; i++) {
            if (Character.isLetterOrDigit(str.charAt(i)) && (i != length - 1)) {
                if (Character.isLetterOrDigit(str.charAt(i + 1))) {
                    parsed.append(str.charAt(i));
                    continue;
                }
            }

            switch (str.charAt(i)) {

                case '(' -> parenthesis.push("(");

                case ')' -> {
                    if (parenthesis.isEmpty()) return null;
                    else parenthesis.pop();
                }

                case '=', '&', '|' -> {
                    if ((i != length - 1) && (str.charAt(i) == str.charAt(i + 1))) {
                        continue;
                    }
                }

                case ' ' -> {
                    continue;
                }

                default -> {
                    return null;
                }
            }

            parsed.append(str.charAt(i)).append(" ");
        }

        if (parsed.length() > 0) return null;
        return parsed.toString().split(" ");
    }

}
