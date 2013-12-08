
import java.util.ArrayList;
import java.util.List;

public class Util
{
	public static Variant add(Variant a, Variant b)
	{
		//make them numerics if they arent already
		a = a.toNumeric();
		b = b.toNumeric();
		
		//if at least one is a double, the result is a double
		if (typesOf(a, b).contains(Variant.Type.DOUBLE)) {
			return new DoubleVariant(
				a.doubleVal() + b.doubleVal()
			);
		}
		
		//they must both be longs, so the result is a long.
		return new LongVariant(
			a.longVal() + b.longVal()
		);
	}
	
	public static Variant subtract(Variant a, Variant b)
	{
		//make them numerics if they arent already
		a = a.toNumeric();
		b = b.toNumeric();
		
		//if at least one is a double, the result is a double
		if (typesOf(a, b).contains(Variant.Type.DOUBLE)) {
			return new DoubleVariant(
				a.doubleVal() - b.doubleVal()
			);
		}
		
		//they must both be longs, so the result is a long.
		return new LongVariant(
			a.longVal() - b.longVal()
		);
	}
	
	public static Variant multiply(Variant a, Variant b)
	{
		//make them numerics if they arent already
		a = a.toNumeric();
		b = b.toNumeric();
		
		//if at least one is a double, the result is a double
		if (typesOf(a, b).contains(Variant.Type.DOUBLE)) {
			return new DoubleVariant(
				a.doubleVal() * b.doubleVal()
			);
		}
		
		//they must both be longs, so the result is a long.
		return new LongVariant(
			a.longVal() * b.longVal()
		);
	}
	
	public static Variant divide(Variant a, Variant b)
	{
		//make them numerics if they arent already
		a = a.toNumeric();
		b = b.toNumeric();
		
		//try to keep the result a long, when possible
		if (!typesOf(a, b).contains(Variant.Type.DOUBLE)) {
			
			//check for division by zero
			//for 2 longs, we make division by zero results in 0
			if (b.longVal() == 0) {
				return new LongVariant(0L);
			}
			
			//when the mod is zero, it means the numbers evenly divide, and so the result can be a long
			if (a.longVal() % b.longVal() == 0) {
				return new LongVariant(
					a.longVal() / b.longVal()
				);
			}
		}
		
		//otherwise, we make the result a double
		//division by zero results in infinity here
		return new DoubleVariant(
			a.doubleVal() / b.doubleVal()
		);
	}
	
	public static Variant mod(Variant a, Variant b)
	{
		long result = 0;
		try {
			//mod is only defined on longs, so we always cast to long
			result = a.longVal() % b.longVal();
		} catch (ArithmeticException e) {}
		
		return new LongVariant(result);
	}
	

	public static Variant min(Variant a, Variant b)
	{
		return cmpVariant(a, b) < 0 ? a : b;
	}
	
	public static Variant max(Variant a, Variant b)
	{
		return cmpVariant(a, b) < 0 ? b : a;
	}
	
	/**
	 * returns -1 if a < b
	 *          1 if a > b
	 *          0 if a == b
	 *          
	 * You can compare any combo of variant types. this will automatically figure out a sane comparison method.
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static int cmpVariant(Variant a, Variant b)
	{
		return VariantComparator.INSTANCE.compare(a, b);
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
