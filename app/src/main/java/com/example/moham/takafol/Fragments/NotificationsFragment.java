package com.example.moham.takafol.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.moham.takafol.Adapters.notificationAdapter;
import com.example.moham.takafol.Models.notificationModel;
import com.example.moham.takafol.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NotificationsFragment extends Fragment {

    RecyclerView recyclerView;

    List<notificationModel> posts_list = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        SharedPreferences sharedPreferences=this.getActivity().getSharedPreferences("user_id",0);
        String userId = sharedPreferences.getString("user_id","");



        String url = "http://takaful.16mb.com/Api.php?NUID="+userId;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getContext(), response, Toast.LENGTH_LONG).show();

                if (response != null) {
                    recyclerView = (RecyclerView) getActivity().findViewById(R.id.notificationRecyclerView);
                    RecyclerView.LayoutManager layoutManager = new
                            LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(new notificationAdapter(posts_list, getActivity()));
                    try {
                        JSONArray posts = new JSONArray(response);
                        for (int i = 0; i < posts.length(); i++) {
                            JSONObject c = posts.getJSONObject(i);

                            //get Data From API
                            String userImage = c.getString("user_image");
                            String notificationContent = c.getString("notify_type");
                            String notificationDate = c.getString("notify_date");
                            String postId = c.getString("post_id");
                            String user_id = c.getString("by_user_id");

                            //set Data To Model
                            notificationModel post = new notificationModel();

                            post.setUserImage(userImage);
                            post.setNotificationType(notificationContent);
                            post.setNotificationUserId(user_id);
                            post.setNotificationDate(notificationDate);
                            post.setPostId(postId);


                            posts_list.add(post);
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "error in posts", Toast.LENGTH_LONG).show();

                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        Volley.newRequestQueue(getContext()).add(stringRequest);




        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notifications, container, false);
    }





}
