package alite.leetcode;
/**
 * https://leetcode.com/problems/strong-password-checker/
A password is considered strong if below conditions are all met:
It has at least 6 characters and at most 20 characters.
It must contain at least one lowercase letter, at least one uppercase letter, and at least one digit.
It must NOT contain three repeating characters in a row ("...aaa..." is weak, but "...aa...a..." is strong, assuming other conditions are met).
Write a function strongPasswordChecker(s), that takes a string s as input, and return the MINIMUM change required to make s a strong password. 
If s is already strong, return 0.
Insertion, deletion and replace of any one character are all considered as one change.

https://discuss.leetcode.com/topic/63185/java-easy-solution-with-explanation
    public int strongPasswordChecker(String s) {
        
        if(s.length()<2) return 6-s.length();
        
        //Initialize the states, including current ending character(end), existence of lowercase letter(lower), uppercase letter(upper), digit(digit) and number of replicates for ending character(end_rep)
        char end = s.charAt(0);
        boolean upper = end>='A'&&end<='Z', lower = end>='a'&&end<='z', digit = end>='0'&&end<='9';
        
        //Also initialize the number of modification for repeated characters, total number needed for eliminate all consequnce 3 same character by replacement(change), and potential maximun operation of deleting characters(delete). Note delete[0] means maximum number of reduce 1 replacement operation by 1 deletion operation, delete[1] means maximun number of reduce 1 replacement by 2 deletion operation, delete[2] is no use here. 
        int end_rep = 1, change = 0;
        int[] delete = new int[3];
        
        for(int i = 1;i<s.length();++i){
            if(s.charAt(i)==end) ++end_rep;
            else{
                change+=end_rep/3;
                if(end_rep/3>0) ++delete[end_rep%3];
                //updating the states
                end = s.charAt(i);
                upper = upper||end>='A'&&end<='Z';
                lower = lower||end>='a'&&end<='z';
                digit = digit||end>='0'&&end<='9';
                end_rep = 1;
            }
        }
        change+=end_rep/3;
        if(end_rep/3>0) ++delete[end_rep%3];
        
        //The number of replcement needed for missing of specific character(lower/upper/digit)
        int check_req = (upper?0:1)+(lower?0:1)+(digit?0:1);
        
        if(s.length()>20){
            int del = s.length()-20;
            
            //Reduce the number of replacement operation by deletion
            if(del<=delete[0]) change-=del;
            else if(del-delete[0]<=2*delete[1]) change-=delete[0]+(del-delete[0])/2;
            else change-=delete[0]+delete[1]+(del-delete[0]-2*delete[1])/3;
            
            return del+Math.max(check_req,change);
        }
        else return Math.max(6-s.length(), Math.max(check_req, change));
    }
http://www.cnblogs.com/zslhq/p/5986623.html
http://www.cnblogs.com/grandyang/p/5988792.html
https://discuss.leetcode.com/topic/63854/o-n-java-solution-by-analyzing-changes-allowed-to-fix-each-condition
The basic principle is straightforward: if we want to make MINIMUM change to turn s into a strong password, 
each change
 made should fix as many problems as possible.
So first let's figure out what changes are suitable for righting each condition. Just to clarify, each change here 
should be characterized by at least two factors: the type of operation it takes and the position in the string
 where the operation is applied.
(Note: Ideally we should also include the characters involved in the operation and the "power" of each operation for 
eliminating problems but they turn out to be partially relevant so I will mention them only when appropriate.)
Length problem: if the total length is less than 6, the change that should be made is (insert, any position), 
which reads as "the operation is insertion and it can be applied to anywhere in the string". If the total length is
 greater than 20, then the change should be (delete, any position).
Missing letter or digit: if any of the lowercase/uppercase letters or digits is missing, we can do either 
(insert, any position) or (replace, any position) to correct it. (Note here the characters for insertion or 
replacement can only be those missing.)
Repeating characters: for repeating characters, all three operations are allowed but the positions where they 
can be applied are limited within the repeating characters. For example, to fix "aaaaa", we can do one
 replacement (replace the middle 'a') or two insertion (one after the second 'a' and one after the fourth 'a') 
 or three deletion (delete any of the three 'a's). So the possible changes are (replace, repeating characters), 
 (insert, repeating characters), (delete, repeating characters). (Note here the "power" of each operation
  for fixing the problem are different -- replacement is the strongest while deletion is the weakest.)
All right, what's next? If we want a change to eliminate as many problems as it can, it must be shared 
among the possible solutions to each problem it can fix. So our task is to find out possible overlappings 
among the changes for fixing each problem.
Since there are most (three) changes allowed for the third condition, we may start from combinations 
first condition & third conditionand second condition & third condition. It's not too hard to conclude that
 any change that can fix the first condition or second condition is also able to fix the third one 
 (since the type of operation here is irrelevant, we are free to choose the position of the operation to 
 match those of the repeating characters). For combination first condition & second condition, depending on the length of the string, 
 there will be overlapping if length is less than 6 or no overlapping if length is greater than 20.
From the analyses above, it seems worthwhile to distinguish between the two cases: when the string length is too short or too long.
For the former case, it can be shown that the changes needed to fix the first and second conditions always outnumber those for the third one. Since whatever change used fixing the first two conditions can also correct the third one, we may concern ourselves with only the first two conditions. Also as there are overlappings between the changes for fixing these two conditions, we will prefer those overlapping ones, i.e. (insert, any position). Another point is that the characters involved in the operation matters now. To fix the first condition, only those missing characters can be inserted while for the second condition, it can be any character. Therefore correcting the first condition takes precedence over the second one.
For the latter case, there are overlappings between the first & third and second & third conditions, 
so those changes will be taken, i.e., first condition => (delete, any position), 
second condition => (replace, any position). The reason not to use (insert, any position) for the second condition
 is that it contradicts the changes made to the first condition (therefore has the tendency to cancel its effects).
  After fixing the first two conditions, what operations should we choose for the third one?
Now the "power" of each operation for eliminating problems comes into play. For the third condition,
 the "power" of each operation will be measured by the maximum number of repeating characters it is able
  to get rid of. For example, one replacement can eliminate at most 5 repeating characters while 
  insertion and deletion can do at most 4 and 3, respectively. In this case, we say replacement has more "power" 
  than insertion or deletion. Intuitively the more "powerful" the operation is, the less number of changes is needed 
  for correcting the problem. Therefore (replace, repeating characters) triumphs in terms of fixing the third condition.
Further more, another very interesting point shows up when the "power" of operation is taken into consideration 
(And thank yicui for pointing it out). As I mentioned that there are overlappings between changes made for fixing 
the first two conditions and for the third one, which means the operations chosen above for the first two conditions
 will also be applied to the third one. For the second condition with change chosen as (replace, any position)
 , we have no problem adapting it so that it coincides with the optimal change (replace, repeating characters) 
 for the third condition. However, there is no way to do that for the first condition with change (delete, any position). 
 We have a conflict now!
So how do we reconcile it? The trick is that for a sequence of repeating characters of length k (k >=3), 
instead of turning it all the way into a sequence of length 2 (so as to fix the repeating character problem)
 by the change (delete, any position), we will first reduce its length to (3m + 2), 
 where (3m + 2) is the largest integer of the form yet no more than k. That is to say,
  if k is a multiple of 3, we apply once such change so its length will become (k - 1);
   else if k is a multiple of 3 plus 1, we apply twice such change to cut its length down to (k - 2), 
   provided we have more such changes to spare (note here we need at least two changes but the remaining available
    changes may be less than that, so we should stick to the smaller one: 2 or the remaining available changes). 
    The reason is that the optimal change (replace, repeating characters) for the third condition will be most powerful 
    when the total length of the repeating characters is of this form. Of course, if we still have more changes (delete, 
    any position) to do after that, then we are free to turn the repeating sequence all the way into a sequence of length 2.

A quick explanation of the program:
"res" denotes the minimum changes; "a", "A" and "d" are the number of missing lowercase letters, uppercase letters and digits, 
respectively; "arr" is an integer array whose element will be the number of repeating characters starting 
at the corresponding position in the string.
In the following loop we fill in the values for "a", "A", "d" and "arr" to identify the problems
 for each condition. The total number of missing characters "total_missing" will be the summation of
  "a", "A", "d" and fixing this problem takes at least "total_missing" changes.
We then distinguish the two cases when the string is too short or too long. If it is too short,
 we pad its length to at least 6 (note in this case we've already inserted "total_missing" characters so 
 the new length is the summation of the original length and "total_missing").
Otherwise, to fix the first condition, we need to delete "over_len" (number of surplus characters) characters. 
Since fixing the first condition also corrects the third one, we need to get rid of those parts from the "arr" 
array. And as I mentioned, we need to first turn all numbers in the "arr" array greater than 2 into the form of
 (3m + 2) and then reduce them all the way to 2 if "over_len" is still greater than 0. After that, we need to 
 replace "total_missing" characters to fix the second condition, which also fixes part (or all) of the third condition. 
 Therefore we only need to take the larger number of changes needed for fixing the second condition (which is "total_missing")
  and the third condition (which is "left_over", as it is the number after fixing the first one).
Both time and space complexity is O(n). Not sure if we can reduce the space down to O(1) by computing the "arr" array on the fly.
public int strongPasswordChecker(String s) {
    int res = 0, a = 1, A = 1, d = 1;
    char[] carr = s.toCharArray();
    int[] arr = new int[carr.length];
        
    for (int i = 0; i < arr.length;) {
        if (Character.isLowerCase(carr[i])) a = 0;
        if (Character.isUpperCase(carr[i])) A = 0;
        if (Character.isDigit(carr[i])) d = 0;
            
        int j = i;
        while (i < carr.length && carr[i] == carr[j]) i++;
        arr[j] = i - j;
    }
        
    int total_missing = (a + A + d);

    if (arr.length < 6) {
        res += total_missing + Math.max(0, 6 - (arr.length + total_missing));
            
    } else {
        int over_len = Math.max(arr.length - 20, 0), left_over = 0;
        res += over_len;
            
        for (int k = 1; k < 3; k++) {
            for (int i = 0; i < arr.length && over_len > 0; i++) {
                if (arr[i] < 3 || arr[i] % 3 != (k - 1)) continue;
                arr[i] -= Math.min(over_len, k);
                over_len -= k;
            }
        }
            
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] >= 3 && over_len > 0) {
                int need = arr[i] - 2;
                arr[i] -= over_len;
                over_len -= need;
            }
                
            if (arr[i] >= 3) left_over += arr[i] / 3;
        }
            
        res += Math.max(total_missing, left_over);
    }
        
    return res;
}

https://discuss.leetcode.com/topic/63185/java-easy-solution-with-explanation
The general idea is to record some states, and calculate the edit distance at the end. All detail are explained in the comments.
    public int strongPasswordChecker(String s) {
        
        if(s.length()<2) return 6-s.length();
        
        //Initialize the states, including current ending character(end), existence of lowercase letter(lower), uppercase letter(upper), digit(digit) and number of replicates for ending character(end_rep)
        char end = s.charAt(0);
        boolean upper = end>='A'&&end<='Z', lower = end>='a'&&end<='z', digit = end>='0'&&end<='9';
        
        //Also initialize the number of modification for repeated characters, total number needed for eliminate all consequnce 3 same character by replacement(change), and potential maximun operation of deleting characters(delete). Note delete[0] means maximum number of reduce 1 replacement operation by 1 deletion operation, delete[1] means maximun number of reduce 1 replacement by 2 deletion operation, delete[2] is no use here. 
        int end_rep = 1, change = 0;
        int[] delete = new int[3];
        
        for(int i = 1;i<s.length();++i){
            if(s.charAt(i)==end) ++end_rep;
            else{
                change+=end_rep/3;
                if(end_rep/3>0) ++delete[end_rep%3];
                //updating the states
                end = s.charAt(i);
                upper = upper||end>='A'&&end<='Z';
                lower = lower||end>='a'&&end<='z';
                digit = digit||end>='0'&&end<='9';
                end_rep = 1;
            }
        }
        change+=end_rep/3;
        if(end_rep/3>0) ++delete[end_rep%3];
        
        //The number of replcement needed for missing of specific character(lower/upper/digit)
        int check_req = (upper?0:1)+(lower?0:1)+(digit?0:1);
        
        if(s.length()>20){
            int del = s.length()-20;
            
            //Reduce the number of replacement operation by deletion
            if(del<=delete[0]) change-=del;
            else if(del-delete[0]<=2*delete[1]) change-=delete[0]+(del-delete[0])/2;
            else change-=delete[0]+delete[1]+(del-delete[0]-2*delete[1])/3;
            
            return del+Math.max(check_req,change);
        }
        else return Math.max(6-s.length(), Math.max(check_req, change));
    }
}
 * @author het
 *
 */
public class LeetCode420_Important_Review_StrongPasswordChecker {
//	http://www.cnblogs.com/zslhq/p/5986623.html
//		http://www.cnblogs.com/grandyang/p/5988792.html
//	A password is considered strong if below conditions are all met:
//		It has at least 6 characters and at most 20 characters.
//		It must contain at least one lowercase letter, at least one uppercase letter, and at least one digit.
//		It must NOT contain three repeating characters in a row ("...aaa..." is weak, but "...aa...a..." is strong,
	//assuming other conditions are met).
//		Write a function strongPasswordChecker(s), that takes a string s as input, and return 
	//the MINIMUM change required to make s a strong password. If s is already strong, return 0.
//		Insertion, deletion and replace of any one character are all considered as one change.
//		https://discuss.leetcode.com/topic/63854/o-n-java-solution-by-analyzing-changes-allowed-to-fix-each-condition
//		The basic principle is straightforward: if we want to make MINIMUM change to turn s into a strong password, each change
//		 made should fix as many problems as possible.
//		So first let's figure out what changes are suitable for righting each condition. Just to clarify, each change here 
//		should be characterized by at least two factors: the type of operation it takes and the position in the string
//		 where the operation is applied.
//		(Note: Ideally we should also include the characters involved in the operation and the "power" of each operation for 
//		eliminating problems but they turn out to be partially relevant so I will mention them only when appropriate.)
//		Length problem: if the total length is less than 6, the change that should be made is (insert, any position), 
//		which reads as "the operation is insertion and it can be applied to anywhere in the string". If the total length is
//		 greater than 20, then the change should be (delete, any position).
//		Missing letter or digit: if any of the lowercase/uppercase letters or digits is missing, we can do either 
//		(insert, any position) or (replace, any position) to correct it. (Note here the characters for insertion or 
//		replacement can only be those missing.)
//		Repeating characters: for repeating characters, all three operations are allowed but the positions where they 
//		can be applied are limited within the repeating characters. For example, to fix "aaaaa", we can do one
//		 replacement (replace the middle 'a') or two insertion (one after the second 'a' and one after the fourth 'a') 
//		 or three deletion (delete any of the three 'a's). So the possible changes are (replace, repeating characters), 
//		 (insert, repeating characters), (delete, repeating characters). (Note here the "power" of each operation
//		  for fixing the problem are different -- replacement is the strongest while deletion is the weakest.)
//		All right, what's next? If we want a change to eliminate as many problems as it can, it must be shared 
//		among the possible solutions to each problem it can fix. So our task is to find out possible overlappings 
//		among the changes for fixing each problem.
//		Since there are most (three) changes allowed for the third condition, we may start from combinations 
//		first condition & third conditionand second condition & third condition. It's not too hard to conclude that
//		 any change that can fix the first condition or second condition is also able to fix the third one 
//		 (since the type of operation here is irrelevant, we are free to choose the position of the operation to 
//		 match those of the repeating characters). For combination first condition & second condition, depending on the 
	//length of the string, 
//		 there will be overlapping if length is less than 6 or no overlapping if length is greater than 20.
//		From the analyses above, it seems worthwhile to distinguish between the two cases: when the string length is too short or too long.
//		For the former case, it can be shown that the changes needed to fix the first and second conditions always
	//outnumber those for the third one. Since whatever change used fixing the first two
	//conditions can also correct the third one, we may concern ourselves with only the first two conditions.
	//Also as there are overlappings between the changes for fixing these two conditions, we will prefer
	//those overlapping ones, i.e. (insert, any position). Another point is that the characters 
	//involved in the operation matters now. To fix the first condition, only those missing characters can be 
	//inserted while for the second condition, it can be any character. Therefore correcting the first 
	//condition takes precedence over the second one.
//		For the latter case, there are overlappings between the first & third and second & third conditions, 
//		so those changes will be taken, i.e., first condition => (delete, any position), 
//		second condition => (replace, any position). The reason not to use (insert, any position) for the second condition
//		 is that it contradicts the changes made to the first condition (therefore has the tendency to cancel its effects).
//		  After fixing the first two conditions, what operations should we choose for the third one?
//		Now the "power" of each operation for eliminating problems comes into play. For the third condition,
//		 the "power" of each operation will be measured by the maximum number of repeating characters it is able
//		  to get rid of. For example, one replacement can eliminate at most 5 repeating characters while 
//		  insertion and deletion can do at most 4 and 3, respectively. In this case, we say replacement has more "power" 
//		  than insertion or deletion. Intuitively the more "powerful" the operation is, the less number of changes is needed 
//		  for correcting the problem. Therefore (replace, repeating characters) triumphs in terms of fixing the third condition.
//		Further more, another very interesting point shows up when the "power" of operation is taken into consideration 
//		(And thank yicui for pointing it out). As I mentioned that there are overlappings between changes made for fixing 
//		the first two conditions and for the third one, which means the operations chosen above for the first two conditions
//		 will also be applied to the third one. For the second condition with change chosen as (replace, any position)
//		 , we have no problem adapting it so that it coincides with the optimal change (replace, repeating characters) 
//		 for the third condition. However, there is no way to do that for the first condition with change (delete, any position). 
//		 We have a conflict now!
//		So how do we reconcile it? The trick is that for a sequence of repeating characters of length k (k >=3), 
//		instead of turning it all the way into a sequence of length 2 (so as to fix the repeating character problem)
//		 by the change (delete, any position), we will first reduce its length to (3m + 2), 
//		 where (3m + 2) is the largest integer of the form yet no more than k. That is to say,
//		  if k is a multiple of 3, we apply once such change so its length will become (k - 1);
//		   else if k is a multiple of 3 plus 1, we apply twice such change to cut its length down to (k - 2), 
//		   provided we have more such changes to spare (note here we need at least two changes but the remaining available
//		    changes may be less than that, so we should stick to the smaller one: 2 or the remaining available changes). 
//		    The reason is that the optimal change (replace, repeating characters) for the third condition will be most powerful 
//		    when the total length of the repeating characters is of this form. Of course, if we still have more changes (delete, 
//		    any position) to do after that, then we are free to turn the repeating sequence all the way into a sequence of length 2.
//
//		A quick explanation of the program:
//		"res" denotes the minimum changes; "a", "A" and "d" are the number of missing lowercase letters, uppercase letters and digits, respectively;
	//"arr" is an integer array whose element will be the number of repeating characters starting at the corresponding position in the string.
//		In the following loop we fill in the values for "a", "A", "d" and "arr" to identify the problems for each condition. 
	//The total number of missing characters "total_missing" will be the summation of "a", "A", "d" and fixing this problem takes at least "total_missing" changes.
//		We then distinguish the two cases when the string is too short or too long. If it is too short, we pad its length to at least 6 
	//(note in this case we've already inserted "total_missing" characters so the new length is the summation of the original length and "total_missing").
//		Otherwise, to fix the first condition, we need to delete "over_len" (number of surplus characters) characters.
	//Since fixing the first condition also corrects the third one, we need to get rid of those parts from the "arr" array. 
	//And as I mentioned, we need to first turn all numbers in the "arr" array greater than 2 into the form of (3m + 2) and
	//then reduce them all the way to 2 if "over_len" is still greater than 0. After that, we need to replace "total_missing" 
	//characters to fix the second condition, which also fixes part (or all) of the third condition. 
	//Therefore we only need to take the larger number of changes needed for fixing the second condition (which is "total_missing")
	//and the third condition (which is "left_over", as it is the number after fixing the first one).
//		Both time and space complexity is O(n). Not sure if we can reduce the space down to O(1) by computing the "arr" array on the fly.
		public int strongPasswordChecker(String s) {
		    int res = 0, a = 1, A = 1, d = 1;
		    char[] carr = s.toCharArray();
		    int[] arr = new int[carr.length];
		        
		    for (int i = 0; i < arr.length;) {
		        if (Character.isLowerCase(carr[i])) a = 0;
		        if (Character.isUpperCase(carr[i])) A = 0;
		        if (Character.isDigit(carr[i])) d = 0;
		            
		        int j = i;
		        while (i < carr.length && carr[i] == carr[j]) i++;
		        arr[j] = i - j;
		    }
		        
		    int total_missing = (a + A + d);

		    if (arr.length < 6) {
		        res += total_missing + Math.max(0, 6 - (arr.length + total_missing));
		            
		    } else {
		        int over_len = Math.max(arr.length - 20, 0), left_over = 0;
		        res += over_len;
		            
		        for (int k = 1; k < 3; k++) {
		            for (int i = 0; i < arr.length && over_len > 0; i++) {
		                if (arr[i] < 3 || arr[i] % 3 != (k - 1)) continue;
		                arr[i] -= Math.min(over_len, k);
		                over_len -= k;
		            }
		        }
		            
		        for (int i = 0; i < arr.length; i++) {
		            if (arr[i] >= 3 && over_len > 0) {
		                int need = arr[i] - 2;
		                arr[i] -= over_len;
		                over_len -= need;
		            }
		                
		            if (arr[i] >= 3) left_over += arr[i] / 3;
		        }
		            
		        res += Math.max(total_missing, left_over);
		    }
		        
		    return res;
		}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	
//	[LeetCode]Strong Password Checker 
//	作者是 在线疯狂 发布于 2016年10月21日 在 LeetCode.
//	题目描述：
//	LeetCode 420. Strong Password Checker
//
//	A password is considered strong if below conditions are all met:
//
//	It has at least 6 characters and at most 20 characters.
//	It must contain at least one lowercase letter, at least one uppercase letter, and at least one digit.
//	It must NOT contain three repeating characters in a row ("...aaa..." is weak, but "...aa...a..." is strong, assuming other conditions are met).
//	Write a function strongPasswordChecker(s), that takes a string s as input, and return the MINIMUM change required to make s a strong password. If s is already strong, return 0.
//
//	Insertion, deletion or replace of any one character are all considered as one change.
//
//	题目大意：
//	密码如果满足下列条件，就被认为是“强密码”：
//
//	至少6个字符，至多20个字符。
//	必须至少包含一个小写字母，一个大写字母以及一个数字。
//	不可以包含3个重复的字符（假设其余条件均满足，"...aaa..."是弱密码, 但"...aa...a..."是强密码）。
//	编写函数strongPasswordChecker(s)，输入字符串s，返回将密码转换为强密码所需的最小改变次数。如果s已经是强密码，就返回0。
//
//	插入、删除或者替换任意字符均被认为是一次变更。
//
//	解题思路：
//	贪心算法（Greedy Algorithm）
//
//	处理重复次数超过3的弱密码字符有3种策略：
//
//	1. 替换字符，例如"aaaaa" -> "aabaa"
//
//	2. 删除字符，例如"aaaaa" -> "aa"
//
//	3. 新增字符，例如"aaaaa" -> "aababaa"
//	替换字符所需的改变次数 ≤ 新增字符所需的改变次数 ≤ 删除字符所需的改变次数
//
//	优先采用替换的方式处理重复字符（还可以顺便补足缺少的字符类型）；
//
//	当字符总数不足6时，考虑采用新增字符的方式；
//
//	当字符总数超过20时才考虑采用删除字符的方式。
//	统计字符串s，获得以下参数：
//
//	totalCnt：字符总数
//
//	typeCnt：有效字符类型数（小写、大写字母、数字各算一种）
//
//	repeats：重复超过2次的字符的个数列表（例如字符串“aaabbcccdd0000”，对应repeats为：3, 3, 4）
//	下面分情况讨论：
//
//	若totalCnt < 6（低于最小字符数下限）：
//
//	若连续字符数为5个：直接返回max(2, 3  - typeCnt)
//
//	否则，直接返回max(6 - totalCnt, 3 - typeCnt)
//	若totalCnt > 20（超过最大字符数上限）：
//
//	删除多余字符，直到字符总数不大于20
//
//	需要删除的最小字符个数：deleteCnt = max(totalCnt - 20, 0)
//
//	删除字符时采用如下优先顺序：
//
//	1. 从重复次数是3的倍数的字符片段中删去1个字符，例如"aaa" -> "aa"（删去1个字符的同时，将替换字符的开销减1）
//
//	2. 从重复次数除3余1的字符片段中删去2个字符，例如"bbbb" -> "bb"（删去2个字符的同时，将替换字符的开销减1）
//
//	3. 从重复次数除3余2的字符片段中删去3个字符，例如"ccccc" -> "cc"（删去3个字符的同时，将替换字符的开销减1）
//	剩余重复字符的替换次数：changeCnt = sum(r / 3 for r in repeats)
//
//	最终结果为：deleteCnt + max(changeCnt, 3 - typeCnt)
//
//	Python代码：
//	class Solution(object):
//	    def strongPasswordChecker(self, s):
//	        """
//	        :type s: str
//	        :rtype: int
//	        """
//	        totalCnt = len(s)
//
//	        lowers = [c for c in s if c.islower()]
//	        uppers = [c for c in s if c.isupper()]
//	        digits = [c for c in s if c.isdigit()]
//
//	        typeCnt = bool(lowers) + bool(uppers) + bool(digits)
//
//	        clist = []
//	        li, lc = 0, (s[0] if s else None)
//	        for i, c in enumerate(s):
//	            if c != lc:
//	                clist.append( (lc, li, i - 1) )
//	                li, lc = i, c
//	        clist.append((lc, li, totalCnt - 1))
//
//	        repeats = [y - x + 1 for c, x, y in clist if y - x > 1]
//
//	        if totalCnt < 6:
//	            if max(repeats + [0]) == 5:
//	                return max(2, 3  - typeCnt)
//	            return max(6 - totalCnt, 3 - typeCnt)
//
//	        deleteCnt = max(totalCnt - 20, 0)
//
//	        heap = [(r % 3, r) for r in repeats]
//
//	        heapq.heapify(heap)
//	        while heap and totalCnt > 20:
//	            mod, r = heapq.heappop(heap)
//	            delta = min(mod + 1, totalCnt - 20)
//	            totalCnt -= delta
//	            heapq.heappush(heap, (2, r - delta))
//
//	        changeCnt = sum(r / 3 for mod, r in heap)
//
//	        return deleteCnt + max(changeCnt, 3 - typeCnt)

}










public class Solution {

    public int strongPasswordChecker(String s) {
        int sLen = s.length();
        if (sLen < 4) {
            return 6 - sLen;
        }
        int lnum = 1;
        int unum = 1;
        int dnum = 1;
        int rcount = 0;
        int ricount = 0;
        int rdcount = 0;
        int sameseq = 0;

        for (int i=0; i<sLen; i++) {
            char ch = s.charAt(i);
            if (ch>=‘a‘ && ch<=‘z‘) {
                lnum = 0;
            }
            if (ch>=‘A‘ && ch<=‘Z‘) {
                unum = 0;
            }
            if (ch>=‘0‘ && ch<=‘9‘) {
                dnum = 0;
            }

            // fix bug
            if (i == 0) {
                sameseq = 1;
            }
            else if (ch != s.charAt(i-1)) {
                if (sameseq >= 3) {
                    // 这个很重要
                    while (sLen + ricount < 6 && sameseq >= 3) {
                        ricount++;
                        sameseq -= 2;
                    }
                    while (sLen - rdcount > 20 && sameseq >= 3) {
                        rdcount++;
                        sameseq --;
                    }
                    rcount += sameseq / 3;
                }
                sameseq = 1;
            }
            else {
                sameseq++;
            }
        }

        // fixbug
        if (sameseq >= 3) {
            // 这个很重要
            while (sLen + ricount < 6 && sameseq >= 3) {
                ricount++;
                sameseq -= 2;
            }
            while (sLen - rdcount > 20 && sameseq >= 3) {
                rdcount++;
                sameseq --;
            }
            rcount += sameseq / 3;
        }

        //System.out.printf("rcount: %d, ricount: %d, rdcount: %d, lnum: %d, unum: %d, dnum: %d\n",
        //        rcount, ricount, rdcount, lnum, unum, dnum);

        int update = lnum + unum + dnum;
        int must = ricount + rcount;
        if (sLen + ricount < 6) {
            must += 6 - sLen - ricount;
        }
        if (sLen < 20) {
            return must > update ? must : update;
        }

        // 跟上面的不一样,因为删除字符是无法增加新的类型的
        if (sLen - rdcount > 20) {
            rdcount += sLen - rdcount - 20;
        }
        return rcount >= update ? rcount + rdcount : update + rdcount;

    }
    
}
 

以下是加了注释的版本：

public class Solution {

    public int strongPasswordChecker(String s) {
        int sLen = s.length();
        if (sLen < 4) {
            return 6 - sLen;
        }

        int lnum = 1; // need lower
        int unum = 1; // need upper
        int dnum = 1; // need digit

        int rcount = 0;  // count need to replace repeated seq
        int ricount = 0; // count need to add in repeated seq
        int rdcount = 0; // count need to remove from repeated seq
        int sameseq = 0; // count of chars in repeated seq

        for (int i=0; i<sLen; i++) {
            char ch = s.charAt(i);
            if (ch>=‘a‘ && ch<=‘z‘) {
                lnum = 0;
            }
            if (ch>=‘A‘ && ch<=‘Z‘) {
                unum = 0;
            }
            if (ch>=‘0‘ && ch<=‘9‘) {
                dnum = 0;
            }

            // check repeated seq
            if (i == 0) {
                sameseq = 1;
            }
            else if (ch != s.charAt(i-1)) {
                if (sameseq >= 3) {
                    // if shorter length, add char into repeated seq
                    while (sLen + ricount < 6 && sameseq >= 3) {
                        ricount++;
                        sameseq -= 2;
                    }
                    // if longer length, remove char from repeated seq
                    while (sLen - rdcount > 20 && sameseq >= 3) {
                        rdcount++;
                        sameseq --;
                    }
                    // if length matches, replace char in repeated seq
                    rcount += sameseq / 3;
                }
                sameseq = 1;
            }
            else {
                sameseq++;
            }
        }

        // need check repeated seq after loop
        if (sameseq >= 3) {
            // as previous process
            while (sLen + ricount < 6 && sameseq >= 3) {
                ricount++;
                sameseq -= 2;
            }
            while (sLen - rdcount > 20 && sameseq >= 3) {
                rdcount++;
                sameseq --;
            }
            rcount += sameseq / 3;
        }

        int update = lnum + unum + dnum;
        int must = ricount + rcount;
        if (sLen + ricount < 6) {
            must += 6 - sLen - ricount;
        }
        if (sLen < 20) {
            return must > update ? must : update;
        }

        // if longer length, use below process
        if (sLen - rdcount > 20) {
            rdcount += sLen - rdcount - 20;
        }
        return rcount >= update ? rcount + rdcount : update + rdcount;

    }
    
}




public class Solution {
	 2     public int strongPasswordChecker(String s) {
	 3         int res = 0, a = 1, A = 1, d = 1;
	 4         char[] carr = s.toCharArray();
	 5         int[] arr = new int[carr.length];
	 6             
	 7         for (int i = 0; i < arr.length;) {
	 8             if (Character.isLowerCase(carr[i])) a = 0;
	 9             if (Character.isUpperCase(carr[i])) A = 0;
	10             if (Character.isDigit(carr[i])) d = 0;
	11                 
	12             int j = i;
	13             while (i < carr.length && carr[i] == carr[j]) i++;
	14             arr[j] = i - j;
	15         }
	16             
	17         int total_missing = (a + A + d);
	18     
	19         if (arr.length < 6) {
	20             res += total_missing + Math.max(0, 6 - (arr.length + total_missing));//优先insert missing element，满6之后采用replace，
	21                 　　　　　　　　　　　　　　　　　　　　　　　　　　
	22         } else {
	23             int over_len = Math.max(arr.length - 20, 0), left_over = 0;
	24             res += over_len;
	25                 
	26             for (int k = 1; k < 3; k++) {　　//全部重复三次以上的元素delete成3m+2,
	27                 for (int i = 0; i < arr.length && over_len > 0; i++) {
	28                     if (arr[i] < 3 || arr[i] % 3 != (k - 1)) continue;//关注余数为0的情况时，余数为1,2都要skip，关注余数为1的情况时，余数为0，2都要skip
	29                     arr[i] -= Math.min(over_len, k);//余数为1,就减二；余数为0，就减一
	30                     over_len -= k;
	31                 }
	32             }
	33                 
	34             for (int i = 0; i < arr.length; i++) {//分析over_len如果还>0的情况
	35                 if (arr[i] >= 3 && over_len > 0) {
	36                     int need = arr[i] - 2; //目标想要被减除的量，然而over_len不一定还有这么多
	37                     arr[i] -= over_len; //简便写法，其实是减完之后arr[i]==2（over_len>=need，只减need这么多） 和 arr[i]==arr[i]-over_len（over_len<need）两种情况
	38                     over_len -= need;
	39                 }
	40                     
	41                 if (arr[i] >= 3) left_over += arr[i] / 3; //这部分需要用replace来消除剩余重复情况
	42             }
	43                 
	44             res += Math.max(total_missing, left_over); //replace的时候可以同时解决missing element的情况
	45         }
	46             
	47         return res;
	48     }
	49 }
















420.Strong Password Checker

A password is considered strong if below conditions are all met:
It has at least 6 characters and at most 20 characters.
It must contain at least one lowercase letter, at least one uppercase letter, and at least one digit.
It must NOT contain three repeating characters in a row ("...aaa..." is weak, but "...aa...a..." is strong, assuming other conditions are met).
Write a function strongPasswordChecker(s), that takes a string s as input, and return the MINIMUM change required to make s a strong password. If s is already strong, return 0.
Insertion, deletion or replace of any one character are all considered as one change.
Solution:
The general idea is to record some states, and calculate the edit distance at the end. All detail are explained in the comments.
public class Solution {
    public int strongPasswordChecker(String s) {

        if(s.length()<2) return 6-s.length();

        //Initialize the states, including current ending character(end), existence of lowercase letter(lower), uppercase letter(upper), digit(digit) and number of replicates for ending character(end_rep)
        char end = s.charAt(0);
        boolean upper = end>='A'&&end<='Z', lower = end>='a'&&end<='z', digit = end>='0'&&end<='9';

        //Also initialize the number of modification for repeated characters, total number needed for eliminate all consequnce 3 same character by replacement(change), and potential maximun operation of deleting characters(delete). Note delete[0] means maximum number of reduce 1 replacement operation by 1 deletion operation, delete[1] means maximun number of reduce 1 replacement by 2 deletion operation, delete[2] is no use here. 
        int end_rep = 1, change = 0;
        int[] delete = new int[3];

        for(int i = 1;i<s.length();++i){
            if(s.charAt(i)==end) ++end_rep;
            else{
                change+=end_rep/3;
                if(end_rep/3>0) ++delete[end_rep%3];
                //updating the states
                end = s.charAt(i);
                upper = upper||end>='A'&&end<='Z';
                lower = lower||end>='a'&&end<='z';
                digit = digit||end>='0'&&end<='9';
                end_rep = 1;
            }
        }
        change+=end_rep/3;
        if(end_rep/3>0) ++delete[end_rep%3];

        //The number of replcement needed for missing of specific character(lower/upper/digit)
        int check_req = (upper?0:1)+(lower?0:1)+(digit?0:1);

        if(s.length()>20){
            int del = s.length()-20;

            //Reduce the number of replacement operation by deletion
            if(del<=delete[0]) change-=del;
            else if(del-delete[0]<=2*delete[1]) change-=delete[0]+(del-delete[0])/2;
            else change-=delete[0]+delete[1]+(del-delete[0]-2*delete[1])/3;

            return del+Math.max(check_req,change);
        }
        else return Math.max(6-s.length(), Math.max(check_req, change));
    }
}









//Time:  O(n)
//Space: O(1)

class Solution {
public:
 int strongPasswordChecker(string s) {
     int missing_type_cnt = 3;
     missing_type_cnt -= static_cast<int>(any_of(s.begin(), s.end(), [](char c){ return isdigit(c); }));
     missing_type_cnt -= static_cast<int>(any_of(s.begin(), s.end(), [](char c){ return isupper(c); }));
     missing_type_cnt -= static_cast<int>(any_of(s.begin(), s.end(), [](char c){ return islower(c); }));

     int total_change_cnt = 0;
     int one_change_cnt = 0, two_change_cnt = 0, three_change_cnt = 0;
     for (int i = 2; i < s.length();) {
         if (s[i] == s[i - 1] && s[i - 1] == s[i - 2]) {
             int length = 2;
             while (i < s.length() && s[i] == s[i - 1]) {
                 ++length;
                 ++i;
             }
             total_change_cnt += length / 3;
             if (length % 3 == 0) {
                 ++one_change_cnt;
             } else if (length % 3 == 1) {
                 ++two_change_cnt;
             } else {
                 ++three_change_cnt;
             }
         } else {
             ++i;
         }
     }

     if (s.length() < 6) {
         return max(missing_type_cnt, 6 - static_cast<int>(s.length()));
     } else if (s.length() <= 20) {
         return max(missing_type_cnt, total_change_cnt);
     }

     int delete_cnt = s.length() - 20;
     
     total_change_cnt -= min(delete_cnt, one_change_cnt) / 1;
     total_change_cnt -= min(max(delete_cnt - one_change_cnt, 0), two_change_cnt * 2) / 2;
     total_change_cnt -= min(max(delete_cnt - one_change_cnt - 2 * two_change_cnt, 0), three_change_cnt * 3) / 3;
         
     return delete_cnt + max(missing_type_cnt, total_change_cnt);
 }
};







[LeetCode] Strong Password Checker 密码强度检查器


A password is considered strong if below conditions are all met:

It has at least 6 characters and at most 20 characters.
It must contain at least one lowercase letter, at least one uppercase letter, and at least one digit.
It must NOT contain three repeating characters in a row ("...aaa..." is weak, but "...aa...a..." is strong, assuming other conditions are met).
Write a function strongPasswordChecker(s), that takes a string s as input, and return the MINIMUM change required to make s a strong password. If s is already strong, return 0.

Insertion, deletion or replace of any one character are all considered as one change.

 

这道题给了我们一个密码串，让我们判断其需要多少步修改能变成一个强密码串，然后给定了强密码串的条件，长度为6到20之间，必须含有至少一个的小写字母，大写字母，数字，而且不能有连续三个相同的字符，给了我们三种修改方法，任意一个位置加入字符，删除字符，或者是置换任意一个字符，让我们修改最小的次数变成强密码串。这道题定义为Hard真是名副其实，博主光是看大神的帖子都看了好久，这里主要是参考了大神fun4LeetCode的帖子，个人感觉这个算是讲的十分清楚的了，这里就照搬过来吧。首先我们来看非强密码串主要有的三个问题：

1. 长度问题，当长度小于6的时候，我们要通过插入字符来补充长度，当长度超过20时，我们要删除字符。

2. 缺失字符或数字，当我们缺少大写，小写和数字的时候，我们可以通过插入字符或者替换字符的方式来补全。

3. 重复字符，这个也是本题最大的难点，因为插入，删除，或者置换都可以解决重复字符的问题，比如有一个字符串"aaaaa"，我们可以用一次置换，比如换掉中间的字符'a'；或者两次插入字符，在第二个a和第四个a后面分别插入一个非a字符；或者可以删除3个a来解决重复字符的问题。由于题目要求我们要用最少的步骤，那么显而易见置换是最高效的去重复字符的方法。

我们通过举例观察可以知道这三种情况并不是相互独立的，一个操作有时候可以解决多个问题，比如字符串"aaa1a"，我们在第二个a后面增加一个'B'，变为"aaBa1a",这样同时解决了三个问题，即增加了长度，又补充了缺失的大写字母，又去掉了重复，所以我们的目标就是尽可能的找出这种能解决多种问题的操作。由于情况三(重复字符)可以用三种操作来解决，所以我们分别来看能同时解决情况一和情况三，跟同时解决情况二和情况三的操作。对于同时解决情况一和情况二的操作如果原密码串长度小于6会有重叠出现，所以我们要分情况讨论：

当密码串长度小于6时，情况一和情况二的操作步骤可以完全覆盖情况三，这个不难理解，因为这种情况下重复字符个数的范围为[3,5]，如果有三个重复字符，那么增加三个字符的操作可以同时解决重复字符问题("aaa" -> "a1BCaa"；如果有四个重复字符，那么增加二个字符的操作也可以解决重复问题("aaaa" -> "aa1Baa")；如果有五个重复字符，那么增加和置换操作也同时解决重复问题("aaaaa" -> "aa1aaB")。所以我们就专心看最少多少步能同时解决情况一和情况二，首先我们计算出当前密码串需要补几个字符才能到6，补充字符的方法只能用插入字符操作，而插入字符操作也可以解决情况二，所以当情况二的缺失种类个数小于等于diff时，我们不用再增加操作，当diff不能完全覆盖缺失种类个数时，我们还应加上二者的差值。

当密码串长度大于等于6个的时候，这种情况就比较复杂了，由于目前字符串的长度只可能超标不可能不达标，所以我们尽量不要用插入字符操作，因为这有可能会使长度超过限制。由于长度的不确定性，所以可能会有大量的重复字符，那么解决情况三就变得很重要了，由于前面的分析，替换字符是最高效的解法，但是这种方法没法解决情况一，因为长度超标了的话，再怎么替换字符，也不会让长度减少，但是我们也不能无脑删除字符，这样不一定能保证是最少步骤，所以在解决情况三的时候还要综合考虑到情况一，这里用到了一个trick (很膜拜大神能想的出来)，对于重复字符个数k大于等于3的情况，我们并不是直接将其删除到2个，而是先将其删除到最近的(3m+2)个，那么如果k正好被3整除，那么我们直接变为k-1，如果k除以3余1，那么变为k-2。这样做的好处是3m+2个重复字符可以最高效的用替换m个字符来去除重复。那么下面我们来看具体的步骤，首先我们算出超过20个的个数over，我们先把over加到结果res中，因为无论如何这over个删除操作都是要做的。如果没超过，over就是0，用变量left表示解决重复字符最少需要替换的个数，初始化为0。然后我们遍历之前统计字符出现个数的数组，如果某个字符出现个数大于等于3，且此时over大于0，那么我们将个数减为最近的3m+2个，over也对应的减少，注意，一旦over小于等于0，不要再进行删除操作。如果所有重复个数都减为3m+2了，但是over仍大于0，那么我们还要进一步的进行删除操作，这回每次直接删除3m个，直到over小于等于0为止，剩下的如果还有重复个数大于3的字符，我们算出置换字符需要的个数直接加到left中即可，最后我们比较left和missing，取其中较大值加入结果res中即可，参见代码如下：

                                                                                                     

复制代码
class Solution {
public:
    int strongPasswordChecker(string s) {
        int res = 0, n = s.size(), lower = 1, upper = 1, digit = 1;
        vector<int> v(n, 0);
        for (int i = 0; i < n;) {
            if (s[i] >= 'a' && s[i] <= 'z') lower = 0;
            if (s[i] >= 'A' && s[i] <= 'Z') upper = 0;
            if (s[i] >= '0' && s[i] <= '9') digit = 0;
            int j = i;
            while (i < n && s[i] == s[j]) ++i;
            v[j] = i - j;
        }
        int missing = (lower + upper + digit);
        if (n < 6) {
            int diff = 6 - n;
            res += diff + max(0, missing - diff);
        } else {
            int over = max(n - 20, 0), left = 0;
            res += over;
            for (int k = 1; k < 3; ++k) {
                for (int i = 0; i < n && over > 0; ++i) {
                    if (v[i] < 3 || v[i] % 3 != (k - 1)) continue;
                    v[i] -= k;
                    over -=k;
                }
            }
            for (int i = 0; i < n; ++i) {
                if (v[i] >= 3 && over > 0) {
                    int need = v[i] - 2;
                    v[i] -= over;
                    over -= need;
                }
                if (v[i] >= 3) left += v[i] / 3;
            }
            res += max(missing, left);
        }
        return res;
    }
};



A password is considered strong if below conditions are all met:

It has at least 6 characters and at most 20 characters.
It must contain at least one lowercase letter, at least one uppercase letter, and at least one digit.
It must NOT contain three repeating characters in a row ("...aaa..." is weak, but "...aa...a..." is strong, assuming other conditions are met).
Write a function strongPasswordChecker(s), that takes a string s as input, and return the MINIMUM change required to make s a strong password. If s is already strong, return 0.

Insertion, deletion or replace of any one character are all considered as one change.
















