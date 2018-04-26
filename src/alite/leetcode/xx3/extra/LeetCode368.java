package alite.leetcode.xx3.extra;
/**
 * LeetCode 368 - Largest Divisible Subset

https://leetcode.com/problems/largest-divisible-subset/
Given a set of distinct positive integers, find the largest subset such that every pair (Si, Sj) of elements
 in this subset satisfies: Si % Sj = 0 or Sj % Si = 0.
If there are multiple solutions, return any subset is fine.
Example 1:
nums: [1,2,3]

Result: [1,2] (of course, [1,3] will also be ok)
Example 2:


nums: [1,2,4,8]

Result: [1,2,4,8]
https://www.hrwhisper.me/leetcode-largest-divisible-subset/
https://segmentfault.com/a/1190000005922634
和LIS很相似，dp[i]表示nums数组从0到i的最大集合的size.
这题应该分成两个问题：
得到最大集合size
输出这个集合
对于第一个问题，最大集合size就是dp数组的最大值，可以边画表边维护一个当前最大值;
对于第二个问题，我们要维护一个parent数组，记录nums[i]加入到了哪个集合;
dp[i] = max(dp[i], dp[j] + 1), where 0<=j<i
注意
注意这个case：
[1,2,4,8,9,72]
到72的时候，往前找到9，可以整除，更新dp[5]为max(1, 2 + 1) = 3,
注意此时应该继续往前找，不能停，直到找到8,发现dp[3] + 1 = 5 > 3，于是更新dp[i]
注意就是不能停，找到一个能整除并不够，前面有可能有更大的啊~~
http://www.bingfengsa.com/article/7kLzV8O
题意：给定一个数组，求其中的一个最大子集，要求该子集中任意的两个元素满足 x % y ==0 或者 y % x==0
思路：其实和求最大上升子序列LIS差不多，只不过这题要求输出序列而已。
先把数组排好序。首先要明确，若a<b且b%a==0 ,  b <c 且 c%b==0那么必然有c%a==0
我们设dp[i] 为最大的子集长度，更新的时候保存上一个的下标即可。

    public List<Integer> largestDivisibleSubset(int[] nums) {

        List<Integer> ans = new ArrayList<Integer>();

        if (nums.length == 0) return ans;

        Arrays.sort(nums);

        int n = nums.length;

        int[] dp = new int[n], index = new int[n];

        Arrays.fill(dp, 1);

        Arrays.fill(index, -1);

        int max_index = 0, max_dp = 1;

        for (int i = 0; i < n; i++) {

            for (int j = 0; j < i; j++) {

                if (nums[i] % nums[j] == 0 && dp[j] + 1 > dp[i]) {

                    dp[i] = dp[j] + 1;

                    index[i] = j;

                }

            }

            if (max_dp < dp[i]) {

                max_dp = dp[i];

                max_index = i;

            }

        }

        for (int i = max_index; i != -1; i = index[i])

            ans.add(nums[i]);

        return ans;

    }

http://blog.csdn.net/qq508618087/article/details/51767785
思路: 可以用动态规划来解决. 为了使得问题可以转化为子问题, 最好将数组按照降序来排列, 然后当nums[j]%nums[i]==0的时候就可以得到一个状态转移方程dp[i] = max(dp[i], dp[j]+1), 因为数组按照降序排序, 所以nums[i] < nums[j],并且之前能够被nums[j]整除的数, 也必然能够别nums[i]整除, 这就保证了状态转移方程的正确性. 
他还要求找出最大结果, 所以我们还需要记录一下路径, 每一个数字, 我们记录一个第一个能够使其到达最大长度的父结点, 最后回溯一下即可.

https://leetcode.com/discuss/111006/java-dp-solution-with-explanation
The idea is to keep track of the next element in the array that gives the longest streak for each element in the array, then in the end, we find the start of the maximal streak, then start from there and find the rest of elements.
nextIdx is an array keeping track of the next element indexed j for each element at i that gives the longest streak, and maxLength keeps track of the current max length for each element. The core algorithm is: for each element at i, we go from n-1 to i+1 to find the best j, and we link i to jby specifying that nextIdx[i] = j.
After we have iterated every element, we start from the maxIdx, which points to the first element in the longest streak. We add that element to our result, then go to the next element according tonextIdx until we reach the last element in that streak, who will have value 0 in its nextIdx slot.
  public List<Integer> largestDivisibleSubset(int[] nums) {
    if (nums.length == 0) {
      return new ArrayList<Integer>();
    }
    int[] nextIdx = new int[nums.length];
    int[] maxLength = new int[nums.length];
    int max = 0, maxIdx = 0;
    Arrays.sort(nums);
    for (int i = nums.length - 1; i >= 0; i--) {
      maxLength[i] = 1;
      for (int j = nums.length - 1; j > i; j--) {
        if (nums[j] % nums[i] == 0) {
          if (maxLength[j] + 1 > maxLength[i]) {
            maxLength[i] = maxLength[j] + 1;
            nextIdx[i] = j;
            if (maxLength[i] > max) {
              maxIdx = i;
            }
          }
        }
      }
    }
    List<Integer> res = new ArrayList<Integer>();
    res.add(nums[maxIdx]);
    int idx = nextIdx[maxIdx];
    while (idx != 0) {
      res.add(nums[idx]);
      idx = nextIdx[idx];
    }
    return res;
  }
https://leetcode.com/discuss/110852/java-dp-o-n-2-time-o-n-space-with-comments
utilize the transitivity of divisibility, kind of like " Longest Increasing Subsequence" problem However this one requires you to return the actual subset rather than cardinality of the set, so you need to do some bookkeeping by using another array.
https://leetcode.com/discuss/110927/concise-java-solution-using-dp-hashmap
    public List<Integer> largestDivisibleSubset(int[] nums) {
        if(nums.length == 0) return (new ArrayList<Integer>());
        Arrays.sort(nums);
        Map<Integer, Integer> map = new HashMap<>();
        int[] dp = new int[nums.length], dp[0] = 1;
        int maxV = 1, index = 0;
        for (int i = 1; i < nums.length; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] % nums[j] == 0) {
                    if (dp[j] + 1 > dp[i]) {
                        dp[i] = dp[j] + 1;
                        map.put(nums[i], nums[j]);  // backward index
                    }
                    if (dp[i] > maxV) {
                        maxV = dp[i];
                        index = i;
                    }
                } 
            }
        }
        List<Integer> ret = new ArrayList<Integer>();
        int key = nums[index];
        ret.add(key);
        while (map.containsKey(key)) {
            ret.add(map.get(key));
            key = map.get(key);
        }
        return ret;        
    }
http://bookshadow.com/weblog/2016/06/27/leetcode-largest-divisible-subset/
    def largestDivisibleSubset(self, nums):
        """
        :type nums: List[int]
        :rtype: List[int]
        """
        nums = sorted(nums)
        size = len(nums)
        dp = [1] * size
        pre = [None] * size
        for x in range(size):
            for y in range(x):
                if nums[x] % nums[y] == 0 and dp[y] + 1 > dp[x]:
                    dp[x] = dp[y] + 1
                    pre[x] = y
        idx = dp.index(max(dp))
        ans = []
        while idx is not None:
            ans += nums[idx],
            idx = pre[idx]
        return ans

    def largestDivisibleSubset(self, nums):
        """
        :type nums: List[int]
        :rtype: List[int]
        """
        S = {-1: set()}
        for x in sorted(nums):
            S[x] = max((S[d] for d in S if x % d == 0), key=len) | {x}
        return list(max(S.values(), key=len))

http://www.cnblogs.com/grandyang/p/5625209.html
不过dp数组现在每一项保存一个pair，相当于上面解法中的dp和parent数组揉到一起表示了，然后的不同就是下面的方法是从前往后遍历的，每个数字又要遍历到开头
    vector<int> largestDivisibleSubset(vector<int>& nums) {
        sort(nums.begin(), nums.end());
        vector<int> res;
        vector<pair<int, int>> dp(nums.size());
        int mx = 0, mx_idx = 0;
        for (int i = 0; i < nums.size(); ++i) {
            for (int j = i; j >= 0; --j) {
                if (nums[i] % nums[j] == 0 && dp[i].first < dp[j].first + 1) {
                    dp[i].first = dp[j].first + 1;
                    dp[i].second = j;
                    if (mx < dp[i].first) {
                        mx = dp[i].first;
                        mx_idx = i;
                    }
                }
            }
        }
        for (int i = 0; i < mx; ++i) {
            res.push_back(nums[mx_idx]);
            mx_idx = dp[mx_idx].second;
        }
        return res;
    }


https://discuss.leetcode.com/topic/49455/4-lines-in-python
def largestDivisibleSubset(self, nums):
    S = {-1: set()}
    for x in sorted(nums):
        S[x] = max((S[d] for d in S if x % d == 0), key=len) | {x}
    return list(max(S.values(), key=len))
My S[x] is the largest subset with x as the largest element, i.e., the subset of all divisors of x in the input. With S[-1] = emptysetas useful base case. Since divisibility is transitive, a multiple x of some divisor d is also a multiple of all elements in S[d], so it's not necessary to explicitly test divisibility of x by all elements in S[d]. Testing x % d suffices.
While storing entire subsets isn't super efficient, it's also not that bad. To extend a subset, the new element must be divisible by all elements in it, meaning it must be at least twice as large as the largest element in it. So with the 31-bit integers we have here, the largest possible set has size 31 (containing all powers of 2).
http://dartmooryao.blogspot.com/2016/06/leetcode-368-largest-divisible-subset.html
    public List<Integer> largestDivisibleSubset(int[] nums) {
        if(nums.length==0){ return new ArrayList<>(); }
        Map<Integer, Integer> map = new HashMap<>();
        Arrays.sort(nums);
        int[] count = new int[nums.length];
        Arrays.fill(count, 1);
        int maxLen = 0;
        int maxIdx = 0;
        for(int i=nums.length-1; i>=0; i--){
            for(int j=i+1; j<nums.length; j++){
                if(nums[j]%nums[i]==0){
                    if(count[i] < count[j]+1){
                        count[i] = count[j]+1;
                        map.put(i, j);
                    }
                }
            }
            if(count[i] > maxLen){
                maxLen = count[i];
                maxIdx = i;
            }
        }
      
        List<Integer> result = new ArrayList<>();
        Integer idx = maxIdx;
        while(idx != null){
            result.add(nums[idx]);
            idx = map.get(idx);
        }
        return result;
    }

If we need result sorted:
https://louisyw1.gitbooks.io/leetcode/content/dynamic_programming/largest_divisible_subset.html
        for(int i = 0; i < largest; i++){
            res.insert(res.begin(), nums[last]);
            last = son[last];
        }
        for (int i = maxIdx; i >= 0;) { //回溯解集
            res.addFirst(nums[i]);
            i = pre[i];
        }

倒过来求这样不用在结果前面插入，从后面开始计算
+
    vector<int> largestDivisibleSubset(vector<int>& nums) {
        sort(nums.begin(), nums.end());

        vector<int> dp(nums.size(), 0);
        vector<int> parent(nums.size(), 0);


        int largest = 0;
        int start = 0;
        for(int i = nums.size() - 1; i >= 0; --i){
            //此处j=i对应着上面将dp初始化为0；从后往前迭加，[1,2,4,5,8],效果为[8]->[4, 8]->[2, 4, 8]->[1,2,4,8]
            for(int j = i; j < nums.size(); j++){
                if(nums[j] % nums[i] == 0 && dp[i] < dp[j] + 1){
                    dp[i] = dp[j] + 1;
                    parent[i] = j;
                }

                if(dp[i] > largest){
                    largest = dp[i];
                    start = i;
                }
            }

        }

        vector<int> res;

        for(int i = 0; i < largest; i++){
            res.push_back(nums[start]);
            start = parent[start];
        }
        return res;
    }
https://discuss.leetcode.com/topic/49563/using-hashmap-o-n-2-time-o-n-space
 * @author het
 *
 */

//和LIS很相似，dp[i]表示nums数组从0到i的最大集合的size.
//这题应该分成两个问题：
//得到最大集合size
//输出这个集合
//对于第一个问题，最大集合size就是dp数组的最大值，可以边画表边维护一个当前最大值;
//对于第二个问题，我们要维护一个parent数组，记录nums[i]加入到了哪个集合;
//dp[i] = max(dp[i], dp[j] + 1), where 0<=j<i
//注意
//注意这个case：
//[1,2,4,8,9,72]
//到72的时候，往前找到9，可以整除，更新dp[5]为max(1, 2 + 1) = 3,
//注意此时应该继续往前找，不能停，直到找到8,发现dp[3] + 1 = 5 > 3，于是更新dp[i]
//注意就是不能停，找到一个能整除并不够，前面有可能有更大的啊~~
//http://www.bingfengsa.com/article/7kLzV8O
//题意：给定一个数组，求其中的一个最大子集，要求该子集中任意的两个元素满足 x % y ==0 或者 y % x==0
//思路：其实和求最大上升子序列LIS差不多，只不过这题要求输出序列而已。
//先把数组排好序。首先要明确，若a<b且b%a==0 ,  b <c 且 c%b==0那么必然有c%a==0
//我们设dp[i] 为最大的子集长度，更新的时候保存上一个的下标即可。
public class LeetCode368 {
	public List<Integer> largestDivisibleSubset(int[] nums) {
	    if (nums.length == 0) {
	      return new ArrayList<Integer>();
	    }
	    int[] nextIdx = new int[nums.length];
	    int[] maxLength = new int[nums.length];
	    int max = 0, maxIdx = 0;
	    Arrays.sort(nums);
	    for (int i = nums.length - 1; i >= 0; i--) {
	      maxLength[i] = 1;
	      for (int j = nums.length - 1; j > i; j--) {
	        if (nums[j] % nums[i] == 0) {
	          if (maxLength[j] + 1 > maxLength[i]) {
	            maxLength[i] = maxLength[j] + 1;
	            nextIdx[i] = j;
	            if (maxLength[i] > max) {
	              maxIdx = i;
	            }
	          }
	        }
	      }
	    }
	    List<Integer> res = new ArrayList<Integer>();
	    res.add(nums[maxIdx]);
	    int idx = nextIdx[maxIdx];
	    while (idx != 0) {
	      res.add(nums[idx]);
	      idx = nextIdx[idx];
	    }
	    return res;
	  }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
