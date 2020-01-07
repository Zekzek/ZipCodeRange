package zip;

public class IntRange {
	
	public final int begin;
	public final int end;
	
	public IntRange(int begin, int end)
	{
		if (end < begin)
			throw new IllegalArgumentException("end cannot be less than begin");
		
		this.begin = begin;
		this.end = end;
	}
}
