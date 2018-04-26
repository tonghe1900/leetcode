package alite.leetcode.xx3.extra;
/**
 * LeetCode 377 - Combination Sum IV

https://www.hrwhisper.me/leetcode-combination-sum-iv/
Given an integer array with all positive numbers and no duplicates, find the number of possible 
combinations that add up to a positive integer target.
Example:
nums = [1, 2, 3]
target = 4
numSum(-1, 0) = 1
numSum(i, target) = numSum(i-1, target) + numSum(i-1, target- nums[i])

The possible combination ways are:
(1, 1, 1, 1)
(1, 1, 2)
(1, 2, 1)
(1, 3)
(2, 1, 1)
(2, 2)
(3, 1)

Note that different sequences are counted as different combinations.

Therefore the output is 7.
Follow up:
What if negative numbers are allowed in the given array?
How does it change the problem?
What limitation we need to add to the question to allow negative numbers?
https://discuss.leetcode.com/topic/52186/my-3ms-java-dp-solution
https://discuss.leetcode.com/topic/52302/1ms-java-dp-solution-with-detailed-explanation/
Think about the recurrence relation first. How does the # of combinations of the target related to the
 # of combinations of numbers that are smaller than the target?
So we know that target is the sum of numbers in the array. Imagine we only need one more 
number to reach target, this number can be any one in the array, right? So the # of combinations of target, 
comb[target] = sum(comb[target - nums[i]]), where 0 <= i < nums.length, and target >= nums[i].
In the example given, we can actually find the # of combinations of 4 with the # of combinations of 3(4 - 1), 2(4- 2) and 1(4 - 3). As a result, comb[4] = comb[4-1] + comb[4-2] + comb[4-3] = comb[3] + comb[2] + comb[1].
Then think about the base case. Since if the target is 0, there is only one way to get zero, which is using 0, we can set comb[0] = 1.
EDIT: The problem says that target is a positive integer that makes me feel it's unclear to put it in the above way. Since target == 0only happens when in the previous call, target = nums[i], we know that this is the only combination in this case, so we return 1.
Now we can come up with at least a recursive solution.
public int combinationSum4(int[] nums, int target) {
    if (target == 0) {
        return 1;
    }
    int res = 0;
    for (int i = 0; i < nums.length; i++) {
        if (target >= nums[i]) {
            res += combinationSum4(nums, target - nums[i]);
        }
    }
    return res;
}
Now for a DP solution, we just need to figure out a way to store the intermediate results, to avoid the same combination sum being calculated many times. We can use an array to save those results, and check if there is already a result before calculation. We can fill the array with -1 to indicate that the result hasn't been calculated yet. 0 is not a good choice because it means there is no combination sum for the target.
private int[] dp;

public int combinationSum4(int[] nums, int target) {
    dp = new int[target + 1];
    Arrays.fill(dp, -1);
    dp[0] = 1;
    return helper(nums, target);
}

private int helper(int[] nums, int target) {
    if (dp[target] != -1) {
        return dp[target];
    }
    int res = 0;
    for (int i = 0; i < nums.length; i++) {
        if (target >= nums[i]) {
            res += helper(nums, target - nums[i]);
        }
    }
    dp[target] = res;
    return res;
}
EDIT: The above solution is top-down. How about a bottom-up one?
public int combinationSum4(int[] nums, int target) {
    int[] comb = new int[target + 1];
    comb[0] = 1;
    for (int i = 1; i < comb.length; i++) {
        for (int j = 0; j < nums.length; j++) {
            if (i - nums[j] >= 0) {
                comb[i] += comb[i - nums[j]];
            }
        }
    }
    return comb[target];
}
One minor improvement I can think of is to use res[0]. Assume empty list is putting zero elements in a list. So there is 1 way to create empty list. This will help remove the check if n is equal to i or not.
public int combinationSum4(int[] nums, int target) {
    Arrays.sort(nums);
    int[] res = new int[target+1];
    res[0]=1; // 1 way to create empty combination.
    for(int i=0 ;i<=target; i++)
    {
        for(int n : nums)
        {
            if(n>i) break;
            res[i]+=res[i-n];
        }
    }
    return res[target];
}

X. Memory search: Top Down
good solution, but some cases such as
[100000000,200000000,300000000]
1400000000
will get Memory Limit Exceeded due to you assign the length of "res" array as target+1,
to avoid that we could use Memory optimization search
    HashMap<Integer, Integer> hashmap;
 
 public int combinationSum4(int[] nums, int target)
 {
  int len=nums.length;
  Arrays.sort(nums);
  hashmap=new HashMap<>(len);
  search(nums, target);
  return hashmap.get(target);
 }
 
 public int search(int[] nums,int target)
 {
  if(hashmap.containsKey(target))
   return hashmap.get(target);
  
  int index=Arrays.binarySearch(nums, target);
  boolean exist=index>=0;
  if(index<0)
   index=-(index+1);
  
  int sum=(exist?1:0);
  
  for(int i=index-1;i>=0;i--)
  {
   int dis=target-nums[i];
   sum+=search(nums, dis);
  }
  
  hashmap.put(target, sum);
  return sum;
 }

https://discuss.leetcode.com/topic/52255/java-recursion-solution-using-hashmap-as-memory
    Map<Integer, Integer> map = new HashMap<>();
    public int combinationSum4(int[] nums, int target) {
        int count = 0;
        if (nums == null || nums.length ==0 || target < 0 ) return 0;
        if ( target ==0 ) return 1;
        if (map.containsKey(target)) return map.get(target);
        for (int num: nums){
            count += combinationSum4(nums, target-num);
        }
        map.put(target, count);
        return count;
    }

X. Follow up
https://discuss.leetcode.com/topic/53553/what-if-negative-numbers-are-allowed-in-the-given-array
The problem with negative numbers is that now the combinations could be potentially of infinite length. 
Think about nums = [-1, 1]and target = 1. We can have all sequences of arbitrary length that 
follow the patterns -1, 1, -1, 1, ..., -1, 1, 1 and 1, -1, 1, -1, ..., 1, -1, 1 (there are also others,
 of course, just to give an example). So we should limit the length of the combination sequence,
  so as to give a bound to the problem.
https://discuss.leetcode.com/topic/52290/java-follow-up-using-recursion-and-memorization
In order to allow negative integers, the length of the combination sum needs to be restricted, 
or the search will not stop. This is a modification from my previous solution, which also use 
memory to avoid repeated calculations.
Map<Integer, Map<Integer,Integer>> map2 = new HashMap<>();
    private int helper2(int[] nums, int len, int target, int MaxLen) {
     int count = 0;
        if (  len > MaxLen  ) return 0;
        if ( map2.containsKey(target) && map2.get(target).containsKey(len)) { 
         return map2.get(target).get(len);
        }
        if ( target == 0 )   count++;
        for (int num: nums) {
            count+= helper2(nums, len+1, target-num, MaxLen);
        }
        if ( ! map2.containsKey(target) ) map2.put(target, new HashMap<Integer,Integer>());
        Map<Integer,Integer> mem = map2.get(target);
        mem.put(len, count);
        return count;
    }
       
    public int combinationSum42(int[] nums, int target, int MaxLen) {
        if (nums == null || nums.length ==0 || MaxLen <= 0 ) return 0;
        map2 = new HashMap<>();
        return helper2(nums, 0,target, MaxLen);
    }

题意：给定一个元素互不相同且均为正数的数组，让你用该数组中的数组合成target（可以重复使用），问有多少种。
思路：DP可以有两种
dp[i] += dp[i-num]
dp[i+num] += dp[i]

    public int combinationSum4(int[] nums, int target) {

        int[] dp= new int[target+1];

        dp[0] = 1;

        for(int i = 1; i <= target;i++){

            for(int num:nums){

                if(i >= num) dp[i] += dp[i - num];

            }

        }

        return dp[target];

    }


    public int combinationSum4(int[] nums, int target) {

        int[] dp= new int[target+1];

        dp[0] = 1;

        for(int i = 0; i < target;i++){

            for(int num:nums){

                if(i + num <= target) dp[i + num] += dp[i];

            }

        }

        return dp[target];

    }
http://blog.csdn.net/yeqiuzs/article/details/52029034
public int combinationSum4(int[] nums, int target) {  
    int dp[] = new int[target + 1];  
    for (int i = 0; i <= target; i++) {  
        for (int k = 0; k < nums.length; k++) {  
            if (i - nums[k] > 0) {  
                dp[i] += dp[i - nums[k]];  
            } else if (i - nums[k] == 0) {  
                dp[i] += 1;  
            }  
  
        }  
    }  
    return dp[target];  
}  
http://www.cnblogs.com/grandyang/p/5705750.html
如果target远大于nums数组的个数的话，上面的算法可以做适当的优化，先给nums数组排个序，然后从1遍历到target，对于i小于数组中的数字x时，我们直接break掉，因为后面的数更大，其余地方不变
https://discuss.leetcode.com/topic/52186/my-3ms-java-dp-solution/5
Because in the loop for (int num: nums) {} we have a break;. Actually if the nums is not sorted, we could continuewhen num > i, the code could still pass the tests.
    public int combinationSum4(int[] nums, int target) {
        Arrays.sort(nums);
        int[] res = new int[target + 1];
        for (int i = 1; i < res.length; i++) {
     for (int num : nums) {
         if (num > i)
      break;
  else if (num == i)
      res[i] += 1;
  else
      res[i] += res[i-num];
     }
 }
        return res[target];
    }
    int combinationSum4(vector<int>& nums, int target) {
        vector<int> dp(target + 1);
        dp[0] = 1;
        sort(nums.begin(), nums.end());
        for (int i = 1; i <= target; ++i) {
            for (auto a : nums) {
                if (i < a) break;
                dp[i] += dp[i - a];
            }
        }
        return dp.back();
    }
https://discuss.leetcode.com/topic/52302/1ms-java-dp-solution-with-detailed-explanation
private int[] dp;

public int combinationSum4(int[] nums, int target) {
    dp = new int[target + 1];
    Arrays.fill(dp, -1);
    dp[0] = 1;
    return helper(nums, target);
}

private int helper(int[] nums, int target) {
    if (dp[target] != -1) {
        return dp[target];
    }
    int res = 0;
    for (int i = 0; i < nums.length; i++) {
        if (target >= nums[i]) {
            res += helper(nums, target - nums[i]);
        }
    }
    dp[target] = res;
    return res;
}

public int combinationSum4() {
    if (target == 0) {
        return 1;
    }
    int res = 0;
    for (int i = 0; i < nums.length; i++) {
        if (target >= nums[i]) {
            res += combinationSum4(nums, target - nums[i]);
        }
    }
    return res;
}
Why not removing that if statement inside the for loop and substitute with a if (target <= 0) return not target; base case? The code would be clearer and shorter.
Follow up:
有负数的情况可能存在无限符合要求的解。(比如你想想有个-1的，我正数随便加，然后再减回去)
所以必须要有限制条件，限制条件有很多啊 比如要求解的长度为L，或者不超过L，或者每个数只能使用X次等等
 * @author het
 *
 */
public class LeetCode377 {
	Map<Integer, Map<Integer,Integer>> map2 = new HashMap<>();
    private int helper2(int[] nums, int len, int target, int MaxLen) {
     int count = 0;
        if (  len > MaxLen  ) return 0;
        if ( map2.containsKey(target) && map2.get(target).containsKey(len)) { 
         return map2.get(target).get(len);
        }
        if ( target == 0 )   count++;
        for (int num: nums) {
            count+= helper2(nums, len+1, target-num, MaxLen);
        }
        if ( ! map2.containsKey(target) ) map2.put(target, new HashMap<Integer,Integer>());
        Map<Integer,Integer> mem = map2.get(target);
        mem.put(len, count);
        return count;
    }
       
    public int combinationSum42(int[] nums, int target, int MaxLen) {
        if (nums == null || nums.length ==0 || MaxLen <= 0 ) return 0;
        map2 = new HashMap<>();
        return helper2(nums, 0,target, MaxLen);
    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
