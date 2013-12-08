
import java.util.List;

public interface Variant
{
	public static enum Type {UNTYPED, BOOLEAN, STRING, ARRAY, DOUBLE, LONG}
	public Type getType();
	public Variant typeCast(Type newType);

	/**
	 * toNumeric will return either a LongVariant or a DoubleVariant, depending on what type and value it currently has.
	 * 
	 * its mostly meant for strings, because strings are special and the string value should be inspected to determine
	  * what type of numeric it would become.
	  * 
	  * For example, if the string is "555" it would return a long, but if it was "1.2" it would return a double.
	 * @return
	 */
	public Variant toNumeric();
	public String stringVal();
	public boolean boolVal();
	public List<Variant> arrayVal();
	public double doubleVal();
	public long longVal();
}
