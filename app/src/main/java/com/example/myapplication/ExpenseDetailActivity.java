package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.DecimalFormat;

public class ExpenseDetailActivity extends AppCompatActivity {

    private TextView tvTitle, tvAmount, tvCategory, tvDate, tvNote;
    private Button btnBack, btnDelete;

    private Expense currentExpense;
    private int expensePosition = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_expense_detail);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvTitle = findViewById(R.id.tv_detail_title);
        tvAmount = findViewById(R.id.tv_detail_amount);
        tvCategory = findViewById(R.id.tv_detail_category);
        tvDate = findViewById(R.id.tv_detail_date);
        tvNote = findViewById(R.id.tv_detail_note);

        btnBack = findViewById(R.id.btn_detail_back);
        btnDelete = findViewById(R.id.btn_detail_delete);

        Intent intent = getIntent();
        if (intent != null) {
            currentExpense = (Expense) intent.getSerializableExtra("selectedExpense");
            expensePosition = intent.getIntExtra("position", -1);
        }

        if (currentExpense != null) {
            tvTitle.setText(currentExpense.getTitle());

            DecimalFormat decimalFormat = new DecimalFormat("#,###");
            String formattedAmount = decimalFormat.format(currentExpense.getAmount()) + " đ";
            tvAmount.setText(formattedAmount);

            tvCategory.setText(currentExpense.getCategory());
            tvDate.setText(currentExpense.getDate());

            if (currentExpense.getNote() != null && !currentExpense.getNote().isEmpty()) {
                tvNote.setText(currentExpense.getNote());
            } else {
                tvNote.setText("Không có ghi chú");
            }
        }

        btnBack.setOnClickListener(v -> finish());
        btnDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(ExpenseDetailActivity.this)
                    .setTitle("Xác nhận xóa")
                    .setMessage("Bạn có chắc chắn muốn xóa khoản chi này không?")
                    .setPositiveButton("Xóa", (dialog, which) -> {
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("deletePosition", expensePosition);
                        setResult(RESULT_OK, resultIntent);
                        finish();
                    })
                    .setNegativeButton("Hủy", null)
                    .show();
        });
    }
}
