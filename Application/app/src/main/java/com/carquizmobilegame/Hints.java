package com.carquizmobilegame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.TextViewCompat;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Hints extends AppCompatActivity {

    private final Quiz quiz = new Quiz();

    private String[] carMakes;

    private Toast toast;

    private List<String> guessedLetters = new ArrayList<>();

    private TextView hintsTextView;

    private String carMake;

    private String result;

    private int fails;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
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
        int randomNumber = quiz.randomlySelectImage(findViewById(R.id.random_car_image));

        carMakes = getResources().getStringArray(R.array.car_makes_array_w_duplicate);

        carMake = carMakes[randomNumber];

        System.out.println(carMake.length());

        TextView hintsTextView = findViewById(R.id.hints_text_view);

        EditText letterEditText = findViewById(R.id.hints_edit_text);


        result = "";
        for(int i = 0; i <= carMake.length(); i++) {
            result = new String(new char[i]).replace("\0", "_");

        }

        hintsTextView.setText(result);

        Button identifyButton = findViewById(R.id.identify_button);

        identifyButton.setOnClickListener(v ->
        {
            String guessedLetter = letterEditText.getText().toString();

            if (fails <= 3)
            {
                toast = quiz.showToast(false, "You have Failed !!!", getApplicationContext());

                identifyButton.setText("Next");

                identifyButton.setOnClickListener(a ->
                {
                    finish();
                    startActivity(getIntent());
                });
            }
            if (guessedLetter.equals(""))

                toast = quiz.showToast(false, "Please enter a Letter", getApplicationContext());

            else if (checkGuessedLetter(guessedLetter))
            {
                hintsTextView.setText(result);
                hintsTextView.setTextColor(getResources().getColor(R.color.green));
            }
            else
            {
                hintsTextView.setTextColor(getResources().getColor(R.color.red));
                fails++;
            }


        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        TextView timerTextView = new TextView(this);
        TextViewCompat.setTextAppearance(timerTextView, R.style.WhiteSmallText);

        timerTextView.setTypeface(timerTextView.getTypeface(), Typeface.BOLD);

        timerTextView.setTextSize(20);

        TextView timerTextView1 = new TextView(this);

        timerTextView1.setText("Hammod");

        new CountDownTimer(20000, 1000)
        {
            public void onTick(long millisUntilFinished)
            {
                timerTextView.setText( "" + millisUntilFinished / 1000);
            }

            public void onFinish()
            {
                quiz.showToast(false, "", getApplicationContext());
            }
        }.start();

        menu.add(0, 0, 1, R.string.countdown_second).
                setActionView(timerTextView).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

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

    public boolean checkGuessedLetter(String guessedLetter)
    {
        boolean matched = false;

        int i = 0;

        char[] myNameChars = result.toCharArray();

        if (! guessedLetter.equals(""))

            for (char letter : carMake.toCharArray())
            {
                System.out.println(Character.toString(letter).toLowerCase());
                if ((guessedLetter.toLowerCase().equals(Character.toString(letter).toLowerCase())))
                {
                    guessedLetters.add(guessedLetter);
                    matched = true;

                    myNameChars[i] = letter;
                }
                i++;
            }

        result = String.valueOf(myNameChars);
        System.out.println(result);
        return matched;
    }
}