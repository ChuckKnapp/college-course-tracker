package com.bomb.c196.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.bomb.c196.Adapter.MentorAdapter;
import com.bomb.c196.Data.DBHandler;
import com.bomb.c196.Model.Assessment;
import com.bomb.c196.Model.Course;
import com.bomb.c196.Model.Mentor;
import com.bomb.c196.R;

import java.util.ArrayList;
import java.util.List;

public class MentorActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MentorAdapter mentorAdapter;
    private List<Mentor> mentorItems;
    private List<Mentor> mentorList = new ArrayList<>();
    private Course courseFromIntent;
    private Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DBHandler db = new DBHandler(this);
        mentorItems = db.getAllMentors();
        extras = getIntent().getExtras();

        if (extras != null) {
            courseFromIntent = new Course(extras.getInt("courseId"), extras.getString("courseTitle"),
                    extras.getString("courseStartDate"), extras.getString("courseAnticipatedEndDate"),
                    extras.getString("courseStatus"), extras.getInt("courseTermId"));

            for (Mentor m : mentorItems) {
                if (m.getCourseId() == courseFromIntent.getId()) {
                    mentorList.add(m);
                }
            }
        } else {
            mentorList = mentorItems;
        }

        mentorAdapter = new MentorAdapter(this, mentorList);
        recyclerView.setAdapter(mentorAdapter);
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