package com.mns.wordfinder.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class File {

    public String load(Path path) {

        try{
            String content = Files.readString(path);
            //System.out.println(content);
            return content;
        }
        catch(IOException e){
            System.out.println("File doesnt exist!");
        }

        return "";

    }
    
}
