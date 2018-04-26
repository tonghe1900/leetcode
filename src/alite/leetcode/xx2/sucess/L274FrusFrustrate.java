package alite.leetcode.xx2.sucess;
/**
 * LEETCODE 274. H-INDEX
LC address: H-Index

Given an array of citations (each citation is a non-negative integer) of a researcher, write a function 
to compute the researcher’s h-index.

According to the definition of h-index on Wikipedia: “A scientist has index h if h of his/her N papers have
 at least h citations each, and the other N − h papers have no more than h citations each.”

For example, given citations = [3, 0, 6, 1, 5], which means the researcher has 5 papers in total and 
each of them had received 3, 0, 6, 1, 5 citations respectively. Since the researcher has 3 papers with
 at least 3 citations each and the remaining two with no more than 3 citations each, his h-index is 3.

Note: If there are several possible values for h, the maximum one is taken as the h-index.

Analysis:

一种做法是直接排序，然后遍历数数，O(nlogn) + O(n)；另一种做法是一边排序一边找，类似k-th select sort加上binary search，
O(n) * O(logn) = O(nlogn)；还有一种就是直接利用radix sort的方法，记录累计论文数量，从引用次数高到引用次数低，
额外使用O(n)空间，时间也是O(n)，我就偷个懒，只写了这个。

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
public class Solution {
    public int hIndex(int[] citations) {
        if (citations == null || citations.length == 0) {
            return 0;
        }
        int n = citations.length;
        int[] count = new int[n + 2];
        for (int num : citations) {
            num = Math.min(num, n + 1);
            count[num] += 1;
        }
         
        for (int i = n; i >= 0; i--) {
            count[i] += count[i + 1];
            if (count[i] >= i) {
                return i;
            }
        }
        return 0;
    }
}
 * @author het
 *
 */
public class L274FrusFrustrate {
	public static int hIndex(int[] citations) {
        if (citations == null || citations.length == 0) {
            return 0;
        }
        int n = citations.length;
        int[] count = new int[n + 2];
        for (int num : citations) {
        	num = Math.min(num, n + 1);
            count[num] += 1;
        }
         
        for (int i = n; i >= 0; i--) {
            count[i] += count[i + 1];
            if (count[i] >= i) {
                return i;
            }
        }
        return 0;
    }
	
	
	public static int hIndex1(int[] citations) {
        if (citations == null || citations.length == 0) {
            return 0;
        }
        int n = citations.length;
        int[] count = new int[n + 1];
        for (int num : citations) {
        	num = Math.min(num, n);
            count[num] += 1;
        }
        if(count[n ] >= n) return n;
        for (int i = n-1; i >= 0; i--) {
        	count[i] += count[i + 1];
        	 if (count[i] >= i) {
                 return i;
             }
            
           
        }
        return 0;
    }
	public static void main(String[] args) {
		System.out.println(hIndex(new int[]{3,3,4,1}));
		// TODO Auto-generated method stub
		 //[3, 0, 6, 1, 5]
	}

}
