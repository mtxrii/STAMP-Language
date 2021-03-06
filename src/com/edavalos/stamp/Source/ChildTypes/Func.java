package com.edavalos.stamp.Source.ChildTypes;

import com.edavalos.stamp.Main;
import com.edavalos.stamp.Runner;
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

//  ############################### STATIC RUNTIME METHODS ###############################

    private static boolean a_print(String[] args, int line) {
        if (a_put(args, line)) {
            System.out.println();
            return true;
        }
        return false;
    }

    private static boolean a_put(String[] args, int line) {
        StringBuilder printer = new StringBuilder();
        for (String arg : args) {
            if (arg.equals("+")) {
                continue;
            }

            if (arg.startsWith("\"") && arg.endsWith("\"")) {
                printer.append(arg.replaceAll("\"", ""));
            }
            else {
                if (Runner.variables.containsKey(arg)) {
                    printer.append(Runner.variables.get(arg).toString());
                }
                else {
                    System.out.println("[ERR] " + Main.fileName + ":" + line + " -> Variable name '" + arg + "' could not be found.");
                    return false;
                }
            }
        }
        System.out.print(printer);
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

    private static boolean a_spot(String[] args) {
        return true;
    }

    private static boolean a_jump(String[] args) {
        return true;
    }

    private static boolean a_continue() {
        return true;
    }

    private static boolean a_break() {
        return true;
    }

    private static boolean a_return() {
        return true;
    }

    private static boolean a_wait(String[] args) {
        return true;
    }

    private static boolean a_end() {
        return true;
    }

    private static boolean a_make_int(String[] args) {
        return true;
    }

    private static boolean a_make_flo(String[] args) {
        return true;
    }

    private static boolean a_make_str(String[] args) {
        return true;
    }

    private static boolean a_make_bln(String[] args) {
        return true;
    }

    private static boolean a_randflo(String[] args) {
        return true;
    }

    private static boolean a_randint(String[] args) {
        return true;
    }

    private static boolean a_randbln(String[] args) {
        return true;
    }

    private static boolean a_sin(String[] args) {
        return true;
    }

    private static boolean a_cos(String[] args) {
        return true;
    }

    private static boolean a_tan(String[] args) {
        return true;
    }

    private static boolean a_abs(String[] args) {
        return true;
    }

    private static boolean a_round(String[] args) {
        return true;
    }
}
