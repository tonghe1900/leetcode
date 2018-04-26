package alite.leetcode.xx3.extra;

import java.util.ArrayList;
import java.util.List;

/**
 * LeetCode 386 - Lexicographical Numbers

https://www.hrwhisper.me/leetcode-contest-1-solution/
Given an integer n, return 1 - n in lexicographical order.
For example, given 13, return: [1,10,11,12,13,2,3,4,5,6,7,8,9].


Please optimize your algorithm to use less time and space. The input size may be as large as 5,000,000.
http://bookshadow.com/weblog/2016/08/21/leetcode-lexicographical-numbers/
解法I 递归（Recursive）构造法
优先将数字乘10；如果数字末位＜9，考虑将数字加1
递归式类似于二叉树的先根遍历
伪代码如下：
def solve(m):
    result.append(m)
    if m * 10 <= n: solve(m * 10)
    if m < n and m % 10 < 9: solve(m + 1)
    private List<Integer> result;
    private int n;
    public List<Integer> lexicalOrder(int n) {
        this.result = new ArrayList<Integer>();
        this.n = n;
        solve(1);
        return result;
    }
    private void solve(int m) {
        result.add(m);
        if (m * 10 <= n) solve(m * 10);
        if (m < n && m % 10 < 9) solve(m + 1);
    }
https://discuss.leetcode.com/topic/55091/java-recursion-backtracking-with-explanation
In the lexicographical order we can see that the first number is 1. The next number is 10, 11, 12 and so on up until 19. Then the next number is 100, 101,
 ... We can see that it is digit based. So, first we start with 1 in the first digit and keep adding 
 digits to the right of 1 as long as it is less than n. Next, we start with 2 as the first digit and do the same.
    public void solve(int curr, int n, List<Integer> ret){
        if(curr > n){//curr is the number
            return;
        }
        ret.add(curr);
        for(int i = 0; i < 10; i++){//append 0-9 to the end of curr 
            if(curr*10 + i <= n){//recurse as long as its less than n
                solve(curr*10 + i, n, ret);
            } else break;
        }
    }
    public List<Integer> lexicalOrder(int n) {
        List<Integer> ret = new ArrayList<Integer>();
        for(int i = 1; i < 10; i++){//fix first digit
            solve(i, n, ret);
        }
        return ret;
    }
http://www.programcreek.com/2014/08/leetcode-lexicographical-numbers-java/
public List<Integer> lexicalOrder(int n) {
    int c=0;
    int t=n;
    while(t>0){
        c++;
        t=t/10;
    }
 
    ArrayList<Integer> result = new ArrayList<Integer>();
    char[] num = new char[c];
 
    helper(num, 0, n, result);
 
    return result;
}
 
public void helper(char[] num, int i, int max, ArrayList<Integer> result){
    if(i==num.length){
        int val = convert(num);
        if(val <=max)
            result.add(val);
        return;
    }
 
    if(i==0){
        for(char c='1'; c<='9'; c++){
            num[i]=c;
            helper(num, i+1, max, result);
        }
    }else{
        num[i]='a';
        helper(num, num.length, max, result);
 
        for(char c='0'; c<='9'; c++){
            num[i]=c;
            helper(num, i+1, max, result);
        }
    }
 
}
 
private int convert(char[] arr){
    int result=0;
    for(int i=0; i<arr.length; i++){
        if(arr[i]>='0'&&arr[i]<='9')
            result = result*10+arr[i]-'0';
        else
            break;
    }
    return result; 
}

该解法实际上是解法I的迭代形式，可以类比二叉树先根遍历的迭代算法，需要用到栈（Stack）数据结构。
    def lexicalOrder(self, n):
        """
        :type n: int
        :rtype: List[int]
        """
        result = []
        stack = [1]
        while stack:
            y = stack.pop()
            result.append(y)
            if y < n and y % 10 < 9:
                stack.append(y + 1)
            if y * 10 <= n: 
                stack.append(y * 10)
        return result

    def lexicalOrder(self, n):
        """
        :type n: int
        :rtype: List[int]
        """
        result = []
        stack = []
        x = 1
        while x <= n:
            stack.append(x)
            result.append(x)
            x *= 10
        while stack:
            y = stack.pop()
            if y % 10 == 9: continue
            y += 1
            while y <= n:
                stack.append(y)
                result.append(y)
                y *= 10
        return result
 * @author het
 *
 */

// curr*10+i    curr*10+i   curr*10+i
//   1    10  100  101 11  12  13  14 15  16  17  18  19  
// curr*10+i
public class LeetCode386 {
//	In the lexicographical order we can see that the first number is 1. The next number is 10, 11, 12 and 
	//so on up until 19. Then the next number is 100, 101,
//	 ... We can see that it is digit based. So, first we start with 1 in the first digit and keep adding 
//	 digits to the right of 1 as long as it is less than n. Next, we start with 2 as the first digit and do the same.
	    public static void solve(int curr, int n, List<Integer> ret){
	        if(curr > n){//curr is the number
	            return;
	        }
	        ret.add(curr);
	        for(int i = 0; i < 10; i++){//append 0-9 to the end of curr 
	            if(curr*10 + i <= n){//recurse as long as its less than n
	                solve(curr*10 + i, n, ret);
	            } else break;
	        }
	    }
	    public static List<Integer> lexicalOrder(int n) {
	        List<Integer> ret = new ArrayList<Integer>();
	        for(int i = 1; i < 10; i++){//fix first digit
	            solve(i, n, ret);
	        }
	        return ret;
	    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(lexicalOrder(200));

	}

}
