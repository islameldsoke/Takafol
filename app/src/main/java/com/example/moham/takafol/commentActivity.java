package com.example.moham.takafol;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.moham.takafol.Adapters.commentsAdapter;
import com.example.moham.takafol.Models.commentModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class commentActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    EditText commentContent;
    Button setComment;
    List<commentModel> commentsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comments_activity_layout);

        commentContent = (EditText) findViewById(R.id.commentC);
        setComment = (Button) findViewById(R.id.commentB);

        final String postId = getIntent().getStringExtra("Post_ID");
        final String ownerId = getIntent().getStringExtra("Owner_ID");

        SharedPreferences sharedPreferences = getSharedPreferences("user_id", 0);
        final String UID = sharedPreferences.getString("user_id", "");

        String url_posts = "http://takaful.16mb.com/Api.php?GET_COM_UID=" + postId;
        StringRequest PostsRequest = new StringRequest(Request.Method.GET, url_posts, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response != null) {
                    recyclerView = (RecyclerView) findViewById(R.id.comment_recyclerView);
                    RecyclerView.LayoutManager layoutManager = new
                            LinearLayoutManager(commentActivity.this, LinearLayoutManager.VERTICAL, false);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(new commentsAdapter(commentsList, commentActivity.this));

                    try {
                        JSONArray posts = new JSONArray(response);
                        for (int i = 0; i < posts.length(); i++) {
                            JSONObject c = posts.getJSONObject(i);

                            String userName = c.getString("user_name");
                            String usr_image = c.getString("user_image");
                            //String date = c.getString("post_date");
                            String content = c.getString("comment_content");
                            String user_id = c.getString("user_id");
                            commentModel post = new commentModel();
                            post.setUserName(userName);
                            post.setCommentContent(content);
                            post.setUserId(user_id);
                            post.setUserImage(usr_image);
                            // post.setPostDate(date);
                            commentsList.add(post);
                            Toast.makeText(commentActivity.this, post.getUserName(), Toast.LENGTH_LONG).show();
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(commentActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        Volley.newRequestQueue(this).add(PostsRequest);


        setComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String url = "http://takaful.16mb.com/Api.php";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(commentActivity.this, response, Toast.LENGTH_LONG).show();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(commentActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap map = new HashMap();
                        map.put("CUID", UID);
                        map.put("CPOSTID", postId);
                        map.put("CONTENT", commentContent.getText().toString());
                        map.put("CUUID", ownerId);

                        Log.d("kh", String.valueOf(map));
                        return map;
                    }
                };
                Volley.newRequestQueue(getBaseContext()).add(stringRequest);
            }
        });

    }
}
