package com.nodeflux.numellow.resultspage;

/**
 * Created by cmmuk_000 on 12/22/2016.
 */

public class Child {

    private String Word;
    private String Number;

    public Child() {
    }

    public Child(String number, String word) {
        Number = number;
        Word = word;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getWord() {
        return Word;
    }

    public void setWord(String word) {
        Word = word;
    }
}
