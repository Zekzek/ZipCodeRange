package zip;

import java.util.ArrayList;
import java.util.List;

public class ZipCodeRangeSet {
	private List<IntRange> zipCodeRanges;
	
	public ZipCodeRangeSet()
	{
		zipCodeRanges = new ArrayList<IntRange>();
	}
	
	/**
	 * Create an object initialized with zip code ranges
 	 * <p>
	 * The zip codes are stored as a series of ranges of integers (each range includes both their upper and lower bounds)
	 *
	 * @param ranges the initial zip code ranges
	 */
	public ZipCodeRangeSet(int[][] ranges)
	{
		this();
		for(int[] range : ranges)
			insertRange(range[0], range[1]);
	}
		
	/**
	 * Insert a new zip code range while handling any conflicts with existing ranges
	 * 
	 * @param beginRange the beginning of the zip code range to add
	 * @param endRange the end of the zip code range to add (cannot be less that beginRange)
	 */
	public void insertRange(int beginRange, int endRange)
	{
		if (endRange < beginRange)
			throw new IllegalArgumentException("endRange cannot be less than beginRange");
		
		int firstConflictIndex = 0;
		while(firstConflictIndex < zipCodeRanges.size() && zipCodeRanges.get(firstConflictIndex).end + 1 < beginRange)
			firstConflictIndex++;
		
		zipCodeRanges.add(firstConflictIndex, new IntRange(beginRange, endRange));
		
		// Continue merging until the next range can't be combined
		while(tryMergeAtIndex(firstConflictIndex));
	}
	
	/**
	 * Try to merge a zip code range with the next one in the list
	 * 
	 * @param startIndex the index of the zip code range to merge 
	 * @return whether the merge was successful 
	 */
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
	
	/**
	 * Creates a copy of the zip code ranges in the array syntax
	 * <p>
	 * The zip codes are stored as a series of ranges of integers (each range includes both their upper and lower bounds)
	 * 
	 * @return the current array of zip code ranges as a 2-dimensional array
	 */
	public int[][] asArray()
	{
		return zipCodeRanges.stream()
				.map(range->new int[]{range.begin, range.end})
				.toArray(int[][]::new);
	}
	
	/**
	 * Combines overlapping and adjacent zip code ranges. 
	 * <p>
	 * The zip codes are stored as a series of ranges of integers (each range includes both their upper and lower bounds)
	 *  
	 * @param input the initial array of zip code ranges
	 * @return the simplified array of zip code ranges
	 */
	public static int[][] getMerged(int[][] input)
	{
		return new ZipCodeRangeSet(input).asArray();
	}
}
