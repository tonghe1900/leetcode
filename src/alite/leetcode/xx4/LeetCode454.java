package alite.leetcode.xx4;

import java.util.HashMap;
import java.util.Map;

/**
 * LeetCode 454 - 4Sum II

http://bookshadow.com/weblog/2016/11/13/leetcode-4sum-ii/
Given four lists A, B, C, D of integer values, compute how many tuples (i, j, k, l)
 there are such that A[i] + B[j] + C[k] + D[l] is zero.
To make problem a bit easier, all A, B, C, D have same length of N where 0 ≤ N ≤ 500.
 All integers are in the range of -228 to 228 - 1 and the result is guaranteed to be at most 231 - 1.
Example:
Input:
A = [ 1, 2]
B = [-2,-1]
C = [-1, 2]
D = [ 0, 2]

Output:
2

Explanation:
The two tuples are:
1. (0, 0, 0, 1) -> A[0] + B[0] + C[0] + D[1] = 1 + (-2) + (-1) + 2 = 0
2. (1, 1, 0, 0) -> A[1] + B[1] + C[0] + D[0] = 2 + (-1) + (-1) + 0 = 0
https://discuss.leetcode.com/topic/67593/clean-java-solution-o-n-2
public int fourSumCount(int[] A, int[] B, int[] C, int[] D) {
    Map<Integer, Integer> map = new HashMap<>();
    
    for(int i=0; i<C.length; i++) {
        for(int j=0; j<D.length; j++) {
            int sum = C[i] + D[j];
            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }
    }
    
    int res=0;
    for(int i=0; i<A.length; i++) {
        for(int j=0; j<B.length; j++) {
            res += map.getOrDefault(-1 * (A[i]+B[j]), 0);
        }
    }
    
    return res;
}

Time complexity:  O(n^2)
Space complexity: O(n^2)
https://discuss.leetcode.com/topic/68168/dividing-arrays-into-two-parts-full-thinking-process-from-naive-n-4-to-effective-n-2-solution
The naive solution is to run four loops by iterating all elements and check for (A[i] + B[j] + C[k] + d[h]) == 0. Time complexity: N^4
We can improve solution by iterating through elements of three arrays and check if the fourth array contains A[i] + B[j] + C[k] + d == 0 ----> d = -A[i] - B[j] - C[k]. We can use HashSet to store elements of fourth array. Overall time complexity: N^3;
To improve the solution we can divide arrays into two parts. Then make calculation of sums of one part (A[i] + B[j]) and store their sum's occurences counter in a HashMap. While calculating second part arrays' sum (secondSum = C[k] + D[h]) we can check whether map contains secondSum*(-1);
A[i] + B[j] == - C[k] - D[h]
A[i] + B[j] == - (C[k]+D[h])
This solution can be extended for N arrays.
    public int fourSumCount(int[] A, int[] B, int[] C, int[] D) {
        HashMap<Integer, Integer> sumCounter = getSumCounters(A,B);
        int fourSumCounter = 0;
        for (int c : C) {
            for (int d: D) {
                fourSumCounter += sumCounter.getOrDefault(c+d, 0);
            }
        }
        return fourSumCounter;
    }
    
    private HashMap<Integer, Integer> getSumCounters(int [] A, int [] B) {
        HashMap<Integer, Integer> sumCounter = new HashMap<>();
        for (int a : A) {
            for (int b: B) {
                int sum = -a-b;
                sumCounter.put(sum, sumCounter.getOrDefault(sum, 0) + 1);
            }
        }
        return sumCounter;
    }
https://discuss.leetcode.com/topic/67658/simple-java-solution-with-explanation
Take the arrays A and B, and compute all the possible sums of two elements. Put the sum in the Hash map, and increase the hash map value if more than 1 pair sums to the same value.
Compute all the possible sums of the arrays C and D. If the hash map contains the opposite value of the current sum, increase the count of four elements sum to 0 by the counter in the map.
public int fourSumCount(int[] A, int[] B, int[] C, int[] D) {
 Map<Integer,Integer> sums = new HashMap<>();
 int count = 0;
 for(int i=0; i<A.length;i++) {
  for(int j=0;j<B.length;j++){
   int sum = A[i]+B[j];
   if(sums.containsKey(sum)) {
    sums.put(sum, sums.get(sum)+1);
   } else {
    sums.put(sum, 1);
   }
  }
 }
 for(int k=0; k<C.length;k++) {
  for(int z=0;z<D.length;z++){
   int sum = -(C[k]+D[z]);
   if(sums.containsKey(sum)) {
    count+=sums.get(sum);
   }
  }
 }
 return count;
}

利用字典cnt，将A，B中各元素（笛卡尔积）的和进行分类计数。
将C，D中各元素（笛卡尔积）和的相反数在cnt中的值进行累加，即为答案。
    def fourSumCount(self, A, B, C, D):
        """
        :type A: List[int]
        :type B: List[int]
        :type C: List[int]
        :type D: List[int]
        :rtype: int
        """
        ans = 0
        cnt = collections.defaultdict(int)
        for a in A:
            for b in B:
                cnt[a + b] += 1
        for c in C:
            for d in D:
                ans += cnt[-(c + d)]
        return ans
https://discuss.leetcode.com/topic/67659/easy-2-lines-o-n-2-python/
def fourSumCount(self, A, B, C, D):
    AB = collections.Counter(a+b for a in A for b in B)
    return sum(AB[-c-d] for c in C for d in D)
 * @author het
 *
 */
public class LeetCode454 {
	public int fourSumCount(int[] A, int[] B, int[] C, int[] D) {
	 Map<Integer, Integer> map = new HashMap<>();
	    
	    for(int i=0; i<C.length; i++) {
	        for(int j=0; j<D.length; j++) {
	            int sum = C[i] + D[j];
	            map.put(sum, map.getOrDefault(sum, 0) + 1);
	        }
	    }
	    
	    int res=0;
	    for(int i=0; i<A.length; i++) {
	        for(int j=0; j<B.length; j++) {
	            res += map.getOrDefault(-1 * (A[i]+B[j]), 0);
	        }
	    }
	    
	    return res;
	}
}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
