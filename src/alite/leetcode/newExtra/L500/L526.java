package alite.leetcode.newExtra.L500;

import java.util.HashSet;
import java.util.Set;

/**
 * LeetCode 526 - Beautiful Arrangement

https://leetcode.com/problems/beautiful-arrangement
Suppose you have N integers from 1 to N. We define a beautiful arrangement as an array that is constructed by 
these N numbers successfully if one of the following is true for the ith position (1 ≤ i ≤ N) in this array:
The number at the ith position is divisible by i.
i is divisible by the number at the ith position.
Now given N, how many beautiful arrangements can you construct?
Example 1:
Input: 2
Output: 2
Explanation: 

The first beautiful arrangement is [1, 2]:

Number at the 1st position (i=1) is 1, and 1 is divisible by i (i=1).

Number at the 2nd position (i=2) is 2, and 2 is divisible by i (i=2).

The second beautiful arrangement is [2, 1]:

Number at the 1st position (i=1) is 2, and 2 is divisible by i (i=1).

Number at the 2nd position (i=2) is 1, and i (i=2) is divisible by 1.
Note:


N is a positive integer and will not exceed 15.
https://discuss.leetcode.com/topic/79916/java-solution-backtracking
Just try every possible number at each position...
    int count = 0;
    
    public int countArrangement(int N) {
        if (N == 0) return 0;
        helper(N, 1, new int[N + 1]);
        return count;
    }
    
    private void helper(int N, int pos, int[] used) {
        if (pos > N) {
            count++;
            return;
        }
        
        for (int i = 1; i <= N; i++) {
            if (used[i] == 0 && (i % pos == 0 || pos % i == 0)) {
                used[i] = 1;
                helper(N, pos + 1, used);
                used[i] = 0;
            }
        }
    }
https://discuss.leetcode.com/topic/79959/12-ms-java-backtracking-sulotion
You can make it a bit faster by stopping the recursion at k < 2 instead of at k == 0. No need to check whether position 1 can hold the last remaining number. It always can.
The trick is: Arrange the values starting from the end of the array.
    public int countArrangement(int N) {
        dfs(N, N, new boolean[N + 1]);
        return count;
    }
    
    int count = 0;
    
    void dfs(int N, int k, boolean[] visited) {
        if (k == 0) {
            count++;
            return;
        }
        for (int i = 1; i <= N; i++) {
            if (visited[i] || k % i != 0 && i % k != 0) {
                continue;
            }
            visited[i] = true;
            dfs(N, k - 1, visited);
            visited[i] = false;
        }
    }   https://people.eecs.berkeley.edu/~jrs/61b/lec/37.pdf
https://discuss.leetcode.com/topic/79910/java-backtracking
We incrementally fill out each index array position [1..N], trying out each possible number. We however do not try a number in a position if it is violates the given constraints.
    public int countArrangement(int N) {
     return backtrack(new boolean[N], 0);
    }
    
    public int backtrack(boolean[] used, int curIndex) {
        if (curIndex == used.length) return 1;
        int sum = 0;
        for (int i=0;i<used.length;i++) {
            if (!used[i] && ((i+1) % (curIndex+1) == 0 || (curIndex + 1) % (i+1) == 0)) {
                used[i] = true;
                sum += backtrack(used, curIndex + 1);
                used[i] = false;
            }
        }
        return sum;
    }
https://discuss.leetcode.com/topic/79972/if-worried-about-time-limit-exceeded-contest-strategy
I don't think it's cheating at all. I think it's a legitimate strategy. Even in the real world. And even in an interview I think it would be good to mention that this could be used.
 * @author het
 *
 */
public class L526 {
	public int nums(int n){
		if(n <=0) return 0;
		if(n <=2) return n;
		int [] nums = new int [1];
		Set<Integer> set = new HashSet<>();
		for(int i=1;i<=n;i+=1){
			set.add(i);
		}
		nums(set, nums, 0, n);
		return nums[0];
	}
	private void nums(Set<Integer> set, int[] nums, int start, int length) {
		if(start ==length){
			nums[0]+=1;
			return;
		}
		Set<Integer> copy = new HashSet<>(set);
		for(Integer element: set){
			if((start +1) % element == 0 || element %(start+1) ==0  ){
				copy.remove(element);
				nums(copy, nums, start+1, length);
				copy.add(element);
			}
			
			
		}
		
	}
	
	
	 public int countArrangement(int N) {
	        dfs(N, N, new boolean[N + 1]);
	        return count;
	    }
	    
	    int count = 0;
	    
	    void dfs(int N, int k, boolean[] visited) {
	        if (k == 0) {
	            count++;
	            return;
	        }
	        for (int i = 1; i <= N; i++) {
	            if (visited[i] || k % i != 0 && i % k != 0) {
	                continue;
	            }
	            visited[i] = true;
	            dfs(N, k - 1, visited);
	            visited[i] = false;
	        }
	    }
	//CM-22536  CM-22536  CM-22536  CM-22536
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(3%5);
		System.out.println(new L526().nums(6));

	}

}
