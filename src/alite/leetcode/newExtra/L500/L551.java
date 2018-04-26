package alite.leetcode.newExtra.L500;
/**
 * LeetCode 551 - Student Attendance Record I

https://leetcode.com/problems/student-attendance-record-i/
You are given a string representing an attendance record for a student. The record only contains the following three characters:
'A' : Absent.
'L' : Late.
'P' : Present.
A student could be rewarded if his attendance record doesn't contain more than one 'A' (absent) or more than two continuous 'L' (late).
You need to return whether the student could be rewarded according to his attendance record.
Example 1:
Input: "PPALLP"
Output: True
Example 2:
Input: "PPALLL"
Output: False
https://discuss.leetcode.com/topic/86581/java-o-n-solution-accepted
public boolean checkRecord(String s) {
        int countA=0;
        int continuosL = 0;
        int charA = 'A';
        int charL ='L';
        for(int i=0;i<s.length();i++){
            if(s.charAt(i) == charA){
                countA++;
                continuosL = 0;
            }
            else if(s.charAt(i) == charL){
                continuosL++;
            }
            else{
                continuosL = 0;
            }
            if(countA >1 || continuosL > 2 ){
                return false;
            }
        }
        return true;

    }
https://discuss.leetcode.com/topic/86559/java-simple-without-regex-3-lines
    public boolean checkRecord(String s) {
        if(s.indexOf("A") != s.lastIndexOf("A") || s.contains("LLL"))
            return false;
        return true;
    }

https://discuss.leetcode.com/topic/86466/java-1-liner
public boolean checkRecord(String s) {
    return !s.matches(".*LLL.*|.*A.*A.*");
}
 * @author het
 *
 */
public class L551 {
	public boolean checkRecord(String s) {
        int countA=0;
        int continuosL = 0;
        int charA = 'A';
        int charL ='L';
        for(int i=0;i<s.length();i++){
            if(s.charAt(i) == charA){
                countA++;
                continuosL = 0;
            }
            else if(s.charAt(i) == charL){
                continuosL++;
            }
            else{
                continuosL = 0;
            }
            if(countA >1 || continuosL > 2 ){
                return false;
            }
        }
        return true;

    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
