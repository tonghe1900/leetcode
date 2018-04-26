package alite.leetcode.conquerLeft;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode.com/problems/fizz-buzz/
Write a program that outputs the string representation of numbers from 1 to n.
But for multiples of three it should output “Fizz” instead of the number and 
for the multiples of five output “Buzz”. For numbers which are multiples of both three and five output “FizzBuzz”.
Example:
n = 15,

Return:
[
    "1",
    "2",
    "Fizz",
    "4",
    "Buzz",
    "Fizz",
    "7",
    "8",
    "Fizz",
    "Buzz",
    "11",
    "Fizz",
    "13",
    "14",
    "FizzBuzz"
]
https://discuss.leetcode.com/topic/63995/java-4ms-solution-not-using-operation
    public List<String> fizzBuzz(int n) {
        List<String> ret = new ArrayList<String>(n);
        for(int i=1,fizz=0,buzz=0;i<=n ;i++){
            fizz++;
            buzz++;
            if(fizz==3 && buzz==5){
                ret.add("FizzBuzz");
                fizz=0;
                buzz=0;
            }else if(fizz==3){
                ret.add("Fizz");
                fizz=0;
            }else if(buzz==5){
                ret.add("Buzz");
                buzz=0;
            }else{
                ret.add(String.valueOf(i));
            }
        } 
        return ret;
    }
https://discuss.leetcode.com/topic/62165/java-solution-with-3-if-conditions
public List<String> fizzBuzz(int n) {
        
        List<String> ls = new ArrayList<String>();
        StringBuffer sb = new StringBuffer();
        for(int i=1;i<=n;i++){
            sb.setLength(0);
            if(i%3==0){
                sb.append("Fizz");
            }
            if(i%5==0){
                sb.append("Buzz");
            }
            if(sb.length()==0){
                sb.append(String.valueOf(i));
            }
            ls.add(sb.toString());
        }
        return ls;
    }
http://www.jiuzhang.com/solutions/fizz-buzz/
https://discuss.leetcode.com/topic/62081/java-easy-iterative-solution
    public ArrayList<String> fizzBuzz(int n) {
        ArrayList<String> results = new ArrayList<String>();
        for (int i = 1; i <= n; i++) {
            if (i % 15 == 0) {
                results.add("fizz buzz");
            } else if (i % 5 == 0) {
                results.add("buzz");
            } else if (i % 3 == 0) {
                results.add("fizz");
            } else {
                results.add(String.valueOf(i));
            }
        }
        return results;
    }
http://www.cnblogs.com/EdwardLiu/p/4278300.html
http://bookshadow.com/weblog/2016/10/09/leetcode-fizz-buzz/
    def fizzBuzz(self, n):
        """
        :type n: int
        :rtype: List[str]
        """
        ans = []
        for x in range(1, n + 1):
            n = str(x)
            if x % 15 == 0:
                n = "FizzBuzz"
            elif x % 3 == 0:
                n = "Fizz"
            elif x % 5 == 0:
                n = "Buzz"
            ans.append(n)
        return ans

https://discuss.leetcode.com/topic/66782/solution-by-java-8-stream
    public List<String> fizzBuzz(int n) {
        final IntFunction<String> func = i -> i % 15 == 0 ? "FizzBuzz" : (i % 3 == 0 ? "Fizz" : (i % 5 == 0 ? "Buzz" : String.valueOf(i)));
        return IntStream.rangeClosed(1, n).mapToObj(func).collect(Collectors.toList());
    }
 * @author het
 *
 */
public class LeetCode412FizzBuzz {
	 public static List<String> fizzBuzz(int n) {
	        List<String> ret = new ArrayList<String>(n);
	        for(int i=1,fizz=0,buzz=0;i<=n ;i++){
	            fizz++;
	            buzz++;
	            if(fizz==3 && buzz==5){
	                ret.add("FizzBuzz");
	                fizz=0;
	                buzz=0;
	            }else if(fizz==3){
	                ret.add("Fizz");
	                fizz=0;
	            }else if(buzz==5){
	                ret.add("Buzz");
	                buzz=0;
	            }else{
	                ret.add(String.valueOf(i));
	            }
	        } 
	        return ret;
	    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(fizzBuzz(15));

	}

}
