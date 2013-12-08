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

iconst_3
anewarray 	LVariant;
dup
iconst_0
ldc "A"
invokestatic 	StringVariant/create(Ljava/lang/String;)LStringVariant;
aastore
dup
iconst_1
ldc "B"
invokestatic 	StringVariant/create(Ljava/lang/String;)LStringVariant;
aastore
dup
iconst_2
ldc "C"
invokestatic 	StringVariant/create(Ljava/lang/String;)LStringVariant;
aastore
invokestatic Util/concat([LVariant;)LVariant;
invokestatic Util/printVariant(LVariant;)V
ldc "sum"
invokestatic 	StringVariant/create(Ljava/lang/String;)LStringVariant;
invokestatic Util/printVariant(LVariant;)V
ldc2_w 4
invokestatic 	LongVariant/create(J)LLongVariant;
ldc2_w 8
invokestatic 	LongVariant/create(J)LLongVariant;
invokestatic Util/add(LVariant;LVariant;)LVariant;
invokestatic Util/printVariant(LVariant;)V
ldc "diff"
invokestatic 	StringVariant/create(Ljava/lang/String;)LStringVariant;
invokestatic Util/printVariant(LVariant;)V
ldc2_w 4
invokestatic 	LongVariant/create(J)LLongVariant;
ldc2_w 8
invokestatic 	LongVariant/create(J)LLongVariant;
invokestatic Util/subtract(LVariant;LVariant;)LVariant;
invokestatic Util/printVariant(LVariant;)V
ldc "mult"
invokestatic 	StringVariant/create(Ljava/lang/String;)LStringVariant;
invokestatic Util/printVariant(LVariant;)V
ldc2_w 4
invokestatic 	LongVariant/create(J)LLongVariant;
ldc2_w 8
invokestatic 	LongVariant/create(J)LLongVariant;
invokestatic Util/multiply(LVariant;LVariant;)LVariant;
invokestatic Util/printVariant(LVariant;)V
ldc "QUOSHUNT"
invokestatic 	StringVariant/create(Ljava/lang/String;)LStringVariant;
invokestatic Util/printVariant(LVariant;)V
ldc2_w 6
invokestatic 	LongVariant/create(J)LLongVariant;
ldc2_w 5
invokestatic 	LongVariant/create(J)LLongVariant;
invokestatic Util/divide(LVariant;LVariant;)LVariant;
invokestatic Util/printVariant(LVariant;)V
ldc "MOD"
invokestatic 	StringVariant/create(Ljava/lang/String;)LStringVariant;
invokestatic Util/printVariant(LVariant;)V
ldc2_w 11
invokestatic 	LongVariant/create(J)LLongVariant;
ldc2_w 5
invokestatic 	LongVariant/create(J)LLongVariant;
invokestatic Util/mod(LVariant;LVariant;)LVariant;
invokestatic Util/printVariant(LVariant;)V
ldc "Max"
invokestatic 	StringVariant/create(Ljava/lang/String;)LStringVariant;
invokestatic Util/printVariant(LVariant;)V
ldc "aa"
invokestatic 	StringVariant/create(Ljava/lang/String;)LStringVariant;
ldc "zz"
invokestatic 	StringVariant/create(Ljava/lang/String;)LStringVariant;
invokestatic Util/max(LVariant;LVariant;)LVariant;
invokestatic Util/printVariant(LVariant;)V
ldc "Min"
invokestatic 	StringVariant/create(Ljava/lang/String;)LStringVariant;
invokestatic Util/printVariant(LVariant;)V
ldc2_w 5
invokestatic 	LongVariant/create(J)LLongVariant;
ldc "2"
invokestatic 	StringVariant/create(Ljava/lang/String;)LStringVariant;
invokestatic Util/min(LVariant;LVariant;)LVariant;
invokestatic Util/printVariant(LVariant;)V
ldc "same"
invokestatic 	StringVariant/create(Ljava/lang/String;)LStringVariant;
invokestatic Util/printVariant(LVariant;)V
ldc2_w 5
invokestatic 	LongVariant/create(J)LLongVariant;
ldc "2"
invokestatic 	StringVariant/create(Ljava/lang/String;)LStringVariant;
invokestatic Util/equal(LVariant;LVariant;)LVariant;
invokestatic Util/printVariant(LVariant;)V
ldc "diffrent"
invokestatic 	StringVariant/create(Ljava/lang/String;)LStringVariant;
invokestatic Util/printVariant(LVariant;)V
ldc2_w 5
invokestatic 	LongVariant/create(J)LLongVariant;
ldc "2"
invokestatic 	StringVariant/create(Ljava/lang/String;)LStringVariant;
invokestatic Util/equal(LVariant;LVariant;)LVariant;
invokestatic Util/negate(LVariant;)LVariant;
invokestatic Util/printVariant(LVariant;)V
ldc "xor"
invokestatic 	StringVariant/create(Ljava/lang/String;)LStringVariant;
invokestatic Util/printVariant(LVariant;)V
iconst_1
invokestatic 	BooleanVariant/create(Z)LBooleanVariant;
iconst_0
invokestatic 	BooleanVariant/create(Z)LBooleanVariant;
invokestatic Util/xor(LVariant;LVariant;)LVariant;
invokestatic Util/printVariant(LVariant;)V
iconst_1
invokestatic 	BooleanVariant/create(Z)LBooleanVariant;
iconst_1
invokestatic 	BooleanVariant/create(Z)LBooleanVariant;
invokestatic Util/xor(LVariant;LVariant;)LVariant;
invokestatic Util/printVariant(LVariant;)V
ldc "both"
invokestatic 	StringVariant/create(Ljava/lang/String;)LStringVariant;
invokestatic Util/printVariant(LVariant;)V
iconst_1
invokestatic 	BooleanVariant/create(Z)LBooleanVariant;
iconst_1
invokestatic 	BooleanVariant/create(Z)LBooleanVariant;
invokestatic Util/and(LVariant;LVariant;)LVariant;
invokestatic Util/printVariant(LVariant;)V
ldc "either"
invokestatic 	StringVariant/create(Ljava/lang/String;)LStringVariant;
invokestatic Util/printVariant(LVariant;)V
iconst_0
invokestatic 	BooleanVariant/create(Z)LBooleanVariant;
iconst_1
invokestatic 	BooleanVariant/create(Z)LBooleanVariant;
invokestatic Util/or(LVariant;LVariant;)LVariant;
invokestatic Util/printVariant(LVariant;)V


    return

.limit locals 1
.limit stack  16
.end method
