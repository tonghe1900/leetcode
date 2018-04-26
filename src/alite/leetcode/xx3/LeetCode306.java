package alite.leetcode.xx3;

///**
// * LeetCode 306 Additive Number
//
//https://leetcode.com/problems/additive-number/
//Additive number is a positive integer whose digits can form additive sequence.
//A valid additive sequence should contain at least three numbers. Except for the first two numbers, each subsequent number 
//in the sequence must be the sum of the preceding two.
//For example:
//"112358" is an additive number because the digits can form an additive sequence: 1, 1, 2, 3, 5, 8.
//1 + 1 = 2, 1 + 2 = 3, 2 + 3 = 5, 3 + 5 = 8
//"199100199" is also an additive number, the additive sequence is: 1, 99, 100, 199.
//1 + 99 = 100, 99 + 100 = 199
//Note: Numbers in the additive sequence cannot have leading zeros, so sequence 1, 2, 03 or 1, 02, 3 is invalid.
//Given a string represents an integer, write a function to determine if it's an additive number.
//Follow up:
//How would you handle overflow for very large input integers?
//https://leetcode.com/discuss/70119/backtracking-with-pruning-java-solution-and-python-solution
//time complexity is O(n^3)
//    public boolean isAdditiveNumber(String num) {
//        if (num == null || num.length() < 3) return false;
//        int n = num.length();
//        for (int i = 1; i < n; i++) {
//            if (i > 1 && num.charAt(0) == '0') break;
//            for (int j = i+1; j < n; j++) {
//                int first = 0, second = i, third = j;
//                if (num.charAt(second) == '0' && third > second+1) break;
//                while (third < n) {
//                    Long result = (Long.parseLong(num.substring(first, second)) + 
//                                   Long.parseLong(num.substring(second, third)) );
//                    if (num.substring(third).startsWith(result.toString())) {
//                        first = second; second = third; third += result.toString().length();
//                    }
//                    else {
//                        break;
//                    }
//                }
//                if (third == n) return true;
//            }
//        }
//        return false;
//    }
//changing the loop to" for i in range(1,n/2+1) and for j in range(i+1,(i+n)/2+1)" slightly improve the code
//
//If no overflow, instead of BigInteger we can consider to use Long which is a lot faster.
//Java Iterative Using Long
//    public boolean isAdditiveNumber(String num) {
//        int n = num.length();
//        for (int i = 1; i <= n / 2; ++i)
//            for (int j = 1; Math.max(j, i) <= n - i - j; ++j)
//                if (isValid(i, j, num)) return true;
//        return false;
//    }
//    private boolean isValid(int i, int j, String num) {
//        if (num.charAt(0) == '0' && i > 1) return false;
//        if (num.charAt(i) == '0' && j > 1) return false;
//        String sum;
//        Long x1 = Long.parseLong(num.substring(0, i));
//        Long x2 = Long.parseLong(num.substring(i, i + j));
//        for (int start = i + j; start != num.length(); start += sum.length()) {
//            x2 = x2 + x1;
//            x1 = x2 - x1;
//            sum = x2.toString();
//            if (!num.startsWith(sum, start)) return false;
//        }
//        return true;
//    }
//    public boolean isAdditiveNumber(String num) {
//        int n = num.length();
//        for (int i = 1; i <= n / 2; ++i)
//            for (int j = 1; Math.max(j, i) <= n - i - j; ++j)
//                if (isValid(i, j, num)) return true;
//        return false;
//    }
//    private boolean isValid(int i, int j, String num) {
//        if (num.charAt(0) == '0' && i > 1) return false;
//        if (num.charAt(i) == '0' && j > 1) return false;
//        String sum;
//        BigInteger x1 = new BigInteger(num.substring(0, i));
//        BigInteger x2 = new BigInteger(num.substring(i, i + j));
//        for (int start = i + j; start != num.length(); start += sum.length()) {
//            x2 = x2.add(x1);
//            x1 = x2.subtract(x1);
//            sum = x2.toString();
//            if (!num.startsWith(sum, start)) return false;
//        }
//        return true;
//    }
//https://discuss.leetcode.com/topic/29856/java-recursive-and-iterative-solutions
//The idea is quite straight forward. Generate the first and second of the sequence, check if the rest of the string match the sum recursively. i and j are length of the first and second number. i should in the range of [0, n/2]. The length of their sum should>= max(i,j)
//    public boolean isAdditiveNumber(String num) {
//        int n = num.length();
//        for (int i = 1; i <= n / 2; ++i) {
//            if (num.charAt(0) == '0' && i > 1) return false;
//            BigInteger x1 = new BigInteger(num.substring(0, i));
//            for (int j = 1; Math.max(j, i) <= n - i - j; ++j) {
//                if (num.charAt(i) == '0' && j > 1) break;
//                BigInteger x2 = new BigInteger(num.substring(i, i + j));
//                if (isValid(x1, x2, j + i, num)) return true;
//            }
//        }
//        return false;
//    }
//    private boolean isValid(BigInteger x1, BigInteger x2, int start, String num) {
//        if (start == num.length()) return true;
//        x2 = x2.add(x1);
//        x1 = x2.subtract(x1);
//        String sum = x2.toString();
//        return num.startsWith(sum, start) && isValid(x1, x2, start + sum.length(), num);
//    }
//}
//
//Since isValid is a tail recursion it is very easy to turn it into a loop.
//    public boolean isAdditiveNumber(String num) {
//        int n = num.length();
//        for (int i = 1; i <= n / 2; ++i)
//            for (int j = 1; Math.max(j, i) <= n - i - j; ++j)
//                if (isValid(i, j, num)) return true;
//        return false;
//    }
//    private boolean isValid(int i, int j, String num) {
//        if (num.charAt(0) == '0' && i > 1) return false;
//        if (num.charAt(i) == '0' && j > 1) return false;
//        String sum;
//        BigInteger x1 = new BigInteger(num.substring(0, i));
//        BigInteger x2 = new BigInteger(num.substring(i, i + j));
//        for (int start = i + j; start != num.length(); start += sum.length()) {
//            x2 = x2.add(x1);
//            x1 = x2.subtract(x1);
//            sum = x2.toString();
//            if (!num.startsWith(sum, start)) return false;
//        }
//        return true;
//    }
//If no overflow, instead of BigInteger we can consider to use Long which is a lot faster.
//    public boolean isAdditiveNumber(String num) {
//        int n = num.length();
//        for (int i = 1; i <= n / 2; ++i)
//            for (int j = 1; Math.max(j, i) <= n - i - j; ++j)
//                if (isValid(i, j, num)) return true;
//        return false;
//    }
//    private boolean isValid(int i, int j, String num) {
//        if (num.charAt(0) == '0' && i > 1) return false;
//        if (num.charAt(i) == '0' && j > 1) return false;
//        String sum;
//        Long x1 = Long.parseLong(num.substring(0, i));
//        Long x2 = Long.parseLong(num.substring(i, i + j));
//        for (int start = i + j; start != num.length(); start += sum.length()) {
//            x2 = x2 + x1;
//            x1 = x2 - x1;
//            sum = x2.toString();
//            if (!num.startsWith(sum, start)) return false;
//        }
//        return true;
//    }
//http://www.cnblogs.com/grandyang/p/4974115.html
//这道题定义了一种加法数，就是至少含有三个数字，除去前两个数外，每个数字都是前面两个数字的和，题目中给了许多例子，也限定了一些不合法的情况，比如两位数以上不能以0开头等等，让我们来判断一个数是否是加法数。开始我还想是否能用动态规划来解，可是发现不会写递推式，只得作罢。其实这题可用Brute Force的思想来解，我们让第一个数字先从一位开始，第二个数字从一位，两位，往高位开始搜索，前两个数字确定了，相加得到第三位数字，三个数组排列起来形成一个字符串，和原字符串长度相比，如果小于原长度，那么取出上一次计算的第二个和第三个数，当做新一次计算的前两个数，用相同的方法得到第三个数，再加入当前字符串，再和原字符串长度相比，以此类推，直到当前字符串长度不小于原字符串长度，比较两者是否相同，相同返回true，不相同则继续循环。如果所有情况都遍历完了还是没有返回true，则说明不是Additive Number，返回false，参见代码如下：
//    bool isAdditiveNumber(string num) {
//        for (int i = 1; i < num.size(); ++i) {
//            for (int j = i + 1; j < num.size(); ++j) {
//                string s1 = num.substr(0, i);
//                string s2 = num.substr(i, j - i);
//                long long d1 = atoll(s1.c_str()), d2 = atoll(s2.c_str());
//                if ((s1.size() > 1 && s1[0] == '0') || (s2.size() > 1 && s2[0] == '0')) continue; // this is not enough
//                long long next = d1 + d2;
//                string nexts = to_string(next);
//                string now = s1 + s2 + nexts;
//                while (now.size() < num.size()) {
//                    d1 = d2;
//                    d2 = next;
//                    next = d1 + d2;
//                    nexts = to_string(next);
//                    now += nexts;
//                }
//                if (now == num) return true;
//            }
//        }
//        return false;
//    }
//https://github.com/colin1990324/LeetCode/blob/master/src/Epic/AddictiveNumber.java
//http://bookshadow.com/weblog/2015/11/18/leetcode-additive-number/
//http://leetcode0.blogspot.com/2015/05/addictive-number.html
//
//http://www.geeksforgeeks.org/string-with-additive-sequence/
//This problem can be solved recursively, note that number of digits in added value can’t be smaller than digits in any of its operand that is why we will loop till (length of string)/2 for first number and (length of string – first number’s length)/ 2 for second number to ignore invalid result.
//Next thing to note is, first and second number can’t start with 0, which is checked in below code by isValid method. When we call recursively, we check that sum of first and second number is exactly equal to rest of string. If yes then direct return the result else check that sum string is prefix of rest of string or not, If yes then call recursively with second number, sum string and rest of string after removing sum string from rest of string and if sum string is not prefix of rest of string then no solution in available.
//
//// Checks whether num is valid or not, by
//
//// checking first character and size
//
//bool isValid(string num)
//
//{
//
//    if (num.size() > 1 && num[0] == '0')
//
//        return false;
//
//    return true;
//
//}
//
// 
//
//// returns int value at pos string, if pos is
//
//// out of bound then returns 0
//
//int val(string a, int pos)
//
//{
//
//    if (pos >= a.length())
//
//        return 0;
//
// 
//
//    //  converting character to integer
//
//    return (a[pos] - '0');
//
//}
//
// 
//
//// add two number in string form and return
//
//// result as a string
//
//string addString(string a, string b)
//
//{
//
//    string sum = "";
//
//    int i = a.length() - 1;
//
//    int j = b.length() - 1;
//
//    int carry = 0;
//
// 
//
//    //  loop untill both string get processed
//
//    while (i >= 0 || j >= 0)
//
//    {
//
//        int t = val(a, i) + val(b, j) + carry;
//
//        sum += (t % 10 + '0');
//
//        carry = t / 10;
//
//        i--;    j--;
//
//    }
//
//    if (carry)
//
//        sum += (carry + '0');
//
//    reverse(sum.begin(), sum.end());
//
//    return sum;
//
//}
//
// 
//
////  Recursive method to check c = a + b
//
//bool checkAddition(list<string>& res, string a,
//
//                             string b, string c)
//
//{
//
//    //  both first and second number should be valid
//
//    if (!isValid(a) || !isValid(b))
//
//        return false;
//
//    string sum = addString(a, b);
//
// 
//
//    //  if sum is same as c then direct return
//
//    if (sum == c)
//
//    {
//
//        res.push_back(sum);
//
//        return true;
//
//    }
//
// 
//
//    /*  if sum size is greater than c, then no
//
//        possible sequence further OR if c is not
//
//        prefix of sum string, then no possible
//
//        sequence further  */
//
//    if (c.size() <= sum.size() ||
//
//        sum != c.substr(0, sum.size()))
//
//        return false;
//
//    else
//
//    {
//
//        res.push_back(sum);
//
//         
//
//        //  next recursive call will have b as first
//
//        //  number, sum as second number and string
//
//        //  c as third number after removing prefix
//
//        //  sum string from c
//
//        return checkAddition(res, b, sum,
//
//                             c.substr(sum.size()));
//
//    }
//
//}
//
// 
//
////  Method returns additive sequence from string as
//
//// a list
//
//list<string> additiveSequence(string num)
//
//{
//
//    list<string> res;
//
//    int l = num.length();
//
// 
//
//    // loop untill l/2 only, because if first
//
//    // number is larger,then no possible sequence
//
//    // later
//
//    for (int i = 1; i <= l/2; i++)
//
//    {
//
//        for (int j = 1; j <= (l - i)/2; j++)
//
//        {
//
//            if (checkAddition(res, num.substr(0, i),
//
//                              num.substr(i, j),
//
//                              num.substr(i + j)))
//
//            {
//
//                // adding first and second number at
//
//                // front of result list
//
//                res.push_front(num.substr(i, j));
//
//                res.push_front(num.substr(0, i));
//
//                return res;
//
//            }
//
//        }
//
//    }
//
// 
//
//    // If code execution reaches here, then string
//
//    // doesn't have any additive sequence
//
//    res.clear();
//
//    return res;
//
//}
//
//XXX number  -------- sorry (I forget the name ):
//find all XXX number within range [low high]
//XXX number is defined as :
//  each adjacent position will differ only 1.   for example 8798 is a XXX number,  890 is NOT.
//
//permutaion II
//Given a string, print all its permutations, (only lower case character can be permuted)
//
//
//Contagious patient
//就是一个party,互相握手。 有一个人开始有传染病。 最终多少人会有传染病？
//输入是一个数值， 和一个2D array.
//
//https://leetcode.com/discuss/70124/0ms-concise-solution-perfectly-handles-the-follow-leading
// * @author het
// *
// */
public class LeetCode306 {
	// time complexity is O(n^3)
	public boolean isAdditiveNumber(String num) {
		if (num == null || num.length() < 3)
			return false;
		int n = num.length();
		for (int i = 1; i < n; i++) {
			if (i > 1 && num.charAt(0) == '0')
				break;
			for (int j = i + 1; j < n; j++) {
				int first = 0, second = i, third = j;
				if (num.charAt(second) == '0' && third > second + 1)
					break;
				while (third < n) {
					Long result = (Long.parseLong(num.substring(first, second))
							+ Long.parseLong(num.substring(second, third)));
					if (num.substring(third).startsWith(result.toString())) {
						first = second;
						second = third;
						third += result.toString().length();
					} else {
						break;
					}
				}
				if (third == n)
					return true;
			}
		}
		return false;
	}

	// changing the loop to" for i in range(1,n/2+1) and for j in
	// range(i+1,(i+n)/2+1)" slightly improve the code
	//
	// If no overflow, instead of BigInteger we can consider to use Long which is a
	// lot faster.
	// Java Iterative Using Long
	// public boolean isAdditiveNumber(String num) {
	// int n = num.length();
	// for (int i = 1; i <= n / 2; ++i)
	// for (int j = 1; Math.max(j, i) <= n - i - j; ++j)
	// if (isValid(i, j, num)) return true;
	// return false;
	// }
	// private boolean isValid(int i, int j, String num) {
	// if (num.charAt(0) == '0' && i > 1) return false;
	// if (num.charAt(i) == '0' && j > 1) return false;
	// String sum;
	// Long x1 = Long.parseLong(num.substring(0, i));
	// Long x2 = Long.parseLong(num.substring(i, i + j));
	// for (int start = i + j; start != num.length(); start += sum.length()) {
	// x2 = x2 + x1;
	// x1 = x2 - x1;
	// sum = x2.toString();
	// if (!num.startsWith(sum, start)) return false;
	// }
	// return true;
	// }
	// public boolean isAdditiveNumber(String num) {
	// int n = num.length();
	// for (int i = 1; i <= n / 2; ++i)
	// for (int j = 1; Math.max(j, i) <= n - i - j; ++j)
	// if (isValid(i, j, num)) return true;
	// return false;
	// }
	// private boolean isValid(int i, int j, String num) {
	// if (num.charAt(0) == '0' && i > 1) return false;
	// if (num.charAt(i) == '0' && j > 1) return false;
	// String sum;
	// BigInteger x1 = new BigInteger(num.substring(0, i));
	// BigInteger x2 = new BigInteger(num.substring(i, i + j));
	// for (int start = i + j; start != num.length(); start += sum.length()) {
	// x2 = x2.add(x1);
	// x1 = x2.subtract(x1);
	// sum = x2.toString();
	// if (!num.startsWith(sum, start)) return false;
	// }
	// return true;
	// }
	// https://discuss.leetcode.com/topic/29856/java-recursive-and-iterative-solutions
	// The idea is quite straight forward. Generate the first and second of the
	// sequence, check if the rest of the string match the sum recursively. i and j
	// are length of the first and second number. i should in the range of [0, n/2].
	// The length of their sum should>= max(i,j)
	// public boolean isAdditiveNumber(String num) {
	// int n = num.length();
	// for (int i = 1; i <= n / 2; ++i) {
	// if (num.charAt(0) == '0' && i > 1) return false;
	// BigInteger x1 = new BigInteger(num.substring(0, i));
	// for (int j = 1; Math.max(j, i) <= n - i - j; ++j) {
	// if (num.charAt(i) == '0' && j > 1) break;
	// BigInteger x2 = new BigInteger(num.substring(i, i + j));
	// if (isValid(x1, x2, j + i, num)) return true;
	// }
	// }
	// return false;
	// }
	// private boolean isValid(BigInteger x1, BigInteger x2, int start, String num)
	// {
	// if (start == num.length()) return true;
	// x2 = x2.add(x1);
	// x1 = x2.subtract(x1);
	// String sum = x2.toString();
	// return num.startsWith(sum, start) && isValid(x1, x2, start + sum.length(),
	// num);
	// }
	// }
	//
	// Since isValid is a tail recursion it is very easy to turn it into a loop.
	// public boolean isAdditiveNumber(String num) {
	// int n = num.length();
	// for (int i = 1; i <= n / 2; ++i)
	// for (int j = 1; Math.max(j, i) <= n - i - j; ++j)
	// if (isValid(i, j, num)) return true;
	// return false;
	// }
	// private boolean isValid(int i, int j, String num) {
	// if (num.charAt(0) == '0' && i > 1) return false;
	// if (num.charAt(i) == '0' && j > 1) return false;
	// String sum;
	// BigInteger x1 = new BigInteger(num.substring(0, i));
	// BigInteger x2 = new BigInteger(num.substring(i, i + j));
	// for (int start = i + j; start != num.length(); start += sum.length()) {
	// x2 = x2.add(x1);
	// x1 = x2.subtract(x1);
	// sum = x2.toString();
	// if (!num.startsWith(sum, start)) return false;
	// }
	// return true;
	// }
	// If no overflow, instead of BigInteger we can consider to use Long which is a
	// lot faster.
	// public boolean isAdditiveNumber(String num) {
	// int n = num.length();
	// for (int i = 1; i <= n / 2; ++i)
	// for (int j = 1; Math.max(j, i) <= n - i - j; ++j)
	// if (isValid(i, j, num)) return true;
	// return false;
	// }
	// private boolean isValid(int i, int j, String num) {
	// if (num.charAt(0) == '0' && i > 1) return false;
	// if (num.charAt(i) == '0' && j > 1) return false;
	// String sum;
	// Long x1 = Long.parseLong(num.substring(0, i));
	// Long x2 = Long.parseLong(num.substring(i, i + j));
	// for (int start = i + j; start != num.length(); start += sum.length()) {
	// x2 = x2 + x1;
	// x1 = x2 - x1;
	// sum = x2.toString();
	// if (!num.startsWith(sum, start)) return false;
	// }
	// return true;
	// }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
