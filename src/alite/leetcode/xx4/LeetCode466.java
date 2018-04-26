package alite.leetcode.xx4;
/**
 * LeetCode 466 - Count The Repetitions

https://leetcode.com/problems/count-the-repetitions/
Define S = [s,n] as the string S which consists of n connected strings s. For example, ["abc", 3] ="abcabcabc".
On the other hand, we define that string s1 can be obtained from string s2 if we can remove some characters from s2 such that it becomes s1. For example, “abc” can be obtained from “abdbec” based on our definition, but it can not be obtained from “acbbe”.
You are given two non-empty strings s1 and s2 (each at most 100 characters long) and two integers 0 ≤ n1 ≤ 106 and 1 ≤ n2 ≤ 106. Now consider the strings S1 and S2, where S1=[s1,n1] and S2=[s2,n2]. Find the maximum integer M such that [S2,M] can be obtained from S1.
Input:
s1="acb", n1=4
s2="ab", n2=2

Return:
2
http://bookshadow.com/weblog/2016/12/04/leetcode-count-the-repetitions/
贪心算法 + 寻找循环节
利用贪心算法计算s1与s2对应字符的匹配位置，由于s1与s2的循环匹配呈现周期性规律，因此可以通过辅助数组dp进行记录

记l1, l2为s1, s2的长度；x1, x2为s1, s2的字符下标

令y1, y2 = x1 % l1, x2 % l2

当s1[y1] == s2[y2]时：

  若dp[y1][y2]不存在，则令dp[y1][y2] = x1, x2

  否则，记dx1, dx2 = dp[y1][y2]，循环节为s1[dx1 ... x1], s2[dx2 ... x2]
另请参阅LeetCode 418. Sentence Screen Fitting的解答。
    def getMaxRepetitions(self, s1, n1, s2, n2):
        """
        :type s1: str
        :type n1: int
        :type s2: str
        :type n2: int
        :rtype: int
        """
        if not set(s2) <= set(s1):
            return 0
        l1, l2 = len(s1), len(s2)
        dp = collections.defaultdict(dict)
        x1 = x2 = 0
        while x1 < l1 * n1:
            while s1[x1 % l1] != s2[x2 % l2]:
                x1 += 1
            if x1 >= l1 * n1:
                break
            y1, y2 = x1 % l1, x2 % l2
            if y2 not in dp[y1]:
                dp[y1][y2] = x1, x2
            else:
                dx1, dx2 = dp[y1][y2]
                round = (l1 * n1 - dx1) / (x1 - dx1)
                x1 = dx1 + round * (x1 - dx1)
                x2 = dx2 + round * (x2 - dx2)
            if x1 < l1 * n1:
                x1 += 1
                x2 += 1
        return x2 / (n2 * l2)

X.
https://discuss.leetcode.com/topic/70707/ugly-java-brute-force-solution-but-accepted-1088ms
How do we know "string s2 can be obtained from string s1"? Easy, use two pointers iterate through s2 and s1. If chars are equal, move both. Otherwise only move pointer1.
We repeat step 1 and go through s1 for n1 times and count how many times can we go through s2.
Answer to this problem is times go through s2 divide by n2.
    public int getMaxRepetitions(String s1, int n1, String s2, int n2) {
        char[] array1 = s1.toCharArray(), array2 = s2.toCharArray();
        int count1 = 0, count2 = 0, i = 0, j = 0;
        
        while (count1 < n1) {
            if (array1[i] == array2[j]) {
                j++;
                if (j == array2.length) {
                    j = 0;
                    count2++;
                }
            }
            i++;
            if (i == array1.length) {
                i = 0;
                count1++;
            }
        }
        
        return count2 / n2;
    }
 * @author het
 *
 */
public class LeetCode466 {
	public int getMaxRepetitions(String s1, int n1, String s2, int n2) {
        char[] array1 = s1.toCharArray(), array2 = s2.toCharArray();
        int count1 = 0, count2 = 0, i = 0, j = 0;
        
        while (count1 < n1) {
            if (array1[i] == array2[j]) {
                j++;
                if (j == array2.length) {
                    j = 0;
                    count2++;
                }
            }
            i++;
            if (i == array1.length) {
                i = 0;
                count1++;
            }
        }
        
        return count2 / n2;
    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
