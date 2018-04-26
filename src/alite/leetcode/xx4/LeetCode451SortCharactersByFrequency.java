package alite.leetcode.xx4;

import java.util.Arrays;
import java.util.Comparator;

/**
 * https://leetcode.com/problems/sort-characters-by-frequency/
Given a string, sort it in decreasing order based on the frequency of characters.
Example 1:
Input:
"tree"

Output:
"eert"

Explanation:
'e' appears twice while 'r' and 't' both appear once.
So 'e' must appear before both 'r' and 't'. Therefore "eetr" is also a valid answer.
Example 2:
Input:
"cccaaa"

Output:
"cccaaa"

Explanation:
Both 'c' and 'a' appear three times, so "aaaccc" is also a valid answer.
Note that "cacaca" is incorrect, as the same characters must be together.
Example 3:
Input:
"Aabb"

Output:
"bbAa"

Explanation:
"bbaA" is also a valid answer, but "Aabb" is incorrect.
Note that 'A' and 'a' are treated as two different characters.
http://leetcodesolution.blogspot.com/2016/11/leetcodesort-characters-by-frequency.html
count the frequency of each character, and then sort the characters by frequency and out put the characters. We can use a two dimension array for the easy counting and sorting. 
public String frequencySort(String s) {
        int[][] count = new int[256][2];
        
        for(char c: s.toCharArray()) {
            count[c][0] = c;
            count[c][1]++;
        }
        
        Arrays.sort(count, new Comparator<int[]>() {
           public int compare(int[] a, int[] b) {
               return a[1] == b[1] ? 0 : (a[1] < b[1] ? 1 : -1);
           } 
        });
        
        
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<256; i++) {
            if (count[i][1] > 0) {
                for(int j=0; j<count[i][1]; j++) {
                    sb.append((char)count[i][0]);
                }
            }
        }
        
        return sb.toString();
    }

https://discuss.leetcode.com/topic/66281/java-o-n-bucket-sort-hashmap
public String frequencySort(String s) {
    char[] arr = s.toCharArray();
    
    // bucket sort
    int[] count = new int[256];
    for(char c : arr) count[c]++;
    
    // count values and their corresponding letters
    Map<Integer, List<Character>> map = new HashMap<>();//\\
    for(int i = 0; i < 256; i++){
        if(count[i] == 0) continue;
        int cnt = count[i];
        if(!map.containsKey(cnt)){
            map.put(cnt, new ArrayList<Character>());
        }
        map.get(cnt).add((char)i);
    }

    // loop throught possible count values
    StringBuilder sb = new StringBuilder();
    for(int cnt = arr.length; cnt > 0; cnt--){ //\\
        if(!map.containsKey(cnt)) continue;
        List<Character> list = map.get(cnt);
        for(Character c: list){
            for(int i = 0; i < cnt; i++){
                sb.append(c);
            }
        }
    }
    return sb.toString();
}
https://discuss.leetcode.com/topic/66024/java-o-n-bucket-sort-solution-o-nlogn-priorityqueue-solution-easy-to-understand
    public String frequencySort(String s) {
        Map<Character, Integer> map = new HashMap<>();
        for (char c : s.toCharArray()) {
            if (map.containsKey(c)) {
                map.put(c, map.get(c) + 1);
            } else {
                map.put(c, 1);
            }
        }
        List<Character> [] bucket = new List[s.length() + 1];
        for (char key : map.keySet()) {
            int frequency = map.get(key);
            if (bucket[frequency] == null) {
                bucket[frequency] = new ArrayList<>();
            }
            bucket[frequency].add(key);
        }
        StringBuilder sb = new StringBuilder();
        for (int pos = bucket.length - 1; pos >=0; pos--) {
            if (bucket[pos] != null) {
                for (char num : bucket[pos]) {
                    for (int i = 0; i < map.get(num); i++) {
                        sb.append(num);
                    }
                }
            }
        }
        return sb.toString();
    }
X. we have normal way using PriorityQueue as follows
https://discuss.leetcode.com/topic/65909/java-solution-with-heap-o-nlgn/
    public String frequencySort(String s) {
        Map<Character, Integer> map = new HashMap<>();
        for (char c : s.toCharArray()) {
            if (map.containsKey(c)) {
                map.put(c, map.get(c) + 1);
            } else {
                map.put(c, 1);
            }
        }
        PriorityQueue<Map.Entry<Character, Integer>> pq = new PriorityQueue<>(
            new Comparator<Map.Entry<Character, Integer>>() {
                @Override
                public int compare(Map.Entry<Character, Integer> a, Map.Entry<Character, Integer> b) {
                    return b.getValue() - a.getValue();
                }
            }
        );
        pq.addAll(map.entrySet());
        StringBuilder sb = new StringBuilder();
        while (!pq.isEmpty()) {
            Map.Entry e = pq.poll();
            for (int i = 0; i < (int)e.getValue(); i++) {
                sb.append(e.getKey());
            }
        }
        return sb.toString();
    }
http://www.cnblogs.com/charlesblc/p/6031645.html
用了Jave Map.Entry这个数据结构，还用到了自定义的Comparator。
http://bookshadow.com/weblog/2016/11/02/leetcode-sort-characters-by-frequency/
字符统计 + 排序
    public String frequencySort(String s) {
        HashMap<Character, Integer> charFreqMap = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            charFreqMap.put(c, charFreqMap.getOrDefault(c, 0) + 1);
        }
        ArrayList<Map.Entry<Character, Integer>> list = new ArrayList<>(charFreqMap.entrySet());
        list.sort(new Comparator<Map.Entry<Character, Integer>>(){
            public int compare(Map.Entry<Character, Integer> o1, Map.Entry<Character, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<Character, Integer> e : list) {
            for (int i = 0; i < e.getValue(); i++) {
                sb.append(e.getKey());
            }
        }
        return sb.toString();
    }
 * @author het
 *
 */
public class LeetCode451SortCharactersByFrequency {
	public String frequencySort(String s) {
        int[][] count = new int[256][2];
        
        for(char c: s.toCharArray()) {
            count[c][0] = c;
            count[c][1]++;
        }
        
        Arrays.sort(count, new Comparator<int[]>() {
           public int compare(int[] a, int[] b) {
               return a[1] == b[1] ? 0 : (a[1] < b[1] ? 1 : -1);
           } 
        });
        
        
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<256; i++) {
            if (count[i][1] > 0) {
                for(int j=0; j<count[i][1]; j++) {
                    sb.append((char)count[i][0]);
                }
            }
        }
        
        return sb.toString();
    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
