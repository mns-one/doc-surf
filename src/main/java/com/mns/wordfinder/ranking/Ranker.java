package com.mns.wordfinder.ranking;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.mns.wordfinder.index.InvertedIndex;

@Component
public class Ranker {

    private final InvertedIndex indexer;

    public Ranker(InvertedIndex indexer){
        this.indexer = indexer;
    }

    public void calcWordFreq(String word, Set<String> resultList, Map<String, Integer> rankedMap){

        Map<String, Integer> docList = indexer.wordData(word);
        //System.out.println("Word map - > " + docList);

        for(Map.Entry<String, Integer> entry : docList.entrySet() ){

            if(resultList.contains(entry.getKey())){
                rankedMap.put( entry.getKey(), rankedMap.getOrDefault(entry.getKey(), 0) + entry.getValue() );
            }

        }

    }

    public Map<String, Integer> rankResult(String word1, String word2, Set<String> resultList){

        Map<String, Integer> rankedMap = new HashMap<>();

        if(word1 != null) calcWordFreq(word1, resultList, rankedMap);
        if(word2 != null) calcWordFreq(word2, resultList, rankedMap);
        //System.out.println("Ranked map - > " + rankedMap);

        Map<String, Integer> sortedMap = rankedMap.entrySet()
        .stream()
        .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
        .collect(Collectors.toMap(
            Map.Entry::getKey,
            Map.Entry::getValue,
            (e1, e2) -> e1,
            LinkedHashMap::new
        ));

        return sortedMap;

    }

    
}
