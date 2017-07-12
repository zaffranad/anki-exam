package com.weekendesk.anki.domain.service;

import com.weekendesk.anki.domain.model.AnkiCard;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import static com.weekendesk.anki.domain.model.BoxColor.GREEN;
import static com.weekendesk.anki.domain.model.BoxColor.ORANGE;
import static com.weekendesk.anki.domain.model.BoxColor.RED;

public class IOService {

    public void showCardsAndAskBox(List<AnkiCard> cards) {
        cards.forEach(card -> {
            System.out.println(String.format("%s | %s", card.getQuestion(), card.getAnswer()));
            System.out.print("Choose box (red, orange, green) (r, o, g): ");

            String choice = null;
            while(choice == null){
                choice = readInput();
                switch (choice) {
                    case "red":
                    case "r":
                        card.setBox(RED);
                        break;
                    case "orange":
                    case "o":
                        card.setBox(ORANGE);
                        break;
                    case "green":
                    case "g":
                        card.setBox(GREEN);
                        break;
                    default:
                        choice = null;
                        System.err.println("wrong input... please retry: ");
                }
            }
        });
    }

    private String readInput(){
        InputStreamReader streamReader = new InputStreamReader(System.in);
        BufferedReader bufferedReader = new BufferedReader(streamReader);
        String choice;
        try {
            choice = bufferedReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return choice;
    }
}
