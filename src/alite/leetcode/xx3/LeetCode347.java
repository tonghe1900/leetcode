package alite.leetcode.xx3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

///**
// * LeetCode 347 - Top K Frequent Elements
//
//https://leetcode.com/problems/top-k-frequent-elements/
//Given a non-empty array of integers, return the k most frequent elements.
//For example,
//Given [1,1,1,2,2,3] and k = 2, return [1,2].
//Note: 
//You may assume k is always valid, 1 ≤ k ≤ number of unique elements.
//Your algorithm's time complexity must be better than O(n log n), where n is the array's size.
//https://discuss.leetcode.com/topic/48158/3-java-solution-using-array-maxheap-treemap
//    public List<Integer> topKFrequent(int[] nums, int k) {
//        Map<Integer, Integer> map = new HashMap<>();
//        for(int n: nums){
//            map.put(n, map.getOrDefault(n,0)+1);
//        }
//        
//        // corner case: if there is only one number in nums, we need the bucket has index 1.
//        List<Integer>[] bucket = new List[nums.length+1];
//        for(int n:map.keySet()){
//            int freq = map.get(n);
//            if(bucket[freq]==null)
//                bucket[freq] = new LinkedList<>();
//            bucket[freq].add(n);
//        }
//        
//        List<Integer> res = new LinkedList<>();
//        for(int i=bucket.length-1; i>0 && k>0; --i){
//            if(bucket[i]!=null){
//                List<Integer> list = bucket[i]; 
//                res.addAll(list);
//                k-= list.size();
//            }
//        }
//        
//        return res;
//    }
//}
//
//
//
//// use maxHeap. Put entry into maxHeap so we can always poll a number with largest frequency
//public class Solution {
//    public List<Integer> topKFrequent(int[] nums, int k) {
//        Map<Integer, Integer> map = new HashMap<>();
//        for(int n: nums){
//            map.put(n, map.getOrDefault(n,0)+1);
//        }
//           
//        PriorityQueue<Map.Entry<Integer, Integer>> maxHeap = 
//                         new PriorityQueue<>((a,b)->(b.getValue()-a.getValue()));
//        for(Map.Entry<Integer,Integer> entry: map.entrySet()){
//            maxHeap.add(entry);
//        }
//        
//        List<Integer> res = new ArrayList<>();
//        while(res.size()<k){
//            Map.Entry<Integer, Integer> entry = maxHeap.poll();
//            res.add(entry.getKey());
//        }
//        return res;
//    }
//}
//
//
//
//// use treeMap. Use freqncy as the key so we can get all freqencies in order
//public class Solution {
//    public List<Integer> topKFrequent(int[] nums, int k) {
//        Map<Integer, Integer> map = new HashMap<>();
//        for(int n: nums){
//            map.put(n, map.getOrDefault(n,0)+1);
//        }
//        
//        TreeMap<Integer, List<Integer>> freqMap = new TreeMap<>();
//        for(int num : map.keySet()){
//           int freq = map.get(num);
//           if(!freqMap.containsKey(freq)){
//               freqMap.put(freq, new LinkedList<>());
//           }
//           freqMap.get(freq).add(num);
//        }
//        
//        List<Integer> res = new ArrayList<>();
//        while(res.size()<k){
//            Map.Entry<Integer, List<Integer>> entry = freqMap.pollLastEntry();
//            res.addAll(entry.getValue());
//        }
//        return res;
//    }
//X. Quick Select
//A more efficient O(n) time and O(n) space solution
//We can solve this problem in linear time by using the QuickSelect for selecting the kth smallest/largest number in an array. 
//
//The idea is that if we know the kth largest (same as (n-k)th smallest) frequency among all the frequencies of the words then we can select first k elements that has frequency less than or equal to the kth frequency. This is only O(n) time scan. So, the algorithm runs with O(n) and O(n) space only. Below is the implementation of the above idea using the kth smallest implementation I posted earlier.
//public String[] topKWordsSelect(final String stream, final int k) {
//    final Map<String, Integer> frequencyMap = new HashMap<String, Integer>();
//
//    final String[] words = stream.toLowerCase().trim().split(" ");
//    for (final String word : words) {
//        int freq = 1;
//        if (frequencyMap.containsKey(word)) {
//            freq = frequencyMap.get(word) + 1;
//        }
//
//        // update the frequency map
//        frequencyMap.put(word, freq);
//    }
//
//    // Find kth largest frequency which is same as (n-k)th smallest frequency
//    final int[] frequencies = new int[frequencyMap.size()];
//    int i = 0;
//    for (final int value : frequencyMap.values()) {
//        frequencies[i++] = value;
//    }
//    final int kthLargestFreq = kthSmallest(frequencies, 0, i - 1, i - k);
//
//    // extract the top K
//    final String[] topK = new String[k];
//    i = 0;
//    for (final java.util.Map.Entry<String, Integer> entry : frequencyMap.entrySet()) {
//        if (entry.getValue().intValue() >= kthLargestFreq) {
//            topK[i++] = entry.getKey();
//            if (i == k) {
//                break;
//            }
//        }
//    }
//
//    return topK;
//}
//
//https://leetcode.com/discuss/36991/java-quick-select
//Personally, the most straightforward way is to use quick select. There is a simple conversion: Find kith largest element is equivalent to find (n - k)th smallest element in array. It is worth mentioning that (n - k) is the real index (start from 0) of an element.
//
//X. Using PriorityQueue
//http://www.zrzahid.com/top-k-or-k-most-frequent-words-in-a-document/
//https://thorcsblog.wordpress.com/2016/05/05/leetcode-top-k-frequent-elements/
//public String[] topKWords(final String stream, final int k) {
//    final class WordFreq implements Comparable<WordFreq> {
//        String word;
//        int freq;
//
//        public WordFreq(final String w, final int c) {
//            word = w;
//            freq = c;
//        }
//
//        @Override
//        public int compareTo(final WordFreq other) {
//            return Integer.compare(this.freq, other.freq);
//        }
//    }
//    final Map<String, Integer> frequencyMap = new HashMap<String, Integer>();
//    final PriorityQueue<WordFreq> topKHeap = new PriorityQueue<WordFreq>(k);
//
//    final String[] words = stream.toLowerCase().trim().split(" ");
//    for (final String word : words) {
//        int freq = 1;
//        if (frequencyMap.containsKey(word)) {
//            freq = frequencyMap.get(word) + 1;
//        }
//
//        // update the frequency map
//        frequencyMap.put(word, freq);
//    }
//
//    // Build the topK heap
//    for (final java.util.Map.Entry<String, Integer> entry : frequencyMap.entrySet()) {
//        if (topKHeap.size() < k) {
//            topKHeap.add(new WordFreq(entry.getKey(), entry.getValue()));
//        } else if (entry.getValue() > topKHeap.peek().freq) {
//            topKHeap.remove();
//            topKHeap.add(new WordFreq(entry.getKey(), entry.getValue()));
//        }
//    }
//
//    // extract the top K
//    final String[] topK = new String[k];
//    int i = 0;
//    while (topKHeap.size() > 0) {
//        topK[i++] = topKHeap.remove().word;
//    }
//
//    return topK;
//}
//
//
//http://www.jiuzhang.com/solutions/top-k-frequent-words/
//class Pair {
//    String key;
//    int value;
//    
//    Pair(String key, int value) {
//        this.key = key;
//        this.value = value;
//    }
//}
//
//public class Solution {
//    /**
//     * @param words an array of string
//     * @param k an integer
//     * @return an array of string
//     */
//     
//    private Comparator<Pair> pairComparator = new Comparator<Pair>() {
//        public int compare(Pair left, Pair right) {
//            if (left.value != right.value) {
//                return left.value - right.value;
//            }
//            return right.key.compareTo(left.key);
//        }
//    };
//    
//    public String[] topKFrequentWords(String[] words, int k) {
//        if (k == 0) {
//            return new String[0];
//        }
//        
//        HashMap<String, Integer> counter = new HashMap<>();
//        for (String word : words) {
//            if (counter.containsKey(word)) {
//                counter.put(word, counter.get(word) + 1);
//            } else {
//                counter.put(word, 1);
//            }
//        }
//        
//        PriorityQueue<Pair> Q = new PriorityQueue<Pair>(k, pairComparator);
//        for (String word : counter.keySet()) {
//            Pair peak = Q.peek();
//            Pair newPair = new Pair(word, counter.get(word));
//            if (Q.size() < k) {
//                Q.add(newPair);
//            } else if (pairComparator.compare(newPair, peak) > 0) {
//                Q.poll();
//                Q.add(new Pair(word, counter.get(word)));
//            }
//        }
//        
//        String[] result = new String[k];
//        int index = 0;
//        while (!Q.isEmpty()) {
//            result[index++] = Q.poll().key;
//        }
//        
//        // reverse
//        for (int i = 0; i < index / 2; i++) {
//            String temp = result[i];
//            result[i] = result[index - i - 1];
//            result[index - i - 1] = temp;
//        }
//        
//        return result;
//     }
//http://www.programcreek.com/2014/05/leetcode-top-k-frequent-elements-java/
//https://leetcode.com/discuss/100561/java-a-simple-accepted-solution
//Map<Integer, Integer> countMap = new HashMap<>();
//    List<Integer> ret = new ArrayList<>();
//    for (int n : nums) {
//        if (countMap.containsKey(n)) {
//            countMap.put(n ,countMap.get(n)+1);
//        } else {
//            countMap.put(n ,1);
//        }
//    }
//   PriorityQueue<Map.Entry<Integer, Integer>> pq =
//            new PriorityQueue<Map.Entry<Integer, Integer>>((o1, o2) -> o2.getValue() - o1.getValue());
//    pq.addAll(countMap.entrySet());
//
//    List<Integer> ret = new ArrayList<>();
//    for(int i = 0; i < k; i++){
//        ret.add(pq.poll().getKey());
//    }
//    return ret;
//https://leetcode.com/discuss/100581/java-o-n-solution-bucket-sort
//public List<Integer> topKFrequent(int[] nums, int k) {
//
//    List<Integer>[] bucket = new List[nums.length + 1];
//    Map<Integer, Integer> frequencyMap = new HashMap<Integer, Integer>();
//
//    for (int n : nums) {
//        frequencyMap.put(n, frequencyMap.getOrDefault(n, 0) + 1);
//    }
//
//    for (int key : frequencyMap.keySet()) {
//        int frequency = frequencyMap.get(key);
//        if (bucket[frequency] == null) {
//            bucket[frequency] = new ArrayList<>();
//        }
//        bucket[frequency].add(key);
//    }
//
//    List<Integer> res = new ArrayList<>();
//
//    for (int pos = bucket.length - 1; pos >= 0 && res.size() < k; pos--) {
//        if (bucket[pos] != null) {
//            res.addAll(bucket[pos]);
//        }
//    }
//    return res;
//}
//
//X. http://bookshadow.com/weblog/2016/05/02/leetcode-top-k-frequent-elements/
//http://www.guoting.org/leetcode/leetcode-347-top-k-frequent-elements/
//我们准备n+1个桶(n为数组长度,其实0号桶没有使用),当统计完数组中每个数出现的次数之后,根据每个数字出现的次数把数字放到相应的桶里,统计完之后,我们依次从编号最大的桶开始依次取数。
//1. 遍历数组nums，利用字典cntDict统计各元素出现次数。
//2. 遍历cntDict，利用嵌套列表freqList记录出现次数为i（ i∈[1, n] ）的所有元素
//3. 逆序遍历freqList，将其中的前k个元素输出。
//    public List<Integer> topKFrequent(int[] nums, int k){
//        List<Integer>[] bucket=new List[nums.length+1];
//        Map<Integer,Integer> frequencyMap=new HashMap<>();
//        for(int n:nums){
//            frequencyMap.put(n,frequencyMap.getOrDefault(n,0)+1);
//        }
//
//        for(int key:frequencyMap.keySet()){
//            int frequency=frequencyMap.get(key);
//            if(bucket[frequency]==null){
//                bucket[frequency]=new ArrayList<>();
//            }
//            bucket[frequency].add(key);
//        }
//
//        List<Integer> res=new ArrayList<>();
//        for(int pos=bucket.length-1;pos>=0;pos--){
//            if(bucket[pos]!=null){
//                for(Integer i:bucket[pos]){
//                    if(res.size()<k){
//                        res.add(i);
//                    }else{
//                        return res;
//                    }
//                }
//            }
//        }
//        return res;
//    }
//https://leetcode.com/discuss/100581/java-o-n-solution-bucket-sort
//http://buttercola.blogspot.com/2016/06/leetcode-347-top-k-frequent-elements.html
//
//http://yuanhsh.iteye.com/blog/2200539
//Approach 3: O(N + K log N) time
//This approach is similar to approach 2 but the main difference is that we make a MAX-HEAP of all the U elements. So the first step is to make the max heap of all the elements in O(U). Then remove the maximum element from the heap K times in O(K log U) time. The K removed elements are the desired most frequent elements. The time complexity of this method is O(U + K log U) and by setting U = O(N) we get O(N + K log N). 
//
//Let us stop for a moment and contrast approach 2 from 3. For simplicity let T2 = K + N log K be the time complexity of approach 2 and T3 = N + K log N be the time complexity of the third approach. Figure below plots the ratio T2/T3 for N=100 and for different values of K. We observe that approach 3 is considerably better for small values of K whereas approach 2 is better for large values of K. Though actual difference depends on the constants involved we can still see the merit of one approach over another. 
//
//    public PriorityQueue(Collection<? extends E> c) {
//        if (c instanceof SortedSet<?>) {
//            SortedSet<? extends E> ss = (SortedSet<? extends E>) c;
//            this.comparator = (Comparator<? super E>) ss.comparator();
//            initElementsFromCollection(ss);
//        }
//        else if (c instanceof PriorityQueue<?>) {
//            PriorityQueue<? extends E> pq = (PriorityQueue<? extends E>) c;
//            this.comparator = (Comparator<? super E>) pq.comparator();
//            initFromPriorityQueue(pq);
//        }
//        else {
//            this.comparator = null;
//            initFromCollection(c);
//        }
//
//    }
//    private void initElementsFromCollection(Collection<? extends E> c) {
//        Object[] a = c.toArray();
//        // If c.toArray incorrectly doesn't return Object[], copy it.
//        if (a.getClass() != Object[].class)
//            a = Arrays.copyOf(a, a.length, Object[].class);
//        int len = a.length;
//        if (len == 1 || this.comparator != null)
//            for (int i = 0; i < len; i++)
//                if (a[i] == null)
//                    throw new NullPointerException();
//        this.queue = a;
//        this.size = a.length;
//
//    }
//    private void heapify() {
//        for (int i = (size >>> 1) - 1; i >= 0; i--)
//            siftDown(i, (E) queue[i]);
//    }
//
//https://discuss.leetcode.com/topic/44313/3-ways-to-solve-this-problem
// * @author het
// *
// */
public class LeetCode347 {
	public class Solution {
	    public List<Integer> topKFrequent(int[] nums, int k) {
	        Map<Integer, Integer> map = new HashMap<>();
	        for(int n: nums){
	            map.put(n, map.getOrDefault(n,0)+1);
	        }
//	        PriorityQueue<Map.Entry<Integer, Integer>> maxHeap = 
//                    new PriorityQueue<>((a,b)->(b.getValue()-a.getValue()));
	           
	        PriorityQueue<Map.Entry<Integer, Integer>> maxHeap = 
	                         new PriorityQueue<>((a,b)->(b.getValue()-a.getValue()));
	        for(Map.Entry<Integer,Integer> entry: map.entrySet()){
	            maxHeap.add(entry);
	        }
	        
	        List<Integer> res = new ArrayList<>();
	        while(res.size()<k){
	            Map.Entry<Integer, Integer> entry = maxHeap.poll();
	            res.add(entry.getKey());
	        }
	        return res;
	    }
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
