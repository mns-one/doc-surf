package com.mns.wordfinder.file;

import java.io.IOException;
import java.nio.file.Path;


public interface DocumentExtractor {

    boolean supports(Path file);

    String extract(Path file) throws IOException;
    
}
