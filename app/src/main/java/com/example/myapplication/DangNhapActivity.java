package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DangNhapActivity extends AppCompatActivity {

    private EditText edtUsername, edtPassword;
    private Button btnLogin;
    private static final String HARDCODED_USER = "admin";
    private static final String HARDCODED_PASS = "123456";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dang_nhap);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edtUsername = findViewById(R.id.username);
        edtPassword = findViewById(R.id.password);
        btnLogin = findViewById(R.id.btn_log_in);

        btnLogin.setOnClickListener(v -> {
            String inputUser = edtUsername.getText().toString().trim();
            String inputPass = edtPassword.getText().toString().trim();

            if (inputUser.isEmpty()) {
                edtUsername.setError("Vui lòng nhập tên đăng nhập");
                Toast.makeText(DangNhapActivity.this, "Vui lòng nhập tên đăng nhập", Toast.LENGTH_SHORT).show();
                return;
            }

            if (inputPass.isEmpty()) {
                edtPassword.setError("Vui lòng nhập mật khẩu");
                Toast.makeText(DangNhapActivity.this, "Vui lòng nhập mật khẩu", Toast.LENGTH_SHORT).show();
                return;
            }

            if (HARDCODED_USER.equals(inputUser) && HARDCODED_PASS.equals(inputPass)) {
                Toast.makeText(DangNhapActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(DangNhapActivity.this, MainActivity.class);
                intent.putExtra("username", inputUser);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(DangNhapActivity.this, "Sai tên đăng nhập hoặc mật khẩu", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
