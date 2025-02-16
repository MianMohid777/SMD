package com.example.quiz_app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class QuizActivity extends AppCompatActivity {

    // Hooks

    TextView questionTxt;
    RadioGroup options;

    TextView option1;
    TextView option2;
    TextView option3;

    TextView option4;

    TextView counter;

    CardView card1;
    CardView card2;
    CardView card3;
    CardView card4;

    TextView prevButton;

    Button nextButton;

    Map<Integer, Question> questionMap;
    List<Integer> solutionKey;
    List<Integer> answers;

    int itr = 0;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle saveInstances) {
        super.onCreate(saveInstances);
        setContentView(R.layout.quiz_activity);

        String username = getIntent().getStringExtra("username");

        init();
        createQuiz();

        Set<Integer> keys = questionMap.keySet();

        display();

        nextButton.setOnClickListener(v -> {

            int selectedId = options.getCheckedRadioButtonId();

            if (selectedId != -1) {
                String radioId = getResources().getResourceEntryName(selectedId);

                switch (radioId) {
                    case "r1":
                        answers.add(itr,1);
                        break;
                    case "r2":
                        answers.add(itr,2);
                        break;
                    case "r3":
                        answers.add(itr,3);
                        break;
                    case "r4":
                        answers.add(itr,4);
                        break;
                    default:
                        Toast.makeText(this, "Error in Selection", Toast.LENGTH_SHORT).show();
                        break;

                }

                if (this.itr+1 < keys.size()) {

                    if (!prevButton.isActivated()) {
                        prevButton.setVisibility(TextView.VISIBLE);
                        handlePrevious();
                    }
                    itr++;

                    display();

                } else {
                    Toast.makeText(this, " End ", Toast.LENGTH_SHORT).show();

                    Intent i = new Intent(QuizActivity.this, ResultActivity.class);
                    i.putExtra("username", username);
                    i.putExtra("score", computeResult());
                    startActivity(i);
                }

            } else {
                Toast.makeText(this, "Select an Option to Proceed !", Toast.LENGTH_SHORT).show();
            }

        });



    }


    void init() {
        questionTxt = findViewById(R.id.question);

        options = findViewById(R.id.options);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);

        counter = findViewById(R.id.counter);

        card1 = findViewById(R.id.card1);
        card2 = findViewById(R.id.card2);
        card3 = findViewById(R.id.card3);
        card4 = findViewById(R.id.card4);

        prevButton = findViewById(R.id.prevBtn);
        nextButton = findViewById(R.id.nextBtn);

        questionMap = new HashMap<>();
        solutionKey = new LinkedList<>();
        answers = new LinkedList<>();

    }

    void createQuiz() {
        questionMap.put(1, new Question("Test Question 1", "A", "B", "C", "D"));
        questionMap.put(2, new Question("Test Question 2", "AA", "BB", "CC", "DD"));
        questionMap.put(3, new Question("Test Question 3", "AAA", "BBB", "CCC", "DDD"));

        solutionKey.add(2);
        solutionKey.add(1);
        solutionKey.add(4);
    }

    void handlePrevious() {
        prevButton.setOnClickListener(v -> {
            itr--;
            if (this.itr == 0)
            prevButton.setVisibility(TextView.INVISIBLE);

            display();
            nextButton.setText("Next");

        });
    }

    boolean display() {
        Set<Integer> keys = questionMap.keySet();
        ArrayList<Integer> keyList = new ArrayList<>(keys);

        Question nextQuest = questionMap.get(keyList.get(itr));

        if (nextQuest != null) {
            questionTxt.setText(nextQuest.getQuestion());
            option1.setText(nextQuest.getOption1());
            option2.setText(nextQuest.getOption2());
            option3.setText(nextQuest.getOption3());
            option4.setText(nextQuest.getOption4());

            StringBuilder stringBuilder = new StringBuilder(this.itr + 1 + "/" + keys.size());
            counter.setText(stringBuilder.toString());

            if (itr + 1 == keys.size())
            {nextButton.setText("Finish");}

            return true;
        }
        return false;
    }

    int computeResult()
    {
        int score = 0;
        for( int i = 0; i< solutionKey.size(); i++)
            if(Objects.equals(solutionKey.get(i), answers.get(i)))
                score++;
        return score;


    }
}


