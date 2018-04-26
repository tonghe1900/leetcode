package alite.leetcode.conquerLeft;
/**
 * LeeCode 341 - Flatten Nested List Iterator

http://www.cnblogs.com/grandyang/p/5358793.html
Given a nested list of integers, implement an iterator to flatten it.
Each element is either an integer, or a list -- whose elements may also be integers or other lists.
Example 1:
Given the list [[1,1],2,[1,1]],
By calling next repeatedly until hasNext returns false, the order of elements returned by next should be:[1,1,2,1,1].
Example 2:
Given the list [1,[4,[6]]],
By calling next repeatedly until hasNext returns false, the order of elements returned by next should be:[1,4,6].
这道题让我们建立压平嵌套链表的迭代器，关于嵌套链表的数据结构最早出现在Nested List Weight Sum中，
而那道题是用的递归的方法来解的，而迭代器一般都是用迭代的方法来解的，而递归一般都需用栈来辅助遍历，
由于栈的后进先出的特性，我们在对向量遍历的时候，从后往前把对象压入栈中，那么第一个对象最后压入栈就会第一个取出来处理，
我们的hasNext()函数需要遍历栈，并进行处理，如果栈顶元素是整数，直接返回true，如果不是，那么移除栈顶元素，并开始遍历这个取出的list，还是从后往前压入栈，循环停止条件是栈为空，返回false

Using Stack:
https://leetcode.com/discuss/95937/simple-iterative-dfs-using-stack
hasNext should be idempotent and optional (so it can be called several times before each next or not at all, and next should still work properly).
public class NestedIterator implements Iterator<Integer> {
    Stack<Iterator<NestedInteger>> stack = new Stack<>();
    Integer current = null;

    public NestedIterator(List<NestedInteger> nestedList) {
        if (nestedList != null) {
            stack.push(nestedList.iterator());
        }
    }

    @Override
    public Integer next() {
        hasNext();

        Integer value = current;
        current = null;
        return value;
    }

    @Override
    public boolean hasNext() {
        while (current == null && !stack.isEmpty()) {
            Iterator<NestedInteger> node = stack.peek();
            if (!node.hasNext()) {
                stack.pop();
                continue;
            }

            NestedInteger value = node.next();
            if (value.isInteger()) {
                current = value.getInteger();
                return true;
            } else {
                stack.push(value.getList().iterator());
            }
        }

        return false;
    }
}
http://www.cnblogs.com/grandyang/p/5358793.html
class NestedIterator {
public:
    NestedIterator(vector<NestedInteger> &nestedList) {
        for (int i = nestedList.size() - 1; i >= 0; --i) {
            s.push(nestedList[i]);
        }
    }

    int next() {
        NestedInteger t = s.top(); s.pop();
        return t.getInteger();
    }

    bool hasNext() {
        while (!s.empty()) {
            NestedInteger t = s.top(); 
            if (t.isInteger()) return true;
            s.pop();
            for (int i = t.getList().size() - 1; i >= 0; --i) {
                s.push(t.getList()[i]);
            }
        }
        return false;
    }

private:
    stack<NestedInteger> s;
};
https://leetcode.com/discuss/95846/easy-to-understand-java-solution
private List<Integer> elements = new LinkedList<>();
public NestedIterator(List<NestedInteger> nestedList) {
    if (   nestedList        == null
        || nestedList.size() == 0)
    {
        return;
    }

    flatten(nestedList, elements);
}

@Override
public Integer next() {
    int ret = -1;
    if (hasNext())
    {
        ret = elements.remove(0);
    }
    return ret;
}

@Override
public boolean hasNext() {
    return elements.size() > 0;
}

private void flatten(List<NestedInteger> nestedList, List<Integer> elements)
{
    for (NestedInteger e: nestedList)
    {
        if (e.isInteger())
        {
            elements.add(e.getInteger());
        }
        else
        {
            flatten(e.getList(), elements);
        }
    }
}
http://www.cnblogs.com/grandyang/p/5358793.html
虽说迭代器是要用迭代的方法，但是我们可以强行使用递归来解，怎么个强行法呢，就是我们使用一个队列queue，在构造函数的时候就利用迭代的方法把这个嵌套链表全部压平展开，然后在调用hasNext()和next()就很简单了：
https://leetcode.com/discuss/95849/java-dfs-solution
don't maintain the index explicitly,  use queue.remove, queue.isEmpty.
public class NestedIterator implements Iterator<Integer> {
List<Integer> result;
int index;
public NestedIterator(List<NestedInteger> nestedList) {
    result = new ArrayList<Integer>();
    index = 0;
    dfs(nestedList, result);
}

@Override
public Integer next() {
    return result.get(index++); // use queue.remove
}

@Override
public boolean hasNext() {
    return index != result.size();
}

private void dfs(List<NestedInteger> list, List<Integer> result) {
    for(NestedInteger tmp : list) {
        if(tmp.isInteger()) result.add(tmp.getInteger());
        else dfs(tmp.getList(), result);
    }
}
http://www.jiuzhang.com/solutions/flatten-nested-iterate/
 * @author het
 *
 */
public class LeetCode341 {
	public class NestedIterator implements Iterator<Integer> {
		List<Integer> result;
		int index;
		public NestedIterator(List<NestedInteger> nestedList) {
		    result = new ArrayList<Integer>();
		    index = 0;
		    dfs(nestedList, result);
		}

		@Override
		public Integer next() {
		    return result.get(index++); // use queue.remove
		}

		@Override
		public boolean hasNext() {
		    return index != result.size();
		}

		private void dfs(List<NestedInteger> list, List<Integer> result) {
		    for(NestedInteger tmp : list) {
		        if(tmp.isInteger()) result.add(tmp.getInteger());
		        else dfs(tmp.getList(), result);
		    }
		}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	
	//hasNext should be idempotent and optional (so it can be called several times before each next or not at all, and next should still work properly).
	public class NestedIterator implements Iterator<Integer> {
	    Stack<Iterator<NestedInteger>> stack = new Stack<>();
	    Integer current = null;

	    public NestedIterator(List<NestedInteger> nestedList) {
	        if (nestedList != null) {
	            stack.push(nestedList.iterator());
	        }
	    }

	    @Override
	    public Integer next() {
	        hasNext();

	        Integer value = current;
	        current = null;
	        return value;
	    }

	    @Override
	    public boolean hasNext() {
	        while (current == null && !stack.isEmpty()) {
	            Iterator<NestedInteger> node = stack.peek();
	            if (!node.hasNext()) {
	                stack.pop();
	                continue;
	            }

	            NestedInteger value = node.next();
	            if (value.isInteger()) {
	                current = value.getInteger();
	                return true;
	            } else {
	                stack.push(value.getList().iterator());
	            }
	        }

	        return false;
	    }
	}
	
	
	
	
	
	public class NestedIterator implements Iterator<Integer> {
	    Stack<Iterator<NestedInteger>> stack = new Stack<>();
	    Integer current = null;

	    public NestedIterator(List<NestedInteger> nestedList) {
	        if (nestedList != null) {
	            stack.push(nestedList.iterator());
	        }
	    }

	    @Override
	    public Integer next() {
	        hasNext();

	        Integer value = current;
	        current = null;
	        return value;
	    }

	    @Override
	    public boolean hasNext() {
	        while (current == null && !stack.isEmpty()) {
	            Iterator<NestedInteger> node = stack.peek();
	            if (!node.hasNext()) {
	                stack.pop();
	                continue;
	            }

	            NestedInteger value = node.next();
	            if (value.isInteger()) {
	                current = value.getInteger();
	                return true;
	            } else {
	                stack.push(value.getList().iterator());
	            }
	        }

	        return false;
	    }
	}
	
	
	
//	Using Stack:
//		https://leetcode.com/discuss/95937/simple-iterative-dfs-using-stack
//		hasNext should be idempotent and optional (so it can be called several times before each next or not at all, and next should still work properly).
//		public class NestedIterator implements Iterator<Integer> {
//		    Stack<Iterator<NestedInteger>> stack = new Stack<>();
//		    Integer current = null;
//
//		    public NestedIterator(List<NestedInteger> nestedList) {
//		        if (nestedList != null) {
//		            stack.push(nestedList.iterator());
//		        }
//		    }
//
//		    @Override
//		    public Integer next() {
//		        hasNext();
//
//		        Integer value = current;
//		        current = null;
//		        return value;
//		    }
//
//		    @Override
//		    public boolean hasNext() {
//		        while (current == null && !stack.isEmpty()) {
//		            Iterator<NestedInteger> node = stack.peek();
//		            if (!node.hasNext()) {
//		                stack.pop();
//		                continue;
//		            }
//
//		            NestedInteger value = node.next();
//		            if (value.isInteger()) {
//		                current = value.getInteger();
//		                return true;
//		            } else {
//		                stack.push(value.getList().iterator());
//		            }
//		        }
//
//		        return false;
//		    }
//		}

}
