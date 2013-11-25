package jbin.runtime.variant;

import jbin.runtime.variant.Variant.Type;


public abstract class AbstractVariant implements Variant
{
	protected Type type = Type.UNTYPED;
	
	
	public Variant typeCast(Type newType) 
	{

		switch (newType) {
		
			case STRING: {
				return new StringVariant(stringVal());
			}

			case DOUBLE: {
				return new DoubleVariant(doubleVal());
			}

			case LONG: {
				return new LongVariant(longVal());
			}

			case UNTYPED: {
				return new UntypedVariant();
			}
			
			case BOOLEAN: {
				return new BooleanVariant(boolVal());
			}
			
			case ARRAY: {
				return new ArrayVariant(arrayVal());
			}
			
			default:
				return null;
				
		}
	}
	
	
	public Type getType()
	{
		return type;
	}
}
