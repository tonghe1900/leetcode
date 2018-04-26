package alite.leetcode.xx3;
///**
// * Leetcode 316 - Remove Duplicate Letters
//
//https://leetcode.com/problems/remove-duplicate-letters/
//Given a string which contains only lowercase letters, remove duplicate letters so that every letter appear once 
//and only once. 
//You must make sure your result is the smallest in lexicographical order among all possible results.
//Example:
//Given "bcabc"
//Return "abc"
//Given "cbacdcbc"
//Return "acdb"
//1https://leetcode.com/discuss/73761/a-short-o-n-recursive-greedy-solution
//Given the string s, the greedy choice (i.e., the leftmost letter in the answer) is the smallest s[i], 
//s.t. the suffix s[i .. ] contains all the unique letters.
//After determining the greedy choice s[i], we get a new string s' from s by
//removing all letters to the left of s[i],
//removing all s[i]'s from s.
//We then recursively solve the problem w.r.t. s'.
//The runtime is O(26 * n) = O(n).
//Each recursive call takes O(n), but at most 26 recursive calls will be triggered.
//    public String removeDuplicateLetters(String s) {
//        int[] cnt = new int[26];
//        int pos = 0; // the position for the smallest s[i]
//        for (int i = 0; i < s.length(); i++) cnt[s.charAt(i) - 'a']++;
//        for (int i = 0; i < s.length(); i++) {
//            if (s.charAt(i) < s.charAt(pos)) pos = i;
//            if (--cnt[s.charAt(i) - 'a'] == 0) break;
//        }
//        return s.length() == 0 ? "" : s.charAt(pos) + removeDuplicateLetters(s.substring(pos + 1).replaceAll("" + s.charAt(pos), ""));
//    }
//
//X. Use Stack
//https://discuss.leetcode.com/topic/32259/java-solution-using-stack-with-comments
//https://discuss.leetcode.com/topic/37554/java-o-n-iterative-greedy-solution/
//https://discuss.leetcode.com/topic/43469/java-o-n-solution-using-stack-with-detail-explanation
//public String removeDuplicateLetters(String sr) {
//
//    int[] res = new int[26]; //will contain number of occurences of character (i+'a')
//    boolean[] visited = new boolean[26]; //will contain if character (i+'a') is present in current result Stack
//    char[] ch = sr.toCharArray();
//    for(char c: ch){  //count number of occurences of character 
//        res[c-'a']++;
//    }
//    Stack<Character> st = new Stack<>(); // answer stack
//    int index;
//    for(char s:ch){ 
//        index= s-'a';
//        res[index]--;   //decrement number of characters remaining in the string to be analysed
//        if(visited[index]) //if character is already present in stack, dont bother
//            continue;
//        //if current character is smaller than last character in stack which occurs later in the string again
//        //it can be removed and  added later e.g stack = bc remaining string abc then a can pop b and then c
//        while(!st.isEmpty() && s<st.peek() && res[st.peek()-'a']!=0){ 
//            visited[st.pop()-'a']=false;
//        }
//        st.push(s); //add current character and mark it as visited
//        visited[index]=true;
//    }
//
//    StringBuilder sb = new StringBuilder();
//    //pop character from stack and build answer string from back
//    while(!st.isEmpty()){
//        sb.insert(0,st.pop());
//    }
//    return sb.toString();
//}
//Shouldn't have used Stack because it's obsolete, ArrayDeque is better. But in this case, might as well just use the resultingStringBuilder as a stack! No boxing required, and one loop less.
//    public String removeDuplicateLetters(String s) {
//        
//        int[] res = new int[26]; // will contain number of occurences of character (i+'a')
//        boolean[] visited = new boolean[26]; // will contain if character ('a' + i) is present in current result Stack
//        char[] ch = s.toCharArray();
//        for(char c : ch){  // count number of occurences of character 
//            res[c-'a']++;
//        }
//        StringBuilder sb = new StringBuilder();; // answer stack
//        int index;
//        for(char c : ch){ 
//            index = c - 'a';
//            res[index]--;   // decrement number of characters remaining in the string to be analysed
//            if(visited[index]) // if character is already present in stack, dont bother
//                continue;
//            // if current character is smaller than last character in stack which occurs later in the string again
//            // it can be removed and  added later e.g stack = bc remaining string abc then a can pop b and then c
//            while( (sb.length() > 0) && c < sb.charAt(sb.length()-1) && res[sb.charAt(sb.length()-1)-'a']!=0){ 
//                visited[sb.charAt(sb.length()-1) - 'a'] = false;
//                sb.deleteCharAt(sb.length()-1);
//            }
//            sb.append(c); // add current character and mark it as visited
//            visited[index] = true;
//        }
//        
//        return sb.toString();
//    
//    }
//I thought of the same approach, which is applied to another problem.
//For an in[] array, and int K, form maximum number, while maintaining order.
//Example - [2,3,9,8,2,6], and K = 3, the maximum number formed is [9,8,6].
//[2] - Add, while remaining K = 2, Remaining elements is 5
//[2,3] - Add 3, while remaining K = 1, Remaining elements is 4
//[9] - Pop 3 and 2, since 9 is greater than both and remaining K = 2 < elements = 3
//[9,8] - Add 8, less than 9, and remainging K = 1 < elements = 2
//[9,8,2] - Add 2, less than 8, and remainging K = 0 < elements = 1
//[9,8,6] - Pop 2, Add 6, since popping 2 makes K = 1, and element left is 1, which is 6
//https://discuss.leetcode.com/topic/40831/the-pattern-of-this-type-of-problem-in-leetcode
//I think this solution is really concise! But I want to add some detailed explainations to show why we do so to solve the problem, This problem is in fact similiar to the problem "Largest Rectangle under the histogram "
//We need to keep the monotically decreasing substring that contains all the char in the s. So we just use a vector to mimic the stack! Just similiar to the previous many solutions that use the vector to simulate a stack.
//In fact this problem is also similiar to the problem that the maximum in the sliding windows, I strongly recommend you to grasp the sliding windows solutions.
//
//https://discuss.leetcode.com/topic/40928/java-o-n-solution-easy-understand
//https://discuss.leetcode.com/topic/31663/java-2ms-two-pointers-solution-or-stack-simulation-beats-99-72
//public String removeDuplicateLetters(String s) {
//    int[] counts = new int[26];
//    boolean[] added = new boolean[26];
//    if(s == null) return "";
//    
//    for(int i=0; i<s.length(); i++) counts[s.charAt(i) -'a']++;
//    
//    Stack<Character> stack = new Stack<Character>();
//    for(int i=0; i<s.length(); i++) {
//        char c = s.charAt(i);
//        int ind = c-'a';
//        
//        if(stack.isEmpty()) {
//            stack.push(c);
//            added[ind] = true;
//        } else if(!added[ind]) {
//            while(!stack.isEmpty() && stack.peek() > c && counts[stack.peek()-'a'] > 0) {
//                char cur = stack.pop();
//                added[cur-'a'] = false;
//            }
//            stack.push(c);
//            added[ind] = true;
//        } 
//
//        counts[ind]--;
//    }
//    
//    
//    StringBuffer str = new StringBuffer();
//    while(!stack.isEmpty()) str.insert(0, stack.pop());//str.insert(0, s1.pop());
//    
//    return str.toString();    
//}
//https://segmentfault.com/a/1190000004188227
//用stack的话，最多每个字符过两遍就可以了。读字符的过程中，把字符存到stack里，当发现stack之前存的字符中比当前字符大而且频率还大于0就可以把那个字符pop出去。类似这种题目都可以用stack解决
//    public String removeDuplicateLetters(String s) {
//        int[] freqs = new int[256];
//        
//        // 统计字符频率
//        for (int i = 0; i < s.length(); i++) {
//            freqs[s.charAt(i)]++;
//        }
//
//        boolean[] visited = new boolean[256]; // 用来标记存在stack里的字符
//        Deque<Character> q = new ArrayDeque<>();    
//        
//        for (int i = 0; i < s.length(); i++) {
//            char c = s.charAt(i);
//            freqs[c]--;
//            if (visited[c]) continue;
//
//            // pop出stack当中比当前字符大但后面还存在的的字符，
//            while (!q.isEmpty() && q.peek() > c && freqs[q.peek()] > 0) {
//                visited[q.pop()] = false;
//            }
//            q.push(c);
//            visited[c] = true;
//        }
//
//        StringBuilder sb = new StringBuilder();
//        for (char c : q) {
//            sb.append(c);
//        }
//
//        return sb.reverse().toString();
//    }
//http://buttercola.blogspot.com/2016/01/leetcode-remove-duplicate-letters.html
//https://leetcode.com/discuss/73777/easy-to-understand-iterative-java-solution
//
//http://www.cnblogs.com/yrbbest/p/5068729.html
//虽然time complexity是O(26n) = O(n)，但实际运行起来速度比较慢，因为有很多string的操作，而我们前面也遇到过，s.substring其实已经不是O(1)的复杂度，所以会比较慢。 Discuss里另外一位rikimberley的解法就快了很多，是用数组模拟stack，还要好好体会理解。
//public class Solution {
//    public String removeDuplicateLetters(String s) {
//        int[] cnt = new int[26];
//        boolean[] isCandidate = new boolean[26];
//        for (int i = 0; i < s.length(); i++) cnt[s.charAt(i) - 'a']++;
//        for (int i = 0; i < s.length(); i++) {
//            char ch = s.charAt(i);
//            isCandidate[ch - 'a'] = true;
//            if (--cnt[ch - 'a'] == 0) break;
//        }
//        for (int i = 0; i < 26; i++)
//            if (isCandidate[i]) {
//                char ch = (char) (i + 'a');
//                for (int j = 0; j < s.length(); j++)
//                    if (s.charAt(j) == ch) return ch + removeDuplicateLetters(s.substring(j + 1).replaceAll("" + ch, ""));
//            }
//        return "";
//    }
//}
//https://leetcode.com/discuss/74373/java-2ms-two-pointers-solution-or-stack-simulation-beats-72%25
//public String removeDuplicateLetters(String s) {
//        /**
//         * First loop: use an array cnt[] to count the number of times
//         * appeared for each letter in s.
//         *
//         * Second loop (Greedy): use a stack, pop() while (!stack.isEmpty()
//         * && (sc = stack.peek()) >= c && cnt[sc] > 0)
//         */
//
//        int i, n = s.length();
//        int[] cnt = new int[128];
//        boolean[] inRes = new boolean[128]; // whether a char is in res[]
//        char[] res = s.toCharArray(); // simulate a stack
//
//        for (i = 0; i < n; i++)
//            cnt[res[i]]++;
//
//        char c, sc;
//        int end = -1;
//        // now cnt[c] means the remaining count of the char c
//        for (i = 0; i < n; i++) {
//            c = res[i];
//            if (inRes[c]) {
//                cnt[c]--;
//                continue;
//            }
//
//            while (end >= 0 && (sc = res[end]) >= c && cnt[sc] > 0) {
//                end--;
//                inRes[sc] = false;
//            }
//
//            res[++end] = c;
//            cnt[c]--;
//            inRes[c] = true;
//        }
//        return String.valueOf(res).substring(0, end + 1);
//    }
//https://leetcode.com/discuss/75738/java-solution-using-stack-with-comments
//public String removeDuplicateLetters(String sr) {
//
//    int[] res = new int[26]; //will contain number of occurences of character (i+'a')
//    boolean[] visited = new boolean[26]; //will contain if character (i+'a') is present in current result Stack
//    char[] ch = sr.toCharArray();
//    for(char c: ch){  //count number of occurences of character 
//        res[c-'a']++;
//    }
//    Stack<Character> st = new Stack<>(); // answer stack
//    int index;
//    for(char s:ch){ 
//        index= s-'a';
//        res[index]--;   //decrement number of characters remaining in the string to be analysed
//        if(visited[index]) //if character is already present in stack, dont bother
//            continue;
//        //if current character is smaller than last character in stack which occurs later in the string again
//        //it can be removed and  added later e.g stack = bc remaining string abc then a can pop b and then c
//        while(!st.isEmpty() && s<st.peek() && res[st.peek()-'a']!=0){ 
//            visited[st.pop()-'a']=false;
//        }
//        st.push(s); //add current character and mark it as visited
//        visited[index]=true;
//    }
//
//    StringBuilder sb = new StringBuilder();
//    //pop character from stack and build answer string from back
//    while(!st.isEmpty()){
//        sb.insert(0,st.pop());
//    }
//    return sb.toString();
//
//}
//Shouldn't have used Stack because it's obsolete, ArrayDeque is better. But in this case, might as well just use the resulting StringBuilder as a stack! No boxing required, and one loop less.
//
//-- Not really better, as sb.deleteCharAt(sb.length()-1); need copy all data: System.arraycopy(value, index+1, value, index, count-index-1);
//public String removeDuplicateLetters(String s) {
//
//    int[] res = new int[26]; // will contain number of occurences of character (i+'a')
//    boolean[] visited = new boolean[26]; // will contain if character ('a' + i) is present in current result Stack
//    char[] ch = s.toCharArray();
//    for(char c : ch){  // count number of occurences of character
//        res[c-'a']++;
//    }
//    StringBuilder sb = new StringBuilder();; // answer stack
//    int index;
//    for(char c : ch){
//        index = c - 'a';
//        res[index]--;   // decrement number of characters remaining in the string to be analysed
//        if(visited[index]) // if character is already present in stack, dont bother
//            continue;
//        // if current character is smaller than last character in stack which occurs later in the string again
//        // it can be removed and  added later e.g stack = bc remaining string abc then a can pop b and then c
//        while( (sb.length() > 0) && c < sb.charAt(sb.length()-1) && res[sb.charAt(sb.length()-1)-'a']!=0){
//            visited[sb.charAt(sb.length()-1) - 'a'] = false;
//            sb.deleteCharAt(sb.length()-1);
//        }
//        sb.append(c); // add current character and mark it as visited
//        visited[index] = true;
//    }
//
//    return sb.toString();
//
//}
//
//http://leetcode0.blogspot.com/2015/12/remove-duplicate-letters.html
//就是每次从后往前数，知道全部发现string里的字符，然后在该位置i之前的字符里找到最小的char。
//
//    public String removeDuplicateLetters(String s) {
//
//        Set foundSet = new HashSet<Character>();
//
//        for(char ch : s.toCharArray())
//
//            foundSet.add(ch);
//
//        return helper(foundSet,new StringBuffer(s));
//
//    }
//
//    private String helper(Set foundSet, StringBuffer buf){
//
//        if(buf.length()==0)
//
//            return "";
//
//        Set set = new HashSet<Character>();
//
//        int i = buf.length()-1 ;
//
//        for( ;i>=0; i--){
//
//            set.add(buf.charAt(i));
//
//            if(set.size()== foundSet.size())
//
//                break;
//
//        }
//
//        // find the min char from 0 to i; inclusive
//
//        char ch = buf.charAt(0);
//
//        int index = 0;
//
//        for(int j = 1 ; j<= i ; j++)
//
//            if(buf.charAt(j)<ch){
//
//                ch = buf.charAt(j);
//
//                index = j;
//
//            }
//
//        // delete from foundSet
//
//        foundSet.remove(ch);
//
//        // delete all characters before j
//
//        buf.delete(0,index+1);
//
//        while(buf.indexOf(""+ch)>=0){
//
//            buf.deleteCharAt(buf.indexOf(""+ch));
//
//        }
//
//        return ""+ch+helper(foundSet,buf);
//
//    }
//
//https://segmentfault.com/a/1190000004188227
//这道题可以采用Greedy的思想，因为最后想要的结果是最小值，所以我们在满足要求的情况下把不断append最小的字符, 最后便可得到最小的字符串.
//关键点就是要满足什么要求以及怎么样去满足。首先要求就是得保证在原来的字符串中存在当前字符append的顺序，即要保证每次append的字符的次数大于0。我们可以用一个数组记录每个字符出现的次数，用一个指针从左到右扫，过程中减小对应字符次数，找当前最小字符, 找的过程中终止条件是发现某个字符次数等于0，因为继续扫的话最终结果很有可能缺那个字符.
//time: O(kn), space: O(k), k表示原字符串中unique字符的个数
//    public String removeDuplicateLetters(String s) {
//        if (s == null ||s.length() == 0)
//            return s;
//            
//        // 记录每个字符出现的次数    
//        int[] cnt = new int[26];
//        for (int i = 0; i < s.length(); i++) {
//            cnt[s.charAt(i) - 'a']++;
//        }
//        
//        // 找出当前最小字符
//        int pos = 0;
//        for (int i = 0; i < s.length(); i++) {
//            if (s.charAt(i) < s.charAt(pos))
//                pos = i;
//            
//            // 避免无字符可用
//            if (--cnt[s.charAt(i) - 'a'] == 0)
//                break;
//        }
//        
//        // 除去字符串中已经append的字符的所有重复值
//return s.charAt(pos) + removeDuplicateLetters(s.substring(pos + 1).replaceAll("" + s.charAt(pos), ""));
//    }
//http://traceformula.blogspot.com/2015/12/remove-duplicate-letters-leetcode.html
//I. Simple Solution - O(kN) 
//(Where k is the total number of distinct characters, N is length of the original string)
//
//My first question after reading the problem description is that: What is the first character of the possible result? Can I determine it?
//
//Yes, we can determine by an observation. If we call totalChars is the total number of distinct characters in the string. Then the required result must have a length of totalChars.  
//
//Furthermore, its first character must be the smallest among all possible candidates. So what is a possible candidate? We easily see that a character is able to be the first character in a possible result string must have (totalChars - 1) distinct characters staying behind it in the original string.
//
//For example, given s="bcabc", there are possible results "bca", "cab", "abc". We see that b=s[0] has 2 = totalChars-1 distinct characters behind it "a", "c".Similarly with c=s[1] and a=s[2]. Among these 3 possible candidates,  a=s[2] is the smallest. If there are 2 possible candidates with the same character, we just pick the one with smaller position.
//
//Now after getting the first character of the result, what we do next? We can eliminate that character from the original string (by marking it as assigned). Then we repeat the process for the rest of characters!
//public class Solution {  
//  
//    public int ASSIGNED = -1;  
//    public int UNTOUCHED = 0;  
//    public int TOUCHED = 1;  
//    public char LARGE_CHAR = (char) 127;  
//    public String removeDuplicateLetters(String s){  
//        int n = s.length();  
//        if(n == 0) return s;  
//          
//        //We use 128 is to avoid substraction  
//        //if we use 26, we have to substract 'a' from a char  
//        int[] status = new int[128];  
//          
//        char c, smallestChar;  
//        int totalChars = 0;  
//          
//        for(int i=0; i<n; i++){  
//            c = s.charAt(i);  
//            if(status[c] == UNTOUCHED) totalChars++;  
//            status[c] = TOUCHED;  
//        }  
//          
//        StringBuilder bd = new StringBuilder();  
//        int tt = -1; //temp variable  
//        int last = -1; //last position of char that was assigned  
//          
//        for(int i=totalChars; i>0; i--){  
//            smallestChar = LARGE_CHAR;  
//            totalChars = 0;  
//              
//            //reset the status array  
//            for(int j='a'; j<='z'; j++)   
//                if (status[j] == TOUCHED) status[j] = UNTOUCHED;  
//              
//            //choose the smallest candiate by running backward  
//            for(int j=n-1; j>last; j--){  
//                c = s.charAt(j);  
//                if(status[c] == ASSIGNED) continue;  
//                if(status[c] == UNTOUCHED)totalChars++;  
//                  
//                if(totalChars == i) {  
//                    if(c <= smallestChar){  
//                        smallestChar = c;  
//                        tt = j;  
//                    }  
//                }  
//                status[c] = TOUCHED;  
//            }  
//              
//            status[smallestChar] = ASSIGNED; //marked as assigned  
//            last = tt;  
//            bd.append(smallestChar);  
//        }  
//          
//        return bd.toString();  
//    }  
//}  
//II. Better Solution - O(hN)
//(Where h is a number between 1 and the total number of distinct characters)
//After coming up with the above solution, I mumble "smallest candidates, smallest char, smallest possible, smallest..." . Yes, in that solution, we tried to find a smallest single character, why don't we try to find a smallest set of characters? Let's go on that direction!
//
//We call a candidate set is a subset of distinct characters from 0 to i in the original string s so that there are still enough available characters from (i+1) to the end of string to make up totalChars distinct character.
//
//Let's consider s="bcabc", at position 0, the candidate set is {b}. At 1, the candidate sets are {b}, {c}, {b,c}. At 2, the candidate sets are {b}, {c}, {a}, {b,c}, {c,a}, {b,c,a}. And our purpose is the same: find the smallest candidate set (by smallest, we mean lexicographically smallest). I.e, At 1, smallest candidate set is obviously {b}, at 1 it is {b}, and at 2 is {a}.
//
//
//
//Suppose at position i, we have the smallest candidate set {a0,a1,..., ak}. Now at position i+1, what is the smallest candidate set? We know that {a0,a1,..., ak}are distinct. 
//
//If s[i+1] already in {a0,a1,..., ak}, is it possible that there is a subset {ai0, ai1, ..., aij} so that  {ai0, ai1, ..., aij, s[i+1]} < {a0,a1,..., ak} is also a candidate set? If yes, we can easily see that {ai0, ai1, ..., aij} is a candidate set at position i, and {ai0, ai1, ..., aij} < {a0,a1,..., ak} . This means that {a0,a1,..., ak}is not the smallest candidate set at position i. Therefore, we don't need to care if s[i+1] is already in the candidate set (or we call it "assigned").
//
//Now, if s[i+1] is not "assigned",  we have the same question - is it possible that there is a subset {ai0, ai1, ..., aij} so that  {ai0, ai1, ..., aij, s[i+1]} < {a0,a1,..., ak} is also a candidate set? If ak > s[i+1], and there are still characters that equals ak after i+1, we can remove ak and check again withak-1; if there is no more, replace it by s[i+1]. If ak < s[i+1], we cannot replace ak by s[i+1]. So we just simply add s[i+1] to the set. Simply enough?
//
//And to represent the smallest candidate set, we can use linked list, or array. Below are 2 different implementations.
//
//a) Using Linked List (also by array ^_^)
//view plainprint?
//public class Solution {  
//  
//    public static char START = (char)('a'-1);  
//    public String removeDuplicateLetters(String s){  
//        if(s.length() == 0) return s;  
//          
//        //We use 128 is to avoid substraction  
//        //if we use 26, we have to substract 'a' from a char  
//        int[] count = new int[128];  
//        char[] prev = new char[128];  
//        boolean[] assigned = new boolean[128];  
//        char c;  
//        char end = START;  
//          
//        for(int i=0; i<s.length(); i++){  
//            c = s.charAt(i);  
//            count[c]++;  
//        }  
//          
//        for(int i=0; i<s.length(); i++){  
//            c = s.charAt(i);  
//            count[c]--;  
//            if(assigned[c])  
//                continue;  
//                  
//            while(end >= c && count[end]>0){  
//                assigned[end] = false;  
//                end = prev[end];  
//            }  
//              
//            prev[c] = end;  
//            end = c;  
//            assigned[c] = true;  
//        }  
//          
//        StringBuilder bd = new StringBuilder();  
//        while(end>START){  
//            bd.append(end);  
//            end = prev[end];  
//        }  
//        return bd.reverse().toString();  
//    }  
//}  
//b) Using array (which similar to stack)
//view plainprint?
//public class Solution {  
//  
//    public String removeDuplicateLetters(String s){  
//        if(s.length() == 0) return s;  
//          
//        //We use 128 is to avoid substraction  
//        //if we use 26, we have to substract 'a' from a char  
//        int[] count = new int[128];  
//        char[] result = new char[26];  
//        boolean[] assigned = new boolean[128];  
//        char c;  
//        int end = -1;  
//          
//        for(int i=0; i<s.length(); i++){  
//            count[s.charAt(i)]++;  
//        }  
//          
//        for(int i=0; i<s.length(); i++){  
//            c = s.charAt(i);  
//            count[c]--;  
//            if(assigned[c])  
//                continue;  
//                  
//            while(end >= 0 && result[end] > c && count[result[end]]>0){  
//                assigned[result[end]] = false;  
//                end--;  
//            }  
//              
//            end++;  
//            result[end] = c;  
//            assigned[c] = true;  
//        }  
//          
//        StringBuilder bd = new StringBuilder();  
//        for(int i=0; i<=end; i++){  
//            bd.append(result[i]);  
//        }  
//        return bd.toString();  
//    }  
//}  
//http://bookshadow.com/weblog/2015/12/09/leetcode-remove-duplicate-letters/
//贪心算法（Greedy Algorithm）
//时间复杂度 O(k * n)，其中k为字符串中唯一字符的个数，n为字符串的长度
//枚举字符串前缀，直到遇到首个唯一字符为止，从前缀中挑选出字典序最小的作为首字符。
//
//然后从剩余字符串中移除所有与首字母相同的字母。
//
//重复此过程，直到选出所有唯一字符为止。
//    def removeDuplicateLetters(self, s):
//        """
//        :type s: str
//        :rtype: str
//        """
//        ans = ''
//        for x in range(len(set(s))):
//            top, idx = s[0], 0
//            counter = collections.Counter(s)
//            for y in range(len(s)):
//                if top > s[y]:
//                    top, idx = s[y], y
//                if counter[s[y]] == 1:
//                    break
//                counter[s[y]] -= 1
//            ans += top
//            s = s[idx+1:].replace(top,'')
//        return ans
//http://fujiaozhu.me/?p=772
//贪心，用栈维护，在看到Stack之前几乎无想法.. 栈中存放到当前位置为止的最优解，对于新到来的元素，如果其小于栈顶元素并且栈顶元素不是最后一次出现并且新元素未在栈中出现，就可以弹栈了，直至新元素可以加入或者判断出不需要不加入。
//
//
// string removeDuplicateLetters(string s) {
//
// string res = "";
//
// stack<char> stk;
//
// vector<bool> exist(26);
//
// vector<int> sum(26), cnt(26);
//
//
// for(int i = 0; i < s.size(); i ++)
//
// sum[s[i] - 'a'] ++;
//
// for(int i = 0; i < s.size(); i ++) {
//
// while(!stk.empty() && s[i] <= stk.top() && cnt[stk.top() - 'a'] != sum[stk.top() - 'a'] && !exist[s[i] - 'a']) {
//
// exist[stk.top() - 'a'] = 0;
//
// stk.pop();
//
// }
//
// if(!exist[s[i] - 'a']) {
//
// exist[s[i] - 'a'] = 1;
//
// stk.push(s[i]);
//
// }
//
// cnt[s[i] - 'a'] ++;
//
// }
//
// while(!stk.empty()) {
//
// res += stk.top();
//
// stk.pop();
//
// }
//
// reverse(res.begin(), res.end());
//
// return res;
//
// }
//https://leetcode.com/discuss/73777/easy-to-understand-iterative-java-solution
//The basic idea is to find out the smallest result letter by letter (one letter at a time). Here is the thinking process for input "cbacdcbc":
//find out the last appeared position for each letter; c - 7 b - 6 a - 2 d - 4
//find out the smallest index from the map in step 1 (a - 2);
//the first letter in the final result must be the smallest letter from index 0 to index 2;
//repeat step 2 to 3 to find out remaining letters.
//the smallest letter from index 0 to index 2: a
//the smallest letter from index 3 to index 4: c
//the smallest letter from index 4 to index 4: d
//the smallest letter from index 5 to index 6: b
//so the result is "acdb"
//Notes:
//after one letter is determined in step 3, it need to be removed from the "last appeared position map", and the same letter should be ignored in the following steps
//in step 3, the beginning index of the search range should be the index of previous determined letter plus one
//
//
//http://www.hrwhisper.me/leetcode-remove-duplicate-letters
//http://www.cnblogs.com/vision-love-programming/p/5044055.html
//http://blog.csdn.net/jiangbo1017/article/details/50252525
//
//TODO:
//https://discuss.leetcode.com/topic/31413/easy-to-understand-iterative-java-solution
//The basic idea is to find out the smallest result letter by letter (one letter at a time). Here is the thinking process for input "cbacdcbc":
//find out the last appeared position for each letter;
//c - 7
//b - 6
//a - 2
//d - 4
//find out the smallest index from the map in step 1 (a - 2);
//the first letter in the final result must be the smallest letter from index 0 to index 2;
//repeat step 2 to 3 to find out remaining letters.
//the smallest letter from index 0 to index 2: a
//the smallest letter from index 3 to index 4: c
//the smallest letter from index 4 to index 4: d
//the smallest letter from index 5 to index 6: b
//so the result is "acdb"
//Notes:
//after one letter is determined in step 3, it need to be removed from the "last appeared position map", and the same letter should be ignored in the following steps
//in step 3, the beginning index of the search range should be the index of previous determined letter plus one
//    public String removeDuplicateLetters(String s) {
//        if (s == null || s.length() <= 1) return s;
//
//        Map<Character, Integer> lastPosMap = new HashMap<>();
//        for (int i = 0; i < s.length(); i++) {
//            lastPosMap.put(s.charAt(i), i);
//        }
//
//        char[] result = new char[lastPosMap.size()];
//        int begin = 0, end = findMinLastPos(lastPosMap);
//
//        for (int i = 0; i < result.length; i++) {
//            char minChar = 'z' + 1;
//            for (int k = begin; k <= end; k++) {
//                if (lastPosMap.containsKey(s.charAt(k)) && s.charAt(k) < minChar) {
//                    minChar = s.charAt(k);
//                    begin = k+1;
//                }
//            }
//
//            result[i] = minChar;
//            if (i == result.length-1) break;
//
//            lastPosMap.remove(minChar);
//            if (s.charAt(end) == minChar) end = findMinLastPos(lastPosMap);
//        }
//
//        return new String(result);
//    }
//
//    private int findMinLastPos(Map<Character, Integer> lastPosMap) {
//        if (lastPosMap == null || lastPosMap.isEmpty()) return -1;
//        int minLastPos = Integer.MAX_VALUE;
//        for (int lastPos : lastPosMap.values()) {
//             minLastPos = Math.min(minLastPos, lastPos);
//        }
//        return minLastPos;
//    }
//
//https://discuss.leetcode.com/topic/31663/java-2ms-two-pointers-solution-or-stack-simulation-beats-99-72
//public String removeDuplicateLetters(String s) {
//  /**
//   * First loop: use an array cnt[] to count the number of times
//   * appeared for each letter in s.
//   * 
//   * Second loop (Greedy): use a stack, pop() while (!stack.isEmpty()
//   * && (sc = stack.peek()) >= c && cnt[sc] > 0)
//   */
//
//  int i, n = s.length();
//  int[] cnt = new int[128];
//  boolean[] inRes = new boolean[128]; // whether a char is in res[]
//  char[] res = s.toCharArray(); // simulate a stack
//
//  for (i = 0; i < n; i++)
//   cnt[res[i]]++;
//
//  char c, sc;
//  int end = -1;
//  // now cnt[c] means the remaining count of the char c
//  for (i = 0; i < n; i++) {
//   c = res[i];
//   if (inRes[c]) {
//    cnt[c]--;
//    continue;
//   }
//
//   while (end >= 0 && (sc = res[end]) >= c && cnt[sc] > 0) {
//    end--;
//    inRes[sc] = false;
//   }
//
//   res[++end] = c;
//   cnt[c]--;
//   inRes[c] = true;
//  }
//  return String.valueOf(res).substring(0, end + 1);
// }
//Indeed, converting s to charArray is nice because accessing each elements in string by array is faster. In my implementation I choose to use a constant space. However I'm at the cost of accessing string (s.charAt(i)) slower. The program runs 5ms, 2.5 times slower than the original.
//public String removeDuplicateLetters(String s) {
//    char[] stack = new char[26];
//    boolean[] inStack = new boolean[128];
//    int[] cnt = new int[128];
//    int top = -1, len = s.length();
//    for (int i = 0; i < len; i++) cnt[s.charAt(i)]++;
//    
//    for (int i = 0; i < len; i++) {
//        char c = s.charAt(i);
//        if (inStack[c]) {
//            cnt[c]--;
//            continue;
//        }
//        
//        while (top >= 0 && cnt[stack[top]] > 0 && stack[top] > c)
//            inStack[stack[top--]] = false;
//        
//        stack[++top] = c;
//        inStack[c] = true;
//        cnt[c]--;
//    }
//    
//    return String.valueOf(stack, 0, top + 1);
//}
// * @author het
// *
// */

//Given "bcabc"
//Return "abc"
//Given "cbacdcbc"
//Return "acdb"

//ac db 

// c  4    b  2   a 1  d 1
//
public class LeetCode316 {
	public static  String removeDuplicateLetters(String s) {
      int[] cnt = new int[26];
      int pos = 0; // the position for the smallest s[i]
      for (int i = 0; i < s.length(); i++) cnt[s.charAt(i) - 'a']++;
      for (int i = 0; i < s.length(); i++) {
          if (s.charAt(i) < s.charAt(pos)) pos = i;//from the beginning to the index where the first letter which appears the last time
          if (--cnt[s.charAt(i) - 'a'] == 0) break;   // find the smallest letter  from the beginning to the letter which is first to appear last 
      }// if(--cnt[s.charAt(i) - 'a']==0) break;
      return s.length() == 0 ? "" : s.charAt(pos) + removeDuplicateLetters(s.substring(pos + 1).replaceAll("" + s.charAt(pos), ""));
  }
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(removeDuplicateLetters("cbacdcbc"));
		//cbacdcbc"
		//Return "acdb"
	}

}
