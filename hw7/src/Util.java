
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
	
	public static Variant concat(Variant...variants)
	{
		String buf = "";
		for (Variant v : variants) {
			buf += v.stringVal();
		}
		return new StringVariant(buf);
	}
	
	private static List<Variant.Type> typesOf(Variant... variants)
	{
		List<Variant.Type> types = new ArrayList<>();
		for (Variant v : variants) {
			types.add(v.getType());
		}
		return types;
	}
}
