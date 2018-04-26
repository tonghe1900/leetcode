package alite.leetcode.selected;
/**
 * LeetCode [271] Encode and Decode Strings

LeetCode [271] Encode and Decode Strings
http://segmentfault.com/a/1190000003791865
Design an algorithm to encode a list of strings to a string. The encoded string is then sent over the network and is decoded back 
to the original list of strings.
Machine 1 (sender) has the function:
string encode(vector<string> strs) { // ... your code return encoded_string; }
Machine 2 (receiver) has the function:
vector<string> decode(string s) { //... your code return strs; }
So Machine 1 does:
string encoded_string = encode(strs);
and Machine 2 does:
vector<string> strs2 = decode(encoded_string);
strs2 in Machine 2 should be the same as strs in Machine 1.
Implement the encode and decode methods.
Note: The string may contain any possible characters out of 256 valid ascii characters. Your algorithm should be generalized 
enough to work on any possible characters. Do not use class member/global/static variables to store states. Your encode
 and decode algorithms should be stateless. Do not rely on any library method such as eval or serialize methods. You should
  implement your own encode/decode algorithm.
Also http://www.meetqun.com/thread-11463-1-1.html
    // Encodes a list of strings to a single string.
    public String encode(List<String> strs) {
        StringBuilder output = new StringBuilder();
        for(String str : strs){
            // 对于每个子串，先把其长度放在前面，用#隔开
            output.append(String.valueOf(str.length())+"#");
            // 再把子串本身放在后面
            output.append(str);
        }
        return output.toString();
    }

    // Decodes a single string to a list of strings.
    public List<String> decode(String s) {
        List<String> res = new LinkedList<String>();
        int start = 0;
        while(start < s.length()){
            // 找到从start开始的第一个#，这个#前面是长度
            int idx = s.indexOf('#', start);
            int size = Integer.parseInt(s.substring(start, idx));
            // 根据这个长度截取子串
            res.add(s.substring(idx + 1, idx + size + 1));
            // 更新start为子串后面一个位置
            start = idx + size + 1;
        }
        return res;
    }
Better: https://leetcode.com/discuss/57716/java-solution-string-length-delimiter-as-header
public String encode(List<String> strs) {
    StringBuffer result = new StringBuffer();

    if(strs == null || strs.size() == 0)
        return result.toString();

    for(String str: strs){
        result.append(str.length());
        result.append("#");
        result.append(str);
    }

    return result.toString();
}

// Decodes a single string to a list of strings.
public List<String> decode(String s) {
    List<String> result = new ArrayList();

    if(s == null || s.length() == 0)
        return result;

    int current = 0;
    while(true){
        if(current == s.length())
            break;
        StringBuffer sb = new StringBuffer();
        while(s.charAt(current) != '#'){
            sb.append(s.charAt(current));
            current++;
        }
        int len = Integer.parseInt(sb.toString());
        int end = current + 1 + len;
        result.add(s.substring(current+1, end));
        current = end;
    }
    return result;
}

X. http://leetcode.tgic.me/encode-and-decode-strings/index.html TODO

X. http://nb4799.neu.edu/wordpress/?p=900
Also http://www.fgdsb.com/2015/01/06/encode-decode-strings/

class Codec:

    def encode(self, strs): 

        return ''.join(s.replace('|', '||') + ' | ' for s in strs) 

    def decode(self, s): 

        return [t.replace('||', '|') for t in s.split(' | ')[:-1]]
Use ” | ” as a delimiter of strings. If any string has “|” in it, you need to replace it with “||” in advance. When decoding, you first get ” | ” splitted words. Then, if there is any “||” in splitted words, you need to replace it back with “|”. Don’t forget to exclude the last element ofs.split(' | ') since it is an empty string. 



http://shibaili.blogspot.com/2015/09/day-124-271-encode-and-decode-strings.html
The string may contain any possible characters out of 256 valid ascii characters. Your algorithm should be generalized enough to work on any possible characters.
Do not use class member/global/static variables to store states. Your encode and decode algorithms should be stateless.
Do not rely on any library method such as eval or serialize methods. You should implement your own encode/decode algorithm.

    // Encodes a list of strings to a single string.

    string encode(vector<string>& strs) {

        string rt = "";

        for (string s : strs) {

            rt += to_string(s.length()) + "/" + s;

        }

         

        return rt;

    }


    // Decodes a single string to a list of strings.

    vector<string> decode(string s) {

        vector<string> rt;

        int i = 0;

        while (i < s.length()) {

            int start = i;

            string part = "",length = "";

            while (isdigit(s[i])) {

                length += s[i];

                i++;

            }

            part = s.substr(i + 1,stoi(length));

            rt.push_back(part);

            i += stoi(length) + 1;

        }

        return rt;

    }

http://www.cnblogs.com/tonix/p/4768262.html
This is about https://en.wikipedia.org/wiki/Run-length_encoding. The trick is, for a valid char, we only compress up to 254 occurences - count 255 means end of a string.


    // Encodes a list of strings to a single string.

    string encode(vector<string>& strs) {

        string ret;

        for(auto s:strs){

            int i = 0, len = s.size(), cnt;

            while(i<len){

                cnt = 1;

                uchar c = s[i++];

                while(i<len && (uchar)s[i]==c && cnt<254){

                    i++; cnt++;

                }

                ret += (uchar)cnt;

                ret += c;

            }

            ret += (uchar)255;

        }

        return ret;

    }


    // Decodes a single string to a list of strings.

    vector<string> decode(string s) {

        vector<string> ret;

        string cur;

        int i=0, len = s.size();

        while(i<len){

            uchar c = s[i++];

            if(c==(uchar)255){

                ret.push_back(cur);

                cur.clear();

            }else{

                int cnt = c;

                c = s[i++];

                for(int i=0; i<cnt; ++i) cur += c;

            }

        }

        return ret;

    }

http://www.cnblogs.com/jcliBlogger/p/4768875.html
http://buttercola.blogspot.com/2015/09/leetcode-encode-and-decode-strings.html

    // Encodes a list of strings to a single string.

    public String encode(List<String> strs) {

        if (strs == null || strs.size() == 0) {

            return "";

        }

         

        StringBuffer sb = new StringBuffer();

         

        for (String str : strs) {

            int len = str == null ? 0 : str.length();

            sb.append(len);

            sb.append('#');

            sb.append(str);

        }

         

        return sb.toString();

    }


    // Decodes a single string to a list of strings.

    public List<String> decode(String s) {

        List<String> result = new ArrayList<String>();

        if (s == null || s.length() == 0) {

            return result;

        }

         

        int i = 0;

        while (i < s.length()) {

            int len = 0;

            // Get length

            while (i < s.length() && s.charAt(i) != '#') {

                len = len * 10 + Character.getNumericValue(s.charAt(i));

                i++;

            }

             

            String str = s.substring(i + 1, i + len + 1);

            result.add(str);

            i = i + len + 1;

        }

         

        return result;

    }
http://algobox.org/encode-and-decode-strings/


Encode: add the lengths of the strings seperated by ',' as metadata in front of the real data, then use ':' to separate the metadata and the real data. In the end append all the strings.
Decode: extract metadata by finding the first ':', then split the metadata and convert it to lengths. Finally, extract the strings from real data according to the lengths one by one.

    // Encodes a list of strings to a single string.

    public String encode(List<String> strs) {

        if (strs.isEmpty()) return "";

        StringBuilder builder = new StringBuilder();

        for (String str : strs) builder.append(str.length()).append(',');

        builder.setCharAt(builder.length() - 1, ':');

        for (String str : strs) builder.append(str);

        return builder.toString();

    }

 

    // Decodes a single string to a list of strings.

    public List<String> decode(String s) {

        List<String> ans = new ArrayList<>();

        int m = s.indexOf(':');

        if (m < 0) return ans;

        int i = m + 1;

        String[] strLens = s.substring(0, m).split(",");

        for (String strLen : strLens) {

            int len = Integer.parseInt(strLen);

            ans.add(s.substring(i, i + len));

            i += len;

        }

        return ans;

    }
Encode: pack every string into a package of (str.length+','+str).
Decode: find the comma and then get the length in front of it then extract the string behind it.

    public String encode(List<String> strs) {

        StringBuilder builder = new StringBuilder();

        for (String str : strs)

            builder.append(str.length()).append(',').append(str);

        return builder.toString();

    }

 

    // Decodes a single string to a list of strings.

    public List<String> decode(String s) {

        List<String> ans = new ArrayList<>();

        for (int i = 0, len; i < s.length(); i += len) {

            int comma = s.indexOf(',', i);

            len = Integer.parseInt(s.substring(i, comma));

            i = comma + 1;

            ans.add(s.substring(i, i + len));

        }

        return ans;

    }
X. https://leetcode.com/discuss/57472/java-with-escaping
public String encode(List<String> strs) {
    StringBuffer out = new StringBuffer();
    for (String s : strs)
        out.append(s.replace("#", "##")).append(" # ");
    return out.toString();
}

public List<String> decode(String s) {
    List strs = new ArrayList();
    String[] array = s.split(" # ", -1);
    for (int i=0; i<array.length-1; ++i)
        strs.add(array[i].replace("##", "#"));
    return strs;
}
public String encode(List<String> strs) {
    return strs.stream()
               .map(s -> s.replace("#", "##") + " # ")
               .collect(Collectors.joining());
}

public List<String> decode(String s) {
    List strs = Stream.of(s.split(" # ", -1))
                      .map(t -> t.replace("##", "#"))
                      .collect(Collectors.toList());
    strs.remove(strs.size() - 1);
    return strs;
}
 * @author het
 *
 */
public class LeetCode271 {
    // Encodes a list of strings to a single string.
    public String encode(List<String> strs) {
        StringBuilder output = new StringBuilder();
        for(String str : strs){
            // 对于每个子串，先把其长度放在前面，用#隔开
            output.append(String.valueOf(str.length())+"#");
            // 再把子串本身放在后面
            output.append(str);
        }
        return output.toString();
    }

    // Decodes a single string to a list of strings.
    public List<String> decode(String s) {
        List<String> res = new LinkedList<String>();
        int start = 0;
        while(start < s.length()){
            // 找到从start开始的第一个#，这个#前面是长度
            int idx = s.indexOf('#', start);
            int size = Integer.parseInt(s.substring(start, idx));
            // 根据这个长度截取子串
            res.add(s.substring(idx + 1, idx + size + 1));
            // 更新start为子串后面一个位置
            start = idx + size + 1;
        }
        return res;
    }
Better: https://leetcode.com/discuss/57716/java-solution-string-length-delimiter-as-header
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
