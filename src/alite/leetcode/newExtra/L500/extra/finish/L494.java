package alite.leetcode.newExtra.L500.extra.finish;
/**
 * LeetCode 494 - Target Sum

https://leetcode.com/problems/target-sum/
You are given a list of non-negative integers, a1, a2, ..., an, and a target, S. Now 
you have 2 symbols + and -. For each integer, you should choose one from + and - as its new symbol.
Find out how many ways to assign symbols to make sum of integers equal to target S.
Example 1:
Input: nums is [1, 1, 1, 1, 1], S is 3. 
Output: 5
Explanation: 

-1+1+1+1+1 = 3
+1-1+1+1+1 = 3
+1+1-1+1+1 = 3
+1+1+1-1+1 = 3
+1+1+1+1-1 = 3

There are 5 ways to assign symbols to make the sum of nums be target 3.
Note:
The length of the given array is positive and will not exceed 20.
The sum of elements in the given array will not exceed 1000.
Your output answer is guaranteed to be fitted in a 32-bit integer.
http://bookshadow.com/weblog/2017/01/22/leetcode-target-sum/
X. 动态规划（Dynamic Programming）
状态转移方程：dp[i + 1][k + nums[i] * sgn] += dp[i][k]

上式中，sgn取值±1，k为dp[i]中保存的所有状态；初始令dp[0][0] = 1

利用滚动数组，可以将空间复杂度优化到O(n)，n为可能的运算结果的个数
    def findTargetSumWays(self, nums, S):
        """
        :type nums: List[int]
        :type S: int
        :rtype: int
        """
        dp = collections.Counter()
        dp[0] = 1
        for n in nums:
            ndp = collections.Counter()
            for sgn in (1, -1):
                for k in dp.keys():
                    ndp[k + n * sgn] += dp[k]
            dp = ndp
        return dp[S]

http://www.wonter.net/archives/1101.html
定义dp[i][j]:=dp[i][j]:=前i个数构成j有多少种方案
然后枚举第i个数前面放+还是-
dp[i][j] = dp[i - 1][j - a[i]] + dp[i - 1][j + a[i]]dp[i][j]=dp[i−1][j−a[i]]+dp[i−1][j+a[i]]
为了节省空间可以使用滚动数组
而且注意到数字可能为负数，所以我们可以让所有数字向右偏移1000，也就是-1000看作0，-999看作1，0看作1000，999看作1999，1000看作2000等等
之后见leetcode上有更巧妙的方法可以避免负数的情况，因为如果没有负数的话，我们则可以直接使用背包来求解，时间和空间上都会更好
大致思路如下：
我们假设数字前面为’+’的集合为a，数字前面为’-‘的集合为b，sum(a)为选的正数的累加和，sum(b)为选了负数的数字累加和
则S = sum(a) - sum(b)S=sum(a)−sum(b)
我们两边同时累加sum(a) + sum(b)sum(a)+sum(b)得到：sum(a) + sum(b) + S = sum(a) - sum(b) + sum(a) + sum(b)sum(a)+sum(b)+S=sum(a)−sum(b)+sum(a)+sum(b)
化简得到：S + sum(a) + sum(b) = 2 * sum(a)S+sum(a)+sum(b)=2∗sum(a)
这里的sum(a) + sum(b)sum(a)+sum(b)则是整个数组的和，所以我们得到了一个新的newS：\frac{newS}{2} = sum(a)
​2
​
​newS
​​ =sum(a)
也就是得到了一个新的问题：选出若干数的和为newS有多少种方案，而且这里的每个数都是正数。newS则是S + 所有数字之和，并且注意到newS为偶数时才有解。

http://blog.csdn.net/gqk289/article/details/54709004
内层循环要用next数组保存下一状态，如果都用dp保存的话，下一状态对于当前未遍历到的状态会有污染。
   public int findTargetSumWays(int[] nums, int s) {  
       int sum = 0;   
       for(int i: nums) sum+=i;  
       if(s>sum || s<-sum) return 0;  
       int[] dp = new int[2*sum+1];  
       dp[0+sum] = 1;  
       for(int i = 0; i<nums.length; i++){  
           int[] next = new int[2*sum+1];  
           for(int k = 0; k<2*sum+1; k++){  
               if(dp[k]!=0){  
                   next[k + nums[i]] += dp[k];  
                   next[k - nums[i]] += dp[k];  
               }  
           }  
           dp = next;  
       }  
       return dp[sum+s];  
   }  

X. https://discuss.leetcode.com/topic/76243/java-15-ms-c-3-ms-o-ns-iterative-dp-solution-using-subset-sum-with-explanation
The recursive solution is very slow, because its runtime is exponential
The original problem statement is equivalent to:
Find a subset of nums that need to be positive, and the rest of them negative, such that the sum is equal to target
Let P be the positive subset and N be the negative subset
For example:
Given nums = [1, 2, 3, 4, 5] and target = 3 then one possible solution is +1-2+3-4+5 = 3
Here positive subset is P = [1, 3, 5] and negative subset is N = [2, 4]
Then let's see how this can be converted to a subset sum problem:
                  sum(P) - sum(N) = target
sum(P) + sum(N) + sum(P) - sum(N) = target + sum(P) + sum(N)
                       2 * sum(P) = target + sum(nums)
So the original problem has been converted to a subset sum problem as follows:
Find a subset P of nums such that sum(P) = (target + sum(nums)) / 2
Note that the above formula has proved that target + sum(nums) must be even
We can use that fact to quickly identify inputs that do not have a solution (Thanks to @BrunoDeNadaiSarnaglia for the suggestion)
For detailed explanation on how to solve subset sum problem, you may refer to Partition Equal Subset Sum
    public int findTargetSumWays(int[] nums, int s) {
        int sum = 0;
        for (int n : nums)
            sum += n;
        return sum < s || (s + sum) % 2 > 0 ? 0 : subsetSum(nums, (s + sum) >>> 1); 
    }   

    public int subsetSum(int[] nums, int s) {
        int[] dp = new int[s + 1]; 
        dp[0] = 1;
        for (int n : nums)
            for (int i = s; i >= n; i--)
                dp[i] += dp[i - n]; 
        return dp[s];
    } 
The DP part is almost the same problem as Partition Equal Subset Sum It is also a subset sum problem

X. DFS+Memorization
https://discuss.leetcode.com/topic/76245/java-simple-dfs-with-memorization
I used a map to record the intermediate result while we are walking along the recursion tree.
    public int findTargetSumWays(int[] nums, int S) {
        if (nums == null || nums.length == 0){
            return 0;
        }
        return helper(nums, 0, 0, S, new HashMap<>());
    }
    private int helper(int[] nums, int index, int sum, int S, Map<String, Integer> map){
        String encodeString = index + "->" + sum;
        if (map.containsKey(encodeString)){
            return map.get(encodeString);
        }
        if (index == nums.length){
            if (sum == S){
                return 1;
            }else {
                return 0;
            }
        }
        int curNum = nums[index];
        int add = helper(nums, index + 1, sum - curNum, S, map);
        int minus = helper(nums, index + 1, sum + curNum, S, map);
        map.put(encodeString, add + minus);
        return add + minus;
    }
https://discuss.leetcode.com/topic/76373/brute-force-and-memorization
    int findTargetSumWays(vector<int>& nums, int S) {
        vector<unordered_map<int,int>> mem(nums.size());
        return find(0,nums,S,mem);    
    }
    int find(int p, vector<int>& nums, int sum, vector<unordered_map<int,int>>& mem) {
        if(p==nums.size()) return sum==0;
        auto it = mem[p].find(sum);
        if(it != mem[p].end()) return it->second;
        return mem[p][sum]=find(p+1,nums,sum+nums[p],mem)+find(p+1,nums,sum-=nums[p],mem);
    }

X.  DFS
https://discuss.leetcode.com/topic/76201/java-short-dfs-solution
http://rainykat.blogspot.com/2017/01/leetcodegf-494-target-sum-dfs.html
思路： use dfs to find travel the array, each number have 2 opitons: '+' and '-'
Complexity: O(2^n)
    public int findTargetSumWays(int[] nums, int S) {
        if(nums == null)return 0;
        return dfs(nums, S, 0, 0);
    }
    public int dfs(int[] nums, int S, int index, int sum){
        int res = 0;
        if(index == nums.length){
            if(sum == S) res++;
            return res;
        }
        res += dfs(nums, S, index + 1, sum + nums[index]);
        res += dfs(nums, S, index + 1, sum - nums[index]);
        return res;
    }

If the sum of all elements left is smaller than absolute value of target, there will be no answer following the current path. Thus we can return.
    public int findTargetSumWays(int[] nums, int S) {
        if(nums == null)return 0;
        int n = nums.length;
        int[] sums = new int[n];
        sums[n-1] = nums[n-1];
        for(int i = n-2; i >= 0; i--){
            sums[i] = nums[i] + sums[i+1];
        }
        return dfs(nums, sums, S, 0, 0);
    }
    public int dfs(int[] nums, int[] sums, int S, int index, int sum){
        int res = 0;
        if(index == nums.length){
            if(sum == S) res++;
            return res;
        }
        if(sums[index] < Math.abs(S - sum))return 0;
        res += dfs(nums, sums, S, index + 1, sum + nums[index]);
        res += dfs(nums, sums, S, index + 1, sum - nums[index]);
        return res;
    }
https://discuss.leetcode.com/topic/76361/backtracking-solution-java-easy
    public int findTargetSumWays(int[] nums, int S) {
        int sum = 0;
        int[] arr = new int[1];
        helper(nums, S, arr,0,0);
        return arr[0];
    }
    
    public void helper(int[] nums, int S, int[] arr,int sum, int start){
        if(start==nums.length){
            if(sum == S){
                arr[0]++;
            }
            return;
        }
            //这里千万不要加for循环，因为我们只是从index0开始
            helper(nums,S,arr,sum-nums[start],start+1);
            helper(nums,S,arr,sum+nums[start],start+1);
        
    }

X. https://discuss.leetcode.com/topic/76264/short-java-dp-solution-with-explanation
    public int findTargetSumWays(int[] nums, int s) {
        int sum = 0; 
        for(int i: nums) sum+=i;
        if(s>sum || s<-sum) return 0;
        int[] dp = new int[2*sum+1];
        dp[0+sum] = 1;
        for(int i = 0; i<nums.length; i++){
            int[] next = new int[2*sum+1];
            for(int k = 0; k<2*sum+1; k++){
                if(dp[k]!=0){
                    next[k + nums[i]] += dp[k];
                    next[k - nums[i]] += dp[k];
                }
            }
            dp = next;
        }
        return dp[sum+s];
    }
0_1485048724190_Screen Shot 2017-01-21 at 8.31.48 PM.jpg


You might also like

 * @author het
 *
 */
public class L494 {

}
