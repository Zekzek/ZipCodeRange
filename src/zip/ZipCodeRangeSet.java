package zip;

import java.util.ArrayList;
import java.util.List;

public class ZipCodeRangeSet {
	private List<IntRange> zipCodeRanges;
	
	public ZipCodeRangeSet()
	{
		zipCodeRanges = new ArrayList<IntRange>();
	}
	
	public ZipCodeRangeSet(int[][] ranges)
	{
		this();
		for(int[] range : ranges)
			insertRange(range[0], range[1]);
	}
		
	public void insertRange(int beginRange, int endRange)
	{
		int firstConflictIndex = 0;
		while(firstConflictIndex < zipCodeRanges.size() && zipCodeRanges.get(firstConflictIndex).end + 1 < beginRange)
			firstConflictIndex++;
		
		zipCodeRanges.add(firstConflictIndex, new IntRange(beginRange, endRange));
		
		// Continue merging until the next range can't be combined
		while(tryMergeAtIndex(firstConflictIndex));
	}
	
	private boolean tryMergeAtIndex(int startIndex)
	{
		// No next value, so fail to merge
		if (startIndex + 1 >= zipCodeRanges.size())
			return false;
		
		IntRange zipCodeRange1 = zipCodeRanges.get(startIndex);
		IntRange zipCodeRange2 = zipCodeRanges.get(startIndex + 1);
		
		// No overlap and not adjacent, so fail to merge
		if (zipCodeRange1.end + 1 < zipCodeRange2.begin)
			return false;
		
		// Merge is necessary, remove the 2 old ranges and replace with a merged range
		zipCodeRanges.remove(startIndex);
		zipCodeRanges.remove(startIndex);
		int beginRange = Math.min(zipCodeRange1.begin, zipCodeRange2.begin);
		int endRange = Math.max(zipCodeRange1.end, zipCodeRange2.end);
		zipCodeRanges.add(startIndex, new IntRange(beginRange, endRange));
		
		return true;
	}
	
	public int[][] asArray()
	{
		return zipCodeRanges.stream()
				.map(range->new int[]{range.begin, range.end})
				.toArray(int[][]::new);
	}
	
	public static int[][] getMerged(int[][] input)
	{
		return new ZipCodeRangeSet(input).asArray();
	}
}
