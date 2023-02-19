package com.assignmenttracker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.ViewHolder>
{
    private ArrayList<Assignment> assignments;
    ItemClicked activity;

    public interface ItemClicked
    {
        void onItemClicked(int index);
    }

    public AssignmentAdapter (Context context, ArrayList<Assignment> list)
    {
        assignments = list;
        activity = (ItemClicked) context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvDate, tvAssignmentName;
        Button btnEdit, btnClear;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            tvDate = itemView.findViewById(R.id.tvDate);
            tvAssignmentName = itemView.findViewById(R.id.tvAssignmentName);
            btnClear = itemView.findViewById(R.id.btnClear);
            btnEdit = itemView.findViewById(R.id.btnEdit);



            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    activity.onItemClicked(assignments.indexOf((Assignment) view.getTag()));
                }
            });
        }
    }

    @NonNull
    @Override
    public AssignmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AssignmentAdapter.ViewHolder holder, int position)
    {
        holder.itemView.setTag(assignments.get(position));

        holder.tvDate.setText(assignments.get(position).getDueDate());
        holder.tvAssignmentName.setText(assignments.get(position).getName());
        //holder.btnEdit.setText("Edit");
        //holder.btnClear.setText("Clear");
    }

    @Override
    public int getItemCount()
    {
        return assignments.size();
    }
}
