package com.example.agriculturalapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.animation.ObjectAnimator;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
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

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    TextView tv;
    Button lgn;
    TextInputLayout emailTextInputLayout;
    TextInputEditText emailEditText;
    EditText pwdEditText;
    String email,pwd;


    final long [] vibe = {0,500};
    final Uri notificationsound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait...");
        dialog.setCanceledOnTouchOutside(false);

        tv=findViewById(R.id.txtRegister);
        lgn=findViewById(R.id.btnLogin);

        emailTextInputLayout = findViewById(R.id.textInputLayout7);
        emailEditText = emailTextInputLayout.findViewById(R.id.text_input_edit_text_mail);
        pwdEditText =findViewById(R.id.editTextTextPassword);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rg();
            }
        });
        lgn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                email = emailEditText.getText().toString();
                pwd=pwdEditText.getText().toString();
                if(!email.equals("")&&!pwd.equals("")){
                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    String url ="http://192.168.100.23/LoginRegister/login.php";

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    if(response.equals("Success")){
                                        dialog.dismiss();
                                        Toast.makeText(Login.this, response, Toast.LENGTH_SHORT).show();
                                        emailEditText.setText(null);
                                        pwdEditText.setText(null);
                                        //Notify
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
                                        hm();
                                    }else{
                                        dialog.dismiss();
                                        Toast.makeText(Login.this, response, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            dialog.dismiss();
                            Toast.makeText(Login.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.e("Error",error.getLocalizedMessage());
                        }
                    }){
                        protected Map<String, String> getParams(){
                            Map<String, String> paramV = new HashMap<>();
                            paramV.put("email", email);
                            paramV.put("password", pwd);
                            return paramV;
                        }
                    };
                    queue.add(stringRequest);

                }else{
                    dialog.dismiss();
                    Toast.makeText(Login.this, "Enter all stared(*) fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void rg(){
        Intent n=new Intent(this,Register.class);
        startActivity(n);
    }
    public void hm(){
        Intent n=new Intent(this,MainActivity.class);
        n.putExtra("mail", email);
        startActivity(n);
    }
}