we included the libs for jasmin and javacc in our files, but we require java7 from you.
------

cd into our base dir. you should see directories like "src/" and "lib/".


first, precompile our grammar
> compile-jjtree.bat

then, compile our grammar
> compile-javacc.bat

then, compile all our java files
> compile-java.bat

then, compile a .lol source file to jasmin
> compile-lolcode-to-jasmin.bat demo/fibonacci.lol

then, compile the jasmin into a .class file
> compile-lolcode-to-jasmin.bat fibonacci.j

then, execute it on the jvm (please uppercase the first letter)
> run-prog.bat Fibonacci
