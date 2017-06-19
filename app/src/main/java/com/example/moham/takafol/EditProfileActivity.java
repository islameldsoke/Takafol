package com.example.moham.takafol;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by moham on 4/23/2017.
 */
public class EditProfileActivity extends AppCompatActivity {
    CircleImageView profileImage;
    ImageView coverImage;
    EditText bio, mobile, address;
    String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        profileImage = (CircleImageView) findViewById(R.id.profile_image_edit);
        coverImage = (ImageView) findViewById(R.id.cover_image_edit);
        bio = (EditText) findViewById(R.id.bioEdit);
        mobile = (EditText) findViewById(R.id.phoneEdit);
        address = (EditText) findViewById(R.id.addressEdit);

        user_id = getSharedPreferences("user_id", 0).getString("user_id", "");
        String url_num = "http://takafull.000webhostapp.com/Api.php?INFOUID=" + user_id;
        StringRequest num_request = new StringRequest(Request.Method.GET, url_num, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String pic_url = jsonObject.getString("profile_image");
                        String cover_url = jsonObject.getString("cover_image");
                        String bioStr = jsonObject.getString("bio");
                        String addressStr = jsonObject.getString("address");
                        String phoneStr = jsonObject.getString("phonenumber");

                        bio.setText(bioStr);
                        address.setText(addressStr);
                        mobile.setText(phoneStr);
                        Glide.with(EditProfileActivity.this).load("http://takafull.000webhostapp.com/" + pic_url)
                                .placeholder(R.drawable.frankenstein)
                                .dontAnimate().into(profileImage);
                        Glide.with(EditProfileActivity.this).load("http://takafull.000webhostapp.com/" + cover_url)
                                .placeholder(R.drawable.frankenstein)
                                .dontAnimate().into(coverImage);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(EditProfileActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        Volley.newRequestQueue(this).add(num_request);
    }

    public void changeProfilePic(View view) {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, 10);
    }

    public void changeCover(View view) {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, 20);
    }

    public void Edit(View view) {
        if (mobile.getText().toString().equals("") || bio.getText().toString().equals("") || address.getText().toString().equals("")) {
            Toast.makeText(EditProfileActivity.this, "تأكد من كتابه البيانات", Toast.LENGTH_LONG).show();
        } else {
            StringRequest editProfileRequest = new StringRequest(Request.Method.POST, "http://takafull.000webhostapp.com/Api.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(EditProfileActivity.this, response, Toast.LENGTH_LONG).show();
                            Log.d("response5ald", response);
                            if (response != null) {
                                if (response.contains("updated")) {
                                    Toast.makeText(EditProfileActivity.this, "تم التعديل بنجاح", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(EditProfileActivity.this, "لم يتم التعديل !", Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(EditProfileActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Bitmap profileBitmap = ((GlideBitmapDrawable) profileImage.getDrawable().getCurrent()).getBitmap();
                    Bitmap coverBitmap = ((GlideBitmapDrawable) coverImage.getDrawable().getCurrent()).getBitmap();

                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("user_id_edit", user_id);
                    hashMap.put("pimage_edit", ImageToString(profileBitmap));
                    hashMap.put("cimage_edit", ImageToString(coverBitmap));
                    hashMap.put("phone_edit", mobile.getText().toString());
                    hashMap.put("address_edit", address.getText().toString());
                    hashMap.put("bio_edit", bio.getText().toString());
                    return hashMap;
                }
            };
            Volley.newRequestQueue(this).add(editProfileRequest);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == RESULT_OK && data != null) {

            Uri uri = data.getData();
            Glide.with(this)
                    .load(uri)
                    .dontAnimate()
                    .placeholder(R.drawable.frankenstein)
                    .into(profileImage);

        } else if (requestCode == 20 && resultCode == RESULT_OK && data != null) {

            Uri uri = data.getData();
            Glide.with(this)
                    .load(uri)
                    .dontAnimate()
                    .placeholder(R.drawable.cover)
                    .into(coverImage);

        }
    }

    private String ImageToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes, Base64.DEFAULT);
    }
}
