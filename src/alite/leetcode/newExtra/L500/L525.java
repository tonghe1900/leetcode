package alite.leetcode.newExtra.L500;
/**
 * LeetCode 525 - Contiguous Array

https://leetcode.com/problems/contiguous-array
Given a binary array, find the maximum length of a contiguous subarray with equal number of 0 and 1.
Example 1:
Input: [0,1]
Output: 2
Explanation: [0, 1] is the longest contiguous subarray with equal number of 0 and 1.
Example 2:
Input: [0,1,0]
Output: 2
Explanation: [0, 1] (or [1, 0]) is a longest contiguous subarray with equal number of 0 and 1.


0001110110001111000111
1100011101101111100000000011111111

boolean true/false

true
2
false
3



Note: The length of the given binary array will not exceed 50,000.
https://discuss.leetcode.com/topic/79906/easy-java-o-n-solution-presum-hashmap
The idea is to change 0 in the original array to -1. Thus, if we find SUM[i, j] == 0 then we know there are even number of -1 and 1 between index i and j. Also put the sum to index mapping to a HashMap to make search faster.
sumToIndex is a hash table stores the accumulative total's corresponding index. If and only if we find two different indices i, j and the two corresponding sum sumToIndex[sum]for i, j are equal, we claim that sum(nums[j+1:i+1]) == 0.
Consider sumToIndex.get(sum) as j, we wanna update answer with j - i which is exactly i - sumToIndex.get(sum).
    public int findMaxLength(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) nums[i] = -1;
        }
        
        Map<Integer, Integer> sumToIndex = new HashMap<>();
        sumToIndex.put(0, -1);
        int sum = 0, max = 0;
        
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            if (sumToIndex.containsKey(sum)) {
                max = Math.max(max, i - sumToIndex.get(sum));
            }
            else {
                sumToIndex.put(sum, i);
            }
        }
        
        return max;
    }

I had a similar idea, but in one pass:
    public int findMaxLength(int[] nums) {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>() {{put(0,0);}};
        int maxLength = 0, runningSum = 0;
        for (int i=0;i<nums.length;i++) {
            runningSum += nums[i];
            Integer prev = map.get(2*runningSum-i-1);//?
            if (prev != null) maxLength = Math.max(maxLength, i+1-prev);
            else map.put(2*runningSum-i-1, i+1);
        }
        return maxLength;
    }
https://discuss.leetcode.com/topic/79977/python-and-java-with-little-tricks-incl-a-oneliner/2
Using putIfAbsent so I only need one map function call per number.
public int findMaxLength(int[] nums) {
    Map<Integer, Integer> index = new HashMap<>();
    index.put(0, -1);
    int balance = 0, maxlen = 0;
    for (int i = 0; i < nums.length; i++) {
        balance += nums[i] * 2 - 1;
        Integer first = index.putIfAbsent(balance, i);
        if (first != null)
            maxlen = Math.max(maxlen, i - first);
    }
    return maxlen;
}
Could avoid using Math.max like this:
        if (first != null && i - first > maxlen)
            maxlen = i - first;
https://discuss.leetcode.com/topic/79932/java-one-pass-o-n-solution-with-explanation
diff[i] is "count of 1s" minus "count of 0s" so far.
For given i and j, if diff[i] == diff[j], then the subarray between i and j is a contiguous array as defined in the questions.
For any value of diff[], we save the index of the first item in the hashmap. Then once the value appears again, we get the length of the subarray between the current index and the index of the first item.
public int findMaxLength(int[] nums) {
    int res = 0;
    int n = nums.length;
    
    int[] diff = new int[n + 1];
    
    Map<Integer, Integer> map = new HashMap<>();
    map.put(0, 0);
    
    for (int i = 1; i <= n; i++) {
        diff[i] = diff[i - 1] + (nums[i - 1] == 0 ? -1 : 1);

        if (!map.containsKey(diff[i]))
            map.put(diff[i], i);
        else
            res = Math.max(res, i - map.get(diff[i]));
    }

    return res;
}

https://discuss.leetcode.com/topic/80056/python-o-n-solution-with-visual-explanation/2
    def findMaxLength(self, nums):
        count = 0
        max_length=0
        table = {0: 0}
        for index, num in enumerate(nums, 1):
            if num == 0:
                count -= 1
            else:
                count += 1
            
            if count in table:
                max_length = max(max_length, index - table[count])
            else:
                table[count] = index
        
        return max_length

https://discuss.leetcode.com/topic/80020/one-pass-use-a-hashmap-to-record-0-1-count-difference
    public int findMaxLength(int[] nums) {
        HashMap<Integer,Integer> map=new HashMap<>();
        map.put(0,-1);
        
        int zero=0;
        int one=0;
        int len=0;
        for(int i=0;i<nums.length;i++){
            if(nums[i]==0){
                zero++;
            }else{
                one++;
            }
            
            if(map.containsKey(zero-one)){
                len=Math.max(len,i-map.get(zero-one));
            }else{
                map.put(zero-one,i);
            }
        }
        
        return len;
    }


You might also like

 * @author het
 *
 */
public class L525 {
	public static int maxLength(int [] arr){
	    if(arr == null || arr.length == 0) return 0;
	    int lengthFor1s = 0;
	    int lengthFor0s = 0;
	    int max=0;
	    for(int i=0;i<arr.length;i+=1){
	       if((i>0 &&arr[i] != arr[i-1] ) ){
	           
	           max= Math.max(max, Math.min(lengthFor1s, lengthFor0s));
	           if(arr[i] == 1){
	                 lengthFor1s = 0;
	           }else{
	                 lengthFor0s = 0;
	           }
	       }
	       if(arr[i] == 1){
	            lengthFor1s+=1;
	       }else{
	            lengthFor0s+=1;
	       }
	       
	       if(i== arr.length-1 ){
	    	   max= Math.max(max, Math.min(lengthFor1s, lengthFor0s));
	       }
	       
	    }
	    return max;

	}
	
	private static int[] toArray(String str){
		int [] arr = new int[str.length()];
		char [] chars = str.toCharArray();
		for(int i=0;i<chars.length;i+=1){
			arr[i] = chars[i] == '1'? 1: 0;
		}
		return arr;
	}
	public static void main(String[] args) {
		String str1 = "000111011000111100000111110000";
		String str2 = "110001110110111110000000001111111110";
		System.out.println(maxLength(toArray(str1)));
		System.out.println(maxLength(toArray(str2)));
		// TODO Auto-generated method stub

	}

}
