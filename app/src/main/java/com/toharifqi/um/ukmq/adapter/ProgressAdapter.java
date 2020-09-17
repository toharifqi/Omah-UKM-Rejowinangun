package com.toharifqi.um.ukmq.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.toharifqi.um.ukmq.ProgressDetailActivity;
import com.toharifqi.um.ukmq.R;
import com.toharifqi.um.ukmq.helpers.Config;
import com.toharifqi.um.ukmq.model.ProgressModel;

import java.util.List;

public class ProgressAdapter extends RecyclerView.Adapter<ProgressAdapter.MyViewHolder> {
    private Context context;
    private List<ProgressModel> progressModelList;

    public ProgressAdapter(Context context, List<ProgressModel> progressModelList) {
        this.context = context;
        this.progressModelList = progressModelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.item_progress, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final ProgressModel progressModel = progressModelList.get(position);

        holder.progressWriter.setText(progressModel.getProgressWriter());
        holder.progressDate.setText(progressModel.getTimeStamp());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProgressDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(Config.PROGRESS_TITLE, progressModel.getProgressTitle());
                bundle.putString(Config.PROGRESS_WRITER, progressModel.getProgressWriter());
                bundle.putString(Config.PROGRESS_DATE, progressModel.getTimeStamp());
                bundle.putString(Config.PROGRESS_DESC, progressModel.getProgressDesc());
                bundle.putString(Config.PROGRESS_IMAGE, progressModel.getDocPic());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return progressModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView progressWriter, progressDate;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.progress_cardview);
            progressWriter = itemView.findViewById(R.id.progress_writer);
            progressDate = itemView.findViewById(R.id.progress_date);
        }
    }
}
