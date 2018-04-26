package alite.leetcode.newExtra.finished;
/**
 * [LeetCode] Two Sum III - Data structure design 两数之和之三 - 数据结构设计
 

Design and implement a TwoSum class. It should support the following operations:add and find.

add - Add the number to an internal data structure.
find - Find if there exists any pair of numbers which sum is equal to the value.

For example,
add(1); add(3); add(5);
find(4) -> true
find(7) -> false

 

这道题让我们设计一个Two Sum的数据结构，跟LeetCode的第一道题Two Sum没有什么太大的不一样，作为LeetCode的首题，Two Sum的名气不小啊，正所谓平生不会TwoSum，刷尽LeetCode也枉然。记得原来在背单词的时候，总是记得第一个单词是abandon，结果有些人背来背去还在abandon，有时候想想刷题其实跟背GRE红宝书没啥太大的区别，都是一个熟练功夫，并不需要有多高的天赋，只要下足功夫，都能达到一个很不错的水平，套用一句鸡汤问来激励下吧，“有些时候我们的努力程度根本达不到需要拼天赋的地步”，好了，不闲扯了，来看题吧。不过这题也没啥可讲的，会做Two Sum的这题就很简单了，我们先来看用哈希表的解法，我们把每个数字和其出现的次数建立映射，然后我们遍历哈希表，对于每个值，我们先求出此值和目标值之间的差值t，然后我们需要分两种情况来看，如果当前值不等于差值t，那么只要哈希表中有差值t就返回True，或者是当差值t等于当前值时，如果此时哈希表的映射次数大于1，则表示哈希表中还有另一个和当前值相等的数字，二者相加就是目标值，参见代码如下：

 

解法一：

复制代码
class TwoSum {
public:
    void add(int number) {
        ++m[number];
    }
    bool find(int value) {
        for (auto a : m) {
            int t = value - a.first;
            if ((t != a.first && m.count(t)) || (t == a.first && a.second > 1)) {
                return true;
            }
        }
        return false;
    }
private:
    unordered_map<int, int> m;
};
复制代码
 

另一种解法不用哈希表，而是unordered_multiset来做，但是原理和上面一样，参见代码如下：

 

解法二：

复制代码
class TwoSum {
public:
    void add(int number) {
        s.insert(number);
    }
    bool find(int value) {
        for (auto a : s) {
            int cnt = a == value - a ? 1 : 0;
            if (s.count(value - a) > cnt) {
                return true;
            }
        }
        return false;
    }
private:
    unordered_multiset<int> s;
};




Design and implement a TwoSum class. It should support the following operations: add and find.

add - Add the number to an internal data structure.
find - Find if there exists any pair of numbers which sum is equal to the value.

For example,

add(1); 
add(3); 
add(5);
find(4) -> true
find(7) -> false
Java Solution

Since the desired class need add and get operations, HashMap is a good option for this purpose.

public class TwoSum {
	private HashMap<Integer, Integer> elements = new HashMap<Integer, Integer>();
 
	public void add(int number) {
		if (elements.containsKey(number)) {
			elements.put(number, elements.get(number) + 1);
		} else {
			elements.put(number, 1);
		}
	}
 
	public boolean find(int value) {
		for (Integer i : elements.keySet()) {
			int target = value - i;
			if (elements.containsKey(target)) {
				if (i == target && elements.get(target) < 2) {
					continue;
				}
				return true;
			}
		}
		return false;
	}
}
 * @author het
 *
 */
public class TwoSum3 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
