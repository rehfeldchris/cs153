.class public Dev
.super java/lang/Object

.field private static IT F

.method public <init>()V

  aload_0
  invokenonvirtual  java/lang/Object/<init>()V
  return

.limit locals 11
.limit stack 11
.end method

.method public static main([Ljava/lang/String;)V

lconst_0
invokestatic 	LongVariant/create(J)LLongVariant;
dup
invokestatic 	Util/setMostRecentExpression(LVariant;)V
invokestatic Util/getMostRecentExpressionAsBoolean()Z
ifeq else_1
ldc "J00 HAV A CAT"
invokestatic 	StringVariant/create(Ljava/lang/String;)LStringVariant;
dup
invokestatic 	Util/setMostRecentExpression(LVariant;)V
invokestatic Util/printVariant(LVariant;)V
goto after_if_0
else_1:
ldc "J00 dont HAV A CAT"
invokestatic 	StringVariant/create(Ljava/lang/String;)LStringVariant;
dup
invokestatic 	Util/setMostRecentExpression(LVariant;)V
invokestatic Util/printVariant(LVariant;)V
after_if_0:


    return

.limit locals 1
.limit stack  16
.end method
