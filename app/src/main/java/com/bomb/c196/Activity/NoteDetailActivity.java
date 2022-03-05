package com.bomb.c196.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bomb.c196.Data.DBHandler;
import com.bomb.c196.Model.Note;
import com.bomb.c196.R;

import java.util.List;

public class NoteDetailActivity extends AppCompatActivity {
    private EditText note;
    private Button editNoteButton, deleteNoteButton, shareNoteButton;
    private DBHandler db = new DBHandler(this);
    private Note noteFromIntent;
    private List<Note> noteList;
    private Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);
        note = findViewById(R.id.note);
        editNoteButton = findViewById(R.id.editNoteButton);
        deleteNoteButton = findViewById(R.id.deleteNoteButton);
        shareNoteButton = findViewById(R.id.shareNoteButton);

        extras = getIntent().getExtras();

        if (extras != null) {
            noteFromIntent = new Note(extras.getInt("id"), extras.getString("note"),
                    extras.getInt("courseId"));

            note.setText(noteFromIntent.getNote());
        }

        shareNoteButton.setOnClickListener(view -> {
            if (!TextUtils.isEmpty(note.getText())) {
                Note shareNote = new Note(noteFromIntent.getId(), note.getText().toString(),
                        noteFromIntent.getCourseId());

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, note.getText().toString());
                intent.putExtra(Intent.EXTRA_SUBJECT, "A message from the C196 App");
                Intent chooserIntent = Intent.createChooser(intent, "Choose an app...");
                startActivity(chooserIntent);
                finish();
            }
        });

        editNoteButton.setOnClickListener(view -> {
            if (!TextUtils.isEmpty(note.getText())) {
                Note newNote = new Note(noteFromIntent.getId(), note.getText().toString(), noteFromIntent.getCourseId());
                db.updateNote(newNote);
                Toast.makeText(this, "Note Changes Saved Successfully", Toast.LENGTH_SHORT).show();
                finish();
                Intent intent = new Intent(this, NoteActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Missing Required Fields", Toast.LENGTH_SHORT).show();
            }
        });

        deleteNoteButton.setOnClickListener(view -> {
            Note noteToDelete = db.getNote(noteFromIntent.getId());
            db.deleteNote(noteToDelete);
            Toast.makeText(this, "Note Deleted Successfully", Toast.LENGTH_SHORT).show();
            finish();
            Intent intent = new Intent(this, NoteActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.termItem:
                intent = new Intent(this, TermActivity.class);
                startActivity(intent);
                return true;
            case R.id.courseItem:
                intent = new Intent(this, CourseActivity.class);
                startActivity(intent);
                return true;
            case R.id.assessmentItem:
                intent  = new Intent(this, AssessmentActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}