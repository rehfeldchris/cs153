package jbin.runtime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jbin.runtime.variant.DoubleVariant;
import jbin.runtime.variant.LongVariant;
import jbin.runtime.variant.StringVariant;
import jbin.runtime.variant.Variant;
import static jbin.runtime.variant.Variant.Type;

public class Util 
{
	public Variant add(Variant a, Variant b)
	{
		if (typesOf(a, b).contains(Type.DOUBLE)) {
			return new DoubleVariant(
				a.doubleVal() + b.doubleVal()
			);
		}
		
		return new LongVariant(
			a.longVal() + b.longVal()
		);
	}
	
	public Variant subtract(Variant a, Variant b)
	{
		if (typesOf(a, b).contains(Type.DOUBLE)) {
			return new DoubleVariant(
				a.doubleVal() - b.doubleVal()
			);
		}
		
		return new LongVariant(
			a.longVal() - b.longVal()
		);
	}
	
	public Variant multiply(Variant a, Variant b)
	{
		if (typesOf(a, b).contains(Type.DOUBLE)) {
			return new DoubleVariant(
				a.doubleVal() * b.doubleVal()
			);
		}
		
		return new LongVariant(
			a.longVal() * b.longVal()
		);
	}
	
	public Variant divide(Variant a, Variant b)
	{
		if (typesOf(a, b).contains(Type.DOUBLE)) {
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
	
	public Variant mod(Variant a, Variant b)
	{
		long result = 0;
		try {
			result = a.longVal() % b.longVal();
		} catch (ArithmeticException e) {}
		
		return new LongVariant(result);
	}
	
	public Variant concat(Variant...variants)
	{
		String buf = "";
		for (Variant v : variants) {
			buf += v.stringVal();
		}
		return new StringVariant(buf);
	}
	
	
	
	
	private List<Type> typesOf(Variant... variants) {
		List<Type> types = new ArrayList<>();
		for (Variant v : variants) {
			types.add(v.getType());
		}
		return types;
	}
}
