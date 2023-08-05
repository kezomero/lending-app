package com.example.agriculturalapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView titleTextView;
    private LinearLayout hm;
    private List<Model> list;
    private Adapt adapter;
    private RecyclerView recyclerView;

    String email;
    Button b;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();


        hm=findViewById(R.id.hm);
        hm.setBackgroundColor(Color.GREEN);
        b=findViewById(R.id.eq);
        b.setBackgroundColor(Color.GREEN);


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

        //retrieve our email value
        email = getIntent().getStringExtra("mail");

        //then initialize adapter and recycleView

        recyclerView = findViewById(R.id.recycle);
        list = new ArrayList<>();
        adapter = new Adapt(this,list,email);

        //then format the recycle view

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        //but also show progress, user to be patient while loading data from server

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Loading tools...");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        //then receive data from php file, then response can be success or failure

        final StringRequest request = new StringRequest("http://192.168.100.23/LoginRegister/viewData.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();

                //make sure database exceptional are handled

                try {
                    JSONArray array = new JSONArray(response);

                    //now loop your incoming data

                    for (int loop = 0; loop < array.length(); loop++) {
                        JSONObject object = array.getJSONObject(loop);
                        list.add(new Model(object.getString("name"),
                                object.getString("id"),
                                object.getString("make"),
                                object.getString("category"),
                                object.getString("image"),
                                object.getString("cost")));
                    }
                    adapter.notifyDataSetChanged();

                }

                //whatever Error happens, then throw here in catch block

                catch (Exception e) {
                    AlertDialog alertDialog = new AlertDialog.Builder(getApplicationContext()).create();
                    alertDialog.setTitle("Failed");
                    alertDialog.setMessage("Error: " + e.getMessage());
                    alertDialog.show();
                }
            }

            //if response is failure give user the message

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
        Volley.newRequestQueue(this).add(request);

    }
    public void hm(View view){
        Intent n=new Intent(getApplicationContext(),MainActivity.class);
        n.putExtra("mail", email);
        startActivity(n);
    }public void up(View view){
        Intent n=new Intent(getApplicationContext(),UpluadEquipment.class);
        n.putExtra("mail", email);
        startActivity(n);
    }public void od(View view){
        Intent n=new Intent(getApplicationContext(),SearchTool.class);
        n.putExtra("mail", email);
        startActivity(n);
    }
    public void mo(View view){
        Intent n=new Intent(getApplicationContext(),MyOrders.class);
        n.putExtra("mail", email);
        startActivity(n);
    }
    public void ma(View view){
        Intent n=new Intent(getApplicationContext(),MyAccount.class);
        n.putExtra("mail", email);
        startActivity(n);
    }public void m(View view){
        Intent n=new Intent(getApplicationContext(),ToolDetails.class);
        n.putExtra("mail", email);
        startActivity(n);
    }public void prod(View view){
        Intent n=new Intent(getApplicationContext(),Products.class);
        n.putExtra("mail", email);
        startActivity(n);
    }public void inp(View view){
        Intent n=new Intent(getApplicationContext(),Resurces.class);
        n.putExtra("mail", email);
        startActivity(n);
    }public void store(View view){
        Intent n=new Intent(getApplicationContext(), Store.class);
        n.putExtra("mail", email);
        startActivity(n);
    }
}