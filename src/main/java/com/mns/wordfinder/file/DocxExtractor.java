package com.mns.wordfinder.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Component;


@Component
public class DocxExtractor implements DocumentExtractor {

    @Override
    public boolean supports(Path file) {
        return file.toString().endsWith(".docx");
    }

    @Override
    public String extract(Path file) throws IOException {

        XWPFDocument doc = new XWPFDocument(Files.newInputStream(file));
        XWPFWordExtractor extractor = new XWPFWordExtractor(doc);
        return extractor.getText();
        
    }
    
}
