package alite.leetcode.xx3.extra;
/**
 * LeetCode 365 - Water and Jug Problem

leetcode Water and Jug Problem - 细语呢喃
You are given two jugs with capacities x and y litres. There is an infinite amount of water supply available.
 You need to determine whether it is possible to measure exactly z litres using these two jugs.
Operations allowed:
Fill any of the jugs completely.
Empty any of the jugs.
Pour water from one jug into another till the other jug is completely full or the first jug itself is empty.
https://discuss.leetcode.com/topic/49751/clear-explanation-of-why-using-gcd/2
Forget about two jugs pouring between each other, which may make you confused.
Let's make it simple: assuming we have one big enough bucket and two cups with volume x and y, respectively. Now we want to perform a series of operation -- pouring water in and out only by those two cups with exactly amount x or y. Somehow, there will be only z water left in this big bucket eventually. Then the equation will be:
z = m * x + n * y
m means using cup-x m times. If m is positive, it means pouring in. Otherwise, it's pouring out.
n is similar.
For example, 4 = (-2) * 3 + 2 * 5, which means you pour in water twice with cup-5 and pour out water twice with cup-3. Talk back to the question, it's like we first fill jug-5, pour water to jug-3 from jug-5, empty jug-3, pour the remaining 2 water into jug-3 from jug-5, fill jug-5 again, pour water into jug-3 from jug-5, empty jug-3, then we have only 4 water left in jug-5. It's exactly fill jug-5 twice and empty jug-3 twice.
Now the question is, can we find those two m and n exist?
The answer is YES. Here we need a little math -- Bezout's identity, which is:
We can always find a and b to satisfy ax + bx = d where d = gcd(x, y)
So, everything is clear, if z % d == 0, then we have (a*z/d)*x + (b*z/d)*y = z, which means m and n exist.
Below is the code:
bool canMeasureWater(int x, int y, int z) {
    return z == 0 || (z - x <= y && z % gcd(x, y) == 0);
}
int gcd(int x, int y)
{
    return y == 0 ? x : gcd(y, x % y);
}
https://discuss.leetcode.com/topic/49238/math-solution-java-solution/
This is a pure Math problem. We need the knowledge of number theory to cover the proof and solution. 
No idea why microsoft uses this problem in real interview.
The basic idea is to use the property of Bézout's identity and check if z is a multiple of GCD(x, y)
Quote from wiki:
Bézout's identity (also called Bézout's lemma) is a theorem in the elementary theory of numbers:
let a and b be nonzero integers and let d be their greatest common divisor. Then there exist integers x
and y such that ax+by=d
In addition, the greatest common divisor d is the smallest positive integer that can be written as ax + by
every integer of the form ax + by is a multiple of the greatest common divisor d.
If a or b is negative this means we are emptying a jug of x or y gallons respectively.
Similarly if a or b is positive this means we are filling a jug of x or y gallons respectively.
x = 4, y = 6, z = 8.
GCD(4, 6) = 2
8 is multiple of 2
so this input is valid and we have:
-1 * 4 + 6 * 2 = 8
In this case, there is a solution obtained by filling the 6 gallon jug twice and emptying the 4 gallon jug once. (Solution. Fill the 6 gallon jug and empty 4 gallons to the 4 gallon jug. Empty the 4 gallon jug. Now empty the remaining two gallons from the 6 gallon jug to the 4 gallon jug. Next refill the 6 gallon jug. This gives 8 gallons in the end)
See wiki:
Bézout's identity

public boolean canMeasureWater(int x, int y, int z) {
    //limit brought by the statement that water is finallly in one or both buckets
    if(x + y < z) return false;
    //case x or y is zero
    if( x == z || y == z || x + y == z ) return true;
    
    //get GCD, then we can use the property of Bézout's identity
    return z%GCD(x, y) == 0;
}

public int GCD(int a, int b){
    while(b != 0 ){
        int temp = b;
        b = a%b;
        a = temp;
    }
    return a;
}
http://www.learn4master.com/interview-questions/leetcode/leetcode-water-and-jug-problem
Read full article from leetcode Water and Jug Problem - 细语呢喃
 * @author het
 *
 */
public class LeetCode365 {
	public boolean canMeasureWater(int x, int y, int z) {
	    //limit brought by the statement that water is finallly in one or both buckets
	    if(x + y < z) return false;
	    //case x or y is zero
	    if( x == z || y == z || x + y == z ) return true;
	    
	    //get GCD, then we can use the property of Bézout's identity
	    return z%GCD(x, y) == 0;
	}

	public int GCD(int a, int b){  // 5   2 ->  2  1  -> 1   0
	    while(b != 0 ){
	        int temp = b;
	        b = a%b;
	        a = temp;
	    }
	    return a;
	}
	
	int gcd(int x, int y)
	{
	    return y == 0 ? x : gcd(y, x % y);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
