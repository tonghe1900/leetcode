package alite.leetcode.newest;
/**
 * LeetCode 659 - Split Array into Consecutive Subsequences


https://leetcode.com/problems/split-array-into-consecutive-subsequences/
You are given an integer array sorted in ascending order (may contain duplicates), you need to split them into several subsequences, where each subsequences consist of at least 3 consecutive integers. Return whether you can make such a split.
Example 1:
Input: [1,2,3,3,4,5]
Output: True
Explanation:
You can split them into two consecutive subsequences : 
1, 2, 3
3, 4, 5

Example 2:
Input: [1,2,3,3,4,4,5,5]
Output: True
Explanation:
You can split them into two consecutive subsequences : 
1, 2, 3, 4, 5
3, 4, 5

Example 3:
Input: [1,2,3,4,4,5]
Output: False

Note:
1. The length of the input is in range of [1, 10000]
http://bookshadow.com/weblog/2017/08/13/leetcode-split-array-into-consecutive-subsequences/
Map + PriorityQueue
思路类似于排列扑克牌，优先将数字排在长度较小的扑克牌队列后面
Map<Integer, PriorityQueue<Integer>> dmap的key为扑克牌队列的末尾，value为优先队列，存储扑克牌队列的长度
    private HashMap<Integer, PriorityQueue<Integer>> dmap;
    public boolean isPossible(int[] nums) {
        dmap = new HashMap<>();
        for (int num : nums) {
            PriorityQueue<Integer> pq0 = getOrPut(num - 1);
            int len = pq0.isEmpty() ? 0 : pq0.poll();
            PriorityQueue<Integer> pq1 = getOrPut(num);
            pq1.offer(len + 1);
        }
        for (int key : dmap.keySet()) {
            for (int len : dmap.get(key)) {
                if (len < 3) return false;
            }
        }
        return true;
    }
    public PriorityQueue<Integer> getOrPut(int num) {
        PriorityQueue<Integer> pq = dmap.get(num);
        if (pq == null) {
            pq = new PriorityQueue<Integer>();
            dmap.put(num, pq);
        }
        return pq;
    }
http://blog.csdn.net/u014688145/article/details/77148515
思路总结：把每个独立的片段先求出来，接着考虑如何拼凑在一起，因为只能往前拼凑，所以解是唯一的，模拟就好了。
此题相对较难，要抓住一些性质不容易，采用模拟，模拟的策略很有意思，首先按要求求出至少三个连续序列组成的所有情况，接着就是【拼接】了。
很有意思的是，在写代码时，先考虑拼接，最后才考虑是否单独（即分桶摆放）摆放。
像此题，我的思路是从频次最大的那个数开始着手，先假设某个数num的频次最大，因此，一定会从num开始，找到num+1，和num+2，组成至少三个序列的情况，如果不存在则可以直接返回false。
那么问题来了，num+1，num+2的频次一定小于等于num，遇到不能分配的情况该怎么办？啥时候是不能分配的时候？
条件： num 还存在待分配的名额时，num+2的个数为0
所以不管num + 1是否被分配完毕，我们都需要将剩余的num 和 num + 1拼接到之前的某个桶的结尾处。
如果找不到这样的桶，同样返回false，否则拼接后，更新桶的最后一个元素。（map实现）
拼谁？一定是num，拼完num，该桶下一步就应该拼num+1的元素，此时再把num+1放进去，两步完成了num和num+1的拼接，完美。

    public boolean isPossible(int[] nums) {
        TreeMap<Integer, Integer> map = new TreeMap<>();
        Map<Integer, Integer> append = new HashMap<>();
        for (int num : nums) map.put(num, map.getOrDefault(num, 0) + 1);
        for (int i : nums){
            if (map.get(i) == 0) continue;
            if (append.getOrDefault(i, 0) > 0){  // 先拼接
                append.put(i, append.get(i) - 1);
                append.put(i + 1, append.getOrDefault(i + 1, 0) + 1);
            }
            else if (map.getOrDefault(i + 1, 0) > 0 && map.getOrDefault(i + 2, 0) > 0){ // 再独立
                map.put(i + 1, map.get(i + 1) - 1);
                map.put(i + 2, map.get(i + 2) - 1);
                append.put(i + 3, append.getOrDefault(i + 3, 0) + 1);
            }
            else return false;
            map.put(i, map.get(i) - 1);
        }
        return true;
    }
X. O(n) time, space
https://discuss.leetcode.com/topic/99187/java-o-n-time-o-n-space
1. We iterate through the array once to get the frequency of all the elements in the array
2. We iterate through the array once more and for each element we either see if it can be appended to a previously constructed consecutive sequence or if it can be the start of a new consecutive sequence. If neither are true, then we return false.
public boolean isPossible(int[] nums) {
    Map<Integer, Integer> freq = new HashMap<>(), appendfreq = new HashMap<>();
    for (int i : nums) freq.put(i, freq.getOrDefault(i,0) + 1);
    for (int i : nums) {
        if (freq.get(i) == 0) continue;
        else if (appendfreq.getOrDefault(i,0) > 0) {
            appendfreq.put(i, appendfreq.get(i) - 1);
            appendfreq.put(i+1, appendfreq.getOrDefault(i+1,0) + 1);
        }   
        else if (freq.getOrDefault(i+1,0) > 0 && freq.getOrDefault(i+2,0) > 0) {
            freq.put(i+1, freq.get(i+1) - 1);
            freq.put(i+2, freq.get(i+2) - 1);
            appendfreq.put(i+3, appendfreq.getOrDefault(i+3,0) + 1);
        }
        else return false;
        freq.put(i, freq.get(i) - 1);
    }
    return true;
}
https://discuss.leetcode.com/topic/99495/java-o-n-time-o-1-space-solution
The basic idea is that, for each distinct element ele in the input array, we only need to maintain three pieces of information: the number of consecutive subsequences ending at ele with length of 1, length of 2 and length >= 3.
The input array will be scanned linearly from left to right. Let cur be the element currently being examined and cnt as its number of appearance. pre is the element processed immediately before cur. The number of consecutive subsequences ending at pre with length of 1, length of 2 and length >= 3 are denoted as p1, p2 and p3, respectively. There are two cases in general:
1. cur != pre + 1: for this case, cur cannot be added to any consecutive subsequences ending at pre, therefore, we must have p1 == 0 && p2 == 0; otherwise the input array cannot be split into consecutive subsequences of length >= 3. Now let c1, c2, c3 be the number of consecutive subsequences ending at cur with length of 1, length of 2 and length >= 3, respectively, we will have c1 = cnt, c2 = 0, c3 = 0, which means we only have consecutive subsequence ending at curwith length of 1 and its number given by cnt. 
2. cur == pre + 1: for this case, cur can be added to consecutive subsequences ending at pre and thus extend those subsequences. But priorities should be given to those with length of 1 first, then length of 2 and lastly length >= 3. Also we must have cnt >= p1 + p2; otherwise the input array cannot be split into consecutive subsequences of length >= 3. Again let c1, c2, c3 be the number of consecutive subsequences ending at cur with length of 1, length of 2 and length >= 3, respectively, we will have: c2 = p1, c3 = p2 + min(p3, cnt - (p1 + p2)), c1 = max(cnt - (p1 + p2 + p3), 0). The meaning is as follows: first adding cur to the end of subsequences of length 1 will make them subsequences of length 2, and we have p1 such subsequences, therefore c2 = p1. Then adding cur to the end of subsequences of length 2 will make them subsequences of length 3, and we have p2 such subsequences, therefore c3 is at least p2. If cnt > p1 + p2, we can add the remaining cur to the end of subsequences of length >= 3 to make them even longer subsequences. The number of such subsequences is the smaller one of p3 and cnt - (p1 + p2). In total, c3 = p2 + min(p3, cnt - (p1 + p2)). If cnt > p1 + p2 + p3, then we still have remaining cur that cannot be added to any subsequences. These residue cur will form subsequences of length 1, hence c1 = max(cnt - (p1 + p2 + p3), 0). 
For either case, we need to update: pre = cur, p1 = c1, p2 = c2, p3 = c3 after processing the current element. When all the elements are done, we check the values of p1 and p2. The input array can be split into consecutive subsequences of length >= 3 if and only if p1 == 0 && p2 == 0.




You might also like
* 		

 * @author het
 *
 */
public class LeetCode659 {

}
