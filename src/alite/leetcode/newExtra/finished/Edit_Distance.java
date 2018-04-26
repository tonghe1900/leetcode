package alite.leetcode.newExtra.finished;
/**
 * [LeetCode] Edit Distance 编辑距离
 

Given two words word1 and word2, find the minimum number of steps required to convert word1 to word2. (each operation is counted as 1 step.)

You have the following 3 operations permitted on a word:

a) Insert a character
b) Delete a character
c) Replace a character

 

这道题让求从一个字符串转变到另一个字符串需要的变换步骤，共有三种变换方式，插入一个字符，删除一个字符，和替换一个字符。根据以往的经验，对于字符串相关的题目十有八九都是用动态规划Dynamic Programming来解，
这道题也不例外。这道题我们需要维护一个二维的数组dp，其中dp[i][j]表示从word1的前i个字符转换到word2的前j个字符所需要的步骤。那我们可以先给这个二维数组dp的第一行第一列赋值，这个很简单，因为第一行和第一列对应的总有一个字符串是空串，于是转换步骤完全是另一个字符串的长度。跟以往的DP题目类似，难点还是在于找出递推式，我们可以举个例子来看，比如word1是“bbc"，word2是”abcd“，那么我们可以得到dp数组如下：

 

  Ø a b c d
Ø 0 1 2 3 4
b 1 1 1 2 3
b 2 2 1 2 3
c 3 3 2 1 2
 

我们通过观察可以发现，当word1[i] == word2[j]时，dp[i][j] = dp[i - 1][j - 1]，其他情况时，dp[i][j]是其左，左上，上的三个值中的最小值加1，那么可以得到递推式为：

dp[i][j] =      /    dp[i - 1][j - 1]                                                                   if word1[i - 1] == word2[j - 1]

                  \    min(dp[i - 1][j - 1], min(dp[i - 1][j], dp[i][j - 1])) + 1            else

 

复制代码
class Solution {
public:
    int minDistance(string word1, string word2) {
        int n1 = word1.size(), n2 = word2.size();
        int dp[n1 + 1][n2 + 1];
        for (int i = 0; i <= n1; ++i) dp[i][0] = i;
        for (int i = 0; i <= n2; ++i) dp[0][i] = i;
        for (int i = 1; i <= n1; ++i) {
            for (int j = 1; j <= n2; ++j) {
                if (word1[i - 1] == word2[j - 1]) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = min(dp[i - 1][j - 1], min(dp[i - 1][j], dp[i][j - 1])) + 1;
                }
            }
        }
        return dp[n1][n2];
    }
};






[LeetCode] One Edit Distance 一个编辑距离
 

Given two strings S and T, determine if they are both one edit distance apart.

 

这道题是之前那道Edit Distance的拓展，然而这道题并没有那道题难，这道题只让我们判断两个字符串的编辑距离是否为1，那么我们只需分下列三种情况来考虑就行了：

1. 两个字符串的长度之差大于1，那么直接返回False

2. 两个字符串的长度之差等于1，那么长的那个字符串去掉一个字符，剩下的应该和短的字符串相同

3. 两个字符串的长度之差等于0，那么两个字符串对应位置的字符只能有一处不同。

分析清楚了所有的情况，代码就很好写了，参见如下：

 

解法一：

复制代码
class Solution {
public:
    bool isOneEditDistance(string s, string t) {
        if (s.size() < t.size()) swap(s, t);
        int m = s.size(), n = t.size(), diff = m - n;
        if (diff >= 2) return false;
        else if (diff == 1) {
            for (int i = 0; i < n; ++i) {
                if (s[i] != t[i]) {
                    return s.substr(i + 1) == t.substr(i);
                }
            }
            return true;
        } else {
            int cnt = 0;
            for (int i = 0; i < m; ++i) {
                if (s[i] != t[i]) ++cnt;
            }
            return cnt == 1;
        }
    }
};
复制代码
 

我们实际上可以让代码写的更加简洁，只需要对比两个字符串对应位置上的字符，如果遇到不同的时候，这时我们看两个字符串的长度关系，如果相等，那么我们比较当前位置后的字串是否相同，如果s的长度大，那么我们比较s的下一个位置开始的子串，和t的当前位置开始的子串是否相同，反之如果t的长度大，那么我们比较t的下一个位置开始的子串，和s的当前位置开始的子串是否相同。如果循环结束，都没有找到不同的字符，那么此时我们看两个字符串的长度是否相差1，参见代码如下：

 

解法二：

复制代码
class Solution {
public:
    bool isOneEditDistance(string s, string t) {
        for (int i = 0; i < min(s.size(), t.size()); ++i) {
            if (s[i] != t[i]) {
                if (s.size() == t.size()) return s.substr(i + 1) == t.substr(i + 1);
                else if (s.size() < t.size()) return s.substr(i) == t.substr(i + 1);
                else return s.substr(i + 1) == t.substr(i);
            }
        }
        return abs(s.size() - t.size()) == 1;
    }
};




One Edit Distance
Given two strings S and T, determine if they are both one edit distance apart.
比较长度法
复杂度
时间 O(N) 空间 O(1)

思路
虽然我们可以用Edit Distance的解法，看distance是否为1，但Leetcode中会超时。这里我们可以利用只有一个不同的特点在O(N)时间内完成。如果两个字符串只有一个编辑距离，则只有两种情况：

两个字符串一样长的时候，说明有一个替换操作，我们只要看对应位置是不是只有一个字符不一样就行了
一个字符串比另一个长1，说明有个增加或删除操作，我们就找到第一个对应位置不一样的那个字符，如果较长字符串在那个字符之后的部分和较短字符串那个字符及之后的部分是一样的，则符合要求
如果两个字符串长度差距大于1，肯定不对
代码
public class Solution {
    public boolean isOneEditDistance(String s, String t) {
        int m = s.length(), n = t.length();
        if(m == n) return isOneModified(s, t);
        if(m - n == 1) return isOneDeleted(s, t);
        if(n - m == 1) return isOneDeleted(t, s);
        // 长度差距大于2直接返回false
        return false;
    }
    
    private boolean isOneModified(String s, String t){
        boolean modified = false;
        // 看是否只修改了一个字符
        for(int i = 0; i < s.length(); i++){
            if(s.charAt(i) != t.charAt(i)){
                if(modified) return false;
                modified = true;
            }
        }
        return modified;
    }
    
    public boolean isOneDeleted(String longer, String shorter){
        // 找到第一组不一样的字符，看后面是否一样
        for(int i = 0; i < shorter.length(); i++){
            if(longer.charAt(i) != shorter.charAt(i)){
                return longer.substring(i + 1).equals(shorter.substring(i));
            }
        }
        return true;
    }
}
2015年10月26日发布 更多




 * @author het
 *
 */
public class Edit_Distance {

}
