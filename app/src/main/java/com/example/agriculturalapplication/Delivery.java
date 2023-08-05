package com.example.agriculturalapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class Delivery extends AppCompatActivity {
    TextView titleTextView;
    private LinearLayout hm;
    String mail,id;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);
        getSupportActionBar().hide();

        hm=findViewById(R.id.hm);
        hm.setBackgroundColor(Color.GREEN);

        //retrieve our email and id value
        mail = getIntent().getStringExtra("mail");
        id = getIntent().getStringExtra("id");

        titleTextView = findViewById(R.id.titleTextView);
        titleTextView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int viewWidth = titleTextView.getWidth(); // Get the width of the TextView
                Animation animation = new TranslateAnimation(viewWidth, -viewWidth, 0, 0);
                animation.setDuration(5000); // Set the duration of animation (adjust as needed)
                animation.setRepeatCount(Animation.INFINITE); // Set the repeat count for infinite scrolling
                animation.setRepeatMode(Animation.RESTART); // Set the repeat mode for scrolling animation
                titleTextView.startAnimation(animation);

                // Remove the listener to avoid unnecessary recalculations
                titleTextView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }public void delivered(View view) {
        //but also show progress, user to be patient while loading data to server

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait...");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        // Get the values from the input fields

        if (id!=null) {

            RequestQueue queue = Volley.newRequestQueue(this);
            String url ="http://192.168.100.23/LoginRegister/delivered.php";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(response.equals("success")){
                                //Send an email to the lender to notify them that a tool is needed
                                dialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Request Submited", Toast.LENGTH_SHORT).show();
                            }else{
                                dialog.dismiss();
                                Toast.makeText(getApplicationContext(),response, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Failed "+error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }){
                protected Map<String, String> getParams(){
                    Map<String, String> paramV = new HashMap<>();
                    paramV.put("id", id);
                    return paramV;
                }
            };
            queue.add(stringRequest);
        } else {
            Toast.makeText(this, "Your id is null", Toast.LENGTH_SHORT).show();
        }
    }public void canceled(View view) {
        //but also show progress, user to be patient while loading data to server

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait...");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        // Get the values from the input fields

        if (id!=null) {

            RequestQueue queue = Volley.newRequestQueue(this);
            String url ="http://192.168.100.23/LoginRegister/canceled.php";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(response.equals("success")){
                                //Send an email to the lender to notify them that a tool is needed
                                dialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Request Submited", Toast.LENGTH_SHORT).show();
                            }else{
                                dialog.dismiss();
                                Toast.makeText(getApplicationContext(),response, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Failed "+error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }){
                protected Map<String, String> getParams(){
                    Map<String, String> paramV = new HashMap<>();
                    paramV.put("id", id);
                    return paramV;
                }
            };
            queue.add(stringRequest);
        } else {
            Toast.makeText(this, "Your id is null", Toast.LENGTH_SHORT).show();
        }
    }
    public void deliver(View view){
        //but also show progress, user to be patient while loading data to server

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait...");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        if (id!=null) {

            RequestQueue queue = Volley.newRequestQueue(this);
            String url ="http://192.168.100.23/LoginRegister/deliver.php";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(response.equals("success")){
                                dialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Delivery Initiated", Toast.LENGTH_SHORT).show();
                            }else{
                                dialog.dismiss();
                                Toast.makeText(getApplicationContext(),response, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Failed "+error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }){
                protected Map<String, String> getParams(){
                    Map<String, String> paramV = new HashMap<>();
                    paramV.put("id", id);
                    return paramV;
                }
            };
            queue.add(stringRequest);
        } else {
            dialog.dismiss();
            Toast.makeText(this, "Your id is null", Toast.LENGTH_SHORT).show();
        }
    }
    public void hm(View view){
        Intent n=new Intent(getApplicationContext(),MainActivity.class);
        n.putExtra("mail", mail);
        startActivity(n);
    }public void up(View view){
        Intent n=new Intent(getApplicationContext(),UpluadEquipment.class);
        n.putExtra("mail", mail);
        startActivity(n);
    }public void od(View view){
        Intent n=new Intent(getApplicationContext(),SearchTool.class);
        n.putExtra("mail", mail);
        startActivity(n);
    }
    public void mo(View view){
        Intent n=new Intent(getApplicationContext(),MyOrders.class);
        n.putExtra("mail", mail);
        startActivity(n);
    }
    public void ma(View view){
        Intent n=new Intent(getApplicationContext(),MyAccount.class);
        n.putExtra("mail", mail);
        startActivity(n);
    }public void store(View view){
        Intent n=new Intent(getApplicationContext(), Store.class);
        n.putExtra("mail", mail);
        startActivity(n);
    }
}