package alite.leetcode.newExtra.L500;
/**
 * 
 * @author het
 *
 */
public class L571 {
/**
 * LeetCode 571. Find Median Given Frequency of Numbers

The Numbers table keeps the value of number and its frequency.

+----------+-------------+
|  Number  |  Frequency  |
+----------+-------------|
|  0       |  7          |
|  1       |  1          |
|  2       |  3          |
|  3       |  1          |
+----------+-------------+
In this table, the numbers are 0, 0, 0, 0, 0, 0, 0, 1, 2, 2, 2, 3, so the median is (0 + 0) / 2 = 0.

Write a query to find the median of all numbers and name the result as median.

题目大意：
给定数据表Numbers，包含两列（数字Number及其频率Frequency）

编写查询计算Number的中位数median。

解题思路：
使用MySQL的User-Defined Variables（用户定义变量）。

构造中间表t，包含列Number, Frequency, AccFreq（累积频率）, SumFreq（频率求和）

例如样例数据得到的中间表结果为

+----------+-------------+-----------+----------+
|  Number  |  Frequency  |  AccFreq  | SumFreq  |
+----------+-------------|-----------|----------|
|  0       |  7          |  7        |  12      |
|  1       |  1          |  8        |  12      |
|  2       |  3          |  11       |  12      |
|  3       |  1          |  12       |  12      |
+----------+-------------+-----------+----------+
AccFreq范围在[SumFreq / 2, SumFreq / 2 + Frequency]的Number均值即为答案。

AccFreq范围的推导过程如下：

AccFreq BETWEEN SumFreq / 2 AND SumFreq / 2 + 1 OR AccFreq - Frequency <= SumFreq / 2 AND AccFreq > SumFreq / 2 + 1
上式含义为：

AccFreq本身介于[SumFreq / 2, SumFreq / 2 + 1]之间

或者上一行的AccFreq <= SumFreq / 2 并且 当前行的AccFreq > SumFreq / 2 + 1
两种情况合并即可得到AccFreq的范围：

AccFreq BETWEEN SumFreq / 2 AND SumFreq / 2 + Frequency
SQL语句：
# Write your MySQL query statement below
SELECT AVG(Number) AS median FROM (
  SELECT Number, Frequency, AccFreq, SumFreq FROM
  (SELECT    Number,
             Frequency, @curFreq := @curFreq + Frequency AS AccFreq
   FROM      Numbers n, (SELECT @curFreq := 0) r
   ORDER BY  Number) t1,
  (SELECT SUM(Frequency) SumFreq FROM Numbers) t2
) t
WHERE AccFreq BETWEEN SumFreq / 2 AND SumFreq / 2 + Frequency
 * @param args
 */
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
