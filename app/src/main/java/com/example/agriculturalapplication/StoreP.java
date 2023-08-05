package com.example.agriculturalapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StoreP extends AppCompatActivity {

    TextView titleTextView;
    private LinearLayout hm;
    String mail;
    private List<ModelStoreP> list;
    private AdaptStoreP adapter;
    private RecyclerView recyclerView;
    private String baseUrl = "http://192.168.100.23/LoginRegister/storeP.php?email=";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_p);
        getSupportActionBar().hide();

        hm=findViewById(R.id.hm9);
        hm.setBackgroundColor(Color.GREEN);

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

        //then initialize adapter and recycleView

        recyclerView = findViewById(R.id.recycle);
        list = new ArrayList<>();
        adapter = new AdaptStoreP(this,list,mail);

        //then format the recycle view

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));


        //but also show progress, user to be patient while loading data from server

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait...");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        // Build the URL with the email parameter
        String url = baseUrl + mail;

        // Create a JSONArrayRequest instead of a StringRequest to handle JSON array response
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        dialog.dismiss();

                        try {
                            // Loop through the JSON array and retrieve data
                            for (int loop = 0; loop < response.length(); loop++) {
                                JSONObject object = response.getJSONObject(loop);
                                list.add(new ModelStoreP(object.getString("name"),
                                        object.getString("id"),
                                        object.getString("quantity"),
                                        object.getString("category"),
                                        object.getString("image"),
                                        object.getString("cost")));
                            }

                            adapter.notifyDataSetChanged();
                        } catch (Exception e) {
                            AlertDialog alertDialog = new AlertDialog.Builder(getApplicationContext()).create();
                            alertDialog.setTitle("Failed");
                            alertDialog.setMessage("Error: " + e.getMessage());
                            alertDialog.show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        Toast.makeText(StoreP.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                });

        // Add the request to the Volley request queue
        Volley.newRequestQueue(this).add(request);
    }
    public void hm(View view){
        Intent n=new Intent(getApplicationContext(),Products.class);
        n.putExtra("mail", mail);
        startActivity(n);
    }public void up(View view){
        Intent n=new Intent(getApplicationContext(),UpluadP.class);
        n.putExtra("mail", mail);
        startActivity(n);
    }public void od(View view){
        Intent n=new Intent(getApplicationContext(),SearchToolP.class);
        n.putExtra("mail", mail);
        startActivity(n);
    }
    public void mo(View view){
        Intent n=new Intent(getApplicationContext(),MyOrdersP.class);
        n.putExtra("mail", mail);
        startActivity(n);
    }
    public void ma(View view){
        Intent n=new Intent(getApplicationContext(),MyAccount.class);
        n.putExtra("mail", mail);
        startActivity(n);
    }
    public void m(View view){
        Intent n=new Intent(getApplicationContext(),MyAccount.class);
        n.putExtra("mail", mail);
        startActivity(n);
    }public void co(View view){
        Intent n=new Intent(getApplicationContext(),ClientOrders.class);
        n.putExtra("mail", mail);
        startActivity(n);
    }public void d(View view){
        Intent n=new Intent(getApplicationContext(),OrderDetail.class);
        n.putExtra("mail", mail);
        startActivity(n);
    }public void store(View view){
        Intent n=new Intent(getApplicationContext(),StoreP.class);
        n.putExtra("mail", mail);
        startActivity(n);
    }
}