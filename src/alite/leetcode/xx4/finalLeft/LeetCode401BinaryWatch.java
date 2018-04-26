package alite.leetcode.xx4.finalLeft;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode.com/problems/binary-watch/
A binary watch has 4 LEDs on the top which represent the hours (0-11), and the 6 LEDs on the bottom represent the minutes (0-59).
Each LED represents a zero or one, with the least significant bit on the right.

For example, the above binary watch reads "3:25".
Given a non-negative integer n which represents the number of LEDs that are currently on, return all possible times the watch could represent.
Example:
Input: n = 1
Return: ["1:00", "2:00", "4:00", "8:00", "0:01", "0:02", "0:04", "0:08", "0:16", "0:32"]
Note:


The order of output does not matter.
The hour must not contain a leading zero, for example "01:00" is not valid, it should be "1:00".
The minute must be consist of two digits and may contain a leading zero, for example "10:2" is not valid, it should be "10:02".
X. Iterative
https://discuss.leetcode.com/topic/59374/simple-python-java
public List<String> readBinaryWatch(int num) {
    List<String> times = new ArrayList<>();
    for (int h=0; h<12; h++)
        for (int m=0; m<60; m++)
            if (Integer.bitCount(h * 64 + m) == num)
                times.add(String.format("%d:%02d", h, m));
    return times;        
}
https://discuss.leetcode.com/topic/59761/just-for-fun-java-1ms-beats-100
  String[][] hour = {{"0"}, 
       {"1", "2", "4", "8"},
       {"3", "5", "6", "9", "10"},
       {"7", "11"}};
  String[][] minute = {{"00"}, //1
                {"01", "02", "04", "08", "16", "32"}, //6
                {"03", "05", "06", "09", "10", "12", "17", "18", "20", "24", "33", "34", "36", "40", "48"}, //15
                {"07", "11", "13", "14", "19", "21", "22", "25", "26", "28", "35", "37", "38", "41", "42", "44", "49", "50", "52", "56"}, //20
                {"15", "23", "27", "29", "30", "39", "43", "45", "46", "51", "53", "54", "57", "58"}, //14
                {"31", "47", "55", "59"}}; //4
    public List<String> readBinaryWatch(int num) {
  List<String> ret = new ArrayList();
  for (int i = 0; i <= 3 && i <= n; i++) {
   if (n - i <= 5) {
    for (String str1 : hour[i]) {
     for (String str2 : minute[n - i]) {
      ret.add(str1 + ":" + str2);
     }
    }
   }
  }
  return ret;     
    }
X. DFS
http://blog.csdn.net/mebiuw/article/details/52575880
https://discuss.leetcode.com/topic/59309/simple-java-ac-solution-with-explanation
   public List<String> readBinaryWatch(int num) {
        List<String> list = new ArrayList<>();
        //用来表示时间的所有可能的取值
        int timecode[] = new int[10];
        dfs(timecode, 0, 0, list, num);
        return list;
    }

    //  dfs 遍历所有可能性
    private void dfs(int[] timecode, int i, int k, List<String> list, int num) {
        if(k == num) {
            String res = decodeToTime(timecode);
            if(res != null)
                list.add(res);
            return;
        }
        if(i == timecode.length) return;
        timecode[i] = 1;
        dfs(timecode, i+1, k+1, list, num);
        timecode[i] = 0;
        dfs(timecode, i+1, k, list, num);
    }

    //输出时间，即输出可能的时间，要是时间不对则输出null
    private String decodeToTime(int[] timecode) {
        int hours = 0;
        //按照位数转换时间
        for(int i = 0; i < 4; i++) {
            if(timecode[i] == 1) {
                hours = hours + (int)Math.pow(2, i);
            }
        }
        int minutes = 0;
        for(int i = 4; i < 10; i++) {
            if(timecode[i] == 1) {
                minutes = minutes + (int)Math.pow(2, i-4);
            }
        }
        String min = "" + minutes;
        if(minutes < 10)
            min = "0" + min;
        //判断时间的可行性
        if(hours  >= 12  ||  minutes  >=  60)
            return null;
        return hours + ":" + min;
    }

https://discuss.leetcode.com/topic/59494/3ms-java-solution-using-backtracking-and-idea-of-permutation-and-combination
    public List<String> readBinaryWatch(int num) {
        List<String> res = new ArrayList<>();
        int[] nums1 = new int[]{8, 4, 2, 1}, nums2 = new int[]{32, 16, 8, 4, 2, 1};
        for(int i = 0; i <= num; i++) {
            List<Integer> list1 = generateDigit(nums1, i);
            List<Integer> list2 = generateDigit(nums2, num - i);
            for(int num1: list1) {
                if(num1 >= 12) continue;
                for(int num2: list2) {
                    if(num2 >= 60) continue;
                    res.add(num1 + ":" + (num2 < 10 ? "0" + num2 : num2));
                }
            }
        }
        return res;
    }

    private List<Integer> generateDigit(int[] nums, int count) {
        List<Integer> res = new ArrayList<>();
        generateDigitHelper(nums, count, 0, 0, res);
        return res;
    }

    private void generateDigitHelper(int[] nums, int count, int pos, int sum, List<Integer> res) {
        if(count == 0) {
            res.add(sum);
            return;
        }
        
        for(int i = pos; i < nums.length; i++) {
            generateDigitHelper(nums, count - 1, i + 1, sum + nums[i], res);    
        }
    }

public List<String> readBinaryWatch(final int num) {
  final List<String> result = new ArrayList<>();
  for (int i = 0; i <= num; i++) {
    final List<Integer> hours = new ArrayList<>();
    final List<Integer> mins = new ArrayList<>();
    readHourMins(i, 0, hours, 0, 4, 12);
    readHourMins(num - i, 0, mins, 0, 6, 60);

    for (final Integer hour : hours) {
      for (final Integer min : mins) {
        final StringBuilder sb = new StringBuilder();
        if (min < 10) {
          sb.append("0");
        }
        sb.append(min);
        result.add(hour.toString() + ":" + sb.toString());
      }
    }
  }
  return result;
}

public void readHourMins(final int num, final int index, final List<Integer> result, final Integer temp,
    final int maxIndex, final int maxValue) {
  // base condition

  if (temp >= maxValue) {
    return;
  }
  if (index > maxIndex) {
    return;
  }
  if (num == 0) {
    result.add(temp);
    return;
  }

  // set the bit
  readHourMins(num - 1, index + 1, result, new Integer(temp + (int) Math.pow(2, index)), maxIndex, maxValue);

  // don't set it
  readHourMins(num, index + 1, result, temp, maxIndex, maxValue);
}
 * @author het
 *
 */
public class LeetCode401BinaryWatch {
	public List<String> readBinaryWatch(int num) {
	    List<String> times = new ArrayList<>();
	    for (int h=0; h<12; h++)
	        for (int m=0; m<60; m++)
	        	//Integer.bitCount(h*64+m)
	        	//Integer.bitCount(h * 64 + m)
	        	//Integer.bitCount(h * 64 + m)
	        	//Integer.bitCount(h * 64 + m)
	        	//Integer.bitCount(h * 64 + m)
	        	//Integer.bitCount(h * 64 + m)
	            if (Integer.bitCount(h * 64 + m) == num)
	                times.add(String.format("%d:%02d", h, m));
	    return times;        
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
