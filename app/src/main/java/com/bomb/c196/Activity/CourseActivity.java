package com.bomb.c196.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Spinner;
import android.widget.Toast;

import com.bomb.c196.Adapter.CourseAdapter;
import com.bomb.c196.Data.DBHandler;
import com.bomb.c196.Model.Course;
import com.bomb.c196.Model.Term;
import com.bomb.c196.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CourseActivity extends AppCompatActivity {
    private RecyclerView courseRecyclerView;
    private RecyclerView.Adapter courseAdapter;
    private List<Course> courseItems;
    private List<Course> courseList = new ArrayList<>();
    private FloatingActionButton courseFab;
    private Term termFromIntent;
    private Bundle extras;
    private List<Term> termList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        courseRecyclerView = findViewById(R.id.courseRecyclerViewId);
        courseRecyclerView.setHasFixedSize(true);
        courseRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        DBHandler db = new DBHandler(this);
        termList = db.getAllTerms();
        courseItems = db.getAllCourses();
        extras = getIntent().getExtras();

        if (extras != null) {
            termFromIntent = new Term(extras.getInt("termId"), extras.getString("termTitle"),
                    extras.getString("termStartDate"), extras.getString("termEndDate"));

            for (Course c : courseItems) {
                if (c.getTermId() == termFromIntent.getId()) {
                    courseList.add(c);
                }
            }
        } else {
            courseList = courseItems;
        }

        courseAdapter = new CourseAdapter(this, courseList);
        courseRecyclerView.setAdapter(courseAdapter);

        courseFab = findViewById(R.id.courseFabId);
        courseFab.setOnClickListener(view -> {
            if (termList.size() > 0) {
                Intent intent = new Intent(this, CourseDetailActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "You need to enter at least one Term before you can enter a course",
                        Toast.LENGTH_LONG).show();
            }
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