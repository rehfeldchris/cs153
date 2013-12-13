java -jar .\lib\jasmin-2.4\jasmin.jar -g %1.j
move /Y %1.class bin\%1.class
java -classpath "bin" %1