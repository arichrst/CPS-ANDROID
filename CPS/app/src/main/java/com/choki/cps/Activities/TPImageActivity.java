package com.choki.cps.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.choki.cps.Adapters.TPImageAdapter;
import com.choki.cps.Adapters.TestPointAdapter;
import com.choki.cps.Interfaces.IApiListener;
import com.choki.cps.Models.Routes;
import com.choki.cps.Models.TestPoint;
import com.choki.cps.Models.TestPointImage;
import com.choki.cps.R;
import com.choki.cps.Utilities.Formatter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shreyaspatil.MaterialDialog.MaterialDialog;
import com.shreyaspatil.MaterialDialog.interfaces.DialogInterface;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class TPImageActivity extends MasterActivity {

    RecyclerView listView;
    TPImageAdapter adapter;
    List<TestPointImage> data;
    Button btnCreateTestPointImage;
    SwipeRefreshLayout refreshLayout;
    TestPoint testpoint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tpimage);
        listView = findViewById(R.id.list_item);

        NOTIFY.ShowToast("Menambahkan gambar tidak disarankan dalam mode offline");
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

        String tmp = NAVIGATE.GetDataFromPreviousActivity("TESTPOINT");
        testpoint = new Gson().fromJson(tmp, new TypeToken<TestPoint>(){}.getType());

        btnCreateTestPointImage = findViewById(R.id.btn_createtestpoint);
        refreshLayout = findViewById(R.id.swipe_container);
        data = new ArrayList<>();
        adapter = new TPImageAdapter(data,this);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LoadData();
            }
        });

        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        listView.setLayoutManager(new LinearLayoutManager((this)));

        btnCreateTestPointImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MaterialDialog mDialog = new MaterialDialog.Builder(TPImageActivity.this)
                        .setTitle("Sumber Gambar")
                        .setMessage("Pilih gambar darimana?")
                        .setCancelable(false)
                        .setPositiveButton("Kamera", R.drawable.ic_photo_camera_16, new MaterialDialog.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialogInterface, int which) {
                                NAVIGATE.ToPage(CameraActivity.class,false,"TESTPOINT" , testpoint );
                                dialogInterface.dismiss();
                            }
                        })
                        .setNegativeButton("Gallery", R.drawable.ic_gallery_16, new MaterialDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                Intent intent = new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                /*intent.putExtra("crop", "true");
                                intent.putExtra("aspectX", 1);
                                intent.putExtra("aspectY", 1);
                                intent.putExtra("outputX", 600);
                                intent.putExtra("outputY", 600);
                                intent.putExtra("scale", true);*/
                                String[] mimeTypes = {"image/jpeg", "image/png"};
                                intent.putExtra("return-data", true);
                                //intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
                                //intent.putExtra("return-data", true);
                                startActivityForResult(intent, 1);
                                dialogInterface.dismiss();
                            }
                        })
                        .build();

                // Show Dialog
                mDialog.show();
            }
        });

        /*ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.DOWN | ItemTouchHelper.UP) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                final int position = viewHolder.getAdapterPosition();
                MaterialDialog mDialog = new MaterialDialog.Builder(TPImageActivity.this)
                        .setTitle("Hapus?")
                        .setMessage("Apakah kamu yakin akan menghapus data ini?")
                        .setCancelable(false)
                        .setPositiveButton("Delete", R.drawable.ic_delete_16, new MaterialDialog.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialogInterface, int which) {
                                API.DeleteTestPointImage(data.get(position), new IApiListener() {
                                    @Override
                                    public void onStart() {
                                        LOADER.Show();
                                    }

                                    @Override
                                    public void onSuccess(String result, String message) {
                                        boolean isSuccess = Formatter.FromJson(result);
                                        if (isSuccess) {
                                            NOTIFY.ShowToast(data.get(position) + " berhasil hapus");
                                            LoadData();
                                        } else
                                            NOTIFY.ShowToast("Ada kesalahan dalam penghapusan gambar");
                                        dialogInterface.dismiss();
                                    }

                                    @Override
                                    public void onFailure(String message) {
                                        NOTIFY.ShowToast(message);
                                        dialogInterface.dismiss();
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

                // Show Dialog
                mDialog.show();

                //NAVIGATE.ToPage(CRUDTestPointActivity.class,false,"TESTPOINT",data.get(position) , "ROUTE" , route);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(listView);*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        LoadData();
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable final Intent dataIntent) {
        if (resultCode != RESULT_OK) {
            super.onActivityResult(requestCode,resultCode,dataIntent);
            return;
        }
        if (requestCode == 1) {
            final Bundle extras = dataIntent.getExtras();
            if (extras != null) {
                //Get image

                Uri selectedImage = dataIntent.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();


                Bitmap newProfilePic =  BitmapFactory.decodeFile(picturePath);
                newProfilePic = scaleBitmap(newProfilePic);
                API.Upload(newProfilePic, new IApiListener() {
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
                        tmpData.setTestPoint(testpoint);
                        tmpData.setTestPointId(testpoint.getId());
                        API.AddTestPointImage(tmpData, new IApiListener() {
                            @Override
                            public void onStart() {
                                if(data.size() == 0)
                                    LOADER.Show();
                            }

                            @Override
                            public void onSuccess(String result, String message) {
                                boolean isSuccess = Formatter.FromJson(result);
                                if (isSuccess) {
                                    NOTIFY.ShowToast("gambar berhasil ditambahkan");
                                    LoadData();
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
        }
    }

    public void LoadData()
    {
        data.clear();
        API.GetTestPointImages(testpoint.getId(), new IApiListener() {
            @Override
            public void onStart() {
                LOADER.Show();

            }

            @Override
            public void onSuccess(String result, String message) {
                try {
                    List<TestPointImage> tmp = new ArrayList<>();
                    Gson gson = new Gson();
                    tmp = gson.fromJson(result, new TypeToken<ArrayList<TestPointImage>>() {
                    }.getType());
                    if (tmp != null) {
                        for (TestPointImage item : tmp) {
                            data.add(item);
                        }
                        adapter.notifyDataSetChanged();
                    }
                }catch (Exception e){}
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
                LOADER.Dismiss();refreshLayout.setRefreshing(false);
            }
        });
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
