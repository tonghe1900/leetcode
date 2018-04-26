package Leetcode600x;
/**
 * 619. Biggest Single Number--MAX()
原创 2017年07月26日 08:59:55 295
Question
Table number contains many numbers in column num including duplicated ones.
Can you write a SQL query to find the biggest number, which only appears once.

+---+
|num|
+---+
| 8 |
| 8 |
| 3 |
| 3 |
| 1 |
| 4 |
| 5 |
| 6 | 
For the sample data above, your query should return the following result:
+---+
|num|
+---+
| 6 |
Note:
If there is no such number, just output null.
Quick Navigation
Solution
Approach: Using subquery and MAX() function [Accepted]
Solution
Approach: Using subquery and MAX() function [Accepted]
Algorithm

Use subquery to select all the numbers appearing just one time.

SELECT
    num
FROM
    `number`
GROUP BY num
HAVING COUNT(num) = 1;
Then choose the biggest one using MAX().

SELECT
    MAX(num) AS num
FROM
    (SELECT
        num
    FROM
        number
    GROUP BY num
    HAVING COUNT(num) = 1) AS t
;
 * @author tonghe
 *
 */
public class Leetcode619 {

}
