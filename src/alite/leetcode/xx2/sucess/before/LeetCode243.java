package alite.leetcode.xx2.sucess.before;
/**
 * LeetCode 243 - Shortest Word Distance

LeetCode: Shortest Word Distance | CrazyEgg
Given a list of words and two words word1 and word2, return the shortest distance between these two words in the list.
For example,
Assume that words = ["practice", "makes", "perfect", "coding", "makes"].
Given word1 = "coding", word2 = "practice", return 3. Given word1 = "makes", word2 = "coding", return 1.
Note:
You may assume that word1 does not equal to word2, and word1 and word2 are both in the list.
http://segmentfault.com/a/1190000003906667
https://leetcode.com/discuss/50192/java-solution-with-one-for-loop
一个指针指向word1上次出现的位置，一个指针指向word2上次出现的位置。因为两个单词如果比较接近的话，肯定是相邻的word1和word2的位置之差，所以我们只要每次得到一个新位置和另一个单词的位置比较一下就行了。
    public int shortestDistance(String[] words, String word1, String word2) {
        int idx1 = -1, idx2 = -1, distance = Integer.MAX_VALUE;
        for(int i = 0; i < words.length; i++){
            if(words[i].equals(word1)){
                idx1 = i;
                // 第一次写入idx就先不比较
                if(idx2 != -1) distance = Math.min(distance, idx1 - idx2);
            }
            else if(words[i].equals(word2)){
                idx2 = i;
                // 第一次写入idx就先不比较
                if(idx1 != -1) distance = Math.min(distance, idx2 - idx1);
            }
        }
        return distance;
    }
https://leetcode.com/discuss/50234/ac-java-clean-solution
public int shortestDistance(String[] words, String word1, String word2) {
    int p1 = -1, p2 = -1, min = Integer.MAX_VALUE;

    for (int i = 0; i < words.length; i++) {
        if (words[i].equals(word1))
            p1 = i;

        if (words[i].equals(word2))
            p2 = i;

        if (p1 != -1 && p2 != -1)
            min = Math.min(min, Math.abs(p1 - p2));
    }
    return min;
}
https://leetcode.com/discuss/50185/java-solution-using-minimum-difference-between-sorted-arrays
public int shortestDistance(String[] words, String word1, String word2) {
    List<Integer> w1occ=new ArrayList<Integer>();
    List<Integer> w2occ=new ArrayList<Integer>();

    for (int i=0; i<words.length; ++i){
        if (words[i].equals(word1)){
            w1occ.add(i);
        }
        if (words[i].equals(word2)){
            w2occ.add(i);
        }
    }

    int min=words.length;
    int p1=0;
    int p2=0;
    while (p1<w1occ.size() && p2<w2occ.size()){
        min=Math.min(Math.abs(w1occ.get(p1)-w2occ.get(p2)), min);
        if (w1occ.get(p1)<w2occ.get(p2)){
            p1++;
        } else
            p2++;
    }
    return min;
}
http://sbzhouhao.net/LeetCode/LeetCode-Shortest-Word-Distance.html
Not efficient in this case. But it's good if getShortDistance are called multiple times for same input.
http://www.1point3acres.com/bbs/forum.php?mod=viewthread&tid=159612&pid=2122185&page=1&extra=page%3D5%26filter%3Dsortid%26sortid%3D311#pid2122185
/* This class will be given a list of words (such as might be tokenized
* from a paragraph of text), and will provide a method that takes two
* words and returns the shortest distance (in words) between those two
* words in the provided text.
* Example:
*   WordDistanceFinder finder = new WordDistanceFinder(Arrays.asList("the", "quick", "brown", "fox", "quick"));
*   assert(finder.distance("fox","the") == 3);
*   assert(finder.distance("quick", "fox") == 1);
* "quick" appears twice in the input. There are two possible distance values for "quick" and "fox":.
*     (3 - 1) = 2 and (4 - 3) = 1..1
* Since we have to return the shortest distance between the two words we return 1.
*/
//public class WordDistanceFinder {. 鍥磋鎴戜滑@1point 3 acres
//    public WordDistanceFinder (List<String> words) {
//        // implementation here
//    }
//    public int distance (String wordOne, String wordTwo) {. visit 1point3acres.com for more.
//        // implementation here
//    }
//}
//
//    public int shortestDistance(String[] words, String word1, String word2) {
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
//                min = Math.min(Math.abs(b - a), min);
//            }
//        }
//
//        return min;
//    }
//Read full article from LeetCode: Shortest Word Distance | CrazyEgg
// * @author het
// *
// */
public class LeetCode243 {
    	
    	
    	//一个指针指向word1上次出现的位置，一个指针指向word2上次出现的位置。因为两个单词如果比较接近的话，肯定是相邻的word1和word2的位置之差，所以我们只要每次得到一个新位置和另一个单词的位置比较一下就行了。
        public int shortestDistance(String[] words, String word1, String word2) {
            int idx1 = -1, idx2 = -1, distance = Integer.MAX_VALUE;
            for(int i = 0; i < words.length; i++){
                if(words[i].equals(word1)){
                    idx1 = i;
                    // 第一次写入idx就先不比较
                    if(idx2 != -1) distance = Math.min(distance, idx1 - idx2);
                }
                else if(words[i].equals(word2)){
                    idx2 = i;
                    // 第一次写入idx就先不比较
                    if(idx1 != -1) distance = Math.min(distance, idx2 - idx1);
                }
            }
            return distance;
        }

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
