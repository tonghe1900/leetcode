package alite.leetcode.newExtra.L500;
/**
 * https://leetcode.com/problems/k-diff-pairs-in-an-array/
Given an array of integers and an integer k, you need to find the number of unique k-diff pairs in the array. 
Here a k-diff pair is defined as an integer pair (i, j), where i and j are both numbers in the array and their 
absolute difference is k.
Example 1:
Input: [3, 1, 4, 1, 5], k = 2
Output: 2
Explanation: There are two 2-diff pairs in the array, (1, 3) and (3, 5).
Although we have two 1s in the input, we should only return the number of unique pairs.
Example 2:
Input:[1, 2, 3, 4, 5], k = 1
Output: 4
Explanation: There are four 1-diff pairs in the array, (1, 2), (2, 3), (3, 4) and (4, 5).
Example 3:
Input: [1, 3, 1, 5, 4], k = 0
Output: 1
Explanation: There is one 0-diff pair in the array, (1, 1).
Note:
The pairs (i, j) and (j, i) count as the same pair.
The length of the array won't exceed 10,000.
All the integers in the given input belong to the range: [-1e7, 1e7].
https://discuss.leetcode.com/topic/81714/java-o-n-solution-one-hashmap-easy-to-understand
将数组值用hashmap来存起来，value来存储个数。如果k=0的话，value>=2的时候count+1，如果k不等于0，则看key+k的值在map里面是否包含，包含的话就count+1
    public int findPairs(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k < 0)   return 0;
        
        Map<Integer, Integer> map = new HashMap<>();
        int count = 0;
        for (int i : nums) {
            map.put(i, map.getOrDefault(i, 0) + 1);
        }
        
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (k == 0) {
                //count how many elements in the array that appear more than twice.
                if (entry.getValue() >= 2) {
                    count++;
                } 
            } else {
                if (map.containsKey(entry.getKey() + k)) {
                    count++;
                }
            }
        }
        
        return count;
    }
https://discuss.leetcode.com/topic/81714/java-o-n-solution-one-hashmap-easy-to-understand/7
    public int findPairs(int[] nums, int k) {
        Map<Integer,Integer> mp = new HashMap<Integer,Integer>();
        if(k<0)
            return 0;
            
        for(int i=0;i<nums.length;i++) {
            mp.put(nums[i],i);
        }
        
        int cnt=0;
        for(int i=0;i<nums.length;i++) {
            if(mp.containsKey(k+nums[i]) && mp.get(k+nums[i])!=i) {
                cnt++;
                mp.remove(k+nums[i]);
            }
        }
        return cnt;
    }
X. https://discuss.leetcode.com/topic/81745/two-pointer-approach
   public int findPairs(int[] nums, int k) {
        Arrays.sort(nums);

        int start = 0, end = 1, result = 0;
        while (start < nums.length && end < nums.length) {
            if (start == end || nums[start] + k > nums[end]) {
                end++;
            } else if (nums[start] + k < nums[end]) {
                start++;
            } else {
                start++;
                result++;
                // start
                //  |
                // [1, 1, ...., 8, 8]
                //              |
                //             end
                while (start < nums.length && nums[start] == nums[start - 1]) start++;
                end = Math.max(end + 1, start + 1);
            }
        }
        return result;
    }
The problem is just a variant of 2-sum.
Update: Fixed a bug that can cause integer subtraction overflow.
Update: The code runs in O(n log n) time, using O(1) space.
public int findPairs(int[] nums, int k) {
    int ans = 0;
    Arrays.sort(nums);
    for (int i = 0, j = 0; i < nums.length; i++) {
        for (j = Math.max(j, i + 1); j < nums.length && (long) nums[j] - nums[i] < k; j++) ;
        if (j < nums.length && (long) nums[j] - nums[i] == k) ans++;
        while (i + 1 < nums.length && nums[i] == nums[i + 1]) i++;
    }
    return ans;
}
https://discuss.leetcode.com/topic/81678/self-explained-ac-java-sliding-window
 public  int findPairs(int[] nums, int k) {
 if(k<0 || nums.length<=1){
     return 0;
 }
   
         Arrays.sort(nums);
         int count = 0;
         int left = 0;
         int right = 1;
         
         while(right<nums.length){
             int firNum = nums[left];
             int secNum = nums[right];
             // If less than k, increase the right index
             if(secNum-firNum<k){
                 right++;
             }
             // If larger than k, increase the left index
             else if(secNum - firNum>k){
                 left++;   
             }
             // If equal, move left and right to next different number
             else{
                 count++;
                 while(left<nums.length && nums[left]==firNum){
                     left++;
                 }
                 while(right<nums.length && nums[right]==secNum){
                     right++;
                 }
                             
             }
             //left and right should not be the same number
             if(right==left){
              right++;
             }
         }
        return count;
    }
https://discuss.leetcode.com/topic/81897/simple-idea-o-nlogn-time-o-1-space-java-solution
    public int findPairs(int[] nums, int k) {
        if (nums == null || nums.length < 2) return 0;
        int res = 0;
        Arrays.sort(nums);
        for (int i = 0; i < nums.length; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) continue;
            if (helper(nums, i + 1, nums[i] + k)) res++;
        }
        return res;
    }
    private boolean helper(int[] nums, int l, int target) {
        int r = nums.length - 1;
        while (l <= r) {
            int mid = l + (r - l) / 2;
            if (nums[mid] == target) return true;
            if (nums[mid] < target) l = mid + 1;
            else r = mid - 1;
        }
        return false;
    }
X. Using hashset
https://discuss.leetcode.com/topic/81690/short-java-solution-but-two-hashsets
    public int findPairs(int[] nums, int k) {
        Arrays.sort(nums);
        Set<Integer> seenNum = new HashSet<>();
        Set<String> seenPair = new HashSet<>();
        int result = 0;
        
        for (int i = 0; i < nums.length; i++) {
            int prev = nums[i] - k;
            if (seenNum.contains(prev) && !seenPair.contains(prev + "," + nums[i])) {
                result++;
                seenPair.add(prev + "," + nums[i]);
            }
            seenNum.add(nums[i]);
        }
        return result;
    }
http://blog.csdn.net/zhouziyu2011/article/details/60466898
成对的值不分先后，所以先对nums进行排序。
用一个set存储出现过的值，用于后续判断是否某个值已经有值与其成对。
分为两种情况：
（1）k==0，即找出值相等的对数。
再用一个sameSet存储所有已成对的值，避免同一个值加入结果多次。只有sameSet中不含该值，且set中包含了该值，才能加入结果。
（2）k!=0，即找出差的绝对值为k的对数。
只有set中不包含该值但包含了该值-k，才能加入结果。

    public int findPairs(int[] nums, int k) {  
        int len = nums.length, result = 0;  
        Arrays.sort(nums);  
        Set<Integer> set = new HashSet<Integer>();  
        Set<Integer> sameSet = new HashSet<Integer>();  
        if (k != 0) {  
            for (int i = 0; i < len; i++) {  
                if (!set.contains(nums[i]) && set.contains(nums[i] - k))   
                    result++;  
                set.add(nums[i]);  
            }  
        }  
        else {  
            for (int i = 0; i < len; i++) {  
                if (!sameSet.contains(nums[i]) && set.contains(nums[i])) {  
                    result++;  
                    sameSet.add(nums[i]);  
                }  
                set.add(nums[i]);  
            }   
        }  
        return result;  
    } 
 * @author het
 *
 */
public class LeetCode532_K_diff_Pairs_in_an_Array {
	public int findPairs(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k < 0)   return 0;
        
        Map<Integer, Integer> map = new HashMap<>();
        int count = 0;
        for (int i : nums) {
            map.put(i, map.getOrDefault(i, 0) + 1);
        }
        
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (k == 0) {
                //count how many elements in the array that appear more than twice.
                if (entry.getValue() >= 2) {
                    count++;
                } 
            } else {
                if (map.containsKey(entry.getKey() + k)) {
                    count++;
                }
            }
        }
        
        return count;
    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
