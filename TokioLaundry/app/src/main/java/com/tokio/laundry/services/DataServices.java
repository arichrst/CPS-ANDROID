package com.tokio.laundry.services;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DataServices<T>{
    private FirebaseDatabase database;
    private DatabaseReference dbreference;
    private String tableName;

    public  DataServices(String name)
    {
        tableName = name;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.setPersistenceEnabled(true);
        database.setPersistenceCacheSizeBytes(1024*1024*10);
        dbreference = database.getReference(tableName);
        dbreference.keepSynced(true);
    }

    public void Add(T data, String id)
    {
        dbreference.child(id).setValue(data);
        dbreference.
    }

    public void Edit(T data)
    {
        dbreference.setValue(data);
    }
}
