//
//      Name:       Beauchamp, Joshua
//      Project:    4
//      Due:        November 10, 2023
//      Course:     cs-2400-02-f23
//
//      Description:
//                  The project is a hashed dictionary that reads the text file "usconstitution.txt".
//                  It adds words into an implemented dictionary, keepign track of the amount of words
//                  collisions that occur, however numbers are not counted as words. It utilizes a key 
//                  iterator object to examine the each dictionary entry via linear probing, and returns
//                  the amount of occurences of each word. There are dictionary of different sizes to show
//                  how many collisions occur based on the length of a table

import java.util.Iterator;

public interface DictionaryInterface<K,V>{

    /**Adds a new entry into the dictionary. Should the key already exist
     * in the dictionary, the corresponding value is replaced with the new entry
     * @param key An object search key of the new entry
     * @param value An object associated with the search key
     * @return The corresponding value being replaced. If there is no value, thenreturns null */
    public V add(K key, V value);

    /**Removes a specific entry from the dictionary using an object search key
     * @param key An object search key that is the key of the entry to be removed
     * @return The object value that was associated with the object search key. If there
     * is no value, then returns null */
    public V remove(K key);

    /**Retrieves an object value associated with the given object search key
     * @param key An object search key that is the key of the object value to be returned
     * @return The object value that is associated with the object key. If there is no entry,
     * then returns null */
    public V getValue(K key);

    /**Determines whether a specific entry is in the dictionary
     * @param key An object search key of the desired entry
     * @return True if the key is associated with an entry in the dictionary */
    public boolean contains(K key);

    /**Creates an interator object to traverse the search keys in the dictionary
     * @returns An iterator that provides sequential access to the search keys in the dictionary */
    public Iterator<K> getKeyIterator();

    /**Creates an iterator object to traverse the values in the dictionary
     * @return An iterator that provides sequential access to the values in the dictionary */
    public Iterator<V> getValueIterator();

    /**Determines if the dictionary is empty
     * @return True if the dictionary is empty */
    public boolean isEmpty();

    /**Gets the size of the dictionary
     * @return Number of entrys, or key-value pairs, currently in the dictionary */
    public int getSize();

    /**Removes all entries from the dictionary */
    public void clear();

    /**Returns the number of collisions that occur in the dictionary
     * @return the number of collisions that occur */
    public int getCollisionCount();

    /**Retrieves the amount of unique words in the dictionary
     * @return The amount of unique words in the dictionary */
    public int getUniqueWordCount();
}   //End DictionaryInterface
