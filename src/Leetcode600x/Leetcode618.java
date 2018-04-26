package Leetcode600x;
/**
 * 618. Students Report By Geography 
 
 
 
 
 
 
 
 
Average Rating: 4.60 (5 votes)

June 21, 2017  |  2.9K views
A U.S graduate school has students from Asia, Europe and America. The students' location information are stored in table student as below.
| name   | continent |
|--------|-----------|
| Jack   | America   |
| Pascal | Europe    |
| Xi     | Asia      |
| Jane   | America   |
Pivot the continent column in this table so that each name is sorted alphabetically and displayed underneath its corresponding continent. The output headers should be America, Asia and Europe respectively. It is guaranteed that the student number from America is no less than either Asia or Europe.
For the sample input, the output is:
| America | Asia | Europe |
|---------|------|--------|
| Jack    | Xi   | Pascal |
| Jane    |      |        |
Follow-up: If it is unknown which continent has the most students, can you write a query to generate the student report?
Solution
Approach: Using "session variables" and join [Accepted]
Intuition

Assign a separate auto increment row id to each of the continent, and then join them together.

Algorithm

To set the row id for each continent, we need to use session variables. For example, we can use the following statement to assign a auto increment row number for students in America.

SELECT 
    row_id, America
FROM
    (SELECT @am:=0) t,
    (SELECT 
        @am:=@am + 1 AS row_id, name AS America
    FROM
        student
    WHERE
        continent = 'America'
    ORDER BY America) AS t2
;
| row_id | America |
|--------|---------|
| 1      | Jack    |
| 2      | Jane    |
Similarly, we can assign other dedicated row id for other continents as the following result.

| row_id | Asia |
|--------|------|
| 1      | Xi   |

| row_id | Europe |
|--------|--------|
| 1      | Jesper |
Then if we join these 3 temp tables together and using the same row_id as the condition, we can have the following table.

| row_id | America | Asia | Europe |
|--------|---------|------|--------|
| 1      | Jack    | Xi   | Pascal |
| 2      | Jane    |      |        |
One issue you may encounter is the student list for America is not complete if you use regular inner join since there are more records in this list comparing with the other two. So you may have a solution to use the outer join. Correct! But how to arrange the 3 tables? The trick is to put the America list in the middle so that we can use right (outer) join and right (outer) join to connect with other two tables.

MySQL

SELECT 
    America, Asia, Europe
FROM
    (SELECT @as:=0, @am:=0, @eu:=0) t,
    (SELECT 
        @as:=@as + 1 AS asid, name AS Asia
    FROM
        student
    WHERE
        continent = 'Asia'
    ORDER BY Asia) AS t1
        RIGHT JOIN
    (SELECT 
        @am:=@am + 1 AS amid, name AS America
    FROM
        student
    WHERE
        continent = 'America'
    ORDER BY America) AS t2 ON asid = amid
        LEFT JOIN
    (SELECT 
        @eu:=@eu + 1 AS euid, name AS Europe
    FROM
        student
    WHERE
        continent = 'Europe'
    ORDER BY Europe) AS t3 ON amid = euid
;
Rate this article
 * @author tonghe
 *
 */
public class Leetcode618 {

}
