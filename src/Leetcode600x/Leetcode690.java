package Leetcode600x;
/**
 * 690. Employee Importance
DescriptionHintsSubmissionsDiscussSolution
You are given a data structure of employee information, which includes the employee's unique id, his importance value and his direct subordinates' id.

For example, employee 1 is the leader of employee 2, and employee 2 is the leader of employee 3. They have importance value 15, 10 and 5, respectively. Then employee 1 has a data structure like [1, 15, [2]], and employee 2 has [2, 10, [3]], and employee 3 has [3, 5, []]. Note that although employee 3 is also a subordinate of employee 1, the relationship is not direct.

Now given the employee information of a company, and an employee id, you need to return the total importance value of this employee and all his subordinates.

Example 1:
Input: [[1, 5, [2, 3]], [2, 3, []], [3, 3, []]], 1
Output: 11
Explanation:
Employee 1 has importance value 5, and he has two direct subordinates: employee 2 and employee 3. They both have importance value 3. So the total importance value of employee 1 is 5 + 3 + 3 = 11.
Note:
One employee has at most one direct leader and may have several subordinates.
The maximum number of employees won't exceed 2000.
Seen this question in a real interview before?


Approach #1: Depth-First Search [Accepted]
Intuition and Algorithm

Let's use a hashmap emap = {employee.id -> employee} to query employees quickly.

Now to find the total importance of an employee, it will be the importance of that employee, plus the total importance of each of that employee's subordinates. This is a straightforward depth-first search.


Complexity Analysis

Time Complexity: O(N)O(N), where NN is the number of employees. We might query each employee in dfs.

Space Complexity: O(N)O(N), the size of the implicit call stack when evaluating dfs.

Analysis written by: @awice.
 * @author tonghe
 *
 */
public class Leetcode690 {
	class Solution {
	    Map<Integer, Employee> emap;
	    public int getImportance(List<Employee> employees, int queryid) {
	        emap = new HashMap();
	        for (Employee e: employees) emap.put(e.id, e);
	        return dfs(queryid);
	    }
	    public int dfs(int eid) {
	        Employee employee = emap.get(eid);
	        int ans = employee.importance;
	        for (Integer subid: employee.subordinates)
	            ans += dfs(subid);
	        return ans;
	    }
	}
}
