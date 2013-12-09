package wci.backend.compiler;

import java.util.ArrayList;
import java.util.Arrays;
import java.io.*;

import wci.frontend.*;
import wci.intermediate.*;
import wci.intermediate.symtabimpl.Predefined;
import wci.backend.*;
import static wci.intermediate.symtabimpl.SymTabKeyImpl.*;
import static wci.intermediate.symtabimpl.DefinitionImpl.*;

/**
 * <p>The code generator for a compiler back end.</p>
 *
 * <p>Copyright (c) 2008 by Ronald Mak</p>
 * <p>For instructional purposes only.  No warranties.</p>
 */
public class CodeGenerator extends Backend
{
    private static final int STACK_LIMIT = 16;
        
    static ICode iCode;
    static SymTabStack symTabStack;
    static PrintWriter objectFile;
    static int uniqueLabelId = 0;

    public void processFunction(ICode iCode, SymTabStack symTabStack,
                        		String objectFilePath, SymTabEntry functionId) throws IOException
    {
    	CodeGenerator.iCode       = iCode;
        CodeGenerator.symTabStack = symTabStack;
        // Open the file in append mode
        CodeGenerator.objectFile  = new PrintWriter(new BufferedWriter(new FileWriter(objectFilePath, true)));
                        
        // Not sure if these are needed?
        //SymTab routineSymTab = (SymTab) functionId.getAttribute(ROUTINE_SYMTAB);
        //ArrayList<SymTabEntry> locals = routineSymTab.sortedEntries();

        // Make the function declaration                
        pln();
        String fName = functionId.getDefinition().toString();
        p(".method private static " + fName + "(");

        ArrayList<SymTabEntry> params = (ArrayList<SymTabEntry>) functionId.getAttribute(ROUTINE_PARMS);
        for (SymTabEntry param : params)
        	p("LVariant;");
        pln(")LVariant");
        
        // Visit the parse tree nodes to generate code for this function
        CodeGeneratorVisitor codeVisitor = new CodeGeneratorVisitor();
        Node rootNode = iCode.getRoot();
        rootNode.jjtAccept(codeVisitor, functionId);
        
        // approximate the number of slots needed for local variables
        SymTabEntry programId = symTabStack.getProgramId();
        int localsCount = (Integer) programId.getAttribute(ROUTINE_LOCALS_COUNT);
        int localSlots = (localsCount * 2) + 1;
        
        // Generate the function epilogue
        pln();
        pln("    return");
        pln();
        pln(".limit locals " + localSlots);
        pln(".limit stack  " + STACK_LIMIT);
        pln(".end method");
        objectFile.flush();

        CodeGenerator.objectFile.close();
    }
    
    
    /**
     * Process the intermediate code and the symbol table generated by the
     * parser to generate machine-language instructions.
     * @param iCode the intermediate code.
     * @param symTabStack the symbol table stack.
     * @param objectFile the object file path for the generated code.
     * @throws Exception if an error occurred.
     */
    public void process(ICode iCode, SymTabStack symTabStack,
                        String objectFilePath)
        throws Exception
    {
       CodeGenerator.iCode       = iCode;
       CodeGenerator.symTabStack = symTabStack;
       CodeGenerator.objectFile  = new PrintWriter(objectFilePath);

       // Make the program and method names.
       int start = objectFilePath.lastIndexOf("/") + 1;
       String programName = objectFilePath.substring(start);
       int end = programName.indexOf(".");
       if (end > -1) {
           programName = programName.substring(0, end);
       }
       programName = programName.substring(0, 1).toUpperCase() +
                     programName.substring(1);
       String methodName = programName.substring(0, 1).toLowerCase() +
                           programName.substring(1);
       
       SymTabEntry programId = symTabStack.getProgramId();
       int localsCount = 
               (Integer) programId.getAttribute(ROUTINE_LOCALS_COUNT);
       SymTab routineSymTab = 
               (SymTab) programId.getAttribute(ROUTINE_SYMTAB);
       ArrayList<SymTabEntry> locals = routineSymTab.sortedEntries();

       // Generate the program header.
       pln(".class public " + programName);
       pln(".super java/lang/Object");
       pln();
       
       // Generate code for the timer and standard input fields.
//       pln(".field private static _runTimer LRunTimer;");
//       pln(".field private static _standardIn LPascalTextIn;");
//       pln();
       
       // Generate code for fields.
       for (SymTabEntry id : locals) {
           Definition defn = id.getDefinition();
           
           if (defn == VARIABLE) {
               String fieldName = id.getName();
               TypeSpec type = id.getTypeSpec();
               String typeCode = type == Predefined.integerType ? "I" : "F";
               pln(".field private static " + fieldName + " " + typeCode);
           }
       }
       pln();
       
       // Generate the class constructor.
       pln(".method public <init>()V");
       pln();
       pln("  aload_0");
       pln("  invokenonvirtual  java/lang/Object/<init>()V");
       pln("  return");
       pln();
       pln(".limit locals 11");
       pln(".limit stack 11");
       pln(".end method");
       pln();
       
       // Generate the main method header.
       pln(".method public static main([Ljava/lang/String;)V");
       pln();
       
       // Generate the main method prologue.
/*       pln("    new  PascalTextIn");
       pln("    dup");
       pln("    invokenonvirtual   PascalTextIn/<init>()V");
       pln("    putstatic " + programName + "/_standardIn LPascalTextIn;");
       pln();
       objectFile.flush();
*/
       // Visit the parse tree nodes to generate code 
       // for the main method's compound statement.
       CodeGeneratorVisitor codeVisitor = new CodeGeneratorVisitor();
       Node rootNode = iCode.getRoot();
       rootNode.jjtAccept(codeVisitor, programName);
       pln();

       //in main, theres always 1 arg, and so we need a local for it.
       //I multiple by 2 because i fear we may add a double or a long, which needs 2 slots ea.
       int localSlots = (localsCount * 2) + 1;
       // Generate the main method epilogue.
       pln();
       pln("    return");
       pln();
       pln(".limit locals " + localSlots);
       pln(".limit stack  " + STACK_LIMIT);
       pln(".end method");
       objectFile.flush();

       CodeGenerator.objectFile.close();
       
    }
    
    
    
    
    
    
    
    
    
    
    
    
    /**
     * below are some short-named aliases to 
     * help improve code legibility.
     */
    static void p(String s) {
    	objectFile.print(s);
    }
    
    static void pln(String s) {
    	objectFile.println(s);
    }
    
    static void pln() {
    	objectFile.println();
    }
    
    static void pf(String format, Object... args) {
    	objectFile.printf(format, args);
    }
    
    static void flush() {
    	objectFile.flush();
    }
    
    /**
     * The methods below try to convert an instruction to the shortcut version.
     * 
     * eg, 
     * 
     * iconst(3) == "iconst_3"
     * 
     * but 
     * 
     * iconst(6) == "iconst 6"
     * 
     */
    static String iconst(int val) {
		if (isEqualToOneOf(val, 0, 1, 2, 3, 4, 5)) {
			return "iconst_" + val;
		}
		if (val == -1) {
			return "iconst_m1";
		}
		return "ldc " + val;
    }
    
    static String lconst(long val) {
		if (isEqualToOneOf(val, 0, 1)) {
			return "lconst_" + val;
		}
		return "ldc2_w " + val;
    }
    
    static String fconst(float val) {
		if (isEqualToOneOf(val, 0, 1, 2)) {
			int ival = (int) val;
			if (isEqualToOneOf(ival, 0, 1, 2)) {
				return "fconst_" + ival;
			}
		}
		return "ldc " + val;
    }
    
    static String dconst(double val) {
		if (isEqualToOneOf(val, 0, 1)) {
			int ival = (int) val;
			if (isEqualToOneOf(ival, 0, 1)) {
				return "dconst_" + ival;
			}
		}
		return "ldc2_w " + val;
    }
    
    static String load(int slot) {
		if (isEqualToOneOf(slot, 0, 1, 2, 3)) {
			return "load_" + slot;
		}
		return "load " + slot;
    }
    
    static String ldc(String value) {
		return "ldc " + value;
    }
    
    static String iload(int slot) {
		return "i" + load(slot);
    }
    
    static String lload(int slot) {
		return "l" + load(slot);
    }
    
    static String fload(int slot) {
		return "f" + load(slot);
    }
    
    static String dload(int slot) {
		return "d" + load(slot);
    }
    
    static String aload(int slot) {
		return "a" + load(slot);
    }
    
    static boolean between(long val, long min, long max) {
    	return val >= min && val <= max;
    }
    static boolean between(double val, double min, double max) {
    	return val >= min && val <= max;
    }
    
    static boolean isEqualToOneOf(double val, Integer... ints) {
    	double epsilon = 0.000000001D;
    	for (int i : ints) {
    		if(Math.abs(val - i) < epsilon) {
    			return true;
    		}
    	}
    	return false;
    }
    static boolean isEqualToOneOf(int val, Integer... ints) {
    	return Arrays.asList(ints).contains(val);
    }
    
    
    /**
     * The methods below take a value, and return a string that contains the jasmin code
     * neccessary to create that type of Variant object to wrap the value.
     * 
     */
    static String jasminStringVariant(String value) {
    	String buf = "";
    	buf += ldc(value);
    	buf += "\n";
    	buf += "invokestatic 	StringVariant/create(Ljava/lang/String;)LStringVariant;";
    	return buf;
    }
    
    static String jasminLongVariant(long value) {
    	String buf = "";
    	buf += lconst(value);
    	buf += "\n";
    	buf += "invokestatic 	LongVariant/create(J)LLongVariant;";
    	return buf;
    }
    
    static String jasminDoubleVariant(double value) {
    	String buf = "";
    	buf += dconst(value);
    	buf += "\n";
    	buf += "invokestatic 	DoubleVariant/create(D)LDoubleVariant;";
    	return buf;
    }
    
    static String jasminBooleanVariant(boolean value) {
    	//i guess theres no boolean type, so use ints. 0 is false, 1 is true.
    	String buf = "";
    	buf += iconst(value ? 1 : 0);
    	buf += "\n";
    	buf += "invokestatic 	BooleanVariant/create(Z)LBooleanVariant;";
    	return buf;
    }
    
    static String jasminUntypedVariant() {
    	String buf = "";
    	buf += "invokestatic 	UntypedVariant/create()LUntypedVariant;";
    	return buf;
    }
    
    /**
     * sometimes we need to create an array of variants in jasmin
     * so we can call a var-args method. this makes the jasmin to initializes the array.
     * @param numElements
     * @return
     */
    static String initVariantArray(int numElements) {
    	String buf = "";
    	buf += iconst(numElements);
    	buf += "\n";
    	buf += "anewarray 	LVariant;";
    	return buf;
    }
    
    /**
     * Whenever an expression is evaluated, we need to remember its value
     * so that we can store it in that lolcode special "IT" variable. Evaluating 
     * an expression results in a value, and our values are always wrapped in a Variant.
     * 
     * This method assumes the Variant is on top of the stack, and then it will call the runtime lib, letting it
     * know that variant was just evaluated. This way the runtime lib can keep track of it for us, 
     * which I think is more flexible.
     * 
     * @return jasmin code
     */
    static String setMostRecentExpression() {
    	String buf = "";
    	buf += "dup";
    	buf += "\n";
    	buf += "invokestatic 	Util/setMostRecentExpression(LVariant;)V";
    	
    	//this is for debugging - it makes every expression print its value when its evaluated
    	//buf += "\n";
    	//buf += "invokestatic 	Util/printMostRecentExpression()V";
    	
    	return buf;
    }

    /**
     * 
     * we have to make goto/jump labels in the jasmin programatically. so, we need a way to uniquely identify where to jump.
     * we use this int as a suffix of the label, eg "if_jmp_label_55"
     * 
     * @param prefix, eg "if_jmp_label"
     * @return your string with a globally unique suffix added to it.
     */
    static String jumpLabel(String prefix)
    {
    	return prefix + "_" + uniqueLabelId++;
    }
    
}
