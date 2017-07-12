package com.weekendesk.anki.domain.service;

import com.weekendesk.anki.domain.model.AnkiCard;
import com.weekendesk.anki.domain.model.BoxColor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileService {

    private final String filePath;

    public FileService(String filePath) throws FileNotFoundException {
        if(!fileExist(filePath)){
            throw new FileNotFoundException(String.format("Missing game file at path: %s", filePath));
        }
        this.filePath = filePath;
    }

    public List<AnkiCard> load(){
        List<AnkiCard> cards = new ArrayList<>();
        try(Stream<String> stream = Files.lines(Paths.get(filePath))){
            cards.addAll(
                    stream.map(this::convertToCard).collect(Collectors.toList())
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(cards.isEmpty()){
            throw new RuntimeException("No cards found in game file.");
        }

        return cards;
    }

    public void dumpGame(List<AnkiCard> cards){

        Path path = Paths.get(filePath);
        try(BufferedWriter writer = Files.newBufferedWriter(path)){
            cards.forEach(card -> {
                try {
                    writer.write(convertToString(card));
                } catch (IOException e) {
                    throw new RuntimeException("Error while dumping to file game", e);
                }
            });
        } catch (IOException e) {
            throw new RuntimeException("Error while dumping to file game", e);
        }


    }

    private String convertToString(AnkiCard card){
        return String.format("%s|%s|%s\n",card.getQuestion(), card.getAnswer(), card.getBox().toString());
    }

    private AnkiCard convertToCard(String line) {
        String[] parts = line.split("\\|");
        if(parts.length != 2 && parts.length != 3){
            throw new RuntimeException("Game file looks corrupted.");
        }

        AnkiCard card;
        if(parts.length == 2){
            card = AnkiCard.newAnkiCard(parts[0], parts[1]);
        }else{
            BoxColor boxColor = BoxColor.fromString(parts[2])
                                     .orElseThrow(
                                             () -> new RuntimeException("Wrong value for box Color in game file")
                                     );
            card = AnkiCard.newAnkiCardWithBox(parts[0], parts[1], boxColor);
        }
        return card;
    }

    public static boolean fileExist(String filePath){
        File file = new File(filePath);
        return file.exists() && !file.isDirectory();
    }
}
