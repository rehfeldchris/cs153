package wci.frontend.pascal.parsers;

import static wci.frontend.pascal.PascalErrorCode.INVALID_SET_ELEMENT_TYPE;
import static wci.frontend.pascal.PascalErrorCode.MISSING_OF;
import static wci.frontend.pascal.PascalTokenType.IDENTIFIER;
import static wci.frontend.pascal.PascalTokenType.INTEGER;
import static wci.frontend.pascal.PascalTokenType.LEFT_PAREN;
import static wci.frontend.pascal.PascalTokenType.MINUS;
import static wci.frontend.pascal.PascalTokenType.OF;
import static wci.frontend.pascal.PascalTokenType.PLUS;
import static wci.frontend.pascal.PascalTokenType.REAL;
import static wci.frontend.pascal.PascalTokenType.SEMICOLON;
import static wci.frontend.pascal.PascalTokenType.STRING;
import static wci.intermediate.typeimpl.TypeFormImpl.SET;
import static wci.intermediate.typeimpl.TypeFormImpl.SCALAR;
import static wci.intermediate.typeimpl.TypeFormImpl.ENUMERATION;
import static wci.intermediate.typeimpl.TypeKeyImpl.SET_ELEMENT_TYPE;
import static wci.intermediate.symtabimpl.Predefined.booleanType;
import static wci.intermediate.symtabimpl.Predefined.integerType;
import static wci.intermediate.symtabimpl.Predefined.charType;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

import wci.frontend.Token;
import wci.frontend.pascal.PascalParserTD;
import wci.frontend.pascal.PascalTokenType;
import wci.intermediate.TypeFactory;
import wci.intermediate.TypeForm;
import wci.intermediate.TypeSpec;
import wci.intermediate.typeimpl.TypeFormImpl;
import wci.intermediate.typeimpl.TypeSpecImpl;

/**
 * <h1>SetTypeParser</h1>
 *
 * <p>Parse a Pascal set type specification.</p>
 *
 * <p>Copyright (c) 2009 by Ronald Mak</p>
 * <p>For instructional purposes only.  No warranties.</p>
 */
class SetTypeParser extends TypeSpecificationParser
{
   /**
    * Constructor.
    * @param parent the parent parser.
    */
   protected SetTypeParser(PascalParserTD parent)
   {
       super(parent);
   }

   // Synchronization set for OF.
   private static final EnumSet<PascalTokenType> OF_SET =
       TypeSpecificationParser.TYPE_START_SET.clone();
   static {
       OF_SET.add(OF);
       OF_SET.add(SEMICOLON);
   }
   
   
   // Synchronization set for OF.
   private static final List<TypeSpec> VALID_SET_ELEMENT_TYPES =
		   Arrays.asList(integerType, booleanType, charType);
   

   /**
    * Parse a Pascal set type specification.
    * @param token the current token.
    * @return the set type specification.
    * @throws Exception if an error occurred.
    */
   public TypeSpec parse(Token token)
       throws Exception
   {
       TypeSpec setType = TypeFactory.createType(SET);
       token = nextToken();  // consume SET
              
       // Synchronize at OF.
       token = synchronize(OF_SET);
       if (token.getType() == OF) {
           token = nextToken();  // consume OF
       }
       else {
           errorHandler.flag(token, MISSING_OF, this);
       }

       SimpleTypeParser simpleTypeParser = new SimpleTypeParser(this);
       
       // Parse the element type.
       TypeSpec typeSpec = simpleTypeParser.parse(token);
       
       //check the form and type to make sure theyre valid for set element
       switch ((TypeFormImpl) typeSpec.getForm()) {
       case SCALAR:
    	   //intentional fall through
       case SUBRANGE:
    	   //only certain scalars are legal. eg, "set of real" isnt allowed, but real is a scalar.
    	   //we check the baseType() because "set of 1..5" is legal, and we need to check the type of the subrange.
    	   //we always use the baseType because baseType() of a scalar is itself.
           if (!VALID_SET_ELEMENT_TYPES.contains(typeSpec.baseType())) {
        	   errorHandler.flag(token, INVALID_SET_ELEMENT_TYPE, this);
           }
           break;
           
       case ENUMERATION:
    	   // always ok
           break;
           
       default:
    	   //any other type of set is forbidden
    	   errorHandler.flag(token, INVALID_SET_ELEMENT_TYPE, this);
       }

       
       setType.setAttribute(SET_ELEMENT_TYPE, typeSpec);

       return setType;
   }

}
