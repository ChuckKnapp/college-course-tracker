package com.bomb.c196.Activity;

import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.bomb.c196.Adapter.NoteAdapter;
import com.bomb.c196.Data.DBHandler;
import com.bomb.c196.Model.Assessment;
import com.bomb.c196.Model.Course;
import com.bomb.c196.Model.Note;
import com.bomb.c196.R;

import java.util.ArrayList;
import java.util.List;

public class NoteActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private NoteAdapter noteAdapter;
    private List<Note> noteItems;
    private List<Note> notesFromCourse = new ArrayList<>();
    private Course courseFromIntent;
    private Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DBHandler db = new DBHandler(this);
        noteItems = db.getAllNotes();
        extras = getIntent().getExtras();

        if (extras != null) {
            courseFromIntent = new Course(extras.getInt("courseId"), extras.getString("courseTitle"),
                    extras.getString("courseStartDate"), extras.getString("courseAnticipatedEndDate"),
                    extras.getString("courseStatus"), extras.getInt("courseTermId"));

            for (Note n : noteItems) {
                if (n.getCourseId() == courseFromIntent.getId()) {
                    notesFromCourse.add(n);
                }
            }
        } else notesFromCourse = noteItems;

        noteAdapter = new NoteAdapter(this, notesFromCourse);
        recyclerView.setAdapter(noteAdapter);
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