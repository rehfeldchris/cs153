
import java.util.Collections;
import java.util.List;

public class UntypedVariant extends AbstractVariant
{
	public UntypedVariant()
	{
		super();
		this.type = Type.UNTYPED;
	}
	
	public static UntypedVariant create() 
	{
		return new UntypedVariant();
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
		return Collections.<Variant>emptyList();
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
