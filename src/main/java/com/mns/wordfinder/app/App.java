package com.mns.wordfinder.app;

import com.mns.wordfinder.file.DocumentExtractor;
import com.mns.wordfinder.file.ExtractorFactory;

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

import lombok.extern.slf4j.Slf4j;


@Component
@Slf4j
public class App {

    private final InvertedIndex indexer;
    private final Query query;
    private final Tokenizer tokenize;
    private final ExtractorFactory factory;

    public App(InvertedIndex indexer, Query qeury, Tokenizer tokenize, ExtractorFactory factory){
        this.indexer = indexer;
        this.query = qeury;
        this.tokenize = tokenize;
        this.factory = factory;
    }

    public void initialize() throws IOException {

        System.out.println("---------------------");
        System.out.println("Fetching files...");
        System.out.println("---------------------");

        // fetch all files from directory
        // parse and store file text in Document model
        List<Document> docs = new ArrayList<>();

        try(Stream<Path> stream = Files.list(Paths.get("doc_files"))){
            
            List<Path> filesList = stream.collect(Collectors.toList());

            for (Path file : filesList) {
                try{
                    DocumentExtractor extractor = factory.getExtractor(file);
                    String text = extractor.extract(file);

                    Document doc = new Document(file.getFileName().toString(), text);
                    docs.add(doc);
                }
                catch(RuntimeException e){
                    throw new RuntimeException("Error loading extractor", e);
                }
                catch(Exception e){
                    log.warn("Error loading file {} : {}", file.getFileName(), e.getMessage());
                    continue;
                }
            }
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
