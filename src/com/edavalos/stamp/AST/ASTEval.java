package com.edavalos.stamp.AST;

public interface ASTEval {
    /**
     * Checks if non-literal fields are real variable identifiers
     */
    boolean areVarsValid();

    /**
     * Replaces those fields with their respective variable values
     */
    void insertVars();

    /**
     * Uses respective algorithm to evaluate the expression
     */
    void evaluate();
}
