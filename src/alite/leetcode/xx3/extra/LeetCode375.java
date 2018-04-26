package alite.leetcode.xx3.extra;
/**
 * LeetCode 375 - Guess Number Higher or Lower II

http://bookshadow.com/weblog/2016/07/16/leetcode-guess-number-higher-or-lower-ii/
We are playing the Guess Game. The game is as follows:
I pick a number from 1 to n. You have to guess which number I picked.
Every time you guess wrong, I'll tell you whether the number I picked is higher or lower.
However, when you guess a particular number x, and you guess wrong, you pay $x. You win the game when you guess 
the number I picked.
Example:
n = 10, I pick 8.

First round:  You guess 5, I tell you that it's higher. You pay $5.
Second round: You guess 7, I tell you that it's higher. You pay $7.
Third round:  You guess 9, I tell you that it's lower. You pay $9.

Game over. 8 is the number I picked.

You end up paying $5 + $7 + $9 = $21.
Given a particular n ≥ 1, find out how much money you need to have to guarantee a win.
Hint:
The best strategy to play the game is to minimize the maximum loss you could possibly face. Another strategy
 is to minimize the expected loss. Here, we are interested in the first scenario.
Take a small example (n = 3). What do you end up paying in the worst case?
Check out this article if you're still stuck.
The purely recursive implementation of minimax would be worthless for even a small n. 
You MUST use dynamic programming.
As a follow-up, how would you modify your code to solve the problem of minimizing the expected loss,
 instead of the worst-case loss?
题目大意：
我们玩猜数字游戏。游戏如下：
我从1到n中选择一个数字。你需要猜我选的是哪个数字。
每一次你猜错，我都会告诉你数字是高了还是低了。
然而，当你选择某个数字x并且猜错，你需要支付$x。当你猜到我选的数字时胜出。
测试用例如题目描述。
给定n ≥ 1，计算你需要多少钱才可以确保赢得游戏。
提示：
游戏的最佳策略是最小化你面临的最大可能损失。另一种策略是最小化期望损失。在这里，我们关注第一种策略。
以一个小的输入为例（n = 3）。最坏情况下你会支付多少金额？
如果还是一筹莫展，可以参考这篇文章。
单纯的minimax递归实现即使对于很小的n都是非常耗时的。你必须使用动态规划。
作为思考题，你怎样修改代码来实现最小化的期望损失，而不是最小化的最坏损失？
https://www.hrwhisper.me/leetcode-guess-number-higher-lower-ii/
在 374 Guess Number Higher or Lower 中，我们采用二分法来进行查找，但这题并不是用二分法。
这题要求我们在猜测数字y未知的情况下（1~n任意一个数），要我们在最坏情况下我们支付最少的钱。也就是说要考虑所有y的情况。
我们假定选择了一个错误的数x，（1<=x<=n && x!=y ）那么就知道接下来应该从[1,x-1 ] 或者[x+1,n]中进行查找。 假如我们已经解决了
[1,x-1] 和 [x+1,n]计算问题，我们将其表示为solve(L,x-1) 和solve(x+1,n)，那么我们应该选择max(solve(L,x-1),solve(x+1,n)) 
这样就是求最坏情况下的损失。总的损失就是 f(x) = x + max(solve(L,x-1),solve(x+1,n))
那么将x从1~n进行遍历，取使得 f(x) 达到最小，来确定最坏情况下最小的损失，也就是我们初始应该选择哪个数。
上面的说法其实是一个自顶向下的过程（Top-down），可以用递归来解决。很容易得到如下的代码（这里用了记忆化搜索）

动态规划（Dynamic Programming）
参考：https://discuss.leetcode.com/topic/51356/two-python-solutions
状态转移方程：
dp[i][j] = min(k + max(dp[i][k - 1], dp[k + 1][j]))
其中dp[i][j]表示猜出范围[i, j]的数字需要花费的最少金额。
    def getMoneyAmount(self, n):
        """
        :type n: int
        :rtype: int
        """
        dp = [[0] * (n+1) for _ in range(n+1)]
        for gap in range(1, n):
            for lo in range(1, n+1-gap):
                hi = lo + gap
                dp[lo][hi] = min(x + max(dp[lo][x-1], dp[x+1][hi])
                                   for x in range(lo, hi))
        return dp[1][n]


    public int getMoneyAmount(int n) {

        int[][] dp = new int[n+1][n+1];

        for (int L = n - 1; L > 0; L--) {

   for (int R = L + 1; R <= n; R++) {

    dp[L][R] = 0x7FFFFFFF; //INT_MAX

    for (int i = L; i < R; i++) {

     dp[L][R] = Math.min(dp[L][R], i + Math.max(dp[L][i - 1], dp[i + 1][R]));

    }

   }

  }

  return dp[1][n];

    }
https://discuss.leetcode.com/topic/51494/java-commented-dp-solution
Also, it takes a while for me to wrap my head around "min of max cost". My understand is that: 
you strategy is the best, but your luck is the worst. You only guess right when there is no possibilities to guess wrong.
public int getMoneyAmount(int n) {
    int[][] dp = new int[n+1][n+1];
    for(int len=1;len<n;len++){
        for(int i=1;i+len<=n;i++){
            int j=i+len;
            int min = Integer.MAX_VALUE;
            for(int k=i;k<j;k++){
              int tmp = k+Math.max(dp[i][k-1],dp[k+1][j]);
              min = Math.min(min,tmp);
            }
            dp[i][j] = min;
        }
    }
    return dp[1][n];
}
http://www.cnblogs.com/grandyang/p/5677550.html
这道题需要用到Minimax极小化极大算法，关于这个算法可以参见这篇讲解，并且题目中还说明了要用DP来做，那么我们需要建立一个二维的dp数组，其中dp[i][j]表示从数字i到j之间猜中任意一个数字最少需要花费的钱数，那么我们需要遍历每一段区间[j, i]，维护一个全局最小值global_min变量，然后遍历该区间中的每一个数字，计算局部最大值local_max = k + max(dp[j][k - 1], dp[k + 1][i])，这个正好是将该区间在每一个位置都分为两段，然后取当前位置的花费加上左右两段中较大的花费之和为局部最大值，然后更新全局最小值，最后在更新dp[j][i]的时候看j和i是否是相邻的，相邻的话赋为i，否则赋为global_min
    int getMoneyAmount(int n) {
        vector<vector<int>> dp(n + 1, vector<int>(n + 1, 0));
        for (int i = 2; i <= n; ++i) {
            for (int j = i - 1; j > 0; --j) {
                int global_min = INT_MAX;
                for (int k = j + 1; k < i; ++k) {
                    int local_max = k + max(dp[j][k - 1], dp[k + 1][i]);
                    global_min = min(global_min, local_max);
                }
                dp[j][i] = j + 1 == i ? j : global_min;
            }
        }
        return dp[1][n];
    }

记忆化搜索
http://fangde2.blogspot.com/2016/07/leetcode-q375-guess-number-higher-or.html
Definition of dp[i][j]: minimum number of money to guarantee win for subproblem [i, j].
Target: dp[1][n]
Corner case: dp[i][i] = 0 (because the only element must be correct)
Equation: we can choose k (i<=k<=j) as our guess, and pay price k. After our guess, the problem is divided into two subproblems. Notice we do not need to pay the money for both subproblems. We only need to pay the worst case (because the system will tell us which side we should go) to guarantee win. So dp[i][j] = min (i<=k<=j) { k + max(dp[i][k-1], dp[k+1][j]) }
int helper(int i, int j, vector<vector<int> >& myCache){
    if(i==j){
        myCache[i][j]=0;
        return 0;
    }
   
    if(myCache[i][j]!=-1)
        return myCache[i][j];
       
    myCache[i][j]=INT_MAX;
    for(int k = i; k<=j; k++){
        int l = k-1>=i? helper(i, k-1, myCache):0;
        int r = k+1<=j? helper(k+1, j, myCache):0;
        myCache[i][j] = min(myCache[i][j], k+max(l, r));
    }
   
    return myCache[i][j];
}

int getMoneyAmount(int n) {
    if(n==1)
        return 0;
       
    vector<vector<int> > myCache(n+1, vector<int> (n+1, -1));
    int res = helper(1, n, myCache);
    return res;
}

自顶向下（Top-down Approach）求解，采用辅助数组dp记录已经计算过的结果，减少重复计算。
    def getMoneyAmount(self, n):
        """
        :type n: int
        :rtype: int
        """
        dp = [[0] * (n+1) for _ in range(n+1)]
        def solve(lo, hi):
            if lo < hi and dp[lo][hi] == 0:
                dp[lo][hi] = min(x + max(solve(lo,x-1), solve(x+1,hi))
                               for x in range(lo, hi))
            return dp[lo][hi]
        return solve(1, n)







    public int getMoneyAmount(int n) {


        int[][] dp = new int[n+1][n+1];


        return solve(dp, 1, n);


    }


    int solve(int[][] dp, int L, int R) {


  if (L >= R) return 0;


  if (dp[L][R] != 0) return dp[L][R];


  dp[L][R] = 0x7FFFFFFF;


  for (int i = L; i <= R; i++) {


   dp[L][R] = Math.min(dp[L][R], i + Math.max(solve(dp,L,i-1),solve(dp,i+1,R)));


  }


  return dp[L][R];




 }

https://discuss.leetcode.com/topic/51353/simple-dp-solution-with-explanation
For each number x in range[i~j]
we do: result_when_pick_x = x + max{DP([i~x-1]), DP([x+1, j])}
--> // the max means whenever you choose a number, the feedback is always bad and therefore leads you to a worse branch.
then we get DP([i~j]) = min{xi, ... ,xj}
--> // this min makes sure that you are minimizing your cost.
public class Solution {
    public int getMoneyAmount(int n) {
        int[][] table = new int[n+1][n+1];
        return DP(table, 1, n);
    }
    
    int DP(int[][] t, int s, int e){
        if(s >= e) return 0;
        if(t[s][e] != 0) return t[s][e];
        int res = Integer.MAX_VALUE;
        for(int x=s; x<=e; x++){
            int tmp = x + Math.max(DP(t, s, x-1), DP(t, x+1, e));
            res = Math.min(res, tmp);
        }
        t[s][e] = res;
        return res;
    }
}
Here is a bottom up solution.
public class Solution {
    public int getMoneyAmount(int n) {
        int[][] table = new int[n+1][n+1];
        for(int j=2; j<=n; j++){
            for(int i=j-1; i>0; i--){
                int globalMin = Integer.MAX_VALUE;
                for(int k=i+1; k<j; k++){
                    int localMax = k + Math.max(table[i][k-1], table[k+1][j]);
                    globalMin = Math.min(globalMin, localMax);
                }
                table[i][j] = i+1==j?i:globalMin;
            }
        }
        return table[1][n];
    }
}
X.O(N^2)
https://discuss.leetcode.com/topic/51984/java-o-n-2-dp-solution-with-clear-explanation
The main idea of the algorithm is to optimize the computations for the trivial O(n^3) dp solution. My understanding for the algorithm is as follows. Notice that my code is a little different from the original code given by the two references.
First, define f[a][b] = the min worst-cast cost to guess a number a<=m<=b, thus, f[1][n] is the result, and f[a][b] = min{max{f[a][k-1], f[k+1][b]}+k} for a<=k<=b.
Second, define k0[a][b] = max{k : a<=k<=b && f[a][k-1]<=f[k+1][b]}. then
max{f[a][k-1], f[k+1][b]} = f[k+1][b] if a<=k<=k0[a][b], and =f[a][k-1] if k0[a][b]<k<=b.
Therefore, f[a][b]=min( f1[a][b], f2[a][b] ), where f1[a][b] = min{ f[k+1][b]+k } for a<=k<=k0[a][b], and f2[a][b] = min{ f[a][k-1]+k, k0[a][b]<k<=b} = f[a][k0[a][b]]+k0[a][b]+1.
Now the key is: given a, b, how to find k0[a][b] and f1[a][b], in O(1) time. And I think that is also the most tricky or difficult part.
We shall run the algorithm in double-looping structure, which is for(b=1, b<=n, b++){ for(a=b-1; a>0; a--) proceed_to_find_f[a][b]; }. Therefore, f[i][j] for abs(i-j)<b-a are already obtained, and k0[a+1][b] was just found.
Clearly, a<=k0[a][b]<=k0[a+1][b]<=b. Thus, along the inner loop of (a=b-1; a>0; a--), k0[all a's][b] would be found by definition in O(b) time. In other words, it is O(1) time to get k0[a][b] for fixed a, b.
Now consider the index sequence: a, a+1,..., k0[a][b] ,..., k0[a+1][b], ..., b.
Suppose currently a deque is used to store the values of { f[k+1][b]+k, a+1<=k<=k0[a+1][b] } sorted in ascending order (from the last step).
To find f1[a][b] = min{ f[k+1][b]+k } for a<=k<=k0[a][b], we have to throw away the values in the deque whose corresponding index j satisfies k0[a][b]<j<= k0[a+1][b], and add the value f[a+1][b]+a into deque, then extract the minimum. Since the deque is sorted, we can do the process by:
while(peekFirst().index > k0[a][b]) pollFirst();
while(f[a+1][b]+a < peekLast().value) pollLast(); // The elements polled are useless in the later loops (when a is smaller)
offerLast(new Item(index=a, value=f[a+1][b]+a));
f1[a][b] = peekFirst().value;
Similar to the insertion sort, the above process still yields a sorted deque. Notice that given a, b, the deque is offered only once. Thus, for fixed b, deque.size()<= b. Hence along the inner loop of (a=b-1;a>0; a--), deque is offered for b times, and so is polled at most for b times. In other words, it is O(1) time to get f1[a][b] for fixed a, b.
Since we have a double-looping with variables a,b, the overall time complexity is O(n^2). In fact, k0[ ][ ], f1[ ][ ], f2[ ][ ] needn't be stored
public int xxxgetMoneyAmount(int n) {
 int[][] f = new int[n + 1][n + 1];
 Deque<Integer[]> q; // item[]{index, value}

 int a, b, k0, v, f1, f2;

 for (b = 2; b <= n; b++) {
  k0 = b - 1;
  q = new LinkedList<Integer[]>();

  for (a = b - 1; a > 0; a--) {
   // find k0[a][b] by definition in O(1) time.
   while (f[a][k0 - 1] > f[k0 + 1][b])
    k0--;

   // find f1[a][b] in O(1) time.
   while (!q.isEmpty() && q.peekFirst()[0] > k0)
    q.pollFirst();

   v = f[a + 1][b] + a;

   while (!q.isEmpty() && v < q.peekLast()[1])
    q.pollLast();

   q.offerLast(new Integer[] { a, v });

   f1 = q.peekFirst()[1];
   f2 = f[a][k0] + k0 + 1;
   f[a][b] = Math.min(f1, f2);
  }
 }

 return f[1][n];
}
Notice that the operations of deques are not quite efficient. However, from the analysis above, we know that deque.size()<= b, therefore,we can just use arrays of size O(n) to fulfill the operations of the deque. The code is as follows, which runs about 4 times faster than the one above.
public int getMoneyAmount(int n) {
 int[][] f = new int[n + 1][n + 1];

 // replace deque by idx and val arrays:
 // q.pollFirst()={index[beginIdx], value[beginIdx]},
 // q.pollLast()={index[endIdx], value[endIdx]}, ...
 int beginIdx, endIdx;
 int[] index = new int[n + 1];
 int[] value = new int[n + 1];

 int a, b, k0, v, f1, f2;

 for (b = 2; b <= n; b++) {
  k0 = b - 1;

  beginIdx = 0;
  endIdx = -1; // q.isEmpty()==(beginIdx>endIdx)

  for (a = b - 1; a > 0; a--) {
   // find k0[a][b] by definition in O(1) time.
   while (f[a][k0 - 1] > f[k0 + 1][b])
    k0--;

   // find f1[a][b] in O(1) time.
   while (beginIdx <= endIdx && index[beginIdx] > k0)
    beginIdx++; // q.pollFirst();

   v = f[a + 1][b] + a;

   while (beginIdx <= endIdx && v < value[endIdx])
    endIdx--; // q.pollLast();

                        // q.offerLast(new Integer[] { a, v });
   endIdx++;
   index[endIdx] = a;
   value[endIdx] = v; 

   f1 = value[beginIdx];
   f2 = f[a][k0] + k0 + 1;
   f[a][b] = Math.min(f1, f2);
  }
 }

 return f[1][n];
}
https://discuss.leetcode.com/topic/51487/an-o-n-2-dp-solution-quite-hard
http://artofproblemsolving.com/community/c296841h1273742
First, here is a trivial DP solution with $O(n^{3})$. Let $f(a,b)$ be the minimum worst-case cost to guess a number $a\le n\le b$, then we have$$
f(a,b)=\min_{a\le k\le b}\Big(\max\big\{ f(a,k-1),f(k+1,b)\big\}+k\Big)\ .
$$Enumerate $k$ and we are done. To optimize the computation, let$$
k_{0}(a,b)=\max\{k:f(a,k-1)\le f(k+1,b)\}\ ,
$$then\begin{align*}
 & \max\big\{ f(a,k-1),f(k+1,b)\big\}\\
= & \begin{cases}
f(k+1,b) & \text{if }a\le k\le k_{0}(a,b)\\
f(a,k-1) & \text{if }k_{0}(a,b)<k\le b
\end{cases}\;.
\end{align*}Therefore,\begin{align*}
f(a,b) & =\min\big\{ f_{1}(a,b),f_{2}(a,b)\big\}\\
f_{1}(a,b) & =\min_{a\le k\le k_{0}(a,b)}\big(f(k+1,b)+k\big)\\
f_{2}(a,b) & =\min_{k_{0}(a,b)<k\le b}\big(f(a,k-1)+k\big)\\
 & =f\big(a,k_{0}(a,b)\big)+k_{0}(a,b)+1\ .
\end{align*}Notice that $k_{0}(a_{1},b)\le k_{0}(a_{2},b)$ when $a_{1}\le a_{2}$. So we can compute $k_{0}(a,b)$ in amortized $O(1)$ time. Then we need the sliding minimum of $g(k,b)=f(k+1,b)+k$ with $b$ fixed and $a\le k\le k_{0}(a,b)$. This can be done using an increasing deque, and it takes amortized $O(1)$ time to get the desired minimum. We need to compute $O(n^{2})$ values of $f$, and each takes $O(1)$ time, so in all $O(n^{2})$.
    int getMoneyAmount(int n) {
        vector<vector<int>> u(n + 2, vector<int>(n + 2));
        for (int b = 2; b <= n; ++b) {
            int k0 = b - 1;
            deque<pair<int, int>> v;
            for (int a = b - 1; a; --a) {
                while (u[a][k0 - 1] > u[k0 + 1][b]) {
                    if (!v.empty() && v.front().second == k0) v.pop_front();
                    --k0;
                }
                int vn = a + u[a + 1][b];
                while (!v.empty() && vn < v.back().first) v.pop_back();
                v.emplace_back(vn, a);
                int u1 = u[a][k0] + k0 + 1;
                int u2 = v.front().first;
                u[a][b] = u1 < u2 ? u1 : u2;
            }
        }
        return u[1][n];
    }

我们可以将路径打印出来，来看看具体的选择策略的过程，以便加深理解


    def getMoneyAmount(self, n):


        """


        :type n: int


        :rtype: int


        """


        dp = [[0] * (n + 1) for _ in range(n + 1)]


        self.solve(dp, 1, n)


        self.print_path(dp, 1, n)


        return dp[1][n]




    def solve(self, dp, L, R):


        if L >= R: return 0


        if dp[L][R]: return dp[L][R]


        dp[L][R] = min(i + max(self.solve(dp, L, i - 1), self.solve(dp, i + 1, R)) for i in range(L, R + 1))


        return dp[L][R]




    def print_path(self, dp, L, R):


        if L >= R: return


        for i in range(L, R + 1):


            if dp[L][R] == i + max(dp[L][i - 1], dp[i + 1][R]):


                print i


                if dp[L][i - 1] > dp[i + 1][R]:


                    self.print_path(dp, L, i - 1)


                else:


                    self.print_path(dp, i + 1, R)


                break

https://discuss.leetcode.com/topic/52760/still-confused-about-guarantee-a-win-why-minimize-the-max-cost/2
When n = 10, the answer is 16 by any of the correct solutions here. However, the example in the question 
clearly gives a scenario that you have to pay 21 to win the game. If you only have 16, how is it possible to
 guarantee a win?
I see a post said this problem is essentially "Best strategy and worst luck". However, assume 
there is a person coming into play, how is it possible that he knows the best strategy? 
If not, he is very likely to make some bad moves. If a win needs to be guaranteed, then 
should we consider the "worst strategy"?
So my question is, why each time we minimize the max cost?
The example in the problem description was intended to demonstrate the rules of the game.
 It does not reflect the optimal strategy.
The problem is to find a strategy that minimizes the cost for any possible secret number in [1..N].
 If a player adheres to that strategy, it will be impossible for them to lose more than this minimum.
You can visualize the strategy as a binary tree. For N = 10:
0_1469924346187_strategy10.png
The secret number can be any number [1..10] but if you choose your sequence of guesses by 
following the left or right branches according to whether your guess was high or low, then you will 
always guess the right number while losing no more than $16.
 * @author het
 *
 */


//动态规划（Dynamic Programming）
//参考：https://discuss.leetcode.com/topic/51356/two-python-solutions
//状态转移方程：
//dp[i][j] = min(k + max(dp[i][k - 1], dp[k + 1][j]))
//其中dp[i][j]表示猜出范围[i, j]的数字需要花费的最少金额。
//dp[i][j] = min(k + max(dp[i][k - 1], dp[k + 1][j]))
//dp[i][j] = min(k + max(dp[i][k - 1], dp[k + 1][j]))
public class LeetCode375 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
