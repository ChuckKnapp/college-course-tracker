package com.bomb.c196.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.bomb.c196.Data.DBHandler;
import com.bomb.c196.Model.Assessment;
import com.bomb.c196.Model.Course;
import com.bomb.c196.Model.Mentor;
import com.bomb.c196.Model.Note;
import com.bomb.c196.Model.Term;
import com.bomb.c196.R;
import com.bomb.c196.Utility.MyReceiver;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class CourseDetailActivity extends AppCompatActivity {
    private TextView courseDetail, mentorLabel, mentorNameLabel, mentorPhoneLabel, mentorEmailLabel;
    private Bundle extras;
    private EditText title, startDate, endDate, status, mentorName, mentorPhone, mentorEmail, addNote;
    private Spinner spinner;
    private Button saveCourseButton, editCourseButton, deleteCourseButton;
    private Button saveMentorButton, viewMentorsButton, saveNoteButton, viewNotesButton, viewAssessmentsButton;
    private CheckBox startDateCheckbox, endDateCheckbox;
    DatePickerDialog.OnDateSetListener startDateCal;
    DatePickerDialog.OnDateSetListener endDateCal;
    final Calendar myCalendarStart = Calendar.getInstance();
    final Calendar myCalendarEnd = Calendar.getInstance();
    private DBHandler db = new DBHandler(this);
    private Course courseFromIntent;
    ArrayList termlist = new ArrayList();
    private long courseId = 0;
    private Boolean isEdit = false;
    private String myFormat = "MM/dd/yy";
    private SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
    private List<Term> termList;
    private List<String> termNames = new ArrayList<>();
    private List<Assessment> assessmentList;
    private List<Note> noteList;
    private List<Mentor> mentorList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        saveCourseButton = findViewById(R.id.saveCourseButton);
        editCourseButton = findViewById(R.id.editCourseButton);
        deleteCourseButton = findViewById(R.id.deleteCourseButton);
        title = findViewById(R.id.title);
        status = findViewById(R.id.status);
        startDate = findViewById(R.id.startDate);
        endDate = findViewById(R.id.endDate);
        addNote = findViewById(R.id.addNote);
        saveNoteButton = findViewById(R.id.saveNoteButton);
        mentorName = findViewById(R.id.mentorName);
        mentorPhone = findViewById(R.id.mentorPhone);
        mentorEmail = findViewById(R.id.mentorEmail);
        saveMentorButton = findViewById(R.id.saveMentorButton);
        mentorLabel = findViewById(R.id.mentorLabel);
        mentorNameLabel = findViewById(R.id.mentorNameLabel);
        mentorPhoneLabel = findViewById(R.id.mentorPhoneLabel);
        mentorEmailLabel = findViewById(R.id.mentorEmailLabel);
        viewNotesButton = findViewById(R.id.viewNotesButton);
        viewMentorsButton = findViewById(R.id.viewMentorsButton);
        startDateCheckbox = findViewById(R.id.startDateCheckbox);
        endDateCheckbox = findViewById(R.id.endDateCheckbox);
        viewAssessmentsButton = findViewById(R.id.viewAssessmentButton);
        spinner = findViewById(R.id.spinner);

        termList = db.getAllTerms();
        for (Term t : termList) {
            termNames.add(t.getTitle());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_layout,
                R.id.spinner_txt, termNames);
        spinner.setAdapter(adapter);

        extras = getIntent().getExtras();

        if (extras != null) {
            courseFromIntent = new Course(extras.getInt("id"), extras.getString("title"),
                    extras.getString("startDate"), extras.getString("anticipatedEndDate"),
                    extras.getString("status"), extras.getInt("termId"), extras.getInt("assessmentId"),
                    extras.getInt("noteId"), extras.getInt("mentorId"));

            title.setText(courseFromIntent.getTitle());
            status.setText(courseFromIntent.getStatus());
            startDate.setText(courseFromIntent.getStartDate());
            endDate.setText(courseFromIntent.getAnticipatedEndDate());
            Term t = db.getTerm(courseFromIntent.getTermId());
            spinner.setSelection(adapter.getPosition(t.getTitle()));

            isEdit = true;
        }

        saveNoteButton.setOnClickListener(view -> {
            if (!TextUtils.isEmpty(addNote.getText())) {
                Note note = new Note(addNote.getText().toString(), courseFromIntent.getId());
                db.addNote(note);
                addNote.getText().clear();
            }
        });

        viewNotesButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, NoteActivity.class);
            intent.putExtra("courseId", courseFromIntent.getId());
            intent.putExtra("courseTitle", courseFromIntent.getTitle());
            intent.putExtra("courseStartDate", courseFromIntent.getStartDate());
            intent.putExtra("courseAnticipatedEndDate", courseFromIntent.getAnticipatedEndDate());
            intent.putExtra("courseStatus", courseFromIntent.getStatus());
            intent.putExtra("courseTermId", courseFromIntent.getTermId());
            startActivity(intent);
        });

        viewAssessmentsButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, AssessmentActivity.class);
            intent.putExtra("courseId", courseFromIntent.getId());
            intent.putExtra("courseTitle", courseFromIntent.getTitle());
            intent.putExtra("courseStartDate", courseFromIntent.getStartDate());
            intent.putExtra("courseAnticipatedEndDate", courseFromIntent.getAnticipatedEndDate());
            intent.putExtra("courseStatus", courseFromIntent.getStatus());
            intent.putExtra("courseTermId", courseFromIntent.getTermId());
            startActivity(intent);
        });

        saveMentorButton.setOnClickListener(view -> {
            if(!TextUtils.isEmpty(mentorName.getText()) && !TextUtils.isEmpty(mentorPhone.getText())
                    && !TextUtils.isEmpty(mentorEmail.getText())) {
                Mentor mentor = new Mentor(mentorName.getText().toString(),
                        mentorPhone.getText().toString(), mentorEmail.getText().toString(), courseFromIntent.getId());

                db.addMentor(mentor);
                mentorName.getText().clear();
                mentorPhone.getText().clear();
                mentorEmail.getText().clear();
            }
        });

        viewMentorsButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, MentorActivity.class);
            intent.putExtra("courseId", courseFromIntent.getId());
            intent.putExtra("courseTitle", courseFromIntent.getTitle());
            intent.putExtra("courseStartDate", courseFromIntent.getStartDate());
            intent.putExtra("courseAnticipatedEndDate", courseFromIntent.getAnticipatedEndDate());
            intent.putExtra("courseStatus", courseFromIntent.getStatus());
            intent.putExtra("courseTermId", courseFromIntent.getTermId());
            startActivity(intent);
        });

        saveCourseButton.setOnClickListener(view -> {
            String termTitle = String.valueOf(spinner.getSelectedItem());
            int id = 1;
            for (Term t : termList) {
                if (t.getTitle().equals(termTitle)) {
                    id = t.getId();
                }
            }
            if (!TextUtils.isEmpty(title.getText()) && !TextUtils.isEmpty(startDate.getText())
                    && !TextUtils.isEmpty(endDate.getText()) && !TextUtils.isEmpty(status.getText())) {
                Course course = new Course(title.getText().toString(), startDate.getText().toString(),
                        endDate.getText().toString(), status.getText().toString(), id);

                db.addCourse(course);

                if (startDateCheckbox.isChecked()) {
                    setCourseAlarm(course, startDate, "Starts today");
                }
                if (endDateCheckbox.isChecked()) {
                    setCourseAlarm(course, endDate, "Ends today");
                }

                finish();
                Intent intent = new Intent(this, CourseActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Missing Required Fields", Toast.LENGTH_SHORT).show();
            }
        });

        editCourseButton.setOnClickListener(view -> {
            String termTitle = String.valueOf(spinner.getSelectedItem());
            int id = 1;
            for (Term t : termList) {
                if (t.getTitle().equals(termTitle)) {
                    id = t.getId();
                }
            }
            if (!TextUtils.isEmpty(title.getText()) && !TextUtils.isEmpty(startDate.getText())
                    && !TextUtils.isEmpty(endDate.getText()) && !TextUtils.isEmpty(status.getText())) {
                Course course = new Course(title.getText().toString(), startDate.getText().toString(),
                        endDate.getText().toString(), status.getText().toString(), id);
                course.setId(courseFromIntent.getId());
                db.updateCourse(course);

                finish();
                Intent intent = new Intent(this, CourseActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Missing Required Fields", Toast.LENGTH_SHORT).show();
            }
        });

        deleteCourseButton.setOnClickListener(view -> {
            assessmentList = db.getAllAssessments();
            for (Assessment a : assessmentList) {
                if (a.getCourseId() == courseFromIntent.getId()) {
                    db.deleteAssessment(a);
                }
            }

            noteList = db.getAllNotes();
            for (Note n : noteList) {
                if (n.getCourseId() == courseFromIntent.getId()) {
                    db.deleteNote(n);
                }
            }

            mentorList = db.getAllMentors();
            for (Mentor m : mentorList) {
                if (m.getCourseId() == courseFromIntent.getId()) {
                    db.deleteMentor(m);
                }
            }

            Course courseToDelete = db.getCourse(courseFromIntent.getId());
            db.deleteCourse(courseToDelete);
            finish();
            Intent intent = new Intent(this, CourseActivity.class);
            startActivity(intent);
        });

        if (isEdit) {
            saveCourseButton.setVisibility(View.GONE);
        } else {
            viewAssessmentsButton.setVisibility(View.GONE);
            mentorLabel.setVisibility(View.GONE);
            mentorNameLabel.setVisibility(View.GONE);
            mentorPhoneLabel.setVisibility(View.GONE);
            mentorEmailLabel.setVisibility(View.GONE);
            mentorName.setVisibility(View.GONE);
            mentorPhone.setVisibility(View.GONE);
            mentorEmail.setVisibility(View.GONE);
            saveMentorButton.setVisibility(View.GONE);
            viewMentorsButton.setVisibility(View.GONE);
            addNote.setVisibility(View.GONE);
            saveNoteButton.setVisibility(View.GONE);
            viewNotesButton.setVisibility(View.GONE);
            editCourseButton.setVisibility(View.GONE);
            deleteCourseButton.setVisibility(View.GONE);
        }

        startDateCal = (view, year, month, day) -> {
            myCalendarStart.set(Calendar.YEAR, year);
            myCalendarStart.set(Calendar.MONTH, month);
            myCalendarStart.set(Calendar.DAY_OF_MONTH, day);
            startDate.setText(sdf.format(myCalendarStart.getTime()));
        };

        startDate.setOnClickListener(view -> new DatePickerDialog(CourseDetailActivity.this, startDateCal, myCalendarStart
                .get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH), myCalendarStart.get(Calendar.DAY_OF_MONTH)).show());

        endDateCal = (view, year, month, day) -> {
            myCalendarEnd.set(Calendar.YEAR, year);
            myCalendarEnd.set(Calendar.MONTH, month);
            myCalendarEnd.set(Calendar.DAY_OF_MONTH, day);
            endDate.setText(sdf.format(myCalendarEnd.getTime()));
        };

        endDate.setOnClickListener(view -> new DatePickerDialog(CourseDetailActivity.this, endDateCal, myCalendarEnd
                .get(Calendar.YEAR), myCalendarEnd.get(Calendar.MONTH), myCalendarEnd.get(Calendar.DAY_OF_MONTH)).show());

    }

    public void setCourseAlarm(Course course, EditText date, String message) {
        String dateString = date.getText().toString();
        Date d = null;
        try {
            d = sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Long trigger = d.getTime();
        Intent intent = new Intent(CourseDetailActivity.this, MyReceiver.class);
        intent.putExtra("key", course.getTitle() +  " " + message);
        PendingIntent sender = PendingIntent.getBroadcast(CourseDetailActivity.this, ++MainActivity.numAlert, intent, 0);
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