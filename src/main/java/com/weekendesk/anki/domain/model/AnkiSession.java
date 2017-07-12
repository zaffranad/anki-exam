package com.weekendesk.anki.domain.model;

import com.weekendesk.anki.domain.service.FileService;
import com.weekendesk.anki.domain.service.IOService;
import com.weekendesk.anki.domain.service.InitCardService;

import java.io.FileNotFoundException;
import java.util.List;

import static com.weekendesk.anki.domain.model.BoxColor.GREEN;
import static com.weekendesk.anki.domain.model.BoxColor.ORANGE;
import static com.weekendesk.anki.domain.model.BoxColor.RED;

public class AnkiSession {

    public final Deck deck;

    private final IOService ioService;
    private final FileService fileService;

    private AnkiSession(Deck deck, FileService fileService, IOService ioService) {
        this.deck = deck;
        this.fileService = fileService;
        this.ioService = ioService;
    }

    public static AnkiSession newSession(FileService fileService, IOService ioService) throws FileNotFoundException {
        List<AnkiCard> cards = new InitCardService(fileService).load();

        if(cards.isEmpty()){
            throw new RuntimeException("No cards found for deck");
        }

        Deck deck = Deck.newDeck(cards);

        return new AnkiSession(deck, fileService, ioService);
    }

    public void displayRedCards() {
        ioService.showCardsAndAskBox(deck.getCards(RED));
    }

    public Boolean isOver(){
        return deck.getCards(RED).isEmpty() && deck.getCards(ORANGE).isEmpty();
    }

    public void slideBeforeNextRound() {
        deck.getCards(ORANGE).forEach(ankiCard -> ankiCard.setBox(RED));
        deck.getCards(GREEN).forEach(ankiCard -> ankiCard.setBox(ORANGE));
    }

    public Deck getDeck() {
        return deck;
    }

    public void dump() {
        fileService.dumpGame(deck.getCards());
    }

}
