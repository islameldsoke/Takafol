package com.example.moham.takafol;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.moham.takafol.Adapters.SuggestAdapter;
import com.example.moham.takafol.Models.suggestModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class SuggestToFollow extends AppCompatActivity {
    String url;
    RecyclerView recyclerView;
    List<suggestModel> suggest_list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggest_to_follow);

        String user_id = getSharedPreferences("user_id", 0).getString("user_id", "");
        url = "http://takaful.16mb.com/Api.php?follow=1&&UID="+user_id;
        fetchSuggest();
    }

    private void fetchSuggest() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(SuggestToFollow.this, response, Toast.LENGTH_LONG).show();

                if (response != null) {
                    recyclerView = (RecyclerView) findViewById(R.id.recyclerSuggest);
                    RecyclerView.LayoutManager layoutManager = new
                            LinearLayoutManager(SuggestToFollow.this, LinearLayoutManager.VERTICAL, false);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(new SuggestAdapter(suggest_list, SuggestToFollow.this));
                    try {
                        JSONArray posts = new JSONArray(response);
                        for (int i = 0; i < posts.length(); i++) {
                            JSONObject c = posts.getJSONObject(i);

                            //get Data From API

                            String userName = c.getString("username");
                            String userImage = c.getString("image");
                            String followId = c.getString("followed_id");
                            Log.d("MAS", userImage);


                            //set Data To Model
                            suggestModel suggest = new suggestModel();

                            suggest.setUserName(userName);
                            suggest.setUserImage(userImage);
                            suggest.setFollowId(followId);

                            suggest_list.add(suggest);
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SuggestToFollow.this, "error in posts", Toast.LENGTH_LONG).show();

                Toast.makeText(SuggestToFollow.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        Volley.newRequestQueue(SuggestToFollow.this).add(stringRequest);


    }
}
