package alite.leetcode.newExtra.fight;
/**
 * LeetCode 477 - Total Hamming Distance

https://leetcode.com/problems/total-hamming-distance/
The Hamming distance between two integers is the number of positions at which the corresponding bits are different.
Now your job is to find the total Hamming distance between all pairs of the given numbers.
Example:
Input: 4, 14, 2

Output: 6

Explanation: In binary representation, the 4 is 0100, 14 is 1110, and 2 is 0010 (just
showing the four bits relevant in this case). So the answer will be:
HammingDistance(4, 14) + HammingDistance(4, 2) + HammingDistance(14, 2) = 2 + 2 + 2 = 6.
Note:
Elements of the given array are in the range of 0 to 10^9
Length of the array will not exceed 10^4.
http://startleetcode.blogspot.com/2016/12/477-total-hamming-distance.html
May use the bitwise operation to reduce the time complexity. All the numbers can be viewed as 32 bits with 0s and 1s. 
So for each bit, we just need to count the total number of 1s, then the total number of 0s will be (total numbers - the numbers of 1s). 
And the contribution to the Hamming distance will be count of 1s times the count of 0s. Similarly argument can be applied to all the rest
 bits.

    int totalHammingDistance(vector<int>& nums) {

        int res = 0;

        for(int i=0; i<32; ++i){

            int one = 0;

            for(int j=0; j<nums.size(); ++j){

                if((1<<i) & nums[j]) ++one;//one is the numbers of 1s;

            }

            res += one*(nums.size()-one);// nums of 1s x nums of 0s

        }

        return res;

    }

http://bookshadow.com/weblog/2016/12/18/leetcode-total-hamming-distance/
按位统计各整数的二进制0与1的个数之和，分别记为zero[i], 和one[i]
ans = ∑(zero[i] * one[i]),  i∈[0, 31]
    def totalHammingDistance(self, nums):
        """
        :type nums: List[int]
        :rtype: int
        """
        ans = 0
        for x in range(32):
            mask = 1 << x
            zero = one = 0
            for num in nums:
                if num & mask:
                    one += 1
                else:
                    zero += 1
            ans += zero * one
        return ans

X.
http://blog.csdn.net/zhouziyu2011/article/details/53726081
(1)遍历数组nums，对每一个nums[i]，求其余后面的每一个nums[j]的Hamming Distance。
(2)求x与y的Hamming Distance的方法：
---1)先求x^y的结果res。
---2)再依次求32位res的每一位与1进行与操作的结果，若不为0，则Hamming Distance加一。
    public int totalHammingDistance(int[] nums) {  
        int len = nums.length;  
        if (len == 0)  
            return 0;  
        int result = 0;  
        for (int i = 0; i < len - 1; i++) {  
            for (int j = i + 1; j < len; j++)  
                result += hammingDistance(nums[i], nums[j]);  
        }  
        return result;  
    }  
    public int hammingDistance(int x, int y) {  
        int res = x ^ y;  
        int count = 0;  
        for (int i = 0; i < 32; i++) {  
            if ((res & 1) != 0)  
                count++;  
            res >>= 1;  
        }  
        return count;  
    } 
 * @author het
 *
 */
public class L477 {
	
	 public int hammingDistance(int x, int y) {  
	        int res = x ^ y;  
	        int count = 0;  
	        for (int i = 0; i < 32; i++) {  
	            if ((res & 1) != 0)  
	                count++;  
	            res >>= 1;  
	        }  
	        return count;  
	    } 
	 
	 
	 int totalHammingDistance(vector<int>& nums) {

	        int res = 0;

	        for(int i=0; i<32; ++i){

	            int one = 0;

	            for(int j=0; j<nums.size(); ++j){

	                if((1<<i) & nums[j]) ++one;//one is the numbers of 1s;

	            }

	            res += one*(nums.size()-one);// nums of 1s x nums of 0s

	        }

	        return res;

	    }
//	def totalHammingDistance(self, nums):
//        """
//        :type nums: List[int]
//        :rtype: int
//        """
//        ans = 0
//        for x in range(32):
//            mask = 1 << x
//            zero = one = 0
//            for num in nums:
//                if num & mask:
//                    one += 1
//                else:
//                    zero += 1
//            ans += zero * one
//        return ans
}
