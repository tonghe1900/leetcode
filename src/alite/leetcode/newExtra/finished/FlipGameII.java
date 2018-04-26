package alite.leetcode.newExtra.finished;
/**
 * [LeetCode] Flip Game II

Problem Description:

You are playing the following Flip Game with your friend: Given a string that contains only these two characters: 
+ and -, you and your friend take turns to flip two consecutive "++" into "--". The game ends when a person can 
no longer make a move and therefore the other person will be the winner.

Write a function to determine if the starting player can guarantee a win.

For example, given s = "++++", return true. The starting player can guarantee a win by flipping 
the middle "++" to become "+--+".

Follow up:
Derive your algorithm's runtime complexity.

The simplest solution that I have found is here (refer to the first paragraph for explanations 
of the idea and the Java code). I rewrite it in C++.

The idea is very straightforward: if you can make s non-winnable by a move, then you will win.

复制代码
1 class Solution {
2 public:
3     bool canWin(string s) {
4         for (int i = -1; (i = s.find("++", i + 1)) >= 0; )
5             if (!canWin(s.substr(0, i) + '-' + s.substr(i+2)))
6                 return true;
7         return false;
8     }
9 };
复制代码
If you are interested to learn more, this post shares a more sophisticated solution using game theory (it reduces the running time to 0 seconds!). The code is restructured as below.

复制代码
 1 class Solution {
 2 public:
 3     bool canWin(string s) {
 4         vector<int> states = gameStates(s);
 5         if (states.empty()) return false;
 6          return spragueGrundy(states) != 0;
 7     }
 8 private:
 9     vector<int> gameStates(string& s) {
10         vector<int> states;
11         int n = s.length(), c = 0;
12         for (int i = 0; i < n; i++) {
13             if (s[i] == '+') c++;
14             if (i == n - 1 || s[i] == '-') {
15                 if (c >= 2) states.push_back(c);
16                 c = 0;
17             }
18         }
19         return states;
20     }
21     int firstMissingNumber(unordered_set<int>& st) {
22         int m = st.size();
23         for (int i = 0; i < m; i++)
24             if (!st.count(i)) return i;
25         return m;
26     }
27     int spragueGrundy(vector<int>& states) {
28         int m = *max_element(states.begin(), states.end());
29         vector<int> sg(m + 1, 0);
30         for (int l = 2; l <= m; l++) {
31             unordered_set<int> st;
32             for (int l1 = 0; l1 < l / 2; l1++) {
33                 int l2 = l - l1 - 2;
34                 st.insert(sg[l1] ^ sg[l2]);
35             }
36             sg[l] = firstMissingNumber(st);
37         }
38         int v = 0;
39         for (int state : states) v ^= sg[state];
40         return v;
41     }
42 };
 * @author het
 *
 */
public class FlipGameII {

}
