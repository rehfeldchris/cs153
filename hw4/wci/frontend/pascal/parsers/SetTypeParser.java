package wci.frontend.pascal.parsers;

import static wci.frontend.pascal.PascalErrorCode.MISSING_OF;
import static wci.frontend.pascal.PascalTokenType.OF;
import static wci.frontend.pascal.PascalTokenType.SEMICOLON;
import static wci.intermediate.typeimpl.TypeFormImpl.SET;
import static wci.intermediate.typeimpl.TypeKeyImpl.SET_ELEMENT_TYPE;

import java.util.EnumSet;

import wci.frontend.Token;
import wci.frontend.pascal.PascalParserTD;
import wci.frontend.pascal.PascalTokenType;
import wci.intermediate.TypeFactory;
import wci.intermediate.TypeSpec;

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
       setType.setAttribute(SET_ELEMENT_TYPE, simpleTypeParser.parse(token));

       return setType;
   }

}
