package alite.leetcode.xx3;
/**
 * 【leetcode】349. Intersection of Two Arrays
Given two arrays, write a function to compute their intersection.

Example:
Given nums1 = [1, 2, 2, 1], nums2 = [2, 2], return [2].

Note:

Each element in the result must be unique.
The result can be in any order.
 

我们可以使用两个指针来做，先给两个数组排序，然后用两个指针分别指向两个数组的开头，

然后比较两个数组的大小，把小的数字的指针向后移，如果两个指针指的数字相等，就加入到结果中。

但是结果中的数字不可以重复，可以用unique然后erase。（好像有些麻烦）也可以在结果中判断最后一个数字

是不是和当前的数字相等（因为已经sort过了）或者结果是不是empty，记住判断empty要在前面，否则直接

对一个空的vector求back会报错。

 

复制代码
复制代码
class Solution {
public:
    vector<int> intersection(vector<int>& nums1, vector<int>& nums2) {
        vector<int> ret;
        if(nums1.size()==0||nums2.size()==0)
            return ret;

        sort(nums1.begin(),nums1.end());
        sort(nums2.begin(),nums2.end());

        
        auto i=nums1.begin();
        auto j=nums2.begin();
        while(i!=nums1.end()&&j!=nums2.end()){
            if(*i<*j)
                i++;
            else if(*i>*j)
                j++;
            else {
                if(ret.empty()||ret.back()!=*i)
                    ret.push_back(*i);
                i++;
                j++;
            }
        }
        return ret;        
    }
};
 * @author het
 *
 */
public class LeetCode349 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
