package alite.leetcode.xx2.sucess.extra;
///**
// * LIKE CODING: LeetCode [273] Integer to English Words
//Convert a non-negative integer to its english words representation. Given input is guaranteed to be less than 231 - 1.
//For example,
//123 -> "One Hundred Twenty Three"
//12345 -> "Twelve Thousand Three Hundred Forty Five"
//1234567 -> "One Million Two Hundred Thirty Four Thousand Five Hundred Sixty Seven"
//
//const int BILLION = 1000000000;
//
//const int MILLION = 1000000;
//
//
//const int THOUSAND = 1000;
//
//
//
//
//
//http://blog.welkinlan.com/2015/09/29/integer-to-english-words-leetcode-java/
//
//
//public class Solution {
//
//
// private final String[] lessThan20 = {"", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen"};
//
//
// private final String[] tens = {"", "", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"};
//
//
// private final String[] thousands = {"", "Thousand", "Million", "Billion"};
//
//
// 
//
//
// public String numberToWords(int num) {
//
//
//  if (num == 0) {
//
//
//      return "Zero";
//
//
//  }
//
//
//  String result = "";
//
//
//  int i = 0;
//
//
//  while (num > 0) {
//
//
//      if (num % 1000 != 0) {
//
//
//          result = helper(num % 1000) + thousands[i] + " " + result;    
//
//
//      }
//
//
//      num /= 1000;
//
//
//      i++;
//
//
//  }
//
//
//  return result.trim();
//
//
// }
//
//
// 
//
//
// private String helper(int num) {
//
//
//     if (num == 0) {
//
//
//         return "";
//
//
//     } else if (num < 20) {
//
//
//         return lessThan20[num] + " ";
//
//
//     } else if (num < 100) {
//
//
//         return tens[num / 10] + " " + helper(num % 10);
//
//
//     } else {
//
//
//         return lessThan20[num / 100] + " Hundred " + helper(num % 100);
//
//
//     }
//
//
// }
//
//
//}
//
//
//http://www.jyuan92.com/blog/leetcode-integer-to-english-words/
//
//
//    String[] digit = {"", " One", " Two", " Three", " Four", " Five",
//
//
//            " Six", " Seven", " Eight", " Nine"};
//
//
//    String[] tenDigit = {" Ten", " Eleven", " Twelve", " Thirteen", " Fourteen", " Fifteen",
//
//
//            " Sixteen", " Seventeen", " Eighteen", " Nineteen"};
//
//
//    String[] tenMutipleDigit = {"", "", " Twenty", " Thirty", " Forty", " Fifty",
//
//
//
//
//            " Sixty", " Seventy", " Eighty", " Ninety"};
//
//
//public String numberToWords(int num) { 
//
//
//    String[] bigs = new String[]{" Thousand", " Million", " Billion"};
//
//
//    StringBuilder sb = new StringBuilder();
//
//
//    int i = 0;
//
//
//    sb.append(convertToWords(num % 1000));
//
//
//    num /= 1000; // no need to first preprocess
//
//
//    while (num > 0) {
//
//
//        if (num % 1000 != 0) {
//
//
//            sb.insert(0, convertToWords(num % 1000) + bigs[i]);
//
//
//        }
//
//
//        i++;
//
//
//        num /= 1000;
//
//
//    }
//
//
//    return sb.length() == 0 ? "Zero" : sb.toString().trim();
//
//
//}
//
//
// 
//
//
//public String convertToWords(int num) { //rename to like: threeDigitToWords
//
//
//    StringBuilder sb = new StringBuilder();
//
//
//    if (num >= 100) {
//
//
//        sb.append(digit[num / 100]).append(" Hundred");
//
//
//        num %= 100;
//
//
//    }
//
//
//    if (num > 9 && num < 20) {
//
//
//        sb.append(tenDigit[num - 10]);
//
//
//    } else {
//
//
//        if (num >= 20) {
//
//
//            sb.append(tenMutipleDigit[num / 10]);
//
//
//            num %= 10;
//
//
//        }
//
//
//        sb.append(digit[num]);
//
//
//    }
//
//
//    return sb.toString();
//
//
//}
//
//
//
//
//http://shibaili.blogspot.com/2015/09/day-128-256-265-paint-house.html
//
//
//    string translate(vector<string> &ones,vector<string> &oneTens,vector<string> &tens,vector<string> &ends,int nums,string end) {
//
//
//        string rt = "";
//
//
//        if (nums >= 100) {
//
//
//            rt += ones[nums / 100 - 1] + " Hundred";
//
//
//            nums %= 100;
//
//
//        }
//
//
//         
//
//
//        if (nums >= 10) {
//
//
//            if (rt != "") rt += " ";
//
//
//            if (nums <= 19) {
//
//
//                rt += oneTens[nums % 10];
//
//
//                return rt + end;
//
//
//            }
//
//
//            rt += tens[nums / 10 - 2];
//
//
//            nums %= 10;
//
//
//        }
//
//
//         
//
//
//        if (nums >= 1) {
//
//
//            if (rt != "") rt += " ";
//
//
//            rt += ones[nums - 1];
//
//
//        }
//
//
//         
//
//
//        return rt + end;
//
//
//    }
//
//
//
//
//
//
//    string numberToWords(int num) {
//
//
//        if (num == 0) return "Zero";
//
//
//        vector<string> ones = {"One","Two","Three","Four","Five","Six","Seven","Eight","Nine"};
//
//
//        vector<string> oneTens = {"Ten","Eleven","Twelve","Thirteen","Fourteen","Fifteen","Sixteen","Seventeen","Eighteen","Nineteen"};
//
//
//        vector<string> tens = {"Twenty","Thirty","Forty","Fifty","Sixty","Seventy","Eighty","Ninety"};
//
//
//        vector<string> ends = {" Billion"," Million"," Thousand",""};
//
//
//         
//
//
//        string rt = "";
//
//
//        int temp = 0, endI = 0,bill = 1000000000;
//
//
//        while (num > 0) {
//
//
//            if (num / bill > 0) {
//
//
//                if (rt != "") rt += " ";
//
//
//                rt += translate(ones,oneTens,tens,ends,num / bill,ends[endI]);
//
//
//            }
//
//
//            num %= bill;
//
//
//            bill /= 1000;
//
//
//            endI++;
//
//
//        }
//
//
//        return rt;
//
//
//
//
//
//    }
//
//
//class Solution {
//
//    unordered_map<int, string> hash;
//
//public:
//
//    Solution(){
//
//        hash[0] = "Zero";
//
//        hash[1] = "One";
//
//        hash[2] = "Two";
//
//        hash[3] = "Three";
//
//        hash[4] = "Four";
//
//        hash[5] = "Five";
//
//        hash[6] = "Six";
//
//        hash[7] = "Seven";
//
//        hash[8] = "Eight";
//
//        hash[9] = "Nine";
//
//        hash[10] = "Ten";
//
//        hash[11] = "Eleven";
//
//        hash[12] = "Twelve";
//
//        hash[13] = "Thirteen";
//
//        hash[14] = "Fourteen";
//
//        hash[15] = "Fifteen";
//
//        hash[16] = "Sixteen";
//
//        hash[17] = "Seventeen";
//
//        hash[18] = "Eighteen";
//
//        hash[19] = "Nineteen";
//
//        hash[20] = "Twenty";
//
//        hash[30] = "Thirty";
//
//        hash[40] = "Forty";
//
//        hash[50] = "Fifty";
//
//        hash[60] = "Sixty";
//
//        hash[70] = "Seventy";
//
//        hash[80] = "Eighty";
//
//        hash[90] = "Ninety";
//
//    }
//
//    string numberToWordsLessThousand(int num){
//
//        if(!num) return hash[num];
//
//        string s;
//
//        if(num/100) s += hash[num/100] + " Hundred";
//
//        num = num%100;
//
//        if(num>=20){
//
//            if(!s.empty()) s = s+" ";
//
//            s += hash[num/10*10];
//
//            num = num%10;
//
//        }
//
//        if(num){
//
//            if(!s.empty()) s = s+" ";
//
//            s += hash[num];
//
//        }
//
//        return s;
//
//    }
//
//    string numberToWords(int num) {
//
//        if(!num) return hash[num];
//
//        string s;
//
//        if(num>=BILLION){
//
//            int nb = num/BILLION;
//
//            s += numberToWordsLessThousand(nb) + " Billion";
//
//            num = num%BILLION;
//
//        }
//
//        if(num>=MILLION){
//
//            if(!s.empty()) s = s+" ";
//
//            int nm = num/MILLION;
//
//            s += numberToWordsLessThousand(nm) + " Million";
//
//            num = num%MILLION;
//
//        }
//
//        if(num>=THOUSAND){
//
//            if(!s.empty()) s = s+" ";
//
//            int nt = num/THOUSAND;
//
//            s += numberToWordsLessThousand(nt) + " Thousand";
//
//            num = num%THOUSAND;
//
//        }
//
//
//        if(num){
//
//            if(!s.empty()) s = s+" ";
//
//            s += numberToWordsLessThousand(num);
//
//        }
//
//        return s;
//
//    }
//
//};
//http://likesky3.iteye.com/blog/2240465
//    public String numberToWords(int num) {  
//        if (num == 0) return "Zero";  
//        StringBuilder result = new StringBuilder();  
//        boolean hasBillion = false;  
//        boolean hasMillion = false;  
//        boolean hasThousand = false;  
//        if (num >= 1000000000) {  
//            hasBillion = true;  
//            result.append(digits[num / 1000000000]).append(' ').append("Billion").append(' ');  
//            num %= 1000000000;  
//        }  
//        if (num >= 1000000) {  
//            hasMillion = true;  
//            result.append(readALessThousandNum(String.valueOf(num / 1000000), false)).append(' ').append("Million").append(' ');  
//            num %= 1000000;  
//        } /*else if (hasBillion && num > 0) { 
//            result.append("and").append(' '); 
//        }*/  
//        if (num >= 1000) {  
//            hasThousand = true;  
//            result.append(readALessThousandNum(String.valueOf(num / 1000), false)).append(' ').append("Thousand").append(' ');  
//            num %= 1000;  
//        } /*else if (hasMillion && num > 0) { 
//            result.append("and").append(' '); 
//        }*/  
//        result.append(readALessThousandNum(String.valueOf(num), hasThousand));  
//        return result.toString().trim();  
//    }  
//    private String[] digits = {"", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine"};  
//    private String[] tenx = {"Ten", "Eleven", "Twelve", "Thirteen", "Fourteen",   
//        "Fifteen", "Sixteen", "Seventeen","Eighteen", "Nineteen"  
//    };  
//    private String[] tens = {"","","Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"};  
//    public String readALessThousandNum(String num, boolean hasThousand) {  
//        if (num.length() == 1)  
//            num = "00" + num;  
//        else if (num.length() == 2)  
//            num = "0" + num;  
//        StringBuilder result = new StringBuilder();  
//        boolean hasHundred = false;  
//        if (num.charAt(0) != '0') {  
//            hasHundred = true;  
//            result.append(digits[num.charAt(0) - '0']).append(' ').append("Hundred").append(' ');  
//        } /*else if (hasThousand && (num.charAt(1) != '0'|| num.charAt(0) != '0')) { 
//            result.append("and"); 
//        }*/  
//        if (num.charAt(1) == '0' && num.charAt(2) != '0') {  
//            // if ((hasHundred || hasThousand))  
//            //     result.append("and").append(' ');  
//            result.append(digits[num.charAt(2) - '0']).append(' ');  
//        } else if (num.charAt(1) == '1') {  
//            // if (hasHundred || hasThousand)  
//            //     result.append("and").append(' ');  
//            result.append(tenx[num.charAt(2) - '0']).append(' ');  
//        } else {  
//            result.append(tens[num.charAt(1) - '0']).append(' ');  
//            if (num.charAt(2) != '0')  
//                result.append(digits[num.charAt(2) - '0']).append(' ');  
//        }  
//        return result.toString().trim();  
//    }  
//http://bookshadow.com/weblog/2015/08/31/leetcode-integer-english-words/
//class Solution(object):
//    def numberToWords(self, num):
//        to19 = 'One Two Three Four Five Six Seven Eight Nine Ten Eleven Twelve ' \
//               'Thirteen Fourteen Fifteen Sixteen Seventeen Eighteen Nineteen'.split()
//        tens = 'Twenty Thirty Forty Fifty Sixty Seventy Eighty Ninety'.split()
//        def words(n):
//            if n < 20:
//                return to19[n-1:n]
//            if n < 100:
//                return [tens[n/10-2]] + words(n%10)
//            if n < 1000:
//                return [to19[n/100-1]] + ['Hundred'] + words(n%100)
//            for p, w in enumerate(('Thousand', 'Million', 'Billion'), 1):
//                if n < 1000**(p+1):
//                    return words(n/1000**p) + [w] + words(n%1000**p)
//        return ' '.join(words(num)) or 'Zero'
//
//    def numberToWords(self, num):
//        lv1 = "Zero One Two Three Four Five Six Seven Eight Nine Ten \
//               Eleven Twelve Thirteen Fourteen Fifteen Sixteen Seventeen Eighteen Nineteen".split()
//        lv2 = "Twenty Thirty Forty Fifty Sixty Seventy Eighty Ninety".split()
//        lv3 = "Hundred"
//        lv4 = "Thousand Million Billion".split()
//        words, digits = [], 0
//        while num:
//            token, num = num % 1000, num / 1000
//            word = ''
//            if token > 99:
//                word += lv1[token / 100] + ' ' + lv3 + ' '
//                token %= 100
//            if token > 19:
//                word += lv2[token / 10 - 2] + ' '
//                token %= 10
//            if token > 0:
//                word += lv1[token] + ' '
//            word = word.strip()
//            if word:
//                word += ' ' + lv4[digits - 1] if digits else ''
//                words += word,
//            digits += 1
//        return ' '.join(words[::-1]) or 'Zero'
//Read full article from LIKE CODING: LeetCode [273] Integer to English Words
// * @author het
// *
// */
public class LeetCode273IntegertoEnglishWords {
    String[] digit = {"", " One", " Two", " Three", " Four", " Five",


            " Six", " Seven", " Eight", " Nine"};


    String[] tenDigit = {" Ten", " Eleven", " Twelve", " Thirteen", " Fourteen", " Fifteen",


            " Sixteen", " Seventeen", " Eighteen", " Nineteen"};


    String[] tenMutipleDigit = {"", "", " Twenty", " Thirty", " Forty", " Fifty",




            " Sixty", " Seventy", " Eighty", " Ninety"};


public String numberToWords(int num) { 


    String[] bigs = new String[]{" Thousand", " Million", " Billion"};


    StringBuilder sb = new StringBuilder();


    int i = 0;


    sb.append(convertToWords(num % 1000));


    num /= 1000; // no need to first preprocess


    while (num > 0) {


        if (num % 1000 != 0) {


            sb.insert(0, convertToWords(num % 1000) + bigs[i]);


        }


        i++;


        num /= 1000;


    }


    return sb.length() == 0 ? "Zero" : sb.toString().trim();


}


 


public String convertToWords(int num) { //rename to like: threeDigitToWords


    StringBuilder sb = new StringBuilder();


    if (num >= 100) {


        sb.append(digit[num / 100]).append(" Hundred");


        num %= 100;


    }


    if (num > 9 && num < 20) {


        sb.append(tenDigit[num - 10]);


    } else {


        if (num >= 20) {


            sb.append(tenMutipleDigit[num / 10]);


            num %= 10;


        }


        sb.append(digit[num]);


    }


    return sb.toString();


}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
