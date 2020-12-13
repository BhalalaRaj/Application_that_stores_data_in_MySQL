package com.example.dell.mysql;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText Username,Email,Password;
    private Button Register;
    private ProgressDialog progressDialog;
    private TextView textviewLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Username = findViewById(R.id.Username);
        Email = findViewById(R.id.Email);
        Password = findViewById(R.id.Password);
        Register = findViewById(R.id.Register);

        textviewLogin = findViewById(R.id.teextviewLogin);

        progressDialog = new ProgressDialog(this);
        Register.setOnClickListener(this);
        textviewLogin.setOnClickListener(this);
    }

    private void registerUser(){
        final String getEmail = Email.getText().toString().trim();
        final String getUsername = Username.getText().toString().trim();
        final String getPassword = Password.getText().toString().trim();

        progressDialog.setMessage("Registering User");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                            //Toast.makeText(getApplicationContext(),"1",Toast.LENGTH_LONG).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            //Toast.makeText(getApplicationContext(),"2",Toast.LENGTH_LONG).show();
                        }

                    }
                },
                new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.hide();
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                //Toast.makeText(getApplicationContext(),"3",Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("username",getUsername);
                params.put("email",getEmail);
                params.put("password",getPassword);
                return params;
            }
        };
       RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
        //Toast.makeText(getApplicationContext(),"4",Toast.LENGTH_LONG).show();
    }
    @Override
    public void onClick(View view) {
        if(view==Register)
        {
            registerUser();
        }
        if(view==textviewLogin)
        {
            startActivity(new Intent(this,LoginActivity.class));
        }
    }
}
