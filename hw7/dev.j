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

ldc2_w 5
invokestatic 	LongVariant/create(J)LLongVariant;
dup
invokestatic 	Util/setMostRecentExpression(LVariant;)V
ldc2_w 6
invokestatic 	LongVariant/create(J)LLongVariant;
dup
invokestatic 	Util/setMostRecentExpression(LVariant;)V
invokestatic Util/greaterThan(LVariant;LVariant;)LVariant;
dup
invokestatic 	Util/setMostRecentExpression(LVariant;)V
invokestatic Util/printVariant(LVariant;)V


    return

.limit locals 3
.limit stack  16
.end method
