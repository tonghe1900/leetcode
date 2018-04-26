package alite.leetcode.xx3.extra;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

///**
// * LeetCode 355 - Design Twitter
//
//https://leetcode.com/problems/design-twitter/
//Design a simplified version of Twitter where users can post tweets, follow/unfollow another user and 
//is able to see the 10 most recent tweets in the user's news feed. Your design should support the following methods:
//postTweet(userId, tweetId): Compose a new tweet.
//getNewsFeed(userId): Retrieve the 10 most recent tweet ids in the user's news feed. 
//Each item in the news feed must be posted by users who the user followed or by the user herself.
// Tweets must be ordered from most recent to least recent.
//follow(followerId, followeeId): Follower follows a followee.
//unfollow(followerId, followeeId): Follower unfollows a followee.
//Example:
//Twitter twitter = new Twitter();
//
//// User 1 posts a new tweet (id = 5).
//twitter.postTweet(1, 5);
//
//// User 1's news feed should return a list with 1 tweet id -> [5].
//twitter.getNewsFeed(1);
//
//// User 1 follows user 2.
//twitter.follow(1, 2);
//
//// User 2 posts a new tweet (id = 6).
//twitter.postTweet(2, 6);
//
//// User 1's news feed should return a list with 2 tweet ids -> [6, 5].
//// Tweet id 6 should precede tweet id 5 because it is posted after tweet id 5.
//twitter.getNewsFeed(1);
//
//// User 1 unfollows user 2.
//twitter.unfollow(1, 2);
//
//// User 1's news feed should return a list with 1 tweet id -> [5],
//// since user 1 is no longer following user 2.
//twitter.getNewsFeed(1);
//http://bookshadow.com/weblog/2016/06/11/leetcode-design-twitter/
//Map + Set + PriorityQueue
//系统应当维护下列信息：
//1). 一个全局的推文计数器：postCount 用户发推文时，计数器+1
//
//2). 推文Id -> 推文计数器的映射：tweetIdMap 用来记录推文的次序
//
//3). 推文Id -> 用户Id的映射：tweetOwnerMap 用来记录推文的发送者是谁
//
//4). 用户Id -> 关注对象集合的映射： followeeMap 用来记录用户的关注对象（关注/取消关注）
//方法的实现：
//1). 关注 follow / 取消关注 unfollow：
//只需要维护followeeMap中对应的关注对象集合followeeSet即可
//2). 发送推文 postTweet：
//更新推文计数器postCount，维护tweetIdMap；
//
//向用户的推文发送列表中新增一条记录
//3). 获取推文推送 getNewsFeed：
//这里需要使用优先队列PriorityQueue，记为pq
//
//遍历用户的关注对象列表，将每一位关注对象的最新一条推文添加至pq中。
//
//然后从pq中弹出最近的一条推文，记为topTweetId；
//
//通过tweetOwnerMap找到这条推文的发送者userId，然后将该发送者的下一条比较新的推文添加至pq。
//
//直到弹出10条最新的推文，或者pq为空为止。
//    private int postCount;    
//    private Map<Integer, Integer> tweetCountMap;
//    private Map<Integer, List<Integer>> tweetIdMap;
//    private Map<Integer, Integer> tweetOwnerMap;
//    private Map<Integer, Set<Integer>> followeeMap;
//    
//    /** Initialize your data structure here. */
//    public Twitter() {
//        tweetCountMap = new HashMap<Integer, Integer>();
//        tweetIdMap = new HashMap<Integer, List<Integer>>();
//        tweetOwnerMap = new HashMap<Integer, Integer>();
//        followeeMap = new HashMap<Integer, Set<Integer>>();
//    }
//    
//    /** Compose a new tweet. */
//    public void postTweet(int userId, int tweetId) {
//        tweetCountMap.put(tweetId, ++postCount);
//        tweetOwnerMap.put(tweetId, userId);
//        getTweetIdList(userId).add(tweetId);
//    }
//    
//    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
//    public List<Integer> getNewsFeed(int userId) {
//        List<Integer> result = new ArrayList<Integer>();
//        Set<Integer> followeeSet = getFolloweeSet(userId);
//        PriorityQueue<Integer> pq = new PriorityQueue<Integer>(new Comparator<Integer>(){
//            @Override
//            public int compare(Integer a, Integer b) {
//                return tweetCountMap.get(b) - tweetCountMap.get(a);
//            }
//        });
//        Map<Integer, Integer> idxMap = new HashMap<Integer, Integer>();
//        for (Integer followeeId : followeeSet) {
//            List<Integer> tweetIdList = getTweetIdList(followeeId);
//            int idx = tweetIdList.size() - 1;
//            if (idx >= 0) {
//                idxMap.put(followeeId, idx - 1);
//                pq.add(tweetIdList.get(idx));
//            }
//        }
//        while (result.size() < 10 && !pq.isEmpty()) {
//            Integer topTweetId = pq.poll();
//            result.add(topTweetId);
//            Integer ownerId = tweetOwnerMap.get(topTweetId);
//            int idx = idxMap.get(ownerId);
//            if (idx >= 0) {
//                List<Integer> tweetIdList = getTweetIdList(ownerId);
//                pq.add(tweetIdList.get(idx));
//                idxMap.put(ownerId, idx - 1);
//            }
//        }
//        return result;
//    }
//    
//    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
//    public void follow(int followerId, int followeeId) {
//        getFolloweeSet(followerId).add(followeeId);
//    }
//    
//    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
//    public void unfollow(int followerId, int followeeId) {
//        if (followerId != followeeId) {
//         getFolloweeSet(followerId).remove(followeeId);
//        }
//    }
//
//    /** Get a non-empty followee set of an user. */
//    private Set<Integer> getFolloweeSet(int userId) {
//        Set<Integer> followeeSet = followeeMap.get(userId);
//        if (followeeSet == null) {
//            followeeSet = new HashSet<Integer>();
//            followeeSet.add(userId);
//            followeeMap.put(userId, followeeSet);
//        }
//        return followeeSet;
//    }
//    
//    /** Get a non-empty tweet id list of an user. */
//    private List<Integer> getTweetIdList(int userId) {
//        List<Integer> tweetIdList = tweetIdMap.get(userId);
//        if (tweetIdList == null) {
//            tweetIdList = new ArrayList<Integer>();
//            tweetIdMap.put(userId, tweetIdList);
//        }
//        return tweetIdList;
//    }
//https://leetcode.com/discuss/107638/java-solution-using-hashmap-%26-hashset-%26-priorityqueue
//private Map<Integer,Set<Integer>> users = new HashMap<>();
//private Map<Integer,Map<Integer,Integer>> tweets = new HashMap<>();
//private int timeStamp = 0;
//
//public Twitter() {
//
//}
//
//public void postTweet(int userId, int tweetId) {
//    if(users.get(userId) == null){
//        users.put(userId,new HashSet<>());
//        tweets.put(userId,new HashMap<>());
//    }
//    tweets.get(userId).put(timeStamp++,tweetId);
//}
//
//public List<Integer> getNewsFeed(int userId) {
//    List<Integer> res = new ArrayList<>();
//    if(users.get(userId) == null) return res;
//    Queue<Map.Entry<Integer,Integer>> queue = new PriorityQueue<>((e1,e2) -> e2.getKey() - e1.getKey());
//    for(Map.Entry<Integer,Integer> e : tweets.get(userId).entrySet()) queue.offer(e);
//    for(Integer user : users.get(userId)){
//        for(Map.Entry<Integer,Integer> e : tweets.get(user).entrySet()){
//            queue.offer(e);
//        }
//    }
//    for(int i = 0; i < 10 && !queue.isEmpty(); i++){
//        res.add(queue.poll().getValue());
//    }
//    return res;
//}
//
//public void follow(int followerId, int followeeId) {
//    if(followerId == followeeId) return;
//    if(users.get(followerId) == null){
//        users.put(followerId,new HashSet<>());
//        tweets.put(followerId,new HashMap<>());
//    }
//    if(users.get(followeeId) == null){
//        users.put(followeeId,new HashSet<>());
//        tweets.put(followeeId,new HashMap<>());
//    }
//    users.get(followerId).add(followeeId);
//}
//
//public void unfollow(int followerId, int followeeId) {
//    if(followerId == followeeId) return;
//    if(users.get(followerId) == null || users.get(followeeId) == null) return;
//    users.get(followerId).remove(followeeId);
//}
//https://leetcode.com/discuss/107697/java-solution-using-hashmap-and-priorityqueue
//private static class Tweet {
//    int timestamp;
//    int tweetId;
//
//    public Tweet(int tweetId, int timestamp) {
//        this.tweetId = tweetId;
//        this.timestamp = timestamp;
//    }
//}
//
//private Map<Integer, Set<Integer>> followMap = new HashMap<Integer, Set<Integer>>();
//private Map<Integer, List<Tweet>> tweetMap = new HashMap<Integer, List<Tweet>>();
//
//private AtomicInteger timestamp;
//
///** Initialize your data structure here. */
//public Twitter() {
//    timestamp = new AtomicInteger(0);
//}
//
///** Compose a new tweet. */
//public void postTweet(int userId, int tweetId) {
//    Tweet newTweet = new Tweet(tweetId, timestamp.getAndIncrement());
//
//    if (!tweetMap.containsKey(userId)) {
//        tweetMap.put(userId, new ArrayList<Tweet>());  //Assuming no deletion for now?
//    }
//
//    tweetMap.get(userId).add(newTweet);
//}
//
///**
// * Retrieve the 10 most recent tweet ids in the user's news feed. Each item
// * in the news feed must be posted by users who the user followed or by the
// * user herself. Tweets must be ordered from most recent to least recent.
// */
//public List<Integer> getNewsFeed(int userId) {
//    List<Integer> result = new ArrayList<Integer>(10);
//
//    PriorityQueue<int[]> pq = new PriorityQueue<int[]>(new Comparator<int[]>() {
//        public int compare(int[] it1, int[] it2) {
//            return tweetMap.get(it2[0]).get(it2[1]).timestamp - tweetMap.get(it1[0]).get(it1[1]).timestamp;
//        }
//    });
//
//    Set<Integer> followeeSet = new HashSet<Integer>();
//    followeeSet.add(userId);
//    if (followMap.containsKey(userId)) {
//        followeeSet.addAll(followMap.get(userId));
//    }
//
//    for (Integer followee : followeeSet) {
//        if (tweetMap.containsKey(followee)) {
//            List<Tweet> tweetList = tweetMap.get(followee);
//            if (tweetList.size() > 0) {
//                pq.add(new int[] { followee, tweetList.size() - 1 });
//            }
//        }
//    }
//
//    while (result.size() < 10 && pq.size() > 0) {
//        int[] it = pq.poll();
//
//        result.add(tweetMap.get(it[0]).get(it[1]).tweetId);
//        it[1]--;
//        if (it[1] >= 0) {
//            pq.add(it);
//        }
//    }
//
//    return result;
//}
//
///**
// * Follower follows a followee. If the operation is invalid, it should be a
// * no-op.
// */
//public void follow(int followerId, int followeeId) {
//    Set<Integer> followSet = followMap.get(followerId);
//    if (followSet == null) {
//        followSet = new HashSet<Integer>();
//        followMap.put(followerId, followSet);
//    }
//
//    followSet.add(followeeId);
//}
//
///**
// * Follower unfollows a followee. If the operation is invalid, it should be
// * a no-op.
// */
//public void unfollow(int followerId, int followeeId) {
//    Set<Integer> followSet = followMap.get(followerId);
//    if (followSet == null) {
//        followSet = new HashSet<Integer>();
//        followMap.put(followerId, followSet);
//    }
//
//    followSet.remove(followeeId);
//}
//http://www.w2bc.com/article/149069
//用户之间的follow关系因为userId是唯一的，所以可以用HashMap保存user之间的follow关系。
//如何设计用户的feed流呢？看到本题的tag有heap，想了一下没想到如何用heap实现feed， 那就暴力点，将所有用户发布的feed 用一个list保存。 当要获取某用户的feeds时，按时间顺序从list后往前，如果一个feed的userid属于该用户或其follower则 将该feed存入结果集。
//题目不难，甚至有点简单。。follow和unfollow时间复杂度为O(1)，空间复杂度为O(n)，getNewsFeed时间复杂度为O(n)，空间复杂度为O(1)。
//    HashMap<Integer, Set<Integer>> maps = new HashMap<Integer, Set<Integer>>();
//    List<Feed> feeds = new ArrayList<Feed>();
//
//    class Feed {
//        int userId, tweetId;
//
//        public Feed(int userId, int tweetId) {
//            this.userId = userId;
//            this.tweetId = tweetId;
//        }
//
//        public String toString(){
//            return tweetId+"";
//        }
//    }
//
//    /** Initialize your data structure here. */
//    public Twitter() {
//
//    }
//
//    /** Compose a new tweet. */
//    public void postTweet(int userId, int tweetId) {
//        Feed f = new Feed(userId, tweetId);
//        feeds.add(f);
//    }
//
//    /**
//     * Retrieve the 10 most recent tweet ids in the user's news feed. Each item
//     * in the news feed must be posted by users who the user followed or by the
//     * user herself. Tweets must be ordered from most recent to least recent.
//     */
//    public List<Integer> getNewsFeed(int userId) {
//        List<Integer> res = new ArrayList<Integer>();
//        Set<Integer> users = maps.get(userId);
//        if(users==null)
//            users=  new HashSet<Integer>();
//        users.add(userId);
//        for (int i=feeds.size()-1;i>=0;i--) {
//            Feed f = feeds.get(i);
//            if (res.size()<10&&users.contains(f.userId)) {
//                res.add(f.tweetId);
//            }
//            if(res.size()>=10)
//                break;
//        }
//        return res;
//    }
//
//    /**
//     * Follower follows a followee. If the operation is invalid, it should be a
//     * no-op.
//     */
//    public void follow(int followerId, int followeeId) {
//        Set<Integer> sets;
//        if (maps.containsKey(followerId)) {
//            sets = maps.get(followerId);
//        } else {
//            sets = new HashSet<Integer>();
//        }
//        sets.add(followeeId);
//        maps.put(followerId, sets);
//    }
//
//    /**
//     * Follower unfollows a followee. If the operation is invalid, it should be
//     * a no-op.
//     */
//    public void unfollow(int followerId, int followeeId) {
//        Set<Integer> sets = maps.get(followerId);
//        if(sets!=null&&sets.contains(followeeId)){
//            sets.remove(followeeId);
//            maps.put(followerId, sets);
//        }
//    }
//https://www.hrwhisper.me/leetcode-design-twitter/
//题意：要求设计一个数据结构，使其能满足twitter的4种基本操作，发推、获得关注用户和自身最新10条推文、关注用户和取消关注。
//思路：水题。和上一题一样，不明白为啥为hard。  可能难点就在于，直接写出无bug 的code吧。
//需要那些非法的情况需要进行考虑吧。比如：
//follow操作 followerId, followeeId 相等
//unfollow操作followerId 不存在，或者followerId 压根就没关注 followeeId
//求10个最近的推文可以用堆（当然这里我没有）
//
//Python
//
//1
//2
//3
//4
//5
//6
//7
//8
//9
//10
//11
//12
//13
//14
//15
//16
//17
//18
//19
//20
//21
//22
//23
//24
//25
//26
//27
//28
//29
//30
//31
//32
//33
//34
//35
//36
//37
//38
//39
//40
//41
//42
//43
//44
//45
//46
//47
//48
//49
//50
//51
//52
//53
//54
//55
//56
//57
//58
//59
//60
//61
//62
//
//
//class Twitter(object):
//
//
//    def __init__(self):
//
//
//        """
//
//
//        Initialize your data structure here.
//
//
//        """
//
//
//        self.tweets_cnt = 0
//
//
//        self.tweets = collections.defaultdict(list)
//
//
//        self.follower_ship = collections.defaultdict(set)
//
//
//
//
//    def postTweet(self, userId, tweetId):
//
//
//        """
//
//
//        Compose a new tweet.
//
//
//        :type userId: int
//
//
//        :type tweetId: int
//
//
//        :rtype: void
//
//
//        """
//
//
//        self.tweets[userId].append([tweetId, self.tweets_cnt])
//
//
//        self.tweets_cnt += 1
//
//
//
//
//    def getNewsFeed(self, userId):
//
//
//        """
//
//
//        Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent.
//
//
//        :type userId: int
//
//
//        :rtype: List[int]
//
//
//        """
//
//
//        recent_tweets = []
//
//
//        user_list = list(self.follower_ship[userId]) + [userId]
//
//
//        userId_tweet_index = [[userId, len(self.tweets[userId]) - 1] for userId in user_list if userId in self.tweets]
//
//
//
//
//        for _ in xrange(10):
//
//
//            max_index = max_tweet_id = max_user_id = -1
//
//
//            for i, (user_id, tweet_index) in enumerate(userId_tweet_index):
//
//
//                if tweet_index >= 0:
//
//
//                    tweet_info = self.tweets[user_id][tweet_index]
//
//
//                    if tweet_info[1] > max_tweet_id:
//
//
//                        max_index, max_tweet_id, max_user_id = i, tweet_info[1], user_id
//
//
//
//
//            if max_index < 0: break
//
//
//            recent_tweets.append(self.tweets[max_user_id][userId_tweet_index[max_index][1]][0])
//
//
//            userId_tweet_index[max_index][1] -= 1
//
//
//
//
//        return recent_tweets
//
//
//
//
//    def follow(self, followerId, followeeId):
//
//
//        """
//
//
//        Follower follows a followee. If the operation is invalid, it should be a no-op.
//
//
//        :type followerId: int
//
//
//        :type followeeId: int
//
//
//        :rtype: void
//
//
//        """
//
//
//        if followerId != followeeId:
//
//
//            self.follower_ship[followerId].add(followeeId)
//
//
//
//
//    def unfollow(self, followerId, followeeId):
//
//
//        """
//
//
//        Follower unfollows a followee. If the operation is invalid, it should be a no-op.
//
//
//        :type followerId: int
//
//
//        :type followeeId: int
//
//
//        :rtype: void
//
//
//        """
//
//
//        if followerId in self.follower_ship and followeeId in self.follower_ship[followerId]:
//
//
//            self.follower_ship[followerId].remove(followeeId)
// * @author het
// *
// */
public class LeetCode355 {
        	private Map<Integer,Set<Integer>> users = new HashMap<>();
        	private Map<Integer,Map<Integer,Integer>> tweets = new HashMap<>();
        	private int timeStamp = 0;

        	public LeetCode355() {

        	}

        	public void postTweet(int userId, int tweetId) {
        	    if(users.get(userId) == null){
        	        users.put(userId,new HashSet<>());
        	        tweets.put(userId,new HashMap<>());
        	    }
        	    tweets.get(userId).put(timeStamp++,tweetId);
        	}

        	public List<Integer> getNewsFeed(int userId) {
        	    List<Integer> res = new ArrayList<>();
        	    if(users.get(userId) == null) return res;
        	    Queue<Map.Entry<Integer,Integer>> queue = new PriorityQueue<>((e1,e2) -> e2.getKey() - e1.getKey());
        	    for(Map.Entry<Integer,Integer> e : tweets.get(userId).entrySet()) queue.offer(e);
        	    for(Integer user : users.get(userId)){
        	        for(Map.Entry<Integer,Integer> e : tweets.get(user).entrySet()){
        	            queue.offer(e);
        	        }
        	    }
        	    for(int i = 0; i < 10 && !queue.isEmpty(); i++){
        	        res.add(queue.poll().getValue());
        	    }
        	    return res;
        	}

        	public void follow(int followerId, int followeeId) {
        	    if(followerId == followeeId) return;
        	    if(users.get(followerId) == null){
        	        users.put(followerId,new HashSet<>());
        	        tweets.put(followerId,new HashMap<>());
        	    }
        	    if(users.get(followeeId) == null){
        	        users.put(followeeId,new HashSet<>());
        	        tweets.put(followeeId,new HashMap<>());
        	    }
        	    users.get(followerId).add(followeeId);
        	}

        	public void unfollow(int followerId, int followeeId) {
        	    if(followerId == followeeId) return;
        	    if(users.get(followerId) == null || users.get(followeeId) == null) return;
        	    users.get(followerId).remove(followeeId);
        	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
