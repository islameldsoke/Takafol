package com.example.moham.takafol;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.moham.takafol.Adapters.HomeAdapter;
import com.example.moham.takafol.Adapters.ProfilePostsAdapter;
import com.example.moham.takafol.Models.Post;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrendsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<Post> posts_list = new ArrayList<>();
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trends);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String user_id = getSharedPreferences("user_id", 0).getString("user_id", "");

        String url = "http://takaful.16mb.com/Api.php?trend=1&&UID=" + user_id;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(TrendsActivity.this, response, Toast.LENGTH_LONG).show();

                if (response != null) {
                    recyclerView = (RecyclerView) findViewById(R.id.recyclerTrend);
                    RecyclerView.LayoutManager layoutManager = new
                            LinearLayoutManager(TrendsActivity.this, LinearLayoutManager.VERTICAL, false);
                    recyclerView.setLayoutManager(layoutManager);
                    try {
                        JSONArray posts = new JSONArray(response);
                        for (int i = 0; i < posts.length(); i++) {
                            JSONObject c = posts.getJSONObject(i);
                            String userImage = c.getString("user_image");
                            String userName = c.getString("user_name");
                            String post_content = c.getString("post_content");
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
                        recyclerView.setAdapter(new HomeAdapter(posts_list, TrendsActivity.this));


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(TrendsActivity.this, "error in posts", Toast.LENGTH_LONG).show();
                Toast.makeText(TrendsActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        Volley.newRequestQueue(TrendsActivity.this).add(stringRequest);

    }
}
