package alite.leetcode.newExtra.finished;
/**
 * 
 * 
 * 
 * Count of parallelograms in a plane - GeeksforGeeks

Count of parallelograms in a plane - GeeksforGeeks
Given some points on a plane, which are distinct and no three of them lie on the same line. We need to find number of Parallelograms 
with the vertices as the given points.



We can solve this problem by using a special property of parallelograms that diagonals of a parallelogram 
intersect each other in the middle. So if we get such a middle point which is middle point of more than one line segment, 
then we can conclude that a parallelogram exists, more accurately if a middle point occurs x times, then diagonals of
 possible parallelograms can be chosen in xC2 ways, i.e. there will be x*(x-1)/2 parallelograms corresponding to this
  particular middle point with a frequency x. So we iterate over all pair of points and we calculate their middle point 
  and increase frequency of middle point by 1. At the end, we count number of parallelograms according to the frequency
   of each distinct middle point as explained above. As we just need frequency of middle point, division by 2 is ignored 
   while calculating middle point for simplicity.


int countOfParallelograms(int x[], int y[], int N)

{

    // Map to store frequency of mid points

    map<pair<int, int>, int> cnt;

    for (int i=0; i<N; i++)

    {

        for (int j=i+1; j<N; j++)

        {

            // division by 2 is ignored, to get

            // rid of doubles

            int midX = x[i] + x[j];

            int midY = y[i] + y[j];

 

            // increase the frequency of mid point

            cnt[make_pair(midX, midY)]++;

        }

    }

 

    // Iterating through all mid points

    int res = 0;

    for (auto it = cnt.begin(); it != cnt.end(); it++)

    {

        int freq = it->second;

 

        // Increase the count of Parallelograms by

        // applying function on frequency of mid point

        res += freq*(freq - 1)/2

    }

 

    return res;

}
Read full article from Count of parallelograms in a plane - GeeksforGeeks
 * Count of parallelograms in a plane - GeeksforGeeks

Count of parallelograms in a plane - GeeksforGeeks
Given some points on a plane, which are distinct and no three of them lie on the same line. We need to find number of Parallelograms with the vertices as the given points.



We can solve this problem by using a special property of parallelograms that diagonals of a parallelogram intersect each other in the middle. So if we get such a middle point which is middle point of more than one line segment, then we can conclude that a parallelogram exists, more accurately if a middle point occurs x times, then diagonals of possible parallelograms can be chosen in xC2 ways, i.e. there will be x*(x-1)/2 parallelograms corresponding to this particular middle point with a frequency x. So we iterate over all pair of points and we calculate their middle point and increase frequency of middle point by 1. At the end, we count number of parallelograms according to the frequency of each distinct middle point as explained above. As we just need frequency of middle point, division by 2 is ignored while calculating middle point for simplicity.


int countOfParallelograms(int x[], int y[], int N)

{

    // Map to store frequency of mid points

    map<pair<int, int>, int> cnt;

    for (int i=0; i<N; i++)

    {

        for (int j=i+1; j<N; j++)

        {

            // division by 2 is ignored, to get

            // rid of doubles

            int midX = x[i] + x[j];

            int midY = y[i] + y[j];

 

            // increase the frequency of mid point

            cnt[make_pair(midX, midY)]++;

        }

    }

 

    // Iterating through all mid points

    int res = 0;

    for (auto it = cnt.begin(); it != cnt.end(); it++)

    {

        int freq = it->second;

 

        // Increase the count of Parallelograms by

        // applying function on frequency of mid point

        res += freq*(freq - 1)/2

    }

 

    return res;

}
 * @author het
 *
 */
public class Count_of_parallelograms_in_a_plane {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
