package com.example.agriculturalapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Password extends AppCompatActivity {
    TextView titleTextView;
    private LinearLayout hm;
    String mail,password,op,np,cnp;
    EditText OPwd,NPwd,CNPwd;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        getSupportActionBar().hide();

        hm = findViewById(R.id.hm);
        hm.setBackgroundColor(Color.GREEN);//but also show progress, user to be patient while loading data from server

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait...");
        dialog.setCanceledOnTouchOutside(false);
        //dialog.show();

        //retrieve our value
        mail = getIntent().getStringExtra("mail");
        password = getIntent().getStringExtra("password");

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
        OPwd=findViewById(R.id.OPwd);
        NPwd=findViewById(R.id.NPwd);
        CNPwd=findViewById(R.id.CNPwd);
    }public void hm(View view) {
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
    }public void m() {
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
    }public void rg(View view){

        op = OPwd.getText().toString();
        np=NPwd.getText().toString();
        cnp=CNPwd.getText().toString();
        //but also show progress, user to be patient while loading data from server

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait...");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        if(!op.equals("")&&!np.equals("")&&!cnp.equals("")){
            if (cnp.equals(np)) {
                if (op.equals(password)) {
                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    String url = "http://192.168.100.23/LoginRegister/password.php";

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    if (response.equals("Success")) {
                                        dialog.dismiss();
                                        Toast.makeText(Password.this, "Password Changed", Toast.LENGTH_SHORT).show();
                                        m();
                                    } else {
                                        dialog.dismiss();
                                        Toast.makeText(Password.this, response, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            dialog.dismiss();
                            Toast.makeText(Password.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        protected Map<String, String> getParams() {
                            Map<String, String> paramV = new HashMap<>();
                            paramV.put("pwd", np);
                            paramV.put("email", mail);
                            return paramV;
                        }
                    };
                    queue.add(stringRequest);
                }else {
                    Toast.makeText(this, "Entered Old Password is invalid", Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(this, "New passwornd and confirm new password fields do not match", Toast.LENGTH_SHORT).show();
            }

        }else{
            dialog.dismiss();
            Toast.makeText(Password.this, "Enter all stared(*) fields", Toast.LENGTH_SHORT).show();
        }
    }
}