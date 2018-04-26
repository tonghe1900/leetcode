package alite.leetcode.newest;
/**
 * LeetCode 611 - Valid Triangle Number

Count the number of possible triangles
https://leetcode.com/problems/valid-triangle-number
Given an array consists of non-negative integers, your task is to count the number of triplets chosen from the array that can make triangles if we take them as side lengths of a triangle.
Example 1:
Input: [2,2,3,4]
Output: 3
Explanation:
Valid combinations are: 
2,3,4 (using the first 2)
2,3,4 (using the second 2)
2,2,3
Note:
The length of the given array won't exceed 1000.
The integers in the given array are in the range of [0, 1000].
X.
https://discuss.leetcode.com/topic/92110/java-solution-3-pointers
Same as https://leetcode.com/problems/3sum-closest
Assume a is the longest edge, b and c are shorter ones, to form a triangle, they need to satisfy len(b) + len(c) > len(a).
    public int triangleNumber(int[] nums) {
        int result = 0;
        if (nums.length < 3) return result;
        
        Arrays.sort(nums);

        for (int i = 2; i < nums.length; i++) {
            int left = 0, right = i - 1;
            while (left < right) {
                if (nums[left] + nums[right] > nums[i]) {
                    result += (right - left);
                    right--;
                }
                else {
                    left++;
                }
            }
        }
        
        return result;
    }
https://discuss.leetcode.com/topic/92099/java-o-n-2-time-o-1-space
public static int triangleNumber(int[] A) {
    Arrays.sort(A);
    int count = 0, n = A.length;
    for (int i=n-1;i>=2;i--) {
        int l = 0, r = i-1;
        while (l < r) {
            if (A[l] + A[r] > A[i]) {
                count += r-l;
                r--;
            }
            else l++;
        }
    }
    return count;
}
https://leetcode.com/articles/valid-triangle-number/
Approach #3 Linear Scan
once we sort the given numsnums array, we need to find the right limit of the index kk for a pair of indices (i, j)(i,j) chosen to find the countcount of elements satisfying nums[i] + nums[j] > nums[k]nums[i]+nums[j]>nums[k] for the triplet (nums[i], nums[j], nums[k])(nums[i],nums[j],nums[k]) to form a valid triangle.
We can find this right limit by simply traversing the index kk's values starting from the index k=j+1k=j+1 for a pair (i, j)(i,j) chosen and stopping at the first value of kk not satisfying the above inequality. Again, the countcount of elements nums[k]nums[k] satisfying nums[i] + nums[j] > nums[k]nums[i]+nums[j]>nums[k] for the pair of indices (i, j)(i,j)chosen is given by k - j - 1k−j−1 as discussed in the last approach.
Further, as discussed in the last approach, when we choose a higher value of index jj for a particular ii chosen, we need not start from the index j + 1j+1. Instead, we can start off directly from the value of kk where we left for the last index jj. This helps to save redundant computations.

Time complexity : O(n^2)O(n
​2
​​ ). Loop of kk and jj will be executed O(n^2)O(n
​2
​​ ) times in total, because, we do not reinitialize the value of kk for a new value of jjchosen(for the same ii). Thus the complexity will be O(n^2+n^2)=O(n^2).
Space complexity : O(logn)O(logn). Sorting takes O(logn) space.
    public int triangleNumber(int[] nums) {
        int count = 0;
        Arrays.sort(nums);
        for (int i = 0; i < nums.length - 2; i++) {
            int k = i + 2;
            for (int j = i + 1; j < nums.length - 1 && nums[i] != 0; j++) {
                while (k < nums.length && nums[i] + nums[j] > nums[k])
                    k++;
                count += k - j - 1;
            }
        }
        return count;
    }

X. Approach #2 Using Binary Search
Time complexity : O(n^2logn)O(n
​2
​​ logn). In worst case inner loop will take nlognnlogn (binary search applied nn times).
Space complexity : O(logn)O(logn). Sorting takes O(logn)O(logn) space.
    int binarySearch(int nums[], int l, int r, int x) {
        while (r >= l && r < nums.length) {
            int mid = (l + r) / 2;
            if (nums[mid] >= x)
                r = mid - 1;
            else
                l = mid + 1;
        }
        return l;
    }
    public int triangleNumber(int[] nums) {
        int count = 0;
        Arrays.sort(nums);
        for (int i = 0; i < nums.length - 2; i++) {
            int k = i + 2;
            for (int j = i + 1; j < nums.length - 1 && nums[i] != 0; j++) {
                k = binarySearch(nums, k, nums.length - 1, nums[i] + nums[j]);
                count += k - j - 1;
            }
        }
        return count;
    }

X. https://leetcode.com/articles/valid-triangle-number/
    public int triangleNumber(int[] nums) {
        int count = 0;
        for (int i = 0; i < nums.length - 2; i++) {
            for (int j = i + 1; j < nums.length - 1; j++) {
                for (int k = j + 1; k < nums.length; k++) {
                    if (nums[i] + nums[j] > nums[k] && nums[i] + nums[k] > nums[j] && nums[j] + nums[k] > nums[i])
                        count++;
                }
            }
        } I
        return count;
    }
 * @author het
 *
 */
public class LeetCode611ValidTriangleNumber {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
