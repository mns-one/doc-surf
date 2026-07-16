package com.mns.wordfinder.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.mns.wordfinder.index.InvertedIndex;
import com.mns.wordfinder.model.WordData;


@Component
public class Query {

    private final InvertedIndex indexer;

    public Query(InvertedIndex indexer){
        this.indexer = indexer;
    }

    public  Map<String, Integer> find(String[] phrase){

        Map<String, Integer> matchedDocs = new HashMap<>();

        if (phrase == null || phrase.length == 0) {
            return matchedDocs;
        }

        // fetch document set for each word in phrase
        List<Set<String>> setList = new ArrayList<>();

        for (String word : phrase) {
            WordData data = indexer.getWordData(word);
            if (data == null) {
                return matchedDocs; // if any word is not indexed, return empty result
            }
            setList.add(data.getAllFilename());
        }

        // Use set intersection to build set containing all words in phrase
        Set<String> candidateDocs = setList.getFirst();

        for (int i = 1; i < setList.size(); i++) {
            candidateDocs.retainAll(setList.get(i));
        }

        // check if candidate doc contains phrase words in correct order
        for (String filename : candidateDocs) {
            // retuns no of occurences of phrase in document, 0 if not found
            int score = isPhraseInDoc(phrase, filename);
            if (score != 0) {
                matchedDocs.put(filename, score);
            }
        }

        // return empty result if no docs left in map
        if(matchedDocs.size() == 0) return matchedDocs;

        // sorted the map data using score values to rank the documents based on phrase occurences
        Map<String, Integer> sortedResult = matchedDocs.entrySet()
        .stream()
        .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
        .collect(Collectors.toMap(
            Map.Entry::getKey,
            Map.Entry::getValue,
            (e1, e2) -> e1,
            LinkedHashMap::new
        ));

        return sortedResult;

    }

    private int isPhraseInDoc(String[] phrase, String filename) {

        // use postional index to check for adjacency of phrase words in order
        Set<Integer> currentPositions = indexer.getWordData(phrase[0]).getPositions(filename);

        for (int i = 1; i < phrase.length; i++) {
            Set<Integer> nextWordPositions = indexer.getWordData(phrase[i]).getPositions(filename);
            Set<Integer> matchedPositions = new HashSet<>();

            // build set of all next possible indexes
            for (int pos : currentPositions) {
                if (nextWordPositions.contains(pos + 1)) {
                    matchedPositions.add(pos + 1);
                }
            }

            // if no next index found, phrase does not exist in document
            if (matchedPositions.isEmpty()) {
                return 0;
            }

            currentPositions = matchedPositions;
        }

        return currentPositions.size();
    }
    
}

