package alite.leetcode.newest;
/**
 * LeetCode 628 - Maximum Product of Three Numbers

https://leetcode.com/problems/maximum-product-of-three-numbers/
Given an integer array, find three numbers whose product is maximum and output the maximum product.
Example 1:
Input: [1,2,3]
Output: 6
Example 2:
Input: [1,2,3,4]
Output: 24
Note:
The length of the given array will be in range [3,104] and all elements are in the range [-1000, 1000].
Multiplication of any three numbers in the input won't exceed the range of 32-bit signed integer.
https://leetcode.com/articles/maximmum-product-of-three-numbers/

Approach #3 Single Scan
We need not necessarily sort the given numsnums array to find the maximum product. Instead, we can only find the required 2 smallest values(min1min1 and min2min2) and the three largest values(max1, max2, max3max1,max2,max3) in the numsnums array, by iterating over the numsnums array only once. At the end, again we can find out the larger value out of min1min1xmin2min2xmax1max1 and max1max1xmax2max2xmax3max3 to find the required maximum product.
    public int maximumProduct(int[] nums) {
        int min1 = Integer.MAX_VALUE, min2 = Integer.MAX_VALUE;
        int max1 = Integer.MIN_VALUE, max2 = Integer.MIN_VALUE, max3 = Integer.MIN_VALUE;
        for (int n: nums) {
            if (n <= min1) {
                min2 = min1;
                min1 = n;
            } else if (n <= min2) {     // n lies between min1 and min2
                min2 = n;
            }
            if (n >= max1) {            // n is greater than max1, max2 and max3
                max3 = max2;
                max2 = max1;
                max1 = n;
            } else if (n >= max2) {     // n lies betweeen max1 and max2
                max3 = max2;
                max2 = n;
            } else if (n >= max3) {     // n lies betwen max2 and max3
                max3 = n;
            }
        }
        return Math.max(min1 * min2 * max1, max1 * max2 * max3);
    }
https://discuss.leetcode.com/topic/93804/java-o-1-space-o-n-time-solution-beat-100
    int maximumProduct(vector<int>& nums) {
        int N = nums.size();
        if(N < 3) {
            return 0;
        }
        if(N == 3) {
            return nums[0] * nums[1] * nums[2];
        }
        sort(nums.begin(), nums.end());
        if(nums[0] >= 0 || nums.back() < 0) {
            return nums[N-1] * nums[N-2] * nums[N-3];
        } else if(nums.back() == 0) {
            N = N - 1;
            return nums[N-1] * nums[N-2] * nums[N-3];
        } else {
            return max(nums[0] * nums[1] * nums.back(), nums[N-1] * nums[N-2] * nums[N-3]);
        }
    }
https://discuss.leetcode.com/topic/93690/java-easy-ac/
    public int maximumProduct(int[] nums) {
        Arrays.sort(nums);
        return Math.max(nums[0] * nums[1] * nums[nums.length - 1], nums[nums.length - 1] * nums[nums.length - 2] * nums[nums.length - 3]);
    }


    public int maximumProduct(int[] nums) {
        int res = Math.MIN_VALUE;
        for (int i = 0; i < nums.length - 2; i++) {
            for (int j = i + 1; j < nums.length - 1; j++) {
                for (int k = j + 1; k < nums.length; k++) {
                    res = Math.max(res, nums[i] * nums[j] * nums[k]);
                }
            }
        }
        return res;
    }
 * @author het
 *
 */
public class LeetCode628MaximumProductofThreeNumbers {

}
