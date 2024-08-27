package com.example.projectbeegame;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class GameController {
    private static String path = "src/main/resources/sozluk_v2.txt";
    int total = 0;
    public ArrayList<String> allWords = new ArrayList<>();
    public ArrayList<String> pangramWords = new ArrayList<>();
    public String honeycombWord = "";
    public ArrayList<String> validateWords = new ArrayList<>();

    public void readFile() throws IOException {
        FileReader fileReader = new FileReader(path);
        BufferedReader mBufferedReader = new BufferedReader(fileReader);
        String row;
        String[] words;
        ArrayList<Character> pangramWordLetter = new ArrayList<>();

        while ((row = mBufferedReader.readLine()) != null) {
            words = row.split(" ");
            for (String word : words) {
                allWords.add(word);
                pangramWordLetter.clear();
                for (int i = 0; i <= word.length() - 1; i++) {
                    if (!(pangramWordLetter.contains(word.charAt(i))))
                        pangramWordLetter.add(word.charAt(i));
                    if (pangramWordLetter.size() == word.length() && pangramWordLetter.size() == 7)
                        pangramWords.add(word);
                }
            }
        }
        mBufferedReader.close();
    }

    public boolean pangramCheck(String text) {
        for (int i=0;i<pangramWords.size();i++) {
            if (text.equals(pangramWords.get(i))) {
                return true;
            }
        }
        return false;
    }

    public String getRandomPangram() {
        int min = 0, max = pangramWords.size() - 1;
        int randomIndex = (int) ((Math.random() * (max - min)) + min);
        return pangramWords.get(randomIndex);
    }

    public void createPangram(String text, Boolean isUserWord) throws IOException {
        String usedPangram = "";
        if (isUserWord == true) {
            usedPangram = text;
        } else {
            usedPangram = getRandomPangram();
        }
        ArrayList<Character> letters = separateWord(usedPangram);
        ArrayList<Character> tempLetters;
        for (int i = 0; i < allWords.size(); i++) {
            int count = 0;
            tempLetters = separateWord(allWords.get(i));
            for (int tempLetterCount = 0; tempLetterCount < tempLetters.size(); tempLetterCount++) {
                for (int lettersCount = 0; lettersCount < letters.size(); lettersCount++) {
                    if (tempLetters.get(tempLetterCount).equals(letters.get(lettersCount))) {
                        count++;
                    }
                }
            }
            if (count == tempLetters.size() && tempLetters.size() > 3) {
                validateWords.add(allWords.get(i));
                System.out.println(allWords.get(i));
            }
        }
        if (validateWords.size() > 20 && validateWords.size() < 80) {
            honeycombWord = usedPangram;
            System.out.println("------------------------------");
            System.out.println(honeycombWord);
            System.out.println(validateWords.size());
        }
        else {
            if(isUserWord)
                validateWords.clear();

            else{
                validateWords.clear();
                createPangram(text, isUserWord);
            }
        }
    }

    public int calculatePoint(String text) {
        if (text.length() == 4)
            total++;
        else if (text.length() == 5)
            total += 2;
        else if (text.length() == 6)
            total += 3;
        else if (text.length() >= 7)
            total += 4;
        return total;
    }

    public boolean isValid(String text) {
        for (String validWord : validateWords) {
            if (text.equals(validWord))
                return true;
        }
        return false;
    }

    public ArrayList<Character> separateWord(String word) {
        ArrayList<Character> separatedWord = new ArrayList<>();
        for (int i = 0; i < word.length(); i++)
            separatedWord.add(word.charAt(i));
        return separatedWord;
    }
}
