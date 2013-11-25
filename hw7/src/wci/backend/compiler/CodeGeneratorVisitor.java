package wci.backend.compiler;

import wci.frontend.ASTintegerConstant;
import wci.frontend.ASTrealConstant;
import wci.frontend.ASTstringLiteral;
import wci.frontend.LOLCodeParserTreeConstants;
import wci.intermediate.LOLCodeParserVisitorAdapter;
import wci.intermediate.icodeimpl.*;

import static wci.backend.compiler.CodeGenerator.*;

public class CodeGeneratorVisitor extends LOLCodeParserVisitorAdapter implements
      LOLCodeParserTreeConstants 
{
   public Object visit(ASTintegerConstant node, Object data)
   {
      int value = (Integer) node.getAttribute(ICodeKeyImpl.VALUE);
      
      // Emit a load constant instruction.
      pln("    ldc " + value);
      flush();
      
      return data;
   }
   
   public Object visit(ASTrealConstant node, Object data)
   {
      float value = (Float) node.getAttribute(ICodeKeyImpl.VALUE);
      
      // Emit a load constant instruction.
      pln("    ldc " + value);
      flush();
      
      return data;
   }
   
   public Object visit(ASTstringLiteral node, Object data)
   {
      String value = (String) node.getAttribute(ICodeKeyImpl.VALUE);
      
      // Emit a load constant instruction.
      pln("    ldc " + value);
      flush();
      
      return data;
   }


}
