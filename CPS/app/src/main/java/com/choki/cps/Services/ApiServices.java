package com.choki.cps.Services;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.choki.cps.Activities.MasterActivity;
import com.choki.cps.BuildConfig;
import com.choki.cps.Interfaces.IApiListener;
import com.choki.cps.Models.Login;
import com.choki.cps.Models.Routes;
import com.choki.cps.Models.TestPoint;
import com.choki.cps.Models.TestPointImage;
import com.choki.cps.Models.User;
import com.choki.cps.Utilities.InputStreamVolleyRequest;
import com.choki.cps.Utilities.Networker;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Observable;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;


interface Api {
    @Multipart
    @POST("Files/UploadImage")
    Call<UploadResult> upload(@Part MultipartBody.Part part);
}

 class UploadResult {
    @SerializedName("link")
    public String link;

}

public class ApiServices {
    String apiEndPoint =  "http://167.71.201.121/api"; //"http://192.168.1.39/api"
    MasterActivity context;
    RequestQueue queue;
    public ApiServices(MasterActivity activity)
    {
        context = activity;
        queue = Volley.newRequestQueue(context);
    }

    void Execute(int method, final String url, final IApiListener listener , final Object param , final boolean saveToCache)
    {
        /*if(!Networker.isNetworkAvailable(context)) {
            String cache = context.FILE.LoadFromCache(url);

            listener.onStart();
            if(cache == null)
                listener.onFailure("no cache found");
            else
                listener.onSuccess(cache,"loaded from cache");
            listener.onEnd();
            return;
        }*/

        Gson gson = new Gson();
        JSONObject jsonObject = null;
        try {
            String jsonData = gson.toJson(param);
            jsonObject = new JSONObject(jsonData);

        } catch (JSONException e) {

        }

        String fullUrl = apiEndPoint + url;
        listener.onStart();
        JsonObjectRequest request = new JsonObjectRequest(method, fullUrl,jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        if(response != null)
                        {
                            if(response.has("content"))
                            {
                                try {
                                    if(response.getBoolean("isLoginRequired"))
                                    {
                                        listener.onUnauthenticated();
                                    }
                                    String content = response.getString("content");
                                    if(content.equals("[]"))
                                       content = "";
                                    String message = response.getString("message");
                                    if(saveToCache)
                                        context.FILE.SaveToCache(url,content);
                                    listener.onSuccess(content,message);
                                } catch (JSONException e) {
                                    listener.onFailure(e.getMessage());
                                }
                            }
                            else
                            {
                                listener.onFailure("Internal server error");
                            }
                        }
                        else
                        {
                            listener.onFailure("Internal server error");
                        }
                        listener.onEnd();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //listener.onFailure(error.getMessage());
                listener.onEnd();
            }
        }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/json; charset=utf-8");
                if(context.FILE.IsLogin())
                {
                    params.put("token", context.FILE.Profile().getToken());
                }
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(24000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);
    }

    private File createTempFile(Bitmap bitmap) {
        File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                , System.currentTimeMillis() +"_image.png");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.PNG,0, bos);
        byte[] bitmapdata = bos.toByteArray();
        //write the bytes in file

        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
    public void Upload(final Bitmap image, final IApiListener listener)
    {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(apiEndPoint + "/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build();

        Api service = retrofit.create(Api.class);

        listener.onStart();
        // Calling '/api/users/2'
        byte[] params = getFileDataFromDrawable(image);
        File file = createTempFile(image);
        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), reqFile);


        Call<UploadResult> fileUpload = service.upload(body);
        fileUpload.enqueue(new Callback<UploadResult>() {
            @Override
            public void onResponse(Call<UploadResult> call, retrofit2.Response<UploadResult> response) {
                listener.onEnd();
                if(response.isSuccessful())
                {
                    listener.onSuccess(response.body().link,"Success");
                }
                else        {
                    listener.onFailure(String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<UploadResult> call, Throwable t) {
                listener.onEnd();
                listener.onFailure(t.getMessage());
            }
        });

    }

    public void download(final Routes route)
    {
        String fullUrl = apiEndPoint + "/Files/DownloadReport?routeId=" + route.getId();
        InputStreamVolleyRequest request = new InputStreamVolleyRequest(Request.Method.GET, fullUrl,
                new Response.Listener<byte[]>() {
                    @Override
                    public void onResponse(byte[] response) {
                        // TODO handle the response
                        try {
                            if (response!=null) {

                                String name=  route.getField() + ".xlsx";

                                File file = new File(name);

                                if(!file.exists()){
                                    file.createNewFile();
                                }
                                FileOutputStream outputStream = new FileOutputStream(name,true);
                                context.NOTIFY.ShowToast(file.getPath());
                                outputStream.write(response);
                                outputStream.close();
                                try {
                                    Uri uri = null;
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                        uri = FileProvider.getUriForFile(Objects.requireNonNull(context.getApplicationContext()),
                                                BuildConfig.APPLICATION_ID + ".provider", file);
                                    } else {
                                        uri = Uri.fromFile(file);
                                    }
                                    Intent intent = new Intent(Intent.ACTION_VIEW);
                                    intent.setDataAndType(uri,"application/vnd.ms-excel");
                                    context.startActivity(intent);
                                } catch (ActivityNotFoundException e) {
                                    // no Activity to handle this kind of files
                                }
                                //Toast.makeText(context, "Download complete.", Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            Log.d("KEY_ERROR", "UNABLE TO DOWNLOAD FILE");
                            e.printStackTrace();
                        }
                    }
                } ,new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO handle the error
                error.printStackTrace();
            }
        }, null);
        request.setRetryPolicy(new DefaultRetryPolicy(24000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);
    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }


    public void GetRoutes(IApiListener listener)
    {
        Execute(Request.Method.GET,"/Routes/GetRoutes", listener , null , false);
    }

    public void GetTestPoints(int routeId, IApiListener listener)
    {
        Execute(Request.Method.GET,"/Routes/GetTestPoints?routeId=" + routeId, listener , routeId , false);
    }

    public void GetTestPointImages(int tpid, IApiListener listener)
    {
        Execute(Request.Method.GET,"/Routes/GetTestPointImages?testPointId=" + tpid, listener , tpid , false);
    }

    public void Login(Login user, IApiListener listener)
    {
        Execute(Request.Method.POST,"/Users/Login", listener, user , true);
    }

    public void Register(User user, IApiListener listener)
    {
        Execute(Request.Method.POST,"/Users/Register", listener, user , true);
    }

    public void Logout(User user, IApiListener listener)
    {
        Execute(Request.Method.POST,"/Users/Logout", listener, user , true);
    }

    public void AddRoute(Routes route, IApiListener listener , boolean offlineMode)
    {
        if(offlineMode) {
            context.FILE.AddRouteOffline(route,true);
            context.NOTIFY.ShowToast(route.getName() + " disimpan dalam mode offline");
        }
        else
            Execute(Request.Method.POST,"/Routes/AddRoute", listener, route , false);
    }

    public void EditRoute(Routes route, IApiListener listener, boolean offlineMode)
    {
        if(offlineMode) {
            List<Routes> tmp = context.FILE.LoadRoutes();

            if(context.FILE.EditRouteOffline(route)) {
                context.NOTIFY.ShowToast(route.getName() + " disimpan dalam mode offline");
            }
            else {
                context.FILE.AddRouteOffline(route,false);
                context.FILE.EditRouteOffline(route);
                context.NOTIFY.ShowToast(route.getName() + " disimpan dalam mode offline");
            }
        }
        else
        Execute(Request.Method.POST,"/Routes/EditRoute", listener, route , false);
    }

    public void DeleteRoute(Routes route, IApiListener listener)
    {
        Execute(Request.Method.POST,"/Routes/DeleteRoute", listener, route , false);
    }

    public void AddTestPoint(TestPoint data,Routes route, IApiListener listener, boolean offlineMode)
    {
        if(offlineMode) {
            context.FILE.AddTestPointOffline(data,route,true);
            context.NOTIFY.ShowToast(data.getName() + " disimpan dalam mode offline");
            context.finish();
        }
        else
            Execute(Request.Method.POST,"/Routes/AddTestPoint", listener, data , false);
    }

    public void EditTestPoint(TestPoint data,Routes route, IApiListener listener, boolean offlineMode)
    {
        if(offlineMode) {
            List<TestPoint> tmp = context.FILE.LoadTestPoints(route);

            if(context.FILE.EditTestPointOffline(data,route)) {
                context.NOTIFY.ShowToast(data.getName() + " disimpan dalam mode offline");
            }
            else {
                context.FILE.AddTestPointOffline(data,route,false);
                context.FILE.EditTestPointOffline(data,route);
                context.NOTIFY.ShowToast(data.getName() + " disimpan dalam mode offline");
            }
        }
        else
        Execute(Request.Method.POST,"/Routes/EditTestPoint", listener, data , false);
    }

    public void DeleteTestPoint(TestPoint data, IApiListener listener)
    {
        Execute(Request.Method.POST,"/Routes/DeleteTestPoint", listener, data , false);
    }

    public void AddTestPointImage(TestPointImage data, IApiListener listener)
    {
        Execute(Request.Method.POST,"/Routes/AddTpImage", listener, data , false);
    }

    public void DeleteTestPointImage(TestPointImage data, IApiListener listener)
    {
        Execute(Request.Method.POST,"/Routes/DeleteTpImage", listener, data , false);
    }
}
