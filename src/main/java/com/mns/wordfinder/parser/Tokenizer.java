package com.mns.wordfinder.parser;

import java.util.Arrays;
import java.util.List;


public class Tokenizer {

    public List<String> tokenize(String text) {

        text = text.toLowerCase();
        
        text = text.replaceAll("[^a-z0-9]", " ");

        return Arrays.asList(text.split("\\s+"));

    }
    
}
