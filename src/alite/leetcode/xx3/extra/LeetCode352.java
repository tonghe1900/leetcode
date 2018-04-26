package alite.leetcode.xx3.extra;
///**
// * LeetCode 352 - Data Stream as Disjoint Intervals
//
//http://www.cnblogs.com/grandyang/p/5548284.html
//Given a data stream input of non-negative integers a1, a2, ..., an, ..., summarize the numbers seen so far as a list of disjoint intervals.
//For example, suppose the integers from the data stream are 1, 3, 7, 2, 6, ..., then the summary will be:
//[1, 1]
//[1, 1], [3, 3]
//[1, 1], [3, 3], [7, 7]
//[1, 3], [7, 7]
//[1, 3], [6, 7]
//Follow up:
//What if there are lots of merges and the number of disjoint intervals are small compared to the data stream's size?
//* public class Interval {
//*     int start;
//*     int end;
//*     Interval() { start = 0; end = 0; }
//*     Interval(int s, int e) { start = s; end = e; }
//* }
//这道题说有个数据流每次提供一个数字，然后让我们组成一系列分离的区间，这道题跟之前那道Insert Interval很像，思路也很像，
//每进来一个新的数字val，我们都生成一个新的区间[val, val]，然后将其插入到当前的区间里，注意分情况讨论，无重叠，相邻，和有重叠分开讨论处理
//
//
//https://leetcode.com/discuss/105756/java-solution-using-treemap-real-o-logn-per-adding
//Use TreeMap to easily find the lower and higher keys, the key is the start of the interval. 
//Merge the lower and higher intervals when necessary. The time complexity for adding is O(logN) since lowerKey(),
// higherKey(), put() and remove() are all O(logN). It would be O(N) if you use an ArrayList and remove an interval from it.
//    TreeMap<Integer, Interval> tree;
//    public SummaryRanges() {
//        tree = new TreeMap<>();
//    }
//    public void addNum(int val) {
//        if(tree.containsKey(val)) return;
//        Integer l = tree.lowerKey(val);
//        Integer h = tree.higherKey(val);
//        if(l != null && h != null && tree.get(l).end + 1 == val && h == val + 1) {
//            tree.get(l).end = tree.get(h).end;
//            tree.remove(h);
//        } else if(l != null && tree.get(l).end + 1 >= val) {
//            tree.get(l).end = Math.max(tree.get(l).end, val);
//        } else if(h != null && h == val + 1) {
//            tree.put(val, new Interval(val, tree.get(h).end));
//            tree.remove(h);
//        } else {
//            tree.put(val, new Interval(val, val));
//        }
//    }
//
//    public List<Interval> getIntervals() {
//        return new ArrayList<>(tree.values());
//    }
//http://www.guoting.org/leetcode/leetcode-352-data-stream-as-disjoint-intervals/
//http://www.allenlipeng47.com/blog/index.php/2016/06/13/data-stream-as-disjoint-intervals/
//https://discuss.leetcode.com/topic/46887/java-solution-using-treemap-real-o-logn-per-adding
//这题用Maptree会通过map.lowerKey, map.higherKey很快定位到当前数所处在哪两个interval之间，从而进行高效的比较与合并。
//Use TreeMap and put start as key, (start, end) as value in TreeMap. Basically, ; there will be 3 conditions when adding a new value: add 4 to (5, 6), (8, 9); add 6 to (5, 5); add 4 to (5, 5):
//add_middle              add_left              add_right
//Besides, there are 2 exceptions: add 6 to (5, 7); add 6 to (6, 6):
//exception1       exception2
//https://github.com/allenlipeng47/algorithm/blob/master/src/main/java/com/pli/project/algorithm/leetcode/SummaryRanges.java
//public class SummaryRanges {
//    TreeMap<Integer, Interval> tree;
//
//    public SummaryRanges() {
//        tree = new TreeMap<>();
//    }
//
//    public void addNum(int val) {
//        if(tree.containsKey(val)) return;
//        Integer l = tree.lowerKey(val);
//        Integer h = tree.higherKey(val);
//        if(l != null && h != null && tree.get(l).end + 1 == val && h == val + 1) {
//            tree.get(l).end = tree.get(h).end;
//            tree.remove(h);
//        } else if(l != null && tree.get(l).end + 1 >= val) {
//            tree.get(l).end = Math.max(tree.get(l).end, val);
//        } else if(h != null && h == val + 1) {
//            tree.put(val, new Interval(val, tree.get(h).end));
//            tree.remove(h);
//        } else {
//            tree.put(val, new Interval(val, val));
//        }
//    }
//
//    public List<Interval> getIntervals() {
//        return new ArrayList<>(tree.values());
//    }
//}
//http://dartmooryao.blogspot.com/2016/06/leetcode-352-data-stream-as-disjoint.html
//(1) Use TreeMap to store the intervals. The key is the start of interval, value is the Interval object.
//(2) For each addNum, we first create a Interval object using the val as start and end.
//(3) Get the floorKey from the TreeMap, if the prev Interval is connected to this input value, update the current interval by smaller start, and larger end. Also update the key.
//(4) Get the ceilingKey from the TreeMap, if there is overlap, remove the interval from the map. Then update the current interval's end.
//(5) Put the current interval into the map.
//
//    TreeMap<Integer, Interval> map;
//
//    /** Initialize your data structure here. */
//    public SummaryRanges() {
//        map = new TreeMap<Integer, Interval>();
//    }
//  
//    public void addNum(int val) {
//        Integer prevKey = map.floorKey(val);
//        int thisKey = val;
//        Interval thisInt = new Interval(val, val);
//        if(prevKey!=null && map.get(prevKey).end+1 >= val){
//            thisKey = map.get(prevKey).start;
//            thisInt.start = Math.min(map.get(prevKey).start, val);
//            thisInt.end = Math.max(map.get(prevKey).end, val);
//        }
//      
//        Integer nextKey = map.ceilingKey(val);
//        if(nextKey!=null && nextKey-1==val){
//            thisInt.end = map.get(nextKey).end;
//            map.remove(nextKey);
//        }
//      
//        map.put(thisKey, thisInt);
//    }
//  
//    public List<Interval> getIntervals() {
//        return new ArrayList(map.values());
//    }
//X.
//http://bookshadow.com/weblog/2016/05/31/leetcode-data-stream-as-disjoint-intervals/
//如果合并次数非常多，与数据流的规模相比不相交区间的数目很少，应当做怎样的优化？
//
//解题思路：
//利用TreeSet数据结构，将不相交区间Interval存储在TreeSet中。
//
//TreeSet底层使用红黑树实现，可以用log(n)的代价实现元素查找。
//
//每次执行addNum操作时，利用TreeSet找出插入元素val的左近邻元素floor（start值不大于val的最大Interval），以及右近邻元素higher（start值严格大于val的最小Interval）
//
//然后根据floor, val, higher之间的关系决定是否对三者进行合并。
//如果合并次数非常多，与数据流的规模相比不相交区间的数目很少，应当做怎样的优化？
//
//解题思路：
//利用TreeSet数据结构，将不相交区间Interval存储在TreeSet中。
//
//TreeSet底层使用红黑树实现，可以用log(n)的代价实现元素查找。
//
//每次执行addNum操作时，利用TreeSet找出插入元素val的左近邻元素floor（start值不大于val的最大Interval），以及右近邻元素higher（start值严格大于val的最小Interval）
//
//然后根据floor, val, higher之间的关系决定是否对三者进行合并。
//public class SummaryRanges {
//
//    /** Initialize your data structure here. */
//    
//    private TreeSet<Interval> intervalSet; 
//    
//    public SummaryRanges() {
//        intervalSet = new TreeSet<Interval>(new Comparator<Interval>() {
//            public int compare(Interval a, Interval b) {
//                return a.start - b.start;
//            }
//        });
//    }
//    
//    public void addNum(int val) {
//        Interval valInterval = new Interval(val, val);
//        Interval floor = intervalSet.floor(valInterval);
//        if (floor != null) {
//            if (floor.end >= val) {
//                return;
//            } else if (floor.end + 1 == val) {
//                valInterval.start = floor.start;
//                intervalSet.remove(floor);
//            }
//        }
//        Interval higher = intervalSet.higher(valInterval);
//        if (higher != null && higher.start == val + 1) {
//            valInterval.end = higher.end;
//            intervalSet.remove(higher);
//        }
//        intervalSet.add(valInterval);
//    }
//    
//    public List<Interval> getIntervals() {
//        return Arrays.asList(intervalSet.toArray(new Interval[0]));
//    }
//}
//X. http://www.mobile-open.com/2016/961231.html
//建立一棵二叉搜索树，树的节点值用interval表示，其实就是类似于“区间树”节点值是个区间，这些区间不相交。建立树之后中序遍历输出结果。
//插入的过程较为麻烦，插入需要考虑一个节点的两端点，情况较多，具体见代码注释。
//X.
//http://blog.csdn.net/qq508618087/article/details/51553166
//思路: 用一个数组来保存结果, 然后每次搜索的时候用lower_bound可以找到第一个大于等于要插入的值的区间, 当前要插入的值可能会正好等于上个区间的end+1, 因此需要做个判断. 把能合并的区间都从数组中删除, 最后插入一个新的区间.
//关于follow-up, 如果合并的操作非常多但是最后区间可能很少, 这种情况下不适合直接用vector, 因为他的合并操作需要移位, 故时间复杂度是O(n), 频繁的移位会造成时间复杂度大大增加. 一个更好的解决办法是使用set, 因为二叉搜索树插入查找都可以在O(log n)完成, 只需要在返回结果的时候将set中的值放到vector中即可.
//
//http://dartmooryao.blogspot.com/2016/06/leetcode-352-data-stream-as-disjoint.html
//This time I do it using a binary search.
//(1) The Idea is to find the first interval in the list that's larger than the input value, then check the higer interval and lower interval to merge them.
//(2) Here is a trick to make the code clean: We create an interval from the input first. Then if we find an overlap, we delete the interval from the list!! Do it separately for the higher interval and lower interval!
//(3) The time complexity here is actually O(N) for addNum and O(1) for getIntervals. Why? For the addNum, although we use a binary search, the time complexity to delete a interval from arrayList is O(N).
//    List<Interval> list = new ArrayList<>();
//
//    /** Initialize your data structure here. */
//    public SummaryRanges() {
//       
//    }
//   
//    public void addNum(int val) {
//        int idx = findFirstLarger(val);
//        Interval newInt = new Interval(val, val);
//        if(idx>0 && list.get(idx-1).end+1 >= val){
//            Interval before = list.remove(idx-1);
//            newInt.start = Math.min(before.start, newInt.start);
//            newInt.end = Math.max(before.end, newInt.end);
//            idx--;
//        }
//
//        if(idx<list.size() && list.get(idx).start-1 <= val){
//            Interval after = list.remove(idx);
//            newInt.start = Math.min(after.start, newInt.start);
//            newInt.end = Math.max(after.end, newInt.end);
//        }
//       
//        list.add(idx, newInt);
//    }
//   
//    public List<Interval> getIntervals() {
//        return list;
//    }
//   
//    private int findFirstLarger(int val){
//        int start = 0, end = list.size();
//        while(start<end){
//            int mid = start+(end-start)/2;
//            if(list.get(mid).start<val){
//                start = mid+1;
//            }else{
//                end = mid;
//            }
//        }
//        return start;
//    }
//
//https://leetcode.com/discuss/105742/simple-java-solution-using-insert-interval
//Basic idea is to use the method of 57. Insert interval. What we have to change here is to add some code to merge intervals with distance 1.
//
//public class SummaryRanges {
//
//    /** Initialize your data structure here. */
//    List<Interval> summary;
//    public SummaryRanges() {
//        summary = new ArrayList<>();
//    }
//
//    public void addNum(int val) {
//        Interval cur = new Interval(val,val);
//        List<Interval> rst = new ArrayList<Interval>();
//        int pos = 0;
//        for(Interval interval : summary){
//            //non Overlap
//            if(cur.end + 1 < interval.start) {
//                rst.add(interval);
//                continue;
//            }
//            if(cur.start > interval.end + 1){
//                rst.add(interval);
//                pos++;
//                continue;
//            }
//            //Overlap // where is the remove?
//            if(cur.start - 1 == interval.end) cur.start = interval.start;
//            else if(cur.end + 1 == interval.start) cur.end = interval.end;
//            //Merge
//            else{
//                cur.start = Math.min(cur.start, interval.start);
//                cur.end = Math.max(cur.end, interval.end);
//            }
//        }
//        rst.add(pos, cur);
//        summary = new ArrayList<Interval>(rst);
//    }
//
//    public List<Interval> getIntervals() {
//        return summary;
//    }
//}
//Returns the greatest key strictly less than the given key, or null if there is no such key.
//
//    public K lowerKey(K key) 
//Returns the least key strictly greater than the given key, or null if there is no such key.
//    public K higherKey(K key)
//
//https://discuss.leetcode.com/topic/47084/java-fast-log-n-solution-186ms-without-using-the-treemap-but-a-customized-bst
//Java fast log (N) solution (186ms) without using the TreeMap but a customized BST
//    class BSTNode {
//        Interval interval;
//        BSTNode left;
//        BSTNode right;
//        BSTNode(Interval in){
//            interval = in;
//        }
//    }
//    
//    BSTNode findMin(BSTNode root) {
//        if (root == null) return null;
//        if (root.left == null ) return root;
//        else return findMin(root.left);
//    }
//    
//    BSTNode remove(Interval x, BSTNode root) {
//        if (root == null) return null;
//        else if ( x == null ) return root;
//        else if (x.start > root.interval.end ) {
//            root.right = remove(x, root.right);
//        } else if (x.end < root.interval.start ) {
//            root.left = remove(x, root.left);
//        } else if ( root.left != null && root.right != null) {
//            root.interval = findMin(root.right).interval;
//            root.right = remove( root.interval, root.right);
//        } else {
//            root = ( root.left != null ) ? root.left : root.right;
//        }
//        return root;
//    }
//    
//    BSTNode findKey(int val, BSTNode root) {
//        if (root == null) return null;
//        if (root.interval.start > val) {
//            return findKey(val, root.left);
//        } else if (root.interval.end < val) {
//            return findKey(val, root.right);
//        } else return root;
//    }
//    
//    BSTNode addKey(int val, BSTNode root) {
//        if (root == null) {
//            root = new BSTNode( new Interval(val, val) ); 
//        } else if (root.interval.start > val) {
//            root.left = addKey(val, root.left);
//        } else if (root.interval.end < val) {
//            root.right = addKey(val, root.right);
//        }  
//        return root;
//    }
//    void inOrder(BSTNode root) {
//        if (root != null) {
//            inOrder(root.left);
//            list.add(root.interval);
//            inOrder(root.right);
//        }
//    }
//    
//    /** Initialize your data structure here. */
//    BSTNode root;
//    List<Interval> list = new ArrayList();
//    public SummaryRanges() {
//        root = null;
//    }
//    
//    public void addNum(int val) {
//        if (root == null) {
//            root = addKey(val, root);
//        } else {
//            if ( findKey(val, root) != null) return;
//            BSTNode left = findKey(val-1, root);
//            BSTNode right = findKey(val+1, root);
//            if (left == null && right == null) {
//                root = addKey(val, root);
//            } else if (left != null && right == null) {
//                left.interval.end++;
//            } else if (left == null && right != null) {
//                right.interval.start--;
//            } else {
//                Interval l = left.interval;
//                int e = right.interval.end;
//                root = remove(right.interval, root);
//                l.end = e;
//            }
//        }
//    }
//    
//    public List<Interval> getIntervals() {
//        list.clear();
//        inOrder(root);
//        return list;
//    }
//}
// * @author het
// *
// */
public class LeetCode352 {
//	Use TreeMap to easily find the lower and higher keys, the key is the start of the interval. 
//	Merge the lower and higher intervals when necessary. The time complexity for adding is O(logN) since lowerKey(),
//	 higherKey(), put() and remove() are all O(logN). It would be O(N) if you use an ArrayList and remove an interval from it.
	    TreeMap<Integer, Interval> tree;
	    public SummaryRanges() {
	        tree = new TreeMap<>();
	    }
	    public void addNum(int val) {
	        if(tree.containsKey(val)) return;
	        Integer l = tree.lowerKey(val);
	        Integer h = tree.higherKey(val);
	        if(l != null && h != null && tree.get(l).end + 1 == val && h == val + 1) {
	            tree.get(l).end = tree.get(h).end;
	            tree.remove(h);
	        } else if(l != null && tree.get(l).end + 1 >= val) {
	            tree.get(l).end = Math.max(tree.get(l).end, val);
	        } else if(h != null && h == val + 1) {
	            tree.put(val, new Interval(val, tree.get(h).end));
	            tree.remove(h);
	        } else {
	            tree.put(val, new Interval(val, val));
	        }
	    }

	    public List<Interval> getIntervals() {
	        return new ArrayList<>(tree.values());
	    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
