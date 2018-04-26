package alite.leetcode.xx4.left;
///**
// * http://bookshadow.com/weblog/2016/10/30/leetcode-arranging-coins/
//You have a total of n coins that you want to form in a staircase shape, where every k-th row must have exactly k coins.
//Given n, find the total number of full staircase rows that can be formed.
//n is a non-negative integer and fits within the range of a 32-bit signed integer.
//Example 1:
//n = 5
//
//The coins can form the following rows:
//¤
//¤ ¤
//¤ ¤
//
//Because the 3rd row is incomplete, we return 2.
//Example 2:
//n = 8
//
//The coins can form the following rows:
//¤
//¤ ¤
//¤ ¤ ¤
//¤ ¤
//
//Because the 4th row is incomplete, we return 3.
//https://discuss.leetcode.com/topic/65631/c-three-solutions-o-n-o-logn-o-1
//The author means O(k), O(1), O(log(k)), where k is the number of staircases. In fact k is of order O(log(n)),
//
//    int way1(int n) {
//        int level = 1;
//        for (long sum = 0; sum <= n; level++) 
//            sum += level;
//        return max(level - 2, 0);    
//    }
//    
//    int way2(int n) {
//        return sqrt(2 * (long)n + 1 / 4.0) - 1 / 2.0;
//    }
//    
//    int arrangeCoins(int n) {
//        long low = 1, high = n;
//        while (low < high) {
//            long mid = low + (high - low + 1) / 2;
//            if ((mid + 1) * mid / 2.0 <= n) low = mid;
//            else high = mid - 1;
//        }
//        return high;
//    }
//
//解法I 解一元二次方程（初等数学）：
//x ^ 2 + x = 2 * n
//解得：
//x = sqrt(2 * n + 1/4) - 1/2
//The problem is basically asking the maximum length of consecutive number that has the running sum lesser or equal to 
//n
//. In other word, find 
//x
// that satisfy the following condition:
//1
//+
//2
//+
//3
//+
//4
//+
//5
//+
//6
//+
//7
//+
//...
//+
//x
//≤
//n
//1
//2
//3
//4
//5
//6
//7
//x
//n
//x
//∑
//i
//=
//1
// 
//i
//≤
//n
//i
//1
//x
//i
//n
//Running sum can be simplified,
//x
//⋅
//(
//x
//+
//1
//)
//2
//≤
//n
//x
//x
//1
//2
//n
//Using quadratic formula, 
//x
// is evaluated to be,
//x
//=
//1
//2
//⋅
//(
//−
//√
//8
//⋅
//n
//+
//1
//−
//1
//)
//x
//1
//2
//8
//n
//1
//1
// (Inapplicable) or 
//x
//=
//1
//2
//⋅
//(
//√
//8
//⋅
//n
//+
//1
//−
//1
//)
//x
//1
//2
//8
//n
//1
//1
//http://blog.csdn.net/mebiuw/article/details/52981579
//    public int arrangeCoins(int n) {
//        /* 数学推导
//        (1+k)*k/2 = n
//        k+k*k = 2*n
//        k*k + k + 0.25 = 2*n + 0.25
//        (k + 0.5) ^ 2 = 2*n +0.25
//        k + 0.5 = sqrt(2*n + 0.25)
//        k = sqrt(2*n + 0.25) - 0.5
//        
//        x = sqrt(2 * n + 1/4) - 1/2
//        */
//        return (int) (Math.sqrt(2*(long)n+0.25) - 0.5);
//    }
//https://discuss.leetcode.com/topic/65575/java-o-1-solution-math-problem
//http://blog.csdn.net/cloudox_/article/details/53005388
//The idea is about quadratic equation, the formula to get the sum of arithmetic progression is
//sum = (x + 1) * x / 2
//so for this problem, if we know the the sum, then we can know the x = (-1 + sqrt(8 * n + 1)) / 2
//(1 + X) * X = 2n
//4X * X + 4 * X = 8n
//(2X + 1)(2X + 1) - 1 = 8n
//X = (Math.sqrt(8 * n + 1) - 1)/ 2
//I just had to add the inequalities to completely convince me:
//If x is the answer, then n coins do fill x rows but don't fill x+1 rows. So we have x(x+1)/2 ≤ n < (x+1)((x+1)+1)/2. Which, using the other formula, turns into x ≤ (sqrt(8n+1) - 1) / 2 < x+1. So we indeed get the right answer by rounding down (sqrt(8n+1) - 1) / 2.
//    public int arrangeCoins(int n) {
//        return (int)((-1 + Math.sqrt(1 + 8 * (long)n)) / 2);
//    }
//https://discuss.leetcode.com/topic/65593/java-clean-code-with-explanations-and-running-time-2-solutions
//解法II 二分枚举答案（Binary Search）：
//等差数列前m项和:m * (m + 1) / 2
//在上下界l, r = [0, n]范围内二分枚举答案
//X.  https://ratchapong.com/algorithm-practice/leetcode/arranging-coins
//The problem is basically asking the maximum length of consecutive number that has the running sum lesser or equal to 
//n
//. In other word, find 
//x
// that satisfy the following condition:
//1
//+
//2
//+
//3
//+
//4
//+
//5
//+
//6
//+
//7
//+
//...
//+
//x
//≤
//n
//1
//2
//3
//4
//5
//6
//7
//x
//n
//x
//∑
//i
//=
//1
// 
//i
//≤
//n
//i
//1
//x
//i
//n
//Running sum can be simplified,
//x
//⋅
//(
//x
//+
//1
//)
//2
//≤
//n
//x
//x
//1
//2
//n
//Binary search is used in this case to slowly narrow down the 
//x
// that will satisfy the equation. Note that 0.5 * mid * mid + 0.5 * mid does not have overflow issue as the intermediate result is implicitly autoboxed to double data type.
//
//    public int arrangeCoins(int n) {
//        int start = 0;
//        int end = n;
//        int mid = 0;
//        while (start <= end) {
//            mid = (start + end) >>> 1;
//            if ((0.5 * mid * mid + 0.5 * mid) <= n) {
//                start = mid + 1;
//            } else {
//                end = mid - 1;
//            }
//        }
//        return start - 1;
//    }
//
//O(k)
//http://www.cnblogs.com/grandyang/p/6026066.html
//这道题给了我们n个硬币，让我们按一定规律排列，第一行放1个，第二行放2个，以此类推，问我们有多少行能放满。通过分析题目中的例子可以得知最后一行只有两种情况，放满和没放满。由于是按等差数列排放的，我们可以快速计算出前i行的硬币总数。我们先来看一种O(n)的方法，非常简单粗暴，就是从第一行开始，一行一行的从n中减去，如果此时剩余的硬币没法满足下一行需要的硬币数了，我们之间返回当前行数即可
//    int arrangeCoins(int n) {
//        int cur = 1, rem = n - 1;
//        while (rem >= cur + 1) {
//            ++cur;
//            rem -= cur;
//        }
//        return n == 0 ? 0 : cur;
//    }
// * @author het
// *
// */
//public class LeetCode441ArrangingCoins {
//	 public int arrangeCoins(int n) {
//	        /* 数学推导
//	        (1+k)*k/2 <= n
//	        k+k*k = 2*n
//	        k*k + k + 0.25 = 2*n + 0.25
//	        (k + 0.5) ^ 2 = 2*n +0.25
//	        k + 0.5 = sqrt(2*n + 0.25)
//	        k = sqrt(2*n + 0.25) - 0.5
//	        
//	        x = sqrt(2 * n + 1/4) - 1/2
//	        */
//	        return (int) (Math.sqrt(2*(long)n+0.25) - 0.5);
//	    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		/* 数学推导
//        (1+k)*k/2 <= n
//        k+k*k = 2*n
//        k*k + k + 0.25 = 2*n + 0.25
//        (k + 0.5) ^ 2 = 2*n +0.25
//        k + 0.5 = sqrt(2*n + 0.25)
//        k = sqrt(2*n + 0.25) - 0.5
//        
//        x = sqrt(2 * n + 1/4) - 1/2
//        */
//        return (int) (Math.sqrt(2*(long)n+0.25) - 0.5);

	}
	}
}
