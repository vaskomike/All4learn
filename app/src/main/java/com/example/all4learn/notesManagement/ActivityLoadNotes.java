package com.example.all4learn.notesManagement;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

import com.example.all4learn.firebaseManagement.FirebaseFireStoreNotesCreate;
import com.google.firebase.firestore.ListenerRegistration;

import java.lang.ref.WeakReference;
import java.util.List;

public class ActivityLoadNotes extends BasePresenter<ActivityLoadNotes.Listener> implements NotesOperations.GetNotesListener {

    private final NotesOperations notesDao = FirebaseFireStoreNotesCreate.getInstance().getDao();

    private ListenerRegistration registration;

    public ActivityLoadNotes(@NonNull Listener listener) {
        super(listener);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void loadNotes() {
        registration = notesDao.listenNotes(new WeakReference<>(this));
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void stopLoadNotes() {
        registration = notesDao.listenNotes(new WeakReference<>(this));
    }

    @Override
    public void onNotesLoaded(List<Note> notes) {
        postOnMainThread(listener -> listener.onNotesLoaded(notes));
    }

    @Override
    public void onNotesLoadFailed() {

    }

    public interface Listener extends LifecycleOwner {

        void onNotesLoaded(List<Note> notes);
    }
}


