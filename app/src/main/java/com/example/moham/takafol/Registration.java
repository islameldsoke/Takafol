package com.example.moham.takafol;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.RecursiveAction;

public class Registration extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {


    EditText fnameReg;
    EditText lnameReg;
    EditText emailReg;
    EditText passwordReg;
    EditText phoneReg;
    EditText addressReg;
    RadioGroup radioGroup;
    Button dateButton;
    String date ="";
    String gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


        fnameReg= (EditText) findViewById(R.id.fnameReg);
        passwordReg= (EditText) findViewById(R.id.passwordReg);
        lnameReg= (EditText) findViewById(R.id.lnameReg);
        addressReg= (EditText) findViewById(R.id.addressReg);
        emailReg= (EditText) findViewById(R.id.emailReg);
        phoneReg= (EditText) findViewById(R.id.phoneReg);


        dateButton=(Button)findViewById(R.id.dateBtn);
        radioGroup=(RadioGroup)findViewById(R.id.radioGp);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId)
                {
                    case R.id.male:
                        gender="male";
                        break;
                    case R.id.female:
                        gender="female";
                        break;
                }
            }
        });



    }

    public void Register(View view)
    {

        if (fnameReg.getText().toString().equals("") || lnameReg.getText().toString().equals("") ||
        emailReg.getText().toString().equals("") || phoneReg.getText().toString().equals("") || gender.equals("") ||
        passwordReg.getText().toString().equals("") || addressReg.getText().toString().equals("") || date.equals(""))
        {
            Toast.makeText(Registration.this," تأكد من كتابة جميع البيانات",Toast.LENGTH_LONG).show();

        }
        else
        {

        String url="http://khaledapi.000webhostapp.com/try.php";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(Registration.this,response,Toast.LENGTH_LONG).show();

                Toast.makeText(Registration.this,"تم التسجيل بنجاح ..",Toast.LENGTH_LONG).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Registration.this,"لم يتم التسجيل بنجاح",Toast.LENGTH_LONG).show();

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap map=new HashMap();
                map.put("fname",fnameReg.getText().toString());
                map.put("pass",passwordReg.getText().toString());
                map.put("lname",lnameReg.getText().toString());
                map.put("email",emailReg.getText().toString());
                map.put("option",gender);
                map.put("address",addressReg.getText().toString());
                map.put("phone_number",phoneReg.getText().toString());
                map.put("birthday_day",date);

                Log.d("khaledTry", String.valueOf(map));
                return map;
            }
        };
        Volley.newRequestQueue(this).add(stringRequest);


            startActivity(new Intent(this,LoginActivity.class));


        }
    }

    public void SetDate(View view)
    {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getFragmentManager(), "Datepickerdialog");
        dpd.setVersion(DatePickerDialog.Version.VERSION_1);
        dpd.dismissOnPause(true);
        dpd.setAccentColor(getResources().getColor(R.color.primary));
        Calendar maxcalendar = new GregorianCalendar(2002, 01, 01);
        Calendar mincalendar = new GregorianCalendar(1950, 01, 01);

        dpd.setMaxDate(maxcalendar);
        dpd.setMinDate(mincalendar);


    }




    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
         date = dayOfMonth+"/"+(monthOfYear+1)+"/"+year;
        Toast.makeText(this,date,Toast.LENGTH_LONG).show();
        //dateButton.setText(date);
    }
}

