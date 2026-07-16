package com.mns.wordfinder.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import lombok.Data;


@Data
public class WordData {

    private Map<String, Set<Integer>> wordData = new HashMap<>();
    private int freq = 0;

    public boolean containsFile(String filename){
        return wordData.containsKey(filename);
    }

    public int getFreq(){
        return freq;
    }

    public int getFreqInFile(String filename){
        return wordData.get(filename).size();
    }

    public void addData(String filename, int pos){

        if(!containsFile(filename)){
            wordData.put(filename, new HashSet<>());
        }

        wordData.get(filename).add(pos);
        freq++;

    }

    public Set<String> getAllFilename(){
        return new HashSet<>(wordData.keySet());
    }
    
    public Set<Integer> getPositions(String filename) {
        return new HashSet<>(wordData.getOrDefault(filename, new HashSet<>()));
    }


}
