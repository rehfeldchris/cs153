package jbin.runtime.variant;

import java.util.List;

public interface Variant
{
	public static enum Type {UNTYPED, BOOLEAN, STRING, ARRAY, DOUBLE, LONG}
	public Type getType();
	public Variant typeCast(Type newType);
	public String stringVal();
	public boolean boolVal();
	public List<Variant> arrayVal();
	public double doubleVal();
	public long longVal();
}
