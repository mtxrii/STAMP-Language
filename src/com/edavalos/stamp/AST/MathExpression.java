package com.edavalos.stamp.AST;

import com.edavalos.stamp.Runner;
import com.edavalos.stamp.Source.ChildTypes.Var;
import com.edavalos.stamp.Types.VarType;

public class MathExpression {
    private String actualInput;
    private final String[] splitInput;
    private boolean checked;
    private double result;

    public MathExpression(String input) {
        actualInput = input;
        splitInput = input.replaceAll("[()]", " ").split(" ");
        checked = false;
    }

    public boolean areVarsValid() {
        for (String str : splitInput) {
            if (!Var.isValidIdentifier(str)) continue;

            if (!Runner.variables.containsKey(str)) return false;
            if (Runner.variables.get(str).getType() == VarType.STR) return false;
        }
        checked = true;
        return true;
    }

    public void insertVars() {
        if (!checked) return;

        for (String str : splitInput) {
            if (!Var.isValidIdentifier(str)) continue;

            if (Runner.variables.containsKey(str)) {
                String var = Runner.variables.get(str).toString();
                actualInput = actualInput.replaceAll(str, var);
            }
        }
    }

    public void evaluate() {
        if (!checked) return;

        result = eval(actualInput);
    }

    public double getResult() {
        return result;
    }

    public int getResultAsInt() {
        return ((int) result);
    }



}
