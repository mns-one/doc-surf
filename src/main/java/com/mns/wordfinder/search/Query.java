package com.mns.wordfinder.search;

import com.mns.wordfinder.ranking.Ranker;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.mns.wordfinder.index.InvertedIndex;

@Component
public class Query {

    private final Ranker ranker;
    private final InvertedIndex indexer;

    public Query(InvertedIndex indexer, Ranker ranker){
        this.indexer = indexer;
        this.ranker = ranker;
    }

    private Set<String> getWordSet(String word){
        if (!indexer.contains(word)) {
            return new HashSet<>();
        }
        return new HashSet<>(indexer.wordData(word).keySet());
    }

    public Map<String, Integer> search(String[] words){

        // if its a single word
        if(words.length == 1){
            return ranker.rankResult(words[0], null, getWordSet(words[0]));
        }

        String word1 = words[0];
        String op = words[1];
        String word2 = words[2];

        Set<String> left = getWordSet(word1);
        Set<String> right = getWordSet(word2);

        if(op.equals("and")){

            System.out.println("use AND!");

            left.retainAll(right);

        }
        else if(op.equals("not")){

            System.out.println("use NOT!");

            left.removeAll(right);

        }
        else{

            System.out.println("use OR!");

            left.addAll(right);

        }

        return ranker.rankResult(word1, word2, left);

    }
    
}
