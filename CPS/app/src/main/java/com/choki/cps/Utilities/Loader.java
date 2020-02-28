package com.choki.cps.Utilities;

import android.app.ProgressDialog;

import com.choki.cps.Activities.MasterActivity;
import com.choki.cps.R;

public class Loader {
    MasterActivity context;
    ProgressDialog progressDialog;
    public Loader(MasterActivity activity)
    {
        context = activity;
        progressDialog = new ProgressDialog(context, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Harap sabar menunggu");
        //progressDialog.show();
    }

    public void Show()
    {
        progressDialog.show();
    }

    public void Dismiss()
    {
        progressDialog.dismiss();
    }
}
