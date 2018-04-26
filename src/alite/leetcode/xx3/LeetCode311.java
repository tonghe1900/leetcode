package alite.leetcode.xx3;

import java.util.ArrayList;
import java.util.List;

/**
 * LeetCode 311 Sparse Matrix Multiplication

http://www.cnblogs.com/jcliBlogger/p/5015959.html
Given two sparse matrices A and B, return the result of AB.
You may assume that A's column number is equal to B's row number.
Example:
A = [
  [ 1, 0, 0],
  [-1, 0, 3]
]

B = [
  [ 7, 0, 0 ],
  [ 0, 0, 0 ],
  [ 0, 0, 1 ]
]


     |  1 0 0 |   | 7 0 0 |   |  7 0 0 |
AB = | -1 0 3 | x | 0 0 0 | = | -7 0 3 |
                  | 0 0 1 |
http://algobox.org/sparse-matrix-multiplication/

public int[][] multiply(int[][] A, int[][] B) {

    int n = A.length;

    int m = A[0].length;

    int p = B[0].length;

    int[][] AB = new int[n][p];


    for (int k = 0; k < m; ++k)

        for (int i = 0; i < n; ++i)

            if (A[i][k] != 0)

                for (int j = 0; j < p; ++j)

                    if (B[k][j] != 0)

                        AB[i][j] += A[i][k] * B[k][j];


    return AB;

}
http://yuancrackcode.com/2015/11/27/sparse-matrix-multiplication-2/
http://www.cs.cmu.edu/~scandal/cacm/node9.html
Given two sparse matrices A and B, return the result of AB.

    public int[][] multiply(int[][] A, int[][] B) {

        int row1 = A.length, col1 = A[0].length, col2 = B[0].length;

        int[][] result = new int[row1][col2];

        

        List[] rowA = new List[row1];

        for (int i = 0; i < row1; i++) {

            List<Integer> list = new ArrayList<Integer>();

            for (int j = 0; j < col1; j++) {

                if (A[i][j] != 0) {

                    list.add(j);

                    list.add(A[i][j]);

                }

            }

            rowA[i] = list;

        }

        

        for (int i = 0; i < row1; i++) {

            List<Integer> list = rowA[i];

            for (int p = 0; p < list.size() - 1; p += 2) {

                int colA = list.get(p);

                int valA = list.get(p + 1);

                for (int j = 0; j < col2; j++) {

                    result[i][j] += valA * B[colA][j];

                }

            }

        }

        return result;

    }



Naive:

    public int[][] multiply(int[][] A, int[][] B) {

        int row1 = A.length, col1 = A[0].length, row2 = B.length, col2 = B[0].length;

        int[][] result = new int[row1][col2];

        

        for (int i = 0; i < row1; i++) {

            for (int j = 0; j < col2; j++) {

                for (int k = 0; k < col1; k++) {

                    result[i][j] += A[i][k] * B[k][j];

                }

            }

        }

        return result;

    }

X. https://leetcode.com/discuss/71935/my-java-solution
     class Node {
        int x,y;
        Node(int x, int y) {
            this.x=x;
            this.y=y;
        }
    }
    public int[][] multiply(int[][] A, int[][] B) {
        int[][] result = new int[A.length][B[0].length];
        List<Node> listA = new ArrayList<>();
        List<Node> listB = new ArrayList<>();
        for (int i=0;i<A.length;i++) {
            for (int j=0; j<A[0].length; j++) {
                if (A[i][j]!=0) listA.add(new Node(i,j));
            }
        }
        for (int i=0;i<B.length;i++) {
            for (int j=0;j<B[0].length;j++) {
                if (B[i][j]!=0) listB.add(new Node(i,j));
            }
        }

        for (Node nodeA : listA) {
            for (Node nodeB: listB) {
                if (nodeA.y==nodeB.x) {
                    result[nodeA.x][nodeB.y] += A[nodeA.x][nodeA.y] * B[nodeB.x][nodeB.y];
                }
            }
        }

        return result;
https://discuss.leetcode.com/topic/30626/java-and-python-solutions-with-and-without-tables
Java solution with two tables (~160ms):
public class Solution {
    public int[][] multiply(int[][] A, int[][] B) {
        if (A == null || B == null) return null;
        if (A[0].length != B.length) 
            throw new IllegalArgumentException("A's column number must be equal to B's row number.");
        Map<Integer, HashMap<Integer, Integer>> tableA = new HashMap<>();
        Map<Integer, HashMap<Integer, Integer>> tableB = new HashMap<>();
        int[][] C = new int[A.length][B[0].length];
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[i].length; j++) {
                if (A[i][j] != 0) {
                    if(tableA.get(i) == null) tableA.put(i, new HashMap<Integer, Integer>());
                    tableA.get(i).put(j, A[i][j]);
                }
            }
        }

        for (int i = 0; i < B.length; i++) {
            for (int j = 0; j < B[i].length; j++) {
                if (B[i][j] != 0) {
                    if(tableB.get(i) == null) tableB.put(i, new HashMap<Integer, Integer>());
                    tableB.get(i).put(j, B[i][j]);
                }
            }
        }

        for (Integer i: tableA.keySet()) {
            for (Integer k: tableA.get(i).keySet()) {
                if (!tableB.containsKey(k)) continue;
                for (Integer j: tableB.get(k).keySet()) {
                    C[i][j] += tableA.get(i).get(k) * tableB.get(k).get(j);
                }
            }
        }
        return C;
    }
}
Java solution with only one table for B (~150ms):
public class Solution {
    public int[][] multiply(int[][] A, int[][] B) {
        if (A == null || A[0] == null || B == null || B[0] == null) return null;
        int m = A.length, n = A[0].length, l = B[0].length;
        int[][] C = new int[m][l];
        Map<Integer, HashMap<Integer, Integer>> tableB = new HashMap<>();

        for(int k = 0; k < n; k++) {
            tableB.put(k, new HashMap<Integer, Integer>());
            for(int j = 0; j < l; j++) {
                if (B[k][j] != 0){
                    tableB.get(k).put(j, B[k][j]);
                }
            }
        }

        for(int i = 0; i < m; i++) {
            for(int k = 0; k < n; k++) {
                if (A[i][k] != 0){
                    for (Integer j: tableB.get(k).keySet()) {
                        C[i][j] += A[i][k] * tableB.get(k).get(j);
                    }
                }
            }
        }
        return C;   
    }
}
Java solution without table (~70ms):
public class Solution {
    public int[][] multiply(int[][] A, int[][] B) {
        int m = A.length, n = A[0].length, l = B[0].length;
        int[][] C = new int[m][l];

        for(int i = 0; i < m; i++) {
            for(int k = 0; k < n; k++) {
                if (A[i][k] != 0){
                    for (int j = 0; j < l; j++) {
                        if (B[k][j] != 0) C[i][j] += A[i][k] * B[k][j];
                    }
                }
            }
        }
        return C;   
    }
}
 * @author het
 *
 */
public class LeetCode311 {
	public int[][] multiply(int[][] A, int[][] B) {

        int row1 = A.length, col1 = A[0].length, col2 = B[0].length;

        int[][] result = new int[row1][col2];

        

        List[] rowA = new List[row1];

        for (int i = 0; i < row1; i++) {

            List<Integer> list = new ArrayList<Integer>();

            for (int j = 0; j < col1; j++) {

                if (A[i][j] != 0) {

                    list.add(j);

                    list.add(A[i][j]);

                }

            }

            rowA[i] = list;

        }

        

        for (int i = 0; i < row1; i++) {

            List<Integer> list = rowA[i];

            for (int p = 0; p < list.size() - 1; p += 2) {

                int colA = list.get(p);

                int valA = list.get(p + 1);

                for (int j = 0; j < col2; j++) {

                    result[i][j] += valA * B[colA][j];

                }

            }

        }

        return result;

    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
