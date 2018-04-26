package alite.leetcode.newExtra.L500;
/**
 * 
https://leetcode.com/problems/encode-and-decode-tinyurl/
TinyURL is a URL shortening service where you enter a URL such as https://leetcode.com/problems/design-tinyurl 
and it returns a short URL such as http://tinyurl.com/4e9iAk.
Design the encode and decode methods for the TinyURL service. There is no restriction on how your encode/decode
 algorithm should work. You just need to ensure that a URL can be encoded to a tiny URL and the tiny URL can be 
 decoded to the original URL.
X.
https://mikecoder.github.io/oj-code/2017/03/04/EncodeandDecodeTinyURL/

        map<string, string> urls;




        string hash(string url) {


            long long hash  = 0;


            for (int i = 0; i < (int)url.length(); i++) {


                hash = hash * 10 + url[i];


            }


            return to_string(hash);


        }




        string encode(string longUrl) {


            string key = hash(longUrl);


            urls.insert(pair<string, string>(key, longUrl));


            return key;


        }




        string decode(string shortUrl) {


            return urls.find(shortUrl)->second;


        }

http://xiadong.info/2017/03/leetcode-535-encode-and-decode-tinyurl/
短链接的维护，但不限制内部如何生成短链接。就是Hash表的问题，我直接使用的unordered_map容器所提供的Hash函数对原链接进行处理，得到一个数值，然后将该数值转换为62进制字符串（10个数字+大小写字母各26个），该字符串作为短链接的后缀部分。

class Solution {

    string tinyUrlPrefix = "http://tinyurl.com/";

    unordered_map<string, string> urls;

public:

 

    // Encodes a URL to a shortened URL.

    string encode(string longUrl) {

        auto hashFunc = urls.hash_function();

        size_t key = hashFunc(longUrl);

        string shortUrl = tinyUrlPrefix + convertToSixtyTwoBase(key);

        urls[shortUrl] = longUrl;

        return shortUrl;

    }

 

    // Decodes a shortened URL to its original URL.

    string decode(string shortUrl) {

        return urls[shortUrl];

    }

    

    string convertToSixtyTwoBase (size_t key) {

        string str;

        while (key > 0) {

            int mod = key % 62;

            if (mod < 10) str.push_back(mod + '0');

            else if (mod < 36) str.push_back(mod - 10 + 'a');

            else str.push_back(mod - 36 + 'A');

            key /= 62;

        }

        return str;

    }

};
https://discuss.leetcode.com/topic/82273/three-different-approaches-in-java
Approach 1- Using simple counter
public class Codec {
    Map<Integer, String> map = new HashMap<>();
    int i=0;
    public String encode(String longUrl) {
        map.put(i,longUrl);
        return "http://tinyurl.com/"+i++;
    }
    public String decode(String shortUrl) {
        return map.get(Integer.parseInt(shortUrl.replace("http://tinyurl.com/", "")));
    }
}
https://discuss.leetcode.com/topic/81637/two-solutions-and-thoughts
https://discuss.leetcode.com/topic/81633/easy-solution-in-java-5-line-code
My first solution produces short URLs like http://tinyurl.com/0, http://tinyurl.com/1, etc, in that order.
class Codec:

    def __init__(self):
        self.urls = []

    def encode(self, longUrl):
        self.urls.append(longUrl)
        return 'http://tinyurl.com/' + str(len(self.urls) - 1)

    def decode(self, shortUrl):
        return self.urls[int(shortUrl.split('/')[-1])]
Using increasing numbers as codes like that is simple but has some disadvantages, which the below solution fixes:
If I'm asked to encode the same long URL several times, it will get several entries. That wastes codes and memory.
People can find out how many URLs have already been encoded. Not sure I want them to know.
People might try to get special numbers by spamming me with repeated requests shortly before their desired number comes up.
Only using digits means the codes can grow unnecessarily large. Only offers a million codes with length 6 (or smaller). Using six digits or lower or upper case letters would offer (10+26*2)6 = 56,800,235,584 codes with length 6.
The following solution doesn't have these problems. It produces short URLs like http://tinyurl.com/KtLa2U, using a random code of six digits or letters. If a long URL is already known, the existing short URL is used and no new entry is generated.
class Codec:

    alphabet = string.ascii_letters + '0123456789'

    def __init__(self):
        self.url2code = {}
        self.code2url = {}

    def encode(self, longUrl):
        while longUrl not in self.url2code:
            code = ''.join(random.choice(Codec.alphabet) for _ in range(6))
            if code not in self.code2url:
                self.code2url[code] = longUrl
                self.url2code[longUrl] = code
        return 'http://tinyurl.com/' + self.url2code[longUrl]

    def decode(self, shortUrl):
        return self.code2url[shortUrl[-6:]]
It's possible that a randomly generated code has already been generated before. In that case, another random code is generated instead. Repeat until we have a code that's not already in use. How long can this take? Well, even if we get up to using half of the code space, which is a whopping 626/2 = 28,400,117,792 entries, then each code has a 50% chance of not having appeared yet. So the expected/average number of attempts is 2, and for example only one in a billion URLs takes more than 30 attempts. And if we ever get to an even larger number of entries and this does become a problem, then we can just use length 7. We'd need to anyway, as we'd be running out of available codes.
    Map<String, String> index = new HashMap<String, String>();
    Map<String, String> revIndex = new HashMap<String, String>();
    static String BASE_HOST = "http://tinyurl.com/";
    
    // Encodes a URL to a shortened URL.
    public String encode(String longUrl) {
        if (revIndex.containsKey(longUrl)) return BASE_HOST + revIndex.get(longUrl);
        String charSet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String key = null;
        do {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 6; i++) {
                int r = (int) (Math.random() * charSet.length());
                sb.append(charSet.charAt(r));
            }
            key = sb.toString();
        } while (index.containsKey(key));
        index.put(key, longUrl);
        revIndex.put(longUrl, key);
        return BASE_HOST + key;
    }

    // Decodes a shortened URL to its original URL.
    public String decode(String shortUrl) {
        return index.get(shortUrl.replace(BASE_HOST, ""));
    }

https://discuss.leetcode.com/topic/81620/maybe-base64
Your solution, while terse, totally misses the point of the question. Encoding is valuable as long as you can send back a "shorter" URL; your method seems to generate much longer URLs than the supplied input.
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Codec {
    public String encode(String longUrl) {
        return Base64.getUrlEncoder().encodeToString(longUrl.getBytes(StandardCharsets.UTF_8));
    }

    public String decode(String shortUrl) {
        return new String(Base64.getUrlDecoder().decode(shortUrl));
    }
}
 * @author het
 *
 */
public class LeetCode535_Encode_and_Decode_TinyURL {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
