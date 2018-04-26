package alite.leetcode.xx2.sucess.extra;
/**
 * You are playing the following Nim Game with your friend: 
 * There is a heap of stones on the table, each time one of you take turns to 
 * remove 1 to 3 stones. The one who removes the last stone will be the winner. 
 * You will take the first turn to remove the stones.
Both of you are very clever and have optimal strategies for the game. 
Write a function to determine whether you can win the game given the number of stones in the heap.
For example, if there are 4 stones in the heap, then you will never win the game: 
no matter 1, 2, or 3 stones you remove, the last stone will always be removed by your friend.
Hint:
If there are 5 stones in the heap, could you figure out a way to remove the stones such that you will always be the
 winner?
Nim游戏的解题关键是寻找“必胜态”。
根据题设条件：
当n∈[1,3]时，先手必胜。

当n == 4时，无论先手第一轮如何选取，下一轮都会转化为n∈[1,3]的情形，此时先手必负。

当n∈[5,7]时，先手必胜，先手分别通过取走[1,3]颗石头，可将状态转化为n == 4时的情形，此时后手必负。

当n == 8时，无论先手第一轮如何选取，下一轮都会转化为n∈[5,7]的情形，此时先手必负。
......
以此类推，可以得出结论：
当n % 4 != 0时，先手必胜；否则先手必负。
class Solution(object):
    def canWinNim(self, n):
        return n % 4 > 0
https://segmentfault.com/a/1190000003884660
策略在于，因为每个人都取不到4个，假设自己后走，要保证每轮自己和对方取得数量的和是4，这样就能确保每轮完后都有4的倍数个石头被取走。这样，如果我们先走的话，先把n除4的余数个石头拿走，这样不管怎样，到最后都会留4个下来，对方取1个你就取3个，对方取2个你就取2个，就必赢了。
https://leetcode.com/discuss/63725/theorem-all-4s-shall-be-false

Theorem: The first one who got the number that is multiple of 4 (i.e. n % 4 == 0) will lost, otherwise he/she will win.
Proof:
the base case: when n = 4, as suggested by the hint from the problem, no matter which number that that first player, the second player would always be able to pick the remaining number.
For 1* 4 < n < 2 * 4, (n = 5, 6, 7), the first player can reduce the initial number into 4 accordingly, which will leave the death number 4 to the second player. i.e. The numbers 5, 6, 7 are winning numbers for any player who got it first.
Now to the beginning of the next cycle, n = 8, no matter which number that the first player picks, it would always leave the winning numbers (5, 6, 7) to the second player. Therefore, 8 % 4 == 0, again is a death number.
Following the second case, for numbers between (2*4 = 8) and (3*4=12), which are 9, 10, 11, are winning numbers for the first player again, because the first player can always reduce the number into the death number 8.
If you are familiar with math induction, this problem can be proved by natural induction.
Base case: n = 4, we can show that for taking 1, 2, or 3, you will always lose.
Induction case: suppose for n = 4K, you will lose. For 4 (K + 1), you have 4K + 4, which is 4 more than 4K. For all the possible choice (1, 2, 3), your enemy can take 3, 2, 1 so you must take 4K, which is losing.
Thus, for all 4K (K is natural number) cases, you will lose.
public boolean canWinNim(int n) {
    return (n & 3) != 0;
}
https://leetcode.com/discuss/72624/my-solution-with-java
public boolean canWinNim(int n) {
    return n>>2<<2!=n;
}
X.  DP
https://leetcode.com/discuss/63659/two-java-solution
I can explain it to you. When there is n stones you have three choices: A)pick one and leave (n-1) to your friend. B)pick two and leave (n-2). C)pick three and leave (n-3). In situation A), your friend have three choices too, to leave (n-2),(n-3),(n-4) to you. To win, you need make sure all the three possible situations are true. Similar with situation B and C. Because you can choose from AB or C, then you need to make sure A or B or C is true. That is dp[i] = (dp[i - 2] && dp[i - 3] && dp[i - 4])|| (dp[i - 3] && dp[i - 4] && dp[i - 5])|| (dp[i - 4] && dp[i - 5] && dp[i - 6]). Must have a three consecutive true to win between n-2 and n-6. Other wise lose.
DP : Line 7: java.lang.OutOfMemoryError: Java heap space
public boolean canWinNim(int n) {
    if(n <= 0)
        throw new IllegalArgumentException();
    if(n < 4)
        return true;
    boolean[] res = new boolean[n + 1];
    res[0] = true;
    res[1] = true;
    res[2] = true;
    res[3] = true;
    for(int i = 4 ; i <= n ; i++)
        res[i] = !(res[i - 1] && res[i - 2] && res[i - 3]);
    return res[n];
}

I came up with a DP solution that uses O(n) time and O(1) space, but still the online Judge complained about time limit exceeded. Is the Online Judge only looking for the constant time solution?
public class Solution {
    public boolean canWinNim(int n) {
        boolean[] results = {true,true,true,false};
        for (int i=4; i<n; i++){
            results[i%4] = !(results[(i-1)%4]&&results[(i-2)%4]&&results[(i-3)%4]);
        }
        return results[(n-1)%4];
    }
}
It should be: (!res[i - 1]) || (!res[i - 2]) || (!res[i - 3]) which is equivalent to !(res[i - 1] && res[i - 2] && res[i - 3])

X. DFS
http://www.zhuangjingyang.com/leetcode-nim-game.html

    private int count = 1;

    public boolean canWinNim(int n) {

        if(n<=3){

            if(count%2!=0)

                return true;

            else

                return false;

        }

        else {

            count++;

            return canWinNim(n - 1) || canWinNim(n - 2) || canWinNim(n - 3);

        }

    }
https://leetcode.com/discuss/72322/interviewer-prefer-candidates-burte-force-instead-method
    public boolean canWinNim(int n) {
            if(n>=134882061){//I have no any other ways,please forgive my unchastity(无节操)!
               return n%4 != 0;
            }
            int[] array=new int[n+1];
            return dfs(n, array);
     }
     public boolean dfs(int n,int[] array){
         if(array[n]!=0){
             return array[n]==1?true:false;
         }
         if(n<=3){
                array[n]=1;
                return true;
            }else{
                for(int i=1;i<=3;i++){
                    if(!dfs(n-i,array)){
                        array[n-i]=-1;
                        array[n]=1;
                        return true;
                    }
                }
                array[n]=-1;
                return false;
            }
     }
Read full article from [LeetCode]Nim Game | 书影博客
 * @author het
 *
 */
public class LeetCode292NimGame {
	
	class Solution(object):
	    def canWinNim(self, n):
	        return n % 4 > 0
	 public boolean canWinNim(int n) {
	        boolean[] results = {true,true,true,false};
	        for (int i=4; i<n; i++){
	            results[i%4] = !(results[(i-1)%4]&&results[(i-2)%4]&&results[(i-3)%4]);
	            //  results[i%4] = !(results[(i-1)%4]&&results[(i-2)%4]&&results[(i-3)%4]);
	        }
	        return results[(n-1)%4];
	    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
