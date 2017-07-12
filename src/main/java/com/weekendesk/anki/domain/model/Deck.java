package com.weekendesk.anki.domain.model;

import java.util.List;
import java.util.stream.Collectors;

public class Deck {

    private final List<AnkiCard> cards;

    public Deck(List<AnkiCard> cards) {
        this.cards = cards;
    }

    public static Deck newDeck(List<AnkiCard> cards) {
        return new Deck(cards);
    }

    public List<AnkiCard> getCards() {
        return cards;
    }

    public List<AnkiCard> getCards(BoxColor box) {
        return cards.stream().filter(c -> c.getBox() == box).collect(Collectors.toList());
    }
}
