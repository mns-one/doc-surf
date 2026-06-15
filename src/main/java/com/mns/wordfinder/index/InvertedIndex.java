package com.mns.wordfinder.index;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.mns.wordfinder.model.WordData;


@Component
public class InvertedIndex {

    private final Map<String, WordData> index = new HashMap<>();

    public void addWord(String word, String filename, int idx) {

        if(!index.containsKey(word)){
            index.put(word, new WordData());
        }
        index.get(word).addData(filename, idx);

    }

    public boolean contains(String word) {
        return index.containsKey(word);
    }

    public WordData getWordData(String word) {
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

        for (Map.Entry<String, WordData> entry : index.entrySet()) {

            String word = entry.getKey();
            WordData wordData = entry.getValue();

            int wordTotalFreq = wordData.getFreq();

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
