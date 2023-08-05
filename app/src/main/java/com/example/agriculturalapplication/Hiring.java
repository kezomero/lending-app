package com.example.agriculturalapplication;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.se.omapi.Session;
import android.telephony.SmsManager;
import android.util.Base64;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
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

public class Hiring extends AppCompatActivity {
    TextView titleTextView,fileNameTextView;
    private LinearLayout hm;
    public static final int STORAGE_PERMISSION_CODE = 4655;
    private int PICK_IMAGE_RESULT = 1;
    EditText name, contact, days, farm, county, location;
    Bitmap imageToStore;
    Uri imagePath;
    Button choose;
    String mail;
    private static final  int PICK_IMAGE_REQUEST=99;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hiring);
        getSupportActionBar().hide();


        storagePermission();

        hm = findViewById(R.id.hm);
        hm.setBackgroundColor(Color.GREEN);


        //retrieve our email value
        mail = getIntent().getStringExtra("mail");

        fileNameTextView=findViewById(R.id.fileNameTextView);

        choose=findViewById(R.id.choose);
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent in = new Intent();
                    in.setType("image/*");
                    in.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(in,PICK_IMAGE_REQUEST);

                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        name = findViewById(R.id.name);
        contact = findViewById(R.id.contact);
        days = findViewById(R.id.days);
        farm = findViewById(R.id.farm);
        county = findViewById(R.id.county);
        location = findViewById(R.id.location);

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
    }
    //get our image
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
                imagePath = data.getData();
                imageToStore = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath);
                // Get the file name from the Uri
                String imageName = getFileName(imagePath);
                // Set the file name to the fileNameTextView
                fileNameTextView.setText(imageName);
            }
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    // Function to get the file name from the Uri
    private String getFileName(Uri uri) {
        String fileName = null;
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            if (nameIndex != -1) {
                fileName = cursor.getString(nameIndex);
            }
            cursor.close();
        }
        return fileName;
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
    }public void o() {
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
    }public void store(View view){
        Intent n=new Intent(getApplicationContext(), Store.class);
        n.putExtra("mail", mail);
        startActivity(n);
    }

    private void storagePermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void saveData(View view) {
        //but also show progress, user to be patient while loading data to server

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait...");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        // Get the values from the input fields
        String nm = name.getText().toString();
        String conta = contact.getText().toString();
        String dys = days.getText().toString();
        String far = farm.getText().toString();
        String cnty = county.getText().toString();
        String loc = location.getText().toString();

        // Retrieve data from the ToolDetails activity
        String nameItem = getIntent().getStringExtra("name");
        String cost = getIntent().getStringExtra("cost");
        String overdueCost = getIntent().getStringExtra("overdueCost");
        String contactLender = getIntent().getStringExtra("contact");
        String id = getIntent().getStringExtra("id");
        String ml=getIntent().getStringExtra("email");

        if (imageToStore!=null&&!nm.isEmpty()&& !mail.isEmpty() && !cnty.isEmpty() && !loc.isEmpty() && !conta.isEmpty()&& !dys.isEmpty()&& !far.isEmpty()) {

            ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
            imageToStore.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
            byte[] bytes=byteArrayOutputStream.toByteArray();
            final String base64Image= Base64.encodeToString(bytes,Base64.DEFAULT);
            RequestQueue queue = Volley.newRequestQueue(this);
            String url ="http://192.168.100.23/LoginRegister/toolHiring.php";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(response.equals("success")){
                                //Send an email to the lender to notify them that a tool is needed
                               dialog.dismiss();

                                name.setText(null);
                                contact.setText(null);
                                days.setText(null);
                                farm.setText(null);
                                county.setText(null);
                                location.setText(null);
                               Toast.makeText(getApplicationContext(), "Request Submited", Toast.LENGTH_SHORT).show();
                               o();
                            }else{
                                dialog.dismiss();
                                Toast.makeText(getApplicationContext(),nameItem+ response, Toast.LENGTH_SHORT).show();
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
                    paramV.put("image", base64Image);
                    paramV.put("name", nm);
                    paramV.put("farm", far);
                    paramV.put("contact", conta);
                    paramV.put("email", ml);
                    paramV.put("county", cnty);
                    paramV.put("location", loc);
                    paramV.put("days", dys);
                    paramV.put("item", nameItem);
                    paramV.put("cost", cost);
                    paramV.put("overdueCost", overdueCost);
                    paramV.put("contactLender", contactLender);
                    paramV.put("mail", mail);
                    paramV.put("id", id);
                    return paramV;
                }
            };
            queue.add(stringRequest);
        } else {
            Toast.makeText(this, "Please fill in all the fields with a *", Toast.LENGTH_SHORT).show();
        }
    }
}