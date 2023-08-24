package com.example.news;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.nativead.NativeAd;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<String> items;
    Boolean isScrolling = false;
    int currentItem, totalItem, ScrolledOutItem;
    CustomAdapter customAdapter;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        items = new ArrayList<>();
        loadMeme();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                {
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItem = linearLayoutManager.getChildCount();
                totalItem = linearLayoutManager.getItemCount();
                ScrolledOutItem = linearLayoutManager.findFirstVisibleItemPosition();
                if (isScrolling && (currentItem + ScrolledOutItem == totalItem))
                {
                    isScrolling = false;
                    loadMore();

                }
            }
        });

    }
    public void loadMeme()
    {

        String url = "https://meme-api.com/gimme/5";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, response -> {
                    try {
                        int count = response.getInt("count");
                        JSONArray memes = response.getJSONArray("memes");
                        for (int i = 0; i < count; i++) {
                            JSONObject jsonObject = memes.getJSONObject(i);
                            items.add(jsonObject.getString("url"));
                        }
                        customAdapter = new CustomAdapter(items);
                        recyclerView.setAdapter(customAdapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show());

        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }
    public void loadMore()
    {
        ArrayList<String> arr = new ArrayList<>();
        String url = "https://meme-api.com/gimme/5";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, response -> {
                    try {
                        int count = response.getInt("count");
                        JSONArray memes = response.getJSONArray("memes");
                        for (int i = 0; i < count; i++) {
                            JSONObject jsonObject = memes.getJSONObject(i);
                            arr.add(jsonObject.getString("url"));
                        }
                        customAdapter.update(arr);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show());

        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }
}

