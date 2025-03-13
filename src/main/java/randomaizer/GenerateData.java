package randomaizer;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;

public class GenerateData {

    public static int getRandomNumber() {
        Random random = new Random();
        return random.nextInt(10000);
    }

    public static String getRandomWordWithNumbers() {
        boolean useLetters = true;
        boolean useNumbers = true;
        int length = 25;
        return RandomStringUtils.random(length, useLetters, useNumbers);
    }

    public static String getRandomWord() {
        boolean useLetters = true;
        boolean useNumbers = false;
        int length = 20;
        return RandomStringUtils.random(length, useLetters, useNumbers);
    }

    public static String getRandomEmail() {
        StringBuilder stringBuilder = new StringBuilder();
        String end = "@gmail.com";
        String wordsAndNumbers = getRandomWordWithNumbers();
        stringBuilder.append(wordsAndNumbers);
        stringBuilder.append(end);
        return stringBuilder.toString();
    }

}
