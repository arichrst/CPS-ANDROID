package com.choki.cps.VIewModels;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.choki.cps.R;

public class TestPointViewHolder extends RecyclerView.ViewHolder {
    public ImageView icon;
    public TextView title;
    public TextView description;
    public TextView date;
    public LinearLayout masterLayout;

    public TestPointViewHolder(@NonNull View itemView) {
        super(itemView);
        icon = itemView.findViewById(R.id.icon);
        title = itemView.findViewById(R.id.text_title);
        description = itemView.findViewById(R.id.text_description);
        masterLayout = itemView.findViewById(R.id.master_item_layout);
        date = itemView.findViewById(R.id.text_date);
    }
}
