package com.choki.cps.VIewModels;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.choki.cps.Activities.MasterActivity;
import com.choki.cps.Activities.RouteDashboardActivity;
import com.choki.cps.Models.Routes;
import com.choki.cps.R;

public class RouteViewHolder extends RecyclerView.ViewHolder {
    public ImageView icon;
    public TextView title;
    public TextView description;
    public TextView distance;
    public TextView creator;
    public LinearLayout masterLayout;

    public RouteViewHolder(@NonNull View itemView) {
        super(itemView);
        icon = itemView.findViewById(R.id.icon);
        title = itemView.findViewById(R.id.text_title);
        description = itemView.findViewById(R.id.text_description);
        masterLayout = itemView.findViewById(R.id.master_item_layout);
        distance = itemView.findViewById(R.id.text_distance);
    }
}
