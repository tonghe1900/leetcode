package alite.leetcode.newExtra.L500.extra.finish;
/**
 * https://leetcode.com/problems/base-7/
Given an integer, return its base 7 string representation.
Example 1:
Input: 100
Output: "202"
Example 2:
Input: -7
Output: "-10"
Note: The input will be in range of [-1e7, 1e7].
https://discuss.leetcode.com/topic/78952/verbose-java-solution
    public String convertTo7(int num) {
        if (num == 0) return "0";
        
        StringBuilder sb = new StringBuilder();
        boolean negative = false;
        
        if (num < 0) {
            negative = true;
        }
        while (num != 0) {
            sb.append(Math.abs(num % 7));
            num = num / 7;
        }
        
        if (negative) {
            sb.append("-");
        }
        
        return sb.reverse().toString();
    }

https://discuss.leetcode.com/topic/78935/java-1-liner-standard-solution
public String convertTo7(int num) {
    return Integer.toString(num, 7);
}
Not using standard library:
    public String convertTo7(int num) {
        if (num == 0) return "0";
        String res = "";
        boolean isNeg = num < 0;
        while (num != 0) {
            res = Math.abs((num % 7)) + res;
            num /= 7;
        }
        return isNeg ? "-" + res : res;
    }
http://blog.csdn.net/zhouziyu2011/article/details/55004517
取余数加入到result的前面，除以7，直到小于7，最后再把num加入result的前面。
注意负数。

    public String convertTo7(int num) {  
        String result = "";  
        int flag = 0;  
        if (num < 0) {  
            flag = 1;  
            num = Math.abs(num);  
        }  
        while(num >= 7) {  
            result = num % 7 + result;  
            num = num / 7;  
        }  
        result = num + result;  
        if (flag == 1)  
            result = "-" + result;  
        return result;  
    }
 * @author het
 *
 */


//Example 1:
//Input: 100
//Output: "202"
//Example 2:
//Input: -7
//Output: "-10"
public class L504_Base_7 {
	public static  String convertTo7(int num) {  
        StringBuilder result = new StringBuilder();  
        boolean isNegative = false;  
        if (num < 0) {  
        	isNegative =  true;
            num = Math.abs(num);  
        }  
        while(num >= 7) {  
            result.append( num % 7);  
            num = num / 7;  
        }  
        result.append(num);
        
        
        if (isNegative)  
            result.append("-");
        return result.reverse().toString();  
    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        System.out.println(convertTo7(100));
        System.out.println(convertTo7(-7));
	}

}
