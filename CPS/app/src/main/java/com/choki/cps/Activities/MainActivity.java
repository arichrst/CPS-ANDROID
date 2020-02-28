package com.choki.cps.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.choki.cps.Interfaces.IApiListener;
import com.choki.cps.R;
import com.choki.cps.Utilities.Notifier;

public class MainActivity extends MasterActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
}
