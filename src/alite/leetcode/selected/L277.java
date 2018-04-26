package alite.leetcode.selected;
/**
 * LEETCODE 277. FIND THE CELEBRITY
LC address: Find the Celebrity

Suppose you are at a party with n people (labeled from 0 to n - 1) and among them, there may exist one celebrity.
 The definition of a celebrity is that all the other n - 1 people know him/her but he/she does not know any of them.

Now you want to find out who the celebrity is or verify that there is not one. The only thing you are allowed to 
do is to ask questions like: “Hi, A. Do you know B?” to get information of whether A knows B. You need to find out
 the celebrity (or verify there is not one) by asking as few questions as possible (in the asymptotic sense).

You are given a helper function bool knows(a, b) which tells you whether A knows B. Implement a function int 
findCelebrity(n), your function should minimize the number of calls to knows.

Note: There will be exactly one celebrity if he/she is in the party. Return the celebrity’s label if there
 is a celebrity in the party. If there is no celebrity, return -1.

Analysis:

理清逻辑就好，先假设名人是0号，遍历所有人，看当前人认不认识名人，如果不认识，假设当前人就是名人以此继续。因为名人必须被所有人认识，
同时不认识所有人，所以通过这样的遍历筛选相当于“必要条件”。所以在之后需要重新验证一下我们得到的名人，完成“充分条件”部分。

Solution:

1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
// The knows API is defined in the parent class Relation.
  //    boolean knows(int a, int b); 
 
public class Solution extends Relation {
    public int findCelebrity(int n) {
        int celebrity = 0;
        for (int i = 0; i < n; i++) {
            celebrity = (knows(celebrity, i)) ? i : celebrity;
        }
        for (int i = 0; i < n; i++) {
            if (i != celebrity) {
                if (knows(celebrity, i) || !knows(i, celebrity)) {
                    return -1;
                }
            }
        }
        return celebrity;
    }
}
 * @author het
 *
 */
public class L277 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
