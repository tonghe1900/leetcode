package alite.leetcode.xx2.sucess.before;
/**
 * LeetCode 229 + Lintcode: Majority Number II - neverlandly - 博客园

Lintcode: Majority Number II - neverlandly - 博客园
Given an array of integers, the majority number is the number that occurs more than 1/3 of the size of the array. Find it. 

Note There is only one majority number in the array Example For [1, 2, 1, 2, 1, 3, 3] return 1
http://blog.csdn.net/nicaishibiantai/article/details/43635069
1. 我们对cnt1,cnt2减数时，相当于丢弃了3个数字（当前数字，n1, n2）。也就是说，每一次丢弃数字，我们是丢弃3个不同的数字。
而Majority number超过了1/3所以它最后一定会留下来。
设定总数为N, majority number次数为m。丢弃的次数是x。则majority 被扔的次数是x
而m > N/3, N - 3x > 0. 
3m > N,  N > 3x 所以 3m > 3x, m > x 也就是说 m一定没有被扔完
最坏的情况，Majority number每次都被扔掉了，但它一定会在n1,n2中。
2. 为什么最后要再检查2个数字呢？因为数字的编排可以让majority 数被过度消耗，使其计数反而小于n2，或者等于n2.前面举的例子即是。
另一个例子：
1 1 1 1 2 3 2 3 4 4 4 这个 1就会被消耗过多，最后余下的反而比4少。
http://algorithm.yuanbin.me/zh-cn/math_and_bit_manipulation/majority_number_ii.html
 首先处理count == 0的情况，这里需要注意的是count2 == 0 && key1 = num, 不重不漏。最后再次遍历原数组也必不可少，因为由于添加顺序的区别，count1 和 count2的大小只具有相对意义，还需要最后再次比较其真实计数器值。
    public int majorityNumber(ArrayList<Integer> nums) {
        if (nums == null || nums.isEmpty()) return -1;
        // pair
        int key1 = -1, key2 = -1;
        int count1 = 0, count2 = 0;
        for (int num : nums) {
            if (count1 == 0) {
                key1 = num;
                count1 = 1;
                continue;
            } else if (count2 == 0 && key1 != num) {
                key2 = num;
                count2 = 1;
                continue;
            }
            if (key1 == num) {
                count1++;
            } else if (key2 == num) {
                count2++;
            } else {
                count1--;
                count2--;
            }
        }

        count1 = 0;
        count2 = 0;
        for (int num : nums) {
            if (key1 == num) {
                count1++;
            } else if (key2 == num) {
                count2++;
            }
        }
        return count1 > count2 ? key1 : key2;
    }
}
http://www.jiuzhang.com/solutions/majority-number-ii/
    public int majorityNumber(ArrayList<Integer> nums) {
        int candidate1 = 0, candidate2 = 0;
        int count1, count2;
        count1 = count2 = 0;
        for (int i = 0; i < nums.size(); i++) {
            if (candidate1 == nums.get(i)) {
                count1 ++;
            } else if (candidate2 == nums.get(i)) {
                count2 ++;
            } else if (count1 == 0) {
                candidate1 = nums.get(i);
                count1 = 1;
            } else if (count2 == 0) {
                candidate2 = nums.get(i);
                count2 = 1;
            } else {
                count1--;
                count2--;
            }
        }
        count1 = count2 = 0;
        for (int i = 0; i < nums.size(); i++) {
            if (nums.get(i) == candidate1) {
                count1++;
            } else if (nums.get(i) == candidate2) {
                count2++;
            }
        }    
        return count1 > count2 ? candidate1 : candidate2;
    }
}
http://codechen.blogspot.com/2015/06/leetcode-majority-element-ii.html
这道题和LintCode的Majority Number II很像，但是稍有不同，需要考虑一个特殊情况：

Input:
[1,2]
Expected:
[1,2]

When there are two different elements in the array, both of the two elements appear more than n/3 times.
    public List<Integer> majorityElement(int[] nums) {
        List<Integer> result = new ArrayList<Integer>();
        if (nums == null || nums.length == 0) {
            return result;
        }
        
        int candidate1 = 0;
        int candidate2 = 0;
        int count1 = 0;
        int count2 = 0;
        
        for (int i = 0; i < nums.length; i++) {
            if (count1 == 0) {
                candidate1 = nums[i];
            } else if (count2 == 0) {
                candidate2 = nums[i];
            }
            
            if (nums[i] == candidate1) {
                count1++;
            }
            else if (nums[i] == candidate2) {
                count2++;
            } else {
                count1--;
                count2--;
            }
        }
        
        count1 = 0;
        count2 = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == candidate1) {
                count1++;
            }else if (nums[i] == candidate2) {
                count2++;
            }
        }
        
        if (count1 > nums.length / 3) {
            result.add(candidate1);
        }
        if (count2 > nums.length / 3) {
            result.add(candidate2);
        }
        
        return result;
    }
http://www.programcreek.com/2014/07/leetcode-majority-element-ii-java/
public List<Integer> majorityElement(int[] nums) {
    List<Integer> result = new ArrayList<Integer>();
 
    Integer n1=null, n2=null;
    int c1=0, c2=0;
 
    for(int i: nums){
        if(n1!=null && i==n1.intValue()){
            c1++;
        }else if(n2!=null && i==n2.intValue()){
            c2++;
        }else if(c1==0){
            c1=1;
            n1=i;
        }else if(c2==0){
            c2=1;
            n2=i;
        }else{
            c1--;
            c2--;
        }
    }
 
    c1=c2=0;
 
    for(int i: nums){
        if(i==n1.intValue()){
            c1++;
        }else if(i==n2.intValue()){
            c2++;
        }
    }
 
    if(c1>nums.length/3)
        result.add(n1);
    if(c2>nums.length/3)
        result.add(n2);
 
    return result;
}
Using Counter Map:
public List<Integer> majorityElement(int[] nums) {
    HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
    for(int i: nums){
        if(map.containsKey(i)){
            map.put(i, map.get(i)+1);
        }else{
            map.put(i, 1);
        }
    }
 
    List<Integer> result = new ArrayList<Integer>();
 
    for(Map.Entry<Integer, Integer> entry: map.entrySet()){
        if(entry.getValue() > nums.length/3){
            result.add(entry.getKey());
        }    
    }
 
    return result;
}


    public int majorityNumber(ArrayList<Integer> nums) {
 7         // write your code
 8         // When there are only 1 or 2 elements in the array,
 9         // there is no solution.
10         if (nums == null || nums.size() <= 2) {
11             return -1;
12         }
13         
14         int n1 = 0;
15         int n2 = 0;
16         
17         int cnt1 = 0;
18         int cnt2 = 0;
19         
20         int size = nums.size();
21         for (int i = 0; i < size; i++) {
22             int num = nums.get(i);
23             if (cnt1 != 0 && num == n1) {
24                 cnt1++;
25             } else if (cnt2 != 0 && num == n2) {
26                 cnt2++;
27             } else if (cnt1 == 0) {
28                 cnt1 = 1;
29                 n1 = num;
30             } else if (cnt2 == 0) {
31                 cnt2 = 1;
32                 n2 = num;
33             } else {
34                 cnt1--;
35                 cnt2--;
36             }
37         }
38         
39         // count the two candiates.
40         cnt1 = 0;
41         cnt2 = 0;
42         for (int num: nums) {
43             if (num == n1) {
44                 cnt1++;
45             } else if (num == n2) {
46                 cnt2++;
47             }
48         }
49         
50         if (cnt1 < cnt2) {
51             return n2;
52         }        
53         
54         return n1;
55     }
https://reeestart.wordpress.com/2016/07/01/google-majority-number-sorted/
给一个sorted array, 找出所有出现次数大于n/4的数。
[Solution]
看到sorted，应该很本能的反应出binary search。 但是这道题怎么binary search还是有点技巧。首先找出1/4, 2/4, 3/4三个数作为candidates, 因为题目要求是__大于n/4的才是majority，如果是大于等于__，那么就应该取1/4, 2/4, 3/4, 4/4四个candidates。
取完candidates，对每个进行binary search分别找floor和ceiling，然后看index range是不是满足要求(大于n/4)。
[Note]
candidates之间注意去重，有可能某一个candidate甚至出现了超过n/2次。

  public List<Integer> majorityNumber(int[] nums) {

    List<Integer> result = new ArrayList<>();

    if (nums == null || nums.length == 0) {

      return result;

    }

    int n = nums.length;

 

    int candidate1 = nums[n / 4];

    int candidate2 = nums[n / 2];

    int candidate3 = nums[3 * n / 4];

 

    if (isMajority(nums, candidate1)) {

      result.add(candidate1);

    }

    if (candidate2 != candidate1 && isMajority(nums, candidate2)) {

      result.add(candidate2);

    }

    if (candidate3 != candidate2 && isMajority(nums, candidate3)) {

      result.add(candidate3);

    }

 

    return result;

  }

 

  private boolean isMajority(int[] nums, int candidate) {

    int floor = findFloor(nums, candidate);

    int ceiling = findCeiling(nums, candidate);

 

    return ceiling - floor - 1 > nums.length / 4;

  }

 

  private int findFloor(int[] nums, int candidate) {

    if (nums[nums.length - 1] < candidate) {

      return nums.length - 1;

    }

    if (nums[0] >= candidate) {

      return -1;

    }

 

    int l = 0;

    int r = nums.length - 1;

    while (l <= r) {

      int mid = l + (r - l) / 2;

      if (nums[mid] < candidate) {

        if (mid + 1 < nums.length && nums[mid + 1] >= candidate) {

          return mid;

        }

        else {

          l = mid + 1;

        }

      }

      else {

        if (mid - 1 >= 0 && nums[mid - 1] < candidate) {

          return mid - 1;

        }

        else {

          r = mid - 1;

        }

      }

    }

 

    return -1;

  }

 

  private int findCeiling(int[] nums, int candidate) {

    if (nums[nums.length - 1] <= candidate) {

      return nums.length;

    }

    if (nums[0] > candidate) {

      return 0;

    }

 

    int l = 0;

    int r = nums.length - 1;

    while (l <= r) {

      int mid = l + (r - l) / 2;

      if (nums[mid] <= candidate) {

        if (mid + 1 < nums.length && nums[mid + 1] > candidate) {

          return mid + 1;

        }

        else {

          l = mid + 1;

        }

      }

      else {

        if (mid - 1 >= 0 && nums[mid - 1] <= candidate) {

          return mid;

        }

        else {

          r = mid - 1;

        }

      }

    }

    return -1;

  }
Read full article from Lintcode: Majority Number II - neverlandly - 博客园
 * @author het
 *
 */
public class LeetCode229 {
	 public int majorityNumber(ArrayList<Integer> nums) {
	        if (nums == null || nums.isEmpty()) return -1;
	        // pair
	        int key1 = -1, key2 = -1;
	        int count1 = 0, count2 = 0;
	        for (int num : nums) {
	            if (count1 == 0) {
	                key1 = num;
	                count1 = 1;
	                continue;
	            } else if (count2 == 0 && key1 != num) {
	                key2 = num;
	                count2 = 1;
	                continue;
	            }
	            if (key1 == num) {
	                count1++;
	            } else if (key2 == num) {
	                count2++;
	            } else {
	                count1--;
	                count2--;
	            }
	        }

	        count1 = 0;
	        count2 = 0;
	        for (int num : nums) {
	            if (key1 == num) {
	                count1++;
	            } else if (key2 == num) {
	                count2++;
	            }
	        }
	        return count1 > count2 ? key1 : key2;
	    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
