package alite.leetcode.newest;
/**
 * LeetCode 631 - Design Excel Sum Formula

https://leetcode.com/problems/design-excel-sum-formula
Your task is to design the basic function of Excel and implement the function of sum formula. Specifically, you need to implement the following functions:
Excel(int H, char W): This is the constructor. The inputs represents the height and width of the Excel form. H is a positive integer, range from 1 to 26. It represents the height. W is a character range from 'A' to 'Z'. It represents that the width is the number of characters from 'A' to W. The Excel form content is represented by a height * width 2D integer array C, it should be initialized to zero. You should assume that the first row of C starts from 1, and the first column of C starts from 'A'.

void Set(int row, char column, int val): Change the value at C(row, column) to be val.

int Get(int row, char column): Return the value at C(row, column).

int Sum(int row, char column, List of Strings : numbers): This function calculate and set the value at C(row, column), where the value should be the sum of cells represented by numbers. This function return the sum result at C(row, column). This sum formula should exist until this cell is overlapped by another value or another sum formula.
numbers is a list of strings that each string represent a cell or a range of cells. If the string represent a single cell, then it has the following format : ColRow. For example, "F7" represents the cell at (7, F).
If the string represent a range of cells, then it has the following format : ColRow1:ColRow2. The range will always be a rectangle, and ColRow1 represent the position of the top-left cell, and ColRow2 represents the position of the bottom-right cell.

Example 1:
Excel(3,"C"); 
// construct a 3*3 2D array with all zero.
//   A B C
// 1 0 0 0
// 2 0 0 0
// 3 0 0 0

Set(1, "A", 2);
// set C(1,"A") to be 2.
//   A B C
// 1 2 0 0
// 2 0 0 0
// 3 0 0 0

Sum(3, "C", ["A1", "A1:B2"]);
// set C(3,"C") to be the sum of value at C(1,"A") and the values sum of the rectangle range whose top-left cell is C(1,"A") and bottom-right cell is C(2,"B"). Return 4. 
//   A B C
// 1 2 0 0
// 2 0 0 0
// 3 0 0 4

Set(2, "B", 2);
// set C(2,"B") to be 2. Note C(3, "C") should also be changed.
//   A B C
// 1 2 0 0
// 2 0 2 0
// 3 0 0 6
Note:
You could assume that there won't be any circular sum reference. For example, A1 = sum(B1) and B1 = sum(A1).
The test cases are using double-quotes to represent a character.
Please remember to RESET your class variables declared in class Excel, as static/class variables are persisted across multiple test cases. Please see here for more details.
/**
 * Your Excel object will be instantiated and called as such:
 * Excel obj = new Excel(H, W);
 * obj.set(r,c,v);
 * int param_2 = obj.get(r,c);
 * int param_3 = obj.sum(r,c,strs);
 */
https://leetcode.com/articles/design-excel-sum-formula/
Firstly, we can note that once a formula is applied to any cell in excel, let's say C1 = C2 + C3C1=C2+C3, if any change is made to C2C2 or C3C3, the result to be put into C1C1 needs to be evaluated again based on the new values of C2C2 and C3C3. Further, suppose some other cell, say D2D2 is also dependent on C1C1 due to some prior formula applied to D2D2. Then, when any change is made to, say, C2C2, we re-evaluate C1C1's value. Furhter, since D2D2 is dependent on C1C1, we need to re-evaluate D2D2's value as well.
Thus, whenver, we make any change to any cell, xx, we need to determine the cells which are dependent on xx, and update these cells, and further determine the cells which are dependent on the changed cells and so on. We can assume that no cycles are present in the formulas, i.e. Any cell's value won't directly or indirectly be dependent on its own value.
But, while doing these set of evaluations of the cells to determine their updated values, we need to update the cells in such an order that the cell on which some other cell is dependent is always evaluated prior to the cell which is dependent on the former cell. In order to do so, we can view the dependence between the cells in the form of a dependency graph, which can be a Directed Graph. Since, no cycles are allowed between the formulas, the graph reduces to a Directed Acyclic Graph. Now, to solve the problem of evaluating the cells in the required order, we can make use of a very well known method specifically used for such problems in Directed Acyclic Graphs, known as the Topological Sorting.
in the case of a topological sort, we can't print a node until all the nodes on which it is dependent have already been printed. To solve this problem, we make use of a temporary stack. We do the traversals in the same manner as in DFS, but we don’t print the current vertex immediately, we first recursively call topological sorting for all its adjacent vertices, then push it to a stack. Note that a vertex is pushed to stack only when all of its adjacent(dependent) vertices (and their adjacent(dependent) vertices and so on) are already in stack. At the end, we print the contents of the stack. Thus, we obtain the correct ordering of the vertices

set takes O\big((r*c)^2\big)O((r∗c)
​2
​​ ) time. Here, rr and cc refer to the number of rows and columns in the current Excel Form. There can be a maximum of O(r*c)O(r∗c)formulas for an Excel Form with rr rows and cc columns. For each formula, r*cr∗c time will be needed to find the dependent nodes. Thus, in the worst case, a total of O\big((r*c)^2\big)O((r∗c)
​2
​​ ) will be needed.
sum takes O\big((r*c)^2 + 2*r*c*l\big)O((r∗c)
​2
​​ +2∗r∗c∗l) time. Here, ll refers to the number of elements in the the list of strings used for obtaining the cells required for the current sum. In the worst case, the expansion of each such element requires O(r*c)O(r∗c) time, leading to O(l*r*c)O(l∗r∗c) time for expanding ll such elements. After doing the expansion, calculate_sum itself requires O(l*r*c)O(l∗r∗c) time for traversing over the required elements for obtaining the sum. After this, we need to update all the dependent cells, which requires the use of set which itself requires O\big((r*c)^2\big)O((r∗c)
​2
​​ ) time.
get takes O(1)O(1) time.
The space required will be O\big((r*c)^2\big)O((r∗c)
​2
​​ ) in the worst case. O(r*c)O(r∗c) space will be required for the Excel Form itself. For each cell in this form, the cellscellslist can contain O(r*c)O(r∗c) cells.
    Formula[][] Formulas;
    class Formula {
        Formula(HashMap < String, Integer > c, int v) {
            val = v;
            cells = c;
        }
        HashMap < String, Integer > cells;
        int val;
    }
    Stack < int[] > stack = new Stack < > ();
    public Excel(int H, char W) {
        Formulas = new Formula[H][(W - 'A') + 1];
    }

    public int get(int r, char c) {
        if (Formulas[r - 1][c - 'A'] == null)
            return 0;
        return Formulas[r - 1][c - 'A'].val;
    }
    public void set(int r, char c, int v) {
        Formulas[r - 1][c - 'A'] = new Formula(new HashMap < String, Integer > (), v);
        topologicalSort(r - 1, c - 'A');
        execute_stack();
    }

    public int sum(int r, char c, String[] strs) {
        HashMap < String, Integer > cells = convert(strs);
        int summ = calculate_sum(r - 1, c - 'A', cells);
        set(r, c, summ);
        Formulas[r - 1][c - 'A'] = new Formula(cells, summ);
        return summ;
    }

    public void topologicalSort(int r, int c) {
        for (int i = 0; i < Formulas.length; i++)
            for (int j = 0; j < Formulas[0].length; j++)
                if (Formulas[i][j] != null && Formulas[i][j].cells.containsKey("" + (char)('A' + c) + (r + 1))) {
                    topologicalSort(i, j);
                }
        stack.push(new int[] {r,c});
    }

    public void execute_stack() {
        while (!stack.isEmpty()) {
            int[] top = stack.pop();
            if (Formulas[top[0]][top[1]].cells.size() > 0)
                calculate_sum(top[0], top[1], Formulas[top[0]][top[1]].cells);
        }
    }

    public HashMap < String, Integer > convert(String[] strs) {
        HashMap < String, Integer > res = new HashMap < > ();
        for (String st: strs) {
            if (st.indexOf(":") < 0)
                res.put(st, res.getOrDefault(st, 0) + 1);
            else {
                String[] cells = st.split(":");
                int si = Integer.parseInt(cells[0].substring(1)), ei = Integer.parseInt(cells[1].substring(1));
                char sj = cells[0].charAt(0), ej = cells[1].charAt(0);
                for (int i = si; i <= ei; i++) {
                    for (char j = sj; j <= ej; j++) {
                        res.put("" + j + i, res.getOrDefault("" + j + i, 0) + 1);
                    }
                }
            }
        }
        return res;
    }

    public int calculate_sum(int r, int c, HashMap < String, Integer > cells) {
        int sum = 0;
        for (String s: cells.keySet()) {
            int x = Integer.parseInt(s.substring(1)) - 1, y = s.charAt(0) - 'A';
            sum += (Formulas[x][y] != null ? Formulas[x][y].val : 0) * cells.get(s);
        }
        Formulas[r][c] = new Formula(cells, sum);
        return sum;
    }

https://discuss.leetcode.com/topic/93812/c-3-ms-concise-and-easy-to-understand


1 Sum(1, "A", ["A1","B1"]);
2 Sum(1, "A", ["B1","C1"]);
Sum(1, "B", ["A1"]);
I think this shoud be illegal and need to defend it.

X. https://discuss.leetcode.com/topic/94526/a-different-solution-in-java-beats-80

http://bookshadow.com/weblog/2017/06/25/leetcode-design-excel-sum-formula/
观察者模式（Observer Pattern）
为单元格cell注册观察者列表target（关心cell变化的单元格），被观察者列表source（变化会影响到cell的单元格）
利用字典values存储每个单元格的值
单元格之间的观察者关系为图结构，当某一单元格发生变化时，其所有观察者节点均会依次发生变化
对于某单元格触发的观察者单元格更新操作，可以利用BFS实现
当执行set操作时，清除单元格的被观察者列表，然后更新其观察者列表的值
当执行sum操作时，清除单元格的被观察者列表，然后重新注册其被观察者，并更新其被观察者的观察关系，最后更新其观察者列表的值
 * @author het
 *
 */
public class LeetCode631 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
