# STAMP Lang
### Simple Translated Assembly Markup Programming Language

<img src="https://i.imgur.com/u9bh6sC.png" width=60%>

# Usage
### Execution
Run Stamp once compiled with the files to execute as the first program argument. Arguments following filename will apply to your script. Like such:

```
./STAMP Fibo-Sequence.txt 6 --v
```

In this case a script named Fibo-Sequence is run with program arguments `6` and `--v`

# Full Syntax
STAMP Lang scripts are composed of three types of statements.
* Actions (aka functions)
* Assignments (aka variables)
* Conditionals (aka if's & loops)

And every statement lives inside a block.

### Blocks & Indenting
Programs in Stamp are divided into blocks of code. Each block is independent to eachother to allow different blocks to be designated for different tasks. A new block is designated with the `on` keyword followed by a name. The 'on run:' header is required somewhere, as this is the entry point to your program.
```
on run:
  print "Hello, World!"
```

After each header (`on run:`) there must be a colon, and all code after that should be indented by two spaces. Any statements after this with no indentation must be other `on` declarations.
```
on run:
  print "Hello, World!"
  jump end

on end:
  print "Goodbye"
```

### Conditionals / Ifs & Loops
Inside a block, smaller sub-blocks can be created by declaring a conditional.

Valid designations for these are:
* `loop n times:` - execute statements in this block `n` times
* `loop n times at i:` - execute statements in this block `n` times and automatically keep an index `i`
* `while n:` - execute statements in this block over and over until `n` is false
* `while n at i:` - execute statements in this block until `n` is false and automatically keep an index `i`
* `if n:` - execute statements in this block once if `n` is true

These must end in a colon, and code inside them must be indented by two more spaces than the previous level
<details>
  <summary>Example</summary>
  
  ```
  on run:
    loop 7 times at i:
      if i == 5:
        jump biz
        continue
    
      print i
    

  on biz:
    print "BIZ!"
    return
  ```
  The above prints the following:
  ```
  0
  1
  2
  3
  4
  BIZ!
  6
  ```
  
</details>

### Assignments / Variables
You can assign values to variables on the heap, or temporarily push a value onto the stack. Variables on the heap are statically typed, so the first time you assign a new variable, you must provide a type. 

The four variable types are as follows:
* INT (Integer)
* FLO (Float)
* STR (String)
* BLN (Boolean)

The syntax for declaration is `type name = value` and the syntax for updating / resetting already existing variables is `name = new value`
<details>
  <summary>Example</summary>
  
  ```
  int x = 3
  int y = 7
  int z = x + y
  
  flo d = 3.5
  str e = "Hello"
  
  x = 4
  bln i = T
  
  if i:
    y = 2 * x
  
  print y
  // should print "8"
  ```
</details>
