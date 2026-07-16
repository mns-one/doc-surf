package com.mns.wordfinder.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.stereotype.Component;


@Component
public class TxtExtractor implements DocumentExtractor {

    @Override
    public boolean supports(Path file) {

        Boolean txt =  file.toString().endsWith(".txt");
        Boolean md =  file.toString().endsWith(".md");
        return txt || md;

    }

    @Override
    public String extract(Path file) throws IOException {
        return Files.readString(file);
    }
    
}
