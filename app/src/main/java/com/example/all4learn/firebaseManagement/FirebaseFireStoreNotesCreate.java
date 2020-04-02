package com.example.all4learn.firebaseManagement;

public class FirebaseFireStoreNotesCreate {
    private static FirebaseFireStoreNotesCreate instance;

    private FirebaseNotesManagement dao;

    public static FirebaseFireStoreNotesCreate getInstance() {
        if (instance == null) {
            instance = new FirebaseFireStoreNotesCreate();
        }
        return instance;
    }

    //todo what the fuck
    public FirebaseNotesManagement getDao() {
        if (dao == null) {
            dao = new FirebaseNotesManagement();
        }
        return dao;
    }
}
