package com.weekendesk.anki.domain.service;

import com.weekendesk.anki.domain.model.AnkiCard;
import com.weekendesk.anki.domain.model.BoxColor;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class FileServiceTest {

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    @ClassRule
    public static TemporaryFolder temporaryFolder = new TemporaryFolder();

    private final static String FILE_PATH = "gamefile";

    private final static String RESOURCE_GAME_DIR = "src/test/resources";

    @BeforeClass
    public static void beforeClass() throws IOException {
        temporaryFolder.newFile(FILE_PATH);
    }


    @Test
    public void newFileService_filePathPointingAtNonExistingFile_FileNotFoundException() throws Exception {
        expectedException.expect(FileNotFoundException.class);
        expectedException.expectMessage("Missing game file at path: nonexistingfile");

        // Given
        String filePath = "nonexistingfile";

        // When
        new FileService(filePath);

        // Then
    }

    @Test
    public void newFileService_filePathPointingAtExistingFile_fileServiceCreated() throws Exception {

        // When
        FileService fileService = new FileService(temporaryFolder.getRoot() + "\\" + FILE_PATH);

        // Then
        assertThat(fileService).isNotNull();
    }

    @Test
    public void loadFromFile_fileWithCards_cardsLoaded() throws Exception {
        // Given
        FileService fileService = new FileService(RESOURCE_GAME_DIR + "/game-file-with-cards");

        // When
        List<AnkiCard> cards = fileService.load();

        // Then
        assertThat(cards).hasSize(2);
        assertThat(cards).extracting(AnkiCard::getQuestion).containsExactly("the first card", "the second card");
        assertThat(cards).extracting(AnkiCard::getAnswer).containsExactly("the answer", "another answer");
        assertThat(cards).extracting(AnkiCard::getBox).containsExactly(BoxColor.RED, BoxColor.ORANGE);
    }

    @Test
    public void loadFromFile_fileWithoutCards_RuntimeException() throws Exception {
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("No cards found in game file.");
        // Given
        FileService fileService = new FileService(RESOURCE_GAME_DIR + "/game-file-without-cards");

        // When
        List<AnkiCard> cards = fileService.load();

    }

    @Test
    public void loadFromFile_fileWithWrongCardFormat_RuntimeException() throws Exception {
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("Game file looks corrupted.");

        // Given
        FileService fileService = new FileService(RESOURCE_GAME_DIR + "/game-file-with-format-error-cards");

        // When
        List<AnkiCard> cards = fileService.load();

    }

}