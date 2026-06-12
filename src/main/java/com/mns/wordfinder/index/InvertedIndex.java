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
    
}
