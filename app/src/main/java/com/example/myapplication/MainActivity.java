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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

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

        arrTasks.add("Đi học (08:00)");
        arrTasks.add("Làm bài tập Android (14:00)");
        arrTasksSapToi.add("Học từ vựng Tiếng Anh (Ngày mai)");
        arrTasksSapToi.add("Đi tập thể thao (Cuối tuần)");

        ArrayAdapter<String> adap = new ArrayAdapter<>(this, R.layout.item_task, R.id.tv_task_title, arrTasks);
        ArrayAdapter<String> adapSapToi = new ArrayAdapter<>(this, R.layout.item_task, R.id.tv_task_title, arrTasksSapToi);

        lvTask.setAdapter(adap);
        lvTaskSapToi.setAdapter(adapSapToi);

        ActivityResultLauncher<Intent> addTaskLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        String newTask = result.getData().getStringExtra("new Task");
                        String taskTime = result.getData().getStringExtra("task Time");

                        if (newTask != null && !newTask.isEmpty()) {
                            String displayText = newTask;
                            if (taskTime != null && !taskTime.isEmpty()) {
                                displayText += " (" + taskTime + ")";
                            }

                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                            String todayStr = sdf.format(new Date());

                            if (taskTime == null || taskTime.isEmpty() || taskTime.contains(todayStr)) {
                                arrTasks.add(displayText);
                                adap.notifyDataSetChanged();
                                Toast.makeText(MainActivity.this, "Đã thêm vào Công việc hôm nay", Toast.LENGTH_SHORT).show();
                            } else {
                                arrTasksSapToi.add(displayText);
                                adapSapToi.notifyDataSetChanged();
                                Toast.makeText(MainActivity.this, "Đã thêm vào Công việc sắp tới", Toast.LENGTH_SHORT).show();
                            }
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
