package Leetcode600x;
/**
 * TODO 614. Second Degree Follower[SQL]
https://leetcode.com/problems/second-degree-follower/description/

In facebook, there is a follow table with two columns: followee, follower.

Please write a sql query to get the amount of each follower’s follower if he/she has one.

For example:

+-------------+------------+
| followee    | follower   |
+-------------+------------+
|     A       |     B      |
|     B       |     C      |
|     B       |     D      |
|     D       |     E      |
+-------------+------------+
should output:

+-------------+------------+
| follower    | num        |
+-------------+------------+
|     B       |  2         |
|     D       |  1         |
+-------------+------------+
Explaination:

Both B and D exist in the follower list, when as a followee, B’s follower is C and D, and D’s follower is E. A does not exist in follower list.

Note:

Followee would not follow himself/herself in all cases.

Please display the result in follower’s alphabet order.

解法一 感觉对了 。但是没有过。。。TODO 原因

select followee as follower, count(*) as num
from (select distinct * from follow) t
where followee in 
(select distinct follower from follow)
group by followee
order by followee

解法二巧妙的地方在join 上

Select f1.follower, count(distinct f2.follower) as num
from follow f1
inner join follow f2 on f1.follower = f2.followee
Group by f1.follower
 * @author tonghe
 *
 */
public class Leetcode614 {

}
