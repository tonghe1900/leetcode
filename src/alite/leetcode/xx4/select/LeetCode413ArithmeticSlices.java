package alite.leetcode.xx4.select;
/**
 * https://leetcode.com/problems/arithmetic-slices/
A sequence of number is called arithmetic if it consists of at least three elements and
 if the difference between any two consecutive elements is the same.
For example, these are arithmetic sequence:
1, 3, 5, 7, 9
7, 7, 7, 7
3, -1, -5, -9
The following sequence is not arithmetic.
1, 1, 2, 5, 7

A zero-indexed array A consisting of N numbers is given. A slice of that array is any pair of integers (P, Q) such that 0 <= P < Q < N.
A slice (P, Q) of array A is called arithmetic if the sequence:
A[P], A[p + 1], ..., A[Q - 1], A[Q] is arithmetic. In particular, this means that P + 1 < Q.
The function should return the number of arithmetic slices in the array A.

Example:
A = [1, 2, 3, 4]

return: 3, for 3 arithmetic slices in A: [1, 2, 3], [2, 3, 4] and [1, 2, 3, 4] itself.

https://discuss.leetcode.com/topic/63302/simple-java-solution-9-lines-2ms
https://discuss.leetcode.com/topic/63313/java-concise-solution
sum += curr;
This line does the trick. It accumulates all the possible seqs of the longest arithmetic seq of it's kind.
sum += curr really does the trick. Brilliant!
I think the easy way to understand this is that adding current number to our existing arithmetic sequence, we will have curr additional combinations of new arithmetic slices.
Let's say if we have [1, 2, 3, 4] and currently we have 3 arithmetic slices (curr is 2). We are going to add 5 to our arithmetic sequence. So that we will have curr new slices (curr is 3), which is [3, 4, 5], [2, 3, 4, 5] and [1, 2, 3, 4, 5]. Now, the total valid arithmetic slices is 3 + curr = 6. That's exactly the same as sum += curr.
public int numberOfArithmeticSlices(int[] A) {
    int curr = 0, sum = 0;
    for (int i=2; i<A.length; i++)
        if (A[i]-A[i-1] == A[i-1]-A[i-2]) {
            curr += 1;
            sum += curr;
        } else {
            curr = 0;
        }
    return sum;
}
if (A[i]-A[i-1] == A[i-1]-A[i-2]) this introduces an integer overflow bug.
If the input is [Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE - 1], it returns 1, but it should be 0. Correction:
public int numberOfArithmeticSlices(int[] A) {
    int curr = 0, sum = 0;
    for (int i = 2; i < A.length; i++) {
        if ((long)A[i] - A[i - 1] == (long)A[i - 1] - A[i - 2]) {
            curr += 1;
            sum += curr;
        }
        else curr = 0;
    }
    return sum;
}
https://discuss.leetcode.com/topic/62884/2ms-java-o-n-time-o-1-space-solution
public int numberOfArithmeticSlices(int[] A) {
        if(A == null || A.length < 3)
            return 0;
        int sum = 0;
        int len = 2;

        for(int i=2;i<A.length;i++) {

            // keep increasing the splice
            if(A[i] - A[i-1] == A[i-1] - A[i-2]) {
                len++;
            }
            else {
                if(len > 2) {
                    sum += calculateSlices(len);
                }
                // reset the length of new slice
                len = 2;
            }
        }
        // add up the slice in the rear
        if(len>2)
            sum += calculateSlices(len);

        return sum;
    }

    private int calculateSlices(int n){
        return (n-1)*(n-2)/2;
    }
X.
https://discuss.leetcode.com/topic/62396/java-solution-time-complexity-o-n-space-complexity-o-1
    public int numberOfArithmeticSlices(int[] A) {
        int res = 0;
        if (A == null || A.length < 3)
            return res;
            
        int start = 0;
        
        while (start + 2 < A.length) {
            int offset = A[start + 1] - A[start];
            int i = start + 2;
            int count = 0;
            while (i < A.length && A[i] - A[i - 1] == offset) {
                count += (i - 1 - start);
                i++;
            }
            if (i - start >= 3) res+= count;
            start = i - 1;
        }
        return res;
    }
https://discuss.leetcode.com/topic/62396/java-solution-time-complexity-o-n-space-complexity-o-1/2
    public int numberOfArithmeticSlices(int[] A) {
        int res = 0;
        if (A == null || A.length < 3)
            return res;
            
        int start = 0;
        
        while (start + 2 < A.length) {
            int offset = A[start + 1] - A[start];
            int i = start + 2;
            while (i < A.length && A[i] - A[i - 1] == offset)
                i++;
            int n = i - start;
            if (n >= 3) res += (n - 1) * (n - 2) / 2;
            start = i - 1;
        }
        return res;
    }
https://discuss.leetcode.com/topic/62884/2ms-java-o-n-time-o-1-space-solution
public int numberOfArithmeticSlices(int[] A) {
        if(A == null || A.length < 3)
            return 0;
        int sum = 0;
        int len = 2;

        for(int i=2;i<A.length;i++) {

            // keep increasing the splice
            if(A[i] - A[i-1] == A[i-1] - A[i-2]) {
                len++;
            }
            else {
                if(len > 2) {
                    sum += calculateSlices(len);
                }
                // reset the length of new slice
                len = 2;
            }
        }
        // add up the slice in the rear
        if(len>2)
            sum += calculateSlices(len);

        return sum;
    }

    private int calculateSlices(int n){
        return (n-1)*(n-2)/2;
    }

http://bookshadow.com/weblog/2016/10/09/leetcode-arithmetic-slices/
若序列S为等差数列，其长度为N，则其等差数列切片的个数SUM = 1 + 2 + ... + (N - 2)
例如，等差数列[1, 2, 3, 4, 5, 6]的切片个数为1+2+3+4 = 10
这10个切片分别是：
[1,2,3], [1,2,3,4], [1,2,3,4,5], [1,2,3,4,5,6]
[2,3,4], [2,3,4,5], [2,3,4,5,6]
[3,4,5], [3,4,5,6]
[4,5,6]

    def numberOfArithmeticSlices(self, A):
        """
        :type A: List[int]
        :rtype: int
        """
        size = len(A)
        if size < 3: return 0
        ans = cnt = 0
        delta = A[1] - A[0]
        for x in range(2, size):
            if A[x] - A[x - 1] == delta:
                cnt += 1
                ans += cnt
            else:
                delta = A[x] - A[x - 1]
                cnt = 0
        return ans
https://discuss.leetcode.com/topic/62233/simple-java-solution-2ms

https://discuss.leetcode.com/topic/63362/5-lines-clean-java-solution
    public int numberOfArithmeticSlices(int[] A) {
        int[] lens = new int[A.length];
        for (int i = 2; i < A.length; ++i)
            if (A[i - 1] - A[i - 2] == A[i] - A[i - 1])
                lens[i] = Math.max(1, lens[i - 1] + 1);
        return Arrays.stream(lens).sum();
    }
 * @author het
 *
 */

//  1  2  3   4  5
public class LeetCode413ArithmeticSlices {
	public int numberOfArithmeticSlices(int[] A) {
	    int curr = 0, sum = 0;
	    for (int i = 2; i < A.length; i++) {
	        if ((long)A[i] - A[i - 1] == (long)A[i - 1] - A[i - 2]) {
	            curr += 1;
	            sum += curr;
	        }
	        else curr = 0;
	    }
	    return sum;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
