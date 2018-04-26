package alite.leetcode.xx3;
/**
 * LeetCode – Bulb Switcher (Java)
 
There are n bulbs that are initially off. You first turn on all the bulbs. Then, you turn off 
every second bulb. On the third round, you toggle every third bulb (turning on if it's off or turning off if it's on).
 For the ith round, you toggle every i bulb. For the nth round, you only toggle the last bulb.
  Find how many bulbs are on after n rounds.

Analysis

By using some examples we can find out the number of switches for each bulb:

1 -> 1 (1)
2 -> 2 (1 2)
3 -> 2 (1 3)
4 -> 3 (1 2 4)
5 -> 2 (1 5)
6 -> 4 (1 2 3 6)
7 -> 2 (1 7)
8 -> 4 (1 2 4 8)
9 -> 3 (1 3 9)
So only (i*i)th element has odd number of switches and they are on. The problem is now get all the square numbers.

Java Solution 1 - Naive

public int bulbSwitch(int n) {
    int count=0;
    for(int i=1; i<=n; i++){
        int numSwitch = helper(i);
        if(numSwitch%2 ==1)
            count++;
    }
 
    return count;
}
 
public int helper(int n){
    int count=0;
    for(int i=1; i<=n; i++){
        if(n%i==0)
            count++;
    }
    return count;
}
Java Solution 2 - Simplified

public int bulbSwitch(int n) {
    return (int)Math.sqrt(n);
}
 * @author het
 *
 */
public class LeetCodeBulbSwitcher {
	public int bulbSwitch(int n) {
	    return (int)Math.sqrt(n);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
