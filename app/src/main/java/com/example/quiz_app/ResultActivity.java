package com.example.quiz_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle saveInstances) {
        super.onCreate(saveInstances);
        setContentView(R.layout.result_activity);

        // Getting the Data from Intent
        String username = getIntent().getStringExtra("username");
        int score = getIntent().getIntExtra("score",0);

        Log.d("Data", username + " " + score);

        // Dynamically Setting up the Scoreboard

        TextView userName = findViewById(R.id.name);
        TextView scoreView = findViewById(R.id.score);

        userName.setText(username);
        scoreView.setText(" " + score + "/10");

        Button shareBtn = findViewById(R.id.shareBtn);

        shareBtn.setOnClickListener(v -> {

            String shareText = "I scored " + score + "/10 on the Quiz App";

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
