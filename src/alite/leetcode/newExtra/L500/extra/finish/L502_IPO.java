package alite.leetcode.newExtra.L500.extra.finish;
/**
 * LeetCode 502 - IPO

https://leetcode.com/problems/ipo/
Suppose LeetCode will start its IPO soon. In order to sell a good price of its shares to Venture Capital, LeetCode
 would like to work on some projects to increase its capital before the IPO. Since it has limited resources, 
 it can only finish at most k distinct projects before the IPO. Help LeetCode design the best way to maximize 
 its total capital after finishing at most k distinct projects.
You are given several projects. For each project i, it has a pure profit Pi and a minimum capital of Ci is 
needed to start the corresponding project. Initially, you have W capital. When you finish a project, you will 
obtain its pure profit and the profit will be added to your total capital.
To sum up, pick a list of at most k distinct projects from given projects to maximize your final capital, 
and output your final maximized capital.
Example 1:
Input: k=2, W=0, Profits=[1,2,3], Capital=[0,1,1].

Output: 4

Explanation: Since your initial capital is 0, you can only start the project indexed 0.
             After finishing it you will obtain profit 1 and your capital becomes 1.
             With capital 1, you can either start the project indexed 1 or the project indexed 2.
             Since you can choose at most 2 projects, you need to finish the project indexed 2 to get the maximum capital.
             Therefore, output the final maximized capital, which is 0 + 1 + 3 = 4.
Note:
You may assume all numbers in the input are non-negative integers.
The length of Profits array and Capital array will not exceed 50,000.
The answer is guaranteed to fit in a 32-bit signed integer.
https://discuss.leetcode.com/topic/77768/very-simple-greedy-java-solution-using-two-priorityqueues
The idea is each time we find a project with max profit and within current capital capability.
Algorithm:
Create (capital, profit) pairs and put them into PriorityQueue pqCap. This PriorityQueue sort by capital increasingly.
Keep polling pairs from pqCap until the project out of current capital capability. Put them into
PriorityQueue pqPro which sort by profit decreasingly.
Poll one from pqPro, it's guaranteed to be the project with max profit and within current capital capability.
 Add the profit to capital W.
Repeat step 2 and 3 till finish k steps or no suitable project (pqPro.isEmpty()).
Time Complexity: For worst case, each project will be inserted and polled from both PriorityQueues once,
 so the overall runtime complexity should be O(NlgN), N is number of projects.
    public int findMaximizedCapital(int k, int W, int[] Profits, int[] Capital) {
        PriorityQueue<int[]> pqCap = new PriorityQueue<>((a, b) -> (a[0] - b[0]));
        PriorityQueue<int[]> pqPro  = new PriorityQueue<>((a, b) -> (b[1] - a[1]));
        
        for (int i = 0; i < Profits.length; i++) {
            pqCap.add(new int[] {Capital[i], Profits[i]});
        }
        
        for (int i = 0; i < k; i++) {
            while (!pqCap.isEmpty() && pqCap.peek()[0] <= W) {
                pqPro.add(pqCap.poll());
            }
            
            if (pqPro.isEmpty()) break;
            
            W += pqPro.poll()[1];
        }
        
        return W;
    }
http://bookshadow.com/weblog/2017/02/05/leetcode-ipo/
在启动资金允许的范围之内，选取收益最大的项目
首先将项目projects按照启动资金从小到大排序（projects为<Capital, Profits>的组合）

记当前资金为ans，初始令ans = W

维护优先队列pq，将所有启动资金不大于ans的收益加入pq

将pq中的最大值弹出并加入ans

循环直到完成k个项目为止
    public int findMaximizedCapital(int k, int W, int[] Profits, int[] Capital) {
        int size = Profits.length;
        int ans = W;
        Point projects[] = new Point[size];
        for (int i = 0; i < projects.length; i++) {
            projects[i] = new Point(Capital[i], Profits[i]);
        }
        Arrays.sort(projects, new Comparator<Point>(){
            public int compare(Point a, Point b) {
                if (a.x == b.x)
                    return a.y - b.y;
                return a.x - b.x;
            }
        });
        PriorityQueue<Integer> pq = new PriorityQueue<Integer>(Comparator.reverseOrder());
        int j = 0;
        for (int i = 0; i < Math.min(k, size); i++) {
            while(j < size && projects[j].x <= ans) {
                pq.add(projects[j].y);
                j++;
            }
            if (!pq.isEmpty())
                ans += pq.poll();
        }
        return ans;
    }
 * @author het
 *
 */
public class L502_IPO {
//	在启动资金允许的范围之内，选取收益最大的项目
//	首先将项目projects按照启动资金从小到大排序（projects为<Capital, Profits>的组合）
//
//	记当前资金为ans，初始令ans = W
//
//	维护优先队列pq，将所有启动资金不大于ans的收益加入pq
//
//	将pq中的最大值弹出并加入ans
//
//	循环直到完成k个项目为止
	    public int findMaximizedCapital(int k, int W, int[] Profits, int[] Capital) {
	        int size = Profits.length;
	        int ans = W;
	        Point projects[] = new Point[size];
	        for (int i = 0; i < projects.length; i++) {
	            projects[i] = new Point(Capital[i], Profits[i]);
	        }
	        Arrays.sort(projects, new Comparator<Point>(){
	            public int compare(Point a, Point b) {
	                if (a.x == b.x)
	                    return a.y - b.y;
	                return a.x - b.x;
	            }
	        });
	        PriorityQueue<Integer> pq = new PriorityQueue<Integer>(Comparator.reverseOrder());
	        int j = 0;
	        for (int i = 0; i < Math.min(k, size); i++) {
	            while(j < size && projects[j].x <= ans) {
	                pq.add(projects[j].y);
	                j++;
	            }
	            if (!pq.isEmpty())
	                ans += pq.poll();
	        }
	        return ans;
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
