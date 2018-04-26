package alite.leetcode.xx4.left;

import java.util.HashMap;
import java.util.Map;

/**
 * https://leetcode.com/problems/can-i-win/
In the "100 game," two players take turns adding, to a running total, any integer from 1..10. 
The player who first causes the running total to reach or exceed 100 wins.
What if we change the game so that players cannot re-use integers?
For example, two players might take turns drawing from a common pool of numbers of 1..15
 without replacement until they reach a total >= 100.
Given an integer maxChoosableInteger and another integer desiredTotal, determine if the first player 
to move can force a win, assuming both players play optimally.
You can always assume that maxChoosableInteger will not be larger than
 20 and desiredTotal will not be larger than 300.
Example


Input:
maxChoosableInteger = 10
desiredTotal = 11

Output:
false

Explanation:
No matter which integer the first player choose, the first player will lose.
The first player can choose an integer from 1 up to 10.
If the first player choose 1, the second player can only choose integers from 2 up to 10.
The second player will win by choosing 10 and get a total = 11, which is >= desiredTotal.
Same with other integers chosen by the first player, the second player will always win.
http://bookshadow.com/weblog/2016/11/20/leetcode-can-i-win/
记忆化搜索 + 位运算
由于maxChoosableInteger不大于20，因此可以通过整数state表示当前已经选择了哪些数字
state的第i位为1时，表示选择了数字i + 1
利用字典dp记录已经搜索过的状态
    def canIWin(self, maxChoosableInteger, desiredTotal):
        """
        :type maxChoosableInteger: int
        :type desiredTotal: int
        :rtype: bool
        """
        dp = dict()
        def search(state, total):
            for x in range(maxChoosableInteger, 0, -1):
                if not state & (1 << (x - 1)): // x has not been chosen
                    if total + x >= desiredTotal:
                        dp[state] = True
                        return True
                    break
            for x in range(1, maxChoosableInteger + 1):
                if not state & (1 << (x - 1)):
                    nstate = state | (1 << (x - 1))
                    if nstate not in dp:
                        dp[nstate] = search(nstate, total + x)
                    if not dp[nstate]:
                        dp[state] = True
                        return True
            dp[state] = False
            return False
        if maxChoosableInteger >= desiredTotal: return True
        if (1 + maxChoosableInteger) * maxChoosableInteger < 2 * desiredTotal: return False
        return search(0, 0)
 * @author het
 *
 */
public class LeetCode464 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(new LeetCode464().canIWin(10, 11));

	}
	// 11 10 9  ...       1    (1+11)*11/2 == 66
	//  12*11<2 * 66
	public boolean canIWin(int  maxChoosableInteger, int desiredTotal){
		if (maxChoosableInteger >= desiredTotal) return true;
		if ((1 + maxChoosableInteger) * maxChoosableInteger < 2 * desiredTotal) return false;
		return canIWin(0, maxChoosableInteger, 0, desiredTotal, new HashMap<Integer, Boolean>());
	}
	
	public boolean canIWin(int state, int  maxChoosableInteger, int currentTotal, int desiredTotal, Map<Integer, Boolean> cache){
		for(int i= maxChoosableInteger; i>0;i-=1){
			if((state & (1 << (i-1))) == 0){
				if(currentTotal + i >= desiredTotal){
					cache.put(state, true);
					return true;
				}
			}
		}
		
		for(int i=1 ;i<=maxChoosableInteger;i+=1){
			if((state & (1 << (i-1))) == 0){
				int newState = state | (1 << (i-1));
				boolean resultForCounterpart;
				if(cache.containsKey(newState)){
					 resultForCounterpart  =cache.get(newState);
				}else{
					resultForCounterpart = canIWin(newState, maxChoosableInteger, currentTotal+i, desiredTotal, cache);
					cache.put(newState, resultForCounterpart);
				}
				if(!resultForCounterpart) {
					cache.put(state, true);
			    	 return true;
				}
				
			}
			
		}
		
		 cache.put(state, false);
		 return false;
		
		
	}
	
	
	//10
//	记忆化搜索 + 位运算
//	由于maxChoosableInteger不大于20，因此可以通过整数state表示当前已经选择了哪些数字
//	state的第i位为1时，表示选择了数字i + 1
//	利用字典dp记录已经搜索过的状态
//	    def canIWin(self, maxChoosableInteger, desiredTotal):
//	        """
//	        :type maxChoosableInteger: int
//	        :type desiredTotal: int
//	        :rtype: bool
//	        """
//	        dp = dict()
//	        def search(state, total):
//	            for x in range(maxChoosableInteger, 0, -1):
//	                if not state & (1 << (x - 1)):
//	                    if total + x >= desiredTotal:
//	                        dp[state] = True
//	                        return True
//	                    break
//	            for x in range(1, maxChoosableInteger + 1):
//	                if not state & (1 << (x - 1)):
//	                    nstate = state | (1 << (x - 1))
//	                    if nstate not in dp:
//	                        dp[nstate] = search(nstate, total + x)
//	                    if not dp[nstate]:
//	                        dp[state] = True
//	                        return True
//	            dp[state] = False
//	            return False
//	        if maxChoosableInteger >= desiredTotal: return True
//	        if (1 + maxChoosableInteger) * maxChoosableInteger < 2 * desiredTotal: return False
//	        return search(0, 0)

}
