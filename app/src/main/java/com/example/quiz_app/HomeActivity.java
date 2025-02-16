package com.example.quiz_app;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    private EditText textField;
    private Button startBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        init();

        startBtn.setOnClickListener(v -> {

            String userName = textField.getText().toString().trim();

            if(userName.isEmpty()) {
                Toast.makeText(HomeActivity.this, "Provide a Username to proceed !", Toast.LENGTH_LONG).show();
                return;
            }
            Intent intent = new Intent(HomeActivity.this, QuizActivity.class);
            intent.putExtra("username",userName);
            startActivity(intent);
            finish();
        });

    }


    void init(){
        textField = findViewById(R.id.editTxt);
        startBtn = findViewById(R.id.startBtn);
    }
}
