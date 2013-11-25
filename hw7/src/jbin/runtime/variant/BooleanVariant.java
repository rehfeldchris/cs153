package jbin.runtime.variant;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import jbin.runtime.variant.Variant.Type;

public class BooleanVariant extends AbstractVariant
{
	private boolean value;

	public BooleanVariant(boolean value) 
	{
		super();
		this.value = value;
		this.type = Type.BOOLEAN;
	}

	public String stringVal()
	{
		return value ? "WIN" : "FAIL";
	}
	
	public boolean boolVal()
	{
		return value;
	}

	public List<Variant> arrayVal() 
	{
		return Collections.<Variant>singletonList(this);
	}

	public double doubleVal()
	{
		return value ? 1.0D : 0.0D;
	}

	public long longVal() 
	{
		return value ? 1L : 0L;
	}
	
}
