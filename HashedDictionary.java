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

public class HashedDictionary<K, V> implements DictionaryInterface<K, V>{
    private Entry<K, V> [] hashDictionary;   //The hashed dictionary ADT
    private int numberOfEntries;    //Keeps track of the amount of entries
    private int collisionCount;     //Keeps track the amount of collisions that occur
    private int capacity;   //Stores the value of the total capacity
    private int uniqueWordCount = 0;    //Keeps track of the amount of unique words
    private static final int DEFAULT_CAPACITY = 31;  //Defines the default capacity for the default constuctor of the hashed dictionary
    protected final Entry<K,V> AVAILABLE = new Entry<>(null, null);

    //Default constructor for hashed dictionary
    public HashedDictionary(){
        this (DEFAULT_CAPACITY);
    }

    //Standardized constructor for hashed dictionary
    public HashedDictionary(int initialCapacity){
        capacity = initialCapacity;
        numberOfEntries = 0;
        Entry<K, V>[] temp = (Entry<K,V> []) new Entry[initialCapacity];
        hashDictionary = temp;
        collisionCount = 0;
    }

    //Private inner class Entry that is used as the data types for the heshed dictionary
    private class Entry<K, V> {
        private K key;
        private V value;

        private Entry (K searchKey, V dataValue){   //paramterized constructor for Entry class
            key = searchKey;
            value = dataValue;
        }

        private K getKey(){ //getter class for key objects
            return key;
        }

        private V getValue(){   //getter class for value objects
            return value;
        }

        private void setValue(V newValue){  //setter class for value objects
            value = newValue;
        }
    }

    //Private Iterator class that creates an iterator object that traverse the keys in the dicitonary
    private class KeyIterator<T> implements Iterator<K>{
        private int currentIndex;   //The index of the current key that is being considered by the key iterator object
        private int numberLeft;     //The number of keys left to consider
        
        private KeyIterator(){  //The constructor of the iterator object
            currentIndex = 0;
            numberLeft = 0;
            for (Entry<K,V> entry : hashDictionary){
                if(entry != null){
                    numberLeft++;
                }
            }
        }

        /**Checks if there are still more keys to be traversed
         * @return True if there is more keys after the current key */
        public boolean hasNext(){
            return currentIndex < numberLeft;
        }

        /**Retrieves the next key object in the dictionary
         * @return A key object that follows the current key object */
        public K next(){
            K result;
            while ((hashDictionary[currentIndex] == null) && (currentIndex < capacity)){
                currentIndex++;
            } if (currentIndex < capacity){
                result = hashDictionary[currentIndex].getKey();
                currentIndex++;
            } else{
                throw new RuntimeException("Can not call next");
            }

            return result;
        }
    }

    /**Creates a new index for the key object in the hashed dictionary using its hash code
     * @param key A key object that is used to create a new hash index for itself
     * @return An integer index for the key objects location in the hashed dictionary */
    private int getHashIndex(K key){
        int index = key.hashCode() % hashDictionary.length;
        if(index < 0){
            index = index + hashDictionary.length;
        }
        return index;
    }

    /**Resolves any collisions by finding a new location for a key object
     * @param index The index of the key object in the dictionary
     * @param key The given key object that is being used to find a new location
     * @return A integer of the new index */
    private int linearProbe(int index, K key){  //IN ADD METHOD
        boolean found = false;
        int availableStateIndex = -1;   //returns if location is available

        while ( !found && (hashDictionary[index] != null) ){     //checks if the hashcode index is not found and the location is not null
            if (hashDictionary[index] != AVAILABLE){     //checks if the location is AVAILABLE
                if (key.equals(hashDictionary[index].getKey()))  //if NOT AVILABLE, checks if keys match
                    found = true;  //returns found as true if keys match
                else             
                    index = (index + 1) % hashDictionary.length;     //increments to the next index
            }
            else{
                if (availableStateIndex == -1)  //checks if the location is avilable
                    availableStateIndex = index;    //sets the avilable spot to index
                index = (index + 1) % hashDictionary.length;   //increments the index to next index
            } 
        } 
        if (found || (availableStateIndex == -1) )  //checks if the index is key IS in the dictionary, OR if it was not found
            return index;   //returns the index      
        else
            return availableStateIndex;     //returns the available location
    }

    /**Adds a new entry into the dictionary. Should the key already exist
     * in the dictionary, the corresponding value is replaced with the new entry
     * @param key An object search key of the new entry
     * @param value An object associated with the search key
     * @return The corresponding value being replaced. If there is no value, thenreturns null */  
    public V add(K key, V value){
        if((key == null) || (value == null)){
            throw new IllegalArgumentException("Cannot add null to a dictionary.");
        }
        else{
            V result;
            int index = getHashIndex(key);
            index = linearProbe(index, key);

            if(hashDictionary[index] == null){
                hashDictionary[index] = new Entry<>(key, value);
                numberOfEntries++;
                uniqueWordCount++;
                result = null;
            }
            else{
                result = hashDictionary[index].getValue();
                hashDictionary[index].setValue(value);
                collisionCount++;
            }
            return result;
        }
    }

    /**Retrieves an object value associated with the given object search key
     * @param key An object search key that is the key of the object value to be returned
     * @return The object value that is associated with the object key. If there is no entry,
     * then returns null */
    public V getValue(K key){
        V result = null;
        int index = getHashIndex(key);
        if((hashDictionary[index] != null) && (hashDictionary[index] != AVAILABLE)){
            result = hashDictionary[index].getValue();
        }
        return result;
    }

    /**Creates an interator object to traverse the search keys in the dictionary
     * @returns An iterator that provides sequential access to the search keys in the dictionary */
    public Iterator<K> getKeyIterator() {
        return new KeyIterator<>();
    }

    /**Determines if the dictionary is empty
     * @return True if the dictionary is empty */
    public boolean isEmpty() {
        throw new UnsupportedOperationException("Unimplemented method.");   
    }

    /**Returns the number of collisions that occur in the dictionary
     * @return the number of collisions that occur */
    public int getCollisionCount(){
        return collisionCount;
    }

    /**Retrieves the amount of unique words in the dictionary
     * @return The amount of unique words in the dictionary */
    public int getUniqueWordCount(){
        return uniqueWordCount;
    }

    /**Removes a specific entry from the dictionary using an object search key
     * @param key An object search key that is the key of the entry to be removed
     * @return The object value that was associated with the object search key. If there
     * is no value, then returns null */
    public V remove(K key) {
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }

    /**Determines whether a specific entry is in the dictionary
     * @param key An object search key of the desired entry
     * @return True if the key is associated with an entry in the dictionary */
    public boolean contains(K key) {
        throw new UnsupportedOperationException("Unimplemented method.");
    }

    /**Creates an iterator object to traverse the values in the dictionary
     * @return An iterator that provides sequential access to the values in the dictionary */
    public Iterator<V> getValueIterator(){
        throw new UnsupportedOperationException("Unimplemented method.");
    }

    /**Removes all entries from the dictionary */
    public void clear() {
        throw new UnsupportedOperationException("Unimplemented method.");
    }

    /**Gets the size of the dictionary
     * @return Number of entrys, or key-value pairs, currently in the dictionary */
    public int getSize() {
        return hashDictionary.length;
    }
}