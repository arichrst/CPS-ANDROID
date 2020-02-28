package com.choki.cps.Activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.choki.cps.Services.ApiServices;
import com.choki.cps.Services.FileServices;
import com.choki.cps.Utilities.Loader;
import com.choki.cps.Utilities.Navigator;
import com.choki.cps.Utilities.Notifier;

public class MasterActivity extends AppCompatActivity {
    public ApiServices API;
    public FileServices FILE;
    public Notifier NOTIFY;
    public Loader LOADER;
    public Navigator NAVIGATE;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        API = new ApiServices(this);
        FILE = new FileServices(this);
        NOTIFY = new Notifier(this);
        LOADER = new Loader(this);
        NAVIGATE = new Navigator(this);
        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
