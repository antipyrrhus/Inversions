import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/** Class: FindInversionsAndMergeSort.java
 *  @author Yury Park
 *
 *  This class - Efficiently finds the total number of inversions given a list of integers.
 *               For example, the list [5, 3, 4] has two inversions (5,3 and 5,4).
 *
 *  Purpose - To perform the above algorithm in O(n log n) time by using divide-and-conquer approach
 *            and piggybacking off merge sort algorithm (which also runs in O(n log n) time).
 */
public class FindInversionsAndMergeSort {

	private int[] intArr;	//this will save the list of integers

	/**
	 * No-arg constructor.
	 */
	public FindInversionsAndMergeSort() {
		this.intArr = null;
	}

	/**
	 * 1-arg constructor. Used when the total number of integers inside a file is known in advance.
	 * @param sizeOfArray
	 */
	public FindInversionsAndMergeSort(int sizeOfArray) {
		this.intArr = new int[sizeOfArray];
	}

	/**
	 * Method: findInversions
	 * @param fileName the name of the text file (containing integers) to read from
	 * @return the total number of inversions found in the file.
	 */
	public long findInversions(String fileName) {

		//first read from given file
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
			String line = null;
			int index = 0;	//initialize the index of the intArr array.
			if (intArr != null) { //Use when the array size is known in advance
				while ( (line = rd.readLine()) != null) {
					StringTokenizer st = new StringTokenizer(line);
					intArr[index++] = Integer.parseInt(st.nextToken());	//read and save each integer in the file to the array
				}
			} else {   //Use when the array size is NOT known in advance.
				ArrayList<Integer> intAL = new ArrayList<>();
				while ( (line = rd.readLine()) != null) {
					StringTokenizer st = new StringTokenizer(line);
					intAL.add(Integer.parseInt(st.nextToken()));	//read and save each integer in the file
				}
				this.intArr = new int[intAL.size()];   //copy contents from arraylist to array
				for (int i = 0; i < this.intArr.length; ++i)
					this.intArr[i] = intAL.get(i);
			}
			rd.close();
		} catch (FileNotFoundException fnf) {
			fnf.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		//then invoke recursive method to find total no. of inversions
		return numOfInversions(intArr);
	}
	//end public long findInversions

	/**
	 * Method: numOfInversions
	 * @param list array of integers
	 * @return total no. of inversions in the array.
	 */
	public long numOfInversions(int[] list) {
		if(list.length <= 1) return 0;	//Base case - if list contains only one integer, then there are obviously no inversions.

		/* Divide and conquer algorithm. Divide the given list to 2 smaller lists.
		 * Then do recursive call to first half and second half.
		 * NOTE: this also performs a merge sort of the list. */
		int firstHalfLength = list.length / 2;
		int[] firstHalf = new int[firstHalfLength];
		System.arraycopy(list, 0, firstHalf, 0, firstHalfLength);
		long x = numOfInversions(firstHalf);	//Recursive call!

		/* This is the second half. */
		int secondHalfLength = list.length - firstHalfLength;
		int[] secondHalf = new int[secondHalfLength];
		System.arraycopy(list, firstHalfLength, secondHalf, 0, secondHalfLength);
		long y = numOfInversions(secondHalf);	//Recursive call!

		// Merge firstHalf with secondHalf into list and in doing so, we count the number of inversions
		// between these two lists!
		long z = mergeAndCountInversions(firstHalf, secondHalf, list); //custom call
		return x + y + z;	//the sum of x, y and z is the total number of inversions.
	}


	/**
	 * Method: mergeAndCountInversions
	 *         Performs merge-sort and simultaneously counts the number of inversions.
	 * @param list1 list 1 (to be merged with list 2)
	 * @param list2 list 2 (to be merged sith list 1)
	 * @param masterList the master list that consists of list1 and list2. This list will be mutated as list1 and list2 are merge-sorted.
	 * @return the total number of inversions in masterList as dynamically computed during merge-sorting.
	 */
	public static long mergeAndCountInversions(int[] list1, int[] list2, int[] masterList) {
		int current1 = 0; // Current index in list1
		int current2 = 0; // Current index in list2
		int current3 = 0; // Current index in masterList
		long numOfInversions = 0;	//initialize the total no. of inversions

		/* Begin merge-sorting. Continue this while loop as long as both list1 and list2
		 * still have elements in them that haven't been processed. */
		while (current1 < list1.length && current2 < list2.length) {
			/* Mutate the corresponding index of masterList with the smaller of the corresponding elements in list1 and list2.
			 * Case 1: the corresponding element in list1 is smaller than that in list2. Then we just update
			 * the masterList. */
			if (list1[current1] <= list2[current2]) masterList[current3++] = list1[current1++];
			/* Case 2: the corresponding element in list1 is LARGER than that in list2. In this case we
			 * add the element in list2 into the masterList.
			 *
			 * KEY INSIGHT: then, we increment the numOfInversions by the no. of elements that remain in list1 at this moment.*/
			else {
				int remainingElementsInList1 = list1.length - current1;
				numOfInversions += remainingElementsInList1;
				masterList[current3++] = list2[current2++];
			}
		}

		/* After the above while loop terminates, there might still be elements left in either list1 or list2
		 * that haven't been processed yet. */
		while (current1 < list1.length) masterList[current3++] = list1[current1++];
		while (current2 < list2.length)	masterList[current3++] = list2[current2++];

		return numOfInversions;
	}
}
//end public class FindInversionsAndMergeSort