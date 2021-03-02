package com.example.cyedemo.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cyedemo.R;
import com.example.cyedemo.databinding.ActivityLoginBinding;

public class Login extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private final String USER_NAME = "anik";
    private final String PASSWORD = "1234";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.signInBtn.setOnClickListener(v -> {

            if (TextUtils.isEmpty(binding.signInPhoneEmilEt.getText())) {
                binding.signInPhoneEmilEt.setError(getString(R.string.enter_user_name));
            } else if (TextUtils.isEmpty(binding.signInPasswordEt.getText())) {
                binding.signInPasswordEt.setError(getString(R.string.enter_valid_password));
                binding.signInPasswordEt.requestFocus();
            } else {
                if (binding.signInPhoneEmilEt.getText().toString().equals(USER_NAME) && binding.signInPasswordEt.getText().toString().equals(PASSWORD)) {
                    startActivity(new Intent(Login.this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(this, "Wrong username or password", Toast.LENGTH_SHORT).show();
                }

            }

        });
    }
}