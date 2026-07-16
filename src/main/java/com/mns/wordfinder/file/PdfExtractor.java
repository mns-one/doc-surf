package com.mns.wordfinder.file;

import java.io.IOException;
import java.nio.file.Path;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Component;


@Component
public class PdfExtractor implements DocumentExtractor {

    @Override
    public boolean supports(Path file) {
        return file.toString().endsWith(".pdf");
    }

    @Override
    public String extract(Path file) throws IOException {

        try(PDDocument document = Loader.loadPDF(file.toFile())) {
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);
        }
    }
    
}
