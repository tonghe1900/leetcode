package Leetcode600x;
/**
 * 
 * @author tonghe
 *602. Friend Requests II: Who Has the Most Friends [SQL]
https://leetcode.com/problems/friend-requests-ii-who-has-the-most-friends

In a social network like Facebook or Twitter, people send friend requests and accept others’ requests as well.

Table

request_accepted

holds the data of friend acceptance, while

requester_id

and

accepter_id

both are the id of a person.

| requester_id | accepter_id | accept_date|
|--------------|-------------|------------|
| 1            | 2           | 2016_06-03 |
| 1            | 3           | 2016-06-08 |
| 2            | 3           | 2016-06-08 |
| 3            | 4           | 2016-06-09 |
Write a query to find the the people who has most friends and the most friends number. For the sample data above, the result is:

| id | num |
|----|-----|
| 3  | 3   |
Note:

It is guaranteed there is only 1 people having the most friends.The friend request could only been accepted once, which mean there is no multiple records with the same

requester_id

and

accepter_id

value.

Explanation:

The person with id ‘3’ is a friend of people ‘1’, '2’ and '4’, so he has 3 friends in total, which is the most number than any others.

Follow-up:

In the real world, multiple people could have the same most number of friends, can you find all these people in this case?

之前union 错的，然后用了union all 就解决了！

“Note that UNION only appends distinct values. More specifically, when you use UNION, the dataset is appended, and any rows in the appended table that are exactly identical to rows in the first table are dropped. If you’d like to append all the values from the second table, use UNION ALL. You’ll likely use UNION ALLfar more often than UNION. In this particular case, there are no duplicate rows, so UNION ALL will produce the same results”

Solution：

select id, sum© num
from(
  select requester_id as id, count(accepter_id) as c
  from request_accepted 
  group by requester_id
  union all
  select accepter_id as id, count(requester_id) as c
  from request_accepted 
  group by accepter_id
) t
group by id
order by num desc limit 1
 */
public class Leetcode602 {

}
