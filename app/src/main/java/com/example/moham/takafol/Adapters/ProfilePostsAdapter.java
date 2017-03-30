package com.example.moham.takafol.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moham.takafol.Models.Post;
import com.example.moham.takafol.R;

import java.util.List;

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
        View view = layoutInflater.from(parent.getContext()).inflate(R.layout.post_row, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.user_id.setText(list.get(position).getUserId());
        holder.post_content.setText(list.get(position).getContent());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView user_id;
        TextView post_content;
        ImageButton support, comment, share;

        public MyViewHolder(View itemView) {
            super(itemView);
            user_id = (TextView) itemView.findViewById(R.id.USERname);
            post_content = (TextView) itemView.findViewById(R.id.postContent);
            support = (ImageButton) itemView.findViewById(R.id.Support);
            comment = (ImageButton) itemView.findViewById(R.id.comment);
            share = (ImageButton) itemView.findViewById(R.id.share);

            support.setOnClickListener(this);
            comment.setOnClickListener(this);
            share.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            if (view.getId() == support.getId()) {

                Toast.makeText(view.getContext(), "Support Clicked ", Toast.LENGTH_SHORT).show();

            } else if (view.getId() == comment.getId()) {
                Toast.makeText(view.getContext(), "Comment Clicked ", Toast.LENGTH_SHORT).show();

            } else if (view.getId() == share.getId()) {
                Toast.makeText(view.getContext(), "Share Clicked ", Toast.LENGTH_SHORT).show();


            }
        }
    }
}
