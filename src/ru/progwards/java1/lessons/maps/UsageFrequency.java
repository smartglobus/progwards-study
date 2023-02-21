package ru.progwards.java1.lessons.maps;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class UsageFrequency {
    private String fileToString;

    public void processFile(String fileName) {
        try (FileReader reader = new FileReader(fileName); Scanner scanner = new Scanner(reader)) {
            StringBuilder builder = new StringBuilder();
            while (scanner.hasNextLine()) builder.append(scanner.nextLine());
            fileToString = builder.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // вернуть Map, который содержит все найденные буквы и цифры, и количество раз, которое встретился каждый искомый символ.
// Знаки препинания, такие как “.,!? @” и др не учитывать.
    public Map<Character, Integer> getLetters() {
        Map<Character, Integer> getLtr = new HashMap<>();
        char[] fileToChar = fileToString.toCharArray();
        Collection<Character> characterSet = new ArrayList<>();
        for (char ch : fileToChar) characterSet.add(ch);
        for (char ch : fileToChar)
            if (Character.isAlphabetic(ch) || Character.isDigit(ch))
                getLtr.putIfAbsent(ch, Collections.frequency(characterSet, ch));
        return getLtr;
    }


    // вернуть Map, который содержит все найденные слова и количество раз, которое каждое слово встретилось.
// Знаки препинания, такие как “.,!? @” и др являются разделителями.
    public Map<String, Integer> getWords() {
        Map<String, Integer> getWrd = new HashMap<>();
        String[] words = fileToString.split("[\\W ]+");
        Collection<String> wordsSet = new ArrayList<>(Arrays.asList(words));
        for (String s : wordsSet) getWrd.putIfAbsent(s, Collections.frequency(wordsSet, s));
        return getWrd;
    }
}