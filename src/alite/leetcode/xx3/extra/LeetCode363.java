package alite.leetcode.xx3.extra;

import java.util.TreeSet;

///**
// * LeetCode 363 - Max Sum of Rectangle No Larger Than K
//
//https://leetcode.com/problems/max-sum-of-sub-matrix-no-larger-than-k/
//Given a non-empty 2D matrix matrix and an integer k, find the max sum of a rectangle 
//in the matrix such that its sum is no larger than k.
//Example:
//Given matrix = [
//  [1,  0, 1],
//  [0, -2, 3]
//]
//k = 2
//The answer is 2. Because the sum of rectangle [[0, 1], [-2, 3]] is 2 and 2 is the max number no larger than k (k = 2).
//Note:
//The rectangle inside the matrix must have an area > 0.
//What if the number of rows is much larger than the number of columns?
//http://bookshadow.com/weblog/2016/06/22/leetcode-max-sum-of-sub-matrix-no-larger-than-k/
//题目可以通过降维转化为求解子问题：和不大于k的最大子数组，解法参考：https://www.quora.com/Given-an-array-of-integers-A-and-an-integer-k-find-a-subarray-that-contains-the-largest-sum-subject-to-a-constraint-that-the-sum-is-less-than-k
//首先枚举列的起止范围x, y，子矩阵matrix[][x..y]可以通过部分和数组sums进行表示：
//sums[i] = Σ(matrix[i][x..y])
//接下来求解sums数组中和不大于k的最大子数组的和。
//如果矩阵的列数远大于行数，则将枚举列变更为枚举行即可。
//    public int maxSumSubmatrix(int[][] matrix, int k) {
//        int m = matrix.length, n = 0;
//        if (m > 0) n = matrix[0].length;
//        if (m * n == 0) return 0;
//        
//        int M = Math.max(m, n);
//        int N = Math.min(m, n);
//        
//        int ans = Integer.MIN_VALUE;
//        for (int x = 0; x < N; x++) {
//            int sums[] = new int[M];
//            for (int y = x; y < N; y++) {
//                TreeSet<Integer> set = new TreeSet<Integer>();
//                int num = 0;
//                for (int z = 0; z < M; z++) {
//                    sums[z] += m > n ? matrix[z][y] : matrix[y][z];
//                    num += sums[z];
//                    if (num <= k) ans = Math.max(ans, num);
//                    Integer i = set.ceiling(num - k);
//                    if (i != null) ans = Math.max(ans, num - i);
//                    set.add(num);
//                }
//            }
//        }
//        return ans;
//    }
//https://www.hrwhisper.me/leetcode-max-sum-rectangle-no-larger-k/
//朴素的思想为，枚举起始行，枚举结束行，枚举起始列，枚举终止列。。。。。O(m^2 * n^2)
//这里用到一个技巧就是，进行求和时，我们可以把二维的合并成一维，然后就变为求一维的解。
//比如对于矩阵：
//[1, 0, 1],
//[0, -2, 3]
//进行起始行为0，终止行为1时，可以进行列的求和，即[1, -2, 3]中不超过k的最大值。
//求和的问题解决完，还有一个是不超过k. 这里我参考了 https://leetcode.com/discuss/109705/java-binary-search-solution-time-complexity-min-max-log-max 的方法
//使用了二分搜索。对于当前的和为sum，我们只需要找到一个最小的数x，使得 sum – k <=x，这样可以保证sum – x <=k。
//这里需要注意，当行远大于列的时候怎么办呢？转换成列的枚举 即可。
//在代码实现上，我们只需要让 m 永远小于 n即可。这样复杂度总是为O(m^2*n*log n)
// int maxSumSubmatrix(vector<vector<int>>& matrix, int k) {
//  if (matrix.empty()) return 0;
//  int ans = INT_MIN,m = matrix.size(), n = matrix[0].size(),row_first=true;
//  if (m > n) {
//   swap(m, n);
//   row_first = false;
//  }
//  for (int ri = 0; ri < m; ri++) {
//   vector<int> temp(n, 0);
//   for (int i = ri; i >= 0; i--) {
//    set<int> sums;
//    int sum = 0;
//    sums.insert(sum);
//    for (int j = 0; j < n; j++) {
//     temp[j] += row_first ? matrix[i][j]: matrix[j][i];
//     sum += temp[j];
//     auto it = sums.lower_bound(sum - k);
//     if (it != sums.end())
//      ans = max(ans, sum - *it);     
//     sums.insert(sum);
//    }
//   }
//  }
//  return ans;
// }
//https://leetcode.com/discuss/109705/java-binary-search-solution-time-complexity-min-max-log-max
///* first  consider the situation matrix is 1D
//    we can save every sum of 0~i(0<=i<len) and binary search previous sum to find 
//    possible result for every index, time complexity is O(NlogN).
//    so in 2D matrix, we can sum up all values from row i to row j and create a 1D array 
//    to use 1D array solution.
//    If col number is less than row number, we can sum up all values from col i to col j 
//    then use 1D array solution.
//*/
//public int maxSumSubmatrix(int[][] matrix, int target) {
//    int row = matrix.length;
//    if(row==0)return 0;
//    int col = matrix[0].length;
//    int m = Math.min(row,col);
//    int n = Math.max(row,col);
//    //indicating sum up in every row or every column
//    boolean colIsBig = col>row;
//    int res = Integer.MIN_VALUE;
//    for(int i = 0;i<m;i++){
//        int[] array = new int[n];
//        // sum from row j to row i
//        for(int j = i;j>=0;j--){
//            int val = 0;
//            TreeSet<Integer> set = new TreeSet<Integer>();
//            set.add(0);
//            //traverse every column/row and sum up
//            for(int k = 0;k<n;k++){
//                array[k]=array[k]+(colIsBig?matrix[j][k]:matrix[k][j]);
//                val = val + array[k];
//                //use  TreeMap to binary search previous sum to get possible result 
//                Integer subres = set.ceiling(val-target);
//                if(null!=subres){
//                    res=Math.max(res,val-subres);
//                }
//                set.add(val);
//            }
//        }
//    }
//    return res;
//}
//Thank you for your great answer! Can you explain why you need to set.add(0) ?? even though s(j) (j < i) may not necessarily contains 0?
//Because the result can start from index 0. e.g. [1,1,5] k=3 solution is 1+1=2. So 0 in set means no need to subtract previous sum.
//X. https://leetcode.com/discuss/109847/2-accepted-java-solution
//There is a simple version of O(n^4). The idea is to calculate every rectangle [[r1,c1], [r2,c2]], and simply pick the max area <= k. An improved version takes O(n^3logn). It borrows the idea to find max subarray with sum <= k in 1D array, and apply here: we find all rectangles bounded between r1 & r2, with columns from 0 to end. Pick a pair from tree. I remember the interviewer said there could be an even better solution, but I haven't figured that out...
//public int maxSumSubmatrix(int[][] matrix, int k) {
//    if (matrix == null || matrix.length == 0 || matrix[0].length == 0)
//        return 0;
//    int rows = matrix.length, cols = matrix[0].length;
//    int[][] areas = new int[rows][cols];
//    for (int r = 0; r < rows; r++) {
//        for (int c = 0; c < cols; c++) {
//            int area = matrix[r][c];
//            if (r-1 >= 0)
//                area += areas[r-1][c];
//            if (c-1 >= 0)
//                area += areas[r][c-1];
//            if (r-1 >= 0 && c-1 >= 0)
//                area -= areas[r-1][c-1];
//            areas[r][c] = area;
//        }
//    }
//    int max = Integer.MIN_VALUE;
//    for (int r1 = 0; r1 < rows; r1++) {
//        for (int c1 = 0; c1 < cols; c1++) {
//            for (int r2 = r1; r2 < rows; r2++) {
//                for (int c2 = c1; c2 < cols; c2++) {
//                    int area = areas[r2][c2];
//                    if (r1-1 >= 0)
//                        area -= areas[r1-1][c2];
//                    if (c1-1 >= 0)
//                        area -= areas[r2][c1-1];
//                    if (r1-1 >= 0 && c1 -1 >= 0)
//                        area += areas[r1-1][c1-1];
//                    if (area <= k)
//                        max = Math.max(max, area);
//                }
//            }
//        }
//    }
//    return max;
//}
//Solution II (O(n^3logn)
//public int maxSumSubmatrix(int[][] matrix, int k) {
//    if (matrix == null || matrix.length == 0 || matrix[0].length == 0)
//        return 0;
//    int rows = matrix.length, cols = matrix[0].length;
//    int[][] areas = new int[rows][cols];
//    for (int r = 0; r < rows; r++) {
//        for (int c = 0; c < cols; c++) {
//            int area = matrix[r][c];
//            if (r-1 >= 0)
//                area += areas[r-1][c];
//            if (c-1 >= 0)
//                area += areas[r][c-1];
//            if (r-1 >= 0 && c-1 >= 0)
//                area -= areas[r-1][c-1];
//            areas[r][c] = area;
//        }
//    }
//    int max = Integer.MIN_VALUE;
//    for (int r1 = 0; r1 < rows; r1++) {
//        for (int r2 = r1; r2 < rows; r2++) {
//            TreeSet<Integer> tree = new TreeSet<>();
//            tree.add(0);    // padding
//            for (int c = 0; c < cols; c++) {
//                int area = areas[r2][c];
//                if (r1-1 >= 0)
//                    area -= areas[r1-1][c];
//                Integer ceiling = tree.ceiling(area - k);
//                if (ceiling != null)
//                    max = Math.max(max, area - ceiling);
//                tree.add(area);
//            }
//        }
//    }
//    return max;
//}
//
//https://reeestart.wordpress.com/2016/06/24/google-largest-matrix-less-than-target/
//这题和Maximum Sub Matrix差不多，只不过Less than target的条件使得Kadane’s Algorithm不再适用于这道题。需要将maximum subarray改成maximum subarray less than target.
//正确的方法要借助cumulative sum,
//sums[j] – sums[i] <= k
//sums[j] <= k + sums[i]
//所以，遍历sums[]数组，对于每个sums[i]，在[i+1, n-1]里面找 满足小于等于k + sums[i]中最大的。找得到就更新result，找不到就不更新。
//那么问题来了，怎么找小于等于k + sums[i]中最大的?
//答曰：Binary Search!
//然后我就对着我的binary search code debug了快4个小时，当知道真相的那一刻眼泪都要掉下来了。
//谁他妈说过cumulative sum一定是递增的？
//那么问题又来了，一个个找要O(n^2) time，还有别的方法么？
//“满足小于等于k + sums[i]中最大的”  ==  floor(k + sums[i])
//看到floor想到什么？
//Red-Black Tree啊！TreeSet啊！TreeMap啊！Balanced Binary Search Tree啊！
//[Time & Space]
//由于Maximum subarray less than Target需要O(nlogn) time，所以总的时间为O(n^3 * logn)
//
//  public int maxSumSubmatrix(int[][] matrix, int k) {
//
//    if (matrix == null || matrix.length == 0) {
//
//      return 0;
//
//    }
//
// 
//
//    int m = matrix.length;
//
//    int n = matrix[0].length;
//
//    int result = Integer.MIN_VALUE;
//
//    for (int i = 0; i < n; i++) {
//
//      int r = i;
//
//      int[] local = new int[m];
//
//      while (r < n) {
//
//        for (int x = 0; x < m; x++) {
//
// 
//
//          local[x] += matrix[x][r];
//
//        }
//
// 
//
//        int localResult = maxSubArray(local, k);
//
//        result = Math.max(result, localResult);
//
//        r++;
//
//      }
//
//    }
//
//    return result;
//
//  }
//
// 
//
//  // maximum subarray less than k
//
//  // can not use two pointer, [2, -7, 6, -1], k = 0
//
// 
//
//  // TreeSet, find ceiling, O(nlogn)
//
//  private int maxSubArray(int[] nums, int k) {
//
//    int result = Integer.MIN_VALUE;
//
//    TreeSet<Integer> tSet = new TreeSet<>();
//
//    int[] sums = new int[nums.length];
//
//    sums[0] = nums[0];
//
//    tSet.add(sums[0]);
//
//    if (sums[0] <= k) {
//
//      result = Math.max(result, sums[0]);
//
//    }
//
//    for (int i = 1; i < nums.length; i++) {
//
//      sums[i] = sums[i - 1] + nums[i];
//
//      if (sums[i] <= k) {
//
//        result = Math.max(result, sums[i]);
//
//      }
//
//      Integer ceil = tSet.ceiling(sums[i] - k);
//
//      if (ceil != null) {
//
//        result = Math.max(result, sums[i] - ceil);
//
//      }
//
//      tSet.add(sums[i]);
//
//    }
//
// 
//
//    return result;
//
//  }
//
// 
//
//  // TreeSet find floor
//
//  private int maxSubArray2(int[] nums, int k) {
//
//    int n = nums.length;
//
//    int result = Integer.MIN_VALUE;
//
//    TreeSet<Integer> tSet = new TreeSet<>();
//
//    int[] sums = new int[n];
//
//    int sum = 0;
//
//    for (int i = 0; i < n; i++) {
//
//      sum += nums[i];
//
//    }
//
// 
//
//    for (int i = n - 1; i >= 0; i--) {
//
//      sums[i] = sum;
//
//      if (sums[i] <= k) {
//
//        result = Math.max(result, sums[i]);
//
//      }
//
//      if (i != n - 1) {
//
//        Integer floor = tSet.floor(sums[i] + k);
//
//        if (floor != null) {
//
//          result = Math.max(result, floor - sums[i]);
//
//        }
//
//      }
//
//      sum -= nums[i];
//
//      tSet.add(sums[i]);
//
//    }
//
//    return result;
//
//  }
// * @author het
// *
// */
public class LeetCode363 {
	public int maxSumSubmatrix(int[][] matrix, int k) {
		int m = matrix.length, n = 0;
		if (m > 0)
			n = matrix[0].length;
		if (m * n == 0)
			return 0;

		int M = Math.max(m, n);
		int N = Math.min(m, n);

		int ans = Integer.MIN_VALUE;
		for (int x = 0; x < N; x++) {
			int sums[] = new int[M];
			for (int y = x; y < N; y++) {
				TreeSet<Integer> set = new TreeSet<Integer>();
				int num = 0;
				for (int z = 0; z < M; z++) {
					sums[z] += m > n ? matrix[z][y] : matrix[y][z];
					num += sums[z];
					if (num <= k)
						ans = Math.max(ans, num);
					Integer i = set.ceiling(num - k);
					if (i != null)
						ans = Math.max(ans, num - i);
					set.add(num);
				}
			}
		}
		return ans;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
