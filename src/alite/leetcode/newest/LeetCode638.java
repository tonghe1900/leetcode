package alite.leetcode.newest;
/**
 * LeetCode 638 - Shopping Offers

https://leetcode.com/problems/shopping-offers
In LeetCode Store, there are some kinds of items to sell. Each item has a price.
However, there are some special offers, and a special offer consists of one or more different kinds of items with a sale price.
You are given the each item's price, a set of special offers, and the number we need to buy for each item. The job is to output the lowest price you have to pay for exactly certain items as given, where you could make optimal use of the special offers.
Each special offer is represented in the form of an array, the last number represents the price you need to pay for this special offer, other numbers represents how many specific items you could get if you buy this offer.
You could use any of special offers as many times as you want.
Example 1:
Input: [2,5], [[3,0,5],[1,2,10]], [3,2] Output: 14 Explanation: There are two kinds of items, A and B. Their prices are $2 and $5 respectively. In special offer 1, you can pay $5 for 3A and 0B In special offer 2, you can pay $10 for 1A and 2B. You need to buy 3A and 2B, so you may pay $10 for 1A and 2B (special offer #2), and $4 for 2A.

https://leetcode.com/articles/shopping-offers/
https://discuss.leetcode.com/topic/95200/simple-java-recursive-solution/
The basic idea is to pick each offer, and subtract the needs. And then compute the price without the offer.
Pick whichever is minimum.
    public int shoppingOffers(List<Integer> price, List<List<Integer>> special, List<Integer> needs) {
        int result = Integer.MAX_VALUE;
        //apply each offer to the needs, and recurse
        for(int i = 0; i < special.size(); i++) {
            List<Integer> offer = special.get(i);
            boolean invalidOffer = false;
            int offerCount = Integer.MAX_VALUE; // number of times offer can be applied
            for(int j = 0; j < needs.size(); j++) { // pre-compute number of times offer can be called
                int remain = needs.get(j) - offer.get(j);
                if(!invalidOffer && remain < 0) invalidOffer = true; // if offer has more items than needs
                if(offer.get(j) > 0)
                offerCount = Math.min(offerCount, needs.get(j)/offer.get(j));
            }
            for(int j = 0; j < needs.size(); j++) { // subtract offer items from needs
                int remain = needs.get(j) - offer.get(j) * offerCount;
                needs.set(j, remain);
            }
            if(!invalidOffer) { //if valid offer, add offer price and recurse remaining needs
                result = Math.min(result, shoppingOffers(price, special, needs) + (offerCount * offer.get(needs.size())));
            }

            for(int j = 0; j < needs.size(); j++) { // reset the needs
                int remain = needs.get(j) + offer.get(j) * offerCount;
                needs.set(j, remain);
            }
        }

        // choose b/w offer and non offer
        int nonOfferPrice = 0;
        for(int i = 0; i < needs.size(); i++) {
            nonOfferPrice += price.get(i) * needs.get(i);
        }
        return Math.min(result, nonOfferPrice);
    }
https://leetcode.com/articles/shopping-offers/
    public int shoppingOffers(List < Integer > price, List < List < Integer >> special, List < Integer > needs) {
        return shopping(price, special, needs, 0);
    }
    public int shopping(List < Integer > price, List < List < Integer >> special, List < Integer > needs, int i) {
        if (i == special.size())
            return dot(needs, price);
        ArrayList < Integer > clone = new ArrayList < > (needs);
        int j = 0;
        for (j = 0; j < special.get(i).size() - 1; j++) {
            int diff = clone.get(j) - special.get(i).get(j);
            if (diff < 0)
                break;
            clone.set(j, diff);
        }
        if (j == special.get(i).size() - 1)
            return Math.min(special.get(i).get(j) + shopping(price, special, clone, i), shopping(price, special, needs, i + 1));
        else
            return shopping(price, special, needs, i + 1);
    }
    public int dot(List < Integer > a, List < Integer > b) {
        int sum = 0;
        for (int i = 0; i < a.size(); i++) {
            sum += a.get(i) * b.get(i);
        }
        return sum;
    }
X. DFS+Cache
https://discuss.leetcode.com/topic/95237/java-dfs-dp
List's hashCode method is defined here.
As the stackoverflow discussion points out, it generally is a bad idea to use Lists as map keys, since the hashCode will change once you mutate the List. But in this solution, Lists will have the same contents when Map::get and Map::put are called.
The solution actually relies on the fact that two Lists with same contents generate the same hashCode. It is very easy to test it. Try setting your "needs" list as all zeros, and you know "allZero" and "needs" are different Objects, but the first call of dp.containsKey(needs) still returns true because "allZero" and "needs" have the same contents.
You can create your own hashcode function in this case since there are no more than 6 of each item in this question, so slightly modify your method:
    public int shoppingOffers(List<Integer> price, List<List<Integer>> special, List<Integer> needs) {
        Map<List<Integer>, Integer> dp = new HashMap<>();
        List<Integer> allZero = new ArrayList<>();
        for(int i=0;i<needs.size();i++) {
            allZero.add(0);
        }
        dp.put(allZero, 0);
        return dfs(needs, price, special, dp);
    }
    private int dfs(List<Integer> needs, List<Integer> price, List<List<Integer>> special, Map<List<Integer>, Integer> dp) {
        if(dp.containsKey(needs)) return dp.get(needs);
        int res = Integer.MAX_VALUE;
        for(List<Integer> s : special) {
            List<Integer> needsCopy = new ArrayList<>(needs);
            boolean valid = true;
            for(int i=0;i<needs.size();i++) {
                needsCopy.set(i, needsCopy.get(i) - s.get(i));
                if(needsCopy.get(i) < 0) {
                    valid = false;
                    break;
                }
            }
            if(valid) {
                res = Math.min(res, s.get(needs.size()) + dfs(needsCopy, price, special, dp));
            }
        }
        //What if we do not use specials? specials can be deceiving,
        //perhaps buying using regular prices is cheaper.
        int noSpecial = 0;
            for(int i=0;i<needs.size();i++) {
                noSpecial += needs.get(i) * price.get(i);
            }
        res = Math.min(res, noSpecial);    

        dp.put(needs, res);
        return res;
    }
}
https://discuss.leetcode.com/topic/95247/java-code-using-dfs-with-memorization
 * @author het
 *
 */
public class LeetCode638 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
