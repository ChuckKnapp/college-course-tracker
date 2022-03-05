package com.bomb.c196.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import com.bomb.c196.Data.DBHandler;
import com.bomb.c196.R;

public class MainActivity extends AppCompatActivity {
    private Button termButton, courseButton, assessmentButton;
    public static int numAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBHandler db = new DBHandler(this);

        termButton = findViewById(R.id.termButtonId);
        courseButton = findViewById(R.id.courseButtonId);
        assessmentButton = findViewById(R.id.assessmentButtonId);

        termButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, TermActivity.class);
            startActivity(intent);
        });

        courseButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, CourseActivity.class);
            startActivity(intent);
        });

        assessmentButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AssessmentActivity.class);
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
