package com.mns.wordfinder.file;

import java.nio.file.Path;
import java.util.List;

import org.springframework.stereotype.Service;

// Factory class to get the appropriate DocumentExtractor based on file type

@Service
public class ExtractorFactory {

    private final List<DocumentExtractor> extractors;

    public ExtractorFactory(List<DocumentExtractor> extractors) {
        this.extractors = extractors;
    }

    // Find the first extractor that supports the given file type
    public DocumentExtractor getExtractor(Path file) {
        if(extractors.isEmpty()){
            throw new IllegalStateException("No extractors available");
        }

        return extractors.stream()
                .filter(e -> e.supports(file))
                .findFirst()
                .orElseThrow(() ->
                    new UnsupportedOperationException("Unsupported file type"));
    }

}
