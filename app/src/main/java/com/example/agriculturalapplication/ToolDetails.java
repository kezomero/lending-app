package com.example.agriculturalapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ToolDetails extends AppCompatActivity {
    TextView titleTextView;
    private LinearLayout hm;
    private List<ModelTool> list;
    String mail;
    Intent n;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tool_details);
        getSupportActionBar().hide();

        hm=findViewById(R.id.hm);
        hm.setBackgroundColor(Color.GREEN);


        //retrieve our email value
        mail = getIntent().getStringExtra("mail");


        //but also show progress, user to be patient while loading data from server

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait...");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        list = new ArrayList<>();

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
        // Get the 'id' value from the intent
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");

        if (id!=null) {


            RequestQueue queue = Volley.newRequestQueue(this);
            String url ="http://192.168.100.23/LoginRegister/toolDetails.php";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(response.equals("No results found.")){
                                Toast.makeText(getApplicationContext(), "Failure"+response, Toast.LENGTH_SHORT).show();

                            }else{
                                try {
                                    JSONArray jsonArray = new JSONArray(response);
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                                        String name = jsonObject.getString("name");
                                        String make = jsonObject.getString("make");
                                        String category = jsonObject.getString("category");
                                        String image = jsonObject.getString("image");
                                        String cost = jsonObject.getString("cost");
                                        String overdueCost = jsonObject.getString("overdueCost");
                                        String contact = jsonObject.getString("contact");
                                        String email = jsonObject.getString("email");
                                        String county = jsonObject.getString("county");
                                        String location = jsonObject.getString("location");
                                        String maxDays = jsonObject.getString("maxLendDays");
                                        String status = jsonObject.getString("status");


                                        ModelTool tool = new ModelTool(name, make, category, image, cost, overdueCost, contact, email, county, location, maxDays,status);
                                        list.add(tool);

                                        //pass data to Hiring class
                                        n = new Intent(getApplicationContext(), Hiring.class);
                                        n.putExtra("name", name);
                                        n.putExtra("cost", cost);
                                        n.putExtra("overdueCost", overdueCost);
                                        n.putExtra("contact", contact);
                                        n.putExtra("mail", mail);
                                        n.putExtra("email",email);
                                        n.putExtra("id",id);
                                    }

                                    // Display or use the retrieved data from the list
                                    for (ModelTool tool : list) {
                                        // Example: Log the tool name
                                        TextView name=findViewById(R.id.name);
                                        TextView make=findViewById(R.id.make);
                                        TextView category=findViewById(R.id.category);
                                        TextView cost=findViewById(R.id.cost);
                                        TextView ovCost=findViewById(R.id.overdue);
                                        TextView contact=findViewById(R.id.contact);
                                        TextView email=findViewById(R.id.email);
                                        TextView county=findViewById(R.id.county);
                                        TextView location=findViewById(R.id.location);
                                        TextView days=findViewById(R.id.days);
                                        TextView status=findViewById(R.id.status);

                                        name.setText(tool.getName());
                                        make.setText("Make: "+tool.getMake());
                                        category.setText("Category: "+tool.getCategory());
                                        cost.setText("Cost per Day: "+tool.getCost());
                                        ovCost.setText("Overdue Cost per Day: "+tool.getOverdueCost());
                                        contact.setText("Lender Contact: "+tool.getContact());
                                        email.setText("Lender Email: "+tool.getEmail());
                                        county.setText("Lender County: "+tool.getCounty());
                                        location.setText("Lender Exact Location: "+tool.getlocation());
                                        days.setText("Max Lending Days: "+tool.getMaxDays());
                                        status.setText("Status: "+tool.getStatus());
                                        dialog.dismiss();

                                        String imageUrl = tool.getImage();
                                        ImageView image=findViewById(R.id.image);

                                        GetImageFromUrl getImageTask = new GetImageFromUrl(image,getApplicationContext());
                                        getImageTask.execute(imageUrl);
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
                    Toast.makeText(getApplicationContext(), "Failed "+error.getMessage(), Toast.LENGTH_SHORT).show();
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
            Toast.makeText(this, "Your item id is null", Toast.LENGTH_SHORT).show();
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
    }public void hr(View view) {
        startActivity(n);
    }public void store(View view){
        Intent n=new Intent(getApplicationContext(), Store.class);
        n.putExtra("mail", mail);
        startActivity(n);
    }private static class GetImageFromUrl extends AsyncTask<String, Void, Bitmap> {

        private ImageView imageView;
        private Context context;
        private ProgressDialog dialog;

        public GetImageFromUrl(ImageView imageView, Context context) {
            this.imageView = imageView;
            this.context = context;
            this.dialog = new ProgressDialog(context);
        }

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Loading images...");
            dialog.setCanceledOnTouchOutside(false);
            //dialog.show();
        }

        @Override
        protected Bitmap doInBackground(String... url) {
            String urlDisplay = url[0];
            Bitmap bitmap = null;

            try {
                URL imageUrl = new URL("http://192.168.100.23/LoginRegister/" + urlDisplay);
                HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
                conn.setDoInput(true);
                conn.connect();
                InputStream inputStream = conn.getInputStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
                //dialog.dismiss();
            } catch (IOException e) {
                Toast.makeText(context, "Error loading image: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }

            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }

            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
            } else {
                Toast.makeText(context, "Bitmap null", Toast.LENGTH_SHORT).show();
            }
        }
    }
}