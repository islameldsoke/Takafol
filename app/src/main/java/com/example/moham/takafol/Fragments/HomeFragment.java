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
import com.example.moham.takafol.Adapters.HomeAdapter;
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
    String user_id;

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

       loadHome();
        return view;
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        new HomeAdapter(posts_list,getActivity()).clear(posts_list);
//
//    }

    private void loadHome(){
        user_id = getActivity().getSharedPreferences("user_id", 0).getString("user_id", "");

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
                            String neededM = c.getString("needed_money");
                            String donatedM = c.getString("donated_money");
                            String user_email = c.getString("user_email");
                            String user_phone = c.getString("user_phone");
                            String trust_Status = c.getString("trust_status");
                            String trust_number = c.getString("trust_number");
                            String untrust_number = c.getString("untrust_number");

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
                            post.setDonated_money(donatedM);
                            post.setNeeded_money(neededM);
                            post.setUser_email(user_email);
                            post.setUser_phone(user_phone);
                            post.setTrust_number(trust_number);
                            post.setUntrust_number(untrust_number);
                            post.setTrust_status(trust_Status);
                            Log.e("KH5", post.getDonated_money());

                            posts_list.add(post);

                        }
                        recyclerView.setAdapter(new HomeAdapter(posts_list, getActivity()));


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

    }
}
