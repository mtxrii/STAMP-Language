package com.edavalos.stamp.Types;

// every function possible
// (var = variable, literal = straight number, string or expression, identifier = string id)
public enum Action {
    /** Console actions **/
    PRINT,    // <var|literal> - prints something to console with a newline
    PUT,      // <var|literal> - prints something to console without a newline
    INPUT,    // <var> - stores input in str var

    /** Stack actions **/
    PUSH,     // <var|literal> - puts something in stack
    POP,      // <var> - stores most recent item pushed to stack in var, and deletes it from stack
    LENGTH,   // <var|literal> - gets number of characters or digits in value, and stores it in stack
    TYPE,     // <var|literal> - gets string of type, ex: "FLO" or "STR", and stores it in stack

    /** Loop actions **/
    SPOT,     // str <identifier> - stores current line number under an identifier to jump back to
    JUMP,     // str <identifier> - goes to line number of given identifier and continues with code there.
              //                    Can be a spot ('spot <name>') or block ('on <name>').
    END,      // no args - ends program. Like a Syscall 10 or a Raise SysExit
    CONTINUE, // no args - skips to next iteration of a loop. Not allowed outside loops
    BREAK,    // no args - ends current loop and continues outside. If run outside a loop it ends the
              //           code block and returns to where block was called. If run in main block
              //           ('on run:'), then it ends the program.

    /** System actions **/
    WAIT,     // int|flo <var|literal> - sleeps thread for given seconds

    /** Conversion actions **/
    MAKE_INT, // <var> - makes var of type int (error if: type is str & isnt only numbers)
    MAKE_FLO, // <var> - makes var of type flo (error if: type is str & isnt only numbers with one decimal)
    MAKE_STR, // <var> - makes var of type str
    MAKE_BLN, // <var> - makes var of type bln (error if: type is str & isnt between "T/F" and "True/False")

    /** Math actions **/
    RANDFLO,  // <var> - stores random number from 0 to 1 in flo var
    RANDINT,  // <var> - stores random number from 0 to 10 in int var
    RANDBLN,  // <var> - stores random T or F in bln var
    SIN,      // flo <var> - replaces value in flo var with the sine of the value in the var
    COS,      // flo <var> - replaces value in flo var with the cosine of the value in the var
    TAN,      // flo <var> - replaces value in flo var with the tangent of the value in the var
    ABS,      // int|flo <var> - replaces value in var with the absolute value of the var
    ROUND,    // flo <var> - removes one decimal from flo var, and rounds the next decimal


}
