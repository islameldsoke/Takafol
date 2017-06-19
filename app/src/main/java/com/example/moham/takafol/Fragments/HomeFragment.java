package com.example.moham.takafol.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.moham.takafol.Adapters.ProfilePostsAdapter;
import com.example.moham.takafol.Models.Post;
import com.example.moham.takafol.PostActivity;
import com.example.moham.takafol.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    RecyclerView recyclerView;
    FloatingActionButton post;
    List<Post> posts_list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        post = (FloatingActionButton) view.findViewById(R.id.f1);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), PostActivity.class));
            }
        });

        String user_id = getActivity().getSharedPreferences("user_id", 0).getString("user_id", "");

        String url = "http://takaful.16mb.com/Api.php?HPUID=" + user_id;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getContext(), response, Toast.LENGTH_LONG).show();

                if (response != null) {
                    recyclerView = (RecyclerView) getActivity().findViewById(R.id.recyclerView);
                    RecyclerView.LayoutManager layoutManager = new
                            LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                    recyclerView.setLayoutManager(layoutManager);
                    try {
                        JSONArray posts = new JSONArray(response);
                        for (int i = 0; i < posts.length(); i++) {
                            JSONObject c = posts.getJSONObject(i);
                            String userImage = c.getString("user_image");
                            String userName = c.getString("user_name");
                            String post_content = c.getString("content");
                            String postDate = c.getString("post_date");
                            String postId = c.getString("post_id");
                            String user_id = c.getString("user_id");
                            String support_numbers = c.getString("supporters_number");
                            String share_number = c.getString("shares_number");
                            String comment_number = c.getString("comments_number");
                            String like_status = c.getString("like_status");

                            Post post = new Post();
                            post.setContent(post_content);
                            post.setUserId(user_id);
                            post.setUserName(userName);
                            post.setImage(userImage);
                            post.setCommentsNumber(comment_number + " تعليقات");
                            post.setSharesNumber(share_number + " مشاركات");
                            post.setSupportersNumber(support_numbers);
                            post.setPostDate(postDate);
                            post.setPostId(postId);
                            post.setLike_status(like_status);
                            Log.e("KH5", post.getLike_status());

                            posts_list.add(post);
                        }
                        recyclerView.setAdapter(new ProfilePostsAdapter(posts_list, getActivity()));


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

        return view;
    }
}
