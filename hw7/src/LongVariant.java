
import java.util.Collections;
import java.util.List;

public class LongVariant extends AbstractVariant
{
	private long value;
	
	public LongVariant(long value) 
	{
		super();
		this.value = value;
		this.type = Type.LONG;
	}
	
	public static LongVariant create(long value) 
	{
		return new LongVariant(value);
	}

	public String stringVal()
	{
		return Long.toString(value);
	}
	
	public boolean boolVal()
	{
		return value != 0L;
	}

	public List<Variant> arrayVal() 
	{
		return Collections.<Variant>singletonList(this);
	}

	public double doubleVal()
	{
		return (double) value;
	}

	public long longVal() 
	{
		return value;
	}

}
