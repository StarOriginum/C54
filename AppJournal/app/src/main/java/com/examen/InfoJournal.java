package com.examen;

import java.io.Serializable;

public class InfoJournal implements Serializable {

    private String date;
    private String message;

    public InfoJournal(String date, String message) {
        this.date = date;
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
