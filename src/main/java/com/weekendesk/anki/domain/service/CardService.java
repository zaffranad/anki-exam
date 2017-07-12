package com.weekendesk.anki.domain.service;

import com.weekendesk.anki.domain.model.AnkiCard;

import java.util.List;

public interface CardService {

    List<AnkiCard> load();

}
