package com.example.moham.takafol;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.moham.takafol.Adapters.ProfilePostsAdapter;
import com.example.moham.takafol.Models.Post;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<Post> posts_list = new ArrayList<>();
    TextView posts_num;
    TextView follower_num;
    TextView following_num;
    TextView email;
    TextView name;
    TextView bio;
    TextView address;
    TextView phone;
    ImageView cover;
    CircleImageView circleImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_content);


        posts_num = (TextView) findViewById(R.id.posts_num);
        follower_num = (TextView) findViewById(R.id.followers_num);
        following_num = (TextView) findViewById(R.id.following_num);
        name = (TextView) findViewById(R.id.nameUser);
        address = (TextView) findViewById(R.id.addressInfo);
        phone = (TextView) findViewById(R.id.phoneInfo);
        bio = (TextView) findViewById(R.id.bio);
        email = (TextView) findViewById(R.id.emailUser);
        cover = (ImageView) findViewById(R.id.coverUser);
        circleImageView = (CircleImageView) findViewById(R.id.profile_image);


        String user_id = getSharedPreferences("user_id", 0).getString("user_id", "");
        String url_num = "http://takaful.16mb.com/Api.php?INFOUID=" + user_id;
        StringRequest num_request = new StringRequest(Request.Method.GET, url_num, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String posts_number = jsonObject.getString("posts_number");
                        String followers_number = jsonObject.getString("followers_number");
                        String following_number = jsonObject.getString("following_number");
                        String pic_url = jsonObject.getString("profile_image");
                        String cover_url = jsonObject.getString("cover_image");
                        String nameStr = jsonObject.getString("username");
                        String emailStr = jsonObject.getString("email");
                        String bioStr = jsonObject.getString("bio");
                        String addressStr = jsonObject.getString("address");
                        String phoneStr = jsonObject.getString("phonenumber");

                        posts_num.setText(posts_number);
                        following_num.setText(following_number);
                        follower_num.setText(followers_number);
                        name.setText(nameStr);
                        email.setText(emailStr);
                        bio.setText(bioStr);
                        address.setText(addressStr);
                        phone.setText(phoneStr);
                        Glide.with(ProfileActivity.this).load("http://takaful.16mb.com/Api.php" + pic_url)
                                .placeholder(R.drawable.frankenstein)
                                .dontAnimate().into(circleImageView);
                        Glide.with(ProfileActivity.this).load("http://takaful.16mb.com/Api.php" + cover_url)
                                .placeholder(R.drawable.cover).into(cover);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ProfileActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
        Volley.newRequestQueue(this).add(num_request);


        String url_posts = "http://takaful.16mb.com/Api.php?PUID=" + user_id;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url_posts, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(ProfileActivity.this, response, Toast.LENGTH_LONG).show();

                if (response != null) {
                    recyclerView = (RecyclerView) ProfileActivity.this.findViewById(R.id.profileRecycler);
                    RecyclerView.LayoutManager layoutManager = new
                            LinearLayoutManager(ProfileActivity.this, LinearLayoutManager.VERTICAL, false);
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
                            String post_img=c.getString("post_image");
                            String like_status=c.getString("like_status");
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

                            Log.e("KH5",post.getLike_status());

                            posts_list.add(post);
                        }
                        recyclerView.setAdapter(new ProfilePostsAdapter(posts_list, ProfileActivity.this));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ProfileActivity.this, "error in posts", Toast.LENGTH_LONG).show();

                Toast.makeText(ProfileActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        // stringRequest.setShouldCache(false);
        Volley.newRequestQueue(ProfileActivity.this).add(stringRequest);

    }


    public void postBtn(View view) {
        startActivity(new Intent(this, PostActivity.class));
    }

    public void EditProfile(View view) {
        startActivity(new Intent(this, EditProfileActivity.class));
    }
}
