package wci.backend.compiler;

import wci.frontend.*;
import wci.frontend.LOLCodeParserTreeConstants;
import wci.frontend.SimpleNode;
import wci.intermediate.LOLCodeParserVisitorAdapter;
import wci.intermediate.icodeimpl.*;
import static wci.backend.compiler.CodeGenerator.*;

public class CodeGeneratorVisitor extends LOLCodeParserVisitorAdapter implements
      LOLCodeParserTreeConstants 
{
   public Object visit(ASTintegerConstant node, Object data)
   {
      int value = (int) node.getAttribute(ICodeKeyImpl.VALUE);
      pln(jasminLongVariant(value));
      pln(setMostRecentExpression());
      flush();
      
      return data;
   }
   
   public Object visit(ASTrealConstant node, Object data)
   {
      float value = (float) node.getAttribute(ICodeKeyImpl.VALUE);
      pln(jasminDoubleVariant(value));
      pln(setMostRecentExpression());
      flush();
      
      return data;
   }
   
   //todo: this gets called for "MAEK 5 A TROOF". it prob shouldnt.
   public Object visit(ASTbooleanValue node, Object data)
   {
	  boolean value = node.getAttribute(ICodeKeyImpl.VALUE).equals("true");
	  
      pln(jasminBooleanVariant(value));
      pln(setMostRecentExpression());
      flush();
      
      return data;
   }
   
   public Object visit(ASTstringLiteral node, Object data)
   {
      //TODO
      //litteral strings might contain a double quote, or a new line char.
      //this will break our jasmin, so the way we will handle it is to encode
      //the value into a safe format. once its safely into jasmins memory, we call
      //a meth in our runtime lib to decode it. 
      //im gonna use base64 encoding.
	      
      String value = (String) node.getAttribute(ICodeKeyImpl.VALUE);
      pln(jasminStringVariant(value));
      pln(setMostRecentExpression());
      flush();
      
      return data;
   }

   public Object visit(ASTvisible node, Object data)
   {
	  //put the printVariant() arg onto the stack
	  putChildrenDirectlyOntoStack(node, data);
      
      //print the variant
      pln("invokestatic Util/printVariant(LVariant;)V");
      flush();

      return data;
   }
   
   public Object visit(ASTconcatenate node, Object data)
   {
	  //put the concat() args onto the stack
	  putChildrenIntoVarArgsArray(node, data);
      
      //call the add method using the array on the stack as the argument
      pln("invokestatic Util/concat([LVariant;)LVariant;");
      pln(setMostRecentExpression());
      flush();

      return data;
   }
   
   public Object visit(ASTadd node, Object data)
   {
	  //put the args onto the stack
	  putChildrenDirectlyOntoStack(node, data);

      //call the add method, letting it pick up its 2 args from top of stack
      pln("invokestatic Util/add(LVariant;LVariant;)LVariant;");
      pln(setMostRecentExpression());
      flush();

      return data;
   }
   
   public Object visit(ASTsubtract node, Object data)
   {
	  //put the args onto the stack
	  putChildrenDirectlyOntoStack(node, data);

      //call the sub method, letting it pick up its 2 args from top of stack
      pln("invokestatic Util/subtract(LVariant;LVariant;)LVariant;");
      pln(setMostRecentExpression());
      flush();

      return data;
   }
   
   public Object visit(ASTmultiply node, Object data)
   {
	  //put the args onto the stack
	  putChildrenDirectlyOntoStack(node, data);

      //call the mult method, letting it pick up its 2 args from top of stack
      pln("invokestatic Util/multiply(LVariant;LVariant;)LVariant;");
      pln(setMostRecentExpression());
      flush();

      return data;
   }
   
   public Object visit(ASTdivide node, Object data)
   {
	  //put the args onto the stack
	  putChildrenDirectlyOntoStack(node, data);

      //call the mult method, letting it pick up its 2 args from top of stack
      pln("invokestatic Util/divide(LVariant;LVariant;)LVariant;");
      pln(setMostRecentExpression());
      flush();

      return data;
   }
   
   public Object visit(ASTmodulo node, Object data)
   {
	  //put the args onto the stack
	  putChildrenDirectlyOntoStack(node, data);

      //call the mult method, letting it pick up its 2 args from top of stack
      pln("invokestatic Util/mod(LVariant;LVariant;)LVariant;");
      pln(setMostRecentExpression());
      flush();

      return data;
   }
   
   public Object visit(ASTmax node, Object data)
   {
	  //put the args onto the stack
	  putChildrenDirectlyOntoStack(node, data);

      //call the max method, letting it pick up its 2 args from top of stack
      pln("invokestatic Util/max(LVariant;LVariant;)LVariant;");
      pln(setMostRecentExpression());
      flush();

      return data;
   }
   
   public Object visit(ASTmin node, Object data)
   {
	  //put the args onto the stack
	  putChildrenDirectlyOntoStack(node, data);

      //call the min method, letting it pick up its 2 args from top of stack
      pln("invokestatic Util/min(LVariant;LVariant;)LVariant;");
      pln(setMostRecentExpression());
      flush();

      return data;
   }
   
   public Object visit(ASTxor node, Object data)
   {
	  //put the args onto the stack
	  putChildrenDirectlyOntoStack(node, data);

      //call the min method, letting it pick up its 2 args from top of stack
      pln("invokestatic Util/xor(LVariant;LVariant;)LVariant;");
      pln(setMostRecentExpression());
      flush();

      return data;
   }
   
   public Object visit(ASTcast node, Object data)
   {
	  putChildrenDirectlyOntoStack(node, data);
      pln("invokestatic Util/typeCast(LVariant;LVariant;)LVariant;");
      pln(setMostRecentExpression());
      flush();

      return data;
   }
   
   public Object visit(ASTequals node, Object data)
   {
	  putChildrenDirectlyOntoStack(node, data);
      pln("invokestatic Util/equal(LVariant;LVariant;)LVariant;");
      pln(setMostRecentExpression());
      flush();

      return data;
   }
   
   public Object visit(ASTnot node, Object data)
   {
	  putChildrenDirectlyOntoStack(node, data);
      pln("invokestatic Util/negate(LVariant;)LVariant;");
      pln(setMostRecentExpression());
      flush();

      return data;
   }
   
   public Object visit(ASTand node, Object data)
   {
	  putChildrenDirectlyOntoStack(node, data);
      pln("invokestatic Util/and(LVariant;LVariant;)LVariant;");
      pln(setMostRecentExpression());
      flush();

      return data;
   }
   
   public Object visit(ASTor node, Object data)
   {
	  putChildrenDirectlyOntoStack(node, data);
      pln("invokestatic Util/or(LVariant;LVariant;)LVariant;");
      pln(setMostRecentExpression());
      flush();

      return data;
   }
   
   public Object visit(ASTtype node, Object data)
   {
      String value = (String) node.getAttribute(ICodeKeyImpl.VALUE);
      pln(jasminStringVariant(value));
      flush();

      return data;
   }

   
   /**
    * This will emit the jasmin code to package all Variants which result from the child
    * nodes, into a Variant[] array. This is used for calling a var-args method from within jasmin.
    * 
    * @param node THe node whos children will be recursively visited
    * @param data
    */
   public void putChildrenIntoVarArgsArray(SimpleNode node, Object data) 
   {
	   int numChildren = node.jjtGetNumChildren();
	   
	   //init a java array like Variant[] varArgs = new Variant[numChildren];
	   //we have to do this in order to pass the arguments to the Util.concat() method, because its a var-args method
	   pln(initVariantArray(numChildren));
	   
	   for (int i = 0; i < numChildren; i++) {
		   
		   //we dup the array reference because the aastore instruction will eat it
		  pln("dup");
		  
		  // in a moment when we store the Variant into the array, this int will be used as its array index
		  pln(iconst(i));
		  
	      //recursively let a visitor put some value onto the stack for us. this should be a variant.
	      SimpleNode literalNode = (SimpleNode) node.jjtGetChild(i);
	      literalNode.jjtAccept(this, data);
	      
	      //stores whatever variant is on top of the stack into our array at index i
	      pln("aastore");
	  }
   }
   
   /**
    * This will recursively visit the child nodes, leaving their resulting Variant directly 
    * objects on the stack. This is useful to put the arguments to a method onto the stack 
    * before calling the method.
    * 
    * @param node THe node whos children will be recursively visited
    * @param data
    */
   public void putChildrenDirectlyOntoStack(SimpleNode node, Object data) 
   {
	   int numChildren = node.jjtGetNumChildren();
	   
	   //loop over all the children
	   for (int i = 0; i < numChildren; i++) {
	      //recursively let a visitor put some value onto the stack for us. this should be a variant.
	      SimpleNode literalNode = (SimpleNode) node.jjtGetChild(i);
	      literalNode.jjtAccept(this, data);
	  }
   }
   
   
}
