package alite.leetcode.newest;
/**
 * LeetCode 630 - Course Schedule III

https://leetcode.com/problems/course-schedule-iii
here are n different online courses numbered from 1 to n. Each course has some duration(course length) t and closed on dthday. A course should be taken continuously for t days and must be finished before or on the dth day. You will start at the 1st day.
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

https://leetcode.com/articles/course-schedule-iii/
X. Using Priority Queue
In the last few approaches, we've seen that we needed to traverse over the courses which have been taken to find the course(with the maximum duration) which can be replaced by the current course(if it can't be taken directly). These traversals can be saved, if we make use of a Priority Queue, queuequeue(which is implemented as a Max-Heap) which contains the durations of all the courses that have been taken till now.
Time complexity : O\big(nlog(n)\big)O(nlog(n)). At most nn elements are added to the queuequeue. Adding each element is followed by heapification, which takes O\big(log(n)\big)O(log(n)) time.
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
https://discuss.leetcode.com/topic/93712/python-straightforward-with-explanation
https://discuss.leetcode.com/topic/93790/short-java-code-using-priorityqueue
Sort all the courses by their ending time. When considering the first K courses, they all end before end. A necessary and sufficient condition for our schedule to be valid, is that (for all K), the courses we choose to take within the first K of them, have total duration less than end.
For each K, we will greedily remove the largest-length course until the total duration start is <= end. To select these largest-length courses, we will use a max heap. start will maintain the loop invariant that it is the sum of the lengths of the courses we have currently taken.
Clearly, this greedy choice makes the number of courses used maximal for each K. When considering potential future K, there's never a case where we preferred having a longer course to a shorter one, so indeed our greedy choice dominates all other candidates.
We can safely replace the while statement at while (start > course[1]) by if statement, and it will still work. This is because each time we only need to remove at most one element from the pq.


    public int scheduleCourse(int[][] courses) {
        int n = courses.length;
        if (n == 0) return 0;
        Arrays.sort(courses, (a, b) ->  a[1] - b[1]);
        PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> b - a);
        int start = 0;
        for (int[] course: courses) {
            start += course[0];
            pq.offer(course[0]);
            if (start > course[1]) {
                start -= pq.poll();
            }
        }        
        return pq.size();
    } 
    public int scheduleCourse(int[][] courses) {
        Arrays.sort(courses,(a,b)->a[1]-b[1]); //Sort the courses by their deadlines (Greedy! We have to deal with courses with early deadlines first)
        PriorityQueue<Integer> pq=new PriorityQueue<>((a,b)->b-a);
        int time=0;
        for (int[] c:courses) 
        {
            time+=c[0]; // add current course to a priority queue
            pq.add(c[0]);
            if (time>c[1]) time-=pq.poll(); //If time exceeds, drop the previous course which costs the most time. (That must be the best choice!)
        }        
        return pq.size();
    }
http://bookshadow.com/weblog/2017/06/25/leetcode-course-schedule-iii/
课程根据最迟完成时间从小到大排序
遍历课程，利用优先队列（时长最大堆）维护当前满足最迟完成时间约束的课程时长，弹出不满足约束条件的课程时长
返回优先队列的长度
http://blog.csdn.net/zjucor/article/details/73719072
其实排完序后可以one pass就解决了，遍历一遍，每次取到当前位置最贪心的情况，何为最贪心？就是选的课程数目最大，在课程数目一样的情况下，结束时间最早（这样就有利于后面的选课），为什么这样贪心是全局最优解呢？假设我们求得i位置的最贪心结果，现在要求i+1位置的最贪心结果
1. 当i+1课程能选我们就先选，贪心使然
2. 如果不能选，我们就尽量把修完课程的时间降到最小（把课程安排的最紧凑），即如果当前课程需要的duration比之前能选的课程最大的duration小，那我们情愿选当前这个，因为这样到目前为止，这样是选课程数目最大，结束时间最早的最优选择（即最贪心的选择），而且一定选的上，因为当前课程结束时间比前面的都大（排过序的），
而且删除之前duration最大也一定只能再加第i+1课程，i+1之前的一定不能加，否则违背之前是最紧凑的贪心情况这一约束

至于怎么快速求出之前最大的duration，用优先队列
    public int scheduleCourse(int[][] courses) {  
        Arrays.sort(courses, new Comparator<int[]>(){  
            @Override  
            public int compare(int[] a, int[] b) {  
                return a[1] - b[1]; // 相等再按照默认第一位排序  
            }  
        });  
          
        // 把最大的duration放在顶端，这样当来一个课程放不下时就把最大的duration弹出  
        PriorityQueue<Integer> pq = new PriorityQueue<Integer>(10, new Comparator<Integer>(){  
            @Override  
            public int compare(Integer o1, Integer o2) {  
                return o2-o1;  
            }  
        });  
          
        int time = 0;  
        for(int[] c : courses) {  
            // 能插进pq就插，不能就尽量把pq弄紧凑点  
            if(time + c[0] <= c[1]) {  
                pq.add(c[0]);  
                time += c[0];  
            } else if(!pq.isEmpty() && pq.peek() > c[0]) {  
                time += c[0] - pq.poll();  
                pq.add(c[0]);  
            }  
        }  
          
        return pq.size();  
    } 
X. DFS
From the above example, we can conclude that it is always profitable to take the course with a smaller end day prior to a course with a larger end day. This is because, the course with a smaller duration, if can be taken, can surely be taken only if it is taken prior to a course with a larger end day.
Based on this idea, firstly, we sort the given coursescourses array based on their end days. Then, we try to take the courses in a serial order from this sorted coursescourses array.
we make use of a recursive function schedule(courses, i, time) which returns the maximum number of courses that can be taken starting from the i^{th}i
​th
​​  course(starting from 0), given the time aleady consumed by the other courses is timetime, i.e. the current time is timetime, given a coursescourses array as the schedule.
Now, in each function call to schedule(courses, i, time), we try to include the current course in the taken courses. But, this can be done only if time + duration_i < end\_day_itime+duration
​i
​​ <end_day
​i
​​ .

Time complexity : O(2^n)O(2
​n
​​ ). Size of recursion tree will be 2^n2
​n
​​ .
    public int scheduleCourse(int[][] courses) {
        Arrays.sort(courses, (a, b) -> a[1] - b[1]);
        return schedule(courses, 0, 0);
    }
    public int schedule(int[][] courses, int i, int time) {
        if (i == courses.length)
            return 0;
        int taken = 0;
        if (time + courses[i][0] <= courses[i][1])
            taken = 1 + schedule(courses, i + 1, time + courses[i][0]);
        int not_taken = schedule(courses, i + 1, time);
        return Math.max(taken, not_taken);
    }

DFS+ Cache
Time complexity : O(n*d)O(n∗d). memomemo array of size nnxdd is filled once. Here, nn refers to the number of courses in the given coursescourses array and dd refers to the maximum value of the end day from all the end days in the coursescourses array.
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

X.
But, if we aren't able to take the current course i.e. 
t
i
m
e
+
d
u
r
a
t
i
o
n
i
>
e
n
d
\day
i
time+durationi>end\dayi, we can try to take this course by removing some other course from amongst the courses that have already been taken. But, this course can the current course can fit in, only if the duration of the course(j^{th}j
​th
​​ ) being removed duration_jduration
​j
​​  is larger than the current course's duration, duration_iduration
​i
​​  i.e. duration_j > duration_iduration
​j
​​ >duration
​i
​​ . We are sure of the fact that by removing the j^{th}j
​th
​​  course, we can fit in the current course, because, course_jcourse
​j
​​  was already fitting in the duration available till now. Since, duration_i < duration_jduration
​i
​​ <duration
​j
​​ , the current course can surely take its place. Thus, we look for a course from amongst the taken courses having a duration larger than the current course.

But why are we doing this replacement? The answer to this question is as follows. By replacing the j^{th}j
​th
​​  course, with the i^{th}i
​th
​​  course of a relatively smaller duration, we can increase the time available for upcoming courses to be taken. An extra duration_j - duration_iduration
​j
​​ −duration
​i
​​  time can be made available by doing so. Now, for this saving in time to be maximum, the course taken for the replacement should be the one with the maximum duration. Thus, from amongst the courses that have been taken till now, we find the course having the maximum duration which should be more than the duration of the current course(which can't be taken). Let's say, this course be called as max\_imax_i. Thus, now, a saving of duration_{max\_i} - duration_iduration
​max_i
​​ −duration
​i
​​  can be achived, which could help later in fitting in more courses to be taken.
If such a course, max\_imax_i, is found, we remove this course from the taken courses and consider the current course as taekn. We also mark this course with \text{-1}-1to indicate that this course has not been taken and should not be considered in the future again for replacement. But, if such a course isn't found, we can't take the current course at any cost. Thus, we mark the current course with \text{-1}-1 to indicate that the current course has not been taken.
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
Time complexity : O(n*count)O(n∗count). We iterate over a total of nn elements of the coursescourses array. For every element, we can traverse backwards upto atmost countcount(final value) number of elements.
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

Time complexity : O(n*m)O(n∗m). We iterate over a total of nn elements of the coursescourses array. For every element, we can traverse over atmost mm number of elements. Here, mm refers to the final length of the valid\_listvalid_list.
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
http://blog.csdn.net/sinat_14826343/article/details/73739638
    def scheduleCourse(self, courses):
        #用于查找list中的元素//二分查找
        def findElement(l, s, e, x):
            if s > e:
                return s
            mid = int((s + e) / 2)
            if l[mid] < x:
                if mid == len(l) - 1:
                    return mid + 1
                if l[mid + 1] >= x:
                    return mid + 1
                return findElement(l, mid + 1, e, x)
            if l[mid] > x:
                if mid == 0:
                    return 0
                if l[mid - 1] <= x:
                    return mid
                return findElement(l, s, mid - 1, x)
            return mid


        if courses == []:
            return 0

        #按照结束时间排序
        courses.sort(key = lambda x : x[1])
        res = [courses[0][0]]
        #已花去的时间
        consumingTimes = res[0]
        for i in courses[1:]:
            #若课程可在due time前完成，则直接加入list
            if consumingTimes + i[0] <= i[1]:
                pos = findElement(res, 0, len(res) - 1, i[0])
                if pos == len(res):
                    res.append(i[0])
                else:
                    res.insert(pos, i[0])
                consumingTimes += i[0]
            #否则若该课程耗费时间较少，则替换list中耗费时间最长的课程
            else:
                if i[0] < res[-1]:
                    consumingTimes += i[0] - res[-1]
                    del res[-1]
                    pos = findElement(res, 0, len(res) - 1, i[0])
                    if pos == len(res):
                        res.append(i[0])
                    else:
                        res.insert(pos, i[0])

        return len(res)


You might also like

 * @author het
 *
 */
public class LeetCode630 {

}
