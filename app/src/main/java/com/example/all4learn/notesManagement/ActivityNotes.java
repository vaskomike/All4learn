package com.example.all4learn.notesManagement;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.all4learn.R;
import com.example.all4learn.firebaseManagement.FireStoreNoteMapper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import static com.example.all4learn.firebaseManagement.FireStoreNoteMapper.DATE;
import static com.example.all4learn.firebaseManagement.FireStoreNoteMapper.TEXT;
import static com.example.all4learn.firebaseManagement.FireStoreNoteMapper.TITLE;


public class ActivityNotes extends AppCompatActivity implements ActivityLoadNotes.Listener, OnItemClickListener<Note> {


    private static final int REQUEST_CODE_ADD_NOTE = 1;

    private ActivityLoadNotes loadNotes = new ActivityLoadNotes(this);

    private SwipeRefreshLayout refreshLayout;

    private NotesAdapter adapter;

    NotesAdapter.NoteViewHolder viewHolder;

    public static Intent createIntent(Context context) {
        return new Intent(context, ActivityNotes.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        adapter = new NotesAdapter(getLayoutInflater(), this);

        refreshLayout = findViewById(R.id.swipeRefresh);
        refreshLayout.setRefreshing(true);
        refreshLayout.setOnRefreshListener(loadNotes::loadNotes);

        RecyclerView recyclerView = findViewById(R.id.notes);
        recyclerView.setAdapter(adapter);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);



        findViewById(R.id.addNote).setOnClickListener(v -> {
            Intent intent = new Intent(this, ActivityAddNote.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_ADD_NOTE) {
            if (resultCode == Activity.RESULT_OK) {
                loadNotes.loadNotes();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onNotesLoaded(List<Note> notes) {
        refreshLayout.setRefreshing(false);
        adapter.submitList(notes);
    }

    private FirebaseFirestore fireStore = FirebaseFirestore.getInstance();

    private TextView dateNote;

    private TextInputEditText titleInputEditText, textInputEditText;

    @Override
    public void onItemClicked(Note item) {
        setContentView(R.layout.activity_open_note2);

        titleInputEditText = findViewById(R.id.title);
        textInputEditText = findViewById(R.id.text);
        dateNote = findViewById(R.id.dateNote);
        loadNote(item);

    }

    public void loadNote(Note note) {
        fireStore.collection(FireStoreNoteMapper.COLLECTION).document(note.getId()).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {

                            titleInputEditText.setText(documentSnapshot.getString(TITLE));
                            textInputEditText.setText(documentSnapshot.getString(TEXT));
                            dateNote.setText(documentSnapshot.getString(DATE));

                        } else
                            Toast.makeText(ActivityNotes.this, "Document does not exist", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ActivityNotes.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
