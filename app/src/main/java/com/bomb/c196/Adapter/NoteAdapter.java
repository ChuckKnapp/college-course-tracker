package com.bomb.c196.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bomb.c196.Activity.NoteDetailActivity;
import com.bomb.c196.Model.Note;
import com.bomb.c196.R;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    private Context context;
    private List<Note> noteItems;
    public static final String NOTE_ID = "note_id";

    public NoteAdapter(Context context, List<Note> noteItems) {
        this.context = context;
        this.noteItems = noteItems;
    }

    @Override
    public NoteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NoteAdapter.ViewHolder holder, int position) {
        Note note = noteItems.get(position);
        holder.title.setText(note.getNote());
    }

    @Override
    public int getItemCount() { return noteItems.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title;


        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            title = itemView.findViewById(R.id.title);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Note note = noteItems.get(position);
            Intent intent = new Intent(context, NoteDetailActivity.class);
            intent.putExtra("id", note.getId());
            intent.putExtra("note", note.getNote());
            intent.putExtra("courseId", note.getCourseId());

            context.startActivity(intent);
        }

//        @Override
//        public void onClick(View view) {
//            int position = getAdapterPosition();
//            Note note = noteItems.get(position);
//            Intent intent = new Intent(Intent.ACTION_SEND);
//            intent.setType("text/plain");
//            intent.putExtra(Intent.EXTRA_TEXT, note.getNote());
//            intent.putExtra(Intent.EXTRA_SUBJECT, "A message from the C196 App");
//            Intent chooserIntent = Intent.createChooser(intent, "Choose an app...");
//            context.startActivity(chooserIntent);
//        }
    }
}
