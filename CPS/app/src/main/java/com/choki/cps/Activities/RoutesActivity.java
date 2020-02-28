package com.choki.cps.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.choki.cps.Adapters.RouteAdapter;
import com.choki.cps.Interfaces.IApiListener;
import com.choki.cps.Models.Routes;
import com.choki.cps.R;
import com.choki.cps.Utilities.Formatter;

import java.util.ArrayList;
import java.util.List;

import com.choki.cps.Utilities.Loader;
import com.choki.cps.Utilities.Networker;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class RoutesActivity extends MasterActivity {

    RecyclerView listView;
    RouteAdapter adapter;
    List<Routes> data;
    Button btnCreateRoute;
    SwipeRefreshLayout refreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes);
        listView = findViewById(R.id.list_item);
        btnCreateRoute = findViewById(R.id.btn_createroute);
        refreshLayout = findViewById(R.id.swipe_container);
        data = new ArrayList<>();
        adapter = new RouteAdapter(data,this);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LoadData();
            }
        });

        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        listView.setLayoutManager(new LinearLayoutManager((this)));

        btnCreateRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NAVIGATE.ToPage(CRUDRouteActivity.class,false);
            }
        });

        /*ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.DOWN | ItemTouchHelper.UP) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int position = viewHolder.getAdapterPosition();
                NAVIGATE.ToPage(CRUDRouteActivity.class,false,"ROUTE",data.get(position));
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

    void LoadData()
    {
        data.clear();
        if(Networker.isNetworkAvailable(this)) {
            API.GetRoutes(new IApiListener() {
                @Override
                public void onStart() {
                    //if(data.size() == 0)
                    //LOADER.Show();
                }

                @Override
                public void onSuccess(String result, String message) {
                    try {
                        List<Routes> tmp = new ArrayList<>();
                        Gson gson = new Gson();
                        tmp = gson.fromJson(result, new TypeToken<ArrayList<Routes>>() {
                        }.getType());
                        if (tmp != null) {
                            for (Routes item : tmp) {
                                data.add(item);
                            }

                            adapter.notifyDataSetChanged();
                        }
                    } catch (Exception e) {
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
                    //LOADER.Dismiss();
                    refreshLayout.setRefreshing(false);
                }
            });
        }

        List<Routes> offlineData = FILE.LoadRoutes();
        for (Routes item : offlineData) {
            data.add(item);
        }

        adapter.notifyDataSetChanged();
        if(Networker.isNetworkAvailable(this))
            Sync();
        refreshLayout.setRefreshing(false);
    }
    void Edit(final Routes item)
    {
        API.EditRoute(item, new IApiListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(String result, String message) {
                boolean isSuccess = Formatter.FromJson(result);
                if (isSuccess) {
                    NOTIFY.ShowToast(item.getName() + " berhasil disinkronasi");
                    FILE.RemoveRouteOffline(item);

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
    void Add(final Routes item)
    {
        API.AddRoute(item, new IApiListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(String result, String message) {
                boolean isSuccess = Formatter.FromJson(result);
                if (isSuccess) {
                    NOTIFY.ShowToast(item.getName() + " berhasil disinkronasi");
                    FILE.RemoveRouteOffline(item);
                    for (Routes route : data) {
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
        }, false);
    }
    void Sync()
    {
        List<Routes> offlineData = FILE.LoadRoutes();
        for (final Routes item : offlineData) {
            if(item.isEdit())
            {
                boolean isExistInServer = true;
                for (Routes itemroute :data) {
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
