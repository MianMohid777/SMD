package com.example.quiz_app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ResultActivity extends AppCompatActivity {

    @SuppressLint({"SetTextI18n", "QueryPermissionsNeeded"})
    @Override
    protected void onCreate(Bundle saveInstances) {
        super.onCreate(saveInstances);
        EdgeToEdge.enable(this);
        setContentView(R.layout.result_activity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.result), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        // Getting the Data from Intent
        String username = getIntent().getStringExtra("username");
        int score = getIntent().getIntExtra("score",0);
        int total = getIntent().getIntExtra("total",10);

        Log.d("Data", username + " " + score);

        // Dynamically Setting up the Scoreboard

        TextView userName = findViewById(R.id.name);
        TextView scoreView = findViewById(R.id.score);
        ImageView back = findViewById(R.id.backBtn);

        back.setOnClickListener(v -> {

            Intent intent = new Intent(ResultActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }

        );


        userName.setText(username);
        scoreView.setText(" " + score + "/" + total);

        Button shareBtn = findViewById(R.id.shareBtn);

        shareBtn.setOnClickListener(v -> {

            String shareText = "I scored " + score + "/" + total + " on the Quiz App";

            // Create implicit intent
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);


            if (shareIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(Intent.createChooser(shareIntent, "Share via"));
            }
        });
    }
}
