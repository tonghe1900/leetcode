package alite.leetcode.newest;
/**
 * LeetCode 635 - Design Log Storage System

https://leetcode.com/problems/design-log-storage-system
You are given several logs that each log contains a unique id and timestamp. Timestamp is a string that has the following format: Year:Month:Day:Hour:Minute:Second, for example, 2017:01:01:23:59:59. All domains are zero-padded decimal numbers.
Design a log storage system to implement the following functions:
void Put(int id, string timestamp): Given a log's unique id and timestamp, store the log in your storage system.

int[] Retrieve(String start, String end, String granularity): Return the id of logs whose timestamps are within the range from start to end. Start and end all have the same format as timestamp. However, granularity means the time level for consideration. For example, start = "2017:01:01:23:59:59", end = "2017:01:02:23:59:59", granularity = "Day", it means that we need to find the logs within the range from Jan. 1st 2017 to Jan. 2nd 2017.
Example 1:
put(1, "2017:01:01:23:59:59");
put(2, "2017:01:01:22:59:59");
put(3, "2016:01:01:00:00:00");
retrieve("2016:01:01:01:01:01","2017:01:01:23:00:00","Year"); // return [1,2,3], because you need to return all logs within 2016 and 2017.
retrieve("2016:01:01:01:01:01","2017:01:01:23:00:00","Hour"); // return [1,2], because you need to return all logs start from 2016:01:01:01 to 2017:01:01:23, where log 3 is left outside the range.
Note:
There will be at most 300 operations of Put or Retrieve.
Year ranges from [2000,2017]. Hour ranges from [00,23].
Output for Retrieve has no order required.
https://leetcode.com/articles/design-log-storage/
    TreeMap < Long, Integer > map;
    public LogSystem() {
        map = new TreeMap < Long, Integer > ();
    }

    public void put(int id, String timestamp) {
        int[] st = Arrays.stream(timestamp.split(":")).mapToInt(Integer::parseInt).toArray();
        map.put(convert(st), id);
    }
    public long convert(int[] st) {
        st[1] = st[1] - (st[1] == 0 ? 0 : 1);
        st[2] = st[2] - (st[2] == 0 ? 0 : 1);
        return (st[0] - 1999L) * 365 * 24 * 60 * 60 + st[1] * 30 * 24 * 60 * 60 + st[2] * 24 * 60 * 60 + st[3] * 60 * 60 + st[4] * 60 + st[5];
    }
    public List < Integer > retrieve(String s, String e, String gra) {
        ArrayList < Integer > res = new ArrayList();
        long start = granularity(s, gra, false);
        long end = granularity(e, gra, true);
        for (long key: map.tailMap(start).keySet()) {
            if (key >= start && key < end)
                res.add(map.get(key));
        }
        return res;
    }

    public long granularity(String s, String gra, boolean end) {
        HashMap < String, Integer > h = new HashMap();
        h.put("Year", 0);
        h.put("Month", 1);
        h.put("Day", 2);
        h.put("Hour", 3);
        h.put("Minute", 4);
        h.put("Second", 5);
        String[] res = new String[] {"1999", "00", "00", "00", "00", "00"};
        String[] st = s.split(":");
        for (int i = 0; i <= h.get(gra); i++) {
            res[i] = st[i];
        }
        int[] t = Arrays.stream(res).mapToInt(Integer::parseInt).toArray();
        if (end)
            t[h.get(gra)]++;
        return convert(t);
    }


    ArrayList < long[] > list;
    public LogSystem() {
        list = new ArrayList < long[] > ();
    }

    public void put(int id, String timestamp) {
        int[] st = Arrays.stream(timestamp.split(":")).mapToInt(Integer::parseInt).toArray();
        list.add(new long[] {convert(st), id});
    }
    public long convert(int[] st) {
        st[1] = st[1] - (st[1] == 0 ? 0 : 1);
        st[2] = st[2] - (st[2] == 0 ? 0 : 1);
        return (st[0] - 1999L) * 365 * 24 * 60 * 60 + st[1] * 30 * 24 * 60 * 60 + st[2] * 24 * 60 * 60 + st[3] * 60 * 60 + st[4] * 60 + st[5];
    }
    public List < Integer > retrieve(String s, String e, String gra) {
        ArrayList < Integer > res = new ArrayList();
        long start = granularity(s, gra, false);
        long end = granularity(e, gra, true);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i)[0] >= start && list.get(i)[0] < end)
                res.add((int) list.get(i)[1]);
        }
        return res;
    }

    public long granularity(String s, String gra, boolean end) {
        HashMap < String, Integer > h = new HashMap();
        h.put("Year", 0);
        h.put("Month", 1);
        h.put("Day", 2);
        h.put("Hour", 3);
        h.put("Minute", 4);
        h.put("Second", 5);
        String[] res = new String[] {"1999", "00", "00", "00", "00", "00"};
        String[] st = s.split(":");
        for (int i = 0; i <= h.get(gra); i++) {
            res[i] = st[i];
        }
        int[] t = Arrays.stream(res).mapToInt(Integer::parseInt).toArray();
        if (end)
            t[h.get(gra)]++;
        return convert(t);
    }
https://discuss.leetcode.com/topic/94449/concise-java-solution/
    List<String[]> timestamps = new LinkedList<>();
    List<String> units = Arrays.asList("Year", "Month", "Day", "Hour", "Minute", "Second");
    int[] indices = new int[]{4,7,10,13,16,19};
    
    public void put(int id, String timestamp) { timestamps.add(new String[]{Integer.toString(id), timestamp}); }

    public List<Integer> retrieve(String s, String e, String gra) {
        List<Integer> res = new LinkedList<>();
        int idx = indices[units.indexOf(gra)];
        for (String[] timestamp : timestamps) {
            if (timestamp[1].substring(0, idx).compareTo(s.substring(0, idx)) >= 0 &&
                timestamp[1].substring(0, idx).compareTo(e.substring(0, idx)) <= 0) res.add(Integer.parseInt(timestamp[0]));
        }
        return res;
    }
http://bookshadow.com/weblog/2017/07/02/leetcode-design-log-storage-system/
    private HashMap<String, Integer> dmap = new HashMap<>();
    private final String[] gras = "Year:Month:Day:Hour:Minute".split(":");
    private final int[] ends = {4, 7, 10, 13, 16};
    
    private String getKeyByGra(String timestamp, String gra) {
        for (int i = 0; i < gras.length; i++) {
            if (gra.equals(gras[i])) {
                return timestamp.substring(0, ends[i]);
            }
        }
        return timestamp;
    }
    
    public LogSystem() {
    }
    
    public void put(int id, String timestamp) {
        dmap.put(timestamp, id);
    }
    
    public List<Integer> retrieve(String s, String e, String gra) {
        String sk = getKeyByGra(s, gra);
        String ek = getKeyByGra(e, gra);
        List<Integer> ans = new LinkedList<>();
        for (String key : dmap.keySet()) {
            String ck = getKeyByGra(key, gra);
            if (sk.compareTo(ck) <= 0 && ck.compareTo(ek) <= 0) {
                ans.add(dmap.get(key));
            }
        }
        return ans;
    }
 * @author het
 *
 */
public class LeetCode635 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
