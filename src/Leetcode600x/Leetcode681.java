package Leetcode600x;
/**
 *  LWC 51：681. Next Closest Time
2017年09月24日 13:01:48978人阅读 评论(0) 收藏  举报
 分类： 算法竞赛（131）  
版权声明：本文为博主原创文章，未经博主允许不得转载。 https://blog.csdn.net/u014688145/article/details/78076330

LWC 51：681. Next Closest Time
传送门：681. Next Closest Time

Problem:

Given a time represented in the format “HH:MM”, form the next closest time by reusing the current digits. There is no limit on how many times a digit can be reused.

You may assume the given input string is always valid. For example, “01:34”, “12:09” are all valid. “1:34”, “12:9” are all invalid.

Example 1:

Input: “19:34” 
Output: “19:39” 
Explanation: The next closest time choosing from digits 1, 9, 3, 4, is 19:39, which occurs 5 minutes later. It is not 19:33, because this occurs 23 hours and 59 minutes later.

Example 2:

Input: “23:59” 
Output: “22:22” 
Explanation: The next closest time choosing from digits 2, 3, 5, 9, is 22:22. It may be assumed that the returned time is next day’s time since it is smaller than the input time numerically.

思路： 
暴力遍历，下一个最近的时间点，只需要让时间一点点增大，并且找到第一个所有数字都被reused的情况跳出即可。

代码如下：

    public String nextClosestTime(String time) {
        String[] val = time.split(":");
        Set<Integer> set = new HashSet<>();
        int num1 = Integer.parseInt(val[0]);
        set.add(num1 / 10);
        set.add(num1 % 10);
        int num2 = Integer.parseInt(val[1]);
        set.add(num2 / 10);
        set.add(num2 % 10);

        int hour = num1;
        int minu = num2;

        minu ++;
        if (minu == 60) {
            hour ++;
            minu = 0;
            if (hour == 24) hour = 0;
        }

        while (!contains(hour, minu, set)) {
            minu++;
            if (minu == 60) {
                hour ++;
                minu = 0;
                if (hour == 24) hour = 0;
            }
        }
        String ans = "";
        if (hour >= 0 && hour <= 9) ans = "0" + hour;
        else ans += hour;
        ans += ":";
        if (minu >= 0 && minu <= 9) ans += "0" + minu;
        else ans += minu;
        return ans;
    }

    public boolean contains(int hour, int minu, Set<Integer> set) {
        return set.contains(hour / 10) && set.contains(hour % 10) && set.contains(minu / 10) && set.contains(minu % 10);
    }
1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
21
22
23
24
25
26
27
28
29
30
31
32
33
34
35
36
37
38
39
40
优化一下：

    public String nextClosestTime(String time) {
        String[] val = time.split(":");
        Set<Integer> set = new HashSet<>();
        int hour = add(set, val[0]);
        int minu = add(set, val[1]);

        int[] times = new int[] {hour, minu};
        nxt(times);

        while (!contains(times[0], times[1], set)) {
            nxt(times);
        }
        return valid(times[0]) + ":" + valid(times[1]);
    }

    public void nxt(int[] time) {
        int hour = time[0];
        int minu = time[1];
        minu ++;
        if (minu == 60) {
            hour ++;
            minu = 0;
            if (hour == 24) hour = 0;
        }
        time[0] = hour;
        time[1] = minu;
    }

    public int add(Set<Integer> set, String timeStr) {
        int time = Integer.parseInt(timeStr);
        set.add(time / 10);
        set.add(time % 10);
        return time;
    }

    public String valid(int time) {
        if (time >= 0 && time <= 9) return "0" + time;
        else return time + "";
    }

    public boolean contains(int hour, int minu, Set<Integer> set) {
        return set.contains(hour / 10) && set.contains(hour % 10) && set.contains(minu / 10) && set.contains(minu % 10);
    }
1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
21
22
23
24
25
26
27
28
29
30
31
32
33
34
35
36
37
38
39
40
41
42
43
当然，你也可以直接搜索，因为最多只有4个数字，所以总共有4 * 4 * 4 * 4种情况，在这些情况中找出diff最小的hour和minute即可。

代码如下：

    int diff = 0x3f3f3f3f;
    String result = "";
    int h;
    int m;
    public String nextClosestTime(String time) {
        int[] digit = new int[4];
        int tot = 0;
        String[] val = time.split(":");
        int hour = Integer.parseInt(val[0]);
        int minu = Integer.parseInt(val[1]);
        digit[tot++] = hour / 10;
        digit[tot++] = hour % 10;
        digit[tot++] = minu / 10;
        digit[tot++] = minu % 10;

        h = hour;
        m = minu;

        dfs(digit, 0, new int[4]);

        return result;
    }

    void dfs(int[] digit, int i, int[] ans) {
        if (i == 4) {
            int hour = 10 * ans[0] + ans[1];
            int minu = 10 * ans[2] + ans[3];
            if (hour >= 0 && hour <= 23 && minu >= 0 && minu <= 59) {
                int df = diff(hour, minu);
                if (df < diff) {
                    diff = df;
                    result = valid(hour) + ":" + valid(minu);
                }
            }
        }
        else {
            for (int j = 0; j < 4; ++j) {
                ans[i] = digit[j];
                dfs(digit, i + 1, ans);
                ans[i] = -1;
            }
        }
    }

    int diff(int hour, int minu) {
        int c2o = 60 * 60 - h * 60 - m;
        int n2o = 60 * 60 - hour * 60 - minu;
        return n2o < c2o ? c2o - n2o : c2o - n2o + 3600;
    }

    public String valid(int time) {
        if (time >= 0 && time <= 9) return "0" + time;
        else return time + "";
    }
1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
21
22
23
24
25
26
27
28
29
30
31
32
33
34
35
36
37
38
39
40
41
42
43
44
45
46
47
48
49
50
51
52
53
54
剪枝优化：

    int diff = 0x3f3f3f3f;
    String result = "";
    int h;
    int m;
    public String nextClosestTime(String time) {
        int[] digit = new int[4];
        int tot = 0;
        String[] val = time.split(":");
        int hour = Integer.parseInt(val[0]);
        int minu = Integer.parseInt(val[1]);
        digit[tot++] = hour / 10;
        digit[tot++] = hour % 10;
        digit[tot++] = minu / 10;
        digit[tot++] = minu % 10;

        h = hour;
        m = minu;

        dfs(digit, 0, new int[4]);

        return result;
    }

    void dfs(int[] digit, int i, int[] ans) {
        if (i == 4) {
            int hour = 10 * ans[0] + ans[1];
            int minu = 10 * ans[2] + ans[3];
            int df = diff(hour, minu);
            if (df < diff) {
                diff = df;
                result = valid(hour) + ":" + valid(minu);
            }
        }
        else {
            for (int j = 0; j < 4; ++j) {
                ans[i] = digit[j];
                if (i == 1) {
                    int hour = 10 * ans[0] + ans[1];
                    if (hour >= 0 && hour <= 23) dfs(digit, i + 1, ans);
                }
                else if (i == 3) {
                    int minu = 10 * ans[2] + ans[3];
                    if (minu >= 0 && minu <= 59) dfs(digit, i + 1, ans);
                }
                else {
                    dfs(digit, i + 1, ans);
                }
            }
        }
    }

    int diff(int hour, int minu) {
        int c2o = 60 * 60 - h * 60 - m;
        int n2o = 60 * 60 - hour * 60 - minu;
        return n2o < c2o ? c2o - n2o : c2o - n2o + 3600;
    }

    public String valid(int time) {
        if (time >= 0 && time <= 9) return "0" + time;
        else return time + "";
    }
 * @author tonghe
 *
 */
public class Leetcode681 {

}
