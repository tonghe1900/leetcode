package alite.leetcode.newExtra.L500.extra.finish;
/**
 * LeetCode 507 - Perfect Number

https://leetcode.com/problems/perfect-number/
We define the Perfect Number is a positive integer that is equal to the sum of all its positive divisors except itself.
Now, given an integer n, write a function that returns true when it is a perfect number and false when it is not.
Example:
Input: 28
Output: True
Explanation: 28 = 1 + 2 + 4 + 7 + 14
Note: The input number n will not exceed 100,000,000. (1e8)
https://discuss.leetcode.com/topic/84259/simple-java-solution
   public boolean checkPerfectNumber(int num) {
        if (num == 1) return false;
        
        int sum = 0;
        for (int i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0) {
                sum += i;
                if (i != num / i) sum += num / i;
            }
        }
        sum++;
        
        return sum == num;
    }
Update Enlightened by discussion below by @StefanPochmann and @jdrogin, in the given range we don't need to test if (i != num / i) before add num / i to sum.
    public boolean checkPerfectNumber(int num) {
        if (num == 1) return false;
        
        int sum = 0;
        for (int i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0) {
                sum += i + num / i;
            }
        }
        sum++;
        
        return sum == num;
    }
https://discuss.leetcode.com/topic/84260/java-4-liner-o-sqrt-n-solution
public boolean checkPerfectNumber(int num) {
    int sum = 1;
    for (int i=2;i<Math.sqrt(num);i++) 
        if (num % i == 0) sum += i + (num/i == i ? 0 : num/i);
    return num != 1 && sum == num;
}
https://discuss.leetcode.com/topic/84270/hard-coded-java-solution
    public boolean checkPerfectNumber(int num) {
        HashSet<Integer> set = new HashSet<Integer>();
        set.add(6);
        set.add(28);
        set.add(496);
        set.add(8128);
        set.add(33550336);
        return set.contains(num);
    }
 * @author het
 *
 */
public class L507 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
