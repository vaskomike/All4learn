package com.example.all4learn.firebaseManagement;

import com.example.all4learn.notesManagement.Note;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.HashMap;
import java.util.Map;

public class FireStoreNoteMapper {
    public static final String COLLECTION = "notes";

    public static final String TITLE = "title";

    public static final String TEXT = "text";

    public static final String OWNER_ID = "ownerId";

    public static final String DATE = "date";

    public static Map<String, Object> newNote(String ownerId, String title, String text, String date) {
        Map<String, Object> map = new HashMap<>();
        map.put(OWNER_ID, ownerId);
        map.put(TITLE, title);
        map.put(TEXT, text);
        map.put(DATE, date);
        return map;
    }

    public static Note fromDocument(DocumentSnapshot document) {
        return new Note(
                document.getId(),
                document.getString(OWNER_ID),
                document.getString(TITLE),
                document.getString(TEXT),
                document.getString(DATE)
        );
    }
}

