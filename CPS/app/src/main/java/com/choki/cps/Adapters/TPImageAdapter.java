package com.choki.cps.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.choki.cps.Activities.CRUDTestPointActivity;
import com.choki.cps.Activities.MasterActivity;
import com.choki.cps.Activities.TPImageActivity;
import com.choki.cps.Interfaces.IApiListener;
import com.choki.cps.Models.TestPoint;
import com.choki.cps.Models.TestPointImage;
import com.choki.cps.R;
import com.choki.cps.Utilities.Formatter;
import com.choki.cps.VIewModels.TPImageViewHolder;
import com.choki.cps.VIewModels.TestPointViewHolder;
import com.shreyaspatil.MaterialDialog.MaterialDialog;
import com.shreyaspatil.MaterialDialog.interfaces.DialogInterface;

import java.util.List;

public class TPImageAdapter extends RecyclerView.Adapter<TPImageViewHolder> {

    List<TestPointImage> data;
    TPImageActivity context;

    public TPImageAdapter(List<TestPointImage> objects, TPImageActivity activity)
    {
        data = objects;
        context = activity;
    }

    @NonNull
    @Override
    public TPImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tpimage, parent, false);

        return new TPImageViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull TPImageViewHolder holder, final int position) {

        Glide.with(context).load(data.get(position).getLink()).into(holder.icon);
        holder.masterLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialDialog mDialog = new MaterialDialog.Builder(context)
                        .setTitle("Hapus?")
                        .setMessage("Apakah kamu yakin akan menghapus data ini?")
                        .setCancelable(false)
                        .setPositiveButton("Delete", R.drawable.ic_delete_16, new MaterialDialog.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialogInterface, int which) {
                                context.API.DeleteTestPointImage(data.get(position), new IApiListener() {
                                    @Override
                                    public void onStart() {
                                        context.LOADER.Show();
                                    }

                                    @Override
                                    public void onSuccess(String result, String message) {
                                        boolean isSuccess = Formatter.FromJson(result);
                                        if (isSuccess) {
                                            context.NOTIFY.ShowToast(data.get(position) + " berhasil hapus");
                                            context.LoadData();
                                        } else
                                            context.NOTIFY.ShowToast("Ada kesalahan dalam penghapusan gambar");
                                        dialogInterface.dismiss();
                                    }

                                    @Override
                                    public void onFailure(String message) {
                                        context.NOTIFY.ShowToast(message);
                                        dialogInterface.dismiss();
                                    }

                                    @Override
                                    public void onUnauthenticated() {

                                    }

                                    @Override
                                    public void onEnd() {
                                        context.LOADER.Dismiss();
                                    }
                                });
                            }
                        })
                        .setNegativeButton("Cancel", R.drawable.ic_cancel_16, new MaterialDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
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
