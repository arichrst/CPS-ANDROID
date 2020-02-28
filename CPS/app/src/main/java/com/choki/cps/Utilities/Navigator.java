package com.choki.cps.Utilities;

import android.content.Intent;
import android.os.Bundle;

import com.choki.cps.Activities.MasterActivity;

public class Navigator {
    MasterActivity context;
    public Navigator(MasterActivity activity)
    {
        context = activity;
    }

    public void ToPage(Class<?> page, boolean killPage)
    {
        Intent intent = new Intent(context,page);
        context.startActivity(intent);
        if(killPage)context.finish();
    }

    public void ToPage(Class<?> page, boolean killPage , String key , Object param)
    {
        Intent intent = new Intent(context,page);
        intent.putExtra(key, Formatter.ToJson(param));
        context.startActivity(intent);
        if(killPage)context.finish();
    }

    public void ToPage(Class<?> page, boolean killPage , String key , Object param , String key1 , Object param1)
    {
        Intent intent = new Intent(context,page);
        intent.putExtra(key, Formatter.ToJson(param));
        intent.putExtra(key1, Formatter.ToJson(param1));
        context.startActivity(intent);
        if(killPage)context.finish();
    }

    public String GetDataFromPreviousActivity(String key)
    {
        return context.getIntent().getStringExtra(key);
    }
}
