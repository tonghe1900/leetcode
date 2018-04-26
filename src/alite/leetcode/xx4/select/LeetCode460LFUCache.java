package alite.leetcode.xx4.select;

import java.util.HashMap;
import java.util.Map;

//// There exists a diagram related.
///**
// * https://leetcode.com/problems/lfu-cache/
//Design and implement a data structure for Least Frequently Used (LFU) cache. 
//It should support the following operations: get and set.
//get(key) - Get the value (will always be positive) of the key if 
//the key exists in the cache, otherwise return -1.
//set(key, value) - Set or insert the value if the key is not already present.
// When the cache reaches its capacity, it should invalidate the least frequently used 
// item before inserting a new item. For the purpose of this problem, 
// when there is a tie (i.e., two or more keys that have the same frequency), the least recently used
//key would be evicted.
//Follow up:
//Could you do both operations in O(1) time complexity?
//Example:
//
//
//LFUCache cache = new LFUCache( 2 /* capacity */ );
//
//cache.set(1, 1);
//cache.set(2, 2);
//cache.get(1);       // returns 1
//cache.set(3, 3);    // evicts key 2
//cache.get(2);       // returns -1 (not found)
//cache.get(3);       // returns 3.
//cache.set(4, 4);    // evicts key 1.
//cache.get(1);       // returns -1 (not found)
//cache.get(3);       // returns 3
//cache.get(4);       // returns 4
//
//双向链表（Doubly Linked List） + 哈希表（Hash Table）
//首先定义双向链表节点：KeyNode（Key节点）与FreqNode（频度节点）。
//KeyNode中保存key（键），value（值），freq（频度），prev（前驱），next（后继）
//
//FreqNode中保存freq（频度）、prev（前驱）、next（后继）、first（指向最新的KeyNode），last（指向最老的KeyNode）
//在数据结构LFUCache中维护如下属性：
//capacity：缓存的容量
//
//keyDict：从key到KeyNode的映射
//
//freqDict：从freq到FreqNode的映射
//
//head：指向最小的FreqNode
//整体数据结构设计如下图所示：
//head --- FreqNode1 ---- FreqNode2 ---- ... ---- FreqNodeN
//              |               |                       |               
//            first           first                   first             
//              |               |                       |               
//           KeyNodeA        KeyNodeE                KeyNodeG           
//              |               |                       |               
//           KeyNodeB        KeyNodeF                KeyNodeH           
//              |               |                       |               
//           KeyNodeC         last                   KeyNodeI           
//              |                                       |      
//           KeyNodeD                                 last
//              |
//            last
//LFUCache操作实现如下：
//set(key, value)：
//如果capacity为0，忽略当前操作，结束
//
//如果keyDict中包含key，则替换其value，更新节点频度，结束
//
//否则，如果当前keyDict的长度 == capcity，移除head.last（频度最低且最老的KeyNode）
//
//新增KeyNode(key, value)，加入keyDict，并更新freqDict
//get(key)：
//若keyDict中包含key，则更新节点频度，返回对应的value
//
//否则，返回-1
//节点频度的更新：
//从keyDict中找到对应的KeyNode，然后通过KeyNode的freq值，从freqDict找到对应的FreqNode
//
//如果FreqNode的next节点不等于freq + 1，则在其右侧插入一个值为freq + 1的新FreqNode节点
//
//将KeyNode的freq值+1后，从当前KeyNode链表转移到新的FreqNode对应的KeyNode链表
//
//如果KeyNode移动之后，原来的FreqNode对应的KeyNode链表为空，则删除原来的FreqNode
//
//在操作完毕后如果涉及到head的变更，则更新head
//class KeyNode(object):
//    def __init__(self, key, value, freq = 1):
//        self.key = key
//        self.value = value
//        self.freq = freq
//        self.prev = self.next = None
//
//class FreqNode(object):
//    def __init__(self, freq, prev, next):
//        self.freq = freq
//        self.prev = prev
//        self.next = next
//        self.first = self.last = None
//
//class LFUCache(object):
//
//    def __init__(self, capacity):
//        """
//        
//        :type capacity: int
//        """
//        self.capacity = capacity
//        self.keyDict = dict()
//        self.freqDict = dict()
//        self.head = None
//
//    def get(self, key):
//        """
//        :type key: int
//        :rtype: int
//        """
//        if key in self.keyDict:
//            keyNode = self.keyDict[key]
//            value = keyNode.value
//            self.inc(key, value)
//            return value
//        return -1
//
//    def set(self, key, value):
//        """
//        :type key: int
//        :type value: int
//        :rtype: void
//        """
//        if self.capacity == 0: return
//        if key not in self.keyDict and len(self.keyDict) == self.capacity:
//            self.removeKeyNode(self.head.last)
//        self.inc(key, value)
//
//    def inc(self, key, value):
//        """
//        Inserts a new KeyNode<key, value> with freq 1. Or increments the freq of an existing KeyNode<key, value> by 1.
//        :type key: str
//        :rtype: void
//        """
//        if key in self.keyDict:
//            keyNode = self.keyDict[key]
//            keyNode.value = value
//            freqNode = self.freqDict[keyNode.freq]
//            nextFreqNode = freqNode.next
//            keyNode.freq += 1
//            if nextFreqNode is None or nextFreqNode.freq > keyNode.freq:
//                nextFreqNode = self.insertFreqNodeAfter(keyNode.freq, freqNode)
//            self.unlinkKey(keyNode, freqNode)
//            self.linkKey(keyNode, nextFreqNode)
//        else:
//            keyNode = self.keyDict[key] = KeyNode(key, value)
//            freqNode = self.freqDict.get(1)
//            if freqNode is None:
//                freqNode = self.freqDict[1] = FreqNode(1, None, self.head)
//                if self.head:
//                    self.head.prev = freqNode
//                self.head = freqNode
//            self.linkKey(keyNode, freqNode)
//
//    def delFreqNode(self, freqNode):
//        """
//        Delete freqNode.
//        :rtype: void
//        """
//        prev, next = freqNode.prev, freqNode.next
//        if prev: prev.next = next
//        if next: next.prev = prev
//        if self.head == freqNode: self.head = next
//        del self.freqDict[freqNode.freq]
//
//    def insertFreqNodeAfter(self, freq, node):
//        """
//        Insert a new FreqNode(freq) after node.
//        :rtype: FreqNode
//        """
//        newNode = FreqNode(freq, node, node.next)
//        self.freqDict[freq] = newNode
//        if node.next: node.next.prev = newNode
//        node.next = newNode
//        return newNode
//
//    def removeKeyNode(self, keyNode):
//        """
//        Remove keyNode
//        :rtype: void
//        """
//        self.unlinkKey(keyNode, self.freqDict[keyNode.freq])
//        del self.keyDict[keyNode.key]
//
//    def unlinkKey(self, keyNode, freqNode):
//        """
//        Unlink keyNode from freqNode
//        :rtype: void
//        """
//        next, prev = keyNode.next, keyNode.prev
//        if prev: prev.next = next
//        if next: next.prev = prev
//        if freqNode.first == keyNode: freqNode.first = next
//        if freqNode.last == keyNode: freqNode.last = prev
//        if freqNode.first is None: self.delFreqNode(freqNode)
//
//    def linkKey(self, keyNode, freqNode):
//        """
//        Link keyNode to freqNode
//        :rtype: void
//        """
//        firstKeyNode = freqNode.first
//        keyNode.prev = None
//        keyNode.next = firstKeyNode
//        if firstKeyNode: firstKeyNode.prev = keyNode
//        freqNode.first = keyNode
//        if freqNode.last is None: freqNode.last = keyNode
//
//# Your LFUCache object will be instantiated and called as such:
//# obj = LFUCache(capacity)
//# param_1 = obj.get(key)
//# obj.set(key,value)
//https://github.com/laurentluce/lfu-cache/blob/master/lfucache/lfu_cache.py
//http://www.laurentluce.com/posts/least-frequently-used-cache-eviction-scheme-with-complexity-o1-in-python/
//The goal here is for the LFU cache algorithm to have a runtime complexity of O(1) for all of its operations, which include insertion, access and deletion (eviction).
//Doubly linked lists are used in this algorithm. One for the access frequency and each node in that list contains a list with the elements of same access frequency. Let say we have five elements in our cache. Two have been accessed one time and three have been accessed two times. In that case, the access frequency list has two nodes (frequency = 1 and frequency = 2). The first frequency node has two nodes in its list and the second frequency node has three nodes in its list.
//LFU doubly linked lists.
//How do we build that? The first object we need is a node:
//1
//class Node(object):
//2
//    """Node containing data, pointers to previous and next node."""
//3
//    def __init__(self, data):
//4
//        self.data = data
//5
//        self.prev = None
//6
//        self.next = None
//Next, our doubly linked list. Each node has a prev and next attribute equal to the previous node and next node respectively. The head is set to the first node and the tail to the last node.
//LFU doubly linked list.
//We can define our doubly linked list with methods to add a node at the end of the list, insert a node, remove a node and get a list with the nodes data.
//01
//class DoublyLinkedList(object):
//02
//    def __init__(self):
//03
//        self.head = None
//04
//        self.tail = None
//05
//        # Number of nodes in list.
//06
//        self.count = 0
//07
// 
//08
//    def add_node(self, cls, data):
//09
//        """Add node instance of class cls."""
//10
//        return self.insert_node(cls, data, self.tail, None)
//11
// 
//12
//    def insert_node(self, cls, data, prev, next):
//13
//        """Insert node instance of class cls."""
//14
//        node = cls(data)
//15
//        node.prev = prev
//16
//        node.next = next
//17
//        if prev:
//18
//            prev.next = node
//19
//        if next:
//20
//            next.prev = node
//21
//        if not self.head or next is self.head:
//22
//            self.head = node
//23
//        if not self.tail or prev is self.tail:
//24
//            self.tail = node
//25
//        self.count += 1
//26
//        return node
//27
// 
//28
//    def remove_node(self, node):
//29
//        if node is self.tail:
//30
//            self.tail = node.prev
//31
//        else:
//32
//            node.next.prev = node.prev
//33
//        if node is self.head:
//34
//            self.head = node.next
//35
//        else:
//36
//            node.prev.next = node.next
//37
//        self.count -= 1
//38
// 
//39
//    def get_nodes_data(self):
//40
//        """Return list nodes data as a list."""
//41
//        data = []
//42
//        node = self.head
//43
//        while node:
//44
//            data.append(node.data)
//45
//            node = node.next
//46
//        return data
//Each node in the access frequency doubly linked list is a frequency node (Freq Node on the diagram below). It is a node and also a doubly linked list containing the elements (Item nodes on the diagram below) of same frequency. Each item node has a pointer to its frequency node parent.
//LFU frequency and items doubly linked list.
//01
//class FreqNode(DoublyLinkedList, Node):
//02
//    """Frequency node containing linked list of item nodes with
//03
//       same frequency."""
//04
//    def __init__(self, data):
//05
//        DoublyLinkedList.__init__(self)
//06
//        Node.__init__(self, data)
//07
// 
//08
//    def add_item_node(self, data):
//09
//        node = self.add_node(ItemNode, data)
//10
//        node.parent = self
//11
//        return node
//12
// 
//13
//    def insert_item_node(self, data, prev, next):
//14
//        node = self.insert_node(ItemNode, data, prev, next)
//15
//        node.parent = self
//16
//        return node
//17
// 
//18
//    def remove_item_node(self, node):
//19
//        self.remove_node(node)
//20
// 
//21
// 
//22
//class ItemNode(Node):
//23
//    def __init__(self, data):
//24
//        Node.__init__(self, data)
//25
//        self.parent = None
//The item node data is equal to the key of the element we are storing, an HTTP request could be the key. The content itself (HTTP response for example) is stored in a dictionary. Each value in this dictionary is of type LfuItem where “data” is the content cached, “parent” is a pointer to the frequency node and “node” is a pointer to the item node under the frequency node.
//LFU Item.
//1
//class LfuItem(object):
//2
//    def __init__(self, data, parent, node):
//3
//        self.data = data
//4
//        self.parent = parent
//5
//        self.node = node
//We have defined our data objects classes, now we can define our cache object class. It has a doubly linked list (access frequency list) and a dictionary to contain the LFU items (LfuItem above). We defined two methods: one to insert a frequency node and one to remove a frequency node.
//01
//class Cache(DoublyLinkedList):
//02
//    def __init__(self):
//03
//        DoublyLinkedList.__init__(self)
//04
//        self.items = dict()
//05
// 
//06
//    def insert_freq_node(self, data, prev, next):
//07
//        return self.insert_node(FreqNode, data, prev, next)
//08
// 
//09
//    def remove_freq_node(self, node):
//10
//        self.remove_node(node)
//Next step is to define methods to insert to the cache, access the cache and delete from the cache.
//Let’s look at the insert method logic. It takes a key and a value, for example HTTP request and response. If the frequency node with frequency one does not exist, it is inserted at the beginning of the access frequency linked list. An item node is added to the frequency node items linked list. The key and value are added to the dictionary. Complexity is O(1).
//1
//def insert(self, key, value):
//2
//    if key in self.items:
//3
//        raise DuplicateException('Key exists')
//4
//    freq_node = self.head
//5
//    if not freq_node or freq_node.data != 1:
//6
//        freq_node = self.insert_freq_node(1, None, freq_node)
//7
// 
//8
//    freq_node.add_item_node(key)
//9
//    self.items[key] = LfuItem(value, freq_node)
//We insert two elements in our cache, we end up with:
//LFU insert method.
//Let’s look at the access method logic. If the key does not exist, we raise an exception. If the key exists, we move the item node to the frequency node list with frequency + 1 (adding the frequency node if it does not exist). Complexity is O(1).
//01
//def access(self, key):
//02
//    try:
//03
//        tmp = self.items[key]
//04
//    except KeyError:
//05
//        raise NotFoundException('Key not found')
//06
// 
//07
//    freq_node = tmp.parent
//08
//    next_freq_node = freq_node.next
//09
// 
//10
//    if not next_freq_node or next_freq_node.data != freq_node.data + 1:
//11
//        next_freq_node = self.insert_freq_node(freq_node.data + 1,
//12
//            freq_node, next_freq_node)
//13
//    item_node = next_freq_node.add_item_node(key)
//14
//    tmp.parent = next_freq_node
//15
// 
//16
//    freq_node.remove_item_node(tmp.node)
//17
//    if freq_node.count == 0:
//18
//        self.remove_freq_node(freq_node)
//19
// 
//20
//    tmp.node = item_node
//21
//    return tmp.data
//If we access the item with Key 1, the item node with data Key 1 is moved to the frequency node with frequency equal to 2. We end up with:
//LFU access method.
//If we access the item with Key 2, the item node with data Key 2 is moved to the frequency node with frequency equal to 2. The frequency node 1 is removed. We end up with:
//LFU access 2 method.
//Let’s look at the delete_lfu method. It removes the least frequently used item from the cache. To do that, it removes the first item node from the first frequency node and also the LFUItem object from the dictionary. If after this operation, the frequency node list is empty, it is removed.
//01
//def delete_lfu(self):
//02
//    """Remove the first item node from the first frequency node.
//03
//    Remove the item from the dictionary.
//04
//    """
//05
//    if not self.head:
//06
//        raise NotFoundException('No frequency nodes found')
//07
//    freq_node = self.head
//08
//    item_node = freq_node.head
//09
//    del self.items[item_node.data]
//10
//    freq_node.remove_item_node(item_node)
//11
//    if freq_node.count == 0:
//12
//        self.remove_freq_node(freq_node)
//If we call delete_lfu on our cache, the item node with data equal to Key 1 is removed and its LFUItem too. We end up with:
//LFU delete method.
//
//
//
//You might also like
//
// * @author het
// *
// */
public class LeetCode460LFUCache {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	
	//双向链表（Doubly Linked List） + 哈希表（Hash Table）
	//首先定义双向链表节点：KeyNode（Key节点）与FreqNode（频度节点）。
	//KeyNode中保存key（键），value（值），freq（频度），prev（前驱），next（后继）
	//
	//FreqNode中保存freq（频度）、prev（前驱）、next（后继）、first（指向最新的KeyNode），last（指向最老的KeyNode）
	//在数据结构LFUCache中维护如下属性：
	//capacity：缓存的容量
	//
	//keyDict：从key到KeyNode的映射
	//
	//freqDict：从freq到FreqNode的映射
	//
	//head：指向最小的FreqNode
	//整体数据结构设计如下图所示：
	//head --- FreqNode1 ---- FreqNode2 ---- ... ---- FreqNodeN
//	              |               |                       |               
//	            first           first                   first             
//	              |               |                       |               
//	           KeyNodeA        KeyNodeE                KeyNodeG           
//	              |               |                       |               
//	           KeyNodeB        KeyNodeF                KeyNodeH           
//	              |               |                       |               
//	           KeyNodeC         last                   KeyNodeI           
//	              |                                       |      
//	           KeyNodeD                                 last
//	              |
//	            last
	//LFUCache操作实现如下：
	//set(key, value)：
	//如果capacity为0，忽略当前操作，结束
	//
	//如果keyDict中包含key，则替换其value，更新节点频度，结束
	//
	//否则，如果当前keyDict的长度 == capcity，移除head.last（频度最低且最老的KeyNode）
	//
	//新增KeyNode(key, value)，加入keyDict，并更新freqDict
	//get(key)：
	//若keyDict中包含key，则更新节点频度，返回对应的value
	//
	//否则，返回-1
	//节点频度的更新：
	//从keyDict中找到对应的KeyNode，然后通过KeyNode的freq值，从freqDict找到对应的FreqNode
	//
	//如果FreqNode的next节点不等于freq + 1，则在其右侧插入一个值为freq + 1的新FreqNode节点
	//
	//将KeyNode的freq值+1后，从当前KeyNode链表转移到新的FreqNode对应的KeyNode链表
	//
	//如果KeyNode移动之后，原来的FreqNode对应的KeyNode链表为空，则删除原来的FreqNode
	//
	//在操作完毕后如果涉及到head的变更，则更新head
    class KeyNode{
    	private String key;
    	private String value;
    	private int frequency = 1;
    	private KeyNode next;
    	private KeyNode prev;
    	KeyNode(String key, String value, int frequency){
    		this.key = key;
    		this.value = value;
    		this.frequency = frequency;
    		
    		
    		
    	}
    	
    	KeyNode(String key, String value){
    		this.key = key;
    		this.value = value;
    		
    		
    		
    		
    	}
    }
    
    class FreqNode{
    	private int frequency;
    	private FreqNode prev;
    	private FreqNode next;
    	private KeyNode first;
    	private KeyNode last;
    	FreqNode(int frequency, FreqNode prev, FreqNode next){
    		this.frequency = frequency;
    		this.prev = prev;
    		this.next = next;
    		
    	}
    }
   
	//class KeyNode(object):
//	    def __init__(self, key, value, freq = 1):
//	        self.key = key
//	        self.value = value
//	        self.freq = freq
//	        self.prev = self.next = None
	//
	//class FreqNode(object):
//	    def __init__(self, freq, prev, next):
//	        self.freq = freq
//	        self.prev = prev
//	        self.next = next
//	        self.first = self.last = None
    class LFUCache{
    	private  int capacity;
    	private Map<String, KeyNode> keyDict = new HashMap<>();
    	private Map<Integer, FreqNode> freqDict = new HashMap<>();
    	private FreqNode head;
    	
    	
    	public String get(String key){
    		if(keyDict.containsKey(key)){
    			KeyNode keyNode = keyDict.get(key);
    			String value = keyNode.value;
    			this.inc(key, value);
    			return value;
    		}
    		return "-1";
    	}


		


		
   
	//
	//class LFUCache(object):
	//
//	    def __init__(self, capacity):
//	        """
//	        
//	        :type capacity: int
//	        """
//	        self.capacity = capacity
//	        self.keyDict = dict()
//	        self.freqDict = dict()
//	        self.head = None
	//
//	    def get(self, key):
//	        """
//	        :type key: int
//	        :rtype: int
//	        """
//	        if key in self.keyDict:
//	            keyNode = self.keyDict[key]
//	            value = keyNode.value
//	            self.inc(key, value)
//	            return value
//	        return -1
    	
    	public void set(String key, String value){
    		if(capacity == 0) return ;
    		if(!keyDict.containsKey(key) && keyDict.size() == capacity){
    			this.removeKeyNode(head.last);
    		}
    		this.inc(key, value);
    	}
	//
//	    def set(self, key, value):
//	        """
//	        :type key: int
//	        :type value: int
//	        :rtype: void
//	        """
//	        if self.capacity == 0: return
//	        if key not in self.keyDict and len(self.keyDict) == self.capacity:
//	            self.removeKeyNode(self.head.last)
//	        self.inc(key, value)
    
    private void inc(String key, String value) {
    	if(keyDict.containsKey(key)){
    		KeyNode keyNode = keyDict.get(key);
    		keyNode.value = value;
    		FreqNode freqNode = freqDict.get(new Integer(keyNode.frequency));
    		FreqNode nextFreqNode = freqNode.next ;
    		keyNode.frequency+=1;
    		if(nextFreqNode == null || nextFreqNode.frequency > keyNode.frequency){
    			nextFreqNode = insertFreqNodeAfter(keyNode.frequency, freqNode);
    		}
    		this.unlinkKey(keyNode,freqNode);
    		this.linkKey(keyNode, nextFreqNode);
    		
    	}else{
    		KeyNode keyNode = new KeyNode(key, value);
    		keyDict.put(key, keyNode);
    		FreqNode freqNode = freqDict.get(new Integer(1));
    		if(freqNode == null){
    			 freqNode = new FreqNode(1, null, this.head);
    			 if (head != null){
    				 head.prev = freqNode;
    			 }
    			 this.head = freqNode;
    		}
    		this.linkKey(keyNode, freqNode);
    	}
		
	}
	//
//	    def inc(self, key, value):
//	        """
//	        Inserts a new KeyNode<key, value> with freq 1. Or increments the freq of an existing KeyNode<key, value> by 1.
//	        :type key: str
//	        :rtype: void
//	        """
//	        if key in self.keyDict:
//	            keyNode = self.keyDict[key]
//	            keyNode.value = value
//	            freqNode = self.freqDict[keyNode.freq]
//	            nextFreqNode = freqNode.next
//	            keyNode.freq += 1
//	            if nextFreqNode is None or nextFreqNode.freq > keyNode.freq:
//	                nextFreqNode = self.insertFreqNodeAfter(keyNode.freq, freqNode)
//	            self.unlinkKey(keyNode, freqNode)
//	            self.linkKey(keyNode, nextFreqNode)
//	        else:
//	            keyNode = self.keyDict[key] = KeyNode(key, value)
//	            freqNode = self.freqDict.get(1)
//	            if freqNode is None:
//	                freqNode = self.freqDict[1] = FreqNode(1, None, self.head)
//	                if self.head:
//	                    self.head.prev = freqNode
//	                self.head = freqNode
//	            self.linkKey(keyNode, freqNode)
	//
    private void delFreqNode(FreqNode freqNode) {
    	FreqNode prev = freqNode.prev;
    	FreqNode next = freqNode.next;
    	if(prev != null) prev.next = next;
    	if(next != null) next.prev= prev;
    	if(head == freqNode) head = next;
    	freqDict.remove(new Integer(freqNode.frequency));
		
	}
//	    def delFreqNode(self, freqNode):
//	        """
//	        Delete freqNode.
//	        :rtype: void
//	        """
//	        prev, next = freqNode.prev, freqNode.next
//	        if prev: prev.next = next
//	        if next: next.prev = prev
//	        if self.head == freqNode: self.head = next
//	        del self.freqDict[freqNode.freq]
	//
    
    private FreqNode insertFreqNodeAfter(int frequency, FreqNode node) {
		FreqNode newNode = new FreqNode(frequency, node, node.next);
		this.freqDict.put(frequency, newNode);
		if(node.next != null){
			node.next.prev = newNode;
			
		}
		node.next = newNode;
		
		
		return newNode;
	}
//	    def insertFreqNodeAfter(self, freq, node):
//	        """
//	        Insert a new FreqNode(freq) after node.
//	        :rtype: FreqNode
//	        """
//	        newNode = FreqNode(freq, node, node.next)
//	        self.freqDict[freq] = newNode
//	        if node.next: node.next.prev = newNode
//	        node.next = newNode
//	        return newNode
	//
    private void removeKeyNode(KeyNode keyNode){
    	this.unlinkKey(keyNode, freqDict.get(new Integer(keyNode.frequency)));
    	keyDict.remove(keyNode.key);
    }
//	    def removeKeyNode(self, keyNode):
//	        """
//	        Remove keyNode
//	        :rtype: void
//	        """
//	        self.unlinkKey(keyNode, self.freqDict[keyNode.freq])
//	        del self.keyDict[keyNode.key]
	//
    
    







	
//	    def unlinkKey(self, keyNode, freqNode):
//	        """
//	        Unlink keyNode from freqNode
//	        :rtype: void
//	        """
//	        next, prev = keyNode.next, keyNode.prev
//	        if prev: prev.next = next
//	        if next: next.prev = prev
//	        if freqNode.first == keyNode: freqNode.first = next
//	        if freqNode.last == keyNode: freqNode.last = prev
//	        if freqNode.first is None: self.delFreqNode(freqNode)
	//
    private void unlinkKey(KeyNode keyNode, FreqNode freqNode) {
		KeyNode next = keyNode.next;
		KeyNode prev = keyNode.prev;
		if(prev != null) prev.next = next;
		if(next != null) next.prev = prev;
		if(freqNode.first == keyNode) {
			freqNode.first = next;
		}
		if(freqNode.last == keyNode){
			freqNode.last = prev;
		}
		if(freqNode.first == null) {
			this.delFreqNode(freqNode);
		}
		
	}
    
private void linkKey(KeyNode keyNode, FreqNode freqNode) {
	KeyNode firstKeyNode = freqNode.first;
	keyNode.prev = null;
	keyNode.next = firstKeyNode;
	if(firstKeyNode != null){
		firstKeyNode.prev = keyNode;
	}
	freqNode.first = keyNode;
	if(freqNode.last == null){
		freqNode.last = keyNode;
	}
		
	}
//	    def linkKey(self, keyNode, freqNode):
//	        """
//	        Link keyNode to freqNode
//	        :rtype: void
//	        """
//	        firstKeyNode = freqNode.first
//	        keyNode.prev = None
//	        keyNode.next = firstKeyNode
//	        if firstKeyNode: firstKeyNode.prev = keyNode
//	        freqNode.first = keyNode
//	        if freqNode.last is None: freqNode.last = keyNode
	//







	







	







	
    }
}
