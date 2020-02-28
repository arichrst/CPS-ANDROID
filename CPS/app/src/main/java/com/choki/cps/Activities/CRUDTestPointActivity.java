package com.choki.cps.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.choki.cps.Interfaces.IApiListener;
import com.choki.cps.Models.Routes;
import com.choki.cps.Models.TestPoint;
import com.choki.cps.R;
import com.choki.cps.Utilities.Formatter;
import com.choki.cps.Utilities.Networker;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shreyaspatil.MaterialDialog.MaterialDialog;
import com.shreyaspatil.MaterialDialog.interfaces.DialogInterface;

public class CRUDTestPointActivity extends MasterActivity {

    EditText inputName , inputLatitude , inputLongitude , inputNotes ,
            inputKpLocation , inputNativePipa , inputAnoda , inputProteksi ,
            inputAnodaPower , inputSoilResistivity , inputPh;

    Button btnSubmit , btnDelete;
    TestPoint data;
    Routes route;
    Boolean isEdit = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crudtestpoint);
        try {
            String tmp = NAVIGATE.GetDataFromPreviousActivity("TESTPOINT");
            data = new Gson().fromJson(tmp, new TypeToken<TestPoint>(){}.getType());
            isEdit = data != null;
        }catch (Exception e)
        {

        }

        String tmp = NAVIGATE.GetDataFromPreviousActivity("ROUTE");
        route = new Gson().fromJson(tmp, new TypeToken<Routes>(){}.getType());

        btnDelete = findViewById(R.id.btn_delete);
        inputName = findViewById(R.id.input_name);
        inputLatitude = findViewById(R.id.input_latitude);
        inputLongitude = findViewById(R.id.input_longitude);
        inputNotes = findViewById(R.id.input_notes);
        inputKpLocation = findViewById(R.id.input_kp_location);
        inputNativePipa = findViewById(R.id.input_native_pipa);
        inputAnoda = findViewById(R.id.input_anoda);
        inputProteksi = findViewById(R.id.input_proteksi);
        inputAnodaPower = findViewById(R.id.input_anoda_power);
        inputSoilResistivity = findViewById(R.id.input_resistivity);
        inputPh = findViewById(R.id.input_ph);


        btnDelete.setVisibility(View.GONE);
        if(isEdit)
        {
            btnDelete.setVisibility(View.VISIBLE);
            setValueOf(inputName,data.getName());
            setValueOf(inputLongitude,data.getLongitude());
            setValueOf(inputLatitude,data.getLatitude());
            setValueOf(inputNotes,data.getNotes());
            setValueOf(inputKpLocation,data.getKpLocation());
            setValueOf(inputNativePipa,data.getNativePipe());
            setValueOf(inputProteksi,data.getProtection());
            setValueOf(inputAnodaPower,data.getAnodePower());
            setValueOf(inputSoilResistivity,data.getSoilResistivity());
            setValueOf(inputPh,data.getPh());
        }

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialDialog mDialog = new MaterialDialog.Builder(CRUDTestPointActivity.this)
                        .setTitle("Hapus?")
                        .setMessage("Apakah kamu yakin akan menghapus data ini?")
                        .setCancelable(false)
                        .setPositiveButton("Delete", R.drawable.ic_delete_16, new MaterialDialog.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialogInterface, int which) {
                                API.DeleteTestPoint(data, new IApiListener() {
                                    @Override
                                    public void onStart() {
                                        LOADER.Show();
                                    }

                                    @Override
                                    public void onSuccess(String result, String message) {
                                        boolean isSuccess = Formatter.FromJson(result);
                                        if (isSuccess) {
                                            NOTIFY.ShowToast(data.getName() + " berhasil dihapus");
                                            finish();
                                        } else
                                            NOTIFY.ShowToast("Tidak dapat menghapus rute");

                                    }

                                    @Override
                                    public void onFailure(String message) {

                                    }

                                    @Override
                                    public void onUnauthenticated() {

                                    }

                                    @Override
                                    public void onEnd() {
                                        LOADER.Dismiss();
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
        btnSubmit = findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data = isEdit ? data : new TestPoint();
                try {
                    data.setName(stringValueOf(inputName));
                    data.setLatitude(numberValueOf(inputLatitude));
                    data.setLongitude(numberValueOf(inputLongitude));
                    data.setNotes(stringValueOf(inputNotes));
                    data.setKpLocation(numberValueOf(inputKpLocation));
                    data.setNativePipe(numberValueOf(inputNativePipa));
                    data.setAnode(numberValueOf(inputAnoda));
                    data.setProtection(numberValueOf(inputProteksi));
                    data.setAnodePower(numberValueOf(inputAnodaPower));
                    data.setSoilResistivity(numberValueOf(inputSoilResistivity));
                    data.setPh(numberValueOf(inputPh));
                    data.setUser(FILE.Profile());
                    data.setUserId(FILE.Profile().getId());
                    data.setRoute(route);
                    data.setRouteId(route.getId());
                    data.setLandCorrosivity("FORMULA");

                    if(isEdit)
                    {
                        API.EditTestPoint(data,route, new IApiListener() {
                            @Override
                            public void onStart() {

                            }

                            @Override
                            public void onSuccess(String result, String message) {
                                boolean isSuccess = Formatter.FromJson(result);
                                if (isSuccess) {
                                    NOTIFY.ShowToast(data.getName() + " berhasil diedit");
                                    finish();
                                } else
                                    NOTIFY.ShowToast("Silahkan lengkapi data dengan benar");

                            }

                            @Override
                            public void onFailure(String message) {
                                NOTIFY.ShowToast(message);
                            }

                            @Override
                            public void onUnauthenticated() {

                            }

                            @Override
                            public void onEnd() {

                            }
                        },!Networker.isNetworkAvailable(CRUDTestPointActivity.this));
                    }
                    else {
                        API.AddTestPoint(data,route, new IApiListener() {
                            @Override
                            public void onStart() {

                            }

                            @Override
                            public void onSuccess(String result, String message) {
                                boolean isSuccess = Formatter.FromJson(result);
                                if (isSuccess) {
                                    NOTIFY.ShowToast(data.getName() + " berhasil ditambahkan");
                                    finish();
                                } else
                                    NOTIFY.ShowToast("Silahkan lengkapi data dengan benar");

                            }

                            @Override
                            public void onFailure(String message) {
                                NOTIFY.ShowToast(message);
                            }

                            @Override
                            public void onUnauthenticated() {

                            }

                            @Override
                            public void onEnd() {

                            }
                        },!Networker.isNetworkAvailable(CRUDTestPointActivity.this));
                    }
                }catch (Exception e)
                {
                    NOTIFY.ShowToast("Data tdak diinput dengan benar");
                }
            }
        });
    }

    private String stringValueOf(EditText input)
    {
        if(input.getText().toString().equals("") || input.getText().toString().equals(null))
            return "-";
        else
            return input.getText().toString();
    }

    private double numberValueOf(EditText input)
    {
        if(input.getText().toString().equals("") || input.getText().toString().equals(null))
            return 0;
        else {
            try {
                return Double.parseDouble(input.getText().toString());
            }catch (Exception e){
                return 0;
            }
        }
    }

    private void setValueOf(EditText input, String val)
    {
        if(!val.equals("-"))
            input.setText(val);
    }

    private void setValueOf(EditText input, Double val)
    {
        if(!val.toString().equals("0.0"))
            input.setText(val.toString());
    }
}
