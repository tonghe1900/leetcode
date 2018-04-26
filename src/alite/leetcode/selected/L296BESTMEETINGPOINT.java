package alite.leetcode.selected;
/**
 * A group of two or more people wants to meet and minimize the total travel distance.
 *  You are given a 2D grid of values 0 or 1, where each 1 marks the home of someone in the group.
 *   The distance is calculated using Manhattan Distance, where distance(p1, p2) = |p2.x - p1.x| + |p2.y - p1.y|.

For example, given three people living at (0,0), (0,4), and (2,2):

1 - 0 - 0 - 0 - 1
|   |   |   |   |
0 - 0 - 0 - 0 - 0
|   |   |   |   |
0 - 0 - 1 - 0 - 0
The point (0,2) is an ideal meeting point, as the total travel distance of 2+2+2=6 is minimal. So return 6.

Analysis:

经过观察容易发现最好的集合点P有这样的特性，向任何方向移动都会使得所有点移动的总距离增加，所以这样一个点的横纵坐标
都应该是所有点横纵坐标的中位数。原因是，如果将P向左移动一个单位，那么在P右边的点（至少一半）需要增加一个移动距离，而在
P左边的点（至多一半）会减少一个移动距离，所以总移动距离会增加一个非负数，向其他方向移动P同理。

如果这个2D grid是稀疏的，如下做法比较高效；如果不是，则需要把横纵坐标用两次遍历去储存，这样不需要另外排序，可以省去sort的步骤。

Solution:

1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
21
22
23
24
25
26
27
28
29
30
public class Solution {
    public int minTotalDistance(int[][] grid) {
        int count = 0;
        List<Integer> posX = new ArrayList<>();
        List<Integer> posY = new ArrayList<>();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 1) {
                    count++;
                    posX.add(j);
                    posY.add(i);
                }
            }
        }
        if (count == 0) {
            return 0;
        } else {
            int py = posY.get(count / 2);
            Collections.sort(posX);
            int px = posX.get(count / 2);
            int dis = 0;
            for (int i = 0; i < posX.size(); i++) {
                int x = posX.get(i);
                int y = posY.get(i);
                dis += Math.abs(px - x) + Math.abs(py - y);
            }
            return dis;
        }
    }
}
Solution code can also be found here: https://github.com/all4win/LeetCode.git
 * @author het
 *
 */
public class L296BESTMEETINGPOINT {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
