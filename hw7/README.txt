we included the libs for jasmin and javacc in our files, but we require java7 from you.

we put some sample programs in the demo folder, and we'll run through how to compile one below.
also, the output of these programs can be found in the demo folder.
------

cd into our base dir. you should see directories like "src/" and "lib/".


first, precompile our grammar
> compile-jjtree.bat

then, compile our grammar
> compile-javacc.bat

then, compile all our java files
> compile-java.bat

then, compile a .lol source file to jasmin
> compile-lolcode-to-jasmin.bat demo/Fibonacci.lol

then, compile the jasmin into a .class file
> compile-lolcode-to-jasmin.bat Fibonacci.j

then, execute it on the jvm
> run-prog.bat Fibonacci
