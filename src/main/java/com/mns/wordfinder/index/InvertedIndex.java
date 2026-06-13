package com.mns.wordfinder.index;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

@Component
public class InvertedIndex {

    private final Map<String, Map<String, Integer>> index = new HashMap<>();

    public void addWord(String word, String filename) {

        Map<String, Integer> docs = index.get(word);
        if (docs == null) {
            docs = new HashMap<>();
            index.put(word, docs);
        }

        docs.put(filename, docs.getOrDefault(filename, 0) + 1);

    }

    public boolean contains(String word) {

        return index.containsKey(word);

    }

    public Map<String, Integer> wordData(String word) {

        return index.get(word);

    }

    public Set<String> allWords(){
        return index.keySet();
    }

    public void show() {
        System.out.println(index);
    }

    public void stats() {
        int totalWords = 0;
        int maxFreq = 0;
        String maxFreqWord = "";

        for (Map.Entry<String, Map<String, Integer>> entry : index.entrySet()) {
            String word = entry.getKey();
            Map<String, Integer> docFreqs = entry.getValue();

            int wordTotalFreq = 0;
            for (int freq : docFreqs.values()) {
                wordTotalFreq += freq;
            }

            totalWords += wordTotalFreq;

            if (wordTotalFreq > maxFreq) {
                maxFreq = wordTotalFreq;
                maxFreqWord = word;
            }
        }

        System.out.println("Total words -> " + totalWords);
        System.out.println("Unique words -> " + index.size());
        System.out.println("Most freq word -> " + maxFreqWord);
    }
    
}
