package alite.leetcode.xx4.left;

import java.util.Arrays;

/**
 * http://bookshadow.com/weblog/2016/11/13/leetcode-assign-cookies/
Assume you are an awesome parent and want to give your children some cookies.
 But, you should give each child at most one cookie. Each child i has a greed factor gi, 
 which is the minimum size of a cookie that the child will be content with;
  and each cookie j has a size sj. If sj >= gi, we can assign the cookie j to the child i,
   and the child i will be content. Your goal is to maximize the number of your content 
   children and output the maximum number.
Note:
You may assume the greed factor is always positive.
You cannot assign more than one cookie to one child.
Example 1:
Input: [1,2,3], [1,1]

Output: 1

Explanation: You have 3 children and 2 cookies. The greed factors of 3 children are 1, 2, 3. 
And even though you have 2 cookies, since their size is both 1, you could only make the child whose greed factor is 1 content.
You need to output 1.
Example 2:
Input: [1,2], [1,2,3]

Output: 2

Explanation: You have 2 children and 3 cookies. The greed factors of 2 children are 1, 2. 
You have 3 cookies and their sizes are big enough to gratify all of the children, 
You need to output 2.
首先对贪婪系数g、饼干尺寸s从小到大排序

令指针i指向g的末尾，指针j指向s的末尾

当g和s均≥0时，执行循环：

  若g[i] ≤ s[j] 则令计数器+1，并令j -= 1 （将j号饼干分配给i号孩子，并令j指向下一个更小的饼干）

  令i -= 1 （将i指向下一个贪婪系数更小的孩子）
    def findContentChildren(self, g, s):
        """
        :type g: List[int]
        :type s: List[int]
        :rtype: int
        """
        cnt = 0
        i, j = len(g) - 1, len(s) - 1
        g, s = sorted(g), sorted(s)
        while min(i, j) >= 0:
            if g[i] <= s[j]:
                cnt += 1
                j -= 1
            i -= 1
        return cnt
https://discuss.leetcode.com/topic/68455/array-sort-two-pointer-greedy-solution-o-nlogn
https://discuss.leetcode.com/topic/67589/clean-java-solution-o-nlogn
Two assign cookies to children optimaly we should give for each child the closest higher cookie. By using this greedy approach overall sum of wasted cookies will be minimum amoung all. To use this greedy solution in effective way we can sort both arrays and use two pointers. We should move pointer of children only if there is enough cookies to make that child content. In each step we will try to make content child at position pointerG by searching the closes higher cookie value.
    public int findContentChildren(int[] g, int[] s) {
        Arrays.sort(g);
        Arrays.sort(s);
        
        int pointG = 0;
        int pointS = 0;
        
        while (pointG<g.length && pointS<s.length) {
            if (g[pointG]<=s[pointS]) {
                pointG++;
                pointS++;
            } else {
                pointS++;
            }
        }
        
        return pointG;
    }
http://blog.csdn.net/mebiuw/article/details/53192830
    public int findContentChildren(int[] g, int[] s) {
        Arrays.sort(g);
        Arrays.sort(s);
        int res = 0;
        int i = 0;
        int j = 0;
        while( i < g.length && j < s.length){
            if( g[i]<=s[j] ){
                i++;
                j++;
                res++;
            } else{
                j++;
            }
        }
        return res;

    }
X. https://discuss.leetcode.com/topic/68288/java-solution-with-binary-search-tree
    public static int findContentChildren(int[] g, int[] s) {
     int count = 0;
     TreeMap<Integer,Integer> tree = new TreeMap<>();
     for(int temp : s){
      Integer num = tree.get(temp);
      num = num==null?0:num;
      tree.put(temp,num+1);
     }
     for(int temp : g){
      Integer targ = tree.ceilingKey(temp);
      if(targ!=null){
       Integer num = tree.get(targ);
       if(num>0){
        count++;
        if(num==1){
         tree.remove(targ);
        }else{
                                        tree.put(targ, num - 1);
                                }
       }
      }
     }
        return count;
    }

https://discuss.leetcode.com/topic/67676/simple-greedy-java-solution
 * @author het
 *
 */
public class LeetCode455AssignCookies {
	public int findContentChildren(int [] g ,int [] s){
		int result = 0;
		int i = g.length -1;
		int j = s.length - 1;
		Arrays.sort(g);
		Arrays.sort(s);
		while(Math.min(i, j) >=0){
			if(g[i] <=s[j]){
				result+=1;
				j-=1;
			}
			i-=1;
		}
		return result;
	}
//	 def findContentChildren(self, g, s):
//	       
//	        cnt = 0
//	        i, j = len(g) - 1, len(s) - 1
//	        g, s = sorted(g), sorted(s)
//	        while min(i, j) >= 0:
//	            if g[i] <= s[j]:
//	                cnt += 1
//	                j -= 1
//	            i -= 1
//	        return cnt
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
