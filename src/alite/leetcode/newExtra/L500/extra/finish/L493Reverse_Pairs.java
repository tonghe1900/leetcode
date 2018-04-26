package alite.leetcode.newExtra.L500.extra.finish;
/**
 * 
https://leetcode.com/problems/reverse-pairs/
Given an array nums, we call (i, j) an important reverse pair if i < j and nums[i] > 2*nums[j].
You need to return the number of important reverse pairs in the given array.
Example1:
Input: [1,3,2,3,1]
Output: 2
Example2:
Input: [2,4,3,5,1]
Output: 3
Note:
The length of the given array will not exceed 50,000.
All the numbers in the input array are in the range of 32-bit integer.

X. BST
https://discuss.leetcode.com/topic/78933/very-short-and-clear-java-solution-using-bst
Build the Binary Search Tree from right to left, and at the same time, search the partially built tree with nums[i]/2.0
Similar to this https://leetcode.com/problems/count-of-smaller-numbers-after-self/. But the main difference is: here, the number to add and 
the number to search are different (add nums[i], but search nums[i]/2.0), so not a good idea to combine build and search together.
    public int reversePairs(int[] nums) {
        Node root = null;
        int[] cnt = new int[1];
        for(int i = nums.length-1; i>=0; i--){
            search(cnt, root, nums[i]/2.0);//search and count the partially built tree
            root = build(nums[i], root);//add nums[i] to BST
        }
        return cnt[0];
    }
    
    private void search(int[] cnt, Node node, double target){
        if(node==null) return; 
        else if(target == node.val) cnt[0] += node.less;
        else if(target < node.val) search(cnt, node.left, target);
        else{
            cnt[0]+=node.less + node.same; 
            search(cnt, node.right, target);
        }
    }
    
    private Node build(int val, Node n){
        if(n==null) return new Node(val);
        else if(val == n.val) n.same+=1;
        else if(val > n.val) n.right = build(val, n.right);
        else{
            n.less += 1;
            n.left = build(val, n.left);
        }
        return n;
    }
    
    class Node{
        int val, less = 0, same = 1;//less: number of nodes that less than this node.val
        Node left, right;
        public Node(int v){
            this.val = v;
        }
    }
X. Merge Sort
https://discuss.leetcode.com/topic/78950/java-merge-sort-solution-o-nlog-n
    public int ret;
    public int reversePairs(int[] nums) {
        ret = 0;
        mergeSort(nums, 0, nums.length-1);
        return ret;
    }

    public void mergeSort(int[] nums, int left, int right) {
        if (right <= left) {
            return;
        }
        int middle = left + (right - left)/2;
        mergeSort(nums, left, middle);
        mergeSort(nums,middle+1, right);

        //count elements
        int count = 0;
        for (int l = left, r = middle+1; l <= middle;) {
            if (r > right || (long)nums[l] <= 2*(long)nums[r]) {
                l++;
                ret += count;
            } else {
                r++;
                count++;
            }
        }
        
        //merge sort
        int[] temp = new int[right - left + 1];
        for (int l = left, r = middle+1, k = 0; l <= middle || r <= right;) {
            if (l <= middle && ((r > right) || nums[l] < nums[r])) {
                temp[k++] = nums[l++];
            } else {
                temp[k++] = nums[r++];
            }
        }
        for (int i = 0; i < temp.length; i++) {
            nums[left + i] = temp[i];
        }
    }

http://blog.csdn.net/kakitgogogo/article/details/55097648
给一个序列，要求求出其中“重要反转对”的个数。如果用暴力的方法对每个数nums[i]都找一遍符合nums[i] > 2*nums[j]，要运算n*(n-1)/2次。参考discuss的答案。。用merge_sort的想法来实现。对当前的序列二分，对每个子序列，求出在每个子序列中“重要反转对”的个数（递归实现），然后对每个子序列用merge_sort排序。这样得到两个子序列各自的“重要反转对”的个数，还有排序后的子序列，然后两个子序列各自的“重要反转对”的个数的和，再加上第一个子序列中的数和第二个子序列中的数作比较符合要求的总数（因为已经排好序所以容易实现），就能得到当前序列的“重要反转对”的个数，最后将两个子序列merge_sort并返回当前结果。要注意的是比较nums[i] > 2*nums[j]时，会出现2*nums[j]溢出int的范围，所以要注意转换成long long。

这道题一看就是二分查找的典型题，再用一个同样大的数组保存元素2*nums的值，然后对这个数组排序。剩下的操作都是显而易见了，全是二分查找的基本操作
 * @author het
 *
 */
public class L493Reverse_Pairs {
//	Build the Binary Search Tree from right to left, and at the same time, search the partially built tree with nums[i]/2.0
//	Similar to this https://leetcode.com/problems/count-of-smaller-numbers-after-self/. But the main difference is: here, the number to add and 
//	the number to search are different (add nums[i], but search nums[i]/2.0), so not a good idea to combine build and search together.
	    public int reversePairs(int[] nums) {
	        Node root = null;
	        int[] cnt = new int[1];
	        for(int i = nums.length-1; i>=0; i--){
	            search(cnt, root, nums[i]/2.0);//search and count the partially built tree
	            root = build(nums[i], root);//add nums[i] to BST
	        }
	        return cnt[0];
	    }
	    
	    private void search(int[] cnt, Node node, double target){
	        if(node==null) return; 
	        else if(target == node.val) cnt[0] += node.less;
	        else if(target < node.val) search(cnt, node.left, target);
	        else{
	            cnt[0]+=node.less + node.same; 
	            search(cnt, node.right, target);
	        }
	    }
	    
	    private Node build(int val, Node n){
	        if(n==null) return new Node(val);
	        else if(val == n.val) n.same+=1;
	        else if(val > n.val) n.right = build(val, n.right);
	        else{
	            n.less += 1;
	            n.left = build(val, n.left);
	        }
	        return n;
	    }
	    
	    class Node{
	        int val, less = 0, same = 1;//less: number of nodes that less than this node.val
	        Node left, right;
	        public Node(int v){
	            this.val = v;
	        }
	    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
