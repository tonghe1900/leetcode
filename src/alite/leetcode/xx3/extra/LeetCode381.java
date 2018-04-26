package alite.leetcode.xx3.extra;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

///**
// * LeetCode 381 - Insert Delete GetRandom O(1) - Duplicates allowed
//
//https://www.hrwhisper.me/leetcode-insert-delete-getrandom-o1-duplicates-allowed/
//Design a data structure that supports all following operations in average O(1) time.
//Note: Duplicate elements are allowed.
//insert(val): Inserts an item val to the collection.
//remove(val): Removes an item val from the collection if present.
//getRandom: Returns a random element from current collection of elements. 
//The probability of each element being returned is linearly related to the number of same value the collection contains.
//Example:
//
//
//// Init an empty collection.
//RandomizedCollection collection = new RandomizedCollection();
//
//// Inserts 1 to the collection. Returns true as the collection did not contain 1.
//collection.insert(1);
//
//// Inserts another 1 to the collection. Returns false as the collection contained 1. Collection now contains [1,1].
//collection.insert(1);
//
//// Inserts 2 to the collection, returns true. Collection now contains [1,1,2].
//collection.insert(2);
//
//// getRandom should return 1 with the probability 2/3, and returns 2 with the probability 1/3.
//collection.getRandom();
//
//// Removes 1 from the collection, returns true. Collection now contains [1,2].
//collection.remove(1);
//
//// getRandom should return 1 and 2 both equally likely.
//collection.getRandom();
//题目大意: 要求实现一个数据结构（数允许重复），可以支持插入，删除，随机数生成。 他们的复杂度均要求O(1)
//思路：其实和上一题  leetcode Insert Delete GetRandom O(1) 差不多，只不过这题允许重复，把字典里换成一个list即可
//
//private:
//
// unordered_map<int, vector<int>> index;
//
// vector<int> output;
//
//public:
//
// /** Initialize your data structure here. */
//
// RandomizedCollection() {}
//
// 
//
// /** Inserts a value to the collection. Returns true if the collection did not already contain the specified element. */
//
// bool insert(int val) {
//
//  bool return_val = index.find(val) == index.end();
//
//  index[val].push_back(output.size());
//
//  output.push_back(val);
//
//  return return_val;
//
// }
//
// 
//
// /** Removes a value from the collection. Returns true if the collection contained the specified element. */
//
// bool remove(int val) {
//
//  if (index.find(val) == index.end()) return false;
//
//  int _id = index[val].back(); index[val].pop_back();
//
//  int last = output.back(); output.pop_back();
//
//  if (last != val) {
//
//   index[last].back() = _id;
//
//   output[_id] = last;
//
//  }
//
//  if (index[val].empty())
//
//   index.erase(val);
//
//  return true;
//
// }
//
// 
//
// /** Get a random element from the collection. */
//
// int getRandom() {
//
//  return output[rand() % output.size()];
//
// }
// * @author het
// *
// */
public class LeetCode381 {
	
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
