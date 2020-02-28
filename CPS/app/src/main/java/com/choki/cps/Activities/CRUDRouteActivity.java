package com.choki.cps.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.choki.cps.Interfaces.IApiListener;
import com.choki.cps.Models.Routes;
import com.choki.cps.R;
import com.choki.cps.Utilities.Formatter;
import com.choki.cps.Utilities.Navigator;
import com.choki.cps.Utilities.Networker;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shreyaspatil.MaterialDialog.MaterialDialog;
import com.shreyaspatil.MaterialDialog.interfaces.DialogInterface;

import java.util.ArrayList;

public class CRUDRouteActivity extends MasterActivity {

    EditText inputName , inputFromRegion , inputToRegion , inputDistance ,
    inputDiameter , inputField , inputProtectionCatodicType , inputAnodeMaterial ;

    Button btnSubmit , btnDelete;
    Routes data;
    Boolean isEdit = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crudroutes);
        try {
            String route = NAVIGATE.GetDataFromPreviousActivity("ROUTE");
            data = new Gson().fromJson(route, new TypeToken<Routes>(){}.getType());
            isEdit = data != null;
        }catch (Exception e)
        {

        }

        btnDelete = findViewById(R.id.btn_delete);
        inputName = findViewById(R.id.input_name);
        inputFromRegion = findViewById(R.id.input_from_region);
        inputToRegion = findViewById(R.id.input_to_region);
        inputDistance = findViewById(R.id.input_distance);
        inputDiameter = findViewById(R.id.input_diameter);
        inputField = findViewById(R.id.input_field);
        inputProtectionCatodicType = findViewById(R.id.input_protection_catodic_type);
        inputAnodeMaterial = findViewById(R.id.input_anode_material);

        btnDelete.setVisibility(View.GONE);
        if(isEdit)
        {
            btnDelete.setVisibility(View.VISIBLE);
            setValueOf(inputName,data.getName());
            setValueOf(inputFromRegion,data.getFromRegion());
            setValueOf(inputToRegion,data.getToRegion());
            setValueOf(inputDistance,data.getDistance());
            setValueOf(inputDiameter,data.getDiameter());
            setValueOf(inputField,data.getField());
            setValueOf(inputProtectionCatodicType,data.getProtectionCatodicType());
            setValueOf(inputAnodeMaterial,data.getAnodeMaterial());
        }

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialDialog mDialog = new MaterialDialog.Builder(CRUDRouteActivity.this)
                        .setTitle("Hapus?")
                        .setMessage("Apakah kamu yakin akan menghapus data ini?")
                        .setCancelable(false)
                        .setPositiveButton("Delete", R.drawable.ic_delete_16, new MaterialDialog.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialogInterface, int which) {
                                API.DeleteRoute(data, new IApiListener() {
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
                                        dialogInterface.dismiss();

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
                data = isEdit ? data : new Routes();
                try {

                    data.setName(stringValueOf(inputName));
                    data.setAnodeMaterial(stringValueOf(inputAnodeMaterial));
                    data.setAnodeMaterialTools("-");
                    data.setAnodeMaterialToolsBrand("-");
                    data.setCatodicProtectionTools("-");
                    data.setCatodicProtectionToolsBrand("-");
                    data.setDiameter(numberValueOf(inputDiameter));
                    data.setDiameterTools("-");
                    data.setDistance(numberValueOf(inputDistance));
                    data.setField(stringValueOf(inputField));
                    data.setFieldTools("-");
                    data.setFieldToolsBrand("-");
                    data.setFromRegion(stringValueOf(inputFromRegion));
                    data.setPipeLengthTools("-");
                    data.setUser(FILE.Profile());
                    data.setUserId(FILE.Profile().getId());
                    data.setToRegion(stringValueOf(inputToRegion));
                    data.setProtectionCatodicType(stringValueOf(inputProtectionCatodicType));
                    data.setPipeLengthToolsBrand("-");
                    data.setDiameterToolsBrand("-");

                    if(isEdit)
                    {
                        API.EditRoute(data, new IApiListener() {
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
                        },!Networker.isNetworkAvailable(CRUDRouteActivity.this));
                    }
                    else {


                        API.AddRoute(data, new IApiListener() {
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
                        },!Networker.isNetworkAvailable(CRUDRouteActivity.this));
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
