package alite.leetcode.xx3.extra;
/**
 * LeetCode 380 - Insert Delete GetRandom O(1)

http://www.cnblogs.com/grandyang/p/5740864.html
Design a data structure that supports all following operations in average O(1) time.

insert(val): Inserts an item val to the set if not already present.
remove(val): Removes an item val from the set if present.
getRandom: Returns a random element from current set of elements. Each element must have the same probability of being returned.

Example:
// Init an empty set.
RandomizedSet randomSet = new RandomizedSet();

// Inserts 1 to the set. Returns true as 1 was inserted successfully.
randomSet.insert(1);

// Returns false as 2 does not exist in the set.
randomSet.remove(2);

// Inserts 2 to the set, returns true. Set now contains [1,2].
randomSet.insert(2);

// getRandom should return either 1 or 2 randomly.
randomSet.getRandom();

// Removes 1 from the set, returns true. Set now contains [2].
randomSet.remove(1);

// 2 was already in the set, so return false.
randomSet.insert(2);

// Since 1 is the only number in the set, getRandom always return 1.
randomSet.getRandom();

http://www.cnblogs.com/grandyang/p/5740864.html
此题的正确解法是利用到了一个一维数组和一个哈希表，其中数组用来保存数字，哈希表用来建立每个数字和其在数组中的位置之间的映射，对于插入操作，我们先看这个数字是否已经在哈希表中存在，如果存在的话直接返回false，不存在的话，我们将其插入到数组的末尾，然后建立数字和其位置的映射。删除操作是比较tricky的，我们还是要先判断其是否在哈希表里，如果没有，直接返回false。由于哈希表的删除是常数时间的，而数组并不是，为了使数组删除也能常数级，我们实际上将要删除的数字和数组的最后一个数字调换个位置，然后修改对应的哈希表中的值，这样我们只需要删除数组的最后一个元素即可，保证了常数时间内的删除。而返回随机数对于数组来说就很简单了，我们只要随机生成一个位置，返回该位置上的数字即可

http://www.jianshu.com/p/a9c2987cd51d
https://discuss.leetcode.com/topic/53323/java-hashmap-and-list/4
ArrayList 的本质是 Array,
so list.remove(list.size() - 1) -> time complexity is only O(1)
public class RandomizedSet {
    private HashMap<Integer, Integer> map; // value -> index
    private ArrayList<Integer> list; // value
    private Random rand;
    /** Initialize your data structure here. */
    public RandomizedSet() {
        map = new HashMap<Integer, Integer>();
        list = new ArrayList<Integer>();
        rand = new Random();
    }

    /** Inserts a value to the set. Returns true if the set did not already contain the specified element. */
    public boolean insert(int val) {
        if (map.containsKey(val)) {
            return false;
        }
        else {
            map.put(val, list.size());
            list.add(val);
            return true;
        }
    }

    /** Removes a value from the set. Returns true if the set contained the specified element. */
    public boolean remove(int val) {
        if (!map.containsKey(val)) {
            return false;
        }
        else {
            int index = map.remove(val);
            int last = list.remove(list.size() - 1);
            if (last != val) {
                list.set(index, last);
                map.put(last, index);
            }
            return true;
        }
    }

    /** Get a random element from the set. */
    public int getRandom() {
        int index = rand.nextInt(list.size());
        return list.get(index);
    }
}
X. 
https://discuss.leetcode.com/topic/53206/java-solution-with-two-hashmaps-easy-to-understand
    private HashMap<Integer, Integer> keyMap = null;
    private HashMap<Integer, Integer> valueMap = null;
    int count;

    /** Initialize your data structure here. */
    public RandomizedSet() {
        keyMap = new HashMap<Integer, Integer>();
        valueMap = new HashMap<Integer, Integer>();
    }
    
    /** Inserts a value to the set. Returns true if the set did not already contain the specified element. */
    public boolean insert(int val) {
        if(keyMap.containsKey(val)) {
            return false;
        } else {
            keyMap.put(val, count);
            valueMap.put(count, val);
            count = keyMap.size();
            return true;
        }
    }
    
    /** Removes a value from the set. Returns true if the set contained the specified element. */
    public boolean remove(int val) {
        if(!keyMap.containsKey(val)) {
            return false;
        } else {
            int valueKey = keyMap.get(val);
            keyMap.remove(val);
            if(valueKey != valueMap.size() - 1) {
                valueMap.put(valueKey, valueMap.get(valueMap.size() - 1));
                keyMap.put(valueMap.get(valueMap.size() - 1), valueKey);
                valueMap.remove(valueMap.size() - 1);
            } else {
                valueMap.remove(valueKey);
            }
            count = keyMap.size();
            return true;
        }
    }
    
    /** Get a random element from the set. */
    public int getRandom() {
        Random random = new Random();
        int n = random.nextInt(keyMap.size());
        return valueMap.get(n);
    }
http://www.programcreek.com/2014/08/leetcode-insert-delete-getrandom-o1-java/
We can use two hashmaps to solve this problem. One uses value as keys and the other uses index as the keys.
public class RandomizedSet {
 
    HashMap<Integer, Integer> map1;
    HashMap<Integer, Integer> map2;
    Random rand;
 
    /** Initialize your data structure here. */
    public RandomizedSet() {
        map1  = new HashMap<Integer, Integer>();
        map2  = new HashMap<Integer, Integer>();
        rand = new Random(System.currentTimeMillis());
    }
 
    /** Inserts a value to the set. Returns true if the set did not already contain the specified element. */
    public boolean insert(int val) {
        if(map1.containsKey(val)){
            return false;
        }else{
            map1.put(val, map1.size());
            map2.put(map2.size(), val);
        }
        return true;
    }
 
    /** Removes a value from the set. Returns true if the set contained the specified element. */
    public boolean remove(int val) {
        if(map1.containsKey(val)){
            int index = map1.get(val);
 
            //remove the entry from both maps
            map1.remove(val);
            map2.remove(index);
 
            if(map1.size()==0){
                return true;
            }
 
            //if last is deleted, do nothing 
            if(index==map1.size()){
                return true;
            }    
 
            //update the last element's index     
            int key1 = map2.get(map2.size());
 
            map1.put(key1, index);
            map2.remove(map2.size());
            map2.put(index, key1);
 
        }else{
            return false;
        }
 
        return true;
    }
 
    /** Get a random element from the set. */
    public int getRandom() {
        if(map1.size()==0){
            return -1; 
        }
 
        if(map1.size()==1){
            return map2.get(0);    
        }    
 
        return map2.get(new Random().nextInt(map1.size()));
        //return 0;
    }
}
X. Follow up
https://discuss.leetcode.com/topic/53216/java-solution-using-a-hashmap-and-an-arraylist-along-with-a-follow-up-131-ms/4
How do you modify your code to allow duplicated number?
The follow-up: allowing duplications.
For example, after insert(1), insert(1), insert(2), getRandom() should have 2/3 chance return 1 and 1/3 chance return 2.
Then, remove(1), 1 and 2 should have an equal chance of being selected by getRandom().
The idea is to add a set to the hashMap to remember all the locations of a duplicated number.
public class RandomizedSet {
     ArrayList<Integer> nums;
     HashMap<Integer, Set<Integer>> locs;
     java.util.Random rand = new java.util.Random();
     /** Initialize your data structure here. */
     public RandomizedSet() {
         nums = new ArrayList<Integer>();
         locs = new HashMap<Integer, Set<Integer>>();
     }
     
     /** Inserts a value to the set. Returns true if the set did not already contain the specified element. */
     public boolean insert(int val) {
         boolean contain = locs.containsKey(val);
         if ( ! contain ) locs.put( val, new HashSet<Integer>() ); 
         locs.get(val).add(nums.size());        
         nums.add(val);
         return ! contain ;
     }
     
     /** Removes a value from the set. Returns true if the set contained the specified element. */
     public boolean remove(int val) {
         boolean contain = locs.containsKey(val);
         if ( ! contain ) return false;
         int loc = locs.get(val).iterator().next();
         if (loc < nums.size() - 1 ) {
             int lastone = nums.get(nums.size() - 1 );
             nums.set( loc , lastone );
             locs.get(lastone).remove(nums.size() - 1);
             locs.get(lastone).add(loc);
         }
         nums.remove(nums.size() - 1);
         locs.get(val).remove(loc);
         if (locs.get(val).isEmpty()) locs.remove(val);
         return true;
     }
     
     /** Get a random element from the set. */
     public int getRandom() {
         return nums.get( rand.nextInt(nums.size()) );
     }
 }
https://reeestart.wordpress.com/2016/06/10/google-design-a-data-structure-to-support-add-get-set-delete-getrandom-in-o1-time/
2 Hash Table + 1 List
valueMap<Integer, Integer> stores key-value pair
indexMap<Integer, Integer> stores key-index pair, where index is the index in the List. This hash table is to support delete operation in O(1) time.
List is for getRandom() method.
There is another tricky place in Delete operation. Instead of compare the trade off between ArrayList and LinkedList, maintain a variable N to store the number of keys in this data structure, whenever a delete operation is executed, just swap the keys with the last one and decrement N by one. In this way, delete operation is guaranteed in Constant time.

class RandomizedHashTable {

   

  Map<Integer, Integer> valueMap = new HashMap<>();

  Map<Integer, Integer> indexMap = new HashMap<>();

  List<Integer> keys = new ArrayList<>();

  int N = 0;

   

  /* Insert a key-value */

  public void insert(int key, int val) {

    if (valueMap.containsKey(key)) {

      return;

    }

     

    valueMap.put(key, val);

    keys.add(key);

    indexMap.put(key, keys.size() - 1);

    N++;

  }

   

  /* Get the value */

  public int get(int key) {

    if (!valueMap.containsKey(key)) {

      throw new IllegalArgumentException();

    }

     

    return valueMap.get(key);

  }

   

  /* Update the value */

  public void set(int key, int val) {

    if (!valueMap.containsKey(key)) {

      return;

    }

     

    valueMap.put(key, val);

  }

   

  /* Get a random value */

  public int getRandom() {

    int index = new Random().randInt(keys.size());

    return valueMap.get(keys.get(index));

  }

   

  /* Delete a key-value */

  public void delete(int key) {

    if(!valueMap.containsKey(key)) {

      return;

    }

     

    valueMap.remove(key); // ALSO indexMap.remove(key);

    int index = indexMap.get(key);

    int last = keys.get(N - 1);

    indexMap.put(last, index);

    swap(keys, index, N - 1);

    N--;

  }

   

  private void swap(List<Integer> keys, int l, int r) {

    int tmp = keys.get(l);

    keys.set(l, keys.get(r);

    keys.set(r, tmp);

  }

}

https://reeestart.wordpress.com/2016/06/14/google-design-a-data-structure-to-support-insert-delete-medium-and-mode/
Design a data structure to support insert(), delete(), medium() and mode()
mode() 是众数，出现最多的那个。
BST + Heap
class TNode {
int val;
int freq;
int leftSize;
}
insert() O(logn)
delete() O(logn)
medium() O(logn)
mode() O(1)
Use a PriorityQueue to on TNode.freq to get mode number.
 * @author het
 *
 */
public class LeetCode380 {
	public class RandomizedSet {
	    private HashMap<Integer, Integer> map; // value -> index
	    private ArrayList<Integer> list; // value
	    private Random rand;
	    /** Initialize your data structure here. */
	    public RandomizedSet() {
	        map = new HashMap<Integer, Integer>();
	        list = new ArrayList<Integer>();
	        rand = new Random();
	    }

	    /** Inserts a value to the set. Returns true if the set did not already contain the specified element. */
	    public boolean insert(int val) {
	        if (map.containsKey(val)) {
	            return false;
	        }
	        else {
	            map.put(val, list.size());
	            list.add(val);
	            return true;
	        }
	    }

	    /** Removes a value from the set. Returns true if the set contained the specified element. */
	    public boolean remove(int val) {
	        if (!map.containsKey(val)) {
	            return false;
	        }
	        else {
	            int index = map.remove(val);
	            int last = list.remove(list.size() - 1);
	            if (last != val) {
	                list.set(index, last);
	                map.put(last, index);
	            }
	            return true;
	        }
	    }

	    /** Get a random element from the set. */
	    public int getRandom() {
	        int index = rand.nextInt(list.size());
	        return list.get(index);
	    }
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
