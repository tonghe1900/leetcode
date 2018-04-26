package Leetcode600x;
/**
 * 
 * @author tonghe
 *603. Consecutive Available Seats 
 
 
 
 
 
 
 
 
Average Rating: 4.80 (5 votes)

June 15, 2017  |  3.8K views
Several friends at a cinema ticket office would like to reserve consecutive available seats.
Can you help to query all the consecutive available seats order by the seat_id using the following cinema table?
| seat_id | free |
|---------|------|
| 1       | 1    |
| 2       | 0    |
| 3       | 1    |
| 4       | 1    |
| 5       | 1    |
Your query should return the following result for the sample case above.
| seat_id |
|---------|
| 3       |
| 4       |
| 5       |
Note:
The seat_id is an auto increment int, and free is bool ('1' means free, and '0' means occupied.).
Consecutive available seats are more than 2(inclusive) seats consecutively available.
Solution
Approach: Using self join and abs()[Accepted]
Intuition

There is only one table in this problem, so we probably need to use self join for this relative complex problem.

Algorithm

First, let's see what we have after joining this table with itself.

Note: The result of join two tables is the Cartesian product of these two tables.

select a.seat_id, a.free, b.seat_id, b.free
from cinema a join cinema b;
seat_id	free	seat_id	free
1	1	1	1
2	0	1	1
3	1	1	1
4	1	1	1
5	1	1	1
1	1	2	0
2	0	2	0
3	1	2	0
4	1	2	0
5	1	2	0
1	1	3	1
2	0	3	1
3	1	3	1
4	1	3	1
5	1	3	1
1	1	4	1
2	0	4	1
3	1	4	1
4	1	4	1
5	1	4	1
1	1	5	1
2	0	5	1
3	1	5	1
4	1	5	1
5	1	5	1
To find the consecutive available seats, the value in the a.seat_id should be more(or less) than the value b.seat_id, and both of them should be free.

select a.seat_id, a.free, b.seat_id, b.free
from cinema a join cinema b
  on abs(a.seat_id - b.seat_id) = 1
  and a.free = true and b.free = true;
seat_id	free	seat_id	free
4	1	3	1
3	1	4	1
5	1	4	1
4	1	5	1
At last, choose the concerned column seat_id, and display the result ordered by seat_id.

Note: You may notice that the seat with seat_id '4' appears twice in this table. This is because seat '4' next to '3' and also next to '5'. So we need to use distinct to filter the duplicated records.

MySQL

select distinct a.seat_id
from cinema a join cinema b
  on abs(a.seat_id - b.seat_id) = 1
  and a.free = true and b.free = true
order by a.seat_id
;
 */
public class Leetcode603 {

}
