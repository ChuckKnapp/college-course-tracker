package com.bomb.c196.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bomb.c196.Activity.MentorDetailActivity;
import com.bomb.c196.Model.Mentor;
import com.bomb.c196.R;

import java.util.List;

public class MentorAdapter extends RecyclerView.Adapter<MentorAdapter.ViewHolder> {
    private Context context;
    private List<Mentor> mentorItems;

    public MentorAdapter(Context context, List<Mentor> mentorItems) {
        this.context = context;
        this.mentorItems = mentorItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Mentor mentor = mentorItems.get(position);
        holder.title.setText(mentor.getName() + " " + mentor.getPhone() + "\n" + mentor.getEmail());
    }

    @Override
    public int getItemCount() { return mentorItems.size(); }

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
            Mentor mentor = mentorItems.get(position);
            Intent intent = new Intent(context, MentorDetailActivity.class);
            intent.putExtra("id", mentor.getId());
            intent.putExtra("name", mentor.getName());
            intent.putExtra("phone", mentor.getPhone());
            intent.putExtra("email", mentor.getEmail());
            intent.putExtra("courseId", mentor.getCourseId());

            context.startActivity(intent);
        }
    }
}
