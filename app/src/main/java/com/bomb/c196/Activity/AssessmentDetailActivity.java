package com.bomb.c196.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.bomb.c196.Adapter.AssessmentAdapter;
import com.bomb.c196.Data.DBHandler;
import com.bomb.c196.Model.Assessment;
import com.bomb.c196.Model.Course;
import com.bomb.c196.Model.Note;
import com.bomb.c196.R;
import com.bomb.c196.Utility.MyReceiver;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AssessmentDetailActivity extends AppCompatActivity {
    private EditText title, dueDate, goalDate, addNote;
    private Switch typeSwitch;
    private Spinner spinner;
    private boolean type;
    private CheckBox dueDateCheckbox, goalDateCheckbox;
    private Button saveNoteButton, viewNotesButton, saveAssessmentButton, editAssessmentButton, deleteAssessmentButton;
    DatePickerDialog.OnDateSetListener dueDateCal;
    DatePickerDialog.OnDateSetListener goalDateCal;
    final Calendar myCalendarDue = Calendar.getInstance();
    final Calendar myCalendarGoal = Calendar.getInstance();
    private DBHandler db = new DBHandler(this);
    private Assessment assessmentFromIntent;
    private int assessmentId = 0;
    private Boolean isEdit = false;
    private String myFormat = "MM/dd/yy";
    private SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
    private Bundle extras;
    private List<Course> courseList;
    private List<String> courseNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_detail);
        saveAssessmentButton = findViewById(R.id.saveAssessmentButton);
        editAssessmentButton = findViewById(R.id.editAssessmentButton);
        deleteAssessmentButton = findViewById(R.id.deleteAssessmentButton);
        title = findViewById(R.id.title);
        dueDate = findViewById(R.id.dueDate);
        goalDate = findViewById(R.id.goalDate);
        addNote = findViewById(R.id.addNote);
        saveNoteButton = findViewById(R.id.saveNoteButton);
        viewNotesButton = findViewById(R.id.viewNotesButton);
        dueDateCheckbox = findViewById(R.id.dueDateCheckbox);
        goalDateCheckbox = findViewById(R.id.goalDateCheckbox);
        typeSwitch = findViewById(R.id.typeSwitch);
        spinner = findViewById(R.id.spinner);

        courseList = db.getAllCourses();
        for (Course c : courseList) {
            courseNames.add(c.getTitle());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_layout,
                R.id.spinner_txt, courseNames);
        spinner.setAdapter(adapter);

        extras = getIntent().getExtras();

        if (extras != null) {
            assessmentFromIntent = new Assessment(extras.getInt("id"),
                    extras.getString("title"), extras.getString("type"), extras.getString("dueDate"),
                    extras.getString("goalDate"), extras.getInt("courseId"));

            title.setText(assessmentFromIntent.getTitle());
            dueDate.setText(assessmentFromIntent.getDueDate());
            goalDate.setText(assessmentFromIntent.getGoalDate());
            Course c = db.getCourse(assessmentFromIntent.getCourseId());
            spinner.setSelection(adapter.getPosition(c.getTitle()));
            if (assessmentFromIntent.getType().equals("Performance")) {
                typeSwitch.setChecked(true);
            }

            if (extras.getBoolean("dueAlertChecked")) {
                dueDateCheckbox.setChecked(true);
            }

            if (extras.getBoolean("goalAlertChecked")) {
                goalDateCheckbox.setChecked(true);
            }

            isEdit = true;
        }

//        saveNoteButton.setOnClickListener(view -> {
//            if (!TextUtils.isEmpty(addNote.getText())) {
//                Note note = new Note(addNote.getText().toString(), assessmentFromIntent.getCourseId());
//                db.addNote(note);
//                addNote.getText().clear();
//            }
//        });
//
//        viewNotesButton.setOnClickListener(view -> {
//            Intent intent = new Intent(this, NoteActivity.class);
//            intent.putExtra("assessmentId", assessmentFromIntent.getId());
//            intent.putExtra("assessmentTitle", assessmentFromIntent.getTitle());
//            intent.putExtra("assessmentType", assessmentFromIntent.getType());
//            intent.putExtra("assessmentDueDate", assessmentFromIntent.getDueDate());
//            intent.putExtra("assessmentGoalDate", assessmentFromIntent.getGoalDate());
//            intent.putExtra("assessmentCourseId", assessmentFromIntent.getCourseId());
//            startActivity(intent);
//        });

        editAssessmentButton.setOnClickListener(view -> {
            String courseTitle = String.valueOf(spinner.getSelectedItem());
            int id = 1;
            for (Course c : courseList) {
                if (c.getTitle().equals(courseTitle)) {
                    id = c.getId();
                }
            }
            type = typeSwitch.isChecked();
            String assessmentType;
            if (type) {
                assessmentType = "Performance";
            } else {
                assessmentType = "Objective";
            }
            if (!TextUtils.isEmpty(title.getText()) && !TextUtils.isEmpty(dueDate.getText())
                    && !TextUtils.isEmpty(goalDate.getText())) {
                Assessment assessment = new Assessment(assessmentFromIntent.getId(), title.getText().toString(),
                        assessmentType, dueDate.getText().toString(),
                        goalDate.getText().toString(), id);

                db.updateAssessment(assessment);

                if (dueDateCheckbox.isChecked()) {
                    setAssessmentAlarm(assessment, dueDate, "Assessment Due today");
                }
                if (goalDateCheckbox.isChecked()) {
                    setAssessmentAlarm(assessment, goalDate, "Assessment Goal Date is today");
                }
                finish();
                Intent intent = new Intent(this, AssessmentActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Missing Required Fields", Toast.LENGTH_SHORT).show();
            }
        });

        deleteAssessmentButton.setOnClickListener(view -> {
            Assessment assessmentToDelete = db.getAssessment(assessmentFromIntent.getId());
            db.deleteAssessment(assessmentToDelete);
            finish();
            Intent intent = new Intent(this, AssessmentActivity.class);
            startActivity(intent);
        });

        if (isEdit) {
            saveAssessmentButton.setVisibility(View.GONE);
        } else {
//            addNote.setVisibility(View.GONE);
//            saveNoteButton.setVisibility(View.GONE);
//            viewNotesButton.setVisibility(View.GONE);
            editAssessmentButton.setVisibility(View.GONE);
            deleteAssessmentButton.setVisibility(View.GONE);
        }

        dueDateCal = (view, year, month, day) -> {
            myCalendarDue.set(Calendar.YEAR, year);
            myCalendarDue.set(Calendar.MONTH, month);
            myCalendarDue.set(Calendar.DAY_OF_MONTH, day);
            dueDate.setText(sdf.format(myCalendarDue.getTime()));
        };

        dueDate.setOnClickListener(view -> new DatePickerDialog(AssessmentDetailActivity.this, dueDateCal,
                myCalendarDue.get(Calendar.YEAR), myCalendarDue.get(Calendar.MONTH),
                myCalendarDue.get(Calendar.DAY_OF_MONTH)).show());

        goalDateCal = (view, year, month, day) -> {
            myCalendarGoal.set(Calendar.YEAR, year);
            myCalendarGoal.set(Calendar.MONTH, month);
            myCalendarGoal.set(Calendar.DAY_OF_MONTH, day);
            goalDate.setText(sdf.format(myCalendarGoal.getTime()));
        };

        goalDate.setOnClickListener(view -> new DatePickerDialog(AssessmentDetailActivity.this, goalDateCal,
                myCalendarGoal.get(Calendar.YEAR), myCalendarGoal.get(Calendar.MONTH),
                myCalendarGoal.get(Calendar.DAY_OF_MONTH)).show());

        saveAssessmentButton.setOnClickListener(view -> {
            String courseTitle = String.valueOf(spinner.getSelectedItem());
            int id = 1;
            for (Course c : courseList) {
                if (c.getTitle().equals(courseTitle)) {
                    id = c.getId();
                }
            }
            type = typeSwitch.isChecked();
            String assessmentType;
            if (type) {
                assessmentType = "Performance";
            } else {
                assessmentType = "Objective";
            }
            if (!TextUtils.isEmpty(title.getText()) && !TextUtils.isEmpty(dueDate.getText())
                    && !TextUtils.isEmpty(goalDate.getText())) {
                Assessment assessment = new Assessment(title.getText().toString(), assessmentType, dueDate.getText().toString(),
                        goalDate.getText().toString(), id);

                db.addAssessment(assessment);

                if (dueDateCheckbox.isChecked()) {
                    setAssessmentAlarm(assessment, dueDate, "Assessment Due today");
                }
                if (goalDateCheckbox.isChecked()) {
                    setAssessmentAlarm(assessment, goalDate, "Assessment Goal Date is today");
                }

                finish();
                Intent intent = new Intent(this, AssessmentActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Missing Required Fields", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setAssessmentAlarm(Assessment assessment, EditText date, String message) {
        String dateString = date.getText().toString();
        Date d = null;
        try {
            d = sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Long trigger = d.getTime();
        Intent intent = new Intent(AssessmentDetailActivity.this, MyReceiver.class);
        intent.putExtra("key", assessment.getTitle() + " " + assessment.getType() + " " + message);
        PendingIntent sender = PendingIntent.getBroadcast(AssessmentDetailActivity.this, ++MainActivity.numAlert, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
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