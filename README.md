# ZipCodeRange
utility to merge a series of potentially overlapping zip code ranges

This utility takes a 2-dimensional array as input. Each entry in the array is treated as a range containing start and end values. For example, [[12345,12345]] represents the single zipcode 12345, while [[12300,12302],[12305,12305]] represents 12300, 12301, 12302, and 12305. The utility combines ranges where posiible.

To use as a one time utility:
```
int[][] zipsArray = ZipCodeRangeSet.getMerged(new int[][]{ {95800, 95900}, {12345, 90210}, {23456, 23456} });
// [[12345,90210],[95800,95900]]
```

To use as an up-to-date storage of zipcode ranges:
```
ZipCodeRangeSet zips = new ZipCodeRangeSet(); 
zips.insertRange(95800, 95900); 
zips.insertRange(12345, 90210); 
zips.insertRange(23456, 23456); 
int[][] zipsArray = zips.asArray();
// [[12345,90210],[95800,95900]]
```
