/** Class: FindInversionsAndMergeSort_Tester.java
 *  @author Yury Park
 *
 *  This class - Tester class for FindInversionsAndMergeSort.java.
 *  Purpose - Tests whether the above-mentioned class works properly.
 */
public class FindInversionsAndMergeSort_Tester {

	public static void main(String[] args) {

		/* Load a variety of text files and test the algorithm */
		FindInversionsAndMergeSort fims = new FindInversionsAndMergeSort();
		System.out.println("No. of inversions: " + fims.findInversions("IntegerArray_SixElements.txt") + " (expected: 3)");

		fims = new FindInversionsAndMergeSort();
		System.out.println("No. of inversions: " + fims.findInversions("IntegerArray_SixElements_2.txt") + " (expected: 0)");

		fims = new FindInversionsAndMergeSort();
		System.out.println("No. of inversions: " + fims.findInversions("IntegerArray_SixElements_3.txt") + " (expected: 14)");

		fims = new FindInversionsAndMergeSort();
		System.out.println("No. of inversions: " + fims.findInversions("IntegerArray_ThreeElements.txt") + " (expected: 2)");

		fims = new FindInversionsAndMergeSort();
		System.out.println("No. of inversions: " + fims.findInversions("IntegerArray.txt") + " (expected: 2407905288)");
	}
}
