package alite.leetcode.xx3.extra;
/**
 * LeetCode 358 - Rearrange String k Distance Apart

http://blog.csdn.net/jmspan/article/details/51678257
Given a non-empty string str and an integer k, rearrange the string such that the same characters are at least distance k f
rom each other.
All input strings are given in lowercase letters. If it is not possible to rearrange the string, return an empty string "".
Example 1:
str = "aabbcc", k = 3

Result: "abcabc"

The same letters are at least distance 3 from each other.
Example 2:
str = "aaabc", k = 3 

Answer: ""

It is not possible to rearrange the string.
Example 3:
str = "aaadbbcc", k = 2

Answer: "abacabcd"

Another possible answer is: "abcabcda"

The same letters are at least distance 2 from each other.

X. https://discuss.leetcode.com/topic/49022/greedy-solution-beats-95
for example: "aaabbcc", k = 3
Count the statistics of letters, sort them in terms of frequency in a descending way.
so it has the result: a - 3, b - 2, c - 2.
Suppose the rewrite string length is len, divide the len into bins of size k, so in total
you have
bin number of nBin = (len - 1) / k + 1,
with last bin size:
lastBinSize = len % k.
in the example, nBin = 3, lastBinSize = 1;
Fill the same letter in different bins:
after filling 'a' ---> result = a##a##a
after filling 'b' ---> result = ab#ab#a
after filling 'c' ---> result = abcabca

http://dartmooryao.blogspot.com/2016/06/leetcode-358-rearrange-string-k.html
    public String rearrangeString(String str, int k) {
        if(k<=1){ return str; }
        int[] count = new int[26];
        for(int i=0; i<str.length(); i++){
            count[str.charAt(i)-'a']++;
        }
        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b)->b[0]-a[0]);
        for(int i=0; i<count.length; i++){ pq.add(new int[]{ count[i], i}); }
       
        char[] result = new char[str.length()];
        int idx = 0;
        int start = 0;
        while(!pq.isEmpty()){
            int[] num = pq.remove();
            for(int i=0; i<num[0]; i++){
                result[idx] = (char)(num[1]+'a');
                if(idx>0 && result[idx-1]==result[idx]){ return ""; }
                idx+=k;
                if(idx>=str.length()){ idx=++start; }
            }
        }
        return new String(result);
    }
https://leetcode.com/discuss/108232/java_solution_in_12_ms-o-n-time-and-space
方法：根据出现频率将字母从大到小排列，以k为间隔进行重排。
https://leetcode.com/discuss/108174/c-unordered_map-priority_queue-solution-using-cache
http://www.cnblogs.com/grandyang/p/5586009.html
这道题给了我们一个字符串str，和一个整数k，让我们对字符串str重新排序，使得其中相同的字符之间的距离不小于k，这道题的难度标为Hard，看来不是省油的灯。的确，这道题的解法用到了哈希表，堆，和贪婪算法。这道题我最开始想的算法没有通过OJ的大集合超时了，下面的方法是参考网上大神的解法，发现十分的巧妙。我们需要一个哈希表来建立字符和其出现次数之间的映射，然后需要一个堆来保存这每一堆映射，按照出现次数来排序。然后如果堆不为空我们就开始循环，我们找出k和str长度之间的较小值，然后从0遍历到这个较小值，对于每个遍历到的值，如果此时堆为空了，说明此位置没法填入字符了，返回空字符串，否则我们从堆顶取出一对映射，然后把字母加入结果res中，此时映射的个数减1，如果减1后的个数仍大于0，则我们将此映射加入临时集合v中，同时str的个数len减1，遍历完一次，我们把临时集合中的映射对由加入堆
    string rearrangeString(string str, int k) {
        if (k == 0) return str;
        string res;
        int len = (int)str.size();
        unordered_map<char, int> m;
        priority_queue<pair<int, char>> q;
        for (auto a : str) ++m[a];
        for (auto it = m.begin(); it != m.end(); ++it) {
            q.push({it->second, it->first});
        }
        while (!q.empty()) {
            vector<pair<int, int>> v;
            int cnt = min(k, len);
            for (int i = 0; i < cnt; ++i) {
                if (q.empty()) return "";
                auto t = q.top(); q.pop();
                res.push_back(t.second);
                if (--t.first > 0) v.push_back(t);
                --len;
            }
            for (auto a : v) q.push(a);
        }
        return res;
    }
https://discuss.leetcode.com/topic/48125/java_solution_in_12_ms-o-n-time-and-space
    public String rearrangeString(String str, int k) {
        if(k < 2) return str;
      int[] times = new int[26];
      for(int i = 0; i < str.length(); i++){
          ++times[str.charAt(i) - 'a'];
      }
      SortedSet<int[]> set = new TreeSet<int[]>(new Comparator<int[]>(){
          @Override
          public int compare(int[] a, int[] b){
              return a[0] == b[0] ? Integer.compare(a[1], b[1]) : Integer.compare(b[0], a[0]);
          }
      });
      for(int i = 0; i < 26; i++){
          if(times[i] != 0){
            set.add(new int[]{times[i], i});
          }
      }
      int cycles = 0;
      int cur = cycles;
      Iterator<int[]> iter = set.iterator();
      char[] res = new char[str.length()];
      while(iter.hasNext()){
          int[] e = iter.next();
          for(int i = 0; i < e[0]; i++){
              res[cur] = (char)('a'+e[1]);
              if(cur > 0 && res[cur] == res[cur-1])
                return "";
              cur += k;
              if(cur >= str.length()){
                  cur = ++cycles;
              }
          }
      }
      return new String(res);
    }
http://blog.csdn.net/jmspan/article/details/51678257
方法：根据出现频率将字母从大到小排列，以k为间隔进行重排。
    public String rearrangeString(String str, int k) {  
        if (k <= 0) return str;  
        int[] f = new int[26];  
        char[] sa = str.toCharArray();  
        for(char c: sa) f[c-'a'] ++;  
        int r = sa.length / k;  
        int m = sa.length % k;  
        int c = 0;  
        for(int g: f) {  
            if (g-r>1) return "";  
            if (g-r==1) c ++;  
        }  
        if (c>m) return "";  
        Integer[] pos = new Integer[26];  
        for(int i=0; i<pos.length; i++) pos[i] = i;  
        Arrays.sort(pos, new Comparator<Integer>() {  
           @Override  
           public int compare(Integer i1, Integer i2) {  
               return f[pos[i2]] - f[pos[i1]];  
           }  
        });  
        char[] result = new char[sa.length];  
        for(int i=0, j=0, p=0; i<sa.length; i++) {  
            result[j] = (char)(pos[p]+'a');  
            if (-- f[pos[p]] == 0) p ++;  
            j += k;  
            if (j >= sa.length) {  
                j %= k;  
                j ++;  
            }  
        }  
        return new String(result);  
    } 

方法二：最多允许有str.length() % k个冗余的字符，所以可以不排序，O(N)时间复杂度。


https://reeestart.wordpress.com/201
    public String rearrangeString(String str, int k) {  
        if (str == null || str.length() <= 1 || k <= 0) return str;  
        char[] sa = str.toCharArray();  
        int[] frequency = new int[26];  
        for(char ch : sa) {  
            frequency[ch - 'a'] ++;  
        }  
        int bucketSize = sa.length / k;  
        int remainSize = sa.length % k;  
        int[] remain = new int[remainSize];  
        int count = 0;  
        for(int i = 0; i < frequency.length; i++) {  
            if (frequency[i] > bucketSize + 1) return "";  
            if (frequency[i] > bucketSize && count >= remainSize) return "";  
            if (frequency[i] > bucketSize) remain[count++] = i;  
        }  
          
        int offset = 0, j = 0;  
        for(int i = 0; i < count; i++) {  
            while (frequency[remain[i]] > 0) {  
                frequency[remain[i]] --;  
                sa[j] = (char)('a' + remain[i]);  
                j += k;  
                if (j >= sa.length) {  
                    offset ++;  
                    j = offset;  
                }  
            }  
        }  
          
        for(int i = 0; i < 26; i ++) {  
            while (frequency[i] > 0) {  
                frequency[i] --;  
                sa[j] = (char)('a' + i);  
                j += k;  
                if (j >= sa.length) {  
                    offset ++;  
                    j = offset;  
                }  
            }  
        }  
        return new String(sa);  
    }  
6/06/23/rearrange-string-with-k-distance-apart/
第一个是统计frequency用hash table vs int[]的区别，事实证明array的确要比hash table快一点（80+ms vs 110+ms）。无论input string是不是只包含小写字母，都可以用array来替代hash table，只是size大点小点的关系。
之前onsite面试的时候就已经被面试官不止一次的指出这个问题，能用array的时候就别用hash table.
第二个是sort vs TreeSet的区别。这个真是震惊了，不比不知道，一比下一跳，TreeSet的运行时间最低居然只有8ms，比sorting快了将近10倍。但理论上时间复杂度都是O(nlogn)，为什么会这样有点理解不能…

  public String rearrangeString(String str, int k) {

    if (str == null || str.isEmpty() || k <= 1) {

      return str;

    }


    int[] cnt = new int[26];

    for (char c : str.toCharArray()) {

      cnt[c - 'a']++;

    }


//    List<int[]> entryList = new ArrayList<>();

//    for (int i = 0; i < 26; i++) {

//      if (cnt[i] != 0) {

//        entryList.add(new int[] {i, cnt[i]});

//      }

//    }

//    Collections.sort(entryList, (a, b) -> (-(a[1] - b[1])));


    TreeSet<int[]> entryList = new TreeSet<>(new Comparator<int[]>() {

      public int compare(int[] a, int[] b) {

        if (a[1] == b[1]) {

          return a[0] - b[0];

        }

        return -(a[1] - b[1]);

      }

    });


    for (int i = 0; i < 26; i++) {

      if (cnt[i] != 0) {

        entryList.add(new int[] {i, cnt[i]});

      }

    }


    char[] ch = new char[str.length()];

    int i = 0;

    int start = 1;

    for (int[] entry : entryList) {

      for (int j = 0; j < entry[1]; j++) {

        ch[i] = (char) (entry[0] + 'a');

        if (i != 0 && ch[i] == ch[i - 1]) {

          return "";

        }


        i += k;

        if (i >= str.length()) {

          i = start;

          start++;

        }

      }

    }


    return new String(ch);

  }
 * @author het
 *
 */
public class LeetCode358 {
	 public String rearrangeString(String str, int k) {
	        if(k<=1){ return str; }
	        int[] count = new int[26];
	        for(int i=0; i<str.length(); i++){
	            count[str.charAt(i)-'a']++;
	        }
	        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b)->b[0]-a[0]);
	        //  PriorityQueue<int[]> pq = new PriorityQueue<>((a,b)->b[0]-a[0]);
	        for(int i=0; i<count.length; i++){ pq.add(new int[]{ count[i], i}); }
	       
	        char[] result = new char[str.length()];
	        int idx = 0;
	        int start = 0;
	        while(!pq.isEmpty()){
	            int[] num = pq.remove();
	            for(int i=0; i<num[0]; i++){
	                result[idx] = (char)(num[1]+'a');
	                if(idx>0 && result[idx-1]==result[idx]){ return ""; }
	                idx+=k;
	                if(idx>=str.length()){ idx=++start; }
	            }
	        }
	        return new String(result);
	    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
