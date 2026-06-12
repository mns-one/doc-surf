package com.mns.wordfinder.load;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Load {

    public String file() {

        try{
            String content = Files.readString(Path.of("dev_assets/sample.txt"));
            System.out.println(content);
            return content;
        }
        catch(IOException e){
            System.out.println("File doesnt exist!");
        }

        return "";

    }
    
}
