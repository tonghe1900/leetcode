package alite.leetcode.xx4.select;

import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

/**
 * http://bookshadow.com/weblog/2016/10/30/leetcode-sequence-reconstruction/
Check whether the original sequence org can be uniquely reconstructed from the sequences in seqs. 
The org sequence is a permutation of the integers from 1 to n, with 1 ≤ n ≤ 104. 
Reconstruction means building a shortest common supersequence of the sequences in seqs 
(i.e., a shortest sequence so that all sequences in seqs are subsequences of it). 
Determine whether there is only one sequence that can be reconstructed from seqs and it is the org sequence.
Example 1:
Input:
org: [1,2,3], seqs: [[1,2],[1,3]]

Output:
false

Explanation:
[1,2,3] is not the only one sequence that can be reconstructed, because [1,3,2] is also a valid sequence that can be reconstructed.
Example 2:
Input:
org: [1,2,3], seqs: [[1,2]]

Output:
false

Explanation:
The reconstructed sequence can only be [1,2].
Example 3:
Input:
org: [1,2,3], seqs: [[1,2],[1,3],[2,3]]

Output:
true

Explanation:
The sequences [1,2], [1,3], and [2,3] can uniquely reconstruct the original sequence [1,2,3].
Example 4:
Input:
org: [4,1,5,2,6,3], seqs: [[5,2,6,3],[4,1,5,2]]

Output:
true
解法I 拓扑排序（Topological Sort）
将seqs中的各序列seq按照其中元素出现的先后顺序建立有向图g。

例如seqs中的某序列seq = [1, 2, 3]，对应有向图，顶点为1, 2, 3；边为(1, 2), (2, 3)。

利用数组indeg记录各顶点的入度（indegree），sucset记录各顶点的后继（边）。

然后对图g执行拓扑排序，将得到的排序结果与原始序列org作比对即可。
    def sequenceReconstruction(self, org, seqs):
        """
        :type org: List[int]
        :type seqs: List[List[int]]
        :rtype: bool
        """
        size = len(org)
        indeg = [0] * size
        sucset = [set() for x in range(size)]
        if not seqs: return False
        for seq in seqs:
            if any(s > size or s < 1 for s in seq):
                return False
            for i in range(1, len(seq)):
                if seq[i] not in sucset[seq[i - 1] - 1]:
                    indeg[seq[i] - 1] += 1
                    sucset[seq[i - 1] - 1].add(seq[i])

        q = [i for i in org if not indeg[i - 1]]
        for x in range(size):
            if len(q) != 1 or q[0] != org[x]:
                return False
            nq = []
            for suc in sucset[q[0] - 1]:
                indeg[suc - 1] -= 1
                if not indeg[suc - 1]:
                    nq.append(suc)
            q = nq
        return True

https://discuss.leetcode.com/topic/65948/java-solution-using-bfs-topological-sort
http://www.geeksforgeeks.org/topological-sorting/
Time Complexity: The above algorithm is simply DFS with an extra stack. So time complexity is same as DFS which is O(V+E).
http://www.geeksforgeeks.org/topological-sorting-indegree-based-solution/
Time Complexity: The outer for loop will be executed V number of times and the inner for loop will be executed E number of times, Thus overall time complexity is O(V+E).
http://www.drdobbs.com/database/topological-sorting/184410262
The overall time complexity of this basic algorithm is O(n+m). The O(n) comes from the number of times that the while loop (and initial for loop) is executed, and the O(m) from the nested for loop.
Although there is no way to calculate how many times the inner loop will be executed on any one iteration of the outer loop, it will only be executed once for each successor of each member, which means that the total number of times that it will be executed is the total number of successors of all the members -- or the total number of relations.


Space complexity is also O(n+m). The O(n) component comes from the predecessor count information stored for each member, and the maximum length of the auxiliary queue. The O(m) comes from storing the successors for each member; once again, the total number of successors is the number of relations, so O(m).
    public boolean sequenceReconstruction(int[] org, int[][] seqs) {
        Map<Integer, Set<Integer>> map = new HashMap<>();
        Map<Integer, Integer> indegree = new HashMap<>();
        
        for(int[] seq: seqs) {
            if(seq.length == 1) { // no need to handle this specially
                if(!map.containsKey(seq[0])) {
                    map.put(seq[0], new HashSet<>());
                    indegree.put(seq[0], 0);
                }
            } else {
                for(int i = 0; i < seq.length - 1; i++) {
                    if(!map.containsKey(seq[i])) {
                        map.put(seq[i], new HashSet<>());
                        indegree.put(seq[i], 0);
                    }

                    if(!map.containsKey(seq[i + 1])) {
                        map.put(seq[i + 1], new HashSet<>());
                        indegree.put(seq[i + 1], 0);
                    }

                    if(map.get(seq[i]).add(seq[i + 1])) {
                        indegree.put(seq[i + 1], indegree.get(seq[i + 1]) + 1);
                    }
                }
            }
        }

        Queue<Integer> queue = new LinkedList<>();
        for(Map.Entry<Integer, Integer> entry: indegree.entrySet()) {
            if(entry.getValue() == 0) queue.offer(entry.getKey());
        }

        int index = 0;
        while(!queue.isEmpty()) {
            int size = queue.size();
            if(size > 1) return false; //\\
            int curr = queue.poll();
            if(index == org.length || curr != org[index++]) return false;
            for(int next: map.get(curr)) {
                indegree.put(next, indegree.get(next) - 1);
                if(indegree.get(next) == 0) queue.offer(next);
            }
        }
        return index == org.length && index == map.size();
    }
https://discuss.leetcode.com/topic/65948/java-solution-using-bfs-topological-sort/5
public boolean sequenceReconstruction(int[] org, int[][] seqs) {
    Map<Integer, List<Integer>> graph = new HashMap<>();
    Map<Integer, Integer> indegree = new HashMap<>();
    for (int[] seq : seqs) {
        for (int i = 0; i < seq.length; i++) {
            graph.putIfAbsent(seq[i], new ArrayList<Integer>());
            indegree.putIfAbsent(seq[i], 0);
            if (i > 0) {
                graph.get(seq[i - 1]).add(seq[i]);
                indegree.put(seq[i], indegree.get(seq[i]) + 1);
            }
        }
    }
    if (org.length != indegree.size()) {
        return false;
    }
    
    Queue<Integer> q = new LinkedList<>();
    for (Map.Entry<Integer, Integer> entry : indegree.entrySet()) {
        if (entry.getValue() == 0) {
            q.add(entry.getKey());
        }
    }
    
    int index = 0;
    while (!q.isEmpty()) {
        if (q.size() > 1) {
            return false;
        }
        int curr = q.poll();
        if (org[index++] != curr) {
            return false;
        }
        for (int nb : graph.get(curr)) {
            indegree.put(nb, indegree.get(nb) - 1);
            if (indegree.get(nb) == 0) {
                q.add(nb);
            }
        }
    }
    return index == org.length;
}
解法II “边检查法”
首先建立原始序列org中各元素到其下标的映射indices。

遍历seqs，记当前序列为seq，遍历seq；

记seq中相邻元素为pre, cur，若indices[pre] > indices[cur]，说明与org中各元素的前后关系产生矛盾，返回False

否则，将(pre, cur)加入边集edges。

最后遍历org，判断其中两两相邻元素构成的边是否都在edges中，若是返回True，否则返回False。
    def sequenceReconstruction(self, org, seqs):
        """
        :type org: List[int]
        :type seqs: List[List[int]]
        :rtype: bool
        """
        indices = {e : i for i, e in enumerate(org)}
        edges = set()

        if not seqs: return False
        for seq in seqs:
            for s in seq:
                if s not in indices:
                    return False
            for i in range(1, len(seq)):
                pre, cur = seq[i - 1], seq[i]
                if indices[pre] > indices[cur]:
                    return False
                edges.add((pre, cur))

        for x in range(1, len(org)):
            if (org[x - 1], org[x]) not in edges:
                return False
        return True

https://discuss.leetcode.com/topic/65633/very-short-solution-with-explanation
For org to be uniquely reconstructible from seqs we need to satisfy 2 conditions:
Every sequence in seqs should be a subsequence in org. This part is obvious.
Every 2 consecutive elements in org should be consecutive elements in some sequence from seqs. Why is that? Well, suppose condition 1 is satisfied. Then for 2 any consecutive elements x and y in org we have 2 options.
We have both xand y in some sequence from seqs. Then (as condition 1 is satisfied) they must be consequtive elements in this sequence.
There is no sequence in seqs that contains both x and y. In this case we cannot uniquely reconstruct org from seqsas sequence with x and y switched would also be a valid original sequence for seqs.
So this are 2 necessary criterions. It is pretty easy to see that this are also sufficient criterions for org to be uniquely reconstructible (there is only 1 way to reconstruct sequence when we know that condition 2 is satisfied).
To implement this idea I have idxs hash that maps item to its index in org sequence to check condition 1. And I have pairs set that holds all consequitive element pairs for sequences from seqs to check condition 2 (I also consider first elements to be paired with previous undefined elements, it is necessary to check this).
public boolean sequenceReconstruction(int[] org, int[][] seqs) {
        if(seqs == null || seqs.length == 0){
            return false;
        }
        int orgLen = org.length;
        int[] idx = new int[orgLen + 1];
        boolean[] pairs = new boolean[orgLen];
        
        for(int i = 0; i < orgLen; i++){
            idx[org[i]] = i;
        }
        
        for(int[] seq : seqs){
            for(int i = 0; i < seq.length; i++){
                if(seq[i] > orgLen || seq[i] < 0){
                    return false;
                }
                
                if(i > 0 && idx[seq[i - 1]] >= idx[seq[i]]){
                    return false;
                }
                
                if(i > 0 && idx[seq[i - 1]] + 1 == idx[seq[i]]){
                    pairs[idx[seq[i - 1]]] = true;
                }
            }
        }
        
        for(int i = 0; i < orgLen - 1; i++){
            if(!pairs[i]){
                return false;
            }
        }
        
        return true;
    }

https://discuss.leetcode.com/topic/65961/simple-solution-one-pass-using-only-array-c-92ms-java-16ms/
After reading a few posts on this topic, I guess most of people over-think about this problem.
IMHO, We don't need to implement any graph theory expressively here; rather, it is sufficient to just check if every two adjacent elements also appears adjacently in the sub-sequences. (and of course, some basic boundary checking is also necessary)
    public boolean sequenceReconstruction(int[] org, int[][] seqs) {
        if(seqs.length == 0) return false; 
        int[] pos = new int[org.length+1];
        for(int i=0;i<org.length;++i) pos[org[i]] = i;
        boolean[] flags = new boolean[org.length+1];
        int toMatch = org.length-1;
        for(int[] v : seqs) {
            for(int i=0;i<v.length;++i) {
                if(v[i]<=0 || v[i] > org.length)return false;
                if(i==0)continue;
                int x = v[i-1], y = v[i];
                if(pos[x] >= pos[y])return false;//\\
                if(flags[x] == false && pos[x]+1 == pos[y]) {
                    flags[x] = true;
                    --toMatch;
                }
            }
        }
        return toMatch == 0;
    }
https://discuss.leetcode.com/topic/66338/java-o-n-time-o-n-space-ac-solution-14ms-like-count-sort
The basic idea is to count how many numbers are smaller(self include) than the current number.
We then compare this count to the org.
It is pretty like the idea of count sort.
    public boolean sequenceReconstruction(int[] org, int[][] seqs) {
        int len = org.length;
        int[] map = new int[len + 1];//map number to its index
        Arrays.fill(map, -1);
        int[] memo = new int[org.length];//count how many numbers are smaller(on the right)
        for (int i = 0; i < len; i++) {
            map[org[i]] = i;
        }
        for (int[] seq : seqs) {
            if (seq.length == 0) continue;
            int prev = seq[0];
            if (prev <= 0 || prev > len || map[prev] == -1) return false;
            for (int i = 1; i < seq.length; i++) {
                int curr = seq[i];
                if (curr <= 0 || curr > len || map[curr] == -1) return false;
                memo[map[prev]] = Math.max(memo[map[prev]], len - map[curr] + 1);
                prev = curr;
            }
            memo[map[prev]] = Math.max(memo[map[prev]], 1);
        }
        for (int i = 0; i < memo.length; i++) {
            if (memo[i] != len - i) return false;
        }
        return true;
    }
 * @author het
 *
 */
public class LeetCode444SequenceReconstruction {
	 public static boolean sequenceReconstruction(int[] org, int[][] seqs) {
	        Map<Integer, Set<Integer>> map = new HashMap<>();
	        Map<Integer, Integer> indegree = new HashMap<>();
	        
	        for(int[] seq: seqs) {
	            if(seq.length == 1) { // no need to handle this specially
	                if(!map.containsKey(seq[0])) {
	                    map.put(seq[0], new HashSet<Integer>());
	                    indegree.put(seq[0], 0);
	                }
	            } else {
	                for(int i = 0; i < seq.length - 1; i++) {
	                    if(!map.containsKey(seq[i])) {
	                        map.put(seq[i], new HashSet<Integer>());
	                        indegree.put(seq[i], 0);
	                    }

	                    if(!map.containsKey(seq[i + 1])) {
	                        map.put(seq[i + 1], new HashSet<Integer>());
	                        indegree.put(seq[i + 1], 0);
	                    }

	                    if(map.get(seq[i]).add(seq[i + 1])) {
	                        indegree.put(seq[i + 1], indegree.get(seq[i + 1]) + 1);
	                    }
	                }
	            }
	        }

	        Deque<Integer> queue = new LinkedList<>();
	        for(Map.Entry<Integer, Integer> entry: indegree.entrySet()) {
	            if(entry.getValue() == 0) queue.offer(entry.getKey());
	        }

	        int index = 0;
	        while(!queue.isEmpty()) {
	            int size = queue.size();
	            if(size > 1) return false; //\\
	            int curr = queue.poll();
	            if(index == org.length || curr != org[index++]) return false;
	            for(int next: map.get(curr)) {
	                indegree.put(next, indegree.get(next) - 1);
	                if(indegree.get(next) == 0) queue.offer(next);
	            }
	        }
	        return index == org.length && index == map.size();
	    }
	public static void main(String[] args) {
		int[] org = {1,2,3};int[][] seqs ={{1,2},{1,3},{2,3}};
		System.out.println(sequenceReconstruction(org, seqs));
		// TODO Auto-generated method stub
		//org: [1,2,3], seqs: [[1,2],[1,3]]

//Output:
//false
//
//Explanation:
//[1,2,3] is not the only one sequence that can be reconstructed, because [1,3,2] is also a valid sequence that can be reconstructed.
//Example 2:
//Input:
//org: [1,2,3], seqs: [[1,2]]
//
//Output:
//false
//
//Explanation:
//The reconstructed sequence can only be [1,2].
//Example 3:
//Input:
//org: [1,2,3], seqs: [[1,2],[1,3],[2,3]]

	}

}
