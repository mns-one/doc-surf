package com.mns.wordfinder.search;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.mns.wordfinder.index.InvertedIndex;

@Component
public class Query {

    private final InvertedIndex indexer;

    public Query(InvertedIndex indexer){
        this.indexer = indexer;
    }

    private Set<String> getWordSet(String word){
        if (!indexer.contains(word)) {
            return new HashSet<>();
        }
        return new HashSet<>(indexer.wordData(word).keySet());
    }

    public Set<String> search(String[] words){

        // if its a single word
        if(words.length == 1){
            return getWordSet(words[0]);
        }

        String word1 = words[0];
        String op = words[1];
        String word2 = words[2];

        Set<String> left = getWordSet(word1);
        Set<String> right = getWordSet(word2);

        if(op.equals("and")){

            System.out.println("use AND!");

            left.retainAll(right);
            return left;

        }
        else if(op.equals("not")){

            System.out.println("use NOT!");

            left.removeAll(right);
            return left;

        }
        else{

            System.out.println("use OR!");

            left.addAll(right);
            return left;

        }

    }
    
}
