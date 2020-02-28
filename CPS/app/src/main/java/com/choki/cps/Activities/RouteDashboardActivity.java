package com.choki.cps.Activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.choki.cps.Models.ExposedPipe;
import com.choki.cps.Models.Routes;
import com.choki.cps.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class RouteDashboardActivity extends MasterActivity {

    CardView btnTestPoint , btnPipeExposed , btnUnduhReport;
    TextView textTitle;
    Routes data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routedashboard);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED||
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    },
                    1052);

        }

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder(); StrictMode.setVmPolicy(builder.build());
        final String route = NAVIGATE.GetDataFromPreviousActivity("ROUTE");
        data = new Gson().fromJson(route, new TypeToken<Routes>(){}.getType());

        btnPipeExposed = findViewById(R.id.btn_pipe_exposed);
        btnTestPoint = findViewById(R.id.btn_test_point);
        btnUnduhReport = findViewById(R.id.btn_unduh_report);


        textTitle = findViewById(R.id.title);

        btnPipeExposed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //NAVIGATE.ToPage(ExposedPipeActivity.class , false , "ROUTE" , data);
            }
        });

        btnTestPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NAVIGATE.ToPage(TestPointActivity.class , false , "ROUTE" , data);
            }
        });

        btnUnduhReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                API.download(data);
            }
        });

        textTitle.setText(data.getName());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1052: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED ){

                    // permission was granted.

                } else {


                    // Permission denied - Show a message to inform the user that this app only works
                    // with these permissions granted

                }
                return;
            }

        }
    }
}
