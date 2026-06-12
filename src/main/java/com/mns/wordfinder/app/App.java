package com.mns.wordfinder.app;

import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Component;

import com.mns.wordfinder.index.InvertedIndex;
import com.mns.wordfinder.load.Load;
import com.mns.wordfinder.parser.Tokenizer;

@Component
public class App {

    private final InvertedIndex indexer;

    public App(InvertedIndex indexer){
        this.indexer = indexer;
    }

    public void load() {

        Load loader = new Load();
        String text = loader.file();

		Tokenizer tokenize = new Tokenizer();
		List<String> tokens = tokenize.tokenize(text);
		System.out.println(tokens);


		for(String token: tokens){
			indexer.addWord(token);
		}

		indexer.show();

    }

    public void start() {

        Scanner scanner = new Scanner(System.in);

        while(true){

            System.out.print("Enter a word: ");

            String word = scanner.nextLine().toLowerCase().trim();

            if(indexer.contains(word)){
                System.out.println(word + " exists in doc!");
                System.out.println("Freq -> " + indexer.frequency(word));
            }
            else{
                System.out.println(word + " does not exist in doc!");
            }

        }

    }
    
}
