package Leetcode600x;
/**
 * 
 * @author tonghe
 *Suppose you have a long flowerbed in which some of the plots are planted and some are not. However, flowers cannot be planted in adjacent plots - they would compete for water and both would die.

Given a flowerbed (represented as an array containing 0 and 1, where 0 means empty and 1 means not empty), and a number n, return if n new flowers can be planted in it without violating the no-adjacent-flowers rule.

Example 1:
Input: flowerbed = [1,0,0,0,1], n = 1
Output: True
Example 2:
Input: flowerbed = [1,0,0,0,1], n = 2
Output: False
Note:
The input array won't violate no-adjacent-flowers rule.
The input array size is in the range of [1, 20000].
n is a non-negative integer which won't exceed the input array size.

Approach #1 Single Scan [Accepted]
The solution is very simple. We can find out the extra maximum number of flowers, countcount, that can be planted for the given flowerbedflowerbed arrangement. To do so, we can traverse over all the elements of the flowerbedflowerbed and find out those elements which are 0(implying an empty position). For every such element, we check if its both adjacent positions are also empty. If so, we can plant a flower at the current position without violating the no-adjacent-flowers-rule. For the first and last elements, we need not check the previous and the next adjacent positions respectively.

If the countcount obtained is greater than or equal to nn, the required number of flowers to be planted, we can plant nn flowers in the empty spaces, otherwise not.



public class Solution {
    public boolean canPlaceFlowers(int[] flowerbed, int n) {
        int i = 0, count = 0;
        while (i < flowerbed.length) {
            if (flowerbed[i] == 0 && (i == 0 || flowerbed[i - 1] == 0) && (i == flowerbed.length - 1 || flowerbed[i + 1] == 0)) {
                flowerbed[i] = 1;
                count++;
            }
            i++;
        }
        return count >= n;
    }
}




Complexity Analysis

Time complexity : O(n)O(n). A single scan of the flowerbedflowerbed array of size nn is done.

Space complexity : O(1)O(1). Constant extra space is used.


Approach #2 Optimized [Accepted]
Algorithm

Instead of finding the maximum value of countcount that can be obtained, as done in the last approach, we can stop the process of checking the positions for planting the flowers as soon as countcount becomes equal to nn. Doing this leads to an optimization of the first approach. If countcount never becomes equal to nn, nn flowers can't be planted at the empty positions.



public class Solution {
    public boolean canPlaceFlowers(int[] flowerbed, int n) {
        int i = 0, count = 0;
        while (i < flowerbed.length) {
            if (flowerbed[i] == 0 && (i == 0 || flowerbed[i - 1] == 0) && (i == flowerbed.length - 1 || flowerbed[i + 1] == 0)) {
                flowerbed[i++] = 1;
                count++;
            }
             if(count>=n)
                return true;
            i++;
        }
        return false;
    }
}
Complexity Analysis

Time complexity : O(n)O(n). A single scan of the flowerbedflowerbed array of size nn is done.

Space complexity : O(1)O(1). Constant extra space is used.

Analysis written by: @vinod23
 */
public class Leetcode605 {

}
