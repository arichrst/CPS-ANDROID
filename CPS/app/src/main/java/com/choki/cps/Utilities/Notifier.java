package com.choki.cps.Utilities;

import android.content.Context;
import android.widget.Toast;

import com.choki.cps.Activities.MasterActivity;

public class Notifier {
    MasterActivity context;
    public Notifier(MasterActivity activity)
    {
        context = activity;
    }
    public void ShowToast(String message)
    {
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }
}
