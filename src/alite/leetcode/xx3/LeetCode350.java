package alite.leetcode.xx3;
/**
 * LeetCode 350 - Intersection of Two Arrays II

https://leetcode.com/problems/intersection-of-two-arrays-ii/
Given two arrays, write a function to compute their intersection.
Example:
Given nums1 = [1, 2, 2, 1], nums2 = [2, 2], return [2, 2].
Note:
Each element in the result should appear as many times as it shows in both arrays.
The result can be in any order.
Follow up:
What if the given array is already sorted? How would you optimize your algorithm?
What if nums1's size is small compared to num2's size? Which algorithm is better?
What if elements of nums2 are stored on disk, and the memory is limited such that you cannot load all elements into the memory at once?
http://bookshadow.com/weblog/2016/05/21/leetcode-intersection-of-two-arrays-ii/

如果数组已经排好序，怎样优化你的算法？
如果nums1的长度＜nums2的长度？哪一种算法更好？
如果nums2的元素存储在磁盘上，并且内存大小有限，不足以将其一次性的加载到内存中。此时应当怎样做？
X. 解法I 排序（Sort）+双指针（Two Pointers）
http://vegetablefinn.github.io/2016/05/21/350-Intersection-of-Two-Arrays-II/
If the arrays are sorted, then we can use two points.
    public int[] intersect(int[] nums1, int[] nums2) {
        //先排序
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        //确定head
        int nums1Head = 0;
        int nums2Head = 0;
        //确定输出数组
        List<Integer> resultList = new ArrayList<Integer>();
        while(nums1Head < nums1.length && nums2Head < nums2.length ){
          if(nums1[nums1Head] == nums2[nums2Head]){
            resultList.add(nums1[nums1Head]);
            nums1Head++;
            nums2Head++;
            continue;
          }else if(nums1[nums1Head] < nums2[nums2Head]){
            nums1Head++;
            continue;
          }else if(nums1[nums1Head] > nums2[nums2Head]){
            nums2Head++;
            continue;
          }
        }
        int result[] = new int[resultList.size()];
        for (int i=0; i < result.length; i++)
        {
            result[i] = resultList.get(i).intValue();
        }
        return result;
    }

解法II Counter计数
该解法不需要将nums2一次性加载到内存中
    def intersect(self, nums1, nums2):
        """
        :type nums1: List[int]
        :type nums2: List[int]
        :rtype: List[int]
        """
        if len(nums1) > len(nums2):
            nums1, nums2 = nums2, nums1
        c = collections.Counter(nums1)
        ans = []
        for x in nums2:
            if c[x] > 0:
                ans += x,
                c[x] -= 1
        return ans

    Map<Integer, Long> map = Arrays.stream(nums2).boxed().collect
            (Collectors.groupingBy(e->e, Collectors.counting()));
    return Arrays.stream(nums1).filter(e ->{
        if(!map.containsKey(e)) return false;
        map.put(e, map.get(e) - 1);
        if(map.get(e) == 0) map.remove(e);
        return true;
    }).toArray();

[JAVA] What's the best way you think to convert List<Integer> to a normal int array?
int[] array = list.stream().mapToInt(i->i).toArray();
list.stream().mapToInt(Integer :: intValue).toArray()
X. Follow up
https://leetcode.com/discuss/103969/solution-to-3rd-follow-up-question
What if elements of nums2 are stored on disk, and the memory is
limited such that you cannot load all elements into the memory at
once?
If only nums2 cannot fit in memory, put all elements of nums1 into a HashMap, read chunks of array that 
fit into the memory, and record the intersections.
If both nums1 and nums2 are so huge that neither fit into the memory, sort them individually
 (external sort), then read 2 elements from each array at a time in memory, record intersections.
I think the second part of the solution is impractical, if you read 2 elements at a time, 
this procedure will take forever. In principle, we want minimize the number of disk access during the run-time.
An improvement can be sort them using external sort, read (let's say) 2G of each into memory 
and then using the 2 pointer technique, then read 2G more from the array that has been exhausted. 
Repeat this until no more data to read from disk.
But I am not sure this solution is good enough for an interview setting. Maybe the interviewer 
is expecting some solution using Map-Reduce paradigm.
http://faculty.simpson.edu/lydia.sinapova/www/cmsc250/LN250_Weiss/L17-ExternalSortEX2.htm
http://massivealgorithms.blogspot.com/2014/07/exceptional-code-external-sorting-for.html
http://buttercola.blogspot.com/2016/06/leetcode-intersection-of-two-arrays-ii.html

What if the given array is already sorted? How would you optimize your algorithm?
Solution 1, i.e., sorting,  would be better since it does not need extra memory. 
What if nums1's size is small compared to num2's size? Which algorithm is better?
If two arrays are sorted, we could use binary search, i.e., for each element in the shorter array,
 search in the longer one. So the overall time complexity is O(nlogm), where n is the length of the shorter array,
  and m is the length of the longer array. Note that this is better than Solution 1 since the time complexity is O(n + m) 
  in the worst case. 
What if elements of nums2 are stored on disk, and the memory is limited such that you cannot load all elements into
 the memory at once?
If the two arrays have relatively the same length, we can use external sort to sort out the two arrays in the disk. 
Then load chunks of each array into the memory and compare, by using the method 1. 
If one array is too short while the other is long, in this case, if memory is limited and nums2 is stored in disk,
 partition it and send portions of nums2 piece by piece. keep a pointer for nums1 indicating the current position, 
 and it should be working fine~
Another method is, store the larger array into disk, and for each element in the shorter array, 
use "Exponential Search" and search in the longer array. 
https://helloyuantechblog.wordpress.com/2016/06/20/leetcode-350-intersection-of-two-arrays-ii-easy/

    vector<int> intersect(vector<int>& nums1, vector<int>& nums2) {

        //sort(nums1.begin(), nums1.end()); not needed

        sort(nums2.begin(), nums2.end());

        vector<int> result;

        int len1 = nums1.size(), len2 = nums2.size();

        for(int i = 0; i < nums1.size(); i++) { if (i > 0 && nums1[i] == nums1[i-1]) {

                continue;

            }

            int j = binarySearch(nums2, nums1[i]);

            if (j != -1) {

                result.push_back(nums1[i]);

                while(i + 1 < len1 && nums1[i+1] == nums1[i] && j + 1 < len2 && nums2[j+1] == nums2[j]) {

                    result.push_back(nums1[i]);

                    i++; j++;

                }

            }

        }

        return result;

    }

    int binarySearch(vector<int>& nums, int target) {

        if (nums.empty()) {

            return -1;

        }

        int start = 0, end = nums.size()-1, mid;

        while(start + 1 < end) {

            mid = start + (end - start) / 2;

            if (nums[mid] < target) { 

                start = mid + 1; 

            } else {

                end = mid;

            } 

        }

        if (nums[start] == target) {

            return start;

        }

        if (nums[end] == target) {

            return end;

        }

        return -1;

    }
https://discuss.leetcode.com/topic/49329/all-the-follow-up-solution
1.What if the given array is already sorted? How would you optimize your algorithm?
class Solution {
public:
    vector<int> intersect(vector<int>& nums1, vector<int>& nums2) {
        vector<int> res;
        if(nums1.empty() || nums2.empty())
            return res;
        for(int i=0,j=0; i<nums1.size() && j<nums2.size(); ) {
            if(nums1[i] == nums2[j]) {
                res.push_back(nums2[j]);
                ++j;
                ++i;
            }else if(nums1[i] < nums2[j]) {
                ++i;
            }else if(nums1[i] > nums2[j]) {
                ++j;
            }
        }
        return res;
    }
};
2.What if nums1's size is small compared to nums2's size? Which algorithm is better?
class Solution {
public:
    vector<int> intersect(vector<int>& nums1, vector<int>& nums2) {
        unordered_map<int, int> mymap;
        vector<int> res;
        for(auto it : nums1) {
            mymap[it]++;
        }
        for(auto it : nums2) {
            if(--mymap[it] >= 0) {
                res.push_back(it);
            }
        }
        return res;

    }
};
 * @author het
 *
 */
public class LeetCode350 {
	public int[] intersect(int[] nums1, int[] nums2) {
        //先排序
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        //确定head
        int nums1Head = 0;
        int nums2Head = 0;
        //确定输出数组
        List<Integer> resultList = new ArrayList<Integer>();
        while(nums1Head < nums1.length && nums2Head < nums2.length ){
          if(nums1[nums1Head] == nums2[nums2Head]){
            resultList.add(nums1[nums1Head]);
            nums1Head++;
            nums2Head++;
            continue;
          }else if(nums1[nums1Head] < nums2[nums2Head]){
            nums1Head++;
            continue;
          }else if(nums1[nums1Head] > nums2[nums2Head]){
            nums2Head++;
            continue;
          }
        }
        int result[] = new int[resultList.size()];
        for (int i=0; i < result.length; i++)
        {
            result[i] = resultList.get(i).intValue();
        }
        return result;
    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
