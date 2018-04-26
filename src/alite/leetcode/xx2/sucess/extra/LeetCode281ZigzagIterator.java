package alite.leetcode.xx2.sucess.extra;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Given two 1d vectors, implement an iterator to return their elements alternately.
For example, given two 1d vectors:
v1 = [1, 2]
v2 = [3, 4, 5, 6]
By calling next repeatedly until hasNext returns false, the order of elements returned by next should be:[1, 3, 2, 4, 5, 6].
Follow up: What if you are given k 1d vectors? How well can your code be extended to such cases?
Clarification for the follow up question - Update (2015-09-18):
The "Zigzag" order is not clearly defined and is ambiguous for k > 2 cases. If "Zigzag" does not look right to you, replace "Zigzag" with "Cyclic". For example, given the following input:
[1,2,3]
[4,5,6,7]
[8,9]
It should return [1,4,8,2,5,9,3,6,7].
X.
https://discuss.leetcode.com/topic/24231/short-java-o-1-space
Two iterators, one for each list. Switching them before reading the next number instead of afterwards saves a bit of code, I think.
    private Iterator<Integer> i, j, tmp;

    public ZigzagIterator(List<Integer> v1, List<Integer> v2) {
        i = v2.iterator();
        j = v1.iterator();
    }

    public int next() {
        if (j.hasNext()) { tmp = j; j = i; i = tmp; }
        return i.next();
    }

    public boolean hasNext() {
        return i.hasNext() || j.hasNext();
    }
http://www.cnblogs.com/airwindow/p/4805995.html
    Iterator<Integer> cur_iterator;
    Iterator<Integer> iterator1;
    Iterator<Integer> iterator2;
    
    public ZigzagIterator(List<Integer> v1, List<Integer> v2) {
        this.iterator1 = v1.iterator();
        this.iterator2 = v2.iterator();
        this.cur_iterator = (this.iterator1.hasNext() ? this.iterator1 : this.iterator2);
    }

    public int next() {
        int ret = cur_iterator.next();
        if (cur_iterator == iterator1) {
            if (iterator2.hasNext()) {
                cur_iterator = iterator2;
            }
        } else{
            if (iterator1.hasNext()) {
                cur_iterator = iterator1;
            }
        }
        return ret;
    }

    public boolean hasNext() {
        return cur_iterator.hasNext();
    }
X.
https://discuss.leetcode.com/topic/30615/clean-java-solution-works-for-k-lists
    List<Iterator<Integer>> itrs;
    int idx;

    public ZigzagIterator(List<Integer> v1, List<Integer> v2) {
        itrs = new ArrayList<Iterator<Integer>>();
        itrs.add(v1.iterator());
        itrs.add(v2.iterator());
     idx = 0;
    }

    public int next() {
     hasNext();
        int val = itrs.get(idx).next();
      idx = (idx + 1) % itrs.size();
        return val;
    }

    public boolean hasNext() {
     if(itrs.size()==0)
         return false;
     else if(itrs.get(idx).hasNext())
         return true;
     else {
            do {
             itrs.remove(idx);
             if(itrs.size()==0)
              return false;
             idx = (idx+1)%itrs.size();
         } while(!itrs.get(idx).hasNext());
         return true;
     }
    }
https://discuss.leetcode.com/topic/24193/java-o-n-solution-for-k-vector
    Iterator<Integer>[] its;
    int pos;

    public ZigzagIterator(List<Integer> v1, List<Integer> v2) {
        its = new Iterator[]{v1.iterator(), v2.iterator()};
        pos = 0;
    }

    public int next() {
        int next = its[pos].next();
        pos = (pos == its.length - 1) ? 0 : pos + 1;
        return next;
    }

    public boolean hasNext() {
        if (its[pos].hasNext()) return true;
        for (int i = pos + 1; i < its.length; i++) {
            if (its[i].hasNext()) {
                pos = i;
                return true;
            }
        }
        for (int i = 0; i < pos; i++) {
            if (its[i].hasNext()) {
                pos = i;
                return true;
            }
        }
        return false;
    }
https://discuss.leetcode.com/topic/26654/simple-java-solution-for-k-vector
Uses a linkedlist to store the iterators in different vectors. Every time we call next(), we pop an element from the list, and re-add it to the end to cycle through the lists.
    LinkedList<Iterator> list;
    public ZigzagIterator(List<Integer> v1, List<Integer> v2) {
        list = new LinkedList<Iterator>();
        if(!v1.isEmpty()) list.add(v1.iterator());
        if(!v2.isEmpty()) list.add(v2.iterator());
    }

    public int next() {
        Iterator poll = list.remove();
        int result = (Integer)poll.next();
        if(poll.hasNext()) list.add(poll);
        return result;
    }

    public boolean hasNext() {
        return !list.isEmpty();
    }
https://discuss.leetcode.com/topic/24201/java-clean-solution-for-k-lists

https://tenderleo.gitbooks.io/leetcode-solutions-/content/GoogleMedium/281.html
     List<List<Integer>> lists = new ArrayList<>();                                 
      int p = 0; // which list                 
      int q = 0; // current index in current list;                                   
      int[] indices = new int[2];                                    
      public ZigzagIterator(List<Integer> v1, List<Integer> v2) {                    
       lists.add(v1);   
       lists.add(v2);                  
       indices[0] = indices[1] = 0;                    
      }                                                                              

      public int next() {                                                            
          int val = lists.get(p).get(q);                                             
          indices[p] = q+1;                                                          
          p = 1-p;                                                                   
          q = indices[p];                                                            
          return val;                                                                
      }                                                                              

      public boolean hasNext() {                                                     
          if(lists.get(p).size() > indices[p]) return true;                          
          else{                                                                      
              p = 1-p;                                                               
              q = indices[p];                                                        
              return lists.get(p).size() > q;                                        
          }                                                                          
      }  

Zigzag Iterator | tech::interview
Suppose you have a Iterator class with has_next() and get_next() methods.
Please design and implement a ZigzagIterator class as a wrapper of two iterators.
For example, given two iterators:
i0 = [1,2,3,4]
i1 = [5,6]
ZigzagIterator it(i0, i1);

while(it.has_next()) {
    print(it.get_next());
}
The output of the above pseudocode would be [1,5,2,6,3,4].
写一个变形的iterator，给定两个iterator，让两个iterator进行交互输出。
例子：
A: 1234
B: abcd
则输出为：1a2b3c4d，如果一个读完了那就读没读完那个直到两个都读完为止。
http://shibaili.blogspot.com/2015/08/notes-from-others.html

class ZigZagIterator() {

    ZigZagIterator(vector<Iterator> &itrs) {

        this->itrs = itrs;

        pointer = 0;

        if (!itrs[pointer].hasNext()) setNextAvailable();

    }  


    void setNextAvailable() {

        int old = pointer;

        while (true) {

            pointer++;

            pointer %= itrs.size();

            if (itrs[pointer].hasNext()) {

                return;    

            }     

            if (pointer == old) {

                pointer = -1;

                return;

            }

        }

    }


    bool hasNext() {

        return pointer == -1;    

    }

     

    int next() {

        int ret = itrs[pointer];

        setNextAvailable();

        return ret;

    }

     

private:

    vector<Iterator> itrs;

    int pointer;

};



class ZigzagIterator {


public:


ZigzagIterator(Iterator& a, Iterator& b) :_its{a,b} {


_pointer = a.has_next() ? 0 : 1;


}




int get_next() {


int ret = _its[_pointer].get_next(), old = _pointer;


do {


if(++_pointer >= 2) _pointer = 0;


} while(!_its[_pointer].has_next() && _pointer != old);


return ret;


}




bool has_next() { return _its[_pointer].has_next(); }


private:


Iterator _its[2];


int _pointer;


};

http://yuanhsh.iteye.com/blog/2228675
public class ZigzagIterator {  
    Iterator i0, i1;  
    Iterator it;  
    public ZigzagIterator(Iterator i0, Iterator i1) {  
        this.i0 = i0; this.i1 = i1;  
        this.it = i0.hasNext()? i0:i1;  
    }  
      
    public boolean has_next() {  
        return it.hasNext();  
    }  
      
    public int get_next() {  
        int val = (Integer)it.next();  
        if(it == i0 && i1.hasNext())  
            it = i1;  
        else if(it == i1 && i0.hasNext())  
            it = i0;  
        return val;  
    }  
}  
第二种解法，这个方便解有多个Iterator的情况，也就是followup的问题：
class ZigzagIterator {  
private:  
    vector<Iterator> vec;  
    vector<Iterator>::iterator it;  
public:  
    ZigzagIterator(Iterator& i0, Iterator& i1) {  
        vec = {i0, i1};  
        it = vec.begin();  
        while(!(*it).has_next()) it++;  
    }  
  
    bool has_next() {  
        return (*it).has_next();  
    }  
      
    int get_next() {  
        auto prev = it;  
        int val = (*it).get_next();  
        do {  
            if(++it == vec.end())  
                it = vec.begin();  
        } while(!(*it).has_next() && it != prev);  
        return val;  
    }  
};  
http://segmentfault.com/a/1190000003786218
该题实际上就是轮流取两个列表的下一个元素。我们存下两个列表的迭代器，然后用一个递增的turns变量和取模的方法来判断该取哪一个列表的元素。
public class ZigzagIterator {
    
    Iterator<Integer> it1;
    Iterator<Integer> it2;
    int turns;

    public ZigzagIterator(List<Integer> v1, List<Integer> v2) {
        this.it1 = v1.iterator();
        this.it2 = v2.iterator();
        turns = 0;
    }

    public int next() {
        // 如果没有下一个则返回0
        if(!hasNext()){
            return 0;
        }
        turns++;
        // 如果是第奇数个，且第一个列表也有下一个元素时，返回第一个列表的下一个
        // 如果第二个列表已经没有，返回第一个列表的下一个
        if((turns % 2 == 1 && it1.hasNext()) || (!it2.hasNext())){
            return it1.next();
        // 如果是第偶数个，且第二个列表也有下一个元素时，返回第二个列表的下一个
        // 如果第一个列表已经没有，返回第二个列表的下一个
        } else if((turns % 2 == 0 && it2.hasNext()) || (!it1.hasNext())){
            return it2.next();
        }
        return 0;
    }

    public boolean hasNext() {
        return it1.hasNext() || it2.hasNext();
    }
}
Q：如果输入是k个列表呢？
A：使用一个迭代器的列表来管理这些迭代器。用turns变量和取模来判断我们该取列表中的第几个迭代器。不同点在于，一个迭代器用完后，我们要将其从列表中移出，这样我们下次就不用再找这个空的迭代器了。同样，由于每用完一个迭代器后都要移出一个，turns变量也要相应的更新为该迭代器下标的上一个下标。如果迭代器列表为空，说明没有下一个了。
public class ZigzagIterator implements Iterator<Integer> {
    
    List<Iterator<Integer>> itlist;
    int turns;

    public ZigzagIterator(List<Iterator<Integer>> list) {
        this.itlist = new LinkedList<Iterator<Integer>>(); // use ArrayList
        // 将非空迭代器加入列表
        for(Iterator<Integer> it : list){
            if(it.hasNext()){
                itlist.add(it);
            }
        }
        turns = 0;
    }

    public Integer next() {
        if(!hasNext()){
            return 0;
        }
        Integer res = 0;
        // 算出本次使用的迭代器的下标
        int pos = turns % itlist.size();
        Iterator<Integer> curr = itlist.get(pos);
        res = curr.next();
        // 如果这个迭代器用完，就将其从列表中移出
        if(!curr.hasNext()){
            itlist.remove(turns % itlist.size());
            // turns变量更新为上一个下标
            turns = pos - 1;
        }
        turns++;
        return res;
    }

    public boolean hasNext() {
        return itlist.size() > 0;
    }
}
http://blog.csdn.net/xudli/article/details/48749219

http://www.codescream.com/ContentDisplay?targetContent=RoundRobinIterator
The three biggest things to watch out for are:
Making next() and hasNext() work together - hasNext() must be able to locate the next element to know one exists, and next() should defer to hasNext() to ensure that a next element does exist before trying to return one.
Ensuring that you correctly reset the iterator which moves over the sub-list iterators each time it reaches the end of the iterator list. You will move over the list of iterators many times as you are only taking one item from each sub-list on each pass.
Remove sub-lists using the Iterator.remove() method once you determine they have no elements left. This lets you track your "finished" condition easily; you are finished once no iterators remain in your list of iterators.
    //Define the iterator position in the overall list of iterators
    //and the list of iterators itself.
    private Iterator<Iterator<Integer>> iter;
    private List<Iterator<Integer>> iterators;

    //The next value to be returned.  If null, the next value has
    //not been located yet.
    private Integer nextValue;

    public RoundRobinIterator(List<Iterator<Integer>> iterators) {

        //Gets an iterator over a list of iterators.
        iter = iterators.iterator();
        this.nextValue = null;
        this.iterators = iterators;
    }

    @Override
    public Integer next() {
        if (!hasNext())
            throw new NoSuchElementException();
        Integer n = nextValue;
        nextValue = null;
        return n;
    }

    @Override
    public boolean hasNext() {
        return nextValue != null || setNext();
    }

    private boolean setNext() {

        //If we've already found the next element, do nothing.
        if (nextValue != null) return true;

        //Loop until we determine the next element or that no elements remain.
        while (true) {

            //If we're at the end of the list of iterators, restart at the beginning, assuming
            //any of the contained lists have remaining elements.
            if (!iter.hasNext()) {
                if (!iterators.isEmpty()) iter = iterators.iterator();
                else return false;
            }

            //Get the next iterator from the list of iterators, assuming we're
            //not at the last one already.
            if (iter.hasNext()) {
                Iterator<Integer> currentIter = iter.next();

                //If the iterator we are positioned at has more elements left in its
                //sub-list, then take the next element and return it.  If no elements remain
                //then remove the iterator from the round-robin iterator list for good.
                if (currentIter.hasNext()) {
                    nextValue = currentIter.next();
                    return true;
                }
                else {
                    iter.remove();
                }
            }
        }
    }
Related: http://buttercola.blogspot.com/2015/11/zenefits-zigzag-iterator-column-major.html

  public static class ZigZagIterator {

    private List<Iterator<Integer>> iterators;

    private Queue<Iterator<Integer>> queue;

     

    public ZigZagIterator(List<Iterator<Integer>> iterators) {

      this.iterators = iterators;

      queue = new LinkedList<>();

       

      for (Iterator<Integer> iterator : iterators) {

        if (iterator.hasNext()) {

          queue.add(iterator);

        }

      }

       

    }

     

    public boolean hasNext() {

      return !queue.isEmpty();

    }

     

    public Integer next() {

      Iterator<Integer> currIterator = queue.poll();

      Integer result = currIterator.next();

      if (currIterator.hasNext()) {

        queue.offer(currIterator);

      }

       

      return result;

    }

  }
Read full article from Zigzag Iterator | tech::interview
 * @author het
 *
 */
public class LeetCode281ZigzagIterator {
	 List<Iterator<Integer>> itrs;
	    int idx;

	    public LeetCode281ZigzagIterator(List<Integer> v1, List<Integer> v2) {
	        itrs = new ArrayList<Iterator<Integer>>();
	        itrs.add(v1.iterator());
	        itrs.add(v2.iterator());
	     idx = 0;
	    }

	    public int next() {
	     hasNext();
	        int val = itrs.get(idx).next();
	      idx = (idx + 1) % itrs.size();
	        return val;
	    }

	    public boolean hasNext() {
	     if(itrs.size()==0)
	         return false;
	     else if(itrs.get(idx).hasNext())
	         return true;
	     else {
	            do {
	             itrs.remove(idx);
	             if(itrs.size()==0)
	              return false;
	             idx = (idx+1)%itrs.size();  //   idx = (idx+1)%itrs.size();   //   idx = (idx+1)%itrs.size();
	         } while(!itrs.get(idx).hasNext());
	         return true;
	     }
	    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
