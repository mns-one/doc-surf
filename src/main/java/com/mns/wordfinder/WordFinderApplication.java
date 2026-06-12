package com.mns.wordfinder;

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
        app.load();
        app.start();
    }

}

