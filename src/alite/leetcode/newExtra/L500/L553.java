package alite.leetcode.newExtra.L500;
/**
 * LeetCode 553 - Optimal Division

https://leetcode.com/problems/optimal-division
Given a list of positive integers, the adjacent integers will perform the float division. For example, [2,3,4]
 -> 2 / 3 / 4.
However, you can add any number of parenthesis at any position to change the priority of operations. You should 
find out how to add parenthesis to get the maximum result, and return the corresponding expression in string format. 
Your expression should NOT contain redundant parenthesis.
Example:
Input: [1000,100,10,2]
Output: "1000/(100/10/2)"
Explanation:
1000/(100/10/2) = 1000/((100/10)/2) = 200
However, the bold parenthesis in "1000/((100/10)/2)" are redundant, 
since they don't influence the operation priority. So you should return "1000/(100/10/2)". 
(a)/(b/c/d) ->a/(b/c/d) 

(a/b)/(c/d)   ->a/b/(c/d)
(a/b/c)/(d)
x/y/z

x * z/y


Other cases:
1000/(100/10)/2 = 50
1000/(100/(10/2)) = 50
1000/100/10/2 = 0.5
1000/100/(10/2) = 2
Note:
The length of the input array is [1, 10].
Elements in the given array will be in range [2, 1000].
There is only one optimal division for each test case.
https://discuss.leetcode.com/topic/86483/easy-to-understand-simple-o-n-solution-with-explanation
X1/X2/X3/../Xn will always be equal to (X1/X2) * Y, no matter how you place parentheses. i.e no matter how you 
place parentheses, X1 always goes to the numerator and X2 always goes to the denominator.
 Hence you just need to maximize Y. And Y is maximized when it is equal to X3 *..*Xn.
  So the answer is always X1/(X2/X3/../Xn) = (X1 *X3 *..*Xn)/X2
https://discuss.leetcode.com/topic/86473/o-n-very-easy-java-solution
    public String optimalDivision(int[] nums) {
        StringBuilder builder = new StringBuilder();
        builder.append(nums[0]);
        for (int i = 1; i < nums.length; i++) {
            if (i == 1 && nums.length > 2) {
                builder.append("/(").append(nums[i]);
            } else {
                builder.append("/").append(nums[i]);
            }
        }
        
        return nums.length > 2 ? builder.append(")").toString() : builder.toString();
    }
https://discuss.leetcode.com/topic/86475/simple-java-solution
    public String optimalDivision(int[] nums) {
        if (nums.length == 1)
            return nums[0] + "";
        if (nums.length == 2)
            return nums[0] + "/" + nums[1];
        String res = nums[0] + "/(" + nums[1];
        for (int i = 2; i < nums.length; i++) {
            res += "/" + nums[i];
        }
        return res + ")";
    }
https://discuss.leetcode.com/topic/86468/java-solution-backtracking
    class Result {
        String str;
        double val;
    }
    
    public String optimalDivision(int[] nums) {
        int len = nums.length;
        return getMax(nums, 0, len - 1).str;
    }
    
    private Result getMax(int[] nums, int start, int end) {
        Result r = new Result();
        r.val = -1.0;
        
        if (start == end) {
            r.str = nums[start] + "";
            r.val = (double)nums[start];
        }
        else if (start + 1 == end) {
            r.str = nums[start] + "/" + nums[end];
            r.val = (double)nums[start] / (double)nums[end];
        }
        else {
            for (int i = start; i < end; i++) {
                Result r1 = getMax(nums, start, i);
                Result r2 = getMin(nums, i + 1, end);
                if (r1.val / r2.val > r.val) {
                    r.str = r1.str + "/" + (end - i >= 2 ? "(" + r2.str + ")" : r2.str);
                    r.val = r1.val / r2.val;
                }
            }
        }
        
        //System.out.println("getMax " + start + " " + end + "->" + r.str + ":" + r.val);
        return r;
    }
    
    private Result getMin(int[] nums, int start, int end) {
        Result r = new Result();
        r.val = Double.MAX_VALUE;
        
        if (start == end) {
            r.str = nums[start] + "";
            r.val = (double)nums[start];
        }
        else if (start + 1 == end) {
            r.str = nums[start] + "/" + nums[end];
            r.val = (double)nums[start] / (double)nums[end];
        }
        else {
            for (int i = start; i < end; i++) {
                Result r1 = getMin(nums, start, i);
                Result r2 = getMax(nums, i + 1, end);
                if (r1.val / r2.val < r.val) {
                    r.str = r1.str + "/" + (end - i >= 2 ? "(" + r2.str + ")" : r2.str);
                    r.val = r1.val / r2.val;
                }
            }
        }
        
        //System.out.println("getMin " + start + " " + end + "->" + r.str + ":" + r.val);
        return r;
    }
 * @author het
 *
 */
public class L553 {
	 public String optimalDivision(int[] nums) {
	        StringBuilder builder = new StringBuilder();
	        builder.append(nums[0]);
	        for (int i = 1; i < nums.length; i++) {
	            if (i == 1 && nums.length > 2) {
	                builder.append("/(").append(nums[i]);
	            } else {
	                builder.append("/").append(nums[i]);
	            }
	        }
	        
	        return nums.length > 2 ? builder.append(")").toString() : builder.toString();
	    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
