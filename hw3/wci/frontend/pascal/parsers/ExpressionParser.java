package wci.frontend.pascal.parsers;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;

import wci.frontend.*;
import wci.frontend.pascal.*;
import wci.intermediate.*;
import wci.intermediate.icodeimpl.*;

import static wci.frontend.pascal.PascalTokenType.*;
import static wci.frontend.pascal.PascalTokenType.NOT;
import static wci.frontend.pascal.PascalErrorCode.*;
import static wci.intermediate.icodeimpl.ICodeNodeTypeImpl.*;
import static wci.intermediate.icodeimpl.ICodeKeyImpl.*;

/**
 * <h1>ExpressionParser</h1>
 *
 * <p>Parse a Pascal expression.</p>
 *
 * <p>Copyright (c) 2009 by Ronald Mak</p>
 * <p>For instructional purposes only.  No warranties.</p>
 */
public class ExpressionParser extends StatementParser
{
    /**
     * Constructor.
     * @param parent the parent parser.
     */
    public ExpressionParser(PascalParserTD parent)
    {
        super(parent);
    }

    // Synchronization set for starting an expression.
    static final EnumSet<PascalTokenType> EXPR_START_SET =
        EnumSet.of(PLUS, MINUS, IDENTIFIER, INTEGER, REAL, STRING,
                   PascalTokenType.NOT, LEFT_PAREN);

    /**
     * Parse an expression.
     * @param token the initial token.
     * @return the root node of the generated parse tree.
     * @throws Exception if an error occurred.
     */
    public ICodeNode parse(Token token)
        throws Exception
    {
        return parseExpression(token);
    }

    // Set of relational operators.
    private static final EnumSet<PascalTokenType> REL_OPS =
        EnumSet.of(EQUALS, NOT_EQUALS, LESS_THAN, LESS_EQUALS,
                   GREATER_THAN, GREATER_EQUALS, IN);

    // Map relational operator tokens to node types.
    private static final HashMap<PascalTokenType, ICodeNodeType>
        REL_OPS_MAP = new HashMap<PascalTokenType, ICodeNodeType>();
    static {
        REL_OPS_MAP.put(EQUALS, EQ);
        REL_OPS_MAP.put(NOT_EQUALS, NE);
        REL_OPS_MAP.put(LESS_THAN, LT);
        REL_OPS_MAP.put(LESS_EQUALS, LE);
        REL_OPS_MAP.put(GREATER_THAN, GT);
        REL_OPS_MAP.put(GREATER_EQUALS, GE);
        REL_OPS_MAP.put(IN, IN_SET);
    };
    
    /**
     * Parse an expression.
     * @param token the initial token.
     * @return the root of the generated parse subtree.
     * @throws Exception if an error occurred.
     */
    private ICodeNode parseExpression(Token token)
        throws Exception
    {
        // Parse a simple expression and make the root of its tree
        // the root node unless it is a set expression
        ICodeNode rootNode = parseSimpleExpression(token);
        
        token = currentToken();
        TokenType tokenType = token.getType();
        
        
        // Look for a relational operator.
        if (REL_OPS.contains(tokenType)) {

            // Create a new operator node and adopt the current tree
            // as its first child.
            ICodeNodeType nodeType = REL_OPS_MAP.get(tokenType);
            ICodeNode opNode = ICodeFactory.createICodeNode(nodeType);
            opNode.addChild(rootNode);

            Token errorToken = token; // Get correct position if there's an error
            token = nextToken();  // consume the operator

            // Parse the second simple expression.  The operator node adopts
            // the simple expression's tree as its second child.
            ICodeNode simpExpr = parseSimpleExpression(token);
            opNode.addChild(simpExpr);
            
            // The operator node becomes the new root node.
            rootNode = opNode;
            if (rootNode.getType() == IN_SET && simpExpr.getType() == INTEGER_CONSTANT)
                errorHandler.flag(errorToken, INVALID_OPERATOR, this);
        }

        return rootNode;
    }
    
    /**
     * Parse a set expression.
     * @param token the initial token.
     * @return the root of the generated parse subtree
     * @throws Exception if an error occurred
     */
    private ICodeNode parseSetExpression(Token token) throws Exception
    {   // Create the rootNode with type SET, and adopt a blank SET_VALUES node
        ICodeNode rootNode = ICodeFactory.createICodeNode(ICodeNodeTypeImpl.SET);
        
        // Create a SET_VALUES node which contains INTEGER_CONSTANTS that can be
        //  evaluated at compile time
        ICodeNode valuesNode = ICodeFactory.createICodeNode(ICodeNodeTypeImpl.SET_VALUES);
        HashSet<Integer> valuesSet = new HashSet<>();
        valuesNode.setAttribute(VALUE, valuesSet);
        rootNode.addChild(valuesNode);
        
        // Continue until we reach a closing bracket or some sort of error occurs
        TokenType tokenType = token.getType();
        boolean unrecoverableError = false;
                
        while (EXPR_START_SET.contains(tokenType))
        {   
            // This is used to give the correct error position for , or ] 
            Token errorHandlingToken = currentToken();
            
            // get the first, or perhaps only value in this part of the set
            ICodeNode firstValueNode = parseSimpleExpression(token);
            ICodeNodeType firstValueType = firstValueNode.getType();                

            token = currentToken();
            tokenType = token.getType();

            switch ((PascalTokenType) tokenType)
            {   // just one value
                case COMMA: case RIGHT_BRACKET:
                    if (firstValueNode.getType() == INTEGER_CONSTANT)
                    {
                        Integer val = (Integer) firstValueNode.getAttribute(VALUE);
                        if (val >  50 || val < 0)
                        {
                            errorHandler.flag(errorHandlingToken, RANGE_INTEGER, this);
                            break;
                        }
                        if (valuesSet.contains(val))
                        {                                
                            errorHandler.flag(errorHandlingToken, DUPLICATE_SET_VALUE, this);
                            break;
                        }
                        // add integers to valuesSet
                        valuesSet.add(val);                 
                    }
                    else // add variables to be evaluated at runtime
                        rootNode.addChild(firstValueNode);                            
                    break;
                // range of values
                case DOT_DOT:
                    // get the second value in the range, make sure it is valid
                    token = nextToken();
                    tokenType = token.getType();     
                    if (!EXPR_START_SET.contains(tokenType))
                    {                            
                        errorHandler.flag(token, MISSING_CONSTANT, this); 
                        break;
                    }
                    ICodeNode secondValueNode = parseSimpleExpression(token);
                    ICodeNodeType secondValueType = secondValueNode.getType();

                    // Both are integers so we can evaluate the range now
                    if (firstValueType == INTEGER_CONSTANT && secondValueType == INTEGER_CONSTANT)
                    {
                        Integer firstValue =  (Integer) firstValueNode.getAttribute(VALUE);
                        Integer secondValue = (Integer) secondValueNode.getAttribute(VALUE);
                        for (Integer i = firstValue; i <= secondValue; ++i)
                        {
                            if (i > 50 || i < 0)
                            {
                                errorHandler.flag(token, RANGE_INTEGER, this);
                                break;
                            }
                            if (valuesSet.contains(i))
                            {
                                errorHandler.flag(token, DUPLICATE_SET_VALUE, this);
                                break;
                            }
                            valuesSet.add(i);
                        }             
                    }
                    // one is a variable or expression, so make a range node to evaluate later
                    else 
                    {
                        ICodeNode rangeNode = ICodeFactory.createICodeNode(RANGE);
                        rangeNode.addChild(firstValueNode);
                        rangeNode.addChild(secondValueNode);
                        rootNode.addChild(rangeNode);
                    }                 
                    break;
                case VAR: case INTEGER:
                    errorHandler.flag(token, MISSING_COMMA, this); 
                    continue;
                case SEMICOLON:
                    errorHandler.flag(token, MISSING_RIGHT_BRACKET, this);
                    unrecoverableError = true;
                    break;
                default: // something really weird is here
                    errorHandler.flag(token, UNEXPECTED_TOKEN, this);
                    continue;
            }
            if (unrecoverableError)
                break;
            token = nextToken();
            tokenType = token.getType();
            // there are a lot of cases for this, but it is pointless to keep
            // checking for errors if the code is that garbled.. this catches
            // ONE extra comma or misplaced square bracket
            if (tokenType == COMMA || tokenType == RIGHT_BRACKET)
            {
                errorHandler.flag(token, UNEXPECTED_TOKEN, this);
                token = nextToken();
                tokenType = token.getType();
            }
        }        
        return rootNode;
    }
    
    // Set of additive operators.
    private static final EnumSet<PascalTokenType> ADD_OPS =
        EnumSet.of(PLUS, MINUS, PascalTokenType.OR);

    // Map additive operator tokens to node types.
    private static final HashMap<PascalTokenType, ICodeNodeTypeImpl>
        ADD_OPS_OPS_MAP = new HashMap<PascalTokenType, ICodeNodeTypeImpl>();
    static {
        ADD_OPS_OPS_MAP.put(PLUS, ADD);
        ADD_OPS_OPS_MAP.put(MINUS, SUBTRACT);
        ADD_OPS_OPS_MAP.put(PascalTokenType.OR, ICodeNodeTypeImpl.OR);
    };    
    
    // Operations that cannot be performed with a set as the first operand
    private static final EnumSet<PascalTokenType> DISALLOWED_SET_OPS = 
            EnumSet.of(STAR, SLASH, PascalTokenType.OR, PascalTokenType.AND, 
            LESS_THAN, GREATER_THAN, IN);
    /**
     * Parse a simple expression.
     * @param token the initial token.
     * @return the root of the generated parse subtree.
     * @throws Exception if an error occurred.
     */
    private ICodeNode parseSimpleExpression(Token token)
        throws Exception
    {
        TokenType signType = null;  // type of leading sign (if any)

        // Look for a leading + or - sign.
        TokenType tokenType = token.getType();
        if ((tokenType == PLUS) || (tokenType == MINUS)) {
            signType = tokenType;
            token = nextToken();  // consume the + or -
        }

        // Parse a term and make the root of its tree the root node.
        ICodeNode rootNode = parseTerm(token);

        // Was there a leading - sign?
        if (signType == MINUS) {

            // Create a NEGATE node and adopt the current tree
            // as its child. The NEGATE node becomes the new root node.
            ICodeNode negateNode = ICodeFactory.createICodeNode(NEGATE);
            negateNode.addChild(rootNode);
            rootNode = negateNode;
        }

        token = currentToken();
        tokenType = token.getType();

        // Loop over additive operators.
        while (ADD_OPS.contains(tokenType)) {

            // Create a new operator node and adopt the current tree
            // as its first child.
            ICodeNodeType nodeType = ADD_OPS_OPS_MAP.get(tokenType);
            ICodeNode opNode = ICodeFactory.createICodeNode(nodeType);
            opNode.addChild(rootNode);

            token = nextToken();  // consume the operator

            // Parse another term.  The operator node adopts
            // the term's tree as its second child.
            opNode.addChild(parseTerm(token));

            // The operator node becomes the new root node.
            rootNode = opNode;

            token = currentToken();
            tokenType = token.getType();
        }

        return rootNode;
    }

    // Set of multiplicative operators.
    private static final EnumSet<PascalTokenType> MULT_OPS =
        EnumSet.of(STAR, SLASH, DIV, PascalTokenType.MOD, PascalTokenType.AND);

    // Map multiplicative operator tokens to node types.
    private static final HashMap<PascalTokenType, ICodeNodeType>
        MULT_OPS_OPS_MAP = new HashMap<PascalTokenType, ICodeNodeType>();
    static {
        MULT_OPS_OPS_MAP.put(STAR, MULTIPLY);
        MULT_OPS_OPS_MAP.put(SLASH, FLOAT_DIVIDE);
        MULT_OPS_OPS_MAP.put(DIV, INTEGER_DIVIDE);
        MULT_OPS_OPS_MAP.put(PascalTokenType.MOD, ICodeNodeTypeImpl.MOD);
        MULT_OPS_OPS_MAP.put(PascalTokenType.AND, ICodeNodeTypeImpl.AND);
    };

    /**
     * Parse a term.
     * @param token the initial token.
     * @return the root of the generated parse subtree.
     * @throws Exception if an error occurred.
     */
    private ICodeNode parseTerm(Token token)
        throws Exception
    {
        // Parse a factor and make its node the root node.
        ICodeNode rootNode = parseFactor(token);        

        token = currentToken();
        TokenType tokenType = token.getType();
        
        // Flag invalid set operations
        if ((rootNode.getType() == ICodeNodeTypeImpl.SET && 
                DISALLOWED_SET_OPS.contains(tokenType)))
            errorHandler.flag(token, INVALID_OPERATOR, this);
        
        // Loop over multiplicative operators.
        while (MULT_OPS.contains(tokenType)) {

            // Create a new operator node and adopt the current tree
            // as its first child.
            ICodeNodeType nodeType = MULT_OPS_OPS_MAP.get(tokenType);
            ICodeNode opNode = ICodeFactory.createICodeNode(nodeType);
            opNode.addChild(rootNode);

            token = nextToken();  // consume the operator

            // Parse another factor.  The operator node adopts
            // the term's tree as its second child.
            opNode.addChild(parseFactor(token));

            // The operator node becomes the new root node.
            rootNode = opNode;

            token = currentToken();
            tokenType = token.getType();
        }

        return rootNode;
    }

    /**
     * Parse a factor.
     * @param token the initial token.
     * @return the root of the generated parse subtree.
     * @throws Exception if an error occurred.
     */
    private ICodeNode parseFactor(Token token)
        throws Exception
    {
        TokenType tokenType = token.getType();
        ICodeNode rootNode = null;

        switch ((PascalTokenType) tokenType) {

            case IDENTIFIER: {
                // Look up the identifier in the symbol table stack.
                // Flag the identifier as undefined if it's not found.
                String name = token.getText().toLowerCase();
                SymTabEntry id = symTabStack.lookup(name);
                if (id == null) {
                    errorHandler.flag(token, IDENTIFIER_UNDEFINED, this);
                    id = symTabStack.enterLocal(name);
                }

                rootNode = ICodeFactory.createICodeNode(VARIABLE);
                rootNode.setAttribute(ID, id);
                id.appendLineNumber(token.getLineNumber());

                token = nextToken();  // consume the identifier
                break;
            }

            case INTEGER: {
                // Create an INTEGER_CONSTANT node as the root node.
                rootNode = ICodeFactory.createICodeNode(INTEGER_CONSTANT);
                rootNode.setAttribute(VALUE, token.getValue());

                token = nextToken();  // consume the number
                break;
            }

            case REAL: {
                // Create an REAL_CONSTANT node as the root node.
                rootNode = ICodeFactory.createICodeNode(REAL_CONSTANT);
                rootNode.setAttribute(VALUE, token.getValue());

                token = nextToken();  // consume the number
                break;
            }

            case STRING: {
                String value = (String) token.getValue();

                // Create a STRING_CONSTANT node as the root node.
                rootNode = ICodeFactory.createICodeNode(STRING_CONSTANT);
                rootNode.setAttribute(VALUE, value);

                token = nextToken();  // consume the string
                break;
            }

            case NOT: {
                token = nextToken();  // consume the NOT

                // Create a NOT node as the root node.
                rootNode = ICodeFactory.createICodeNode(ICodeNodeTypeImpl.NOT);

                // Parse the factor.  The NOT node adopts the
                // factor node as its child.
                rootNode.addChild(parseFactor(token));

                break;
            }

            case LEFT_PAREN: {
                token = nextToken();      // consume the (

                // Parse an expression and make its node the root node.
                rootNode = parseExpression(token);

                // Look for the matching ) token.
                token = currentToken();
                if (token.getType() == RIGHT_PAREN) {
                    token = nextToken();  // consume the )
                }
                else {
                    errorHandler.flag(token, MISSING_RIGHT_PAREN, this);
                }

                break;
            }
                
            case LEFT_BRACKET: {
                token = nextToken(); // consume the [
                
                // parse the set expression and make its node the root node
                rootNode = parseSetExpression(token);
                
                // look for matching ] token
                token = currentToken();
                if (token.getType() == RIGHT_BRACKET)
                    token = nextToken(); // consume the ]
                break;
            }

            default: {
                errorHandler.flag(token, UNEXPECTED_TOKEN, this);
                break;
            }
        }
        return rootNode;
    }
}
