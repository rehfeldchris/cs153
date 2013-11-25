package jbin.runtime.variant;

import java.util.Collections;
import java.util.List;

import jbin.runtime.variant.Variant.Type;


public class StringVariant extends AbstractVariant
{
	private String value;
	
	public StringVariant(String value)
	{
		super();
		this.value = value;
		this.type = Type.STRING;
	}
	
	public String stringVal()
	{
		return value;
	}

	public boolean boolVal()
	{
		return "WIN".equals(value);
	}

	public List<Variant> arrayVal() 
	{
		return Collections.<Variant>singletonList(this);
	}

	public double doubleVal()
	{
		try {
			return Double.parseDouble(value);
		} catch (NumberFormatException e) {}
		return 0.0D;
	}

	public long longVal() 
	{
		try {
			return Long.parseLong(value);
		} catch (NumberFormatException e) {}
		return 0L;
	}

}
