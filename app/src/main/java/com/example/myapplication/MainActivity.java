package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

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

        TextView tvWelcome = findViewById(R.id.user_name);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("username")) {
            String username = intent.getStringExtra("username");
            tvWelcome.setText("Hi: " + username);
        }

        ListView lvTask = findViewById(R.id.lv_task);
        ListView lvTaskSapToi = findViewById(R.id.lv_task_sap_toi);
        ArrayList<String> arrTasks = new ArrayList<>();
        ArrayList<String> arrTasksSapToi = new ArrayList<>();

        arrTasks.add("Đi học");
        arrTasks.add("Làm bài tập Android");
        arrTasksSapToi.add("Học từ vựng Tiếng Anh");
        arrTasksSapToi.add("Đi tập thể thao");

        ArrayAdapter<String> adap = new ArrayAdapter<>(this, R.layout.item_task, R.id.tv_task_title, arrTasks);
        ArrayAdapter<String> adapSapToi = new ArrayAdapter<>(this, R.layout.item_task, R.id.tv_task_title, arrTasksSapToi);

        lvTask.setAdapter(adap);
        lvTaskSapToi.setAdapter(adapSapToi);

        ActivityResultLauncher<Intent> addTaskLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        String newTask = result.getData().getStringExtra("new Task");
                        if (newTask != null && !newTask.isEmpty()) {
                            arrTasks.add(newTask);
                            adap.notifyDataSetChanged();
                            Toast.makeText(MainActivity.this, "Đã thêm task: " + newTask, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        TextView tvAddTask = findViewById(R.id.btn_add_task);
        tvAddTask.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, AddTaskActivity.class);
            addTaskLauncher.launch(i);
        });
    }
}
