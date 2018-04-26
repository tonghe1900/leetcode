package alite.leetcode.xx3.extra;
/**
 * LeetCode 397 - Integer Replacement

http://bookshadow.com/weblog/2016/09/11/leetcode-integer-replacement/
Given a positive integer n and you can do operations as follow:
If n is even, replace n with n/2.
If n is odd, you can replace n with either n + 1 or n - 1.
What is the minimum number of replacements needed for n to become 1?
Example 1:
Input:
8

Output:
3

Explanation:
8 -> 4 -> 2 -> 1
Example 2:
Input:
7

Output:
4

Explanation:
7 -> 8 -> 4 -> 2 -> 1
or
7 -> 6 -> 3 -> 2 -> 1
    def integerReplacement(self, n):
        """
        :type n: int
        :rtype: int
        """
        if n == 1: return 0
        if n & 1 == 0: return self.integerReplacement(n / 2) + 1
        return min(self.integerReplacement(n + 1), self.integerReplacement(n - 1)) + 1

https://discuss.leetcode.com/topic/58425/java-12-line-5ms-iterative-solution-with-explanations-no-other-data-structures
Since when n is even, the operation is fixed. The only unknown procedure is when it is odd. When n is odd it can be written into the form n = 2k+1 (k is a non-negative integer.). That is, n+1 = 2k+2 and n-1 = 2k. Then, (n+1)/2 = k+1 and (n-1)/2 = k. So the one of (n+1)/2 and (n-1)/2 is even, another is odd. And the "best" case of this problem is to divide as much as possible. Because of that, always pick n+1 or n-1 based on if it can be divided by 4. The only special case of that is when n=3 you would like to pick n-1 rather than n+1.
public int integerReplacement(int n) {
    if(n==Integer.MAX_VALUE) return 32; //n = 2^31-1;
    int count = 0;
    while(n>1){
        if(n%2==0) n/=2;
        else{
            if((n+1)%4==0&&(n-1!=2)) n+=1;
            else n-=1;
        }
        count++;
    }
    return count;
}
https://discuss.leetcode.com/topic/58334/a-couple-of-java-solutions-with-explanations
The first step towards solution is to realize that you're allowed to remove the LSB only if it's zero. And to reach the target as fast as possible, removing digits is the best way to go. Hence, even numbers are better than odd. This is quite obvious.
What is not so obvious is what to do with odd numbers. One may think that you just need to remove as many 1's as possible to increase the evenness of the number. Wrong! Look at this example:
111011 -> 111010 -> 11101 -> 11100 -> 1110 -> 111 -> 1000 -> 100 -> 10 -> 1
And yet, this is not the best way because
111011 -> 111100 -> 11110 -> 1111 -> 10000 -> 1000 -> 100 -> 10 -> 1
See? Both 111011 -> 111010 and 111011 -> 111100 remove the same number of 1's, but the second way is better.
So, we just need to remove as many 1's as possible, doing +1 in case of a tie? Not quite. The infamous test with n=3 fails for that strategy because 11 -> 10 -> 1 is better than 11 -> 100 -> 10 -> 1. Fortunately, that's the only exception (or at least I can't think of any other, and there are none in the tests).
So the logic is:
If n is even, halve it.
If n=3 or n-1 has less 1's than n+1, decrement n.
Otherwise, increment n.
public int integerReplacement(int n) {
    int c = 0;
    while (n != 1) {
        if ((n & 1) == 0) {
            n >>>= 1;
        } else if (n == 3 || Integer.bitCount(n + 1) > Integer.bitCount(n - 1)) {
            --n;
        } else {
            ++n;
        }
        ++c;
    }
    return c;
}
Of course, doing bitCount on every iteration is not the best way. It is enough to examine the last two digits to figure out whether incrementing or decrementing will give more 1's. Indeed, if a number ends with 01, then certainly decrementing is the way to go. Otherwise, if it ends with 11, then certainly incrementing is at least as good as incrementing (*011 -> *010 / *100) or even better (if there are three or more 1's). This leads to the following solution:
public int integerReplacement(int n) {
    int c = 0;
    while (n != 1) {
        if ((n & 1) == 0) {
            n >>>= 1;
        } else if (n == 3 || ((n >>> 1) & 1) == 0) {
            --n;
        } else {
            ++n;
        }
        ++c;
    }
    return c;
}
https://discuss.leetcode.com/topic/58435/java-5-lines-4ms-recursive-math-solution
https://discuss.leetcode.com/topic/58308/4ms-recursive-java-solution-explained
how to better deal with overflow?
    public int integerReplacement(int n) {
        if(n==1) return 0;
        if(n==3) return 2;
        if(n==2147483647) return integerReplacement(n/2+1)+2;
        if(n%2==0) return integerReplacement(n/2)+1;
        else return n%4==1?integerReplacement(n-1)+1:integerReplacement(n+1)+1;
    }
http://blog.csdn.net/yeqiuzs/article/details/52506492
public int integerReplacement(int n) {  
    if (n == Integer.MAX_VALUE)  
        return 32;  
    int count = 0;  
    while (n != 1) {  
        if ((n & 1) == 0) {  
            n = n >> 1;  
            count++;  
        } else {  
            if (n == 3)  
                n = 2;  
            else {  
                if (countTrailZero(n - 1) > countTrailZero(n + 1)) {  
                    n = n - 1;  
                } else {  
                    n = n + 1;  
                }  
            }  
            count++;  
        }  
    }  
    return count;  
  
}  
  
public int countTrailZero(int n) {  
    int c = 0;  
    while ((n & 1) == 0) {  
        n = n >> 1;  
        c++;  
    }  
    return c;  
}  

https://segmentfault.com/a/1190000007318944
对于奇数的操作
如果倒数第二位是0，那么n-1的操作比n+1的操作能消掉更多的1。
1001 + 1 = 1010
1001 - 1 = 1000
如果倒数第二位是1，那么n+1的操作能比n-1的操作消掉更多的1。
1011 + 1 = 1100
1111 + 1 = 10000
还有一个tricky的地方是，为了防止integer越界，可以将n先转换成long。long N = n;这样的处理。
public int integerReplacement(int n) {
    // 处理大数据的时候tricky part, 用Long来代替int数据
    long N = n;
    int count = 0;
    while(N != 1) {
        if(N % 2 == 0) {
            N = N >> 1;
        }
        else {
            // corner case;
            if(N == 3) {
                count += 2;
                break;
            }
            N = (N & 2) == 2 ? N + 1 : N - 1;
        }
        count ++;
    }
    return count;
}
X. DP
http://www.jianshu.com/p/cd525bed9b41
    int integerReplacement(int n) {
        if(n <= 1) return 0;
        else if(n == INT_MAX) return 32;

        vector<int> dp(n+2, n+2);
        dp[1] = 0, dp[2] = 1;
        for(int i=2; i<n+2; i++){
            if(i % 2 == 0 && dp[i] > dp[i/2] + 1) dp[i] = dp[i/2]+1;
            if(i+1 <= n+1 && dp[i] > dp[i+1] + 1) dp[i] = dp[i+1] + 1;
            if(i % 2 == 0){
                if(i+1 <= n+1) dp[i+1] = min(dp[i+1], dp[i]+1);
                dp[i-1] = min(dp[i-1], dp[i]+1);
            }
        }
        return dp[n];
    }
https://discuss.leetcode.com/topic/59525/c-0ms-11-lines-dp-solution
int integerReplacement(int n) {
        int dp[n + 1]; memset(dp, 0, sizeof(dp));
        for (int i = 2; i <= n; i++) {
            dp[i] = 1 + (i & 1 == 0 ? dp[i / 2] : min(dp[i - 1], 1 + dp[i / 2 + 1]));
        }
        return dp[n];
}
    int integerReplacement(int n) {        
        if (n == 1) { return 0; }
        if (visited.count(n) == 0) {
            if (n & 1 == 1) {
                visited[n] = 2 + min(integerReplacement(n / 2), integerReplacement(n / 2 + 1));
            } else {
                visited[n] = 1 + integerReplacement(n / 2);
            }
        }
        return visited[n];
    }

X.DFS + cache
https://discuss.leetcode.com/topic/58401/my-java-solution-with-memorization-search-handling-overflow-test-case
Same idea with other recursive solutions, but two ticky points here.
With the helper of hashmap, we don't need to search for one intermediate result multiple times
To hand the overflow for test case INT.MAX, use 1 + (n - 1) / 2 instead of (n + 1) / 2. The idea comes from solving some binary search questions. To avoid overflow, we use int mid = start + (end - start) / 2 instead of int mid = (start + end) / 2
    public int integerReplacement(int n) {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        map.put(1, 0);
        map.put(2, 1);

        return helper(n, map);
    }
    
    private int helper(int n, Map<Integer, Integer> map) {
        if (map.containsKey(n)) {
            return map.get(n);
        }
        
        int steps = -1;
        if (n % 2 == 0) {
            steps = helper(n / 2, map) + 1;
        } else {
            steps = Math.min(helper((n - 1), map) + 1, helper(1 + (n - 1) / 2, map) + 2);
        }
        
        map.put(n, steps);
        
        return steps;
    }
http://www.dongcoder.com/detail-229130.html

X. BFS
https://discuss.leetcode.com/topic/58422/java-bfs-solution-tail-recursion
    public int integerReplacement(int n) {
        assert n > 0;
        Queue<Long> queue = new LinkedList<>();
        queue.offer((long)n);
        return bfs(queue, 0);
    }
    
    private int bfs(Queue<Long> oldqueue, int level) {
        Queue<Long> newqueue = new LinkedList<>();
        while (!oldqueue.isEmpty()) {
            long n = oldqueue.poll();
            if (n == 1) {
                return level;
            }
            if (n % 2 == 0) {
                newqueue.offer(n / 2);
            } else {
                newqueue.offer(n + 1);
                newqueue.offer(n - 1);
            }
        }
        return bfs(newqueue, level + 1);
    }
https://discuss.leetcode.com/topic/58422/java-bfs-solution-tail-recursion
    public int integerReplacement(int n) {
        assert n > 0;
        Queue<Long> queue = new LinkedList<>();
        queue.offer((long)n);
        return bfs(queue, 0);
    }
    
    private int bfs(Queue<Long> oldqueue, int level) {
        Queue<Long> newqueue = new LinkedList<>();
        while (!oldqueue.isEmpty()) {
            long n = oldqueue.poll();
            if (n == 1) {
                return level;
            }
            if (n % 2 == 0) {
                newqueue.offer(n / 2);
            } else {
                newqueue.offer(n + 1);
                newqueue.offer(n - 1);
            }
        }
        return bfs(newqueue, level + 1);
    }
 * @author het
 *
 */
public class LeetCode397 {
//	def integerReplacement(self, n):
//        """
//        :type n: int
//        :rtype: int
//        """
//        if n == 1: return 0
//        if n & 1 == 0: return self.integerReplacement(n / 2) + 1
//        return min(self.integerReplacement(n + 1), self.integerReplacement(n - 1)) + 1
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	
	public int integerReplacement(int n) {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        map.put(1, 0);
        map.put(2, 1);

        return helper(n, map);
    }
    
    private int helper(int n, Map<Integer, Integer> map) {
        if (map.containsKey(n)) {
            return map.get(n);
        }
        
        int steps = -1;
        if (n % 2 == 0) {
            steps = helper(n / 2, map) + 1;
        } else {
            steps = Math.min(helper((n - 1), map) + 1, helper(1 + (n - 1) / 2, map) + 2);
        }
        
        map.put(n, steps);
        
        return steps;
    }
//http://www.dongcoder.com/detail-229130.html
//
//X. BFS
//https://discuss.leetcode.com/topic/58422/java-bfs-solution-tail-recursion
    public int integerReplacement(int n) {
        assert n > 0;
        Queue<Long> queue = new LinkedList<>();
        queue.offer((long)n);
        return bfs(queue, 0);
    }
    
    private int bfs(Queue<Long> oldqueue, int level) {
        Queue<Long> newqueue = new LinkedList<>();
        while (!oldqueue.isEmpty()) {
            long n = oldqueue.poll();
            if (n == 1) {
                return level;
            }
            if (n % 2 == 0) {
                newqueue.offer(n / 2);
            } else {
                newqueue.offer(n + 1);
                newqueue.offer(n - 1);
            }
        }
        return bfs(newqueue, level + 1);
    }
//https://discuss.leetcode.com/topic/58422/java-bfs-solution-tail-recursion
    public int integerReplacement(int n) {
        assert n > 0;
        Queue<Long> queue = new LinkedList<>();
        queue.offer((long)n);
        return bfs(queue, 0);
    }
    
    private int bfs(Queue<Long> oldqueue, int level) {
        Queue<Long> newqueue = new LinkedList<>();
        while (!oldqueue.isEmpty()) {
            long n = oldqueue.poll();
            if (n == 1) {
                return level;
            }
            if (n % 2 == 0) {
                newqueue.offer(n / 2);
            } else {
                newqueue.offer(n + 1);
                newqueue.offer(n - 1);
            }
        }
        return bfs(newqueue, level + 1);
    }

}
