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

import java.util.Scanner;
import java.util.Iterator;
import java.io.File;

public class WordFrequency {
    public static void main(String[] args) throws Exception{
        final int[] SIZES = {1361, 2003, 3001};
        DictionaryInterface<String ,Integer> dictionary = new HashedDictionary<>(1361);
        DictionaryInterface<String ,Integer> dictionary2 = new HashedDictionary<>(2003);
        DictionaryInterface<String ,Integer> dictionary3 = new HashedDictionary<>(3001);

        Scanner scnr = new Scanner(new File("usconstitution.txt"));
        while(scnr.hasNext()){
            String nextWord = scnr.next();
            try{
                Integer.parseInt(nextWord);
            }
            catch(Exception e){
                nextWord = nextWord.toLowerCase();
                Integer frequency = dictionary.getValue(nextWord);
                if(frequency == null){
                    dictionary.add(nextWord, 1);
                    dictionary2.add(nextWord, 1);
                    dictionary3.add(nextWord, 1);
                }
                else{
                    dictionary.add(nextWord, frequency + 1);
                    dictionary2.add(nextWord, frequency + 1);
                    dictionary3.add(nextWord, frequency + 1);
                }
            }
        }

        
        System.out.println("Word Frequency by J. Beauchamp\n");
        System.out.println("Count Word");
        System.out.println("----- -----------------");
        
        Iterator<String> keyIterator = dictionary.getKeyIterator();
        while(keyIterator.hasNext()){
            String key = keyIterator.next();
            int count = dictionary.getValue(key);
            System.out.println(count + "\t" + key);
        }

        int[] collisions = new int[SIZES.length];
        for(int i = 0; i < SIZES.length; i++){
            Iterator<String> keyIterator2 = dictionary.getKeyIterator();
            while(keyIterator2.hasNext()){
                String nextWord = keyIterator2.next();
                int count = dictionary.getValue(nextWord);
                dictionary.add(nextWord, count);
            }
            collisions[i] = dictionary.getCollisionCount();
        }
        System.out.println("\nUnique Words: " + dictionary.getUniqueWordCount());
        System.out.println("\n\nTable\nLength\tCollisions");
        System.out.println(dictionary.getSize() + "\t" + dictionary.getCollisionCount());
        System.out.println(dictionary2.getSize() + "\t" + dictionary2.getCollisionCount());
        System.out.println(dictionary3.getSize() + "\t" + dictionary3.getCollisionCount());
    }
}
