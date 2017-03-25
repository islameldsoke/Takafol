package com.example.moham.takafol;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.moham.takafol.Adapters.ProfilePostsAdapter;
import com.example.moham.takafol.Models.Post;
import com.example.moham.takafol.Models.Posts;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;

    List<Post> posts_list=new ArrayList<>();
   EditText writepost;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_content);

        toolbar=(Toolbar)findViewById(R.id.toolbarProfile);
        setSupportActionBar(toolbar);

        writepost=(EditText)findViewById(R.id.writePost);


//
//        List<Users> user =new ArrayList<>();
//
//
//        //fake data
//
//        int Images [] = {R.drawable.frankenstein,R.drawable.frankenstein,R.drawable.frankenstein,R.drawable.frankenstein};
//        String UNames [] = {"trika","trika","trika","trika","trika"};
//        String Posters [] = {"hello takfol hello takfol hello takfol hello takfol",
//                "hello takfol hello takfol hello takfol hello takfol" ,
//                "hello takfol hello takfol hello takfol hello takfol" ,
//                "hello takfol hello takfol hello takfol hello takfol"};
//
//        for (int i=0 ; i<Images.length; i++){
//            Users users=
//                    new Users(UNames[i] , Posters [i] ,Images[i]);
//            user.add(users);
//        }

        String url="http://khaledapi.000webhostapp.com/posts.php?UID=1";
        StringRequest stringRequest=new StringRequest( Request.Method.GET,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(ProfileActivity.this,response,Toast.LENGTH_LONG).show();

                if (response !=null)
               {
                   recyclerView=(RecyclerView)findViewById(R.id.profileRecycler);
                   RecyclerView.LayoutManager layoutManager=new
                           LinearLayoutManager(ProfileActivity.this,LinearLayoutManager.VERTICAL,false);
                   recyclerView.setLayoutManager(layoutManager);
                   recyclerView.setAdapter(new ProfilePostsAdapter(posts_list,ProfileActivity.this));
                   try {
                       JSONArray posts = new JSONArray(response);
                       for (int i=0;i<posts.length();i++)
                       {
                           JSONObject c = posts.getJSONObject(i);
                           String user_id=c.getString("user_id");
                           String post_content=c.getString("content");

                           Post post=new Post();
                           post.setContent(post_content);
                           post.setUserId(user_id);

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
                Toast.makeText(ProfileActivity.this,"error in posts",Toast.LENGTH_LONG).show();

                Toast.makeText(ProfileActivity.this,error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
        Volley.newRequestQueue(this).add(stringRequest);

        Log.d("myTag2", String.valueOf(posts_list));

    }



    public void postBtn(View view)
    {

        String url="http://khaledapi.000webhostapp.com/posts.php";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(ProfileActivity.this,response,Toast.LENGTH_LONG).show();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ProfileActivity.this,error.getMessage(),Toast.LENGTH_LONG).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap map=new HashMap();
                map.put("UID","1");
                map.put("pcontent",writepost.getText().toString());
                return map;
            }
        };
        Volley.newRequestQueue(this).add(stringRequest);
    }
}
