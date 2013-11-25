package jbin.runtime.variant;

import java.util.Collections;
import java.util.List;

import jbin.runtime.variant.Variant.Type;

public class UntypedVariant extends AbstractVariant
{
	public UntypedVariant()
	{
		super();
		this.type = Type.UNTYPED;
	}

	public String stringVal()
	{
		return "NOOB";
	}

	public boolean boolVal()
	{
		return false;
	}

	public List<Variant> arrayVal() 
	{
		return Collections.<Variant>singletonList(this);
	}

	public double doubleVal()
	{
		return 0.0D;
	}

	public long longVal() 
	{
		return 0L;
	}
	
}
