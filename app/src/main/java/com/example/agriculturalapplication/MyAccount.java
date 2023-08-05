package com.example.agriculturalapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyAccount extends AppCompatActivity {
    TextView titleTextView;
    private LinearLayout hm;
    private List<ModelAccount> list;
    String mail, id,fName,sName,oName,email,idN,gender,password,phone,county;
    Intent n;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        getSupportActionBar().hide();

        hm = findViewById(R.id.hm);
        hm.setBackgroundColor(Color.GREEN);//but also show progress, user to be patient while loading data from server

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait...");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        list = new ArrayList<>();


        //retrieve our email value
        mail = getIntent().getStringExtra("mail");
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
        if (mail != null) {


            RequestQueue queue = Volley.newRequestQueue(this);
            String url = "http://192.168.100.23/LoginRegister/account.php";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.equals("No results found.")) {
                                dialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Failure" + response, Toast.LENGTH_SHORT).show();

                            } else {
                                try {
                                    JSONArray jsonArray = new JSONArray(response);
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                                        fName = jsonObject.getString("fName");
                                        sName = jsonObject.getString("sName");
                                        oName = jsonObject.getString("oName");
                                        email = jsonObject.getString("email");
                                        gender = jsonObject.getString("gender");
                                        idN = jsonObject.getString("id");
                                        password = jsonObject.getString("password");
                                        phone = jsonObject.getString("phone");
                                        county = jsonObject.getString("county");


                                        ModelAccount tool = new ModelAccount(fName, sName, oName, email, gender, idN, phone, county);
                                        list.add(tool);
                                    }

                                    // Display or use the retrieved data from the list
                                    for (ModelAccount tool : list) {
                                        // Example: Log the tool name
                                        TextView fName = findViewById(R.id.fName);
                                        TextView sName = findViewById(R.id.sName);
                                        TextView oName = findViewById(R.id.oName);
                                        TextView email = findViewById(R.id.email);
                                        TextView gender = findViewById(R.id.gender);
                                        TextView id = findViewById(R.id.id);
                                        TextView phone = findViewById(R.id.phone);
                                        TextView county = findViewById(R.id.county);

                                        fName.setText(tool.getfName());
                                        sName.setText(tool.getsName());
                                        oName.setText(tool.getoName());
                                        email.setText(tool.getEmail());
                                        gender.setText(tool.getGender());
                                        id.setText(tool.getId());
                                        phone.setText(tool.getPhone());
                                        county.setText(tool.getCounty());
                                        dialog.dismiss();
                                    }
                                } catch (Exception e) {
                                    dialog.dismiss();
                                    Toast.makeText(getApplicationContext(), "Failure: " + e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Failed " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {
                protected Map<String, String> getParams() {
                    Map<String, String> paramV = new HashMap<>();
                    paramV.put("mail", mail);
                    return paramV;
                }
            };
            queue.add(stringRequest);
        } else {
            Toast.makeText(this, "Your email is null", Toast.LENGTH_SHORT).show();
        }
    }

    public void hm(View view) {
        Intent n = new Intent(getApplicationContext(), MainActivity.class);
        n.putExtra("mail", mail);
        startActivity(n);
    }

    public void up(View view) {
        Intent n = new Intent(getApplicationContext(), UpluadEquipment.class);
        n.putExtra("mail", mail);
        startActivity(n);
    }

    public void od(View view) {
        Intent n = new Intent(getApplicationContext(), SearchTool.class);
        n.putExtra("mail", mail);
        startActivity(n);
    }

    public void mo(View view) {
        Intent n = new Intent(getApplicationContext(), MyOrders.class);
        n.putExtra("mail", mail);
        startActivity(n);
    }

    public void ma(View view) {
        Intent n = new Intent(getApplicationContext(), MyAccount.class);
        n.putExtra("mail", mail);
        startActivity(n);
    }

    public void lg(View view) {
        Intent n = new Intent(getApplicationContext(), Login.class);
        n.putExtra("mail", mail);
        startActivity(n);
    }public void store(View view){
        Intent n=new Intent(getApplicationContext(), Store.class);
        n.putExtra("mail", mail);
        startActivity(n);
    }

    public void update(View view) {
        Intent n = new Intent(getApplicationContext(), Update.class);
        n.putExtra("mail", mail);
        n.putExtra("fName",fName);
        n.putExtra("sName",sName);
        n.putExtra("oName",oName);
        n.putExtra("gender",gender);
        n.putExtra("id",idN);
        n.putExtra("phone",phone);
        n.putExtra("county",county);
        startActivity(n);
    }public void pwd(View view){
        Intent n=new Intent(getApplicationContext(),Password.class);
        n.putExtra("mail", mail);
        n.putExtra("password",password);
        startActivity(n);
    }
}