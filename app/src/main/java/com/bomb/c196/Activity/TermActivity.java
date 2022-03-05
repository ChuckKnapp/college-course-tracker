package com.bomb.c196.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.bomb.c196.Adapter.TermAdapter;
import com.bomb.c196.Data.DBHandler;
import com.bomb.c196.Model.Term;
import com.bomb.c196.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;
import java.util.List;

public class TermActivity extends AppCompatActivity {
    private RecyclerView termRecyclerView;
    private RecyclerView.Adapter termAdapter;
    private List<Term> termItems;
    private Date dummyDate = new Date(2022, 01, 01);
    private FloatingActionButton termFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term);

        termRecyclerView = findViewById(R.id.termRecyclerViewId);
        termRecyclerView.setHasFixedSize(true);
        termRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        DBHandler db = new DBHandler(this);
        termItems = db.getAllTerms();
        termAdapter = new TermAdapter(this, termItems);
        termRecyclerView.setAdapter(termAdapter);

        termFab = findViewById(R.id.termFabId);
        termFab.setOnClickListener(view -> {
            Intent intent = new Intent(this, TermDetailActivity.class);
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