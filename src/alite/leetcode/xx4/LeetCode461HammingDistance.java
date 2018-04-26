package alite.leetcode.xx4;
/**
 * The Hamming distance between two integers is the number of positions at which the corresponding bits are different.
Given two integers x and y, calculate the Hamming distance.
Note:
0 ≤ x, y < 231.
Example:
Input: x = 1, y = 4

Output: 2

Explanation:
1   (0 0 0 1)
4   (0 1 0 0)
       ↑   ↑

The above arrows point to positions where the corresponding bits are different.
http://blog.csdn.net/zhouziyu2011/article/details/53726977
    public int hammingDistance(int x, int y) {  
        int res = x ^ y;  
        int count = 0;  
        for (int i = 0; i < 32; i++) {  
            if ((res & 1) != 0)  
                count++;  
            res >>= 1;  
        }  
        return count;  
    }  
 * @author het
 *
 */
public class LeetCode461HammingDistance {
	public int hammingDistance(int x, int y) {  
        int res = x ^ y;  
        int count = 0;  
        for (int i = 0; i < 32; i++) {  
            if ((res & 1) != 0)  
                count++;  
            res >>= 1;  
        }  
        return count;  
    }  
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
