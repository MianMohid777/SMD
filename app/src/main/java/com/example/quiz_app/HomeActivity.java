package com.example.quiz_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HomeActivity extends AppCompatActivity {

    private EditText textField;
    private Button startBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.home_activity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.home), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        init();

        startBtn.setOnClickListener(v -> {

            String userName = textField.getText().toString().trim();

            if (userName.isEmpty()) {
                Toast.makeText(HomeActivity.this, "Provide a Username to proceed !", Toast.LENGTH_LONG).show();
                return;
            }
            Intent intent = new Intent(HomeActivity.this, QuizActivity.class);
            intent.putExtra("username", userName);
            startActivity(intent);
            finish();
        });

    }


    void init() {
        textField = findViewById(R.id.editTxt);
        startBtn = findViewById(R.id.startBtn);
    }
}
