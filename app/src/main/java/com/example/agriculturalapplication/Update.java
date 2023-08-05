package com.example.agriculturalapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class Update extends AppCompatActivity {
    TextView titleTextView;
    private LinearLayout hm;
    int selectedRadioButtonId;
    String mail, id,fName,sName,oName,gender,phone,county,email;
    EditText eml, idn,fNme,sNme,oNme,phon,cnty;
    RadioGroup radioGroupGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        getSupportActionBar().hide();

        hm = findViewById(R.id.hm);
        hm.setBackgroundColor(Color.GREEN);


        //retrieve our value
        email = getIntent().getStringExtra("mail");
        fName = getIntent().getStringExtra("fName");
        sName = getIntent().getStringExtra("sName");
        oName = getIntent().getStringExtra("oName");
        gender = getIntent().getStringExtra("gender");
        id = getIntent().getStringExtra("id");
        phone = getIntent().getStringExtra("phone");
        county = getIntent().getStringExtra("county");

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
        fNme=findViewById(R.id.fName);fNme.setText(fName);
        sNme=findViewById(R.id.sName);sNme.setText(sName);
        oNme=findViewById(R.id.oName);oNme.setText(oName);
        eml=findViewById(R.id.email);eml.setText(email);
        idn=findViewById(R.id.id);idn.setText(id);
        phon=findViewById(R.id.phone);phon.setText(phone);
        cnty=findViewById(R.id.county);cnty.setText(county);
        radioGroupGender = findViewById(R.id.radioGroupGender);
        RadioButton male,female;
        male=findViewById(R.id.male);
        female=findViewById(R.id.female);
        if (gender.equals("Male")){
            male.setChecked(true);
        }else{
            female.setChecked(true);
        }


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
    }public void l() {
        Intent n = new Intent(getApplicationContext(), Login.class);
        n.putExtra("mail", mail);
        startActivity(n);
    }public void rg(View view){

        fName = fNme.getText().toString();
        sName=sNme.getText().toString();
        oName=oNme.getText().toString();
        mail=eml.getText().toString();
        id=idn.getText().toString();
        phone=phon.getText().toString();
        county=cnty.getText().toString();
        //finding gender entered
        selectedRadioButtonId = radioGroupGender.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
        gender = selectedRadioButton.getText().toString();
        //but also show progress, user to be patient while loading data from server

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait...");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        if(!fName.equals("")&&!sName.equals("")&&!gender.equals("")&&!mail.equals("")&&!id.equals("")&&!phone.equals("")&&!county.equals("")){
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            String url ="http://192.168.100.23/LoginRegister/update.php";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(response.equals("Success")){
                                dialog.dismiss();
                                Toast.makeText(Update.this, "Record Updated", Toast.LENGTH_SHORT).show();
                                if(mail.equals(email)) {
                                    m();
                                }else{
                                    l();
                                }
                            }else{
                                dialog.dismiss();
                                Toast.makeText(Update.this, response, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    dialog.dismiss();
                    Toast.makeText(Update.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }){
                protected Map<String, String> getParams(){
                    Map<String, String> paramV = new HashMap<>();
                    paramV.put("fName", fName);
                    paramV.put("sName", sName);
                    paramV.put("oName", oName);
                    paramV.put("email", mail);
                    paramV.put("mail", email);
                    paramV.put("id", id);
                    paramV.put("phone", phone);
                    paramV.put("county", county);
                    paramV.put("gender", gender);
                    return paramV;
                }
            };
            queue.add(stringRequest);

        }else{
            dialog.dismiss();
            Toast.makeText(Update.this, "Enter all stared(*) fields", Toast.LENGTH_SHORT).show();
        }
    }
}