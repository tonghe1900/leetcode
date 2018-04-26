package alite.leetcode.xx3;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * LeetCode 307 Range Sum Query - Mutable
 * 
 * LeetCode
 * 
 * 题目描述
 * 
 * 
 * Given an integer array nums, find the sum of the elements between indices i
 * and j (i ≤ j), inclusive. The update(i, val) function modifies nums by
 * updating the element at index i to val. Example: Given nums = [1, 3, 5]
 * 
 * sumRange(0, 2) -> 9 update(1, 2) sumRange(0, 2) -> 8 Note: The array is only
 * modifiable by the update function. You may assume the number of calls to
 * update and sumRange function is distributed evenly.
 * 
 * Given an integer array nums, find the sum of the elements between indices i
 * and j (i ≤ j), inclusive.
 * 
 * The update(i, val) function modifies nums by updating the element at index i
 * to val.
 * 
 * Example:
 * 
 * Given nums = [1, 3, 5]
 * 
 * sumRange(0, 2) -> 9 update(1, 2) sumRange(0, 2) -> 8 Note:
 * 
 * The array is only modifiable by the update function. You may assume the
 * number of calls to update and sumRange function is distributed evenly. 分析
 * 
 * LeetCode 303 的数组是不可变的，本题的数组是可变的。可以使用树状数组，维基百科链接，不再具体介绍（不重复造轮子）。
 * 
 * 树状数组(Fenwick_tree)，最早由Peter M. Fenwick于1994年以A New Data Structure for
 * Cumulative Frequency Tables1为题发表在SOFTWARE PRACTICE AND
 * EXPERIENCE。其初衷是解决数据压缩里的累积频率(Cumulative
 * Frequency)的计算问题，现多用于高效计算数列的前缀和。它可以以O(\log n)的时间得到\sum_{i=1}^N a[i]，并同样以O(\log
 * n)对某项加一个常数。
 * 
 * 初始化复杂度最优为O(N\log N) 单次询问复杂度O(\log N),其中N为数组大小 单次修改复杂度O(\log N),其中N为数组大小
 * 空间复杂度O(N)
 * 
 * 1.png-25.1kB
 * 
 * 代码 //fenwick tree public class NumArray { // array存储
 * nums数组，helper用来维护array的前缀和 int[] array, helper; public NumArray(int[] nums) {
 * array = new int[nums.length]; helper = new int[nums.length + 1]; for (int i =
 * 0; i < nums.length; i++) { array[i] = nums[i]; } for (int i = 0; i <
 * nums.length; i++) { add(i + 1, nums[i]); } } private void add(int pos, int
 * value) { while (pos < helper.length) { helper[pos] += value; pos +=
 * lowBit(pos); }
 * 
 * //lowBit(pos); // pos & (-pos); pos & (-pos); pos & (-pos); } // lowBit pos &
 * (-pos) // 预备函数，返回参数转为二进制后,最后一个1的位置所代表的数值 private int lowBit(int pos) { return
 * pos & (-pos); // pos& (-pos) // pos &(-pos) } private int sum(int pos) { int
 * rt = 0; while (pos > 0) { rt += helper[pos]; pos -= lowBit(pos); } return rt;
 * } void update(int i, int val) { int delta = val - array[i]; array[i] = val;
 * add(i + 1, delta); } public int sumRange(int i, int j) { return sum(j + 1) -
 * sum(i); } }
 * 
 * @author het
 *
 */
public class LeetCode307 {
	// Segment TRee

	/**
	 * Created by ricardodpsx@gmail.com on 4/01/15.
	 * <p>
	 * In {@code Fenwick Tree} structure We arrange the array in an smart way to
	 * perform efficient <em>range queries and updates</em>. The key point is this:
	 * In a fenwick array, each position "responsible" for storing cumulative data
	 * of N previous positions (N could be 1) For example: array[40] stores:
	 * array[40] + array[39] ... + array[32] (8 positions) array[32] stores:
	 * array[32] + array[31] ... + array[1] (32 positions)
	 * <p>
	 * <strong>But, how do you know how much positions a given index is
	 * "responsible" for?</strong>
	 * <p>
	 * To know the number of items that a given array position 'ind' is responsible
	 * for We should extract from 'ind' the portion up to the first significant one
	 * of the binary representation of 'ind' for example, given ind == 40 (101000 in
	 * binary), according to Fenwick algorithm what We want is to extract 1000(8 in
	 * decimal).
	 * <p>
	 * This means that array[40] has cumulative information of 8 array items. But We
	 * still need to know the cumulative data bellow array[40 - 8 = 32] 32 is 100000
	 * in binnary, and the portion up to the least significant one is 32 itself! So
	 * array[32] has information of 32 items, and We are done!
	 * <p>
	 * So cummulative data of array[1...40] = array[40] + array[32] Because 40 has
	 * information of items from 40 to 32, and 32 has information of items from 32
	 * to 1
	 * <p>
	 * Memory usage: O(n)
	 *
	 * @author Ricardo Pacheco
	 */
	static class FenwickTree {

		int[] array; // 1-indexed array, In this array We save cumulative information to perform
						// efficient range
		// queries and updates

		public FenwickTree(int size) {
			array = new int[size + 1];
		}

		/**
		 * Range Sum query from 1 to ind ind is 1-indexed
		 * <p>
		 * Time-Complexity: O(log(n))
		 *
		 * @param ind
		 *            index
		 * @return sum
		 */
		public int rsq(int ind) {
			assert ind > 0;
			int sum = 0;
			while (ind > 0) {
				sum += array[ind];
				// Extracting the portion up to the first significant one of the binary
				// representation
				// of 'ind' and decrementing ind by that number
				ind -= ind & (-ind);  //ind & (-ind);   //ind & (-ind);
				//ind & (-ind);   ind & (-ind);
			}

			return sum;
		}

		/**
		 * Range Sum Query from a to b. Search for the sum from array index from a to b
		 * a and b are 1-indexed
		 * <p>
		 * Time-Complexity: O(log(n))
		 *
		 * @param a
		 *            left index
		 * @param b
		 *            right index
		 * @return sum
		 */
		public int rsq(int a, int b) {
			assert b >= a && a > 0 && b > 0;

			return rsq(b) - rsq(a - 1);
		}

		/**
		 * Update the array at ind and all the affected regions above ind. ind is
		 * 1-indexed
		 * <p>
		 * Time-Complexity: O(log(n))
		 *
		 * @param ind
		 *            index
		 * @param value
		 *            value
		 */
		public void update(int ind, int value) {
			assert ind > 0;
			while (ind < array.length) {
				array[ind] += value;
				// Extracting the portion up to the first significant one of the binary
				// representation of 'ind' and incrementing ind by that number
				ind += ind & (-ind);
				//ind & (-ind);
			}
		}

		public int size() {
			return array.length - 1;
		}

		/**
		 * Read the following commands: init n Initializes the array of size n all
		 * zeroes set a b c Initializes the array with [a, b, c ...] rsq a b Range Sum
		 * Query for the range [a,b] up i v Update the i position of the array with
		 * value v. exit
		 * <p>
		 * The array is 1-indexed Example: set 1 2 3 4 5 6 rsq 1 3 Sum from 1 to 3 = 6
		 * rmq 1 3 Min from 1 to 3 = 1 input up 1 3 [3,2,3,4,5,6]
		 *
		 * @param args
		 *            the command-line arguments
		 */

	}

	public static void main(String[] args) {

		FenwickTree ft = new FenwickTree(500);
		ft.update(1, 30);// \
		ft.update(2, 10);
		ft.update(3, 15);
		System.out.println(ft.rsq(3));

		// String cmd = "cmp";
		// while (true) {
		// String[] line = StdIn.readLine().split(" ");
		//
		// if (line[0].equals("exit")) break;
		//
		// int arg1 = 0, arg2 = 0;
		//
		// if (line.length > 1) {
		// arg1 = Integer.parseInt(line[1]);
		// }
		// if (line.length > 2) {
		// arg2 = Integer.parseInt(line[2]);
		// }
		//
		// if ((!line[0].equals("set") && !line[0].equals("init")) && ft == null) {
		// StdOut.println("FenwickTree not initialized");
		// continue;
		// }
		//
		// if (line[0].equals("init")) {
		// ft = new FenwickTree(arg1);
		// for (int i = 1; i <= ft.size(); i++) {
		// StdOut.print(ft.rsq(i, i) + " ");
		// }
		// StdOut.println();
		// }
		// else if (line[0].equals("set")) {
		// ft = new FenwickTree(line.length - 1);
		// for (int i = 1; i <= line.length - 1; i++) {
		// ft.update(i, Integer.parseInt(line[i]));
		// }
		// }
		//
		// else if (line[0].equals("up")) {
		// ft.update(arg1, arg2);
		// for (int i = 1; i <= ft.size(); i++) {
		// StdOut.print(ft.rsq(i, i) + " ");
		// }
		// StdOut.println();
		// }
		// else if (line[0].equals("rsq")) {
		// StdOut.printf("Sum from %d to %d = %d%n", arg1, arg2, ft.rsq(arg1, arg2));
		// }
		// else {
		// StdOut.println("Invalid command");
		// }
		//
		// }
		//
		//
		// StdOut.close();
	}

}
