package alite.leetcode.newExtra.finished;
/**
 * LeetCode 480 - Sliding Window Median

http://bookshadow.com/weblog/2017/01/08/leetcode-sliding-window-median/
Median is the middle value in an ordered integer list. If the size of the list is even, there is no middle value. So the median is the mean of the two middle value.
Examples:
[2,3,4] , the median is 3
[2,3], the median is (2 + 3) / 2 = 2.5
Given an array nums, there is a sliding window of size k which is moving from the very left of the array to the very right. You can only see the k numbers in the window. Each time the sliding window moves right by one position. Your job is to output the median array for each window in the original array.
For example,
Given nums = [1,3,-1,-3,5,3,6,7], and k = 3.
Window position                Median
---------------               -----
[1  3  -1] -3  5  3  6  7       1
 1 [3  -1  -3] 5  3  6  7       -1
 1  3 [-1  -3  5] 3  6  7       -1
 1  3  -1 [-3  5  3] 6  7       3
 1  3  -1  -3 [5  3  6] 7       5
 1  3  -1  -3  5 [3  6  7]      6
Therefore, return the median sliding window as [1,-1,-1,3,5,6].
Note: 
You may assume k is always valid, ie: 1 ≤ k ≤ input array's size for non-empty array.
二叉排序树（Binary Search Tree） / 堆（Heap）
维护二叉排序树hiSet与loSet，其中：

  hiSet的最小元素 > loSet的最大元素

  hiSet的大小 - loSet的大小 ∈ [0, 1]

获取中位数：

  若hiSet的大小 > loSet的大小，则返回hiSet.minValue
  
  否则，返回(hiSet.minValue + loSet.maxValue) / 2

新增元素：

  若hiSet为空，或者新元素 > hiSet.minValue，则加入hiSet
  
  否则加入loSet
  
  调整hiSet, loSet

移除元素：

  若hiSet包含目标元素，则从hiSet中移除
  
  否则，从loSet中移除
  
  调整hiSet, loSet

调整元素：

  若loSet的大小 > hiSet的大小，则将loSet的最大值弹出，加入hiSet
  
  否则，若hiSet的大小 - loSet的大小 > 1，则将hiSet的最小值弹出，加入loSet
    private Comparator<Point> cmp = new Comparator<Point>(){
        public int compare(Point a, Point b) {
            if (a.getX() != b.getX())
                return Double.valueOf(b.getX()).compareTo(Double.valueOf(a.getX()));
            return Double.valueOf(b.getY()).compareTo(Double.valueOf(a.getY()));
        }
    };

    private TreeSet<Point> hiSet = new TreeSet<>(cmp);
    private TreeSet<Point> loSet = new TreeSet<>(cmp);

    private double getMedian() {
        if (hiSet.size() > loSet.size()) {
            return hiSet.last().getX();
        }
        return ((hiSet.last().getX()) + (loSet.first().getX())) / 2;
    }
    
    private void addValue(int val, int i) {
        Point p = new Point(val, i);
        if (hiSet.isEmpty() || hiSet.last().getX() < p.getX()) {
            hiSet.add(p);
        } else {
            loSet.add(p);
        }
        adjustSet();
    }
    
    private void removeValue(int val, int i) {
        Point p = new Point(val, i);
        if (hiSet.contains(p)) {
            hiSet.remove(p);
        } else {
            loSet.remove(p);
        }
        adjustSet();
    }
    
    private void adjustSet() {
        if (loSet.size() > hiSet.size()) {
            hiSet.add(loSet.pollFirst());
        } else if (hiSet.size() > loSet.size() + 1) {
            loSet.add(hiSet.pollLast());
        }
    }
    
    public double[] medianSlidingWindow(int[] nums, int k) {
        double[] ans = new double[nums.length - k + 1];
        for (int i = 0; i < nums.length; i++) {
            if (i >= k) {
                removeValue(nums[i - k], i - k);
            }
            addValue(nums[i], i);
            if (i >= k - 1) {
                ans[i - k + 1] = getMedian();
            }
        }
        return ans;
    }

 * @author het
 *
 */
public class L480 {

}
