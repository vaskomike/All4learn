package com.example.all4learn.firebaseManagement;

import com.example.all4learn.notesManagement.NotesOperations;

public class FirebaseFireStoreNotesCreate {
    private static FirebaseFireStoreNotesCreate instance;

    private NotesOperations dao;

    public static FirebaseFireStoreNotesCreate getInstance() {
        if (instance == null) {
            instance = new FirebaseFireStoreNotesCreate();
        }
        return instance;
    }

    public NotesOperations getDao() {
        if (dao == null) {
            dao = new FirebaseNotesManagement();
        }
        return dao;
    }
}
