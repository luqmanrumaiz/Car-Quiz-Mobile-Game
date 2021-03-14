package com.carquizmobilegame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Hints extends AppCompatActivity {

    private final Quiz quiz = new Quiz();
    private String[] carMakes;
    private Toast toast;
    private List<String> guessedLetters = new ArrayList<>();
    private List<Integer> previousRandomNumbers = new ArrayList<>();
    private TextView hintsTextView;
    private EditText letterEditText;
    private Button checkLetterMatchButton;
    private String carMake;
    private String result;
    private int fails;
    private boolean timerToggled;
    private CountDownTimer countDownTimer;

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
        int randomNumber = quiz.randomlySelectImage(findViewById(R.id.random_car_image), previousRandomNumbers);

        previousRandomNumbers.add(randomNumber);

        carMakes = getResources().getStringArray(R.array.car_makes_array_w_duplicate);
        carMake = carMakes[randomNumber];

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
    public boolean onCreateOptionsMenu(Menu menu) {

        if (timerToggled)
        {
            getMenuInflater().inflate(R.menu.custom_toolbar, menu);

            menu.add(0, 0, 1, R.string.countdown_second)
                    .setActionView(quiz.getTimer(this, getIntent(), checkLetterMatchButton))
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        }
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

        if (timerToggled)

            quiz.stopTimer();



        super.onDestroy();
    }

    public void checkGuessedLetter(View view)
    {
            String guessedLetter = letterEditText.getText().toString();

            if (fails == 2 || guessedLetters.size() == carMake.length())
            {

                if(guessedLetters.size() == carMake.length())

                    toast = quiz.showToast(true, "You Have Won !!!", getApplicationContext());

                else toast = quiz.showToast(false, "You have Failed !!!", getApplicationContext());

                checkLetterMatchButton.setText("Next");

                checkLetterMatchButton.setOnClickListener(a ->
                {
                    finish();
                    startActivity(getIntent());
                });
            }
            if (guessedLetter.equals(""))

                toast = quiz.showToast(false, "Please enter a Letter", getApplicationContext());

            else
                {
                boolean matched = false;

                int i = 0;

                char[] myNameChars = result.toCharArray();

                for (char letter : carMake.toCharArray())
                {

                    if ((guessedLetter.toLowerCase().equals(Character.toString(letter).toLowerCase())))
                    {
                        guessedLetters.add(guessedLetter);
                        matched = true;
                        myNameChars[i] = letter;
                    }
                    i++;
                }

                result = String.valueOf(myNameChars);

                if (matched)
                {
                    hintsTextView.setText(result);
                    hintsTextView.setTextColor(getResources().getColor(R.color.green));
                }
                else
                {
                    hintsTextView.setTextColor(getResources().getColor(R.color.red));
                    fails++;
                }
            }
        }
    }
