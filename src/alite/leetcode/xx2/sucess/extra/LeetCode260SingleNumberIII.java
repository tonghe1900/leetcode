package alite.leetcode.xx2.sucess.extra;
/**
 * LIKE CODING: LeetCode [260] Single Number III
Given an array of numbers nums, in which exactly two elements appear only once and all the other elements appear exactly twice. Find the two elements that appear only once.
For example:
Given nums = [1, 2, 1, 3, 2, 5], return [3, 5].
Note:
The order of the result is not important. So in the above example, [5, 3] is also correct.
Your algorithm should run in linear runtime complexity. Could you implement it using only constant space complexity?
http://www.chenguanghe.com/single-number-iii/
思路是这样的: 首先用一个变量diff, 对每个数做xor, 这样得到的diff里面只有这两个数字中bit不同的位代表的数字,diff是一定不为0的, 因为这两个数不同, 不然所有数都出现两次了, 其他的数字因为出现两次, 都被xor删除了, 剩下的两个数字的不同位的代表的数. diff虽然表示了两个数中不同位, 但是它不能用来直接计算, 因为如果用它判断这两个数, 不能确保判断出两个数的位是留下的位. 所以这里我们要取diff中的最右边的为1的位. 这里用的方法是:
int rightBit = diff & -diff;
这个1的意义是, 我们需要找的两个数可以通过这个1来区别. 所以我们通过这个1,可以把数组分成两组, 每组都包含一个我们要找的数, 因为其他数都是出现两次, 所以留下的就是最后我们要找的答案

    public int[] singleNumber(int[] nums) {

        if (nums.length == 0 || nums == null)

            return null;

        int diff = 0;

        for(int i : nums)

            diff ^= i;

        int rightBit = diff & -diff;

        int[] result = new int[]{0,0};

        for(int i : nums){

            if((i & rightBit) == 0)

                result[0] ^= i;

            else

                result[1] ^= i;

        }

        return result;

    }
https://leetcode.com/discuss/52351/accepted-java-space-easy-solution-with-detail-explanations
Once again, we need to use XOR to solve this problem. But this time, we need to do it in two passes:
In the first pass, we XOR all elements in the array, and get the XOR of the two numbers we need to find. Note that since the two numbers are distinct, so there must be a set bit in the XOR result. Find out the bit.
In the second pass, we divide all numbers into two groups, one with the aforementioned bit set, another with the aforementinoed bit unset. Two different numbers we need to find must fall into thte two distrinct groups. XOR numbers in each group, we can find a number in either group.
    public int[] singleNumber(int[] nums) {
        // Pass 1 : 
        // Get the XOR of the two numbers we need to find
        int diff = 0;
        for (int num : nums) {
            diff ^= num;
        }
        // Get its last set bit
        diff &= -diff;

        // Pass 2 :
        int[] rets = {0, 0}; // this array stores the two numbers we will return
        for (int num : nums)
        {
            if ((num & diff) == 0) // the bit is not set
            {
                rets[0] ^= num;
            }
            else // the bit is set
            {
                rets[1] ^= num;
            }
        }
        return rets;
    }


    vector<int> singleNumber(vector<int>& nums) {

        int diff = accumulate(nums.begin(), nums.end(), 0, bit_xor<int>());

        diff &= -diff;

        vector<int> ret(2);

        for(int n:nums){

            if(diff & n){

                ret[0] ^= n;

            }else{

                ret[1] ^= n;

            }

        }

        return ret;

    }
Read full article from LIKE CODING: LeetCode [260] Single Number III
 * @author het
 *
 */
public class LeetCode260SingleNumberIII {
	 public int[] singleNumber(int[] nums) {

	        if (nums.length == 0 || nums == null)

	            return null;

	        int diff = 0;

	        for(int i : nums)

	            diff ^= i;

	        int rightBit = diff & -diff;// rightBit = diff & -diff
	        
	        // int rightBit = diff & -diff;// rightBit = diff & -diff
	        // int rightBit = diff & -diff;// rightBit = diff & -diff
	         int test =  diff & ~(diff -1);
// int test =  diff & ~(diff -1);
	        int[] result = new int[]{0,0};

	        for(int i : nums){

	            if((i & rightBit) == 0)

	                result[0] ^= i;

	            else

	                result[1] ^= i;

	        }

	        return result;

	    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
