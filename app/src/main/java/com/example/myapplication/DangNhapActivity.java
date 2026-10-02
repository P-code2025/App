package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
    private TextView tvGoToRegister;

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
        tvGoToRegister = findViewById(R.id.tv_go_to_register);

        tvGoToRegister.setOnClickListener(v -> {
            Intent i = new Intent(DangNhapActivity.this, DangKyActivity.class);
            startActivity(i);
        });

        btnLogin.setOnClickListener(v -> {
            String inputUser = edtUsername.getText().toString().trim();
            String inputPass = edtPassword.getText().toString().trim();

            if (inputUser.isEmpty()) {
                edtUsername.setError("Vui lòng nhập tên đăng nhập");
                return;
            }

            if (inputPass.isEmpty()) {
                edtPassword.setError("Vui lòng nhập mật khẩu");
                return;
            }

            SharedPreferences pref = getSharedPreferences("UserAccount", MODE_PRIVATE);
            String savedUser = pref.getString("username", null);
            String savedPassHash = pref.getString("password", null);

            if (savedUser == null || savedPassHash == null) {
                Toast.makeText(this, "Chưa có tài khoản! Vui lòng bấm 'Đăng ký ngay' ở bên dưới.", Toast.LENGTH_LONG).show();
                return;
            }

            String inputPassHash = PasswordUtils.hashPassword(inputPass);

            if (inputUser.equals(savedUser) && inputPassHash.equals(savedPassHash)) {
                Toast.makeText(this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();

                Intent i = new Intent(DangNhapActivity.this, MainActivity.class);
                i.putExtra("username", inputUser);
                startActivity(i);
                finish();
            } else {
                Toast.makeText(this, "Tên đăng nhập hoặc Mật khẩu không chính xác!", Toast.LENGTH_LONG).show();
            }
        });

    }
}
