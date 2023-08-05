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

import java.util.HashMap;
import java.util.Map;

public class updateTool extends AppCompatActivity {

    TextView titleTextView;
    private LinearLayout hm;
    String mail, id,name,make,category,lendCost,ovdCost,lCont,lCounty,lLoc,mDays;
    EditText nm, mk,cat,lecst,ocst,cont,cnty,loc,dys;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_tool);
        getSupportActionBar().hide();

        hm = findViewById(R.id.hm);
        hm.setBackgroundColor(Color.GREEN);
        //retrieve our value
        mail = getIntent().getStringExtra("mail");
        name= getIntent().getStringExtra("name");
        id= getIntent().getStringExtra("id");
        make= getIntent().getStringExtra("make");
        category= getIntent().getStringExtra("category");
        lendCost= getIntent().getStringExtra("cost");
        ovdCost= getIntent().getStringExtra("overdue");
        lCont= getIntent().getStringExtra("contact");
        lCounty= getIntent().getStringExtra("county");
        lLoc= getIntent().getStringExtra("location");
        mDays= getIntent().getStringExtra("days");

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

        nm=findViewById(R.id.name);nm.setText(name);
        mk=findViewById(R.id.make);mk.setText(make);
        cat=findViewById(R.id.category);cat.setText(category);
        lecst=findViewById(R.id.cost);lecst.setText(lendCost);
        ocst=findViewById(R.id.overdue);ocst.setText(ovdCost);
        cont=findViewById(R.id.cont);cont.setText(lCont);
        cnty=findViewById(R.id.county);cnty.setText(lCounty);
        dys=findViewById(R.id.dy);dys.setText(mDays);
        loc=findViewById(R.id.loc);loc.setText(lLoc);
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
    }public void m() {
        Intent n = new Intent(getApplicationContext(), MyAccount.class);
        n.putExtra("mail", mail);
        startActivity(n);
    }

    public void lg(View view) {
        Intent n = new Intent(getApplicationContext(), Login.class);
        n.putExtra("mail", mail);
        startActivity(n);
    }public void l() {
        Intent n = new Intent(getApplicationContext(), Login.class);
        n.putExtra("mail", mail);
        startActivity(n);
    }public void store(View view){
        Intent n=new Intent(getApplicationContext(), Store.class);
        n.putExtra("mail", mail);
        startActivity(n);
    }public void store(){
        Intent n=new Intent(getApplicationContext(), Store.class);
        n.putExtra("mail", mail);
        startActivity(n);}
    public void rg(View view) {

        name = nm.getText().toString();
        make = mk.getText().toString();
        category = cat.getText().toString();
        lendCost = lecst.getText().toString();
        ovdCost = ocst.getText().toString();
        lCont = cont.getText().toString();
        lCounty = cnty.getText().toString();
        lLoc=loc.getText().toString();
        mDays=dys.getText().toString();

        //but also show progress, user to be patient while loading data from server

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait...");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        if (!name.equals("") && !make.equals("") && !category.equals("") && !lendCost.equals("") && !ovdCost.equals("") && !lCont.equals("") && !lCounty.equals("")&& !lLoc.equals("") && !mDays.equals("")) {
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            String url = "http://192.168.100.23/LoginRegister/updateTool.php";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.equals("Success")) {
                                dialog.dismiss();
                                Toast.makeText(updateTool.this, "Record Updated", Toast.LENGTH_SHORT).show();
                                store();
                            } else {
                                dialog.dismiss();
                                Toast.makeText(updateTool.this,"Failed"+ response+id, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    dialog.dismiss();
                    Toast.makeText(updateTool.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {
                protected Map<String, String> getParams() {
                    Map<String, String> paramV = new HashMap<>();
                    paramV.put("name", name);
                    paramV.put("make", make);
                    paramV.put("category", category);
                    paramV.put("cost", lendCost);
                    paramV.put("overdue", ovdCost);
                    paramV.put("id", id);
                    paramV.put("contact", lCont);
                    paramV.put("county", lCounty);
                    paramV.put("location", lLoc);
                    paramV.put("days", mDays);
                    return paramV;
                }
            };
            queue.add(stringRequest);

        } else {
            dialog.dismiss();
            Toast.makeText(updateTool.this, "Enter all stared(*) fields", Toast.LENGTH_SHORT).show();
        }
    }
}