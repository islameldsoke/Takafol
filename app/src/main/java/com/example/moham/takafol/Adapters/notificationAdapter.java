package com.example.moham.takafol.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.moham.takafol.Models.notificationModel;
import com.example.moham.takafol.R;
import com.example.moham.takafol.notificationContent;

import java.util.List;

/**
 * Created by islam on 24/04/2017.
 */

public class notificationAdapter extends RecyclerView.Adapter<notificationAdapter.MyViewHolder> {

    List<notificationModel> list;
    Context context;
    LayoutInflater layoutInflater;

    public notificationAdapter( List<notificationModel> list ,Context context) {
        this.list = list;
        this.context = context;
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.from(parent.getContext()).inflate(R.layout.notification_raw   , parent, false);

        notificationAdapter.MyViewHolder myViewHolder = new notificationAdapter.MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        ImageView imageView = holder.userImage;
        Glide.with(context).load("http://khaledapi.000webhostapp.com/"+list.get(position).getUserImage())
                .placeholder(R.drawable.mdtp_done_background_color_dark)
                .into(imageView);
        holder.commentContent.setText(list.get(position).getNotificationType());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, notificationContent.class);
                intent.putExtra("Post_IDD", list.get(position).getPostId());
                context.startActivity(intent);


            }
        });

    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView userImage;
        TextView commentContent , userName;

        public MyViewHolder(View itemView) {
            super(itemView);

            userImage = (ImageView)itemView.findViewById(R.id.notificationprofilePic);
            commentContent = (TextView) itemView.findViewById(R.id.whonotifiy);
            userName = (TextView)itemView.findViewById(R.id.notificationUSERname);
        }

    }


}
