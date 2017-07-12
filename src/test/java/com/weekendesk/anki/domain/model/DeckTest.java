package com.weekendesk.anki.domain.model;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class DeckTest {

    @Test
    public void newDeck_someCards_allCardsFound() throws Exception {
        // Given
        AnkiCard firstCard = new AnkiCard("q1", "r1", BoxColor.RED);
        AnkiCard secondCard = new AnkiCard("q2", "r2", BoxColor.RED);
        AnkiCard thirdCard = new AnkiCard("q3", "r3", BoxColor.GREEN);

        // When
        Deck deck = Deck.newDeck(Arrays.asList(
                firstCard,
                secondCard,
                thirdCard
        ));

        // Then
        assertThat(deck.getCards()).containsExactly(
                firstCard,
                secondCard,
                thirdCard
        );
    }

    @Test
    public void getCardsByBoxColor_colorExistInList_redCards() throws Exception {
        // Given
        AnkiCard firstCard = new AnkiCard("q1", "r1", BoxColor.RED);
        AnkiCard secondCard = new AnkiCard("q2", "r2", BoxColor.RED);
        AnkiCard thirdCard = new AnkiCard("q3", "r3", BoxColor.GREEN);

        Deck deck = Deck.newDeck(Arrays.asList(
                firstCard,
                secondCard,
                thirdCard
        ));

        // When
        List<AnkiCard> redCards = deck.getCards(BoxColor.RED);

        // Then
        assertThat(redCards).containsExactly(firstCard, secondCard)
                            .extracting(AnkiCard::getBox)
                            .containsOnly(BoxColor.RED);
    }

    @Test
    public void getCardsByBoxColor_colorNotFound_redCards() throws Exception {
        // Given
        AnkiCard firstCard = new AnkiCard("q1", "r1", BoxColor.RED);
        AnkiCard secondCard = new AnkiCard("q2", "r2", BoxColor.RED);
        AnkiCard thirdCard = new AnkiCard("q3", "r3", BoxColor.GREEN);

        Deck deck = Deck.newDeck(Arrays.asList(
                firstCard,
                secondCard,
                thirdCard
        ));

        // When
        List<AnkiCard> redCards = deck.getCards(BoxColor.ORANGE);

        // Then
        assertThat(redCards).isEmpty();
    }
}