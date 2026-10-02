package com.example.myapplication;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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

import java.util.Calendar;

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

        edtTime.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, selectedYear, selectedMonth, selectedDay) -> {
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(this, (timeView, selectedHour, selectedMinute) -> {
                    String formattedTime = String.format("%02d:%02d %02d/%02d/%d",
                            selectedHour, selectedMinute, selectedDay, selectedMonth + 1, selectedYear);
                    edtTime.setText(formattedTime);
                }, hour, minute, true);

                timePickerDialog.show();
            }, year, month, day);

            datePickerDialog.show();
        });

        // Nút Lưu công việc
        btnSave.setOnClickListener(v -> {
            String name = edtName.getText().toString().trim();
            String time = edtTime.getText().toString().trim();

            if (name.isEmpty()) {
                edtName.setError("Vui lòng nhập tên công việc");
                return;
            }

            Intent intentResult = new Intent();
            intentResult.putExtra("new Task", name);
            intentResult.putExtra("task Time", time); // Gửi thêm Thời gian về Màn 2
            setResult(RESULT_OK, intentResult);

            Toast.makeText(this, "Đã lưu : " + name, Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
