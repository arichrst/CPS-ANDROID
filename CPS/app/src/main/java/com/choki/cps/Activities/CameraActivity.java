package com.choki.cps.Activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.camerakit.CameraKit;
import com.camerakit.CameraKitView;
import com.choki.cps.Interfaces.IApiListener;
import com.choki.cps.Models.Routes;
import com.choki.cps.Models.TestPoint;
import com.choki.cps.Models.TestPointImage;
import com.choki.cps.R;
import com.choki.cps.Utilities.Formatter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class CameraActivity extends MasterActivity {

    private CameraKitView cameraKitView;
    TestPoint data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        try {
            String tmp = NAVIGATE.GetDataFromPreviousActivity("TESTPOINT");
            data = new Gson().fromJson(tmp, new TypeToken<TestPoint>(){}.getType());
        }catch (Exception e)
        {
        }

        cameraKitView = findViewById(R.id.camera);

        Button btnCapture = findViewById(R.id.btn_capture);
        btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cameraKitView.captureImage(new CameraKitView.ImageCallback() {
                    @Override
                    public void onImage(final CameraKitView cameraKitView, byte[] bytes) {

                        API.Upload(ByteArrayToBitmap(bytes), new IApiListener() {
                            @Override
                            public void onStart() {
                                LOADER.Show();
                            }

                            @Override
                            public void onSuccess(String result, String message) {
                                NOTIFY.ShowToast(result);
                                String link = "http://167.71.201.121" + result;
                                link = link.replace("\\", "");
                                TestPointImage tmpData = new TestPointImage();
                                tmpData.setLink(link);
                                tmpData.setNotes("-");
                                tmpData.setTestPoint(data);
                                tmpData.setTestPointId(data.getId());
                                API.AddTestPointImage(tmpData, new IApiListener() {
                                    @Override
                                    public void onStart() {
                                        LOADER.Show();
                                    }

                                    @Override
                                    public void onSuccess(String result, String message) {
                                        boolean isSuccess = Formatter.FromJson(result);
                                        if (isSuccess) {
                                            NOTIFY.ShowToast("gambar berhasil ditambahkan");
                                            finish();
                                        } else
                                            NOTIFY.ShowToast("Silahkan coba lagi");

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
                                        LOADER.Dismiss();
                                    }
                                });
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
                                LOADER.Dismiss();
                            }
                        });
                    }
                });
            }
        });
    }

    public Bitmap ByteArrayToBitmap(byte[] byteArray)
    {
        ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(byteArray);
        Bitmap bitmap = BitmapFactory.decodeStream(arrayInputStream);

        return scaleBitmap(bitmap);
    }

    public Bitmap scaleBitmap(Bitmap mBitmap) {
        float aspectRatio = mBitmap.getWidth() /
                (float) mBitmap.getHeight();
        int width = 600;
        int height = Math.round(width / aspectRatio);

        return Bitmap.createScaledBitmap(
                mBitmap, width, height, false);
        /*ByteArrayOutputStream stream = new ByteArrayOutputStream();

        mBitmap.compress(Bitmap.CompressFormat.JPEG,20,stream);

        byte[] byteArray = stream.toByteArray();
        return BitmapFactory.decodeByteArray(byteArray,0,byteArray.length);*/

    }

    @Override
    protected void onStart() {
        super.onStart();
        cameraKitView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cameraKitView.onResume();
    }

    @Override
    protected void onPause() {
        cameraKitView.onPause();
        super.onPause();
    }

    @Override
    protected void onStop() {
        cameraKitView.onStop();
        super.onStop();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        cameraKitView.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
