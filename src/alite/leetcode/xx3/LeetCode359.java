package alite.leetcode.xx3;
///**
// * LeetCode - 359 Logger Rate Limiter
//
//http://www.cnblogs.com/grandyang/p/5592635.html
//Design a logger system that receive stream of messages along with its timestamps, each message should be printed 
//if and only if it is not printed in the last 10 seconds.
//
//Given a message and a timestamp (in seconds granularity), return true if the message should 
//be printed in the given timestamp, otherwise returns false.
//
//It is possible that several messages arrive roughly at the same time.
//
//Example:
//
//Logger logger = new Logger();
//
//// logging string "foo" at timestamp 1
//logger.shouldPrintMessage(1, "foo"); returns true; 
//
//// logging string "bar" at timestamp 2
//logger.shouldPrintMessage(2,"bar"); returns true;
//
//// logging string "foo" at timestamp 3
//logger.shouldPrintMessage(3,"foo"); returns false;
//
//// logging string "bar" at timestamp 8
//logger.shouldPrintMessage(8,"bar"); returns false;
//
//// logging string "foo" at timestamp 10
//logger.shouldPrintMessage(10,"foo"); returns false;
//
//// logging string "foo" at timestamp 11
//logger.shouldPrintMessage(11,"foo"); returns true;
//
//http://dartmooryao.blogspot.com/2016/06/leetcode-359-logger-rate-limiter.html
//Use a map to store the message and timestamp.
//When the "shouldPrintMsg" is called, we check whether the map don't have the string or the previous timestamp is 10 seconds before.
//If yes, we update the map with the new record and return true.
//Otherwise, do nothing and return false
//    Map<String, Integer> msg;
//    public Logger() {
//        msg = new HashMap<>();
//    }   
//    /** Returns true if the message should be printed in the given timestamp, otherwise returns false.
//        If this method returns false, the message will not be printed.
//        The timestamp is in seconds granularity. */
//    public boolean shouldPrintMessage(int timestamp, String message) {
//        if(!msg.containsKey(message) || timestamp-msg.get(message)>=10){
//            msg.put(message, timestamp);
//            return true;
//        }else{
//            return false;
//        }
//    }
//https://discuss.leetcode.com/topic/48359/short-c-java-python-bit-different/
//https://discuss.leetcode.com/topic/52146/java-no-brainer-using-hashmap
//    private Map<String, Integer> ok = new HashMap<>();
//
//    public boolean shouldPrintMessage(int timestamp, String message) {
//        if (timestamp < ok.getOrDefault(message, 0))
//            return false;
//        ok.put(message, timestamp + 10);
//        return true;
//    }
//
//这道题让我们设计一个记录系统每次接受信息并保存时间戳，然后让我们打印出该消息，前提是最近10秒内没有打印出这个消息。这不是一道难题，我们可以用哈希表来做，建立消息和时间戳之间的映射，如果某个消息不再哈希表表，我们建立其和时间戳的映射，并返回true。如果应经在哈希表里了，我们看当前时间戳是否比哈希表中保存的时间戳大10，如果是，更新哈希表，并返回true，反之返回false.
//    bool shouldPrintMessage(int timestamp, string message) {
//        if (!m.count(message)) {
//            m[message] = timestamp;
//            return true;
//        } 
//        if (timestamp - m[message] >= 10) {
//            m[message] = timestamp;
//            return true;
//        }
//        return false;
//    }
//    unordered_map<string, int> m;
//https://leetcode.com/discuss/108649/a-java-solution
//public boolean shouldPrintMessage(int timestamp, String message) {
////update timestamp of the message if the message is coming in for the first time,or the last coming time is earlier than 10 seconds from now
//    if(!map.containsKey(message)||timestamp-map.get(message)>=10){
//        map.put(message,timestamp);
//        return true;
//    }
//    return false;
//}
//https://leetcode.com/discuss/108703/short-c-java-python-bit-different
//    private Map<String, Integer> ok = new HashMap<>();
//
//    public boolean shouldPrintMessage(int timestamp, String message) {
//        if (timestamp < ok.getOrDefault(message, 0))
//            return false;
//        ok.put(message, timestamp + 10);
//        return true;
//    }
//
//https://discuss.leetcode.com/topic/48399/java-concurrenthashmap-solution/
//    ConcurrentHashMap<String, Integer> lastPrintTime;
//
//    /** Initialize your data structure here. */
//    public Logger() {
//        lastPrintTime = new ConcurrentHashMap<String, Integer>();
//    }
//    
//    /** Returns true if the message should be printed in the given timestamp, otherwise returns false. The timestamp is in seconds granularity. */
//    public boolean shouldPrintMessage(int timestamp, String message) {
//  Integer last = lastPrintTime.get(message);
//
//  return last == null && lastPrintTime.putIfAbsent(message, timestamp) == null
//    || last != null && timestamp - last >= 10 && lastPrintTime.replace(message, last, timestamp);
//
//    }
//https://discuss.leetcode.com/topic/50776/java-with-a-linkedhashmap-and-using-removeeldestentry
//    public Map<String, Integer> map;
//    int lastSecond = 0;
//    
//    /** Initialize your data structure here. */
//    public Logger() {
//        map = new java.util.LinkedHashMap<String, Integer>(100, 0.6f, true) {
//            protected boolean removeEldestEntry(Map.Entry<String, Integer> eldest) {
//                return lastSecond - eldest.getValue() > 10;
//            }
//        };
//    }
//    
//    /** Returns true if the message should be printed in the given timestamp, otherwise returns false.
//        If this method returns false, the message will not be printed.
//        The timestamp is in seconds granularity. */
//    public boolean shouldPrintMessage(int timestamp, String message) {
//        lastSecond = timestamp;
//        if(!map.containsKey(message)||timestamp - map.get(message) >= 10){
//            map.put(message,timestamp);
//            return true;
//        }
//        return false;
//    }
//
//https://discuss.leetcode.com/topic/48615/a-solution-that-only-keeps-part-of-the-messages/
//class Log {
//    int timestamp;
//    String message;
//    public Log(int aTimestamp, String aMessage) {
//        timestamp = aTimestamp;
//        message = aMessage;
//    }
//}
//
//public class Logger {
//    PriorityQueue<Log> recentLogs;
//    Set<String> recentMessages;   
//    
//    /** Initialize your data structure here. */
//    public Logger() {
//        recentLogs = new PriorityQueue<Log>(10, new Comparator<Log>() {
//            public int compare(Log l1, Log l2) {
//                return l1.timestamp - l2.timestamp;
//            }
//        });
//        
//        recentMessages = new HashSet<String>();
//    }
//    
//    /** Returns true if the message should be printed in the given timestamp, otherwise returns false.
//        If this method returns false, the message will not be printed.
//        The timestamp is in seconds granularity. */
//    public boolean shouldPrintMessage(int timestamp, String message) {
//        while (recentLogs.size() > 0)   {
//            Log log = recentLogs.peek();
//            // discard the logs older than 10 minutes
//            if (timestamp - log.timestamp >= 10) {
//                recentLogs.poll();
//                recentMessages.remove(log.message);
//            } else 
//             break;
//        }
//        boolean res = !recentMessages.contains(message);
//        if (res) {
//            recentLogs.add(new Log(timestamp, message));
//            recentMessages.add(message);
//        }
//        return res;
//    }
//}
// * @author het
// *
// */
public class LeetCode359 {
	 //Map<String, Integer> msg;
//   public Logger() {
//       msg = new HashMap<>();
//   }   
//   /** Returns true if the message should be printed in the given timestamp, otherwise returns false.
//       If this method returns false, the message will not be printed.
//       The timestamp is in seconds granularity. */
//   public boolean shouldPrintMessage(int timestamp, String message) {
//       if(!msg.containsKey(message) || timestamp-msg.get(message)>=10){
//           msg.put(message, timestamp);
//           return true;
//       }else{
//           return false;
//       }
//   }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
