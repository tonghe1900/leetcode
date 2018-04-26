package alite.leetcode.xx3;
/**
 * LeetCode 338 - Counting Bits

https://www.hrwhisper.me/leetcode-counting-bits/
Given a non negative integer number num. For every numbers i in the range 0 ≤ i ≤ num calculate the number
 of 1’s in their binary representation and return them as an array.
Example:
For num = 5 you should return [0,1,1,2,1,2].
Follow up:
It is very easy to come up with a solution with run time O(n*sizeof(integer)). But can you do it in linear time O(n) /possibly in a single pass?
Space complexity should be O(n).
Can you do it like a boss? Do it without using any builtin function like __builtin_popcount in c++ or in any other language.

想一想，当一个数为2的整数幂的时候，1的个数为1，比如2（10) 和4(100)，8(1000)
在这之后就是前一个序列的数+1 比如 9(1001) = 1(1) + 8 (1) = 2
就是把一个数分解为小于它的最大2的整数幂 + x

    public int[] countBits(int num) {

        int[] res = new int[num+1];

        int pow2 = 1,before =1;

        for(int i=1;i<=num;i++){

            if (i == pow2){

                before = res[i] = 1;

                pow2 <<= 1;

            }

            else{

                res[i] = res[before] + 1;

                before += 1;

            }

        }

        return res;

    }

https://leetcode.com/discuss/92609/three-line-java-solution
An easy recurrence for this problem is f[i] = f[i / 2] + i % 2.
public int[] countBits(int num) {
    int[] f = new int[num + 1];
    for (int i=1; i<=num; i++) f[i] = f[i >> 1] + (i & 1);
    return f;
}
倒过来想，一个数 * 2 就是把它的二进制全部左移一位，也就是说 1的个数是相等的。
那么我们可以利用这个结论来做。
res[i /2] 然后看看最低位是否为1即可（上面*2一定是偶数，这边比如15和14除以2都是7，但是15时通过7左移一位并且+1得到，14则是直接左移）
所以res[i] = res[i >>1] + (i&1)

    public int[] countBits(int num) {

        int[] res = new int[num+1];

        for(int i=1;i<=num;i++){

            res[i] = res[i >> 1] + (i & 1);

        }

        return res;

https://leetcode.com/discuss/92609/three-line-java-solution
    public int[] countBits(int num) {
        int[] answer = new int[num+1];
        int offset = 1;
        for(int i = 1; i < answer.length; i++){
            if(offset * 2 == i) offset *= 2;
            answer[i] = 1 + answer[i - offset];
        }
        return answer;
    }
https://leetcode.com/discuss/93807/simple-java-dynamic-programming-without-bitwise-operation
    int[] bits = new int[num + 1];    
    for(int i = 1; i <= num; i++){
        bits[i] = bits[i/2];
        if(i%2 == 1) bits[i]++; 
    }
    return bits;
}
Brute Force:
http://blog.csdn.net/lnho2015/article/details/50924299
    public int[] countBits(int num) {
        int[] result=new int[num+1];
        result[0]=0;
        for(int i=1;i<=num;i++){
            result[i]=getCount(i);
        }
        return result;
    }
    public int getCount(int num){
        int count=0;
        while(num!=0){
            if((num&1)==1){
                count++;
            }
            num/=2;
        }
        return count;
    }
https://leetcode.com/discuss/92675/handle-this-question-interview-thinking-process-solution
 * @author het
 *
 */
public class LeetCode338frustrated {
//	想一想，当一个数为2的整数幂的时候，1的个数为1，比如2（10) 和4(100)，8(1000)
//	在这之后就是前一个序列的数+1 比如 9(1001) = 1(1) + 8 (1) = 2
//	就是把一个数分解为小于它的最大2的整数幂 + x

	    public static  int[] countBits(int num) {

	        int[] res = new int[num+1];

	        int pow2 = 1,before =1;

	        for(int i=1;i<=num;i++){

	            if (i == pow2){

	                before = res[i] = 1;

	                pow2 <<= 1;

	            }

	            else{

	                res[i] = res[before] + 1;

	                before += 1;

	            }

	        }

	        return res;

	    }
	    
	    public static  int[] countBits1(int num) {

	        int[] res = new int[num+1];

	        int pow2 = 1;

	        for(int i=1;i<=num;i++){

	            if (i == pow2){

	               res[i] = 1;

	                pow2 <<= 1;

	            }

	            else{

	                res[i] = 1 + res[i - pow2/2];


	            }

	        }

	        return res;

	    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] countBits = countBits(99);
		for(int i=0;i<countBits.length;i+=1){
			System.out.print(countBits[i]+" ");
		}
		System.out.println();
		
		int[] countBits1 = countBits1(99);
		for(int i=0;i<countBits1.length;i+=1){
			System.out.print(countBits1[i]+" ");
		}
		System.out.println();
		

	}

}
