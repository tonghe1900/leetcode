package alite.leetcode.xx2.sucess.before;
/**
 * leetcode 213 : House Robber II

leetcode 213 : House Robber II - 西施豆腐渣 - 博客频道 - CSDN.NET
After robbing those houses on that street, the thief has found himself a new place for his thievery so that he will not 
get too much attention. This time, all houses at this place are arranged in a circle. That means the first house is the
 neighbor of the last one. Meanwhile, the security system for these houses remain the same as for those in the previous 
 street.
Given a list of non-negative integers representing the amount of money of each house, determine the maximum amount
 of money you can rob tonight without alerting the police.
https://discuss.leetcode.com/topic/14375/simple-ac-solution-in-java-in-o-n-with-explanation
Since this question is a follow-up to House Robber, we can assume we already have a way to solve the 
simpler question, i.e. given a 1 row of house, we know how to rob them. So we already have such a helper function.
 We modify it a bit to rob a given range of houses.
private int rob(int[] num, int lo, int hi) {
    int include = 0, exclude = 0;
    for (int j = lo; j <= hi; j++) {
        int i = include, e = exclude;
        include = e + num[j];
        exclude = Math.max(e, i);
    }
    return Math.max(include, exclude);
}
Now the question is how to rob a circular row of houses. It is a bit complicated to solve like the simpler question.
 It is because in the simpler question whether to rob num[lo] is entirely our choice.
  But, it is now constrained by whether num[hi] is robbed.
However, since we already have a nice solution to the simpler problem. We do not want to throw it away.
 Then, it becomes how can we reduce this problem to the simpler one. Actually, extending from the logic
  that if house i is not robbed, then you are free to choose whether to rob house i + 1, you can break the circle 
  by assuming a house is not robbed.
For example, 1 -> 2 -> 3 -> 1 becomes 2 -> 3 if 1 is not robbed.
Since every house is either robbed or not robbed and at least half of the houses are not robbed, the solution 
is simply the larger of two cases with consecutive houses, i.e. house i not robbed, break the circle, 
solve it, or house i + 1 not robbed. Hence, the following solution. I chose i = n and i + 1 = 0 
for simpler coding. But, you can choose whichever two consecutive ones.
public int rob(int[] nums) {
    if (nums.length == 1) return nums[0];
    return Math.max(rob(nums, 0, nums.length - 2), rob(nums, 1, nums.length - 1));
}
https://discuss.leetcode.com/topic/23097/java-clean-short-solution-dp
public int rob(int[] nums) {
 return Math.max(rob(nums, 0, nums.length-2), rob(nums, 1, nums.length-1));
}

public int rob(int[] nums, int lo, int hi) {
    int preRob = 0, preNotRob = 0, rob = 0, notRob = 0;
    for (int i = lo; i <= hi; i++) {
       rob = preNotRob + nums[i];
     notRob = Math.max(preRob, preNotRob);
     
     preNotRob = notRob;
     preRob = rob;
    }
    return Math.max(rob, notRob);
}


    public int rob(int[] nums) {
        if(nums==null || nums.length==0) return 0;
        if(nums.length==1) return nums[0];
        if(nums.length==2) return Math.max(nums[0], nums[1]);
        return Math.max(robsub(nums, 0, nums.length-2), robsub(nums, 1, nums.length-1));
    }
    
    private int robsub(int[] nums, int s, int e) {
        int n = e - s + 1;
        int[] d =new int[n];
        d[0] = nums[s];
        d[1] = Math.max(nums[s], nums[s+1]);
        
        for(int i=2; i<n; i++) {
            d[i] = Math.max(d[i-2]+nums[s+i], d[i-1]);
        }
        return d[n-1];
    }
http://shibaili.blogspot.com/2015/06/day-108-add-and-search-word-data.html

    int robHelper(vector<int>& nums, int left, int right) {

        int pre_1 = 0, pre_2 = 0, pre_3 = 0;

        for (int i = left; i <= right; i++) {

            int temp = pre_3;

            pre_3 = max(pre_3,max(pre_1,pre_2) + nums[i]);

            pre_1 = pre_2;

            pre_2 = temp;

        }

         

        return pre_3;

    }


    int rob(vector<int>& nums) {

        if (nums.size() == 0) return 0;

        if (nums.size() == 1) return nums[0];

        return max(robHelper(nums,0,nums.size() - 2),robHelper(nums,1,nums.size() - 1));

    }
X. Iterative:
https://discuss.leetcode.com/topic/24060/good-performance-dp-solution-using-java
    public int rob(int[] nums) {
        if (nums.length == 0)
            return 0;
        if (nums.length < 2)
            return nums[0];
        
        int[] startFromFirstHouse = new int[nums.length + 1];
        int[] startFromSecondHouse = new int[nums.length + 1];
        
        startFromFirstHouse[0]  = 0;
        startFromFirstHouse[1]  = nums[0];
        startFromSecondHouse[0] = 0;
        startFromSecondHouse[1] = 0;
        
        for (int i = 2; i <= nums.length; i++) {
            startFromFirstHouse[i] = Math.max(startFromFirstHouse[i - 1], startFromFirstHouse[i - 2] + nums[i-1]);
            startFromSecondHouse[i] = Math.max(startFromSecondHouse[i - 1], startFromSecondHouse[i - 2] + nums[i-1]);
        }
        
        return Math.max(startFromFirstHouse[nums.length - 1], startFromSecondHouse[nums.length]);
    }
 * @author het
 *
 */
public class LeetCode213 {
//	public int rob(int[] nums) {
//	    if (nums.length == 1) return nums[0];
//	    return Math.max(rob(nums, 0, nums.length - 2), rob(nums, 1, nums.length - 1));
//	}
//	https://discuss.leetcode.com/topic/23097/java-clean-short-solution-dp
	public int rob(int[] nums) {
	 return Math.max(rob(nums, 0, nums.length-2), rob(nums, 1, nums.length-1));
	}
	
	
	private int rob1(int[] num, int lo, int hi) {
	    int include = 0, exclude = 0;
	    for (int j = lo; j <= hi; j++) {
	        int i = include, e = exclude;
	        include = e + num[j];
	        exclude = Math.max(e, i);
	    }
	    return Math.max(include, exclude);
	}

	public int rob(int[] nums, int lo, int hi) {
	    int preRob = 0, preNotRob = 0, rob = 0, notRob = 0;
	    for (int i = lo; i <= hi; i++) {
	       rob = preNotRob + nums[i];
	     notRob = Math.max(preRob, preNotRob);
	     
	     preNotRob = notRob;
	     preRob = rob;
	    }
	    return Math.max(rob, notRob);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
