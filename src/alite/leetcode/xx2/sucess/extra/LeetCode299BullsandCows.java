package alite.leetcode.xx2.sucess.extra;
/**
 * [LeetCode]Bulls and Cows | 书影博客
You are playing the following Bulls and Cows game with your friend: You write a 4-digit secret
 number and ask your friend to guess it, each time your friend guesses a number, you give a hint, 
 the hint tells your friend how many digits are in the correct positions (called "bulls") and how
  many digits are in the wrong positions (called "cows"), your friend will use those hints to find out the secret number.
For example:
Secret number:  1807  Friend's guess: 7810
Hint: 1 bull and 3 cows. (The bull is 8, the cows are 0, 1 and 7.)
According to Wikipedia: "Bulls and Cows (also known as Cows and Bulls or Pigs and Bulls or Bulls and Cleots) is an old code-breaking mind or paper and pencil game for two or more players, predating the similar commercially marketed board game Mastermind. The numerical version of the game is usually played with 4 digits, but can also be played with 3 or any other number of digits."
Write a function to return a hint according to the secret number and friend's guess, use A to indicate the bulls and B to indicate the cows, in the above example, your function should return 1A3B.
You may assume that the secret number and your friend's guess only contain digits, and their lengths are always equal.
X.
https://leetcode.com/discuss/67031/one-pass-java-solution
int b is the count of 'cow' events. The int[] digits stores the frequency of each digit (from 0 to 9) in secret and guess. ++digits[n] means digit n is found in secret. If digits[n] was 0, now it becomes 1 denoting that n has shown up once in secret, while --digits[n] means n is found in guess. Digits[n] being smaller than 0 means n has shown up in guess before. Therefore when we find n in secret and do ++digits[n], having ++digits[n]<=0 means n was somewhere in the guess before. It is confirmed a 'cow'. Vice versa, --digits[n] >=0 means n is found in guess AND n has shown up in secret before, which makes a 'cow'.

The idea is to iterate over the numbers in secret and in guess and count all bulls right away. For cows maintain an array that stores count of the number appearances in secret and inguess. Increment cows when either number from secret was already seen in guest or vice versa.


public String getHint(String secret, String guess) {
    int bulls = 0;
    int cows = 0;
    int[] numbers = new int[10];
    for (int i = 0; i<secret.length(); i++) {
        if (secret.charAt(i) == guess.charAt(i)) bulls++;
        else {
            if (numbers[secret.charAt(i)-'0']++ < 0) cows++;
            if (numbers[guess.charAt(i)-'0']-- > 0) cows++;
        }
    }
    return bulls + "A" + cows + "B";
}
http://www.cnblogs.com/grandyang/p/4929139.html
这道题提出了一个叫公牛母牛的游戏，其实就是之前文曲星上有的猜数字的游戏，有一个四位数字，你猜一个结果，然后根据你猜的结果和真实结果做对比，提示有多少个数字和位置都正确的叫做bulls，还提示有多少数字正确但位置不对的叫做cows，根据这些信息来引导我们继续猜测正确的数字。这道题并没有让我们实现整个游戏，而只用实现一次比较即可。给出两个字符串，让我们找出分别几个bulls和cows。这题需要用哈希表，来建立数字和其出现次数的映射。我最开始想的方法是用两次遍历，第一次遍历找出所有位置相同且值相同的数字，即bulls，并且记录secret中不是bulls的数字出现的次数。然后第二次遍历我们针对guess中不是bulls的位置，如果在哈希表中存在，cows自增1，然后映射值减1
    string getHint(string secret, string guess) {
        int m[256] = {0}, bulls = 0, cows = 0;
        for (int i = 0; i < secret.size(); ++i) {
            if (secret[i] == guess[i]) ++bulls;
            else ++m[secret[i]];
        }
        for (int i = 0; i < secret.size(); ++i) {
            if (secret[i] != guess[i] && m[guess[i]]) {
                ++cows;
                --m[guess[i]];
            }
        }
        return to_string(bulls) + "A" + to_string(cows) + "B";
    }
我们其实可以用一次循环就搞定的，在处理不是bulls的位置时，我们看如果secret当前位置数字的映射值小于0，则表示其在guess中出现过，cows自增1，然后映射值加1，如果guess当前位置的数字的映射值大于0，则表示其在secret中出现过，cows自增1，然后映射值减1
    string getHint(string secret, string guess) {
        int m[256] = {0}, bulls = 0, cows = 0;
        for (int i = 0; i < secret.size(); ++i) {
            if (secret[i] == guess[i]) ++bulls;
            else {
                if (m[secret[i]]++ < 0) ++cows;
                if (m[guess[i]]-- > 0) ++ cows;
            }
        }
        return to_string(bulls) + "A" + to_string(cows) + "B";
    }
最后我们还可以稍作修改写的更简洁一些，a是bulls的值，b是bulls和cows之和
    string getHint(string secret, string guess) {
        int m[256] = {0}, a = 0, b = 0, i = 0;
        for (char s : secret) {
            char g = guess[i++];
            a += s == g;
            b += (m[s]++ < 0) + (m[g]-- > 0);
        }
        return to_string(a) + "A" + to_string(b - a) + "B";
    }

http://likemyblogger.blogspot.com/2015/10/leetcode-299-bulls-and-cows.html

    string getHint(string secret, string guess) {

        int a = 0, b = 0, ns = secret.size(), ng = guess.size();

        if(ns!=ng || ns==0) return "0A0B";

         

        int s[10] = {0};

        int g[10] = {0};

        for(int i=0; i<ns; ++i){

            if(secret[i]==guess[i]){

                a++;

            }else{

                s[secret[i]-'0']++;

                g[guess[i]-'0']++;

            }

        }

        for(int i=0; i<10; ++i){

            b += min(s[i], g[i]);

        }

        return to_string(a)+"A"+to_string(b)+"B";

    }
http://storypku.com/2015/11/leetcode-question-299-bows-and-cows/

    string getHint(string secret, string guess) {

        int size = secret.size();

        int  *secret_dict = new int[10]();

        int  *guess_dict = new int[10]();

        int A = 0, B = 0;

        for(int i = 0; i < size; i++) {

            int s = secret[i] - '0', g = guess[i]- '0';

            if (s == g) {

                A++;

            } else {

                if (secret_dict[g] > 0) {

                    secret_dict[g] --;

                    B++;

                } else {

                    guess_dict[g]++;

                }

                if (guess_dict[s] > 0) {

                    guess_dict[s]--;

                    B++;

                } else {

                    secret_dict[s]++;

                }

                

            }

        }

        delete []secret_dict;

        delete []guess_dict;

        ostringstream oss;

        oss << A << 'A' << B << 'B';

        return oss.str();


    }
Best X. http://my.oschina.net/Tsybius2014/blog/524452
创建一个包含10个元素的数组，分别用于记录遇到的每个数字的情况。这个方法只需要遍历一次数组

    public String getHint(String secret, String guess) {

         

        if (secret == null || guess == null || secret.length() != guess.length()) {

            return "";

        }

         

        int countA = 0;

        int countB = 0;

        int[] count = new int[10];

         

        for (int i = 0; i < secret.length(); i++) {

            if (secret.charAt(i) == guess.charAt(i)) {

                countA++;

            } else {

                count[secret.charAt(i) - '0']++;

                if (count[secret.charAt(i) - '0'] <= 0) {

                    countB++;

                }

                count[guess.charAt(i)- '0']--;

                if (count[guess.charAt(i)- '0'] >= 0) {

                    countB++;

                }

            }

        }

         

        return String.valueOf(countA) + "A" + String.valueOf(countB) + "B";

    }
X. 按照0-9分别统计
https://leetcode.com/discuss/68660/java-solution-with-two-buckets
https://leetcode.com/discuss/67016/3-lines-in-python?show=67016
def getHint(self, secret, guess):
    bulls = sum(map(operator.eq, secret, guess))
    both = sum(min(secret.count(x), guess.count(x)) for x in '0123456789')

    return '%dA%dB' % (bulls, both - bulls)
 3     string getHint(string secret, string guess) {
 4         int bull = 0, both = 0, n = secret.length();
 5         for (int i = 0; i < n; i++)
 6             bull += (secret[i] == guess[i]);
 7         for (char c = '0'; c <= '9'; c++)
 8             both += min(count(secret.begin(), secret.end(), c), 
 9                         count(guess.begin(), guess.end(), c));
10         return to_string(bull) + "A" + to_string(both - bull) + "B";
11     }
12 };
https://leetcode.com/discuss/79935/easy-java-solution
public String getHint(String secret, String guess) {
    int bull = 0, cow = 0;
    int[] array = new int[10];

    for(int i = 0; i < secret.length(); i++) {
        char s = secret.charAt(i);
        char g = guess.charAt(i);
        if(s == g){
            bull++;
        }else {
            if(array[s - '0'] < 0) {
                cow++;
            }
            array[s - '0']++;
            if(array[g - '0'] > 0) {
                cow++;
            }
            array[g -'0']--;
        }
    }
    return bull + "A" + cow + "B";

}
https://leetcode.com/discuss/67031/one-pass-java-solution
The idea is to iterate over the numbers in secret and in guess and count all bulls right away. For cows maintain an array that stores count of the number appearances in secret and inguess. Increment cows when either number from secret was already seen in guest or vice versa.
https://leetcode.com/discuss/68660/java-solution-with-two-buckets
https://leetcode.com/discuss/89261/my-3ms-java-solution-may-help-u
public String getHint(String secret, String guess) {
    int bull = 0, cow = 0;

    int[] sarr = new int[10];
    int[] garr = new int[10];

    for(int i = 0; i < secret.length(); i++){
        if(secret.charAt(i) != guess.charAt(i)){
            sarr[secret.charAt(i)-'0']++;
            garr[guess.charAt(i)-'0']++;
        }else{
            bull++;
        }
    }

    for(int i = 0; i <= 9; i++){
        cow += Math.min(sarr[i], garr[i]);
    }

    return (bull + "A" + cow + "B");
}
http://my.oschina.net/Tsybius2014/blog/524452
做这道题还需要注意，一般的猜数字游戏中，被猜的数字有四个且互不相同，但在本题中，可以有任意多个数字，且数字有可能存在同一数字重复多次出现的情况。
一开始我想了一个比较笨的办法，即分别求出A和B的数量，暴力破解

    public String getHint(String secret, String guess) {

         

        if (secret == null || guess == null || secret.length() != guess.length()) {

            return "";

        }

         

        int countA = 0;

        int countB = 0;

         

        char[] arrA = secret.toCharArray();

        char[] arrB = guess.toCharArray();

         

        //求A的数量

        for (int i = 0; i < arrA.length; i++) {

            for (int j = 0; j < arrB.length; j++) {

                if (arrA[i] == ' ' || arrB[j] == ' ') {

                    continue;

                } else if (arrA[i] == arrB[j]) {

                    if (i == j) {

                        countA++;

                        arrA[i] = ' ';

                        arrB[j] = ' ';

                    }

                }

            }

        }


        //求B的数量

        for (int i = 0; i < arrA.length; i++) {

            for (int j = 0; j < arrB.length; j++) {

                if (arrA[i] == ' ' || arrB[j] == ' ') {

                    continue;

                } else if (arrA[i] == arrB[j]) {

                    countB++;

                    arrA[i] = ' ';

                    arrB[j] = ' ';

                }

            }

        }

         

        return String.valueOf(countA) + "A" + String.valueOf(countB) + "B";

    }

Read full article from [LeetCode]Bulls and Cows | 书影博客
 * @author het
 *
 */
public class LeetCode299BullsandCows {
	public String getHint(String secret, String guess) {
	    int bulls = 0;
	    int cows = 0;
	    int[] numbers = new int[10];
	    for (int i = 0; i<secret.length(); i++) {
	        if (secret.charAt(i) == guess.charAt(i)) bulls++;
	        else {
	            if (numbers[secret.charAt(i)-'0']++ < 0) cows++;
	            if (numbers[guess.charAt(i)-'0']-- > 0) cows++;
	        }
	    }
	    return bulls + "A" + cows + "B";
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(new LeetCode299BullsandCows().getHint("1807", "7810"));
		//1807  Friend's guess: 7810
	}

}
