package alite.leetcode.newest;
/**
 * LeetCode 640 - Solve the Equation

https://leetcode.com/problems/solve-the-equation/#/description


Solve a given equation and return the value of x in the form of string "x=#value". The equation contains only '+', '-' operation, the variable x and its coefficient.
If there is no solution for the equation, return "No solution".
If there are infinite solutions for the equation, return "Infinite solutions".
If there is exactly one solution for the equation, we ensure that the value of x is an integer.
Example 1:
Input: "x+5-3+x=6+x-2"
Output: "x=2"
Example 2:
Input: "x=x"
Output: "Infinite solutions"
Example 3:
Input: "2x=x"
Output: "x=0"
Example 4:
Input: "2x+3x-6x=x+2"
Output: "x=-1"
Example 5:
Input: "x=x+2"
Output: "No solution"
X. Use regex
https://discuss.leetcode.com/category/810/solve-the-equation
public String solveEquation(String equation) {
    int[] res = evaluateExpression(equation.split("=")[0]),  
     res2 = evaluateExpression(equation.split("=")[1]);
    res[0] -= res2[0];
    res[1] = res2[1] - res[1];
    if (res[0] == 0 && res[1] == 0) return "Infinite solutions";
    if (res[0] == 0) return "No solution";
    return "x=" + res[1]/res[0];
}  

public int[] evaluateExpression(String exp) {
    String[] tokens = exp.split("(?=[-+])"); 
    int[] res =  new int[2];
    for (String token : tokens) {
        if (token.equals("+x") || token.equals("x")) res[0] += 1;
 else if (token.equals("-x")) res[0] -= 1;
 else if (token.contains("x")) res[0] += Integer.parseInt(token.substring(0, token.indexOf("x")));
 else res[1] += Integer.parseInt(token);
    }
    return res;
}
https://leetcode.com/articles/solve-the-equation/
    public String coeff(String x) {
        if (x.length() > 1 && x.charAt(x.length() - 2) >= '0' && x.charAt(x.length() - 2) <= '9')
            return x.replace("x", "");
        return x.replace("x", "1");
    }
    public String solveEquation(String equation) {
        String[] lr = equation.split("=");
        int lhs = 0, rhs = 0;
        for (String x: lr[0].split("(?=\\+)|(?=-)")) {
            if (x.indexOf("x") >= 0) {

                lhs += Integer.parseInt(coeff(x));
            } else
                rhs -= Integer.parseInt(x);
        }
        for (String x: lr[1].split("(?=\\+)|(?=-)")) {
            if (x.indexOf("x") >= 0)
                lhs -= Integer.parseInt(coeff(x));
            else
                rhs += Integer.parseInt(x);
        }
        if (lhs == 0) {
            if (rhs == 0)
                return "Infinite solutions";
            else
                return "No solution";
        } else
            return "x=" + rhs / lhs;
    }
X.
https://leetcode.com/articles/solve-the-equation/
We treat the given equation as if we're bringing all the x's on the left hand side and all the rest of the numbers on the right hand side as done below for an example.
x+5-3+x=6+x-2
x+x-x=6-2-5+3
Thus, every x in the left hand side of the given equation is treated as positive, while that on the right hand side is treated as negative, in the current implementation. Likewise, every number on the left hand side is treated as negative, while that on the right hand side is treated as positive. Thus, by doing so, we obtain all the x's in the new lhslhs and all the numbers in the new rhsrhs of the original equation.
Further, in case of an x, we also need to find its corresponding coefficients in order to evaluate the final effective coefficient of x on the left hand side. We also evaluate the final effective number on the right hand side as well.
Now, in case of a unique solution, the ratio of the effective rhsrhs and lhslhs gives the required result. In case of infinite solutions, both the effective lhslhs and rhsrhsturns out to be zero e.g. x+1=x+1. In case of no solution, the coefficient of x(lhslhs) turns out to be zero, but the effective number on the rhsrhs is non-zero.
    public String coeff(String x) {
        if (x.length() > 1 && x.charAt(x.length() - 2) >= '0' && x.charAt(x.length() - 2) <= '9')
            return x.replace("x", "");
        return x.replace("x", "1");
    }
    public String solveEquation(String equation) {
        String[] lr = equation.split("=");
        int lhs = 0, rhs = 0;
        for (String x: breakIt(lr[0])) {
            if (x.indexOf("x") >= 0) {
                lhs += Integer.parseInt(coeff(x));
            } else
                rhs -= Integer.parseInt(x);
        }
        for (String x: breakIt(lr[1])) {
            if (x.indexOf("x") >= 0)
                lhs -= Integer.parseInt(coeff(x));
            else
                rhs += Integer.parseInt(x);
        }
        if (lhs == 0) {
            if (rhs == 0)
                return "Infinite solutions";
            else
                return "No solution";
        }
        return "x=" + rhs / lhs;
    }
    public List < String > breakIt(String s) {
        List < String > res = new ArrayList < > ();
        String r = "";
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '+' || s.charAt(i) == '-') {
                if (r.length() > 0)
                    res.add(r);
                r = "" + s.charAt(i);
            } else
                r += s.charAt(i);
        }
        res.add(r);
        return res;
    }
https://discuss.leetcode.com/topic/95207/easiest-java-solution-self-explanatory
 * @author het
 *
 */
public class LeetCode640 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
