package alite.leetcode.xx2.sucess;
/**
 * LeetCode 262. Trips and Users

The Trips table holds all taxi trips. Each trip has a unique Id, while Client_Id and Driver_Id are both foreign keys to the Users_Id at the Users table. Status is an ENUM type of (‘completed’, ‘cancelled_by_driver’, ‘cancelled_by_client’).

+----+-----------+-----------+---------+--------------------+----------+
| Id | Client_Id | Driver_Id | City_Id |        Status      |Request_at|
+----+-----------+-----------+---------+--------------------+----------+
| 1  |     1     |    10     |    1    |     completed      |2013-10-01|
| 2  |     2     |    11     |    1    | cancelled_by_driver|2013-10-01|
| 3  |     3     |    12     |    6    |     completed      |2013-10-01|
| 4  |     4     |    13     |    6    | cancelled_by_client|2013-10-01|
| 5  |     1     |    10     |    1    |     completed      |2013-10-02|
| 6  |     2     |    11     |    6    |     completed      |2013-10-02|
| 7  |     3     |    12     |    6    |     completed      |2013-10-02|
| 8  |     2     |    12     |    12   |     completed      |2013-10-03|
| 9  |     3     |    10     |    12   |     completed      |2013-10-03| 
| 10 |     4     |    13     |    12   | cancelled_by_driver|2013-10-03|
+----+-----------+-----------+---------+--------------------+----------+
The Users table holds all users. Each user has an unique Users_Id, and Role is an ENUM type of (‘client’, ‘driver’, ‘partner’).

+----------+--------+--------+
| Users_Id | Banned |  Role  |
+----------+--------+--------+
|    1     |   No   | client |
|    2     |   Yes  | client |
|    3     |   No   | client |
|    4     |   No   | client |
|    10    |   No   | driver |
|    11    |   No   | driver |
|    12    |   No   | driver |
|    13    |   No   | driver |
+----------+--------+--------+
Write a SQL query to find the cancellation rate of requests made by unbanned clients between Oct 1, 2013 and Oct 3, 2013. For the above tables, your SQL query should return the following rows with the cancellation rate being rounded to two decimal places.

+------------+-------------------+
|     Day    | Cancellation Rate |
+------------+-------------------+
| 2013-10-01 |       0.33        |
| 2013-10-02 |       0.00        |
| 2013-10-03 |       0.50        |
+------------+-------------------+
题目大意：
Trips表存储出租车旅程记录。每一个旅程包含一个唯一的Id，Client_Id与Driver_Id都是Users表中Users_Id的外键。Status是枚举类型 
(‘completed’, ‘cancelled_by_driver’, ‘cancelled_by_client’)。

Users表存储用户信息。每一个用户都包含一个唯一的Users_Id以及角色Role，枚举类型(‘client’, ‘driver’, ‘partner’)。

编写SQL查询统计日期范围2013-10-01到2013-10-03之间的旅程取消率。对于上面的两张表，你的SQL查询应当返回下面的结果，取消率应四舍五入保留2位小数。

解题思路：
利用IF函数与SUM函数组合查询
IF(t.Status = 'completed', 0, 1)

ROUND(SUM(IF(t.Status = 'completed', 0, 1)) / COUNT(*), 2) AS `Cancellation Rate` 


SQL语句：
# Write your MySQL query statement below
SELECT t.Request_at AS `Day`, 
ROUND(SUM(IF(t.Status = 'completed', 0, 1)) / COUNT(*), 2) AS `Cancellation Rate` 
FROM Trips t INNER JOIN Users u ON t.Client_Id = u.Users_Id 
WHERE t.Request_at BETWEEN '2013-10-01' AND '2013-10-03' 
AND u.Banned = 'No' AND u.Role = 'client' 
GROUP BY t.Request_at ORDER BY t.Request_at;
1
2
3
4
5
6
7
 

本文链接：http://bookshadow.com/weblog/2016/07/19/leetcode-trips-and-users/
 * @author het
 *
 */
public class L262 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
