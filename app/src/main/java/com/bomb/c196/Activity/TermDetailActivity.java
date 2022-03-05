package com.bomb.c196.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bomb.c196.Data.DBHandler;
import com.bomb.c196.Model.Course;
import com.bomb.c196.Model.Term;
import com.bomb.c196.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TermDetailActivity extends AppCompatActivity {
    private Bundle extras;
    private EditText title, startDate, endDate;
    private Button saveTermButton, editTermButton, deleteTermButton, viewTermCourses;
    private DatePickerDialog.OnDateSetListener startDateCal;
    private DatePickerDialog.OnDateSetListener endDateCal;
    final Calendar myCalendarStart = Calendar.getInstance();
    final Calendar myCalendarEnd = Calendar.getInstance();
    private DBHandler db = new DBHandler(this);
    private Term termFromIntent;
    private long termId = 0;
    private Boolean isEdit = false;
    private String myFormat = "MM/dd/yy";
    private SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
    private List<Course> courseItems;
    private List<Course> courseList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_detail);
        saveTermButton = findViewById(R.id.saveTermButton);
        editTermButton = findViewById(R.id.editTermButton);
        deleteTermButton = findViewById(R.id.deleteTermButton);
        viewTermCourses = findViewById(R.id.viewTermCourses);
        title = findViewById(R.id.title);
        startDate = findViewById(R.id.startDate);
        endDate = findViewById(R.id.endDate);

        extras = getIntent().getExtras();

        if (extras != null) {
            termFromIntent = new Term(extras.getInt("id"), extras.getString("title"),
                    extras.getString("startDate"), extras.getString("endDate"));

            title.setText(termFromIntent.getTitle());
            startDate.setText(termFromIntent.getStartDate());
            endDate.setText(termFromIntent.getEndDate());

            isEdit = true;
        }

        saveTermButton.setOnClickListener(view -> {
            if (!TextUtils.isEmpty(title.getText()) && !TextUtils.isEmpty(startDate.getText())
                && !TextUtils.isEmpty(endDate.getText())) {
                Term term = new Term(title.getText().toString(), startDate.getText().toString(),
                        endDate.getText().toString());
                db.addTerm(term);

                finish();
                Intent intent = new Intent(this, TermActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Missing Required Fields", Toast.LENGTH_SHORT).show();
            }
        });

        editTermButton.setOnClickListener(view -> {
            if (!TextUtils.isEmpty(title.getText()) && !TextUtils.isEmpty(startDate.getText())
                    && !TextUtils.isEmpty(endDate.getText())) {
                Term term = new Term(termFromIntent.getId(), title.getText().toString(), startDate.getText().toString(),
                        endDate.getText().toString());
                db.updateTerm(term);

                finish();
                Intent intent = new Intent(this, TermActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Missing Required Fields", Toast.LENGTH_SHORT).show();
            }
        });

        deleteTermButton.setOnClickListener(view -> {
            courseItems = db.getAllCourses();
            for (Course c : courseItems) {
                if (c.getTermId() == termFromIntent.getId()) {
                    courseList.add(c);
                }
            }

            if (courseList.size() == 0) {
                Term termToDelete = db.getTerm(termFromIntent.getId());
                db.deleteTerm(termToDelete);

                finish();
                Intent intent = new Intent(this, TermActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Cannot delete this Term. Courses are assigned to it",
                        Toast.LENGTH_LONG).show();
            }
        });

        viewTermCourses.setOnClickListener(view -> {
            Intent intent = new Intent(this, CourseActivity.class);
            intent.putExtra("termId", termFromIntent.getId());
            intent.putExtra("termTitle", termFromIntent.getTitle());
            intent.putExtra("termStartDate", termFromIntent.getStartDate());
            intent.putExtra("termEndDate", termFromIntent.getEndDate());
            startActivity(intent);
        });

        if (isEdit) {
            saveTermButton.setVisibility(View.GONE);
        } else {
            editTermButton.setVisibility(View.GONE);
            deleteTermButton.setVisibility(View.GONE);
            viewTermCourses.setVisibility(View.GONE);
        }

        startDateCal = (view, year, month, day) -> {
            myCalendarStart.set(Calendar.YEAR, year);
            myCalendarStart.set(Calendar.MONTH, month);
            myCalendarStart.set(Calendar.DAY_OF_MONTH, day);
            startDate.setText(sdf.format(myCalendarStart.getTime()));
        };

        startDate.setOnClickListener(view -> new DatePickerDialog(TermDetailActivity.this, startDateCal, myCalendarStart
                .get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH),
                myCalendarStart.get(Calendar.DAY_OF_MONTH)).show());

        endDateCal = (view, year, month, day) -> {
            myCalendarEnd.set(Calendar.YEAR, year);
            myCalendarEnd.set(Calendar.MONTH, month);
            myCalendarEnd.set(Calendar.DAY_OF_MONTH, day);
            endDate.setText(sdf.format(myCalendarEnd.getTime()));
        };

        endDate.setOnClickListener(view -> new DatePickerDialog(TermDetailActivity.this, endDateCal, myCalendarEnd
                .get(Calendar.YEAR), myCalendarEnd.get(Calendar.MONTH),
                myCalendarEnd.get(Calendar.DAY_OF_MONTH)).show());
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