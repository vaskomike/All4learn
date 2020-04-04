package com.example.all4learn.firebaseManagement;

import android.content.Context;
import android.widget.Toast;

import com.example.all4learn.notesManagement.Note;
import com.example.all4learn.notesManagement.NotesOperations;
import com.example.all4learn.notesManagement.SchedulersHandler;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FirebaseNotesManagement implements NotesOperations {
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    private DocumentReference loadNote = firebaseFirestore.collection("notes").document();

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    public void getNotes(WeakReference<NotesOperations.GetNotesListener> listenerRef) {
        Task<QuerySnapshot> snapshotTask = getUserNotesQuery().get();

        snapshotTask.addOnSuccessListener(SchedulersHandler.getIo(), queryDocumentSnapshots -> {
            List<Note> notes = parseNotes(queryDocumentSnapshots.getDocuments());
            NotesOperations.GetNotesListener listener = listenerRef.get();
            if (listener != null) {
                listener.onNotesLoaded(notes);
            }
        });

        snapshotTask.addOnFailureListener(e -> {
            NotesOperations.GetNotesListener listener = listenerRef.get();
            if (listener != null) {
                listener.onNotesLoadFailed();
            }
        });
    }

    @Override
    public ListenerRegistration listenNotes(WeakReference<NotesOperations.GetNotesListener> listenerRef) {
        return getUserNotesQuery()
                .addSnapshotListener(SchedulersHandler.getIo(), (queryDocumentSnapshots, e) -> {
                    if (queryDocumentSnapshots != null) {
                        List<Note> notes = parseNotes(queryDocumentSnapshots.getDocuments());
                        NotesOperations.GetNotesListener listener = listenerRef.get();
                        if (listener != null) {
                            listener.onNotesLoaded(notes);
                        }
                    } else if (e != null) {
                        NotesOperations.GetNotesListener listener = listenerRef.get();
                        if (listener != null) {
                            listener.onNotesLoadFailed();
                        }
                    }
                });
    }

    @Override
    public void deleteNote(Note note) {
        firebaseFirestore.collection(FireStoreNoteMapper.COLLECTION).document(note.getId()).delete();
    }


    private Query getUserNotesQuery() {
        return firebaseFirestore.collection(FireStoreNoteMapper.COLLECTION)
                .whereEqualTo(FireStoreNoteMapper.OWNER_ID, getUid());
    }


    private List<Note> parseNotes(List<DocumentSnapshot> documents) {
        List<Note> notes = new ArrayList<>(documents.size());
        for (DocumentSnapshot snapshot : documents) {
            notes.add(FireStoreNoteMapper.fromDocument(snapshot));
        }
        return notes;
    }


    private String getUid() {
        return firebaseAuth.getCurrentUser().getUid();
    }

//    @Override
//    public Note addNote(String title, String text, Date date) {
//
//        DocumentReference reference = firebaseFirestore.collection(FireStoreNoteMapper.COLLECTION).document();
//        reference.set(FireStoreNoteMapper.newNote(getUid(), title, text, date));
//        return new Note(reference.getId(), title, text, date);
//    }
}
