package alite.leetcode.xx2.sucess.extra;
/**
 * You are a product manager and currently leading a team to develop a new product. Unfortunately, the latest version
 *  of your product fails the quality check. Since each version is developed based on the previous version, all the
 *   versions after a bad version are also bad.
Suppose you have n versions [1, 2, ..., n] and you want to find out the first bad one, which causes all the following 
ones to be bad.
You are given an API bool isBadVersion(version) which will return whether version is bad. Implement a function to
 find the first bad version. You should minimize the number of calls to the API.
This is a typical example of type of binary search that does not return immediately after finding the satisfied element.
Suppose we have 2 pointers start and end. We take middle = (start + end) / 2. Now we check if middle is a bad version.
 If it is yes, we do not stop here, but we will search on the left, in order to find out that whether there is some 
 smaller bad version. By going to the left, we set end = middle - 1. So if we find nothing on the left, we return
  end + 1, because it is the last time we saw a bad version. If middle is not a bad version, we simply go to the
   right by setting start = middle + 1.
middle = ((end - start) >> 1)+start;
One thing to note is that in some programming language, to avoid overflow, we use (end-start)/2 + start
instead
of (start + end)/2 
    public int firstBadVersion(int n) {  
        int start = 1;  
        int end = n;  
        int middle;  
        while(start <= end){  
            middle = ((end - start)>>1) + start;  
            if (isBadVersion(middle)) end = middle - 1;  
            else start = middle + 1;  
        }  
        return end + 1;  
    }  
https://leetcode.com/articles/first-bad-version/
 We just need to test an input of size 2. Check if it reduces the search space to a single element (which must be the answer) for both of the scenarios above. If not, your algorithm will never terminate.
public int firstBadVersion(int n) {
    int left = 1;
    int right = n;
    while (left < right) {
        int mid = left + (right - left) / 2;
        if (isBadVersion(mid)) {
            right = mid;
        } else {
            left = mid + 1;
        }
    }
    return left;
}
Lintcode: First Bad Version
Also check http://storypku.com/2015/10/leetcode-question-278-first-bad-version/
The code base version is an integer and start from 1 to n. One day, someone commit a bad version in the code case, so it caused itself and the following versions are all failed in the unit tests.
13     public int findFirstBadVersion(int n) {
14         int l = 1;
15         int r = n;
16         while (l <= r) {
17             int m = (l + r) / 2; // use mid = l + (r - l) / 2; to avoid overflow
18             if (VersionControl.isBadVersion(m)) {
19                 r = m - 1;
20             }
21             else {
22                 l = m + 1;
23             }
24         }
25         return l; //
26     }
http://blog.welkinlan.com/2015/05/14/firstbadversion/

    public int findFirstBadVersion(int n) {

        int l = 1, r = n;

        while (l < r) {

            int mid = l + (r - l) / 2;

            if (VersionControl.isBadVersion(mid)) {

                r = mid;

            } else {

                l = mid + 1;

            }

        }

        return VersionControl.isBadVersion(l) ? l : -1;

    }
http://shibaili.blogspot.com/2015/09/day-129-277-find-celebrity.html
This is not efficient - the isBadVersion is called twice in the loop.

class Solution {

public:

    int firstBadVersion(int n) {

        int left = 1, right = n;

        while (left <= right) {

            int mid = left + (right - left) / 2;

            bool bad = isBadVersion(mid);

            if (bad && (mid == 1 || !isBadVersion(mid - 1))) return mid;

            if (bad) {

                right = mid - 1;

            }else {

                left = mid + 1;

            }

        }

         

        return -1;

    }

};
https://leetcode.com/articles/first-bad-version/
Brute force:
public int firstBadVersion(int n) {
    for (int i = 1; i < n; i++) {
        if (isBadVersion(i)) {
            return i;
        }
    }
    return n;
}
Lintcode: First Bad Version
 * @author het
 *
 */
public class LeetCode278FirstBadVersion {
    public int firstBadVersion(int n) {  
       int start = 1;   
        int end = n;  
        int middle;  
        while(start <= end){  
            middle = ((end - start)>>1) + start;  
            if (isBadVersion(middle)) end = middle - 1;  
            else start = middle + 1;  
        }  
        return end + 1;  
    }  
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
