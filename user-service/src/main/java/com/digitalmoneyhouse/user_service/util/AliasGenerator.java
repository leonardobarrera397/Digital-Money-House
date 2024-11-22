package com.digitalmoneyhouse.user_service.util;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class AliasGenerator {

    private final List<String> words;

    public AliasGenerator() {
        this.words = loadWordsFromFile();
    }

    private List<String> loadWordsFromFile() {
        List<String> wordList = new ArrayList<>();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("words.txt")) {
            assert inputStream != null;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

                String line;
                while ((line = reader.readLine()) != null) {
                    wordList.add(line.trim());
                }
            }
        } catch (IOException | NullPointerException e) {
            throw new RuntimeException("Error reading words file: " + "words.txt", e);
        }
        return wordList;
    }

    public String generateAlias() {
        if (words.size() < 3) {
            throw new IllegalStateException("Not enough words to generate an alias");
        }

        List<String> selectedWords = new ArrayList<>(words);
        Collections.shuffle(selectedWords);

        return String.join(".", selectedWords.subList(0, 3));
    }
}