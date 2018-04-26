package alite.leetcode.xx4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * https://leetcode.com/problems/reconstruct-original-digits-from-english/
Given a non-empty string containing an out-of-order English representation of digits 0-9, 
output the digits in ascending order.
Note:
Input contains only lowercase English letters.
Input is guaranteed to be valid and can be transformed to its original digits. 
That means invalid inputs such as "abc" or "zerone" are not permitted.
Input length is less than 50,000.
Example 1:
Input: "owoztneoer"

Output: "012"
Example 2:
Input: "fviefuro"

Output: "45"
X.
https://discuss.leetcode.com/topic/63386/one-pass-o-n-java-solution-simple-and-clear
for zero, it's the only word has letter 'z',
for two, it's the only word has letter 'w',
......
so we only need to count the unique letter of each word, Coz the input is always valid.
public String originalDigits(String s) {
    int[] count = new int[10];
    for (int i = 0; i < s.length(); i++){
        char c = s.charAt(i);
        if (c == 'z') count[0]++;
        if (c == 'w') count[2]++;
        if (c == 'x') count[6]++;
        if (c == 's') count[7]++; //7-6
        if (c == 'g') count[8]++;
        if (c == 'u') count[4]++; 
        if (c == 'f') count[5]++; //5-4
        if (c == 'h') count[3]++; //3-8
        if (c == 'i') count[9]++; //9-8-5-6
        if (c == 'o') count[1]++; //1-0-2-4
    }
    count[7] -= count[6];
    count[5] -= count[4];
    count[3] -= count[8];
    count[9] = count[9] - count[8] - count[5] - count[6];
    count[1] = count[1] - count[0] - count[2] - count[4];
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i <= 9; i++){
        for (int j = 0; j < count[i]; j++){
            sb.append(i);
        }
    }
    return sb.toString();
}
https://discuss.leetcode.com/topic/63382/share-my-simple-and-easy-o-n-solution
    public String originalDigits(String s) {
        if(s==null || s.length()==0) return "";
        int[] count = new int[128];
        for(int i=0;i<s.length();i++)  count[s.charAt(i)]++;
        int[] num = new int[10];
        num[0] = count['z'];
        num[2] = count['w'];
        num[6] = count['x'];
        num[8] = count['g'];
        num[7] = count['s']-count['x'];
        num[5] = count['v']-count['s']+count['x'];
        num[4] = count['u'];
        num[3] = count['h']-count['g'];
        num[1] = count['o']-count['z']-count['w']-count['u'];
        num[9] = count['i']-count['x']-count['g']-count['v']+count['s']-count['x'];
        String ret = new String();
        for(int i=0;i<10;i++)
            for(int j=num[i];j>0;j--) ret += String.valueOf(i);
        return ret;
    }

http://bookshadow.com/weblog/2016/10/16/leetcode-reconstruct-original-digits-from-english/
字符统计 + 枚举
统计字符串s中各字符的个数，需要注意的是，在枚举英文字母时，需要按照特定的顺序方可得到正确答案。

例如按照顺序：6028745913，这个顺序可以类比拓扑排序的过程。

观察英文单词，six, zero, two, eight, seven, four中分别包含唯一字母x, z, w, g, v, u；因此6, 0, 2, 8, 7, 4需要排在其余数字之前。

排除这6个数字之后，剩下的4个数字中，按照字母唯一的原则顺次挑选。

由于剩下的单词中，只有five包含f，因此选为下一个单词；

以此类推，可以得到上面所述的顺序。
    def originalDigits(self, s):
        """
        :type s: str
        :rtype: str
        """
        cnts = collections.Counter(s)
        nums = ['six', 'zero', 'two', 'eight', 'seven', 'four', 'five', 'nine', 'one', 'three']
        numc = [collections.Counter(num) for num in nums]
        digits = [6, 0, 2, 8, 7, 4, 5, 9, 1, 3]
        ans = [0] * 10
        for idx, num in enumerate(nums):
            cntn = numc[idx]
            t = min(cnts[c] / cntn[c] for c in cntn)
            ans[digits[idx]] = t
            for c in cntn:
                cnts[c] -= t * cntn[c]
        return ''.join(str(i) * n for i, n in enumerate(ans))


https://discuss.leetcode.com/topic/63486/short-matrix-solution
https://discuss.leetcode.com/topic/64207/java-naive-solution
    public String originalDigits(String s) {
        char[] dic = {'z','w','g','x','u','s','v','o','t','i'};
        String[] digits = {"zero","two","eight","six","four","seven","five","one","three","nine"};
        int[] index = {0,2,8,6,4,7,5,1,3,9};
        int[] map = new int[26];
        int[] arr = new int[10];
        for(int i=0; i<s.length(); i++){
            map[s.charAt(i)-'a']++;
        }
        for(int i=0; i<10; i++){
            getNum(map, dic[i], digits[i], arr, index[i]);
        }
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<10; i++){
            for(int t=0; t<arr[i]; t++){
                sb.append(i);
            }
        }
        return sb.toString();
    }
    private void getNum(int[] map, char c, String s, int[] arr, int index){
        int dup = map[c-'a'];
        for(int i=0; i<dup; i++){
            arr[index]++;
            for(int j=0; j<s.length(); j++){
                map[s.charAt(j)-'a']--;
            }
        }
    }
 * @author het
 *
 */
public class LeetCode423ReconstructOriginalDigitsfromEnglish {
	public String originalDigits(String s) {
	    int[] count = new int[10];
	    for (int i = 0; i < s.length(); i++){
	        char c = s.charAt(i);
	        if (c == 'z') count[0]++;
	        if (c == 'w') count[2]++;
	        if (c == 'x') count[6]++;
	        if (c == 's') count[7]++; //7-6
	        if (c == 'g') count[8]++;
	        if (c == 'u') count[4]++; 
	        if (c == 'f') count[5]++; //5-4
	        if (c == 'h') count[3]++; //3-8
	        if (c == 'i') count[9]++; //9-8-5-6
	        if (c == 'o') count[1]++; //1-0-2-4
	    }
	    count[7] -= count[6];
	    count[5] -= count[4];
	    count[3] -= count[8];
	    count[9] = count[9] - count[8] - count[5] - count[6];
	    count[1] = count[1] - count[0] - count[2] - count[4];
	    StringBuilder sb = new StringBuilder();
	    for (int i = 0; i <= 9; i++){
	        for (int j = 0; j < count[i]; j++){
	            sb.append(i);
	        }
	    }
	    return sb.toString();
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Map<String, Integer> map = new HashMap<>();
		map.put("zero", 0);
		map.put("one", 1);
		map.put("two", 2);
		map.put("three", 3);
		map.put("four", 4);
		map.put("five", 5);
		map.put("six", 6);
		map.put("seven", 7);
		map.put("eight", 8);
		map.put("nine", 9);
		Map<Character, List<Integer>> result = new HashMap<>();
		for(String key: map.keySet()){
			for(int i=0;i<key.length();i+=1){
				put(key.charAt(i), result, map.get(key));
			}
		}
		System.out.println(result);

	}
	private static void put(char charAt, Map<Character, List<Integer>> result,
			Integer integer) {
		List<Integer> list = null;
		if(!result.containsKey(charAt)){
			list = new ArrayList<>();
			result.put(charAt, list);
		}else{
			list = result.get(charAt);
		}
		list.add(integer);
		
	}

}
