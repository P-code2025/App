package com.example.myapplication;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddExpenseActivity extends AppCompatActivity {

    private EditText edtTitle, edtAmount, edtDate, edtNote;
    private Spinner spCategory;
    private Button btnSave;

    private Calendar calendar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_expense);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edtTitle = findViewById(R.id.edt_add_title);
        edtAmount = findViewById(R.id.edt_add_amount);
        spCategory = findViewById(R.id.sp_add_category);
        edtDate = findViewById(R.id.edt_add_date);
        edtNote = findViewById(R.id.edt_add_note);
        btnSave = findViewById(R.id.btn_save_expense);

        ArrayList<String> categories = new ArrayList<>();
        categories.add("Ăn uống");
        categories.add("Đi lại");
        categories.add("Học tập");
        categories.add("Giải trí");
        categories.add("Khác");

        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategory.setAdapter(categoryAdapter);

        calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        edtDate.setText(sdf.format(calendar.getTime()));

        edtDate.setOnClickListener(v -> {
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, selectedYear, selectedMonth, selectedDay) -> {
                calendar.set(selectedYear, selectedMonth, selectedDay);
                edtDate.setText(sdf.format(calendar.getTime()));
            }, year, month, day);
            datePickerDialog.show();
        });

        btnSave.setOnClickListener(v -> {
            String title = edtTitle.getText().toString().trim();
            String amountStr = edtAmount.getText().toString().trim();
            String category = spCategory.getSelectedItem() != null ? spCategory.getSelectedItem().toString() : "";
            String date = edtDate.getText().toString().trim();
            String note = edtNote.getText().toString().trim();

            if (title.isEmpty()) {
                Toast.makeText(AddExpenseActivity.this, "Vui lòng nhập tên khoản chi", Toast.LENGTH_SHORT).show();
                edtTitle.requestFocus();
                return;
            }

            if (amountStr.isEmpty()) {
                Toast.makeText(AddExpenseActivity.this, "Vui lòng nhập số tiền", Toast.LENGTH_SHORT).show();
                edtAmount.requestFocus();
                return;
            }

            double amount = 0;
            try {
                amount = Double.parseDouble(amountStr);
            } catch (NumberFormatException e) {
                Toast.makeText(AddExpenseActivity.this, "Số tiền không hợp lệ", Toast.LENGTH_SHORT).show();
                return;
            }

            if (amount <= 0) {
                Toast.makeText(AddExpenseActivity.this, "Số tiền phải lớn hơn 0", Toast.LENGTH_SHORT).show();
                edtAmount.requestFocus();
                return;
            }

            Expense newExpense = new Expense(title, amount, category, date, note);

            Intent intentResult = new Intent();
            intentResult.putExtra("newExpense", newExpense);
            setResult(RESULT_OK, intentResult);

            Toast.makeText(AddExpenseActivity.this, "Lưu khoản chi thành công!", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
