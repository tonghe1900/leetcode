package alite.leetcode.xx4.left;
/**
 * LeetCode 458 - Poor Pigs

https://leetcode.com/problems/poor-pigs/
There are 1000 buckets, one and only one of them contains poison, the rest are filled with water. T
hey all look the same. If a pig drinks that poison it will die within 15 minutes. 
What is the minimum amount of pigs you need to figure out which bucket contains the poison within one hour.
Answer this question, and write an algorithm for the follow-up general case.
Follow-up:


If there are n buckets and a pig drinking poison will die within m minutes, how many pigs (x) you need
 to figure out the "poison" bucket within p minutes? There is exact one bucket with poison.
https://discuss.leetcode.com/topic/66856/major-flaw-in-current-algorithm-fixed
The basic case (and presumably a significant amount more) are incorrect as more efficient
 solutions can be generated if we do not use base 2.
Assume for now we have: [buckets, 15, 60]
Per pig, we have 5 states that we can detect: death on 15, 30, 45, 60, or life.
Assume for the moment we have 5 buckets and one pig with the above timings.
Represent each bucket as an integer in base 5 (0-indexed).
Pig drinks from bucket 0 at 0 minutes, bucket 1 at time 15 etc...
If the pig is still alive, by elimination, we know that bucket 4 would be poisonous.
This should be trivial to see.
We now increase the number of buckets to 25, we have ceil(log_5(25)) = 2 pigs
Bucket 25 is labelled 44, bucket 1 is labelled 00.
At time t, have the first pig drink from the buckets where the first digit is t,
 have the second pig drink when the second digit is t. 
 As each pig represents a single digit in base 5, 
 and 25 consists of two base 5 digits, two pigs are necessary.
  We can determine the value of each digit based on the time of death of each pig.
   An overlap of the death of two pigs gives us the information of two digits, so nothing is lost.
Example: 18 is poisonous (33 in base 5).
On t=3, one pig is drinking [15,16,17,18,19] and the other is drinking [3,8,13,18,23].
 We can see that if both die, the intersection of the sets yields 18.
Given the default input: 1000, 15, 60
The above yields the minimum number of pigs = ceil(log_5(1000)) = 5 pigs.
The OJ currently returns 8 pigs, which is significantly higher.
    public int poorPigs(int buckets, int minutesToDie, int minutesToTest) {
        if (buckets--==1){
            return 0;
        }
        int base=minutesToTest/minutesToDie+1;
        int count=0;
        while (buckets>0){
            buckets/=base;
            count++;
        }
        return count;
    }
https://discuss.leetcode.com/topic/67176/math-problem-java-ac-code-with-brief-explanations-11-09-2016
The table below shows how it works, n denotes the pig, and s is associated with each status of the pig.
for each pig, there is t+1 status, which is death in 1st, 2nd, 3rd, 4th time slot and live eventually.
Therefore the question is asking for how many pigs can denote 1000 in 5-nary number system.
=======Table=======
n1 n2 n3 n4 n5
s1 1 0 0 1 0
s2 0 0 0 0 1
s3 0 1 0 0 0
s4 0 0 0 0 0
s5 0 0 1 0 0


In this case, the number is 13012 in 5-nary number system.
public int poorPigs(int buckets, int minutesToDie, int minutesToTest) {
        int status = minutesToTest/minutesToDie + 1;
        int num_of_pig = 0;
        while(Math.pow(status, num_of_pig) < buckets) {num_of_pig++;}
        return num_of_pig;
    }

http://bookshadow.com/weblog/2016/11/08/leetcode-poor-pigs/
数学题（Mathematics）
令r = p / m，表示在规定时间内可以做多少轮“试验”。
假设有3头猪，计算可以确定的最大桶数。
首先考虑只能做1轮试验的情形：
0 1 2 3 4 5 6 7 （桶序号）
0 0 0 0 1 1 1 1 （第1头猪饮用：4，5，6，7）
0 0 1 1 0 0 1 1 （第2头猪饮用：2，3，6，7）
0 1 0 1 0 1 0 1 （第3头猪饮用：1，3，5，7）
可能的试验结果如下（0表示幸存， 1表示死亡）：
0 1 2 （猪序号）
0 0 0 （毒药为0号桶，0、1、2号猪均幸存）
0 0 1 （毒药为1号桶，2号猪死亡）
0 1 0 （毒药为2号桶，1号猪死亡）
0 1 1 （毒药为3号桶，1、2号猪死亡）
1 0 0 （毒药为4号桶，0号猪死亡）
1 0 1 （毒药为5号桶，0、2号猪死亡）
1 1 0 （毒药为6号桶，0、1号猪死亡）
1 1 1 （毒药为7号桶，0、1、2号猪均死亡）
因此3头猪做1轮试验可以确定的最大桶数为pow(2, 3) = 8
接下来考虑做2轮试验的情形：
与只做一轮试验类似，依然采用二进制分组的形式。
不同之处在于，每一个分组内可以包含若干个桶，第1轮试验确定分组，第2轮试验确定具体的桶。
0 1 2 3 4 5 6 7 （组序号）
0 0 0 0 1 1 1 1 （第1头猪饮用：4，5，6，7）
0 0 1 1 0 0 1 1 （第2头猪饮用：2，3，6，7）
0 1 0 1 0 1 0 1 （第3头猪饮用：1，3，5，7）
8 4 4 2 4 2 2 1 （桶数量）
各分组包含的桶数量根据做完第1轮试验后猪的幸存情况确定。
幸存猪数     桶数量     可能的情况数
   3           8             1
   2           4             3
   1           2             3
   0           1             1
合计： 1 * 8 + 3 * 4 + 3 * 2 + 1 * 1 = 27
因此3头猪做2轮试验可以确定的最大桶数为pow(3, 3) = 27
实际上1,3,3,1是二项式系数，8,4,2,1是2的幂
由此可以归纳出公式：
buckets = ∑( C(n, i) * pow(r, i) ) 

其中 i∈ [0, n]，C是二项式系数，r为做试验的轮数，n表示猪的数量
化简上式：
buckets = pow(r + 1, n)
最终得：
n = log(buckets, r + 1)
    def poorPigs(self, buckets, minutesToDie, minutesToTest):
        """
        :type buckets: int
        :type minutesToDie: int
        :type minutesToTest: int
        :rtype: int
        """
        return int(math.ceil(math.log(buckets, 1 + minutesToTest / minutesToDie)))
 * @author het
 *
 */
public class LeetCode458 {
//	public int poorPigs(int buckets, int minutesToDie, int minutesToTest) {
//        if (buckets--==1){
//            return 0;
//        }
//        int base=minutesToTest/minutesToDie+1;
//        int count=0;
//        while (buckets>0){
//            buckets/=base;
//            count++;
//        }
//        return count;
//    }
	
	public int poorPigs(int buckets, int minutesToDie, int minutesToTest) {
        int status = minutesToTest/minutesToDie + 1;
        int num_of_pig = 0;
        while(Math.pow(status, num_of_pig) < buckets) {num_of_pig++;}
        return num_of_pig;
    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
