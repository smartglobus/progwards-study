package ru.progwards.java1.lessons.maps;


import java.io.FileReader;
import java.io.IOException;

import java.util.*;


public class UsageFrequency {
    private String fileToString;

    public UsageFrequency(String fileName) {
        processFile(fileName);
    }

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
//        Collection<Character> characterSet = new ArrayList<>();
//        for (char ch : fileToChar) characterSet.add(ch);
//        for (char ch : fileToChar)
//            if (Character.isAlphabetic(ch) || Character.isDigit(ch))
//                getLtr.putIfAbsent(ch, Collections.frequency(characterSet, ch));

        for (char ch : fileToChar) {
            if (Character.isAlphabetic(ch) || Character.isDigit(ch))
                if (getLtr.putIfAbsent(ch, 1) != null) getLtr.put(ch, getLtr.get(ch) + 1);
        }
        return getLtr;
    }


    // вернуть Map, который содержит все найденные слова и количество раз, которое каждое слово встретилось.
// Знаки препинания, такие как “.,!? @” и др являются разделителями.
    public Map<String, Integer> getWords() {
        Map<String, Integer> getWrd = new HashMap<>();
        String[] words = fileToString.split("[\\W]+");
        List<String> wordsSet = new ArrayList<>(Arrays.asList(words));
        for (int i = 0; i < wordsSet.size(); i++) {
            if ("".equals(wordsSet.get(i)) || wordsSet.get(i) == null) continue;
            getWrd.putIfAbsent(wordsSet.get(i), Collections.frequency(wordsSet, wordsSet.get(i)));
        }
        return getWrd;
    }

    public static void main(String[] args) {
        UsageFrequency test = new UsageFrequency("C:\\Users\\User\\IdeaProjects\\Progwards first project\\src\\ru\\progwards\\java1\\lessons\\maps\\wiki.test.tokens");

        for (var lt : test.getLetters().entrySet()) {
            System.out.println(lt);
        }
        System.out.println("---------------------------");
        for (var wd : test.getWords().entrySet()) {
            System.out.println(wd);
        }
    }
}