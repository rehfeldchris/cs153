
import java.util.Collections;
import java.util.List;

public class DoubleVariant extends AbstractVariant
{
	private double value;

	public DoubleVariant(double value) 
	{
		super();
		this.value = value;
		this.type = Type.DOUBLE;
	}
	
	public static DoubleVariant create(double value) 
	{
		return new DoubleVariant(value);
	}

	public String stringVal()
	{
		return Double.toString(value);
	}

	public boolean boolVal()
	{
		return Double.compare(value, 0.0d) != 0;
	}

	public List<Variant> arrayVal() 
	{
		return Collections.<Variant>singletonList(this);
	}

	public double doubleVal()
	{
		return value;
	}

	public long longVal() 
	{
		return (long) value;
	}

}
