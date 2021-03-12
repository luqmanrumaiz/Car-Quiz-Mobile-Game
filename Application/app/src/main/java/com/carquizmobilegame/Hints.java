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

import java.util.Arrays;
import java.util.List;

public class Hints extends AppCompatActivity {

    private final Quiz quiz = new Quiz();

    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
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

        EditText letterEditText = findViewById(R.id.hints_edit_text);
        String guessedLetter = letterEditText.getText().toString();

        Button identifyButton = findViewById(R.id.identify_button);

        identifyButton.setOnClickListener(v ->
        {
            if (guessedLetter.equals(""))
            {
                toast = quiz.showToast(false, "Please enter a Letter", getApplicationContext());
            }
        });

        List<String> randomCarNames = Arrays.asList(getResources().getStringArray(R.array.car_names_array));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        TextView timerTextView = new TextView(this);
        TextViewCompat.setTextAppearance(timerTextView, R.style.WhiteSmallText);

        timerTextView.setTypeface(timerTextView.getTypeface(), Typeface.BOLD);

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
}