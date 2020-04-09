package com.example.all4learn.notesManagement;

import com.google.firebase.firestore.ListenerRegistration;

import java.lang.ref.WeakReference;
import java.util.List;

public interface NotesOperations {

    void getNotes(WeakReference<GetNotesListener> listener);

    ListenerRegistration listenNotes(WeakReference<GetNotesListener> listener);

    void deleteNote(Note note);


    interface GetNotesListener {

        void onNotesLoaded(List<Note> notes);

        void onNotesLoadFailed();
    }
}
