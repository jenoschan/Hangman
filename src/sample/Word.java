package sample;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

public class Word {

    private String word;

    public Word() throws IOException {
        wordGenerator();
    }

     public String wordGenerator() throws IOException {
         String content = Files.readString(Paths.get("src/sample/words.txt"), StandardCharsets.US_ASCII);
         String[] words = content.split("\n");
         int index = new Random().nextInt(words.length);
         word = words[index];

         return word;
     }

    public String getWord() {
        return word;
    }

    public int getLetters() {
        return word.length();
    }
}
