package com.example.moham.takafol;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.moham.takafol.Models.notificationContentModel;

import org.json.JSONException;
import org.json.JSONObject;

public class notificationContent extends AppCompatActivity {

    ImageView profilePic;
    TextView userName, content, commentNumber, supportNumber, date;

    notificationContentModel notiModel = new notificationContentModel();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_content);

        profilePic = (ImageView) findViewById(R.id.profilePic);
        userName = (TextView) findViewById(R.id.USERname);
        content = (TextView) findViewById(R.id.postContent);
        commentNumber = (TextView) findViewById(R.id.commentnumber);
        supportNumber = (TextView) findViewById(R.id.supportNumber);
        date = (TextView) findViewById(R.id.time);


        final String postId = getIntent().getStringExtra("Post_IDD");
        String url_posts = "http://takafull.000webhostapp.com/Api.php/?POID=" + postId;
        Log.v("zatr", postId);
        StringRequest PostsRequest = new StringRequest(Request.Method.GET, url_posts, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(notificationContent.this, response.toString(), Toast.LENGTH_LONG);
                Log.v("notifi", response.toString());

                if (response != null) {


                    JSONObject c = null;
                    try {
                        c = new JSONObject(response);

                        String user_name = c.getString("user_name");
                        String usr_image = c.getString("user_image");
                        String date = c.getString("post_date");
                        String postContent = c.getString("content");
                        String user_id = c.getString("user_id");
                        String supporters = c.getString("supporters_number");
                        String commenters = c.getString("comments_number");
                        String shares = c.getString("shares_number");
                        String lastLiker = c.getString("name of last liker");
                        String lastCommenter = c.getString("name of last commenter");

                        Glide.with(notificationContent.this).load("http://takafull.000webhostapp.com/" + usr_image).into(profilePic);
                        userName.setText(user_name);
                        content.setText(postContent);
                        supportNumber.setText(lastLiker + supporters);
                        commentNumber.setText(lastCommenter + commenters);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

//                            notiModel.setUser_name(user_name);
//                            notiModel.setPostContent(postContent);
//                            notiModel.setUser_id(user_id);
//                            notiModel.setUser_image(usr_image);
//                            notiModel.setPost_date(date);
//                            notiModel.setSupporters_number(supporters);
//                            notiModel.setComments_number(commenters);
//                            notiModel.setShares_number(shares);
//                            notiModel.setLastLiker(lastLiker);
//                            notiModel.setLastCommenter(lastCommenter);


                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(notificationContent.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        Volley.newRequestQueue(this).add(PostsRequest);


    }
}
