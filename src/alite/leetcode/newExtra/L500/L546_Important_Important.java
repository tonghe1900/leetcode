package alite.leetcode.newExtra.L500;
/**
 * LeetCode 546 - Remove Boxes

https://leetcode.com/problems/remove-boxes
Given several boxes with different colors represented by different positive numbers. 
You may experience several rounds to remove boxes until there is no box left. Each time you can choose
 some continuous boxes with the same color (composed of k boxes, k >= 1), remove them and get k*k points.
Find the maximum points you can get.
Example 1:
Input:
[1, 3, 2, 2, 2, 3, 4, 3, 1]
Output:
23
Explanation:
[1, 3, 2, 2, 2, 3, 4, 3, 1] 
----> [1, 3, 3, 4, 3, 1] (3*3=9 points) 
----> [1, 3, 3, 3, 1] (1*1=1 points) 
----> [1, 1] (3*3=9 points) 
----> [] (2*2=4 points)
Note: The number of boxes n would not exceed 100.
X.
https://discuss.leetcode.com/topic/84300/java-dp-memorization-60ms
When facing this problem, I am keeping thinking how to simulate the case when boxes[i] == boxes[j] 
when i and j are not consecutive. It turns out that the dp matrix needs one more dimension to store such state. 
So we are going to define the state as
dp[i][j][k] represents the max points from box[i] to box[j] with k boxes whose values equal to box[i]
The transformation function is as below
dp[i][j][k] = max(dp[i+1][m-1][1] + dp[m][j][k+1]) when box[i] = box[m]
From your explanation
dp[i][j][k] represents the max points from box[i] to box[j] with k boxes whose values equal to box[i]
However, I think dp[i][j][k] should mean the max points from box[i] to box[j] with k boxes of value box[i] merged.
And also the DP
We have two condition
We choose to merge k boxes
this mean we would have score = dp(i+1, j, 1) + k * k ...............(1)
We don't merge k boxes
so, we can continue to find box which is the same
this means when we find box m equal to box i, we can have one more box, so k become k + 1
So we have dp(i+1, m-1, 1) + dp (m, j, k + 1) ...............(2)
the first term is the other boxes
and the second term contain information of the same boxes(box[i] or box[m]) we have found till now
dp[i][j][k] = max ((1), (2))
  public int removeBoxes(int[] boxes) {
        if (boxes == null || boxes.length == 0) {
            return 0;
        }

        int size = boxes.length;
        int[][][] dp = new int[size][size][size];

        return get(dp, boxes, 0, size-1, 1);
    }

    private int get(int[][][] dp, int[] boxes, int i, int j, int k) {
        if (i > j) {
            return 0;
        } else if (i == j) {
            return k * k;
        } else if (dp[i][j][k] != 0) {
            return dp[i][j][k];
        } else {
            int temp = get(dp, boxes, i + 1, j, 1) + k * k;

            for (int m = i + 1; m <= j; m++) {
                if (boxes[i] == boxes[m]) {
                    temp = Math.max(temp, get(dp, boxes, i + 1, m - 1, 1) + get(dp, boxes, m, j, k + 1));
                }
            }

            dp[i][j][k] = temp;
            return temp;
        }


    }

https://discuss.leetcode.com/topic/84687/java-top-down-and-bottom-up-dp-solutions
Finally here are the two solutions, one for top-down DP and the other for bottom-up DP. From the bottom-up solution, the time complexity will be O(n^4) and the space complexity will be O(n^3).
Top-down DP:
public int removeBoxes(int[] boxes) {
    int n = boxes.length;
    int[][][] dp = new int[n][n][n];
    return removeBoxesSub(boxes, 0, n - 1, 0, dp);
}
    
private int removeBoxesSub(int[] boxes, int i, int j, int k, int[][][] dp) {
    if (i > j) return 0;
    if (dp[i][j][k] > 0) return dp[i][j][k];
        
    int res = (k + 1) * (k + 1) + removeBoxesSub(boxes, i + 1, j, 0, dp);
        
    for (int m = i + 1; m <= j; m++) {
        if (boxes[i] == boxes[m]) {
            res = Math.max(res, removeBoxesSub(boxes, i + 1, m - 1, 0, dp) + removeBoxesSub(boxes, m, j, k + 1, dp));
        }
    }
        
    dp[i][j][k] = res;
    return res;
}



首先把连续相同颜色的盒子进行合并，得到数组color以及数组length，分别表示合并后盒子的颜色和长度。
dp[l][r][k]表示第l到第r个合并后的盒子，连同其后颜色为color[r]的长度k一并消去所能得到的最大得分。
dp[l][r][k] = dp[l][r - 1][0] + (length[r] + k) ** 2

dp[l][r][k] = max(dp[l][r][k], dp[l][i][length[r] + k] + dp[i + 1][r - 1][0])  其中 i ∈[l, r - 1]
 * @author het
 *
 */

//Note: The number of boxes n would not exceed 100.
//X.
//https://discuss.leetcode.com/topic/84300/java-dp-memorization-60ms
//When facing this problem, I am keeping thinking how to simulate the case when boxes[i] == boxes[j] 
//when i and j are not consecutive. It turns out that the dp matrix needs one more dimension to store such state. 
//So we are going to define the state as
//dp[i][j][k] represents the max points from box[i] to box[j] with k boxes whose values equal to box[i]
//The transformation function is as below
//dp[i][j][k] = max(dp[i+1][m-1][1] + dp[m][j][k+1]) when box[i] = box[m]
//From your explanation
//dp[i][j][k] represents the max points from box[i] to box[j] with k boxes whose values equal to box[i]
//However, I think dp[i][j][k] should mean the max points from box[i] to box[j] with k boxes of value box[i] merged.
//And also the DP
//We have two condition
//We choose to merge k boxes
//this mean we would have score = dp(i+1, j, 1) + k * k ...............(1)
//We don't merge k boxes
//so, we can continue to find box which is the same
//this means when we find box m equal to box i, we can have one more box, so k become k + 1
//So we have dp(i+1, m-1, 1) + dp (m, j, k + 1) ...............(2)
//the first term is the other boxes
//and the second term contain information of the same boxes(box[i] or box[m]) we have found till now
//dp[i][j][k] = max ((1), (2))
public class L546_Important_Important {
	
//	public int removeBoxes(int[] boxes) {
//        if (boxes == null || boxes.length == 0) {
//            return 0;
//        }
//
//        int size = boxes.length;
//        int[][][] dp = new int[size][size][size];
//
//        return get(dp, boxes, 0, size-1, 1);
//    }
//
//    private int get(int[][][] dp, int[] boxes, int i, int j, int k) {
//        if (i > j) {
//            return 0;
//        } else if (i == j) {
//            return k * k;
//        } else if (dp[i][j][k] != 0) {
//            return dp[i][j][k];
//        } else {
//            int temp = get(dp, boxes, i + 1, j, 1) + k * k;
//
//            for (int m = i + 1; m <= j; m++) {
//                if (boxes[i] == boxes[m]) {
//                    temp = Math.max(temp, get(dp, boxes, i + 1, m - 1, 1) + get(dp, boxes, m, j, k + 1));
//                }
//            }
//
//            dp[i][j][k] = temp;
//            return temp;
//        }
//
//
//    }
//	Finally here are the two solutions, one for top-down DP and the other for bottom-up DP. From the bottom-up solution, the time complexity will be O(n^4) and the space complexity will be O(n^3).
//	Top-down DP:
	
	//However, I think dp[i][j][k] should mean the max points from box[i] to box[j] with k boxes of value box[i] merged.
	//And also the DP
	
	// dp[0][n-1][0] = dp[1][n-1][0]+(1+0)^2 + dp[m][n-1][]
	
	// dp[i][j][k] = max(dp[i+1][j][0] + (k+1)^2, dp[i+1][m-1][0] + dp[m][j][k+1] )  i<m<=j and arr[i] = arr[m]
	//// dp[i][j][k] = max(dp[i+1][j][0] + (k+1)^2, dp[i+1][m-1][0] + dp[m][j][k+1] )  i<m<=j and arr[i] = arr[m]
	public int removeBoxes(int[] boxes) {
	    int n = boxes.length;
	    int[][][] dp = new int[n][n][n];
	    return removeBoxesSub(boxes, 0, n - 1, 0, dp);
	}
	    
	private int removeBoxesSub(int[] boxes, int i, int j, int k, int[][][] dp) {
	    if (i > j) return 0;
	    if (dp[i][j][k] > 0) return dp[i][j][k];
	        
	    int res = (k + 1) * (k + 1) + removeBoxesSub(boxes, i + 1, j, 0, dp);
	        
	    for (int m = i + 1; m <= j; m++) {
	        if (boxes[i] == boxes[m]) {
	            res = Math.max(res, removeBoxesSub(boxes, i + 1, m - 1, 0, dp) + removeBoxesSub(boxes, m, j, k + 1, dp));
	        }
	    }
	        
	    dp[i][j][k] = res;
	    return res;
	}
	
    public static void main(String [] args){
    	//new int {}[1, 3, 2, 2, 2, 3, 4, 3, 1];
    }

}
