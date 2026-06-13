package com.mns.wordfinder.app;

import com.mns.wordfinder.WordFinderApplication;
import com.mns.wordfinder.file.File;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

import com.mns.wordfinder.index.InvertedIndex;
import com.mns.wordfinder.model.Document;
import com.mns.wordfinder.parser.Tokenizer;
import com.mns.wordfinder.search.Query;

@Component
public class App {

    private final InvertedIndex indexer;
    private final Query query;

    public App(InvertedIndex indexer, Query qeury){
        this.indexer = indexer;
        this.query = qeury;
    }

    public void load() {

        File loader = new File();
        List<Document> docs = new ArrayList<>();;

        System.out.println("Fetching files...");

        // store stream of all files into document model
        try (Stream<Path> stream = Files.list(Paths.get("doc_files"))) {

            List<Path> filesList = stream.collect(Collectors.toList());

            for (Path file : filesList) {
                //System.out.println("Fetching " + file + "...");
                String content = loader.load(file);
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

            System.out.print("Enter query: ");

            String user_input = scanner.nextLine().toLowerCase().trim();

            String[] parts = user_input.split("\\s+");

            if(parts.length != 1 && (parts.length > 3 || parts.length < 3)){
                System.out.println("Invalid input!");
                continue;
            }

            Set<String> result = query.search(parts);

            if(result.size() == 0){
                System.out.println("No document match found for query!");
            }
            else{  
                System.out.println("Documents matches for Query: " + result);
            }

        }

    }
    
}
