package alite.leetcode.xx2.sucess.extra;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * http://sbzhouhao.net/LeetCode/LeetCode-Shortest-Word-Distance-II.html
Related: LeetCode [245] Shortest Word Distance III
This is a follow up of Shortest Word Distance. The only difference is now you are given the list of words and your method
 will be called repeatedly many times with different parameters. How would you optimize it?
Design a class which receives a list of words in the constructor, and implements a method that takes two words word1 and word2 
and return the shortest distance between these two words in the list.
For example,
Assume that words = ["practice", "makes", "perfect", "coding", "makes"].
Given word1 = “coding”, word2 = “practice”, return 3.
Given word1 = "makes", word2 = "coding", return 1.
Note:
You may assume that word1 does not equal to word2, and word1 and word2 are both in the list.
http://buttercola.blogspot.com/2015/08/leetcode-shortest-word-distance-ii.html

    private Map<String, List<Integer>> map;

    public WordDistance(String[] words) {

        map = new HashMap<>();

        for (int i = 0; i < words.length; i++) {

            if (map.containsKey(words[i])) {

                List<Integer> pos = map.get(words[i]);

                pos.add(i);

                map.put(words[i], pos);

            } else {

                List<Integer> pos = new ArrayList<>();

                pos.add(i);

                map.put(words[i], pos);

            }

        }

    }


    public int shortest(String word1, String word2) {

        List<Integer> pos1 = map.get(word1);

        List<Integer> pos2 = map.get(word2);

         

        int minDistance = Integer.MAX_VALUE;

        int i = 0;

        int j = 0;

        while (i < pos1.size() && j < pos2.size()) {

            int p1 = pos1.get(i);

            int p2 = pos2.get(j);

            if (p1 < p2) {

                minDistance = Math.min(minDistance, p2 - p1);

                i++;

            } else {

                minDistance = Math.min(minDistance, p1 - p2);

                j++;

            }

        }

         

        return minDistance;

    }
http://www.fgdsb.com/2015/01/13/word-distance/
可以用hash table把每个单词在vector中的所有索引存起来。这样调用distance的时候问题就转化成：
从两个已排序的数组中分别选一个数字，求绝对值最小值。
这个问题也有可以讨论的地方，假设两个数组的大小分别为M和N，如果M和N大致相同，可以直接用类似merge sorted array的方法从头开始一个一个比下去。最坏的时间复杂度为O(M+N)。
如果两个数组大小相差非常大，假设M>>N，那么可以遍历N，然后用binary search找出最接近N[i]的值，复杂度为O(NlogM)。
http://segmentfault.com/a/1190000003906667
时间 O(N) 空间 O(N)
 因为会多次调用，我们不能每次调用的时候再把这两个单词的下标找出来。我们可以用一个哈希表，在传入字符串数组时，就把每个单词的下标找出存入表中。这样当调用最短距离的方法时，我们只要遍历两个单词的下标列表就行了。具体的比较方法，则类似merge two list，每次比较两个list最小的两个值，得到一个差值。然后把较小的那个给去掉。因为我们遍历输入数组时是从前往后的，所以下标列表也是有序的。
    HashMap<String, List<Integer>> map = new HashMap<String, List<Integer>>();
    
    public WordDistance(String[] words) {
        // 统计每个单词出现的下标存入哈希表中
        for(int i = 0; i < words.length; i++){
            List<Integer> cnt = map.get(words[i]);
            if(cnt == null){
                cnt = new ArrayList<Integer>();
            }
            cnt.add(i);
            map.put(words[i], cnt);
        }
    }

    public int shortest(String word1, String word2) {
        List<Integer> idx1 = map.get(word1);
        List<Integer> idx2 = map.get(word2);
        int distance = Integer.MAX_VALUE;
        int i = 0, j = 0;
        // 每次比较两个下标列表最小的下标，然后把跳过较小的那个
        while(i < idx1.size() && j < idx2.size()){
            distance = Math.min(Math.abs(idx1.get(i) - idx2.get(j)), distance);
            if(idx1.get(i) < idx2.get(j)){
                i++;
            } else {
                j++;
            }
        }
        return distance;
    }

http://shibaili.blogspot.com/2015/09/day-126-243-shortest-word-distance.html

class WordDistance {

public:

    WordDistance(vector<string>& words) {

        for (int i = 0; i < words.size(); i++) {

            if (dic.find(words[i]) == dic.end()) {

                vector<int> t = {i};

                dic[words[i]] = t;

            }else {

                dic[words[i]].push_back(i);

            }

        }

    }


    int shortest(string word1, string word2) {

        int i = 0, j = 0;

        int distance = INT_MAX;

         

        while (i < dic[word1].size() && j < dic[word2].size()) {

            distance = min(distance,abs(dic[word1][i] - dic[word2][j]));

            if (dic[word1][i] < dic[word2][j]) {

                i++;

            }else {

                j++;

            }

        }

         

        return distance;

    }

private:

    unordered_map<string,vector<int>> dic;

};
http://www.tangjikai.com/algorithms/leetcode-244-shortest-word-distance-ii

Hints:
Use a hash table mapping word to index list.
O(n^2) time   O(n) space

    def __init__(self, words):

        self.dic = collections.defaultdict(list)

        for i, w in enumerate(words):

            self.dic[w].append(i)


    # @param {string} word1

    # @param {string} word2

    # @return {integer}

    # Adds a word into the data structure.

    def shortest(self, word1, word2):

        l1 = self.dic[word1]

        l2 = self.dic[word2]

        ans = 2174783647

        

        for i in l1:

            for j in l2:

                ans = min(ans, abs(i - j))

        

        return ans


    private Map<String, List<Integer>> map = new HashMap<>();

    public WordDistance(String[] words) {

        for (int i = 0; i < words.length; i++) {
            String s = words[i];
            List<Integer> list;
            if (map.containsKey(s)) {
                list = map.get(s);
            } else {
                list = new ArrayList<>();
            }
            list.add(i);
            map.put(s, list);
        }
    }

    public int shortest(String word1, String word2) {
        List<Integer> l1 = map.get(word1);
        List<Integer> l2 = map.get(word2);

        int min = Integer.MAX_VALUE;

        for (int a : l1) {
            for (int b : l2) {
                min = Math.min(Math.abs(b - a), min);
            }
        }
        return min;
    }


LIKE CODING: LeetCode [244] Shortest Word Distance II

// Your WordDistance object will be instantiated and called as such:

// WordDistance wordDistance(words);

// wordDistance.shortest("word1", "word2");

// wordDistance.shortest("anotherWord1", "anotherWord2");

    WordDistance(vector<string> words) {

        int n = words.size();

        for(int i=0; i<n; ++i){

            hash[words[i]].push_back(i);

        }

    }


    int shortest(string word1, string word2) {

        vector<int> pos1 = hash[word1];

        vector<int> pos2 = hash[word2];

        int dist = INT_MAX;

        for(auto p1:pos1){

            for(auto p2:pos2){

                dist = min(dist, abs(p1-p2));

            }

        }

        return dist;

    }
https://github.com/algorhythms/LeetCode/blob/master/244%20Shortest%20Word%20Distance%20II.py

def shortest(self, word1, word2):
mini = sys.maxint
for i in self.word_dict[word1]:
idx = bisect_left(self.word_dict[word2], i)
for nei in (-1, 0):
if 0 <= idx+nei < len(self.word_dict[word2]):
mini = min(mini, abs(i-self.word_dict[word2][idx+nei]))
return mini

https://moonstonelin.wordpress.com/2015/10/16/352/

Related: LeetCode 243: Shortest Word Distance
Read full article from LIKE CODING: LeetCode [244] Shortest Word Distance II
 * @author het
 *
 */
public class LeetCode244ShortestWordDistanceII {
	 HashMap<String, List<Integer>> map = new HashMap<String, List<Integer>>();
	    
	    public LeetCode244ShortestWordDistanceII(String[] words) {
	        // 统计每个单词出现的下标存入哈希表中
	        for(int i = 0; i < words.length; i++){
	            List<Integer> cnt = map.get(words[i]);
	            if(cnt == null){
	                cnt = new ArrayList<Integer>();
	            }
	            cnt.add(i);
	            map.put(words[i], cnt);
	        }
	    }

	    public int shortest(String word1, String word2) {
	        List<Integer> idx1 = map.get(word1);
	        List<Integer> idx2 = map.get(word2);
	        int distance = Integer.MAX_VALUE;
	        int i = 0, j = 0;
	        // 每次比较两个下标列表最小的下标，然后把跳过较小的那个
	        while(i < idx1.size() && j < idx2.size()){
	            distance = Math.min(Math.abs(idx1.get(i) - idx2.get(j)), distance);
	            if(idx1.get(i) < idx2.get(j)){
	                i++;
	            } else {
	                j++;
	            }
	        }
	        return distance;
	    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
