package com.mns.wordfinder.scanner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;


@Component
public class FileScanner {

    public List<Path> scan(Path root) throws IOException {

        try(Stream<Path> paths = Files.walk(root)) {

            return paths
                    .filter(Files::isRegularFile)
                    .toList();
        }
    }
    
}
