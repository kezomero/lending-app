package com.example.agriculturalapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationManager;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ProgressBar;
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
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.vishnusivadas.advanced_httpurlconnection.FetchData;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    TextView tv;
    String email,fName,sName,oName,id,pwd,mNo,country,gender;
    int selectedRadioButtonId;
    RadioGroup radioGroupGender;
    Button b;
    TextInputLayout emailTextInputLayout,fNameTextInputLayout,countryTextInputLayout,sNameTextInputLayout,mNoTextInputLayout,idTextInputLayout,oNameTextInputLayout,pwdTextInputLayout;
    TextInputEditText emailEditText,fNameEditText,sNameEditText,oNameEditText,idEditText,pwdEditText,mNoEditText,countryEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        tv=findViewById(R.id.txtLogin);
        //user input
        emailTextInputLayout = findViewById(R.id.email);
        emailEditText = emailTextInputLayout.findViewById(R.id.text_input_edit_text_mail);
        fNameTextInputLayout = findViewById(R.id.fName);
        fNameEditText = fNameTextInputLayout.findViewById(R.id.text_input_edit_text_fName);
        sNameTextInputLayout = findViewById(R.id.sName);
        sNameEditText = sNameTextInputLayout.findViewById(R.id.text_input_edit_text_sName);
        oNameTextInputLayout = findViewById(R.id.oname);
        oNameEditText = oNameTextInputLayout.findViewById(R.id.text_input_edit_text_oName);
        idTextInputLayout = findViewById(R.id.id);
        idEditText = idTextInputLayout.findViewById(R.id.text_input_edit_text_id);
        pwdTextInputLayout = findViewById(R.id.password);
        pwdEditText = pwdTextInputLayout.findViewById(R.id.text_input_edit_text_pwd);
        mNoTextInputLayout = findViewById(R.id.mNumber);
        mNoEditText = mNoTextInputLayout.findViewById(R.id.text_input_edit_text_mNo);
        countryTextInputLayout = findViewById(R.id.country);
        countryEditText = countryTextInputLayout.findViewById(R.id.text_input_edit_text_country);
        radioGroupGender = findViewById(R.id.radioGroupGender);

        b=findViewById(R.id.btnRegister);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rg();
                if(!fName.equals("")&&!sName.equals("")&&!email.equals("")&&!gender.equals("")&&!id.equals("")&&!pwd.equals("")&&!mNo.equals("")&&!country.equals("")){
                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    String url ="http://192.168.100.23/LoginRegister/signup.php";

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    if(response.equals("Success")){
                                        Toast.makeText(Register.this, "Registered", Toast.LENGTH_SHORT).show();
                                        //user input
                                        emailEditText.setText(null);
                                        fNameEditText.setText(null);
                                        sNameEditText.setText(null);
                                        oNameEditText.setText(null);
                                        idEditText.setText(null);
                                        pwdEditText.setText(null);
                                        mNoEditText.setText(null);
                                        countryEditText.setText(null);
                                        lg();

                                        //Notify
                                        final long [] vibe = {0,500};
                                        final Uri notificationsound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                                        NotificationCompat.Builder mbuilder = (NotificationCompat.Builder)
                                                new NotificationCompat.Builder(getApplicationContext())
                                                        .setSmallIcon(R.drawable.icon,10)
                                                        .setSound(notificationsound)
                                                        .setVibrate(vibe)
                                                        .setContentTitle("FarmConnect")
                                                        .setContentText("You have succefully created an account with us. Login to enjoy our services");

                                        NotificationManager notificationManager = (NotificationManager)
                                                getSystemService(NOTIFICATION_SERVICE);
                                        notificationManager.notify(0,mbuilder.build());
                                    }else{
                                        Toast.makeText(Register.this, response, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(Register.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.e("Error",error.getLocalizedMessage());
                        }
                    }){
                        protected Map<String, String> getParams(){
                            Map<String, String> paramV = new HashMap<>();
                            paramV.put("firstName", fName);
                            paramV.put("secondName", sName);
                            paramV.put("otherName", oName);
                            paramV.put("email", email);
                            paramV.put("gender", gender);
                            paramV.put("id", id);
                            paramV.put("password", pwd);
                            paramV.put("mobileNumber", mNo);
                            paramV.put("country", country);
                            return paramV;
                        }
                    };
                    queue.add(stringRequest);

                }else{
                    Toast.makeText(Register.this, "Enter all stared(*) fields", Toast.LENGTH_SHORT).show();
                }

            }
        });

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lg();
            }
        });
    }
    public void lg(){
        Intent n=new Intent(this,Login.class);
        startActivity(n);
    }

    public void rg(){

        email = emailEditText.getText().toString();
        fName=fNameEditText.getText().toString();
        sName=sNameEditText.getText().toString();
        oName=oNameEditText.getText().toString();
        id=idEditText.getText().toString();
        pwd=pwdEditText.getText().toString();
        mNo=mNoEditText.getText().toString();
        country=countryEditText.getText().toString();
        //finding gender entered
        selectedRadioButtonId = radioGroupGender.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
        gender = selectedRadioButton.getText().toString();



    }

}