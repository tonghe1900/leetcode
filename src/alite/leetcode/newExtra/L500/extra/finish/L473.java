package alite.leetcode.newExtra.L500.extra.finish;
/**
 * LeetCode 473 - Matchsticks to Square

http://bookshadow.com/weblog/2016/12/18/leetcode-matchsticks-to-square/
Remember the story of Little Match Girl? By now, you know exactly what matchsticks the little match girl has, please find out 
a way you can make one square by using up all those matchsticks. You should not break any stick, but you can link them up, and
 each matchstick must be used exactly one time.
Your input will be several matchsticks the girl has, represented with their stick length. Your output will either be true or false,
 to represent whether you can save this little girl or not.
Example 1:
Input: [1,1,2,2,2]
Output: true

Explanation: You can form a square with length 2, one side of the square came two sticks with length 1.
Example 2:
Input: [3,3,3,3,4]
Output: false

Explanation: You cannot find a way to form a square with all the matchsticks.
Note:
The length sum of the given matchsticks is in the range of 0 to 10^9.
The length of the given matchstick array will not exceed 15.

记忆化搜索
利用辅助数组dp[i][j]记录是否可以用火柴棍数组i，拼接成j条长度相等的边。

    def makesquare(self, nums):
        """
        :type nums: List[int]
        :rtype: bool
        """
        if sum(nums) % 4: return False
        self.q = sum(nums) / 4
        self.dp = collections.defaultdict(lambda: collections.defaultdict())
        return self.solve(nums, 4)
        
    def solve(self, nums, part):
        total = sum(nums)
        if part == 1:
            return total == self.q
        size = len(nums)
        if size < part or total % part:
            return False
        tnums = tuple(nums)
        if part in self.dp[tnums]:
            return self.dp[tnums][part]
        for x in range(1, (1 << size) - 1):
            left, right = [], []
            for y in range(size):
                if x & (1 << y):
                    left.append(nums[y])
                else:
                    right.append(nums[y])
            if sum(left) != self.q:
                continue
            if self.solve(right, part - 1):
                self.dp[tnums][part] = True
                return True
        self.dp[tnums][part] = False
        return False

 * @author het
 *
 */
public class L473 {

}
