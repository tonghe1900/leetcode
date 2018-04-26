package alite.leetcode;

import java.util.Arrays;
import java.util.Comparator;

/**
 * There are a number of spherical balloons spread in two-dimensional space. For
 * each balloon, provided input is the start and end coordinates of the
 * horizontal diameter. Since it's horizontal, y-coordinates don't matter and
 * hence the x-coordinates of start and end of the diameter suffice. Start is
 * always smaller than end. There will be at most 104 balloons. An arrow can be
 * shot up exactly vertically from different points along the x-axis. A balloon
 * with xstart and xend bursts by an arrow shot at x if xstart ≤ x ≤ xend. There
 * is no limit to the number of arrows that can be shot. An arrow once shot
 * keeps travelling up infinitely. The problem is to find the minimum number of
 * arrows that must be shot to burst all balloons. Example: Input: [[10,16],
 * [2,8], [1,6], [7,12]]
 * 
 * Output: 2
 * 
 * Explanation: One way is to shoot one arrow for example at x = 6 (bursting the
 * balloons [2,8] and [1,6]) and another arrow at x = 11 (bursting the other two
 * balloons).
 * 
 * @author het
 * 
 */
public class LettCode452MinimumNumberofArrowstoBurstBalloons {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	// 假设现在有6个活动，下面是6个活动的起始和结束时间。
	//
	//
	// start[] = {1, 3, 0, 5, 8, 5};
	//
	//
	// finish[] = {2, 4, 6, 7, 9, 9};
	//
	//
	// 一个人一天最多能参见的活动为
	//
	//
	// {0, 1, 3, 4}
	//
	//
	// 关于活动选择问题就不在详细解说了。这道题非常算是变形。将所有的气球按照终止位置排序，开始从前向后扫描。以第一个气球的终止位置为准，只要出现的气球起始位置小于这个气球的终止位置，代表可以一箭使这些气球全部爆炸；当出现一个气球的起始位置大于第一个气球的终止位置时再以这个气球的终止位置为准，找出所有可以再一箭爆炸的所有气球；以此类推。。。

	public int findMinArrowShots(int[][] points) {

		if (points == null || points.length == 0 || points[0].length == 0) {

			return 0;

		}

		Arrays.sort(points, new Comparator<int[]>() {

			public int compare(int[] a, int[] b) {

				return a[1] - b[1];

			}

		});

		long lastEnd = Long.MIN_VALUE;

		int minArrows = 0;

		for (int i = 0; i < points.length; i++) {

			if (lastEnd < points[i][0]) {

				lastEnd = points[i][1];

				minArrows++;

			}

		}

		return minArrows;

	}

}
