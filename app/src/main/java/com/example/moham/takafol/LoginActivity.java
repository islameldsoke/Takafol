package com.example.moham.takafol;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.moham.takafol.Models.userModel;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText email;
    EditText password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


        email=(EditText)findViewById(R.id.emailLogin);
        password=(EditText)findViewById(R.id.passwordLogin);




    }

    public void newMember(View view)
    {
        startActivity(new Intent(this,Registration.class));
    }
    public void Login(View view)
    {
        String url="http://khaledapi.000webhostapp.com/login.php";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.contains("user_id"))
                {

                    Gson gson=new Gson();
                    userModel userModel=gson.fromJson(response,userModel.class);
                    String user_id=userModel.getUserId();

                    SharedPreferences sharedPreferences=getSharedPreferences("user_id",0);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("user_id",user_id);
                    editor.commit();

                   // Toast.makeText(LoginActivity.this,user_id,Toast.LENGTH_LONG).show();

                    startActivity(new Intent(LoginActivity.this,MainActivity.class));

                }
                else if (response.contains("false"))
                {

                    Toast.makeText(LoginActivity.this,"خطأ في البريد الاكتروني او كلمه المرور",Toast.LENGTH_LONG).show();

                }else {
                    Toast.makeText(LoginActivity.this,"حدث خطأ حاول لاحقا ..",Toast.LENGTH_LONG).show();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this,error.getMessage(),Toast.LENGTH_LONG).show();


            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap hashMap=new HashMap();
                hashMap.put("umail",email.getText().toString());
                hashMap.put("upass",password.getText().toString());


                return hashMap;
            }
        };

        Volley.newRequestQueue(this).add(stringRequest);


    }

}
