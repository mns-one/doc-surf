package com.mns.wordfinder.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.stereotype.Component;


@Component
public class PlainTextExtractor implements DocumentExtractor {

    @Override
    public boolean supports(Path file) {

        boolean txt =  file.toString().endsWith(".txt");
        boolean md =  file.toString().endsWith(".md");
        return txt || md;

    }

    @Override
    public String extract(Path file) throws IOException {
        return Files.readString(file);
    }
    
}
