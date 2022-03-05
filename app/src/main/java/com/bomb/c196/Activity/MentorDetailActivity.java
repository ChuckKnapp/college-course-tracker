package com.bomb.c196.Activity;

import androidx.appcompat.app.AppCompatActivity;

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
import com.bomb.c196.Model.Mentor;
import com.bomb.c196.R;

import java.util.ArrayList;
import java.util.List;

public class MentorDetailActivity extends AppCompatActivity {
    private EditText mentorName, mentorPhone, mentorEmail;
    private Button editMentorButton, deleteMentorButton;
    private Bundle extras;
    private DBHandler db = new DBHandler(this);
    private Mentor mentorFromIntent;
    List<Mentor> mentorList = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_detail);
        mentorName = findViewById(R.id.mentorName);
        mentorPhone = findViewById(R.id.mentorPhone);
        mentorEmail = findViewById(R.id.mentorEmail);
        editMentorButton = findViewById(R.id.editMentorButton);
        deleteMentorButton = findViewById(R.id.deleteMentorButton);

        extras = getIntent().getExtras();

        if (extras != null) {
            mentorFromIntent = new Mentor(extras.getInt("id"), extras.getString("name"),
                    extras.getString("phone"), extras.getString("email"), extras.getInt("courseId"));

            mentorName.setText(mentorFromIntent.getName());
            mentorPhone.setText(mentorFromIntent.getPhone());
            mentorEmail.setText(mentorFromIntent.getEmail());
        }

        editMentorButton.setOnClickListener(view -> {
            if (!TextUtils.isEmpty(mentorName.getText()) && !TextUtils.isEmpty(mentorPhone.getText())
                    && !TextUtils.isEmpty(mentorEmail.getText())) {
                Mentor mentor = new Mentor(mentorFromIntent.getId(), mentorName.getText().toString(),
                        mentorPhone.getText().toString(), mentorEmail.getText().toString(),
                        mentorFromIntent.getCourseId());
                db.updateMentor(mentor);
                finish();
                Intent intent = new Intent(this, MentorActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Missing Required Fields", Toast.LENGTH_SHORT).show();
            }
        });

        deleteMentorButton.setOnClickListener(view -> {
            Mentor mentorToDelete = db.getMentor(mentorFromIntent.getId());
            db.deleteMentor(mentorToDelete);
            finish();
            Intent intent = new Intent(this, MentorActivity.class);
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