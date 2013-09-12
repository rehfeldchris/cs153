package wci.frontend.java;

import java.util.Hashtable;
import java.util.HashSet;

import wci.frontend.TokenType;

/**
 * <h1>JavaTokenType</h1>
 *
 * <p>Java token types.</p>
 * 
 * <p>These were copied from the lists in the assignment pdf.
 * The ones in the "misc category" im not sure if its correct - chris
 * </p>
 * 
 *
 * <p>Copyright (c) 2009 by Ronald Mak</p>
 * <p>For instructional purposes only.  No warranties.</p>
 * 
 * @author chris rehfeld
 */
public enum JavaTokenType implements TokenType
{
    // Reserved words.
   ABSTRACT("abstract"),
	DOUBLE("double"),
	INT("int"),
	LONG("long"),
	BREAK("break"),
	ELSE("else"),
	SWITCH("switch"),
	CASE("case"),
	ENUM("enum"),
	NATIVE("native"),
	SUPER("super"),
	CHAR("char"),
	EXTENDS("extends"),
	RETURN("return"),
	THIS("this"),
	CLASS("class"),
	FLOAT("float"),
	SHORT("short"),
	THROW("throw"),
	CONST("const"),
	FOR("for"),
	PACKAGE("package"),
	VOID("void"),
	CONTINUE("continue"),
	GOTO("goto"),
	PROTECTED("protected"),
	VOLATILE("volatile"),
	DO("do"),
	IF("if"),
	STATIC("static"),
	WHILE("while"),
    

    // Special symbols.
	TILDE("~"),
	BANG("!"),
	AT_SYMBOL("@"),
	PERCENT("%"),
	UP_ARROW("^"),
	AMPERSAND("&"),
	STAR("*"), 
    MINUS("-"), 
    PLUS("+"),
    EQUALS("="),
    PIPE("|"),
    FORWARD_SLASH("/"),
    COLON(":"),
    SEMICOLON(";"),
    QUESTION_MARK("?"),
    LESS_THAN("<"),
    GREATER_THAN(">"), 
    DOT("."),
    COMMA(","),
    SINGLE_QUOTE("'"),
    DOUBLE_QUOTE("\""),
    LEFT_PAREN("("), 
    RIGHT_PAREN(")"),
    LEFT_BRACKET("["), 
    RIGHT_BRACKET("]"), 
    LEFT_BRACE("{"), 
    RIGHT_BRACE("}"),
    PLUS_PLUS("++"),
    MINUS_MINUS("--"),
    DBL_LEFT_ARROW("<<"),
    DBL_RIGHT_ARROW(">>"),
    LESS_EQUALS("<="),
    GREATER_EQUALS(">="), 
    PLUS_EQUALS("+="),
    MINUS_EQUALS("-="),
    STAR_EQUALS("*="),
    SLASH_EQUALS("/="),
    DBL_EQUALS("=="),
    PIPE_EQUALS("|="),
    PERCENT_EQUALS("%="),
    AMPERSAND_EQUALS("&="),
    UP_ARROW_EQUALS("^="),
    BANG_EQUALS("!="),
    DBL_LEFT_ARROW_EQUALS("<<="),
    DBL_RIGHT_ARROW_EQUALS(">>="),
    DBL_PIPE("||"),
    DBL_AMPERSAND("&&"),
    SINGLE_LINE_COMMENT_START("//"),
    MULTI_LINE_COMMENT_START("/*"),
    MULTI_LINE_COMMENT_END("*/"),


    //misc category
    IDENTIFIER,
    CHARACTER,
    STRING,
    INTEGER, 
    REAL,
    COMMENT,
    ERROR, 
    END_OF_FILE
    ;

    private static final int FIRST_RESERVED_INDEX = ABSTRACT.ordinal();
    private static final int LAST_RESERVED_INDEX  = WHILE.ordinal();

    private static final int FIRST_SPECIAL_INDEX = TILDE.ordinal();
    private static final int LAST_SPECIAL_INDEX  = MULTI_LINE_COMMENT_END.ordinal();

    private String text;  // token text

    /**
     * Constructor.
     */
    JavaTokenType()
    {
        this.text = this.toString().toLowerCase();
    }

    /**
     * Constructor.
     * @param text the token text.
     */
    JavaTokenType(String text)
    {
        this.text = text;
    }

    /**
     * Getter.
     * @return the token text.
     */
    public String getText()
    {
        return text;
    }

    // Set of lower-cased Pascal reserved word text strings.
    public static HashSet<String> RESERVED_WORDS = new HashSet<String>();
    static {
        JavaTokenType values[] = JavaTokenType.values();
        for (int i = FIRST_RESERVED_INDEX; i <= LAST_RESERVED_INDEX; ++i) {
            RESERVED_WORDS.add(values[i].getText());
        }
    }

    // Hash table of Pascal special symbols.  Each special symbol's text
    // is the key to its Pascal token type.
    public static Hashtable<String, JavaTokenType> SPECIAL_SYMBOLS =
        new Hashtable<String, JavaTokenType>();
    static {
        JavaTokenType values[] = JavaTokenType.values();
        for (int i = FIRST_SPECIAL_INDEX; i <= LAST_SPECIAL_INDEX; ++i) {
            SPECIAL_SYMBOLS.put(values[i].getText(), values[i]);
        }
    }
}
