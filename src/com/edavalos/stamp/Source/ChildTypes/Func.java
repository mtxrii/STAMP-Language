package com.edavalos.stamp.Source.ChildTypes;

import com.edavalos.stamp.Source.Child;
import com.edavalos.stamp.Types.Action;
import com.edavalos.stamp.Types.Statement;

public class Func extends Child {
    private final Action action; // what is the function doing?
    private String args; // Variable, Literal, and/or Expression(s)

    public Func(int line, Action action, String args) {
        super(line, Statement.FUNC);
        this.action = action;
        this.args = args;
    }

    public Action getAction() {
        return action;
    }

    @Override
    public void run() {

    }

//    ############################### STATIC METHODS ###############################

    private static boolean a_print(String[] args) {
        return true;
    }

    private static boolean a_put(String[] args) {
        return true;
    }

    private static boolean a_input(String[] args) {
        return true;
    }

    private static boolean a_push(String[] args) {
        return true;
    }

    private static boolean a_pop(String[] args) {
        return true;
    }

    private static boolean a_length(String[] args) {
        return true;
    }

    private static boolean a_type(String[] args) {
        return true;
    }
}
