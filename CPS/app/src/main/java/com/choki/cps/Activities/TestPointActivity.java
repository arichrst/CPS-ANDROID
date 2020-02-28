package com.choki.cps.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.choki.cps.Adapters.RouteAdapter;
import com.choki.cps.Adapters.TestPointAdapter;
import com.choki.cps.Interfaces.IApiListener;
import com.choki.cps.Models.Routes;
import com.choki.cps.Models.TestPoint;
import com.choki.cps.R;
import com.choki.cps.Utilities.Formatter;
import com.choki.cps.Utilities.Networker;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class TestPointActivity extends MasterActivity {

    RecyclerView listView;
    TestPointAdapter adapter;
    List<TestPoint> data;
    Button btnCreateTestPoint;
    SwipeRefreshLayout refreshLayout;
    Routes route;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testpoint);
        listView = findViewById(R.id.list_item);

        String tmp = NAVIGATE.GetDataFromPreviousActivity("ROUTE");
        route = new Gson().fromJson(tmp, new TypeToken<Routes>(){}.getType());

        btnCreateTestPoint = findViewById(R.id.btn_createtestpoint);
        refreshLayout = findViewById(R.id.swipe_container);
        data = new ArrayList<>();
        adapter = new TestPointAdapter(route,data,this);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LoadData();
            }
        });

        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        listView.setLayoutManager(new LinearLayoutManager((this)));

        btnCreateTestPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NAVIGATE.ToPage(CRUDTestPointActivity.class,false, "ROUTE" , route);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        LoadData();
    }

    void LoadData()
    {
        data.clear();
        if(Networker.isNetworkAvailable(this)) {
            API.GetTestPoints(route.getId(), new IApiListener() {
                @Override
                public void onStart() {
                }

                @Override
                public void onSuccess(String result, String message) {
                    try {
                        List<TestPoint> tmp = new ArrayList<>();
                        Gson gson = new Gson();
                        tmp = gson.fromJson(result, new TypeToken<ArrayList<TestPoint>>() {
                        }.getType());
                        if (tmp != null) {
                            for (TestPoint item : tmp) {
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
                    refreshLayout.setRefreshing(false);
                }
            });
        }

        List<TestPoint> offlineData = FILE.LoadTestPoints(route);
        for (TestPoint item : offlineData) {
            data.add(item);
        }

        adapter.notifyDataSetChanged();
        if(Networker.isNetworkAvailable(this))
            Sync();
        refreshLayout.setRefreshing(false);
    }
    void Edit(final TestPoint item)
    {
        API.EditTestPoint(item,route, new IApiListener() {
            @Override
            public void onStart() {
                LOADER.Show();
            }

            @Override
            public void onSuccess(String result, String message) {
                boolean isSuccess = Formatter.FromJson(result);
                if (isSuccess) {
                    NOTIFY.ShowToast(item.getName() + " berhasil disinkronasi");
                    FILE.RemoveTestPointOffline(item,route);

                    LoadData();
                }
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
        },false);

    }
    void Add(final TestPoint item)
    {
        API.AddTestPoint(item,route, new IApiListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(String result, String message) {
                boolean isSuccess = Formatter.FromJson(result);
                if (isSuccess) {
                    NOTIFY.ShowToast(item.getName() + " berhasil disinkronasi");
                    FILE.RemoveTestPointOffline(item,route);
                    for (TestPoint route : data) {
                        if (route.getId() == item.getId())
                            data.remove(route);
                    }
                    LoadData();
                }
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
        },false);
    }
    void Sync()
    {
        List<TestPoint> offlineData = FILE.LoadTestPoints(route);
        for (final TestPoint item : offlineData) {
            if(item.isEdit())
            {
                boolean isExistInServer = true;
                for (TestPoint itemroute :data) {
                    if(!itemroute.isOffline() && itemroute.getId() == item.getId())
                    {
                        isExistInServer = false;
                    }
                }
                if(isExistInServer)
                    Edit(item);
                else
                    Add(item);
            }
            else {
                Add(item);
            }
        }
    }
}
