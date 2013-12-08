
public abstract class AbstractVariant implements Variant
{
	protected Type type = Type.UNTYPED;
	
	/**
	 * creates a new variant with the given type, 
	 * using this objects value to be converted for the new object. 
	 * 
	 * this object is not modified.
	 */
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
	
	/**
	 * By default, toNumeric() converts everything to a long.
	 * StringVariant, LongVariant, and DoubleVariant will override this.
	 */
	public Variant toNumeric()
	{
		return typeCast(Type.LONG);
	}
	
	public String toString()
	{
		return stringVal();
	}
}
