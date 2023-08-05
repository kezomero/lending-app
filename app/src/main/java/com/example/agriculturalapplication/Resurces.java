package com.example.agriculturalapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Resurces extends AppCompatActivity {
    TextView titleTextView;
    private LinearLayout hm;
    String email;
    Button b;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resurces);
        getSupportActionBar().hide();

        b=findViewById(R.id.res);
        b.setBackgroundColor(Color.GREEN);
        hm=findViewById(R.id.hm);
        hm.setBackgroundColor(Color.GREEN);


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
    }
    public void hm(View view){
        Intent n=new Intent(getApplicationContext(),Resurces.class);
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