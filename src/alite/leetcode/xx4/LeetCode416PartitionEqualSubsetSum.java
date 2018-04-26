package alite.leetcode.xx4;

import java.util.HashMap;
import java.util.Map;

///**
// * https://leetcode.com/problems/partition-equal-subset-sum/
//Related: Partition of a set into K subsets with equal sum
//Given a non-empty array containing only positive integers, find if the array can be partitioned into two subsets such that the sum of elements in both subsets is equal.
//Note:
//Both the array size and each of the array element will not exceed 100.
//Example 1:
//Input: [1, 5, 11, 5]
//
//Output: true
//
//Explanation: The array can be partitioned as [1, 5, 5] and [11].
//Example 2:
//Input: [1, 2, 3, 5]
//
//Output: false
//
//Explanation: The array cannot be partitioned into equal sum subsets.
//
//X. DP
//http://www.cnblogs.com/grandyang/p/5951422.html
//这道题给了我们一个数组，问我们这个数组能不能分成两个非空子集合，使得两个子集合的元素之和相同。那么我们想，原数组所有数字和一定是偶数，不然根本无法拆成两个和相同的子集合，那么我们只需要算出原数组的数字之和，然后除以2，就是我们的target，那么问题就转换为能不能找到一个非空子集合，使得其数字之和为target。开始我想的是遍历所有子集合，算和，但是这种方法无法通过OJ的大数据集合。于是乎，动态规划DP就是我们的不二之选。我们定义一个一维的dp数组，其中dp[i]表示数字i是否是原数组的任意个子集合之和，那么我们我们最后只需要返回dp[target]就行了。我们初始化dp[0]为true，由于题目中限制了所有数字为正数，那么我们就不用担心会出现和为0或者负数的情况。那么关键问题就是要找出递归公式了，我们需要遍历原数组中的数字，对于遍历到的每个数字nums[i]，我们需要更新我们的dp数组，要更新[nums[i], target]之间的值，那么对于这个区间中的任意一个数字j，如果dp[j - nums[j]]为true的话，那么dp[j]就一定为true，于是地推公式如下：
//dp[j] = dp[j] || dp[j - nums[i]]         (nums[i] <= j <= target)
//
//https://discuss.leetcode.com/topic/62307/java-short-dp-solution-with-explanation
//If sum of all the numbers is odd, the surely we cannot reach equal partition.
//Using a boolean dp array (limit its max index to sum/2) whose ith entry indicates there is a way to reach the value i using certain subset of the numbers. SO if at any time we can find a subset to reach sum/2 index, we are able to equally partition.
//Disclaimer: logic borrowed from https://chinmaylokesh.wordpress.com/2011/02/10/balanced-partition-problem-finding-the-minimized-sum-between-two-partitions-of-a-set-of-positive-integers/
//https://discuss.leetcode.com/topic/62312/java-solution-similar-to-backpack-problem-easy-to-understand
//
//Since the problem is a 0-1 backpack problem, we only have two choices which are take or not. Thus in this problem, by using the sum value as the index of DP array, we transfer the problem to "whether should we take the currently visited number into the sum or not".
//To construct the DP recurrence, when we are visiting nums[i] and to find partition of sum j: if we do not take the nums[i], then the current iteration does not make any difference on current DP value; if we take the nums[i], then we need to find whether the (new_sum = j - nums[i]) can be constructed. If any of this two construction can work, the partition of sum == j can be reached.
//
//You can check for success within your elements loop (outer loop) to possibly terminate early.
//Second, you can start your dp loop (inner loop) from a "max" position which is the lesser of the current sum of all elements used so far or the dp length.
//
//    public bool CanPartition(int[] nums) 
//    {
//        int sum = 0;
//        for (int i = 0; i < nums.Length; i++) sum += nums[i];
//        int target = sum / 2;
//        if (target * 2 != sum) return false;
//        
//        bool[] dp = new bool[target + 1];
//        dp[0] = true;
//
//        int currSum = 0;        
//        foreach (int x in nums)
//        {
//            int max = Math.Min(currSum + x, target);
//            for (int j = max; j >= x; j--)
//            {
//                dp[j] = dp[j] || dp[j-x];
//            }
//            
//            if (dp[target] == true) return true;
//            currSum += x;
//        }
//        
//        return dp[target];
//    }
//
//    public boolean canPartition(int[] nums) {
//        // check edge case
//        if (nums == null || nums.length == 0) {
//            return true;
//        }
//        // preprocess
//        int volumn = 0;
//        for (int num : nums) {
//            volumn += num;
//        }
//        if (volumn % 2 != 0) {
//            return false;
//        }
//        volumn /= 2;
//        // dp def
//        boolean[] dp = new boolean[volumn + 1];
//        // dp init
//        dp[0] = true;
//        // dp transition
//        for (int i = 1; i <= nums.length; i++) { // if(nums[i-1] > volumn/2) retu
//            for (int j = volumn; j >= nums[i-1]; j--) {
//                dp[j] = dp[j] || dp[j - nums[i-1]];
//            }
//        }
//        return dp[volumn];
//    }
//https://discuss.leetcode.com/topic/67539/0-1-knapsack-detailed-explanation
//This problem is essentially let us to find whether there are several numbers in a set which are able to sum to a specific value (in this problem, the value is sum/2).
//Actually, this is a 0/1 knapsack problem, for each number, we can pick it or not. Let us assume dp[i][j] means whether the specific sum j can be gotten from the first i numbers. If we can pick such a series of numbers from 0-i whose sum is j, dp[i][j] is true, otherwise it is false.
//Base case: dp[0][0] is true; (zero number consists of sum 0 is true)
//Transition function: For each number, if we don't pick it, dp[i][j] = dp[i-1][j], which means if the first i-1 elements has made it to j, dp[i][j] would also make it to j (we can just ignore nums[i]). If we pick nums[i]. dp[i][j] = dp[i-1][j-nums[i]], which represents that j is composed of the current value nums[i] and the remaining composed of other previous numbers. Thus, the transition function is dp[i][j] = dp[i-1][j] || dp[i-1][j-nums[i]]
//https://discuss.leetcode.com/topic/64499/java-solution-similar-to-subset-sum-problem
//http://leetcodesolution.blogspot.com/2016/10/leetcode-partition-equal-subset-sum.html
//https://discuss.leetcode.com/topic/62913/38ms-dp-java-solution
//    public boolean canPartition(int[] nums) {
//        int n = nums.length;
//        if (n == 0) 
//            return true;
//        int sum = 0;
//        for (int num: nums) {
//            sum += num;
//        }
//        if (sum % 2 == 1)
//            return false;
//        Arrays.sort(nums);
//        int target = sum / 2;
//        boolean[][] dp = new boolean[n + 1][target + 1];
//        dp[0][0] = true;
//        for (int i = 1; i <= n; i++) {
//            if (nums[i-1] == target)
//                return true;
//            if (nums[i-1] > target)
//                return false;
//            System.arraycopy(dp[i-1], 0, dp[i], 0, Math.min(target + 1, nums[i-1]));
//            for (int j = nums[i-1]; j <= target; j++) {
//                dp[i][j] = dp[i-1][j - nums[i-1]];
//            }
//            if (dp[i][target])
//                return true;
//        }
//        return false;
//    }
//https://discuss.leetcode.com/topic/64499/java-solution-similar-to-subset-sum-problem
//It's similar to Subset Sum Problemwhich asks us to find if there is a subset whose sum equals to target value. For this problem, the target value is exactly the half of sum of array.
//    public boolean canPartition(int[] nums) {
//        int sum = 0;
//        for(int num: nums) sum += num;
//        if(sum % 2 == 1) return false;
//        
//        int target = sum / 2;
//        boolean[][] dp = new boolean[nums.length][target + 1];
//        // deal with the first row
//        if(nums[0] <= target) dp[0][nums[0]] = true;
//        
//        // deal with the first col
//        for(int i = 0; i < nums.length; i++) dp[i][0] = true;
//        
//        // deal with the rest
//        for(int i = 1; i < dp.length; i++) {
//            for(int j = 1; j < dp[0].length; j++) {
//                if(j < nums[i]) {
//                    dp[i][j] = dp[i - 1][j];
//                } else {
//                    dp[i][j] = dp[i - 1][j] || dp[i - 1][j - nums[i]];
//                }
//            }
//        }
//        return dp[dp.length - 1][dp[0].length - 1];
//    }
//
//http://bookshadow.com/weblog/2016/10/09/leetcode-partition-equal-subset-sum/
//动态规划（Dynamic Programming）
//利用数组dp[i]记录和为i的子数组是否存在，初始令dp[0] = 1
//for num in nums:
//    for i in range(sum(nums) - num + 1):
//        if dp[i]: dp[i + num] = 1
//    def canPartition(self, nums):
//        """
//        :type nums: List[int]
//        :rtype: bool
//        """
//        sums = sum(nums)
//        if sums & 1: return False
//        nset = set([0])
//        for n in nums:
//            for m in nset.copy():
//                nset.add(m + n)
//        return sums / 2 in nset
//
//X. DP2
//http://www.cnblogs.com/wangxiaobao/p/5943978.html
//用01背包的思路，bool值 dp[i][j] 表示从0,1,2...i选重量为j是否可能。
//则有递推关系：
//dp[i][j] = dp[i - 1][j] || dp[i][j - nums[i]]; （i位置取或者不取）
//结果返回值为dp[nums.size() - 1][sum]; （sum为和的一半）
// 3     bool canPartition(vector<int>& nums) {
// 4         int sum = 0;
// 5         for (int i = 0; i < nums.size(); ++i) {
// 6             sum += nums[i];
// 7         }
// 8         if (sum % 2 == 1) {
// 9             return false;
//10         } 
//11         sum /= 2;
//12         int dp[nums.size()][sum] = {false};
//13         if (nums.size() == 1) {
//14             return false;
//15         }
//16         for (int i = 0; i <= sum; ++i) {
//17             if (nums[0] == i) {
//18                 dp[0][i] = true;
//19             }
//20         }
//21         for (int i = 1; i < nums.size(); ++i) {
//22             for (int j = 1; j <= sum; ++j) {
//23                 dp[i][j] = dp[i - 1][j] || dp[i][j - nums[i]];
//24             }
//25         }
//26         return dp[nums.size() - 1][sum];
//27     }
//
//X. DFS+Cache - TLE
//https://discuss.leetcode.com/topic/62267/java-solution-with-commets-using-dfs
//    public boolean canPartition(int[] nums) {
//        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
//        int sum = 0;
//        for(int i : nums){
//            if(map.containsKey(i)){
//                map.put(i, map.get(i) + 1);
//            }else{
//                map.put(i, 1);
//            }
//            sum += i;
//        }
//        if(sum % 2 == 1) return false;
//        return helper(map, sum / 2);
//    }
//    
//    private boolean helper(Map<Integer, Integer> map, int target){
//        /*target is achieveable*/
//        if(map.containsKey(target) && map.get(target) > 0) return true;
//        /*dfs*/
//        for(int key : map.keySet()){
//            if(key < target && map.get(key) > 0){
//                map.put(key, map.get(key) - 1);
//                if(helper(map, target - key)) return true;
//                map.put(key, map.get(key) + 1);
//            }
//        }
//        return false;
//    }
//}
//https://discuss.leetcode.com/topic/62342/why-dp-is-slower-than-dfs-even-with-no-memoization/
//The N in DFS is the length of the input array, while in DP it is the half of the summation of all elements in the input array. It might be possible that the length of the input array is short, however, the summation is pretty large, in which case DP performs worse than DFS.
//Could be. But then again: both size and elements have the same bound in the problem description (100). That means the maximum possible sum is O(N^2). That makes DP O(N^3), and backtracking is still O(2^N). Even when N is around 20 they should be on about the same level, for larger N backtracking should be prohibitively slow! And there are test cases as large as 50 elements.
//
//X. DFS - Brute Force
//http://blog.csdn.net/mebiuw/article/details/52765840
//老旧的暴力法 仅供参考 不可AC
//    /**
//     * 首先和为奇数的过滤
//     * 其次使用DFS
//     * 排序后可以剪枝很多情况
//     * */
//    public boolean canPartition(int[] nums) {
//        Arrays.sort(nums);
//        int sum=0;
//        for (int num:nums) sum+= num;
//        if(sum % 2 == 1) return false;
//        sum/=2;
//        return dfs(0,sum,nums);
//    }
//    // 一一尝试
//    public boolean dfs(int index,int sum,int[] nums){
//        sum -= nums[index] ;
//        if(sum == 0) return true;
//        for(int i=index+1;i<nums.length;i++){
//            if(sum<nums[i]) break;
//            if(dfs(i,sum,nums)) return true;
//        }
//        return false;
//    }
//http://www.hihuyue.com/hihuyue/codepractise/leetcode/leetcode416-partition-equal-subset-sum
//
//X. BitSet
//https://discuss.leetcode.com/topic/62334/simple-c-4-line-solution-using-a-bitset
//the question description has changed (max array size 100 ==> 200). I have modified my code below according to the new description, and also made it a bit easier to understand.
//Time complexity O(n), size of the bitset is 1256 bytes
//class Solution {
//public:
//    bool canPartition(vector<int>& nums) {
//        const int MAX_NUM = 100;
//        const int MAX_ARRAY_SIZE = 200;
//        bitset<MAX_NUM * MAX_ARRAY_SIZE / 2 + 1> bits(1);
//        int sum = 0;
//        for (auto n : nums) {
//            sum += n;
//            bits |= bits << n;
//        }
//        return !(sum % 2) && bits[sum / 2];
//    }
//};
//It's possible to shorten the solution to 4 lines, by using std::accumulate(), but that doesn't really make you type less or make it run faster though...
//class Solution {
//public:
//    bool canPartition(vector<int>& nums) {
//        bitset<10001> bits(1);
//        int sum = accumulate(nums.begin(), nums.end(), 0);
//        for (auto n : nums) bits |= bits << n;
//        return !(sum & 1) && bits[sum >> 1];
//    }
//};
//http://www.cnblogs.com/grandyang/p/5951422.html
//这道题还可以用bitset来做，感觉也十分的巧妙，bisets的大小设为5001，为啥呢，因为题目中说了数组的长度和每个数字的大小都不会超过100，那么最大的和为10000，那么一半就是5000，前面再加上个0，就是5001了。我们初始化把最低位赋值为1，然后我们算出数组之和，然后我们遍历数字，对于遍历到的数字num，我们把bits向左平移num位，然后再或上原来的bits，这样所有的可能出现的和位置上都为1。举个例子来说吧，比如对于数组[2,3]来说，初始化bits为1，然后对于数字2，bits变为101，我们可以看出来bits[2]标记为了1，然后遍历到3，bits变为了101101，我们看到bits[5],bits[3],bits[2]都分别为1了，正好代表了可能的和2，3，5，这样我们遍历玩整个数组后，去看bits[sum >> 1]是否为1即可
//    bool canPartition(vector<int>& nums) {
//        bitset<5001> bits(1);
//        int sum = 0;
//        for (auto n : nums) {
//            sum += n;
//            bits |= bits << n;
//        }
//        return !(sum & 1) && bits[sum >> 1]; // !(sum % 2) && bits[sum / 2];
//    }
//http://love-oriented.com/pack/P01.html
// * @author het
// *
// */

//Related: Partition of a set into K subsets with equal sum
//Given a non-empty array containing only positive integers, find if the array can be partitioned into two subsets such that the sum of elements in both subsets is equal.
//Note:
//Both the array size and each of the array element will not exceed 100.
//Example 1:
//Input: [1, 5, 11, 5]
//
//Output: true
//
//Explanation: The array can be partitioned as [1, 5, 5] and [11].
//Example 2:
//Input: [1, 2, 3, 5]
//
//Output: false
//
//Explanation: The array cannot be partitioned into equal sum subsets.
//O(n * n * maxElement)
public class LeetCode416PartitionEqualSubsetSum {
    public static boolean hasTwoSubsets(int [] a){ 
    	if(a == null || a.length < 2) return false;
    	if(a.length == 2) return a[0] == a[1];
    	int sum = 0;
    	for(int element: a){
    		sum+=element;
    	}
    	if(sum % 2 !=0 ) return false;
    	return hasSubSet(a, sum/2);
    	
    }
	private static boolean hasSubSet(int[] a, int expected) {
		
		return hasSubSetHelper(a, expected, a.length-1, new HashMap<String, Boolean>());
	}
	public static String createKey(int expected, int endIndex){
		return expected+","+endIndex;
	}
    private static boolean hasSubSetHelper(int[] a, int expected, int endIndex, Map<String, Boolean> cache) {
		String key  = createKey(expected, endIndex);
		if(cache.containsKey(key)){
			return cache.get(key);
		}
		if(endIndex == 0) {
			cache.put(key, expected == 0 || expected== a[0]);
			return cache.get(key);
		}
		boolean result = hasSubSetHelper(a, expected, endIndex-1, cache)|| hasSubSetHelper(a, expected - a[endIndex], endIndex-1, cache);
		cache.put(key, result);
		return result;
	}
    
    
    //、、Partition of a set into K subsets with equal sum
    //
    
    
    
//    Partition of a set into K subsets with equal sum
//    Given an integer array of N elements, the task is to divide this array into K non-empty subsets such that the sum of elements in every subset is same. All elements of this array should be part of exactly one partition.
//    Examples:
//
//    Input : arr = [2, 1, 4, 5, 6], K = 3
//    Output : Yes
//    we can divide above array into 3 parts with equal
//    sum as [[2, 4], [1, 5], [6]]
//
//    Input  : arr = [2, 1, 5, 5, 6], K = 3
//    Output : No
//    It is not possible to divide above array into 3
//    parts with equal sum
//    We strongly recommend that you click here and practice it, before moving on to the solution.
//
//    We can solve this problem recursively, we keep an array for sum of each partition and a boolean array to check whether an element is already taken into some partition or not.
//    First we need to check some base cases,
//    If K is 1, then we already have our answer, complete array is only subset with same sum.
//    If N < K, then it is not possible to divide array into subsets with equal sum, because we can’t divide the array into more than N parts.
//    If sum of array is not divisible by K, then it is not possible to divide the array. We will proceed only if k divides sum. Our goal reduces to divide array into K parts where sum of each part should be array_sum/K
//    In below code a recursive method is written which tries to add array element into some subset. If sum of this subset reaches required sum, we iterate for next part recursively, otherwise we backtrack for different set of elements. If number of subsets whose sum reaches the required sum is (K-1), we flag that it is possible to partition array into K parts with equal sum, because remaining elements already have a sum equal to required sum.
//
//    // C++ program to check whether an array can be
//    // subsetitioned into K subsets of equal sum
//    #include <bits/stdc++.h>
//    using namespace std;
//     
//    // Recursive Utility method to check K equal sum
//    // subsetition of array
//    /**
//        array           - given input array
//        subsetSum array   - sum to store each subset of the array
//        taken           - boolean array to check whether element
//                          is taken into sum subsetition or not
//        K               - number of subsetitions needed
//        N               - total number of element in array
//        curIdx          - current subsetSum index
//        limitIdx        - lastIdx from where array element should
//                          be taken */
//    bool isKPartitionPossibleRec(int arr[], int subsetSum[], bool taken[],
//                       int subset, int K, int N, int curIdx, int limitIdx)
//    {
//        if (subsetSum[curIdx] == subset)
//        {
//            /*  current index (K - 2) represents (K - 1) subsets of equal
//                sum last subsetition will already remain with sum 'subset'*/
//            if (curIdx == K - 2)
//                return true;
//     
//            //  recursive call for next subsetition
//            return isKPartitionPossibleRec(arr, subsetSum, taken, subset,
//                                                K, N, curIdx + 1, N - 1);
//        }
//     
//        //  start from limitIdx and include elements into current subsetition
//        for (int i = limitIdx; i >= 0; i--)
//        {
//            //  if already taken, continue
//            if (taken[i])
//                continue;
//            int tmp = subsetSum[curIdx] + arr[i];
//     
//            // if temp is less than subset then only include the element
//            // and call recursively
//            if (tmp <= subset)
//            {
//                //  mark the element and include into current subsetition sum
//                taken[i] = true;
//                subsetSum[curIdx] += arr[i];
//                bool nxt = isKPartitionPossibleRec(arr, subsetSum, taken,
//                                                subset, K, N, curIdx, i - 1);
//     
//                // after recursive call unmark the element and remove from
//                // subsetition sum
//                taken[i] = false;
//                subsetSum[curIdx] -= arr[i];
//                if (nxt)
//                    return true;
//            }
//        }
//        return false;
//    }
//     
//    //  Method returns true if arr can be subsetitioned into K subsets
//    // with equal sum
//    bool isKPartitionPossible(int arr[], int N, int K)
//    {
//        //  If K is 1, then complete array will be our answer
//        if (K == 1)
//            return true;
//     
//        //  If total number of subsetitions are more than N, then
//        // division is not possible
//        if (N < K)
//            return false;
//     
//        // if array sum is not divisible by K then we can't divide
//        // array into K subsetitions
//        int sum = 0;
//        for (int i = 0; i < N; i++)
//            sum += arr[i];
//        if (sum % K != 0)
//            return false;
//     
//        //  the sum of each subset should be subset (= sum / K)
//        int subset = sum / K;
//        int subsetSum[K];
//        bool taken[N];
//     
//        //  Initialize sum of each subset from 0
//        for (int i = 0; i < K; i++)
//            subsetSum[i] = 0;
//     
//        //  mark all elements as not taken
//        for (int i = 0; i < N; i++)
//            taken[i] = false;
//     
//        // initialize first subsubset sum as last element of
//        // array and mark that as taken
//        subsetSum[0] = arr[N - 1];
//        taken[N - 1] = true;
//        if (subset < subsetSum[0])
//            return false;
//     
//        //  call recursive method to check K-subsetition condition
//        return isKPartitionPossibleRec(arr, subsetSum, taken,
//                                         subset, K, N, 0, N - 1);
//    }
//     
//    //  Driver code to test above methods
//    int main()
//    {
//        int arr[] = {2, 1, 4, 5, 3, 3};
//        int N = sizeof(arr) / sizeof(arr[0]);
//        int K = 3;
//     
//        if (isKPartitionPossible(arr, N, K))
//            cout << "Partitions into equal sum is possible.\n";
//        else
//            cout << "Partitions into equal sum is not possible.\n";
//    }
//    Run on IDE
//    Output:
//
//    Partitions into equal sum is possible.
//    This article is contributed by Utkarsh Trivedi. If you like GeeksforGeeks and would like to contribute, you can also write an article using contribute.geeksforgeeks.org or mail your article to contribute@geeksforgeeks.org. See your article appearing on the GeeksforGeeks main page and help other Geeks.
//
//    Please write comments if you find anything incorrect, or you want to share more information about the topic discussed above.
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(hasTwoSubsets(new int []{1, 5, 11, 5, 2, 7}));
		// [1, 2, 3, 5]

	}
	
	
	//http://www.cnblogs.com/wangxiaobao/p/5943978.html
	//用01背包的思路，bool值 dp[i][j] 表示从0,1,2...i选重量为j是否可能。
	//则有递推关系：
	//dp[i][j] = dp[i - 1][j] || dp[i][j - nums[i]]; （i位置取或者不取）
	//结果返回值为dp[nums.size() - 1][sum]; （sum为和的一半）
	// 3     bool canPartition(vector<int>& nums) {
	// 4         int sum = 0;
	// 5         for (int i = 0; i < nums.size(); ++i) {
	// 6             sum += nums[i];
	// 7         }
	// 8         if (sum % 2 == 1) {
	// 9             return false;
	//10         } 
	//11         sum /= 2;
	//12         int dp[nums.size()][sum] = {false};
	//13         if (nums.size() == 1) {
	//14             return false;
	//15         }
	//16         for (int i = 0; i <= sum; ++i) {
	//17             if (nums[0] == i) {
	//18                 dp[0][i] = true;
	//19             }
	//20         }
	//21         for (int i = 1; i < nums.size(); ++i) {
	//22             for (int j = 1; j <= sum; ++j) {
	//23                 dp[i][j] = dp[i - 1][j] || dp[i][j - nums[i]];
	//24             }
	//25         }
	//26         return dp[nums.size() - 1][sum];
	//27     }
	
	
//  public boolean canPartition(int[] nums) {
//  // check edge case
//  if (nums == null || nums.length == 0) {
//      return true;
//  }
//  // preprocess
//  int volumn = 0;
//  for (int num : nums) {
//      volumn += num;
//  }
//  if (volumn % 2 != 0) {
//      return false;
//  }
//  volumn /= 2;
//  // dp def
//  boolean[] dp = new boolean[volumn + 1];
//  // dp init
//  dp[0] = true;
//  // dp transition
//  for (int i = 1; i <= nums.length; i++) { // if(nums[i-1] > volumn/2) retu
//      for (int j = volumn; j >= nums[i-1]; j--) {
//          dp[j] = dp[j] || dp[j - nums[i-1]];
//      }
//  }
//  return dp[volumn];
//}

}
