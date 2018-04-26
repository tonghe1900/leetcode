package alite.leetcode.xx2.sucess.extra;
///**
// * http://sbzhouhao.net/LeetCode/LeetCode-Shortest-Word-Distance-III.html
//Related: LeetCode [244] Shortest Word Distance II
//Given a list of words and two words word1 and word2, return the shortest distance between these two words in the list.
//word1 and word2 may be the same and they represent two individual words in the list.
//For example,
//
//Assume that words = ["practice", "makes", "perfect", "coding", "makes"].
//Given word1 = "makes", word2 = "coding", return 1. Given word1 = "makes", word2 = "makes", return 3.
//Note:
//
//You may assume word1 and word2 are both in the list.
//https://leetcode.com/discuss/50715/12-16-lines-java-c
//public int shortestWordDistance(String[] words, String word1, String word2) {
//    long dist = Integer.MAX_VALUE, i1 = dist, i2 = -dist;
//    boolean same = word1.equals(word2);
//    for (int i=0; i<words.length; i++) {
//        if (words[i].equals(word1)) {
//            if (same) {
//                i1 = i2;
//                i2 = i;
//            } else {
//                i1 = i;
//            }
//        } else if (words[i].equals(word2)) {
//            i2 = i;
//        }
//        dist = Math.min(dist, Math.abs(i1 - i2));
//    }
//    return (int) dist;
//}
//https://leetcode.com/discuss/70327/short-solution-lines-modified-from-shortest-word-distance
//
//http://buttercola.blogspot.com/2015/08/leetcode-shortest-word-distance-iii.html
//The problem is an extension of the previous one. The only diff is the word1 and word2 can be the same. It can still be easily solved by using a hash map. The question is, can we solve it by using the one-pass of the array? 
//
//The key is we cannot update the two pointers simultaneously, if they are the same. We could update one, compare the distance, and then update the other. 
//
//    public int shortestWordDistance(String[] words, String word1, String word2) {
//
//        int posA = -1;
//
//        int posB = -1;
//
//        int minDistance = Integer.MAX_VALUE;
//
//         
//
//        for (int i = 0; i < words.length; i++) {
//
//            String word = words[i];
//
//             
//
//            if (word.equals(word1)) {
//
//                posA = i;
//
//            } else if (word.equals(word2)) {
//
//                posB = i;
//
//            }
//
//             
//
//            if (posA != -1 && posB != -1 && posA != posB) {
//
//                minDistance = Math.min(minDistance, Math.abs(posA - posB));
//
//            }
//
//             
//
//            if (word1.equals(word2)) {
//
//                posB = posA;
//
//            }
//
//        }
//
//         
//
//        return minDistance;
//
//    }
//https://wxx5433.gitbooks.io/interview-preparation/content/part_ii_leetcode_lintcode/array/shortest_word_distance_iii.html
//  public int shortestWordDistance(String[] words, String word1, String word2) {
//    int index1 = -1;
//    int index2 = -1;
//    int minDistance = Integer.MAX_VALUE;
//    for (int i = 0; i < words.length; i++) {
//      if (words[i].equals(word1)) {
//        if (index1 != -1 && word1.equals(word2)) {
//          minDistance = Math.min(minDistance, Math.abs(i - index1));
//        }
//        index1 = i;
//      } else if (words[i].equals(word2)) {
//        index2 = i;
//      }
//      if (index1 != -1 && index2 != -1) {
//        minDistance = Math.min(minDistance, Math.abs(index1 - index2));
//      }
//    }
//    return minDistance;
//  }
//https://segmentfault.com/a/1190000003906667
//这题和I是一样的，唯一不同的是对于word1和word2相同的时候，我们要区分第一次遇到和第二次遇到这个词。这里加入了一个turns，如果是相同单词的话，每次遇到一个单词turn加1，这样可以根据turn来判断是否要switch。
//http://likesky3.iteye.com/blog/2236128
//    public int shortestWordDistance1(String[] words, String word1, String word2) {  
//        if (words == null) return -1;  
//        if (word1.equals(word2)) return shortestWordDistanceCaseSame(words, word1);  
//          
//        int idx1 = -1, idx2 = -1;  
//        int diff = words.length;  
//        for (int i = 0; i < words.length; i++) {  
//            if (words[i].equals(word1)) {  
//                idx1 = i;  
//                if (idx2 != -1) {  
//                    diff = Math.min(diff, idx1 - idx2);  
//                }  
//            } else if (words[i].equals(word2)) {  
//                idx2 = i;  
//                if (idx1 != -1) {  
//                    diff = Math.min(diff, idx2 - idx1);  
//                }  
//            }  
//        }  
//        return diff;  
//    }  
//    public int shortestWordDistanceCaseSame(String[] words, String word) {  
//        int prev = -1, curr = -1;  
//        int diff = words.length;  
//        for (int i = 0; i < words.length; i++) {  
//            if (words[i].equals(word)) {  
//                curr = i;  
//                if (prev != -1)  
//                    diff = Math.min(curr - prev, diff);  
//                prev = curr;  
//            }  
//        }  
//        return diff;  
//    }  
//      
//    // Method 2: all in one method  
//    public int shortestWordDistance(String[] words, String word1, String word2) {  
//        if (words == null) return -1;  
//        int idx1 = -1, idx2 = -1;  
//        int diff = words.length;  
//        for (int i = 0; i < words.length; i++) {  
//            if (words[i].equals(word1)) {  
//                if (word1.equals(word2)) {  
//                    if (idx1 != -1)   
//                        diff = Math.min(diff, i - idx1);  
//                    idx1 = i;  
//                } else {  
//                    idx1 = i;  
//                    if (idx2 != -1) {  
//                        diff = Math.min(diff, idx1 - idx2);  
//                    }  
//                }  
//            } else if (words[i].equals(word2)) {  
//                idx2 = i;  
//                if (idx1 != -1) {  
//                    diff = Math.min(diff, idx2 - idx1);  
//                }  
//            }  
//        }  
//        return diff;  
//    }  
//
//LIKE CODING: LeetCode [245] Shortest Word Distance III
//
//    int shortestWordDistance(vector<string>& words, string word1, string word2) {
//
//        int n = words.size();
//
//        int p1 = -1, p2 = -1, dist = INT_MAX;
//
//        for(int i=0; i<n; ++i){
//
//            if(word1==word2){
//
//                if(words[i]==word1){
//
//                    if(p1>p2) p2 = i;
//
//                    else p1 = i;
//
//                }
//
//            }else{
//
//                if(words[i]==word1) p1 = i;
//
//                if(words[i]==word2) p2 = i;
//
//            }
//
//            if(p1>=0 && p2>=0)
//
//                dist = min(dist, abs(p1-p2));
//
//        }
//
//        return dist;
//
//    }
//https://github.com/algorhythms/LeetCode/blob/master/245%20Shortest%20Word%20Distance%20III.py
//X. Using HashMap
//https://hzhou.me/LeetCode/LeetCode-Shortest-Word-Distance-III.html
//    public int shortestWordDistance(String[] words, String word1, String word2) {
//        Map<String, List<Integer>> map = new HashMap<>();
//
//        for (int i = 0; i < words.length; i++) {
//            String s = words[i];
//            List<Integer> list;
//            if (map.containsKey(s)) {
//                list = map.get(s);
//            } else {
//                list = new ArrayList<>();
//            }
//            list.add(i);
//            map.put(s, list);
//        }
//        List<Integer> l1 = map.get(word1);
//        List<Integer> l2 = map.get(word2);
//
//        int min = Integer.MAX_VALUE;
//
//        for (int a : l1) {
//            for (int b : l2) {
//               int dist = Math.abs(b - a);
//                if (dist != 0) {
//                    min = Math.min(dist, min);
//                }
//            }
//        }
//
//        return min;
//    }
//http://www.1point3acres.com/bbs/thread-109587-1-1.html
//只有一道题，就是minimum word index distance，一看题就乐了，重复率太高。. 鐗涗汉浜戦泦,涓€浜╀笁鍒嗗湴
///* This class will be given a list of words (such as might be tokenized
//* from a paragraph of text), and will provide a method that takes two
//* words and returns the shortest distance (in words) between those two
//* words in the provided text. 
//* Example:
//*   WordDistanceFinder finder = new WordDistanceFinder(Arrays.asList("the", "quick", "brown", "fox", "quick"));
//*   assert(finder.distance("fox","the") == 3);
//*   assert(finder.distance("quick", "fox") == 1);
//*/
//1. 先来了一发On的
//2. 如果distance方法被call很多次怎么改进. 1point3acres.com/bbs
//3. 然后说如果没有重复单词怎么改进。。. 鐗涗汉浜戦泦,涓€浜╀笁鍒嗗湴
//4. 如果想preprocessing这个list怎么做， 给两种方法，（第一种就是来一发hashmap，对所有的word pair，call distance记录下来，第二种就是来一个map，key是单词，value是array of indexes，然后问这样distance怎么算）
//5. 如果cache太大memory装不下了怎么办（LRU）
//6. 来实现一下LRU
//Read full article from LIKE CODING: LeetCode [245] Shortest Word Distance III
//
//
//
//
//LIKE CODING: LeetCode [245] Shortest Word Distance III
//
//http://sbzhouhao.net/LeetCode/LeetCode-Shortest-Word-Distance-III.html
//Related: LeetCode [244] Shortest Word Distance II
//Given a list of words and two words word1 and word2, return the shortest distance between these two words in the list.
//word1 and word2 may be the same and they represent two individual words in the list.
//For example,
//
//Assume that words = ["practice", "makes", "perfect", "coding", "makes"].
//Given word1 = "makes", word2 = "coding", return 1. Given word1 = "makes", word2 = "makes", return 3.
//Note:
//
//You may assume word1 and word2 are both in the list.
//https://leetcode.com/discuss/50715/12-16-lines-java-c
//public int shortestWordDistance(String[] words, String word1, String word2) {
//    long dist = Integer.MAX_VALUE, i1 = dist, i2 = -dist;
//    boolean same = word1.equals(word2);
//    for (int i=0; i<words.length; i++) {
//        if (words[i].equals(word1)) {
//            if (same) {
//                i1 = i2;
//                i2 = i;
//            } else {
//                i1 = i;
//            }
//        } else if (words[i].equals(word2)) {
//            i2 = i;
//        }
//        dist = Math.min(dist, Math.abs(i1 - i2));
//    }
//    return (int) dist;
//}
//https://leetcode.com/discuss/70327/short-solution-lines-modified-from-shortest-word-distance
//
//http://buttercola.blogspot.com/2015/08/leetcode-shortest-word-distance-iii.html
//The problem is an extension of the previous one. The only diff is the word1 and word2 can be the same. It can still be easily solved by using a hash map. The question is, can we solve it by using the one-pass of the array? 
//
//The key is we cannot update the two pointers simultaneously, if they are the same. We could update one, compare the distance, and then update the other. 
//
//    public int shortestWordDistance(String[] words, String word1, String word2) {
//
//        int posA = -1;
//
//        int posB = -1;
//
//        int minDistance = Integer.MAX_VALUE;
//
//         
//
//        for (int i = 0; i < words.length; i++) {
//
//            String word = words[i];
//
//             
//
//            if (word.equals(word1)) {
//
//                posA = i;
//
//            } else if (word.equals(word2)) {
//
//                posB = i;
//
//            }
//
//             
//
//            if (posA != -1 && posB != -1 && posA != posB) {
//
//                minDistance = Math.min(minDistance, Math.abs(posA - posB));
//
//            }
//
//             
//
//            if (word1.equals(word2)) {
//
//                posB = posA;
//
//            }
//
//        }
//
//         
//
//        return minDistance;
//
//    }
//https://wxx5433.gitbooks.io/interview-preparation/content/part_ii_leetcode_lintcode/array/shortest_word_distance_iii.html
//  public int shortestWordDistance(String[] words, String word1, String word2) {
//    int index1 = -1;
//    int index2 = -1;
//    int minDistance = Integer.MAX_VALUE;
//    for (int i = 0; i < words.length; i++) {
//      if (words[i].equals(word1)) {
//        if (index1 != -1 && word1.equals(word2)) {
//          minDistance = Math.min(minDistance, Math.abs(i - index1));
//        }
//        index1 = i;
//      } else if (words[i].equals(word2)) {
//        index2 = i;
//      }
//      if (index1 != -1 && index2 != -1) {
//        minDistance = Math.min(minDistance, Math.abs(index1 - index2));
//      }
//    }
//    return minDistance;
//  }
//https://segmentfault.com/a/1190000003906667
//这题和I是一样的，唯一不同的是对于word1和word2相同的时候，我们要区分第一次遇到和第二次遇到这个词。这里加入了一个turns，如果是相同单词的话，每次遇到一个单词turn加1，这样可以根据turn来判断是否要switch。
//http://likesky3.iteye.com/blog/2236128
//    public int shortestWordDistance1(String[] words, String word1, String word2) {  
//        if (words == null) return -1;  
//        if (word1.equals(word2)) return shortestWordDistanceCaseSame(words, word1);  
//          
//        int idx1 = -1, idx2 = -1;  
//        int diff = words.length;  
//        for (int i = 0; i < words.length; i++) {  
//            if (words[i].equals(word1)) {  
//                idx1 = i;  
//                if (idx2 != -1) {  
//                    diff = Math.min(diff, idx1 - idx2);  
//                }  
//            } else if (words[i].equals(word2)) {  
//                idx2 = i;  
//                if (idx1 != -1) {  
//                    diff = Math.min(diff, idx2 - idx1);  
//                }  
//            }  
//        }  
//        return diff;  
//    }  
//    public int shortestWordDistanceCaseSame(String[] words, String word) {  
//        int prev = -1, curr = -1;  
//        int diff = words.length;  
//        for (int i = 0; i < words.length; i++) {  
//            if (words[i].equals(word)) {  
//                curr = i;  
//                if (prev != -1)  
//                    diff = Math.min(curr - prev, diff);  
//                prev = curr;  
//            }  
//        }  
//        return diff;  
//    }  
//      
//    // Method 2: all in one method  
//    public int shortestWordDistance(String[] words, String word1, String word2) {  
//        if (words == null) return -1;  
//        int idx1 = -1, idx2 = -1;  
//        int diff = words.length;  
//        for (int i = 0; i < words.length; i++) {  
//            if (words[i].equals(word1)) {  
//                if (word1.equals(word2)) {  
//                    if (idx1 != -1)   
//                        diff = Math.min(diff, i - idx1);  
//                    idx1 = i;  
//                } else {  
//                    idx1 = i;  
//                    if (idx2 != -1) {  
//                        diff = Math.min(diff, idx1 - idx2);  
//                    }  
//                }  
//            } else if (words[i].equals(word2)) {  
//                idx2 = i;  
//                if (idx1 != -1) {  
//                    diff = Math.min(diff, idx2 - idx1);  
//                }  
//            }  
//        }  
//        return diff;  
//    }  
//
//LIKE CODING: LeetCode [245] Shortest Word Distance III
//
//    int shortestWordDistance(vector<string>& words, string word1, string word2) {
//
//        int n = words.size();
//
//        int p1 = -1, p2 = -1, dist = INT_MAX;
//
//        for(int i=0; i<n; ++i){
//
//            if(word1==word2){
//
//                if(words[i]==word1){
//
//                    if(p1>p2) p2 = i;
//
//                    else p1 = i;
//
//                }
//
//            }else{
//
//                if(words[i]==word1) p1 = i;
//
//                if(words[i]==word2) p2 = i;
//
//            }
//
//            if(p1>=0 && p2>=0)
//
//                dist = min(dist, abs(p1-p2));
//
//        }
//
//        return dist;
//
//    }
//https://github.com/algorhythms/LeetCode/blob/master/245%20Shortest%20Word%20Distance%20III.py
//X. Using HashMap
//https://hzhou.me/LeetCode/LeetCode-Shortest-Word-Distance-III.html
//    public int shortestWordDistance(String[] words, String word1, String word2) {
//        Map<String, List<Integer>> map = new HashMap<>();
//
//        for (int i = 0; i < words.length; i++) {
//            String s = words[i];
//            List<Integer> list;
//            if (map.containsKey(s)) {
//                list = map.get(s);
//            } else {
//                list = new ArrayList<>();
//            }
//            list.add(i);
//            map.put(s, list);
//        }
//        List<Integer> l1 = map.get(word1);
//        List<Integer> l2 = map.get(word2);
//
//        int min = Integer.MAX_VALUE;
//
//        for (int a : l1) {
//            for (int b : l2) {
//               int dist = Math.abs(b - a);
//                if (dist != 0) {
//                    min = Math.min(dist, min);
//                }
//            }
//        }
//
//        return min;
//    }
//http://www.1point3acres.com/bbs/thread-109587-1-1.html
//只有一道题，就是minimum word index distance，一看题就乐了，重复率太高。. 鐗涗汉浜戦泦,涓€浜╀笁鍒嗗湴
///* This class will be given a list of words (such as might be tokenized
//* from a paragraph of text), and will provide a method that takes two
//* words and returns the shortest distance (in words) between those two
//* words in the provided text. 
//* Example:
//*   WordDistanceFinder finder = new WordDistanceFinder(Arrays.asList("the", "quick", "brown", "fox", "quick"));
//*   assert(finder.distance("fox","the") == 3);
//*   assert(finder.distance("quick", "fox") == 1);
//*/
//1. 先来了一发On的
//2. 如果distance方法被call很多次怎么改进. 1point3acres.com/bbs
//3. 然后说如果没有重复单词怎么改进。。. 鐗涗汉浜戦泦,涓€浜╀笁鍒嗗湴
//4. 如果想preprocessing这个list怎么做， 给两种方法，（第一种就是来一发hashmap，对所有的word pair，call distance记录下来，第二种就是来一个map，key是单词，value是array of indexes，然后问这样distance怎么算）
//5. 如果cache太大memory装不下了怎么办（LRU）
//6. 来实现一下LRU
//Read full article from LIKE CODING: LeetCode [245] Shortest Word Distance III
// * @author het
// *
// */
public class LeetCode245ShortestWordDistanceIII {
	  public int shortestWordDistance1(String[] words, String word1, String word2) {  
	        if (words == null) return -1;  
	        if (word1.equals(word2)) return shortestWordDistanceCaseSame(words, word1);  
	          
	        int idx1 = -1, idx2 = -1;  
	        int diff = words.length;  
	        for (int i = 0; i < words.length; i++) {  
	            if (words[i].equals(word1)) {  
	                idx1 = i;  
	                if (idx2 != -1) {  
	                    diff = Math.min(diff, idx1 - idx2);  
	                }  
	            } else if (words[i].equals(word2)) {  
	                idx2 = i;  
	                if (idx1 != -1) {  
	                    diff = Math.min(diff, idx2 - idx1);  
	                }  
	            }  
	        }  
	        return diff;  
	    }  
	    public int shortestWordDistanceCaseSame(String[] words, String word) {  
	        int prev = -1, curr = -1;  
	        int diff = words.length;  
	        for (int i = 0; i < words.length; i++) {  
	            if (words[i].equals(word)) {  
	                curr = i;  
	                if (prev != -1)  
	                    diff = Math.min(curr - prev, diff);  
	                prev = curr;  
	            }  
	        }  
	        return diff;  
	    }  
	      
	    // Method 2: all in one method  
	    public int shortestWordDistance(String[] words, String word1, String word2) {  
	        if (words == null) return -1;  
	        int idx1 = -1, idx2 = -1;  
	        int diff = words.length;  
	        for (int i = 0; i < words.length; i++) {  
	            if (words[i].equals(word1)) {  
	                if (word1.equals(word2)) {  
	                    if (idx1 != -1)   
	                        diff = Math.min(diff, i - idx1);  
	                    idx1 = i;  
	                } else {  
	                    idx1 = i;  
	                    if (idx2 != -1) {  
	                        diff = Math.min(diff, idx1 - idx2);  
	                    }  
	                }  
	            } else if (words[i].equals(word2)) {  
	                idx2 = i;  
	                if (idx1 != -1) {  
	                    diff = Math.min(diff, idx2 - idx1);  
	                }  
	            }  
	        }  
	        return diff;  
	    }  
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
