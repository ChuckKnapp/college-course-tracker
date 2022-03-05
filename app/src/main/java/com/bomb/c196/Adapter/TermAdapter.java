package com.bomb.c196.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bomb.c196.Activity.TermDetailActivity;
import com.bomb.c196.Model.Term;
import com.bomb.c196.R;

import java.util.List;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.ViewHolder> {
    private Context context;
    private List<Term> termItems;
    public static final String TERM_ID = "term_id";

    public TermAdapter(Context context, List<Term> termItems) {
        this.context = context;
        this.termItems = termItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TermAdapter.ViewHolder holder, int position) {
        Term term = termItems.get(position);
        holder.title.setText(term.getTitle());
    }

    @Override
    public int getItemCount() { return termItems.size(); }

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
            Term term = termItems.get(position);
            Toast.makeText(context, term.getTitle(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, TermDetailActivity.class);
            intent.putExtra("id", term.getId());
            intent.putExtra("title", term.getTitle());
            intent.putExtra("startDate", term.getStartDate());
            intent.putExtra("endDate", term.getEndDate());

            context.startActivity(intent);
        }
    }
}
