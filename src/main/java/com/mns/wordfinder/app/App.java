package com.mns.wordfinder.app;

import com.mns.wordfinder.file.FileLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
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
    private final FileLoader loader;
    private final Tokenizer tokenize;

    public App(InvertedIndex indexer, Query qeury, FileLoader loader, Tokenizer tokenize){
        this.indexer = indexer;
        this.query = qeury;
        this.loader = loader;
        this.tokenize = tokenize;
    }

    public void initialize() {

        System.out.println("---------------------");
        System.out.println("Fetching files...");
        System.out.println("---------------------");

        // fetch and store all dir files in document model
        List<Document> docs = new ArrayList<>();

        try (Stream<Path> stream = Files.list(Paths.get("doc_files"))) {

            List<Path> filesList = stream.collect(Collectors.toList());

            for (Path file : filesList) {
                String content = loader.loadDir(file);
                Document doc = new Document(file.getFileName().toString(), content);
                docs.add(doc);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        // tokenize and index all documents content
        for(Document doc : docs){

            List<String> tokens = tokenize.tokenize(doc.getContent());
            int idx = 1;

            for(String token: tokens){
                indexer.addWord(token, doc.getFilename(), idx);
                idx++;
            }

        }

        System.out.println("---------------------");
        System.out.println("Indexing complete!");
		indexer.stats();
        System.out.println("---------------------");

    }

    public void start() {

        Scanner scanner = new Scanner(System.in);

        while(true){

            System.out.println("---------------------");
            System.out.print("Enter query: ");

            // fetch all words from user input n process them
            String user_input = scanner.nextLine().toLowerCase().trim();
            String[] parts = user_input.split("\\s+");

            Map<String, Integer> result = query.find(parts);

            // print result map
            if(result.size() != 0){
                System.out.println("Results: ");
                for(Map.Entry<String, Integer> entry : result.entrySet()){
                    System.out.println(entry.getKey() + " - " + entry.getValue() + " Match found");
                }
            }
            else{  
                System.out.println("No match found!");
            }

            System.out.println("---------------------");

        }

    }
    
}
