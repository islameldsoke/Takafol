package com.example.moham.takafol;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class PostActivity extends AppCompatActivity {
    EditText postContent,donatedValue;
    String user_id;
    ImageView postImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        user_id = getSharedPreferences("user_id", 0).getString("user_id", "");
        postContent = (EditText) findViewById(R.id.post);
        donatedValue = (EditText)findViewById(R.id.donatedValue);
        postImage = (ImageView) findViewById(R.id.postImage);
    }

    public void getImage(View view) {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, 50);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 50 && data != null) {
            Uri uri = data.getData();
            Glide.with(this).load(uri).into(postImage);
        }
    }

    public void sendPost(View view) {
        String url = "http://takaful.16mb.com/Api.php";
        StringRequest sendPostRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Toast.makeText(PostActivity.this,response.toString(), Toast.LENGTH_LONG).show();
                if (response != null) {
                    if (response.contains("true")) {
                        Toast.makeText(PostActivity.this,response.toString(), Toast.LENGTH_LONG).show();
                        finish();
                    } else if(response.contains("false")){
                        Toast.makeText(PostActivity.this, response.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PostActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("UID", user_id);
                map.put("pcontent", postContent.getText().toString());
                //changeHereمش شغالة
                map.put("value",donatedValue.getText().toString());
                try {
                    Bitmap postImageBitmap = ((GlideBitmapDrawable) postImage.getDrawable().getCurrent()).getBitmap();
                    map.put("post_image", ImageToString(postImageBitmap));
                } catch (NullPointerException n) {
                    map.put("post_image", "");
                }
                return map;
            }
        };
        Volley.newRequestQueue(this).add(sendPostRequest);
    }

    private String ImageToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes, Base64.DEFAULT);
    }


}
