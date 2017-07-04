package com.example.moham.takafol.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.moham.takafol.Models.Post;
import com.example.moham.takafol.OthersActivity;
import com.example.moham.takafol.R;
import com.example.moham.takafol.commentActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by moham on 3/22/2017.
 */

public class ProfilePostsAdapter extends RecyclerView.Adapter<ProfilePostsAdapter.MyViewHolder> {
    List<Post> list;
    Context context;
    LayoutInflater layoutInflater;

    public ProfilePostsAdapter(List<Post> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.from(parent.getContext()).inflate(R.layout.profile_raw, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.userName.setText(list.get(position).getUserName());
        holder.post_content.setText(list.get(position).getContent());
        holder.commentsNumber.setText(list.get(position).getCommentsNumber());
        holder.supportersNumber.setText(list.get(position).getSupportersNumber() + " داعمون");
        holder.sharesNumber.setText(list.get(position).getSharesNumber());
        holder.date.setText(list.get(position).getPostDate());

        Glide.with(context).load("http://takafull.000webhostapp.com/" + list.get(position).getImage())
                .dontAnimate().placeholder(R.drawable.mdtp_done_background_color_dark)
                .into(holder.userImg);

        if (list.get(position).getLike_status().equals("1")) {
            holder.support.setImageResource(R.drawable.plus_green);
        } else {
            holder.support.setImageResource(R.drawable.plus);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView post_content, userName, date, commentsNumber, supportersNumber, sharesNumber;
        ImageButton support, comment, share;
        CircleImageView userImg;

        public MyViewHolder(View itemView) {
            super(itemView);
            userName = (TextView) itemView.findViewById(R.id.userName);
            post_content = (TextView) itemView.findViewById(R.id.postContent);
            date = (TextView) itemView.findViewById(R.id.time);
            commentsNumber = (TextView) itemView.findViewById(R.id.commentnumber);
            supportersNumber = (TextView) itemView.findViewById(R.id.supportNumber);
            sharesNumber = (TextView) itemView.findViewById(R.id.sharesNumber);


            userImg = (CircleImageView) itemView.findViewById(R.id.profilePic);
            support = (ImageButton) itemView.findViewById(R.id.Support);
            comment = (ImageButton) itemView.findViewById(R.id.comment);
            share = (ImageButton) itemView.findViewById(R.id.share);

            itemView.setOnClickListener(this);
            support.setOnClickListener(this);
            comment.setOnClickListener(this);
            share.setOnClickListener(this);
            userName.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            if (view.getId() == support.getId()) {

                SharedPreferences sharedPreferences = context.getSharedPreferences("user_id", 0);
                final String UID = sharedPreferences.getString("user_id", "");

                final String userID = list.get(getAdapterPosition()).getUserId().toString();
                final String postID = list.get(getAdapterPosition()).getPostId().toString();
                String url = "http://takaful.16mb.com/Api.php";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                Toast.makeText(context, response, Toast.LENGTH_LONG).show();

                                if (response.contains("deleted")) {
                                    support.setImageResource(R.drawable.plus);
                                    String[] strs = supportersNumber.getText().toString().split(" ");
                                    int num = Integer.parseInt(strs[0]);
                                    int newNum = num - 1;
                                    supportersNumber.setText(String.valueOf(newNum) + " داعمون");

                                } else {
                                    support.setImageResource(R.drawable.plus_green);
                                    String[] strs = supportersNumber.getText().toString().split(" ");
                                    int num = Integer.parseInt(strs[0]);
                                    int newNum = num + 1;
                                    supportersNumber.setText(String.valueOf(newNum) + " داعمون");
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap map = new HashMap();
                        map.put("LUID", UID);
                        map.put("LPOSTID", postID);
                        map.put("UUID", userID);

                        Log.d("kh", String.valueOf(map));
                        return map;
                    }
                };
                Volley.newRequestQueue(context).add(stringRequest);

            } else if (view.getId() == comment.getId()) {
                Intent intent = new Intent(context, commentActivity.class);
                intent.putExtra("Post_ID", list.get(getAdapterPosition()).getPostId());
                intent.putExtra("Owner_ID", list.get(getAdapterPosition()).getUserId());
                context.startActivity(intent);
                Toast.makeText(view.getContext(), "Comment Clicked ", Toast.LENGTH_SHORT).show();
            } else if (view.getId() == share.getId()) {

                SharedPreferences sharedPreferences = context.getSharedPreferences("user_id", 0);
                final String UID = sharedPreferences.getString("user_id", "");

                final String userID = list.get(getAdapterPosition()).getUserId().toString();
                final String postID = list.get(getAdapterPosition()).getPostId().toString();
                String url = "http://takaful.16mb.com/Api.php";
                StringRequest stringRequest = new StringRequest(1, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(context, response, Toast.LENGTH_LONG).show();

                        if (response.equals("true"))
                            Toast.makeText(context, "you shared post !" + response, Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(context, "error in sharing post !" + response, Toast.LENGTH_LONG).show();

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap map = new HashMap();
                        map.put("SUID", UID);
                        map.put("SPOSTID", postID);
                        map.put("SUUID", userID);

                        Log.d("kh", String.valueOf(map));
                        return map;
                    }
                };
                Volley.newRequestQueue(context).add(stringRequest);
            } else if (view.getId() == userName.getId()) {

                final String userID = list.get(getAdapterPosition()).getUserId().toString();

                Toast.makeText(context, userID, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context, OthersActivity.class);
                intent.putExtra("user_id", userID);
                context.startActivity(intent);

            }
        }
    }
}
