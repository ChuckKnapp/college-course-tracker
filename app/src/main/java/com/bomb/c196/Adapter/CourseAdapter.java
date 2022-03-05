package com.bomb.c196.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bomb.c196.Activity.CourseDetailActivity;
import com.bomb.c196.Model.Course;
import com.bomb.c196.R;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {
    private Context context;
    private List<Course> courseItems;
    public static final String COURSE_ID = "course_id";

    public CourseAdapter(Context context, List<Course> courseItems) {
        this.context = context;
        this.courseItems = courseItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CourseAdapter.ViewHolder holder, int position) {
        Course course = courseItems.get(position);
        holder.title.setText(course.getTitle());
    }

    @Override
    public int getItemCount() { return courseItems.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            title = itemView.findViewById(R.id.title);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Course course = courseItems.get(position);
            Intent intent = new Intent(context, CourseDetailActivity.class);
            intent.putExtra("id", course.getId());
            intent.putExtra("title", course.getTitle());
            intent.putExtra("startDate", course.getStartDate());
            intent.putExtra("anticipatedEndDate", course.getAnticipatedEndDate());
            intent.putExtra("status", course.getStatus());
            intent.putExtra("termId", course.getTermId());
            intent.putExtra("assessmentId", course.getAssessmentId());
            intent.putExtra("noteId", course.getNoteId());
            intent.putExtra("mentorId", course.getMentorId());

            context.startActivity(intent);
        }
    }
}
