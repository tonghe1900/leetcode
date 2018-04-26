package Leetcode600x;
/**
 * There are n different online courses numbered from 1 to n. Each course has some duration(course length) t and closed on dth day. A course should be taken continuously for t days and must be finished before or on the dth day. You will start at the 1st day.

Given n online courses represented by pairs (t,d), your task is to find the maximal number of courses that can be taken.

Example:
Input: [[100, 200], [200, 1300], [1000, 1250], [2000, 3200]]
Output: 3
Explanation: 
There're totally 4 courses, but you can take 3 courses at most:
First, take the 1st course, it costs 100 days so you will finish it on the 100th day, and ready to take the next course on the 101st day.
Second, take the 3rd course, it costs 1000 days so you will finish it on the 1100th day, and ready to take the next course on the 1101st day. 
Third, take the 2nd course, it costs 200 days so you will finish it on the 1300th day. 
The 4th course cannot be taken now, since you will finish it on the 3300th day, which exceeds the closed date.
Note:
The integer 1 <= d, t, n <= 10,000.
You can't take two courses simultaneously.
Seen this question in a real interview before? 
 * @author tonghe
 *
 */
public class Leetcode630 {
//https://leetcode.com/problems/course-schedule-iii/solution/
	public class Solution {
	    public int scheduleCourse(int[][] courses) {
	        Arrays.sort(courses, (a, b) -> a[1] - b[1]);
	        Integer[][] memo = new Integer[courses.length][courses[courses.length - 1][1] + 1];
	        return schedule(courses, 0, 0, memo);
	    }
	    public int schedule(int[][] courses, int i, int time, Integer[][] memo) {
	        if (i == courses.length)
	            return 0;
	        if (memo[i][time] != null)
	            return memo[i][time];
	        int taken = 0;
	        if (time + courses[i][0] <= courses[i][1])
	            taken = 1 + schedule(courses, i + 1, time + courses[i][0], memo);
	        int not_taken = schedule(courses, i + 1, time, memo);
	        memo[i][time] = Math.max(taken, not_taken);
	        return memo[i][time];
	    }
	}
	
	
	
	
	public class Solution {
	    public int scheduleCourse(int[][] courses) {
	        System.out.println(courses.length);
	        Arrays.sort(courses, (a, b) -> a[1] - b[1]);
	        int time = 0, count = 0;
	        for (int i = 0; i < courses.length; i++) {
	            if (time + courses[i][0] <= courses[i][1]) {
	                time += courses[i][0];
	                count++;
	            } else {
	                int max_i = i;
	                for (int j = 0; j < i; j++) {
	                    if (courses[j][0] > courses[max_i][0])
	                        max_i = j;
	                }
	                if (courses[max_i][0] > courses[i][0]) {
	                    time += courses[i][0] - courses[max_i][0];
	                }
	                courses[max_i][0] = -1;
	            }
	        }
	        return count;
	    }
	}
	
	
	
	

public class Solution {
    public int scheduleCourse(int[][] courses) {
        System.out.println(courses.length);
        Arrays.sort(courses, (a, b) -> a[1] - b[1]);
        int time = 0, count = 0;
        for (int i = 0; i < courses.length; i++) {
            if (time + courses[i][0] <= courses[i][1]) {
                time += courses[i][0];
                courses[count++] = courses[i];
            } else {
                int max_i = i;
                for (int j = 0; j < count; j++) {
                    if (courses[j][0] > courses[max_i][0])
                        max_i = j;
                }
                if (courses[max_i][0] > courses[i][0]) {
                    time += courses[i][0] - courses[max_i][0];
                    courses[max_i] = courses[i];
                }
            }
        }
        return count;
    }
}


public class Solution {
    public int scheduleCourse(int[][] courses) {
        Arrays.sort(courses, (a, b) -> a[1] - b[1]);
        List< Integer > valid_list = new ArrayList < > ();
        int time = 0;
        for (int[] c: courses) {
            if (time + c[0] <= c[1]) {
                valid_list.add(c[0]);
                time += c[0];
            } else {
                int max_i=0;
                for(int i=1; i < valid_list.size(); i++) {
                    if(valid_list.get(i) > valid_list.get(max_i))
                        max_i = i;
                }
                if (valid_list.get(max_i) > c[0]) {
                    time += c[0] - valid_list.get(max_i);
                    valid_list.set(max_i, c[0]);
                }
            }
        }
        return valid_list.size();
    }
}





public class Solution {
    public int scheduleCourse(int[][] courses) {
        Arrays.sort(courses, (a, b) -> a[1] - b[1]);
        PriorityQueue < Integer > queue = new PriorityQueue < > ((a, b) -> b - a);
        int time = 0;
        for (int[] c: courses) {
            if (time + c[0] <= c[1]) {
                queue.offer(c[0]);
                time += c[0];
            } else if (!queue.isEmpty() && queue.peek() > c[0]) {
                time += c[0] - queue.poll();
                queue.offer(c[0]);
            }
        }
        return queue.size();
    }
}
}
