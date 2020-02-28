package com.choki.cps.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.choki.cps.Activities.CRUDRouteActivity;
import com.choki.cps.Activities.CRUDTestPointActivity;
import com.choki.cps.Activities.MasterActivity;
import com.choki.cps.Activities.RouteDashboardActivity;
import com.choki.cps.Activities.TPImageActivity;
import com.choki.cps.Models.Routes;
import com.choki.cps.Models.TestPoint;
import com.choki.cps.R;
import com.choki.cps.VIewModels.RouteViewHolder;
import com.choki.cps.VIewModels.TestPointViewHolder;
import com.shreyaspatil.MaterialDialog.MaterialDialog;
import com.shreyaspatil.MaterialDialog.interfaces.DialogInterface;

import java.util.List;

public class TestPointAdapter extends RecyclerView.Adapter<TestPointViewHolder> {

    List<TestPoint> data;
    MasterActivity context;
    Routes routeData;
    public TestPointAdapter(Routes route, List<TestPoint> objects, MasterActivity activity)
    {
        routeData = route;
        data = objects;
        context = activity;
    }

    @NonNull
    @Override
    public TestPointViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_testpoint, parent, false);

        return new TestPointViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull TestPointViewHolder holder, final int position) {
        holder.title.setText(data.get(position).getName() + " - " + routeData.getField());

        String description = "";
        description += "Anoda\t\t\t\t\t\t: -" + data.get(position).getAnode() + " mV";
        description += "\nNative Pipa\t\t\t: -" + data.get(position).getNativePipe() + " mV";
        description += "\nProteksi Pipa\t: -" + data.get(position).getProtection() + " mV";
        description += "\nResistivitas\t\t: " + data.get(position).getSoilResistivity() + " ohm.cm";
        description += "\nPh\t\t\t\t\t\t\t\t\t: " + data.get(position).getPh();

        holder.description.setText(description );
        holder.date.setText(data.get(position).getLandCorrosivity());
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
                                context.NAVIGATE.ToPage(TPImageActivity.class,false,"TESTPOINT", data.get(position));
                                dialogInterface.dismiss();
                            }
                        })
                        .setNegativeButton("Ubah Data", R.drawable.ic_writing_16, new MaterialDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                context.NAVIGATE.ToPage(CRUDTestPointActivity.class,false,"TESTPOINT",data.get(position) , "ROUTE" , routeData);
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
