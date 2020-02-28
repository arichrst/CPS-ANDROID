package com.choki.cps.Services;

import com.choki.cps.Activities.MasterActivity;
import com.choki.cps.Models.Routes;
import com.choki.cps.Models.TestPoint;
import com.choki.cps.Models.User;
import com.choki.cps.Utilities.Formatter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FileServices {
    MasterActivity context;
    String fileExtension = ".cps";
    public FileServices(MasterActivity activity){
        context = activity;
    }

    public void SaveToCache(String filename , Object data)
    {
        filename = filename.replaceAll("/","");
        String fullPath = context.getFilesDir() + "/" + filename + fileExtension;
        File file = new File(context.getFilesDir() + "/cache/");

        if(!file.exists()){
            file.mkdir();
        }

        File gpxfile = new File(file, filename+fileExtension);
        FileWriter writer = null;
        try {
            writer = new FileWriter(gpxfile);
            writer.append(Formatter.ToJson(data));
            writer.flush();
            writer.close();
        } catch (IOException e) {
        }
    }


    public String LoadFromCache(String filename)
    {
        try {
            filename = filename.replaceAll("/","");
            File file = new File(context.getFilesDir() + "/cache/",filename + fileExtension);

//Read text from file
            StringBuilder text = new StringBuilder();


            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                text.append(line);
            }
            br.close();
            String result =  text.toString();
            if(result.startsWith("\""))
                result = result.substring(1,result.length()-1).replace("\\","");
            return  result;
        } catch (FileNotFoundException e) {

        } catch (IOException e) {

        }
        return  null;
    }

    public <T> T LoadFromCache(Class<T> type, String filename)
    {
        try {
            String jsonData = LoadFromCache(filename);
            Gson gson = new Gson();
            T result = gson.fromJson(jsonData.toString(), type);
            return  result;
        } catch (Exception e) {
            //context.NOTIFY.ShowToast(e.getMessage());
        }
        return  null;
    }

    public void SaveProfile(User user)
    {
        SaveToCache("Users/Login",user);
    }

    public User Profile()
    {
        return  LoadFromCache(User.class,"Users/Login");
    }

    public boolean IsLogin()
    {
        return Profile() != null;
    }



    public void SaveRoutes(List<Routes> data)
    {
        String filename = "Routes/Offline";
        filename = filename.replaceAll("/","");
        String fullPath = context.getFilesDir() + "/" + filename + fileExtension;
        File file = new File(context.getFilesDir() + "/cache/");

        if(!file.exists()){
            file.mkdir();
        }

        File gpxfile = new File(file, filename+fileExtension);
        FileWriter writer = null;
        try {
            writer = new FileWriter(gpxfile);
            Gson gson = new Gson();
            String json = gson.toJson(data);
            writer.append(json);
            writer.flush();
            writer.close();
        } catch (IOException e) {
        }
    }

    public List<Routes> LoadRoutes()
    {
        String result = LoadFromCache("Routes/Offline");
        List<Routes> tmp = new ArrayList<>();
        try {
            Gson gson = new Gson();
            tmp = gson.fromJson(result, new TypeToken<ArrayList<Routes>>() {
            }.getType());
        }catch (Exception e){}
        if(tmp == null)
            return  new ArrayList<>();
        return  tmp;
    }

    public void AddRouteOffline(Routes data , boolean generateNewID)
    {
        if(generateNewID)
            data.setId(Math.abs(new Random().nextInt()));
        data.setOffline(true);
        List<Routes> tmp = LoadRoutes();
        tmp.add(data);
        SaveRoutes(tmp);
    }

    public boolean EditRouteOffline(Routes data)
    {
        data.setOffline(true);
        List<Routes> tmp = LoadRoutes();
        Routes target = null;
        for (Routes item:tmp) {
            if(item.getId() == data.getId())
            {
                target = item;
                break;
            }
        }
        if(target != null)
        {
            tmp.remove(target);
            data.setEdit(true);
            tmp.add(data);
            SaveRoutes(tmp);
            return  true;
        }
        return  false;
    }

    public void RemoveRouteOffline(Routes data)
    {
        List<Routes> tmp = LoadRoutes();
        Routes target = null;
        for (Routes item:tmp) {
            if(item.getId() == data.getId())
            {
                target = item;
            }
        }
        tmp.remove(target);
        SaveRoutes(tmp);
    }



    public void SaveTestPoint(List<TestPoint> data, Routes route)
    {
        String filename = "TestPoint/Offline"+ String.valueOf(route.getId());
        filename = filename.replaceAll("/","");
        String fullPath = context.getFilesDir() + "/" + filename + fileExtension;
        File file = new File(context.getFilesDir() + "/cache/");

        if(!file.exists()){
            file.mkdir();
        }

        File gpxfile = new File(file, filename+fileExtension);
        FileWriter writer = null;
        try {
            writer = new FileWriter(gpxfile);
            Gson gson = new Gson();
            String json = gson.toJson(data);
            writer.append(json);
            writer.flush();
            writer.close();
        } catch (IOException e) {
        }
    }

    public List<TestPoint> LoadTestPoints(Routes route)
    {
        String result = LoadFromCache("TestPoint/Offline"+String.valueOf(route.getId()));
        List<TestPoint> tmp = new ArrayList<>();
        try {
            Gson gson = new Gson();
            tmp = gson.fromJson(result, new TypeToken<ArrayList<TestPoint>>() {
            }.getType());
        }catch (Exception e){}
        if(tmp == null)
            return  new ArrayList<>();
        return  tmp;
    }

    public void AddTestPointOffline(TestPoint data , Routes route , boolean generateNewID)
    {
        if(generateNewID)
            data.setId(Math.abs(new Random().nextInt()));
        data.setOffline(true);
        List<TestPoint> tmp = LoadTestPoints(route);
        tmp.add(data);
        SaveTestPoint(tmp,route);
    }

    public boolean EditTestPointOffline(TestPoint data, Routes route)
    {
        data.setOffline(true);
        List<TestPoint> tmp = LoadTestPoints(route);
        TestPoint target = null;
        for (TestPoint item:tmp) {
            if(item.getId() == data.getId())
            {
                target = item;
                break;
            }
        }
        if(target != null)
        {
            tmp.remove(target);
            data.setEdit(true);
            tmp.add(data);
            SaveTestPoint(tmp,route);
            return  true;
        }
        return  false;
    }

    public void RemoveTestPointOffline(TestPoint data, Routes route)
    {
        List<TestPoint> tmp = LoadTestPoints(route);
        TestPoint target = null;
        for (TestPoint item:tmp) {
            if(item.getId() == data.getId())
            {
                target = item;
            }
        }
        tmp.remove(target);
        SaveTestPoint(tmp,route);
    }
}
