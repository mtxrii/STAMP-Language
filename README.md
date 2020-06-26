# STAMP Lang
### Simple Translated Assembly Markup Programming Language

# Syntax
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


### Ifs & Loops
Inside a block, there can be smaller sub-blocks for conditionals and loops.

Valid designations for these are:
* `loop n times:` - execute statements in this block `n` times
* `loop n times at i:` - execute statements in this block `n` times and automatically keep an index `i`
* `while n:` - execute statements in this block over and over until `n` is false
* `while n at i:` - execute statements in this block until `n` is false and automatically keep an index `i`
* `if n:` - execute statements in this block once if `n` is true

These must end in a colon, and code inside them must be indented by two more spaces than the previous level
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
1
2
3
4
BIZ!
6
7
```
