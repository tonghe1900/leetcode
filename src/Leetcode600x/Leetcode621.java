package Leetcode600x;
/**
 * Given a char array representing tasks CPU need to do. It contains capital letters A to Z where different letters represent different tasks.Tasks could be done without original order. Each task could be done in one interval. For each interval, CPU could finish one task or just be idle.

However, there is a non-negative cooling interval n that means between two same tasks, there must be at least n intervals that CPU are doing different tasks or just be idle.

You need to return the least number of intervals the CPU will take to finish all the given tasks.

Example 1:
Input: tasks = ["A","A","A","B","B","B"], n = 2
Output: 8
Explanation: A -> B -> idle -> A -> B -> idle -> A -> B.
Note:
The number of tasks is in the range [1, 10000].
The integer n is in the range [0, 100].
Seen this question in a real interview before?


https://leetcode.com/problems/task-scheduler/solution/



 * @author tonghe
 *
 */
public class Leetcode621 {
	public class Solution {
	    public int leastInterval(char[] tasks, int n) {
	        int[] map = new int[26];
	        for (char c: tasks)
	            map[c - 'A']++;
	        Arrays.sort(map);
	        int time = 0;
	        while (map[25] > 0) {
	            int i = 0;
	            while (i <= n) {
	                if (map[25] == 0)
	                    break;
	                if (i < 26 && map[25 - i] > 0)
	                    map[25 - i]--;
	                time++;
	                i++;
	            }
	            Arrays.sort(map);
	        }
	        return time;
	    }
	}
	
	
	public class Solution {
	    public int leastInterval(char[] tasks, int n) {
	        int[] map = new int[26];
	        for (char c: tasks)
	            map[c - 'A']++;
	        PriorityQueue < Integer > queue = new PriorityQueue < > (26, Collections.reverseOrder());
	        for (int f: map) {
	            if (f > 0)
	                queue.add(f);
	        }
	        int time = 0;
	        while (!queue.isEmpty()) {
	            int i = 0;
	            List < Integer > temp = new ArrayList < > ();
	            while (i <= n) {
	                if (!queue.isEmpty()) {
	                    if (queue.peek() > 1)
	                        temp.add(queue.poll() - 1);
	                    else
	                        queue.poll();
	                }
	                time++;
	                if (queue.isEmpty() && temp.size() == 0)
	                    break;
	                i++;
	            }
	            for (int l: temp)
	                queue.add(l);
	        }
	        return time;
	    }
	}

	
	
	public class Solution {
	    public int leastInterval(char[] tasks, int n) {
	        int[] map = new int[26];
	        for (char c: tasks)
	            map[c - 'A']++;
	        Arrays.sort(map);
	        int max_val = map[25] - 1, idle_slots = max_val * n;
	        for (int i = 24; i >= 0 && map[i] > 0; i--) {
	            idle_slots -= Math.min(map[i], max_val);
	        }
	        return idle_slots > 0 ? idle_slots + tasks.length : tasks.length;
	    }
	}
}
