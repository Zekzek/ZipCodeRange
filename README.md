# ZipCodeRange
utility to merge a series of potentially overlapping zip code ranges

To use as a one time utility:
> int[][] zipsArray = ZipCodeRangeSet.getMerged(new int[][]{ {95800, 95900}, {12345, 90210}, {23456, 23456} });

To use as an up-to-date storage of zipcode ranges:
> ZipCodeRangeSet zips = new ZipCodeRangeSet();
> zips.insertRange(95800, 95900);
> zips.insertRange(12345, 90210);
> zips.insertRange(23456, 23456);
> int[][] zipsArray = zips.asArray()