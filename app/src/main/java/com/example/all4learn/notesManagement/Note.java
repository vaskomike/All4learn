package com.example.all4learn.notesManagement;

import java.util.Date;
import java.util.Objects;

public class Note {
    private final String id;

    private final String title;

    private final String text;

    private final Date timestamp;

    public Note(String id, String title, String text, Date timestamp) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.timestamp = timestamp;
    }


    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public Date getDate() {
        return timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return Objects.equals(id, note.id) &&
                Objects.equals(title, note.title) &&
                Objects.equals(text, note.text) &&
                Objects.equals(timestamp, note.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, text, timestamp);
    }
}
