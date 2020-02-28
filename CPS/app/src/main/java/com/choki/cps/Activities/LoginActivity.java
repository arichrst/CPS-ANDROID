package com.choki.cps.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.choki.cps.Interfaces.IApiListener;
import com.choki.cps.Models.Login;
import com.choki.cps.Models.User;
import com.choki.cps.R;
import com.choki.cps.Utilities.Formatter;
import com.choki.cps.Utilities.Loader;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends MasterActivity {

    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    @BindView(R.id.input_email) EditText _emailText;
    @BindView(R.id.input_password)
    EditText _passwordText;
    @BindView(R.id.btn_login)
    Button _loginButton;
    @BindView(R.id.link_signup)
    TextView _signupLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        _loginButton = findViewById(R.id.btn_login);
        _signupLink = findViewById(R.id.link_signup);

        _passwordText = findViewById(R.id.input_password);
        _emailText = findViewById(R.id.input_email);

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                NAVIGATE.ToPage(RegisterActivity.class,true);
            }
        });

        NOTIFY.ShowToast("Matikan network data untuk mengaktifkan mode offline");

        if(FILE.IsLogin())
            NAVIGATE.ToPage(RoutesActivity.class,true);
    }

    void login()
    {
        Login user = new Login();
        user.setUsername(_emailText.getText().toString());
        user.setPassword( _passwordText.getText().toString());
        API.Login(user, new IApiListener() {
            @Override
            public void onStart() {
                LOADER.Show();
            }

            @Override
            public void onSuccess(String result, String message) {
                if(FILE.Profile() != null) {
                    NOTIFY.ShowToast("Selamat datang " + FILE.Profile().getName());
                    NAVIGATE.ToPage(RoutesActivity.class, true);
                }
                else
                {
                    NOTIFY.ShowToast("Username atau password tidak terdaftar");
                }
            }

            @Override
            public void onFailure(String message) {
                NOTIFY.ShowToast(message);
            }

            @Override
            public void onUnauthenticated() {

            }

            @Override
            public void onEnd() {
                LOADER.Dismiss();
            }
        });
    }

}
