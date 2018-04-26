package alite.leetcode.newest;
/**
 * LeetCode 621 - Task Scheduler

https://leetcode.com/problems/task-scheduler
Given a char array representing tasks CPU need to do. It contains capital letters A to Z where different letters represent different tasks.Tasks could be done without original order. Each task could be done in one interval. For each interval, CPU could finish one task or just be idle.
However, there is a non-negative cooling interval n that means between two same tasks, there must be at least n intervals that CPU are doing different tasks or just be idle.
You need to return the least number of intervals the CPU will take to finish all the given tasks.
Example 1:
Input: tasks = ['A','A','A','B','B','B'], n = 2
Output: 8
Explanation: A -> B -> idle -> A -> B -> idle -> A -> B.
Note:
The number of tasks is in the range [1, 10000].
The integer n is in the range [0, 100].
https://leetcode.com/articles/task-scheduler/
Approach #3 Calculating Idle slots
https://discuss.leetcode.com/topic/92852/concise-java-solution-o-n-time-o-26-space
First consider the most frequent characters, we can determine their relative positions first and use them as a frame to insert the remaining less frequent characters. Here is a proof by construction:
Let F be the set of most frequent chars with frequency k.
We can create k chunks, each chunk is identical and is a string consists of chars in F in a specific fixed order.
Let the heads of these chunks to be H_i; then H_2 should be at least n chars away from H_1, and so on so forth; then we insert the less frequent chars into the gaps between these chunks sequentially one by one ordered by frequency in a decreasing order and try to fill the k-1 gaps as full or evenly as possible each time you insert a character. In summary, append the less frequent characters to the end of each chunk of the first k-1 chunks sequentially and round and round, then join the chunks and keep their heads' relative distance from each other to be at least n.
Examples:
AAAABBBEEFFGG 3
here X represents a space gap:
Frame: "AXXXAXXXAXXXA"
insert 'B': "ABXXABXXABXXA" <--- 'B' has higher frequency than the other characters, insert it first.
insert 'E': "ABEXABEXABXXA"
insert 'F': "ABEFABEXABFXA" <--- each time try to fill the k-1 gaps as full or evenly as possible.
insert 'G': "ABEFABEGABFGA"
AACCCBEEE 2
3 identical chunks "CE", "CE CE CE" <-- this is a frame
insert 'A' among the gaps of chunks since it has higher frequency than 'B' ---> "CEACEACE"
insert 'B' ---> "CEABCEACE" <----- result is tasks.length;
AACCCDDEEE 3
3 identical chunks "CE", "CE CE CE" <--- this is a frame.
Begin to insert 'A'->"CEA CEA CE"
Begin to insert 'B'->"CEABCEABCE" <---- result is tasks.length;
ACCCEEE 2
3 identical chunks "CE", "CE CE CE" <-- this is a frame
Begin to insert 'A' --> "CEACE CE" <-- result is (c[25] - 1) * (n + 1) + 25 -i = 2 * 3 + 2 = 8

for the last line (c[25] - 1) * (n + 1) + 25 - i):
c[25]-1: the most frequent letter happen time minus 1
*(n+1): count for the loops for above
+25-i: count for the less frequence letters.
For example:
tasks = ['A','A','A','B','B','B'], n = 2
most frequent letter : A (happen 3 times, let it minus 1= 2)
count loops: (n+1) =3
plus the letters left: A,B.
[A -> B -> idle]*loop1* -> [A -> B -> idle]*loop2* -> A -> B.
// (c[25] - 1) * (n + 1) + 25 - i  is frame size
// when inserting chars, the frame might be "burst", then tasks.length takes precedence
// when 25 - i > n, the frame is already full at construction, the following is still valid.
    public int leastInterval(char[] tasks, int n) {

        int[] c = new int[26];
        for(char t : tasks){
            c[t - 'A']++;
        }
        Arrays.sort(c);
        int i = 25;
        while(i >= 0 && c[i] == c[25]) i--;

        return Math.max(tasks.length, (c[25] - 1) * (n + 1) + 25 - i);
    }

If we are able to, somehow, determine the number of idle slots(idle\_slotsidle_slots), we can find out the time required to execute all the tasks as idle\_slots + Total Number Of Tasksidle_slots+TotalNumberOfTasks. Thus, the idea is to find out the idle time first.

Time complexity : O(n)O(n). We iterate over taskstasks array only once. (O(n)O(n)).Sorting taskstasks array of length nn takes O\big(26log(26)\big)= O(1)O(26log(26))=O(1) time. After this, only one iteration over 26 elements of mapmap is done(O(1)O(1).

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
TODO
https://discuss.leetcode.com/topic/92966/java-o-n-time-o-1-space-1-pass-no-sorting-solution-with-detailed-explanation/
    public int leastInterval(char[] tasks, int n) {
        int[] counter = new int[26];
        int max = 0;
        int maxCount = 0;
        for(char task : tasks) {
            counter[task - 'A']++;
            if(max == counter[task - 'A']) {
                maxCount++;
            }
            else if(max < counter[task - 'A']) {
                max = counter[task - 'A'];
                maxCount = 1;
            }
        }
        
        int partCount = max - 1;
        int partLength = n - (maxCount - 1);
        int emptySlots = partCount * partLength;
        int availableTasks = tasks.length - max * maxCount;
        int idles = Math.max(0, emptySlots - availableTasks);
        
        return tasks.length + idles;
    }


Approach #2 Using Priority-Queue
we can also make use of a Max-Heap(queuequeue) to pick the order in which the tasks need to be executed. But we need to ensure that the heapification occurs only after the intervals of cooling time, nn, as done in the last approach.
To do so, firstly, we put only those elements from mapmap into the queuequeue which have non-zero number of instances. Then, we start picking up the largest task from the queuequeue for current execution. (Again, at every instant, we update the current timetime as well.) We pop this element from the queuequeue. We also decrement its pending number of instances and if any more instances of the current task are pending, we store them(count) in a temporary temptemp list, to be added later on back into the queuequeue. We keep on doing so, till a cycle of cooling time has been finished. After every such cycle, we add the generated temptemplist back to the queuequeue for considering the most critical task again.
We keep on doing so till the queuequeue(and temptemp) become totally empty. At this instant, the current value of timetime gives the required result.
Time complexity : O(n)O(n). Number of iterations will be equal to resultant time timetime.
Space complexity : O(1)O(1). queuequeue and temptemp size will not exceed O(26).
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
https://discuss.leetcode.com/topic/92873/c-java-clean-code-priority-queue
To work on the same task again, CPU has to wait for time n, therefore we can think of as if there is a cycle, of time n+1, regardless whether you schedule some other task in the cycle or not.
To avoid leave the CPU with limited choice of tasks and having to sit there cooling down frequently at the end, it is critical the keep the diversity of the task pool for as long as possible.
In order to do that, we should try to schedule the CPU to always try round robin between the most popular tasks at any time.
https://discuss.leetcode.com/topic/92866/java-priorityqueue-solution-similar-problem-rearrange-string-k-distance-apart
The idea used here is similar to - https://leetcode.com/problems/rearrange-string-k-distance-apart
We need to arrange the characters in string such that each same character is K distance apart, where distance in this problems is time b/w two similar task execution.
Idea is to add them to a priority Q and sort based on the highest frequency.
And pick the task in each round of 'n' with highest frequency. As you pick the task, decrease the frequency, and put them back after the round.
public int leastInterval(char[] tasks, int n) {
     Map<Character, Integer> map = new HashMap<>();
    for (int i = 0; i < tasks.length; i++) {
        map.put(tasks[i], map.getOrDefault(tasks[i], 0) + 1); // map key is TaskName, and value is number of times to be executed.
    }
    PriorityQueue<Map.Entry<Character, Integer>> q = new PriorityQueue<>( //frequency sort
            (a,b) -> a.getValue() != b.getValue() ? b.getValue() - a.getValue() : a.getKey() - b.getKey());

    q.addAll(map.entrySet());

    int count = 0;
    while (!q.isEmpty()) {
        int k = n + 1;//\\
        List<Map.Entry> tempList = new ArrayList<>();
        while (k > 0 && !q.isEmpty()) {
            Map.Entry<Character, Integer> top = q.poll(); // most frequency task
            top.setValue(top.getValue() - 1); // decrease frequency, meaning it got executed
            tempList.add(top); // collect task to add back to queue
            k--;
            count++; //successfully executed task
        }

        for (Map.Entry<Character, Integer> e : tempList) {
            if (e.getValue() > 0) q.add(e); // add valid tasks 
        }

        if (q.isEmpty()) break;
        count = count + k; // if k > 0, then it means we need to be idle
    }
    return count;
}
https://discuss.leetcode.com/topic/92968/java-greedy-algorithm-with-correctness-proof-using-priorityqueue-and-waiting-list
The idea of greedy algorithm is at each time point we choose the task with most amount to be done and is also at least n apart from the last execution of the same task. Below is a proof of the correctness:
At some time, task A has the most remaining amount to be done and A is also at least n apart from its most recent execution. However, suppose the optimal solution doesn't choose A as the first task but rather chooses B. Assume we have x task A remain and y task B remain, we know x >= y.
Also assume in the optimal solution those m task A are done at a series of time points of a1, a2 ... ax, and n task B are done at a series of time points of b1, b2 ... by and we know that b1 < a1.
Further, we assume k is the largest number that for all i <= k, bi < ai. Now if we swap a1 and b1, a2 and b2 ... ak and bk, it will still be a valid solution since the separation between ak and ak+1 (if exists) becomes even larger. As to bk, it's the previous ak and bk+1 > ak+1 > ak(prev) + n = bk(now) + n.
So we proved that no solution will better than schedule A first.
https://discuss.leetcode.com/topic/92881/java-solution-priorityqueue-and-hashmap
Greedy - It's obvious that we should always process the task which has largest amount left.
Put tasks (only their counts are enough, we don't care they are 'A' or 'B') in a PriorityQueue in descending order.
Start to process tasks from front of the queue. If amount left > 0, put it into a coolDown HashMap
If there's task which cool-down expired, put it into the queue and wait to be processed.
Repeat step 3, 4 till there is no task left.
    public int leastInterval(char[] tasks, int n) {
        if (n == 0) return tasks.length;
        
        Map<Character, Integer> taskToCount = new HashMap<>();
        for (char c : tasks) {
            taskToCount.put(c, taskToCount.getOrDefault(c, 0) + 1);
        }
        
        Queue<Integer> queue = new PriorityQueue<>((i1, i2) -> i2 - i1);
        for (char c : taskToCount.keySet()) queue.offer(taskToCount.get(c));
        
        Map<Integer, Integer> coolDown = new HashMap<>();
        int currTime = 0;
        while (!queue.isEmpty() || !coolDown.isEmpty()) {
            if (coolDown.containsKey(currTime - n - 1)) {
                queue.offer(coolDown.remove(currTime - n - 1));
            }
            if (!queue.isEmpty()) {
                int left = queue.poll() - 1;
         if (left != 0) coolDown.put(currTime, left);
            }
            currTime++;
        }
        
        return currTime;
    }
Approach #1 Using Sorting

The first solution that comes to the mind is to consider the tasks to be executed in the descending order of their number of instances. For every task executed, we can keep a track of the time at which this task was executed in order to consider the impact of cooling time in the future. We can execute all the tasks in the descending order of their number of instances and can keep on updating the number of instances pending for each task as well. After one cycle of the task list is executed, we can again start with the first task(largest count of instances) and keep on continuing the process by inserting idle cycles wherever appropriate by considering the last execution time of the task and the cooling time as well.
But, there is a flaw in the above idea. Consider the case, where say the number of instances of tasks A, B, C, D, E are 6, 1, 1, 1, 1 respectively with n=2(cooling time). If we go by the above method, firstly we give 1 round to each A, B, C, D and E. Now, only 5 instances of A are pending, but each instance will take 3 time units to complete because of cooling time. But a better way to schedule the tasks will be this: A, B, C, A, D, E, ... . In this way, by giving turn to the task A as soon as its cooling time is over, we can save a good number of clock cycles.
From the above example, we are clear with one idea. It is that, the tasks with the currently maximum number of outstanding (pending)instances will contribute to a large number of idle cycles in the future, if not executed with appropriate interleavings with the other tasks. Thus, we need to re-execute such a task as soon as its cooling time is finished.
Thus, based on the above ideas, firstly, we obtain a count of the number of instances of each task in mapmap array. Then, we start executing the tasks in the order of descending number of their initial instances. As soon as we execute the first task, we start its cooling timer as well(ii). For every task executed, we update the pending number of instances of the current task. We update the current time, timetime, at every instant as well. Now, as soon as the timer, ii's value exceeds the cooling time, as discussed above, we again need to consider the task with the largest number of pending instances. Thus, we again sort the taskstasks array with updated counts of instances and again pick up the tasks in the descending order of their number of instances.
Now, the task picked up first after the sorting, will either be the first task picked up in the last iteration(which will now be picked after its cooling time has been finished) or the task picked will be the one which lies at (n+1)^{th}(n+1)
​th
​​  position in the previous descending taskstasks array. In either of the cases, the cooling time won't cause any conflicts(it has been considered implicitly). Further, the task most critical currently will always be picked up which was the main requirement.
We stop this process, when the pending instances of all the tasks have been reduced to 0. At this moment, timetime gives the required result.
Time complexity : O(time)O(time). Number of iterations will be equal to resultant time timetime.
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

 * @author het
 *
 */
public class LeetCode621TaskScheduler {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
