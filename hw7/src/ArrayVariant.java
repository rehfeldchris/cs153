
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ArrayVariant extends AbstractVariant
{
	private List<? extends Variant> value;
	
	public ArrayVariant(List<? extends Variant> value) 
	{
		super();
		this.value = new ArrayList<>(value);
		this.type = Type.ARRAY;
	}
	
	public static ArrayVariant create(List<? extends Variant> value)
	{
		return new ArrayVariant(value);
	}

	public String stringVal()
	{
		return Arrays.deepToString(value.toArray());
	}
	
	public boolean boolVal()
	{
		return value.size() > 0;
	}

	public List<Variant> arrayVal()
	{
		return Collections.unmodifiableList(value);
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
