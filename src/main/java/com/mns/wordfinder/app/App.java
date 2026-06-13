package com.mns.wordfinder.app;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

import com.mns.wordfinder.index.InvertedIndex;
import com.mns.wordfinder.load.Load;
import com.mns.wordfinder.model.Document;
import com.mns.wordfinder.parser.Tokenizer;

@Component
public class App {

    private final InvertedIndex indexer;

    public App(InvertedIndex indexer){
        this.indexer = indexer;
    }

    public void load() {

        Load loader = new Load();
        List<Document> docs = new ArrayList<>();;

        // store stream of all files into document model
        try (Stream<Path> stream = Files.list(Paths.get("doc_files"))) {

            List<Path> filesList = stream.collect(Collectors.toList());

            for (Path file : filesList) {
                System.out.println("Fetching " + file + "...");
                String content = loader.file(file);
                Document doc = new Document(file.getFileName().toString(), content);
                docs.add(doc);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        // tokenize and index all documents
        Tokenizer tokenize = new Tokenizer();

        for(Document doc : docs){

            List<String> tokens = tokenize.tokenize(doc.getContent());

            for(String token: tokens){
                indexer.addWord(token, doc.getFilename());
            }

        }

       // indexer.show();
		indexer.stats();

    }

    public void start() {

        Scanner scanner = new Scanner(System.in);

        while(true){

            System.out.print("Enter a word: ");

            String word = scanner.nextLine().toLowerCase().trim();

            if(indexer.contains(word)){
                System.out.println(word + " exists in doc!");
                System.out.println("Data -> " + indexer.wordData(word));
            }
            else{
                System.out.println(word + " does not exist in doc!");
            }

        }

    }
    
}
