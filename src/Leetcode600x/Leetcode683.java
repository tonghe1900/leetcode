package Leetcode600x;
/**
 * LeetCode 683. K Empty Slots(Java)
原创 2018年01月16日 09:29:16 标签：leetcode /谷歌 496
There is a garden with N slots. In each slot, there is a flower. The N flowers will bloom one by one in N days. In each day, there will be exactly one flower blooming and it will be in the status of blooming since then.

Given an array flowers consists of number from 1 to N. Each number in the array represents the place where the flower will open in that day.

For example, flowers[i] = x means that the unique flower that blooms at day i will be at position x, where i and x will be in the range from 1 to N.

Also given an integer k, you need to output in which day there exists two flowers in the status of blooming, and also the number of flowers between them is k and these flowers are not blooming.

If there isn’t such day, output -1.

Example 1:
Input: 
flowers: [1,3,2]
k: 1
Output: 2
1
2
3
4
5
Explanation: In the second day, the first and the third flower have become blooming.

Example 2:
Input: 
flowers: [1,2,3]
k: 1
Output: -1
1
2
3
4
5
Note: 
The given array will be in the range [1, 20000].

解法一：最优解法，时间复杂度为O(n)，空间复杂度为O(n)，思路是用额外空间记录一下1 ~ n个花槽，是第几天开花。再从头遍历，看看每隔K天两个花槽之间有没有更早的天数，没有则更新result，有则从天数最早的index向后走直到right走到最后。
class Solution {
    public int kEmptySlots(int[] flowers, int k) {
        int[] days = new int[flowers.length];
        for (int i = 0; i < flowers.length; i++) days[flowers[i] - 1] = i + 1;

        int left = 0, right = k + 1, result = Integer.MAX_VALUE;
        for (int i = 0; right < days.length; i++) {
            if (days[i] < days[left] || days[i] <= days[right]) {
                if (i == right)
                    result = Math.min(result, Math.max(days[left], days[right]));
                left = i;
                right = k + 1 + i;
            }
        }

        return (result == Integer.MAX_VALUE) ? -1 : result;
    }
}
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
解法二：用treemap – 内部排序的hashmap，时间复杂度是O(nlogn)，空间复杂度是O(n)。思路是遍历数组，每次都将花槽编号放进treeset，同时每次都检查到这天为止离此花槽编号最近的花槽编号，如果两个编号之间刚好相差k，那么就返回这时的天数。
    public int kEmptySlots(int[] flowers, int k) {
        int n = flowers.length;
        if (n == 1 && k == 0) return 1;
        TreeSet<Integer> sort = new TreeSet<>();
        for (int i = 0; i < n; ++i) {
            sort.add(flowers[i]);
            Integer min = sort.lower(flowers[i]);
            Integer max = sort.higher(flowers[i]);
            if (min != null && flowers[i] - min == k + 1) return i + 1;
            if (max != null && max - flowers[i] == k + 1) return i + 1;
        }
        return -1;
    }
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
解法三：用hashset，从头遍历数组，每次找相距k的花槽编号在不在hashset里，在的话检查直接有没有开花，没有则可以返回，这样的时间复杂度是O(kn)，空间复杂度是O(n)，但是LeetCode里TLE了，不过这里还是放出来吧，拓展一下思路。
public int kEmptySlots(int[] flowers, int k) {
        if (flowers.length == 0) return -1;
        HashSet<Integer> set = new HashSet<>();
        for (int i = 0; i < flowers.length; i++) {
            set.add(flowers[i]);
            int pre = flowers[i] - k - 1, next = flowers[i] + k + 1, tag = 0;
            if (pre >= 1 && set.contains(pre)) {
                for (int j = pre + 1; j < pre + 1 + k; j++) {
                    if (set.contains(j)) {
                        tag = 1;
                        break;
                    }
                }
                if (tag == 0) return i + 1;
            }
            tag = 0;
            if (next <= flowers.length && set.contains(next)) {
                for (int j = flowers[i] + 1; j < next; j++) {
                    while (set.contains(j)) {
                        tag = 1;
                        break;
                    }
                }
                if (tag == 0) return i + 1;
            }
        }
        return -1;
    }
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
解题思路：
树状数组（Fenwick Tree）

树状数组ft[k]存储前k个槽一共有多少朵花，则区间[m, n]的花朵总数 = ft[n] - ft[m - 1]

利用该数据结构，遍历flowers即可求解。

Python代码：
class Solution(object):
    def kEmptySlots(self, flowers, k):
        """
        :type flowers: List[int]
        :type k: int
        :rtype: int
        """
        maxn = max(flowers)
        nums = [0] * (maxn + 1)
        ft = FenwickTree(maxn)
        for i, v in enumerate(flowers):
            ft.add(v, 1)
            nums[v] = 1
            if v >= k and ft.sum(v) - ft.sum(v - k - 2) == 2 and nums[v - k - 1]:
                return i + 1
            if v + k + 1<= maxn and ft.sum(v + k + 1) - ft.sum(v - 1) == 2 and nums[v + k + 1]:
                return i + 1
        return -1 
 * @author tonghe
 *
 */
public class Leetcode683 {

}
