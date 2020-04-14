package com.example.all4learn.notesManagement;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.all4learn.R;

public class NotesAdapter extends ListAdapter<Note, NotesAdapter.NoteViewHolder> {
    private static final DiffUtil.ItemCallback<Note> COMPARATOR = new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.equals(newItem);
        }
    };

    private final LayoutInflater layoutInflater;

    private final OnItemClickListener<Note> noteClickListener;

    public NotesAdapter(LayoutInflater layoutInflater, OnItemClickListener<Note> noteClickListener) {
        super(COMPARATOR);
        this.layoutInflater = layoutInflater;
        this.noteClickListener = noteClickListener;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteViewHolder(
                layoutInflater.inflate(R.layout.note_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvTitle;

        private final TextView tvDescription;

        private final TextView tvDate;

        private Note binded;

        private NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.title);
            tvDescription = itemView.findViewById(R.id.text);
            tvDate = itemView.findViewById(R.id.dateItem);
            itemView.setOnClickListener(v -> noteClickListener.onItemClicked(binded));
        }

        private void bind(Note note) {
            binded = note;
            tvTitle.setText(note.getTitle());
            tvDescription.setText(note.getText());
            tvDate.setText(note.getDate());

        }
    }
}
