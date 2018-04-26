package alite.leetcode.selected;
/**
 * 题目：

You are playing the following Flip Game with your friend: Given a string that contains only these two characters: + and -, you and your friend take turns to flip twoconsecutive "++" into "--". The game ends when a person can no longer make a move and therefore the other person will be the winner.

Write a function to determine if the starting player can guarantee a win.

For example, given s = "++++", return true. The starting player can guarantee a win by flipping the middle "++" to become "+--+".

Follow up:
Derive your algorithm's runtime complexity.

链接：  http://leetcode.com/problems/flip-game-ii/

题解：

求是否startng player可以有一种策略保证赢取游戏。直觉就是dfs +backtracking。 代码和Flip Game I基本一样，不过加入了验证下一步的一个条件语句。假如下一步next，对手不能赢，则这一步我们可以赢。看了Discuss以后发现还可以有O(n2)的DP做法，有些Game Theory的成分。Stellari大神好厉害。

Time Complexity - O(2n)， Space Complexity - O(2n)
[272] Closest Binary Search Tree Value II
复制代码
复制代码
public class Solution {
    public boolean canWin(String s) {
        char[] arr = s.toCharArray();
        for(int i = 1; i < s.length(); i++) {
            if(arr[i] == '+' && arr[i - 1] == '+') {
                arr[i] = '-';
                arr[i - 1] = '-';
                String next = String.valueOf(arr);
                if(!canWin(next)) {
                    return true;
                }
                arr[i] = '+';
                arr[i - 1] = '+';
            }
        }
        
        return false;
    }
}
 * @author het
 *
 */
public class L294FlipGameII {
	
	  public boolean canWin(String s) {
	        char[] arr = s.toCharArray();{
	        for(int i = 1; i < s.length(); i++) 
	            if(arr[i] == '+' && arr[i - 1] == '+') {
	                arr[i] = '-';
	                arr[i - 1] = '-';
	                String next = String.valueOf(arr);
	                if(!canWin(next)) {
	                    return true;
	                }
	                arr[i] = '+';
	                arr[i - 1] = '+';
	            }
	        }
	        
	        return false;
	    }

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
