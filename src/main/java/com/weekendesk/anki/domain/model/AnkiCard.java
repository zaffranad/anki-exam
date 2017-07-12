package com.weekendesk.anki.domain.model;


import static com.weekendesk.anki.domain.model.BoxColor.RED;

public class AnkiCard {

    private final String question;
    private final String answer;
    private BoxColor box;

    public AnkiCard(String question, String answer, final BoxColor box) {
        this.box = box;
        this.question = question;
        this.answer = answer;
    }

    public static AnkiCard newAnkiCard(String question, String answer){
        return new AnkiCard(question, answer, RED);
    }

    public static AnkiCard newAnkiCardWithBox(String question, String answer, BoxColor box){
        return new AnkiCard(question, answer, box);
    }

    public BoxColor getBox() {
        return box;
    }

    public String getAnswer() {
        return answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setBox(BoxColor box) {
        this.box = box;
    }

    @Override
    public String toString() {
        return "AnkiCard{" +
                "question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                ", box=" + box +
                '}';
    }
}
