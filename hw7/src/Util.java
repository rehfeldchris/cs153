
import java.util.ArrayList;
import java.util.List;

public class Util 
{
	public static Variant add(Variant a, Variant b)
	{
		if (typesOf(a, b).contains(Variant.Type.DOUBLE)) {
			return new DoubleVariant(
				a.doubleVal() + b.doubleVal()
			);
		}
		
		return new LongVariant(
			a.longVal() + b.longVal()
		);
	}
	
	public static Variant subtract(Variant a, Variant b)
	{
		if (typesOf(a, b).contains(Variant.Type.DOUBLE)) {
			return new DoubleVariant(
				a.doubleVal() - b.doubleVal()
			);
		}
		
		return new LongVariant(
			a.longVal() - b.longVal()
		);
	}
	
	public static Variant multiply(Variant a, Variant b)
	{
		if (typesOf(a, b).contains(Variant.Type.DOUBLE)) {
			return new DoubleVariant(
				a.doubleVal() * b.doubleVal()
			);
		}
		
		return new LongVariant(
			a.longVal() * b.longVal()
		);
	}
	
	public static Variant divide(Variant a, Variant b)
	{
		if (typesOf(a, b).contains(Variant.Type.DOUBLE)) {
			return new DoubleVariant(
				a.doubleVal() / b.doubleVal()
			);
		}
		
		long result = 0;
		try {
			result = a.longVal() / b.longVal();
		} catch (ArithmeticException e) {}
		
		return new LongVariant(result);
	}
	
	public static Variant mod(Variant a, Variant b)
	{
		long result = 0;
		try {
			result = a.longVal() % b.longVal();
		} catch (ArithmeticException e) {}
		
		return new LongVariant(result);
	}
	
	/**
	 * this is lolcodes smoosh operator. it takes an array of variants, concats their string representations, 
	 * then returns a new variant with the results in it.
	 * @param variants
	 * @return
	 */
	public static Variant concat(Variant...variants)
	{
		String buf = "";
		for (Variant v : variants) {
			buf += v.stringVal();
		}
		return new StringVariant(buf);
	}
	
	/**
	 * this method just returns a list of the types of the variants. i do 
	 * this so ican easily search the list to see if it contains a double type
	 * so i know whether to use integer or float arithmetic.
	 * 
	 * @param variants
	 * @return
	 */
	private static List<Variant.Type> typesOf(Variant... variants)
	{
		List<Variant.Type> types = new ArrayList<>();
		for (Variant v : variants) {
			types.add(v.getType());
		}
		return types;
	}

	/**
	 * prints the string representation of the variant to standard out.
	 * we shouldnt bother doing printing via native java methods in jasmin. 
	 * instead, we should have jasmin call this method and let this handle the printing,
	 * because then we dont have to put and keep track of a PrintStream obj onto the stack, 
	 * which simplifies the generated jasmin.
	 * 
	 * @param v
	 */
	public static void printVariant(Variant v)
	{
		System.out.println(v.stringVal());
	}
}
