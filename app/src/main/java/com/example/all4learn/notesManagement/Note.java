package com.example.all4learn.notesManagement;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

public class Note implements Parcelable {

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };


    private final String id;

    private final String ownerId;

    private final String title;

    private final String text;

    private final String timestamp;


    public Note(String id, String ownerId, String title, String text, String timestamp) {
        this.id = id;
        this.ownerId = ownerId;
        this.title = title;
        this.text = text;
        this.timestamp = timestamp;
    }


    protected Note(Parcel in) {
        id = in.readString();
        ownerId = in.readString();
        title = in.readString();
        text = in.readString();
        timestamp = in.readString();
    }

    public String getId() {
        return id;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public String getDate() {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(ownerId);
        dest.writeString(title);
        dest.writeString(text);
        dest.writeString(timestamp);
    }
}
