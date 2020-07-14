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



    // EVAL ALGORITHM GENEROUSLY PROVIDED BY:
    // https://stackoverflow.com/a/26227947

    public static double eval(final String str) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char)ch);
                return x;
            }

            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor
            // factor = `+` factor | `-` factor | `(` expression `)`
            //        | number | functionName factor | factor `^` factor

            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if      (eat('+')) x += parseTerm(); // addition
                    else if (eat('-')) x -= parseTerm(); // subtraction
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if      (eat('*')) x *= parseFactor(); // multiplication
                    else if (eat('/')) x /= parseFactor(); // division
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor(); // unary plus
                if (eat('-')) return -parseFactor(); // unary minus

                double x;
                int startPos = this.pos;
                if (eat('(')) { // parentheses
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z') { // functions
                    while (ch >= 'a' && ch <= 'z') nextChar();
                    String func = str.substring(startPos, this.pos);
                    x = parseFactor();
                    if (func.equals("sqrt")) x = Math.sqrt(x);
                    else if (func.equals("sin")) x = Math.sin(Math.toRadians(x));
                    else if (func.equals("cos")) x = Math.cos(Math.toRadians(x));
                    else if (func.equals("tan")) x = Math.tan(Math.toRadians(x));
                    else throw new RuntimeException("Unknown function: " + func);
                } else {
                    throw new RuntimeException("Unexpected: " + (char)ch);
                }

                if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation

                return x;
            }
        }.parse();
    }
}
