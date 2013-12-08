package wci.backend.compiler;

import wci.frontend.ASTintegerConstant;
import wci.frontend.ASTrealConstant;
import wci.frontend.ASTstringLiteral;
import wci.frontend.ASTvisible;
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
      flush();
      
      return data;
   }
   
   public Object visit(ASTrealConstant node, Object data)
   {
      float value = (float) node.getAttribute(ICodeKeyImpl.VALUE);
      pln(jasminDoubleVariant(value));
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
      flush();
      
      return data;
   }

   public Object visit(ASTvisible node, Object data)
   {
      //recursively let a visitor put some value onto the stack for us. this should be a variant.
      SimpleNode literalNode = (SimpleNode) node.jjtGetChild(0);
      literalNode.jjtAccept(this, data);
      
      //print the variant
      pln("invokestatic Util/printVariant(LVariant;)V");
      flush();

      return data;
   }

}
