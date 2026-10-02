package com.example.myapplication;

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

public class DangKyActivity extends AppCompatActivity {

    private EditText edtUsername, edtPassword, edtConfirmPassword;
    private Button btnRegister;
    private TextView tvGoToLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dang_ky);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edtUsername = findViewById(R.id.edt_reg_username);
        edtPassword = findViewById(R.id.edt_reg_password);
        edtConfirmPassword = findViewById(R.id.edt_reg_confirm_password);
        btnRegister = findViewById(R.id.btn_register);
        tvGoToLogin = findViewById(R.id.tv_go_to_login);

        btnRegister.setOnClickListener(v -> {
            String user = edtUsername.getText().toString().trim();
            String pass = edtPassword.getText().toString().trim();
            String confirmPass = edtConfirmPassword.getText().toString().trim();

            if (user.isEmpty()) {
                edtUsername.setError("Vui lòng nhập tên đăng nhập");
                return;
            }

            if (pass.isEmpty()) {
                edtPassword.setError("Vui lòng nhập mật khẩu");
                return;
            }

            if (!pass.equals(confirmPass)) {
                edtConfirmPassword.setError("Mật khẩu xác nhận không trùng khớp");
                return;
            }

            String hashedPassword = PasswordUtils.hashPassword(pass);

            SharedPreferences pref = getSharedPreferences("UserAccount", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("username", user);
            editor.putString("password", hashedPassword);
            editor.apply(); // Lưu dữ liệu

            Toast.makeText(this, "Đăng ký tài khoản thành công!", Toast.LENGTH_SHORT).show();
            finish();
        });

        tvGoToLogin.setOnClickListener(v -> finish());
    }
}
