package com.example.moham.takafol.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.moham.takafol.Models.commentModel;
import com.example.moham.takafol.R;

import java.util.List;

/**
 * Created by islam on 22/04/2017.
 */

public class commentsAdapter extends RecyclerView.Adapter<commentsAdapter.MyViewHolder> {

    List<commentModel> list;
    //commentModel newobj;
    Context context;
    LayoutInflater layoutInflater;

    public commentsAdapter(List<commentModel> list, Context context) {
        this.list = list;
        this.context = context;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.from(parent.getContext()).inflate(R.layout.commentrow, parent, false);
        commentsAdapter.MyViewHolder myViewHolder = new commentsAdapter.MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        ImageView imageView = holder.userImage;
        Glide.with(context).load("http://khaledapi.000webhostapp.com/" + list.get(position).getUserImage())
                .placeholder(R.drawable.mdtp_done_background_color_dark)
                .into(imageView);
        holder.userName.setText(list.get(position).getUserName());
        holder.commentContent.setText(list.get(position).getCommentContent());
//        Log.d("ttt",list.get(position).getCommentContent());


    }


    @Override
    public int getItemCount() {

        return list.size();

    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView userImage;
        TextView commentContent, userName;

        public MyViewHolder(View itemView) {
            super(itemView);

            userImage = (ImageView) itemView.findViewById(R.id.profilePic_comment);
            commentContent = (TextView) itemView.findViewById(R.id.comment_content);
            userName = (TextView) itemView.findViewById(R.id.user_name_comment);
        }
    }

}
