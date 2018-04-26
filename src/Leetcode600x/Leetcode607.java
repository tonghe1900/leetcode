package Leetcode600x;
//**
/*
 * 607. Sales Person 
 
 
 
 
 
 
 
 
Average Rating: 4.33 (3 votes)

June 22, 2017  |  3.5K views
Description

Given three tables: salesperson, company, orders.
Output all the names in the table salesperson, who didn’t have sales to company 'RED'.

Example
Input

Table: salesperson

+----------+------+--------+-----------------+-----------+
| sales_id | name | salary | commission_rate | hire_date |
+----------+------+--------+-----------------+-----------+
|   1      | John | 100000 |     6           | 4/1/2006  |
|   2      | Amy  | 120000 |     5           | 5/1/2010  |
|   3      | Mark | 65000  |     12          | 12/25/2008|
|   4      | Pam  | 25000  |     25          | 1/1/2005  |
|   5      | Alex | 50000  |     10          | 2/3/2007  |
+----------+------+--------+-----------------+-----------+
The table salesperson holds the salesperson information. Every salesperson has a sales_id and a name.
Table: company

+---------+--------+------------+
| com_id  |  name  |    city    |
+---------+--------+------------+
|   1     |  RED   |   Boston   |
|   2     | ORANGE |   New York |
|   3     | YELLOW |   Boston   |
|   4     | GREEN  |   Austin   |
+---------+--------+------------+
The table company holds the company information. Every company has a com_id and a name.
Table: orders

+----------+----------+---------+----------+--------+
| order_id |  date    | com_id  | sales_id | amount |
+----------+----------+---------+----------+--------+
| 1        | 1/1/2014 |    3    |    4     | 100000 |
| 2        | 2/1/2014 |    4    |    5     | 5000   |
| 3        | 3/1/2014 |    1    |    1     | 50000  |
| 4        | 4/1/2014 |    1    |    4     | 25000  |
+----------+----------+---------+----------+--------+
The table orders holds the sales record information, salesperson and customer company are represented by sales_id and com_id.
output

+------+
| name | 
+------+
| Amy  | 
| Mark | 
| Alex |
+------+
Explanation

According to order '3' and '4' in table orders, it is easy to tell only salesperson 'John' and 'Alex' have sales to company 'RED',
so we need to output all the other names in table salesperson.

Solution
Approach: Using OUTER JOIN and NOT IN [Accepted]
Intuition

If we know all the persons who have sales in this company 'RED', it will be fairly easy to know who do not have.

Algorithm

To start, we can query the information of sales in company 'RED' as a temporary table. And then try to build a connection between this table and the salesperson table since it has the name information.

SELECT
    *
FROM
    orders o
        LEFT JOIN
    company c ON o.com_id = c.com_id
WHERE
    c.name = 'RED'
;
Note: "LEFT OUTER JOIN" could be written as "LEFT JOIN".

| order_id | date     | com_id | sales_id | amount | com_id | name | city   |
|----------|----------|--------|----------|--------|--------|------|--------|
| 3        | 3/1/2014 | 1      | 1        | 50000  | 1      | RED  | Boston |
| 4        | 4/1/2014 | 1      | 4        | 25000  | 1      | RED  | Boston |
Obviously, the column sales_id exists in table salesperson so we may use it as a subquery, and then utilize the NOT IN to get the target data.

MySQL

SELECT
    s.name
FROM
    salesperson s
WHERE
    s.sales_id NOT IN (SELECT
            o.sales_id
        FROM
            orders o
                LEFT JOIN
            company c ON o.com_id = c.com_id
        WHERE
            c.name = 'RED')
;
 */
public class Leetcode607 {

}
