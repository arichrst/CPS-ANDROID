package com.choki.cps.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.choki.cps.Activities.CRUDRouteActivity;
import com.choki.cps.Activities.MasterActivity;
import com.choki.cps.Activities.RouteDashboardActivity;
import com.choki.cps.Activities.TPImageActivity;
import com.choki.cps.Interfaces.IApiListener;
import com.choki.cps.Models.Routes;
import com.choki.cps.R;
import com.choki.cps.Utilities.Formatter;
import com.choki.cps.VIewModels.RouteViewHolder;
import com.shreyaspatil.MaterialDialog.MaterialDialog;
import com.shreyaspatil.MaterialDialog.interfaces.DialogInterface;

import java.util.List;

public class RouteAdapter extends RecyclerView.Adapter<RouteViewHolder> {

    List<Routes> data;
    MasterActivity context;

    public RouteAdapter(List<Routes> objects, MasterActivity activity)
    {
        data = objects;
        context = activity;
    }

    @NonNull
    @Override
    public RouteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_routes, parent, false);

        return new RouteViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull RouteViewHolder holder, final int position) {
        holder.title.setText(data.get(position).getName());
        holder.description.setText(data.get(position).getFromRegion() + " - " + data.get(position).getToRegion() );
        holder.distance.setText("Jarak : " + data.get(position).getDistance() + " meter");
        holder.masterLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MaterialDialog mDialog = new MaterialDialog.Builder(context)
                        .setTitle("Aktivitas")
                        .setMessage("Apa yang akan kamu lakukan dengan data ini?")
                        .setCancelable(true)
                        .setPositiveButton("Lihat Detail", R.drawable.ic_magnifying_glass_16, new MaterialDialog.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialogInterface, int which) {
                                context.NAVIGATE.ToPage(RouteDashboardActivity.class,false,"ROUTE", data.get(position));
                                dialogInterface.dismiss();
                            }
                        })
                        .setNegativeButton("Ubah Data", R.drawable.ic_writing_16, new MaterialDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                context.NAVIGATE.ToPage(CRUDRouteActivity.class,false,"ROUTE",data.get(position));
                                dialogInterface.dismiss();
                            }
                        })
                        .build();
                mDialog.show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
