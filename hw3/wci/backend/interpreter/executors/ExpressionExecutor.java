package wci.backend.interpreter.executors;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashSet;

import wci.intermediate.*;
import wci.intermediate.icodeimpl.*;
import wci.backend.interpreter.*;
import static wci.intermediate.symtabimpl.SymTabKeyImpl.*;
import static wci.intermediate.icodeimpl.ICodeNodeTypeImpl.*;
import static wci.intermediate.icodeimpl.ICodeKeyImpl.*;
import static wci.backend.interpreter.RuntimeErrorCode.*;

/**
 * <h1>ExpressionExecutor</h1>
 *
 * <p>Execute an expression.</p>
 *
 * <p>Copyright (c) 2009 by Ronald Mak</p>
 * <p>For instructional purposes only.  No warranties.</p>
 */
public class ExpressionExecutor extends StatementExecutor
{
    /**
     * Constructor.
     * @param the parent executor.
     */
    public ExpressionExecutor(Executor parent)
    {
        super(parent);
    }

    /**
     * Execute an expression.
     * @param node the root intermediate code node of the compound statement.
     * @return the computed value of the expression.
     */
    public Object execute(ICodeNode node)
    {
        ICodeNodeTypeImpl nodeType = (ICodeNodeTypeImpl) node.getType();

        switch (nodeType) {

            case VARIABLE: {

                // Get the variable's symbol table entry and return its value.
                SymTabEntry entry = (SymTabEntry) node.getAttribute(ID);
                return entry.getAttribute(DATA_VALUE);
            }

            case INTEGER_CONSTANT: {

                // Return the integer value.
                return (Integer) node.getAttribute(VALUE);
            }

            case REAL_CONSTANT: {

                // Return the float value.
                return (Float) node.getAttribute(VALUE);
            }

            case STRING_CONSTANT: {

                // Return the string value.
                return (String) node.getAttribute(VALUE);
            }

            case NEGATE: {

                // Get the NEGATE node's expression node child.
                ArrayList<ICodeNode> children = node.getChildren();
                ICodeNode expressionNode = children.get(0);

                // Execute the expression and return the negative of its value.
                Object value = execute(expressionNode);
                if (value instanceof Integer) {
                    return -((Integer) value);
                }
                else {
                    return -((Float) value);
                }
            }

            case NOT: {

                // Get the NOT node's expression node child.
                ArrayList<ICodeNode> children = node.getChildren();
                ICodeNode expressionNode = children.get(0);

                // Execute the expression and return the "not" of its value.
                boolean value = (Boolean) execute(expressionNode);
                return !value;
            }
            
            case SET: {
               
               ArrayList<ICodeNode> setChildren = node.getChildren();
               HashSet<Integer> values = new HashSet<Integer>();
               
               for (ICodeNode i : setChildren) {
                  ICodeNodeTypeImpl setNodeType = (ICodeNodeTypeImpl) i.getType();
                  
                  switch (setNodeType) {
                  
                  /**
                   * SET_VALUES POSTCONDITION = HashSet<Integer> with range [0,50]
                   * so just adopt the values into our runtime set
                   */
                     case SET_VALUES: {                        
                        values = new HashSet<>((HashSet<Integer>) i.getAttribute(VALUE));
                        break;
                     }
                     // Expression, variable, or range
                     default: {                        
                        Object expressionValue = execute(i);
                        // Expression or variable
                        if (expressionValue instanceof Integer) {
                             Integer val = (Integer) expressionValue;
                             if (val < 0 || val > 50) {
                                     errorHandler.flag(node, VALUE_RANGE, this);
                             } else {
                                     values.add(val);
                             }
                        }
                        // Range
                        else if (expressionValue instanceof HashSet<?>) {
                           HashSet<?> vals = (HashSet<?>) expressionValue;
                           for (Object o : vals) {
                              if (o instanceof Integer) {
                                 Integer val = (Integer) o;
                                 if (val < 0 || val > 50) {
                                    errorHandler.flag(node, VALUE_RANGE, this);
                                 } else {
                                    values.add(val);
                                 }
                              }
                              else
                                  errorHandler.flag(node, NONINTEGER_SET_MEMBER, this);
                              
                           }
                        }
                        else
                            errorHandler.flag(node, NONINTEGER_SET_MEMBER, this);
                        break;
                     }                    
                  }
               }
               return values;
            }

            // Must be a binary operator.
            default: return executeBinaryOperator(node, nodeType);
        }
    }
    

    // Set of arithmetic operator node types.
    private static final EnumSet<ICodeNodeTypeImpl> ARITH_OPS =
        EnumSet.of(ADD, SUBTRACT, MULTIPLY, FLOAT_DIVIDE, INTEGER_DIVIDE, MOD);

    /**
     * Execute a binary operator.
     * @param node the root node of the expression.
     * @param nodeType the node type.
     * @return the computed value of the expression.
     */
    private Object executeBinaryOperator(ICodeNode node,
                                         ICodeNodeTypeImpl nodeType)
    {
        // Get the two operand children of the operator node.
        ArrayList<ICodeNode> children = node.getChildren();
        ICodeNode operandNode1 = children.get(0);
        ICodeNode operandNode2 = children.get(1);

        // Operands.
        Object operand1 = execute(operandNode1);
        Object operand2 = execute(operandNode2);

        boolean integerMode = (operand1 instanceof Integer) &&
                              (operand2 instanceof Integer);
        boolean setMode = (operand1 instanceof HashSet<?>) ||
                          (operand2 instanceof HashSet<?>);
        
        // ====================
        // Arithmetic operators
        // ====================

        if (ARITH_OPS.contains(nodeType)) {
            if (integerMode) {
                int value1 = (Integer) operand1;
                int value2 = (Integer) operand2;

                // Integer operations.
                switch (nodeType) {
                    case ADD:      return value1 + value2;
                    case SUBTRACT: return value1 - value2;
                    case MULTIPLY: return value1 * value2;

                    case FLOAT_DIVIDE: {

                        // Check for division by zero.
                        if (value2 != 0) {
                            return ((float) value1)/((float) value2);
                        }
                        else {
                            errorHandler.flag(node, DIVISION_BY_ZERO, this);
                            return 0;
                        }
                    }

                    case INTEGER_DIVIDE: {

                        // Check for division by zero.
                        if (value2 != 0) {
                            return value1/value2;
                        }
                        else {
                            errorHandler.flag(node, DIVISION_BY_ZERO, this);
                            return 0;
                        }
                    }

                    case MOD:  {

                        // Check for division by zero.
                        if (value2 != 0) {
                            return value1%value2;
                        }
                        else {
                            errorHandler.flag(node, DIVISION_BY_ZERO, this);
                            return 0;
                        }
                    }
                }
            }
            else if (setMode) 
            {
                HashSet<Integer> result, value1, value2;

                // Make sure both operands are sets
                if (operand1 instanceof HashSet<?> && operand2 instanceof HashSet<?>)
                {
                    value1 = (HashSet<Integer>) operand1;
                    value2 = (HashSet<Integer>) operand2;
                }
                else
                {
                    errorHandler.flag(node, TYPE_MISMATCH, this);
                    return 0;
                }                
               result = new HashSet<>(value1);
                
               // set operations
               switch (nodeType) 
               {
                  // set intersection
                  case MULTIPLY: { 
                     result.retainAll(value2);
                     break;
                  }
                  // set union
                  case ADD: {
                     result.addAll(value2);
                     break;
                  }
                  // set asymmetric difference
                  case SUBTRACT: {
                     result.removeAll(value2);
                     break;
                  }
               }
               return result;
            }
            else {
                float value1 = operand1 instanceof Integer
                                   ? (Integer) operand1 : (Float) operand1;
                float value2 = operand2 instanceof Integer
                                   ? (Integer) operand2 : (Float) operand2;

                // Float operations.
                switch (nodeType) {
                    case ADD:      return value1 + value2;
                    case SUBTRACT: return value1 - value2;
                    case MULTIPLY: return value1 * value2;

                    case FLOAT_DIVIDE: {

                        // Check for division by zero.
                        if (value2 != 0.0f) {
                            return value1/value2;
                        }
                        else {
                            errorHandler.flag(node, DIVISION_BY_ZERO, this);
                            return 0.0f;
                        }
                    }
                }
            }
        }

        // ==========
        // AND and OR
        // ==========

        else if ((nodeType == AND) || (nodeType == OR)) {
            boolean value1 = (Boolean) operand1;
            boolean value2 = (Boolean) operand2;

            switch (nodeType) {
                case AND: return value1 && value2;
                case OR:  return value1 || value2;
            }
        }

        // ==============
        // Range operator
        // ==============
        
        else if (nodeType == RANGE) {
           if (integerMode) {              
              int value1 = (Integer) operand1;
              int value2 = (Integer) operand2;
              HashSet<Integer> rangeValues = new HashSet<>();

              for (int i = value1; i <= value2; i++)
                 rangeValues.add(i);
              
              return rangeValues;
           }
        }
        // ====================
        // Relational operators
        // ====================

        else if (integerMode) {
            int value1 = (Integer) operand1;
            int value2 = (Integer) operand2;

            // Integer operands.
            switch (nodeType) {
                case EQ: return value1 == value2;
                case NE: return value1 != value2;
                case LT: return value1 <  value2;
                case LE: return value1 <= value2;
                case GT: return value1 >  value2;
                case GE: return value1 >= value2;
            }
        }
        else if (setMode) {
            // IN is only valid for INTEGER_CONSTANT IN SET. fpc says 
            // "Operator not overloaded" if this is tried between 2 sets for example
            if (nodeType == IN_SET)
            {
                if (operand1 instanceof Integer)
                    return ((HashSet<Integer>) operand2).contains((Integer) operand1);
                errorHandler.flag(node, INVALID_OPERATOR, this);
                return 0; 
            }
            //  cannot  perform relational operator unless both sets. TYPE_MISMATCH is
            // the error code that fpc uses
            if (!(operand1 instanceof HashSet<?>) || !(operand2 instanceof HashSet<?>))
            {
                errorHandler.flag(node, TYPE_MISMATCH, this);
                return 0;
            }
                
            HashSet<Integer> value1 = (HashSet<Integer>) operand1;
            HashSet<Integer> value2 = (HashSet<Integer>) operand2;
                        
           switch (nodeType) 
           {
                case EQ: return value1.equals(value2);
                case NE: return !value1.equals(value2);
                case LE: return value2.containsAll(value1);
                case GE: return value1.containsAll(value2);
           }
           
        }
        else {
            float value1 = operand1 instanceof Integer
                               ? (Integer) operand1 : (Float) operand1;
            float value2 = operand2 instanceof Integer
                               ? (Integer) operand2 : (Float) operand2;

            // Float operands.
            switch (nodeType) {
                case EQ: return value1 == value2;
                case NE: return value1 != value2;
                case LT: return value1 <  value2;
                case LE: return value1 <= value2;
                case GT: return value1 >  value2;
                case GE: return value1 >= value2;
            }
        }

        return 0;  // should never get here
    }
}
