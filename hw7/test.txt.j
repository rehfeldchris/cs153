.class public Test
.super java/lang/Object

.field private static CASE_1 F
.field private static CASE_2 F
.field private static CASE_3 F
.field private static GRADE F
.field private static I F
.field private static IGOR F
.field private static IT F
.field private static METH F
.field private static NAME F
.field private static SUMVAR F

.method public <init>()V

  aload_0
  invokenonvirtual  java/lang/Object/<init>()V
  return

.limit locals 11
.limit stack 11
.end method

.method public static main([Ljava/lang/String;)V

ldc2_w 3
invokestatic 	LongVariant/create(J)LLongVariant;
ldc2_w 4
invokestatic 	LongVariant/create(J)LLongVariant;
invokestatic Util/add(LVariant;LVariant;)LVariant;
ldc2_w 3.0999999046325684
invokestatic 	DoubleVariant/create(D)LDoubleVariant;
ldc2_w 4.199999809265137
invokestatic 	DoubleVariant/create(D)LDoubleVariant;
invokestatic Util/subtract(LVariant;LVariant;)LVariant;
ldc2_w 3
invokestatic 	LongVariant/create(J)LLongVariant;
ldc2_w 4
invokestatic 	LongVariant/create(J)LLongVariant;
invokestatic Util/multiply(LVariant;LVariant;)LVariant;
ldc2_w 3.0999999046325684
invokestatic 	DoubleVariant/create(D)LDoubleVariant;
ldc2_w 4.199999809265137
invokestatic 	DoubleVariant/create(D)LDoubleVariant;
invokestatic Util/divide(LVariant;LVariant;)LVariant;
ldc2_w 3
invokestatic 	LongVariant/create(J)LLongVariant;
ldc2_w 4
invokestatic 	LongVariant/create(J)LLongVariant;
invokestatic Util/mod(LVariant;LVariant;)LVariant;
ldc2_w 3.0999999046325684
invokestatic 	DoubleVariant/create(D)LDoubleVariant;
ldc2_w 4.199999809265137
invokestatic 	DoubleVariant/create(D)LDoubleVariant;
invokestatic Util/max(LVariant;LVariant;)LVariant;
ldc2_w 3
invokestatic 	LongVariant/create(J)LLongVariant;
ldc2_w 4
invokestatic 	LongVariant/create(J)LLongVariant;
invokestatic Util/min(LVariant;LVariant;)LVariant;
iconst_1
invokestatic 	BooleanVariant/create(Z)LBooleanVariant;
iconst_0
invokestatic 	BooleanVariant/create(Z)LBooleanVariant;
invokestatic Util/and(LVariant;LVariant;)LVariant;
iconst_1
invokestatic 	BooleanVariant/create(Z)LBooleanVariant;
iconst_0
invokestatic 	BooleanVariant/create(Z)LBooleanVariant;
invokestatic Util/or(LVariant;LVariant;)LVariant;
iconst_1
invokestatic 	BooleanVariant/create(Z)LBooleanVariant;
iconst_0
invokestatic 	BooleanVariant/create(Z)LBooleanVariant;
invokestatic Util/xor(LVariant;LVariant;)LVariant;
iconst_1
invokestatic 	BooleanVariant/create(Z)LBooleanVariant;
invokestatic Util/negate(LVariant;)LVariant;
iconst_1
invokestatic 	BooleanVariant/create(Z)LBooleanVariant;
iconst_0
invokestatic 	BooleanVariant/create(Z)LBooleanVariant;
iconst_1
invokestatic 	BooleanVariant/create(Z)LBooleanVariant;
iconst_1
invokestatic 	BooleanVariant/create(Z)LBooleanVariant;
invokestatic Util/and(LVariant;LVariant;)LVariant;
iconst_1
invokestatic 	BooleanVariant/create(Z)LBooleanVariant;
iconst_0
invokestatic 	BooleanVariant/create(Z)LBooleanVariant;
iconst_0
invokestatic 	BooleanVariant/create(Z)LBooleanVariant;
iconst_0
invokestatic 	BooleanVariant/create(Z)LBooleanVariant;
invokestatic Util/or(LVariant;LVariant;)LVariant;
iconst_1
invokestatic 	BooleanVariant/create(Z)LBooleanVariant;
iconst_1
invokestatic 	BooleanVariant/create(Z)LBooleanVariant;
iconst_0
invokestatic 	BooleanVariant/create(Z)LBooleanVariant;
invokestatic Util/or(LVariant;LVariant;)LVariant;
invokestatic Util/and(LVariant;LVariant;)LVariant;
ldc2_w 3
invokestatic 	LongVariant/create(J)LLongVariant;
ldc2_w 3
invokestatic 	LongVariant/create(J)LLongVariant;
invokestatic Util/equal(LVariant;LVariant;)LVariant;
ldc2_w 3
invokestatic 	LongVariant/create(J)LLongVariant;
ldc2_w 4
invokestatic 	LongVariant/create(J)LLongVariant;
invokestatic Util/equal(LVariant;LVariant;)LVariant;
invokestatic Util/negate(LVariant;)LVariant;
ldc2_w 3
invokestatic 	LongVariant/create(J)LLongVariant;
ldc2_w 4
invokestatic 	LongVariant/create(J)LLongVariant;
invokestatic Util/add(LVariant;LVariant;)LVariant;
iconst_5
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
dup
iconst_3
ldc2_w 99
invokestatic 	LongVariant/create(J)LLongVariant;
aastore
dup
iconst_4
ldc2_w 3
invokestatic 	LongVariant/create(J)LLongVariant;
ldc2_w 4
invokestatic 	LongVariant/create(J)LLongVariant;
invokestatic Util/add(LVariant;LVariant;)LVariant;
aastore
invokestatic Util/concat([LVariant;)LVariant;
ldc "A+"
invokestatic 	StringVariant/create(Ljava/lang/String;)LStringVariant;
ldc "CHRIS"
invokestatic 	StringVariant/create(Ljava/lang/String;)LStringVariant;
ldc "-1.5"
invokestatic 	StringVariant/create(Ljava/lang/String;)LStringVariant;
ldc null
invokestatic 	StringVariant/create(Ljava/lang/String;)LStringVariant;
invokestatic Util/typeCast(LVariant;LVariant;)LVariant;
ldc "MISSING"
invokestatic 	StringVariant/create(Ljava/lang/String;)LStringVariant;
ldc null
invokestatic 	StringVariant/create(Ljava/lang/String;)LStringVariant;
invokestatic Util/typeCast(LVariant;LVariant;)LVariant;
lconst_1
invokestatic 	LongVariant/create(J)LLongVariant;
lconst_1
invokestatic 	LongVariant/create(J)LLongVariant;
invokestatic Util/equal(LVariant;LVariant;)LVariant;
ldc "BLUE"
invokestatic 	StringVariant/create(Ljava/lang/String;)LStringVariant;
ldc "NOT BLUE"
invokestatic 	StringVariant/create(Ljava/lang/String;)LStringVariant;
ldc2_w 3
invokestatic 	LongVariant/create(J)LLongVariant;
ldc2_w 4
invokestatic 	LongVariant/create(J)LLongVariant;
invokestatic Util/add(LVariant;LVariant;)LVariant;
ldc2_w 7
invokestatic 	LongVariant/create(J)LLongVariant;
ldc "SEVEN"
invokestatic 	StringVariant/create(Ljava/lang/String;)LStringVariant;
ldc2_w 8
invokestatic 	LongVariant/create(J)LLongVariant;
ldc "EIGHT"
invokestatic 	StringVariant/create(Ljava/lang/String;)LStringVariant;
ldc "DEFAULT"
invokestatic 	StringVariant/create(Ljava/lang/String;)LStringVariant;
ldc2_w 5
invokestatic 	LongVariant/create(J)LLongVariant;
lconst_0
invokestatic 	LongVariant/create(J)LLongVariant;
ldc2_w 10
invokestatic 	LongVariant/create(J)LLongVariant;
invokestatic Util/add(LVariant;LVariant;)LVariant;
ldc2_w 10
invokestatic 	LongVariant/create(J)LLongVariant;
invokestatic Util/negate(LVariant;)LVariant;
invokestatic Util/add(LVariant;LVariant;)LVariant;
lconst_1
invokestatic 	LongVariant/create(J)LLongVariant;
invokestatic Util/add(LVariant;LVariant;)LVariant;
invokestatic Util/add(LVariant;LVariant;)LVariant;
