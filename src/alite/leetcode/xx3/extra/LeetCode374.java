package alite.leetcode.xx3.extra;
/**
 * LeetCode 374 - Guess Number Higher or Lower

https://www.hrwhisper.me/leetcode-guess-number-higher-lower/
https://leetcode.com/articles/guess-number-higher-or-lower/


We are playing the Guess Game. The game is as follows:
I pick a number from 1 to n. You have to guess which number I picked.
Every time you guess wrong, I'll tell you whether the number is higher or lower.
You call a pre-defined API guess(int num) which returns 3 possible results (-1, 1, or 0):
-1 : My number is lower
 1 : My number is higher
 0 : Congrats! You got it!
Example:
n = 10, I pick 6.

Return 6.

public class Solution extends GuessGame {
    public int guessNumber(int n) {
        int low = 1;
        int high = n;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            int res = guess(mid);
            if (res == 0)
                return mid;
            else if (res < 0)
                high = mid - 1;
            else
                low = mid + 1;
        }
        return -1;
    }
X. Ternary Search
Time complexity : 
O\big(\log_3 n \big)
O(log
​3
​​ n). Ternary Search is used.
In Binary Search, we choose the middle element as the pivot in splitting. In Ternary Search, we choose two pivots (say 
m1
m1 and 
m2
m2) such that the given range is divided into three equal parts. If the required number 
num
num is less than 
m1
m1 then we apply ternary search on the left segment of 
m1
m1. If 
num
num lies between 
m1
m1 and 
m2
m2, we apply ternary search between 
m1
m1 and 
m2
m2. Otherwise we will search in the segment right to 
m2
m2.
    public int guessNumber(int n) {
        int low = 1;
        int high = n;
        while (low <= high) {
            int mid1 = low + (high - low) / 3;
            int mid2 = high - (high - low) / 3;
            int res1 = guess(mid1);
            int res2 = guess(mid2);
            if (res1 == 0)
                return mid1;
            if (res2 == 0)
                return mid2;
            else if (res1 < 0)
                high = mid1 - 1;
            else if (res2 > 0)
                low = mid2 + 1;
            else {
                low = mid1 + 1;
                high = mid2 - 1;
            }
        }
        return -1;
    }
It seems that ternary search is able to terminate earlier compared to binary search. But why is binary search
 more widely used?
Ternary Search is worse than Binary Search. The following outlines the recursive formula 
to count comparisons of Binary Search in the worst case.
T
(
n
)
=
T
(
n
2
 
)
+
2
,
T
(
1
)
=
1
T
(
n
2
 
)
=
T
(
n
2
2
 
)
+
2
∴
T
(
n
)
=
T
(
n
2
2
 
)
+
2
×
2
=
T
(
n
2
3
 
)
+
3
×
2
=
…
=
T
(
n
2
log
2
n
 
)
+
2
log
2
n
=
T
(
1
)
+
2
log
2
n
=
1
+
2
log
2
n
Tn	T
n
2
 
2T11
T
n
2
 
T
n
2
2
 
2
Tn	T
n
2
2
 
22
T
n
2
3
 
32
T
n
2
log
2
n
 
2
log
2
n
T12
log
2
n
12
log
2
n

The following outlines the recursive formula to count comparisons of Ternary Search in the worst case.
T
(
n
)
=
T
(
n
3
 
)
+
4
,
T
(
1
)
=
1
T
(
n
3
 
)
=
T
(
n
3
2
 
)
+
4
∴
T
(
n
)
=
T
(
n
3
2
 
)
+
2
×
4
=
T
(
n
3
3
 
)
+
3
×
4
=
…
=
T
(
n
3
log
3
n
 
)
+
4
log
3
n
=
T
(
1
)
+
4
log
3
n
=
1
+
4
log
3
n
Tn	T
n
3
 
4T11
T
n
3
 
T
n
3
2
 
4
Tn	T
n
3
2
 
24
T
n
3
3
 
34
T
n
3
log
3
n
 
4
log
3
n
T14
log
3
n
14
log
3
n

As shown above, the total comparisons in the worst case for ternary and binary search are 
1 + 4 \log_3 n
1+4log
​3
​​ n and 
1 + 2 \log_2 n
1+2log
​2
​​ n comparisons respectively. To determine which is larger, we can just look at the expression 
2 \log_3 n
2log
​3
​​ n and 
\log_2 n
log
​2
​​ n . The expression 
2 \log_3 n
2log
​3
​​ n can be written as 
\frac{2}{\log_2 3} \times \log_2 n
​log
​2
​​ 3
​
​2
​​ ×log
​2
​​ n . Since the value of 
\frac{2}{\log_2 3}
​log
​2
​​ 3
​
​2
​​  is greater than one, Ternary Search does more comparisons than Binary Search in the worst case.

    public int guessNumber(int n) {
        for (int i = 1; i < n; i++)
            if (guess(i) == 0)
                return i;
        return n;
    }
 * @author het
 *
 */
public class LeetCode374 {
	public class Solution extends GuessGame {
	    public int guessNumber(int n) {
	        int low = 1;
	        int high = n;
	        while (low <= high) {
	            int mid = low + (high - low) / 2;   //  int mid = low + (high - low) / 2;
	            int res = guess(mid);
	            if (res == 0)
	                return mid;
	            else if (res < 0)
	                high = mid - 1;
	            else
	                low = mid + 1;
	        }
	        return -1;
	    }
//	X. Ternary Search
//	Time complexity : 
//	O\big(\log_3 n \big)
//	O(log
//	​3
//	​​ n). Ternary Search is used.
//	In Binary Search, we choose the middle element as the pivot in splitting. In Ternary Search, we choose two pivots (say 
//	m1
//	m1 and 
//	m2
//	m2) such that the given range is divided into three equal parts. If the required number 
//	num
//	num is less than 
//	m1
//	m1 then we apply ternary search on the left segment of 
//	m1
//	m1. If 
//	num
//	num lies between 
//	m1
//	m1 and 
//	m2
//	m2, we apply ternary search between 
//	m1
//	m1 and 
//	m2
//	m2. Otherwise we will search in the segment right to 
//	m2
//	m2.
	    public int guessNumber(int n) {
	        int low = 1;
	        int high = n;
	        while (low <= high) {
	            int mid1 = low + (high - low) / 3;
	            int mid2 = high - (high - low) / 3;
	            int res1 = guess(mid1);
	            int res2 = guess(mid2);
	            if (res1 == 0)
	                return mid1;
	            if (res2 == 0)
	                return mid2;
	            else if (res1 < 0)
	                high = mid1 - 1;
	            else if (res2 > 0)
	                low = mid2 + 1;
	            else {
	                low = mid1 + 1;
	                high = mid2 - 1;
	            }
	        }
	        return -1;
	    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
