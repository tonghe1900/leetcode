package alite.leetcode.xx2.sucess;
/**
 * LEETCODE 275. H-INDEX II
LC address: H-Index II

Follow up for H-Index: What if the citations array is sorted in ascending order? Could you optimize your algorithm?

Analysis:

因为已经是sorted，直接二分查找就可以了。

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
public class Solution {
    public int hIndex(int[] citations) {
        if (citations == null || citations.length == 0) {
            return 0;
        }
        int start = 0;
        int end = citations.length - 1;
        while (start <= end) {
            int mid = (end - start) / 2 + start;
            int i = citations.length - mid;
            if (citations[mid] >= i && (mid == 0 || citations[mid - 1] <= i)) {
                return i;
            } else {
                if (citations[mid] >= i) {
                    end = mid;
                } else {
                    start = mid + 1;
                }
            }
        }
        return citations.length - start;
    }
}
 * @author het
 *
 */
public class L275FrusFrustrateImportant {
	
//	 public static int hIndex1(int[] citations, int start, int end) {
//	        if (citations == null || citations.length == 0) {
//	            return 0;
//	        }
//	        if(start > end) return 0;
//	        
//	       
//	       
//	            int mid = (end - start) / 2 + start;
//	            int i = end - mid + 1;
//	            if (citations[mid] >= i && (mid == 0 || citations[mid - 1] <= i)) {
//	                return i;
//	            } else {
//	                if (citations[mid] >= i) {
//	                    return i + hIndex1(citations, start, mid-1);
//	                } else {
//	                    return hIndex1(citations, mid+1, end);
//	                }
//	            }
//	       
//	      //  return citations.length - start;
//	    }
	 public static int hIndex(int[] citations) {
	        if (citations == null || citations.length == 0) {
	            return 0;
	        }
	        int start = 0;
	        int end = citations.length - 1;
	        while (start <= end) {
	            int mid = (end - start) / 2 + start;
	            int i = citations.length - mid;
	            if (citations[mid] >= i && (mid == 0 || citations[mid - 1] <= i)) {
	                return i;
	            } else {
	                if (citations[mid] >= i) {
	                    end = mid;
	                } else {
	                    start = mid + 1;
	                }
	            }
	        }
	        return citations.length - start;
	    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(hIndex(new int[]{3,4,4 , 5, 6}));
		//System.out.println(hIndex1(new int[]{3,4,4 , 5, 6}, 0, 4));

	}

}
