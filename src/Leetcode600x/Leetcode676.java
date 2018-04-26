package Leetcode600x;
/**
 * 676. Implement Magic Dictionary
DescriptionHintsSubmissionsDiscussSolution
Implement a magic directory with buildDict, and search methods.

For the method buildDict, you'll be given a list of non-repetitive words to build a dictionary.

For the method search, you'll be given a word, and judge whether if you modify exactly one character into another character in this word, the modified word is in the dictionary you just built.

Example 1:
Input: buildDict(["hello", "leetcode"]), Output: Null
Input: search("hello"), Output: False
Input: search("hhllo"), Output: True
Input: search("hell"), Output: False
Input: search("leetcoded"), Output: False
Note:
You may assume that all the inputs are consist of lowercase letters a-z.
For contest purpose, the test data is rather small by now. You could think about highly efficient algorithm after the contest.
Please remember to RESET your class variables declared in class MagicDictionary, as static/class variables are persisted across multiple test cases. Please see here for more details.
Seen this question in a real interview before?
 * @author tonghe
 *
 */
public class Leetcode676 {
	Approach #1: Brute Force with Bucket-By-Length [Accepted]
			Intuition and Algorithm

			Call two strings neighbors if exactly one character can be changed in one to make the strings equal (ie. their hamming distance is 1.)

			Strings can only be neighbors if their lengths are equal. When searching a new word, let's check only the words that are the same length.

			Python

			class MagicDictionary(object):
			    def __init__(self):
			        self.buckets = collections.defaultdict(list)

			    def buildDict(self, words):
			        for word in words:
			            self.buckets[len(word)].append(word)

			    def search(self, word):
			        return any(sum(a!=b for a,b in zip(word, candidate)) == 1
			                   for candidate in self.buckets[len(word)])
			Java

			class MagicDictionary {
			    Map<Integer, ArrayList<String>> buckets;
			    public MagicDictionary() {
			        buckets = new HashMap();
			    }

			    public void buildDict(String[] words) {
			        for (String word: words) {
			            buckets.computeIfAbsent(word.length(), x -> new ArrayList()).add(word);
			        }
			    }

			    public boolean search(String word) {
			        if (!buckets.containsKey(word.length())) return false;
			        for (String candidate: buckets.get(word.length())) {
			            int mismatch = 0;
			            for (int i = 0; i < word.length(); ++i) {
			                if (word.charAt(i) != candidate.charAt(i)) {
			                    if (++mismatch > 1) break;
			                }
			            }
			            if (mismatch == 1) return true;
			        }
			        return false;
			    }
			}
			Complexity Analysis

			Time Complexity: O(S)O(S) to build and O(NK)O(NK) to search, where NN is the number of words in our magic dictionary, SS is the total number of letters in it, and KK is the length of the search word.

			Space Complexity: O(S)O(S), the space used by buckets.

			Approach #2: Generalized Neighbors [Accepted]
			Intuition

			Recall in Approach #1 that two words are neighbors if exactly one character can be changed in one word to make the strings equal.

			Let's say a word 'apple' has generalized neighbors '*pple', 'a*ple', 'ap*le', 'app*e', and 'appl*'. When searching for whether a word like 'apply' has a neighbor like 'apple', we only need to know whether they have a common generalized neighbor.

			Algorithm

			Continuing the above thinking, one issue is that 'apply' is not a neighbor with itself, yet it has the same generalized neighbor '*pply'. To remedy this, we'll count how many sources generated '*pply'. If there are 2 or more, then one of them won't be 'apply'. If there is exactly one, we should check that it wasn't 'apply'. In either case, we can be sure that there was some magic word generating '*pply' that wasn't 'apply'.

			class MagicDictionary(object):
			    def _genneighbors(self, word):
			        for i in xrange(len(word)):
			            yield word[:i] + '*' + word[i+1:]

			    def buildDict(self, words):
			        self.words = set(words)
			        self.count = collections.Counter(nei for word in words
			                                        for nei in self._genneighbors(word))

			    def search(self, word):
			        return any(self.count[nei] > 1 or
			                   self.count[nei] == 1 and word not in self.words
			                   for nei in self._genneighbors(word))
			public class MagicDictionary {
			    Set<String> words;
			    Map<String, Integer> count;

			    public MagicDictionary() {
			        words = new HashSet();
			        count = new HashMap();
			    }

			    private ArrayList<String> generalizedNeighbors(String word) {
			        ArrayList<String> ans = new ArrayList();
			        char[] ca = word.toCharArray();
			        for (int i = 0; i < word.length(); ++i) {
			            char letter = ca[i];
			            ca[i] = '*';
			            String magic = new String(ca);
			            ans.add(magic);
			            ca[i] = letter;
			        }
			        return ans;
			    }

			    public void buildDict(String[] words) {
			        for (String word: words) {
			            this.words.add(word);
			            for (String nei: generalizedNeighbors(word)) {
			                count.put(nei, count.getOrDefault(nei, 0) + 1);
			            }
			        }
			    }

			    public boolean search(String word) {
			        for (String nei: generalizedNeighbors(word)) {
			            int c = count.getOrDefault(nei, 0);
			            if (c > 1 || c == 1 && !words.contains(word)) return true;
			        }
			        return false;
			    }
			}
			Complexity Analysis

			Time Complexity: O(\sum w_i^2)O(∑w
			​i
			​2
			​​ ) to build and O(K^2)O(K
			​2
			​​ ) to search, where w_iw
			​i
			​​  is the length of words[i], and KK is the length of our search word.

			Space Complexity: O(\sum w_i^2)O(∑w
			​i
			​2
			​​ ), the space used by count. We also use O(K^2)O(K
			​2
			​​ ) space when generating neighbors to search.

			Analysis written by: @awice.
}
