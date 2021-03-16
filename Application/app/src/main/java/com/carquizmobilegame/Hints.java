package com.carquizmobilegame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.TextViewCompat;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Hints extends AppCompatActivity {

    private final Quiz quiz = new Quiz();

    private Toast toast;

    private List<String> guessedLetters = new ArrayList<>();
    private List<Integer> previousRandomNumbers = new ArrayList<>();

    private TextView hintsTextView;
    private TextView timerTextView;
    private TextView failsTextView;

    private EditText letterEditText;

    private Button checkLetterMatchButton;

    private String carMake;
    private String result;

    private int fails;

    private boolean timerToggled;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        timerToggled = getIntent().getBooleanExtra("timerToggled", false);

        Intent intent = getIntent();

        intent.getBooleanExtra("timer",false);

        fails = 0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hints);

        Toolbar toolbar = findViewById(R.id.toolbar);

        // Setting the Action Bar as the above Toolbar
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        // Calling the randomlySelectImage to Display a random Car Image
        int randomNumber = quiz.randomlySelectImage(findViewById(R.id.random_car_image), previousRandomNumbers, false);

        previousRandomNumbers.add(randomNumber);

        carMake = Quiz.CAR_MAKES[randomNumber];

        hintsTextView = findViewById(R.id.hints_text_view);
        letterEditText = findViewById(R.id.hints_edit_text);
        checkLetterMatchButton = findViewById(R.id.identify_button);

        result = "";
        for (int i = 0; i <= carMake.length(); i++)
        {
            result = new String(new char[i]).replace("\0", "_");
        }

        hintsTextView.setText(result);
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
            timerTextView = quiz.getTimerTextView(this, getIntent(), checkLetterMatchButton);

            menu.findItem(R.id.timer_text_view).setActionView(timerTextView);
        }

        menu.findItem(R.id.ic_score).setIcon(getResources().getDrawable(R.drawable.ic_cross));
        menu.findItem(R.id.ic_score).setVisible(true);

        failsTextView = new TextView(this);
        failsTextView.setText("0    ");

        TextViewCompat.setTextAppearance(failsTextView, R.style.WhiteSmallText);
        failsTextView.setTypeface(failsTextView.getTypeface(), Typeface.BOLD);
        failsTextView.setTextColor(getResources().getColor(R.color.red));
        failsTextView.setTextSize(20);


        menu.findItem(R.id.score_text_view).setActionView(failsTextView);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy()
    {

        if (toast != null)

            toast.cancel();

        if (timerToggled && quiz.getCountDownTimer() != null)

            quiz.getCountDownTimer().cancel();

        super.onDestroy();
    }

    /**
     * This method displays an appropriate message based on if the User has guessed all Letters of the Random Car Name right,
     * and vise-versa including the Guess Letter Button changing to the Next Button, however if the User still has not missed
     * a guess 3 Times, then simply 1 is added to counter of the Users failed guesses.
     *
     * @param view
     */
    public void checkGuessedLetter(View view)
    {
            String guessedLetter = letterEditText.getText().toString();

            /* This if condition is used to check if the User has won as there must be less than 3 Fails and the number
             * of correctly guessed letters would be the same as the number of letters in the randomly generated Car
             */
            if (fails < 3 || guessedLetters.size() == carMake.length())
            {
                if(guessedLetters.size() == carMake.length())

                    toast = quiz.showToast(true, "You Have Won !!!", "",this);

                else toast = quiz.showToast(false, "You have Failed !!!", "",this);

                checkLetterMatchButton.setText("Next");

                checkLetterMatchButton.setOnClickListener(a ->
                {
                    finish();
                    startActivity(getIntent());
                });
            }
            if (guessedLetter.equals(""))

                toast = quiz.showToast(false, "Please enter a Letter", "",this);

            else
             {
                boolean matched = false;

                int i = 0;

                char[] guessedLetterChars = result.toCharArray();
                for (char letter : carMake.toCharArray())
                {

                    if ((guessedLetter.toLowerCase().equals(Character.toString(letter).toLowerCase())))
                    {
                        guessedLetters.add(guessedLetter);
                        matched = true;
                        guessedLetterChars[i] = letter;
                    }
                    i++;
                }

                result = String.valueOf(guessedLetterChars);

                // This If else condition changes the Color of TextView to green if Matched correctly else red
                if (matched)
                {
                    hintsTextView.setText(result);
                    hintsTextView.setTextColor(getResources().getColor(R.color.green));
                }
                else
                {
                    hintsTextView.setTextColor(getResources().getColor(R.color.red));
                    fails++;
                    failsTextView.setText(fails + "    ");
                }
            }
        }
    }
