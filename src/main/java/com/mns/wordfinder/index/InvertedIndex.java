package com.mns.wordfinder.index;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class InvertedIndex {

    private final Map<String, Integer> index = new HashMap<>();

    public void addWord(String word) {

        word = word.toLowerCase().trim();
        index.put(word, index.getOrDefault(word, 0)+1);

    }

    public boolean contains(String word) {

        return index.containsKey(word);

    }

    public int frequency(String word) {

        return index.getOrDefault(word, 0);

    }

    public void show() {
        System.out.println(index);
    }

    public void stats() {

        int totalwords = 0;
        int maxFreq = 0;
        String maxFreqWord = "";

        for(Map.Entry<String, Integer> entry : index.entrySet()){

            String word = entry.getKey();
            int freq = entry.getValue();

            totalwords += freq;
            if(freq > maxFreq){
                maxFreq = freq;
                maxFreqWord = word;
            }

        }

        System.out.println("Total words -> " + totalwords);
        System.out.println("Unique words -> " + index.size());
        System.out.println("Most freq word -> " + maxFreqWord);

    }
    
}
