package alite.leetcode.newExtra.L500.extra.finish;
/**
 * Using Trie

http://www.geeksforgeeks.org/print-valid-words-possible-using-characters-array/
Given a dictionary and a character array, print all valid words that are possible using characters from the array.
The idea is to use Trie data structure to store dictionary, then search words in Trie using characters of given array.
Create an empty Trie and insert all words of given dictionary into the Trie.
After that, we have pick only those characters in ‘Arr[]’ which are a child of the root of Trie.
To quickly find whether a character is present in array or not, we create a hash of character arrays.


struct TrieNode


{


    TrieNode *Child[SIZE];


 


    // isLeaf is true if the node represents


    // end of a word


    bool leaf;

};

// A recursive function to print all possible valid

// words present in array

void searchWord(TrieNode *root, bool Hash[], string str)

{

    // if we found word in trie / dictionary

    if (root->leaf == true)

        cout << str << endl ;


    // traverse all child's of current root

    for (int K =0; K < SIZE; K++)

    {

        if (Hash[K] == true && root->Child[K] != NULL )

        {

            // add current character

            char c = int_to_char(K);


            // Recursively search reaming character of word

            // in trie

            searchWord(root->Child[K], Hash, str + c);

        }

    }

}


// Prints all words present in dictionary.

void PrintAllWords(char Arr[], TrieNode *root, int n)

{

    // create a 'has' array that will store all present

    // character in Arr[]

    bool Hash[SIZE];


    for (int i = 0 ; i < n; i++)

        Hash[char_int(Arr[i])] = true;


    // tempary node

    TrieNode *pChild = root ;


    // string to hold output words

    string str = "";


    // Traverse all matrix elements. There are only 26

    // character possible in char array

    for (int i = 0 ; i < SIZE ; i++)

    {

        // we start searching for word in dictionary

        // if we found a character which is child

        // of Trie root

        if (Hash[i] == true && pChild->Child[i] )

        {

            str = str+(char)int_to_char(i);

            searchWord(pChild->Child[i], Hash, str);

            str = "";

        }

    }

}

    // Root Node of Trie

    TrieNode *root = getNode();

 

    // insert all words of dictionary into trie

    int n = sizeof(Dict)/sizeof(Dict[0]);

    for (int i=0; i<n; i++)

        insert(root, Dict[i]);

 

    char arr[] = {'e', 'o', 'b', 'a', 'm', 'g', 'l'} ;

    int N = sizeof(arr)/sizeof(arr[0]);

 

    PrintAllWords(arr, root, N);
 * @author het
 *
 */
public class Using_trie {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
