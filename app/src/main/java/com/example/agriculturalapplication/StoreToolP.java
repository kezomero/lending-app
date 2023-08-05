package com.example.agriculturalapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
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

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StoreToolP extends AppCompatActivity {

    TextView titleTextView;
    private LinearLayout hm;
    private List<ModelToolP> list;
    String mail, id, name, quantity, category, image, cost, contact, email, county, location, status;
    Intent n;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_tool_p);
        getSupportActionBar().hide();

        hm = findViewById(R.id.hm);
        hm.setBackgroundColor(Color.GREEN);


        //retrieve our email value
        mail = getIntent().getStringExtra("mail");
        id = getIntent().getStringExtra("id");


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

        if (id != null) {


            RequestQueue queue = Volley.newRequestQueue(this);
            String url = "http://192.168.100.23/LoginRegister/toolDetailsP.php";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.equals("No results found.")) {
                                Toast.makeText(getApplicationContext(), "Failure" + response, Toast.LENGTH_SHORT).show();

                            } else {
                                try {
                                    JSONArray jsonArray = new JSONArray(response);
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                                        name = jsonObject.getString("name");
                                        quantity = jsonObject.getString("quantity");
                                        category = jsonObject.getString("category");
                                        image = jsonObject.getString("image");
                                        cost = jsonObject.getString("cost");
                                        contact = jsonObject.getString("contact");
                                        email = jsonObject.getString("email");
                                        county = jsonObject.getString("county");
                                        location = jsonObject.getString("location");
                                        status = jsonObject.getString("status");


                                        ModelToolP tool = new ModelToolP(name, quantity, category, image, cost,  contact, email, county, location,  status);
                                        list.add(tool);


                                    }

                                    // Display or use the retrieved data from the list
                                    for (ModelToolP tool : list) {
                                        // Example: Log the tool name
                                        TextView name = findViewById(R.id.name);
                                        TextView quantity = findViewById(R.id.quantity);
                                        TextView category = findViewById(R.id.category);
                                        TextView cost = findViewById(R.id.cost);
                                        TextView ovCost = findViewById(R.id.overdue);
                                        TextView contact = findViewById(R.id.contact);
                                        TextView email = findViewById(R.id.email);
                                        TextView county = findViewById(R.id.county);
                                        TextView location = findViewById(R.id.location);
                                        TextView days = findViewById(R.id.days);
                                        TextView status = findViewById(R.id.status);

                                        name.setText(tool.getName());
                                        quantity.setText("Quantity: " + tool.getQuantity());
                                        category.setText("Category: " + tool.getCategory());
                                        cost.setText("Cost per Unit: " + tool.getCost());
                                        contact.setText("Contact: " + tool.getContact());
                                        email.setText("Email: " + tool.getEmail());
                                        county.setText("County: " + tool.getCounty());
                                        location.setText("Exact Location: " + tool.getlocation());
                                        status.setText("Status: " + tool.getStatus());
                                        dialog.dismiss();

                                        String imageUrl = tool.getImage();
                                        ImageView image = findViewById(R.id.image);

                                        StoreToolP.GetImageFromUrl getImageTask = new StoreToolP.GetImageFromUrl(image, getApplicationContext());
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
                    Toast.makeText(getApplicationContext(), "Failed " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {
                protected Map<String, String> getParams() {
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

    public void hm(View view) {
        Intent n = new Intent(getApplicationContext(), Products.class);
        n.putExtra("mail", mail);
        startActivity(n);
    }

    public void up(View view) {
        Intent n = new Intent(getApplicationContext(), UpluadP.class);
        n.putExtra("mail", mail);
        startActivity(n);
    }

    public void od(View view) {
        Intent n = new Intent(getApplicationContext(), SearchToolP.class);
        n.putExtra("mail", mail);
        startActivity(n);
    }

    public void mo(View view) {
        Intent n = new Intent(getApplicationContext(), MyOrdersP.class);
        n.putExtra("mail", mail);
        startActivity(n);
    }

    public void ma(View view) {
        Intent n = new Intent(getApplicationContext(), MyAccount.class);
        n.putExtra("mail", mail);
        startActivity(n);
    }

    public void hr(View view) {
        startActivity(n);
    }

    public void store(View view) {
        Intent n = new Intent(getApplicationContext(), StoreP.class);
        n.putExtra("mail", mail);
        startActivity(n);
    }

    public void stor() {
        Intent n = new Intent(getApplicationContext(), StoreP.class);
        n.putExtra("mail", mail);
        startActivity(n);
    }

    public void update(View view) {
        Intent n = new Intent(getApplicationContext(), updateToolP.class);
        //pass data to update class
        n.putExtra("name", name);
        n.putExtra("quantity", quantity);
        n.putExtra("category", category);
        n.putExtra("cost", cost);
        n.putExtra("contact", contact);
        n.putExtra("mail", mail);
        n.putExtra("county", county);
        n.putExtra("location", location);
        n.putExtra("id", id);
        startActivity(n);
    }

    private static class GetImageFromUrl extends AsyncTask<String, Void, Bitmap> {

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

    public void delt(View view) {
        //but also show progress, user to be patient while loading data to server

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait...");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        // Get the values from the input fields

        if (id != null) {

            RequestQueue queue = Volley.newRequestQueue(this);
            String url = "http://192.168.100.23/LoginRegister/deleteToolP.php";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.equals("success")) {
                                //Send an email to the lender to notify them that a tool is needed
                                dialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Product Deleted"+id, Toast.LENGTH_SHORT).show();
                                stor();
                            } else {
                                dialog.dismiss();
                                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Failed " + error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }) {
                protected Map<String, String> getParams() {
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
}