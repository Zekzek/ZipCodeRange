package zip;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class ZipCodeRangeSetTest {

	@Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][]{
			// No change for disjoint ranges
			{new int[][]{{1,1}, {3,3}, {5,5}}, new int[][]{{1,1}, {3,3}, {5,5}}},
			// Merge adjacent ranges - larger
			{new int[][]{{1,1}, {3,4}, {5,6}}, new int[][]{{1,1}, {3,6}}}, 
			// Merge adjacent ranges - smaller
			{new int[][]{{1,1}, {5,6}, {3,4}}, new int[][]{{1,1}, {3,6}}}, 
			// Consume contained ranges
			{new int[][]{{5,5}, {3,3}, {1,1}, {1,5}}, new int[][]{{1,5}}}, 
			// Don't duplicate fully represented ranges
			{new int[][]{{1,5}, {5,5}, {3,3}, {1,1}}, new int[][]{{1,5}}},
			// Merge overlapping ranges - larger
			{new int[][]{{1,4}, {2,5}}, new int[][]{{1,5}}}, 
			// Merge overlapping ranges - smaller
			{new int[][]{{2,5}, {1,4}}, new int[][]{{1,5}}}, 
			// Merge duplicates
			{new int[][]{{3,3}, {3,3}, {1,1}, {1,1}, {3,3}}, new int[][]{{1,1}, {3,3}}},
			// Gracefully handle empty sets
			{new int[0][0], new int[0][0]}, 
			// problem set 1
			{new int[][]{{94133,94133}, {94200,94299}, {94600,94699}}, new int[][]{{94133,94133}, {94200,94299}, {94600,94699}}},  
			// problem set 2
			{new int[][]{{94133,94133}, {94200,94299}, {94226,94399}}, new int[][]{{94133,94133}, {94200,94399}}}, 
		});
	}
	
	private int[][] input;
	private int[][] expectedOutput;
	
	public ZipCodeRangeSetTest(int[][] input, int[][] expectedOutput) {
		this.input = input;
		this.expectedOutput = expectedOutput;
	}
	
	@Test
	public void shouldMergeZipCodeRanges() {
		int[][] actualOutput = ZipCodeRangeSet.getMerged(input);
		
		String errorMessage = Arrays.deepToString(input) 
				+ " resolved to " + Arrays.deepToString(actualOutput) 
				+ " instead of expected " + Arrays.deepToString(expectedOutput);
		assertTrue(errorMessage, Arrays.deepEquals(actualOutput, expectedOutput));
	}
}
