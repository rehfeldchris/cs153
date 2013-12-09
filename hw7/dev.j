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

ldc "4aaaRa"
invokestatic 	StringVariant/create(Ljava/lang/String;)LStringVariant;
dup
invokestatic 	Util/setMostRecentExpression(LVariant;)V
invokestatic Util/getMostRecentExpression()LVariant;
dup
ldc "R"
invokestatic 	StringVariant/create(Ljava/lang/String;)LStringVariant;
dup
invokestatic 	Util/setMostRecentExpression(LVariant;)V
invokestatic Util/equal(LVariant;LVariant;)LVariant;
dup
invokestatic 	Util/setMostRecentExpression(LVariant;)V
pop
invokestatic Util/getMostRecentExpressionAsBoolean()Z
ifne case_2
dup
ldc "4R"
invokestatic 	StringVariant/create(Ljava/lang/String;)LStringVariant;
dup
invokestatic 	Util/setMostRecentExpression(LVariant;)V
invokestatic Util/equal(LVariant;LVariant;)LVariant;
dup
invokestatic 	Util/setMostRecentExpression(LVariant;)V
pop
invokestatic Util/getMostRecentExpressionAsBoolean()Z
ifne case_3
dup
ldc "4Ra"
invokestatic 	StringVariant/create(Ljava/lang/String;)LStringVariant;
dup
invokestatic 	Util/setMostRecentExpression(LVariant;)V
invokestatic Util/equal(LVariant;LVariant;)LVariant;
dup
invokestatic 	Util/setMostRecentExpression(LVariant;)V
pop
invokestatic Util/getMostRecentExpressionAsBoolean()Z
ifne case_4
dup
ldc "Y"
invokestatic 	StringVariant/create(Ljava/lang/String;)LStringVariant;
dup
invokestatic 	Util/setMostRecentExpression(LVariant;)V
invokestatic Util/equal(LVariant;LVariant;)LVariant;
dup
invokestatic 	Util/setMostRecentExpression(LVariant;)V
pop
invokestatic Util/getMostRecentExpressionAsBoolean()Z
ifne case_5
goto case_default_1
goto after_switch_0
case_2:
case_3:
case_4:
ldc "RED FISH"
invokestatic 	StringVariant/create(Ljava/lang/String;)LStringVariant;
dup
invokestatic 	Util/setMostRecentExpression(LVariant;)V
invokestatic Util/printVariant(LVariant;)V
case_5:
ldc "YELLOW FISH"
invokestatic 	StringVariant/create(Ljava/lang/String;)LStringVariant;
dup
invokestatic 	Util/setMostRecentExpression(LVariant;)V
invokestatic Util/printVariant(LVariant;)V
goto after_switch_0
case_default_1:
ldc "FISH IS TRANSPARENT"
invokestatic 	StringVariant/create(Ljava/lang/String;)LStringVariant;
dup
invokestatic 	Util/setMostRecentExpression(LVariant;)V
invokestatic Util/printVariant(LVariant;)V
after_switch_0:
pop


    return

.limit locals 1
.limit stack  16
.end method
