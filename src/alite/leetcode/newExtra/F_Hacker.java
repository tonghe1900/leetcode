package alite.leetcode.newExtra;
/**
 * Facebook Hacker Cup 2014 Quals: Square Detector | Random Math and Computer Science Related Things

Facebook Hacker Cup 2014 Quals: Square Detector | Random Math and Computer Science Related Things
You want to write an image detection system that is able to recognize different geometric shapes. In the first version of the
 system you settled with just being able to detect filled squares on a grid.
You are given a grid of N×N square cells. Each cell is either white or black. Your task is to detect whether all the black cells
 form a square shape.
Input
The first line of the input consists of a single number T, the number of test cases.
Each test case starts with a line containing a single integer N. Each of the subsequent N lines contain N characters. 
Each character is either "." symbolizing a white cell, or "#" symbolizing a black cell. Every test case contains at least one black cell.
Output
For each test case i numbered from 1 to T, output "Case #i: ", followed by YES or NO depending on whether or not all the black cells form a completely filled square with edges parallel to the grid of cells.
Constraints
1 ≤ T ≤ 20
1 ≤ N ≤ 20
Example
Test cases 1 and 5 represent valid squares. Case 2 has an extra cell that is outside of the square. Case 3 shows a square not filled inside. And case 4 is a rectangle but not a square.
Sample Input
5
4
..##
..##
....
....
4
..##
..##
#...
....
4
####
#..#
#..#
####
5
#####
#####
#####
#####
.....
5
#####
#####
#####
#####
#####
Sample Output
Case #1: YES
Case #2: NO
Case #3: NO
Case #4: NO
Case #5: YES
“You are given a grid of N×N square cells. Each cell is either white or black. Your task is to detect whether all the black cells form a square shape.”
https://github.com/jlhuertas/FacebookHackerCup2014/blob/master/src/com/jlhuertas/fbhackercup/qualifying/SquareDetector.java
https://gist.github.com/darkrho/7640162
http://www.cnblogs.com/yejianfei/p/3442573.html
def solve():
    n = int(raw_input().strip())
    b = [raw_input().strip() for i in range(n)]
    black = 0
    minX, maxX, minY, maxY = n, 0, n, 0
    for x in range(0, n) :
        for y in range(0, n) :
            if b[x][y] == '#':
                minX, minY  = min(minX, x), min(minY, y)
                maxX, maxY = max(maxX, x), max(maxY, y)
                black += 1
    dx = maxX - minX + 1
    dy = maxY - minY + 1
    return dx == dy and dx * dy == black

if __name__ == '__main__':
    t = int(raw_input().strip())
    for i in range(1, t+1):
        res = solve()
        print 'Case #%d: %s' % (i, 'YES' if res else 'NO')
Read full article from Facebook Hacker Cup 2014 Quals: Square Detector | Random Math and Computer Science Related Things
 * @author het
 *
 */
public class F_Hacker {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
