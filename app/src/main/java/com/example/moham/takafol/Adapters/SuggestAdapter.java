package com.example.moham.takafol.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.moham.takafol.Models.suggestModel;
import com.example.moham.takafol.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by moham on 6/21/2017.
 */

public class SuggestAdapter extends RecyclerView.Adapter<SuggestAdapter.MyViewHolder> {
    List<suggestModel> list;
    Context context;
    LayoutInflater layoutInflater;

    public SuggestAdapter(List<suggestModel> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @Override
    public SuggestAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.from(parent.getContext()).inflate(R.layout.suggest_raw, parent, false);

        SuggestAdapter.MyViewHolder myViewHolder = new SuggestAdapter.MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(SuggestAdapter.MyViewHolder holder, final int position) {

        ImageView imageView = holder.userImage;
        Glide.with(context).load("http://takaful.16mb.com/" + list.get(position).getUserImage())
                .placeholder(R.drawable.frankenstein)
                .into(imageView);
        holder.userName.setText(list.get(position).getUserName());

    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CircleImageView userImage;
        TextView userName;
        Button follow;

        public MyViewHolder(View itemView) {
            super(itemView);

            userImage = (CircleImageView) itemView.findViewById(R.id.notificationprofilePic);
            userName = (TextView) itemView.findViewById(R.id.notificationUSERname);
            follow = (Button) itemView.findViewById(R.id.suggestFB);

            follow.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(v.getId() == follow.getId()){
//                String url = "http://takaful.16mb.com/Api.php?";
//                StringRequest sendPostRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        if (response != null) {
//                            if (response.contains("true")) {
//                                Toast.makeText(OthersActivity.this, "تم المتابعه", Toast.LENGTH_LONG).show();
//                                followBtn.setText("الغاء المتابعه");
//                                //followBtn.setBackground(R.drawable.buttondrwable);
//
//                            } else if (response.contains("deleted")){
//                                Toast.makeText(OthersActivity.this, "تم الغاء المتابعه", Toast.LENGTH_LONG).show();
//                                followBtn.setText("متابعه");
//                            }
//                            else {
//                                Toast.makeText(OthersActivity.this, "حدث خطأ", Toast.LENGTH_LONG).show();
//                            }
//                        }
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(OthersActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
//                    }
//                }) {
//                    @Override
//                    protected Map<String, String> getParams() throws AuthFailureError {
//                        HashMap<String, String> map = new HashMap<>();
//                        map.put("FOLLOWER_ID", user_id);
//                        map.put("FOLLOWED_ID",otherUser_id );
//
//                        return map;
//                    }
//                };
//                Volley.newRequestQueue(this).add(sendPostRequest);
            }
        }
    }
}
