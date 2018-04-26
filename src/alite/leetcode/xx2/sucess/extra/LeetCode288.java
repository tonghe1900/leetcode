package alite.leetcode.xx2.sucess.extra;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * 
 * 
 * LIKE CODING: LeetCode [288] Unique Word Abbreviation
http://www.cnblogs.com/easonliu/p/4852129.html
An abbreviation of a word follows the form <first letter><number><last letter>. Below are some examples of word abbreviations:
a) it                      --> it    (no abbreviation)

     1
b) d|o|g                   --> d1g

              1    1  1
     1---5----0----5--8
c) i|nternationalizatio|n  --> i18n

              1
     1---5----0
d) l|ocalizatio|n          --> l10n
Assume you have a dictionary and given a word, find whether its abbreviation is unique in the dictionary
. A word's abbreviation is unique if no other word from the dictionary has the same abbreviation.
Example: 
Given dictionary = [ "deer", "door", "cake", "card" ]

isUnique("dear") -> false
isUnique("cart") -> true
isUnique("cane") -> false
isUnique("make") -> true

http://buttercola.blogspot.com/2015/10/leetcode-unique-word-abbreviation.html
When build the abbr -> world mapping, if there is, no need to store a list, just set it as empty string -> not null(npe).


public class ValidWordAbbr {

    private Map<String, String> map;

    public ValidWordAbbr(String[] dictionary) {

        this.map = new HashMap<>();

         

        for (String word : dictionary) {

            String abbr = toAbbr(word);

            if (map.containsKey(abbr)) {

                map.put(abbr, "");

            } else {

                map.put(abbr, word);

            }

        }

    }


    public boolean isUnique(String word) {

        String abbr = toAbbr(word);

        if (!map.containsKey(abbr) || map.get(abbr).equals(word)) {

            return true;

        } else {

            return false;

        }

    }

     

    private String toAbbr(String s) {

        if (s == null || s.length() <= 2) {

            return s;

        }

         

        int len = s.length() - 2;

         

        String result = s.charAt(0) + "" + len + s.charAt(s.length() - 1);

         

        return result;

    }

}

https://leetcode.com/discuss/71652/java-solution-with-hashmap-string-string-beats-submissions
map.put(key, ""); is nice!
HashMap<String, String> map;
public ValidWordAbbr(String[] dictionary) {
    map = new HashMap<String, String>();
    for(String str:dictionary){
        String key = getKey(str);
        // If there is more than one string belong to the same key
        // then the key will be invalid, we set the value to ""
        if(map.containsKey(key) && !map.get(key).equals(str))
            map.put(key, "");
        else
            map.put(key, str);
    }
}
public boolean isUnique(String word) {
    String key = getKey(word);
    return !map.containsKey(key)||map.get(key).equals(word);
}

private String getKey(String str){
    if(str.length()<=2) return str;
    return str.charAt(0)+Integer.toString(str.length()-2)+str.charAt(str.length()-1);
}
https://leetcode.com/discuss/61658/share-my-java-solution
I think we don't need to contain a set in HashMap, since if we got more than one value corresponds to this key, then this key is just a marker of duplicates. So my suggestion is to replace the value in HashMap by string.
For duplicates, the value would be some special strings like empty string or null. For others, the value is simply the word that produces this key
The idea is pretty straightforward, we use a map to track a set of words that have the same abbreviation. The word is unique when its abbreviation does not exist in the map or it's the only one in the set.
  Map<String, Set<String>> map = new HashMap<>();

  public ValidWordAbbr(String[] dictionary) {
    // build the hashmap
    // the key is the abbreviation
    // the value is a hash set of the words that have the same abbreviation
    for (int i = 0; i < dictionary.length; i++) {
      String a = abbr(dictionary[i]);
      Set<String> set = map.containsKey(a) ? map.get(a) : new HashSet<>();
      set.add(dictionary[i]);
      map.put(a, set);
    }
  }

  public boolean isUnique(String word) {
    String a = abbr(word);
    // it's unique when the abbreviation does not exist in the map or
    // it's the only word in the set
    return !map.containsKey(a) || (map.get(a).contains(word) && map.get(a).size() == 1);
  }

  String abbr(String s) {
    if (s.length() < 3) return s;
    return s.substring(0, 1) + String.valueOf(s.length() - 2) + s.substring(s.length() - 1);
  }

http://blog.csdn.net/xudli/article/details/48860911
public class ValidWordAbbr {  
    Map<String, Set<String> > map = new HashMap<>();  
  
    public ValidWordAbbr(String[] dictionary) {  
        for(int i=0; i<dictionary.length; i++) {  
            String s = dictionary[i];  
            if(s.length() > 2 ) {  
                s = s.charAt(0) + Integer.toString(s.length()-2) + s.charAt(s.length()-1);  
            }  
            if(map.containsKey(s) ) {  
                map.get(s).add(dictionary[i]);  
            } else {  
                Set<String> set = new HashSet<String>();  
                set.add(dictionary[i]);  
                map.put(s, set);  
            }  
        }  
    }  
  
    public boolean isUnique(String word) {  
        //input check  
        String s = word;  
        if(s.length() > 2 ) {  
            s = s.charAt(0) + Integer.toString(s.length()-2) + s.charAt(s.length()-1);  
        }  
        if(!map.containsKey(s)) return true;  
        else return map.get(s).contains(word) && map.get(s).size()<=1;  
          
    }  
}  


class ValidWordAbbr {

    unordered_map<string, vector<string>> abbs;

public:

    string getAbb(string s){

        int n = s.size();

        if(n<=2) return s;

        else return s.substr(0,1)+to_string(n-2)+s.substr(n-1,1);

    }

    ValidWordAbbr(vector<string> &dictionary) {

        for(auto w:dictionary){

            abbs[getAbb(w)].push_back(w);

        }

    }


    bool isUnique(string word) {

        string key = getAbb(word);

        return abbs.count(key)==0 || ((int)abbs[key].size()==1&&abbs[key][0]==word);

    }

};
 2 private:
 3     unordered_map<string, int> m_map;
 4     unordered_set<string> m_set;
 5 public:
 6     ValidWordAbbr(vector<string> &dictionary) {
 7         for (auto word : dictionary) {
 8             m_set.insert(word);
 9             if (word.length() == 2) ++m_map[word];
10             else ++m_map[word.front() + to_string(word.length() - 2) + word.back()];
11         }
12     }
13 
14     bool isUnique(string word) {
15         string key;
16         if (word.length() == 2) key = word;
17         else key = word.front() + to_string(word.length() - 2) + word.back();
18         if (m_set.find(word) == m_set.end()) return m_map[key] < 1;
19         else return m_map[key] < 2;
20     }
21 };
http://likemyblogger.blogspot.com/2015/10/leetcode-288-unique-word-abbreviation.html

class ValidWordAbbr {

    unordered_map<string, vector<string>> abbs;

public:

    string getAbb(string s){

        int n = s.size();

        if(n<=2) return s;

        else return s.substr(0,1)+to_string(n-2)+s.substr(n-1,1);

    }

    ValidWordAbbr(vector<string> &dictionary) {

        for(auto w:dictionary){

            abbs[getAbb(w)].push_back(w);

        }

    }


    bool isUnique(string word) {

        string key = getAbb(word);

        return abbs.count(key)==0 || ((int)abbs[key].size()==1&&abbs[key][0]==word);

    }

};

Follow up:
http://massivealgorithms.blogspot.com/2016/10/leetcode-411-minimum-unique-word.html

Read full article from LIKE CODING: LeetCode [288] Unique Word Abbreviation
 * 
 * @author het
 *
 */
public class LeetCode288 {
	
	
	 private Map<String, String> map;

	    public LeetCode288(String[] dictionary) {

	        this.map = new HashMap<>();

	         

	        for (String word : dictionary) {

	            String abbr = toAbbr(word);

	            if (map.containsKey(abbr)) {

	                map.put(abbr, "");

	            } else {

	                map.put(abbr, word);

	            }

	        }

	    }


	    public boolean isUnique(String word) {

	        String abbr = toAbbr(word);

	        if (!map.containsKey(abbr) || map.get(abbr).equals(word)) {

	            return true;

	        } else {

	            return false;

	        }

	    }

	     

	    private String toAbbr(String s) {

	        if (s == null || s.length() <= 2) {

	            return s;

	        }

	         

	        int len = s.length() - 2;

	         

	        String result = s.charAt(0) + "" + len + s.charAt(s.length() - 1);

	         

	        return result;

	    }

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
