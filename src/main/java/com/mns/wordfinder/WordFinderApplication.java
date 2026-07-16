package com.mns.wordfinder;

import java.io.IOException;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.mns.wordfinder.app.App;


@SpringBootApplication
public class WordFinderApplication implements CommandLineRunner {

	private final App app;

    public WordFinderApplication(App app) {
        this.app = app;
    }

	public static void main(String[] args) {
		SpringApplication.run(WordFinderApplication.class, args);
	}

	@Override
    public void run(String... args) {

        try {
            app.initialize();
        } 
        catch (IOException e) {
            throw new RuntimeException("Error initializing application", e);
        }

        app.start();

    }

}

