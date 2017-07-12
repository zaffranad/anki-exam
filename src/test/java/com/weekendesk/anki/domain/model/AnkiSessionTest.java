package com.weekendesk.anki.domain.model;

import com.weekendesk.anki.domain.service.FileService;
import com.weekendesk.anki.domain.service.IOService;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class AnkiSessionTest {

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    @Mock
    private FileService fileService;

    @Mock
    private IOService ioService;

    @Test
    public void newGame_fileServiceLoadCards_notNull() throws Exception {
        // Given
        Mockito.when(fileService.load()).thenReturn(Arrays.asList(
                new AnkiCard("q", "r", BoxColor.RED),
                new AnkiCard("q", "r", BoxColor.GREEN)
        ));

        // When
        AnkiSession ankiSession = AnkiSession.newSession(fileService, ioService);

        // Then
        assertThat(ankiSession).isNotNull();
    }

    @Test
    public void newGame_fileServiceLoadsNoCard_exception() throws Exception {

        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("No cards found for deck");

        // Given
        Mockito.when(fileService.load()).thenReturn(Collections.emptyList());

        // When
        AnkiSession.newSession(fileService, ioService);

    }

    @Test
    public void gameIsOver_notAllCardsAreGreen_false() throws Exception {
        // Given
        FileService fileService = Mockito.mock(FileService.class);
        Mockito.when(fileService.load()).thenReturn(Arrays.asList(
                new AnkiCard("q", "r", BoxColor.RED),
                new AnkiCard("q", "r", BoxColor.GREEN)
        ));

        // When
        AnkiSession ankiSession = AnkiSession.newSession(fileService,ioService);

        // Then
        assertThat(ankiSession.isOver()).isFalse();

    }

    @Test
    public void gameIsOver_allCardsAreGreen_true() throws Exception {
        // Given
        FileService fileService = Mockito.mock(FileService.class);
        Mockito.when(fileService.load()).thenReturn(Arrays.asList(
                new AnkiCard("q", "r", BoxColor.GREEN),
                new AnkiCard("q", "r", BoxColor.GREEN)
        ));

        // When
        AnkiSession ankiSession = AnkiSession.newSession(fileService, ioService);

        // Then
        assertThat(ankiSession.isOver()).isTrue();

    }

    @Test
    public void slideBeforeNextRound_oneCardOfEachColor_twoRedAndOneOrange() throws Exception {
        // Given
        AnkiCard firstCard = new AnkiCard("q1", "r1", BoxColor.RED);
        AnkiCard secondCard = new AnkiCard("q2", "r2", BoxColor.ORANGE);
        AnkiCard thirdCard = new AnkiCard("q3", "r3", BoxColor.GREEN);

        Mockito.when(fileService.load()).thenReturn(Arrays.asList(
                firstCard,
                secondCard,
                thirdCard
        ));

        // When
        AnkiSession ankiSession = AnkiSession.newSession(fileService, ioService);
        ankiSession.slideBeforeNextRound();

        // Then
        assertThat(ankiSession.getDeck().getCards().get(0).getBox()).isEqualTo(BoxColor.RED);
        assertThat(ankiSession.getDeck().getCards().get(1).getBox()).isEqualTo(BoxColor.RED);
        assertThat(ankiSession.getDeck().getCards().get(2).getBox()).isEqualTo(BoxColor.ORANGE);

    }

}