package alite.leetcode.xx4;
/**
 * https://leetcode.com/problems/find-all-duplicates-in-an-array/
Given an array of integers, 1 ≤ a[i] ≤ n (n = size of array), some elements appear twice and others appear once.
Find all the elements that appear twice in this array.
Could you do it without extra space and in O(n) runtime?
Example:
Input:
[4,3,2,7,8,2,3,1]

Output:
[2,3]

解法I 正负号标记法（一趟遍历）
参考LeetCode Discuss：https://discuss.leetcode.com/topic/64735/java-simple-solution
遍历nums，记当前数字为n（取绝对值），将数字n视为下标（因为a[i]∈[1, n]）

当n首次出现时，nums[n - 1]乘以-1

当n再次出现时，则nums[n - 1]一定＜0，将n加入答案
    def findDuplicates(self, nums):
        """
        :type nums: List[int]
        :rtype: List[int]
        """
        ans = []
        for n in nums:
            if nums[abs(n) - 1] < 0:
                ans.append(abs(n))
            else:
                nums[abs(n) - 1] *= -1
        return ans

https://discuss.leetcode.com/topic/64735/java-simple-solution
https://discuss.leetcode.com/topic/64805/java-easy-to-understand-solution-without-extra-space-and-in-o-n-time
    // when find a number i, flip the number at position i-1 to negative. 
    // if the number at position i-1 is already negative, i is the number that occurs twice.
    
    public List<Integer> findDuplicates(int[] nums) {
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < nums.length; ++i) {
            int index = Math.abs(nums[i])-1;
            if (nums[index] < 0)
                res.add(Math.abs(index+1));
            nums[index] = -nums[index];
        }
        return res;
https://discuss.leetcode.com/topic/64908/java-solution-without-destroying-the-input-array-o-n-time-o-1-space/
public List<Integer> findDuplicates(int[] nums) {
     List<Integer> result = new ArrayList<Integer>();
    if(nums == null)
        return result;
    for(int i=0; i<nums.length; i++){
        int location = Math.abs(nums[i])-1;
        if(nums[location] < 0){
            result.add(Math.abs(nums[i]));
        }else{
            nums[location] = -nums[location];
        }
    }
    for(int i=0; i<nums.length; i++)
        nums[i] = Math.abs(nums[i]);
   
    return result;
}
http://bookshadow.com/weblog/2016/10/25/leetcode-find-all-duplicates-in-an-array/
解法II 位置交换法
遍历nums，记当前下标为i

当nums[i] > 0 并且 nums[i] != i + 1时，执行循环：

令n = nums[i]

如果n == nums[n - 1]，则将n加入答案，并将nums[i]置为0

否则，交换nums[i], nums[n - 1]
    def findDuplicates(self, nums):
        """
        :type nums: List[int]
        :rtype: List[int]
        """
        ans = []
        for i in range(len(nums)):
            while nums[i] and nums[i] != i + 1:
                n = nums[i]
                if nums[i] == nums[n - 1]:
                    ans.append(n)
                    nums[i] = 0
                else:
                    nums[i], nums[n - 1] = nums[n - 1], nums[i]
        return ans
 * @author het
 *
 */
public class LeetCode442FindAllDuplicatesinanArray {
//	 public List<Integer> findDuplicates(self, nums):
//	        """
//	        :type nums: List[int]
//	        :rtype: List[int]
//	        """
//	        ans = []
//	        for n in nums:
//	            if nums[abs(n) - 1] < 0:
//	                ans.append(abs(n))
//	            else:
//	                nums[abs(n) - 1] *= -1
//	        return ans
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
