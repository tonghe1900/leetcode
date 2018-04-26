package alite.leetcode.newExtra.L500;
/**
 * LeetCode 537 - Complex Number Multiplication

https://leetcode.com/problems/complex-number-multiplication
Given two strings representing two complex numbers.
You need to return a string representing their multiplication. Note i2 = -1 according to the definition.
Example 1:
Input: "1+1i", "1+1i"
Output: "0+2i"
Explanation: (1 + i) * (1 + i) = 1 + i2 + 2 * i = 2i, and you need convert it to the form of 0+2i.
Example 2:
Input: "1+-1i", "1+-1i"
Output: "0+-2i"
Explanation: (1 - i) * (1 - i) = 1 + i2 - 2 * i = -2i, and you need convert it to the form of 0+-2i.
Note:


The input strings will not have extra blank.
The input strings will be given in the form of a+bi, where the integer a and b will both belong to the range of [-100, 100]. And the output should be also in this form.

https://discuss.leetcode.com/topic/84261/java-3-liner
This solution relies on the fact that (a+bi)(c+di) = (ac - bd) + (ad+bc)i.
public String complexNumberMultiply(String a, String b) {
    int[] coefs1 = Stream.of(a.split("\\+|i")).mapToInt(Integer::parseInt).toArray(), 
          coefs2 = Stream.of(b.split("\\+|i")).mapToInt(Integer::parseInt).toArray();
    return (coefs1[0]*coefs2[0] - coefs1[1]*coefs2[1]) + "+" + (coefs1[0]*coefs2[1] + coefs1[1]*coefs2[0]) + "i";
}
To beat a 3-liner, we have to use 2-liner:
    public String complexNumberMultiply(String a, String b) {
        int[] coef = Stream.of((a+b).split("\\+|i")).mapToInt(Integer::parseInt).toArray();  
        return (coef[0]*coef[2] - coef[1]*coef[3]) + "+" + (coef[1]*coef[2] + coef[0]*coef[3]) + "i";
    }

To beat a 2-liner, we have to use a 1-liner:
String complexNumberMultiply(String a, String b) {
  return ((Function<int[], String>) vs -> (vs[0] * vs[2] - vs[1] * vs[3]) + "+" + (vs[1] * vs[2] + vs[0] * vs[3]) + "i").apply(Arrays.stream((a + b).split("[+i]")).mapToInt(Integer::parseInt).toArray());
}

https://discuss.leetcode.com/topic/84629/java-7ms-easy-to-understand-solution
https://discuss.leetcode.com/topic/84262/java-solution-cross-products
    public String complexNumberMultiply(String a, String b) {
        int[] valA = getValue(a);
        int[] valB = getValue(b);
        
        int real = valA[0] * valB[0] - valA[1] * valB[1];
        int img = valA[0] * valB[1] + valA[1] * valB[0];
        
        return real + "+" + img + "i";
    }
    
    private int[] getValue(String s) {
        String[] str = s.split("\\+");
        int[] val = new int[2];
        val[0] = Integer.valueOf(str[0]);
        int indexOfI = str[1].indexOf("i");
        val[1] = Integer.valueOf(str[1].substring(0, indexOfI));
        
        return val;
    }

https://discuss.leetcode.com/topic/84279/java-a1-b1-a2-b2-a1a2-b1b2-a1b2-b1a2/
(a1+b1)*(a2+b2) = (a1a2 + b1b2 + (a1b2+b1a2))

public String complexNumberMultiply(String a, String b) {
    String result = "";
    String[] A = a.split("\\+");
    String[] B = b.split("\\+");
    int a1 = Integer.parseInt(A[0]);
    int b1 = Integer.parseInt(A[1].replace("i",""));

    int a2 = Integer.parseInt(B[0]);
    int b2 = Integer.parseInt(B[1].replace("i",""));

    int a1a2 = a1 * a2;
    int b1b2 = b1 * b2;
    int a1b2a2b1 = (a1 * b2) + (b1 * a2);

    String afinal = (a1a2 + (-1 * b1b2)) + "";
    String bfinal = a1b2a2b1 + "i";
    result = afinal+"+"+bfinal;
    return result;
}
 * @author het
 *
 */
public class L537 {
	 public String complexNumberMultiply(String a, String b) {
	        int[] valA = getValue(a);
	        int[] valB = getValue(b);
	        
	        int real = valA[0] * valB[0] - valA[1] * valB[1];
	        int img = valA[0] * valB[1] + valA[1] * valB[0];
	        
	        return real + "+" + img + "i";
	    }
	    
	    private int[] getValue(String s) {
	        String[] str = s.split("\\+");
	        int[] val = new int[2];
	        val[0] = Integer.valueOf(str[0]);
	        int indexOfI = str[1].indexOf("i");
	        val[1] = Integer.valueOf(str[1].substring(0, indexOfI));
	        
	        return val;
	    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
