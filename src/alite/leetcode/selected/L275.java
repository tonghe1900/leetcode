package alite.leetcode.selected;
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
public class L275 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
