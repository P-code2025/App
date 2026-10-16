package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView tvTotalAmount;
    private ListView lvExpense;
    private Button btnAddExpense;

    private ArrayList<Expense> listExpense;
    private ExpenseAdapter expenseAdapter;

    private ActivityResultLauncher<Intent> addExpenseLauncher;
    private ActivityResultLauncher<Intent> detailExpenseLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvTotalAmount = findViewById(R.id.tv_total_amount);
        lvExpense = findViewById(R.id.lv_expense);
        btnAddExpense = findViewById(R.id.btn_add_expense);

        listExpense = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String todayStr = sdf.format(new Date());

        listExpense.add(new Expense("Ăn sáng", 30000, "Ăn uống", todayStr, "Bánh mì + Cà phê"));
        listExpense.add(new Expense("Đổ xăng", 50000, "Đi lại", todayStr, "Xăng RON 95"));
        listExpense.add(new Expense("Mua sách", 120000, "Học tập", todayStr, "Sách Lập trình Android"));

        expenseAdapter = new ExpenseAdapter(this, listExpense);
        lvExpense.setAdapter(expenseAdapter);

        double total = 0;
        for (Expense expense : listExpense) {
            total += expense.getAmount();
        }
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        tvTotalAmount.setText(decimalFormat.format(total) + " đ");

        addExpenseLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Expense newExpense = (Expense) result.getData().getSerializableExtra("newExpense");
                        if (newExpense != null) {
                            listExpense.add(newExpense);
                            expenseAdapter.notifyDataSetChanged();
                            double currentTotal = 0;
                            for (Expense e : listExpense) {
                                currentTotal += e.getAmount();
                            }
                            tvTotalAmount.setText(decimalFormat.format(currentTotal) + " đ");
                            Toast.makeText(MainActivity.this, "Đã thêm khoản chi mới", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        detailExpenseLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        int deletePosition = result.getData().getIntExtra("deletePosition", -1);
                        if (deletePosition >= 0 && deletePosition < listExpense.size()) {
                            listExpense.remove(deletePosition);
                            expenseAdapter.notifyDataSetChanged();
                            double currentTotal = 0;
                            for (Expense e : listExpense) {
                                currentTotal += e.getAmount();
                            }
                            tvTotalAmount.setText(decimalFormat.format(currentTotal) + " đ");
                            Toast.makeText(MainActivity.this, "Đã xóa khoản chi", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        btnAddExpense.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddExpenseActivity.class);
            addExpenseLauncher.launch(intent);
        });

        lvExpense.setOnItemClickListener((parent, view, position, id) -> {
            Expense selectedExpense = listExpense.get(position);
            Intent intent = new Intent(MainActivity.this, ExpenseDetailActivity.class);
            intent.putExtra("selectedExpense", selectedExpense);
            intent.putExtra("position", position);
            detailExpenseLauncher.launch(intent);
        });
    }
}
