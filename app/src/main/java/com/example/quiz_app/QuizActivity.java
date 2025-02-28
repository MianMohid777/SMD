package com.example.quiz_app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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

    RadioButton option1;
    RadioButton option2;
    RadioButton option3;

    RadioButton option4;

    TextView counter;

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
        EdgeToEdge.enable(this);
        setContentView(R.layout.quiz_activity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.quiz), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        String username = getIntent().getStringExtra("username"); // Getting the Username Data from previous Intent

        init(); // Initialization
        createQuiz(); // Quiz Creation

        Set<Integer> keys = questionMap.keySet();

        display(); // Displaying the Quiz Dynamically

        // Event Listener
        nextButton.setOnClickListener(v -> {

            int selectedId = options.getCheckedRadioButtonId();

            if (selectedId != -1) {
                String radioId = getResources().getResourceEntryName(selectedId); // Getting the Option Selected via Radio Button Id

                switch (radioId) {
                    case "r1":
                        addAnswer(1);
                        break;
                    case "r2":
                       addAnswer(2);
                        break;
                    case "r3":
                       addAnswer(3);
                        break;
                    case "r4":
                       addAnswer(4);
                        break;
                    default:
                        Toast.makeText(this, "Error in Selection", Toast.LENGTH_SHORT).show();
                        break;

                }

                if (this.itr+1 < keys.size()) {

                    // Activating the Previous Button
                    if (!prevButton.isActivated()) {
                        prevButton.setVisibility(TextView.VISIBLE);
                        handlePrevious();
                    }
                    itr++;

                    display();

                    if(answers.size() == itr)
                        options.clearCheck();
                    else
                        setStoredAnswers();

                } else {
                    // Onto the Next Activity
                    Intent i = new Intent(QuizActivity.this, ResultActivity.class);
                    i.putExtra("username", username);
                    i.putExtra("score", computeResult());
                    i.putExtra("total", solutionKey.size());
                    startActivity(i);
                    finish();
                }

            } else {
                Toast.makeText(this, "Select an Option to Proceed !", Toast.LENGTH_SHORT).show();
            }

        });



    }


    void init() {
        questionTxt = findViewById(R.id.question);

        options = findViewById(R.id.options);

        option1 = findViewById(R.id.r1);
        option2 = findViewById(R.id.r2);
        option3 = findViewById(R.id.r3);
        option4 = findViewById(R.id.r4);


        counter = findViewById(R.id.counter);

        prevButton = findViewById(R.id.prevBtn);
        nextButton = findViewById(R.id.nextBtn);

        questionMap = new HashMap<>();
        solutionKey = new LinkedList<>();
        answers = new ArrayList<>();

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
        // Event Listener for Previous Button
        prevButton.setOnClickListener(v -> {
            itr--;
            if (this.itr == 0)
                prevButton.setVisibility(TextView.INVISIBLE);

            display();
            setStoredAnswers();
            nextButton.setText("Next");

        });
    }

    @SuppressLint("SetTextI18n")
    void display() {
        Set<Integer> keys = questionMap.keySet();
        ArrayList<Integer> keyList = new ArrayList<>(keys);

        Question nextQuest = questionMap.get(keyList.get(itr));

        if (nextQuest != null) {
            questionTxt.setText(nextQuest.getQuestion());
            option1.setText(nextQuest.getOption1());
            option2.setText(nextQuest.getOption2());
            option3.setText(nextQuest.getOption3());
            option4.setText(nextQuest.getOption4());

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.itr + 1).append( "/" ).append(keys.size());

            counter.setText(stringBuilder.toString());

            if (itr + 1 == keys.size())
                {nextButton.setText("Finish");}

        }
    }

    int computeResult()
    {
        int score = 0;
        for( int i = 0; i< solutionKey.size(); i++)
            if(Objects.equals(solutionKey.get(i), answers.get(i)))
                score++;
        return score;
    }

    void setStoredAnswers()
    {
        int id = -1;

        switch(answers.get(itr)){

            case 1:
                id = R.id.r1;
                break;
            case 2:
                id = R.id.r2;
                break;
            case 3:
                id = R.id.r3;;
                break;
            case 4:
                id = R.id.r4;;
                break;
            default:
                Toast.makeText(this, "Error in retrieving the Integer Id", Toast.LENGTH_SHORT).show();
                break;
        }
        options.check(id); // Checked the Radio Button Saved Before
    }

    void addAnswer(int val){

        if(answers.size() > itr)
         answers.set(itr, val);
        else
            answers.add(itr,val);
    }
}




