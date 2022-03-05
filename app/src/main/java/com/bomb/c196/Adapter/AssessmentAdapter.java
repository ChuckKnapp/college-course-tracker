package com.bomb.c196.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bomb.c196.Activity.AssessmentDetailActivity;
import com.bomb.c196.Model.Assessment;
import com.bomb.c196.R;

import java.util.List;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.ViewHolder> {
    private Context context;
    private List<Assessment> assessmentItems;
    public static final String ASSESSMENT_ID = "assessment_id";

    public AssessmentAdapter(Context context, List<Assessment> assessmentItems) {
        this.context = context;
        this.assessmentItems = assessmentItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AssessmentAdapter.ViewHolder holder, int position) {
        Assessment assessment = assessmentItems.get(position);
        holder.title.setText(assessment.getTitle() + ", " + assessment.getType() + " Assessment");
    }

    @Override
    public int getItemCount() { return assessmentItems.size(); }

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
            Assessment assessment = assessmentItems.get(position);
            Toast.makeText(context, assessment.getTitle(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, AssessmentDetailActivity.class);
//            intent.putExtra(ASSESSMENT_ID, assessment.getId());
            intent.putExtra("id", assessment.getId());
            intent.putExtra("title", assessment.getTitle());
            intent.putExtra("type", assessment.getType());
            intent.putExtra("dueDate", assessment.getDueDate());
            intent.putExtra("goalDate", assessment.getGoalDate());
            intent.putExtra("courseId", assessment.getCourseId());
            intent.putExtra("dueAlertChecked", assessment.isDueAlertChecked());
            intent.putExtra("goalAlertChecked", assessment.isGoalAlertChecked());

            context.startActivity(intent);
        }
    }

}
