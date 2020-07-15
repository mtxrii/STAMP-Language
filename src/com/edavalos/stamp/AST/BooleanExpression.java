package com.edavalos.stamp.AST;

import com.edavalos.stamp.Runner;
import com.edavalos.stamp.Source.ChildTypes.Var;
import com.edavalos.stamp.Types.VarType;

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
        for (String s : splitInput) {
            if (!Var.isValidIdentifier(s)) continue;

            if (Runner.variables.containsKey(s)) {
                if (Runner.variables.get(s).getType() != VarType.BLN) return false;
            }
            else return false;
        }
        checked = true;
        return true;
    }

    @Override
    public void insertVars() {
        if (!checked) return;

        for (int i = 0; i < splitInput.length; i++) {
            if (!Var.isValidIdentifier(splitInput[i])) continue;
            assert Runner.variables.containsKey(splitInput[i]);

            VarNode<?> var = Runner.variables.get(splitInput[i]);
            assert var.getType() == VarType.BLN;

            splitInput[i] = var.getContents().toString();
        }

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
