package com.weekendesk.anki.domain.service;

import com.weekendesk.anki.domain.model.AnkiCard;

import java.util.List;

public class InitCardService implements CardService {

    private FileService fileService;

    public InitCardService(FileService fileService) {
        this.fileService = fileService;
    }

    @Override
    public List<AnkiCard> load() {
        return fileService.load();
    }
}
