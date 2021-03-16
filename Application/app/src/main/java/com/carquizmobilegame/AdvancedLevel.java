package com.carquizmobilegame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.TextViewCompat;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AdvancedLevel extends AppCompatActivity {

    private final Quiz quiz = new Quiz();

    private Toast toast;          // 011 2853966

    private int attempts;
    private int score;

    private boolean timerToggled;

    private Button guessImageButton;

    private TextView timerTextView;
    private TextView scoreTextView;

    private EditText imageOneEditText, imageTwoEditText, imageThreeEditText;

    private String carMakeOne, carMakeTwo, carMakeThree;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        // This List will hold previousRandomNumbers in order to avoid duplication of Random Car Makes
        List<Integer> previousRandomNumbers = new ArrayList<>();
        quiz.setMultipleAttempts(true);
        timerToggled = getIntent().getBooleanExtra("timerToggled", false);
        attempts = 0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_level);

        Toolbar toolbar = findViewById(R.id.toolbar);

        // Setting the Action Bar as the above Toolbar
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        imageOneEditText = findViewById(R.id.random_car_edit_text_1);
        imageTwoEditText = findViewById(R.id.random_car_edit_text_2);
        imageThreeEditText = findViewById(R.id.random_car_edit_text_3);

        guessImageButton = findViewById(R.id.identify_image_button);


        int randomNumberOne = quiz.randomlySelectImage(findViewById(R.id.random_car_image_1),
                previousRandomNumbers, false);
        previousRandomNumbers.add(randomNumberOne);

        int randomNumberTwo = quiz.randomlySelectImage(findViewById(R.id.random_car_image_2),
                previousRandomNumbers, false);
        previousRandomNumbers.add(randomNumberTwo);

        int randomNumberThree = quiz.randomlySelectImage(findViewById(R.id.random_car_image_3)
                , previousRandomNumbers, false);
        previousRandomNumbers.add(randomNumberOne);

        guessImageButton.setOnClickListener(this::checkImageSelected);

        carMakeOne = Quiz.CAR_MAKES[randomNumberOne];
        carMakeTwo = Quiz.CAR_MAKES[randomNumberTwo];
        carMakeThree = Quiz.CAR_MAKES[randomNumberThree];

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        /* https://suragch.medium.com/adding-a-menu-to-the-toolbar-in-android-60d096f9fb89
         * (Summary - How to make a Custom Toolbar)
         */
        getMenuInflater().inflate(R.menu.custom_toolbar, menu);

        if (timerToggled)
        {
            menu.findItem(R.id.ic_timer).setVisible(true);
            timerTextView = quiz.getTimerTextView(this, getIntent(), guessImageButton);

            menu.findItem(R.id.timer_text_view).setActionView(timerTextView);
        }

        menu.findItem(R.id.ic_score).setVisible(true);
        scoreTextView = new TextView(this);
        scoreTextView.setText("0    ");

        TextViewCompat.setTextAppearance(scoreTextView, R.style.WhiteSmallText);
        scoreTextView.setTypeface(scoreTextView.getTypeface(), Typeface.BOLD);
        scoreTextView.setTextColor(getResources().getColor(R.color.yellow));
        scoreTextView.setTextSize(20);


        menu.findItem(R.id.score_text_view).setActionView(scoreTextView);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home)
        {
            // close this activity and return to home page
            finish();
            startActivity(new Intent(this, HomePage.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {

        if (toast != null)

            toast.cancel();

        if (timerToggled && quiz.getCountDownTimer() != null)

            quiz.getCountDownTimer().cancel();

        super.onDestroy();
    }

    public void checkImageSelected(View view)
    {
        attempts ++;
        quiz.setAttempts(attempts);

        if (imageOneEditText.getText().toString().equals("") || imageTwoEditText.getText().toString().equals("")
                || imageThreeEditText.getText().toString().equals(""))

            toast = quiz.showToast(false, "Please make sure all Text Boxes are filled !!!", "", this);
        else {
            boolean matchOne = false, matchTwo = false, matchThree = false;

            // 03 If Conditions that sets the 03 EditTexts to Green and disables it if an Image is matched

            if (imageOneEditText.getText().toString().toLowerCase().equals(carMakeOne.toLowerCase())) {
                imageOneEditText.setTextColor(getResources().getColor(R.color.green));
                imageOneEditText.setEnabled(false);

                if (!matchOne) {
                    score++;
                    scoreTextView.setText(score + " ");
                }
                matchOne = true;
            }
            if (imageTwoEditText.getText().toString().toLowerCase().equals(carMakeTwo.toLowerCase())) {
                imageTwoEditText.setTextColor(getResources().getColor(R.color.green));
                imageTwoEditText.setEnabled(false);

                if (!matchTwo) {
                    score++;
                    scoreTextView.setText(score + " ");
                }
                matchTwo = true;
            }
            if (imageThreeEditText.getText().toString().toLowerCase().equals(carMakeThree.toLowerCase())) {
                imageThreeEditText.setTextColor(getResources().getColor(R.color.green));
                imageThreeEditText.setEnabled(false);

                if (!matchThree) {
                    score++;
                    scoreTextView.setText(score + " ");
                }
                matchThree = true;
            }
            // This If Condition add 1 to fails if any of the Cars are not matched Correctly
            if (matchOne || matchTwo || matchThree) {
                score++;
            }

            /* 03 If Conditions that sets the 03 EditTexts to Red it if an Image is matched, its not
             * disabled it is just used to show that the attempt made is not valid
             */
            if (!matchOne) {
                imageOneEditText.setTextColor(getResources().getColor(R.color.red));

                if (attempts == 3 && score < 3) imageOneEditText.setText(carMakeOne);
            }
            if (!matchTwo) {
                imageTwoEditText.setTextColor(getResources().getColor(R.color.red));

                if (attempts == 3 && score < 3) imageTwoEditText.setText(carMakeTwo);
            }
            if (!matchThree) {
                imageThreeEditText.setTextColor(getResources().getColor(R.color.red));

                if (attempts == 3 && score < 3) imageThreeEditText.setText(carMakeThree);
            }

            /* This If Condition shows an appropriate message and switches the Guess Car Make Button to the Next
             * Button if the User Won or Lost respectively, However in the Case where the number of attempts
             * aren't over then the Timer is reset.
             */
            if (matchOne && matchTwo & matchThree)
            {
                toast = quiz.showToast(true, "Correct !!!", "", this);
                quiz.restartRound(this, getIntent(), guessImageButton);
                quiz.stopTimer();

            } else if (attempts == 3) {
                toast = quiz.showToast(false, "You have failed due to 03 Incorrect Guesses !!!", "", this);
                quiz.restartRound(this, getIntent(), guessImageButton);
            }
        }
    }
}
