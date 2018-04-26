package Leetcode600x;
/**
 * 
 * @author tonghe
 *608. Tree Node 
 
 
 
 
 
 
 
 
Average Rating: 4.50 (4 votes)

July 10, 2017  |  4.3K views
Given a table tree, id is identifier of the tree node and p_id is its parent node's id.

+----+------+
| id | p_id |
+----+------+
| 1  | null |
| 2  | 1    |
| 3  | 1    |
| 4  | 2    |
| 5  | 2    |
+----+------+
Each node in the tree can be one of three types:
Leaf: if the node is a leaf node.
Root: if the node is the root of the tree.
Inner: If the node is neither a leaf node nor a root node.
Write a query to print the node id and the type of the node. Sort your output by the node id. The result for the above sample is:
+----+------+
| id | Type |
+----+------+
| 1  | Root |
| 2  | Inner|
| 3  | Leaf |
| 4  | Leaf |
| 5  | Leaf |
+----+------+
Explanation

Node '1' is root node, because its parent node is NULL and it has child node '2' and '3'.
Node '2' is inner node, because it has parent node '1' and child node '4' and '5'.
Node '3', '4' and '5' is Leaf node, because they have parent node and they don't have child node.

And here is the image of the sample tree as below:
			  1
			/   \
                      2       3
                    /   \
                  4       5
Note

If there is only one node on the tree, you only need to output its root attributes.

Solution
Approach I: Using UNION [Accepted]
Intuition

We can print the node type by judging every record by its definition in this table. Root: it does not have a parent node at all Inner: it is the parent node of some nodes, and it has a not NULL parent itself. * Leaf: rest of the cases other than above two

Algorithm

By transiting the node type definition, we can have the following code.

For the root node, it does not have a parent.

SELECT
    id, 'Root' AS Type
FROM
    tree
WHERE
    p_id IS NULL
For the leaf nodes, they do not have any children, and it has a parent.

SELECT
    id, 'Leaf' AS Type
FROM
    tree
WHERE
    id NOT IN (SELECT DISTINCT
            p_id
        FROM
            tree
        WHERE
            p_id IS NOT NULL)
        AND p_id IS NOT NULL
For the inner nodes, they have have some children and a parent.

SELECT
    id, 'Inner' AS Type
FROM
    tree
WHERE
    id IN (SELECT DISTINCT
            p_id
        FROM
            tree
        WHERE
            p_id IS NOT NULL)
        AND p_id IS NOT NULL
So, one solution to the problem is to combine these cases together using UNION.

MySQL

SELECT
    id, 'Root' AS Type
FROM
    tree
WHERE
    p_id IS NULL

UNION

SELECT
    id, 'Leaf' AS Type
FROM
    tree
WHERE
    id NOT IN (SELECT DISTINCT
            p_id
        FROM
            tree
        WHERE
            p_id IS NOT NULL)
        AND p_id IS NOT NULL

UNION

SELECT
    id, 'Inner' AS Type
FROM
    tree
WHERE
    id IN (SELECT DISTINCT
            p_id
        FROM
            tree
        WHERE
            p_id IS NOT NULL)
        AND p_id IS NOT NULL
ORDER BY id;
Approach II: Using flow control statement CASE [Accepted]
Algorithm

The idea is similar with the above solution but the code is simpler by utilizing the flow control statements, which is effective to output differently based on different input values. In this case, we can use CASE statement.

MySQL

SELECT
    id AS `Id`,
    CASE
        WHEN tree.id = (SELECT atree.id FROM tree atree WHERE atree.p_id IS NULL)
          THEN 'Root'
        WHEN tree.id IN (SELECT atree.p_id FROM tree atree)
          THEN 'Inner'
        ELSE 'Leaf'
    END AS Type
FROM
    tree
ORDER BY `Id`
;
MySQL provides different flow control statements besides CASE. You can try to rewrite the slution above using IF flow control statement.

Approach III: Using IF function [Accepted]
Algorithm

Also, we can use a single IF function instead of the complex flow control statements.

MySQL

SELECT
    atree.id,
    IF(ISNULL(atree.p_id),
        'Root',
        IF(atree.id IN (SELECT p_id FROM tree), 'Inner','Leaf')) Type
FROM
    tree atree
ORDER BY atree.id
Note: This solution was inspired by @richarddia
 */
public class Leetcode608 {

}
