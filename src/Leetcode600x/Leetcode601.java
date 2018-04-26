package Leetcode600x;
/**
 * 601. Human Traffic of Stadium
 * @author tonghe
 *
 *
 *
 *X city built a new stadium, each day many people visit it and the stats are saved as these columns: id, date, people

Please write a query to display the records which have 3 or more consecutive rows and the amount of people more than 100(inclusive).

For example, the table stadium:
+------+------------+-----------+
| id   | date       | people    |
+------+------------+-----------+
| 1    | 2017-01-01 | 10        |
| 2    | 2017-01-02 | 109       |
| 3    | 2017-01-03 | 150       |
| 4    | 2017-01-04 | 99        |
| 5    | 2017-01-05 | 145       |
| 6    | 2017-01-06 | 1455      |
| 7    | 2017-01-07 | 199       |
| 8    | 2017-01-08 | 188       |
+------+------------+-----------+
For the sample data above, the output is:

+------+------------+-----------+
| id   | date       | people    |
+------+------------+-----------+
| 5    | 2017-01-05 | 145       |
| 6    | 2017-01-06 | 1455      |
| 7    | 2017-01-07 | 199       |
| 8    | 2017-01-08 | 188       |
+------+------------+-----------+
Note:
Each day only have one row record, and the dates are increasing with id increasing.





Solution
Approach: Using JOIN and WHERE clause [Accepted]
Intuition

Select the days with more than 100 people attending the stadium, and join this temp table with itself. We might get the solution after using complex conditions in a WHERE clause.

Algorithm

The first step is to get the days with 100 people and join this table with itself.

select distinct t1.*
from stadium t1, stadium t2, stadium t3
where t1.people >= 100 and t2.people >= 100 and t3.people >= 100
;
id	date	people	id	date	people	id	date	people
2	2017-01-02	109	2	2017-01-02	109	2	2017-01-02	109
3	2017-01-03	150	2	2017-01-02	109	2	2017-01-02	109
5	2017-01-05	145	2	2017-01-02	109	2	2017-01-02	109
6	2017-01-06	1455	2	2017-01-02	109	2	2017-01-02	109
7	2017-01-07	199	2	2017-01-02	109	2	2017-01-02	109
8	2017-01-08	188	2	2017-01-02	109	2	2017-01-02	109
2	2017-01-02	109	3	2017-01-03	150	2	2017-01-02	109
3	2017-01-03	150	3	2017-01-03	150	2	2017-01-02	109
5	2017-01-05	145	3	2017-01-03	150	2	2017-01-02	109
6	2017-01-06	1455	3	2017-01-03	150	2	2017-01-02	109
7	2017-01-07	199	3	2017-01-03	150	2	2017-01-02	109
8	2017-01-08	188	3	2017-01-03	150	2	2017-01-02	109
2	2017-01-02	109	5	2017-01-05	145	2	2017-01-02	109
3	2017-01-03	150	5	2017-01-05	145	2	2017-01-02	109
5	2017-01-05	145	5	2017-01-05	145	2	2017-01-02	109
6	2017-01-06	1455	5	2017-01-05	145	2	2017-01-02	109
7	2017-01-07	199	5	2017-01-05	145	2	2017-01-02	109
8	2017-01-08	188	5	2017-01-05	145	2	2017-01-02	109
2	2017-01-02	109	6	2017-01-06	1455	2	2017-01-02	109
3	2017-01-03	150	6	2017-01-06	1455	2	2017-01-02	109
5	2017-01-05	145	6	2017-01-06	1455	2	2017-01-02	109
6	2017-01-06	1455	6	2017-01-06	1455	2	2017-01-02	109
7	2017-01-07	199	6	2017-01-06	1455	2	2017-01-02	109
8	2017-01-08	188	6	2017-01-06	1455	2	2017-01-02	109
2	2017-01-02	109	7	2017-01-07	199	2	2017-01-02	109
3	2017-01-03	150	7	2017-01-07	199	2	2017-01-02	109
5	2017-01-05	145	7	2017-01-07	199	2	2017-01-02	109
6	2017-01-06	1455	7	2017-01-07	199	2	2017-01-02	109
7	2017-01-07	199	7	2017-01-07	199	2	2017-01-02	109
8	2017-01-08	188	7	2017-01-07	199	2	2017-01-02	109
2	2017-01-02	109	8	2017-01-08	188	2	2017-01-02	109
3	2017-01-03	150	8	2017-01-08	188	2	2017-01-02	109
5	2017-01-05	145	8	2017-01-08	188	2	2017-01-02	109
6	2017-01-06	1455	8	2017-01-08	188	2	2017-01-02	109
7	2017-01-07	199	8	2017-01-08	188	2	2017-01-02	109
8	2017-01-08	188	8	2017-01-08	188	2	2017-01-02	109
2	2017-01-02	109	2	2017-01-02	109	3	2017-01-03	150
3	2017-01-03	150	2	2017-01-02	109	3	2017-01-03	150
5	2017-01-05	145	2	2017-01-02	109	3	2017-01-03	150
6	2017-01-06	1455	2	2017-01-02	109	3	2017-01-03	150
7	2017-01-07	199	2	2017-01-02	109	3	2017-01-03	150
8	2017-01-08	188	2	2017-01-02	109	3	2017-01-03	150
2	2017-01-02	109	3	2017-01-03	150	3	2017-01-03	150
3	2017-01-03	150	3	2017-01-03	150	3	2017-01-03	150
5	2017-01-05	145	3	2017-01-03	150	3	2017-01-03	150
6	2017-01-06	1455	3	2017-01-03	150	3	2017-01-03	150
7	2017-01-07	199	3	2017-01-03	150	3	2017-01-03	150
8	2017-01-08	188	3	2017-01-03	150	3	2017-01-03	150
2	2017-01-02	109	5	2017-01-05	145	3	2017-01-03	150
3	2017-01-03	150	5	2017-01-05	145	3	2017-01-03	150
5	2017-01-05	145	5	2017-01-05	145	3	2017-01-03	150
6	2017-01-06	1455	5	2017-01-05	145	3	2017-01-03	150
7	2017-01-07	199	5	2017-01-05	145	3	2017-01-03	150
8	2017-01-08	188	5	2017-01-05	145	3	2017-01-03	150
2	2017-01-02	109	6	2017-01-06	1455	3	2017-01-03	150
3	2017-01-03	150	6	2017-01-06	1455	3	2017-01-03	150
5	2017-01-05	145	6	2017-01-06	1455	3	2017-01-03	150
6	2017-01-06	1455	6	2017-01-06	1455	3	2017-01-03	150
7	2017-01-07	199	6	2017-01-06	1455	3	2017-01-03	150
8	2017-01-08	188	6	2017-01-06	1455	3	2017-01-03	150
2	2017-01-02	109	7	2017-01-07	199	3	2017-01-03	150
3	2017-01-03	150	7	2017-01-07	199	3	2017-01-03	150
5	2017-01-05	145	7	2017-01-07	199	3	2017-01-03	150
6	2017-01-06	1455	7	2017-01-07	199	3	2017-01-03	150
7	2017-01-07	199	7	2017-01-07	199	3	2017-01-03	150
8	2017-01-08	188	7	2017-01-07	199	3	2017-01-03	150
2	2017-01-02	109	8	2017-01-08	188	3	2017-01-03	150
3	2017-01-03	150	8	2017-01-08	188	3	2017-01-03	150
5	2017-01-05	145	8	2017-01-08	188	3	2017-01-03	150
6	2017-01-06	1455	8	2017-01-08	188	3	2017-01-03	150
7	2017-01-07	199	8	2017-01-08	188	3	2017-01-03	150
8	2017-01-08	188	8	2017-01-08	188	3	2017-01-03	150
2	2017-01-02	109	2	2017-01-02	109	5	2017-01-05	145
3	2017-01-03	150	2	2017-01-02	109	5	2017-01-05	145
5	2017-01-05	145	2	2017-01-02	109	5	2017-01-05	145
6	2017-01-06	1455	2	2017-01-02	109	5	2017-01-05	145
7	2017-01-07	199	2	2017-01-02	109	5	2017-01-05	145
8	2017-01-08	188	2	2017-01-02	109	5	2017-01-05	145
2	2017-01-02	109	3	2017-01-03	150	5	2017-01-05	145
3	2017-01-03	150	3	2017-01-03	150	5	2017-01-05	145
5	2017-01-05	145	3	2017-01-03	150	5	2017-01-05	145
6	2017-01-06	1455	3	2017-01-03	150	5	2017-01-05	145
7	2017-01-07	199	3	2017-01-03	150	5	2017-01-05	145
8	2017-01-08	188	3	2017-01-03	150	5	2017-01-05	145
2	2017-01-02	109	5	2017-01-05	145	5	2017-01-05	145
3	2017-01-03	150	5	2017-01-05	145	5	2017-01-05	145
5	2017-01-05	145	5	2017-01-05	145	5	2017-01-05	145
6	2017-01-06	1455	5	2017-01-05	145	5	2017-01-05	145
7	2017-01-07	199	5	2017-01-05	145	5	2017-01-05	145
8	2017-01-08	188	5	2017-01-05	145	5	2017-01-05	145
2	2017-01-02	109	6	2017-01-06	1455	5	2017-01-05	145
3	2017-01-03	150	6	2017-01-06	1455	5	2017-01-05	145
5	2017-01-05	145	6	2017-01-06	1455	5	2017-01-05	145
6	2017-01-06	1455	6	2017-01-06	1455	5	2017-01-05	145
7	2017-01-07	199	6	2017-01-06	1455	5	2017-01-05	145
8	2017-01-08	188	6	2017-01-06	1455	5	2017-01-05	145
2	2017-01-02	109	7	2017-01-07	199	5	2017-01-05	145
3	2017-01-03	150	7	2017-01-07	199	5	2017-01-05	145
5	2017-01-05	145	7	2017-01-07	199	5	2017-01-05	145
6	2017-01-06	1455	7	2017-01-07	199	5	2017-01-05	145
7	2017-01-07	199	7	2017-01-07	199	5	2017-01-05	145
8	2017-01-08	188	7	2017-01-07	199	5	2017-01-05	145
2	2017-01-02	109	8	2017-01-08	188	5	2017-01-05	145
3	2017-01-03	150	8	2017-01-08	188	5	2017-01-05	145
5	2017-01-05	145	8	2017-01-08	188	5	2017-01-05	145
6	2017-01-06	1455	8	2017-01-08	188	5	2017-01-05	145
7	2017-01-07	199	8	2017-01-08	188	5	2017-01-05	145
8	2017-01-08	188	8	2017-01-08	188	5	2017-01-05	145
2	2017-01-02	109	2	2017-01-02	109	6	2017-01-06	1455
3	2017-01-03	150	2	2017-01-02	109	6	2017-01-06	1455
5	2017-01-05	145	2	2017-01-02	109	6	2017-01-06	1455
6	2017-01-06	1455	2	2017-01-02	109	6	2017-01-06	1455
7	2017-01-07	199	2	2017-01-02	109	6	2017-01-06	1455
8	2017-01-08	188	2	2017-01-02	109	6	2017-01-06	1455
2	2017-01-02	109	3	2017-01-03	150	6	2017-01-06	1455
3	2017-01-03	150	3	2017-01-03	150	6	2017-01-06	1455
5	2017-01-05	145	3	2017-01-03	150	6	2017-01-06	1455
6	2017-01-06	1455	3	2017-01-03	150	6	2017-01-06	1455
7	2017-01-07	199	3	2017-01-03	150	6	2017-01-06	1455
8	2017-01-08	188	3	2017-01-03	150	6	2017-01-06	1455
2	2017-01-02	109	5	2017-01-05	145	6	2017-01-06	1455
3	2017-01-03	150	5	2017-01-05	145	6	2017-01-06	1455
5	2017-01-05	145	5	2017-01-05	145	6	2017-01-06	1455
6	2017-01-06	1455	5	2017-01-05	145	6	2017-01-06	1455
7	2017-01-07	199	5	2017-01-05	145	6	2017-01-06	1455
8	2017-01-08	188	5	2017-01-05	145	6	2017-01-06	1455
2	2017-01-02	109	6	2017-01-06	1455	6	2017-01-06	1455
3	2017-01-03	150	6	2017-01-06	1455	6	2017-01-06	1455
5	2017-01-05	145	6	2017-01-06	1455	6	2017-01-06	1455
6	2017-01-06	1455	6	2017-01-06	1455	6	2017-01-06	1455
7	2017-01-07	199	6	2017-01-06	1455	6	2017-01-06	1455
8	2017-01-08	188	6	2017-01-06	1455	6	2017-01-06	1455
2	2017-01-02	109	7	2017-01-07	199	6	2017-01-06	1455
3	2017-01-03	150	7	2017-01-07	199	6	2017-01-06	1455
5	2017-01-05	145	7	2017-01-07	199	6	2017-01-06	1455
6	2017-01-06	1455	7	2017-01-07	199	6	2017-01-06	1455
7	2017-01-07	199	7	2017-01-07	199	6	2017-01-06	1455
8	2017-01-08	188	7	2017-01-07	199	6	2017-01-06	1455
2	2017-01-02	109	8	2017-01-08	188	6	2017-01-06	1455
3	2017-01-03	150	8	2017-01-08	188	6	2017-01-06	1455
5	2017-01-05	145	8	2017-01-08	188	6	2017-01-06	1455
6	2017-01-06	1455	8	2017-01-08	188	6	2017-01-06	1455
7	2017-01-07	199	8	2017-01-08	188	6	2017-01-06	1455
8	2017-01-08	188	8	2017-01-08	188	6	2017-01-06	1455
2	2017-01-02	109	2	2017-01-02	109	7	2017-01-07	199
3	2017-01-03	150	2	2017-01-02	109	7	2017-01-07	199
5	2017-01-05	145	2	2017-01-02	109	7	2017-01-07	199
6	2017-01-06	1455	2	2017-01-02	109	7	2017-01-07	199
7	2017-01-07	199	2	2017-01-02	109	7	2017-01-07	199
8	2017-01-08	188	2	2017-01-02	109	7	2017-01-07	199
2	2017-01-02	109	3	2017-01-03	150	7	2017-01-07	199
3	2017-01-03	150	3	2017-01-03	150	7	2017-01-07	199
5	2017-01-05	145	3	2017-01-03	150	7	2017-01-07	199
6	2017-01-06	1455	3	2017-01-03	150	7	2017-01-07	199
7	2017-01-07	199	3	2017-01-03	150	7	2017-01-07	199
8	2017-01-08	188	3	2017-01-03	150	7	2017-01-07	199
2	2017-01-02	109	5	2017-01-05	145	7	2017-01-07	199
3	2017-01-03	150	5	2017-01-05	145	7	2017-01-07	199
5	2017-01-05	145	5	2017-01-05	145	7	2017-01-07	199
6	2017-01-06	1455	5	2017-01-05	145	7	2017-01-07	199
7	2017-01-07	199	5	2017-01-05	145	7	2017-01-07	199
8	2017-01-08	188	5	2017-01-05	145	7	2017-01-07	199
2	2017-01-02	109	6	2017-01-06	1455	7	2017-01-07	199
3	2017-01-03	150	6	2017-01-06	1455	7	2017-01-07	199
5	2017-01-05	145	6	2017-01-06	1455	7	2017-01-07	199
6	2017-01-06	1455	6	2017-01-06	1455	7	2017-01-07	199
7	2017-01-07	199	6	2017-01-06	1455	7	2017-01-07	199
8	2017-01-08	188	6	2017-01-06	1455	7	2017-01-07	199
2	2017-01-02	109	7	2017-01-07	199	7	2017-01-07	199
3	2017-01-03	150	7	2017-01-07	199	7	2017-01-07	199
5	2017-01-05	145	7	2017-01-07	199	7	2017-01-07	199
6	2017-01-06	1455	7	2017-01-07	199	7	2017-01-07	199
7	2017-01-07	199	7	2017-01-07	199	7	2017-01-07	199
8	2017-01-08	188	7	2017-01-07	199	7	2017-01-07	199
2	2017-01-02	109	8	2017-01-08	188	7	2017-01-07	199
3	2017-01-03	150	8	2017-01-08	188	7	2017-01-07	199
5	2017-01-05	145	8	2017-01-08	188	7	2017-01-07	199
6	2017-01-06	1455	8	2017-01-08	188	7	2017-01-07	199
7	2017-01-07	199	8	2017-01-08	188	7	2017-01-07	199
8	2017-01-08	188	8	2017-01-08	188	7	2017-01-07	199
2	2017-01-02	109	2	2017-01-02	109	8	2017-01-08	188
3	2017-01-03	150	2	2017-01-02	109	8	2017-01-08	188
5	2017-01-05	145	2	2017-01-02	109	8	2017-01-08	188
6	2017-01-06	1455	2	2017-01-02	109	8	2017-01-08	188
7	2017-01-07	199	2	2017-01-02	109	8	2017-01-08	188
8	2017-01-08	188	2	2017-01-02	109	8	2017-01-08	188
2	2017-01-02	109	3	2017-01-03	150	8	2017-01-08	188
3	2017-01-03	150	3	2017-01-03	150	8	2017-01-08	188
5	2017-01-05	145	3	2017-01-03	150	8	2017-01-08	188
6	2017-01-06	1455	3	2017-01-03	150	8	2017-01-08	188
7	2017-01-07	199	3	2017-01-03	150	8	2017-01-08	188
8	2017-01-08	188	3	2017-01-03	150	8	2017-01-08	188
2	2017-01-02	109	5	2017-01-05	145	8	2017-01-08	188
3	2017-01-03	150	5	2017-01-05	145	8	2017-01-08	188
5	2017-01-05	145	5	2017-01-05	145	8	2017-01-08	188
6	2017-01-06	1455	5	2017-01-05	145	8	2017-01-08	188
7	2017-01-07	199	5	2017-01-05	145	8	2017-01-08	188
8	2017-01-08	188	5	2017-01-05	145	8	2017-01-08	188
2	2017-01-02	109	6	2017-01-06	1455	8	2017-01-08	188
3	2017-01-03	150	6	2017-01-06	1455	8	2017-01-08	188
5	2017-01-05	145	6	2017-01-06	1455	8	2017-01-08	188
6	2017-01-06	1455	6	2017-01-06	1455	8	2017-01-08	188
7	2017-01-07	199	6	2017-01-06	1455	8	2017-01-08	188
8	2017-01-08	188	6	2017-01-06	1455	8	2017-01-08	188
2	2017-01-02	109	7	2017-01-07	199	8	2017-01-08	188
3	2017-01-03	150	7	2017-01-07	199	8	2017-01-08	188
5	2017-01-05	145	7	2017-01-07	199	8	2017-01-08	188
6	2017-01-06	1455	7	2017-01-07	199	8	2017-01-08	188
7	2017-01-07	199	7	2017-01-07	199	8	2017-01-08	188
8	2017-01-08	188	7	2017-01-07	199	8	2017-01-08	188
2	2017-01-02	109	8	2017-01-08	188	8	2017-01-08	188
3	2017-01-03	150	8	2017-01-08	188	8	2017-01-08	188
5	2017-01-05	145	8	2017-01-08	188	8	2017-01-08	188
6	2017-01-06	1455	8	2017-01-08	188	8	2017-01-08	188
7	2017-01-07	199	8	2017-01-08	188	8	2017-01-08	188
8	2017-01-08	188	8	2017-01-08	188	8	2017-01-08	188
Note: - There are 6 days with more than 100 people. So there are 216(666) records in total after Cartesian product. - The first 3 columns are from t1, and the next 3 ones are from t2, and the last 3 are from t3.

Considering t1, t2 and t3 are identical, we can take one of them to consider what conditions we should add to filter the data and get the final result. Taking t1 for example, it could exist in the beginning of the consecutive 3 days, or the middle, or the last.

t1 in the beginning: (t1.id - t2.id = 1 and t1.id - t3.id = 2 and t2.id - t3.id =1) -- t1, t2, t3
t1 in the middle: (t2.id - t1.id = 1 and t2.id - t3.id = 2 and t1.id - t3.id =1) -- t2, t1, t3
t1 in the end: (t3.id - t2.id = 1 and t2.id - t1.id =1 and t3.id - t1.id = 2) -- t3, t2, t1
So if we add these conditions in the SQL code, we can get this.

select t1.*
from stadium t1, stadium t2, stadium t3
where t1.people >= 100 and t2.people >= 100 and t3.people >= 100
and
(
      (t1.id - t2.id = 1 and t1.id - t3.id = 2 and t2.id - t3.id =1)  -- t1, t2, t3
    or
    (t2.id - t1.id = 1 and t2.id - t3.id = 2 and t1.id - t3.id =1) -- t2, t1, t3
    or
    (t3.id - t2.id = 1 and t2.id - t1.id =1 and t3.id - t1.id = 2) -- t3, t2, t1
)
;
| id | date       | people |
|----|------------|--------|
| 7  | 2017-01-07 | 199    |
| 6  | 2017-01-06 | 1455   |
| 8  | 2017-01-08 | 188    |
| 7  | 2017-01-07 | 199    |
| 5  | 2017-01-05 | 145    |
| 6  | 2017-01-06 | 1455   |
You may notice some records duplicates since they appear in different positions. So, we can use DISTINCT to deal with it.

MySQL

select distinct t1.*
from stadium t1, stadium t2, stadium t3
where t1.people >= 100 and t2.people >= 100 and t3.people >= 100
and
(
      (t1.id - t2.id = 1 and t1.id - t3.id = 2 and t2.id - t3.id =1)  -- t1, t2, t3
    or
    (t2.id - t1.id = 1 and t2.id - t3.id = 2 and t1.id - t3.id =1) -- t2, t1, t3
    or
    (t3.id - t2.id = 1 and t2.id - t1.id =1 and t3.id - t1.id = 2) -- t3, t2, t1
)
order by t1.id
;
 */
public class Leetcode601 {

}
