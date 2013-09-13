package wci.frontend.java.tokens;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import wci.frontend.*;
import wci.frontend.java.*;
import static wci.frontend.Source.EOL;
import static wci.frontend.Source.EOF;
import static wci.frontend.java.JavaTokenType.*;
import static wci.frontend.java.JavaErrorCode.*;

/**
 * <h1>JavaStringToken</h1>
 *
 * <p> Java string tokens.</p>
 *
 * <p>Copyright (c) 2009 by Ronald Mak</p>
 * <p>For instructional purposes only.  No warranties.</p>
 * 
 * @author chris rehfeld
 */
public class JavaStringToken extends Token
{
    
    /**
     * Chars that have special meaning in a Java String or char, when preceded by a backslash.
     * For example: "\t" gets expanded to a tab character.
     * The key is the literal char that can be preceded by the backslash, and the value is the escaped/interpreted value.
     */
    protected static final Map<Character, Character> ESCAPE_SEQUENCE_TRANSLATIONS = new HashMap<Character, Character>();
    static {
        ESCAPE_SEQUENCE_TRANSLATIONS.put('"', '"');
        ESCAPE_SEQUENCE_TRANSLATIONS.put('\'', '\'');
        ESCAPE_SEQUENCE_TRANSLATIONS.put('\\', '\\');
        ESCAPE_SEQUENCE_TRANSLATIONS.put('b', '\b');
        ESCAPE_SEQUENCE_TRANSLATIONS.put('f', '\f');
        ESCAPE_SEQUENCE_TRANSLATIONS.put('n', '\n');
        ESCAPE_SEQUENCE_TRANSLATIONS.put('r', '\r');
        ESCAPE_SEQUENCE_TRANSLATIONS.put('t', '\t');
    }
    
    /**
     * Constructor.
     * @param source the source from where to fetch the token's characters.
     * @throws Exception if an error occurred.
     */
    public JavaStringToken(Source source)
        throws Exception
    {
        super(source);
    }

    /**
     * Extract a Java String token from the source.
     * @throws Exception if an error occurred.
     */
    protected void extract()
        throws Exception
    {
        StringBuilder textBuffer = new StringBuilder();
        StringBuilder valueBuffer = new StringBuilder();
        type = STRING;
        textBuffer.append(currentChar());//append dbl quote
        nextChar();  // consume initial dbl quote
        
        char currentChar = currentChar();
        while (currentChar != EOF && currentChar != '"') {
            extractChar(textBuffer, valueBuffer);
            currentChar = nextChar();
        }


        //the next char needs to be a dbl quote
        switch (currentChar) {
		case '"':
    		//success
    		textBuffer.append(currentChar);//consume the double quote
            text = textBuffer.toString();
            value = valueBuffer.toString();
            nextChar();
    		break;
		
    	case EOF:
        	type = ERROR;
        	value = UNEXPECTED_EOF;
        	break;
        	
    	default:
        	throw new RuntimeException("This case should never happen.");
        }
    }
    
    private void extractChar(StringBuilder textBuffer, StringBuilder valueBuffer) throws Exception {
        char currentChar = currentChar();
        switch (currentChar) {
        case '\\':
            textBuffer.append(currentChar);
            currentChar = nextChar();
            if (ESCAPE_SEQUENCE_TRANSLATIONS.containsKey(currentChar)) {
                //normal escape sequence like '\n'. must be exactly 1 escaped char, 
                //eg nothing like '\nfoo' or '\n\n'
                textBuffer.append(currentChar);
                //set the value to the translation of \t, which is a tab char
                valueBuffer.append(ESCAPE_SEQUENCE_TRANSLATIONS.get(currentChar));
            } else if (currentChar == '\n' || currentChar == '\r') {
                //this case is to allow multi-line string, which java doesnt actually support...
                textBuffer.append(currentChar);
                valueBuffer.append(currentChar);
            } else if (currentChar == EOF) {
                type = ERROR;
                value = UNEXPECTED_EOF;
                return;
            } else {
                // we found something like \a, which isnt valid
                type = ERROR;
                value = ILLEGAL_ESCAPE_SEQUENCE;
                return;
            }
            break;
        default:
            //No escape sequence. Normal single char like 'a'. 
            textBuffer.append(currentChar);
            valueBuffer.append(currentChar);
        }
    }
}
