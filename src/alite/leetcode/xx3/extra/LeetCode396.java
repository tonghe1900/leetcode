package alite.leetcode.xx3.extra;
/**
 * LeetCode LeetCode 396 - Rotate Function

http://bookshadow.com/weblog/2016/09/11/leetcode-rotate-function/
Given an array of integers A and let n to be its length.
Assume Bk to be an array obtained by rotating the array A k positions
 clock-wise, we define a "rotation function" F on A as follow:
F(k) = 0 * Bk[0] + 1 * Bk[1] + ... + (n-1) * Bk[n-1].
Calculate the maximum value of F(0), F(1), ..., F(n-1).
Note:
n is guaranteed to be less than 105.
Example:
A = [4, 3, 2, 6]

F(0) = (0 * 4) + (1 * 3) + (2 * 2) + (3 * 6) = 0 + 3 + 4 + 18 = 25
F(1) = (0 * 6) + (1 * 4) + (2 * 3) + (3 * 2) = 0 + 4 + 6 + 6 = 16
F(2) = (0 * 2) + (1 * 6) + (2 * 4) + (3 * 3) = 0 + 6 + 8 + 9 = 23
F(3) = (0 * 3) + (1 * 2) + (2 * 6) + (3 * 4) = 0 + 2 + 12 + 12 = 26

So the maximum value of F(0), F(1), F(2), F(3) is F(3) = 26.
https://discuss.leetcode.com/topic/58459/java-o-n-solution-with-explanation
F(k) = 0 * Bk[0] + 1 * Bk[1] + ... + (n-1) * Bk[n-1]
F(k-1) = 0 * Bk-1[0] + 1 * Bk-1[1] + ... + (n-1) * Bk-1[n-1]
       = 0 * Bk[1] + 1 * Bk[2] + ... + (n-2) * Bk[n-1] + (n-1) * Bk[0]
Then,
F(k) - F(k-1) = Bk[1] + Bk[2] + ... + Bk[n-1] + (1-n)Bk[0]
              = (Bk[0] + ... + Bk[n-1]) - nBk[0]
              = sum - nBk[0]
Thus,
F(k) = F(k-1) + sum - nBk[0]
What is Bk[0]?
k = 0; B[0] = A[0];
k = 1; B[0] = A[len-1];
k = 2; B[0] = A[len-2];
...
int allSum = 0;
int len = A.length;
int F = 0;
for (int i = 0; i < len; i++) {
    F += i * A[i];
    allSum += A[i];
}
int max = F;
for (int i = len - 1; i >= 1; i--) {
    F = F + allSum - len * A[i];
    max = Math.max(F, max);
}
return max;   
I think the above deductions may have some flaws. Because we cannot get the F(0) from F(k). Personally I would use only
the base case, k = 0
F(0) = 0 * Bk[0] + 1 * Bk[1] + ... + (n - 1) * Bk[n - 1]
and the case when k > 0 and k < n
F(k) = 0 * Bk[n - k] + 1 * Bk[n - k + 1] + ... + (n - 1) * Bk[n - (k + 1)]
For those who got confused why we will start from i = len - 1:
F(1) = F(0) + sum - n * A[n - 1], here n = len so that A[n - 1] == A[i]
F(2) = F(1) + sum - n * A[n - 2]
...
F(k) = F(k - 1) + sum - n * A[n - k]
Why we will end if i < 1? It's because we deduced k < n and k > 0. So that it's obviousn - k > 0, which means i > 0.
https://discuss.leetcode.com/topic/58616/java-solution-o-n-with-non-mathametical-explaination
    public int maxRotateFunction(int[] A) {
        int sumA = 0; int prevRotationSum = 0;
        for (int i = 0; i < A.length; i++) {
            sumA += A[i];
            prevRotationSum += i * A[i];
        }
        int max = prevRotationSum;

        for (int i = A.length -1; i > 0; i--){
            prevRotationSum += sumA - A.length * A[i];
            max = Math.max(prevRotationSum, max);
        }
        return max;
    }
https://discuss.leetcode.com/topic/58389/java-solution
This is essentially a Math problem.
Consider the array [ A, B, C, D ] with very simple coefficients as following:
f(0) = 0A + 1B + 2C + 3D
f(1) = 3A + 0B + 1C + 2D
f(2) = 2A + 3B + 0C + 1D
f(3) = 1A + 2B + 3C + 0D
We can see from above that:
f(0) -> f(1) -> f(2) -> f(3)
f(i) = f(i - 1) - SUM(A -> B) + N * A[i - 1]
    public int maxRotateFunction(int[] A) {
        int n = A.length;
 int sum = 0;
 int candidate = 0;

 for (int i = 0; i < n; i++) {
  sum += A[i];
  candidate += A[i] * i;
 }
 int best = candidate;

 for (int i = 1; i < n; i++) {
  candidate = candidate - sum + A[i - 1] * n;
  best = Math.max(best, candidate);
 }
 return best;
    }
https://discuss.leetcode.com/topic/58302/java-solution
 public int maxRotateFunction(int[] A) {
  int n = A.length;
  int sum = 0;
  int candidate = 0;

  for (int i = 0; i < n; i++) {
   sum += A[i];
   candidate += A[i] * i;
  }
  int best = candidate;

  for (int i = n - 1; i > 0; i--) {
   candidate = candidate + sum - A[i] * n;
   best = Math.max(best, candidate);
  }
  return best;
 }

假设数组A的长度为5，其旋转函数F的系数向量如下所示：
0 1 2 3 4
1 2 3 4 0
2 3 4 0 1
3 4 0 1 2
4 0 1 2 3
用每一行系数与其上一行做差，差值恰好为sum(A) + size * A[size - x]，其中x为行数
因此，通过一次遍历即可求出F(0), F(1), ..., F(n-1)的最大值。
    def maxRotateFunction(self, A):
        """
        :type A: List[int]
        :rtype: int
        """
        size = len(A)
        sums = sum(A)
        sumn = sum(x * n for x, n in enumerate(A))
        ans = sumn
        for x in range(size - 1, 0, -1):
            sumn += sums - size * A[x]
            ans = max(ans, sumn)
        return ans
 * @author het
 *  clock-wise, we define a "rotation function" F on A as follow:
F(k) = 0 * Bk[0] + 1 * Bk[1] + ... + (n-1) * Bk[n-1].
Calculate the maximum value of F(0), F(1), ..., F(n-1).
Note:
n is guaranteed to be less than 105.
Example:
A = [4, 3, 2, 6]

F(0) = (0 * 4) + (1 * 3) + (2 * 2) + (3 * 6) = 0 + 3 + 4 + 18 = 25
F(1) = (0 * 6) + (1 * 4) + (2 * 3) + (3 * 2) = 0 + 4 + 6 + 6 = 16
F(2) = (0 * 2) + (1 * 6) + (2 * 4) + (3 * 3) = 0 + 6 + 8 + 9 = 23
F(3) = (0 * 3) + (1 * 2) + (2 * 6) + (3 * 4) = 0 + 2 + 12 + 12 = 26
 *
 */
public class LeetCode396 {
	public int maxRotateFunction(int[] A) {
		  int n = A.length;
		  int sum = 0;
		  int candidate = 0;

		  for (int i = 0; i < n; i++) {
		   sum += A[i];
		   candidate += A[i] * i;
		  }
		  int best = candidate;

		  for (int i = n - 1; i > 0; i--) {
		   candidate = candidate + sum - A[i] * n;
		   best = Math.max(best, candidate);
		  }
		  return best;
		 }
}
