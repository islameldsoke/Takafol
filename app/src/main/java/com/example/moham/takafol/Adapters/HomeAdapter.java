package com.example.moham.takafol.Adapters;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.daimajia.numberprogressbar.NumberProgressBar;
import com.example.moham.takafol.Models.Post;
import com.example.moham.takafol.OthersActivity;
import com.example.moham.takafol.R;
import com.example.moham.takafol.commentActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by moham on 6/21/2017.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {
    List<Post> list;
    Context context;
    LayoutInflater layoutInflater;



    public HomeAdapter(List<Post> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public void clear(List newList) {
        list.clear();
        list.addAll(newList);
        notifyDataSetChanged();
    }

    @Override
    public HomeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.from(parent.getContext()).inflate(R.layout.post_row, parent, false);
        HomeAdapter.MyViewHolder myViewHolder = new HomeAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(HomeAdapter.MyViewHolder holder, int position) {
        holder.userName.setText(list.get(position).getUserName());
        holder.post_content.setText(list.get(position).getContent());
        holder.commentsNumber.setText(list.get(position).getCommentsNumber());
        holder.supportersNumber.setText(list.get(position).getSupportersNumber() + " داعمون");
        holder.sharesNumber.setText(list.get(position).getSharesNumber());
        holder.date.setText(list.get(position).getPostDate());
        //changeHere
        float neededM = Float.parseFloat(list.get(position).getNeeded_money().toString());
        float donatedM = Float.parseFloat(list.get(position).getDonated_money().toString());
        int total = (int)((donatedM * 100) / neededM);
        Toast.makeText(context,total+"",Toast.LENGTH_LONG).show();
        SharedPreferences sharedPreferences = context.getSharedPreferences("user_id", 0);
        final String UID = sharedPreferences.getString("user_id", "");

        String vote_stat=list.get(position).getTrust_status().toString();
        if (vote_stat.equals("1")){
            holder.voteGroup.setVisibility(View.GONE);
            holder.vote.setVisibility(View.GONE);
            holder.trust.setVisibility(View.GONE);
            holder.notTrust.setVisibility(View.GONE);
            holder.votStext.setVisibility(View.VISIBLE);

        }else if(vote_stat.equals("0")){
            holder.votStext.setVisibility(View.GONE);
        }else if(list.get(position).equals(UID)){
            holder.voteGroup.setVisibility(View.GONE);
            holder.vote.setVisibility(View.GONE);
            holder.trust.setVisibility(View.GONE);
            holder.notTrust.setVisibility(View.GONE);
            holder.votStext.setVisibility(View.GONE);
        }


        holder.numberProgressBar.incrementProgressBy(total);

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
        TextView post_content, userName, date, commentsNumber, supportersNumber, sharesNumber,votStext;
        ImageButton support, comment, share, donate;
        CircleImageView userImg;
        //RingProgressBar ring;
        NumberProgressBar numberProgressBar;
        RadioGroup voteGroup ;
        RadioButton trust ;
        RadioButton notTrust;
        Button vote;
        boolean voteS;

        public MyViewHolder(View itemView) {
            super(itemView);
            userName = (TextView) itemView.findViewById(R.id.userName);
            post_content = (TextView) itemView.findViewById(R.id.postContent);
            date = (TextView) itemView.findViewById(R.id.time);
            commentsNumber = (TextView) itemView.findViewById(R.id.commentnumber);
            supportersNumber = (TextView) itemView.findViewById(R.id.supportNumber);
            sharesNumber = (TextView) itemView.findViewById(R.id.sharesNumber);
            voteGroup = (RadioGroup)itemView.findViewById(R.id.voteGroup);
            trust = (RadioButton)itemView.findViewById(R.id.trust);
            notTrust = (RadioButton)itemView.findViewById(R.id.notTrust);
            vote = (Button)itemView.findViewById(R.id.vote);
            votStext = (TextView)itemView.findViewById(R.id.votS);



            //changeHere
            donate = (ImageButton) itemView.findViewById(R.id.donate);


            userImg = (CircleImageView) itemView.findViewById(R.id.profilePic);
            support = (ImageButton) itemView.findViewById(R.id.Support);
            comment = (ImageButton) itemView.findViewById(R.id.comment);
            share = (ImageButton) itemView.findViewById(R.id.share);

//changeHere
            numberProgressBar = (NumberProgressBar)itemView.findViewById(R.id.newProgress);
           // ring = (RingProgressBar) itemView.findViewById(R.id.progress_bar_2);


            itemView.setOnClickListener(this);
            support.setOnClickListener(this);
            comment.setOnClickListener(this);
            share.setOnClickListener(this);
            userName.setOnClickListener(this);
            donate.setOnClickListener(this);
            trust.setOnClickListener(this);
            notTrust.setOnClickListener(this);
            vote.setOnClickListener(this);

        }

        @Override
        public void onClick(final View view) {

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
//changeHere
            } else if (view.getId() == donate.getId()) {
                new MaterialDialog.Builder(context)
                        .title("تبرع")
                        .items(R.array.items)
                        .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {

                                if (text.equals("مالي")) {
                                    Toast.makeText(view.getContext(), "ch", Toast.LENGTH_LONG).show();
                                    new MaterialDialog.Builder(context)
                                            .title("تبرع مالي")
                                            .content(text)
                                            .inputType(InputType.TYPE_CLASS_NUMBER)
                                            .input("القيمة المالية", "0", new MaterialDialog.InputCallback() {
                                                @Override
                                                public void onInput(MaterialDialog dialog, final CharSequence input) {
                                                    String url = "http://takaful.16mb.com/Api.php";
                                                    StringRequest sendPostRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                                                        @Override
                                                        public void onResponse(String response) {
                                                            Toast.makeText(context, response.toString(), Toast.LENGTH_LONG).show();

                                                            if (response != null) {
                                                                if (response.contains("true")) {
                                                                    Toast.makeText(context, "done", Toast.LENGTH_LONG).show();

                                                                } else if (response.contains("false")) {
                                                                    Toast.makeText(context, response.toString(), Toast.LENGTH_LONG).show();
                                                                }
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
                                                            HashMap<String, String> map = new HashMap<>();
                                                            map.put("pid", list.get(getAdapterPosition()).getPostId().toString());
                                                            map.put("donate", input.toString());


                                                            return map;
                                                        }
                                                    };
                                                    Volley.newRequestQueue(context).add(sendPostRequest);

//                                                    float neededM = Float.parseFloat(list.get(getAdapterPosition()).getNeeded_money().toString());
//                                                    float donatedM = Float.parseFloat(list.get(getAdapterPosition()).getDonated_money().toString());
//                                                    float total = (donatedM / neededM) * 100;
//                                                    int newTotal = Integer.parseInt(input.toString())+Math.round(total);

                                                    numberProgressBar.setProgress(numberProgressBar.getProgress()+Integer.parseInt(input.toString()));


                                                }
                                            }).show();

                                } else if (text.equals("عيني")) {
                                    new MaterialDialog.Builder(context)
                                            .title("تبرع العيني")
                                            .items(list.get(getAdapterPosition()).getUser_email().toString(),
                                                    list.get(getAdapterPosition()).getUser_phone().toString())
                                            .itemsCallback(new MaterialDialog.ListCallback() {
                                                @Override
                                                public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                                    if (text.toString().equals(list.get(getAdapterPosition()).getUser_email().toString())) {
                                                        Intent email = new Intent();
                                                        email.putExtra(Intent.EXTRA_EMAIL, new String[]{list.get(getAdapterPosition()).getUser_email().toString()});
                                                        email.setType("message/rfc822");
                                                        context.startActivity(email);

                                                    } else if (text.toString().equals(list.get(getAdapterPosition()).getUser_phone().toString())) {
                                                        Intent email = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + list.get(getAdapterPosition()).getUser_phone().toString()));

                                                        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                                            // TODO: Consider calling
                                                            //    ActivityCompat#requestPermissions
                                                            // here to request the missing permissions, and then overriding
                                                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                                            //                                          int[] grantResults)
                                                            // to handle the case where the user grants the permission. See the documentation
                                                            // for ActivityCompat#requestPermissions for more details.
                                                            return;
                                                        }else
                                                        context.startActivity(email);

                                                    }
                                                }
                                            })
                                            .show();


                                }

                                return true;
                            }
                        })
                        .positiveText("choose")
                        .show();
            }else if(view.getId() == trust.getId()){
                if (trust.isChecked())
                voteS = true;
               // Toast.makeText(context,voteS+"",Toast.LENGTH_LONG).show();


            }else if(view.getId() == vote.getId()){
                SharedPreferences sharedPreferences = context.getSharedPreferences("user_id", 0);
                final String UID = sharedPreferences.getString("user_id", "");
                final String postID = list.get(getAdapterPosition()).getPostId().toString();
                String url = "http://takaful.16mb.com/Api.php";

                if (voteS ){
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    Toast.makeText(context, response, Toast.LENGTH_LONG).show();

                                    if (response.contains("true")) {
                                        vote.setClickable(false);



                                    } else {

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
                            map.put("VID", UID);
                            map.put("POSTID", postID);
                            map.put("TRUST", "1");

                            Log.d("kh", String.valueOf(map));
                            return map;
                        }
                    };
                    Volley.newRequestQueue(context).add(stringRequest);

                }else if(!voteS){
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    Toast.makeText(context, response, Toast.LENGTH_LONG).show();

                                    if (response.contains("true")) {
                                        vote.setClickable(false);

                                    } else {

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
                            map.put("VID", UID);
                            map.put("POSTID", postID);
                            map.put("TRUST", "0");

                            Log.d("kh", String.valueOf(map));
                            return map;
                        }
                    };
                    Volley.newRequestQueue(context).add(stringRequest);
                }







            }else if(view.getId() == notTrust.getId()){
                if (notTrust.isChecked())
                voteS = false;
            }
        }
    }

}

