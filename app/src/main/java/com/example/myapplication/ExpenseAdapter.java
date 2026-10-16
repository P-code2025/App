package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ExpenseAdapter extends ArrayAdapter<Expense> {

    private Context context;
    private ArrayList<Expense> listExpense;

    public ExpenseAdapter(@NonNull Context context, @NonNull ArrayList<Expense> objects) {
        super(context, 0, objects);
        this.context = context;
        this.listExpense = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_expense, parent, false);
        }

        Expense expense = listExpense.get(position);

        if (expense != null) {
            TextView tvTitle = convertView.findViewById(R.id.tv_item_title);
            TextView tvAmount = convertView.findViewById(R.id.tv_item_amount);
            TextView tvCategory = convertView.findViewById(R.id.tv_item_category);
            TextView tvDate = convertView.findViewById(R.id.tv_item_date);

            if (tvTitle != null) {
                tvTitle.setText(expense.getTitle());
            }

            if (tvAmount != null) {
                DecimalFormat decimalFormat = new DecimalFormat("#,###");
                String formattedAmount = decimalFormat.format(expense.getAmount()) + " đ";
                tvAmount.setText(formattedAmount);
            }

            if (tvCategory != null) {
                tvCategory.setText("Danh mục: " + expense.getCategory());
            }

            if (tvDate != null) {
                tvDate.setText(expense.getDate());
            }
        }

        return convertView;
    }
}
