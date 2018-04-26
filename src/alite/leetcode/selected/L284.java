package alite.leetcode.selected;
/**
 * LEETCODE 284. PEEKING ITERATOR
LC address: Peeking Iterator

Given an Iterator class interface with methods: next() and hasNext(), design and implement a PeekingIterator that support the 
peek() operation — it essentially peek() at the element that will be returned by the next call to next().

Here is an example. Assume that the iterator is initialized to the beginning of the list: [1, 2, 3].

Call next() gets you 1, the first element in the list.

Now you call peek() and it returns 2, the next element. Calling next() after that still return 2.

You call next() the final time and it returns 3, the last element. Calling hasNext() after that should return false.

Analysis:

把next()和hasNext()的结果先提取出来并保存为next : Integer和hasNext : boolean，这样peek()的时候只需要返回保存的值即可，只有当next()被调用的时候才更新next和hasNext的值。

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
20
21
22
23
24
25
26
27
28
29
30
31
32
33
34
35
36
37
38
39
40
41
42
// Java Iterator interface reference:
// https://docs.oracle.com/javase/8/docs/api/java/util/Iterator.html
class PeekingIterator implements Iterator<Integer> {
    Iterator<Integer> iterator;
    Integer next;
    boolean hasNext;
 
    public PeekingIterator(Iterator<Integer> iterator) {
        // initialize any member here.
        this.iterator = iterator;
        hasNext = iterator.hasNext();
        if (hasNext) {
            next = iterator.next();
        } else {
            next = null;
        }
    }
 
    // Returns the next element in the iteration without advancing the iterator.
    public Integer peek() {
        return next;
    }
 
    // hasNext() and next() should behave the same as in the Iterator interface.
    // Override them if needed.
    @Override
    public Integer next() {
        int res = next;
        hasNext = iterator.hasNext();
        if (hasNext()) {
            next = iterator.next();
        } else {
            next = null;
        }
        return res;
    }
 
    @Override
    public boolean hasNext() {
        return hasNext;
    }
}
 * @author het
 *
 */
public class L284 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
