package alite.leetcode.xx4;
/**
 * https://leetcode.com/problems/third-maximum-number/
Given a non-empty array of integers, return the third maximum number in this array. If it does not exist, return the maximum number. The time complexity must be in O(n).
Example 1:
Input: [3, 2, 1]

Output: 1

Explanation: The third maximum is 1.
Example 2:
Input: [1, 2]

Output: 2

Explanation: The third maximum does not exist, so the maximum (2) is returned instead.
Example 3:
Input: [2, 2, 3, 1]

Output: 1

Explanation: Note that the third maximum here means the third maximum distinct number.
Both numbers with value 2 are both considered as second maximum.
http://www.jianshu.com/p/e9dfd2494f41
 public int thirdMax (int[] nums) {
     long first = Long.MIN_VALUE;
     long second = Long.MIN_VALUE;
     long third = Long.MIN_VALUE;
     for (int i:nums){
         if (i>first){
             third = second;
             second = first;
             first = i;
         }else if (i == first)     //必要
             continue;
         else if (i > second){
             third = second;
             second = i;
         }else if (i == second)     //必要
             continue;
         else if (i > third){
             third = i;
         }
     }
     return third == Long.MIN_VALUE ? (int)first : (int)third;
       //method 要求返回类型为int，强制转化一下，narrowing conversion
 }
https://discuss.leetcode.com/topic/63671/o-n-time-o-1-space-java-short-solution
    public int thirdMax(int[] nums) {
        long first=Long.MIN_VALUE;
        long second=Long.MIN_VALUE;
        long third=Long.MIN_VALUE;
        for(int i:nums){
            if(i>first){
                third=second;
                second=first;
                first=i;
            }else if(i==first)
                continue;
            else if(i>second){
                third=second;
                second=i;
            }else if(i==second)
                continue;
            else if(i>third){
                third=i;
            }
        }
        return third==Long.MIN_VALUE?(int)first:(int)third;
    }

https://discuss.leetcode.com/topic/62236/java-solution-in-0ms-run-time-o-n-and-space-o-1
Another way to check whether the third maximum number exists is to declare all three numbers as long integer type and initialize them to the corresponding minimum value. Then at last if the third number is still the minimum value of long integer, we know it does not exist (its value will not get updated if it does not exist). Something like this:
public int thirdMax(int[] nums) {
    long max = Long.MIN_VALUE, mid = max, min = max;
        
    for (int ele : nums) {
        if (ele > max) {
            min = mid;
            mid = max;
            max = ele;
        } else if (max > ele && ele > mid) {
            min = mid;
            mid = ele;
        } else if (mid > ele && ele > min) {
            min = ele;
        }
    }
        
    return (int)(min != Long.MIN_VALUE ? min : max);
}

public int thirdMax(int[] nums) {
        int max, mid, small, count;
        max = mid = small = Integer.MIN_VALUE;
        count = 0;  //Count how many top elements have been found.

        for( int x: nums) {
            //Skip loop if max or mid elements are duplicate. The purpose is for avoiding right shift.
            if( x == max || x == mid ) {
                continue;
            }

            if (x > max) {
                //right shift
                small = mid;
                mid = max;

                max = x;
                count++;
            } else if( x > mid) {
                //right shift
                small = mid;

                mid = x;
                count++;
            } else if ( x >= small) { //if small duplicated, that's find, there's no shift and need to increase count.
                small = x;
                count++;
            }
        }

        //"count" is used for checking whether found top 3 maximum elements.
        if( count >= 3) { 
            return small;
        } else {
            return max;
        }
    }
http://bookshadow.com/weblog/2016/10/09/leetcode-third-maximum-number/
利用变量a, b, c分别记录数组第1,2,3大的数字
遍历一次数组即可，时间复杂度O(n)

    def thirdMax(self, nums):
        """
        :type nums: List[int]
        :rtype: int
        """
        a = b = c = None
        for n in nums:
            if n > a:
                a, b, c = n, a, b
            elif a > n > b:
                b, c = n, b
            elif b > n > c:
                c = n
        return c if c is not None else a


X. TreeSet
https://discuss.leetcode.com/topic/63903/short-easy-c-using-set/8
  public int thirdMax(int[] nums) {
    TreeSet<Integer> set = new TreeSet<>();
    for(int num : nums) {
      set.add(num);
      if(set.size() > 3) {
        set.remove(set.first());
      }
    }
    return set.size() < 3 ? set.last() : set.first();
  }
https://discuss.leetcode.com/topic/65119/java-solution-using-treeset
    public final int N = 3;
    public int thirdMax(int[] nums) {
        if (nums.length == 0) return 0;

        TreeSet<Integer> set = new TreeSet<Integer>();
        for (int i = 0; i < nums.length; i++) {
            if (set.contains(nums[i])) continue;
            if (set.size() < N || nums[i] > set.first()) {
                if (set.size() == N) set.remove(set.first());
                set.add(nums[i]);
            }
        }
        return set.size() == N ? set.first() : set.last();
    }

http://www.cnblogs.com/grandyang/p/5983113.html
下面这种方法利用了set的自动排序和自动去重复项的特性，很好的解决了问题，对于遍历到的数字，加入set中，重复项就自动去掉了，如果此时set大小大于3个了，那么我们把set的第一个元素去掉，也就是将第四大的数字去掉，那么就可以看出set始终维护的是最大的三个不同的数字，最后遍历结束后，我们看set的大小是否为3，是的话就返回首元素，不是的话就返回尾元素

    int thirdMax(vector<int>& nums) {
        set<int> s;
        for (int num : nums) {
            s.insert(num);
            if (s.size() > 3) {
                s.erase(s.begin());
            }
        }
        return s.size() == 3 ? *s.begin() : *s.rbegin();
    }
https://discuss.leetcode.com/topic/63086/java-priorityqueue-o-n-o-1
you should use pq.poll() instead of pq.remove(pq.poll())

    public int thirdMax(int[] nums) {
       PriorityQueue<Integer> pq = new PriorityQueue<>();
       Set<Integer> set = new HashSet<>();
       for(int n : nums) {
           if(set.add(n)) {
               pq.offer(n);
               if(pq.size() > 3 ) pq.remove(pq.poll());
           }
       }
       if(pq.size() == 2) pq.poll();
       return pq.peek();
    }
 * @author het
 *
 */
public class LeetCode414ThirdMaximumNumber {
	public int thirdMax (int[] nums) {
	     long first = Long.MIN_VALUE;
	     long second = Long.MIN_VALUE;
	     long third = Long.MIN_VALUE;
	     for (int i:nums){
	         if (i>first){
	             third = second;
	             second = first;
	             first = i;
	         }else if (i == first)     //必要
	             continue;
	         else if (i > second){
	             third = second;
	             second = i;
	         }else if (i == second)     //必要
	             continue;
	         else if (i > third){
	             third = i;
	         }
	     }
	     return third == Long.MIN_VALUE ? (int)first : (int)third;
	}
	       //method 要求返回类型为int，强制转化一下，narrowing conversion
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
