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

public class AddTaskActivity extends AppCompatActivity {

    private EditText edtName, edtTime, edtNote;
    private Button btnSave;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_task);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edtName = findViewById(R.id.edt_task_name);
        edtTime = findViewById(R.id.edt_task_time);
        edtNote = findViewById(R.id.edt_task_note);
        btnSave = findViewById(R.id.btn_save_task);

        btnSave.setOnClickListener(v -> {
            String name = edtName.getText().toString().trim();
            if (name.isEmpty()) {
                edtName.setError("Nhập task");
                return;
            }

            Intent intentResult = new Intent();
            intentResult.putExtra("new Task",name);
            setResult(RESULT_OK, intentResult);

            Toast.makeText(this, "Đã lưu : " + name, Toast.LENGTH_SHORT).show();
            finish();

        });
    }
}
