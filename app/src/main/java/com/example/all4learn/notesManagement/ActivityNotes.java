package com.example.all4learn.notesManagement;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.all4learn.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;


public class ActivityNotes extends AppCompatActivity implements ActivityLoadNotes.Listener, OnItemClickListener<Note> {

    //todo load notes on this screen

    private static final int REQUEST_CODE_ADD_NOTE = 1;

    private ActivityLoadNotes loadNotes = new ActivityLoadNotes(this);



    private NotesAdapter adapter;


    public static Intent createIntent(Context context) {
        return new Intent(context, ActivityNotes.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        adapter = new NotesAdapter(getLayoutInflater(), this);


        RecyclerView recyclerView = findViewById(R.id.notes);
        recyclerView.setAdapter(adapter);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);


        findViewById(R.id.addNote).setOnClickListener(v -> {
            Intent intent = new Intent(this, ActivityAddNote.class);
            startActivity(intent);
        });
    }

    @Override
    public void onNotesLoaded(List<Note> notes) {
        adapter.submitList(notes);
    }

    private FirebaseFirestore fireStore = FirebaseFirestore.getInstance();


    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    private String getUid() {
        return firebaseAuth.getCurrentUser().getUid();
    }

    @Override
    public void onItemClicked(Note item) {
        startActivity(EditNoteActivity.createIntent(this, item));
    }

}
