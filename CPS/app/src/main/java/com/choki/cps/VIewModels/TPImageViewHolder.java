package com.choki.cps.VIewModels;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.choki.cps.R;

public class TPImageViewHolder extends RecyclerView.ViewHolder {
    public ImageView icon;
    public LinearLayout masterLayout;

    public TPImageViewHolder(@NonNull View itemView) {
        super(itemView);
        icon = itemView.findViewById(R.id.icon);
        masterLayout = itemView.findViewById(R.id.master_item_layout);
    }
}
