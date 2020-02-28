package com.choki.cps.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.choki.cps.Interfaces.IApiListener;
import com.choki.cps.Models.User;
import com.choki.cps.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends MasterActivity {

    private static final String TAG = "SignupActivity";

    @BindView(R.id.input_name) EditText _nameText;
    @BindView(R.id.input_email) EditText _emailText;
    @BindView(R.id.input_mobile) EditText _mobileText;
    @BindView(R.id.input_password) EditText _passwordText;
    @BindView(R.id.input_reEnterPassword)
    EditText _reEnterPasswordText;
    @BindView(R.id.btn_signup)
    Button _signupButton;
    @BindView(R.id.link_login)
    TextView _loginLink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        _loginLink = findViewById(R.id.link_login);
        _signupButton = findViewById(R.id.btn_signup);
        _nameText = findViewById(R.id.input_name);
        _emailText = findViewById(R.id.input_email);
        _mobileText = findViewById(R.id.input_mobile);
        _passwordText = findViewById(R.id.input_password);
        _reEnterPasswordText = findViewById(R.id.input_reEnterPassword);

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NAVIGATE.ToPage(LoginActivity.class,true);
            }
        });
    }

    void signup()
    {
        User user = new User();
        if(!_passwordText.getText().toString().equals(_reEnterPasswordText.getText().toString()))
        {
            NOTIFY.ShowToast("Konfirmasi password dan password tidak sesuai");return;
        }
        user.setPassword(_passwordText.getText().toString());
        user.setName(_nameText.getText().toString());
        user.setPhone(_mobileText.getText().toString());
        user.setUsername(_emailText.getText().toString());
        API.Register(user, new IApiListener() {
            @Override
            public void onStart() {
                LOADER.Show();
            }

            @Override
            public void onSuccess(String result, String message) {
                FILE.SaveToCache("Users/Login",result);
                NAVIGATE.ToPage(RoutesActivity.class,true);
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
