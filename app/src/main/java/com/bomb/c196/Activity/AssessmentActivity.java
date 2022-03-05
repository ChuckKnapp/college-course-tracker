package com.bomb.c196.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.bomb.c196.Adapter.AssessmentAdapter;
import com.bomb.c196.Data.DBHandler;
import com.bomb.c196.Model.Assessment;
import com.bomb.c196.Model.Course;
import com.bomb.c196.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class AssessmentActivity extends AppCompatActivity {
    private RecyclerView assessmentRecyclerView;
    private RecyclerView.Adapter assessmentAdapter;
    private List<Assessment> assessmentItems;
    private List<Assessment> assessmentList = new ArrayList<>();
    private FloatingActionButton assessmentFab;
    private Course courseFromIntent;
    private Bundle extras;
    private DBHandler db = new DBHandler(this);
    private List<Course> courseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment);

        assessmentRecyclerView = findViewById(R.id.assessmentRecyclerViewId);
        assessmentRecyclerView.setHasFixedSize(true);
        assessmentRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        DBHandler db = new DBHandler(this);
        courseList = db.getAllCourses();
        assessmentItems = db.getAllAssessments();
        extras = getIntent().getExtras();

        if (extras != null) {
            courseFromIntent = new Course(extras.getInt("courseId"), extras.getString("courseTitle"),
                    extras.getString("courseStartDate"), extras.getString("courseAnticipatedEndDate"),
                    extras.getString("courseStatus"), extras.getInt("courseTermId"));

            for (Assessment a : assessmentItems) {
                if (a.getCourseId() == courseFromIntent.getId()) {
                    assessmentList.add(a);
                }
            }
        } else {
            assessmentList = assessmentItems;
        }

        assessmentAdapter = new AssessmentAdapter(this, assessmentList);
        assessmentRecyclerView.setAdapter(assessmentAdapter);

        assessmentFab = findViewById(R.id.assessmentFabId);
        assessmentFab.setOnClickListener(view -> {
            if (courseList.size() > 0) {
                Intent intent = new Intent(this, AssessmentDetailActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "You need to enter at least one Course before you can enter an assessment",
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