package com.bomb.c196.Data;

import static com.bomb.c196.Utility.Formatter.formatString;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.bomb.c196.Model.Assessment;
import com.bomb.c196.Model.Course;
import com.bomb.c196.Model.Mentor;
import com.bomb.c196.Model.Note;
import com.bomb.c196.Model.Term;
import com.bomb.c196.Utility.Formatter;
import com.bomb.c196.Utility.Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DBHandler extends SQLiteOpenHelper {
    Date dummyDate = new Date(2022, 01, 01);

    public DBHandler(Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_ASSESSMENT_TABLE = "CREATE TABLE " + Util.ASSESSMENT_TABLE_NAME + "("
                + Util.ASSESSMENT_ID + " INTEGER PRIMARY KEY," + Util.ASSESSMENT_TITLE + " TEXT,"
                + Util.ASSESSMENT_TYPE + " TEXT," + Util.ASSESSMENT_DUE_DATE + " TEXT,"
                + Util.ASSESSMENT_GOAL_DATE + " TEXT," + Util.ASSESSMENT_COURSE_ID + " INTEGER" + ")";
        db.execSQL(CREATE_ASSESSMENT_TABLE);

        String CREATE_COURSE_TABLE = "CREATE TABLE " + Util.COURSE_TABLE_NAME + "("
                + Util.COURSE_ID + " INTEGER PRIMARY KEY," + Util.COURSE_TITLE + " TEXT,"
                + Util.COURSE_START_DATE + " INTEGER," + Util.COURSE_ANTICIPATED_END_DATE + " INTEGER,"
                + Util.COURSE_STATUS + " TEXT," + Util.COURSE_TERM_ID + " INTEGER,"
                + Util.COURSE_ASSESSMENT_ID + " INTEGER," + Util.COURSE_NOTE_ID + " INTEGER,"
                + Util.COURSE_MENTOR_ID + " INTEGER" + ")";
        db.execSQL(CREATE_COURSE_TABLE);

        String CREATE_MENTOR_TABLE = "CREATE TABLE " + Util.MENTOR_TABLE_NAME + "("
                + Util.MENTOR_ID + " INTEGER PRIMARY KEY," + Util.MENTOR_NAME + " TEXT,"
                + Util.MENTOR_PHONE + " TEXT," + Util.MENTOR_EMAIL + " TEXT,"
                + Util.MENTOR_COURSE_ID + " INTEGER" + ")";
        db.execSQL(CREATE_MENTOR_TABLE);

        String CREATE_NOTE_TABLE = "CREATE TABLE " + Util.NOTE_TABLE_NAME + "("
                + Util.NOTE_ID + " INTEGER PRIMARY KEY," + Util.NOTE_NOTE + " TEXT,"
                + Util.NOTE_COURSE_ID + " INTEGER" + ")";
        db.execSQL(CREATE_NOTE_TABLE);

        String CREATE_TERM_TABLE = "CREATE TABLE " + Util.TERM_TABLE_NAME + "("
                + Util.TERM_ID + " INTEGER PRIMARY KEY," + Util.TERM_TITLE + " TEXT,"
                + Util.TERM_START_DATE + " TEXT," + Util.TERM_END_DATE + " TEXT" + ")";
        db.execSQL(CREATE_TERM_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + Util.ASSESSMENT_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Util.COURSE_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Util.MENTOR_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Util.NOTE_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Util.TERM_TABLE_NAME);
        onCreate(db);
    }

    public void addAssessment(Assessment assessment) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(Util.ASSESSMENT_TITLE, assessment.getTitle());
        value.put(Util.ASSESSMENT_TYPE, assessment.getType());
        value.put(Util.ASSESSMENT_DUE_DATE, assessment.getDueDate().toString());
        value.put(Util.ASSESSMENT_GOAL_DATE, assessment.getGoalDate().toString());
        value.put(Util.ASSESSMENT_COURSE_ID, assessment.getCourseId());

        db.insert(Util.ASSESSMENT_TABLE_NAME, null, value);
//        db.close();
    }

    public Assessment getAssessment(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Util.ASSESSMENT_TABLE_NAME, new String[] { Util.ASSESSMENT_ID,
            Util.ASSESSMENT_TITLE, Util.ASSESSMENT_TYPE, Util.ASSESSMENT_DUE_DATE,
            Util.ASSESSMENT_GOAL_DATE, Util.ASSESSMENT_COURSE_ID}, Util.ASSESSMENT_ID + "=?",
                new String[] {String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Assessment assessment = new Assessment(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3),
                cursor.getString(4), Integer.parseInt(cursor.getString(5)));

        return assessment;
    }

    public List<Assessment> getAllAssessments() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Assessment> assessmentList = new ArrayList<>();

        String selectAll = "SELECT * FROM " + Util.ASSESSMENT_TABLE_NAME;
        Cursor cursor = db.rawQuery(selectAll, null);

        if (cursor.moveToFirst()) {
            do {
                Assessment assessment = new Assessment();
                assessment.setId(Integer.parseInt(cursor.getString(0)));
                assessment.setTitle(cursor.getString(1));
                assessment.setType(cursor.getString(2));
                assessment.setDueDate(cursor.getString(3));
                assessment.setGoalDate(cursor.getString(4));
                assessment.setCourseId(Integer.parseInt(cursor.getString(5)));

                assessmentList.add(assessment);
            } while (cursor.moveToNext());
        }
        return assessmentList;
    }

    public int updateAssessment(Assessment assessment) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Util.ASSESSMENT_TITLE, assessment.getTitle());
        values.put(Util.ASSESSMENT_TYPE, assessment.getType());
        values.put(Util.ASSESSMENT_DUE_DATE, assessment.getDueDate().toString());
        values.put(Util.ASSESSMENT_GOAL_DATE, assessment.getGoalDate().toString());
        values.put(Util.ASSESSMENT_COURSE_ID, assessment.getCourseId());

        return db.update(Util.ASSESSMENT_TABLE_NAME, values, Util.ASSESSMENT_ID + "=?",
                new String[] {String.valueOf(assessment.getId())});
    }

    public void deleteAssessment(Assessment assessment) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Util.ASSESSMENT_TABLE_NAME, Util.ASSESSMENT_ID + " = ? ",
                new String[] {String.valueOf(assessment.getId())});

//        db.close();
    }

    public int getAssessmentsCount() {
        String countQuery = "SELECT * FROM " + Util.ASSESSMENT_TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        return cursor.getCount();
    }

    public void addCourse(Course course) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(Util.COURSE_TITLE, course.getTitle());
        value.put(Util.COURSE_START_DATE, course.getStartDate().toString());
        value.put(Util.COURSE_ANTICIPATED_END_DATE, course.getAnticipatedEndDate().toString());
        value.put(Util.COURSE_STATUS, course.getStatus());
        value.put(Util.COURSE_TERM_ID, course.getTermId());
        value.put(Util.COURSE_ASSESSMENT_ID, course.getAssessmentId());
        value.put(Util.COURSE_NOTE_ID, course.getNoteId());
        value.put(Util.COURSE_MENTOR_ID, course.getMentorId());

        db.insert(Util.COURSE_TABLE_NAME, null, value);
//        db.close();
    }

    public Course getCourse(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Util.COURSE_TABLE_NAME, new String[] {Util.COURSE_ID,
                        Util.COURSE_TITLE, Util.COURSE_START_DATE, Util.COURSE_ANTICIPATED_END_DATE,
                        Util.COURSE_STATUS, Util.COURSE_TERM_ID, Util.COURSE_ASSESSMENT_ID,
                        Util.COURSE_NOTE_ID, Util.COURSE_MENTOR_ID}, Util.COURSE_ID + "=?",
                new String[] {String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Course course = new Course(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2),
                cursor.getString(3), cursor.getString(4),
                Integer.parseInt(cursor.getString(5)), Integer.parseInt(cursor.getString(6)),
                Integer.parseInt(cursor.getString(7)), Integer.parseInt(cursor.getString(8)));

        return course;
    }

    public List<Course> getAllCourses() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Course> courseList = new ArrayList<>();

        String selectAll = "SELECT * FROM " + Util.COURSE_TABLE_NAME;
        Cursor cursor = db.rawQuery(selectAll, null);

        if (cursor.moveToFirst()) {
            do {
                Course course = new Course();
                course.setId(Integer.parseInt(cursor.getString(0)));
                course.setTitle(cursor.getString(1));
                course.setStartDate(cursor.getString(2));
                course.setAnticipatedEndDate(cursor.getString(3));
                course.setStatus(cursor.getString(4));
                course.setTermId(Integer.parseInt(cursor.getString(5)));
                course.setAssessmentId(Integer.parseInt(cursor.getString(6)));
                course.setNoteId(Integer.parseInt(cursor.getString(7)));
                course.setMentorId(Integer.parseInt(cursor.getString(8)));

                courseList.add(course);
            } while (cursor.moveToNext());
        }
        return courseList;
    }

    public int updateCourse(Course course) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Util.COURSE_TITLE, course.getTitle());
        values.put(Util.COURSE_START_DATE, course.getStartDate().toString());
        values.put(Util.COURSE_ANTICIPATED_END_DATE, course.getAnticipatedEndDate().toString());
        values.put(Util.COURSE_STATUS, course.getStatus());
        values.put(Util.COURSE_TERM_ID, course.getTermId());
        values.put(Util.COURSE_ASSESSMENT_ID, course.getAssessmentId());
        values.put(Util.COURSE_NOTE_ID, course.getNoteId());
        values.put(Util.COURSE_MENTOR_ID, course.getMentorId());

        return db.update(Util.COURSE_TABLE_NAME, values, Util.COURSE_ID + "=?",
                new String[] {String.valueOf(course.getId())});
    }

    public void deleteCourse(Course course) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Util.COURSE_TABLE_NAME, Util.COURSE_ID + "=?",
                new String[] {String.valueOf(course.getId())});

//        db.close();
    }

    public int getCoursesCount() {
        String countQuery = "SELECT * FROM " + Util.COURSE_TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        return cursor.getCount();
    }

    public void addMentor(Mentor mentor) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(Util.MENTOR_NAME, mentor.getName());
        value.put(Util.MENTOR_PHONE, mentor.getPhone());
        value.put(Util.MENTOR_EMAIL, mentor.getEmail());
        value.put(Util.MENTOR_COURSE_ID, mentor.getCourseId());

        db.insert(Util.MENTOR_TABLE_NAME, null, value);
//        db.close();
    }

    public Mentor getMentor(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Util.MENTOR_TABLE_NAME, new String[] {Util.MENTOR_ID,
                        Util.MENTOR_NAME, Util.MENTOR_PHONE, Util.MENTOR_EMAIL, Util.MENTOR_COURSE_ID},
                Util.MENTOR_ID + "=?", new String[] {String.valueOf(id)},
                null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Mentor mentor = new Mentor(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3),
                Integer.parseInt(cursor.getString(4)));

        return mentor;
    }

    public List<Mentor> getAllMentors() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Mentor> mentorList = new ArrayList<>();

        String selectAll = "SELECT * FROM " + Util.MENTOR_TABLE_NAME;
        Cursor cursor = db.rawQuery(selectAll, null);

        if (cursor.moveToFirst()) {
            do {
                Mentor mentor = new Mentor();
                mentor.setId(Integer.parseInt(cursor.getString(0)));
                mentor.setName(cursor.getString(1));
                mentor.setPhone(cursor.getString(2));
                mentor.setEmail(cursor.getString(3));
                mentor.setCourseId(Integer.parseInt(cursor.getString(4)));

                mentorList.add(mentor);
            } while (cursor.moveToNext());
        }
        return mentorList;
    }

    public int updateMentor(Mentor mentor) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Util.MENTOR_NAME, mentor.getName());
        values.put(Util.MENTOR_PHONE, mentor.getPhone());
        values.put(Util.MENTOR_EMAIL, mentor.getEmail());
        values.put(Util.MENTOR_COURSE_ID, mentor.getCourseId());

        return db.update(Util.MENTOR_TABLE_NAME, values, Util.MENTOR_ID + "=?",
                new String[] {String.valueOf(mentor.getId())});
    }

    public void deleteMentor(Mentor mentor) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Util.MENTOR_TABLE_NAME, Util.MENTOR_ID + "=?",
                new String[] {String.valueOf(mentor.getId())});

//        db.close();
    }

    public void addNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(Util.NOTE_NOTE, note.getNote());
        value.put(Util.NOTE_COURSE_ID, note.getCourseId());

        db.insert(Util.NOTE_TABLE_NAME, null, value);
//        db.close();
    }

    public Note getNote(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Util.NOTE_TABLE_NAME, new String[] {Util.NOTE_ID,
                        Util.NOTE_NOTE, Util.NOTE_COURSE_ID}, Util.NOTE_ID + "=?",
                new String[] {String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Note note = new Note(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), Integer.parseInt(cursor.getString(2)));

        return note;
    }

    public List<Note> getAllNotes() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Note> noteList = new ArrayList<>();

        String selectAll = "SELECT * FROM " + Util.NOTE_TABLE_NAME;
        Cursor cursor = db.rawQuery(selectAll, null);

        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setId(Integer.parseInt(cursor.getString(0)));
                note.setNote(cursor.getString(1));
                note.setCourseId(Integer.parseInt(cursor.getString(2)));

                noteList.add(note);
            } while (cursor.moveToNext());
        }
        return noteList;
    }

    public int updateNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Util.NOTE_NOTE, note.getNote());
        values.put(Util.NOTE_COURSE_ID, note.getCourseId());

        return db.update(Util.NOTE_TABLE_NAME, values, Util.NOTE_ID + "=?",
                new String[] {String.valueOf(note.getId())});
    }

    public void deleteNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Util.NOTE_TABLE_NAME, Util.NOTE_ID + "=?",
                new String[] {String.valueOf(note.getId())});

//        db.close();
    }

    public void addTerm(Term term) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(Util.TERM_TITLE, term.getTitle());
        value.put(Util.TERM_START_DATE, term.getStartDate());
        value.put(Util.TERM_END_DATE, term.getEndDate());

        db.insert(Util.TERM_TABLE_NAME, null, value);
//        db.close();
    }

    public Term getTerm(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Util.TERM_TABLE_NAME, new String[] {Util.TERM_ID,
                        Util.TERM_TITLE, Util.TERM_START_DATE, Util.TERM_END_DATE},
                Util.TERM_ID + "=?", new String[] {String.valueOf(id)},
                null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Term term = new Term(Integer.parseInt(cursor.getString(0)), cursor.getString(1),
                cursor.getString(2), cursor.getString(3));

        return term;
    }

    public List<Term> getAllTerms() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Term> termList = new ArrayList<>();

        String selectAll = "SELECT * FROM " + Util.TERM_TABLE_NAME;
        Cursor cursor = db.rawQuery(selectAll, null);

        if (cursor.moveToFirst()) {
            do {
                Term term = new Term();
                term.setId(Integer.parseInt(cursor.getString(0)));
                term.setTitle(cursor.getString(1));
                term.setStartDate(cursor.getString(2));
                term.setEndDate(cursor.getString(3));

                termList.add(term);
            } while (cursor.moveToNext());
        }
        return termList;
    }

    public int updateTerm(Term term) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Util.TERM_TITLE, term.getTitle());
        values.put(Util.TERM_START_DATE, term.getStartDate());
        values.put(Util.TERM_END_DATE, term.getEndDate());

        return db.update(Util.TERM_TABLE_NAME, values, Util.TERM_ID + "=?",
                new String[] {String.valueOf(term.getId())});
    }

    public void deleteTerm(Term term) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Util.TERM_TABLE_NAME, Util.TERM_ID + "=?",
                new String[] {String.valueOf(term.getId())});

//        db.close();
    }

    public int getTermsCount() {
        String countQuery = "SELECT * FROM " + Util.TERM_TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        return cursor.getCount();
    }
}
