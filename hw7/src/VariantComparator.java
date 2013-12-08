import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


/**
 * Compares variants using complicated rules similar to php's rules.
 * 
 * in general, we look at both types, and then convert one of
 * the variants to have the same type as the other so we can compare them.
 * 
 * an exception is strings. both args could be strings, but if one of them is a 
 * numeric looking string, then both will be converted to numerics, and then compared.
 * 
 * 
 * 
 * @param a
 * @param b
 * @return
 */

public class VariantComparator implements Comparator<Variant>
{
	public static final VariantComparator INSTANCE = new VariantComparator();
	
	public int compare(Variant a, Variant b)
	{
		if (typesOf(a, b).contains(Variant.Type.BOOLEAN)) {
			boolean aVal = a.boolVal();
			boolean bVal = b.boolVal();
			
			if (aVal && !bVal) {
				return -1;
			} else if (!aVal && bVal) {
				return 1;
			} else {
				return 0;
			}
		}
		
		if (looksLikeNumericString(a) || looksLikeNumericString(b)) {
			return Double.compare(
				  a.toNumeric().doubleVal()
				, b.toNumeric().doubleVal()
			);
		}
		
		if (typesOf(a, b).contains(Variant.Type.DOUBLE)) {
			double aVal = a.doubleVal();
			double bVal = b.doubleVal();
			
			if (aVal < bVal) {
				return -1;
			} else if (aVal > bVal) {
				return 1;
			} else {
				return 0;
			}
		}
		
		if (typesOf(a, b).contains(Variant.Type.LONG)) {
			long aVal = a.longVal();
			long bVal = b.longVal();
			
			if (aVal < bVal) {
				return -1;
			} else if (aVal > bVal) {
				return 1;
			} else {
				return 0;
			}
		}
		
		
		if (a.getType() == Variant.Type.STRING && b.getType() == Variant.Type.STRING) {
			return a.stringVal().compareTo(b.stringVal());
		}
		
		
		if (a.getType() == Variant.Type.ARRAY && b.getType() == Variant.Type.ARRAY) {
			List<Variant> aChildren = a.arrayVal();
			List<Variant> bChildren = b.arrayVal();
			int maxElems = Math.max(aChildren.size(), bChildren.size());
			for (int i = 0; i < maxElems; i++) {
				int cmp = compare(aChildren.get(i), bChildren.get(i));
				if (cmp != 0) {
					return cmp;
				}
			}
			if (aChildren.size() < bChildren.size()) {
				return -1;
			} else if (aChildren.size() > bChildren.size()) {
				return 1;
			} else {
				return 0;
			}
		}
		
		if (typesOf(a, b).contains(Variant.Type.ARRAY)) {
			List<Variant> aChildren = a.arrayVal();
			List<Variant> bChildren = b.arrayVal();
			if (aChildren.size() < bChildren.size()) {
				return -1;
			} else if (aChildren.size() > bChildren.size()) {
				return 1;
			} else {
				return 0;
			}
		}
		
		
		return a.stringVal().compareTo(b.stringVal());
	}
	
	
	/**
	 * returns true if its a StringVariant and its string contents could parse as a double or long.
	 */
	private static boolean looksLikeNumericString(Variant v)
	{
		if (v instanceof StringVariant) {
			try {
				Double.parseDouble(v.stringVal());
				return true;
			} catch (NumberFormatException e) {}
		}
		return false;
	}
	

	
	/**
	 * this method just returns a list of the types of the variants. i do 
	 * this so ican easily search the list to see if it contains a double type
	 * so i know whether to use integer or float arithmetic.
	 * 
	 * @param variants
	 * @return
	 */
	private static List<Variant.Type> typesOf(Variant... variants)
	{
		List<Variant.Type> types = new ArrayList<>();
		for (Variant v : variants) {
			types.add(v.getType());
		}
		return types;
	}
}
