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
 * <h1>JavaCharToken</h1>
 *
 * <p> Java char tokens.</p>
 *
 * <p>Copyright (c) 2009 by Ronald Mak</p>
 * <p>For instructional purposes only.  No warranties.</p>
 * 
 * @author chris rehfeld
 */
public class JavaCharToken extends JavaToken
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
    public JavaCharToken(Source source)
        throws Exception
    {
        super(source);
    }

    /**
     * Extract a Java char token from the source.
     * @throws Exception if an error occurred.
     */
    protected void extract()
        throws Exception
    {
        StringBuilder textBuffer = new StringBuilder();
        StringBuilder valueBuffer = new StringBuilder();
        type = CHARACTER;
        textBuffer.append(currentChar());//append single quote
        nextChar();  // consume initial single quote

        char currentChar = currentChar();
        switch (currentChar) {
        case '\'':
        case '\r':
        case '\n':
        	type = ERROR;
        	value = INVALID_CHARACTER_CONSTANT;
        	return;
        	
        case EOF:
        	type = ERROR;
        	value = UNEXPECTED_EOF;
        	return;
        	
        case '\\':
    		textBuffer.append(currentChar);
    		currentChar = nextChar();
    		if (ESCAPE_SEQUENCE_TRANSLATIONS.containsKey(currentChar)) {
    			//normal escape sequence like '\n'. must be exactly 1 escaped char, 
    			//eg nothing like '\nfoo' or '\n\n'
                textBuffer.append(currentChar);
                //set the value to the translation of \t, which is a tab char
                valueBuffer.append(ESCAPE_SEQUENCE_TRANSLATIONS.get(currentChar));
                nextChar();
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
    		nextChar();
        }
        
        //the next char needs to be a single quote
        currentChar = currentChar();
        switch (currentChar) {
		case '\'':
    		//success
    		textBuffer.append(currentChar);//append the single quote
            text = textBuffer.toString();
            value = valueBuffer.charAt(0);
            nextChar();//consume the character
    		break;
		
    	case EOF:
        	type = ERROR;
        	value = UNEXPECTED_EOF;
        	break;
        	
    	default:
        	type = ERROR;
        	value = INVALID_CHARACTER_CONSTANT;
        }
    }


}
