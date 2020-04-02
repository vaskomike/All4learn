package com.example.all4learn.firebaseManagement;

public class FirebaseFireStoreNotesCreate {
    private static FirebaseFireStoreNotesCreate instance;

    private FirebaseFireStoreNotesCreate dao;

    public static FirebaseFireStoreNotesCreate getInstance() {
        if (instance == null) {
            instance = new FirebaseFireStoreNotesCreate();
        }
        return instance;
    }

    //todo what the fuck
    public FirebaseFireStoreNotesCreate getDao() {
        if (dao == null) {
            dao = new FirebaseFireStoreNotesCreate();
        }
        return dao;
    }
}
