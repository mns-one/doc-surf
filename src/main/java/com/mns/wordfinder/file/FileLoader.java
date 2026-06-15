package com.mns.wordfinder.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.stereotype.Component;


@Component
public class FileLoader {

    public String loadDir(Path path) {

        try{
            String content = Files.readString(path);
            return content;
        }
        catch(IOException e){
            System.out.println("File doesnt exist!");
        }

        return "";

    }
    
}
