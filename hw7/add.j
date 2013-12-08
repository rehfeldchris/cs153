.class public add
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

	; this puts the System.out object onto the stack, well use it later.
    getstatic		java/lang/System/out Ljava/io/PrintStream;
    
    
    ; next two instructions do this: StringVariant.create("9");
    ; ps - StringVariant.create is a static factory method i wrote
    ldc 			"9"
    invokestatic 	StringVariant/create(Ljava/lang/String;)LStringVariant;
    
    
    ; next two instructions do this: StringVariant.create("2");
    ;
    ldc 			"2"
    invokestatic 	StringVariant/create(Ljava/lang/String;)LStringVariant;
    
    
    ; now our stack has 2 string variant objects on the top. lets multiply them using the runtime lib multiply method.
    invokestatic	Util/multiply(LVariant;LVariant;)LVariant;
    
    
    
    ;now those two objects are gone, and the stack has a brand new Variant obj on top. it contains the result of the multiply.
    ;now lets get a string representatrion from it, so we can print it.
    ;notice i use invokeinterface, and call the iterface method. this is because i dont know what type of Variant the new obj it is.
    ;it might be LongVariant or RealVariant, but instead of doing if/else crap, we just let the jvm figure it out and
    ;call the appropriate LongVariant/stringVal() or  RealVariant/stringVal() etc...for us. we can ALWAYS call the interface methods
    ;with the way i designed it.
    ; note the "1" specifies how many args to pop off the stack. i dont know why invokeinterface needs this but the other instructions dont.
    ; im guessing the 1 stack elem is for the object to call the method on?
    invokeinterface	Variant/stringVal()Ljava/lang/String; 1
    
    
	;now our stack has a String obj on top, and a PrintStream obj below it.
	; the next line will call println using those two objects.
	invokevirtual 	java/io/PrintStream/println(Ljava/lang/String;)V


	;booyah! it should print 18
	
    return

.limit locals 11
.limit stack  16
.end method
