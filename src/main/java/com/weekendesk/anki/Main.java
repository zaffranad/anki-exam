package com.weekendesk.anki;

import com.weekendesk.anki.domain.model.AnkiSession;
import com.weekendesk.anki.domain.service.FileService;
import com.weekendesk.anki.domain.service.IOService;

import java.io.FileNotFoundException;

public final class Main {

    public static void main(String[] args) throws FileNotFoundException {

        checkArgs(args);

        final AnkiSession ankiSession = AnkiSession.newSession(
                new FileService(args[0]),
                new IOService()
        );

        ankiSession.displayRedCards();

        if(!ankiSession.isOver()){
            ankiSession.slideBeforeNextRound();
            ankiSession.dump();
            System.out.println("goodbye and see you tomorrow!");
        }else{
            System.out.println("Congrats!");
        }
    }

    private static void checkArgs(String[] args){
        if(args.length == 0){
            System.err.println("Missing file argument.");
            System.exit(1);
        }

        if(!FileService.fileExist(args[0])){
            System.err.println("No file at path " + args[0]);
            System.exit(1);
        }
    }
}
