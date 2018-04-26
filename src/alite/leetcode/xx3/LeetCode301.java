package alite.leetcode.xx3;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * LeetCode 301 - Remove Invalid Parentheses

https://leetcode.com/problems/remove-invalid-parentheses/
Remove the minimum number of invalid parentheses in order to make the input string valid. Return all possible results.
Note: The input string may contain letters other than the parentheses ( and ).
Examples:
"()())()" -> ["()()()", "(())()"]
"(a)())()" -> ["(a)()()", "(a())()"]
")(" -> [""]
http://algobox.org/remove-invalid-parentheses/
https://leetcode.com/discuss/81478/easy-short-concise-and-fast-java-dfs-3-ms-solution
Generate unique answer once and only once, do not rely on Set.
Do not need preprocess.
We all know how to check a string of parentheses is valid using a stack. Or even simpler use a counter.
The counter will increase when it is ‘(‘ and decrease when it is ‘)’. Whenever the counter is negative, we have more ‘)’ than 
‘(‘ in the prefix.
To make the prefix valid, we need to remove a ‘)’. The problem is: which one? The answer is any ‘)’ in the prefix. 
However, if we remove any one, we will generate duplicate results, for example: s = ()), we can remove s[1] ors[2] 
but the result is the same (). Thus, we restrict ourself to remove the first ) in a series of consecutive )s.
After the removal, the prefix is then valid. We then call the function recursively to solve the rest of the string. 
However, we need to keep another information: the last removal position. If we do not have this position, 
we will generate duplicate by removing two ) in two steps only with a different order.
For this, we keep tracking the last removal position and only remove ) after that.
Now one may ask. What about (? What if s = (()(() in which we need remove (?
The answer is: do the same from right to left.
However, a cleverer idea is: reverse the string and reuse the code!

    public List<String> removeInvalidParentheses(String s) {

        List<String> ans = new ArrayList<>();

        remove(s, ans, 0, 0, new char[]{'(', ')'});

        return ans;

    }


    public void remove(String s, List<String> ans, int last_i, int last_j, char[] par) {

        int stack = 0, i;

        // Search for mismatch

        for (i = last_i; i < s.length(); ++i) {

            if (s.charAt(i) == par[0]) stack++;

            if (s.charAt(i) == par[1]) stack--;

            if (stack < 0) break;

        }

        // Remove a parenthesis

        if (stack < 0) {

            for (int j = last_j; j <= i; ++j) {

                if (s.charAt(j) == par[1] && (j == last_j || s.charAt(j - 1) != par[1])) {

                    String candidate = s.substring(0, j) + s.substring(j + 1, s.length());

                    remove(candidate, ans, i, j, par);

                }

            }

            return;

        }

        // If no mismatch, try reverse the string

        String reversed = new StringBuilder(s).reverse().toString();

        if (par[0] == '(')

            remove(reversed, ans, 0, 0, new char[]{')', '('});

        else

            ans.add(reversed); // both sides are finished, got an answer

    }
https://leetcode.com/discuss/67919/java-optimized-dfs-solution-3-ms
DFS solution with optimizations:
Before starting DFS, calculate the total numbers of opening and closing parentheses that need to be removed in the final solution, then these two numbers could be used to speed up the DFS process.
Use while loop to avoid duplicate result in DFS, instead of using HashSet.
Use count variable to validate the parentheses dynamically.
openN and closeN means the total numbers of opening and closing parentheses that need to be removed in the final solution (removed the minimum number of invalid parentheses).
dfs(s, p + i, count + i, openN, closeN, result, sb) means not to remove the current parenthesis.
dfs(s, p + 1, count, openN - 1, closeN, result, sb) means to remove the current parenthesis. We won't need to do the dfs if openN has been decreased to zero - if the openN is zero, that means there are already enough open parentheses has been removed, and continually removing open parenthesis won't be a possible solution.
    public List<String> removeInvalidParentheses(String s) {
        int count = 0, openN = 0, closeN = 0;

        // calculate the total numbers of opening and closing parentheses
        // that need to be removed in the final solution
        for (char c : s.toCharArray()) {
            if (c == '(') {
                count++;
            } else if (c == ')') {
                if (count == 0) closeN++;
                else count--;
            }
        }
        openN = count;
        count = 0;

        if (openN == 0 && closeN == 0) return Arrays.asList(s);

        List<String> result = new ArrayList<>();
        StringBuilder sb = new StringBuilder();

        dfs(s.toCharArray(), 0, count, openN, closeN, result, sb);

        return result;
    }

    private void dfs(char[] s, int p, int count, int openN, int closeN, List<String> result, StringBuilder sb) {
        if (count < 0) return; // the parentheses is invalid

        if (p == s.length) {
            if (openN == 0 && closeN == 0) { // the minimum number of invalid parentheses have been removed
                result.add(sb.toString());
            }
            return;
        }

        if (s[p] != '(' && s[p] != ')') {
            sb.append(s[p]);
            dfs(s, p + 1, count, openN, closeN, result, sb);
            sb.deleteCharAt(sb.length() - 1);
        } else if (s[p] == '(') {
            int i = 1;
            while (p + i < s.length && s[p + i] == '(') i++; // use while loop to avoid duplicate result in DFS, instead of using HashSet
            sb.append(s, p, i);
            dfs(s, p + i, count + i, openN, closeN, result, sb);
            sb.delete(sb.length() - i, sb.length());

            if (openN > 0) {
                // remove the current opening parenthesis
                dfs(s, p + 1, count, openN - 1, closeN, result, sb);
            }
        } else {
            int i = 1;
            while (p + i < s.length && s[p + i] == ')') i++; // use while loop to avoid duplicate result in DFS, instead of using HashSet
            sb.append(s, p, i);
            dfs(s, p + i, count - i, openN, closeN, result, sb);
            sb.delete(sb.length() - i, sb.length());

            if (closeN > 0) {
                // remove the current closing parenthesis
                dfs(s, p + 1, count, openN, closeN - 1, result, sb);
            }
        }
    }
http://bookshadow.com/weblog/2015/11/05/leetcode-remove-invalid-parentheses/
移除最小数量的无效括号，使得输入字符串有效。返回所有可能的结果。
注意：输入字符串除了左右小括号外，还可能包含字母。
解法I：深度优先搜索（DFS）+剪枝（Pruning）
利用评价函数计算字符串中未匹配括号的个数

尝试从输入字符串中移除括号，若得到的新字符串的失配括号比原字符串少，则继续搜索；

否则剪枝。
统计左右括号能删的个数，进行DFS。
    def removeInvalidParentheses(self, s):
        def dfs(s):
            mi = calc(s)
            if mi == 0:
                return [s]
            ans = []
            for x in range(len(s)):
                if s[x] in ('(', ')'):
                    ns = s[:x] + s[x+1:]
                    if ns not in visited and calc(ns) < mi:
                        visited.add(ns)
                        ans.extend(dfs(ns))
            return ans    
        def calc(s):
            a = b = 0
            for c in s:
                a += {'(' : 1, ')' : -1}.get(c, 0)
                b += a < 0
                a = max(a, 0)
            return a + b

        visited = set([s])    
        return dfs(s)
https://discuss.leetcode.com/topic/30743/easiest-9ms-java-solution
Here I share my DFS or backtracking solution. It's 10X faster than optimized BFS.
Limit max removal rmL and rmR for backtracking boundary. Otherwise it will exhaust all possible valid substrings, not shortest ones.
Scan from left to right, avoiding invalid strs (on the fly) by checking num of open parens.
If it's '(', either use it, or remove it.
If it's '(', either use it, or remove it.
Otherwise just append it.
Lastly set StringBuilder to the last decision point.
In each step, make sure:
i does not exceed s.length().
Max removal rmL rmR and num of open parens are non negative.
De-duplicate by adding to a HashSet.
Compared to 106 ms BFS (Queue & Set), it's faster and easier
public List<String> removeInvalidParentheses(String s) {
 Set<String> res = new HashSet<>();
 int rmL = 0, rmR = 0;
 for(int i = 0; i < s.length(); i++) {
  if(s.charAt(i) == '(') rmL++;
     if(s.charAt(i) == ')') {
      if(rmL != 0) rmL--;
      else rmR++;
     }
 }
 DFS(res, s, 0, rmL, rmR, 0, new StringBuilder());
    return new ArrayList<String>(res); 
}

public void DFS(Set<String> res, String s, int i, int rmL, int rmR, int open, StringBuilder sb) {
    if(i == s.length() && rmL == 0 && rmR == 0 && open == 0) {
     res.add(sb.toString());
     return;
    }
    if(i == s.length() || rmL < 0 || rmR < 0 || open < 0) return;
    
    char c = s.charAt(i);
    int len = sb.length();
   
 if(c == '(') {
  DFS(res, s, i + 1, rmL - 1, rmR, open, sb);
  DFS(res, s, i + 1, rmL, rmR, open + 1, sb.append(c)); 
  
 } else if(c == ')') {
  DFS(res, s, i + 1, rmL, rmR - 1, open, sb);
  DFS(res, s, i + 1, rmL, rmR, open - 1, sb.append(c));
  
 } else {
  DFS(res, s, i + 1, rmL, rmR, open, sb.append(c)); 
 }
 
 sb.setLength(len);
}
https://www.hrwhisper.me/leetcode-remove-invalid-parentheses

    def removeInvalidParentheses(self, s):

        """

        :type s: str

        :rtype: List[str]

        """

        if not s: return ['']

        left_remove = right_remove = 0

        for c in s:

            if c == '(':

                left_remove += 1

            elif c == ')':

                if left_remove:

                    left_remove -= 1

                else:

                    right_remove += 1


        ans = set()

        self.dfs(0, left_remove, right_remove, 0, '', s, ans)

        return list(ans)


    def dfs(self, index, left_remove, right_remove, left_pare, cur, s, ans):

        if left_remove < 0 or right_remove < 0 or left_pare < 0: return

        if index == len(s):

            if left_remove == right_remove == left_pare == 0:

                ans.add(cur)

            return


        if s[index] == '(':

            self.dfs(index + 1, left_remove - 1, right_remove, left_pare, cur, s, ans)

            self.dfs(index + 1, left_remove, right_remove, left_pare + 1, cur + s[index], s, ans)

        elif s[index] == ')':

            self.dfs(index + 1, left_remove, right_remove - 1, left_pare, cur, s, ans)

            self.dfs(index + 1, left_remove, right_remove, left_pare - 1, cur + s[index], s, ans)

        else:

            self.dfs(index + 1, left_remove, right_remove, left_pare, cur + s[index], s, ans)
https://discuss.leetcode.com/topic/28818/dfs-and-bfs-java-solutions-add-one-more-optimized-fast-dfs-solution
    public List<String> removeInvalidParentheses(String s) {
        if (s.length() == 0) return new ArrayList<String>(Arrays.asList(""));
        Map<Integer, List<String>> dics = new HashMap<Integer, List<String>>();
        Set<String> visited = new HashSet<String>();
        int[] min = new int[]{Integer.MAX_VALUE};
        char[] str = s.toCharArray();
        helper(dics, str, 0, "", 0, 0, min, 0, visited);
        return dics.get(min[0]);
    }
    private void helper(Map<Integer, List<String>> dics, char[] str, int start, String cur, 
                        int left, int right, int[] min, int delete, Set<String> visited) {
        // Base Cases
        if (visited.contains(cur + delete)) return;
        visited.add(cur + delete);
        if (start == str.length) {
            if (left != right) return;
            if (!dics.containsKey(delete)) dics.put(delete, new ArrayList<String>());
            dics.get(delete).add(cur);
            min[0] = Math.min(min[0], delete);
            return;
        }
        if (left < right) return;
        if (str[start] == '(') {
            helper(dics, str, start + 1, cur + "(", left + 1, right, min, delete, visited);
            helper(dics, str, start + 1, cur, left, right, min, delete + 1, visited);
        } else if (str[start] == ')') {
            helper(dics, str, start + 1, cur + ")", left, right + 1, min, delete, visited);
            helper(dics, str, start + 1, cur, left, right, min, delete + 1, visited);
        } else {
            helper(dics, str, start + 1, cur + str[start], left, right, min, delete, visited);
        }
    }
X. BFS 
https://leetcode.com/discuss/67842/share-my-java-bfs-solution
The idea is straightforward, with the input string s, we generate all possible states by removing one ( or ), check if they are valid, if found valid ones on the current level, put them to the final result list and we are done, otherwise, add them to a queue and carry on to the next level.
The good thing of using BFS is that we can guarantee the number of parentheses that need to be removed is minimal, also no recursion call is needed in BFS.
Thanks to @peisi, we don't need stack to check valid parentheses.
Time complexity:
In BFS we handle the states level by level, in the worst case, we need to handle all the levels, we can analyze the time complexity level by level and add them up to get the final complexity.
On the first level, there's only one string which is the input string s, let's say the length of it is n, to check whether it's valid, we need O(n) time. On the second level, we remove one ( or )from the first level, so there are C(n, n-1) new strings, each of them has n-1 characters, and for each string, we need to check whether it's valid or not, thus the total time complexity on this level is (n-1) x C(n, n-1). Come to the third level, total time complexity is (n-2) x C(n, n-2), so on and so forth...
Finally we have this formula:
T(n) = n x C(n, n) + (n-1) x C(n, n-1) + ... + 1 x C(n, 1) = n x 2^(n-1).

the space complexity is O(n*2^n), the space needed for the visited set or the queue; actually it can be reduced to O(n) + size of output.
you need to avoid duplicate states. Let's say we have a string s = )))), it can generate the following string t by removing one left or right paren: [))), ))), ))), )))], notice that they are all the same. So after handle the first ))), you should mark it as visited, then when you see it again, simply ignore it, that will make the code runs faster. In my solution, I use a visited hash set to help me with that, it's a useful technique in both BFS and DFS.



the space complexity is O(n*2^n), the space needed for the visited set or the queue; actually it can be reduced to O(n) + size of output.

I have to say DFS is much faster than the BFS solution.
    public List<String> removeInvalidParentheses(String s) {
      List<String> res = new ArrayList<>();
      
      // sanity check
      if (s == null) return res;
      
      Set<String> visited = new HashSet<>();
      Queue<String> queue = new LinkedList<>();
      
      // initialize
      queue.add(s);
      visited.add(s);
      
      boolean found = false;
      
      while (!queue.isEmpty()) {
        s = queue.poll();
        
        if (isValid(s)) {
          // found an answer, add to the result
          res.add(s);
          found = true;
        }
      
        if (found) continue;
      
        // generate all possible states
        for (int i = 0; i < s.length(); i++) {
          // we only try to remove left or right paren
          if (s.charAt(i) != '(' && s.charAt(i) != ')') continue;
        
          String t = s.substring(0, i) + s.substring(i + 1);
        
          if (!visited.contains(t)) {
            // for each state, if it's not visited, add it to the queue
            queue.add(t);
            visited.add(t);
          }
        }
      }
      
      return res;
    }
    
    // helper function checks if string s contains valid parantheses
    boolean isValid(String s) {
      int count = 0;
    
      for (int i = 0; i < s.length(); i++) {
        char c = s.charAt(i);
        if (c == '(') count++;
        if (c == ')' && count-- == 0) return false;
      }
    
      return count == 0;
    }
http://www.cnblogs.com/grandyang/p/4944875.html
这道题首先可以用BFS来解，我们先把给定字符串排入队中，然后取出检测其是否合法，若合法直接返回，不合法的话，我们队其进行遍历，
对于遇到的左右括号的字符，我们去掉括号字符生成一个新的字符串，如果这个字符串之前没有遇到过，将其排入队中，
我们用哈希表记录一个字符串是否出现过。我们对队列中的每个元素都进行相同的操作，直到队列为空还没找到合法的字符串的话，那就返回空集
http://www.hrwhisper.me/leetcode-remove-invalid-parentheses/
枚举去除的点，当找到后停止BFS树的扩展（因为要去除最少括号，所以即使有其他的结果，也一定在同一层）
    vector<string> removeInvalidParentheses(string s) {
        vector<string> res;
        unordered_map<string, int> visited;
        queue<string> q;
        q.push(s);
        ++visited[s];
        bool found = false;
        while (!q.empty()) {
            s = q.front(); q.pop();
            if (isValid(s)) {
                res.push_back(s);
                found = true;
            }
            if (found) continue; // exit early
            for (int i = 0; i < s.size(); ++i) {
                if (s[i] != '(' && s[i] != ')') continue;
                string t = s.substr(0, i) + s.substr(i + 1);
                if (visited.find(t) == visited.end()) {
                    q.push(t);
                    ++visited[t];
                }
            }
        }
        return res;
    }
    bool isValid(string t) {
        int cnt = 0;
        for (int i = 0; i < t.size(); ++i) {
            if (t[i] == '(') ++cnt;
            if (t[i] == ')' && cnt-- == 0) return false;
        }
        return cnt == 0;
    }
http://www.cnblogs.com/jcliBlogger/p/4939739.html
 3     vector<string> removeInvalidParentheses(string s) {
 4         vector<string> parens;
 5         queue<string> candidates;
 6         unordered_set<string> visited;
 7         candidates.push(s);
 8         visited.insert(s);
 9         bool found = false;
10         while (!candidates.empty()) {
11             string now = candidates.front();
12             candidates.pop();
13             if (isValid(now)) {
14                 parens.push_back(now);
15                 found = true;
16             }
17             if (!found) {
18                 int n = now.length();
19                 for (int i = 0; i < n; i++) {
20                     if (now[i] == '(' || now[i] == ')') {
21                         string next = now.substr(0, i) + now.substr(i + 1);
22                         if (visited.find(next) == visited.end()) {
23                             candidates.push(next);
24                             visited.insert(next);
25                         }
26                     }
27                 }
28             }
29         }
30         return parens;
31     }
https://leetcode.com/discuss/67842/share-my-java-bfs-solution
The idea is straightforward, with the input string s, we generate all possible states by removing one ( or ), 
check if they are valid, if found valid ones on the current level, put them to the final result list and 
we are done, otherwise, add them to a queue and carry on to the next level.
The good thing of using BFS is that we can guarantee the number of parentheses that need to removed is minimal,
 also no recursion call is needed in BFS.
Time complexity:
In BFS we handle the states level by level, in the worst case, we need to handle all the levels, we can analyze 
the time complexity level by level and add them up to get the final complexity.
On the first level, there's only one string which is the input string s, let's say the length of it is n, 
to check whether it's valid, we need O(n) time. On the second level, we remove one ( or )from the first level,
 so there are C(n, n-1) new strings, each of them has n-1 characters, and for each string, we need to check whether 
 it's valid or not, thus the total time complexity on this level is (n-1) x C(n, n-1). Come to the third level,
  total time complexity is (n-2) x C(n, n-2), so on and so forth...
Finally we have this formula:
T(n) = n x C(n, n) + (n-1) x C(n, n-1) + ... + 1 x C(n, 1) = n x 2^(n-1).
 public List<String> removeInvalidParentheses(String s) { List<String> res = new ArrayList<>(); 
 // sanity check if (s == null) return res; Set<String> visited = new HashSet<>(); Queue<String> queue = new LinkedList<>(); 
  * // initialize queue.add(s); visited.add(s); boolean found = false; while (!queue.isEmpty()) 
  * { s = queue.poll(); if (isValid(s)) { // found an answer, add to the result res.add(s); found = true; } 
  * if (found) continue; // generate all possible states for (int i = 0; i < s.length(); i++) 
  * { // we only try to remove left or right paren if (s.charAt(i) != '(' && s.charAt(i) != ')') continue; 
  * String t = s.substring(0, i) + s.substring(i + 1); if (!visited.contains(t))
  *  { // for each state, if it's not visited, add it to the queue queue.add(t); visited.add(t); } } }
  *   return res; } // helper function checks if string s contains
  *    valid parantheses boolean isValid(String s) { int count = 0; for (int i = 0; i < s.length(); i++) 
  *    { char c = s.charAt(i); if (c == '(') count++; if (c == ')' && count-- == 0) return false; } return count == 0; }
http://bookshadow.com/weblog/2015/11/05/leetcode-remove-invalid-parentheses/
通过从输入字符串中移除每一个括号，生成新的字符串加入队列。

如果从队列中取出的字符串是有效的，则加入结果列表。

一旦发现有效的字符串，则不再向队列中补充新的可能字符串。

根据BFS的性质，当首次从队列中发现有效字符串时，其去掉的括号数一定是最小的。

而此时，队列中存在的元素与队头元素去掉的括号数的差值 ≤ 1

并且，只有与队头元素去掉括号数目相同的元素才有可能是候选答案（根据括号匹配的性质可知）。
    def removeInvalidParentheses(self, s):
        def isValid(s):
            a = 0
            for c in s:
                a += {'(' : 1, ')' : -1}.get(c, 0)
                if a < 0:
                    return False
            return a == 0

        visited = set([s])
        ans = []
        queue = collections.deque([s])
        done = False
        while queue:
            t = queue.popleft()
            if isValid(t):
                done = True
                ans.append(t)
            if done:
                continue
            for x in range(len(t)):
                if t[x] not in ('(', ')'):
                    continue
                ns = t[:x] + t[x + 1:]
                if ns not in visited:
                    visited.add(ns)
                    queue.append(ns)

        return ans
BFS也可应用解法I中的剪枝策略：
    def removeInvalidParentheses(self, s):
        def calc(s):
            a = b = 0
            for c in s:
                a += {'(' : 1, ')' : -1}.get(c, 0)
                b += a < 0
                a = max(a, 0)
            return a + b

        visited = set([s])
        ans = []
        queue = collections.deque([s])
        done = False
        while queue:
            t = queue.popleft()
            mi = calc(t)
            if mi == 0:
                done = True
                ans.append(t)
            if done:
                continue
            for x in range(len(t)):
                if t[x] not in ('(', ')'):
                    continue
                ns = t[:x] + t[x+1:]
                if ns not in visited and calc(ns) < mi:
                    visited.add(ns)
                    queue.append(ns)

        return ans

http://www.geeksforgeeks.org/remove-invalid-parentheses/


As we need to generate all possible output we will backtrack among all states by removing one opening or closing bracket 
and check if they are valid if invalid then add the removed bracket back and go for next state. 
We will use BFS for moving through states, use of BFS will assure removal of minimal number of brackets 
because we traverse into states level by level and each level corresponds to one extra bracket removal.
 Other than this BFS involve no recursion so overhead of passing parameters is also saved.
Below code has a method isValidString to check validity of string, it counts open and closed parenthesis at
 each index ignoring non-parenthesis character. If at any instant count of close parenthesis becomes more
  than open then we return false else we keep update the count variable.

bool isParenthesis(char c)

{

    return ((c == '(') || (c == ')'));

}

 

//  method returns true if string contains valid

// parenthesis

bool isValidString(string str)

{

    int cnt = 0;

    for (int i = 0; i < str.length(); i++)

    {

        if (str[i] == '(')

            cnt++;

        else if (str[i] == ')')

            cnt--;

        if (cnt < 0)

            return false;

    }

    return (cnt == 0);

}

 

//  method to remove invalid parenthesis

void removeInvalidParenthesis(string str)

{

    if (str.empty())

        return ;

 

    //  visit set to ignore already visited string

    set<string> visit;

 

    //  queue to maintain BFS

    queue<string> q;

    string temp;

    bool level;

 

    //  pushing given string as starting node into queue

    q.push(str);

    visit.insert(str);

    while (!q.empty())

    {

        str = q.front();  q.pop();

        if (isValidString(str))

        {

            cout << str << endl;

 

            // If answer is found, make level true

            // so that valid string of only that level

            // are processed.

            level = true;

        }

        if (level)

            continue;

        for (int i = 0; i < str.length(); i++)

        {

            if (!isParenthesis(str[i]))

                continue;

 

            // Removing parenthesis from str and

            // pushing into queue,if not visited already

            temp = str.substr(0, i) + str.substr(i + 1);

            if (visit.find(temp) == visit.end())

            {

                q.push(temp);

                visit.insert(temp);

            }

        }

    }

}
 * @author het
 *
 */
public class LeetCode301 {
//	Generate unique answer once and only once, do not rely on Set.
//	Do not need preprocess.
//	We all know how to check a string of parentheses is valid using a stack. Or even simpler use a counter.
//	The counter will increase when it is ‘(‘ and decrease when it is ‘)’. 
	//Whenever the counter is negative, we have more ‘)’ than 
//	‘(‘ in the prefix.
//	To make the prefix valid, we need to remove a ‘)’. The problem is: which one? The answer is any ‘)’ in the prefix. 
//	However, if we remove any one, we will generate duplicate results, for example: s = ()), we can remove s[1] ors[2] 
//	but the result is the same (). Thus, we restrict ourself to remove the first ) in a series of consecutive )s.
//	After the removal, the prefix is then valid. We then call the function recursively to solve the rest of the string. 
//	However, we need to keep another information: the last removal position. If we do not have this position, 
//	we will generate duplicate by removing two ) in two steps only with a different order.
//	For this, we keep tracking the last removal position and only remove ) after that.
//	Now one may ask. What about (? What if s = (()(() in which we need remove (?
//	The answer is: do the same from right to left.
//	However, a cleverer idea is: reverse the string and reuse the code!

//	    public static List<String> removeInvalidParentheses(String s) {
//
//	        List<String> ans = new ArrayList<>();
//
//	        remove(s, ans, 0, 0, new char[]{'(', ')'});
//
//	        return ans;
//
//	    }
//
//
//	    public  static void remove(String s, List<String> ans, int last_i, int last_j, char[] par) {
//
//	        int stack = 0, i;
//
//	        // Search for mismatch
//
//	        for (i = last_i; i < s.length(); ++i) {
//
//	            if (s.charAt(i) == par[0]) stack++;
//
//	            if (s.charAt(i) == par[1]) stack--;
//
//	            if (stack < 0) break;
//
//	        }
//
//	        // Remove a parenthesis
//
//	        if (stack < 0) {
//
//	            for (int j = last_j; j <= i; ++j) {
//
//	                if (s.charAt(j) == par[1] && (j == last_j || s.charAt(j - 1) != par[1])) {
//
//	                    String candidate = s.substring(0, j) + s.substring(j + 1, s.length());
//
//	                    remove(candidate, ans, i, j, par);
//
//	                }
//
//	            }
//
//	            return;
//
//	        }
//
//	        // If no mismatch, try reverse the string
//
//	        String reversed = new StringBuilder(s).reverse().toString();
//
//	        if (par[0] == '(')
//
//	            remove(reversed, ans, 0, 0, new char[]{')', '('});
//
//	        else
//
//	            ans.add(reversed); // both sides are finished, got an answer
//
//	    }
	    
	    public static List<String> removeInvalidParentheses(String s) {
	    	 Set<String> res = new HashSet<>();
	    	 int rmL = 0, rmR = 0;
	    	 for(int i = 0; i < s.length(); i++) {
	    	  if(s.charAt(i) == '(') rmL++;
	    	     if(s.charAt(i) == ')') {
	    	      if(rmL != 0) rmL--;
	    	      else rmR++;
	    	     }
	    	 }
	    	 DFS(res, s, 0, rmL, rmR, 0, new StringBuilder());
	    	    return new ArrayList<String>(res); 
	    	}

	    	public static void DFS(Set<String> res, String s, int i, int rmL, int rmR, int open, StringBuilder sb) {
	    	    if(i == s.length() && rmL == 0 && rmR == 0 && open == 0) {
	    	     res.add(sb.toString());
	    	     return;
	    	    }
	    	    if(i == s.length() || rmL < 0 || rmR < 0 || open < 0) return;
	    	    
	    	    char c = s.charAt(i);
	    	    int len = sb.length();
	    	   
	    	 if(c == '(') {
	    	  DFS(res, s, i + 1, rmL - 1, rmR, open, sb);
	    	  DFS(res, s, i + 1, rmL, rmR, open + 1, sb.append(c)); 
	    	  
	    	 } else if(c == ')') {
	    	  DFS(res, s, i + 1, rmL, rmR - 1, open, sb);
	    	  DFS(res, s, i + 1, rmL, rmR, open - 1, sb.append(c));
	    	  
	    	 } else {
	    	  DFS(res, s, i + 1, rmL, rmR, open, sb.append(c)); 
	    	 }
	    	 
	    	 sb.setLength(len);
	    	}
	    	
	    	
//	    	 * LeetCode 301 - Remove Invalid Parentheses
//
//	    	 https://leetcode.com/problems/remove-invalid-parentheses/
//	    	 Remove the minimum number of invalid parentheses in order to make the input string valid. Return all possible results.
//	    	 Note: The input string may contain letters other than the parentheses ( and ).
//	    	 Examples:
//	    	 "()())()" -> ["()()()", "(())()"]
//	    	 "(a)())()" -> ["(a)()()", "(a())()"]
//	    	 ")(" -> [""]
	public static void main(String[] args) {
		
//		// TODO Auto-generated method stub
////		"()())()" -> ["()()()", "(())()"]
////				"(a)())()" -> ["(a)()()", "(a())()"]
////				")(" -> [""]
//		//System.out.println(removeInvalidParentheses("()())()"));
//		System.out.println(removeInvalidParentheses("))(((aa(a)"));
		

	}

}
