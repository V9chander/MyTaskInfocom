package com.example.mytaskinfocom;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    Context context;
    List<Task> taskList;

    public TaskAdapter(Context ctx, List<Task> list){
      this.context=ctx;
      this.taskList=list;


    }
    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_view, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {


        Task t = taskList.get(position);
        holder.tv_email.setText(t.getEmail());
        holder.tv_pwd.setText(t.getPwd());


    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public class TaskViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tv_email,tv_pwd;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);


            tv_email = itemView.findViewById(R.id.textViewEmail);
            tv_pwd = itemView.findViewById(R.id.textViewPwd);


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
