package alite.leetcode.xx2.sucess.extra;

import java.util.Arrays;

/**
 * Perfect Squares [LeetCode] | Training dragons the hard way - Programming Every Day!
Given a positive integer n, find the least number of perfect square numbers (for example, 1, 4, 9, 16, ...) which sum to n.
For example, given n = 12, return 3 because 12 = 4 + 4 + 4; given n = 13, return 2 because 13 = 4 + 9.
http://www.zrzahid.com/least-number-of-perfect-squares-that-sums-to-n/
maximum perfect square less then n will be √n. So, we can check for each numbers in j=1 to √n whether we can break n into two parts such that one part is a perfect square j*j and the remaining part n-j*j can be broken into perfect squares in similar manner. Clearly it has a recurrence relation ps(n)=j*j+ps(n-j*j), for all possible 1≤j≤√n. We need to find such j that minimizes number of perfect squares generated.
Let, PSN(i) is minimum number of perfect squares that sum to i
PSN(i) = min{1+PSN(i-j*j)}, for all j, 1≤j≤√n
O(nlogn) DP solution
https://discuss.leetcode.com/topic/26400/an-easy-understanding-dp-solution-in-java/
public static int perfectSquareDP(int n){
 if(n <= 0){
  return 0;
 }
 
 int[] dp = new int[n+1];
 Arrays.fill(dp, Integer.MAX_VALUE);
 dp[0] = 0;
 dp[1] = 1;
 
 //to compute least perfect for n we compute top down for each 
 //possible value sum from 2 to n
 for(int i = 2; i<=n; i++){
  //for a particular value i we can break it as sum of a perfect square j*j and 
  //all perfect squares from solution of the remainder (i-j*j)
  for(int j = 1; j*j<=i; j++){
   dp[i] = Math.min(dp[i], 1+dp[i-j*j]);
  }
 }
 
 return dp[n];
}
Fastest Solution – Lagrange’s four-square theorem
private static boolean is_square(int n){  
    int sqrt_n = (int)(Math.sqrt(n));  
    return (sqrt_n*sqrt_n == n);  
}
 
// Based on Lagrange's Four Square theorem, there 
// are only 4 possible results: 1, 2, 3, 4.
public static int perfectSquaresLagrange(int n) 
{  
    // If n is a perfect square, return 1.
    if(is_square(n)) 
    {
        return 1;  
    }

    // The result is 4 if n can be written in the 
    // form of 4^k*(8*m + 7).
    while ((n & 3) == 0) // n%4 == 0  
    {
        n >>= 2;  
    }
    if ((n & 7) == 7) // n%8 == 7
    {
        return 4;
    }

    // Check whether 2 is the result.
    int sqrt_n = (int)(Math.sqrt(n)); 
    for(int i = 1; i <= sqrt_n; i++)
    {  
        if (is_square(n - i*i)) 
        {
            return 2;  
        }
    }  

    return 3;  
} 
X.  动态规划（Dynamic Programming） - 时间复杂度都是O(n3/2)
http://bookshadow.com/weblog/2015/09/09/leetcode-perfect-squares/
时间复杂度：O(n * sqrt n)
初始化将dp数组置为无穷大；令dp[y * y] = 1，其中：y * y <= n
状态转移方程：
dp[x + y * y] = min(dp[x + y * y], dp[x] + 1)
其中：x + y * y <= n
    public int numSquares(int n) {
        int dp[] = new int[n + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        for (int i = 1; i * i <= n; i++) {
            dp[i * i] = 1;
        }
        for (int i = 1; i <= n; i++) {
            for (int j = 1; i + j * j <= n; j++) {
                dp[i + j * j] = Math.min(dp[i] + 1, dp[i + j * j]);
            }
        }
        return dp[n];
    }

int numSquares(int n) {
        static vector<int> dp {0};
        int m = dp.size();
        dp.resize(max(m, n+1), INT_MAX);
        for (int i=1, i2; (i2 = i*i)<=n; ++i)
          for (int j=max(m, i2); j<=n; ++j)
            if (dp[j] > dp[j-i2] + 1) dp[j] = dp[j-i2] + 1;
        return dp[n];
}
http://buttercola.blogspot.com/2015/09/leetcode-perfect-squares.html

    public int numSquares(int n) {

        if (n <= 0) {

            return 0;

        }

         

        int[] dp = new int[n + 1];

         

        for (int i = 1; i <= n; i++) {

            dp[i] = Integer.MAX_VALUE;//\\

            for (int j = 1; j * j <= i; j++) {

                dp[i] = Math.min(dp[i], dp[i - j * j] + 1);

            }

        }

         

        return dp[n];

    }
https://asanchina.wordpress.com/2015/09/11/perfect-squares/

    public int numSquares(int n) {
        if(n==1 || n==2 || n==3) return n;
        
        //dp[i] store the result of "i"
        int[] dp = new int[n+1];
        
        dp[0] = 0; dp[1] = 1; dp[2] = 2; dp[3] = 3;
        for(int i = 4; i < n+1; ++i) 
        { 
            //now divide i to two parts: i = left + right 
            int right = 1; 
            dp[i] = i; 
            while(i >= right*right)
            {
                dp[i] = dp[i]>dp[i-right*right]+1?dp[i-right*right]+1:dp[i];
                ++right;
            }
        }
        return dp[n];
    }
https://github.com/shawnfan/LeetCode/blob/master/Java/Perfect%20Squares.java
public int numSquares(int n) {
    if (n <= 0) {
      return 0;
    }
    int[] dp = new int[n + 1];
    dp[0] = 0;

    for (int i = 1; i <= n; i++) {
      int maxSqrNum = (int)Math.floor(Math.sqrt(i));
      int min = Integer.MAX_VALUE;
      for (int j = 1; j <= maxSqrNum; j++) {
        min = Math.min(min, dp[i - j * j] + 1);
      }
      dp[i] = min;
    }
    return dp[n];
}
http://shibaili.blogspot.com/2015/09/day-125-279-perfect-squares.html

    int numSquares(int n) {

        vector<int> dp(n + 1,INT_MAX);

        dp[0] = 0;

         

        for (int i = 0; i <= n; i++) {

            for (int j = 1; j *j + i <= n; j++) {

                dp[i + j * j] = min(dp[i + j * j],dp[i] + 1);

            }

        }

        return dp[n];

    }
Recursive Version: O(n * sqrt(n)) dp递归, 同样复杂度，但过不了oj

    int helper(unordered_map<int,int> &dic,int n) {

        if (n < 4) return n;

        if (dic.find(n) != dic.end()) return dic[n];

         

        int minLen = INT_MAX;

        int i = (int)sqrt(n);

     for (; i > 0; i--) {

      minLen = min(minLen,helper(dic,n - i * i) + 1);

     }

     dic[n] = minLen;

     return minLen;

    }


    int numSquares(int n) {

        unordered_map<int,int> dic;

     return helper(dic,n);

    }
由于该题有许多大型的测试样例，因此对于速度比较慢的编程语言，在测试样例之间共享计算结果，可以节省时间开销。
参考链接：https://leetcode.com/discuss/56993/static-dp-c-12-ms-python-172-ms-ruby-384-ms
class Solution(object):
    _dp = [0]
    def numSquares(self, n):
        dp = self._dp
        while len(dp) <= n:
            dp += min(dp[-i*i] for i in range(1, int(len(dp)**0.5+1))) + 1,
        return dp[n]
https://leetcode.com/discuss/58056/summary-of-different-solutions-bfs-static-and-mathematics
A Static DP solution in Java: 15ms.
    static List<Integer> result = new ArrayList<>();
    public int numSquares(int n) {
        if (result.size() == 0) {
            result.add(0);
        }
        while (result.size() <= n) {
            int m = result.size();
            int tempMin = Integer.MAX_VALUE;
            for (int j = 1; j * j <= m; j++) {
                tempMin = Math.min(tempMin, result.get(m - j * j) + 1);
            }
            result.add(tempMin);
        }
        return result.get(n);
    }

II. Breadth First Search
Summary of 4 different solutions (BFS, DP, static DP and mathematics)
http://traceformula.blogspot.com/2015/09/perfect-squares-leetcode.html
In general, the time complexity of BFS is O(|V| + |E|) where |V| is the number of vertices in the graph and |E| is the number of edges in the graph. As in the constructed graph, |V| = n and |E| <= n^2. The time complexity of the BFS here is O(n^2).



Picture 1: Graph of numbers 
In this problem, we define a graph where each number from 0 to n is a node. Two numbers p < q is connected if (q-p) is a perfect square.
So we can simply do a Breadth First Search from the node 0.
class Solution(object):  
    _dp = [0]  
    def numSquares(self, n):  
        """ 
        :type n: int 
        :rtype: int 
        """  
         
        q1 = [0]  
        q2 = []  
        level = 0  
        visited = [False] * (n+1)  
        while True:  
            level += 1  
            for v in q1:  
                i = 0  
                while True:  
                    i += 1  
                    t = v + i * i  
                    if t == n: return level  
                    if t > n: break  
                    if visited[t]: continue  
                    q2.append(t)  
                    visited[t] = True  
            q1 = q2  
            q2 = []  
                  
        return 0  
http://nb4799.neu.edu/wordpress/?p=1010
Don’t forget to use a set visited to record which nodes have been visited. A node can be reached through multiple ways but BFS always makes sure whenever it is reached with the least steps, it is flagged as visited.
Why can’t we use DFS? Why can’t we report steps when we reach n by DFS? Because unlike BFS, DFS doesn’t make sure you are always using the least step to reach a node. 
if you want to find “….’s least number to do something”, you can try to think about solving it in BFS way.

    def numSquares(self, n):

        """

        :type n: int

        :rtype: int

        """

        # corner case 1

        if n < 0:

            return -1

        # corner case 2

        if n==0:

            return 1

    

        q = deque()         

        visited = set()

        # val, step

        q.append((0,0))

        visited.add(0)


        while q:

            val, step = q.popleft()    

            for i in xrange(1, n+1):

                tmp = val + i**2

                if tmp > n:

                    break

                if tmp == n:

                    return step+1

                else:

                    if tmp not in visited:

                        visited.add(tmp)

                        q.append((tmp, step+1))                 

        

        # Should never reach here

        return -1   
https://leetcode.com/discuss/62229/short-python-solution-using-bfs
The basic idea of this solution is a BSF search for shortest path, take 12 as an example, as shown below, the shortest path is 12-8-4-0:
exapmle
def numSquares(self, n):
    if n < 2:
        return n
    lst = []
    i = 1
    while i * i <= n:
        lst.append( i * i )
        i += 1
    cnt = 0
    toCheck = {n}
    while toCheck:
        cnt += 1
        temp = set()
        for x in toCheck:
            for y in lst:
                if x == y:
                    return cnt
                if x < y:
                    break
                temp.add(x-y)
        toCheck = temp

    return cnt
https://leetcode.com/discuss/58056/summary-of-different-solutions-bfs-static-and-mathematics
Java BFS implementation: Start from node 0 in queue, and keep pushing in perfect square number + curr value, once we reach number n, we found the solution.
public int numSquares(int n) {
    Queue<Integer> q = new LinkedList<>();
    Set<Integer> visited = new HashSet<>();
    q.offer(0);
    visited.add(0);
    int depth = 0;
    while(!q.isEmpty()) {
        int size = q.size();
        depth++;
        while(size-- > 0) {
            int u = q.poll();
            for(int i = 1; i*i <= n; i++) {
                int v = u+i*i;
                if(v == n) {
                    return depth;
                }
                if(v > n) {
                    break;
                }
                if(!visited.contains(v)) {
                    q.offer(v);
                    visited.add(v);
                }
            }
        }
    }
    return depth;
}
http://nb4799.neu.edu/wordpress/?p=1010

    def numSquares(self, n):

        """

        :type n: int

        :rtype: int

        """

        # corner case 1

        if n < 0:

            return -1

        # corner case 2

        if n==0:

            return 1

    

        q = deque()         

        visited = set()

        # val, step

        q.append((0,0))

        visited.add(0)


        while q:

            val, step = q.popleft()    

            for i in xrange(1, n+1):

                tmp = val + i**2

                if tmp > n:

                    break

                if tmp == n:

                    return step+1

                else:

                    if tmp not in visited:

                        visited.add(tmp)

                        q.append((tmp, step+1))                 

        

        # Should never reach here

        return -1   
Use BFS to solve this problem. We can think of a tree graph in which nodes are value 0, 1,…,n. A link only exists between i and j (i<j) if j=i+square_num. We start from node 0 and add all nodes reachable from 0 to queue. Then we start to fetch elements from the queue to continue to explore until we reach node n. Since BFS guarantees that for every round all nodes in the queue have same depth, so whenever we reach node n, we know the least depth needed to reach n, i.e., the least number of square number to add to n.
Don’t forget to use a set visited to record which nodes have been visited. A node can be reached through multiple ways but BFS always makes sure whenever it is reached with the least steps, it is flagged as visited.
Why can’t we use DFS? Why can’t we report steps when we reach n by DFS? Because unlike BFS, DFS doesn’t make sure you are always using the least step to reach a node. Imagine DFS may traverse all nodes to reach n becaues each node is its previous node plus one (because one is a square number.) When you reach n, you use n steps. But it may or may not be the least number required to reach n.
This teaches us a lesson that if you want to find “….’s least number to do something”, you can try to think about solving it in BFS way.
In this problem, we define a graph where each number from 0 to n is a node. Two numbers p < q is connected if (q-p) is a perfect square.
So we can simply do a Breadth First Search from the node 0.

class Solution(object):  
    _dp = [0]  
    def numSquares(self, n):  
        q1 = [0]  
        q2 = []  
        level = 0  
        visited = [False] * (n+1)  
        while True:  
            level += 1  
            for v in q1:  
                i = 0  
                while True:  
                    i += 1  
                    t = v + i * i  
                    if t == n: return level  
                    if t > n: break  
                    if visited[t]: continue  
                    q2.append(t)  
                    visited[t] = True  
            q1 = q2  
            q2 = []  
                  
        return 0 
X. DFS
http://blog.csdn.net/elton_xiao/article/details/49021623
模型同上，进行深度优先搜索。
从n从发，减去一个平方，即得到下一层的一个节点。 直到找到节点0为止。
遍历所有路径，记录下最短的路径数。
1. 遍历时，使用变量level，限制递归的深度。以节省时间。
2. 先减去较大的平方，再减去较小的平方。 这样，当搜索到节点0时，所用的步数（即深度），不会特别大。从而用此深度，限制后续的搜索深度。
因为我们要找的是最短路径。
反之如果从1个平方开始递减的话，会出现大量的无用搜索。比如每次只减掉1，搜索n，将第一次会达到n层。
https://leetcode.com/discuss/58056/summary-of-different-solutions-bfs-static-and-mathematics
    int numSquares(int n) {
        vector<int> nums(n+1);
        return helper(n, nums, n);
    }

    int helper(int n, vector<int>& nums, int level) {
        if (!n)
            return 0;
        if (nums[n])
            return nums[n];
        if (!level)
            return -1;

        --level;
        const int up = sqrt(n);
        int ans = n;
        int res = n;
        for (int i=up; i>=1 && res; i--) {
            res = helper(n-i*i, nums, min(ans, level));
            if (res >= 0)
                ans = min(ans, res);
        }
        nums[n] = ans + 1;
        return nums[n];
    }
https://leetcode.com/discuss/62782/recursive-dfs-solution-in-java-which-i-believe-has-o-run-time


I'm not certain on my math so please correct me if I'm wrong, but the run-time should be O(n^(1/2) * n^(1/4) * n^(1/8) * ...), repeating as many times as there are layers in the minimum, which regardless of the number of layers approaches O(n).
public int numSquares(int n) {
    return dfs(n,0,n);
}
public int dfs(int n, int level, int min) {
    if(n == 0 || level >= min) return level;
    for(int i = (int) Math.sqrt(n); i > 0; i--) {
        min = dfs(n - ((int)Math.pow(i,2)), level+1, min);
    }
    return min;
}

暴力枚举 brute force time complexity: exponential
    int numSquares(int n) {
        int digit = sqrt(n);
        if(digit*digit == n) return 1;
        
        int minimum = n;//this is the maximum number
        dfs(n, 0, digit, 0, minimum);
        return minimum;
    }
    void dfs(int n, int curSum, int curDigit, int curNum, int& minimum)
    {
        if(curSum > n) return;
        if(curSum == n)
        {
            minimum = minimum>curNum?curNum:minimum;
            return;
        }
        if(curDigit <= 0) return; 
        if(curDigit == 1) 
        { 
            curNum += n-curSum; 
            minimum = minimum>curNum?curNum:minimum;
            return;
        }
        //either use curDigit or not use
        if(n-curSum >= curDigit*curDigit)
            dfs(n, curSum+curDigit*curDigit, curDigit, curNum+1, minimum);
        dfs(n, curSum, curDigit-1, curNum, minimum);
    }
http://blog.csdn.net/u012290414/article/details/48730819
This doesn't use visited - not efficient.
http://traceformula.blogspot.com/2015/09/perfect-squares-leetcode-part-2-solve.html
 I want to confirm that all the returned values will always be in range [1,4] inclusively. Why is that? It is because we have Lagrange's Four Square Theorem, also known as Bachet's conjecture:
Every natural numbers can be expressed as a sum of four square numbers. (*)
As a note, many proofs of the theorem use the Euler's four square identity:

Picture 1: Euler's Four Square Identity
http://www.alpertron.com.ar/4SQUARES.HTM
From the article, you can find that if a number is in the form n = 4^r (8k+7)(Where ^ is power), then n cannot be represented as a sum of less than 4 perfect squares. If n is not in the above form, then n can be represented as a sum of 1, 2, or 3 perfect squares.

    public boolean is_square(int n){  
        int temp = (int) Math.sqrt(n);  
        return temp * temp == n;  
    }  
    public int numSquares(int n) {  
        while ((n & 3) == 0) //n % 4 == 0  
            n >>= 2;  
        if ((n & 7) == 7) return 4; //n% 8 == 7  
          
        if(is_square(n)) return 1;  
        int sqrt_n = (int) Math.sqrt(n);  
        for (int i = 1; i<= sqrt_n; i++){  
            if (is_square(n-i*i)) return 2;  
        }  
        return 3;  
    }  

https://discuss.leetcode.com/topic/24255/summary-of-4-different-solutions-bfs-dp-static-dp-and-mathematics/2
Read full article from Perfect Squares [LeetCode] | Training dragons the hard way - Programming Every Day!
 * @author het
 *
 */
public class LeetCode287PerfectSquares {
	public static int perfectSquareDP(int n){
		 if(n <= 0){
		  return 0;
		 }
		 
		 int[] dp = new int[n+1];
		 Arrays.fill(dp, Integer.MAX_VALUE);
		 dp[0] = 0;
		 dp[1] = 1;
		 
		 //to compute least perfect for n we compute top down for each 
		 //possible value sum from 2 to n
		 for(int i = 2; i<=n; i++){
		  //for a particular value i we can break it as sum of a perfect square j*j and 
		  //all perfect squares from solution of the remainder (i-j*j)
		  for(int j = 1; j*j<=i; j++){
		   dp[i] = Math.min(dp[i], 1+dp[i-j*j]);// dp[i] = Math.min(dp[i], 1+dp[i-j*j]);
		  }
		 }
		 
		 return dp[n];
		}
}
