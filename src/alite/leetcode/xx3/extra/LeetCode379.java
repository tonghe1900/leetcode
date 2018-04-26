package alite.leetcode.xx3.extra;
/**
 * LeetCode 379 - Design Phone Directory

http://www.cnblogs.com/grandyang/p/5735205.html
Design a Phone Directory which supports the following operations:

get: Provide a number which is not assigned to anyone.
check: Check if a number is available or not.
release: Recycle or release a number.
Example:
// Init a phone directory containing a total of 3 numbers: 0, 1, and 2.
PhoneDirectory directory = new PhoneDirectory(3);

// It can return any available phone number. Here we assume it returns 0.
directory.get();

// Assume it returns 1.
directory.get();

// The number 2 is available, so return true.
directory.check(2);

// It returns 2, the only number that is left.
directory.get();

// The number 2 is no longer available, so return false.
directory.check(2);

// Release number 2 back to the pool.
directory.release(2);

// Number 2 is available again, return true.
directory.check(2);

https://discuss.leetcode.com/topic/53120/subclassing-set-python-java
Extending TreeSet, gets accepted in about 640 ms (first attempt was Memory Limit Exceeded, but then it was accepted in 661, 602, 646 and 654 ms). Probably slow, but at least it's simple.
public class PhoneDirectory extends TreeSet<Integer> {

    public PhoneDirectory(int maxNumbers) {
        for (int i=0; i<maxNumbers; i++)
            add(i);
    }
    
    public int get() {
        return isEmpty() ? -1 : pollFirst();
    }
    
    public boolean check(int number) {
        return contains(number);
    }
    
    public void release(int number) {
        add(number);
    }
}

Then I remembered seeing the word BitSet here in a topic title (of the solution from @johnyrufus16). and got the following accepted in 422 ms and 385 ms:
public class PhoneDirectory extends BitSet {

    public PhoneDirectory(int maxNumbers) {
        super(maxNumbers);
        flip(0, maxNumbers);
    }

    public int get() {
        if (isEmpty())
            return -1;
        int number = nextSetBit(0);
        clear(number);
        return number;
    }
    
    public boolean check(int number) {
        return get(number);
    }
    
    public void release(int number) {
        set(number);
    }
}
The idea is to use java's bitset and use smallestFreeIndex/max to keep track of the limit.
Also, by keeping track of the updated smallestFreeIndex all the time, the run time of get()
is spared from scanning the entire bitset every time.
http://www.guoting.org/leetcode/leetcode-379-design-phone-directory/
public class PhoneDirectory {

    BitSet bitset;
    int max; // max limit allowed
    int smallestFreeIndex; // current smallest index of the free bit

    public PhoneDirectory(int maxNumbers) {
        this.bitset = new BitSet(maxNumbers);
        this.max = maxNumbers;
    }

    public int get() {
        // handle bitset fully allocated
        if(smallestFreeIndex == max) {
            return -1;
        }
        int num = smallestFreeIndex;
        bitset.set(smallestFreeIndex);
        //Only scan for the next free bit, from the previously known smallest free index
        smallestFreeIndex = bitset.nextClearBit(smallestFreeIndex);
        return num;
    }

    public boolean check(int number) {
        return bitset.get(number) == false;
    }

    public void release(int number) {
        //handle release of unallocated ones
        if(bitset.get(number) == false)
            return;
        bitset.clear(number);
        if(number < smallestFreeIndex) {
            smallestFreeIndex = number;
        }
    }
}
https://all4win78.wordpress.com/2016/08/07/leetcode-379-design-phone-directory/

public class PhoneDirectory {

    int max;

    int cur;

    Stack<Integer> released;

    boolean[] isUsed;


    /** Initialize your data structure here

        @param maxNumbers - The maximum numbers that can be stored in the phone directory. */

    public PhoneDirectory(int maxNumbers) {

        max = maxNumbers;

        released = new Stack<>();

        isUsed = new boolean[max];

        cur = 0;

    }


    /** Provide a number which is not assigned to anyone.

        @return - Return an available number. Return -1 if none is available. */

    public int get() {

        if (!released.isEmpty()) {

            int num = released.pop();

            isUsed[num] = true;

            return num;

        } else {

            if (cur < max) {

                isUsed[cur] = true;

                cur += 1;

                return cur - 1;

            } else {

                return -1;

            }

        }

    }


    /** Check if a number is available or not. */

    public boolean check(int number) {

        return !isUsed[number];

    }


    /** Recycle or release a number. */

    public void release(int number) {

        if (isUsed[number]) {

            released.push(number);

        }

        isUsed[number] = false;

    }

}

让我们设计一个电话目录管理系统，可以分配电话号码，查询某一个号码是否已经被使用，释放一个号码，需要注意的是，之前释放的号码下一次应该被优先分配。这题对C++解法的时间要求非常苛刻，尝试了好几种用set，或者stack/queue，或者使用vector的push_back等等，都TLE了，终于找到了一种可以通过OJ的解法。这里用两个一维数组recycle和flag，分别来保存被回收的号码和某个号码的使用状态，还有变量max_num表示最大数字，next表示下一个可以分配的数字，idx表示recycle数组中可以被重新分配的数字的位置，然后在get函数中，没法分配的情况是，当next等于max_num并且index小于等于0，此时返回-1。否则我们先看recycle里有没有数字，有的话先分配recycle里的数字，没有的话再分配next。记得更新相对应的flag中的使用状态

    PhoneDirectory(int maxNumbers) {
        max_num = maxNumbers;
        next = idx = 0;
        recycle.resize(max_num);
        flag.resize(max_num, 1);
    }
    
    /** Provide a number which is not assigned to anyone.
        @return - Return an available number. Return -1 if none is available. */
    int get() {
        if (next == max_num && idx <= 0) return -1;
        if (idx > 0) {
            int t = recycle[--idx];
            flag[t] = 0;
            return t;
        }
        flag[next] = false;
        return next++;
    }
    
    /** Check if a number is available or not. */
    bool check(int number) {
        return number >= 0 && number < max_num && flag[number];
    }
    
    /** Recycle or release a number. */
    void release(int number) {
        if (number >= 0 && number < max_num && !flag[number]) {
            recycle[idx++] = number;
            flag[number] = 1;
        }
    }
private:
    int max_num, next, idx;
    vector<int> recycle, flag;

https://reeestart.wordpress.com/2016/06/23/google-design-phone-book/
设计数据结构存电话号码。要求实现
isTaken(String phone)
takeNumber(String phone)
giveMeANumber()
[Solution]
[Solution #1]
Trie, all three method run in O(10) time

1

2

3

4

5

6

private class TrieNode {

  char digit;

  int nAvail;

  int level;

  TrieNode[] children;

}
每个trie node除了包含digit和level之外，还需要一个nAvail来记录当前node下还有多少个数字可以用，也就是children[]里还有多少个null。
这样在giveMeANumber()的实现中，dfs搜索，在任何一个node，如果nAvail不等于0，那么随便找个为null的child, 剩余部分随机生成就可以了。如果nAvail等于0，那么就遍历children，找出一个child.nAvail > 0的node，从这个child继续dfs搜索。
isTaken()和takeNumber()就和普通trie tree的insert和search一样。
这里还有一点设计的小细节，一般TrieNode的children都用一个HashMap来存，目的是为了省空间。但是对于这道题并不适用，这里children的数量最多就10个，而HashMap的initial size远大于10，用HashMap反而更占空间。所以，做题不要太死板，根据要求和面试官讨论各种设计的trade off。
[Solution #2]
Double Linked List + Hash Table, All in O(1) time
有点像LRU的设计。
用一个double linked list来存available的电话号码
用一个hash set来存已经被使用过的电话号码
用一个hash table来map phone number to ListNode
isTaken()直接搜hash set, O(1)
takeNumber(), remove ListNode from linked list, remove phone number from hash table, add phone number into hash set, O(1)
giveMeANumber()直接取链表头。O(1)
cons: 必须把所有available的电话号码都放到linked list里。space开销比Trie tree的solution要大的多，如果电话号码是10位数，那就需要10^10的空间for both list and hash table

// Trie Solution

// All O(n), where n is length of phone numbers, which is actually a constant number(10 in US). So, all three method run in O(1) time

class PhoneBook {

   

  TrieNode root;

   

  public PhoneBook() {

    this.root = new TrieNode('#', 0);

  }

   

  public boolean isTaken(String phone) {

    if (phone == null || phone.isEmpty()) {

      throw new IllegalArgumentException();

    }

    return search(root, phone, 0);

  }

   

  private boolean search(TrieNode curr, String phone, int pos) {

    if (pos == phone.length() && curr.level == 10) {

      return true;

    }

     

    char currDigit = phone.charAt(pos);

    TrieNode next = curr.children[currDigit - '0'];

     

    return next == null? false : search(next, phone, pos + 1);

  }

   

  public void takeNumber(String phone) {

    if (phone == null || phone.isEmpty()) {

      throw new IllegalArgumentException();

    }

     

    insert(root, phone, 0);

  }

   

  private void insert(TrieNode curr, String phone, int pos) {

    if (pos == phone.length()) {

      return;

    }

     

    char currDigit = phone.charAt(pos);

    TrieNode next = curr.children[currDigit - '0'];

     

    if (next == null) {

      curr.nAvail -= 1;

      TrieNode newNode = new TrieNode(currDigit, curr.level + 1);

      curr.children[currDigit - '0'] = newNode;

      next = newNode;

    }

     

    insert(next, phone, pos + 1);

  }

   

  public String giveMeANumber() {

     

    StringBuilder sb = new StringBuilder();

    findNumber(root, sb);

    return sb.toString();

  }

   

  private void findNumber(TrieNode curr, StringBuilder sb) {

    if (curr == null) {

      return;

    }

     

    TrieNode nextNode = null;

    if (curr.nAvail > 0) {

      int nextDigit = -1;

      for (int i = 0; i < 10; i++) {

        if (curr.children[i] == null) {

          nextDigit = i;

          break;

        }

      }

       

      curr.nAvail -= 1;

      nextNode = new TrieNode((char) ('0' + nextDigit), curr.level + 1);

      curr.children[nextDigit] = nextNode;

      sb.append(nextNode.digit);

       

      generateSub(nextNode, sb);

    }

    else {

      for (int i = 0; i < 10; i++) {

        if (curr.children[i].nAvail > 0) {

          nextNode = curr.children[i];

        }

      }

       

      sb.append(nextNode.digit);

      findNumber(curr, sb);

    }

  }

   

  private void generateSub(TrieNode curr, StringBuilder sb) {

    if (sb.length() == 10) {

      return;

    }

    Random rand = new Random();

    int nextDigit = rand.nextInt(10);

    TrieNode nextNode = new TrieNode((char) ('0' + nextDigit), curr.level + 1);

    curr.children[nextDigit] = nextNode;

    sb.append(nextNode.digit);

     

    generateSub(nextNode, sb); 

  }

   

  private class TrieNode {

    char digit;

    int nAvail;

    int level;

    TrieNode[] children;

     

    TrieNode(char digit, int level) {

      this.digit = digit;

      this.level = level;

      this.nAvail = 10;

      this.children = new TrieNode[10];

    }

  }

}

 

 

// Linked list + HashMap Solution

// All O(1). But space is worse than Trie solution because we need store all available phone numbers (10^10) both in linked list and hash table.

class PhoneBook2 {

   

  ListNode head;

  ListNode tail;

  Map<String, ListNode> map;

  Set<String> taken;

   

  public PhoneBook2(ListNode head, ListNode tail, Map<String, ListNode> map) {

    this.head = head;

    this.tail = tail;

    this.map = map;

    this.taken = new HashSet<>();

  }

   

  public boolean isTaken(String phone) {

    if (phone == null || phone.isEmpty()) {

      throw new IllegalArgumentException();

    }

     

    return set.contains(phone);

  }

   

  public void takeNumber(String phone) {

    if (phone == null || phone.isEmpty()) {

      throw new IllegalArgumentException();

    }

    if (!map.containsKey(phone)) {

      return; // already taken

    }

     

    ListNode curr = map.get(phone);

    if (curr == head) {

      head = head.next;

    }

    else if (curr == tail) {

      tail = tail.prev;

    }

    else {

      curr.prev.next = curr.next;

      curr.next.prev = curr.prev;

    }

     

    curr.prev = null;

    curr.next = null;

    map.remove(phone);

    set.add(phone);

  }

   

  public String giveMeANumber() {

    if (head == null) {

      return null; // no more phone number

    }

     

    ListNode curr = head;

    head = head.next;

    map.remove(curr.val);

    set.add(curr.val);

    return curr.val;

  }

}
http://www.1point3acres.com/bbs/thread-137822-1-1.html
 * @author het
 *
 */
public class LeetCode379 {
	public class PhoneDirectory extends TreeSet<Integer> {

	    public PhoneDirectory(int maxNumbers) {
	        for (int i=0; i<maxNumbers; i++)
	            add(i);
	    }
	    
	    public int get() {
	        return isEmpty() ? -1 : pollFirst();
	    }
	    
	    public boolean check(int number) {
	        return contains(number);
	    }
	    
	    public void release(int number) {
	        add(number);
	    }
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
