package com.weekendesk.anki.domain.model;

import java.util.Optional;

public enum BoxColor {

    RED,
    ORANGE,
    GREEN;

    public static Optional<BoxColor> fromString(String value) {
        switch (value) {
            case "RED":
                return Optional.of(RED);
            case "ORANGE":
                return Optional.of(ORANGE);
            case "GREEN":
                return Optional.of(GREEN);
            default:
                return Optional.empty();
        }
    }


}
