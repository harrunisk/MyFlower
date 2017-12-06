package com.example.harun.myflower;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    TextView signup;
    EditText username;
    EditText pasword;
    Button login;
    FirebaseAuth auth;
    SharedPreferences preferences;//preferences referansı
    SharedPreferences.Editor editor;
    private static final int REQUEST_SIGNUP = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText) findViewById(R.id.input_email);
        pasword = (EditText) findViewById(R.id.input_password);
        login = (Button) findViewById(R.id.btn_login);
        auth = FirebaseAuth.getInstance();

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = preferences.edit(); //

        String mail = preferences.getString("email", "");
        username.setText(mail);
        String sifre = preferences.getString("sifre", "");
        pasword.setText(sifre);


       /* if(preferences.getBoolean("login", false)){
            Intent i = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(i);
            finish();
        }*/

        signup = (TextView) findViewById(R.id.link_signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sign_in();


            }
        });


    }

    public void sign_in() {


        final String inputEmail = username.getText().toString().trim();

        final String inputPassword = pasword.getText().toString().trim();

        if (TextUtils.isEmpty(inputEmail)) {
            username.setError("email girin");
            Toast.makeText(getApplicationContext(), "Email Adresinizi girin", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(inputPassword)) {
            pasword.setError("Sifre Girin");
            Toast.makeText(getApplicationContext(), "Şifrenizi Girin", Toast.LENGTH_SHORT).show();
        } else if (inputPassword.length() < 6) {
            Toast.makeText(getApplicationContext(), "Şifreniz 6 karakter olmali!", Toast.LENGTH_SHORT).show();
        } else if(InternetKontrol()) {

            final ProgressDialog progressDialog = new ProgressDialog(Login.this,
                    R.style.My_AppTheme_Dark_Dialog);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Giris Yapiliyor");
            progressDialog.show();

            auth.signInWithEmailAndPassword(inputEmail, inputPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        editor.putBoolean("login", true);
                        Intent i = new Intent(Login.this, AnasayfaActivity.class);
                        i.putExtra("username", inputEmail);
                        startActivity(i);

                        editor.putString("email", inputEmail);//email değeri
                        editor.putString("sifre", inputPassword);//şifre değeri
                        editor.putBoolean("login", true);
                        editor.apply();

                        Toast.makeText(getApplicationContext(), "Giriş Yapildi", Toast.LENGTH_SHORT).show();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Kullanici Bulunamadi", Toast.LENGTH_LONG).show();
                    }
                }
            });

        }
        else Toast.makeText(getApplicationContext(),"INTERNET BAĞLI DEĞİL ",Toast.LENGTH_LONG).show();


    }

    public boolean InternetKontrol() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager.getActiveNetworkInfo() != null && manager.getActiveNetworkInfo().isAvailable() && manager.getActiveNetworkInfo().isConnected()) {
            return true;
        } else {
            return false;
        }
    }
}


