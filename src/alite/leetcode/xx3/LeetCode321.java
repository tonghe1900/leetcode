package alite.leetcode.xx3;
/**
 * LeetCode 321 - Create Maximum Number

http://bookshadow.com/weblog/2015/12/24/leetcode-create-maximum-number/
Given two arrays of length m and n with digits 0-9 representing two numbers. Create the maximum number of length k <= m + n from digits of the two. 
The relative order of the digits from the same array must be preserved. Return an array of the k digits
. You should try to optimize your time and space complexity.
Example 1:
nums1 = [3, 4, 6, 5]
nums2 = [9, 1, 2, 5, 8, 3]
k = 5
return [9, 8, 6, 5, 3]
问题可以转化为这样的两个子问题：
1. 从数组nums中挑选出t个数，在保持元素相对顺序不变的情况下，使得选出的子数组最大化。

2. 在保持元素相对顺序不变的前提下，将数组nums1与数组nums2合并，使合并后的数组最大化。
枚举nums1子数组与nums2子数组的长度len1, len2，在满足长度之和len1+len2等于k的前提下，分别求解最大子数组，并进行合并。
然后从合并得到的子数组中取最大数组即为所求。
子问题1的求解：
参考[LeetCode]Remove Duplicate Letters的思路，利用栈保存最大值子数组
时间复杂度为O(n)，其中n为数组的长度。
子问题2的求解：
两数组的合并可以类比归并排序中的merge操作，只不过在选择两数组中较大的元素时，需要对数组剩余部分的元素进行比较，详见代码
class Solution(object):
    def maxNumber(self, nums1, nums2, k):
        """
        :type nums1: List[int]
        :type nums2: List[int]
        :type k: int
        :rtype: List[int]
        """
        def getMax(nums, t):
            ans = []
            size = len(nums)
            for x in range(size):
                while ans and len(ans) + size - x > t and ans[-1] < nums[x]:
                    ans.pop()
                if len(ans) < t:
                    ans += nums[x],
            return ans

        def merge(nums1, nums2):
            ans = []
            while nums1 or nums2:
                if nums1 > nums2:
                    ans += nums1[0],
                    nums1 = nums1[1:]
                else:
                    ans += nums2[0],
                    nums2 = nums2[1:]
            return ans
        
        len1, len2 = len(nums1), len(nums2)
        res = []
        for x in range(max(0, k - len2), min(k, len1) + 1):
            tmp = merge(getMax(nums1, x), getMax(nums2, k - x))
            res = max(tmp, res)
        return res

1

2

3

4

5

6

7

8

9

10

11

12

13

14

15

16

17

18

19

20

21

22

23

24

25

26

27

28

29

30

31

32

33

34

35


merge函数可以简化成一行：
参考：https://leetcode.com/discuss/75804/short-python
def merge(nums1, nums2):
    return [max(nums1, nums2).pop(0) for _ in nums1 + nums2]
https://www.hrwhisper.me/leetcode-create-maximum-number/

    public int[] maxNumber(int[] nums1, int[] nums2, int k) {

        int[] ans = new int[k];

        for (int i = Math.max(k - nums2.length, 0); i <= Math.min(nums1.length, k); i++) {

            int[] res1 = get_max_sub_array(nums1, i);

            int[] res2 = get_max_sub_array(nums2, k - i);

            int[] res = new int[k];

            int pos1 = 0, pos2 = 0, tpos = 0;

 

            while (pos1 < res1.length || pos2 < res2.length) {

                res[tpos++] = greater(res1, pos1, res2, pos2) ? res1[pos1++] : res2[pos2++];

            }

 

            if (!greater(ans, 0, res, 0))

                ans = res;

        }

 

        return ans;

    }

 

    public boolean greater(int[] nums1, int start1, int[] nums2, int start2) {

        for (; start1 < nums1.length && start2 < nums2.length; start1++, start2++) {

            if (nums1[start1] > nums2[start2]) return true;

            if (nums1[start1] < nums2[start2]) return false;

        }

        return start1 != nums1.length;

    }

 

    public int[] get_max_sub_array(int[] nums, int k) {

        int[] res = new int[k];

        int len = 0;

        for (int i = 0; i < nums.length; i++) {

            while (len > 0 && len + nums.length - i > k && res[len - 1] < nums[i]) {

                len--;

            }

            if (len < k)

                res[len++] = nums[i];

        }

        return res;

    }
http://buttercola.blogspot.com/2016/06/leetcode-321-create-maximum-number.html

    public int[] maxNumber(int[] nums1, int[] nums2, int k) {

        int[] result = new int[k];

        int n1 = nums1.length;

        int n2 = nums2.length;

         

        // step 1: find the largest number from each array, and merge into one

        for (int i = Math.max(0, k - n2); i <= Math.min(n1, k); i++) {

            int[] list1 = findMax(nums1, i);

            int[] list2 = findMax(nums2, k - i);

             

            // then merge into one

            int[] curr = merge(list1, list2);

             

            if (greater(curr, 0, result, 0)) {

                result = curr;

            }

        }

         

        return result;

    }

     

    private int[] findMax(int[] nums, int k) {

        int[] result = new int[k];

         

        int n = nums.length;

        int len = 0;

        for (int i = 0; i < n; i++) {

            while (len > 0 && len + n - i > k && nums[i] > result[len - 1]) {

                len--;

            }

             

            if (len < k) {

                result[len] = nums[i];

                len++;

            }

        }

         

        return result;

    }

     

    private int[] merge(int[] list1, int[] list2) {

        int n1 = list1.length;

        int n2 = list2.length;

         

        int[] result = new int[n1 + n2];

         

        int i = 0; 

        int j = 0;

        int k = 0;

         

        while (k < n1 + n2) {

            if (greater(list1, i, list2, j)) {

                result[k++] = list1[i++];

            } else {

                result[k++] = list2[j++];

            }

        }

         

        return result;

    }

     

    private boolean greater(int[] list1, int pos1, int[] list2, int pos2) {

        int n1 = list1.length;

        int n2 = list2.length;

         

        while (pos1 < n1 && pos2 < n2 && list1[pos1] == list2[pos2]) {

            pos1++;

            pos2++;

        }

         

        if (pos2 == n2) {

            return true;

        }

         

        if (pos1 < n1 && list1[pos1] > list2[pos2]) {

            return true;

        }

         

        return false;

    }
http://www.jiuzhang.com/solutions/create-maximum-number/
    public int[] maxNumber(int[] nums1, int[] nums2, int k) {
        if (k == 0)
            return new int[0];

        int m = nums1.length, n = nums2.length;
        if (m + n < k) return null;
        if (m + n == k) {
            int[] results = merge(nums1, nums2, k);
            return results;
        } else {
            int max = m >= k ? k : m;
            int min = n >= k ? 0 : k - n;

            int[] results = new int[k];
            for(int i=0; i < k; ++i)
                results[i] = -0x7ffffff;
            for(int i = min; i <= max; ++i) {
                int[] temp = merge(getMax(nums1, i), getMax(nums2, k - i), k);
                results = isGreater(results, 0, temp, 0) ? results : temp;
            }
            return results;
        }
    }

    private int[] merge(int[] nums1, int[] nums2, int k) {
        int[] results = new int[k];
        if (k == 0) return results;
        int i = 0, j = 0;
        for(int l = 0; l < k; ++l) {
            results[l] = isGreater(nums1, i, nums2, j) ? nums1[i++] : nums2[j++];
        }
        return results;
    }

    private boolean isGreater(int[] nums1, int i, int[] nums2, int j) {
        for(; i < nums1.length && j < nums2.length; ++i, ++j) {
            if (nums1[i] > nums2[j])
                return true;
            if (nums1[i] < nums2[j])
                return false;
        }
        return i != nums1.length;
    }

    private int[] getMax(int[] nums, int k) {
        if (k == 0)
            return new int[0];
        int[] results = new int[k];
        int i = 0;
        for(int j = 0; j < nums.length; ++j) {
            while(nums.length - j + i > k && i > 0 && results[i-1] < nums[j])
                i--;
            if (i < k)
                results[i++] = nums[j];
        }
        return results;
    }

http://algobox.org/2015/12/24/create-maximum-number/
To solve this problem, first let’s look at simpler version:
Easy Version No. 1

Given one array of length n, create the maximum number of length k.
The solution to this problem is Greedy with the help of stack. The recipe is as following
Initialize a empty stack
Loop through the array nums
pop the top of stack if it is smaller than nums[i] until
stack is empty
the digits left is not enough to fill the stack to size k
if stack size < k push nums[i]
Return stack
Since the stack length is known to be k, it is very easy to use an array to simulate the stack.
The time complexity is O(n) since each element is at most been pushed and popped once.

    public int[] maxArray(int[] nums, int k) {

        int n = nums.length;

        int[] ans = new int[k];

        for (int i = 0, j = 0; i < n; ++i) {

            while (n - i + j > k && j > 0 && ans[j - 1] < nums[i]) j--;

            if (j < k) ans[j++] = nums[i];

        }

        return ans;

    }
Given two array of length m and n, create maximum number of length k = m + n.
OK, this version is a lot closer to our original problem with the exception that we will use all the digits we have.
Still, for this version, Greedy is the first thing come to mind. We have k decisions to make, each time will just need to decide ans[i] is from which of the two. It seems obvious, we should always choose the larger one right? This is correct, but the problem is what should we do if they are equal?
This is not so obvious. The correct answer is we need to see what behind the two to decide. For example,
nums1 = [6, 7]
nums2 = [6, 0, 4]
k = 5
ans = [6, 7, 6, 0, 4]
We decide to choose the 6 from nums1 at step 1, because 7 > 0. What if they are equal again? We continue to look the next digit until they are not equal. If all digits are equal then choose any one is ok. The procedure is like the merge in a merge sort. However due to the “look next until not equal”, the time complexity is O(nm).

private int[] merge(int[] nums1, int[] nums2, int k) {

    int[] ans = new int[k];

    for (int i = 0, j = 0, r = 0; r < k; ++r)

        ans[r] = greater(nums1, i, nums2, j) ? nums1[i++] : nums2[j++];

    return ans;

}

public boolean greater(int[] nums1, int i, int[] nums2, int j) {

    while (i < nums1.length && j < nums2.length && nums1[i] == nums2[j]) {

        i++;

        j++;

    }

    return j == nums2.length || (i < nums1.length && nums1[i] > nums2[j]);

}
Now let’s go back to the real problem. First, we divide the k digits required into two parts, i andk-i. We then find the maximum number of length i in one array and the maximum number of length k-i in the other array using the algorithm in section 1. Now we combine the two results in to one array using the algorithm in section 2. After that we compare the result with the result we have and keep the larger one as final answer.

    public int[] maxNumber(int[] nums1, int[] nums2, int k) {

        int n = nums1.length;

        int m = nums2.length;

        int[] ans = new int[k];        

        for (int i = Math.max(0, k - m); i <= k && i <= n; ++i) {

            int[] candidate = merge(maxArray(nums1, i), maxArray(nums2, k - i), k);

            if (greater(candidate, 0, ans, 0)) ans = candidate;

        }

        return ans;

    }
https://leetcode.com/discuss/75756/share-my-greedy-solution
The algorithm is O((m+n)^3) in the worst case. It runs in 22 ms.
It is worth to mention that, it is possible to achieve O(n + m) runtime for the merge function. In fact, what we need is the lexicographical order of all the suffixes of the two array parametersnums1 and nums2. This can be done in O(n + m) time by creating a suffix array on the concatenation of nums1 and nums2.
Therefore, the total runtime can be further improved to O((n+m)^2).
https://leetcode.com/discuss/75804/short-python-ruby-c
def maxNumber(self, nums1, nums2, k):

    def prep(nums, k):
        drop = len(nums) - k
        out = []
        for num in nums:
            while drop and out and out[-1] < num:
                out.pop()
                drop -= 1
            out.append(num)
        return out[:k]

    def merge(a, b):
        return [max(a, b).pop(0) for _ in a+b]

    return max(merge(prep(nums1, i), prep(nums2, k-i))
               for i in range(k+1)
               if i <= len(nums1) and k-i <= len(nums2))

Translated it to C++ as well now. Not as short anymore, but still decent. And C++ allows different functions with the same name, so I chose to do that here to show how nicely themaxNumber(nums1, nums2, k) problem can be based on the problems maxNumber(nums, k)and maxNumber(nums1, nums2), which would make fine problems on their own.
vector<int> maxNumber(vector<int>& nums1, vector<int>& nums2, int k) {
    int n1 = nums1.size(), n2 = nums2.size();
    vector<int> best;
    for (int k1=max(k-n2, 0); k1<=min(k, n1); ++k1)
        best = max(best, maxNumber(maxNumber(nums1, k1),
                                   maxNumber(nums2, k-k1)));
    return best;
}

vector<int> maxNumber(vector<int> nums, int k) {
    int drop = nums.size() - k;
    vector<int> out;
    for (int num : nums) {
        while (drop && out.size() && out.back() < num) {
            out.pop_back();
            drop--;
        }
        out.push_back(num);
  
    out.resize(k);
    return out;
}

vector<int> maxNumber(vector<int> nums1, vector<int> nums2) {
    vector<int> out;
    while (nums1.size() + nums2.size()) {
        vector<int>& now = nums1 > nums2 ? nums1 : nums2;
        out.push_back(now[0]);
        now.erase(now.begin());
    }
    return out;
}

http://www.cnblogs.com/EdwardLiu/p/5094248.html
https://leetcode.com/discuss/75668/share-my-21ms-java-solution-with-comments
递归写法TLE了。
问了一下CJL的思路~其实都差不多。我是直接原串找当前符合条件的最大的值，然后递归，他是枚举长度，然后找符合长度的。。。
第7个AC~哈哈~
枚举第一个数组A的个数i，那么数组B的个数就确定了 k -i。
然后枚举出A和B长度分别为i和k-i的最大子数组，然后组合看看哪个大。
组合的过程类似合并排序，但是要注意相等的情况要特别判断，需要找到后面不想等的元素的位置，看看哪个大，就选哪个。
Idea:
To find the maximum ,we can enumerate how digits we should get from nums1 , we suppose it is i.
So ,  the digits from nums2 is K – i.
Now the question is , how to get  maximum number(x digits) from an array. It is easy to deal with it.
get the 0~9 digits first occurrence
judge the max number whether it is legal or not
for example:
[2, 1, 2, 5, 8, 3] and i = 3
so we should get three digits.
and the max number is 8 , the index of 8 is 4, but 8 is not legal, because if we choose it , there are only one digital left.
So, we choose second number , it is 5, and is is legal.
OK, Once we choose two maximum subarray , we should combine it to the answer.
It is just like merger sort, but we should pay attention to the case: the two digital are equal.
we should find the digits behind it to judge which digital we should choose now.

    public int[] maxNumber(int[] nums1, int[] nums2, int k) {

        int get_from_nums1 = Math.min(nums1.length, k);

        int[] ans = new int[k];

        for (int i = Math.max(k - nums2.length, 0); i <= get_from_nums1; i++) {

            int[] res1 = new int[i];

            if (i > 0)

                res1 = solve(nums1, i);

            int[] res2 = new int[k - i];

            if (k - i > 0)

                res2 = solve(nums2, k - i);

            int pos1 = 0, pos2 = 0, tpos = 0;

            int[] res = new int[k];

            while (res1.length > 0 && res2.length > 0 && pos1 < res1.length && pos2 < res2.length) {

                if (res1[pos1] > res2[pos2])

                    res[tpos++] = res1[pos1++];

                else if (res1[pos1] < res2[pos2])

                    res[tpos++] = res2[pos2++];

                else {

                    int x = pos1;

                    int y = pos2;

                    while (x < res1.length && y < res2.length && res1[x] == res2[y]) {

                        x++;

                        y++;

                    }

                    if (x < res1.length && y < res2.length) {

                        if (res1[x] < res2[y]) {

                            res[tpos++] = res2[pos2++];

                        } else {

                            res[tpos++] = res1[pos1++];

                        }

                    } else if (x < res1.length) {

                        res[tpos++] = res1[pos1++];

                    } else {

                        res[tpos++] = res2[pos2++];

                    }

                }

            }

            while (pos1 < res1.length)

                res[tpos++] = res1[pos1++];

            while (pos2 < res2.length)

                res[tpos++] = res2[pos2++];


            if (updateAns(ans, res))

                ans = res;

        }

        return ans;

    }


    public boolean updateAns(int[] ans, int[] res) {

        for (int i = 0; i < ans.length; i++) {

            if (ans[i] > res[i])

                return false;

            if (ans[i] < res[i])

                return true;

        }

        return false;

    }


    public int[] solve(int[] nums, int k) {

        int[] res = new int[k];

        int len = 0;

        for (int i = 0; i < nums.length; i++) {

            while (len > 0 && len + nums.length - i > k && res[len - 1] < nums[i]) {

                len--;

            }

            if (len < k)

                res[len++] = nums[i];

        }

        return res;

    }
TODO:
https://leetcode.com/discuss/75655/strictly-o-nk-c-solution-with-detailed-explanation
https://leetcode.com/discuss/86359/my-java-solution-29-ms-passed-online-judge
http://www.cnblogs.com/CarryPotMan/p/5384172.html
 * @author het
 *
 */
public class LeetCode321 {
	
	public static int[] maxNumber(int[] nums1, int[] nums2, int k) {

        int[] ans = new int[k];

        for (int i = Math.max(k - nums2.length, 0); i <= Math.min(nums1.length, k); i++) {

            int[] res1 = get_max_sub_array(nums1, i);

            int[] res2 = get_max_sub_array(nums2, k - i);

            int[] res = new int[k];

            int pos1 = 0, pos2 = 0, tpos = 0;

 

            while (pos1 < res1.length || pos2 < res2.length) {

                res[tpos++] = greater(res1, pos1, res2, pos2) ? res1[pos1++] : res2[pos2++];

            }

 

            if (!greater(ans, 0, res, 0))

                ans = res;

        }

 

        return ans;

    }

 

    public static boolean greater(int[] nums1, int start1, int[] nums2, int start2) {

        for (; start1 < nums1.length && start2 < nums2.length; start1++, start2++) {

            if (nums1[start1] > nums2[start2]) return true;

            if (nums1[start1] < nums2[start2]) return false;

        }

        return start1 != nums1.length;

    }

 

    public static  int[] get_max_sub_array(int[] nums, int k) {

        int[] res = new int[k];

        int len = 0;

        for (int i = 0; i < nums.length; i++) {

            while (len > 0 && len + nums.length - i > k && res[len - 1] < nums[i]) {

                len--;

            }

            if (len < k)

                res[len++] = nums[i];

        }

        return res;

    }

	public static void main(String[] args) {
		int [] nums1 = {3, 4, 6, 5};
int [] nums2 = {9, 1, 2, 5, 8, 3};
int		k = 5;
//				return [9, 8, 6, 5, 3]
		// TODO Auto-generated method stub
		System.out.println(maxNumber(nums1, nums2, k));
//		nums1 = [3, 4, 6, 5]
//				nums2 = [9, 1, 2, 5, 8, 3]
//				k = 5
//				return [9, 8, 6, 5, 3]

	}

}
